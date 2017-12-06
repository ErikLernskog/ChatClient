package com.lernskog.erik.chatclient;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatClientActivity extends Activity implements View.OnClickListener {
    public static final String TAG = "ChatClientActivity";
    public EditText message;
    private TextView chatlog;
    private EditText username;
    private EditText host;
    private EditText port;
    private Button send;
    private Button login;
    private Button logout;
    private Button connect;
    private Button disconnect;
    private ChatClientThread chat_client_thread;
    public ServerListenerThread serverListenerThread;
    public Socket socket;
    public PrintWriter to_server;
    public BufferedReader from_server;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_client);
        chatlog = (TextView) findViewById(R.id.chatlog);
        message = (EditText) findViewById(R.id.message);
        username = (EditText) findViewById(R.id.username);
        host = (EditText) findViewById(R.id.host);
        port = (EditText) findViewById(R.id.port);
        send = (Button) findViewById(R.id.send);
        send.setOnClickListener(this);
        login = (Button) findViewById(R.id.login);
        login.setOnClickListener(this);
        logout = (Button) findViewById(R.id.logout);
        logout.setOnClickListener(this);
        connect = (Button) findViewById(R.id.connect);
        connect.setOnClickListener(this);
        disconnect = (Button) findViewById(R.id.disconnect);
        disconnect.setOnClickListener(this);
        print("onCreate");
    }

    @Override
    public void onClick(View v) {
        // print("onClick " + v.toString());
        String hostStr = host.getText().toString();
        int portInt = Integer.parseInt(port.getText().toString());
        String usernameStr = username.getText().toString();
        String messageStr = message.getText().toString();
        if (v == login) {
            print("login");
            messageStr = "LOGIN " + usernameStr;
            chat_client_thread = new ChatClientThread(this, hostStr, portInt, usernameStr, messageStr);
            chat_client_thread.start();
        } else if (v == logout) {
            print("logout");
            messageStr = "LOGOUT";
            chat_client_thread = new ChatClientThread(this, hostStr, portInt, usernameStr, messageStr);
            chat_client_thread.start();
        } else if (v == send) {
            print("send");
            chat_client_thread = new ChatClientThread(this, hostStr, portInt, usernameStr, messageStr);
            chat_client_thread.start();
        } else if (v == connect) {
            print("connect");
            messageStr = "CONNECT";
            chat_client_thread = new ChatClientThread(this, hostStr, portInt, usernameStr, messageStr);
            chat_client_thread.start();
        } else if (v == disconnect) {
            print("disconnect");
            messageStr = "DISCONNECT";
            chat_client_thread = new ChatClientThread(this, hostStr, portInt, usernameStr, messageStr);
            chat_client_thread.start();
        }
    }

    public void print(final String message) {
        Log.d(TAG, message);
        chatlog.post(new Runnable() {
            @Override
            public void run() {
                chatlog.setText(chatlog.getText() + message + "\n");
            }
        });
    }
}
