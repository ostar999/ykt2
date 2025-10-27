package com.aliyun.player.alivcplayerexpand.view.gesture;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import com.aliyun.player.alivcplayerexpand.view.interfaces.ViewAction;
import com.aliyun.player.aliyunplayerbase.util.AliyunScreenMode;

/* loaded from: classes2.dex */
public class GestureView extends View implements ViewAction {
    private static final String TAG = "GestureView";
    protected GestureControl mGestureControl;
    private ViewAction.HideType mHideType;
    private boolean mIsFullScreenLocked;
    private boolean mIsInMultiWindow;
    private GestureListener mOutGestureListener;

    public interface GestureListener {
        void onDoubleTap();

        void onGestureEnd();

        void onHorizontalDistance(float f2, float f3);

        void onLeftVerticalDistance(float f2, float f3);

        void onLongPressTap();

        void onRightVerticalDistance(float f2, float f3);

        void onSingleTap();
    }

    public GestureView(Context context) {
        super(context);
        this.mOutGestureListener = null;
        this.mHideType = null;
        this.mIsFullScreenLocked = false;
        init();
    }

    private void init() {
        GestureControl gestureControl = new GestureControl(getContext(), this);
        this.mGestureControl = gestureControl;
        gestureControl.setMultiWindow(this.mIsInMultiWindow);
        this.mGestureControl.setView(this);
        this.mGestureControl.setOnGestureControlListener(new GestureListener() { // from class: com.aliyun.player.alivcplayerexpand.view.gesture.GestureView.1
            @Override // com.aliyun.player.alivcplayerexpand.view.gesture.GestureView.GestureListener
            public void onDoubleTap() {
                if (GestureView.this.mIsFullScreenLocked || GestureView.this.mOutGestureListener == null) {
                    return;
                }
                GestureView.this.mOutGestureListener.onDoubleTap();
            }

            @Override // com.aliyun.player.alivcplayerexpand.view.gesture.GestureView.GestureListener
            public void onGestureEnd() {
                if (GestureView.this.mIsFullScreenLocked || GestureView.this.mOutGestureListener == null) {
                    return;
                }
                GestureView.this.mOutGestureListener.onGestureEnd();
            }

            @Override // com.aliyun.player.alivcplayerexpand.view.gesture.GestureView.GestureListener
            public void onHorizontalDistance(float f2, float f3) {
                if (GestureView.this.mIsFullScreenLocked || GestureView.this.mOutGestureListener == null) {
                    return;
                }
                GestureView.this.mOutGestureListener.onHorizontalDistance(f2, f3);
            }

            @Override // com.aliyun.player.alivcplayerexpand.view.gesture.GestureView.GestureListener
            public void onLeftVerticalDistance(float f2, float f3) {
                if (GestureView.this.mIsFullScreenLocked || GestureView.this.mOutGestureListener == null) {
                    return;
                }
                GestureView.this.mOutGestureListener.onLeftVerticalDistance(f2, f3);
            }

            @Override // com.aliyun.player.alivcplayerexpand.view.gesture.GestureView.GestureListener
            public void onLongPressTap() {
                if (GestureView.this.mIsFullScreenLocked || GestureView.this.mOutGestureListener == null) {
                    return;
                }
                GestureView.this.mOutGestureListener.onLongPressTap();
            }

            @Override // com.aliyun.player.alivcplayerexpand.view.gesture.GestureView.GestureListener
            public void onRightVerticalDistance(float f2, float f3) {
                if (GestureView.this.mIsFullScreenLocked || GestureView.this.mOutGestureListener == null) {
                    return;
                }
                GestureView.this.mOutGestureListener.onRightVerticalDistance(f2, f3);
            }

            @Override // com.aliyun.player.alivcplayerexpand.view.gesture.GestureView.GestureListener
            public void onSingleTap() {
                if (GestureView.this.mOutGestureListener != null) {
                    GestureView.this.mOutGestureListener.onSingleTap();
                }
            }
        });
    }

    @Override // com.aliyun.player.alivcplayerexpand.view.interfaces.ViewAction
    public void hide(ViewAction.HideType hideType) {
        if (this.mHideType != ViewAction.HideType.End) {
            this.mHideType = hideType;
        }
        setVisibility(8);
    }

    @Override // com.aliyun.player.alivcplayerexpand.view.interfaces.ViewAction
    public void reset() {
        this.mHideType = null;
    }

    public void setHideType(ViewAction.HideType hideType) {
        this.mHideType = hideType;
    }

    public void setMultiWindow(boolean z2) {
        this.mIsInMultiWindow = z2;
        GestureControl gestureControl = this.mGestureControl;
        if (gestureControl != null) {
            gestureControl.setMultiWindow(z2);
        }
    }

    public void setOnGestureListener(GestureListener gestureListener) {
        this.mOutGestureListener = gestureListener;
    }

    public void setScreenLockStatus(boolean z2) {
        this.mIsFullScreenLocked = z2;
    }

    @Override // com.aliyun.player.alivcplayerexpand.view.interfaces.ViewAction
    public void setScreenModeStatus(AliyunScreenMode aliyunScreenMode) {
    }

    @Override // com.aliyun.player.alivcplayerexpand.view.interfaces.ViewAction
    public void show() {
        if (this.mHideType == ViewAction.HideType.End) {
            return;
        }
        setVisibility(0);
    }

    public GestureView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mOutGestureListener = null;
        this.mHideType = null;
        this.mIsFullScreenLocked = false;
        init();
    }

    public GestureView(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.mOutGestureListener = null;
        this.mHideType = null;
        this.mIsFullScreenLocked = false;
        init();
    }
}
