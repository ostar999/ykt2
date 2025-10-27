package com.psychiatrygarden.widget;

import android.view.MotionEvent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.utils.ToastUtil;

/* loaded from: classes6.dex */
public class LessSensitiveItemTouchListener implements RecyclerView.OnItemTouchListener {
    private static final int SWIPE_THRESHOLD = 200;
    private float initialX;
    private ViewPager2 mViewPager;
    private int total;

    public LessSensitiveItemTouchListener(ViewPager2 viewPager, int total) {
        this.mViewPager = viewPager;
        this.total = total;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.OnItemTouchListener
    public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e2) {
        ViewPager2 viewPager2;
        int action = e2.getAction();
        if (action == 0) {
            this.initialX = e2.getX();
        } else if (action == 1) {
            if (Math.abs(e2.getX() - this.initialX) > 300.0f && (viewPager2 = this.mViewPager) != null && viewPager2.getCurrentItem() == this.total - 1) {
                ToastUtil.shortToast(ProjectApp.instance(), "已是最后一题");
            }
            return Math.abs(e2.getX() - this.initialX) != 0.0f && Math.abs(e2.getX() - this.initialX) < 200.0f;
        }
        return false;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.OnItemTouchListener
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
    }

    @Override // androidx.recyclerview.widget.RecyclerView.OnItemTouchListener
    public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e2) {
    }
}
