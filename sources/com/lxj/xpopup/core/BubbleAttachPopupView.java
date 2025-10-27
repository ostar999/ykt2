package com.lxj.xpopup.core;

import android.content.Context;
import android.graphics.PointF;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import com.lxj.xpopup.R;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.animator.PopupAnimator;
import com.lxj.xpopup.animator.ScaleAlphaAnimator;
import com.lxj.xpopup.enums.PopupAnimation;
import com.lxj.xpopup.enums.PopupPosition;
import com.lxj.xpopup.util.XPopupUtils;
import com.lxj.xpopup.widget.BubbleLayout;

/* loaded from: classes4.dex */
public abstract class BubbleAttachPopupView extends BasePopupView {
    protected BubbleLayout bubbleContainer;
    float centerY;
    protected int defaultOffsetX;
    protected int defaultOffsetY;
    public boolean isShowLeft;
    public boolean isShowUp;
    float maxY;
    int overflow;
    float translationX;
    float translationY;

    public BubbleAttachPopupView(@NonNull Context context) {
        super(context);
        this.defaultOffsetY = 0;
        this.defaultOffsetX = 0;
        this.translationX = 0.0f;
        this.translationY = 0.0f;
        this.maxY = XPopupUtils.getAppHeight(getContext());
        this.overflow = XPopupUtils.dp2px(getContext(), 10.0f);
        this.centerY = 0.0f;
        this.bubbleContainer = (BubbleLayout) findViewById(R.id.bubbleContainer);
    }

    public void addInnerContent() {
        this.bubbleContainer.addView(LayoutInflater.from(getContext()).inflate(getImplLayoutId(), (ViewGroup) this.bubbleContainer, false));
    }

    public void doAttach() {
        int screenHeight;
        int i2;
        float screenHeight2;
        int i3;
        this.maxY = XPopupUtils.getAppHeight(getContext()) - this.overflow;
        final boolean zIsLayoutRtl = XPopupUtils.isLayoutRtl(getContext());
        PopupInfo popupInfo = this.popupInfo;
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
                layoutParams.width = windowWidth;
            }
            getPopupContentView().setLayoutParams(layoutParams);
            getPopupContentView().post(new Runnable() { // from class: com.lxj.xpopup.core.BubbleAttachPopupView.2
                @Override // java.lang.Runnable
                public void run() {
                    float windowWidth2;
                    if (zIsLayoutRtl) {
                        BubbleAttachPopupView bubbleAttachPopupView = BubbleAttachPopupView.this;
                        if (bubbleAttachPopupView.isShowLeft) {
                            windowWidth2 = ((XPopupUtils.getWindowWidth(bubbleAttachPopupView.getContext()) - BubbleAttachPopupView.this.popupInfo.touchPoint.x) - r2.getPopupContentView().getMeasuredWidth()) - BubbleAttachPopupView.this.defaultOffsetX;
                        } else {
                            windowWidth2 = (XPopupUtils.getWindowWidth(bubbleAttachPopupView.getContext()) - BubbleAttachPopupView.this.popupInfo.touchPoint.x) + r2.defaultOffsetX;
                        }
                        bubbleAttachPopupView.translationX = -windowWidth2;
                    } else {
                        BubbleAttachPopupView bubbleAttachPopupView2 = BubbleAttachPopupView.this;
                        bubbleAttachPopupView2.translationX = bubbleAttachPopupView2.isShowLeft ? bubbleAttachPopupView2.popupInfo.touchPoint.x + bubbleAttachPopupView2.defaultOffsetX : (bubbleAttachPopupView2.popupInfo.touchPoint.x - bubbleAttachPopupView2.getPopupContentView().getMeasuredWidth()) - BubbleAttachPopupView.this.defaultOffsetX;
                    }
                    BubbleAttachPopupView bubbleAttachPopupView3 = BubbleAttachPopupView.this;
                    if (bubbleAttachPopupView3.popupInfo.isCenterHorizontal) {
                        if (bubbleAttachPopupView3.isShowLeft) {
                            if (zIsLayoutRtl) {
                                bubbleAttachPopupView3.translationX += bubbleAttachPopupView3.getPopupContentView().getMeasuredWidth() / 2.0f;
                            } else {
                                bubbleAttachPopupView3.translationX -= bubbleAttachPopupView3.getPopupContentView().getMeasuredWidth() / 2.0f;
                            }
                        } else if (zIsLayoutRtl) {
                            bubbleAttachPopupView3.translationX -= bubbleAttachPopupView3.getPopupContentView().getMeasuredWidth() / 2.0f;
                        } else {
                            bubbleAttachPopupView3.translationX += bubbleAttachPopupView3.getPopupContentView().getMeasuredWidth() / 2.0f;
                        }
                    }
                    if (BubbleAttachPopupView.this.isShowUpToTarget()) {
                        BubbleAttachPopupView bubbleAttachPopupView4 = BubbleAttachPopupView.this;
                        bubbleAttachPopupView4.translationY = (bubbleAttachPopupView4.popupInfo.touchPoint.y - bubbleAttachPopupView4.getPopupContentView().getMeasuredHeight()) - BubbleAttachPopupView.this.defaultOffsetY;
                    } else {
                        BubbleAttachPopupView bubbleAttachPopupView5 = BubbleAttachPopupView.this;
                        bubbleAttachPopupView5.translationY = bubbleAttachPopupView5.popupInfo.touchPoint.y + bubbleAttachPopupView5.defaultOffsetY;
                    }
                    if (BubbleAttachPopupView.this.isShowUpToTarget()) {
                        BubbleAttachPopupView.this.bubbleContainer.setLook(BubbleLayout.Look.BOTTOM);
                    } else {
                        BubbleAttachPopupView.this.bubbleContainer.setLook(BubbleLayout.Look.TOP);
                    }
                    BubbleAttachPopupView bubbleAttachPopupView6 = BubbleAttachPopupView.this;
                    if (bubbleAttachPopupView6.popupInfo.isCenterHorizontal) {
                        bubbleAttachPopupView6.bubbleContainer.setLookPositionCenter(true);
                    } else if (bubbleAttachPopupView6.isShowLeft) {
                        bubbleAttachPopupView6.bubbleContainer.setLookPosition(XPopupUtils.dp2px(bubbleAttachPopupView6.getContext(), 1.0f));
                    } else {
                        BubbleLayout bubbleLayout = bubbleAttachPopupView6.bubbleContainer;
                        bubbleLayout.setLookPosition(bubbleLayout.getMeasuredWidth() - XPopupUtils.dp2px(BubbleAttachPopupView.this.getContext(), 1.0f));
                    }
                    BubbleAttachPopupView.this.bubbleContainer.invalidate();
                    BubbleAttachPopupView.this.getPopupContentView().setTranslationX(BubbleAttachPopupView.this.translationX);
                    BubbleAttachPopupView.this.getPopupContentView().setTranslationY(BubbleAttachPopupView.this.translationY);
                    BubbleAttachPopupView.this.initAndStartAnimation();
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
        this.centerY = (rect.top + rect.bottom) / 2;
        if (z2) {
            this.isShowUp = true;
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
        int i7 = screenHeight - i2;
        int windowWidth2 = (this.isShowLeft ? XPopupUtils.getWindowWidth(getContext()) - rect.left : rect.right) - this.overflow;
        if (getPopupContentView().getMeasuredHeight() > i7) {
            layoutParams2.height = i7;
        }
        if (getPopupContentView().getMeasuredWidth() > windowWidth2) {
            layoutParams2.width = windowWidth2;
        }
        getPopupContentView().setLayoutParams(layoutParams2);
        getPopupContentView().post(new Runnable() { // from class: com.lxj.xpopup.core.BubbleAttachPopupView.3
            @Override // java.lang.Runnable
            public void run() {
                if (zIsLayoutRtl) {
                    BubbleAttachPopupView bubbleAttachPopupView = BubbleAttachPopupView.this;
                    bubbleAttachPopupView.translationX = -(bubbleAttachPopupView.isShowLeft ? ((XPopupUtils.getWindowWidth(bubbleAttachPopupView.getContext()) - rect.left) - BubbleAttachPopupView.this.getPopupContentView().getMeasuredWidth()) - BubbleAttachPopupView.this.defaultOffsetX : (XPopupUtils.getWindowWidth(bubbleAttachPopupView.getContext()) - rect.right) + BubbleAttachPopupView.this.defaultOffsetX);
                } else {
                    BubbleAttachPopupView bubbleAttachPopupView2 = BubbleAttachPopupView.this;
                    bubbleAttachPopupView2.translationX = bubbleAttachPopupView2.isShowLeft ? rect.left + bubbleAttachPopupView2.defaultOffsetX : (rect.right - bubbleAttachPopupView2.getPopupContentView().getMeasuredWidth()) - BubbleAttachPopupView.this.defaultOffsetX;
                }
                BubbleAttachPopupView bubbleAttachPopupView3 = BubbleAttachPopupView.this;
                if (bubbleAttachPopupView3.popupInfo.isCenterHorizontal) {
                    if (bubbleAttachPopupView3.isShowLeft) {
                        if (zIsLayoutRtl) {
                            bubbleAttachPopupView3.translationX -= (rect.width() - BubbleAttachPopupView.this.getPopupContentView().getMeasuredWidth()) / 2.0f;
                        } else {
                            bubbleAttachPopupView3.translationX += (rect.width() - BubbleAttachPopupView.this.getPopupContentView().getMeasuredWidth()) / 2.0f;
                        }
                    } else if (zIsLayoutRtl) {
                        bubbleAttachPopupView3.translationX += (rect.width() - BubbleAttachPopupView.this.getPopupContentView().getMeasuredWidth()) / 2.0f;
                    } else {
                        bubbleAttachPopupView3.translationX -= (rect.width() - BubbleAttachPopupView.this.getPopupContentView().getMeasuredWidth()) / 2.0f;
                    }
                }
                if (BubbleAttachPopupView.this.isShowUpToTarget()) {
                    BubbleAttachPopupView.this.translationY = (rect.top - r0.getPopupContentView().getMeasuredHeight()) - BubbleAttachPopupView.this.defaultOffsetY;
                } else {
                    BubbleAttachPopupView.this.translationY = rect.bottom + r0.defaultOffsetY;
                }
                if (BubbleAttachPopupView.this.isShowUpToTarget()) {
                    BubbleAttachPopupView.this.bubbleContainer.setLook(BubbleLayout.Look.BOTTOM);
                } else {
                    BubbleAttachPopupView.this.bubbleContainer.setLook(BubbleLayout.Look.TOP);
                }
                BubbleAttachPopupView bubbleAttachPopupView4 = BubbleAttachPopupView.this;
                if (bubbleAttachPopupView4.popupInfo.isCenterHorizontal) {
                    bubbleAttachPopupView4.bubbleContainer.setLookPositionCenter(true);
                } else {
                    BubbleLayout bubbleLayout = bubbleAttachPopupView4.bubbleContainer;
                    Rect rect2 = rect;
                    bubbleLayout.setLookPosition((rect2.left + (rect2.width() / 2)) - ((int) BubbleAttachPopupView.this.translationX));
                }
                BubbleAttachPopupView.this.bubbleContainer.invalidate();
                BubbleAttachPopupView.this.getPopupContentView().setTranslationX(BubbleAttachPopupView.this.translationX);
                BubbleAttachPopupView.this.getPopupContentView().setTranslationY(BubbleAttachPopupView.this.translationY);
                BubbleAttachPopupView.this.initAndStartAnimation();
            }
        });
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public int getInnerLayoutId() {
        return R.layout._xpopup_bubble_attach_popup_view;
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
        if (this.bubbleContainer.getChildCount() == 0) {
            addInnerContent();
        }
        if (this.popupInfo.getAtView() == null && this.popupInfo.touchPoint == null) {
            throw new IllegalArgumentException("atView() or watchView() must be called for BubbleAttachPopupView before show()！");
        }
        this.bubbleContainer.setElevation(XPopupUtils.dp2px(getContext(), 10.0f));
        this.bubbleContainer.setShadowRadius(XPopupUtils.dp2px(getContext(), 2.0f));
        PopupInfo popupInfo = this.popupInfo;
        this.defaultOffsetY = popupInfo.offsetY;
        int i2 = popupInfo.offsetX;
        this.defaultOffsetX = i2;
        this.bubbleContainer.setTranslationX(i2);
        this.bubbleContainer.setTranslationY(this.popupInfo.offsetY);
        XPopupUtils.applyPopupSize((ViewGroup) getPopupContentView(), getMaxWidth(), getMaxHeight(), getPopupWidth(), getPopupHeight(), new Runnable() { // from class: com.lxj.xpopup.core.BubbleAttachPopupView.1
            @Override // java.lang.Runnable
            public void run() {
                BubbleAttachPopupView.this.doAttach();
            }
        });
    }

    public boolean isShowUpToTarget() {
        PopupInfo popupInfo = this.popupInfo;
        return popupInfo.positionByWindowCenter ? this.centerY > ((float) (XPopupUtils.getAppHeight(getContext()) / 2)) : (this.isShowUp || popupInfo.popupPosition == PopupPosition.Top) && popupInfo.popupPosition != PopupPosition.Bottom;
    }

    public BubbleAttachPopupView setArrowHeight(int i2) {
        this.bubbleContainer.setLookLength(i2);
        this.bubbleContainer.invalidate();
        return this;
    }

    public BubbleAttachPopupView setArrowWidth(int i2) {
        this.bubbleContainer.setLookWidth(i2);
        this.bubbleContainer.invalidate();
        return this;
    }

    public BubbleAttachPopupView setBubbleBgColor(int i2) {
        this.bubbleContainer.setBubbleColor(i2);
        this.bubbleContainer.invalidate();
        return this;
    }

    public BubbleAttachPopupView setBubbleRadius(int i2) {
        this.bubbleContainer.setBubbleRadius(i2);
        this.bubbleContainer.invalidate();
        return this;
    }
}
