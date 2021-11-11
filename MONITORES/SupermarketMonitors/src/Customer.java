
public class Customer extends Thread {
	
	Checkout checkout;
	int i;

	public Customer(Checkout checkout, int i) {
		this.checkout=checkout;
		this.i = i;
	}
	
	@Override
	public void run() {
		try {
			checkout.customerToQueue(i);
			checkout.customerToCashier(i);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
