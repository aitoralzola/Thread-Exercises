#ifndef TABLE_H
#define TABLE_H
#include <stdio.h>
#include <semaphore.h>
#include <stdlib.h>
#include <unistd.h>

typedef struct table{
	sem_t tobacco, fire, paper, tableEmpty;
}TY_TABLE, *PTY_TABLE;

void initValues(PTY_TABLE pTable);
void putIngredients(PTY_TABLE pTable);
void smokerSmokes(int id, PTY_TABLE pTable);

#endif
