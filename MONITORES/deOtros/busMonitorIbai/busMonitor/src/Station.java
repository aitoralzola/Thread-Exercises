import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Station {
	final int MAXCAPACITY = 6;
	int numPassengersWaiting= 0;
	int numPassengersOnBoard;
	boolean busArrived=false, busTraveled=false;
	Lock mutex=new ReentrantLock();

	Condition arriveBus= mutex.newCondition();
	Condition startTravel= mutex.newCondition();
	Condition startNavy= mutex.newCondition();
	Condition waitToGetOut= mutex.newCondition();

	
	public void arriveStation(String name) throws InterruptedException {
		mutex.lock();
		System.out.println("Passenger "+name+" arrive station");
		numPassengersWaiting++;

		if(!busArrived){
			arriveBus.await();
		}

		mutex.unlock();

	}
	public void busStation(String name) throws InterruptedException {
		mutex.lock();
		busTraveled=false;
		System.out.println("Bus arrives the station");
		busArrived=true;
		arriveBus.signalAll();

		mutex.unlock();
	}
	public void boardBus(String name) throws InterruptedException {
		mutex.lock();


		while((numPassengersOnBoard)==MAXCAPACITY){
			startTravel.signal();
			arriveBus.await();

		}
		System.out.println("Passenger "+name+" onboard");
		numPassengersWaiting--;
		numPassengersOnBoard++;
		if(numPassengersWaiting==0){
			startTravel.signal();
		}

		mutex.unlock();
		
	}
	public void travel(String name) throws InterruptedException {
		mutex.lock();

		if(!busTraveled){
			startNavy.await();
		}

		System.out.println("Passenger "+name+" is gets out of the bus");
		numPassengersOnBoard--;
		if(numPassengersOnBoard==0){
			waitToGetOut.signal();
		}
		mutex.unlock();
	}

	public void navy(String name) throws InterruptedException {
		mutex.lock();
		startTravel.await();
		busArrived=false;
		System.out.println("Bus is traveling");
		busTraveled=true;
		startNavy.signalAll();
		if(numPassengersOnBoard>0){
			waitToGetOut.await();
		}
		System.out.println("Returns");
		mutex.unlock();
	}
}

