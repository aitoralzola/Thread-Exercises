public class Main {
	final int NUMPASSENGERS = 20;
	Station dock;
	Passenger [] passengers;
	Bus bus;
	public Main () {
		dock = new Station();
		passengers = new Passenger [NUMPASSENGERS];
	}
	public void createThreads() {
		for (int i = 0; i<NUMPASSENGERS; i++) {
			passengers[i] = new Passenger ("passenger "+i, dock);
		}
		bus = new Bus("Boat ", dock);
	}
	public void initThreads() {

		for (int i = 0; i<NUMPASSENGERS; i++) {
			passengers[i].start();
		}
		bus.start();
		
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
		System.out.println("End");
	}

}
