#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <semaphore.h>

#include "car.h"

#define NUM_THREADS 10

pthread_t thread_list[NUM_THREADS];
sem_t mutexA, mutexB, semaphoreA, semaphoreB;
int ended = 0;
int ACrossing = 0, BCrossing = 0;
int id[NUM_THREADS];

void waitThreads() {
    for (int i = 0; i < NUM_THREADS; i++) {
        pthread_join(thread_list[i], NULL);
    }
    return;
}

void initThreads() {

    for(int i = 0; i < NUM_THREADS; i++) {
        id[i] = i;
    }

    for (int i = 0; i < 5; i++) {
        pthread_create(&thread_list[i], NULL, carALoop, (void*) &id[i]);
    }
    for (int i = 0; i < 5; i++) {
        pthread_create(&thread_list[i + 5], NULL, carBLoop, (void*) &id[i+5]);
    }
}

void destroySem() {
    sem_destroy(&mutexA);
    sem_destroy(&mutexB);
    sem_destroy(&semaphoreA);
    sem_destroy(&semaphoreB);
}

void initVariables() {
    sem_init(&mutexA, 0, 1);
    sem_init(&mutexB, 0, 1);
    sem_init(&semaphoreA, 0, 1);
    sem_init(&semaphoreB, 0, 1);
}

int main() {
    initVariables();
    initThreads();
    getchar();
    ended = 1;
    waitThreads();

    destroySem();

    return 0;
}
