import java.util.concurrent.Semaphore;


public class Table {
	int numPhilosophers;
	Semaphore sticks[];
	
	public Table(int numPhilosophers) {
		this.numPhilosophers = numPhilosophers;
		sticks = new Semaphore [numPhilosophers];
		for (int i=0; i<numPhilosophers; i++) {
			sticks[i] = new Semaphore(1);
		}
	}
	
	public void getSticks (int numP) throws InterruptedException {
		sticks[Math.min(numP, (numP+1)%numPhilosophers)].acquire(); //left fork
		sticks[Math.max(numP,numP+1)%numPhilosophers].acquire(); //rigth fork
	}
	public void leaveSticks (int numP) {
		sticks[Math.min(numP, (numP+1)%numPhilosophers)].release(); //left fork
		sticks[Math.max(numP,numP+1)%numPhilosophers].release(); //right fork
	}
}
