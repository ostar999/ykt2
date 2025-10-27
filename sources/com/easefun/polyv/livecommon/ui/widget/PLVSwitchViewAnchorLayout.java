package com.easefun.polyv.livecommon.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/* loaded from: classes3.dex */
public class PLVSwitchViewAnchorLayout extends FrameLayout {
    private boolean isViewSwitched;
    private IPLVSwitchViewAnchorLayoutListener onSwitchListener;

    public static class IPLVSwitchViewAnchorLayoutListener {
        public void onSwitchBackAfter() {
        }

        public void onSwitchBackBefore() {
        }

        public void onSwitchElsewhereAfter() {
        }

        public void onSwitchElsewhereBefore() {
        }
    }

    public PLVSwitchViewAnchorLayout(@NonNull Context context) {
        this(context, null);
    }

    public View getSwitchView() throws IllegalAccessException {
        int childCount = getChildCount();
        if (childCount == 0) {
            throw new IllegalAccessException("child count must not be 0!");
        }
        if (childCount <= 1) {
            return getChildAt(0);
        }
        throw new IllegalAccessException("child count must exactly be 1");
    }

    public boolean isViewSwitched() {
        return this.isViewSwitched;
    }

    public void notifySwitchBackAfter() {
        IPLVSwitchViewAnchorLayoutListener iPLVSwitchViewAnchorLayoutListener = this.onSwitchListener;
        if (iPLVSwitchViewAnchorLayoutListener != null) {
            iPLVSwitchViewAnchorLayoutListener.onSwitchBackAfter();
        }
        this.isViewSwitched = false;
    }

    public void notifySwitchBackBefore() {
        IPLVSwitchViewAnchorLayoutListener iPLVSwitchViewAnchorLayoutListener = this.onSwitchListener;
        if (iPLVSwitchViewAnchorLayoutListener != null) {
            iPLVSwitchViewAnchorLayoutListener.onSwitchBackBefore();
        }
    }

    public void notifySwitchElsewhereAfter() {
        IPLVSwitchViewAnchorLayoutListener iPLVSwitchViewAnchorLayoutListener = this.onSwitchListener;
        if (iPLVSwitchViewAnchorLayoutListener != null) {
            iPLVSwitchViewAnchorLayoutListener.onSwitchElsewhereAfter();
        }
        this.isViewSwitched = true;
    }

    public void notifySwitchElsewhereBefore() {
        IPLVSwitchViewAnchorLayoutListener iPLVSwitchViewAnchorLayoutListener = this.onSwitchListener;
        if (iPLVSwitchViewAnchorLayoutListener != null) {
            iPLVSwitchViewAnchorLayoutListener.onSwitchElsewhereBefore();
        }
    }

    public void setOnSwitchListener(IPLVSwitchViewAnchorLayoutListener onSwitchListener) {
        this.onSwitchListener = onSwitchListener;
    }

    public PLVSwitchViewAnchorLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PLVSwitchViewAnchorLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.isViewSwitched = false;
    }
}
