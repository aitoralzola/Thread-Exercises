#include "main.h"

void* runCashier(void* param){
    TY_CASHIER cashiersParam = *((PTY_CASHIER) param);
    printf("Cashier %d in checkout \n", cashiersParam.id);
    while(!end){
        cashierWorking(cashiersParam.id);
    }
   // printf("CASHIER FINISH\n");
}

void* runCustomer(void* param){
    TY_CUSTOMER customersParam = *((PTY_CUSTOMER) param);
    arrivesCustomer(customersParam.id);
    siendoAtendido(customersParam.id);
    printf("Customer %d pays and go\n", customersParam.id);
}

void cashierWorking(int id){
    if(supermarket.numWaiting == 0)return;
    sem_wait(&supermarket.customerReady);
    printf("Cashier %d serving Customer %d.\n", id, cashiersParam[id].customerActual);
    sleep(1);
    sem_post(&supermarket.semCashier[id]);
    cashiersParam[id].free = 1;
    cashiersParam[id].customerActual = -1;
    sem_post(&supermarket.full);
}

void siendoAtendido(int idCustomer){
    int idCashier;

    sem_wait(&supermarket.full);
    sem_wait(&supermarket.mutex);
    if(cashiersParam[0].free){
        idCashier = 0;
    }else if(cashiersParam[1].free){
        idCashier = 1;
    }else{
        idCashier = 2;
    }
    supermarket.numWaiting--;
    cashiersParam[idCashier].free = 0;
    cashiersParam[idCashier].customerActual = idCustomer;
    sem_post(&supermarket.mutex);
    sem_post(&supermarket.queue);
    sem_post(&supermarket.customerReady);
    sem_wait(&supermarket.semCashier[idCashier]);
}

void arrivesCustomer(int id){
    sem_wait(&supermarket.queue);
    sem_wait(&supermarket.mutex);
    supermarket.numWaiting++;
    printf("Customer %d goes to checkout queue. \n", id);
    sem_post(&supermarket.mutex);
}

void initSupermarket(){
    supermarket.numWaiting = 0;
    sem_init(&supermarket.mutex, 0, 1);
    sem_init(&supermarket.full, 0, NUM_CASHIERS);
    sem_init(&supermarket.queue, 0, 1);
    sem_init(&supermarket.customerReady, 0, 0);

    for(int i = 0; i < NUM_CASHIERS; i++){
        sem_init(&supermarket.semCashier[i], 0, 0);
    }
}

void initParams(){
    initSupermarket();
    for(int i = 0; i < NUM_CUSTOMERS; i++){
        customersParam[i].id = i;
    }

    for(int i = 0; i < NUM_CASHIERS; i++){
        cashiersParam[i].id = i;
        cashiersParam[i].free = 1;
        cashiersParam[i].customerActual = -1;
    }
}

void initThreads(){
    for(int i = 0; i < NUM_CASHIERS; i++){
        pthread_create(&cashiers[i], NULL, runCashier, (void*)&cashiersParam[i]);
    }

    for(int i = 0; i < NUM_CUSTOMERS; i++){
        pthread_create(&customers[i], NULL, runCustomer, (void*)&customersParam[i]);
    }
}

void waitThreads(){
    for(int i = 0; i < NUM_CUSTOMERS; i++){
        pthread_join(customers[i], NULL);
       //printf("Customer %d pays and go.\n", i);
    }

    end = 1;
    for(int i = 0; i < NUM_CASHIERS; i++){
        pthread_join(cashiers[i], NULL);
    }
}

int main(int argc, char** argv){
    initParams();
    initThreads();
    waitThreads();
    printf("Goodbye!\n");
}