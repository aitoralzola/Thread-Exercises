
public class Oxygen extends Thread {

	int i;
	Tank tank;

	public Oxygen(Tank tank, int i) {
		this.i = i;
		this.tank=tank;
	}

	
	@Override
	public void run() {
		try {
			tank.createOxygen(i);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
}
