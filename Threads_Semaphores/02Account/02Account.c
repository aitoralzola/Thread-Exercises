/*
  using the pthread_join to wait the end of the threads
*/

#include <stdio.h>
#include <pthread.h>
#include <unistd.h>

#define QUANTITY 1
#define INIT_QUANTITY 1000
#define NUM_COUNTERS 100

typedef int ACCOUNT;
ACCOUNT cuenta = INIT_QUANTITY;

void deposit(ACCOUNT*account, int quantity);
void withdrawal(ACCOUNT*account, int quantity);
void* initSave(void*param);
void* initSpend(void*param);

typedef struct param{
  ACCOUNT* paccount;
  int quantity;
  int numTimes;
} TY_SPENDER_PARAM, *PTY_SPENDER_PARAM;

pthread_t counterThread [2*NUM_COUNTERS];
TY_SPENDER_PARAM paramThreads;


void* initSave (void* param)
{
   TY_SPENDER_PARAM values = *((TY_SPENDER_PARAM*) param);
   deposit(values.paccount, values.quantity);
   return NULL;
}
void* initSpend (void* param)
{
   TY_SPENDER_PARAM values = *((TY_SPENDER_PARAM*) param);
   withdrawal(values.paccount, values.quantity);
   return NULL;
}
void deposit(ACCOUNT*paccount, int quantity){

	for (int i = 0; i< NUM_COUNTERS; i++){
		
		*paccount=*paccount + quantity;
	}

}
void withdrawal(ACCOUNT*paccount, int quantity){

	for (int i = 0; i< NUM_COUNTERS; i++){
		
		*paccount=*paccount - quantity;
	}
	

}
void init_params(){
 
      paramThreads.paccount = &cuenta;
      paramThreads.quantity = QUANTITY;
      paramThreads.numTimes = NUM_COUNTERS;
  
}	
void init_threads()
{
  for (int i = 0; i< NUM_COUNTERS; i++)
  {
     pthread_create(&counterThread[i],NULL,initSave,(void*) &paramThreads);
  }
  for (int i = 10; i< 2*NUM_COUNTERS; i++)
  {
     pthread_create(&counterThread[i],NULL,initSpend,(void*) &paramThreads);
  }
}
void wait_threads()
{
  for (int i = 0; i< 20; i++)
  {
     pthread_join(counterThread[i],NULL);
  }
}
int main (int argc,char** argv)
{
	printf ("First value: %d\n",cuenta);
  init_params ();
  init_threads();
  wait_threads();
  
  printf ("Final value: %d\n",cuenta);
}
