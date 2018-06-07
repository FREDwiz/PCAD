/*
*   Pub/sub
*
*   Classe Java che implementa un client in grado di collegarsi ad un (solo) broker ed inviare richieste per pubblicare
*   notifiche su una topic, ascoltare (o smettere di ascoltare) notifiche.
*   L'interfaccia di un client Pub/sub deve contenere un metodo per permettere di effettuare le notifiche da parte
*   di un broker
*   Inoltre un client Pub/sub deve implementare (usando interfaccia PCADBroker) metodi per
*   -   inviare richiesta (dis)connect ad un broker
*   -   inviare richiesta (un)subscribe e publish su determinate topic
*/


import java.rmi.Naming;
import java.rmi.RemoteException;

public class PubSub
{
    public static void main(String args[]) {
        String server;
        String services[] = null;

        server = "rmi://localhost/";

        try {
            services = Naming.list(server);
        }
        catch (RemoteException ex) {
            ex.printStackTrace();
            System.exit(1);
        }
        catch (MalformedURLException mue) {
            mue.printStackTrace();
            System.exit(2);
        }

        for (int q = 0; q < services.length; q++) {
            System.out.println(services[q]);
        }
    }
}
        }