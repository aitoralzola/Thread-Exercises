#include <stdio.h>
#include <pthread.h>
#include "table.h"
#include "smoker.h"
#include "agent.h"

#define NUMSMOKERS 3

pthread_t smokers[NUMSMOKERS];
pthread_t agent;

int end = 0;

TY_TABLE table;
TY_SMOKER smokerParam[NUMSMOKERS];
TY_AGENT agentParam;

void initThreads(){
	initValues(&table);
	agentParam.pTable = &table;
	
	pthread_create(&agent, NULL, put, (void*)&agentParam);
	
	for(int i = 0; i < NUMSMOKERS; i++){
		smokerParam[i].id = i;
		smokerParam[i].pTable = &table;
		pthread_create(&smokers[i], NULL, smoke, (void*)&smokerParam[i]);
	}
}

int main(int argc, char** argv){
	initThreads();
	getchar();
	end = 1;
}
