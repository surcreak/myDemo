package com.example.liang02gao.servicetest;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button bindLocalServiceBtn;
    LocalService mService;
    boolean mBound = false;

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            LocalService.LocalBinder binder = (LocalService.LocalBinder) service;
            mService = binder.getSercice();
            Log.d("gaol", "onServiceConnected: mService="+mService);
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBound = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupView();
    }

    private void setupView() {
        bindLocalServiceBtn = (Button) findViewById(R.id.bind_localservice);
        bindLocalServiceBtn.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("gaol", "onStart: ");
        Intent intent = new Intent(this, LocalService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mBound){
            unbindService(mConnection);
            mBound = false;
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == bindLocalServiceBtn.getId()) {
            int random = mService.getRandomNumber();
            Toast.makeText(this, "random="+random, Toast.LENGTH_LONG).show();
        }
    }
}
