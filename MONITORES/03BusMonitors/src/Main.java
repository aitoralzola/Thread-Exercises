public class Main {
	final int NUMPASSENGERS = 20;
	Station station;
	Passenger [] passengers;
	Bus bus;
	public Main () {
		station = new Station();
		passengers = new Passenger [NUMPASSENGERS];
	}
	public void createThreads() {
		for (int i = 0; i<NUMPASSENGERS; i++) {
			passengers[i] = new Passenger ("passenger "+i, station);
		}
		bus = new Bus("Bus ", station);
	}
	public void initThreads() {
		bus.start();
		for (int i = 0; i<NUMPASSENGERS; i++) {
			passengers[i].start();
		}
		
	}
	public void waitForEndOfThreads() {
		try {
			for (int i = 0; i<NUMPASSENGERS; i++) {
				passengers[i].join();
			}
			bus.interrupt();
			bus.join();
		} catch (InterruptedException e) {
			
		}
	}
	public static void main(String[] args) {
		Main program = new Main();
		program.createThreads();
		program.initThreads();
		program.waitForEndOfThreads();
	}

}
