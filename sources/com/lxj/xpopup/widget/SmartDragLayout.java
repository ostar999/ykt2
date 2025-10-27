package com.lxj.xpopup.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.OverScroller;
import androidx.core.view.NestedScrollingParent;
import androidx.core.view.ViewCompat;
import com.lxj.xpopup.enums.LayoutStatus;

/* loaded from: classes4.dex */
public class SmartDragLayout extends LinearLayout implements NestedScrollingParent {
    private View child;
    boolean dismissOnTouchOutside;
    int duration;
    boolean enableDrag;
    boolean isScrollUp;
    boolean isThreeDrag;
    boolean isUserClose;
    int lastHeight;
    private OnCloseListener listener;
    int maxY;
    int minY;
    OverScroller scroller;
    LayoutStatus status;
    float touchX;
    float touchY;
    VelocityTracker tracker;

    public interface OnCloseListener {
        void onClose();

        void onDrag(int i2, float f2, boolean z2);

        void onOpen();
    }

    public SmartDragLayout(Context context) {
        this(context, null);
    }

    private void finishScroll() {
        int scrollY;
        if (this.enableDrag) {
            int scrollY2 = (getScrollY() > (this.isScrollUp ? this.maxY - this.minY : (this.maxY - this.minY) * 2) / 3 ? this.maxY : this.minY) - getScrollY();
            if (this.isThreeDrag) {
                int i2 = this.maxY / 3;
                float f2 = i2;
                float f3 = 2.5f * f2;
                if (getScrollY() > f3) {
                    i2 = this.maxY;
                    scrollY = getScrollY();
                } else if (getScrollY() <= f3 && getScrollY() > f2 * 1.5f) {
                    i2 *= 2;
                    scrollY = getScrollY();
                } else if (getScrollY() > i2) {
                    scrollY = getScrollY();
                } else {
                    i2 = this.minY;
                    scrollY = getScrollY();
                }
                scrollY2 = i2 - scrollY;
            }
            this.scroller.startScroll(getScrollX(), getScrollY(), 0, scrollY2, this.duration);
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    public void close() {
        this.isUserClose = true;
        post(new Runnable() { // from class: com.lxj.xpopup.widget.SmartDragLayout.2
            @Override // java.lang.Runnable
            public void run() {
                SmartDragLayout.this.scroller.abortAnimation();
                SmartDragLayout smartDragLayout = SmartDragLayout.this;
                smartDragLayout.smoothScroll(smartDragLayout.minY - smartDragLayout.getScrollY(), false);
                SmartDragLayout.this.status = LayoutStatus.Closing;
            }
        });
    }

    @Override // android.view.View
    public void computeScroll() {
        super.computeScroll();
        if (this.scroller.computeScrollOffset()) {
            scrollTo(this.scroller.getCurrX(), this.scroller.getCurrY());
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    public void dismissOnTouchOutside(boolean z2) {
        this.dismissOnTouchOutside = z2;
    }

    public void enableDrag(boolean z2) {
        this.enableDrag = z2;
    }

    @Override // android.view.ViewGroup, androidx.core.view.NestedScrollingParent
    public int getNestedScrollAxes() {
        return 2;
    }

    public void isThreeDrag(boolean z2) {
        this.isThreeDrag = z2;
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.isScrollUp = false;
        this.isUserClose = false;
        setTranslationY(0.0f);
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        this.isUserClose = true;
        LayoutStatus layoutStatus = this.status;
        if (layoutStatus == LayoutStatus.Closing || layoutStatus == LayoutStatus.Opening) {
            return false;
        }
        return super.onInterceptTouchEvent(motionEvent);
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
    public void onLayout(boolean z2, int i2, int i3, int i4, int i5) {
        this.maxY = this.child.getMeasuredHeight();
        this.minY = 0;
        int measuredWidth = (getMeasuredWidth() / 2) - (this.child.getMeasuredWidth() / 2);
        this.child.layout(measuredWidth, getMeasuredHeight(), this.child.getMeasuredWidth() + measuredWidth, getMeasuredHeight() + this.maxY);
        if (this.status == LayoutStatus.Open) {
            if (this.isThreeDrag) {
                scrollTo(getScrollX(), getScrollY() - (this.lastHeight - this.maxY));
            } else {
                scrollTo(getScrollX(), getScrollY() - (this.lastHeight - this.maxY));
            }
        }
        this.lastHeight = this.maxY;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, androidx.core.view.NestedScrollingParent
    public boolean onNestedFling(View view, float f2, float f3, boolean z2) {
        if ((getScrollY() > this.minY && getScrollY() < this.maxY) && f3 < -1500.0f && !this.isThreeDrag) {
            close();
        }
        return false;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, androidx.core.view.NestedScrollingParent
    public boolean onNestedPreFling(View view, float f2, float f3) {
        return false;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, androidx.core.view.NestedScrollingParent
    public void onNestedPreScroll(View view, int i2, int i3, int[] iArr) {
        if (i3 > 0) {
            int scrollY = getScrollY() + i3;
            if (scrollY < this.maxY) {
                iArr[1] = i3;
            }
            scrollTo(getScrollX(), scrollY);
        }
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, androidx.core.view.NestedScrollingParent
    public void onNestedScroll(View view, int i2, int i3, int i4, int i5) {
        scrollTo(getScrollX(), getScrollY() + i5);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, androidx.core.view.NestedScrollingParent
    public void onNestedScrollAccepted(View view, View view2, int i2) {
        this.scroller.abortAnimation();
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, androidx.core.view.NestedScrollingParent
    public boolean onStartNestedScroll(View view, View view2, int i2) {
        return i2 == 2 && this.enableDrag;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent, androidx.core.view.NestedScrollingParent
    public void onStopNestedScroll(View view) {
        finishScroll();
    }

    /* JADX WARN: Removed duplicated region for block: B:28:0x0063  */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean onTouchEvent(android.view.MotionEvent r9) {
        /*
            Method dump skipped, instructions count: 245
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.lxj.xpopup.widget.SmartDragLayout.onTouchEvent(android.view.MotionEvent):boolean");
    }

    @Override // android.view.ViewGroup
    public void onViewAdded(View view) {
        super.onViewAdded(view);
        this.child = view;
    }

    public void open() {
        post(new Runnable() { // from class: com.lxj.xpopup.widget.SmartDragLayout.1
            @Override // java.lang.Runnable
            public void run() {
                SmartDragLayout smartDragLayout = SmartDragLayout.this;
                int scrollY = smartDragLayout.maxY - smartDragLayout.getScrollY();
                SmartDragLayout smartDragLayout2 = SmartDragLayout.this;
                if (smartDragLayout2.enableDrag && smartDragLayout2.isThreeDrag) {
                    scrollY /= 3;
                }
                smartDragLayout2.smoothScroll(scrollY, true);
                SmartDragLayout.this.status = LayoutStatus.Opening;
            }
        });
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x0037  */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void scrollTo(int r6, int r7) {
        /*
            r5 = this;
            int r0 = r5.maxY
            if (r7 <= r0) goto L5
            r7 = r0
        L5:
            int r1 = r5.minY
            if (r7 >= r1) goto La
            r7 = r1
        La:
            int r2 = r7 - r1
            float r2 = (float) r2
            r3 = 1065353216(0x3f800000, float:1.0)
            float r2 = r2 * r3
            int r0 = r0 - r1
            float r0 = (float) r0
            float r2 = r2 / r0
            int r0 = r5.getScrollY()
            if (r7 <= r0) goto L1b
            r0 = 1
            goto L1c
        L1b:
            r0 = 0
        L1c:
            r5.isScrollUp = r0
            com.lxj.xpopup.widget.SmartDragLayout$OnCloseListener r0 = r5.listener
            if (r0 == 0) goto L4d
            boolean r1 = r5.isUserClose
            if (r1 == 0) goto L37
            r1 = 0
            int r1 = (r2 > r1 ? 1 : (r2 == r1 ? 0 : -1))
            if (r1 != 0) goto L37
            com.lxj.xpopup.enums.LayoutStatus r1 = r5.status
            com.lxj.xpopup.enums.LayoutStatus r4 = com.lxj.xpopup.enums.LayoutStatus.Close
            if (r1 == r4) goto L37
            r5.status = r4
            r0.onClose()
            goto L46
        L37:
            int r1 = (r2 > r3 ? 1 : (r2 == r3 ? 0 : -1))
            if (r1 != 0) goto L46
            com.lxj.xpopup.enums.LayoutStatus r1 = r5.status
            com.lxj.xpopup.enums.LayoutStatus r3 = com.lxj.xpopup.enums.LayoutStatus.Open
            if (r1 == r3) goto L46
            r5.status = r3
            r0.onOpen()
        L46:
            com.lxj.xpopup.widget.SmartDragLayout$OnCloseListener r0 = r5.listener
            boolean r1 = r5.isScrollUp
            r0.onDrag(r7, r2, r1)
        L4d:
            super.scrollTo(r6, r7)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.lxj.xpopup.widget.SmartDragLayout.scrollTo(int, int):void");
    }

    public void setDuration(int i2) {
        this.duration = i2;
    }

    public void setOnCloseListener(OnCloseListener onCloseListener) {
        this.listener = onCloseListener;
    }

    public void smoothScroll(final int i2, final boolean z2) {
        post(new Runnable() { // from class: com.lxj.xpopup.widget.SmartDragLayout.3
            @Override // java.lang.Runnable
            public void run() {
                SmartDragLayout smartDragLayout = SmartDragLayout.this;
                smartDragLayout.scroller.startScroll(smartDragLayout.getScrollX(), SmartDragLayout.this.getScrollY(), 0, i2, (int) (z2 ? SmartDragLayout.this.duration : SmartDragLayout.this.duration * 0.8f));
                ViewCompat.postInvalidateOnAnimation(SmartDragLayout.this);
            }
        });
    }

    public SmartDragLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public SmartDragLayout(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.enableDrag = true;
        this.dismissOnTouchOutside = true;
        this.isUserClose = false;
        this.isThreeDrag = false;
        this.status = LayoutStatus.Close;
        this.duration = 400;
        this.scroller = new OverScroller(context);
    }
}
