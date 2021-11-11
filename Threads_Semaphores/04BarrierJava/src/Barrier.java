import java.util.concurrent.Semaphore;

public class Barrier {
	
	int counter;
	int numThreads;
	Semaphore semaphore;
	Semaphore mutex;
	
	public Barrier (int counter){
		this.counter = counter;
		this.numThreads = 0;
		this.semaphore = new Semaphore(0);
		this.mutex = new Semaphore(1);
	}
	
	
	public void waitBarrier() throws InterruptedException{
		mutex.acquire();
		if (++numThreads == counter) {
			semaphore.release(counter);
			numThreads=0;
		}
		mutex.release();
		semaphore.acquire();
		
	}
}
