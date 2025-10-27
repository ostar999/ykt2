package com.easefun.polyv.livecommon.module.modules.marquee;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;
import androidx.annotation.Nullable;
import com.easefun.polyv.livecommon.module.modules.marquee.animation.PLVMarqueeAnimation;
import com.easefun.polyv.livecommon.module.modules.marquee.animation.PLVMarqueeFlick15PercentAnimation;
import com.easefun.polyv.livecommon.module.modules.marquee.animation.PLVMarqueeFlickAdvanceAnimation;
import com.easefun.polyv.livecommon.module.modules.marquee.animation.PLVMarqueeFlickAnimation;
import com.easefun.polyv.livecommon.module.modules.marquee.animation.PLVMarqueeMergeRollFlickAnimation;
import com.easefun.polyv.livecommon.module.modules.marquee.animation.PLVMarqueeRoll15PercentAnimation;
import com.easefun.polyv.livecommon.module.modules.marquee.animation.PLVMarqueeRollAdvanceAnimation;
import com.easefun.polyv.livecommon.module.modules.marquee.animation.PLVMarqueeRollAnimation;
import com.easefun.polyv.livecommon.module.modules.marquee.model.PLVMarqueeAnimationVO;
import com.easefun.polyv.livecommon.module.modules.marquee.model.PLVMarqueeModel;
import com.easefun.polyv.livecommon.module.modules.marquee.model.PLVMarqueeTextVO;
import com.plv.foundationsdk.log.PLVCommonLog;
import java.util.HashMap;

/* loaded from: classes3.dex */
public class PLVMarqueeView extends RelativeLayout implements IPLVMarqueeView {
    private static final String TAG = "PLVMarqueeView";
    private boolean isResetMarqueeVO;
    private PLVMarqueeTextView mainTextView;
    private PLVMarqueeAnimation marqueeAnimation;
    private PLVMarqueeModel plvMarqueeModel;
    private PLVMarqueeTextView secondTextView;

    public PLVMarqueeView(Context context) {
        super(context);
        this.isResetMarqueeVO = false;
        Log.i(TAG, "PLVMarqueeView: ");
        initView(context);
    }

    private void initView(Context context) {
        this.mainTextView = new PLVMarqueeTextView(context);
        this.secondTextView = new PLVMarqueeTextView(context);
        addView(this.mainTextView);
        addView(this.secondTextView);
        this.secondTextView.setAlpha(0.0f);
    }

    private void setMainTextVO(PLVMarqueeTextVO textVO) {
        this.mainTextView.setMarqueeTextModel(textVO);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setMarqueeAnimationType(PLVMarqueeAnimationVO pLVMarqueeAnimationVO) {
        this.mainTextView.clearAnimation();
        this.secondTextView.clearAnimation();
        int animationType = pLVMarqueeAnimationVO.getAnimationType();
        int measuredHeight = this.mainTextView.getMeasuredHeight() + (this.mainTextView.getStrokeWidth() * 2);
        int measuredWidth = this.mainTextView.getMeasuredWidth() + (this.mainTextView.getStrokeWidth() * 2);
        int measuredHeight2 = this.secondTextView.getMeasuredHeight() + (this.secondTextView.getStrokeWidth() * 2);
        int measuredWidth2 = this.secondTextView.getMeasuredWidth() + (this.secondTextView.getStrokeWidth() * 2);
        int width = getWidth();
        int height = getHeight();
        int speed = (pLVMarqueeAnimationVO.getSpeed() / 10) * 1000;
        int lifeTime = pLVMarqueeAnimationVO.getLifeTime() * 1000;
        int interval = pLVMarqueeAnimationVO.getInterval() * 1000;
        int tweenTime = pLVMarqueeAnimationVO.getTweenTime() * 1000;
        boolean zIsAlwaysShowWhenRun = pLVMarqueeAnimationVO.isAlwaysShowWhenRun();
        boolean zIsHiddenWhenPause = pLVMarqueeAnimationVO.isHiddenWhenPause();
        HashMap<Integer, Integer> map = new HashMap<>();
        HashMap<Integer, View> map2 = new HashMap<>();
        switch (animationType) {
            case 1:
                this.marqueeAnimation = new PLVMarqueeRollAnimation();
                map2.put(20, this.mainTextView);
                map.put(0, Integer.valueOf(measuredWidth));
                map.put(1, Integer.valueOf(measuredHeight));
                map.put(2, Integer.valueOf(width));
                map.put(3, Integer.valueOf(height));
                map.put(4, Integer.valueOf(speed));
                map.put(7, Integer.valueOf(interval));
                map.put(10, Integer.valueOf(zIsAlwaysShowWhenRun ? 1 : 0));
                map.put(11, Integer.valueOf(zIsHiddenWhenPause ? 1 : 0));
                break;
            case 2:
                this.marqueeAnimation = new PLVMarqueeFlickAnimation();
                map2.put(20, this.mainTextView);
                map.put(0, Integer.valueOf(measuredWidth));
                map.put(1, Integer.valueOf(measuredHeight));
                map.put(2, Integer.valueOf(width));
                map.put(3, Integer.valueOf(height));
                map.put(5, Integer.valueOf(lifeTime));
                map.put(7, Integer.valueOf(interval));
                map.put(6, Integer.valueOf(tweenTime));
                map.put(10, Integer.valueOf(zIsAlwaysShowWhenRun ? 1 : 0));
                map.put(11, Integer.valueOf(zIsHiddenWhenPause ? 1 : 0));
                break;
            case 3:
                this.marqueeAnimation = new PLVMarqueeMergeRollFlickAnimation();
                map2.put(20, this.mainTextView);
                map.put(0, Integer.valueOf(measuredWidth));
                map.put(1, Integer.valueOf(measuredHeight));
                map.put(2, Integer.valueOf(width));
                map.put(3, Integer.valueOf(height));
                map.put(4, Integer.valueOf(speed));
                map.put(7, Integer.valueOf(interval));
                map.put(6, Integer.valueOf(tweenTime));
                map.put(10, Integer.valueOf(zIsAlwaysShowWhenRun ? 1 : 0));
                map.put(11, Integer.valueOf(zIsHiddenWhenPause ? 1 : 0));
                break;
            case 4:
                this.marqueeAnimation = new PLVMarqueeRoll15PercentAnimation();
                map2.put(20, this.mainTextView);
                map.put(0, Integer.valueOf(measuredWidth));
                map.put(1, Integer.valueOf(measuredHeight));
                map.put(2, Integer.valueOf(width));
                map.put(3, Integer.valueOf(height));
                map.put(4, Integer.valueOf(speed));
                map.put(7, Integer.valueOf(interval));
                map.put(10, Integer.valueOf(zIsAlwaysShowWhenRun ? 1 : 0));
                map.put(11, Integer.valueOf(zIsHiddenWhenPause ? 1 : 0));
                break;
            case 5:
                this.marqueeAnimation = new PLVMarqueeFlick15PercentAnimation();
                map2.put(20, this.mainTextView);
                map.put(0, Integer.valueOf(measuredWidth));
                map.put(1, Integer.valueOf(measuredHeight));
                map.put(2, Integer.valueOf(width));
                map.put(3, Integer.valueOf(height));
                map.put(5, Integer.valueOf(lifeTime));
                map.put(7, Integer.valueOf(interval));
                map.put(6, Integer.valueOf(tweenTime));
                map.put(10, Integer.valueOf(zIsAlwaysShowWhenRun ? 1 : 0));
                map.put(11, Integer.valueOf(zIsHiddenWhenPause ? 1 : 0));
                break;
            case 6:
                this.marqueeAnimation = new PLVMarqueeRollAdvanceAnimation();
                map2.put(20, this.mainTextView);
                map2.put(21, this.secondTextView);
                map.put(0, Integer.valueOf(measuredWidth));
                map.put(1, Integer.valueOf(measuredHeight));
                map.put(2, Integer.valueOf(width));
                map.put(3, Integer.valueOf(height));
                map.put(4, Integer.valueOf(speed));
                map.put(7, Integer.valueOf(interval));
                map.put(8, Integer.valueOf(measuredWidth2));
                map.put(9, Integer.valueOf(measuredHeight2));
                map.put(10, Integer.valueOf(zIsAlwaysShowWhenRun ? 1 : 0));
                map.put(11, Integer.valueOf(zIsHiddenWhenPause ? 1 : 0));
                break;
            case 7:
                this.marqueeAnimation = new PLVMarqueeFlickAdvanceAnimation();
                map2.put(20, this.mainTextView);
                map2.put(21, this.secondTextView);
                map.put(0, Integer.valueOf(measuredWidth));
                map.put(1, Integer.valueOf(measuredHeight));
                map.put(2, Integer.valueOf(width));
                map.put(3, Integer.valueOf(height));
                map.put(5, Integer.valueOf(lifeTime));
                map.put(7, Integer.valueOf(interval));
                map.put(6, Integer.valueOf(tweenTime));
                map.put(8, Integer.valueOf(measuredWidth2));
                map.put(9, Integer.valueOf(measuredHeight2));
                map.put(10, Integer.valueOf(zIsAlwaysShowWhenRun ? 1 : 0));
                map.put(11, Integer.valueOf(zIsHiddenWhenPause ? 1 : 0));
                break;
        }
        PLVMarqueeAnimation pLVMarqueeAnimation = this.marqueeAnimation;
        if (pLVMarqueeAnimation == null) {
            this.mainTextView.setVisibility(8);
            this.secondTextView.setVisibility(8);
            return;
        }
        pLVMarqueeAnimation.setViews(map2);
        this.marqueeAnimation.setParams(map);
        PLVMarqueeAnimation pLVMarqueeAnimation2 = this.marqueeAnimation;
        if (pLVMarqueeAnimation2 != null) {
            pLVMarqueeAnimation2.start();
        }
    }

    private void setSecondTextVO(PLVMarqueeTextVO textVO) {
        this.secondTextView.setMarqueeTextModel(textVO);
    }

    private void updatePLVMarqueeModel(final PLVMarqueeModel plvMarqueeVO) {
        plvMarqueeVO.setSecondDefaultTextVO();
        setMainTextVO(plvMarqueeVO.getMainTextVO());
        setSecondTextVO(plvMarqueeVO.getSecondTextVO());
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.easefun.polyv.livecommon.module.modules.marquee.PLVMarqueeView.1
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public void onGlobalLayout() {
                PLVMarqueeView.this.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                PLVMarqueeView.this.mainTextView.setWidth(PLVMarqueeView.this.mainTextView.getWidth() + (PLVMarqueeView.this.mainTextView.getStrokeWidth() * 2));
                PLVMarqueeView.this.mainTextView.setHeight(PLVMarqueeView.this.mainTextView.getHeight() + (PLVMarqueeView.this.mainTextView.getStrokeWidth() * 2));
                PLVMarqueeView.this.secondTextView.setWidth(PLVMarqueeView.this.secondTextView.getWidth() + (PLVMarqueeView.this.secondTextView.getStrokeWidth() * 2));
                PLVMarqueeView.this.secondTextView.setHeight(PLVMarqueeView.this.secondTextView.getHeight() + (PLVMarqueeView.this.secondTextView.getStrokeWidth() * 2));
                PLVMarqueeView.this.setMarqueeAnimationType(plvMarqueeVO.getAnimationVO());
            }
        });
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        PLVMarqueeAnimation pLVMarqueeAnimation = this.marqueeAnimation;
        if (pLVMarqueeAnimation != null) {
            pLVMarqueeAnimation.stop();
            this.marqueeAnimation.destroy();
        }
    }

    @Override // android.view.View
    public void onSizeChanged(int w2, int h2, int oldw, int oldh) {
        super.onSizeChanged(w2, h2, oldw, oldh);
        PLVMarqueeAnimation pLVMarqueeAnimation = this.marqueeAnimation;
        if (pLVMarqueeAnimation != null) {
            pLVMarqueeAnimation.onParentSizeChanged(this);
        }
    }

    @Override // com.easefun.polyv.livecommon.module.modules.marquee.IPLVMarqueeView
    public void pause() {
        PLVMarqueeAnimation pLVMarqueeAnimation = this.marqueeAnimation;
        if (pLVMarqueeAnimation != null) {
            pLVMarqueeAnimation.pause();
        }
    }

    @Override // com.easefun.polyv.livecommon.module.modules.marquee.IPLVMarqueeView
    public void setPLVMarqueeModel(final PLVMarqueeModel marqueeVO) {
        this.plvMarqueeModel = marqueeVO;
        this.isResetMarqueeVO = true;
    }

    @Override // com.easefun.polyv.livecommon.module.modules.marquee.IPLVMarqueeView
    public void start() {
        if (this.isResetMarqueeVO) {
            updatePLVMarqueeModel(this.plvMarqueeModel);
            this.isResetMarqueeVO = false;
            return;
        }
        PLVMarqueeAnimation pLVMarqueeAnimation = this.marqueeAnimation;
        if (pLVMarqueeAnimation != null) {
            pLVMarqueeAnimation.start();
        } else {
            PLVCommonLog.d(TAG, "need to excute setPLVMarqueeModel before start");
        }
    }

    @Override // com.easefun.polyv.livecommon.module.modules.marquee.IPLVMarqueeView
    public void stop() {
        PLVMarqueeAnimation pLVMarqueeAnimation = this.marqueeAnimation;
        if (pLVMarqueeAnimation != null) {
            pLVMarqueeAnimation.stop();
        }
    }

    public PLVMarqueeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.isResetMarqueeVO = false;
        initView(context);
    }

    public PLVMarqueeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.isResetMarqueeVO = false;
        initView(context);
    }
}
