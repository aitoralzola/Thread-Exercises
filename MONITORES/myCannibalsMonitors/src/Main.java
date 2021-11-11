import java.util.Scanner;

public class Main {
	final static int NUMCANNIBALS = 20;
	
	Cook cook;
	Pot pot;
	Cannibal savages[];

	public Main() {
		pot = new Pot();
		cook = new Cook(pot);
		savages = new Cannibal[NUMCANNIBALS];
		for (int i= 0;i<NUMCANNIBALS;i++) {
			savages[i] = new Cannibal(pot,"cannibal "+i);
		}
	}
	public void startThreads() {
		cook.start();
		for (Cannibal cannibal : savages) {
			cannibal.start();
		}
	}
	public void stopThreads() throws InterruptedException {
		cook.interrupt();
		for (Cannibal cannibal : savages) {
			cannibal.interrupt();
		}
		cook.join();
		for (Cannibal cannibal : savages) {
			cannibal.join();
		}
	}
	public static void main(String[] args) {
		Scanner keyboard = new Scanner(System.in);
		Main program = new Main();
		program.startThreads();
		keyboard.nextLine();
		try {
			program.stopThreads();
		} catch (InterruptedException e) {
			
		}
		System.out.println("goodbye");
	}

}
