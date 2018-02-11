package application;

public class TrafficHandlerThread implements Runnable{

	String address;
	String port;
	String throughput;
	String packet;
	
	
	
	
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
	}
	
	

}
