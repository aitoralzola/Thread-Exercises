public class Cannibal extends Thread {

	Pot pot;
	int id;
	volatile boolean finalize = false;

	public Cannibal(Pot pot, int id) {
		this.pot = pot;
		this.id = id;
	}

	@Override
	public void run() {
		while(!finalize) {
			pot.getMissionary();
			System.out.println("The cannibal "+getCannibalId()+ " gets a missionary");
		}

	}


	public int getCannibalId() {
		return id;
	}

	public void endTask() {
		this.finalize = false;
	}
}
