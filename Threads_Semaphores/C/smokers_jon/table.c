#include "table.h"

void initValues(PTY_TABLE pTable){
	sem_init(&pTable->tobacco, 0, 0);
	sem_init(&pTable->paper, 0, 0);
	sem_init(&pTable->fire, 0, 0);
	sem_init(&pTable->tableEmpty, 0, 1);
}

void putIngredients(PTY_TABLE pTable){
	int random_num = rand() % 3 + 1;
	
	sem_wait(&pTable->tableEmpty);
	if(random_num == 1){
		printf("Put fire and paper\n");
		sem_post(&pTable->tobacco);
	}else if(random_num == 2){
		printf("Put tobacco and paper\n");
		sem_post(&pTable->fire);
	}else if(random_num == 3){
		printf("Put fire and tobacco\n");
		sem_post(&pTable->paper);
	}
}

void smokerSmokes(int id, PTY_TABLE pTable){
	if(id == 0){
		sem_wait(&pTable->tobacco);
	}else if(id == 1){
		sem_wait(&pTable->fire);
	}else if(id == 2){
		sem_wait(&pTable->paper);
	}
	printf("Smoker %d smoking\n", id);
	sem_post(&pTable->tableEmpty);
	sleep(1);
}
