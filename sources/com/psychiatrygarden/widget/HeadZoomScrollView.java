package com.psychiatrygarden.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

/* loaded from: classes6.dex */
public class HeadZoomScrollView extends CoordinatorLayout {
    private float firstPosition;
    private boolean isScrolling;
    private float mReplyRate;
    private float mScrollRate;
    private View mZoomView;
    private int mZoomViewHeight;
    private int mZoomViewWidth;

    public HeadZoomScrollView(Context context) {
        super(context);
        this.mScrollRate = 0.3f;
        this.mReplyRate = 0.5f;
    }

    private void init() {
        setOverScrollMode(2);
        if (getChildAt(0) != null) {
            ViewGroup viewGroup = (ViewGroup) getChildAt(0);
            if (viewGroup.getChildAt(0) != null) {
                this.mZoomView = viewGroup.getChildAt(0);
            }
        }
    }

    private void replyImage() {
        float measuredWidth = this.mZoomView.getMeasuredWidth() - this.mZoomViewWidth;
        ValueAnimator duration = ValueAnimator.ofFloat(measuredWidth, 0.0f).setDuration((long) (measuredWidth * this.mReplyRate));
        duration.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.psychiatrygarden.widget.HeadZoomScrollView.1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator animation) {
                HeadZoomScrollView.this.setZoom(((Float) animation.getAnimatedValue()).floatValue());
            }
        });
        duration.start();
    }

    @Override // android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        init();
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x0041  */
    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout, android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean onTouchEvent(android.view.MotionEvent r4) {
        /*
            r3 = this;
            int r0 = r3.mZoomViewWidth
            if (r0 <= 0) goto L8
            int r0 = r3.mZoomViewHeight
            if (r0 > 0) goto L18
        L8:
            android.view.View r0 = r3.mZoomView
            int r0 = r0.getMeasuredWidth()
            r3.mZoomViewWidth = r0
            android.view.View r0 = r3.mZoomView
            int r0 = r0.getMeasuredHeight()
            r3.mZoomViewHeight = r0
        L18:
            int r0 = r4.getAction()
            r1 = 1
            if (r0 == r1) goto L48
            r2 = 2
            if (r0 == r2) goto L23
            goto L4e
        L23:
            boolean r0 = r3.isScrolling
            if (r0 != 0) goto L33
            int r0 = r3.getScrollY()
            if (r0 != 0) goto L4e
            float r0 = r4.getY()
            r3.firstPosition = r0
        L33:
            float r4 = r4.getY()
            float r0 = r3.firstPosition
            float r4 = r4 - r0
            float r0 = r3.mScrollRate
            float r4 = r4 * r0
            int r4 = (int) r4
            if (r4 >= 0) goto L41
            goto L4e
        L41:
            r3.isScrolling = r1
            float r4 = (float) r4
            r3.setZoom(r4)
            return r1
        L48:
            r4 = 0
            r3.isScrolling = r4
            r3.replyImage()
        L4e:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.psychiatrygarden.widget.HeadZoomScrollView.onTouchEvent(android.view.MotionEvent):boolean");
    }

    public void setZoom(float zoom) {
        if (this.mZoomViewWidth <= 0 || this.mZoomViewHeight <= 0) {
            return;
        }
        ViewGroup.LayoutParams layoutParams = this.mZoomView.getLayoutParams();
        int i2 = this.mZoomViewWidth;
        int i3 = (int) (i2 + zoom);
        layoutParams.width = i3;
        layoutParams.height = (int) (this.mZoomViewHeight * ((i2 + zoom) / i2));
        ((ViewGroup.MarginLayoutParams) layoutParams).setMargins((-(i3 - i2)) / 2, 0, 0, 0);
        this.mZoomView.setLayoutParams(layoutParams);
    }

    public void setmReplyRate(float mReplyRate) {
        this.mReplyRate = mReplyRate;
    }

    public void setmScrollRate(float mScrollRate) {
        this.mScrollRate = mScrollRate;
    }

    public void setmZoomView(View mZoomView) {
        this.mZoomView = mZoomView;
    }

    public HeadZoomScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mScrollRate = 0.3f;
        this.mReplyRate = 0.5f;
    }

    public HeadZoomScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mScrollRate = 0.3f;
        this.mReplyRate = 0.5f;
    }
}
