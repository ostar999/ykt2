package com.lxj.xpopup.core;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import com.lxj.xpopup.R;
import com.lxj.xpopup.animator.BlurAnimator;
import com.lxj.xpopup.animator.PopupAnimator;
import com.lxj.xpopup.enums.PopupStatus;
import com.lxj.xpopup.interfaces.XPopupCallback;
import com.lxj.xpopup.util.KeyboardUtils;
import com.lxj.xpopup.util.XPopupUtils;
import com.lxj.xpopup.widget.SmartDragLayout;

/* loaded from: classes4.dex */
public class BottomPopupView extends BasePopupView {
    protected SmartDragLayout bottomPopupContainer;

    public BottomPopupView(@NonNull Context context) {
        super(context);
        this.bottomPopupContainer = (SmartDragLayout) findViewById(R.id.bottomPopupContainer);
    }

    public void addInnerContent() {
        this.bottomPopupContainer.addView(LayoutInflater.from(getContext()).inflate(getImplLayoutId(), (ViewGroup) this.bottomPopupContainer, false));
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
        this.bottomPopupContainer.close();
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
        BlurAnimator blurAnimator;
        if (this.popupInfo.hasBlurBg.booleanValue() && (blurAnimator = this.blurAnimator) != null) {
            blurAnimator.animateDismiss();
        }
        this.bottomPopupContainer.close();
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void doShowAnimation() {
        BlurAnimator blurAnimator;
        if (this.popupInfo.hasBlurBg.booleanValue() && (blurAnimator = this.blurAnimator) != null) {
            blurAnimator.animateShow();
        }
        this.bottomPopupContainer.open();
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return 0;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public int getInnerLayoutId() {
        return R.layout._xpopup_bottom_popup_view;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public int getMaxWidth() {
        int i2 = this.popupInfo.maxWidth;
        return i2 == 0 ? XPopupUtils.getWindowWidth(getContext()) : i2;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public PopupAnimator getPopupAnimator() {
        return null;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void initPopupContent() {
        super.initPopupContent();
        if (this.bottomPopupContainer.getChildCount() == 0) {
            addInnerContent();
        }
        this.bottomPopupContainer.setDuration(getAnimationDuration());
        this.bottomPopupContainer.enableDrag(this.popupInfo.enableDrag.booleanValue());
        this.bottomPopupContainer.dismissOnTouchOutside(this.popupInfo.isDismissOnTouchOutside.booleanValue());
        this.bottomPopupContainer.isThreeDrag(this.popupInfo.isThreeDrag);
        getPopupImplView().setTranslationX(this.popupInfo.offsetX);
        getPopupImplView().setTranslationY(this.popupInfo.offsetY);
        XPopupUtils.applyPopupSize((ViewGroup) getPopupContentView(), getMaxWidth(), getMaxHeight(), getPopupWidth(), getPopupHeight(), null);
        this.bottomPopupContainer.setOnCloseListener(new SmartDragLayout.OnCloseListener() { // from class: com.lxj.xpopup.core.BottomPopupView.1
            @Override // com.lxj.xpopup.widget.SmartDragLayout.OnCloseListener
            public void onClose() {
                XPopupCallback xPopupCallback;
                BottomPopupView.this.beforeDismiss();
                BottomPopupView bottomPopupView = BottomPopupView.this;
                PopupInfo popupInfo = bottomPopupView.popupInfo;
                if (popupInfo != null && (xPopupCallback = popupInfo.xPopupCallback) != null) {
                    xPopupCallback.beforeDismiss(bottomPopupView);
                }
                BottomPopupView.this.doAfterDismiss();
            }

            @Override // com.lxj.xpopup.widget.SmartDragLayout.OnCloseListener
            public void onDrag(int i2, float f2, boolean z2) {
                BottomPopupView bottomPopupView = BottomPopupView.this;
                PopupInfo popupInfo = bottomPopupView.popupInfo;
                if (popupInfo == null) {
                    return;
                }
                XPopupCallback xPopupCallback = popupInfo.xPopupCallback;
                if (xPopupCallback != null) {
                    xPopupCallback.onDrag(bottomPopupView, i2, f2, z2);
                }
                if (!BottomPopupView.this.popupInfo.hasShadowBg.booleanValue() || BottomPopupView.this.popupInfo.hasBlurBg.booleanValue()) {
                    return;
                }
                BottomPopupView bottomPopupView2 = BottomPopupView.this;
                bottomPopupView2.setBackgroundColor(bottomPopupView2.shadowBgAnimator.calculateBgColor(f2));
            }

            @Override // com.lxj.xpopup.widget.SmartDragLayout.OnCloseListener
            public void onOpen() {
            }
        });
        this.bottomPopupContainer.setOnClickListener(new View.OnClickListener() { // from class: com.lxj.xpopup.core.BottomPopupView.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                BottomPopupView.this.dismiss();
            }
        });
    }
}
