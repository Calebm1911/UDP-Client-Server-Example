
package com.mycompany.udp_example;

/**
 * UDP Server using multithreading
 * 
 */
import java.io.*;
import java.net.*;

public class UDPServer {
	public static void main(String args[]) throws Exception {

		//Start server
		DatagramSocket serverSocket = new DatagramSocket(6789);
		System.out.println("The UDP Server is on.");
		int count = 0;
		byte[] receiveData = new byte[1024];

		while (true) {
			//Wait for client to send packet
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			++count;
			//String sentence = new String(receivePacket.getData(), receivePacket.getOffset(), receivePacket.getLength());
			//System.out.println(sentence);

			serverSocket.receive(receivePacket);

			MessageHandler mess = new MessageHandler(serverSocket, receivePacket, count);
			//Create thread
			new Thread(mess).start();
		}
	}
	// Message Handler for thread
	private static class MessageHandler implements Runnable {
        private  DatagramSocket serverSocket;
		private DatagramPacket receivePacket;
		private byte[] sendData = new byte[1024];
		private int count;

		public MessageHandler(DatagramSocket socket, DatagramPacket packet, int count)
        {
			this.count = count;
            this.serverSocket = socket;
			this.receivePacket = packet;
        }
		//Run method for thread 
		public void run()
        {
			try{
				
			String sentence = new String(receivePacket.getData(), receivePacket.getOffset(), receivePacket.getLength());
			InetAddress IPAddress = receivePacket.getAddress();

			int port = receivePacket.getPort();
			
			String toSend = "Total Messages: " + count + " " + sentence;
			//Print out clients message and IP and port on server
			System.out.println("Message: " + sentence + " Sent from IP: " + IPAddress + " On Port: " + port);
			sendData = toSend.getBytes();
			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
			//Send message to client
			serverSocket.send(sendPacket);
		}
		catch(IOException e)
		{
			System.out.println("IO Exception Caught");
		}
		}
}
}
