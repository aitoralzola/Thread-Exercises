#include <stdio.h>
#include <stdlib.h> //para el random
#include <unistd.h>
#include <pthread.h>
#include <semaphore.h>

#define NUM_SMOKERS 3
#define NTIMES 20

pthread_t agent, tobaccoSmoker, fireSmoker, paperSmoker;
sem_t tobaccoSem, paperSem, fireSem, tableEmptySem; 


void delay(void)
{
    int i;
    int delaytime;
    delaytime = random();
    for (i = 0; i<delaytime; i++);
}

void* generateIngred(){
  while(1){
    sem_wait(&tableEmptySem);
    int random_number = rand() % 3 + 1;
    printf("Random: %d\n", random_number);
    if(random_number==1){
      printf("Tobacco and Paper on the table\n");
      sem_post(&fireSem);
    }
    if(random_number==2){
      printf("Tobacco and Fire on the table\n");
      sem_post(&paperSem);
    }
    if(random_number==3){
      printf("Paper and Fire on the table\n");
      sem_post(&tobaccoSem);
    }
  }
}

void* smoker_w_tobacco(){
    printf("Tobacco smoker created...\n");
    while(1){
    sem_wait(&tobaccoSem);
    printf("Tobacco smoker takes ingredients, ready to smoke...\n");
    sem_post(&tableEmptySem);
    printf("Tobacco smoker, smoking for a while...\n");
    sleep(1);  
    }
}

void* smoker_w_fire(){
    printf("Fire smoker created...\n");
    while(1){
    sem_wait(&fireSem);
    printf("Fire smoker takes ingredients, ready to smoke...\n");
    sem_post(&tableEmptySem);
    printf("Fire smoker, smoking for a while...\n");
    sleep(1); 
    }      
}

void* smoker_w_paper(){
    printf("Paper smoker created...\n");
    while(1){
      sem_wait(&paperSem);
      printf("Paper smoker takes ingredients, ready to smoke...\n");
      sem_post(&tableEmptySem);
      printf("Paper smoker, smoking for a while...\n");
      sleep(1); 
    }

}

void initSems(){
  sem_init(&tobaccoSem, 0, 0);
  sem_init(&paperSem, 0, 0);
  sem_init(&fireSem, 0, 0);
  sem_init(&tableEmptySem, 0, 1);
}

void initThreads()
{
    pthread_create(&tobaccoSmoker,NULL,smoker_w_tobacco,NULL);
    pthread_create(&fireSmoker,NULL,smoker_w_fire,NULL);
    pthread_create(&paperSmoker,NULL,smoker_w_paper,NULL);
    pthread_create(&agent,NULL,generateIngred,NULL);
}

void waitThreads()
{
  pthread_join(tobaccoSmoker,NULL);  
  pthread_join(fireSmoker,NULL);  
  pthread_join(paperSmoker,NULL);   
}  

void semsDestroy(){
  sem_destroy(&tobaccoSem);
  sem_destroy(&paperSem);
  sem_destroy(&fireSem);
  sem_destroy(&tableEmptySem);

}
int main (int argc, char** argv)
{
  // initParams();
   initSems();
   initThreads();
   waitThreads();
   semsDestroy();
}
