import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Checkout {
	
	private final int NUM_CASHIERS = 3;
	Buffer buffer;
	Lock mutex;
	Condition cashierOnCheckout;
	int contCashiers;
	Boolean allCashiersArrive = false;
	Condition customersWaiting;
	int freeCashiers;
	Condition cashierBeginAttending;
	Condition cashierEndAttending;
	Condition customerPaying;
	//boolean anyCashierIn = false;
	
	boolean cashierStart[];
	boolean cashierEnd[];
	boolean customerPay[];
	
	
	
	public Checkout() {
		
		buffer = new Buffer(NUM_CASHIERS);
		mutex = new ReentrantLock();
		
		cashierOnCheckout = mutex.newCondition();
		customersWaiting = mutex.newCondition();
		cashierBeginAttending = mutex.newCondition();	
		cashierEndAttending = mutex.newCondition();
		customerPaying = mutex.newCondition();
		
		cashierStart= new boolean[NUM_CASHIERS];
		for(int i=0; i<NUM_CASHIERS; i++) {
			cashierStart[i]=false;
		}
		cashierEnd= new boolean[NUM_CASHIERS];
		for(int i=0; i<NUM_CASHIERS; i++) {
			cashierEnd[i]=false;
		}
		customerPay= new boolean[NUM_CASHIERS];
		for(int i=0; i<NUM_CASHIERS; i++) {
			customerPay[i]=false;
		}
		
		contCashiers=0;
	}
	
	public void arrivesToCheckout(int i) throws InterruptedException {
		mutex.lock();
		
		contCashiers++;
		System.out.println("Cashier "+ i + " in checkout");
		if(contCashiers==NUM_CASHIERS){
			allCashiersArrive = true;
			customersWaiting.signalAll();
		}
		mutex.unlock();
	}

	public void customerToQueue(int i) throws InterruptedException{
		mutex.lock();

		while(!allCashiersArrive) {
			customersWaiting.await();
		}
		System.out.println("Customer "+i+ "  arrives to the queue...");
		mutex.unlock();
	}

	public int customerToCashier(int i) throws InterruptedException {
		
		int idCashier = buffer.get();
		mutex.lock();
		
		System.out.println("Cashier "+idCashier+ "  attending customer: " + i);
		cashierStart[idCashier] = true;
		cashierBeginAttending.signalAll(); 
		while(!cashierEnd[idCashier]) {
			cashierEndAttending.await();
		}
		System.out.println("Cashier "+ idCashier+ " end with customer "+i);
		
		System.out.println("Customer " +i+ " pays to cashier " + idCashier + " and GOES");
		customerPay[idCashier] = true;
		customerPaying.signalAll();
		mutex.unlock();
		return idCashier;
	}
	
	public void waitForClients(int idCashier) throws InterruptedException {
		buffer.put(idCashier);
		mutex.lock();
		while(!cashierStart[idCashier]) {
			cashierBeginAttending.await();
		}	
		cashierEnd[idCashier] = true;
		cashierEndAttending.signalAll();
		while(!customerPay[idCashier]) {
			customerPaying.await();
		}
		liberarCajera(idCashier);
		
		mutex.unlock();
	}

	private void liberarCajera(int idCashier) {
		cashierStart[idCashier]=false;
		cashierEnd[idCashier]=false;
		customerPay[idCashier]=false;
		
	}
	
}
