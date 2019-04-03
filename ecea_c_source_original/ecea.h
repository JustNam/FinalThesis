/*========================================================================
  PISA  (www.tik.ee.ethz.ch/pisa/)
  ========================================================================
  Computer Engineering (TIK)
  ETH Zurich
  ========================================================================
  ECEA - Epsilon-Constraint EA

  Header file.
  
  file: ecea.h
  author: Marco Laumanns, laumanns@tik.ee.ethz.ch

  last change: $date$
  ========================================================================
*/

#ifndef ECEA_H
#define ECEA_H

/*-----------------------| specify Operating System |------------------*/
/* necessary for wait() */

 #define PISA_WIN 
// #define PISA_UNIX

/*----------------------------| macro |----------------------------------*/

#define PISA_ERROR(x) fprintf(stderr, "\nError: " x "\n"), fflush(stderr), exit(EXIT_FAILURE)

/*---------------------------| constants |-------------------------------*/
#define FILE_NAME_LENGTH 128 /* maximal length of filenames */
#define CFG_ENTRY_LENGTH 128 /* maximal length of entries in cfg file */
#define PISA_MAXDOUBLE 1E99  /* Internal maximal value for double */
#define PISA_MINDOUBLE 1E-99  /* Internal minimal value for double */

/*----------------------------| structs |--------------------------------*/

typedef struct ind_st  /* an individual */
{
    int index;
    double *f; /* objective vector */
    struct ind_st *previous;
} ind;

typedef struct pop_st  /* a population */
{
    int size;
    int maxsize;
    ind **ind_array;
} pop;

typedef struct constraint_st
{
  double value;
  struct constraint_st *next;
} constraint;

typedef struct region_st  /* an individual */
{
    double *l;
    double *u; /* objective vector */
    struct region_st *previous;
} region;

/*-------------| functions for control flow (in ecea.c) |------------*/

void write_flag(char *filename, int flag);
int read_flag(char *filename);
void wait(double sec);

/*---------| initialization function (in ecea_functions.c) |---------*/

void initialize(char *paramfile, char *filenamebase);
void initialize_outer_loop();
int initialize_inner_loop();

/*--------| memory allocation functions (in ecea_functions.c) |------*/

void* chk_malloc(size_t size);
ind* create_ind(int dim);
pop* create_pop(int size, int dim);
constraint* create_constraint(double value, constraint *next);
region* create_region(int dim);

void free_memory(void);
void free_pop(pop *pp);
void complete_free_pop(pop *pp);
void free_ind(ind *p_ind);

/*-----| functions implementing the selection (ecea_functions.c) |---*/

int selection();
void add_region(ind *x);
void update(double *f);
ind* opt();
int opt_found();
int check_dominated(ind *x, ind *P);
void mergeOffspring();
void environmentalSelection();
void matingSelection();

int dominates(ind *a, ind *b);
double violation(ind *a);
int irand(int range);

/*--------------------| data exchange functions |------------------------*/

/* in ecea_functions.c */

int read_ini(void);
int read_var(void);
void write_sel(void);
void write_arc(void);
int check_sel(void);
int check_arc(void);

/* in ecea_io.c */

int read_pop(char *filename, pop *pp, int size, int dim);
void write_pop(char *filename, pop *pp, int size);
int check_file(char *filename);

#endif /* ECEA_H */
