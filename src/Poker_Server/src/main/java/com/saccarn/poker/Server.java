package com.saccarn.poker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * The server class accepts connections coming in on port 3500. Implements Runnable interface.
 * Created by Neil on 08/03/2017.
 */
public class Server implements Runnable {

    private int port = 3500;
    private ServerSocket serverSocket;
    private Thread runningThread;

    public Server() {
        try {
            serverSocket = new   ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Problems with port " + port);
        }
    }

    /**
     * For every new input stream, this method spawns a new WorkerThread to deal with getting an action.
     */
    public void run() {
        synchronized (this) {
            this.runningThread = Thread.currentThread();
        }
        Socket clientSocket = null;
        try {
            clientSocket = this.serverSocket.accept();
        } catch (IOException e) {
            throw new RuntimeException("Error accepting client connection", e);
        }
        System.out.println("Listening for input on port 3500");
        while (true) {
            BufferedReader br = null;
            try {
                br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            String line = null;
            try {
                line = br.readLine();
                System.out.println(line);
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            new Thread(new WorkerThread(clientSocket, line)).start();
        }
    }

    public synchronized void stop() {
        try {
            this.serverSocket.close();
        } catch (IOException e) {
            throw new RuntimeException("Error closing server", e);
        }
    }

    /**
     *  This is the main method that kicks everything off.
     *  Starts a new instance of server - if no connections come in within 50 seconds, the server will stop.
     * @param args
     */
    public static void main(String [] args) {
        Server server = new Server();
        System.out.println("Starting......");
        new Thread(server).start();
        try {
            Thread.sleep(50 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Stopping Server");
        server.stop();
    }
}
