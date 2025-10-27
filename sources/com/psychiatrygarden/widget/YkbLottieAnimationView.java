package com.psychiatrygarden.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import com.airbnb.lottie.LottieAnimationView;

/* loaded from: classes6.dex */
public class YkbLottieAnimationView extends LottieAnimationView {
    public YkbLottieAnimationView(Context context) {
        super(context);
    }

    @Override // com.airbnb.lottie.LottieAnimationView, android.widget.ImageView, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        cancelAnimation();
    }

    @Override // android.widget.ImageView, android.view.View
    public void onDraw(Canvas canvas) {
        try {
            super.onDraw(canvas);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    @Override // com.airbnb.lottie.LottieAnimationView
    public void pauseAnimation() {
        super.pauseAnimation();
    }

    @Override // com.airbnb.lottie.LottieAnimationView
    public void playAnimation() {
        super.playAnimation();
    }

    @Override // android.view.View
    public void setActivated(boolean activated) {
        super.setActivated(activated);
    }

    public YkbLottieAnimationView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public YkbLottieAnimationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
