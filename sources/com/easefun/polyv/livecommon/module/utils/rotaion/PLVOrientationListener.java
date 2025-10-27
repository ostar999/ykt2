package com.easefun.polyv.livecommon.module.utils.rotaion;

import android.app.Activity;
import android.content.Context;
import android.view.OrientationEventListener;
import com.plv.foundationsdk.log.PLVCommonLog;

/* loaded from: classes3.dex */
public class PLVOrientationListener extends OrientationEventListener {
    private static final String TAG = "PLVOrientationListener";
    private Activity context;
    private OrientationListener listener;
    private int orientation;

    public class Orientation {
        private boolean isLandscape;
        private boolean isReverse;

        public Orientation(boolean isLandscape, boolean isReverse) {
            this.isLandscape = isLandscape;
            this.isReverse = isReverse;
        }

        public boolean isLandscape() {
            return this.isLandscape;
        }

        public boolean isReverse() {
            return this.isReverse;
        }
    }

    public interface OrientationListener {
        void onOrientationChanged(Orientation orientation);
    }

    public PLVOrientationListener(Context context) {
        this(context, 3);
    }

    public void initial(Context context) {
        if (context instanceof Activity) {
            this.context = (Activity) context;
        }
    }

    @Override // android.view.OrientationEventListener
    public void onOrientationChanged(int orientation) {
        Activity activity;
        int iAbs = Math.abs(this.orientation - orientation);
        if (this.listener == null || (activity = this.context) == null || iAbs < 30 || iAbs > 330) {
            return;
        }
        this.orientation = orientation;
        int requestedOrientation = activity.getRequestedOrientation();
        PLVCommonLog.d(TAG, "onOrientationChanged:" + orientation);
        if ((orientation >= 0 && orientation < 45) || orientation > 315) {
            if (requestedOrientation == 1 || orientation == 9) {
                return;
            }
            this.listener.onOrientationChanged(new Orientation(false, false));
            return;
        }
        if (orientation > 225 && orientation < 315) {
            if (requestedOrientation != 0) {
                this.listener.onOrientationChanged(new Orientation(true, false));
            }
        } else if (orientation > 45 && orientation < 135) {
            if (requestedOrientation != 8) {
                this.listener.onOrientationChanged(new Orientation(true, true));
            }
        } else {
            if (orientation <= 135 || orientation >= 225 || requestedOrientation == 9) {
                return;
            }
            this.listener.onOrientationChanged(new Orientation(false, true));
        }
    }

    public void setOnOrientationListener(OrientationListener listener) {
        this.listener = listener;
    }

    public PLVOrientationListener(Context context, int rate) {
        super(context, rate);
        initial(context);
    }
}
