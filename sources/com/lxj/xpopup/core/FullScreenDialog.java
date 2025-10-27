package com.lxj.xpopup.core;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import androidx.annotation.NonNull;
import com.lxj.xpopup.R;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.util.FuckRomUtils;
import com.lxj.xpopup.util.XPopupUtils;
import com.yikaobang.yixue.R2;

/* loaded from: classes4.dex */
public class FullScreenDialog extends Dialog {
    BasePopupView contentView;

    public FullScreenDialog(@NonNull Context context) {
        super(context, R.style._XPopup_TransparentDialog);
    }

    private int getNavigationBarColor() {
        int i2 = this.contentView.popupInfo.navigationBarColor;
        return i2 == 0 ? XPopup.getNavigationBarColor() : i2;
    }

    private String getResNameById(int i2) {
        try {
            return getContext().getResources().getResourceEntryName(i2);
        } catch (Exception unused) {
            return "";
        }
    }

    public void autoSetStatusBarMode() {
        if (!this.contentView.popupInfo.hasStatusBar.booleanValue()) {
            getWindow().getDecorView().setSystemUiVisibility(((ViewGroup) getWindow().getDecorView()).getSystemUiVisibility() | R2.attr.defaultIntentData);
        }
        View decorView = getWindow().getDecorView();
        int systemUiVisibility = decorView.getSystemUiVisibility();
        decorView.setSystemUiVisibility(isActivityStatusBarLightMode() ? systemUiVisibility | 8192 : systemUiVisibility & (-8193));
    }

    @Override // android.app.Dialog, android.view.Window.Callback
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        if (isFuckVIVORoom()) {
            motionEvent.setLocation(motionEvent.getX(), motionEvent.getY() + XPopupUtils.getStatusBarHeight());
        }
        return super.dispatchTouchEvent(motionEvent);
    }

    public void hideNavigationBar() {
        ViewGroup viewGroup = (ViewGroup) getWindow().getDecorView();
        int childCount = viewGroup.getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = viewGroup.getChildAt(i2);
            int id = childAt.getId();
            if (id != -1 && "navigationBarBackground".equals(getResNameById(id))) {
                childAt.setVisibility(4);
            }
        }
        viewGroup.setSystemUiVisibility(viewGroup.getSystemUiVisibility() | R2.color.color_qzone);
    }

    public boolean isActivityStatusBarLightMode() {
        return (((Activity) this.contentView.getContext()).getWindow().getDecorView().getSystemUiVisibility() & 8192) != 0;
    }

    public boolean isFuckVIVORoom() {
        String str = Build.MODEL;
        boolean z2 = str.contains("Y") || str.contains("y");
        if (!FuckRomUtils.isVivo()) {
            return false;
        }
        int i2 = Build.VERSION.SDK_INT;
        return (i2 == 26 || i2 == 27) && z2;
    }

    @Override // android.app.Dialog
    public void onCreate(Bundle bundle) {
        BasePopupView basePopupView;
        PopupInfo popupInfo;
        super.onCreate(bundle);
        if (getWindow() == null || (basePopupView = this.contentView) == null || (popupInfo = basePopupView.popupInfo) == null) {
            return;
        }
        if (popupInfo.enableShowWhenAppBackground) {
            if (Build.VERSION.SDK_INT >= 26) {
                getWindow().setType(R2.attr.index_change_img);
            } else {
                getWindow().setType(2003);
            }
        }
        if (this.contentView.popupInfo.keepScreenOn) {
            getWindow().addFlags(128);
        }
        getWindow().setBackgroundDrawable(null);
        getWindow().getDecorView().setPadding(0, 0, 0, 0);
        getWindow().setFlags(16777216, 16777216);
        getWindow().setSoftInputMode(16);
        getWindow().getDecorView().setSystemUiVisibility(1280);
        getWindow().setLayout(-1, Math.max(XPopupUtils.getAppHeight(getContext()), XPopupUtils.getScreenHeight(getContext())));
        if (isFuckVIVORoom()) {
            getWindow().getDecorView().setTranslationY(-XPopupUtils.getStatusBarHeight());
        }
        setWindowFlag(201326592, false);
        getWindow().setStatusBarColor(0);
        int navigationBarColor = getNavigationBarColor();
        if (navigationBarColor != 0) {
            getWindow().setNavigationBarColor(navigationBarColor);
        }
        getWindow().addFlags(Integer.MIN_VALUE);
        if (!this.contentView.popupInfo.hasNavigationBar.booleanValue()) {
            hideNavigationBar();
        }
        if (!this.contentView.popupInfo.isRequestFocus) {
            getWindow().setFlags(8, 8);
        }
        autoSetStatusBarMode();
        setNavBarLightMode();
        setContentView(this.contentView);
    }

    public FullScreenDialog setContent(BasePopupView basePopupView) {
        if (basePopupView.getParent() != null) {
            ((ViewGroup) basePopupView.getParent()).removeView(basePopupView);
        }
        this.contentView = basePopupView;
        return this;
    }

    public void setNavBarLightMode() {
        if (Build.VERSION.SDK_INT >= 26) {
            View decorView = getWindow().getDecorView();
            int systemUiVisibility = decorView.getSystemUiVisibility();
            decorView.setSystemUiVisibility(this.contentView.popupInfo.isLightNavigationBar.booleanValue() ? systemUiVisibility | 16 : systemUiVisibility & (-17));
        }
    }

    public void setWindowFlag(int i2, boolean z2) {
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        if (z2) {
            attributes.flags = i2 | attributes.flags;
        } else {
            attributes.flags = (~i2) & attributes.flags;
        }
        getWindow().setAttributes(attributes);
    }
}
