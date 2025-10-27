package com.easefun.polyv.livecloudclass.modules.chatroom.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationSet;
import android.view.animation.CycleInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import androidx.annotation.Nullable;
import com.easefun.polyv.livecloudclass.R;
import com.easefun.polyv.livecommon.module.utils.PLVBezierEvaluator;
import com.easefun.polyv.livecommon.ui.widget.imageview.IPLVVisibilityChangedListener;
import com.google.android.exoplayer2.ExoPlayer;
import com.plv.foundationsdk.utils.PLVScreenUtils;
import com.plv.thirdpart.blankj.utilcode.util.ConvertUtils;
import java.lang.ref.WeakReference;
import java.util.Random;

/* loaded from: classes3.dex */
public class PLVLCLikeIconView extends RelativeLayout {
    private int height;
    private int iconHeight;
    private int iconWidth;
    private int[] imageId;
    private Interpolator[] interpolators;
    private FrameLayout loveIconContainer;
    private View.OnClickListener onButtonClickListener;
    private Random random;
    private Random randomColor;
    private int srcWH;
    private IPLVVisibilityChangedListener visibilityChangedListener;
    private int width;

    public static class AnimatorListener extends AnimatorListenerAdapter {
        private WeakReference<View> iv;
        private WeakReference<ViewGroup> parent;

        public AnimatorListener(View view, ViewGroup viewGroup) {
            this.iv = new WeakReference<>(view);
            this.parent = new WeakReference<>(viewGroup);
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            View view = this.iv.get();
            ViewGroup viewGroup = this.parent.get();
            if (view == null || viewGroup == null) {
                return;
            }
            viewGroup.removeView(view);
        }
    }

    public static class Const {
        static final float BEAT_ZOOM_RATIO = 1.3f;
        static final float BG_RATIO = 1.6f;
        static final int BOTTOM_MARGIN_DP = 14;
        static final int DURATION_FLY_LOVE_ICON = 2000;
        static final int OFFSET_OF_HEART = 5;
        static final int RIGHT_MARGIN_DP = 14;
    }

    public static class UpdateListener implements ValueAnimator.AnimatorUpdateListener {
        private WeakReference<View> iv;

        public UpdateListener(View view) {
            this.iv = new WeakReference<>(view);
        }

        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            PointF pointF = (PointF) valueAnimator.getAnimatedValue();
            View view = this.iv.get();
            if (view != null) {
                view.setX(pointF.x);
                view.setY(pointF.y);
                view.setAlpha((1.0f - valueAnimator.getAnimatedFraction()) + 0.1f);
                view.setScaleX(valueAnimator.getAnimatedFraction() + 0.5f);
                view.setScaleY(valueAnimator.getAnimatedFraction() + 0.5f);
            }
        }
    }

    public PLVLCLikeIconView(Context context) {
        this(context, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public AnimationSet createClickAnimation() {
        CycleInterpolator cycleInterpolator = new CycleInterpolator(1.0f);
        RotateAnimation rotateAnimation = new RotateAnimation(0.0f, -30.0f, 1, 0.5f, 1, 0.5f);
        ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 1.2f, 1.0f, 1.2f, 1, 0.5f, 1, 0.5f);
        AnimationSet animationSet = new AnimationSet(true);
        animationSet.setDuration(200L);
        animationSet.setInterpolator(cycleInterpolator);
        animationSet.addAnimation(rotateAnimation);
        animationSet.addAnimation(scaleAnimation);
        return animationSet;
    }

    private void init() {
        initInterpolator();
        initChild();
    }

    private void initChild() {
        this.loveIconContainer = new FrameLayout(getContext());
        this.loveIconContainer.setLayoutParams(new RelativeLayout.LayoutParams(-1, -1));
        final View view = new View(getContext());
        view.setBackgroundResource(R.drawable.plvlc_chatroom_btn_like);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(i, i);
        layoutParams.addRule(12);
        layoutParams.addRule(11);
        layoutParams.rightMargin = PLVScreenUtils.dip2px(getContext(), 16.0f);
        view.setLayoutParams(layoutParams);
        view.setOnClickListener(new View.OnClickListener() { // from class: com.easefun.polyv.livecloudclass.modules.chatroom.widget.PLVLCLikeIconView.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                if (PLVLCLikeIconView.this.onButtonClickListener != null) {
                    PLVLCLikeIconView.this.onButtonClickListener.onClick(PLVLCLikeIconView.this);
                }
                view.startAnimation(PLVLCLikeIconView.this.createClickAnimation());
            }
        });
        addView(view);
        addView(this.loveIconContainer);
    }

    private void initInterpolator() {
        this.interpolators = new Interpolator[]{new LinearInterpolator()};
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startAnimator(ImageView imageView) {
        if (this.height <= 0 || this.width <= 0) {
            return;
        }
        ValueAnimator valueAnimatorOfObject = ValueAnimator.ofObject(new PLVBezierEvaluator(new PointF(this.random.nextInt(Math.max(2, this.width - ConvertUtils.dp2px(16.0f))) - ConvertUtils.dp2px(6.0f), this.random.nextInt(this.height / 2)), new PointF(this.random.nextInt(Math.max(2, this.width - ConvertUtils.dp2px(16.0f))) - ConvertUtils.dp2px(6.0f), this.random.nextInt(this.height / 2))), new PointF((this.width - this.iconWidth) / 2.0f, this.height - this.srcWH), new PointF(this.random.nextInt(Math.max(2, this.width - ConvertUtils.dp2px(16.0f))) - ConvertUtils.dp2px(6.0f), 0.0f));
        valueAnimatorOfObject.setTarget(imageView);
        valueAnimatorOfObject.setDuration(ExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS);
        valueAnimatorOfObject.addUpdateListener(new UpdateListener(imageView));
        valueAnimatorOfObject.addListener(new AnimatorListener(imageView, (ViewGroup) imageView.getParent()));
        Interpolator[] interpolatorArr = this.interpolators;
        valueAnimatorOfObject.setInterpolator(interpolatorArr[this.random.nextInt(interpolatorArr.length)]);
        valueAnimatorOfObject.start();
    }

    public void addLoveIcon(int i2) {
        if (this.height <= 0 || this.width <= 0) {
            return;
        }
        post(new Runnable() { // from class: com.easefun.polyv.livecloudclass.modules.chatroom.widget.PLVLCLikeIconView.2
            @Override // java.lang.Runnable
            public void run() {
                ImageView imageView = new ImageView(PLVLCLikeIconView.this.getContext());
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                imageView.setImageResource(PLVLCLikeIconView.this.imageId[PLVLCLikeIconView.this.random.nextInt(PLVLCLikeIconView.this.imageId.length)]);
                int iNextInt = PLVLCLikeIconView.this.random.nextInt(4) + 7;
                PLVLCLikeIconView.this.iconWidth = (imageView.getDrawable().getIntrinsicWidth() * iNextInt) / 10;
                PLVLCLikeIconView.this.iconHeight = (imageView.getDrawable().getIntrinsicHeight() * iNextInt) / 10;
                imageView.setLayoutParams(new RelativeLayout.LayoutParams(PLVLCLikeIconView.this.iconWidth, PLVLCLikeIconView.this.iconHeight));
                PLVLCLikeIconView.this.addView(imageView, 0);
                PLVLCLikeIconView.this.startAnimator(imageView);
            }
        });
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        removeAllViews();
        super.onDetachedFromWindow();
    }

    @Override // android.widget.RelativeLayout, android.view.View
    public void onMeasure(int i2, int i3) {
        super.onMeasure(i2, i3);
        this.width = getMeasuredWidth();
        this.height = getMeasuredHeight();
    }

    public void setOnButtonClickListener(@Nullable View.OnClickListener onClickListener) {
        this.onButtonClickListener = onClickListener;
    }

    @Override // android.view.View
    public void setVisibility(int i2) {
        super.setVisibility(i2);
        IPLVVisibilityChangedListener iPLVVisibilityChangedListener = this.visibilityChangedListener;
        if (iPLVVisibilityChangedListener != null) {
            iPLVVisibilityChangedListener.onChanged(i2);
        }
    }

    public void setVisibilityChangedListener(IPLVVisibilityChangedListener iPLVVisibilityChangedListener) {
        this.visibilityChangedListener = iPLVVisibilityChangedListener;
    }

    public PLVLCLikeIconView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public PLVLCLikeIconView(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.random = new Random();
        this.imageId = new int[]{R.drawable.plvlc_chatroom_btn_like_1, R.drawable.plvlc_chatroom_btn_like_2, R.drawable.plvlc_chatroom_btn_like_3, R.drawable.plvlc_chatroom_btn_like_4, R.drawable.plvlc_chatroom_btn_like_5, R.drawable.plvlc_chatroom_btn_like_6, R.drawable.plvlc_chatroom_btn_like_7, R.drawable.plvlc_chatroom_btn_like_8, R.drawable.plvlc_chatroom_btn_like_9, R.drawable.plvlc_chatroom_btn_like_10};
        this.randomColor = new Random();
        TypedArray typedArrayObtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, R.styleable.PLVLCLikeIconView, i2, 0);
        this.srcWH = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.PLVLCLikeIconView_src_wh, ConvertUtils.dp2px(46.0f));
        typedArrayObtainStyledAttributes.recycle();
        init();
    }
}
