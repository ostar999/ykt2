package com.psychiatrygarden.aliPlayer.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.OvershootInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.yikaobang.yixue.R;
import java.lang.ref.WeakReference;

/* loaded from: classes5.dex */
public class VideoBuyView extends RelativeLayout implements View.OnClickListener {
    private static final int DELAY_TIME = 5000;
    private static final int WHAT_HIDE = 1;
    public TextView back_tv;
    public TextView buy_text;
    public LinearLayout llay_buy_course;
    public LinearLayout llay_renew;
    private HideHandler mHideHandler;
    public RelativeLayout rl_pay_view;
    public TextView tv_buy_course;
    public TextView tv_free_play;
    public TextView tv_renew;
    public TextView tv_renew_day;
    public ViewChildChilckIml viewChildChilckIml;

    public static class HideHandler extends Handler {
        private WeakReference<VideoBuyView> videoBuyViewWeakReference;

        public HideHandler(VideoBuyView videoBuyView) {
            this.videoBuyViewWeakReference = new WeakReference<>(videoBuyView);
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            VideoBuyView videoBuyView = this.videoBuyViewWeakReference.get();
            if (videoBuyView != null) {
                videoBuyView.hideThreeView();
            }
            super.handleMessage(msg);
        }
    }

    public interface ViewChildChilckIml {
        void mBtnBackView();

        void mChilckOpenView();
    }

    public enum VipStatus {
        one,
        two,
        three
    }

    public VideoBuyView(Context context) {
        super(context);
        this.mHideHandler = new HideHandler(this);
        initView();
    }

    public void hide() {
        setVisibility(8);
    }

    public void hideThreeView() {
        LinearLayout linearLayout = this.llay_renew;
        if (linearLayout != null) {
            linearLayout.animate().alpha(1.0f).alpha(0.0f).setInterpolator(new OvershootInterpolator()).setDuration(1500L).setUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.psychiatrygarden.aliPlayer.widget.VideoBuyView.2
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    if (valueAnimator.getCurrentPlayTime() > valueAnimator.getDuration()) {
                        valueAnimator.cancel();
                    }
                }
            }).start();
        }
    }

    public void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_video_buy_view, (ViewGroup) this, true);
        this.llay_buy_course = (LinearLayout) findViewById(R.id.llay_buy_course);
        this.tv_free_play = (TextView) findViewById(R.id.tv_free_play);
        this.tv_buy_course = (TextView) findViewById(R.id.tv_buy_course);
        this.rl_pay_view = (RelativeLayout) findViewById(R.id.rl_pay_view);
        this.back_tv = (TextView) findViewById(R.id.back_tv);
        this.buy_text = (TextView) findViewById(R.id.buy_text);
        this.llay_renew = (LinearLayout) findViewById(R.id.llay_renew);
        this.tv_renew_day = (TextView) findViewById(R.id.tv_renew_day);
        this.tv_renew = (TextView) findViewById(R.id.tv_renew);
        this.rl_pay_view.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.aliPlayer.widget.r
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15289c.onClick(view);
            }
        });
        this.back_tv.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.aliPlayer.widget.r
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15289c.onClick(view);
            }
        });
        this.tv_buy_course.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.aliPlayer.widget.r
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15289c.onClick(view);
            }
        });
        this.buy_text.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.aliPlayer.widget.r
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15289c.onClick(view);
            }
        });
        this.tv_renew.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.aliPlayer.widget.r
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15289c.onClick(view);
            }
        });
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_tv /* 2131362155 */:
                this.viewChildChilckIml.mBtnBackView();
                break;
            case R.id.buy_text /* 2131362443 */:
            case R.id.tv_buy_course /* 2131367765 */:
            case R.id.tv_renew /* 2131368481 */:
                this.viewChildChilckIml.mChilckOpenView();
                break;
        }
    }

    public void setViewChildChilckIml(ViewChildChilckIml viewChildChilckIml) {
        this.viewChildChilckIml = viewChildChilckIml;
    }

    public void show(VipStatus vipStatus) {
        setVisibility(0);
        showViewSpan(vipStatus);
    }

    public void showViewSpan(VipStatus vipStatus) {
        if (vipStatus != VipStatus.one) {
            if (vipStatus == VipStatus.two) {
                this.llay_buy_course.setVisibility(8);
                this.rl_pay_view.setVisibility(0);
                this.llay_renew.setVisibility(8);
                return;
            } else {
                this.llay_buy_course.setVisibility(8);
                this.rl_pay_view.setVisibility(8);
                this.llay_renew.setVisibility(0);
                return;
            }
        }
        if (this.llay_buy_course.getVisibility() == 8) {
            final TranslateAnimation translateAnimation = new TranslateAnimation(1, 1.0f, 1, 0.0f, 1, 0.0f, 1, 0.0f);
            translateAnimation.setDuration(1500L);
            translateAnimation.setInterpolator(AnimationUtils.loadInterpolator(getContext(), android.R.anim.overshoot_interpolator));
            translateAnimation.setAnimationListener(new Animation.AnimationListener() { // from class: com.psychiatrygarden.aliPlayer.widget.VideoBuyView.1
                @Override // android.view.animation.Animation.AnimationListener
                public void onAnimationEnd(Animation animation) {
                    TranslateAnimation translateAnimation2 = translateAnimation;
                    if (translateAnimation2 != null) {
                        translateAnimation2.cancel();
                    }
                    LinearLayout linearLayout = VideoBuyView.this.llay_buy_course;
                    if (linearLayout != null) {
                        linearLayout.clearAnimation();
                    }
                }

                @Override // android.view.animation.Animation.AnimationListener
                public void onAnimationRepeat(Animation animation) {
                }

                @Override // android.view.animation.Animation.AnimationListener
                public void onAnimationStart(Animation animation) {
                }
            });
            this.llay_buy_course.startAnimation(translateAnimation);
        }
        this.llay_buy_course.setVisibility(0);
        this.rl_pay_view.setVisibility(8);
        this.llay_renew.setVisibility(8);
    }

    public void updateOneData(long timer) {
        TextView textView = this.tv_free_play;
        if (textView != null) {
            textView.setText("您可以试看" + (timer / 1000) + "s");
        }
    }

    public void updateThreeData(String expire_str) {
        if (this.tv_renew_day != null) {
            if (TextUtils.equals(expire_str, "0")) {
                this.tv_renew_day.setText("您的观看权限不足一天");
            } else {
                this.tv_renew_day.setText("您的观看权限不足" + expire_str + "天");
            }
            this.mHideHandler.removeMessages(1);
            this.mHideHandler.sendEmptyMessageDelayed(1, 5000L);
        }
    }

    public VideoBuyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mHideHandler = new HideHandler(this);
        initView();
    }

    public VideoBuyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mHideHandler = new HideHandler(this);
        initView();
    }
}
