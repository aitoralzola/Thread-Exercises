#include <stdio.h>
#include <pthread.h>
#define CAPACITY 1000

typedef int ACCOUNT;
ACCOUNT cuenta = INIT_QUANTITY;

typedef struct param
{ 
    ACCOUNT* paccount;
    int quantity;
    int numTimes;
}TY_SPENDER_PARAM, *PTY_SPENDER_PARAM;

typedef struct buffer{
	int idxIn;
	int idxOut;
	int numValues;
	int buffer[CAPACITY];
	sem_t sem_full, sem_empty, sem_mutex;
	}TY_BUFFER, *PTY_BUFFER;
	
//BUFFER
void buffer_init{PTY_BUFFER pBuffer};
void buffer_put{PTY_BUFFER pBuffer, int value};
void buffer_get{PTY_BUFFER pBuffer};
void buffer_destroy{PTY_BUFFER pBuffer};
void buffer_empty{PTY_BUFFER pBuffer};

void buffer_init{PTY_BUFFER pBuffer}{
    
}

void buffer_put{PTY_BUFFER pBuffer, int value}{
    sem_wait(&pBuffer.sem_full);
    sem_wait(&pBuffer.sem_mutex);
    pBuffer->buffer[pBuffer->idxIn]=value;
    pBuffer->idxIn=(pBuffer->idxIn+1)%CAPACITY;
    pBuffer->numValues++;
    sem_post(&pBuffer->sem_mutex);
    sem_post(&pBuffer->sem_empty);
}

void buffer_get{PTY_BUFFER pBuffer, int value}{
    sem_wait(&pBuffer.sem_empty);
    sem_wait(&pBuffer.sem_mutex);
    pBuffer->buffer[pBuffer->idxOut]=value;
    pBuffer->idxOut=(pBuffer->idxOut+1)%CAPACITY;
    pBuffer->numValues--;
    sem_post(&pBuffer->sem_mutex);
    sem_post(&pBuffer->sem_full);
}

//MAIN
void deposit (ACCOUNT* account, int quantity)
{
  sem_wait(&mutex);
  *account += quantity;
  sem_post(&mutex);
}
void withdrawal (ACCOUNT* account, int quantity)
{
  sem_wait(&mutex);
  *account -= quantity;
  sem_post(&mutex);
}

void initThreads()
{
  for (int i = 0; i<MAX_ACTORS; i++)
  {
    pthread_create (&savers[i],NULL,initSave,(void *) &saverParams);
    pthread_create (&spenders[i],NULL,initSpend,(void *) &spenderParams);
  }
}

void waitThreads ()
{
  for (int i = 0; i< MAX_ACTORS; i++)
  {
    pthread_join(savers[i],NULL);
    pthread_join(spenders[i], NULL);
  }
}  

void initParams()
{
  saverParams.paccount = &account;
  saverParams.quantity = QUANTITY;
  saverParams.numTimes = MAX_ACTIONS;
  
  spenderParams.paccount = &account;
  spenderParams.quantity = QUANTITY;
  spenderParams.numTimes = MAX_ACTIONS;
}

int main (int argc, char** argv)
{
   sem_init(&sem_mutex,0,1);
   printf ("Initial balance: %d \n", account);
   initParams();
   initThreads();
   waitThreads();
   printf ("Final balance: %d \n", account);
   sem_destroy(&sem_mutex);
}