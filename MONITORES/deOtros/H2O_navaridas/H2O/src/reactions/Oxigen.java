package reactions;

public class Oxigen extends Thread {

    private Tank tank;
    private int id;

    public Oxigen (Tank tank, int id) {
        this.tank = tank;
        this.id = id;
    }

    @Override
    public void run() {
        try {
            tank.addOxigen(id);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
