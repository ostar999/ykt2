package com.easefun.polyv.livecommon.ui.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.plv.foundationsdk.log.PLVCommonLog;
import com.plv.foundationsdk.utils.PLVScreenUtils;
import com.plv.thirdpart.blankj.utilcode.util.ScreenUtils;

/* loaded from: classes3.dex */
public class PLVTouchFloatingView extends FrameLayout {
    private static final String TAG = "PLVTouchFloatingView";
    private int beforeSoftLeft;
    private int beforeSoftTop;
    private boolean canMove;
    private boolean hasInitLandscapeLocation;
    private boolean hasInitPortraitLocation;
    private boolean isEnabledHorizontalDrag;
    private boolean isEnabledVerticalDrag;
    private boolean isInterceptTouchEvent;
    private boolean isLandscape;
    private int landscapeLeft;
    private int landscapeTop;
    private float lastX;
    private float lastY;
    private int originLandscapeLeft;
    private int originLandscapeTop;
    private int originPortraitLeft;
    private int originPortraitTop;
    private int portraitLeft;
    private int portraitTop;
    private RotateTask rotateTask;

    public class RotateTask implements Runnable {
        private Configuration newConfig;

        public RotateTask() {
        }

        private void resetFloatViewLand() {
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) PLVTouchFloatingView.this.getLayoutParams();
            if (PLVTouchFloatingView.this.landscapeLeft >= 0) {
                marginLayoutParams.leftMargin = PLVTouchFloatingView.this.landscapeLeft;
            }
            if (PLVTouchFloatingView.this.landscapeTop >= 0) {
                marginLayoutParams.topMargin = PLVTouchFloatingView.this.landscapeTop;
            }
            PLVTouchFloatingView.this.setLayoutParams(marginLayoutParams);
        }

        private void resetFloatViewPort() {
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) PLVTouchFloatingView.this.getLayoutParams();
            if (PLVTouchFloatingView.this.portraitLeft >= 0) {
                marginLayoutParams.leftMargin = PLVTouchFloatingView.this.portraitLeft;
            }
            if (PLVTouchFloatingView.this.portraitTop >= 0) {
                marginLayoutParams.topMargin = PLVTouchFloatingView.this.portraitTop;
            }
            PLVTouchFloatingView.this.setLayoutParams(marginLayoutParams);
        }

        public void buildConfig(Configuration configuration) {
            this.newConfig = configuration;
        }

        @Override // java.lang.Runnable
        public void run() {
            int i2 = this.newConfig.orientation;
            if (i2 == 1) {
                PLVTouchFloatingView.this.isLandscape = false;
                PLVCommonLog.d(PLVTouchFloatingView.TAG, "RotateTask.run->portrait");
                resetFloatViewPort();
                PLVTouchFloatingView.this.tryInitPortraitLocation();
                return;
            }
            if (i2 == 2) {
                PLVTouchFloatingView.this.isLandscape = true;
                PLVCommonLog.d(PLVTouchFloatingView.TAG, "RotateTask.run->landscape");
                resetFloatViewLand();
                PLVTouchFloatingView.this.tryInitLandscapeLocation();
            }
        }
    }

    public PLVTouchFloatingView(Context context) {
        this(context, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void tryInitLandscapeLocation() {
        if (this.hasInitLandscapeLocation) {
            return;
        }
        this.hasInitLandscapeLocation = true;
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) getLayoutParams();
        marginLayoutParams.leftMargin = this.originLandscapeLeft;
        marginLayoutParams.topMargin = this.originLandscapeTop;
        setLayoutParams(marginLayoutParams);
        this.landscapeLeft = this.originLandscapeLeft;
        this.landscapeTop = this.originLandscapeTop;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void tryInitPortraitLocation() {
        if (this.hasInitPortraitLocation) {
            return;
        }
        this.hasInitPortraitLocation = true;
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) getLayoutParams();
        marginLayoutParams.leftMargin = this.originPortraitLeft;
        marginLayoutParams.topMargin = this.originPortraitTop;
        setLayoutParams(marginLayoutParams);
        this.portraitTop = this.originPortraitTop;
        this.portraitLeft = this.originPortraitLeft;
    }

    public void enableHorizontalDrag(boolean enable) {
        this.isEnabledHorizontalDrag = enable;
    }

    public void enableVerticalDrag(boolean enable) {
        this.isEnabledVerticalDrag = enable;
    }

    @Override // android.view.View
    public void onConfigurationChanged(final Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        this.rotateTask.buildConfig(newConfig);
        if (getHandler() != null) {
            getHandler().removeCallbacks(this.rotateTask);
        }
        post(this.rotateTask);
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return this.isInterceptTouchEvent;
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent event) {
        if (!this.canMove) {
            return super.onTouchEvent(event);
        }
        if (!(getChildAt(0) == null || getChildAt(0).getVisibility() == 0)) {
            return super.onTouchEvent(event);
        }
        if (event.getAction() == 0) {
            this.lastX = event.getX();
            this.lastY = event.getY();
        }
        if (event.getAction() == 2) {
            float x2 = event.getX();
            float y2 = event.getY();
            int i2 = (int) (x2 - this.lastX);
            int i3 = (int) (y2 - this.lastY);
            int left = getLeft() + i2;
            int top2 = getTop() + i3;
            int measuredWidth = ((View) getParent()).getMeasuredWidth();
            int measuredHeight = ((View) getParent()).getMeasuredHeight();
            if (i2 < 0 && left < 0) {
                left = 0;
            }
            int top3 = (i3 >= 0 || top2 >= 0) ? top2 : 0;
            if (i2 > 0 && getRight() + i2 > measuredWidth) {
                left = getLeft() + (measuredWidth - getRight());
            }
            if (i3 > 0 && getBottom() + i3 > measuredHeight) {
                top3 = getTop() + (measuredHeight - getBottom());
            }
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) getLayoutParams();
            if (this.isLandscape) {
                this.landscapeTop = top3;
                this.landscapeLeft = left;
            } else {
                this.portraitTop = top3;
                this.portraitLeft = left;
            }
            if (this.isEnabledVerticalDrag) {
                marginLayoutParams.topMargin = top3;
            }
            if (this.isEnabledHorizontalDrag) {
                marginLayoutParams.leftMargin = left;
            }
            setLayoutParams(marginLayoutParams);
        }
        if (event.getAction() == 1 || event.getAction() == 3) {
            this.lastX = 0.0f;
            this.lastY = 0.0f;
        }
        return true;
    }

    public void resetSoftTo() {
        post(new Runnable() { // from class: com.easefun.polyv.livecommon.ui.widget.PLVTouchFloatingView.2
            @Override // java.lang.Runnable
            public void run() {
                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) PLVTouchFloatingView.this.getLayoutParams();
                if (marginLayoutParams == null) {
                    return;
                }
                PLVCommonLog.d(PLVTouchFloatingView.TAG, "resetSoftTo left :" + PLVTouchFloatingView.this.beforeSoftLeft + "   top " + PLVTouchFloatingView.this.beforeSoftTop);
                marginLayoutParams.leftMargin = PLVTouchFloatingView.this.beforeSoftLeft;
                marginLayoutParams.topMargin = PLVTouchFloatingView.this.beforeSoftTop;
                PLVTouchFloatingView.this.setLayoutParams(marginLayoutParams);
            }
        });
    }

    public void setContainerMove(boolean canMove) {
        this.canMove = canMove;
    }

    public void setInitLocation(int originPortraitLeft, int originPortraitTop, int originLandscapeLeft, int originLandscapeTop) {
        this.originPortraitLeft = originPortraitLeft;
        this.originPortraitTop = originPortraitTop;
        this.originLandscapeLeft = originLandscapeLeft;
        this.originLandscapeTop = originLandscapeTop;
        if (ScreenUtils.isPortrait()) {
            tryInitPortraitLocation();
        } else {
            tryInitLandscapeLocation();
        }
    }

    public void setIsInterceptTouchEvent(boolean isInterceptTouchEvent) {
        this.isInterceptTouchEvent = isInterceptTouchEvent;
    }

    public void topSubviewTo(final int top2) {
        post(new Runnable() { // from class: com.easefun.polyv.livecommon.ui.widget.PLVTouchFloatingView.1
            @Override // java.lang.Runnable
            public void run() {
                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) PLVTouchFloatingView.this.getLayoutParams();
                if (marginLayoutParams == null) {
                    return;
                }
                PLVTouchFloatingView.this.beforeSoftLeft = marginLayoutParams.leftMargin;
                PLVTouchFloatingView.this.beforeSoftTop = marginLayoutParams.topMargin;
                if (marginLayoutParams.topMargin + marginLayoutParams.height < top2) {
                    return;
                }
                PLVCommonLog.d(PLVTouchFloatingView.TAG, "topSubviewTo left :" + PLVTouchFloatingView.this.beforeSoftLeft + "   top " + top2);
                marginLayoutParams.topMargin = top2 - marginLayoutParams.height;
                PLVTouchFloatingView.this.setLayoutParams(marginLayoutParams);
            }
        });
    }

    public PLVTouchFloatingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PLVTouchFloatingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.originPortraitLeft = 0;
        this.originPortraitTop = 0;
        this.originLandscapeLeft = 0;
        this.originLandscapeTop = 0;
        this.portraitLeft = -1;
        this.portraitTop = -1;
        this.landscapeLeft = -1;
        this.landscapeTop = -1;
        this.beforeSoftLeft = 0;
        this.beforeSoftTop = 0;
        this.isEnabledHorizontalDrag = true;
        this.isEnabledVerticalDrag = true;
        this.isInterceptTouchEvent = true;
        this.hasInitPortraitLocation = false;
        this.hasInitLandscapeLocation = false;
        this.canMove = true;
        this.isLandscape = false;
        this.rotateTask = new RotateTask();
        this.isLandscape = PLVScreenUtils.isLandscape(context);
    }
}
