package reclaid.Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.lang.Thread;
import java.util.ArrayList;
import java.util.List;


public class Server {

	public static void main(String[] args) {
		new ClientHandler().start();
	}
}









class ClientHandler extends Thread{

	ArrayList<Socket> clients;


	public void run(){
		while(true){
			try {
				clients.add(accept());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public Socket accept() throws IOException {
		int port = 1000;

		try {
			Socket socket = new ServerSocket(port).accept();
			new ReadThread(socket);
			return socket;
		}

		catch (IOException e) {
			return accept(port + 1);
		}
	}


	private Socket accept(int port) throws IOException{
		try {
			Socket socket = new ServerSocket(port).accept();
			new ReadThread(socket);
			return socket;
		}

		catch (IOException e) {
			return accept(port + 1);
		}
	}

}

class ReadThread extends Thread{
	BufferedReader br;

	ReadThread(Socket socket) throws IOException {
		this.br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	}

	public void listen() throws IOException {
		System.out.println(br.readLine());
	}

	@Override
	public void run() {
		try {
			while(true){
				listen();
			}
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
}
