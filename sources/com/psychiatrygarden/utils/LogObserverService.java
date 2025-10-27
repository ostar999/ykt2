package com.psychiatrygarden.utils;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import com.mobile.auth.BuildConfig;
import com.psychiatrygarden.aliPlayer.SkinActivity;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/* loaded from: classes6.dex */
public class LogObserverService extends Service implements Runnable {
    private String TAG = "LogObserverService";
    private boolean isObserverLog = false;
    private StringBuffer logContent = null;
    private Bundle mBundle = null;
    private Intent mIntent = null;

    private void sendLogContent(String logContent) {
        this.mBundle.putString(BuildConfig.FLAVOR_type, logContent);
        this.mIntent.putExtras(this.mBundle);
        this.mIntent.setAction(SkinActivity.LOG_ACTION);
        sendBroadcast(this.mIntent);
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        Log.i(this.TAG, "onCreate");
        this.mIntent = new Intent();
        this.mBundle = new Bundle();
        this.logContent = new StringBuffer();
        startLogObserver();
    }

    @Override // android.app.Service
    public void onDestroy() {
        super.onDestroy();
        stopLogObserver();
    }

    @Override // android.app.Service
    @SuppressLint({"WrongConstant"})
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.v("TrafficService", "startCommand");
        return super.onStartCommand(intent, 1, startId);
    }

    @Override // java.lang.Runnable
    public void run() throws IOException {
        BufferedReader bufferedReader;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec(new String[]{"logcat", "-s", "AlivcPlayer | AVMPlayer | lfj0115 | CheckLog | LogSender | GetPlayAuthRequest | lifujunAuthInfoFlow | Request | QualityChooser | PlayerProxy"}).getInputStream()));
        } catch (IOException e2) {
            e2.printStackTrace();
            bufferedReader = null;
        }
        try {
            System.out.println(bufferedReader.readLine());
            while (true) {
                String line = bufferedReader.readLine();
                if (line == null) {
                    return;
                }
                System.out.println(line);
                StringBuffer stringBuffer = this.logContent;
                stringBuffer.delete(0, stringBuffer.length());
                this.logContent.append(line);
                this.logContent.append("\n");
                sendLogContent(this.logContent.toString());
                Thread.yield();
            }
        } catch (Exception e3) {
            e3.printStackTrace();
        }
    }

    public void startLogObserver() {
        Log.i(this.TAG, "startObserverLog");
        this.isObserverLog = true;
        new Thread(this).start();
    }

    public void stopLogObserver() {
        this.isObserverLog = false;
    }
}
