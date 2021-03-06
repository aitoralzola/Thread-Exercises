
public class Cashier extends Thread {
	Checkout checkout;
	int i;
	
	public Cashier(Checkout checkout, int i) {
		this.checkout=checkout;
		this.i = i;
	}

	@Override
	public void run() {
		try {
			checkout.arrivesToCheckout(i);
			while (!isInterrupted()) {
				checkout.waitForClients(i);
			}
		} catch (InterruptedException e) {
			System.out.println("Program interrupted");
		}
	}
}
