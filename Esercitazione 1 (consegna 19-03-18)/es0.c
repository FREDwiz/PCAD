#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <unistd.h>
#include <time.h>

#define MAX_THREAD 5

// The function to be executed by all threads
void *myThreadFun(void *vargp) {

    int *myid = (int *) vargp;

    for (int i = 0; i < 500; i++) {
        printf("(Thread ID: %d) i = %d\r\n", *myid, i);
        if (i % (1+rand()%200) == 0){
            printf("sleeping...\r\n");
            sleep(1);
        }
    }

    return NULL;
}

int main() {
    int i;
    srand(time(NULL));

    int arrayI[MAX_THREAD];
    pthread_t array[MAX_THREAD];

    for (int h = 0; h < MAX_THREAD; h++) {
        arrayI[h] = h;
    }

    for (i = 0; i < MAX_THREAD; i++) {
        pthread_create(&array[i], NULL, myThreadFun, (void *) &arrayI[i]);
    }
    for (int j = 0; j < MAX_THREAD; j++) {
        pthread_join(array[j], NULL);
    }

    pthread_exit(NULL);
    return 0;
}
