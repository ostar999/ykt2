package com.lxj.xpopup.impl;

import android.content.Context;
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
import com.lxj.xpopup.enums.PopupAnimation;
import com.lxj.xpopup.enums.PopupPosition;
import com.lxj.xpopup.interfaces.OnClickOutsideListener;
import com.lxj.xpopup.util.XPopupUtils;
import com.lxj.xpopup.widget.PartShadowContainer;

/* loaded from: classes4.dex */
public abstract class PartShadowPopupView extends BasePopupView {
    protected PartShadowContainer attachPopupContainer;
    public boolean isShowUp;

    public PartShadowPopupView(@NonNull Context context) {
        super(context);
        this.attachPopupContainer = (PartShadowContainer) findViewById(R.id.attachPopupContainer);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initAndStartAnimation() {
        initAnimator();
        doShowAnimation();
        doAfterShow();
    }

    public void addInnerContent() {
        this.attachPopupContainer.addView(LayoutInflater.from(getContext()).inflate(getImplLayoutId(), (ViewGroup) this.attachPopupContainer, false));
    }

    public void doAttach() {
        if (this.popupInfo.getAtView() == null) {
            throw new IllegalArgumentException("atView must not be null for PartShadowPopupView！");
        }
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) getPopupContentView().getLayoutParams();
        marginLayoutParams.width = getMeasuredWidth();
        int[] iArr = new int[2];
        this.popupInfo.getAtView().getLocationOnScreen(iArr);
        int i2 = iArr[0];
        Rect rect = new Rect(i2, iArr[1], this.popupInfo.getAtView().getMeasuredWidth() + i2, iArr[1] + this.popupInfo.getAtView().getMeasuredHeight());
        if (!this.popupInfo.isCenterHorizontal || getPopupImplView() == null) {
            int measuredWidth = rect.left + this.popupInfo.offsetX;
            if (getPopupImplView().getMeasuredWidth() + measuredWidth > XPopupUtils.getWindowWidth(getContext())) {
                measuredWidth -= (getPopupImplView().getMeasuredWidth() + measuredWidth) - XPopupUtils.getWindowWidth(getContext());
            }
            getPopupImplView().setTranslationX(measuredWidth);
        } else {
            getPopupImplView().setTranslationX(((rect.left + rect.right) / 2) - (getPopupImplView().getMeasuredWidth() / 2));
        }
        int iHeight = rect.top + (rect.height() / 2);
        View childAt = ((ViewGroup) getPopupContentView()).getChildAt(0);
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) childAt.getLayoutParams();
        if ((iHeight > getMeasuredHeight() / 2 || this.popupInfo.popupPosition == PopupPosition.Top) && this.popupInfo.popupPosition != PopupPosition.Bottom) {
            marginLayoutParams.height = rect.top;
            this.isShowUp = true;
            layoutParams.gravity = 80;
            if (getMaxHeight() != 0) {
                layoutParams.height = Math.min(childAt.getMeasuredHeight(), getMaxHeight());
            }
        } else {
            int measuredHeight = getMeasuredHeight();
            int i3 = rect.bottom;
            marginLayoutParams.height = measuredHeight - i3;
            this.isShowUp = false;
            marginLayoutParams.topMargin = i3;
            layoutParams.gravity = 48;
            if (getMaxHeight() != 0) {
                layoutParams.height = Math.min(childAt.getMeasuredHeight(), getMaxHeight());
            }
            childAt.setLayoutParams(layoutParams);
        }
        getPopupContentView().setLayoutParams(marginLayoutParams);
        childAt.setLayoutParams(layoutParams);
        getPopupContentView().post(new Runnable() { // from class: com.lxj.xpopup.impl.PartShadowPopupView.2
            @Override // java.lang.Runnable
            public void run() {
                PartShadowPopupView.this.initAndStartAnimation();
                PartShadowPopupView.this.getPopupImplView().setVisibility(0);
            }
        });
        this.attachPopupContainer.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.lxj.xpopup.impl.PartShadowPopupView.3
            @Override // android.view.View.OnLongClickListener
            public boolean onLongClick(View view) {
                if (!PartShadowPopupView.this.popupInfo.isDismissOnTouchOutside.booleanValue()) {
                    return false;
                }
                PartShadowPopupView.this.dismiss();
                return false;
            }
        });
        this.attachPopupContainer.setOnClickOutsideListener(new OnClickOutsideListener() { // from class: com.lxj.xpopup.impl.PartShadowPopupView.4
            @Override // com.lxj.xpopup.interfaces.OnClickOutsideListener
            public void onClickOutside() {
                if (PartShadowPopupView.this.popupInfo.isDismissOnTouchOutside.booleanValue()) {
                    PartShadowPopupView.this.dismiss();
                }
            }
        });
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public int getInnerLayoutId() {
        return R.layout._xpopup_partshadow_popup_view;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public PopupAnimator getPopupAnimator() {
        return new TranslateAnimator(getPopupImplView(), getAnimationDuration(), this.isShowUp ? PopupAnimation.TranslateFromBottom : PopupAnimation.TranslateFromTop);
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void initPopupContent() {
        if (this.attachPopupContainer.getChildCount() == 0) {
            addInnerContent();
        }
        if (this.popupInfo.hasShadowBg.booleanValue()) {
            this.shadowBgAnimator.targetView = getPopupContentView();
        }
        getPopupContentView().setTranslationY(this.popupInfo.offsetY);
        getPopupImplView().setTranslationX(this.popupInfo.offsetX);
        getPopupImplView().setTranslationY(0.0f);
        getPopupImplView().setVisibility(4);
        XPopupUtils.applyPopupSize((ViewGroup) getPopupContentView(), getMaxWidth(), getMaxHeight(), getPopupWidth(), getPopupHeight(), new Runnable() { // from class: com.lxj.xpopup.impl.PartShadowPopupView.1
            @Override // java.lang.Runnable
            public void run() {
                PartShadowPopupView.this.doAttach();
            }
        });
    }
}
