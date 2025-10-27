package com.lxj.xpopup.core;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import com.lxj.xpopup.R;
import com.lxj.xpopup.animator.PopupAnimator;
import com.lxj.xpopup.animator.ScaleAlphaAnimator;
import com.lxj.xpopup.enums.PopupAnimation;
import com.lxj.xpopup.util.XPopupUtils;

/* loaded from: classes4.dex */
public class CenterPopupView extends BasePopupView {
    protected int bindItemLayoutId;
    protected int bindLayoutId;
    protected FrameLayout centerPopupContainer;
    protected View contentView;

    public CenterPopupView(@NonNull Context context) {
        super(context);
        this.centerPopupContainer = (FrameLayout) findViewById(R.id.centerPopupContainer);
    }

    public void addInnerContent() {
        View viewInflate = LayoutInflater.from(getContext()).inflate(getImplLayoutId(), (ViewGroup) this.centerPopupContainer, false);
        this.contentView = viewInflate;
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) viewInflate.getLayoutParams();
        layoutParams.gravity = 17;
        this.centerPopupContainer.addView(this.contentView, layoutParams);
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void applyDarkTheme() {
        super.applyDarkTheme();
        this.centerPopupContainer.setBackground(XPopupUtils.createDrawable(getResources().getColor(R.color._xpopup_dark_color), this.popupInfo.borderRadius));
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void applyLightTheme() {
        super.applyLightTheme();
        this.centerPopupContainer.setBackground(XPopupUtils.createDrawable(getResources().getColor(R.color._xpopup_light_color), this.popupInfo.borderRadius));
    }

    public void applyTheme() {
        if (this.bindLayoutId == 0) {
            if (this.popupInfo.isDarkTheme) {
                applyDarkTheme();
            } else {
                applyLightTheme();
            }
        }
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return 0;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public int getInnerLayoutId() {
        return R.layout._xpopup_center_popup_view;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public int getMaxWidth() {
        int i2 = this.popupInfo.maxWidth;
        return i2 == 0 ? (int) (XPopupUtils.getWindowWidth(getContext()) * 0.8f) : i2;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public PopupAnimator getPopupAnimator() {
        return new ScaleAlphaAnimator(getPopupContentView(), getAnimationDuration(), PopupAnimation.ScaleAlphaFromCenter);
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void initPopupContent() {
        super.initPopupContent();
        if (this.centerPopupContainer.getChildCount() == 0) {
            addInnerContent();
        }
        getPopupContentView().setTranslationX(this.popupInfo.offsetX);
        getPopupContentView().setTranslationY(this.popupInfo.offsetY);
        XPopupUtils.applyPopupSize((ViewGroup) getPopupContentView(), getMaxWidth(), getMaxHeight(), getPopupWidth(), getPopupHeight(), null);
    }

    @Override // com.lxj.xpopup.core.BasePopupView, android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        setTranslationY(0.0f);
    }
}
