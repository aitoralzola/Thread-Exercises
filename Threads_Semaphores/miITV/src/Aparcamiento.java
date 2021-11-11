import java.util.concurrent.Semaphore;

public class Aparcamiento {

	int numCochesParking;
	int numCochesInspeccion;
	
	int numCochesMaximo;
	int numInspeccionesMaximo;
	
	Semaphore semaforoAparcamiento;
	Semaphore semaforoInspeccion;
	
	Semaphore candadoAparcamiento;
	Semaphore candadoOperario;
	
	public Aparcamiento(int numCochesParking, int numCochesInspeccion, int numCochesMaximo, int numInspeccionesMaximo) {
		super();
		this.numCochesParking = numCochesParking;
		this.numCochesInspeccion = numCochesInspeccion;
		this.numCochesMaximo = numCochesMaximo;
		this.numInspeccionesMaximo = numInspeccionesMaximo;
		
		semaforoAparcamiento = new Semaphore(numCochesMaximo);
		semaforoInspeccion = new Semaphore(numInspeccionesMaximo);
		
		candadoAparcamiento = new Semaphore(1);
		candadoOperario = new Semaphore(0);
	}

	public void inspeccionarCoche() throws InterruptedException{
		candadoOperario.acquire();
		candadoAparcamiento.acquire();
		numCochesParking--;
		semaforoAparcamiento.release();
		if(numCochesParking > 0) candadoOperario.release();
		candadoAparcamiento.release();
		semaforoInspeccion.acquire();
		System.out.println("Inspeccionando coche");
		semaforoInspeccion.release();
	}
	
	public void aparcarCoche() throws InterruptedException{
		candadoAparcamiento.acquire();
		if(semaforoAparcamiento.availablePermits() > 0){
			semaforoAparcamiento.acquire();
			numCochesParking++;
			if(numCochesParking == 1) candadoOperario.release();
			System.out.println("Coche aparcado");
		} else {
			//System.out.println("Aparcamiento lleno");
		}
		candadoAparcamiento.release();
	}
}
