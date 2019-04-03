/*========================================================================
  PISA  (www.tik.ee.ethz.ch/pisa/)
  ========================================================================
  Computer Engineering (TIK)
  ETH Zurich
  ========================================================================
  ECEA - Epsilon- Constraint EA
  
  Implements most functions.
  
  file: ecea_functions.c
  author: Marco Laumanns, laumanns@tik.ee.ethz.ch

  last change: $date$
  ========================================================================
*/


#include "ecea.h"

#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <assert.h>
#include <math.h>





/* common parameters */
int alpha;  /* number of individuals in initial population */
int mu;     /* number of individuals selected as parents */
int lambda; /* number of offspring individuals */
int dim;    /* number of objectives */


/* local parameters from paramfile*/
int seed;   /* seed for random number generator */
int max_iterations;


/* other variables */
char cfgfile[FILE_NAME_LENGTH];  /* 'cfg' file (common parameters) */
char inifile[FILE_NAME_LENGTH];  /* 'ini' file (initial population) */
char selfile[FILE_NAME_LENGTH];  /* 'sel' file (parents) */
char arcfile[FILE_NAME_LENGTH];  /* 'arc' file (archive) */
char varfile[FILE_NAME_LENGTH];  /* 'var' file (offspring) */


/* population containers */

pop *pp_all = NULL;
pop *pp_new = NULL;
pop *pp_sel = NULL;

long int counter_i;
ind *P;
int P_size;
region *Q;
int Q_size;
constraint **e;
double *epsilon;
double *epsilon_prime;
int iteration;



/*-----------------------| initialization |------------------------------*/

void initialize(char *paramfile, char *filenamebase)
/* Performs the necessary initialization to start in state 0. */
{
    FILE *fp;
    int result;
    char str[CFG_ENTRY_LENGTH];
    int i;
    
    /* reading parameter file with parameters for selection */
    fp = fopen(paramfile, "r");
    assert(fp != NULL);
    
    fscanf(fp, "%s", str);
    assert(strcmp(str, "seed") == 0);
    result = fscanf(fp, "%d", &seed);

    fscanf(fp, "%s", str);
    assert(strcmp(str, "max_iterations") == 0);
    result = fscanf(fp, "%d", &max_iterations); /* fscanf() returns EOF
                                               if reading fails. */
    assert(result != EOF); /* no EOF, parameters correctly read */
    
    fclose(fp);
    
    srand(seed); /* seeding random number generator */
    
    sprintf(varfile, "%svar", filenamebase);
    sprintf(selfile, "%ssel", filenamebase);
    sprintf(cfgfile, "%scfg", filenamebase);
    sprintf(inifile, "%sini", filenamebase);
    sprintf(arcfile, "%sarc", filenamebase);
    
    /* reading cfg file with common configurations for both parts */
    fp = fopen(cfgfile, "r");
    assert(fp != NULL);
    
    fscanf(fp, "%s", str);
    assert(strcmp(str, "alpha") == 0);
    fscanf(fp, "%d", &alpha);
    assert(alpha >= 1);
    
    fscanf(fp, "%s", str);
    assert(strcmp(str, "mu") == 0);
    fscanf(fp, "%d", &mu);
    assert(mu >= 1);
    
    fscanf(fp, "%s", str);
    assert(strcmp(str, "lambda") == 0);
    fscanf(fp, "%d", &lambda);
    assert(lambda >= 1);
    
    fscanf(fp, "%s", str);
    assert(strcmp(str, "dim") == 0);
    result = fscanf(fp, "%d", &dim);
    assert(result != EOF); /* no EOF, 'dim' correctly read */
    assert(dim > 0);
    
    fclose(fp);
    
    /* create individual and archive pop */
    pp_all = create_pop(alpha + lambda, dim);
    pp_sel = create_pop(mu, dim);    
    pp_new = NULL;

    P = NULL;
    P_size = 0;
    Q = NULL;
    Q_size = 0;
    e = (constraint**) chk_malloc(dim * sizeof(constraint*));
    for (i = 0; i < dim; i++)
    {
        e[i] = create_constraint(PISA_MAXDOUBLE, NULL);
        e[i] = create_constraint(-PISA_MAXDOUBLE, e[i]);
    }
    epsilon = (double*) chk_malloc(dim * sizeof(double));
    epsilon_prime = (double*) chk_malloc(dim * sizeof(double));
}


void initialize_outer_loop()
{
    counter_i = pow(P_size + 1, dim - 1);
}


int initialize_inner_loop()
{
    int j;
    long int i;
    long int d;
    constraint *c;

    do
    {
        counter_i = counter_i - 1;
        iteration = 0;

        /* printf("inner_loop: %ld %d %d \n",counter_i,iteration,P_size); */

        if (counter_i < 0)
        {
            return (3);
        }    
        
        /* retrieve right constraint values */
        i = pow(P_size + 1, dim - 1) - 1 - counter_i; /* minimizing */
        for (j = 1; j < dim; j++)
        {
            d = i % (P_size + 1);
            assert((i - d) % (P_size + 1) == 0); 
            i = (i - d) / (P_size + 1);
            
            c = e[j];
            while (d > 0)
            {
                c = c->next;
                d--;
            }
            epsilon[j] = c->value;
            epsilon_prime[j] = (c->next)->value;
            /* printf("[%g,%g[ ",epsilon[j],epsilon_prime[j]); */
            /* printf("\n"); */
        }   
    }
    while (opt_found());
    
    return (0);
}


/*-------------------| memory allocation functions |---------------------*/

void* chk_malloc(size_t size)
/* Wrapper function for malloc(). Checks for failed allocations. */
{
    void *return_value = malloc(size);
    if (return_value == NULL)
        PISA_ERROR("Selector: Out of memory.");
    return (return_value);
}


pop* create_pop(int maxsize, int dim)
/* Allocates memory for a population. */
{
    int i;
    pop *pp;
    
    assert(dim >= 0);
    assert(maxsize >= 0);
    
    pp = (pop*) chk_malloc(sizeof(pop));
    pp->size = 0;
    pp->maxsize = maxsize;
    pp->ind_array = (ind**) chk_malloc(maxsize * sizeof(ind*));
    
    for (i = 0; i < maxsize; i++)
      pp->ind_array[i] = NULL;
    
    return (pp);
}


ind* create_ind(int dim)
/* Allocates memory for one individual. */
{
    ind *p_ind;
    
    assert(dim >= 0);
    
    p_ind = (ind*) chk_malloc(sizeof(ind));
    
    p_ind->index = -1;
    p_ind->f = (double*) chk_malloc(dim * sizeof(double));
    p_ind->previous = NULL;
    return (p_ind);
}


constraint* create_constraint(double value, constraint *next)
{
  constraint *e = (constraint*) chk_malloc(sizeof(constraint));

  e->value = value;
  e->next = next;

  return (e);
}


region* create_region(int dim)
{
    region *r;
    
    assert(dim >= 0);
    
    r = (region*) chk_malloc(sizeof(ind));
    
    r->l = (double*) chk_malloc(dim * sizeof(double));
    r->u = (double*) chk_malloc(dim * sizeof(double));
    r->previous = NULL;

    return (r);
}


void free_memory()
/* Frees all memory. */
{
    free_pop(pp_sel);
    complete_free_pop(pp_all);
    free_pop(pp_new);
    pp_sel = NULL;
    pp_all = NULL;
    pp_new = NULL;
}


void free_pop(pop *pp)
/* Frees memory for given population. */
{
   if(pp != NULL)
   {
      free(pp->ind_array);
      free(pp);
   }
}

void complete_free_pop(pop *pp)
/* Frees memory for given population and for all individuals in the
   population. */
{
   int i = 0;
   if (pp != NULL)
   {
      if(pp->ind_array != NULL)
      {
         for (i = 0; i < pp->size; i++)
         {
            if (pp->ind_array[i] != NULL)
            {
               free_ind(pp->ind_array[i]);
               pp->ind_array[i] = NULL;
            }
         }
         
         free(pp->ind_array);
      }
   
      free(pp);
   }
}


void free_ind(ind *p_ind)
/* Frees memory for given individual. */
{
    assert(p_ind != NULL);
     
    free(p_ind->f);
    free(p_ind);
}


void free_region(region *r)
/*  Frees memory for given individual. */
{
    assert(r != NULL);
     
    free(r->l);
    free(r->u);
    free(r);
}


/*-----------------------| selection functions|--------------------------*/

int selection()
{
    ind *x;
    int action = 0;

    /* Join offspring individuals from variator to population */
    mergeOffspring();
    
    /* Performs environmental selection
       (truncates 'pp_all' to size 'alpha') */
    environmentalSelection();

    /* Performs mating selection
       (fills mating pool / offspring population pp_sel */
    matingSelection();
    
    iteration++;

    /* printf("%d %d %d - %d\n",counter_i,iteration,P_size,pp_all->size);*/

    if (iteration >= max_iterations)
    {
        x = opt();
        action = 2;

        if (x != NULL)
        {
            if (check_dominated(x, P) == 0)
            {
                add_region(x);
                /* line 12 */
                update(x->f);

                /* line 13 */
                x->previous = P;
                P = x;
                P_size++;
                
                action = 1;
            }
            else
            {
                free_ind(x);
                x = NULL;
                add_region(NULL);
            }
        }
        else
        {
            add_region(NULL);
        }

        while (pp_all->size > 0)
          {
            pp_all->size--;
            free_ind(pp_all->ind_array[pp_all->size]);
            pp_all->ind_array[pp_all->size] = NULL;
          }
    }    

    return (action);
}


void add_region(ind *x)
{
    int j;
    region *r = create_region(dim);
    
    if (x == NULL)
    {
        for (j = 1; j < dim; j++)
        {
            r->l[j] = epsilon[j];
            r->u[j] = epsilon_prime[j];
        }
    }
    else
    {
        for (j = 1; j < dim; j++)
        {
            r->l[j] = x->f[j];
            r->u[j] = epsilon_prime[j];
        }
    }

    r->previous = Q;
    Q = r;
    Q_size++;
}


void update(double *f)
{
    int j;
    constraint *c;
    constraint *c_new;

    for (j = 1; j < dim; j++)
    {
        c = e[j];
        
        while ((c->next)->value < f[j])
        {
            c = c->next;
        }
        
        c_new = create_constraint(f[j], c->next);
        c->next = c_new;
    }
}


ind* opt()
{
    int j; 
    ind *x = create_ind(dim);

    for (j = 0; j < dim; j++)
    {
        x->f[j] = pp_all->ind_array[0]->f[j];
    }
    x->index = pp_all->ind_array[0]->index;

    if (violation(x) > 0)
    {
        free_ind(x);
        x = NULL;
    }

    return (x);
}


int opt_found()
{
    int j;
    region *r = Q;

    for (j = 1; j < dim; j++)
    {
        if (epsilon[j] == epsilon_prime[j])
        {
            return (2);
        }  
    }

    /*
    while (x != NULL)
    {
        if (violation(x) == 0)
        {
            return (2);
        }
        x = x->previous;
    }
    */

    while (r != NULL)
    {
        int found = 1;
        for (j = 1; j < dim; j++)
        {
            if (r->l[j] > epsilon[j] || r->u[j] < epsilon_prime[j])
            {
                found = 0;
                break;
            }
        }
        if (found == 1)
        {
            return (2);
        }
        r = r->previous;
    }

    return (0);
}


int check_dominated(ind *x, ind *P)
{
    while (P != NULL)
    { 
        int j;
        int weakly_dominated = 1;
        for (j = 0; j < dim; j++)
        {
            if (P->f[j] > x->f[j])
            {
                weakly_dominated = 0;
                break;
            }
        }
        if (weakly_dominated == 1)
        {
            return (1);
        }
        P = P->previous;
    }
    return (0);
}


void mergeOffspring()
{
    int i;
    
    assert(pp_all->size + pp_new->size <= pp_all->maxsize);
    
    for (i = 0; i < pp_new->size; i++)
    {
      pp_all->ind_array[pp_all->size + i] = pp_new->ind_array[i];
    }
    
    pp_all->size += pp_new->size;
    
    free_pop(pp_new);
    pp_new = NULL;
}


void environmentalSelection()
{
  int i;
  int j;
  ind *x;

  /* sort population */
  for (i = 0; i < pp_all->size - 1; i++)
    {
      for (j = pp_all->size - 1; j > i; j--)
        {
          if (dominates(pp_all->ind_array[j], pp_all->ind_array[j-1]))
            {
              x = pp_all->ind_array[j];
              pp_all->ind_array[j] = pp_all->ind_array[j-1];
              pp_all->ind_array[j-1] = x;
            }
        }
    }

  for (i = alpha; i < pp_all->size; i++)
    {
      free_ind(pp_all->ind_array[i]);
      pp_all->ind_array[i] = NULL;
    }
        
  pp_all->size = alpha;
}


void matingSelection()
/* Fills mating pool 'pp_sel' */
{
  int i;

  for (i = 0; i < mu; i++)
    {
      pp_sel->ind_array[i] = pp_all->ind_array[irand(alpha)];
    }

  pp_sel->size = mu;
}


int dominates(ind *a, ind *b)
/* Determines if one individual dominates another.
   Minimizing fitness values. */
{
    double va = violation(a);
    double vb = violation(b);

    if (va < vb)
    {
        return (1);
    }
    else if (va == vb)
    {
        int j;
        for (j = 0; j < dim; j++)
        {
            if (a->f[j] < b->f[j])
            {
                return (1);
            }
            else if (a->f[j] > b->f[j])
            {
                return (0);
            }
        }
    }
    return (0);
}


double violation(ind *a)
{
    int j;
    double v;
    double violation = 0;

    for (j = 1; j < dim; j++)
    {
        v = 0;
        if (a->f[j] >= epsilon_prime[j])
        {
            v = a->f[j] - epsilon_prime[j] + PISA_MINDOUBLE;
        }
        else if (a->f[j] < epsilon[j])
        {
            v = epsilon[j] - a->f[j];
        }
        violation += v;
    }

    return (violation);
}

int irand(int range)
/* Generate a random integer. */
{
    int j;
    j=(int) ((double)range * (double) rand() / (RAND_MAX+1.0));
    return (j);
}


/*--------------------| data exchange functions |------------------------*/

int read_ini()
{
    int i;
    pp_new = create_pop(alpha, dim);
    
    for (i = 0; i < alpha; i++)
	pp_new->ind_array[i] = create_ind(dim);
    pp_new->size = alpha;
    
    return (read_pop(inifile, pp_new, alpha, dim));                    
}


int read_var()
{
    int i;
    pp_new = create_pop(lambda, dim);
    
    for (i = 0; i < lambda; i++)
	pp_new->ind_array[i] = create_ind(dim);
    
    pp_new->size = lambda;
    return (read_pop(varfile, pp_new, lambda, dim));
}


void write_sel()
{
    write_pop(selfile, pp_sel, mu);
}


void write_arc()
{
    int i;
    ind *x = P;
    pop *p = create_pop(pp_all->size + P_size, dim);
    
    for (i = 0; i < pp_all->size; i++)
    {
        p->ind_array[i] = pp_all->ind_array[i];
        /* printf("arc contents: %d\n",p->ind_array[i]->index);*/
    }
    p->size += pp_all->size;

    for (i = 0; i < P_size; i++)
    {
        p->ind_array[pp_all->size + i] = x;
        /* printf("arc contents: %d\n",p->ind_array[i+pp_all->size]->index); */
        x = x->previous;
    }
    p->size += P_size;

    /* printf("write_arc: %d %d %d\n",pp_all->size,P_size,p->size); */
    write_pop(arcfile, p, p->size);
    
    free_pop(p);
    p = NULL;
}


int check_sel()
{
     return (check_file(selfile));
}


int check_arc()
{
     return (check_file(arcfile));
}
