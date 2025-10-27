package com.easefun.polyv.livecommon.module.utils.rotaion;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.ContentObserver;
import android.os.Handler;
import android.provider.Settings;
import com.easefun.polyv.livecommon.module.utils.rotaion.PLVOrientationListener;

/* loaded from: classes3.dex */
public class PLVRotationObserver {
    private Activity context;
    private boolean isLifecycleStop;
    private boolean lockOrientation;
    private PLVOrientationListener orientationListener;
    private boolean rotationEnabled = true;
    private RotationObserver rotationObserver = new RotationObserver(new Handler());

    public class RotationObserver extends ContentObserver {
        ContentResolver mResolver;

        public RotationObserver(Handler handler) {
            super(handler);
            this.mResolver = PLVRotationObserver.this.context.getContentResolver();
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            if (Settings.System.getInt(PLVRotationObserver.this.context.getContentResolver(), "accelerometer_rotation", 0) == 1) {
                if (PLVRotationObserver.this.orientationListener != null) {
                    PLVRotationObserver.this.orientationListener.enable();
                }
            } else if (PLVRotationObserver.this.orientationListener != null) {
                PLVRotationObserver.this.orientationListener.disable();
            }
        }

        public void startObserver() {
            this.mResolver.registerContentObserver(Settings.System.getUriFor("accelerometer_rotation"), false, this);
        }

        public void stopObserver() {
            this.mResolver.unregisterContentObserver(this);
        }
    }

    public PLVRotationObserver(Activity context) {
        this.context = context;
        this.orientationListener = new PLVOrientationListener(context);
    }

    public Activity getContext() {
        return this.context;
    }

    public boolean isLockOrientation() {
        return this.lockOrientation;
    }

    public void lockOrientation() {
        this.lockOrientation = true;
        stop(false);
    }

    public void setOrientationListener(PLVOrientationListener.OrientationListener listener) {
        this.orientationListener.setOnOrientationListener(listener);
    }

    public void setRotationEnabled(boolean orientationRotationEnabled) {
        this.rotationEnabled = orientationRotationEnabled;
    }

    public void start() {
        if (!this.lockOrientation && this.rotationEnabled) {
            this.isLifecycleStop = false;
            if (Settings.System.getInt(this.context.getContentResolver(), "accelerometer_rotation", 0) == 1) {
                this.orientationListener.enable();
            } else {
                this.orientationListener.disable();
            }
            this.rotationObserver.startObserver();
        }
    }

    public void stop() {
        stop(true);
    }

    public void unlockOrientation() {
        this.lockOrientation = false;
        if (this.isLifecycleStop) {
            return;
        }
        start();
    }

    public void stop(boolean isLifecycleStop) {
        this.rotationObserver.stopObserver();
        this.orientationListener.disable();
        if (isLifecycleStop) {
            this.isLifecycleStop = true;
        }
    }
}
