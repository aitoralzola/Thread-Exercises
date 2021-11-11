import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Station {
	final int MAXCAPACITY = 6;
	int numWaiting;
	int numInTheBus;
	boolean isTraveling;
	Lock mutex;
	Condition passengersEnter;
	Condition passengersGetOut;
	Condition busOnDestination;
	Condition busOnStop;
	Condition busReady;
	
	public Station() {
		mutex = new ReentrantLock();
		passengersEnter = mutex.newCondition();
		passengersGetOut = mutex.newCondition();
		busOnDestination = mutex.newCondition();
		busOnStop = mutex.newCondition();
		busReady = mutex.newCondition();
		
		numWaiting = 0;
		numInTheBus = 0;
	}
	
	
	public void arriveStation(String name) {
		mutex.lock();

		System.out.println(name + " arrives to the station");
		numWaiting++;
		
		if(numWaiting == 20) {
			busOnStop.signal();
		}	
		mutex.unlock();
	}
	
	public void busStarts() throws InterruptedException {
		mutex.lock();
		if(numWaiting < 20) {
			busOnStop.await();
		}
		System.out.println("BUS ARRIVES, passengers can start getting in...");
		isTraveling = false;
		passengersEnter.signalAll();
		
		mutex.unlock();
	}


	public void getOnBus(String name) throws InterruptedException {
		
		mutex.lock();
		passengersEnter.await();
		while((numInTheBus >= MAXCAPACITY)){
			passengersEnter.await();
		}
		numWaiting--;
		numInTheBus++;
		System.out.println(name + " getting in the bus...");				
		if((numInTheBus == MAXCAPACITY) || (numWaiting == 0)){
			isTraveling = true;
			busReady.signalAll();
		}
		mutex.unlock();		
	}
	
	public void travel() throws InterruptedException{
		mutex.lock();

		while(!isTraveling) {
			busReady.await();
		}	

		System.out.println("BUS TRAVELLING...");
		busOnDestination.signalAll();
		busOnStop.await();
		System.out.println("The bus is empty, returning...");
		isTraveling=false;
		passengersEnter.signalAll();
		mutex.unlock();
	}


	public void getOutBus(String name) throws InterruptedException {
		mutex.lock();
		
		busOnDestination.await();

		System.out.println(name + " passenger getting out of the bus");
		numInTheBus--;
		if(numWaiting == 0) {
			System.out.println("Program FINALIZED");
		}
		if(numInTheBus == 0) {
			busOnStop.signal();
		}
		mutex.unlock();	
	}
}

