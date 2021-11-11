#ifndef MAIN_H
#define MAIN_H

#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <semaphore.h>
#include <unistd.h>

#define CARCOUNT 10
#define OPERARIOCOUNT 3

typedef struct garaje
{
    sem_t mutex, queue, full, semOpe[OPERARIOCOUNT], carReady;
    int numWaiting;
}TY_GARAJE, *PTY_GARAJE;

typedef struct coche
{
    int id;
}TY_COCHE, *PTY_COCHE;

typedef struct operario
{
    int id;
    int libre;
    int cocheActual;
}TY_OPERARIO, *PTY_OPERARIO;

TY_COCHE cochesParam[CARCOUNT];
TY_GARAJE garaje;
TY_OPERARIO operariosParam[OPERARIOCOUNT];

pthread_t coches[CARCOUNT];
pthread_t operarios[OPERARIOCOUNT];

int end = 0;

void waitThreads();
void llegaCoche(int id);
void* runCoche(void* param);
void* runOperario(void* param);
void inspeccion(int idCoche);
void initGaraje();
void initParams();
void initThreads();
void operarioInspeccionando(int id);

#endif