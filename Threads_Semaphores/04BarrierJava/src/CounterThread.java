public class CounterThread extends Thread {
	final static int LIMIT = 10;
	Barrier barrier;
	Barrier barrier2;
	
	public CounterThread(String name,Barrier barrier, Barrier barrier2){
		super(name);
		this.barrier = barrier;
		this.barrier2 = barrier2;
	}
	public void run() {
		for (int i = 0; i<LIMIT; i++){
			System.out.println(this.getName()+": "+i);
		}
		
		try {
			barrier.waitBarrier();
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}
		System.out.println(this.getName()+": goodbye");
	}

}
