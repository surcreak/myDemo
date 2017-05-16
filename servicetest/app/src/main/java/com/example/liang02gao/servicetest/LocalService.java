package com.example.liang02gao.servicetest;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.util.Random;

/**
 * Created by gaoliang on 2017/5/9.
 */

public class LocalService extends Service {

    class LocalBinder extends Binder{
        LocalService getSercice(){
            return LocalService.this;
        }
    }

    private final IBinder mBinder = new LocalBinder();
    private final Random mGenerate = new Random();

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public int getRandomNumber(){
        return  mGenerate.nextInt(100);
    }
}
