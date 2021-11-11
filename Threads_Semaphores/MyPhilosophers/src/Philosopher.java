import java.util.concurrent.Semaphore;

public class Philosopher extends Thread{
	final int MAXTIMES = 25;
	Table table;
	int id;

	public Philosopher (int id,Table table) {
		this.id = id;
		this.table = table;
	}

	@Override
	public void run() {
		try {
			for (int i = 0; i<MAXTIMES;i++) {
				int j=i+1;
				System.out.println("Philosopher " +id+ " is thinking... ");
				table.getSticks(id);
				System.out.println("Philosopher " +id+ " is eating, for the "+j+" time. ");
				Thread.sleep(10);
				table.leaveSticks(id);
			}
		} catch (InterruptedException e) {
		
		}
	}
	
}
