public class Passenger extends Thread {
	Station station;
	
	public Passenger(String name, Station dock) {
		super(name);
		this.station = dock;
	}
	@Override
	public void run() {
		try {
			station.arriveStation(this.getName());
			station.boardBus(this.getName());
			station.travel(this.getName());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
