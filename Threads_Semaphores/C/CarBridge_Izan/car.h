#ifndef CAR_H
#define CAR_H

#include <stdio.h>
#include <pthread.h>
#include <semaphore.h>
#include <unistd.h>

extern sem_t semaphoreA, semaphoreB, mutexA, mutexB;
extern int ended, ACrossing, BCrossing;

void* carALoop(void*);
void* carBLoop(void*);
void crossBridgeA (int);
void crossBridgeB (int);

#endif