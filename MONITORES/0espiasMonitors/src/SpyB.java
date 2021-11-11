import java.util.concurrent.Semaphore;

public class SpyB extends Thread {
	final int NUMTIMES = 6;
	Controller controller;
	
	public SpyB(Controller controller) {
		this.controller = controller;
	}
	@Override
	public void run() {
		try {
			controller.askPermisionA();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		for (int i = 0; i<NUMTIMES;i++) {
			System.out.println("SpyB: Spying");
		}
		try {
			controller.finishA();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			controller.askPermisionA();
			controller.finishB();
			System.out.println("SpyB: pushing bottom");
			
			System.out.println("SpyB: crossing door");
			controller.askPermisionA();
			controller.finishB();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		
		for (int i = 0; i<NUMTIMES;i++) {
			System.out.println("SpyB: Spying");
		}
	}

}
