
public class Hydrogen extends Thread {

	int i;
	Tank tank;
	
	public Hydrogen(Tank tank, int i) {
		this.i = i;
		this.tank = tank;
	}

	@Override
	public void run() {
		try {
			tank.createHydrogen(i);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
