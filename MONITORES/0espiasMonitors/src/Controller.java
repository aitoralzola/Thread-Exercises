
public class Controller {
	
	boolean flag;
	public Controller() {
		flag = true;
	}
	
	public synchronized void askPermisionA() throws InterruptedException{
		if(!flag) wait();
	}

	public synchronized void askPermisionB() throws InterruptedException{ 
		if(flag) wait();
	}
	public synchronized void finishA() throws InterruptedException{ 
		flag = !flag;
		notify();
	}
	public synchronized void finishB() throws InterruptedException{ 
		flag = !flag;
		notify();
	}
}
