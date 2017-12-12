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
    private String message;
    private String host;
    private int port;

    public ChatClientThread(ChatClientActivity chatClientActivity, String host, int port, String message) {
        this.chatClientActivity = chatClientActivity;
        this.host = host;
        this.port = port;
        this.message = message;
    }

    public void run() {
        try {
            if (message == "CONNECT") {
                chatClientActivity.socket = new Socket(host, port);
                chatClientActivity.print("Connected");
                chatClientActivity.to_server = new PrintWriter(new BufferedWriter(new OutputStreamWriter(chatClientActivity.socket.getOutputStream())), true);
                chatClientActivity.from_server = new BufferedReader(new InputStreamReader(chatClientActivity.socket.getInputStream()));
                chatClientActivity.serverListenerThread = new ServerListenerThread(chatClientActivity);
                chatClientActivity.serverListenerThread.start();
            } else if (message == "DISCONNECT") {
                chatClientActivity.print("close start");
                if (chatClientActivity.from_server != null) {
                    chatClientActivity.serverListenerThread.keepreadline = false;
                    chatClientActivity.from_server.close();
                    chatClientActivity.print("from server closed");
                }
                if (chatClientActivity.to_server != null) {
                    chatClientActivity.to_server.close();
                    chatClientActivity.print("to_server closed");
                }
                chatClientActivity.print("close done");
            } else if (message == "LOGOUT") {
                chatClientActivity.print("logout start");
                if (chatClientActivity.from_server != null) {
                    chatClientActivity.serverListenerThread.keepreadline = false;
                    chatClientActivity.print("from server stop keep read line");
                }
                if (chatClientActivity.to_server != null) {
                    chatClientActivity.print(message);
                    chatClientActivity.to_server.println(message);
                }
                if (chatClientActivity.from_server != null) {
                    chatClientActivity.from_server.close();
                    chatClientActivity.print("from server closed");
                }
                chatClientActivity.print("logout done");

            } else if (chatClientActivity.to_server != null) {
                chatClientActivity.print(message);
                chatClientActivity.to_server.println(message);
            }
        } catch (IOException e) {
            chatClientActivity.print("IOException (Socket) " + e.getMessage());
            e.printStackTrace();
        }
    }
}
