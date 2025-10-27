package com.psychiatrygarden.widget.english;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import androidx.annotation.RequiresApi;
import com.psychiatrygarden.utils.ScreenUtil;

/* loaded from: classes6.dex */
public class CustomPopWindow {
    private int mAnimationStyle;
    private boolean mClippEnable;
    private View mContentView;
    private Context mContext;
    private int mHeight;
    private boolean mIgnoreCheekPress;
    private int mInputMode;
    private boolean mIsFocusable;
    private boolean mIsOutside;
    private PopupWindow.OnDismissListener mOnDismissListener;
    private View.OnTouchListener mOnTouchListener;
    private PopupWindow mPopupWindow;
    private int mResLayoutId;
    private int mSoftInputMode;
    private boolean mTouchable;
    private int mWidth;

    private void apply(PopupWindow popupWindow) {
        popupWindow.setClippingEnabled(this.mClippEnable);
        if (this.mIgnoreCheekPress) {
            popupWindow.setIgnoreCheekPress();
        }
        int i2 = this.mInputMode;
        if (i2 != -1) {
            popupWindow.setInputMethodMode(i2);
        }
        int i3 = this.mSoftInputMode;
        if (i3 != -1) {
            popupWindow.setSoftInputMode(i3);
        }
        PopupWindow.OnDismissListener onDismissListener = this.mOnDismissListener;
        if (onDismissListener != null) {
            popupWindow.setOnDismissListener(onDismissListener);
        }
        View.OnTouchListener onTouchListener = this.mOnTouchListener;
        if (onTouchListener != null) {
            popupWindow.setTouchInterceptor(onTouchListener);
        }
        popupWindow.setTouchable(this.mTouchable);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public PopupWindow build() {
        if (this.mContentView == null) {
            this.mContentView = LayoutInflater.from(this.mContext).inflate(this.mResLayoutId, (ViewGroup) null);
        }
        if (this.mWidth == 0 || this.mHeight == 0) {
            this.mPopupWindow = new PopupWindow(this.mContentView, -2, -2);
        } else {
            this.mPopupWindow = new PopupWindow(this.mContentView, this.mWidth, this.mHeight);
        }
        int i2 = this.mAnimationStyle;
        if (i2 != -1) {
            this.mPopupWindow.setAnimationStyle(i2);
        }
        apply(this.mPopupWindow);
        this.mPopupWindow.setFocusable(this.mIsFocusable);
        this.mPopupWindow.setBackgroundDrawable(new ColorDrawable(0));
        this.mPopupWindow.setOutsideTouchable(this.mIsOutside);
        if (this.mWidth == 0 || this.mHeight == 0) {
            this.mPopupWindow.getContentView().measure(0, 0);
            this.mWidth = this.mPopupWindow.getContentView().getMeasuredWidth();
            this.mHeight = this.mPopupWindow.getContentView().getMeasuredHeight();
        }
        this.mPopupWindow.update();
        return this.mPopupWindow;
    }

    public void dismiss() {
        PopupWindow popupWindow = this.mPopupWindow;
        if (popupWindow != null) {
            popupWindow.dismiss();
        }
    }

    public int getHeight() {
        return this.mHeight;
    }

    public int getWidth() {
        return this.mWidth;
    }

    public boolean isShowing() {
        PopupWindow popupWindow = this.mPopupWindow;
        if (popupWindow != null) {
            return popupWindow.isShowing();
        }
        return false;
    }

    public CustomPopWindow setFocusable(boolean isFocusable) {
        PopupWindow popupWindow = this.mPopupWindow;
        if (popupWindow != null) {
            popupWindow.setFocusable(isFocusable);
        }
        return this;
    }

    @RequiresApi(api = 19)
    public CustomPopWindow showAsDropDown(View anchor, int xOff, int yOff, int gravity) {
        PopupWindow popupWindow = this.mPopupWindow;
        if (popupWindow != null) {
            popupWindow.showAsDropDown(anchor, xOff, yOff, gravity);
        }
        return this;
    }

    public CustomPopWindow showAsDropDownCenter(View anchor, int yOff) {
        if (this.mPopupWindow != null) {
            this.mPopupWindow.showAsDropDown(anchor, (ScreenUtil.getScreenWidth((Activity) this.mContext) - this.mWidth) / 2, yOff);
        }
        return this;
    }

    public CustomPopWindow showAtLocation(View parent, int gravity, int x2, int y2) {
        PopupWindow popupWindow = this.mPopupWindow;
        if (popupWindow != null) {
            popupWindow.showAtLocation(parent, gravity, x2, y2);
        }
        return this;
    }

    @RequiresApi(api = 19)
    public CustomPopWindow showLocation(View anchor, int gravity, int xOff, int yOff) {
        PopupWindow popupWindow = this.mPopupWindow;
        if (popupWindow != null) {
            popupWindow.showAtLocation(anchor, gravity, xOff, yOff);
        }
        return this;
    }

    public void update(View anchor, int xoff, int yoff, int width, int height) {
        PopupWindow popupWindow = this.mPopupWindow;
        if (popupWindow != null) {
            popupWindow.update(anchor, xoff, yoff, width, height);
        }
    }

    public static class PopupWindowBuilder {
        private CustomPopWindow mCustomPopWindow;

        public PopupWindowBuilder(Context context) {
            this.mCustomPopWindow = new CustomPopWindow(context);
        }

        public CustomPopWindow create() {
            this.mCustomPopWindow.build();
            return this.mCustomPopWindow;
        }

        public PopupWindowBuilder setAnimationStyle(int animationStyle) {
            this.mCustomPopWindow.mAnimationStyle = animationStyle;
            return this;
        }

        public PopupWindowBuilder setClippingEnable(boolean enable) {
            this.mCustomPopWindow.mClippEnable = enable;
            return this;
        }

        public PopupWindowBuilder setFocusable(boolean focusable) {
            this.mCustomPopWindow.mIsFocusable = focusable;
            return this;
        }

        public PopupWindowBuilder setHeight(int height) {
            this.mCustomPopWindow.mHeight = height;
            return this;
        }

        public PopupWindowBuilder setIgnoreCheekPress(boolean ignoreCheekPress) {
            this.mCustomPopWindow.mIgnoreCheekPress = ignoreCheekPress;
            return this;
        }

        public PopupWindowBuilder setInputMethodMode(int mode) {
            this.mCustomPopWindow.mInputMode = mode;
            return this;
        }

        public PopupWindowBuilder setOnDissmissListener(PopupWindow.OnDismissListener onDissmissListener) {
            this.mCustomPopWindow.mOnDismissListener = onDissmissListener;
            return this;
        }

        public PopupWindowBuilder setOutsideTouchable(boolean outsideTouchable) {
            this.mCustomPopWindow.mIsOutside = outsideTouchable;
            return this;
        }

        public PopupWindowBuilder setSoftInputMode(int softInputMode) {
            this.mCustomPopWindow.mSoftInputMode = softInputMode;
            return this;
        }

        public PopupWindowBuilder setTouchIntercepter(View.OnTouchListener touchIntercepter) {
            this.mCustomPopWindow.mOnTouchListener = touchIntercepter;
            return this;
        }

        public PopupWindowBuilder setTouchable(boolean touchable) {
            this.mCustomPopWindow.mTouchable = touchable;
            return this;
        }

        public PopupWindowBuilder setView(int resLayoutId) {
            this.mCustomPopWindow.mResLayoutId = resLayoutId;
            this.mCustomPopWindow.mContentView = null;
            return this;
        }

        @RequiresApi(api = 17)
        public PopupWindowBuilder setWidth(Context context, int width) {
            this.mCustomPopWindow.mWidth = ScreenUtil.getPxByDp(context, width);
            return this;
        }

        @RequiresApi(api = 17)
        public PopupWindowBuilder size(Activity activity, int width, int height) {
            this.mCustomPopWindow.mWidth = ScreenUtil.getPxByDp((Context) activity, width);
            this.mCustomPopWindow.mHeight = height == -1 ? ScreenUtil.getScreenHeight(activity) : ScreenUtil.getPxByDp((Context) activity, height);
            return this;
        }

        public PopupWindowBuilder setView(View view) {
            this.mCustomPopWindow.mContentView = view;
            this.mCustomPopWindow.mResLayoutId = -1;
            return this;
        }
    }

    private CustomPopWindow(Context context) {
        this.mIsFocusable = true;
        this.mIsOutside = true;
        this.mResLayoutId = -1;
        this.mAnimationStyle = -1;
        this.mClippEnable = true;
        this.mIgnoreCheekPress = false;
        this.mInputMode = -1;
        this.mSoftInputMode = -1;
        this.mTouchable = true;
        this.mContext = context;
    }
}
