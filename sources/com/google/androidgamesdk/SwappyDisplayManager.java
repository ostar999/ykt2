package com.google.androidgamesdk;

import android.annotation.TargetApi;
import android.app.Activity;
import android.hardware.display.DisplayManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;
import com.google.android.exoplayer2.C;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/* loaded from: classes3.dex */
public class SwappyDisplayManager implements DisplayManager.DisplayListener {
    private Activity mActivity;
    private long mCookie;
    private Display.Mode mCurrentMode;
    private a mLooper;
    private WindowManager mWindowManager;
    private final String LOG_TAG = "SwappyDisplayManager";
    private final boolean DEBUG = false;
    private final long ONE_MS_IN_NS = 1000000;
    private final long ONE_S_IN_NS = C.NANOS_PER_SECOND;

    public class a extends Thread {

        /* renamed from: a, reason: collision with root package name */
        public Handler f7037a;

        /* renamed from: c, reason: collision with root package name */
        private Lock f7039c;

        /* renamed from: d, reason: collision with root package name */
        private Condition f7040d;

        private a() {
            ReentrantLock reentrantLock = new ReentrantLock();
            this.f7039c = reentrantLock;
            this.f7040d = reentrantLock.newCondition();
        }

        public /* synthetic */ a(SwappyDisplayManager swappyDisplayManager, byte b3) {
            this();
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public final void run() {
            Log.i("SwappyDisplayManager", "Starting looper thread");
            this.f7039c.lock();
            Looper.prepare();
            this.f7037a = new Handler();
            this.f7040d.signal();
            this.f7039c.unlock();
            Looper.loop();
            Log.i("SwappyDisplayManager", "Terminating looper thread");
        }

        @Override // java.lang.Thread
        public final void start() throws InterruptedException {
            this.f7039c.lock();
            super.start();
            try {
                this.f7040d.await();
            } catch (InterruptedException e2) {
                e2.printStackTrace();
            }
            this.f7039c.unlock();
        }
    }

    public SwappyDisplayManager(long j2, Activity activity) {
        String string;
        byte b3 = 0;
        try {
            Bundle bundle = activity.getPackageManager().getActivityInfo(activity.getIntent().getComponent(), 128).metaData;
            if (bundle != null && (string = bundle.getString("android.app.lib_name")) != null) {
                System.loadLibrary(string);
            }
        } catch (Throwable th) {
            Log.e("SwappyDisplayManager", th.getMessage());
        }
        this.mCookie = j2;
        this.mActivity = activity;
        WindowManager windowManager = (WindowManager) activity.getSystemService(WindowManager.class);
        this.mWindowManager = windowManager;
        Display defaultDisplay = windowManager.getDefaultDisplay();
        this.mCurrentMode = defaultDisplay.getMode();
        updateSupportedRefreshRates(defaultDisplay);
        DisplayManager displayManager = (DisplayManager) this.mActivity.getSystemService(DisplayManager.class);
        synchronized (this) {
            a aVar = new a(this, b3);
            this.mLooper = aVar;
            aVar.start();
            displayManager.registerDisplayListener(this, this.mLooper.f7037a);
        }
    }

    @TargetApi(23)
    private boolean modeMatchesCurrentResolution(Display.Mode mode) {
        return mode.getPhysicalHeight() == this.mCurrentMode.getPhysicalHeight() && mode.getPhysicalWidth() == this.mCurrentMode.getPhysicalWidth();
    }

    private native void nOnRefreshPeriodChanged(long j2, long j3, long j4, long j5);

    private native void nSetSupportedRefreshPeriods(long j2, long[] jArr, int[] iArr);

    private void updateSupportedRefreshRates(Display display) {
        Display.Mode[] supportedModes = display.getSupportedModes();
        int i2 = 0;
        for (Display.Mode mode : supportedModes) {
            if (modeMatchesCurrentResolution(mode)) {
                i2++;
            }
        }
        long[] jArr = new long[i2];
        int[] iArr = new int[i2];
        int i3 = 0;
        for (int i4 = 0; i4 < supportedModes.length; i4++) {
            if (modeMatchesCurrentResolution(supportedModes[i4])) {
                jArr[i3] = (long) (1.0E9f / supportedModes[i4].getRefreshRate());
                iArr[i3] = supportedModes[i4].getModeId();
                i3++;
            }
        }
        nSetSupportedRefreshPeriods(this.mCookie, jArr, iArr);
    }

    @Override // android.hardware.display.DisplayManager.DisplayListener
    public void onDisplayAdded(int i2) {
    }

    @Override // android.hardware.display.DisplayManager.DisplayListener
    public void onDisplayChanged(int i2) {
        synchronized (this) {
            Display defaultDisplay = this.mWindowManager.getDefaultDisplay();
            float refreshRate = defaultDisplay.getRefreshRate();
            Display.Mode mode = defaultDisplay.getMode();
            boolean z2 = true;
            boolean z3 = (mode.getPhysicalWidth() != this.mCurrentMode.getPhysicalWidth()) | (mode.getPhysicalHeight() != this.mCurrentMode.getPhysicalHeight());
            if (refreshRate == this.mCurrentMode.getRefreshRate()) {
                z2 = false;
            }
            this.mCurrentMode = mode;
            if (z3) {
                updateSupportedRefreshRates(defaultDisplay);
            }
            if (z2) {
                long j2 = (long) (1.0E9f / refreshRate);
                nOnRefreshPeriodChanged(this.mCookie, j2, defaultDisplay.getAppVsyncOffsetNanos(), j2 - (this.mWindowManager.getDefaultDisplay().getPresentationDeadlineNanos() - 1000000));
            }
        }
    }

    @Override // android.hardware.display.DisplayManager.DisplayListener
    public void onDisplayRemoved(int i2) {
    }

    public void setPreferredDisplayModeId(final int i2) {
        this.mActivity.runOnUiThread(new Runnable() { // from class: com.google.androidgamesdk.SwappyDisplayManager.1
            @Override // java.lang.Runnable
            public final void run() {
                Window window = SwappyDisplayManager.this.mActivity.getWindow();
                WindowManager.LayoutParams attributes = window.getAttributes();
                attributes.preferredDisplayModeId = i2;
                window.setAttributes(attributes);
            }
        });
    }

    public void terminate() {
        this.mLooper.f7037a.getLooper().quit();
    }
}
