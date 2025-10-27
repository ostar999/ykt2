package com.google.androidgamesdk;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Choreographer;

/* loaded from: classes3.dex */
public class ChoreographerCallback implements Choreographer.FrameCallback {
    private static final String LOG_TAG = "ChoreographerCallback";
    private long mCookie;
    private a mLooper;

    public class a extends Thread {

        /* renamed from: a, reason: collision with root package name */
        public Handler f7033a;

        private a() {
        }

        public /* synthetic */ a(ChoreographerCallback choreographerCallback, byte b3) {
            this();
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public final void run() {
            Log.i(ChoreographerCallback.LOG_TAG, "Starting looper thread");
            Looper.prepare();
            this.f7033a = new Handler();
            Looper.loop();
            Log.i(ChoreographerCallback.LOG_TAG, "Terminating looper thread");
        }
    }

    public ChoreographerCallback(long j2) {
        this.mCookie = j2;
        a aVar = new a(this, (byte) 0);
        this.mLooper = aVar;
        aVar.start();
    }

    @Override // android.view.Choreographer.FrameCallback
    public void doFrame(long j2) {
        nOnChoreographer(this.mCookie, j2);
    }

    public native void nOnChoreographer(long j2, long j3);

    public void postFrameCallback() {
        this.mLooper.f7033a.post(new Runnable() { // from class: com.google.androidgamesdk.ChoreographerCallback.1
            @Override // java.lang.Runnable
            public final void run() {
                Choreographer.getInstance().postFrameCallback(ChoreographerCallback.this);
            }
        });
    }

    public void postFrameCallbackDelayed(long j2) {
        Choreographer.getInstance().postFrameCallbackDelayed(this, j2);
    }

    public void terminate() {
        this.mLooper.f7033a.getLooper().quit();
    }
}
