Labo 1: Concorrenza in Java

Tutorial Java 
	https://docs.oracle.com/javase/tutorial/essential/

Tutorial Java Concorrente
	http://docs.oracle.com/javase/tutorial/essential/concurrency/

Esercizio 1
	
	Scrivere una libreria thread-safe  con monitor Java per simulare la gestione di un parcheggio a pagamento con un solo ingresso P.

	-	Ogni auto corrisponde ad un thread che utilizza le procedure del monitor per entrare ed uscire dal parcheggio un certo numero di volte fissato a priori. 

	-	Quando il parcheggio è pieno le auto in arrivo devono rimanere in attesa alla sbarra. 

	-	Quando si libera un posto nel parcheggio l'auto che esce deve segnalare il posto  libero ai processi in coda.
	
	-	Il parcheggio va implementato attraverso una classe (protetta da un monitor Java) con un contatore dei posti disponibili (fino ad un numero massimo MAXP) e 	le funzioni per entrare (enter) ed uscire dal parcheggio (exit).

	-	La funzione per entrare nel parcheggio deve essere bloccante se non si sono posti disponibili. 
	
	-	Prevedere anche una funzione per chiudere il parcheggio e forzare l'uscita di tutte le auto.

	-	Le auto devono essere rappresentate da thread concorrenti.
	
	-	Ogni auto utilizza in sequenza i metodi enter; exit del parcheggio condiviso.