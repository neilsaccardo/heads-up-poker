package com.saccarn.poker;

import java.io.*;
import java.net.Socket;

import com.saccarn.poker.ai.AiAgent;
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
            System.out.println();

            AiAgent ai = new AiAgent();
            String [] board = {};
            String action = ai.getAction("preflop", "Js", "9s", board, 10000, 200, 1, 0, 100, 0, 5000);
            long time = System.currentTimeMillis();
            System.out.println("Request processed: " + time);
            out.writeUTF(msgs[msgs.length-1] + " " + action + " " + " - response");

        } catch (IOException e) {
            //report exception somewhere.
            e.printStackTrace();
        }
    }
}
