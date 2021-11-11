
public class Cashier extends Thread{
	Checkout check;
	int num;
	public Cashier(int num, Checkout check) {
		this.num=num;
		this.check=check;
	}
	@Override
	public void run() {
		try {
			System.out.println("Cashier "+num+ " in checkout");
			check.empezarDia();
			while(!this.isInterrupted()) {
				check.cashierAttend(num);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
