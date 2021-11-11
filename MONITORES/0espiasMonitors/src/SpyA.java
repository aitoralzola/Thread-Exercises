import java.util.concurrent.Semaphore;

public class SpyA extends Thread {
	final int NUMTIMES = 10;
	Semaphore semA,semB;
	Controller controller;
	
	public SpyA(Controller controller) {
		this.controller = controller;
	}
	@Override
	public void run() {
		try {
			controller.askPermisionB();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		for (int i = 0; i<NUMTIMES;i++) {
			System.out.println("SpyA: Spying");
		}
		try {
			controller.finishB();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			controller.askPermisionB();
			controller.finishA();
			System.out.println("SpyA: pushing bottom");
			
			System.out.println("SpyA: crossing door");
			controller.askPermisionB();
			controller.finishA();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		
		for (int i = 0; i<NUMTIMES;i++) {
			System.out.println("SpyA: Spying");
		}
	}

}
