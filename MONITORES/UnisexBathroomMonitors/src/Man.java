
public class Man extends Thread {
	int id;
	Toilet toilet;
	public Man(int id, Toilet toilet) {
		this.id = id;
		this.toilet = toilet;
	}
	
	@Override
	public void run() {
			System.out.println("");
	}
	

}
