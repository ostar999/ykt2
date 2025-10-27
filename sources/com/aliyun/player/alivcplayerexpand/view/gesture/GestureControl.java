package com.aliyun.player.alivcplayerexpand.view.gesture;

import android.content.Context;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import com.aliyun.player.alivcplayerexpand.view.gesture.GestureView;
import com.aliyun.player.aliyunplayerbase.util.ScreenUtils;

/* loaded from: classes2.dex */
public class GestureControl {
    private static final String TAG = "GestureControl";
    public Context mContext;
    private GestureDetector mGestureDetector;
    private GestureView.GestureListener mGestureListener;
    private View mGesturebleView;
    private boolean mIsMultiWindow;
    private View mView;
    private boolean isGestureEnable = true;
    private boolean isInHorizenalGesture = false;
    private boolean isInRightGesture = false;
    private boolean isInLeftGesture = false;
    private final GestureDetector.OnGestureListener mOnGestureListener = new GestureDetector.OnGestureListener() { // from class: com.aliyun.player.alivcplayerexpand.view.gesture.GestureControl.3
        private float mXDown;

        @Override // android.view.GestureDetector.OnGestureListener
        public boolean onDown(MotionEvent motionEvent) {
            this.mXDown = motionEvent.getX();
            return true;
        }

        @Override // android.view.GestureDetector.OnGestureListener
        public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f2, float f3) {
            return false;
        }

        @Override // android.view.GestureDetector.OnGestureListener
        public void onLongPress(MotionEvent motionEvent) {
            if (GestureControl.this.mGestureListener != null) {
                GestureControl.this.mGestureListener.onLongPressTap();
            }
        }

        @Override // android.view.GestureDetector.OnGestureListener
        public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f2, float f3) {
            if (!GestureControl.this.isGestureEnable || motionEvent == null || motionEvent2 == null) {
                return false;
            }
            if (Math.abs(f2) <= Math.abs(f3)) {
                boolean unused = GestureControl.this.isInHorizenalGesture;
            } else if (!GestureControl.this.isInLeftGesture && !GestureControl.this.isInRightGesture) {
                GestureControl.this.isInHorizenalGesture = true;
            }
            if (GestureControl.this.isInHorizenalGesture) {
                if (GestureControl.this.mGestureListener != null) {
                    GestureControl.this.mGestureListener.onHorizontalDistance(motionEvent.getX(), motionEvent2.getX());
                }
            } else if (GestureControl.this.mIsMultiWindow) {
                if (ScreenUtils.isInLeft(GestureControl.this.mView, (int) this.mXDown)) {
                    GestureControl.this.isInLeftGesture = true;
                    if (GestureControl.this.mGestureListener != null) {
                        GestureControl.this.mGestureListener.onLeftVerticalDistance(motionEvent.getY(), motionEvent2.getY());
                    }
                } else if (ScreenUtils.isInRight(GestureControl.this.mView, (int) this.mXDown)) {
                    GestureControl.this.isInRightGesture = true;
                    if (GestureControl.this.mGestureListener != null) {
                        GestureControl.this.mGestureListener.onRightVerticalDistance(motionEvent.getY(), motionEvent2.getY());
                    }
                }
            } else if (ScreenUtils.isInLeft(GestureControl.this.mContext, (int) this.mXDown)) {
                GestureControl.this.isInLeftGesture = true;
                if (GestureControl.this.mGestureListener != null) {
                    GestureControl.this.mGestureListener.onLeftVerticalDistance(motionEvent.getY(), motionEvent2.getY());
                }
            } else if (ScreenUtils.isInRight(GestureControl.this.mContext, (int) this.mXDown)) {
                GestureControl.this.isInRightGesture = true;
                if (GestureControl.this.mGestureListener != null) {
                    GestureControl.this.mGestureListener.onRightVerticalDistance(motionEvent.getY(), motionEvent2.getY());
                }
            }
            return true;
        }

        @Override // android.view.GestureDetector.OnGestureListener
        public void onShowPress(MotionEvent motionEvent) {
        }

        @Override // android.view.GestureDetector.OnGestureListener
        public boolean onSingleTapUp(MotionEvent motionEvent) {
            return false;
        }
    };

    public GestureControl(Context context, View view) {
        this.mContext = context;
        this.mGesturebleView = view;
        init();
    }

    private void init() {
        GestureDetector gestureDetector = new GestureDetector(this.mContext, this.mOnGestureListener);
        this.mGestureDetector = gestureDetector;
        gestureDetector.setIsLongpressEnabled(true);
        this.mGesturebleView.setOnTouchListener(new View.OnTouchListener() { // from class: com.aliyun.player.alivcplayerexpand.view.gesture.GestureControl.1
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int action = motionEvent.getAction();
                if (action == 1 || action == 3) {
                    if (GestureControl.this.mGestureListener != null) {
                        GestureControl.this.mGestureListener.onGestureEnd();
                    }
                    GestureControl.this.isInLeftGesture = false;
                    GestureControl.this.isInRightGesture = false;
                    GestureControl.this.isInHorizenalGesture = false;
                }
                return GestureControl.this.mGestureDetector.onTouchEvent(motionEvent);
            }
        });
        Log.d("sadasdasdasd", "init: " + this.mGestureDetector.isLongpressEnabled());
        this.mGestureDetector.setOnDoubleTapListener(new GestureDetector.OnDoubleTapListener() { // from class: com.aliyun.player.alivcplayerexpand.view.gesture.GestureControl.2
            @Override // android.view.GestureDetector.OnDoubleTapListener
            public boolean onDoubleTap(MotionEvent motionEvent) {
                if (GestureControl.this.mGestureListener == null) {
                    return false;
                }
                GestureControl.this.mGestureListener.onDoubleTap();
                return false;
            }

            @Override // android.view.GestureDetector.OnDoubleTapListener
            public boolean onDoubleTapEvent(MotionEvent motionEvent) {
                return false;
            }

            @Override // android.view.GestureDetector.OnDoubleTapListener
            public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
                if (GestureControl.this.mGestureListener == null) {
                    return false;
                }
                GestureControl.this.mGestureListener.onSingleTap();
                return false;
            }
        });
    }

    public void enableGesture(boolean z2) {
        this.isGestureEnable = z2;
    }

    public void setMultiWindow(boolean z2) {
        this.mIsMultiWindow = z2;
    }

    public void setOnGestureControlListener(GestureView.GestureListener gestureListener) {
        this.mGestureListener = gestureListener;
    }

    public void setView(View view) {
        this.mView = view;
    }
}
