import java.util.concurrent.Executors;

class DirectExecutor implements Executor {

	public void execute(Runnable r) {
		r.run();
	}
}
