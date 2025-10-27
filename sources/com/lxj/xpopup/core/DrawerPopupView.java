package com.lxj.xpopup.core;

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
import com.lxj.xpopup.enums.PopupPosition;
import com.lxj.xpopup.enums.PopupStatus;
import com.lxj.xpopup.interfaces.XPopupCallback;
import com.lxj.xpopup.util.KeyboardUtils;
import com.lxj.xpopup.util.XPopupUtils;
import com.lxj.xpopup.widget.PopupDrawerLayout;

/* loaded from: classes4.dex */
public abstract class DrawerPopupView extends BasePopupView {
    public ArgbEvaluator argbEvaluator;
    int currColor;
    int defaultColor;
    protected FrameLayout drawerContentContainer;
    protected PopupDrawerLayout drawerLayout;
    float mFraction;
    Paint paint;
    Rect shadowRect;

    public DrawerPopupView(@NonNull Context context) {
        super(context);
        this.mFraction = 0.0f;
        this.paint = new Paint();
        this.argbEvaluator = new ArgbEvaluator();
        this.currColor = 0;
        this.defaultColor = 0;
        this.drawerLayout = (PopupDrawerLayout) findViewById(R.id.drawerLayout);
        this.drawerContentContainer = (FrameLayout) findViewById(R.id.drawerContentContainer);
        this.drawerContentContainer.addView(LayoutInflater.from(getContext()).inflate(getImplLayoutId(), (ViewGroup) this.drawerContentContainer, false));
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void dismiss() {
        PopupInfo popupInfo = this.popupInfo;
        if (popupInfo == null) {
            return;
        }
        PopupStatus popupStatus = this.popupStatus;
        PopupStatus popupStatus2 = PopupStatus.Dismissing;
        if (popupStatus == popupStatus2) {
            return;
        }
        this.popupStatus = popupStatus2;
        if (popupInfo.autoOpenSoftInput.booleanValue()) {
            KeyboardUtils.hideSoftInput(this);
        }
        clearFocus();
        doStatusBarColorTransform(false);
        this.drawerLayout.close();
    }

    @Override // android.view.ViewGroup, android.view.View
    public void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        PopupInfo popupInfo = this.popupInfo;
        if (popupInfo == null || !popupInfo.hasStatusBarShadow.booleanValue()) {
            return;
        }
        if (this.shadowRect == null) {
            this.shadowRect = new Rect(0, 0, getMeasuredWidth(), XPopupUtils.getStatusBarHeight());
        }
        this.paint.setColor(((Integer) this.argbEvaluator.evaluate(this.mFraction, Integer.valueOf(this.defaultColor), Integer.valueOf(getStatusBarBgColor()))).intValue());
        canvas.drawRect(this.shadowRect, this.paint);
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void doAfterDismiss() {
        PopupInfo popupInfo = this.popupInfo;
        if (popupInfo != null && popupInfo.autoOpenSoftInput.booleanValue()) {
            KeyboardUtils.hideSoftInput(this);
        }
        this.handler.removeCallbacks(this.doAfterDismissTask);
        this.handler.postDelayed(this.doAfterDismissTask, 0L);
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void doDismissAnimation() {
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void doShowAnimation() {
        this.drawerLayout.open();
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
        valueAnimatorOfObject.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.lxj.xpopup.core.DrawerPopupView.2
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                DrawerPopupView.this.currColor = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                DrawerPopupView.this.postInvalidate();
            }
        });
        valueAnimatorOfObject.setDuration(getAnimationDuration()).start();
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public int getInnerLayoutId() {
        return R.layout._xpopup_drawer_popup_view;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public PopupAnimator getPopupAnimator() {
        return null;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public View getPopupImplView() {
        return this.drawerContentContainer.getChildAt(0);
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void initPopupContent() {
        super.initPopupContent();
        this.drawerLayout.isDismissOnTouchOutside = this.popupInfo.isDismissOnTouchOutside.booleanValue();
        this.drawerLayout.setOnCloseListener(new PopupDrawerLayout.OnCloseListener() { // from class: com.lxj.xpopup.core.DrawerPopupView.1
            @Override // com.lxj.xpopup.widget.PopupDrawerLayout.OnCloseListener
            public void onClose() {
                XPopupCallback xPopupCallback;
                DrawerPopupView.this.beforeDismiss();
                DrawerPopupView drawerPopupView = DrawerPopupView.this;
                PopupInfo popupInfo = drawerPopupView.popupInfo;
                if (popupInfo != null && (xPopupCallback = popupInfo.xPopupCallback) != null) {
                    xPopupCallback.beforeDismiss(drawerPopupView);
                }
                DrawerPopupView.this.doAfterDismiss();
            }

            @Override // com.lxj.xpopup.widget.PopupDrawerLayout.OnCloseListener
            public void onDrag(int i2, float f2, boolean z2) {
                DrawerPopupView drawerPopupView = DrawerPopupView.this;
                PopupInfo popupInfo = drawerPopupView.popupInfo;
                if (popupInfo == null) {
                    return;
                }
                drawerPopupView.drawerLayout.isDrawStatusBarShadow = popupInfo.hasStatusBarShadow.booleanValue();
                DrawerPopupView drawerPopupView2 = DrawerPopupView.this;
                XPopupCallback xPopupCallback = drawerPopupView2.popupInfo.xPopupCallback;
                if (xPopupCallback != null) {
                    xPopupCallback.onDrag(drawerPopupView2, i2, f2, z2);
                }
                DrawerPopupView drawerPopupView3 = DrawerPopupView.this;
                drawerPopupView3.mFraction = f2;
                drawerPopupView3.shadowBgAnimator.applyColorValue(f2);
                DrawerPopupView.this.postInvalidate();
            }

            @Override // com.lxj.xpopup.widget.PopupDrawerLayout.OnCloseListener
            public void onOpen() {
            }
        });
        getPopupImplView().setTranslationX(this.popupInfo.offsetX);
        getPopupImplView().setTranslationY(this.popupInfo.offsetY);
        PopupDrawerLayout popupDrawerLayout = this.drawerLayout;
        PopupPosition popupPosition = this.popupInfo.popupPosition;
        if (popupPosition == null) {
            popupPosition = PopupPosition.Left;
        }
        popupDrawerLayout.setDrawerPosition(popupPosition);
        this.drawerLayout.enableDrag = this.popupInfo.enableDrag.booleanValue();
    }
}
