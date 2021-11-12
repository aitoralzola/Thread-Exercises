import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Toilet {
	
	final static int NUM_BATHS=3;
	Lock mutex;
	Condition waitingQueue;
	//Condition womanWaiting;
	Condition queue;
	
	Buffer buffer;
	
	//boolean isFree;
	boolean anyManInside;
	boolean anyWomanInside;
	
	int numInside=0;
	
	public Toilet() {
		mutex = new ReentrantLock();
		waitingQueue = mutex.newCondition(); 
		//womanWaiting = mutex.newCondition(); 
		queue = mutex.newCondition(); 
		anyManInside = false;
		anyWomanInside = false;
		numInside=0;
		
		buffer = new Buffer(NUM_BATHS);	
	}
	
	public void initBuffer() throws InterruptedException {
		for(int i=0; i<NUM_BATHS; i++) {
			buffer.put(i);
		}
	}

	public void manToBath(int id) throws InterruptedException {
		int numBath=buffer.get();
		
		mutex.lock();
		while(anyWomanInside || numInside==NUM_BATHS) {
			waitingQueue.await();
		}
		anyManInside=true;
		numInside++;
		System.out.println("Man "+ id+" enters on bathroom "+ numBath);
		mutex.unlock();
	}
	
	public void womanToBath(int id) throws InterruptedException {
		int numBath=buffer.get();
		
		mutex.lock();
		while(anyManInside || numInside==NUM_BATHS) {
			waitingQueue.await();
		}
		anyWomanInside=true;
		numInside++;
		System.out.println("Woman "+ id+" enters on bathroom "+ numBath);
		mutex.unlock();
	}


	public void manLeavesBath(int id) throws InterruptedException {
		buffer.put(id);
		mutex.lock();
		numInside--;
		System.out.println("Man "+id+" leaves the bath");
		if(numInside==0) {
			anyManInside = false;
			waitingQueue.signalAll();
		}
		mutex.unlock();		
	}


	public void womanLeavesBath(int id) throws InterruptedException {
		buffer.put(id);
		
		mutex.lock();
		numInside--;
		System.out.println("Woman "+id+" leaves the bath");
		if(numInside==0) {
			anyWomanInside = false;
			waitingQueue.signalAll();
		}
		mutex.unlock();		
	}
	
}
