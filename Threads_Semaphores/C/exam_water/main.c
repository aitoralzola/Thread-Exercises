#include <stdio.h>
#include <unistd.h> //para el sleep
#include <pthread.h>
#include <semaphore.h>

#define NUM_HYDROGEN 20
#define NUM_OXYGEN 10

 int hydro_count = 0;
 int oxygen_count = 0;
 int end = 0;

pthread_t tHydrogen[NUM_HYDROGEN], tOxygen[NUM_OXYGEN];
sem_t hydrogenSem, oxygenSem, mutex, tankSem; 
int hydroID[NUM_HYDROGEN], oxyID[NUM_OXYGEN];

void* runHydrogen(void* param){
    sem_wait(&hydrogenSem);
    sem_wait(&tankSem);
    int* value = (int*) param;
    sem_wait(&mutex);
    hydro_count++;
    printf("Hydrogen %d reacts to create water\n", *value);
    sleep(1);
    sem_post(&mutex);
    
    if(hydro_count==2 && oxygen_count==1){
        printf("******************************************\n");
        
        sem_wait(&mutex);
        hydro_count=0;
        oxygen_count=0;
        sem_post(&mutex);

        sem_post(&tankSem);

        sem_post(&hydrogenSem);
        sem_post(&hydrogenSem);
        sem_post(&oxygenSem);       
    }else{
        sem_post(&oxygenSem);
        sem_post(&tankSem);
    }
}

void* runOxygen(void* param){
    sem_wait(&oxygenSem);
    sem_wait(&tankSem);
    int* value = (int*) param;
    sem_wait(&mutex);
    oxygen_count++;
    printf("Oxygen %d reacts to create water\n", *value);
    sleep(1);
    sem_post(&mutex);

    if(hydro_count==2 && oxygen_count==1){
        printf("******************************************\n");
        
        sem_wait(&mutex);
        hydro_count=0;
        oxygen_count=0;
        sem_post(&mutex);

        sem_post(&tankSem);
        sem_post(&hydrogenSem);
        sem_post(&hydrogenSem);
        sem_post(&oxygenSem);    
    } 
    else{
        sem_post(&hydrogenSem);
        sem_post(&tankSem);
    }
}

void initSems(){
  sem_init(&hydrogenSem, 0, 2);
  sem_init(&oxygenSem, 0, 1);
  sem_init(&mutex, 0, 1);
  sem_init(&tankSem, 0, 1);
}

void initThreads()
{
for (int i = 0; i<NUM_OXYGEN; i++)
    {
        oxyID[i]=i;
        pthread_create(&tOxygen[i],NULL,runOxygen,(void *) &oxyID[i]);
    }
for (int i = 0; i<NUM_HYDROGEN; i++)
    {
        hydroID[i]=i;
        pthread_create(&tHydrogen[i],NULL,runHydrogen,(void *) &hydroID[i]);
    }
}

void waitThreads()
{

    for (int i = 0; i< NUM_HYDROGEN; i++)
        {
            pthread_join(tHydrogen[i],NULL);  
        }
    for (int i = 0; i< NUM_OXYGEN; i++)
        {
            pthread_join(tOxygen[i],NULL);  
        }
  end=1;
}  

void semsDestroy(){
  sem_destroy(&hydrogenSem);
  sem_destroy(&oxygenSem);
  sem_destroy(&mutex);
  sem_destroy(&tankSem);

}
int main (int argc, char** argv)
{
   initSems();
   initThreads();
   waitThreads();
   semsDestroy();
   printf("Goodbye!\n");
}
