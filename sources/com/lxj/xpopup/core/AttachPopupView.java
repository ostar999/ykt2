package com.lxj.xpopup.core;

import android.content.Context;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import com.lxj.xpopup.R;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.animator.PopupAnimator;
import com.lxj.xpopup.animator.ScrollScaleAnimator;
import com.lxj.xpopup.enums.PopupAnimation;
import com.lxj.xpopup.enums.PopupPosition;
import com.lxj.xpopup.util.XPopupUtils;

/* loaded from: classes4.dex */
public abstract class AttachPopupView extends BasePopupView {
    protected FrameLayout attachPopupContainer;
    float centerY;
    protected int defaultOffsetX;
    protected int defaultOffsetY;
    public boolean isShowLeft;
    public boolean isShowUp;
    float maxY;
    int overflow;
    float translationX;
    float translationY;

    public AttachPopupView(@NonNull Context context) {
        super(context);
        this.defaultOffsetY = 0;
        this.defaultOffsetX = 0;
        this.translationX = 0.0f;
        this.translationY = 0.0f;
        this.maxY = XPopupUtils.getAppHeight(getContext());
        this.overflow = XPopupUtils.dp2px(getContext(), 10.0f);
        this.centerY = 0.0f;
        this.attachPopupContainer = (FrameLayout) findViewById(R.id.attachPopupContainer);
    }

    public void addInnerContent() {
        this.attachPopupContainer.addView(LayoutInflater.from(getContext()).inflate(getImplLayoutId(), (ViewGroup) this.attachPopupContainer, false));
    }

    public void applyBg() {
        Drawable.ConstantState constantState;
        if (this.isCreated) {
            return;
        }
        if (getPopupImplView().getBackground() != null && (constantState = getPopupImplView().getBackground().getConstantState()) != null) {
            this.attachPopupContainer.setBackground(constantState.newDrawable(getResources()));
            getPopupImplView().setBackground(null);
        }
        this.attachPopupContainer.setElevation(XPopupUtils.dp2px(getContext(), 20.0f));
    }

    public void doAttach() {
        int screenHeight;
        int i2;
        float screenHeight2;
        int i3;
        this.maxY = XPopupUtils.getAppHeight(getContext()) - this.overflow;
        final boolean zIsLayoutRtl = XPopupUtils.isLayoutRtl(getContext());
        PopupInfo popupInfo = this.popupInfo;
        if (popupInfo == null) {
            return;
        }
        if (popupInfo.touchPoint != null) {
            PointF pointF = XPopup.longClickPoint;
            if (pointF != null) {
                popupInfo.touchPoint = pointF;
            }
            float f2 = popupInfo.touchPoint.y;
            this.centerY = f2;
            if (f2 + ((float) getPopupContentView().getMeasuredHeight()) > this.maxY) {
                this.isShowUp = this.popupInfo.touchPoint.y > ((float) (XPopupUtils.getScreenHeight(getContext()) / 2));
            } else {
                this.isShowUp = false;
            }
            this.isShowLeft = this.popupInfo.touchPoint.x < ((float) (XPopupUtils.getWindowWidth(getContext()) / 2));
            ViewGroup.LayoutParams layoutParams = getPopupContentView().getLayoutParams();
            if (isShowUpToTarget()) {
                screenHeight2 = this.popupInfo.touchPoint.y - XPopupUtils.getStatusBarHeight();
                i3 = this.overflow;
            } else {
                screenHeight2 = XPopupUtils.getScreenHeight(getContext()) - this.popupInfo.touchPoint.y;
                i3 = this.overflow;
            }
            int i4 = (int) (screenHeight2 - i3);
            int windowWidth = (int) ((this.isShowLeft ? XPopupUtils.getWindowWidth(getContext()) - this.popupInfo.touchPoint.x : this.popupInfo.touchPoint.x) - this.overflow);
            if (getPopupContentView().getMeasuredHeight() > i4) {
                layoutParams.height = i4;
            }
            if (getPopupContentView().getMeasuredWidth() > windowWidth) {
                layoutParams.width = Math.max(windowWidth, getPopupWidth());
            }
            getPopupContentView().setLayoutParams(layoutParams);
            getPopupContentView().post(new Runnable() { // from class: com.lxj.xpopup.core.AttachPopupView.2
                @Override // java.lang.Runnable
                public void run() {
                    float windowWidth2;
                    if (zIsLayoutRtl) {
                        AttachPopupView attachPopupView = AttachPopupView.this;
                        if (attachPopupView.isShowLeft) {
                            windowWidth2 = ((XPopupUtils.getWindowWidth(attachPopupView.getContext()) - AttachPopupView.this.popupInfo.touchPoint.x) - r2.getPopupContentView().getMeasuredWidth()) - AttachPopupView.this.defaultOffsetX;
                        } else {
                            windowWidth2 = (XPopupUtils.getWindowWidth(attachPopupView.getContext()) - AttachPopupView.this.popupInfo.touchPoint.x) + r2.defaultOffsetX;
                        }
                        attachPopupView.translationX = -windowWidth2;
                    } else {
                        AttachPopupView attachPopupView2 = AttachPopupView.this;
                        attachPopupView2.translationX = attachPopupView2.isShowLeft ? attachPopupView2.popupInfo.touchPoint.x + attachPopupView2.defaultOffsetX : (attachPopupView2.popupInfo.touchPoint.x - attachPopupView2.getPopupContentView().getMeasuredWidth()) - AttachPopupView.this.defaultOffsetX;
                    }
                    AttachPopupView attachPopupView3 = AttachPopupView.this;
                    if (attachPopupView3.popupInfo.isCenterHorizontal) {
                        if (attachPopupView3.isShowLeft) {
                            if (zIsLayoutRtl) {
                                attachPopupView3.translationX += attachPopupView3.getPopupContentView().getMeasuredWidth() / 2.0f;
                            } else {
                                attachPopupView3.translationX -= attachPopupView3.getPopupContentView().getMeasuredWidth() / 2.0f;
                            }
                        } else if (zIsLayoutRtl) {
                            attachPopupView3.translationX -= attachPopupView3.getPopupContentView().getMeasuredWidth() / 2.0f;
                        } else {
                            attachPopupView3.translationX += attachPopupView3.getPopupContentView().getMeasuredWidth() / 2.0f;
                        }
                    }
                    if (AttachPopupView.this.isShowUpToTarget()) {
                        AttachPopupView attachPopupView4 = AttachPopupView.this;
                        attachPopupView4.translationY = (attachPopupView4.popupInfo.touchPoint.y - attachPopupView4.getPopupContentView().getMeasuredHeight()) - AttachPopupView.this.defaultOffsetY;
                    } else {
                        AttachPopupView attachPopupView5 = AttachPopupView.this;
                        attachPopupView5.translationY = attachPopupView5.popupInfo.touchPoint.y + attachPopupView5.defaultOffsetY;
                    }
                    AttachPopupView.this.getPopupContentView().setTranslationX(AttachPopupView.this.translationX);
                    AttachPopupView.this.getPopupContentView().setTranslationY(AttachPopupView.this.translationY);
                    AttachPopupView.this.initAndStartAnimation();
                }
            });
            return;
        }
        int[] iArr = new int[2];
        popupInfo.getAtView().getLocationOnScreen(iArr);
        int i5 = iArr[0];
        final Rect rect = new Rect(i5, iArr[1], this.popupInfo.getAtView().getMeasuredWidth() + i5, iArr[1] + this.popupInfo.getAtView().getMeasuredHeight());
        int i6 = (rect.left + rect.right) / 2;
        boolean z2 = ((float) (rect.bottom + getPopupContentView().getMeasuredHeight())) > this.maxY;
        int i7 = rect.top;
        this.centerY = (rect.bottom + i7) / 2;
        if (z2) {
            int statusBarHeight = (i7 - XPopupUtils.getStatusBarHeight()) - this.overflow;
            if (getPopupContentView().getMeasuredHeight() > statusBarHeight) {
                this.isShowUp = ((float) statusBarHeight) > this.maxY - ((float) rect.bottom);
            } else {
                this.isShowUp = true;
            }
        } else {
            this.isShowUp = false;
        }
        this.isShowLeft = i6 < XPopupUtils.getWindowWidth(getContext()) / 2;
        ViewGroup.LayoutParams layoutParams2 = getPopupContentView().getLayoutParams();
        if (isShowUpToTarget()) {
            screenHeight = rect.top - XPopupUtils.getStatusBarHeight();
            i2 = this.overflow;
        } else {
            screenHeight = XPopupUtils.getScreenHeight(getContext()) - rect.bottom;
            i2 = this.overflow;
        }
        int i8 = screenHeight - i2;
        int windowWidth2 = (this.isShowLeft ? XPopupUtils.getWindowWidth(getContext()) - rect.left : rect.right) - this.overflow;
        if (getPopupContentView().getMeasuredHeight() > i8) {
            layoutParams2.height = i8;
        }
        if (getPopupContentView().getMeasuredWidth() > windowWidth2) {
            layoutParams2.width = Math.max(windowWidth2, getPopupWidth());
        }
        getPopupContentView().setLayoutParams(layoutParams2);
        getPopupContentView().post(new Runnable() { // from class: com.lxj.xpopup.core.AttachPopupView.3
            @Override // java.lang.Runnable
            public void run() {
                if (zIsLayoutRtl) {
                    AttachPopupView attachPopupView = AttachPopupView.this;
                    attachPopupView.translationX = -(attachPopupView.isShowLeft ? ((XPopupUtils.getWindowWidth(attachPopupView.getContext()) - rect.left) - AttachPopupView.this.getPopupContentView().getMeasuredWidth()) - AttachPopupView.this.defaultOffsetX : (XPopupUtils.getWindowWidth(attachPopupView.getContext()) - rect.right) + AttachPopupView.this.defaultOffsetX);
                } else {
                    AttachPopupView attachPopupView2 = AttachPopupView.this;
                    attachPopupView2.translationX = attachPopupView2.isShowLeft ? rect.left + attachPopupView2.defaultOffsetX : (rect.right - attachPopupView2.getPopupContentView().getMeasuredWidth()) - AttachPopupView.this.defaultOffsetX;
                }
                AttachPopupView attachPopupView3 = AttachPopupView.this;
                if (attachPopupView3.popupInfo.isCenterHorizontal) {
                    if (attachPopupView3.isShowLeft) {
                        if (zIsLayoutRtl) {
                            attachPopupView3.translationX -= (rect.width() - AttachPopupView.this.getPopupContentView().getMeasuredWidth()) / 2.0f;
                        } else {
                            attachPopupView3.translationX += (rect.width() - AttachPopupView.this.getPopupContentView().getMeasuredWidth()) / 2.0f;
                        }
                    } else if (zIsLayoutRtl) {
                        attachPopupView3.translationX += (rect.width() - AttachPopupView.this.getPopupContentView().getMeasuredWidth()) / 2.0f;
                    } else {
                        attachPopupView3.translationX -= (rect.width() - AttachPopupView.this.getPopupContentView().getMeasuredWidth()) / 2.0f;
                    }
                }
                if (AttachPopupView.this.isShowUpToTarget()) {
                    AttachPopupView.this.translationY = (rect.top - r0.getPopupContentView().getMeasuredHeight()) - AttachPopupView.this.defaultOffsetY;
                } else {
                    AttachPopupView.this.translationY = rect.bottom + r0.defaultOffsetY;
                }
                AttachPopupView.this.getPopupContentView().setTranslationX(AttachPopupView.this.translationX);
                AttachPopupView.this.getPopupContentView().setTranslationY(AttachPopupView.this.translationY);
                AttachPopupView.this.initAndStartAnimation();
            }
        });
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public int getInnerLayoutId() {
        return R.layout._xpopup_attach_popup_view;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public PopupAnimator getPopupAnimator() {
        ScrollScaleAnimator scrollScaleAnimator;
        if (isShowUpToTarget()) {
            scrollScaleAnimator = new ScrollScaleAnimator(getPopupContentView(), getAnimationDuration(), this.isShowLeft ? PopupAnimation.ScrollAlphaFromLeftBottom : PopupAnimation.ScrollAlphaFromRightBottom);
        } else {
            scrollScaleAnimator = new ScrollScaleAnimator(getPopupContentView(), getAnimationDuration(), this.isShowLeft ? PopupAnimation.ScrollAlphaFromLeftTop : PopupAnimation.ScrollAlphaFromRightTop);
        }
        return scrollScaleAnimator;
    }

    public void initAndStartAnimation() {
        initAnimator();
        doShowAnimation();
        doAfterShow();
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void initPopupContent() {
        super.initPopupContent();
        if (this.attachPopupContainer.getChildCount() == 0) {
            addInnerContent();
        }
        if (this.popupInfo.getAtView() == null && this.popupInfo.touchPoint == null) {
            throw new IllegalArgumentException("atView() or watchView() must be called for AttachPopupView before show()！");
        }
        int iDp2px = this.popupInfo.offsetY;
        if (iDp2px == 0) {
            iDp2px = XPopupUtils.dp2px(getContext(), 2.0f);
        }
        this.defaultOffsetY = iDp2px;
        int i2 = this.popupInfo.offsetX;
        this.defaultOffsetX = i2;
        this.attachPopupContainer.setTranslationX(i2);
        this.attachPopupContainer.setTranslationY(this.popupInfo.offsetY);
        applyBg();
        XPopupUtils.applyPopupSize((ViewGroup) getPopupContentView(), getMaxWidth(), getMaxHeight(), getPopupWidth(), getPopupHeight(), new Runnable() { // from class: com.lxj.xpopup.core.AttachPopupView.1
            @Override // java.lang.Runnable
            public void run() {
                AttachPopupView.this.doAttach();
            }
        });
    }

    public boolean isShowUpToTarget() {
        PopupInfo popupInfo = this.popupInfo;
        return popupInfo.positionByWindowCenter ? this.centerY > ((float) (XPopupUtils.getAppHeight(getContext()) / 2)) : (this.isShowUp || popupInfo.popupPosition == PopupPosition.Top) && popupInfo.popupPosition != PopupPosition.Bottom;
    }
}
