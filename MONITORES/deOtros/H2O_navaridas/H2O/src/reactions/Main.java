package reactions;

import java.util.ArrayList;
import java.util.List;

public class Main {

    private static final int MAX_OXIGEN = 10;
    private static final int MAX_HYDROGEN = MAX_OXIGEN * 2;

    private List<Hydrogen> hydrogenList = new ArrayList<>();
    private List<Oxigen> oxigenList = new ArrayList<>();
    private Tank tank = new Tank();

    private void createAtoms() {
        for (int i = 0; i < MAX_OXIGEN; i++) {
            oxigenList.add(new Oxigen(tank, i));
        }
        for (int i = 0; i < MAX_HYDROGEN; i++) {
            hydrogenList.add(new Hydrogen(tank, i));
        }
    }

    private void startThreads() {
        for (Oxigen oxigen : oxigenList) {
            oxigen.start();
        }
        for (Hydrogen hydrogen : hydrogenList) {
            hydrogen.start();
        }
    }

    private void joinThreads() throws InterruptedException {
        for (Oxigen oxigen : oxigenList) {
            oxigen.join();
        }
        for (Hydrogen hydrogen : hydrogenList) {
            hydrogen.join();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Main h2o = new Main();

        h2o.createAtoms();
        h2o.startThreads();
        h2o.joinThreads();
    }
}
