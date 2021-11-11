import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

	
public class Tank {
	
		final int HYDRO_TIMES = 2;
		final int OXY_TIMES = 1;
		boolean isCreated;
		int hydroAtomNum, oxyAtomNum;
		Lock mutex;
		Condition hydrogenQueue;
		Condition oxygenQueue;
		
		public Tank() {
			mutex = new ReentrantLock();
			hydrogenQueue = mutex.newCondition();
			oxygenQueue = mutex.newCondition();		
			hydroAtomNum = 0;
			oxyAtomNum = 0;
		}

		public void createHydrogen(int i) throws InterruptedException {
			mutex.lock();
			while(hydroAtomNum>1) {
				hydrogenQueue.await();
			}
			hydroAtomNum++;
			System.out.println("Hydrogen " + i + " reacts to create water");		
			
			isLastReaction();
			mutex.unlock();	
		}


		public void createOxygen(int i) throws InterruptedException {
			mutex.lock();
			while(oxyAtomNum>0) {
				oxygenQueue.await();
			}
			oxyAtomNum++;
			System.out.println("Oxygen " + i + " reacts to create water");
			
			if(hydroAtomNum==HYDRO_TIMES && oxyAtomNum == OXY_TIMES) {
				isCreated();
				freeElements();			
			}
				
			mutex.unlock();
		}
		
		private void isLastReaction() {
			if(hydroAtomNum==HYDRO_TIMES && oxyAtomNum==OXY_TIMES) {
				isCreated();
				freeElements();			
			}	
		}

		private void freeElements() {
				hydroAtomNum= 0;
				oxyAtomNum = 0;
				hydrogenQueue.signalAll();
				oxygenQueue.signal();
		}
		
		public void isCreated() {
			System.out.println("---------------------");
		}

}
