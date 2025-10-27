package com.plv.livescenes.video;

import android.os.CountDownTimer;
import com.plv.foundationsdk.log.PLVCommonLog;

/* loaded from: classes5.dex */
public class PLVLiveCountDownController {
    private static final String TAG = "PolyvLiveCountDownContr";
    private CountDownTimer countDownTimer;
    private FinishDelegate finishDelegate;
    private long millisInFuture = 1000;
    private long countdownInterval = 1000;

    public interface FinishDelegate {
        void onFinish();
    }

    private CountDownTimer createCountDowntimer() {
        cancelCurrentTask();
        CountDownTimer countDownTimer = new CountDownTimer(this.millisInFuture, this.countdownInterval) { // from class: com.plv.livescenes.video.PLVLiveCountDownController.1
            @Override // android.os.CountDownTimer
            public void onFinish() {
                if (PLVLiveCountDownController.this.finishDelegate != null) {
                    PLVLiveCountDownController.this.finishDelegate.onFinish();
                }
            }

            @Override // android.os.CountDownTimer
            public void onTick(long j2) {
            }
        };
        this.countDownTimer = countDownTimer;
        return countDownTimer;
    }

    public synchronized void cancelCurrentTask() {
        PLVCommonLog.d(TAG, "cancelCurrentTask");
        CountDownTimer countDownTimer = this.countDownTimer;
        if (countDownTimer != null) {
            countDownTimer.cancel();
            this.countDownTimer = null;
        }
    }

    public PLVLiveCountDownController setCountdownInterval(long j2) {
        this.countdownInterval = j2;
        return this;
    }

    public PLVLiveCountDownController setFinishDelegate(FinishDelegate finishDelegate) {
        this.finishDelegate = finishDelegate;
        return this;
    }

    public PLVLiveCountDownController setMillisInFuture(long j2) {
        this.millisInFuture = j2;
        return this;
    }

    public void startTimer() {
        createCountDowntimer();
        this.countDownTimer.start();
        PLVCommonLog.d(TAG, "startTimer");
    }
}
