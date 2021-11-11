public class Bus extends Thread {
	
	Station station;
	
	public Bus(String name, Station dock) {
		super (name);
		this.station = dock;
	}

	@Override
	public void run() {
		try {
			while (!isInterrupted()&&station.numPassengersWaiting>0) {
				station.busStation(getName());
				station.navy(getName());
			}
		} catch (InterruptedException e) {
			
		}
	}

}
