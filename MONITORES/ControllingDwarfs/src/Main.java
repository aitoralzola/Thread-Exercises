

public class Main {

	final static int NUM_DWARFS = 7;
	final static int NUM_SEATS = 4;
	DinningRoom dr;
	Dwarf[] dwarfs;
	SnowWhite snowhite;
	
	private void inicializar() throws InterruptedException {
		dr = new DinningRoom(NUM_SEATS);
		dr.inicializarDr();
		dwarfs = new Dwarf[NUM_DWARFS];
	}
	
	public void createThreads() {
		 snowhite = new SnowWhite(dr);
		for (int i = 0; i<NUM_DWARFS; i++) {
			dwarfs[i] = new Dwarf(dr, i);
		}
	}
	
	public void startThreads() {
		snowhite.start();
		for (int i = 0; i<NUM_DWARFS; i++) {
			dwarfs[i].start();
		}
	}
	
	public void endThreads() throws InterruptedException {
			
		for (int i = 0; i<NUM_DWARFS; i++) {
			dwarfs[i].join();
		}
		snowhite.interrupt();
		snowhite.join();
	}
	
	public void exec() throws InterruptedException {
		inicializar();
		createThreads();
		startThreads();
		endThreads();
		System.out.println("Goodbye");
	}
	

	public static void main(String[] args) throws InterruptedException {
		Main p = new Main();
		p.exec();
	}

}
