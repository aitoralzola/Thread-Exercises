import java.util.Scanner;

public class Principal {

	final int CANNIBALS_NO = 10;

	Pot pot = new Pot();
	Cannibal[] cannibals = new Cannibal[CANNIBALS_NO];
	Cooker cooker;

	Scanner kb = new Scanner(System.in);

	public void createThreads() {
		for(int i = 0; i < CANNIBALS_NO; i++ ) {
			cannibals[i] = new Cannibal(pot, i);
			//cannibals[i].setName("Cannibal "+i);
		}
		cooker = new Cooker(pot);
		cooker.setName("Cooker");
	}
	public void startThreads() {
		for(Cannibal c : cannibals) {
			c.start();
		}
		cooker.start();
	}

	public void stopThreads() {
		for(Cannibal c : cannibals) {
			c.endTask();
		}
		cooker.endTask();
	}

	public void exec() {
		createThreads();
		startThreads();
		kb.nextLine();
		stopThreads();
	}

	public static void main(String[] args) {
		Principal p = new Principal();
		p.exec();
	}
}
