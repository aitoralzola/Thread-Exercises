import java.util.Vector;
import java.util.concurrent.Semaphore;

public class Pot {

	final int MAX_CAPACITY = 10;

	Semaphore get = new Semaphore(0);
	Semaphore put = new Semaphore(MAX_CAPACITY);

	Cooker c;

	Vector<Missionary> buffer = new Vector<>();

	Object lock = new Object();

	volatile int count = 0;

	public Pot() {
	}


	public Missionary getMissionary() {
		Missionary m;
		try {
			get.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		synchronized (lock) {
			m = buffer.remove(0);
			count--;
		}

		if(count == 0) {
			put.release(MAX_CAPACITY);
		}
		return m;
	}

	public void putMissionary(Missionary m){
		try {
			put.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		synchronized (lock) {
			buffer.addElement(m);
			count++;
		}

		if(count == MAX_CAPACITY) {
			get.release(MAX_CAPACITY);
		}

	}
}

