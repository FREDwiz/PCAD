#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <semaphore.h>
#include <unistd.h>

/*
 * Esercizio 2 Asynchronous Functions with Pthreads
 *
 * Es. main invoca  async (eseguita in maniera asincrona su un thread separato) main effettua una serie di
 * elaborazioni qualsiasi (es. sleep di K secondi) main invoca get e si blocca ﬁno a che il risultato è pronto main
 * stampa il risultato.
 *
 * Scrivere un programma C con pthread, lock e variabili condition
 * per implementare “asynch” e “get” stampando alla ﬁne il valore del risultato dal main.
 *
 */

#define MAX_THREAD 5
#define MAX_ITER 50

int arrayG[MAX_THREAD];
pthread_mutex_t mutexes[MAX_THREAD];

/*
 * Consideriamo una funzione chiamata “asynch” con le seguenti caratteristiche:
 * - quando chiamata dal main viene eseguita su un thread separato.
 * - l’esecuzione effettua un task costoso in termini di tempo(es simulato anche solo con una “sleep” di alcuni secondi)
 * - la chiamata restituisce prima della terminazione un valore (es. un intero) al chiamante.
 */

void *asynch(void *vargp) {

    printf("[asynch] ***INSIDE***\n");
    int myid = *((int *) vargp);

    arrayG[myid] = 1;

    for (int i = 0; i < 10; i++) {
        sleep(5);

        pthread_mutex_lock(&mutexes[myid]);
        arrayG[myid] *= 2;
        pthread_mutex_unlock(&mutexes[myid]);

    }
    arrayG[myid] = myid * arrayG[myid];
    return &arrayG[myid];
}

/*
 * Consideriamo ora una funzione “get” con le seguenti caratteristiche:
 * - permette al main di poter recuperare il risultato di una determinata chiamata di asych in un qualsiasi passo della
 *   sua esecuzione.
 * - “get” deve essere bloccante per il main quando il risultato relativo alla chiamata di asynch non è ancora
 *   disponibile.
 */

int get(int myid) {

    printf("[get] ***INSIDE***\n");

    int status;
    pthread_mutex_lock(&mutexes[myid]);
    status = arrayG[myid];
    pthread_mutex_unlock(&mutexes[myid]);

    return status;
}

int main() {

    printf("Creating the threads...\n");

    pthread_t thd[MAX_THREAD];  // il thread su cui voglio lavorare
    pthread_t array[MAX_THREAD]; // l'array nel quale memorizzo

    printf("Calling asynch() ***START***\n");

    // Chiamata di asynch()
    for (int i = 0; i < MAX_THREAD; i++) {
        array[i] = i * 2;
        pthread_create(&thd[i], NULL, asynch, &array[i]);
    }

    printf("Calling asynch() ***END***\n");

    printf("Calling get() ***START***\n");

    // Chiamata di get()

    for (int i = 0; i < MAX_ITER; i++) {
        int th = rand() % MAX_THREAD;
        int stat = get(th);
        printf("Thread %d has status %d\n", th, stat);
        sleep(1);
    }

    printf("Calling get() ***END***\n");

    for (int i = 0; i < MAX_THREAD; i++) {
        printf("Detaching thread # %d\n", i);
        pthread_detach(thd[i]);
    }
    return 0;
}