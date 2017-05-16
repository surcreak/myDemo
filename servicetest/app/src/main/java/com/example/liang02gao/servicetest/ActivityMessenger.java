package com.example.liang02gao.servicetest;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by gaoliang on 2017/5/10.
 */

public class ActivityMessenger extends AppCompatActivity implements View.OnClickListener{
    private boolean mbound = false;
    Messenger mService;
    private Button sayHello;

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mService = new Messenger(service);
            mbound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mService = null;
            mbound = false;
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messenger);
        setupView();
    }

    private void setupView(){
        sayHello = (Button) findViewById(R.id.say_hello);
        sayHello.setOnClickListener(this);
    }

    public void sayHello(){
        if(!mbound) return;
        Message message = Message.obtain(null, 1, 0, 0);
        try {
            mService.send(message);
        }catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.example.liang02gao.messageservicetest",
                "com.example.liang02gao.messageservicetest.MessageService"));
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mbound) {
            unbindService(mConnection);
            mbound = false;
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == sayHello.getId()) {
            sayHello();
        }
    }
}
