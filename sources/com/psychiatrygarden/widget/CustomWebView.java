package com.psychiatrygarden.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.webkit.WebView;

/* loaded from: classes6.dex */
public class CustomWebView extends WebView implements GestureDetector.OnGestureListener {
    private GestureDetector mGestureDetector;

    public CustomWebView(Context context) {
        super(context);
        initGesture();
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchTouchEvent(MotionEvent ev) {
        this.mGestureDetector.onTouchEvent(ev);
        return super.dispatchTouchEvent(ev);
    }

    public void initGesture() {
        this.mGestureDetector = new GestureDetector(this);
    }

    @Override // android.view.GestureDetector.OnGestureListener
    public boolean onDown(MotionEvent e2) {
        return false;
    }

    @Override // android.view.GestureDetector.OnGestureListener
    public boolean onFling(MotionEvent e12, MotionEvent e2, float velocityX, float velocityY) {
        float fAbs = Math.abs(e12.getX() - e2.getX());
        float fAbs2 = Math.abs(e12.getY() - e2.getY());
        if (e12.getX() - e2.getX() <= 200.0f || fAbs <= fAbs2) {
            return e12.getX() - e2.getX() < -200.0f && fAbs > fAbs2;
        }
        return true;
    }

    @Override // android.view.GestureDetector.OnGestureListener
    public void onLongPress(MotionEvent e2) {
    }

    @Override // android.view.GestureDetector.OnGestureListener
    public boolean onScroll(MotionEvent e12, MotionEvent e2, float distanceX, float distanceY) {
        return true;
    }

    @Override // android.view.GestureDetector.OnGestureListener
    public void onShowPress(MotionEvent e2) {
    }

    @Override // android.view.GestureDetector.OnGestureListener
    public boolean onSingleTapUp(MotionEvent e2) {
        return false;
    }

    public CustomWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initGesture();
    }

    public CustomWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initGesture();
    }
}
