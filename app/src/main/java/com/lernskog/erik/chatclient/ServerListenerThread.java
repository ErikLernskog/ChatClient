package com.lernskog.erik.chatclient;

import java.io.BufferedReader;
import java.io.IOException;

public class ServerListenerThread extends Thread {
    private Boolean listen;
    private ChatClientActivity chatClientActivity;

    public ServerListenerThread(ChatClientActivity chatClientActivity) {
        this.chatClientActivity = chatClientActivity;
        this.listen = true;
    }

    public void run() {
        try {
            while (listen) {
                String line_from_server = chatClientActivity.from_server.readLine();
                if (line_from_server == null) {
                    chatClientActivity.print("Nothing to read from server.");
                } else {
                    chatClientActivity.print(line_from_server);
                }
            }
            chatClientActivity.from_server.close();
            chatClientActivity.from_server = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void disconnect()
    {
        listen = false;
    }
}
