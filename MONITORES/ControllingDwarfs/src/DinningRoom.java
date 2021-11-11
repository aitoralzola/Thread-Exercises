import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DinningRoom {
	
	int ocuppySeats, numPortions;
	Lock mutex;
	
	boolean isPrepared[];
	Condition dwarfWaiting;
	Condition dwarfEating;
	Condition snowhiteWaiting;
	Buffer bufferSilla, bufferDemanda;

	
	public DinningRoom(int numSeats){
		mutex= new ReentrantLock();
		dwarfWaiting=mutex.newCondition();
		dwarfEating=mutex.newCondition();
		snowhiteWaiting=mutex.newCondition();
		
	}
	
	public void inicializarDr() throws InterruptedException {
		bufferSilla= new Buffer(Main.NUM_SEATS);
		for(int i=0; i< Main.NUM_SEATS; i++) {
			bufferSilla.put(i);
		}
		bufferDemanda= new Buffer(Main.NUM_SEATS);
		isPrepared = new boolean[Main.NUM_SEATS];
		for(int i=0; i< Main.NUM_SEATS; i++) {
			isPrepared[i]=false;
		}
	}

	
	public void serveDinner() throws InterruptedException {
		int demanda= bufferDemanda.get();
		mutex.lock();
		
		System.out.println("Snowhite is cooking for seat "+demanda);
		isPrepared[demanda]=true;
		dwarfEating.signalAll();
		mutex.unlock();		
	}
	
	public int takeSeat(int id) throws InterruptedException {
		int posicion=bufferSilla.get();
		System.out.println("Dwarf "+id+" sits on seat "+ posicion +" and asks for food");
		bufferDemanda.put(posicion);
		return posicion;
	}
	public void eat(int id, int silla) throws InterruptedException {
		mutex.lock();
		while(!isPrepared[silla]) {
			dwarfEating.await();
		}
		isPrepared[silla]=false;
		System.out.println("Dwarf "+ id +" eats on seat" + silla+ " and goes to bed.");
		mutex.unlock();
		bufferSilla.put(silla);
	}
}
