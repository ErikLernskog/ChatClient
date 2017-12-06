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
    private Socket socket;
    private String username;
    private PrintWriter to_server;
    private String message;
    private String host;
    private int port;

    public ChatClientThread(ChatClientActivity chatClientActivity, String host, int port, String username, String message) {
        this.chatClientActivity = chatClientActivity;
        this.host = host;
        this.port = port;
        this.username = username;
        this.message = message;
    }

    public void run() {

        //if (chatClientActivity.socket != null && chatClientActivity.socket.isConnected()){
        //    chatClientActivity.print("socket connected");
        //}
        //chatClientActivity.print(chatClientActivity.socket.toString());
        try {
            if (message == "CONNECT") {
                chatClientActivity.socket = new Socket(host, port);
                chatClientActivity.print("Connected");
                chatClientActivity.to_server = new PrintWriter(new BufferedWriter(new OutputStreamWriter(chatClientActivity.socket.getOutputStream())), true);
                chatClientActivity.from_server = new BufferedReader(new InputStreamReader(chatClientActivity.socket.getInputStream()));
                chatClientActivity.serverListenerThread = new ServerListenerThread(chatClientActivity);
                chatClientActivity.serverListenerThread.start();
            }
            else if (message == "DISCONNECT")
            {
                if (chatClientActivity.socket != null) {
                    Boolean isConnected = chatClientActivity.socket.isConnected();
                    chatClientActivity.print("isConnected " + isConnected);
                    chatClientActivity.from_server.close();
                    chatClientActivity.to_server.close();
                    chatClientActivity.socket.close();
                    //if (chatClientActivity.to_server != null)
                    //{
                    //    chatClientActivity.print("close to_server");
                    //    chatClientActivity.to_server.close();
                    //    chatClientActivity.to_server.
                    //}
                    //chatClientActivity.serverListenerThread.disconnect();
                    //chatClientActivity.socket.close();
                    //chatClientActivity.to_server = null;
                    //chatClientActivity.socket = null;
                }
            }
            else if (message != "") {
                chatClientActivity.print(message);
                chatClientActivity.to_server.println(message);
            }
            //chatClientActivity.print("LOGIN " + username);
            //to_server.println("LOGIN " + username);
            //chatClientActivity.print("Say Hello");
            //to_server.println("Hello");
            //to_server.close();
            //from_server.close();
            //socket.close();
        } catch (IOException e) {
            chatClientActivity.print("IOException (Socket) " + e.getMessage());
            e.printStackTrace();
        }
    }
}
