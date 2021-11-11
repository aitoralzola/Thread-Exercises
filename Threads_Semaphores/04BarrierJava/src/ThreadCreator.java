import java.util.Scanner;

public class ThreadCreator {
	static final int MAXTHREADS = 10;
	Barrier barrier;
	Barrier barrier2;
	CounterThread threadList[];
	
	public ThreadCreator(){
		barrier = new Barrier (MAXTHREADS);
		threadList = new CounterThread[MAXTHREADS];
	}
	public void createThreads(){
		for (int i = 0; i<MAXTHREADS; i++) {
			threadList[i] = new CounterThread("Thread " + i, barrier, barrier2);
		}
	}

	public void startThreads(){
		System.out.println("Counting begins: ");
		
		for (int i = 0; i<MAXTHREADS; i++){
			threadList[i].start();
		}
	}
	
	public void waitEndOfThreads(){
		for (int i = 0; i<MAXTHREADS; i++){
			try {
				threadList[i].join();
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
		}
	}
	public static void main(String[] args) {
		
		ThreadCreator exercise = new ThreadCreator();
		exercise.createThreads();
		exercise.startThreads();


		exercise.waitEndOfThreads();
	
		System.out.println("adios");
	}

}
