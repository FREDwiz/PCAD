#include <stdio.h>
#include <pthread.h>
#include <semaphore.h>
#include <unistd.h>

/*
 * Esercizio 2 Asynchronous Functions with Pthreads
 *
 * Consideriamo una funzione chiamata “asynch” con le seguenti caratteristiche:
 * - quando chiamata dal main viene eseguita su un thread separato.
 * - l’esecuzione effettua un task costoso in termini di tempo(es simulato anche solo con una “sleep” di alcuni secondi)
 * - la chiamata restituisce prima della terminazione un valore (es. un intero) al chiamante.
 *
 * Consideriamo ora una funzione “get” con le seguenti caratteristiche:
 * - permette al main di poter recuperare il risultato di una determinata chiamata di asych in un qualsiasi passo della
 *   sua esecuzione.
 * - “get” deve essere bloccante per il main quando il risultato relativo alla chiamata di asynch non è ancora
 *   disponibile.
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

int arrayG[50];

void *asynch(void *vargp) {

    int *myid = (int *) vargp;

    // codice a caso tanto per fargli fare qualcosa
    int counter = 1;
    printf("DEBUG[THREAD]\n");

    for (int i = 0; i < 10; i++) {
        counter *= 2;
        sleep(1);
        //printf("Il valore counter vale: %d\n", counter);
    }
    arrayG[*myid] = (*myid) * counter;
    return &arrayG[*myid];
}

void get() {
    // robe future a cui penserò


}

int main() {

    printf("DEBUG[1]\n");

    pthread_t thd[20];  // il thread su cui voglio lavorare a parte
    pthread_t array[20]; // l'array nel quale memorizzo

    printf("DEBUG[2]\n");

    // Chiamata di asynch
    for (int i = 0; i < MAX_THREAD; i++) {
        array[i] = i * 2;
        pthread_create(&thd[i], NULL, asynch, &array[i]); // che faccio qui con l'array?
    }

    printf("DEBUG[3]\n");

    //get();

    for (int i = 0; i < MAX_THREAD; i++) {
        int *temp;
        pthread_join(thd[i], (void **)&temp);
        printf("temp: %d, i: %d\n", *temp, i);
    }

    pthread_detach(thd);

    return 0;
}