public class Saver implements Runnable {

    int paccount;
    int quantity;
    int num_counters;

    public Saver(int paccount, int quantity, int num_counters) {
        this.paccount = paccount;
        this.quantity = quantity;
        this.num_counters = num_counters;
    }

    public int getPaccount() {
        return paccount;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getNum_counters() {
        return num_counters;
    }

    public void setPaccount(int paccount) {
        this.paccount = paccount;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setNum_counters(int num_counters) {
        this.num_counters = num_counters;
    }

    @Override
    public void run() {

    }
}
