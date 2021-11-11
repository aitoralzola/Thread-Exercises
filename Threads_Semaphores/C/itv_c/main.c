#include "main.h"

void* runOperario(void* param){
    TY_OPERARIO operarioParam = *((PTY_OPERARIO) param);

    while(!end){
        operarioInspeccionando(operarioParam.id);
    }
    printf("OPERARIO A CASA\n");
}

void* runCoche(void* param){
    TY_COCHE cocheParam = *((PTY_COCHE) param);
    llegaCoche(cocheParam.id);
    inspeccion(cocheParam.id);
    printf("Coche %d se va a tomar por culo\n", cocheParam.id);
}

void operarioInspeccionando(int id){
    if(garaje.numWaiting == 0)return;
    sem_wait(&garaje.carReady);
    printf("Operario %d inspeccionando coche %d\n", id, operariosParam[id].cocheActual);
    sleep(1);
    sem_post(&garaje.semOpe[id]);
    operariosParam[id].libre = 1;
    operariosParam[id].cocheActual = -1;
    sem_post(&garaje.full);
}

void inspeccion(int idCoche){
    int idOperario;

    sem_wait(&garaje.full);
    sem_wait(&garaje.mutex);
    if(operariosParam[0].libre){
        idOperario = 0;
    }else if(operariosParam[1].libre){
        idOperario = 1;
    }else{
        idOperario = 2;
    }
    garaje.numWaiting--;
    operariosParam[idOperario].libre = 0;
    operariosParam[idOperario].cocheActual = idCoche;
    sem_post(&garaje.mutex);
    sem_post(&garaje.queue);
    sem_post(&garaje.carReady);
    sem_wait(&garaje.semOpe[idOperario]);
}

void llegaCoche(int id){
    sem_wait(&garaje.queue);
    sem_wait(&garaje.mutex);
    garaje.numWaiting++;
    printf("Coche %d llega al garaje\n", id);
    sem_post(&garaje.mutex);
}

void initGaraje(){
    garaje.numWaiting = 0;
    sem_init(&garaje.mutex, 0, 1);
    sem_init(&garaje.full, 0, OPERARIOCOUNT);
    sem_init(&garaje.queue, 0, 1);
    sem_init(&garaje.carReady, 0, 0);

    for(int i = 0; i < OPERARIOCOUNT; i++){
        sem_init(&garaje.semOpe[i], 0, 0);
    }
}

void initParams(){
    initGaraje();
    for(int i = 0; i < CARCOUNT; i++){
        cochesParam[i].id = i;
    }

    for(int i = 0; i < OPERARIOCOUNT; i++){
        operariosParam[i].id = i;
        operariosParam[i].libre = 1;
        operariosParam[i].cocheActual = -1;
    }
}

void initThreads(){
    for(int i = 0; i < OPERARIOCOUNT; i++){
        pthread_create(&operarios[i], NULL, runOperario, (void*)&operariosParam[i]);
    }

    for(int i = 0; i < CARCOUNT; i++){
        pthread_create(&coches[i], NULL, runCoche, (void*)&cochesParam[i]);
    }
}

void waitThreads(){
    for(int i = 0; i < CARCOUNT; i++){
        pthread_join(coches[i], NULL);
        printf("termina coche %d\n", i);
    }

    end = 1;
    for(int i = 0; i < OPERARIOCOUNT; i++){
        pthread_join(operarios[i], NULL);
    }
}

int main(int argc, char** argv){
    initParams();
    initThreads();
    waitThreads();
}