#include "car.h"


void* carALoop(void* args) {
    int id = *((int*)args);

    while (!ended) {
        crossBridgeA(id);
        sleep(2);
    }
}

void* carBLoop(void* args) {
    int id = *((int*)args);

    while (!ended) {
        crossBridgeB(id);
        sleep(2);
    }
}


void crossBridgeA (int carID){
    sem_wait(&semaphoreA);
    sem_post(&semaphoreA);
    sem_wait(&mutexA); 
    if(ACrossing == 0) sem_wait(&semaphoreB); 
    ACrossing++;
    sem_post(&mutexA);

    printf("Car %d crossing from A\n", carID);
    sleep(1);
    printf("Car %d crossed the bridge.\n", carID);

    sem_wait(&mutexA);
    ACrossing--;
    if(ACrossing == 0) sem_post(&semaphoreB);
    sem_post(&mutexA);
}


void crossBridgeB (int carID){
    sem_wait(&semaphoreB);
    sem_post(&semaphoreB);
    sem_wait(&mutexB);
    if(BCrossing == 0) sem_wait(&semaphoreA);
    BCrossing++;
    sem_post(&mutexB);

    printf("Car %d crossing from B\n", carID);
    sleep(1);
    printf("Car %d crossed the bridge.\n", carID);

    sem_wait(&mutexB);
    BCrossing--;
    if(BCrossing == 0) sem_post(&semaphoreA);
    sem_post(&mutexB);
}