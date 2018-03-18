#include <stdio.h>
#include <pthread.h>
#include <semaphore.h>

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

void asynch(){




    return 0;
}

void get(){
    return NULL;
}

int main(){

    pthread_t thd;

    pthread_create(&thd, NULL, asynch(),);

    get();





    return 0;
}