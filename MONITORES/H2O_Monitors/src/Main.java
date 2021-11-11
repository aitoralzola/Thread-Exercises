import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Main {

	final int OXYGEN_ATOMS = 10;
	final int HYDROGEN_ATOMS = 20;
	Tank tank = new Tank();
    private List<Hydrogen> hydrogenList = new ArrayList<>();
    private List<Oxygen> oxygenList = new ArrayList<>();

	Scanner kb = new Scanner(System.in);
	
	public void createThreads() {
        for (int i = 0; i < OXYGEN_ATOMS; i++) {
            oxygenList.add(new Oxygen(tank, i));
        }
        for (int i = 0; i < HYDROGEN_ATOMS; i++) {
            hydrogenList.add(new Hydrogen(tank, i));
        }
	}
	
	public void startThreads() {
        for (Oxygen oxygen : oxygenList) {
            oxygen.start();
        }
        for (Hydrogen hydrogen : hydrogenList) {
            hydrogen.start();
        }
	}
	
	public void endThreads() throws InterruptedException {
        for (Oxygen oxygen : oxygenList) {
            oxygen.join();
        }
        for (Hydrogen hydrogen : hydrogenList) {
            hydrogen.join();
        }
	}
	
	public void exec() throws InterruptedException {
		createThreads();
		startThreads();
		kb.nextLine();
		endThreads();
	}
	
	public static void main(String[] args) throws InterruptedException {
		Main main = new Main();
		main.exec();
	}

}
