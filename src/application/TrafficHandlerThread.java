package application;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class TrafficHandlerThread implements Runnable{

	String address;
	int port;
	String throughput;
	String packet;
	private boolean isStopped = false;
	private static final int SOURCE_PORT = 6010;
	InetAddress IPAddress;
    DatagramSocket serverSocket;
    byte[] sendData;
	
	
	public synchronized void setStopped(boolean isStopped) {
		this.isStopped = isStopped;
	}



	public TrafficHandlerThread(String address, String port, String throughput, String packet) {
		super();
		this.address = address;
		this.port = Integer.parseInt(port);
		this.throughput = throughput;
		this.packet = packet;
		this.sendData= new byte[1024];
		
		for(int r = 0; r<sendData.length;r++){
			sendData[r]=(byte)0xAA;
		}
		
		
		try {
			this.IPAddress=InetAddress.getByName(address);
			this.serverSocket=new DatagramSocket(SOURCE_PORT);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (SocketException s){
			
			s.printStackTrace();
		}
	}



	@Override
	public void run() {
		DatagramPacket sendPacket= new DatagramPacket(sendData, sendData.length, IPAddress, port);
		
		System.out.println("Thread has started!");
		while(isStopped()!=true){
			try {
				serverSocket.send(sendPacket);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
		System.out.println("Thread stop!");
	}
	
	private synchronized boolean isStopped() {
        return this.isStopped;
    }

}
