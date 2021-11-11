import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Checkout {
	Buffer buf;
	Lock mutex;
	int cashierLlegados;
	Condition cajeraBegin;
	boolean cajeraStart[];
	Condition cajeraEnd;
	boolean cajeraAcaba[];
	Condition waitPago;
	boolean pagar[];
	Condition startDay;
	boolean dayStarted;
	public Checkout() {
		mutex= new ReentrantLock();
		cajeraBegin=mutex.newCondition();
		cajeraEnd=mutex.newCondition();
		waitPago=mutex.newCondition();
		startDay=mutex.newCondition();
		dayStarted=false;
		cashierLlegados=0;
		buf= new Buffer(Main.NUMCASHIER);
		cajeraStart= new boolean[Main.NUMCASHIER];
		for(int i=0; i<Main.NUMCASHIER; i++) {
			cajeraStart[i]=false;
		}
		cajeraAcaba= new boolean[Main.NUMCASHIER];
		for(int i=0; i<Main.NUMCASHIER; i++) {
			cajeraAcaba[i]=false;
		}
		pagar= new boolean[Main.NUMCASHIER];
		for(int i=0; i<Main.NUMCASHIER; i++) {
			pagar[i]=false;
		}
		
	}
	public void customerBuy(int name) throws InterruptedException{
		mutex.lock();
		if(dayStarted==false) {
			startDay.await();		
		}
		System.out.println("Customer "+name+" goes to queue");
		mutex.unlock();
		int numCash=buf.get();
		mutex.lock();
		cajeraStart[numCash]=true;
		cajeraBegin.signalAll();
		System.out.println("Cashier "+numCash+" serving Customer "+ name);
		while(!cajeraAcaba[numCash]) {
			cajeraEnd.await();
		}
		pagar[numCash]=true;
		waitPago.signalAll();
		System.out.println("Customer "+name+" pays and goes");
		mutex.unlock();
		
	}
	public void empezarDia() throws InterruptedException{
		mutex.lock();
		cashierLlegados++;
		if(cashierLlegados==Main.NUMCASHIER) {
			dayStarted=true;
			startDay.signalAll();
		}else {
			startDay.await();
		}
		mutex.unlock();
	}
	public void cashierAttend(int name) throws InterruptedException{
		

		buf.put(name);
		mutex.lock();
		while(!cajeraStart[name]) {
			cajeraBegin.await();
		}
		cajeraAcaba[name]=true;
		cajeraEnd.signalAll();
		while(!pagar[name]) {
			waitPago.await();
		}
		cajeraStart[name]=false;
		cajeraAcaba[name]=false;
		pagar[name]=false;
		mutex.unlock();
	}

}
