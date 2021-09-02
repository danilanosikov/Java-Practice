package reclaid.Client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;


public class Client {
    public static void main(String[] args) {
        new ConnectionThread("localhost").start();
    }
}


class ConnectionThread extends Thread{
    final String TARGET_IP;

    public ConnectionThread (String TARGET_IP){
        this.TARGET_IP = TARGET_IP;
    }

    public void run(){
        connect();
    }

    public Socket connect(){
        int port = 1000;
        while(true) {
            try {
                Socket socket = new Socket(TARGET_IP, port);
                new WriteThread(socket).start();
                return socket;
            }
            catch (IOException e) {
                connect(port + 1);
            }
        }
    }

    private Socket connect(int port){
        while(true) {
            try {
                Socket socket = new Socket(TARGET_IP, port);
                new WriteThread(socket).start();
                return socket;
            }
            catch (IOException e) {
                connect(port + 1);
            }
        }
    }

}

class WriteThread extends Thread{
    PrintWriter printWriter;

    WriteThread(Socket socket) {
        try {
            this.printWriter = new PrintWriter(socket.getOutputStream(),true);
        } catch (IOException e) {
            System.out.println("Failed to get output stream");
        }
    }

    public void send(String msg) {
        printWriter.println(msg);
    }

    @Override
    public void run() {
        while(true){
            Scanner sc = new Scanner(System.in);
            String msg = sc.nextLine();
            if(msg.equals("/Exit")){
                break;
            }
            send(msg);
        }
    }
}


