#include <stdio.h>
#include <pthread.h>
#include <semaphore.h>

#define NUM_PERSONAS 10
int numBath=0;

pthread_t manThread[NUM_PERSONAS], womanThread[NUM_PERSONAS];
int manID[NUM_PERSONAS], womanID[NUM_PERSONAS];
sem_t msem, wsem, bathSem; 


void delay(void)
{
    int i;
    int delaytime;
    delaytime = random();
    for (i = 0; i<delaytime; i++);
}

void* manBath(void* param){
    int* value = (int*) param;

    if(numBath==0){
        sem_wait(&wsem);
    }

    sem_wait(&bathSem);
        sem_wait(&msem);
        numBath++;
            printf("Hombre %d, entrando al baño, hay %d personas\n", *value, numBath);   
            sleep(1);
        sem_post(&msem);

        sem_wait(&msem);
            numBath--;
            printf("Hombre %d, saliendo del baño, hay %d personas\n", *value, numBath);  
        sem_post(&msem);
    sem_post(&bathSem);
    if(numBath==0){
        sem_post(&wsem);
    }
}

void* womanBath(void* param){
    int* value = (int*) param;
    if(numBath==0){
        sem_wait(&msem);
    }
        sem_wait(&bathSem);
        sem_wait(&wsem);
        numBath++;
            printf("Mujer %d, entrando al baño, hay %d personas\n", *value, numBath);   
            sleep(1);
        sem_post(&wsem);
        
        sem_wait(&wsem);
            numBath--;
            printf("Mujer %d, saliendo del baño, hay %d personas\n", *value, numBath);  
        sem_post(&wsem);
    sem_post(&bathSem);  
    if(numBath==0){
        sem_post(&msem);
    }
}

void initSems(){
  sem_init(&msem, 0, 1);
  sem_init(&wsem, 0, 1);
  sem_init(&bathSem, 0, 3);
}

void initThreads()
{
  for (int i = 0; i<NUM_PERSONAS; i++)
  {
    manID[i]=i;
    pthread_create(&manThread[i],NULL,manBath,(void *) &manID[i]);
  }
    for (int i = 0; i<NUM_PERSONAS; i++)
  {
    womanID[i]=i;
    pthread_create(&womanThread[i],NULL,womanBath,(void *)&womanID[i]);
  }

}

void waitThreads()
{
  for (int i = 0; i< NUM_PERSONAS; i++)
  {
    pthread_join(manThread[i],NULL);  
  }
    for (int i = 0; i< NUM_PERSONAS; i++)
  {
    pthread_join(womanThread[i],NULL);  
  }
}  

void semsDestroy(){
  sem_destroy(&msem);
  sem_destroy(&wsem);
  sem_destroy(&bathSem);
}
int main (int argc, char** argv)
{
  // initParams();
   initSems();
   initThreads();
   waitThreads();
   semsDestroy();
}
