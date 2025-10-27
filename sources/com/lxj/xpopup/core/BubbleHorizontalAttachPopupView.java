package com.lxj.xpopup.core;

import android.content.Context;
import android.graphics.PointF;
import android.graphics.Rect;
import androidx.annotation.NonNull;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.enums.PopupPosition;
import com.lxj.xpopup.util.XPopupUtils;
import com.lxj.xpopup.widget.BubbleLayout;

/* loaded from: classes4.dex */
public class BubbleHorizontalAttachPopupView extends BubbleAttachPopupView {
    public BubbleHorizontalAttachPopupView(@NonNull Context context) {
        super(context);
    }

    private boolean isShowLeftToTarget() {
        return (this.isShowLeft || this.popupInfo.popupPosition == PopupPosition.Left) && this.popupInfo.popupPosition != PopupPosition.Right;
    }

    @Override // com.lxj.xpopup.core.BubbleAttachPopupView
    public void doAttach() {
        boolean z2;
        int i2;
        float f2;
        float fHeight;
        int i3;
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
            fHeight = this.popupInfo.touchPoint.y - (measuredHeight * 0.5f);
            i3 = this.defaultOffsetY;
        } else {
            int[] iArr = new int[2];
            popupInfo.getAtView().getLocationOnScreen(iArr);
            int i4 = iArr[0];
            Rect rect = new Rect(i4, iArr[1], this.popupInfo.getAtView().getMeasuredWidth() + i4, iArr[1] + this.popupInfo.getAtView().getMeasuredHeight());
            z2 = (rect.left + rect.right) / 2 > XPopupUtils.getWindowWidth(getContext()) / 2;
            this.isShowLeft = z2;
            if (zIsLayoutRtl) {
                i2 = -(z2 ? (XPopupUtils.getWindowWidth(getContext()) - rect.left) + this.defaultOffsetX : ((XPopupUtils.getWindowWidth(getContext()) - rect.right) - getPopupContentView().getMeasuredWidth()) - this.defaultOffsetX);
            } else {
                i2 = isShowLeftToTarget() ? (rect.left - measuredWidth) - this.defaultOffsetX : rect.right + this.defaultOffsetX;
            }
            f2 = i2;
            fHeight = rect.top + ((rect.height() - measuredHeight) / 2.0f);
            i3 = this.defaultOffsetY;
        }
        float f3 = fHeight + i3;
        if (isShowLeftToTarget()) {
            this.bubbleContainer.setLook(BubbleLayout.Look.RIGHT);
        } else {
            this.bubbleContainer.setLook(BubbleLayout.Look.LEFT);
        }
        this.bubbleContainer.setLookPositionCenter(true);
        this.bubbleContainer.invalidate();
        getPopupContentView().setTranslationX(f2);
        getPopupContentView().setTranslationY(f3);
        initAndStartAnimation();
    }

    @Override // com.lxj.xpopup.core.BubbleAttachPopupView, com.lxj.xpopup.core.BasePopupView
    public void initPopupContent() {
        this.bubbleContainer.setLook(BubbleLayout.Look.LEFT);
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
