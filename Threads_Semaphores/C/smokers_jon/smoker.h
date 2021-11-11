#ifndef SMOKER_H
#define SMOKER_H
#include "table.h"

#define NUMTIMES 5

typedef struct smoker{
	int id;
	PTY_TABLE pTable;
}TY_SMOKER, *PTY_SMOKER;

extern int end;

void* smoke(void* param);

#endif
