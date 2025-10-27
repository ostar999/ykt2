package com.lxj.xpopup.core;

import android.graphics.PointF;
import android.view.View;
import com.lxj.xpopup.animator.PopupAnimator;
import com.lxj.xpopup.enums.PopupAnimation;
import com.lxj.xpopup.enums.PopupPosition;
import com.lxj.xpopup.enums.PopupType;
import com.lxj.xpopup.interfaces.XPopupCallback;

/* loaded from: classes4.dex */
public class PopupInfo {
    public int animationDuration;
    public View atView;
    public Boolean autoDismiss;
    public boolean autoFocusEditText;
    public Boolean autoOpenSoftInput;
    public float borderRadius;
    public PopupAnimator customAnimator;
    public Boolean enableDrag;
    public boolean enableShowWhenAppBackground;
    public Boolean hasBlurBg;
    public Boolean hasNavigationBar;
    public Boolean hasShadowBg;
    public Boolean hasStatusBar;
    public Boolean hasStatusBarShadow;
    public boolean isCenterHorizontal;
    public boolean isClickThrough;
    public boolean isDarkTheme;
    public boolean isDestroyOnDismiss;
    public Boolean isDismissOnBackPressed;
    public Boolean isDismissOnTouchOutside;
    public Boolean isLightNavigationBar;
    public Boolean isMoveUpToKeyboard;
    public boolean isRequestFocus;
    public boolean isThreeDrag;
    public boolean isViewMode;
    public boolean keepScreenOn;
    public int maxHeight;
    public int maxWidth;
    public int navigationBarColor;
    public int offsetX;
    public int offsetY;
    public PopupAnimation popupAnimation;
    public int popupHeight;
    public PopupPosition popupPosition;
    public PopupType popupType = null;
    public int popupWidth;
    public boolean positionByWindowCenter;
    public int shadowBgColor;
    public int statusBarBgColor;
    public PointF touchPoint;
    public View watchView;
    public XPopupCallback xPopupCallback;

    public PopupInfo() {
        Boolean bool = Boolean.TRUE;
        this.isDismissOnBackPressed = bool;
        this.isDismissOnTouchOutside = bool;
        this.autoDismiss = bool;
        this.hasShadowBg = bool;
        Boolean bool2 = Boolean.FALSE;
        this.hasBlurBg = bool2;
        this.atView = null;
        this.watchView = null;
        this.popupAnimation = null;
        this.customAnimator = null;
        this.touchPoint = null;
        this.borderRadius = 15.0f;
        this.autoOpenSoftInput = bool2;
        this.isMoveUpToKeyboard = bool;
        this.popupPosition = null;
        this.hasStatusBarShadow = bool2;
        this.hasStatusBar = bool;
        this.hasNavigationBar = bool;
        this.navigationBarColor = 0;
        this.isLightNavigationBar = bool2;
        this.enableDrag = bool;
        this.isCenterHorizontal = false;
        this.isRequestFocus = true;
        this.autoFocusEditText = true;
        this.isClickThrough = false;
        this.isDarkTheme = false;
        this.enableShowWhenAppBackground = false;
        this.isThreeDrag = false;
        this.isDestroyOnDismiss = false;
        this.positionByWindowCenter = false;
        this.isViewMode = false;
        this.keepScreenOn = false;
        this.shadowBgColor = 0;
        this.animationDuration = -1;
        this.statusBarBgColor = 0;
    }

    public View getAtView() {
        return this.atView;
    }
}
