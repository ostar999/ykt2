package com.psychiatrygarden.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import com.beizi.fusion.NativeAd;
import com.beizi.fusion.NativeAdListener;
import com.beizi.fusion.work.splash.SplashContainer;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.utils.AndroidBaseUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.utils.LogUtils;
import com.psychiatrygarden.utils.PublicMethodActivity;
import com.yikaobang.yixue.R;
import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes6.dex */
public class QuestionAdWidegt extends LinearLayout {
    private int adHeight;
    private FrameLayout adLayout;
    private int adWidth;
    ImageView adimgs;
    private boolean isInit;
    ImageView iv_delete_ad;
    LinearLayout lineadview;
    private NativeAd mNativeAd;
    private View vLine;

    public interface OnRemoveListener {
        void onCloseAd(int pos);
    }

    public QuestionAdWidegt(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    private void init(AttributeSet attributeSet) {
        setOrientation(1);
        TypedArray typedArrayObtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, R.styleable.ad_config_attr);
        int i2 = getResources().getDisplayMetrics().widthPixels;
        int dimension = (int) typedArrayObtainStyledAttributes.getDimension(1, getResources().getDisplayMetrics().widthPixels);
        this.adWidth = dimension;
        if (dimension == i2) {
            this.adWidth = (int) ((i2 / getResources().getDisplayMetrics().density) + 0.5f);
        }
        this.adHeight = (int) typedArrayObtainStyledAttributes.getDimension(0, 0.0f);
        typedArrayObtainStyledAttributes.recycle();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$loadYkbAd$0(OnRemoveListener onRemoveListener, int i2, View view) {
        SharePreferencesUtils.writeLongConfig(CommonParameter.DISMESS_TIME_QUESTION_COMMENT_AD, Long.valueOf(System.currentTimeMillis()), getContext());
        if (onRemoveListener != null) {
            onRemoveListener.onCloseAd(i2);
        }
        getChildAt(0).setVisibility(8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$loadYkbAd$1(JSONObject jSONObject, View view) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        PublicMethodActivity.getInstance().mToActivity(jSONObject.optString(PushConstants.EXTRA));
    }

    /* JADX WARN: Removed duplicated region for block: B:29:0x00be  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void loadSdkAd(boolean r10) throws java.lang.InterruptedException {
        /*
            r9 = this;
            boolean r0 = r9.isInit
            r1 = 1098907648(0x41800000, float:16.0)
            if (r0 != 0) goto L51
            android.content.Context r0 = r9.getContext()
            r2 = 2131559967(0x7f0d061f, float:1.8745293E38)
            r3 = 0
            android.view.View r0 = android.view.View.inflate(r0, r2, r3)
            r2 = 2131364717(0x7f0a0b6d, float:1.8349279E38)
            android.view.View r2 = r0.findViewById(r2)
            android.widget.FrameLayout r2 = (android.widget.FrameLayout) r2
            r9.adLayout = r2
            r2 = 2131368970(0x7f0a1c0a, float:1.8357905E38)
            android.view.View r2 = r0.findViewById(r2)
            r9.vLine = r2
            r9.addView(r0)
            r0 = 1
            r9.isInit = r0
            if (r10 == 0) goto L51
            android.widget.FrameLayout r0 = r9.adLayout
            android.view.ViewGroup$LayoutParams r0 = r0.getLayoutParams()
            android.widget.LinearLayout$LayoutParams r0 = (android.widget.LinearLayout.LayoutParams) r0
            android.content.Context r2 = r9.getContext()
            r3 = 1109393408(0x42200000, float:40.0)
            int r2 = com.psychiatrygarden.utils.CommonUtil.dip2px(r2, r3)
            r0.leftMargin = r2
            android.content.Context r2 = r9.getContext()
            int r2 = com.psychiatrygarden.utils.CommonUtil.dip2px(r2, r1)
            r0.rightMargin = r2
            android.widget.FrameLayout r2 = r9.adLayout
            r2.setLayoutParams(r0)
        L51:
            com.beizi.fusion.NativeAd r0 = r9.mNativeAd
            if (r0 != 0) goto L6b
            com.beizi.fusion.NativeAd r0 = new com.beizi.fusion.NativeAd
            android.content.Context r3 = r9.getContext()
            java.lang.String r4 = "104836"
            com.psychiatrygarden.widget.QuestionAdWidegt$1 r5 = new com.psychiatrygarden.widget.QuestionAdWidegt$1
            r5.<init>()
            r6 = 5000(0x1388, double:2.4703E-320)
            r8 = 1
            r2 = r0
            r2.<init>(r3, r4, r5, r6, r8)
            r9.mNativeAd = r0
        L6b:
            android.content.Context r0 = r9.getContext()
            if (r0 == 0) goto Lcc
            android.view.ViewGroup$LayoutParams r0 = r9.getLayoutParams()
            boolean r2 = r0 instanceof android.widget.LinearLayout.LayoutParams
            r3 = 0
            if (r2 == 0) goto L83
            android.widget.LinearLayout$LayoutParams r0 = (android.widget.LinearLayout.LayoutParams) r0
            int r2 = r0.leftMargin
            int r2 = r2 + r3
            int r0 = r0.rightMargin
        L81:
            int r2 = r2 + r0
            goto La8
        L83:
            boolean r2 = r0 instanceof android.widget.RelativeLayout.LayoutParams
            if (r2 == 0) goto L8f
            android.widget.RelativeLayout$LayoutParams r0 = (android.widget.RelativeLayout.LayoutParams) r0
            int r2 = r0.leftMargin
            int r2 = r2 + r3
            int r0 = r0.rightMargin
            goto L81
        L8f:
            boolean r2 = r0 instanceof android.widget.FrameLayout.LayoutParams
            if (r2 == 0) goto L9b
            android.widget.FrameLayout$LayoutParams r0 = (android.widget.FrameLayout.LayoutParams) r0
            int r2 = r0.leftMargin
            int r2 = r2 + r3
            int r0 = r0.rightMargin
            goto L81
        L9b:
            boolean r2 = r0 instanceof androidx.constraintlayout.widget.ConstraintLayout.LayoutParams
            if (r2 == 0) goto La7
            androidx.constraintlayout.widget.ConstraintLayout$LayoutParams r0 = (androidx.constraintlayout.widget.ConstraintLayout.LayoutParams) r0
            int r2 = r0.leftMargin
            int r2 = r2 + r3
            int r0 = r0.rightMargin
            goto L81
        La7:
            r2 = r3
        La8:
            android.content.res.Resources r0 = r9.getResources()
            android.util.DisplayMetrics r0 = r0.getDisplayMetrics()
            float r0 = r0.density
            int r4 = r9.adWidth
            float r4 = (float) r4
            r5 = 1065353216(0x3f800000, float:1.0)
            float r4 = r4 * r5
            float r2 = (float) r2
            float r2 = r2 / r0
            float r4 = r4 - r2
            if (r10 != 0) goto Lbe
            goto Lc0
        Lbe:
            r3 = 56
        Lc0:
            float r10 = (float) r3
            float r4 = r4 - r10
            float r10 = r4 / r1
            r0 = 1091567616(0x41100000, float:9.0)
            float r10 = r10 * r0
            com.beizi.fusion.NativeAd r0 = r9.mNativeAd
            r0.loadAd(r4, r10)
        Lcc:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.psychiatrygarden.widget.QuestionAdWidegt.loadSdkAd(boolean):void");
    }

    public void loadYkbAd(String adContent, final int position, final OnRemoveListener listener) {
        if (!this.isInit) {
            View viewInflate = View.inflate(getContext(), R.layout.layout_question_platform_ad, null);
            this.iv_delete_ad = (ImageView) viewInflate.findViewById(R.id.iv_delete_ad);
            this.lineadview = (LinearLayout) viewInflate.findViewById(R.id.lineadview);
            this.adimgs = (ImageView) viewInflate.findViewById(R.id.adimgs);
            addView(viewInflate);
            this.isInit = true;
        }
        if (adContent == null) {
            setVisibility(8);
            return;
        }
        try {
            final JSONObject jSONObject = new JSONObject(adContent);
            if (jSONObject.optString("force").equals("1")) {
                this.iv_delete_ad.setVisibility(8);
            } else {
                this.iv_delete_ad.setVisibility(0);
                this.iv_delete_ad.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.bf
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f16347c.lambda$loadYkbAd$0(listener, position, view);
                    }
                });
            }
            setVisibility(0);
            this.lineadview.setVisibility(0);
            Glide.with(getContext()).asBitmap().load((Object) GlideUtils.generateUrl(jSONObject.optString("img"))).into((RequestBuilder<Bitmap>) new SimpleTarget<Bitmap>() { // from class: com.psychiatrygarden.widget.QuestionAdWidegt.2
                @Override // com.bumptech.glide.request.target.Target
                public /* bridge */ /* synthetic */ void onResourceReady(@NonNull @NotNull Object resource, @Nullable Transition transition) {
                    onResourceReady((Bitmap) resource, (Transition<? super Bitmap>) transition);
                }

                public void onResourceReady(@NonNull @NotNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                    double dDip2px = QuestionAdWidegt.this.getContext().getResources().getDisplayMetrics().widthPixels - CommonUtil.dip2px(QuestionAdWidegt.this.getContext(), 73.0f);
                    if (AndroidBaseUtils.isPad(QuestionAdWidegt.this.getContext()) && AndroidBaseUtils.isCurOriLand(QuestionAdWidegt.this.getContext())) {
                        dDip2px /= 3.0d;
                    }
                    LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) QuestionAdWidegt.this.adimgs.getLayoutParams();
                    layoutParams.width = (int) dDip2px;
                    layoutParams.height = (int) (resource.getHeight() * (dDip2px / resource.getWidth()));
                    layoutParams.rightMargin = CommonUtil.dip2px(QuestionAdWidegt.this.getContext(), 20.0f);
                    layoutParams.leftMargin = CommonUtil.dip2px(QuestionAdWidegt.this.getContext(), 33.0f);
                    QuestionAdWidegt.this.adimgs.setLayoutParams(layoutParams);
                    QuestionAdWidegt.this.adimgs.setImageBitmap(resource);
                }
            });
            this.adimgs.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.cf
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    QuestionAdWidegt.lambda$loadYkbAd$1(jSONObject, view);
                }
            });
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    public void onDestory() {
        NativeAd nativeAd = this.mNativeAd;
        if (nativeAd != null) {
            nativeAd.destroy();
        }
    }

    public void onResume() {
        NativeAd nativeAd = this.mNativeAd;
        if (nativeAd != null) {
            nativeAd.resume();
        }
    }

    public QuestionAdWidegt(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    /* renamed from: com.psychiatrygarden.widget.QuestionAdWidegt$1, reason: invalid class name */
    public class AnonymousClass1 implements NativeAdListener {
        public AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onAdLoaded$0(ValueAnimator valueAnimator) {
            ViewGroup.LayoutParams layoutParams = QuestionAdWidegt.this.getLayoutParams();
            layoutParams.height = ((Integer) valueAnimator.getAnimatedValue()).intValue();
            QuestionAdWidegt.this.setLayoutParams(layoutParams);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onAdLoaded$1(ValueAnimator valueAnimator) {
            QuestionAdWidegt.this.setAlpha(((Float) valueAnimator.getAnimatedValue()).floatValue());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onAdLoaded$2(View view) {
            ValueAnimator valueAnimatorOfInt = ValueAnimator.ofInt(QuestionAdWidegt.this.getHeight(), 0);
            ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(1.0f, 0.5f, 0.0f);
            valueAnimatorOfInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.psychiatrygarden.widget.ef
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    this.f16442c.lambda$onAdLoaded$0(valueAnimator);
                }
            });
            valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.psychiatrygarden.widget.ff
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    this.f16490c.lambda$onAdLoaded$1(valueAnimator);
                }
            });
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.setDuration(200L);
            animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
            animatorSet.playTogether(valueAnimatorOfInt, valueAnimatorOfFloat);
            animatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.psychiatrygarden.widget.QuestionAdWidegt.1.1
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    QuestionAdWidegt.this.setVisibility(8);
                    QuestionAdWidegt.this.removeAllViews();
                    if (QuestionAdWidegt.this.mNativeAd != null) {
                        QuestionAdWidegt.this.mNativeAd.destroy();
                        QuestionAdWidegt.this.mNativeAd = null;
                    }
                }
            });
            animatorSet.start();
        }

        @Override // com.beizi.fusion.NativeAdListener
        public void onAdClick() {
            LogUtils.e("NativeAd", "onAdClick");
        }

        @Override // com.beizi.fusion.NativeAdListener
        public void onAdClosed() {
            if (QuestionAdWidegt.this.adLayout != null && QuestionAdWidegt.this.adLayout.getChildCount() > 0) {
                QuestionAdWidegt.this.adLayout.removeAllViews();
            }
            QuestionAdWidegt.this.removeAllViews();
            if (QuestionAdWidegt.this.mNativeAd != null) {
                QuestionAdWidegt.this.mNativeAd.destroy();
            }
            LogUtils.e("NativeAd", "onAdClosed");
        }

        @Override // com.beizi.fusion.NativeAdListener
        public void onAdFailed(int i2) {
            LogUtils.e("NativeAd", "onAdFailed=" + i2);
            QuestionAdWidegt.this.setVisibility(8);
        }

        @Override // com.beizi.fusion.NativeAdListener
        public void onAdLoaded(View view) {
            Drawable drawable;
            if (view instanceof SplashContainer) {
                SplashContainer splashContainer = (SplashContainer) view;
                int childCount = splashContainer.getChildCount();
                int i2 = 0;
                while (true) {
                    if (i2 >= childCount) {
                        break;
                    }
                    View childAt = splashContainer.getChildAt(i2);
                    if ((childAt instanceof ImageView) && (drawable = ((ImageView) childAt).getDrawable()) != null && (drawable instanceof VectorDrawable)) {
                        VectorDrawable vectorDrawable = (VectorDrawable) drawable;
                        int intrinsicWidth = vectorDrawable.getIntrinsicWidth();
                        int intrinsicHeight = vectorDrawable.getIntrinsicHeight();
                        if (intrinsicHeight > 0 && intrinsicWidth == intrinsicHeight && intrinsicHeight <= 100) {
                            splashContainer.removeView(childAt);
                            break;
                        }
                    }
                    i2++;
                }
            }
            QuestionAdWidegt.this.adLayout.removeAllViews();
            QuestionAdWidegt.this.adLayout.addView(view);
            LogUtils.d("NativeAd", "onAdLoaded");
            QuestionAdWidegt.this.vLine.setVisibility(8);
            QuestionAdWidegt.this.adLayout.setVisibility(0);
            QuestionAdWidegt.this.setVisibility(0);
            ImageView imageView = new ImageView(QuestionAdWidegt.this.getContext());
            imageView.setImageResource(R.drawable.icon_ad_close);
            imageView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.df
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    this.f16409c.lambda$onAdLoaded$2(view2);
                }
            });
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-2, -2);
            float f2 = (30 / QuestionAdWidegt.this.getResources().getDisplayMetrics().density) + 0.5f;
            layoutParams.gravity = GravityCompat.END;
            int i3 = (int) f2;
            layoutParams.topMargin = i3;
            layoutParams.rightMargin = i3;
            QuestionAdWidegt.this.adLayout.addView(imageView, layoutParams);
        }

        @Override // com.beizi.fusion.NativeAdListener
        public void onAdShown() {
            LogUtils.e("NativeAd", "onAdShown");
        }

        @Override // com.beizi.fusion.NativeAdListener
        public void onAdClosed(View view) {
            LogUtils.e("NativeAd", "onAdClosed view ");
            QuestionAdWidegt.this.adLayout.removeView(view);
            QuestionAdWidegt.this.vLine.setVisibility(8);
            QuestionAdWidegt.this.adLayout.setVisibility(8);
            if (QuestionAdWidegt.this.mNativeAd != null) {
                QuestionAdWidegt.this.mNativeAd.destroy();
            }
            QuestionAdWidegt.this.mNativeAd = null;
            QuestionAdWidegt.this.removeAllViews();
        }
    }
}
