public class Snowhite extends Thread {
	final static int NUMPORTIONS = 5;
	Pot pot;
	
	public Snowhite (Pot pot) {
		this.pot = pot;
	}

	@Override
	public void run() {
		try {
			while (!this.isInterrupted()) {
				pot.putPortions(NUMPORTIONS);
			}
		} catch (InterruptedException e) {	}
	}
	
}
