public class Main {
    final static int ACTORS = 10;
    final static int ACTIONS = 1000000;
    final static int QUANTITY = 1;

    int account = 1000;
    Spender spender[];
    Saver saver[];

    public Main() {
        spender = new Spender[ACTORS];
        saver = new Saver[ACTORS];
        System.out.println("Init balance: "+this.account);
        this.createThreads();
        this.initThreads();
        System.out.println("Final balance: "+this.account);
    }

    private void initThreads() {
        for(int i = 0; i < ACTORS; i++) {
            spender[i].start();
            saver[i].start();
        }

        for(int i = 0; i < ACTORS; i++) {
            try {
                spender[i].join();
                saver[i].join();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public void createThreads() {
        for(int i = 0; i < ACTORS; i++) {
            spender[i] = new Spender(QUANTITY, ACTIONS, account);
            saver[i] = new Saver(QUANTITY, ACTIONS, account);
        }
    }

    public static void main(String[] args) {
        Main main = new Main();
    }
}