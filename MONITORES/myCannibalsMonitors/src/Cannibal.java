
public class Cannibal extends Thread {

	Pot pot;
	
	public Cannibal (Pot pot,String name) {
		super(name);
		this.pot = pot;
	}

	@Override
	public void run() {
		try {
			while (!this.isInterrupted()){
				pot.getPortion(getName());
				System.out.println(this.getName()+ " eating");
			}
		} catch (InterruptedException e) {
			
		}
	}
	
}
