
public class SnowWhite extends Thread{

	DinningRoom dr;
	volatile boolean finalize = false;
	
	public SnowWhite(DinningRoom dr) {
		this.dr = dr;
	}
	
	@Override
	public void run() {
		System.out.println("SnowWhite is having a walk");
		try {
			while(!this.isInterrupted()) {
				
					dr.serveDinner();
				} 
		}catch (InterruptedException e) {
			System.out.println("Programa interrumpido");
			//e.printStackTrace();
		}
	}
	
	public void endTask() {
		this.finalize = true;
	}
	
}
