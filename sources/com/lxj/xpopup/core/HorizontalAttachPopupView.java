package com.lxj.xpopup.core;

import android.content.Context;
import android.graphics.PointF;
import android.graphics.Rect;
import androidx.annotation.NonNull;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.animator.PopupAnimator;
import com.lxj.xpopup.animator.ScrollScaleAnimator;
import com.lxj.xpopup.enums.PopupAnimation;
import com.lxj.xpopup.enums.PopupPosition;
import com.lxj.xpopup.util.XPopupUtils;

/* loaded from: classes4.dex */
public class HorizontalAttachPopupView extends AttachPopupView {
    public HorizontalAttachPopupView(@NonNull Context context) {
        super(context);
    }

    private boolean isShowLeftToTarget() {
        return (this.isShowLeft || this.popupInfo.popupPosition == PopupPosition.Left) && this.popupInfo.popupPosition != PopupPosition.Right;
    }

    @Override // com.lxj.xpopup.core.AttachPopupView
    public void doAttach() {
        boolean z2;
        int i2;
        float f2;
        float fHeight;
        boolean zIsLayoutRtl = XPopupUtils.isLayoutRtl(getContext());
        int measuredWidth = getPopupContentView().getMeasuredWidth();
        int measuredHeight = getPopupContentView().getMeasuredHeight();
        PopupInfo popupInfo = this.popupInfo;
        if (popupInfo.touchPoint != null) {
            PointF pointF = XPopup.longClickPoint;
            if (pointF != null) {
                popupInfo.touchPoint = pointF;
            }
            z2 = popupInfo.touchPoint.x > ((float) (XPopupUtils.getWindowWidth(getContext()) / 2));
            this.isShowLeft = z2;
            if (zIsLayoutRtl) {
                f2 = -(z2 ? (XPopupUtils.getWindowWidth(getContext()) - this.popupInfo.touchPoint.x) + this.defaultOffsetX : ((XPopupUtils.getWindowWidth(getContext()) - this.popupInfo.touchPoint.x) - getPopupContentView().getMeasuredWidth()) - this.defaultOffsetX);
            } else {
                f2 = isShowLeftToTarget() ? (this.popupInfo.touchPoint.x - measuredWidth) - this.defaultOffsetX : this.popupInfo.touchPoint.x + this.defaultOffsetX;
            }
            fHeight = (this.popupInfo.touchPoint.y - (measuredHeight * 0.5f)) + this.defaultOffsetY;
        } else {
            int[] iArr = new int[2];
            popupInfo.getAtView().getLocationOnScreen(iArr);
            int i3 = iArr[0];
            Rect rect = new Rect(i3, iArr[1], this.popupInfo.getAtView().getMeasuredWidth() + i3, iArr[1] + this.popupInfo.getAtView().getMeasuredHeight());
            z2 = (rect.left + rect.right) / 2 > XPopupUtils.getWindowWidth(getContext()) / 2;
            this.isShowLeft = z2;
            if (zIsLayoutRtl) {
                i2 = -(z2 ? (XPopupUtils.getWindowWidth(getContext()) - rect.left) + this.defaultOffsetX : ((XPopupUtils.getWindowWidth(getContext()) - rect.right) - getPopupContentView().getMeasuredWidth()) - this.defaultOffsetX);
            } else {
                i2 = isShowLeftToTarget() ? (rect.left - measuredWidth) - this.defaultOffsetX : rect.right + this.defaultOffsetX;
            }
            f2 = i2;
            fHeight = rect.top + ((rect.height() - measuredHeight) / 2) + this.defaultOffsetY;
        }
        getPopupContentView().setTranslationX(f2);
        getPopupContentView().setTranslationY(fHeight);
        initAndStartAnimation();
    }

    @Override // com.lxj.xpopup.core.AttachPopupView, com.lxj.xpopup.core.BasePopupView
    public PopupAnimator getPopupAnimator() {
        ScrollScaleAnimator scrollScaleAnimator = isShowLeftToTarget() ? new ScrollScaleAnimator(getPopupContentView(), getAnimationDuration(), PopupAnimation.ScrollAlphaFromRight) : new ScrollScaleAnimator(getPopupContentView(), getAnimationDuration(), PopupAnimation.ScrollAlphaFromLeft);
        scrollScaleAnimator.isOnlyScaleX = true;
        return scrollScaleAnimator;
    }

    @Override // com.lxj.xpopup.core.AttachPopupView, com.lxj.xpopup.core.BasePopupView
    public void initPopupContent() {
        super.initPopupContent();
        PopupInfo popupInfo = this.popupInfo;
        this.defaultOffsetY = popupInfo.offsetY;
        int iDp2px = popupInfo.offsetX;
        if (iDp2px == 0) {
            iDp2px = XPopupUtils.dp2px(getContext(), 2.0f);
        }
        this.defaultOffsetX = iDp2px;
    }
}
