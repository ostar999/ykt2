package com.hyphenate.easeui.modules.menu;

import android.R;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;

/* loaded from: classes4.dex */
public class EasePopupWindow extends PopupWindow {
    private Drawable mBackgroundDrawable;
    private boolean mCloseChangeBg;
    private Context mContext;
    private float mShowAlpha = 0.88f;

    public interface OnPopupWindowDismissListener {
        void onDismiss(PopupWindow popupWindow);
    }

    public interface OnPopupWindowItemClickListener {
        boolean onMenuItemClick(MenuItemBean menuItemBean);
    }

    public EasePopupWindow(Context context) {
        this.mContext = context;
        initBasePopupWindow();
    }

    private void addKeyListener(View view) {
        if (view != null) {
            view.setFocusable(true);
            view.setFocusableInTouchMode(true);
            view.setOnKeyListener(new View.OnKeyListener() { // from class: com.hyphenate.easeui.modules.menu.EasePopupWindow.3
                @Override // android.view.View.OnKeyListener
                public boolean onKey(View view2, int i2, KeyEvent keyEvent) {
                    if (i2 != 4) {
                        return false;
                    }
                    EasePopupWindow.this.dismiss();
                    return true;
                }
            });
        }
    }

    private ValueAnimator dismissAnimator() {
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(this.mShowAlpha, 1.0f);
        valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.hyphenate.easeui.modules.menu.EasePopupWindow.2
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float fFloatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                if (EasePopupWindow.this.mCloseChangeBg) {
                    return;
                }
                EasePopupWindow.this.setWindowBackgroundAlpha(fFloatValue);
            }
        });
        valueAnimatorOfFloat.setDuration(320L);
        return valueAnimatorOfFloat;
    }

    private void initBasePopupWindow() {
        setAnimationStyle(R.style.Animation.Dialog);
        setHeight(-2);
        setWidth(-2);
        setOutsideTouchable(true);
        setFocusable(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setWindowBackgroundAlpha(float f2) {
        Window window = ((Activity) getContext()).getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.alpha = f2;
        window.setAttributes(attributes);
    }

    private ValueAnimator showAnimator() {
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(1.0f, this.mShowAlpha);
        valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.hyphenate.easeui.modules.menu.EasePopupWindow.1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float fFloatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                if (EasePopupWindow.this.mCloseChangeBg) {
                    return;
                }
                EasePopupWindow.this.setWindowBackgroundAlpha(fFloatValue);
            }
        });
        valueAnimatorOfFloat.setDuration(360L);
        return valueAnimatorOfFloat;
    }

    @Override // android.widget.PopupWindow
    public void dismiss() {
        super.dismiss();
        dismissAnimator().start();
    }

    public Context getContext() {
        return this.mContext;
    }

    public void setBackgroundAlpha(float f2) {
        this.mShowAlpha = f2;
    }

    @Override // android.widget.PopupWindow
    public void setBackgroundDrawable(Drawable drawable) {
        this.mBackgroundDrawable = drawable;
        setOutsideTouchable(isOutsideTouchable());
    }

    @Override // android.widget.PopupWindow
    public void setContentView(View view) {
        if (view != null) {
            view.measure(0, 0);
            super.setContentView(view);
            addKeyListener(view);
        }
    }

    @Override // android.widget.PopupWindow
    public void setOutsideTouchable(boolean z2) {
        super.setOutsideTouchable(z2);
        if (!z2) {
            super.setBackgroundDrawable(null);
            return;
        }
        if (this.mBackgroundDrawable == null) {
            this.mBackgroundDrawable = new ColorDrawable(0);
        }
        super.setBackgroundDrawable(this.mBackgroundDrawable);
    }

    @Override // android.widget.PopupWindow
    public void showAsDropDown(View view) {
        super.showAsDropDown(view);
        showAnimator().start();
    }

    @Override // android.widget.PopupWindow
    public void showAtLocation(View view, int i2, int i3, int i4) {
        super.showAtLocation(view, i2, i3, i4);
        showAnimator().start();
    }

    @Override // android.widget.PopupWindow
    public void showAsDropDown(View view, int i2, int i3) {
        super.showAsDropDown(view, i2, i3);
        showAnimator().start();
    }

    public EasePopupWindow(Context context, boolean z2) {
        this.mContext = context;
        this.mCloseChangeBg = z2;
        initBasePopupWindow();
    }

    @Override // android.widget.PopupWindow
    public void showAsDropDown(View view, int i2, int i3, int i4) {
        super.showAsDropDown(view, i2, i3, i4);
        showAnimator().start();
    }
}
