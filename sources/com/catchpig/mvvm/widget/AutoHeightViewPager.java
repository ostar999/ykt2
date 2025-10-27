package com.catchpig.mvvm.widget;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import androidx.viewpager.widget.ViewPager;
import java.util.HashMap;
import java.util.LinkedHashMap;

/* loaded from: classes2.dex */
public class AutoHeightViewPager extends ViewPager {
    private int current;
    private int height;
    private HashMap<Integer, View> mChildrenViews;
    private boolean scrollble;

    public AutoHeightViewPager(Context context) {
        super(context);
        this.height = 0;
        this.mChildrenViews = new LinkedHashMap();
        this.scrollble = true;
    }

    public boolean isScrollble() {
        return this.scrollble;
    }

    @Override // androidx.viewpager.widget.ViewPager, android.view.View
    public void onMeasure(int i2, int i3) throws Resources.NotFoundException {
        int size = this.mChildrenViews.size();
        int i4 = this.current;
        if (size > i4) {
            View view = this.mChildrenViews.get(Integer.valueOf(i4));
            view.measure(i2, View.MeasureSpec.makeMeasureSpec(0, 0));
            this.height = view.getMeasuredHeight();
        }
        super.onMeasure(i2, View.MeasureSpec.makeMeasureSpec(this.height, 1073741824));
    }

    @Override // androidx.viewpager.widget.ViewPager, android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (this.scrollble) {
            return super.onTouchEvent(motionEvent);
        }
        return true;
    }

    public void resetHeight(int i2) {
        this.current = i2;
        if (this.mChildrenViews.size() > i2) {
            ViewGroup.LayoutParams layoutParams = getLayoutParams();
            if (layoutParams == null) {
                layoutParams = new ViewGroup.LayoutParams(-1, this.height);
            } else {
                layoutParams.height = this.height;
            }
            setLayoutParams(layoutParams);
        }
    }

    public void setObjectForPosition(View view, int i2) {
        this.mChildrenViews.put(Integer.valueOf(i2), view);
    }

    public void setScrollble(boolean z2) {
        this.scrollble = z2;
    }

    public AutoHeightViewPager(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.height = 0;
        this.mChildrenViews = new LinkedHashMap();
        this.scrollble = true;
    }
}
