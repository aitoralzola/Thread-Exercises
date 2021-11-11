public class Bus extends Thread {
	Station station;
	
	public Bus(String name, Station station) {
		super (name);
		this.station = station;
	}

	@Override
	public void run() {
			try {
				station.busStarts();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		while (!isInterrupted()) {
			try {
				station.travel();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
		}
	}
}
