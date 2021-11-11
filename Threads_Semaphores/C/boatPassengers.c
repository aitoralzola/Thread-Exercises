#include <stdio.h>
#include <pthread.h>
#include <semaphore.h>

#define NUM_PASSENGERS 10
#define MAX_PASSENGERS 4
int numWaiting=0;
int waitingPassengers[10];
pthread_t passengers[NUM_PASSENGERS], boat;
sem_t mutex, sem_subir, sem_bajar; 

void* waitBoat(void* param){
    sem_wait(&mutex);
    int passNum = (long int) param;
    printf("Passenger %d arrives the dock...\n", passNum);   
    waitingPassengers[numWaiting] = passNum;
    numWaiting++;
    sem_post(&mutex);
}

void* navigate(){
    sem_wait(&mutex);
    printf("CHU CHUUUU the boat has arrived\n");
    for(int i=0; i<MAX_PASSENGERS && i<numWaiting; i++)
    {
    printf("Passenger %d on board\n", waitingPassengers[i]);
    }
    printf("Boat crossing the river and passengers getting out...\n");
    sem_post(&mutex);   
}

void initSems(){
  sem_init(&mutex, 0, 1);
  sem_init(&sem_subir, 0, 0);
  sem_init(&sem_bajar, 0, 0);
}

void initThreads()
{
  for (int i = 0; i<NUM_PASSENGERS; i++)
  {
    pthread_create(&passengers[i],NULL,waitBoat,(void *)&i);
  }
    pthread_create(&boat,NULL,navigate,NULL);

}

void waitThreads()
{
  for (int i = 0; i< NUM_PASSENGERS; i++)
  {
    pthread_join(passengers[i],NULL);  
  }
  pthread_join(boat, NULL);
}  

void semsDestroy(){
  sem_destroy(&mutex);
  sem_destroy(&sem_subir);
  sem_destroy(&sem_bajar);
}
int main (int argc, char** argv)
{
  // initParams();
   initSems();
   initThreads();
   waitThreads();
   semsDestroy();
}
