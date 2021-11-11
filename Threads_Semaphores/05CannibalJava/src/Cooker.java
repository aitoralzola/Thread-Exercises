
public class Cooker extends Thread{
	Pot pot;

	volatile boolean finalize = false;
	public Cooker(Pot pot) {
		this.pot = pot;
	}
	@Override
	public void run() {
		while(!finalize) {
			pot.putMissionary(new Missionary());
			System.out.println("The cooker puts a missionary");
		}

	}
	public void endTask() {
		this.finalize = true;
	}
}
