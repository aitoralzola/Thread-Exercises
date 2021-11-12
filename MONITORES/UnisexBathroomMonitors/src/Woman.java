
public class Woman extends Thread {
	int id;
	Toilet toilet;
	public Woman(int id, Toilet toilet) {
		this.id = id;
		this.toilet = toilet;
	}
	
	@Override
	public void run() {
		System.out.println("Woman "+id+ " arrives to queue");
		try {
			toilet.womanToBath(id);
			toilet.womanLeavesBath(id);
			} catch (InterruptedException e) {
				System.out.println("Program interrupted");
			}
	}
}
