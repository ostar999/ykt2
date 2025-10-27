package cn.webdemo.com.supporfragment.fragmentation_swipeback.core;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.ViewGroup;
import androidx.fragment.app.FragmentActivity;
import cn.webdemo.com.supporfragment.ISupportActivity;
import cn.webdemo.com.supporfragment.fragmentation.SwipeBackLayout;

/* loaded from: classes.dex */
public class SwipeBackActivityDelegate {
    private FragmentActivity mActivity;
    private SwipeBackLayout mSwipeBackLayout;

    /* JADX WARN: Multi-variable type inference failed */
    public SwipeBackActivityDelegate(ISwipeBackActivity iSwipeBackActivity) {
        if (!(iSwipeBackActivity instanceof FragmentActivity) || !(iSwipeBackActivity instanceof ISupportActivity)) {
            throw new RuntimeException("Must extends FragmentActivity/AppCompatActivity and implements ISupportActivity");
        }
        this.mActivity = (FragmentActivity) iSwipeBackActivity;
    }

    private void onActivityCreate() {
        this.mActivity.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        this.mActivity.getWindow().getDecorView().setBackgroundColor(0);
        this.mSwipeBackLayout = new SwipeBackLayout(this.mActivity);
        this.mSwipeBackLayout.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
    }

    public SwipeBackLayout getSwipeBackLayout() {
        return this.mSwipeBackLayout;
    }

    public void onCreate(Bundle bundle) {
        onActivityCreate();
    }

    public void onPostCreate(Bundle bundle) {
        this.mSwipeBackLayout.attachToActivity(this.mActivity);
    }

    public void setEdgeLevel(SwipeBackLayout.EdgeLevel edgeLevel) throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
        this.mSwipeBackLayout.setEdgeLevel(edgeLevel);
    }

    public void setSwipeBackEnable(boolean z2) {
        this.mSwipeBackLayout.setEnableGesture(z2);
    }

    public boolean swipeBackPriority() {
        return this.mActivity.getSupportFragmentManager().getBackStackEntryCount() <= 1;
    }

    public void setEdgeLevel(int i2) throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
        this.mSwipeBackLayout.setEdgeLevel(i2);
    }
}
