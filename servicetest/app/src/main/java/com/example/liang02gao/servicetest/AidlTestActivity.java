package com.example.liang02gao.servicetest;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.liang02gao.messageservicetest.IRemoteService;

/**
 * Created by gaoliang on 2017/5/15.
 */

public class AidlTestActivity extends AppCompatActivity implements View.OnClickListener{

    Button getIdBtn;
    Button bindBtn;
    Button unbindBtn;
    boolean isBound = false;
    TextView callbackView;
    IRemoteService mRemoteService;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mRemoteService = IRemoteService.Stub.asInterface(service);
            Log.e("gaol", "mRemoteService = "+mRemoteService);
            if (mRemoteService != null) {
                callbackView.setText("Binded");
            }else {
                callbackView.setText("Bind error");
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mRemoteService = null;
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aidl);
        setupView();
    }

    private void setupView(){
        getIdBtn = (Button) findViewById(R.id.get_id);
        getIdBtn.setOnClickListener(this);
        bindBtn = (Button) findViewById(R.id.bind);
        bindBtn.setOnClickListener(this);
        unbindBtn = (Button) findViewById(R.id.unbind);
        unbindBtn.setOnClickListener(this);
        callbackView = (TextView) findViewById(R.id.callback);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == getIdBtn.getId()) {
            int pid = -1;
            try {
                pid = mRemoteService.getPid();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            callbackView.setText("pid = "+pid);
        }else if(v.getId() == bindBtn.getId()){
            Intent intent = new Intent();
            intent.setComponent(new ComponentName("com.example.liang02gao.messageservicetest",
                    "com.example.liang02gao.messageservicetest.RemoteService"));
            intent.setAction(IRemoteService.class.getName());
            callbackView.setText("Binding");
            bindService(intent, connection, Context.BIND_AUTO_CREATE);
            isBound = true;
        }else if(v.getId() == unbindBtn.getId()){
            unbindService(connection);
            callbackView.setText("Unbinding");
            isBound = false;
        }
    }
}
