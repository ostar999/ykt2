package com.easefun.polyv.livecommon.module.modules.reward.view.effect;

import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import com.easefun.polyv.livecommon.R;
import com.easefun.polyv.livecommon.module.modules.reward.view.effect.IPLVPointRewardEventProducer;
import com.easefun.polyv.livecommon.module.utils.imageloader.PLVImageLoader;
import com.google.android.exoplayer2.ExoPlayer;
import com.plv.foundationsdk.rx.PLVRxTimer;
import com.plv.socket.event.chat.PLVRewardEvent;
import com.plv.thirdpart.blankj.utilcode.util.ScreenUtils;
import io.reactivex.functions.Consumer;

/* loaded from: classes3.dex */
public class PLVPointRewardEffectWidget extends FrameLayout {
    private boolean isFetchingBottom;
    private boolean isLandscape;
    private boolean isRelease;
    private IPLVPointRewardEventProducer.OnPreparedListener onPreparedListener;
    private ImageView plvIvPointRewardEffect1;
    private ImageView plvIvPointRewardEffect2;
    private LinearLayout plvLlPointRewardCount1;
    private LinearLayout plvLlPointRewardCount2;
    private PLVPointRewardStrokeTextView plvTvPointRewardEffectCount1;
    private PLVPointRewardStrokeTextView plvTvPointRewardEffectCount2;
    private TextView plvTvPointRewardEffectNickname1;
    private TextView plvTvPointRewardEffectNickname2;
    private View plvVPointRewardEffectBg1;
    private View plvVPointRewardEffectBg2;
    private IPLVPointRewardEventProducer pointRewardEventProducer;
    private RelativeLayout rlPointRewardEffectBottom;
    private RelativeLayout rlPointRewardEffectTop;
    private TextView tvPointRewardEffectRewardContent1;
    private TextView tvPointRewardEffectRewardContent2;

    /* renamed from: com.easefun.polyv.livecommon.module.modules.reward.view.effect.PLVPointRewardEffectWidget$2, reason: invalid class name */
    public class AnonymousClass2 implements IPLVPointRewardEventProducer.IPLVOnFetchRewardEventListener {
        public AnonymousClass2() {
        }

        @Override // com.easefun.polyv.livecommon.module.modules.reward.view.effect.IPLVPointRewardEventProducer.IPLVOnFetchRewardEventListener
        public void onFetchSucceed(PLVRewardEvent event) {
            if (PLVPointRewardEffectWidget.this.isLandscape) {
                return;
            }
            PLVPointRewardEffectWidget pLVPointRewardEffectWidget = PLVPointRewardEffectWidget.this;
            pLVPointRewardEffectWidget.handleEvent(event, pLVPointRewardEffectWidget.plvTvPointRewardEffectNickname1, PLVPointRewardEffectWidget.this.plvIvPointRewardEffect1, PLVPointRewardEffectWidget.this.plvLlPointRewardCount1, PLVPointRewardEffectWidget.this.plvTvPointRewardEffectCount1, PLVPointRewardEffectWidget.this.tvPointRewardEffectRewardContent1);
            PLVPointRewardEffectWidget pLVPointRewardEffectWidget2 = PLVPointRewardEffectWidget.this;
            pLVPointRewardEffectWidget2.makeOnceAnim(pLVPointRewardEffectWidget2.rlPointRewardEffectTop, PLVPointRewardEffectWidget.this.plvLlPointRewardCount1, new Runnable() { // from class: com.easefun.polyv.livecommon.module.modules.reward.view.effect.PLVPointRewardEffectWidget.2.1
                @Override // java.lang.Runnable
                public void run() {
                    if (!PLVPointRewardEffectWidget.this.isFetchingBottom) {
                        PLVPointRewardEffectWidget.this.fetchEventForTopItem();
                        return;
                    }
                    PLVPointRewardEffectWidget.this.pointRewardEventProducer.destroy();
                    PLVPointRewardEffectWidget.this.pointRewardEventProducer.prepare(PLVPointRewardEffectWidget.this.onPreparedListener = new IPLVPointRewardEventProducer.OnPreparedListener() { // from class: com.easefun.polyv.livecommon.module.modules.reward.view.effect.PLVPointRewardEffectWidget.2.1.1
                        @Override // com.easefun.polyv.livecommon.module.modules.reward.view.effect.IPLVPointRewardEventProducer.OnPreparedListener
                        public void onPrepared() {
                            PLVPointRewardEffectWidget.this.fetchEventForTopItem();
                            PLVPointRewardEffectWidget.this.fetchEventForBottomItem();
                        }
                    });
                }
            });
        }
    }

    /* renamed from: com.easefun.polyv.livecommon.module.modules.reward.view.effect.PLVPointRewardEffectWidget$4, reason: invalid class name */
    public class AnonymousClass4 implements Animation.AnimationListener {
        final /* synthetic */ Runnable val$animEnd;
        final /* synthetic */ RelativeLayout val$itemView;
        final /* synthetic */ View val$zoomView;

        public AnonymousClass4(final RelativeLayout val$itemView, final Runnable val$animEnd, final View val$zoomView) {
            this.val$itemView = val$itemView;
            this.val$animEnd = val$animEnd;
            this.val$zoomView = val$zoomView;
        }

        @Override // android.view.animation.Animation.AnimationListener
        public void onAnimationEnd(Animation animation) {
            final boolean z2 = PLVPointRewardEffectWidget.this.isRelease;
            PLVRxTimer.delay(ExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS, new Consumer<Object>() { // from class: com.easefun.polyv.livecommon.module.modules.reward.view.effect.PLVPointRewardEffectWidget.4.1
                @Override // io.reactivex.functions.Consumer
                public void accept(Object o2) throws Exception {
                    final boolean z3 = z2 || PLVPointRewardEffectWidget.this.isRelease;
                    TranslateAnimation translateAnimation = new TranslateAnimation(1, 0.0f, 1, 0.0f, 1, 0.0f, 1, -1.0f);
                    translateAnimation.setDuration(100L);
                    translateAnimation.setAnimationListener(new Animation.AnimationListener() { // from class: com.easefun.polyv.livecommon.module.modules.reward.view.effect.PLVPointRewardEffectWidget.4.1.1
                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationEnd(Animation animation2) {
                            AnonymousClass4.this.val$itemView.setVisibility(4);
                            AnonymousClass4.this.val$animEnd.run();
                        }

                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationRepeat(Animation animation2) {
                        }

                        @Override // android.view.animation.Animation.AnimationListener
                        public void onAnimationStart(Animation animation2) {
                            if (z3) {
                                AnonymousClass4.this.val$itemView.setVisibility(4);
                                animation2.cancel();
                                AnonymousClass4.this.val$itemView.clearAnimation();
                            }
                        }
                    });
                    if (z3) {
                        AnonymousClass4.this.val$itemView.setVisibility(4);
                    } else {
                        AnonymousClass4.this.val$itemView.startAnimation(translateAnimation);
                    }
                }
            });
            if (this.val$zoomView.isShown()) {
                ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 1.5f, 1.0f, 1.5f, 1, 0.5f, 1, 0.5f);
                scaleAnimation.setFillAfter(true);
                scaleAnimation.setDuration(100L);
                scaleAnimation.setAnimationListener(new Animation.AnimationListener() { // from class: com.easefun.polyv.livecommon.module.modules.reward.view.effect.PLVPointRewardEffectWidget.4.2
                    @Override // android.view.animation.Animation.AnimationListener
                    public void onAnimationEnd(Animation animation2) {
                        PLVRxTimer.delay(50L, new Consumer<Object>() { // from class: com.easefun.polyv.livecommon.module.modules.reward.view.effect.PLVPointRewardEffectWidget.4.2.1
                            @Override // io.reactivex.functions.Consumer
                            public void accept(Object o2) throws Exception {
                                ScaleAnimation scaleAnimation2 = new ScaleAnimation(1.5f, 1.0f, 1.5f, 1.0f, 1, 0.5f, 1, 0.5f);
                                scaleAnimation2.setDuration(150L);
                                AnonymousClass4.this.val$zoomView.startAnimation(scaleAnimation2);
                            }
                        });
                    }

                    @Override // android.view.animation.Animation.AnimationListener
                    public void onAnimationRepeat(Animation animation2) {
                    }

                    @Override // android.view.animation.Animation.AnimationListener
                    public void onAnimationStart(Animation animation2) {
                    }
                });
                this.val$zoomView.startAnimation(scaleAnimation);
            }
        }

        @Override // android.view.animation.Animation.AnimationListener
        public void onAnimationRepeat(Animation animation) {
        }

        @Override // android.view.animation.Animation.AnimationListener
        public void onAnimationStart(Animation animation) {
            this.val$itemView.setVisibility(0);
        }
    }

    public PLVPointRewardEffectWidget(Context context) {
        this(context, null);
    }

    private void clearEffectAnim() {
        this.rlPointRewardEffectTop.clearAnimation();
        this.rlPointRewardEffectTop.setVisibility(8);
        this.rlPointRewardEffectBottom.clearAnimation();
        this.rlPointRewardEffectBottom.setVisibility(8);
        this.plvLlPointRewardCount1.clearAnimation();
        this.plvLlPointRewardCount2.clearAnimation();
    }

    private void destroyEventProducer() {
        IPLVPointRewardEventProducer iPLVPointRewardEventProducer = this.pointRewardEventProducer;
        if (iPLVPointRewardEventProducer != null) {
            iPLVPointRewardEventProducer.destroy();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void fetchEventForBottomItem() {
        this.isFetchingBottom = true;
        this.pointRewardEventProducer.fetchEvent(new IPLVPointRewardEventProducer.IPLVOnFetchRewardEventListener() { // from class: com.easefun.polyv.livecommon.module.modules.reward.view.effect.PLVPointRewardEffectWidget.3
            @Override // com.easefun.polyv.livecommon.module.modules.reward.view.effect.IPLVPointRewardEventProducer.IPLVOnFetchRewardEventListener
            public void onFetchSucceed(PLVRewardEvent event) {
                PLVPointRewardEffectWidget.this.isFetchingBottom = false;
                if (PLVPointRewardEffectWidget.this.isLandscape) {
                    return;
                }
                PLVPointRewardEffectWidget pLVPointRewardEffectWidget = PLVPointRewardEffectWidget.this;
                pLVPointRewardEffectWidget.handleEvent(event, pLVPointRewardEffectWidget.plvTvPointRewardEffectNickname2, PLVPointRewardEffectWidget.this.plvIvPointRewardEffect2, PLVPointRewardEffectWidget.this.plvLlPointRewardCount2, PLVPointRewardEffectWidget.this.plvTvPointRewardEffectCount2, PLVPointRewardEffectWidget.this.tvPointRewardEffectRewardContent2);
                PLVPointRewardEffectWidget pLVPointRewardEffectWidget2 = PLVPointRewardEffectWidget.this;
                pLVPointRewardEffectWidget2.makeOnceAnim(pLVPointRewardEffectWidget2.rlPointRewardEffectBottom, PLVPointRewardEffectWidget.this.plvLlPointRewardCount2, new Runnable() { // from class: com.easefun.polyv.livecommon.module.modules.reward.view.effect.PLVPointRewardEffectWidget.3.1
                    @Override // java.lang.Runnable
                    public void run() {
                        PLVPointRewardEffectWidget.this.fetchEventForBottomItem();
                    }
                });
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void fetchEventForTopItem() {
        this.pointRewardEventProducer.fetchEvent(new AnonymousClass2());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleEvent(PLVRewardEvent event, TextView tvNickName, ImageView ivGoodImage, LinearLayout llGoodCountParent, TextView tvGoodCount, TextView tvGoodContent) {
        String gimg = event.getContent().getGimg();
        event.getContent().getUimg();
        int goodNum = event.getContent().getGoodNum();
        String rewardContent = event.getContent().getRewardContent();
        tvNickName.setText(event.getContent().getUnick());
        loadImage(gimg, ivGoodImage);
        if (goodNum > 1) {
            llGoodCountParent.setVisibility(0);
            tvGoodCount.setText(String.valueOf(goodNum));
        } else {
            llGoodCountParent.setVisibility(4);
        }
        tvGoodContent.setText("赠送   " + rewardContent);
    }

    private void init() {
        initView();
        this.isLandscape = ScreenUtils.isLandscape();
    }

    private void initView() {
        this.rlPointRewardEffectTop = (RelativeLayout) findViewById(R.id.rl_point_reward_effect_top);
        this.rlPointRewardEffectBottom = (RelativeLayout) findViewById(R.id.rl_point_reward_effect_bottom);
        this.plvVPointRewardEffectBg1 = findViewById(R.id.plv_v_point_reward_effect_bg_1);
        this.plvTvPointRewardEffectNickname1 = (TextView) findViewById(R.id.plv_tv_point_reward_effect_nickname_1);
        this.plvIvPointRewardEffect1 = (ImageView) findViewById(R.id.plv_iv_point_reward_effect_1);
        this.tvPointRewardEffectRewardContent1 = (TextView) findViewById(R.id.tv_point_reward_effect_reward_content_1);
        this.plvTvPointRewardEffectCount1 = (PLVPointRewardStrokeTextView) findViewById(R.id.plv_tv_point_reward_effect_count_1);
        this.plvLlPointRewardCount1 = (LinearLayout) findViewById(R.id.plv_ll_point_reward_count_1);
        this.plvVPointRewardEffectBg2 = findViewById(R.id.plv_v_point_reward_effect_bg_2);
        this.plvTvPointRewardEffectNickname2 = (TextView) findViewById(R.id.plv_tv_point_reward_effect_nickname_2);
        this.plvIvPointRewardEffect2 = (ImageView) findViewById(R.id.plv_iv_point_reward_effect_2);
        this.tvPointRewardEffectRewardContent2 = (TextView) findViewById(R.id.tv_point_reward_effect_reward_content_2);
        this.plvTvPointRewardEffectCount2 = (PLVPointRewardStrokeTextView) findViewById(R.id.plv_tv_point_reward_effect_count_2);
        this.plvLlPointRewardCount2 = (LinearLayout) findViewById(R.id.plv_ll_point_reward_count_2);
    }

    private void loadImage(String url, ImageView iv) {
        if (!url.startsWith("http")) {
            url = "https:/" + url;
        }
        PLVImageLoader.getInstance().loadImage(iv.getContext(), url, iv);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void makeOnceAnim(final RelativeLayout itemView, final View zoomView, final Runnable animEnd) {
        TranslateAnimation translateAnimation = new TranslateAnimation(1, -1.0f, 1, 0.0f, 1, 0.0f, 1, 0.0f);
        translateAnimation.setDuration(150L);
        translateAnimation.setAnimationListener(new AnonymousClass4(itemView, animEnd, zoomView));
        itemView.startAnimation(translateAnimation);
    }

    private void prepareEventProducer() {
        IPLVPointRewardEventProducer iPLVPointRewardEventProducer = this.pointRewardEventProducer;
        if (iPLVPointRewardEventProducer == null) {
            return;
        }
        IPLVPointRewardEventProducer.OnPreparedListener onPreparedListener = new IPLVPointRewardEventProducer.OnPreparedListener() { // from class: com.easefun.polyv.livecommon.module.modules.reward.view.effect.PLVPointRewardEffectWidget.1
            @Override // com.easefun.polyv.livecommon.module.modules.reward.view.effect.IPLVPointRewardEventProducer.OnPreparedListener
            public void onPrepared() {
                PLVPointRewardEffectWidget.this.fetchEventForTopItem();
                PLVPointRewardEffectWidget.this.fetchEventForBottomItem();
            }
        };
        this.onPreparedListener = onPreparedListener;
        iPLVPointRewardEventProducer.prepare(onPreparedListener);
    }

    public void hideAndReleaseEffect() {
        this.isRelease = true;
        clearEffectAnim();
        setVisibility(4);
        destroyEventProducer();
    }

    @Override // android.view.View
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        boolean z2 = newConfig.orientation == 2;
        this.isLandscape = z2;
        if (z2) {
            hideAndReleaseEffect();
        } else {
            showAndPrepareEffect();
        }
    }

    public void setEventProducer(IPLVPointRewardEventProducer producer) {
        this.pointRewardEventProducer = producer;
        prepareEventProducer();
    }

    public void showAndPrepareEffect() {
        this.isRelease = false;
        setVisibility(0);
        prepareEventProducer();
    }

    public PLVPointRewardEffectWidget(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PLVPointRewardEffectWidget(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.isLandscape = false;
        this.isRelease = false;
        LayoutInflater.from(context).inflate(R.layout.plv_point_reward_effect, (ViewGroup) this, true);
        init();
    }
}
