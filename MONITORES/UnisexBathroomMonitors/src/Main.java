

public class Main {

	final static int NUM_MANS= 10;
	final static int NUM_WOMANS = 10;
	Toilet toilet;
	Man[] man;
	Woman[] woman;
	
	private void inicializar() throws InterruptedException {
		toilet = new Toilet();
		toilet.initBuffer();
		man = new Man[NUM_MANS];
		woman = new Woman[NUM_WOMANS];
	}
	
	public void createThreads() {
		for (int i = 0; i<NUM_MANS; i++) {
			man[i] = new Man(i, toilet);
		}
		for (int i = 0; i<NUM_WOMANS; i++) {
			woman[i] = new Woman(i, toilet);
		}
	}
	
	public void startThreads() {
		for (int i = 0; i<NUM_MANS; i++) {
			man[i].start();
		}
		for (int i = 0; i<NUM_WOMANS; i++) {
			woman[i].start();
		}
	}
	
	public void endThreads() throws InterruptedException {
			
		for (int i = 0; i<NUM_MANS; i++) {
			man[i].interrupt();
			man[i].join();
		}
		for (int i = 0; i<NUM_WOMANS; i++) {
			woman[i].interrupt();
			woman[i].join();
		}
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
