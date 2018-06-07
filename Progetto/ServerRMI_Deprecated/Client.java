import Extensions.*;
import java.rmi.*;
import java.rmi.server.*;

public class Client implements Runnable {
	
	static boolean DEBUG = false;
	String name;
	static int nCalls = 100;
	static int nThreads = 2;
	static int clientDelay = 10;
	static SingleBarrier barrier;
	static boolean KILL = false;
	
	public static void main(String[] argv) {
		
		if (System.getSecurityManager() == null)
			System.setSecurityManager(new RMISecurityManager());
		
		if (argv.length > 0)
			nCalls = Integer.parseInt(argv[0]);
		
		if (argv.length > 1)
			nThreads = Integer.parseInt(argv[1]);
		
		if (argv.length > 2)
			clientDelay = Integer.parseInt(argv[2]);
		
		if (System.getProperty("DEBUG") != null)
			DEBUG = true;
		
		if (System.getProperty("KILL") != null)
			KILL = true;
		System.out.println("Client(nCalls: " + nCalls + " nThreads: " + nThreads + " clientDelay: " + clientDelay + ")");
		
		barrier = new SingleBarrier(nThreads);
		
		for (int i=0; i<nThreads; i++) {
			Thread t = new Thread(new Client());
			t.start();
		}
		
		if (KILL)
			new Thread(new Killer(120)).start();
		
		barrier.barrierWait();
		System.exit(0);
	}
		
	public void run() {
	
		String selfName = Thread.currentThread().getName();
		try {
			System.out.println("Client[" + selfName + "]\tStarted new thread.");
			ServerOp ro = (ServerOp)Naming.lookup("Frobber");
			
			for (int i = 0; i < nCalls; i++) {
				String msg = "[Client " + selfName + "] Request: " + i;
				ClientImpl ci = new ClientImpl(msg);
				if (DEBUG) {
					System.out.println("Client[" + selfName + "] \tSent: '" + msg + "'");
				}
			
				String reply = ro.frob(ci);
					if (DEBUG) {
						System.out.println("Client[" +  selfName + "] \tGot:  '" + reply + "'");
					}
				
				InterruptibleThread.sleep(clientDelay);
			}
		} catch (Exception x) {
			x.printStackTrace();
			System.exit(1);
		}
		
		barrier.barrierPost();
	}
}