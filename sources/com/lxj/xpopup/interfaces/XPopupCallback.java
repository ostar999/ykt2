package com.lxj.xpopup.interfaces;

import com.lxj.xpopup.core.BasePopupView;

/* loaded from: classes4.dex */
public interface XPopupCallback {
    void beforeDismiss(BasePopupView basePopupView);

    void beforeShow(BasePopupView basePopupView);

    boolean onBackPressed(BasePopupView basePopupView);

    void onCreated(BasePopupView basePopupView);

    void onDismiss(BasePopupView basePopupView);

    void onDrag(BasePopupView basePopupView, int i2, float f2, boolean z2);

    void onKeyBoardStateChanged(BasePopupView basePopupView, int i2);

    void onShow(BasePopupView basePopupView);
}
