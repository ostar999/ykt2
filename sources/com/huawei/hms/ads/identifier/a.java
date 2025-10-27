package com.huawei.hms.ads.identifier;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public final class a implements ServiceConnection {

    /* renamed from: a, reason: collision with root package name */
    public static final ThreadPoolExecutor f7445a = new ThreadPoolExecutor(0, 3, 60, TimeUnit.SECONDS, new LinkedBlockingQueue(2048), new ThreadPoolExecutor.DiscardPolicy());

    /* renamed from: b, reason: collision with root package name */
    boolean f7446b = false;

    /* renamed from: c, reason: collision with root package name */
    private final LinkedBlockingQueue<IBinder> f7447c = new LinkedBlockingQueue<>(1);

    public IBinder a() throws InterruptedException {
        if (this.f7446b) {
            throw new IllegalStateException();
        }
        this.f7446b = true;
        return this.f7447c.take();
    }

    @Override // android.content.ServiceConnection
    public void onServiceConnected(ComponentName componentName, final IBinder iBinder) {
        Log.d("PPSSerivceConnection", "onServiceConnected");
        f7445a.execute(new Runnable() { // from class: com.huawei.hms.ads.identifier.a.1
            @Override // java.lang.Runnable
            public void run() {
                try {
                    Log.d("PPSSerivceConnection", "onServiceConnected " + System.currentTimeMillis());
                    a.this.f7447c.offer(iBinder);
                } catch (Throwable th) {
                    Log.w("PPSSerivceConnection", "onServiceConnected  " + th.getClass().getSimpleName());
                }
            }
        });
    }

    @Override // android.content.ServiceConnection
    public void onServiceDisconnected(ComponentName componentName) {
        Log.d("PPSSerivceConnection", "onServiceDisconnected " + System.currentTimeMillis());
    }
}
