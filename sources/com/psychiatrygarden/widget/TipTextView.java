package com.psychiatrygarden.widget;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

/* loaded from: classes6.dex */
public class TipTextView extends TextView {
    private static final int END_TIME = 400;
    private static final int SHOW_TIME = 1000;
    private static final int START_TIME = 400;
    private int titleHeight;

    public TipTextView(Context context) {
        super(context);
        this.titleHeight = 100;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void topTranslateAnimation() {
        new Handler().postDelayed(new Runnable() { // from class: com.psychiatrygarden.widget.TipTextView.2
            @Override // java.lang.Runnable
            public void run() {
                TranslateAnimation translateAnimation = new TranslateAnimation(0.0f, 0.0f, TipTextView.this.titleHeight, 0.0f);
                translateAnimation.setDuration(400L);
                translateAnimation.setFillAfter(true);
                AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
                alphaAnimation.setDuration(400L);
                AnimationSet animationSet = new AnimationSet(true);
                animationSet.addAnimation(translateAnimation);
                animationSet.addAnimation(alphaAnimation);
                TipTextView.this.startAnimation(animationSet);
                animationSet.setAnimationListener(new Animation.AnimationListener() { // from class: com.psychiatrygarden.widget.TipTextView.2.1
                    @Override // android.view.animation.Animation.AnimationListener
                    public void onAnimationEnd(Animation animation) {
                        TipTextView.this.setVisibility(8);
                    }

                    @Override // android.view.animation.Animation.AnimationListener
                    public void onAnimationRepeat(Animation animation) {
                    }

                    @Override // android.view.animation.Animation.AnimationListener
                    public void onAnimationStart(Animation animation) {
                    }
                });
            }
        }, 1000L);
    }

    public void setTitleHeight(int titleHeight) {
        this.titleHeight = titleHeight;
    }

    public void showTips() {
        setVisibility(0);
        TranslateAnimation translateAnimation = new TranslateAnimation(0.0f, 0.0f, 0.0f, this.titleHeight);
        translateAnimation.setDuration(400L);
        translateAnimation.setFillAfter(true);
        startAnimation(translateAnimation);
        translateAnimation.setAnimationListener(new Animation.AnimationListener() { // from class: com.psychiatrygarden.widget.TipTextView.1
            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationEnd(Animation animation) {
                TipTextView.this.topTranslateAnimation();
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationRepeat(Animation animation) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationStart(Animation animation) {
            }
        });
    }

    public TipTextView(Context context, AttributeSet paramAttributeSet) {
        super(context, paramAttributeSet);
        this.titleHeight = 100;
    }

    public TipTextView(Context context, AttributeSet paramAttributeSet, int paramInt) {
        super(context, paramAttributeSet, paramInt);
        this.titleHeight = 100;
    }
}
