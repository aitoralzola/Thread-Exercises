import java.util.Scanner;

public class Main {
	final static int NUMCANNIBALS = 20;
	
	Snowhite snowhite;
	Pot pot;
	Dwarf savages[];

	public Main() {
		pot = new Pot();
		snowhite = Snowhite(pot);
		savages = new Dwarf[NUMCANNIBALS];
		for (int i= 0;i<NUMCANNIBALS;i++) {
			savages[i] = new Dwarf(pot,"cannibal "+i);
		}
	}
	
	public void startThreads() {
		snowhite.start();
		for (Dwarf dwarf : savages) {
			dwarf.start();
		}
	}
	public void stopThreads() throws InterruptedException {
		snowhite.interrupt();
		for (Dwarf dwarf : savages) {
			dwarf.interrupt();
		}
		snowhite.join();
		for (Dwarf cannibal : savages) {
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
