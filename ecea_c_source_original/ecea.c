/*========================================================================
  PISA  (www.tik.ee.ethz.ch/pisa/)
  ========================================================================
  Computer Engineering (TIK)
  ETH Zurich
  ========================================================================
  ECEA - Epsilon-Constraint EA

  Implementation in C for the selector side.
  
  Implements Petri net.
  
  file: ecea.c
  author: Marco Laumanns, laumanns@tik.ee.ethz.ch

  last change: $date$
  ========================================================================
*/

/* CAUTION: <unistd.h> is not standard C
   It is used only for sleep() and usleep() in wait().
   In Windows use Sleep() in <windows.h> or implement busy waiting.
*/

#include <stdlib.h>
#include <stdio.h>
#include <assert.h>
#include <math.h>

#ifndef ECEA_H
#define ECEA_H
#include "ecea.h"
#endif

#ifdef PISA_UNIX
#include <unistd.h>
#endif

#ifdef PISA_WIN
#include <windows.h>
#endif

/*------------------------------| main() |-------------------------------*/

int main(int argc, char* argv[])
{
     /* command line parameters */
     char paramfile[FILE_NAME_LENGTH];     /* file with local parameters */
     char filenamebase[FILE_NAME_LENGTH];  /* filename base,
                                              e.g., "dir/test." */
     double poll = 1.0;                    /* polling interval in seconds */

     /* other variables */
     int state = -1;
     char statefile[FILE_NAME_LENGTH];
     int result;
     int action = 0;
     long int runs = 0;

     /* reading command line parameters */
     if (argc != 4)
          PISA_ERROR("Selector: wrong number of arguments");
     sscanf(argv[1], "%s", paramfile);
     sscanf(argv[2], "%s", filenamebase);
     sscanf(argv[3], "%lf", &poll);

     /* generate name of statefile */
     sprintf(statefile, "%ssta", filenamebase);

     /* main loop */
     while (state != 6) /* stop state for selector */
                        /* Caution: if reading of the statefile fails
                           (e.g. no permission) this is an infinite loop */
     {
          state = read_flag(statefile);
          
          if (state == 1) /* inital selection */
          {
              if (action <= 0) /* holds only in the beginning */
              {
                  initialize(paramfile, filenamebase);
              }
              if (action <= 1) /* new point was found */
              {
                  initialize_outer_loop();
              }
              if (action <= 2) /* subspace empty, search in next subspace */
              {
                  action = initialize_inner_loop();
              }

              if (action == 3) /* all subspaces empty => terminate */
              {
                  write_arc();
                  /* write_sel(); ist zu diesem Zeitpunkt leer */
                  state = 6;
                  write_flag(statefile, state);
                  state = -1;
              }
              else if (action == 2) /* subspace already searched */
              {
                  state = 1;
                  write_flag(statefile, state);
              }
              else
              {  
                  result = read_ini();   /* read ini file */
                  if (result == 0)       /* reading ini file successful */
                    {
                      action = selection();      /* do selection */
                      if (action == 1 || action == 2)
                        {
                          state = 9; /* ask variator to reset */
                        }
                      else
                        {
                          write_arc(); /* write arc file */
                          write_sel(); /* write sel file */
                          state = 2;
                        }
                    }
                  write_flag(statefile, state);
              } /* else don't do anything and wait again */
          }
          
          else if (state == 3) /* selection */
          {
               if(check_arc() == 0 && check_sel() == 0)
               {
                    result = read_var();  /* read var file */
                    if (result == 0)      /*reading var file successful */
                    {
                        action = selection();     /* do selection */
                        if (action == 1 || action == 2)
                         {
                             state = 9; /* ask variator to reset */
                         }
                         else
                         {
                             write_arc();  /* write arc file */
                             write_sel();  /* write sel file */
                             state = 2;
                         }
                         write_flag(statefile, state);
                    } /* else don't do anything and wait again */
               } /* else don't do anything and wait again */
          }
          
          else if (state == 5) /* variator just terminated,
                                here you can do what you want */
          {
               state = 6; /* e.g., terminate too */
               write_flag(statefile, state);
          }

          else if (state == 9) /* variator ready for reset,
                                here you can do what you want */
          {
               state = 0; /* now: ask variator to restart and initialize*/
               write_flag(statefile, state);
               runs++;
          }
          
          else if (state == 10) /* reset */
          {
               free_memory();
               state = 11;
               write_flag(statefile, state);
          }
          
          else /* state == -1 (reading failed) or state concerns variator */
          {
               wait(poll);
          }
     } /* state == 6 (stop) */
     
     free_memory();
     state = 7;
     write_flag(statefile, state);
     printf("runs: %ld\n",runs);
     return (0);
}


/*--------------------| functions for control flow |---------------------*/

void write_flag(char* filename, int flag)
/* Write the state flag to given file. */
{
     FILE *fp;

     assert(0 <= flag <= 11);
     
     fp = fopen(filename, "w");
     assert(fp != NULL);
     fprintf(fp, "%d", flag);
     fclose(fp);
     /*     printf("write_state: %d...",flag); */
}


int read_flag(char* filename)
/* Read state flag from given file. */
{
     int result;
     int flag = -1;
     FILE *fp;
     fp = fopen(filename, "r");
     if (fp != NULL)
     {
          result = fscanf(fp, "%d", &flag);
          fclose(fp);
          if (result == 1) /* excatly one element read */
          {
               if (flag < 0 || flag > 11)
                    PISA_ERROR("Selector: Invalid state read from file.");
          }
     }
     /*   printf("read_state: %d\n",flag); */
     return (flag);
}


void wait(double sec)
/* Makes the calling process sleep. */
/* pre: sec >= 0.01 */

{
#ifdef PISA_UNIX
     unsigned int int_sec;
     unsigned int usec;

     assert(sec > 0);
     
     int_sec = (unsigned int) floor(sec);
     usec = (unsigned int) floor((sec - floor(sec)) * 1e6);
     /* split it up, usleep can fail if argument greater than 1e6 */

     
     /* two asserts to make sure your file server doesn't break down */
     assert(!((int_sec == 0) && (usec == 0))); /* should never be 0 */
     assert((int_sec * 1e6) + usec >= 10000);  /* you might change this one
                                                  if you know what you are
                                                  doing */
    
     sleep(int_sec);
     usleep(usec);
#endif

#ifdef PISA_WIN
     unsigned int msec;
     assert(sec > 0);
     msec = (unsigned int) floor(sec * 1e3);
     assert(msec >= 10); /* making sure we are really sleeping for some time*/
     Sleep(msec);
#endif

}
