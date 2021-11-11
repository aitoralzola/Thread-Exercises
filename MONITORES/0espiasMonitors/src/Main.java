import java.util.concurrent.Semaphore;

public class Main {
	SpyA spyA;
	SpyB spyB;
	Semaphore semA,semB;
	public Main() {
		semA = new Semaphore(0);
		semB = new Semaphore(0);
		spyA = new SpyA(semA,semB);
		spyB = new SpyB(semA,semB);
	}
	public void startThreads() {
		spyA.start();
		spyB.start();
	}
	private void waitThreads() {
		try {
			spyA.join();
			spyB.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	public static void main(String[] args) {
		Main program = new Main();
		program.startThreads();
		program.waitThreads();
	}
	

}
