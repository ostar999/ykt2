package com.aliyun.player.aliyunplayerbase.util;

import android.content.Context;
import android.view.OrientationEventListener;

/* loaded from: classes2.dex */
public class OrientationWatchDog {
    private static final String TAG = "OrientationWatchDog";
    private Context mContext;
    private OrientationEventListener mLandOrientationListener;
    private Orientation mLastOrientation = Orientation.Port;
    private OnOrientationListener mOrientationListener;

    public interface OnOrientationListener {
        void changedToLandForwardScape(boolean z2);

        void changedToLandReverseScape(boolean z2);

        void changedToPortrait(boolean z2);
    }

    public enum Orientation {
        Port,
        Land_Forward,
        Land_Reverse
    }

    public OrientationWatchDog(Context context) {
        this.mContext = context.getApplicationContext();
    }

    public void destroy() {
        stopWatch();
        this.mLandOrientationListener = null;
    }

    public void setOnOrientationListener(OnOrientationListener onOrientationListener) {
        this.mOrientationListener = onOrientationListener;
    }

    public void startWatch() {
        if (this.mLandOrientationListener == null) {
            this.mLandOrientationListener = new OrientationEventListener(this.mContext, 3) { // from class: com.aliyun.player.aliyunplayerbase.util.OrientationWatchDog.1
                @Override // android.view.OrientationEventListener
                public void onOrientationChanged(int i2) {
                    if (i2 == -1) {
                        return;
                    }
                    boolean z2 = true;
                    boolean z3 = (i2 < 100 && i2 > 80) || (i2 < 280 && i2 > 260);
                    boolean z4 = i2 < 10 || i2 > 350 || (i2 < 190 && i2 > 170);
                    if (!z3) {
                        if (z4) {
                            if (OrientationWatchDog.this.mOrientationListener != null) {
                                OnOrientationListener onOrientationListener = OrientationWatchDog.this.mOrientationListener;
                                if (OrientationWatchDog.this.mLastOrientation != Orientation.Land_Reverse && OrientationWatchDog.this.mLastOrientation != Orientation.Land_Forward) {
                                    z2 = false;
                                }
                                onOrientationListener.changedToPortrait(z2);
                            }
                            OrientationWatchDog.this.mLastOrientation = Orientation.Port;
                            return;
                        }
                        return;
                    }
                    if (OrientationWatchDog.this.mOrientationListener != null && i2 < 100 && i2 > 80) {
                        OnOrientationListener onOrientationListener2 = OrientationWatchDog.this.mOrientationListener;
                        if (OrientationWatchDog.this.mLastOrientation != Orientation.Port && OrientationWatchDog.this.mLastOrientation != Orientation.Land_Forward) {
                            z2 = false;
                        }
                        onOrientationListener2.changedToLandReverseScape(z2);
                        OrientationWatchDog.this.mLastOrientation = Orientation.Land_Reverse;
                        return;
                    }
                    if (OrientationWatchDog.this.mOrientationListener == null || i2 >= 280 || i2 <= 260) {
                        return;
                    }
                    OnOrientationListener onOrientationListener3 = OrientationWatchDog.this.mOrientationListener;
                    if (OrientationWatchDog.this.mLastOrientation != Orientation.Port && OrientationWatchDog.this.mLastOrientation != Orientation.Land_Reverse) {
                        z2 = false;
                    }
                    onOrientationListener3.changedToLandForwardScape(z2);
                    OrientationWatchDog.this.mLastOrientation = Orientation.Land_Forward;
                }
            };
        }
        this.mLandOrientationListener.enable();
    }

    public void stopWatch() {
        OrientationEventListener orientationEventListener = this.mLandOrientationListener;
        if (orientationEventListener != null) {
            orientationEventListener.disable();
        }
    }
}
