package application;

public class TrafficHandlerThread implements Runnable{

	String address;
	String port;
	String throughput;
	String packet;
	private boolean isStopped = false;
	
	
	
	public synchronized void setStopped(boolean isStopped) {
		this.isStopped = isStopped;
	}



	public TrafficHandlerThread(String address, String port, String throughput, String packet) {
		super();
		this.address = address;
		this.port = port;
		this.throughput = throughput;
		this.packet = packet;
	}



	@Override
	public void run() {
		System.out.println("Thread has started!");
		while(isStopped()!=true){
			}
		System.out.println("Thread stop!");
	}
	
	private synchronized boolean isStopped() {
        return this.isStopped;
    }

}
