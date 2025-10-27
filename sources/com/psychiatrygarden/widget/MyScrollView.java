package com.psychiatrygarden.widget;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ScrollView;

/* loaded from: classes6.dex */
public class MyScrollView extends ScrollView {
    private static final int size = 3;
    private View inner;
    private Rect normal;

    /* renamed from: y, reason: collision with root package name */
    private float f16290y;

    public MyScrollView(Context context) {
        super(context);
        this.normal = new Rect();
    }

    public void animation() {
        TranslateAnimation translateAnimation = new TranslateAnimation(0.0f, 0.0f, this.inner.getTop(), this.normal.top);
        translateAnimation.setDuration(200L);
        this.inner.startAnimation(translateAnimation);
        View view = this.inner;
        Rect rect = this.normal;
        view.layout(rect.left, rect.top, rect.right, rect.bottom);
        this.normal.setEmpty();
    }

    public void commOnTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        if (action == 0) {
            this.f16290y = ev.getY();
            return;
        }
        if (action == 1) {
            if (isNeedAnimation()) {
                animation();
            }
        } else {
            if (action != 2) {
                return;
            }
            float f2 = this.f16290y;
            float y2 = ev.getY();
            int i2 = ((int) (f2 - y2)) / 3;
            this.f16290y = y2;
            if (isNeedMove()) {
                if (this.normal.isEmpty()) {
                    this.normal.set(this.inner.getLeft(), this.inner.getTop(), this.inner.getRight(), this.inner.getBottom());
                    return;
                }
                int top2 = this.inner.getTop() - i2;
                View view = this.inner;
                view.layout(view.getLeft(), top2, this.inner.getRight(), this.inner.getBottom() - i2);
            }
        }
    }

    public boolean isNeedAnimation() {
        return !this.normal.isEmpty();
    }

    public boolean isNeedMove() {
        int measuredHeight = this.inner.getMeasuredHeight() - getHeight();
        int scrollY = getScrollY();
        return scrollY == 0 || scrollY == measuredHeight;
    }

    @Override // android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() > 0) {
            if (!this.normal.isEmpty()) {
                this.normal = new Rect();
                this.f16290y = 0.0f;
                this.inner = null;
            }
            this.inner = getChildAt(0);
        }
    }

    @Override // android.widget.ScrollView, android.view.View
    public boolean onTouchEvent(MotionEvent ev) {
        if (this.inner == null) {
            return super.onTouchEvent(ev);
        }
        commOnTouchEvent(ev);
        return super.onTouchEvent(ev);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.normal = new Rect();
    }
}
