package com.lernskog.erik.chatclient;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatClientThread extends Thread {
    private ChatClientActivity chatClientActivity;
    private String host;
    private int port;
    private String username;
    private PrintWriter to_server;
    private String message;

    public ChatClientThread(ChatClientActivity chatClientActivity, String host, int port, String username) {
        this.chatClientActivity = chatClientActivity;
        this.host = host;
        this.port = port;
        this.username = username;
        this.message = "";
    }

    public void run() {
        chatClientActivity.print("run " + host + " " + port);
        try {
            Socket socket = new Socket(host, port);
            chatClientActivity.print("Connected");

            BufferedReader from_server = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            to_server = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
            // true: PrintWriter is line buffered
            chatClientActivity.print("LOGIN " + username);
            to_server.println("LOGIN " + username);
            chatClientActivity.print("Say Hello");
            to_server.println("Hello");
            while (true) {
                String line_from_server = from_server.readLine();
                if (line_from_server == null) {
                    chatClientActivity.print("Nothing to read from server.");
                } else {
                    chatClientActivity.print(line_from_server);
                }
                if (message != "") {
                    to_server.println(message);
                    message = "";
                }
            }
            //to_server.close();
            //from_server.close();
            //socket.close();
        } catch (IOException e) {
            chatClientActivity.print("IOException (Socket) " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void send(String message) {
        this.message = message;
        //to_server.println(message);
    }
}
