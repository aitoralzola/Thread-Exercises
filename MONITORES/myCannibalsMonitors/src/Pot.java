import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.management.monitor.Monitor;

public class Pot {
	
	int portions;
	Lock mutex;
	Condition waitingCannibals;
	Condition waitingCook;
	
	public Pot() {
		portions = 0;
		mutex = new ReentrantLock();
		waitingCannibals = mutex.newCondition();
		waitingCook = mutex.newCondition();
	}
	
	public void  getPortion (String name) throws InterruptedException {
		
		mutex.lock();
		while(portions == 0) {
			waitingCook.signal();
			waitingCannibals.await();
		}
		portions--;
		System.out.println(name + " takes a portion");
		mutex.unlock();
	}
	public void putPortions(int num) throws InterruptedException {
		
		mutex.lock();
		if(portions>0) {
			waitingCook.await();
		}
		System.out.println("cook cooks new portions");
		portions=num;
		waitingCannibals.signalAll();
		mutex.unlock();		
	}
}
