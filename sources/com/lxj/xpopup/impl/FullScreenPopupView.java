package com.lxj.xpopup.impl;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import com.lxj.xpopup.R;
import com.lxj.xpopup.animator.PopupAnimator;
import com.lxj.xpopup.animator.TranslateAnimator;
import com.lxj.xpopup.core.BasePopupView;
import com.lxj.xpopup.core.PopupInfo;
import com.lxj.xpopup.enums.PopupAnimation;
import com.lxj.xpopup.util.XPopupUtils;

/* loaded from: classes4.dex */
public class FullScreenPopupView extends BasePopupView {
    public ArgbEvaluator argbEvaluator;
    protected View contentView;
    int currColor;
    protected FrameLayout fullPopupContainer;
    Paint paint;
    Rect shadowRect;

    public FullScreenPopupView(@NonNull Context context) {
        super(context);
        this.argbEvaluator = new ArgbEvaluator();
        this.paint = new Paint();
        this.currColor = 0;
        this.fullPopupContainer = (FrameLayout) findViewById(R.id.fullPopupContainer);
    }

    public void addInnerContent() {
        View viewInflate = LayoutInflater.from(getContext()).inflate(getImplLayoutId(), (ViewGroup) this.fullPopupContainer, false);
        this.contentView = viewInflate;
        this.fullPopupContainer.addView(viewInflate);
    }

    @Override // android.view.ViewGroup, android.view.View
    public void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        PopupInfo popupInfo = this.popupInfo;
        if (popupInfo == null || !popupInfo.hasStatusBarShadow.booleanValue()) {
            return;
        }
        this.paint.setColor(this.currColor);
        Rect rect = new Rect(0, 0, getMeasuredWidth(), XPopupUtils.getStatusBarHeight());
        this.shadowRect = rect;
        canvas.drawRect(rect, this.paint);
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void doDismissAnimation() {
        super.doDismissAnimation();
        doStatusBarColorTransform(false);
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void doShowAnimation() {
        super.doShowAnimation();
        doStatusBarColorTransform(true);
    }

    public void doStatusBarColorTransform(boolean z2) {
        PopupInfo popupInfo = this.popupInfo;
        if (popupInfo == null || !popupInfo.hasStatusBarShadow.booleanValue()) {
            return;
        }
        ArgbEvaluator argbEvaluator = this.argbEvaluator;
        Object[] objArr = new Object[2];
        objArr[0] = Integer.valueOf(z2 ? 0 : getStatusBarBgColor());
        objArr[1] = Integer.valueOf(z2 ? getStatusBarBgColor() : 0);
        ValueAnimator valueAnimatorOfObject = ValueAnimator.ofObject(argbEvaluator, objArr);
        valueAnimatorOfObject.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.lxj.xpopup.impl.FullScreenPopupView.1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                FullScreenPopupView.this.currColor = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                FullScreenPopupView.this.postInvalidate();
            }
        });
        valueAnimatorOfObject.setDuration(getAnimationDuration()).start();
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public int getInnerLayoutId() {
        return R.layout._xpopup_fullscreen_popup_view;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public PopupAnimator getPopupAnimator() {
        return new TranslateAnimator(getPopupContentView(), getAnimationDuration(), PopupAnimation.TranslateFromBottom);
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void initPopupContent() {
        super.initPopupContent();
        if (this.fullPopupContainer.getChildCount() == 0) {
            addInnerContent();
        }
        getPopupContentView().setTranslationX(this.popupInfo.offsetX);
        getPopupContentView().setTranslationY(this.popupInfo.offsetY);
    }

    @Override // com.lxj.xpopup.core.BasePopupView, android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        if (this.popupInfo != null) {
            getPopupContentView().setTranslationX(this.popupInfo.offsetX);
        }
        if (this.popupInfo != null) {
            getPopupContentView().setTranslationY(this.popupInfo.offsetY);
        }
        super.onDetachedFromWindow();
    }
}
