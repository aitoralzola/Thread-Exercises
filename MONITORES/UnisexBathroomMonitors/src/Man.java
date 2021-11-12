
public class Man extends Thread {
	int id;
	Toilet toilet;
	public Man(int id, Toilet toilet) {
		this.id = id;
		this.toilet = toilet;
	}
	
	@Override
	public void run() {
		System.out.println("Man "+id+ " arrives to queue");
		try {
			toilet.manToBath(id);
			toilet.manLeavesBath(id);
			} catch (InterruptedException e) {
				System.out.println("Program interrupted");
			}
	}
}
