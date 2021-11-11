public class Passenger extends Thread {
	Station station;
	
	public Passenger(String name, Station station) {
		super(name);
		this.station = station;
	}
	@Override
	public void run() {
		station.arriveStation(getName());
		while (!isInterrupted()) {
			try {
				station.getOnBus(getName());
				station.getOutBus(getName());
			} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}	
		}
	}
}
