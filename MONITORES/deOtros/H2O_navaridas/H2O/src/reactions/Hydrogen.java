package reactions;

public class Hydrogen extends Thread {

    private Tank tank;
    private int id;

    public Hydrogen (Tank tank, int id) {
        this.tank = tank;
        this.id = id;
    }

    @Override
    public void run() {
        try {
            tank.addHydrogen(id);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
