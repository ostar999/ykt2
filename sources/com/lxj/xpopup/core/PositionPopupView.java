package com.lxj.xpopup.core;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import com.lxj.xpopup.R;
import com.lxj.xpopup.animator.PopupAnimator;
import com.lxj.xpopup.animator.ScaleAlphaAnimator;
import com.lxj.xpopup.enums.PopupAnimation;
import com.lxj.xpopup.util.XPopupUtils;

/* loaded from: classes4.dex */
public class PositionPopupView extends BasePopupView {
    FrameLayout positionPopupContainer;

    public PositionPopupView(@NonNull Context context) {
        super(context);
        this.positionPopupContainer = (FrameLayout) findViewById(R.id.positionPopupContainer);
        this.positionPopupContainer.addView(LayoutInflater.from(getContext()).inflate(getImplLayoutId(), (ViewGroup) this.positionPopupContainer, false));
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public int getInnerLayoutId() {
        return R.layout._xpopup_position_popup_view;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public PopupAnimator getPopupAnimator() {
        return new ScaleAlphaAnimator(getPopupContentView(), getAnimationDuration(), PopupAnimation.ScaleAlphaFromCenter);
    }

    public void initAndStartAnimation() {
        initAnimator();
        doShowAnimation();
        doAfterShow();
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void initPopupContent() {
        super.initPopupContent();
        XPopupUtils.applyPopupSize((ViewGroup) getPopupContentView(), getMaxWidth(), getMaxHeight(), getPopupWidth(), getPopupHeight(), new Runnable() { // from class: com.lxj.xpopup.core.PositionPopupView.1
            @Override // java.lang.Runnable
            public void run() {
                PositionPopupView positionPopupView = PositionPopupView.this;
                PopupInfo popupInfo = positionPopupView.popupInfo;
                if (popupInfo == null) {
                    return;
                }
                if (popupInfo.isCenterHorizontal) {
                    PositionPopupView.this.positionPopupContainer.setTranslationX((!XPopupUtils.isLayoutRtl(positionPopupView.getContext()) ? XPopupUtils.getWindowWidth(PositionPopupView.this.getContext()) - PositionPopupView.this.positionPopupContainer.getMeasuredWidth() : -(XPopupUtils.getWindowWidth(PositionPopupView.this.getContext()) - PositionPopupView.this.positionPopupContainer.getMeasuredWidth())) / 2.0f);
                } else {
                    positionPopupView.positionPopupContainer.setTranslationX(popupInfo.offsetX);
                }
                PositionPopupView.this.positionPopupContainer.setTranslationY(r0.popupInfo.offsetY);
                PositionPopupView.this.initAndStartAnimation();
            }
        });
    }
}
