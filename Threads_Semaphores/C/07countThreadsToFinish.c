#include <stdio.h>
#include <unistd.h>
#include <pthread.h>
#define N_THREADS 10
#define N_TIMES 10


typedef struct param{
  int id;
  int num;
} TY_PARAM;

int hasFinished=0;

void* contarNumeros(void*);
void initParams();
void initThreads();
	
TY_PARAM paramThreads[N_THREADS];
pthread_t hilo[N_THREADS];


void *contarNumeros(void *param){
	TY_PARAM values = *((TY_PARAM*) param);
	
		for(int i = 0; i<(paramThreads[i].num); i++)
		{
		printf("ID %d: %d\n",values.id,i);
		}
		hasFinished++;
   return NULL;
}

void initThreads()
{
	int i;
	while(hasFinished<=10 & i<=10){
	  for (i = 0; i< N_THREADS; i++)
	  {
		 pthread_create(&hilo[i],NULL,contarNumeros,(void*) &paramThreads[i]);
	  }
	}
}


void initParams(){
  for (int i = 0;i<N_THREADS; i++)
  {
      paramThreads[i].id = i;
      paramThreads[i].num = N_TIMES;
  }
}

int main(int argc, char** argv){

    printf("Creando hilos...n");
    initParams();
    initThreads();
    //waitThreads();
    printf("Todos los hilos han acabado\n");
    return 0;
}