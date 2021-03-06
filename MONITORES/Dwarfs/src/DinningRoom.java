import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DinningRoom {
	
	int ocuppySeats, numPortions;
	Lock mutex;
	
	int demands;
	Condition dwarfWaiting;
	Condition dwarfEating;
	Condition snowhiteWaiting;


	
	public DinningRoom(int numSeats) {
		mutex= new ReentrantLock();
		dwarfWaiting=mutex.newCondition();
		dwarfEating=mutex.newCondition();
		snowhiteWaiting=mutex.newCondition();
		demands = 0;
		
		numPortions=0;
		ocuppySeats=0;
	}

	
	public void serveDinner() throws InterruptedException {
		mutex.lock();
		while(demands==0) {
			snowhiteWaiting.await();
		}
		demands--;
		System.out.println("Snowhite is cooking");
		numPortions++;
		dwarfEating.signal();
		mutex.unlock();		
	}
	
	public void takeSeat(int id) throws InterruptedException {
		mutex.lock();
		while(ocuppySeats >= Main.NUM_SEATS){
			dwarfWaiting.await();
		}
		demands++;
		ocuppySeats++;
		System.out.println("Dwarf "+id+" sits and waits for the food");
		snowhiteWaiting.signal();
		mutex.unlock();
	}
	public void eat(int id) throws InterruptedException {
		mutex.lock();
		while(numPortions <= 0) {
			dwarfEating.await();
		}
		numPortions--;
		ocuppySeats--;
		System.out.println("Dwarf "+ id +" eats and goes to bed.");
		mutex.unlock();
	}
}
