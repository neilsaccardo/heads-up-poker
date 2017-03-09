package com.saccarn.poker;

import java.io.*;
import java.net.Socket;

/**
 * Created by Neil on 08/03/2017.
 */
public class WorkerThread implements Runnable {

    protected Socket clientSocket = null;
    protected String serverText   = "HERE WE ARE";

    public WorkerThread(Socket clientSocket0, String s) {
        clientSocket = clientSocket0;
        serverText = s;
    }


    public void run() {
        try {
            String [] msgs = serverText.split(" ");
            OutputStream output = clientSocket.getOutputStream();
            DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());
            out.writeUTF(msgs[msgs.length-1] + " - response");
            System.out.println();
            long time = System.currentTimeMillis();
            System.out.println("Request processed: " + time);
        } catch (IOException e) {
            //report exception somewhere.
            e.printStackTrace();
        }
    }
}
