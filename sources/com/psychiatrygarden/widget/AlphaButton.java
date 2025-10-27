package com.psychiatrygarden.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.AlphaAnimation;
import androidx.appcompat.widget.AppCompatButton;

/* loaded from: classes6.dex */
public class AlphaButton extends AppCompatButton {
    public AlphaButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    private void cancel() {
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.7f, 1.0f);
        alphaAnimation.setDuration(200L);
        alphaAnimation.setFillAfter(true);
        startAnimation(alphaAnimation);
    }

    private void start() {
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.7f);
        alphaAnimation.setDuration(200L);
        alphaAnimation.setFillAfter(true);
        startAnimation(alphaAnimation);
    }

    @Override // android.widget.TextView, android.view.View
    @SuppressLint({"ClickableViewAccessibility"})
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        if (action == 0) {
            start();
        } else if (action == 1 || action == 3 || action == 4) {
            cancel();
        }
        return super.onTouchEvent(event);
    }

    public AlphaButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AlphaButton(Context context) {
        super(context);
    }
}
