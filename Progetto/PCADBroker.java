/*  PCADBroker
*
*   Una classe  Java che implementa (attraverso RMI) un server multithreaded.
*   Il server mantiene in strutture dati concorrenti sia la lista di topic sia l'associazione tra topic e subscriber
*   della propria sottorete. Un broker può anche collegarsi come subscriber ad un'altro broker.
*   L'interfaccia del broker deve contenere metodi per permettere le seguenti operazioni:
*   -   connessione e disconnessione di un client
*   -   (annullamento della) sottoscrizione di un client su una topic
*   -   pubblicazione di una news su una topic con conseguente broadcast a tutti subscriber della topic
*       (la topic può essere aggiunta on-the-fly se non presente)
*
*/

class PCADBroker(){


        }