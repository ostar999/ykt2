package com.easefun.polyv.livecommon.module.utils;

import android.content.res.Configuration;
import android.view.View;
import com.plv.thirdpart.blankj.utilcode.util.ScreenUtils;

/* loaded from: classes3.dex */
public class PLVViewLocationSensor {
    private OnViewLocationSensorListener li;
    private View rootView;
    private boolean isInFloatingView = false;
    private boolean isFirstTimeLayout = true;

    public interface OnViewLocationSensorListener {
        void onLandscapeBig();

        void onLandscapeSmall();

        void onPortraitBig();

        void onPortraitSmall();
    }

    public PLVViewLocationSensor(View rootView, OnViewLocationSensorListener li) {
        this.rootView = rootView;
        this.li = li;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onSwitchToFloatingView() {
        this.isInFloatingView = true;
        if (ScreenUtils.isLandscape()) {
            OnViewLocationSensorListener onViewLocationSensorListener = this.li;
            if (onViewLocationSensorListener != null) {
                onViewLocationSensorListener.onLandscapeSmall();
                return;
            }
            return;
        }
        OnViewLocationSensorListener onViewLocationSensorListener2 = this.li;
        if (onViewLocationSensorListener2 != null) {
            onViewLocationSensorListener2.onPortraitSmall();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onSwitchToMainScreen() {
        this.isInFloatingView = false;
        if (ScreenUtils.isLandscape()) {
            OnViewLocationSensorListener onViewLocationSensorListener = this.li;
            if (onViewLocationSensorListener != null) {
                onViewLocationSensorListener.onLandscapeBig();
                return;
            }
            return;
        }
        OnViewLocationSensorListener onViewLocationSensorListener2 = this.li;
        if (onViewLocationSensorListener2 != null) {
            onViewLocationSensorListener2.onPortraitBig();
        }
    }

    public void onConfigurationChanged(Configuration newConfig) {
        if (newConfig.orientation == 2) {
            if (this.isInFloatingView) {
                OnViewLocationSensorListener onViewLocationSensorListener = this.li;
                if (onViewLocationSensorListener != null) {
                    onViewLocationSensorListener.onLandscapeSmall();
                    return;
                }
                return;
            }
            OnViewLocationSensorListener onViewLocationSensorListener2 = this.li;
            if (onViewLocationSensorListener2 != null) {
                onViewLocationSensorListener2.onLandscapeBig();
                return;
            }
            return;
        }
        if (this.isInFloatingView) {
            OnViewLocationSensorListener onViewLocationSensorListener3 = this.li;
            if (onViewLocationSensorListener3 != null) {
                onViewLocationSensorListener3.onPortraitSmall();
                return;
            }
            return;
        }
        OnViewLocationSensorListener onViewLocationSensorListener4 = this.li;
        if (onViewLocationSensorListener4 != null) {
            onViewLocationSensorListener4.onPortraitBig();
        }
    }

    public void onSizeChanged(final int w2, int h2, int oldw, int oldh) {
        final int iMin = Math.min(ScreenUtils.getScreenWidth(), ScreenUtils.getScreenHeight());
        if (this.isFirstTimeLayout) {
            this.rootView.post(new Runnable() { // from class: com.easefun.polyv.livecommon.module.utils.PLVViewLocationSensor.1
                @Override // java.lang.Runnable
                public void run() {
                    if (w2 < iMin) {
                        PLVViewLocationSensor.this.onSwitchToFloatingView();
                    } else {
                        PLVViewLocationSensor.this.onSwitchToMainScreen();
                    }
                }
            });
            this.isFirstTimeLayout = false;
        } else if (w2 < iMin) {
            onSwitchToFloatingView();
        } else {
            onSwitchToMainScreen();
        }
    }

    public void setListener(OnViewLocationSensorListener onViewLocationSensorListener) {
        this.li = onViewLocationSensorListener;
    }
}
