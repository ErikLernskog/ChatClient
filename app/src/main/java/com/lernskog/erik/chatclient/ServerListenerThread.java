package com.lernskog.erik.chatclient;

import java.io.IOException;

public class ServerListenerThread extends Thread {
    public Boolean keepreadline = true;
    private ChatClientActivity chatClientActivity;

    public ServerListenerThread(ChatClientActivity chatClientActivity) {
        this.chatClientActivity = chatClientActivity;
    }

    public void stop_readline() {
        keepreadline = false;
    }

    public void run() {
        try {
            while (keepreadline) {
                String line_from_server = chatClientActivity.from_server.readLine();
                if (line_from_server == null) {
                    chatClientActivity.print("Nothing to read from server.");
                } else {
                    chatClientActivity.print(line_from_server);
                }
            }
            chatClientActivity.print("ServerListenerThread stoped");
        } catch (IOException e) {
            chatClientActivity.print("IOException (Socket) " + e.getMessage());
            e.printStackTrace();
        }
    }
}
