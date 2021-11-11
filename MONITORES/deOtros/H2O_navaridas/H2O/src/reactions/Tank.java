package reactions;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Tank {

    private static final int NEEDED_HYDROGEN = 2;

    private boolean oxigenAdded = false;
    private int hydrogenCount = 0;

    private Lock mutex = new ReentrantLock();
    private Condition addOxigen = mutex.newCondition();
    private Condition addHydrogen = mutex.newCondition();
    private Condition reaction = mutex.newCondition();

    public void addOxigen(int id) throws InterruptedException {
        mutex.lock();
        if (oxigenAdded) {
            addOxigen.await();
        }
        System.out.println("Oxigen " + id + " has been added to the mix.");
        oxigenAdded = true;

        if (hydrogenCount < NEEDED_HYDROGEN) {
            reaction.await();
        }
        else {
            reaction.signalAll();
        }
        react();
        mutex.unlock();
    }

    public void addHydrogen(int id) throws InterruptedException {
        mutex.lock();

        if (hydrogenCount == NEEDED_HYDROGEN) {
            addHydrogen.await();
        }
        System.out.println("Hydrogen " + id + " has been added to the mix.");
        hydrogenCount++;

        if (!oxigenAdded) {
            reaction.await();
        }
        else {
            reaction.signalAll();
        }
        react();
        mutex.unlock();
    }

    private void react() {
        mutex.lock();
        if (oxigenAdded && hydrogenCount == NEEDED_HYDROGEN) {
            System.out.println("Reaction done.");
            addHydrogen.signal();
            addHydrogen.signal();
            hydrogenCount = 0;

            addOxigen.signal();
            oxigenAdded = false;
        }
        mutex.unlock();
    }
}
