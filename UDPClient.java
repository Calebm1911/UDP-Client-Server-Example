
package com.mycompany.udp_example;

/**
 * UDP Client for sending messages to server
 * 
 */
import java.io.*;
import java.net.*;

public class UDPClient {
	public static void main(String args[]) throws Exception {

		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));

		DatagramSocket clientSocket = new DatagramSocket();

		InetAddress IPAddress = InetAddress.getByName("localhost");

		byte[] sendData = new byte[1024];
		byte[] receiveData = new byte[1024];
		
		System.out.println("The UDP client is on.");
		//Loop runs until exit is entered. 
		while(true)
		{
		System.out.println("Please enter your input:");
		String sentence = inFromUser.readLine();
		if (sentence.equalsIgnoreCase("exit"))
		{
			clientSocket.close();
			break;
		}

		sendData = sentence.getBytes();

		DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 6789);

		clientSocket.send(sendPacket);

		DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

		clientSocket.receive(receivePacket);

		String modifiedSentence = new String(receivePacket.getData(), receivePacket.getOffset(), receivePacket.getLength());

		System.out.println("FROM SERVER:" + modifiedSentence);
		}
	}
}