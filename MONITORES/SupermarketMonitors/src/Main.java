import java.util.ArrayList;
import java.util.List;

public class Main {

	final int NUM_CUSTOMERS = 20;
	final int NUM_CASHIERS = 3;
	Checkout checkout = new Checkout();
    private List<Customer> customerList = new ArrayList<>();
    private List<Cashier> cashierList = new ArrayList<>();
	
	public void createThreads() {
        for (int i = 0; i < NUM_CUSTOMERS; i++) {
        	customerList.add(new Customer(checkout, i));
        }
        for (int i = 0; i < NUM_CASHIERS; i++) {
        	cashierList.add(new Cashier(checkout, i));
        }
	}
	
	public void startThreads() {
        for (Customer customer : customerList) {
            customer.start();
        }
        for (Cashier cashier : cashierList) {
            cashier.start();
        }
	}
	
	public void endThreads() throws InterruptedException {
        for (Customer customer: customerList) {
        	customer.join();
        }
       
        for (Cashier cashier : cashierList) {
        	cashier.interrupt();
        	cashier.join();
        }
	}
	
	public void exec() throws InterruptedException {
		createThreads();
		startThreads();
		endThreads();
		System.out.println("Goodbye");
	}
	
	public static void main(String[] args) throws InterruptedException {
		Main main = new Main();
		main.exec();
	}

}
