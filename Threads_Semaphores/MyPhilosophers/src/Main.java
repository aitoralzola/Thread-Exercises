public class Main {
	final int NUMPHILOSOPHERS = 5;
	Table table;
	Philosopher [] philosophers;
	
	public Main() {
		philosophers = new Philosopher[NUMPHILOSOPHERS];
		table = new Table(NUMPHILOSOPHERS);
	}
	
	public void createThreads() {
		for (int i = 0; i<NUMPHILOSOPHERS; i++) {
			philosophers[i] = new Philosopher(i,table);
		}
	}
	public void initThreads() {
		for (int i = 0; i<NUMPHILOSOPHERS; i++) {
			philosophers[i].start();
		}
	}
	public void waitEndOfThreads() {
		for (int i = 0; i<NUMPHILOSOPHERS; i++) {
			try {
				philosophers[i].join();
			} catch (InterruptedException e) {
			}
		}
	}
	public static void main(String[] args) {
		Main program = new Main();
		program.createThreads();
		program.initThreads();
		program.waitEndOfThreads();
	}

}
