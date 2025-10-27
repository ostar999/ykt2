package com.aliyun.player.alivcplayerexpand.view.choice;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import androidx.annotation.NonNull;
import com.aliyun.player.alivcplayerexpand.R;
import com.aliyun.player.aliyunplayerbase.util.AliyunScreenMode;
import com.aliyun.player.aliyunplayerbase.util.ScreenUtils;
import com.yikaobang.yixue.R2;
import java.lang.ref.WeakReference;

/* loaded from: classes2.dex */
public class AlivcShowMoreDialog extends Dialog {
    private static final int ANIMATION_DURATION = 200;
    private WeakReference<Context> activityWeakReference;
    private View mContentView;
    private boolean mIsAnimating;
    private AliyunScreenMode screenMode;

    public AlivcShowMoreDialog(@NonNull Context context) {
        this(context, AliyunScreenMode.Full);
    }

    private void animateDown() {
        if (this.mContentView == null) {
            return;
        }
        TranslateAnimation translateAnimation = new TranslateAnimation(1, 0.0f, 1, 0.0f, 1, 0.0f, 1, 1.0f);
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
        AnimationSet animationSet = new AnimationSet(true);
        animationSet.addAnimation(translateAnimation);
        animationSet.addAnimation(alphaAnimation);
        animationSet.setInterpolator(new DecelerateInterpolator());
        animationSet.setDuration(200L);
        animationSet.setFillAfter(true);
        animationSet.setAnimationListener(new Animation.AnimationListener() { // from class: com.aliyun.player.alivcplayerexpand.view.choice.AlivcShowMoreDialog.1
            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationEnd(Animation animation) {
                AlivcShowMoreDialog.this.mIsAnimating = false;
                AlivcShowMoreDialog.this.mContentView.post(new Runnable() { // from class: com.aliyun.player.alivcplayerexpand.view.choice.AlivcShowMoreDialog.1.1
                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            AlivcShowMoreDialog.super.dismiss();
                        } catch (Exception e2) {
                            Log.w("Test", "dismiss error\n" + Log.getStackTraceString(e2));
                        }
                    }
                });
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationRepeat(Animation animation) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationStart(Animation animation) {
                AlivcShowMoreDialog.this.mIsAnimating = true;
            }
        });
        this.mContentView.startAnimation(animationSet);
    }

    private void animateUp() {
        if (this.mContentView != null) {
            return;
        }
        TranslateAnimation translateAnimation = new TranslateAnimation(1, 0.0f, 1, 0.0f, 1, 1.0f, 1, 0.0f);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        AnimationSet animationSet = new AnimationSet(true);
        animationSet.addAnimation(translateAnimation);
        animationSet.addAnimation(alphaAnimation);
        animationSet.setInterpolator(new DecelerateInterpolator());
        animationSet.setDuration(200L);
        animationSet.setFillAfter(true);
        this.mContentView.startAnimation(animationSet);
    }

    private void fullScreenImmersive(View view) {
        view.setSystemUiVisibility(R2.color.umeng_socialize_text_share_content);
    }

    @Override // android.app.Dialog, android.content.DialogInterface
    public void dismiss() {
        super.dismiss();
    }

    @Override // android.app.Dialog
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().getDecorView().setPadding(0, 0, 0, 0);
        setLayoutBySreenMode(this.screenMode);
    }

    @Override // android.app.Dialog
    public void setContentView(@NonNull View view) {
        this.mContentView = view;
        super.setContentView(view);
    }

    public void setLayoutBySreenMode(AliyunScreenMode aliyunScreenMode) {
        if (aliyunScreenMode == AliyunScreenMode.Small) {
            WindowManager.LayoutParams attributes = getWindow().getAttributes();
            attributes.height = -2;
            attributes.gravity = 81;
            int width = ScreenUtils.getWidth(getContext());
            int height = ScreenUtils.getHeight(getContext());
            if (width >= height) {
                width = height;
            }
            attributes.width = width;
            getWindow().setAttributes(attributes);
            setCanceledOnTouchOutside(true);
            return;
        }
        WindowManager.LayoutParams attributes2 = getWindow().getAttributes();
        attributes2.height = -1;
        attributes2.gravity = 5;
        int width2 = ScreenUtils.getWidth(getContext());
        int height2 = ScreenUtils.getHeight(getContext());
        if (width2 >= height2) {
            width2 = height2;
        }
        attributes2.width = width2;
        getWindow().setAttributes(attributes2);
        setCanceledOnTouchOutside(true);
    }

    @Override // android.app.Dialog
    public void show() {
        getWindow().setFlags(8, 8);
        super.show();
        fullScreenImmersive(getWindow().getDecorView());
        getWindow().clearFlags(8);
        animateUp();
    }

    public AlivcShowMoreDialog(Context context, AliyunScreenMode aliyunScreenMode) {
        super(context, R.style.addDownloadDialog);
        this.mIsAnimating = false;
        this.screenMode = aliyunScreenMode;
        this.activityWeakReference = new WeakReference<>(context);
    }
}
