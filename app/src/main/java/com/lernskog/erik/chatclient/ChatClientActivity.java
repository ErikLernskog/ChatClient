package com.lernskog.erik.chatclient;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ChatClientActivity extends Activity implements View.OnClickListener{
    public static final String TAG = "ChatClientActivity";
    private TextView chatlog;
    public EditText message;
    private EditText username;
    private EditText host;
    private EditText port;
    private Button send;
    private Button connect;
    private Button disconnect;
    private ChatClientThread chat_client_thread;

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
        connect = (Button) findViewById(R.id.connect);
        connect.setOnClickListener(this);
        disconnect = (Button) findViewById(R.id.disconnect);
        disconnect.setOnClickListener(this);
        print("onCreate");
    }

    @Override
    public void onClick(View v) {
        // print("onClick " + v.toString());
        if (v == send) {
           print("send");
            chat_client_thread.send("Hallabaloo");
        } else if (v == connect) {
            print("connect");
            chat_client_thread = new ChatClientThread(this, host.getText().toString(), Integer.parseInt(port.getText().toString()), username.getText().toString());
            chat_client_thread.start();

        } else if (v == disconnect) {
            print("disconnect");
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
