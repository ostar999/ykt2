package com.aliyun.player.alivcplayerexpand.view.function;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.aliyun.player.alivcplayerexpand.R;
import com.aliyun.player.aliyunplayerbase.util.AliyunScreenMode;
import com.aliyun.player.aliyunplayerbase.util.ScreenUtils;
import com.google.android.exoplayer2.ExoPlayer;
import java.lang.ref.WeakReference;

/* loaded from: classes2.dex */
public class MarqueeView extends FrameLayout {
    private static final int PAUSE = 2;
    private static final int START = 1;
    private static final int STOP = 3;
    private boolean isAnimStart;
    private boolean isStart;
    private TextView mContentTextView;
    private Context mContext;
    private int mCurrentState;
    private String mFlipText;
    private int mInterval;
    private MarQueeHandler mMarQueeHandler;
    private RelativeLayout mMarqueeRootRelativeLayout;
    private AliyunScreenMode mScreenMode;
    private int mTextColor;
    private int mTextSize;
    private ObjectAnimator objectAnimator;
    private View view;

    public static class MarQueeHandler extends Handler {
        private WeakReference<MarqueeView> weakReference;

        public MarQueeHandler(MarqueeView marqueeView) {
            this.weakReference = new WeakReference<>(marqueeView);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            MarqueeView marqueeView;
            if (message.what == 1 && (marqueeView = this.weakReference.get()) != null) {
                if (marqueeView.mScreenMode == AliyunScreenMode.Small) {
                    marqueeView.stopFlip();
                } else {
                    if (marqueeView.isAnimStart || marqueeView.mCurrentState != 1) {
                        return;
                    }
                    marqueeView.objectAnimator.start();
                }
            }
        }
    }

    public enum MarqueeRegion {
        TOP,
        MIDDLE,
        BOTTOM
    }

    public MarqueeView(Context context) {
        super(context);
        this.mCurrentState = -1;
        this.mInterval = 5000;
        this.mTextSize = 14;
        this.mTextColor = getResources().getColor(R.color.alivc_common_font_white_light);
        this.mFlipText = getResources().getString(R.string.alivc_marquee_test);
        this.isStart = false;
        this.isAnimStart = false;
        this.mScreenMode = AliyunScreenMode.Small;
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        this.view = LayoutInflater.from(context).inflate(R.layout.alivc_marquee_view, this);
        initView();
        initHandler();
    }

    private void initHandler() {
        this.mMarQueeHandler = new MarQueeHandler(this);
    }

    private void initView() {
        this.mContentTextView = (TextView) this.view.findViewById(R.id.tv_content);
        this.mMarqueeRootRelativeLayout = (RelativeLayout) this.view.findViewById(R.id.marquee_root);
        this.mContentTextView.setText(this.mFlipText);
    }

    public void createAnimation() {
        int measuredWidth = this.mContentTextView.getMeasuredWidth();
        int width = ScreenUtils.getWidth(getContext());
        if (this.objectAnimator == null) {
            ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(this.mContentTextView, "translationX", measuredWidth, -width);
            this.objectAnimator = objectAnimatorOfFloat;
            objectAnimatorOfFloat.setDuration(this.mInterval);
            this.objectAnimator.addListener(new Animator.AnimatorListener() { // from class: com.aliyun.player.alivcplayerexpand.view.function.MarqueeView.1
                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationCancel(Animator animator) {
                    MarqueeView.this.isAnimStart = false;
                    if (MarqueeView.this.mMarqueeRootRelativeLayout != null) {
                        MarqueeView.this.mMarqueeRootRelativeLayout.setVisibility(4);
                    }
                }

                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    if (MarqueeView.this.mMarqueeRootRelativeLayout != null) {
                        MarqueeView.this.mMarqueeRootRelativeLayout.setVisibility(4);
                    }
                    MarqueeView.this.isAnimStart = false;
                    MarqueeView.this.mMarQueeHandler.sendEmptyMessageDelayed(1, ExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS);
                }

                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationRepeat(Animator animator) {
                }

                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationStart(Animator animator) {
                    if (MarqueeView.this.mMarqueeRootRelativeLayout != null) {
                        MarqueeView.this.mMarqueeRootRelativeLayout.setVisibility(0);
                    }
                    MarqueeView.this.isAnimStart = true;
                }
            });
        }
    }

    public boolean isStart() {
        return this.isStart;
    }

    public void pause() {
        this.mCurrentState = 2;
        this.mMarqueeRootRelativeLayout.setVisibility(4);
        MarQueeHandler marQueeHandler = this.mMarQueeHandler;
        if (marQueeHandler != null) {
            marQueeHandler.removeCallbacksAndMessages(null);
        }
    }

    public void setInterval(int i2) {
        if (i2 < 5000) {
            i2 = 5000;
        }
        this.mInterval = i2;
        this.objectAnimator.setDuration(i2);
    }

    public void setScreenMode(AliyunScreenMode aliyunScreenMode) {
        this.mScreenMode = aliyunScreenMode;
    }

    public void setText(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        this.mFlipText = str;
        this.mContentTextView.setText(str);
    }

    public void setTextColor(int i2) {
        this.mTextColor = i2;
        this.mContentTextView.setTextColor(i2);
    }

    public void setTextSize(int i2) {
        this.mTextSize = i2;
        this.mContentTextView.setText(i2);
    }

    public void startFlip() {
        if (this.mScreenMode == AliyunScreenMode.Small) {
            return;
        }
        this.mCurrentState = 1;
        this.mMarqueeRootRelativeLayout.setVisibility(0);
        this.isStart = true;
        MarQueeHandler marQueeHandler = this.mMarQueeHandler;
        if (marQueeHandler != null) {
            marQueeHandler.sendEmptyMessage(1);
        }
    }

    public void stopFlip() {
        this.mCurrentState = 3;
        this.mMarqueeRootRelativeLayout.setVisibility(4);
        this.isStart = false;
        MarQueeHandler marQueeHandler = this.mMarQueeHandler;
        if (marQueeHandler != null) {
            marQueeHandler.removeCallbacksAndMessages(null);
        }
    }

    public MarqueeView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mCurrentState = -1;
        this.mInterval = 5000;
        this.mTextSize = 14;
        this.mTextColor = getResources().getColor(R.color.alivc_common_font_white_light);
        this.mFlipText = getResources().getString(R.string.alivc_marquee_test);
        this.isStart = false;
        this.isAnimStart = false;
        this.mScreenMode = AliyunScreenMode.Small;
        init(context);
    }
}
