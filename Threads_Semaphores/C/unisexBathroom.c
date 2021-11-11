#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <semaphore.h>

int mcount,wcount;
sem_t x,y,z,wsem,msem,cap;

void delay(void)
{
    int i;
    int delaytime;
    delaytime = random();
    for (i = 0; i<delaytime; i++);
}

void *woman(void *param)
{
    sem_wait(&z);
        sem_wait(&wsem);
            sem_wait(&y);
                wcount++;
                if(wcount==1)
                { sem_wait(&msem); }
            sem_post(&y);
        sem_post(&wsem);
    sem_post(&z);

    sem_wait(&cap);

    printf("woman in!\n");
    delay();
    printf("\twoman out!\n");

    sem_post(&cap);     

    sem_wait(&y);
        wcount--;
        if(wcount==0)
        { sem_post(&msem); }
    sem_post(&y);
}

void *man(void *param)
{           
    sem_wait(&z);
        sem_wait(&msem);
            sem_wait(&x);
                mcount++;
                if(mcount==1)
                { sem_wait(&wsem); }
            sem_post(&x);
        sem_post(&msem);
    sem_post(&z);

    sem_wait(&cap);

    printf("\t\tman in!\n");
    delay();
    printf("\t\t\tman out!\n");

    sem_post(&cap);

    sem_wait(&x);
        mcount--;
        if(mcount==0)
        {sem_post(&wsem);}
    sem_post(&x);
}

int main(void)
{
    int i;
    srandom(60);

        mcount = 0;
        wcount = 0;
        sem_init(&x,0,1);  // for sem_init, initial value is 3rd argument
        sem_init(&y,0,1);
        sem_init(&z,0,1);
        sem_init(&wsem,0,1);
        sem_init(&msem,0,1);
        sem_init(&cap,0,4);  // eg. cap initialized to 4

        pthread_t *tid;
        tid = malloc(80*sizeof(pthread_t));

    // You can use your cobegin statement here, instead of pthread_create()     
    // I have forgone the use of pthread barriers although I suppose they would nicely imitate the functionality of cobegin. 
    // This is merely to retain simplicity.

    for(i=0;i<10;i++)
    {
        pthread_create(&tid[i],NULL,woman,NULL);
    }
    for(i=10;i<20;i++)
    {     
            pthread_create(&tid[i],NULL,man,NULL);
    }
    for(i=0;i<20;i++)
    {     
            pthread_join(tid[i],NULL);
    }
    return(0);
}