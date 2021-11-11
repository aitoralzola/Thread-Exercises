
public class Woman extends Thread {
	int id;
	Toilet toilet;
	public Woman(int id, Toilet toilet) {
		this.id = id;
		this.toilet = toilet;
	}
	
	@Override
	public void run() {
			System.out.println("");
	}
}
