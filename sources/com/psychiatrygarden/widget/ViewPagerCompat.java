package com.psychiatrygarden.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import androidx.viewpager.widget.ViewPager;
import com.psychiatrygarden.interfaceclass.onDialogShareClickListener;

/* loaded from: classes6.dex */
public class ViewPagerCompat extends ViewPager {
    private float X;
    private float Y;
    private boolean isFirstTouch;
    private onDialogShareClickListener listener;
    private float xDistance;
    private float xLast;
    private float yDistance;
    private float yLast;

    public ViewPagerCompat(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.isFirstTouch = false;
    }

    @Override // androidx.viewpager.widget.ViewPager, android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        onDialogShareClickListener ondialogshareclicklistener;
        onDialogShareClickListener ondialogshareclicklistener2;
        int action = ev.getAction();
        if (action == 0) {
            this.yDistance = 0.0f;
            this.xDistance = 0.0f;
            this.Y = 0.0f;
            this.X = 0.0f;
            this.xLast = ev.getX();
            this.yLast = ev.getY();
            this.isFirstTouch = true;
        } else if (action == 2) {
            float x2 = ev.getX();
            float y2 = ev.getY();
            float f2 = x2 - this.xLast;
            this.X = f2;
            this.Y = y2 - this.yLast;
            this.xDistance += Math.abs(f2);
            this.yDistance += Math.abs(this.Y);
            this.xDistance += Math.abs(x2 - this.xLast);
            float fAbs = this.yDistance + Math.abs(y2 - this.yLast);
            this.yDistance = fAbs;
            this.xLast = x2;
            this.yLast = y2;
            if (this.xDistance > fAbs && getCurrentItem() + 1 == getAdapter().getCount() && this.X < 0.0f && (ondialogshareclicklistener2 = this.listener) != null && this.isFirstTouch) {
                ondialogshareclicklistener2.onclickIntBack(1);
                this.isFirstTouch = false;
            }
            if (this.xDistance > this.yDistance && getCurrentItem() == 0 && this.X > 0.0f && (ondialogshareclicklistener = this.listener) != null && this.isFirstTouch) {
                ondialogshareclicklistener.onclickIntBack(0);
                this.isFirstTouch = false;
            }
            if (this.xDistance < this.yDistance) {
                return false;
            }
        } else if (action == 3) {
            this.isFirstTouch = false;
        }
        return super.onInterceptTouchEvent(ev);
    }

    public void setOnListener(onDialogShareClickListener listener) {
        this.listener = listener;
    }
}
