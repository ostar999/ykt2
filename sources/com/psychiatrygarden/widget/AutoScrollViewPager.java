package com.psychiatrygarden.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

/* loaded from: classes6.dex */
public class AutoScrollViewPager extends ViewPager {
    private OnPageSwitchListener mListener;
    private float startX;

    public interface OnPageSwitchListener {
        void nextPage();

        void previousPage();
    }

    public AutoScrollViewPager(@NonNull Context context) {
        super(context);
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (getAdapter() == null) {
            return super.dispatchTouchEvent(ev);
        }
        if (ev.getAction() == 0 && (getCurrentItem() == 0 || getCurrentItem() == getAdapter().getCount() - 1)) {
            this.startX = ev.getRawX();
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override // androidx.viewpager.widget.ViewPager, android.view.View
    public boolean onTouchEvent(MotionEvent ev) {
        if (getAdapter() == null) {
            return super.onTouchEvent(ev);
        }
        if ((ev.getAction() & 255) == 1) {
            if (getCurrentItem() == 0) {
                if (ev.getRawX() - this.startX > getWidth() / 2.0f) {
                    OnPageSwitchListener onPageSwitchListener = this.mListener;
                    if (onPageSwitchListener != null) {
                        onPageSwitchListener.previousPage();
                    }
                    return true;
                }
            } else if (getCurrentItem() == getAdapter().getCount() - 1 && this.startX - Math.abs(ev.getRawX()) > getWidth() / 2.0f) {
                OnPageSwitchListener onPageSwitchListener2 = this.mListener;
                if (onPageSwitchListener2 != null) {
                    onPageSwitchListener2.nextPage();
                }
                return true;
            }
        }
        return super.onTouchEvent(ev);
    }

    public void setSwitchListener(OnPageSwitchListener listener) {
        this.mListener = listener;
    }

    public AutoScrollViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
}
