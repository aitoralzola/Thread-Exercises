#ifndef AGENT_H
#define AGENT_H
#include "table.h"
#include "smoker.h"

typedef struct agent{
	PTY_TABLE pTable;
}TY_AGENT, *PTY_AGENT;

extern int end;

void* put(void* param);

#endif
