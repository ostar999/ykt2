package com.actionbarsherlock.widget.swipeback;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.actionbarsherlock.R;
import com.actionbarsherlock.widget.swipeback.SwipeBackLayout;

/* loaded from: classes.dex */
public class SwipeBackActivityHelper {
    private Activity mActivity;
    private SwipeBackLayout mSwipeBackLayout;

    public SwipeBackActivityHelper(Activity activity) {
        this.mActivity = activity;
    }

    public View findViewById(int i2) {
        SwipeBackLayout swipeBackLayout = this.mSwipeBackLayout;
        if (swipeBackLayout != null) {
            return swipeBackLayout.findViewById(i2);
        }
        return null;
    }

    public SwipeBackLayout getSwipeBackLayout() {
        return this.mSwipeBackLayout;
    }

    public void onActivityCreate() {
        this.mActivity.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        this.mActivity.getWindow().getDecorView().setBackgroundDrawable(null);
        SwipeBackLayout swipeBackLayout = (SwipeBackLayout) LayoutInflater.from(this.mActivity).inflate(R.layout.swipeback_layout, (ViewGroup) null);
        this.mSwipeBackLayout = swipeBackLayout;
        swipeBackLayout.addSwipeListener(new SwipeBackLayout.SwipeListener() { // from class: com.actionbarsherlock.widget.swipeback.SwipeBackActivityHelper.1
            @Override // com.actionbarsherlock.widget.swipeback.SwipeBackLayout.SwipeListener
            public void onEdgeTouch(int i2) {
                Utils.convertActivityToTranslucent(SwipeBackActivityHelper.this.mActivity);
            }

            @Override // com.actionbarsherlock.widget.swipeback.SwipeBackLayout.SwipeListener
            public void onScrollOverThreshold() {
            }

            @Override // com.actionbarsherlock.widget.swipeback.SwipeBackLayout.SwipeListener
            public void onScrollStateChange(int i2, float f2) {
            }
        });
    }

    public void onPostCreate() {
        this.mSwipeBackLayout.attachToActivity(this.mActivity);
    }
}
