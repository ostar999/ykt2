package com.easefun.polyv.livecommon.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import com.plv.foundationsdk.utils.PLVControlUtils;

/* loaded from: classes3.dex */
public class PLVSoftView extends LinearLayout {
    public static final byte KEYBOARD_STATE_HIDE = -2;
    public static final byte KEYBOARD_STATE_INIT = -1;
    public static final byte KEYBOARD_STATE_SHOW = -3;
    private boolean mHasInit;
    private boolean mHasKeyboard;
    private int mHeightOrigin;
    private int mStatusBarHeight;
    private IOnKeyboardStateChangedListener onKeyboardStateChangedListener;

    public interface IOnKeyboardStateChangedListener {
        void onKeyboardStateChanged(int state);
    }

    public PLVSoftView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mHasInit = false;
        this.mHasKeyboard = false;
        this.mStatusBarHeight = 0;
        this.mStatusBarHeight = PLVControlUtils.getStatusBarHeight(context);
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
    public void onLayout(boolean changed, int l2, int t2, int r2, int b3) {
        super.onLayout(changed, l2, t2, r2, b3);
        if (this.mHasInit) {
            int i2 = this.mHeightOrigin;
            if (i2 < b3) {
                i2 = b3;
            }
            this.mHeightOrigin = i2;
        } else {
            this.mHasInit = true;
            this.mHeightOrigin = b3;
            IOnKeyboardStateChangedListener iOnKeyboardStateChangedListener = this.onKeyboardStateChangedListener;
            if (iOnKeyboardStateChangedListener != null) {
                iOnKeyboardStateChangedListener.onKeyboardStateChanged(-1);
            }
        }
        if (this.mHeightOrigin - this.mStatusBarHeight > b3) {
            this.mHasKeyboard = true;
            IOnKeyboardStateChangedListener iOnKeyboardStateChangedListener2 = this.onKeyboardStateChangedListener;
            if (iOnKeyboardStateChangedListener2 != null) {
                iOnKeyboardStateChangedListener2.onKeyboardStateChanged(-3);
            }
        }
        if (this.mHasKeyboard) {
            int i3 = this.mHeightOrigin;
            if (i3 == b3 || i3 - this.mStatusBarHeight == b3) {
                this.mHasKeyboard = false;
                IOnKeyboardStateChangedListener iOnKeyboardStateChangedListener3 = this.onKeyboardStateChangedListener;
                if (iOnKeyboardStateChangedListener3 != null) {
                    iOnKeyboardStateChangedListener3.onKeyboardStateChanged(-2);
                }
            }
        }
    }

    public void setOnKeyboardStateChangedListener(IOnKeyboardStateChangedListener onKeyboardStateChangedListener) {
        this.onKeyboardStateChangedListener = onKeyboardStateChangedListener;
    }

    public PLVSoftView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mHasInit = false;
        this.mHasKeyboard = false;
        this.mStatusBarHeight = 0;
        this.mStatusBarHeight = PLVControlUtils.getStatusBarHeight(context);
    }

    public PLVSoftView(Context context) {
        super(context);
        this.mHasInit = false;
        this.mHasKeyboard = false;
        this.mStatusBarHeight = 0;
        this.mStatusBarHeight = PLVControlUtils.getStatusBarHeight(context);
    }
}
