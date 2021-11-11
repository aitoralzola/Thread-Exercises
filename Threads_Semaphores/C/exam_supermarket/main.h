#ifndef MAIN_H
#define MAIN_H

#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <semaphore.h>
#include <unistd.h>

#define NUM_CUSTOMERS 10
#define NUM_CASHIERS 3

typedef struct supermarket
{
    sem_t mutex, queue, full, semCashier[NUM_CASHIERS], customerReady;
    int numWaiting;
}TY_SUPERMARKET, *PTY_SUPERMARKET;

typedef struct customer
{
    int id;
}TY_CUSTOMER, *PTY_CUSTOMER;

typedef struct cashier
{
    int id;
    int free;
    int customerActual;
}TY_CASHIER, *PTY_CASHIER;

TY_CUSTOMER customersParam[NUM_CUSTOMERS];
TY_SUPERMARKET supermarket;
TY_CASHIER cashiersParam[NUM_CASHIERS];

pthread_t customers[NUM_CUSTOMERS];
pthread_t cashiers[NUM_CASHIERS];

int end = 0;

void waitThreads();
void arrivesCustomer(int id);
void* runCustomer(void* param);
void* runCashier(void* param);
void siendoAtendido(int idCustomer);
void initSupermarket();
void initParams();
void initThreads();
void cashierWorking(int id);

#endif