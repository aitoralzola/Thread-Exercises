
public class Customer extends Thread{
	Checkout check;
	int num;
	public Customer(int num, Checkout check) {
		this.num=num;
		this.check=check;
	}
	@Override
	public void run() {
		try {
			check.customerBuy(num);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
