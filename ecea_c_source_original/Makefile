# Makefile for SPEA2

# Compiler
CC = gcc-3.3.1

# Compiler options
CFLAGS = -g -Wall

# all object files
SEL_OBJECTS = ecea_functions.o ecea_io.o ecea.o

ecea : $(SEL_OBJECTS)
	$(CC) $(CFLAGS) -lm $(SEL_OBJECTS) -o ecea

ecea_functions.o : ecea_functions.c ecea.h
	$(CC) $(CFLAGS) -c ecea_functions.c 

ecea_io.o : ecea_io.c ecea.h
	$(CC) $(CFLAGS) -c ecea_io.c

ecea.o : ecea.c ecea.h
	$(CC) $(CFLAGS) -c ecea.c

clean:
	rm -f *~ *.o

