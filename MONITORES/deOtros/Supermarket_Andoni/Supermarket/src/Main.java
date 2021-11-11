
public class Main {
	final static int NUMCUSTOMER = 20;
	final static int NUMCASHIER = 3;
	Checkout check;
	Cashier [] cashiers;
	Customer [] customers;
	public Main () {
		check = new Checkout();
		cashiers = new Cashier [NUMCASHIER];
		customers = new Customer [NUMCUSTOMER];
	}
	public void createThreads() {
		for (int i = 0; i<NUMCASHIER; i++) {
			cashiers[i] = new Cashier (i, check);
		}
		for (int i = 0; i<NUMCUSTOMER; i++) {
			customers[i] = new Customer (i, check);
		}
	}
	public void initThreads() {
		for (int i = 0; i<NUMCASHIER; i++) {
			cashiers[i].start();
		}
		for (int i = 0; i<NUMCUSTOMER; i++) {
			customers[i].start();
		}
		
	}
	public void waitForEndOfThreads() {
		try {
			
			for (int i = 0; i<NUMCUSTOMER; i++) {
				customers[i].join();
			}
			for (int i = 0; i<NUMCASHIER; i++) {
				cashiers[i].interrupt();
			}
		} catch (InterruptedException e) {
			
		}
	}
	public static void main(String[] args) {
		Main program = new Main();
		program.createThreads();
		program.initThreads();
		program.waitForEndOfThreads();
	}

}

