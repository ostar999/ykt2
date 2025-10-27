package com.plv.foundationsdk.download;

import com.plv.foundationsdk.download.listener.IPLVDownloaderSpeedListener;
import com.plv.foundationsdk.utils.PLVAppUtils;
import java.util.Timer;
import java.util.TimerTask;

/* loaded from: classes4.dex */
public class PLVDownloaderSpeed {
    private final Object lock = new Object();
    private int trafficStatisticByteCount = 0;
    private int lastTrafficStatisticByteCount = 0;
    private Timer timer = null;
    private CallbackTask callbackTask = null;
    private IPLVDownloaderSpeedListener speedListener = null;
    private int speedCallbackInterval = 1000;

    public class CallbackTask extends TimerTask {
        private CallbackTask() {
        }

        @Override // java.util.TimerTask, java.lang.Runnable
        public void run() {
            if (PLVDownloaderSpeed.this.speedListener != null) {
                PLVAppUtils.postToMainThread(new Runnable() { // from class: com.plv.foundationsdk.download.PLVDownloaderSpeed.CallbackTask.1
                    @Override // java.lang.Runnable
                    public void run() {
                        if (PLVDownloaderSpeed.this.speedListener != null) {
                            PLVDownloaderSpeed.this.speedListener.onSpeed(PLVDownloaderSpeed.this.getSpeed());
                        }
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getSpeed() {
        int i2;
        synchronized (this.lock) {
            int i3 = this.trafficStatisticByteCount;
            i2 = i3 - this.lastTrafficStatisticByteCount;
            if (i2 < 0) {
                i2 = 0;
            }
            this.lastTrafficStatisticByteCount = i3;
        }
        return i2;
    }

    public void add(int i2) {
        synchronized (this.lock) {
            this.trafficStatisticByteCount += i2;
        }
    }

    public int getSpeedCallbackInterval() {
        return this.speedCallbackInterval;
    }

    public void setSpeedCallbackInterval(int i2) {
        this.speedCallbackInterval = i2;
    }

    public void setSpeedListener(IPLVDownloaderSpeedListener iPLVDownloaderSpeedListener) {
        this.speedListener = iPLVDownloaderSpeedListener;
    }

    public synchronized void start() {
        stop();
        this.callbackTask = new CallbackTask();
        Timer timer = new Timer();
        this.timer = timer;
        timer.schedule(this.callbackTask, 0L, this.speedCallbackInterval);
    }

    public synchronized void stop() {
        CallbackTask callbackTask = this.callbackTask;
        if (callbackTask != null) {
            callbackTask.cancel();
            this.callbackTask = null;
        }
        Timer timer = this.timer;
        if (timer != null) {
            timer.cancel();
            this.timer = null;
        }
        synchronized (this.lock) {
            this.trafficStatisticByteCount = 0;
            this.lastTrafficStatisticByteCount = 0;
        }
    }
}
