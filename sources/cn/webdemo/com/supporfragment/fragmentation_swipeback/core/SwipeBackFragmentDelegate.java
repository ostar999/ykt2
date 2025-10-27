package cn.webdemo.com.supporfragment.fragmentation_swipeback.core;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.FloatRange;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import cn.webdemo.com.supporfragment.ISupportFragment;
import cn.webdemo.com.supporfragment.fragmentation.SwipeBackLayout;

/* loaded from: classes.dex */
public class SwipeBackFragmentDelegate {
    private Fragment mFragment;
    private ISupportFragment mSupport;
    private SwipeBackLayout mSwipeBackLayout;

    /* JADX WARN: Multi-variable type inference failed */
    public SwipeBackFragmentDelegate(ISwipeBackFragment iSwipeBackFragment) {
        if (!(iSwipeBackFragment instanceof Fragment) || !(iSwipeBackFragment instanceof ISupportFragment)) {
            throw new RuntimeException("Must extends Fragment and implements ISupportFragment!");
        }
        this.mFragment = (Fragment) iSwipeBackFragment;
        this.mSupport = (ISupportFragment) iSwipeBackFragment;
    }

    private void onFragmentCreate() {
        if (this.mFragment.getContext() == null) {
            return;
        }
        this.mSwipeBackLayout = new SwipeBackLayout(this.mFragment.getContext());
        this.mSwipeBackLayout.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
        this.mSwipeBackLayout.setBackgroundColor(0);
    }

    public View attachToSwipeBack(View view) {
        this.mSwipeBackLayout.attachToFragment(this.mSupport, view);
        return this.mSwipeBackLayout;
    }

    public SwipeBackLayout getSwipeBackLayout() {
        return this.mSwipeBackLayout;
    }

    public void onCreate(@Nullable Bundle bundle) {
        onFragmentCreate();
    }

    public void onDestroyView() {
        this.mSwipeBackLayout.internalCallOnDestroyView();
    }

    public void onHiddenChanged(boolean z2) {
        SwipeBackLayout swipeBackLayout;
        if (!z2 || (swipeBackLayout = this.mSwipeBackLayout) == null) {
            return;
        }
        swipeBackLayout.hiddenFragment();
    }

    public void onViewCreated(View view, @Nullable Bundle bundle) {
        if (!(view instanceof SwipeBackLayout)) {
            this.mSupport.getSupportDelegate().setBackground(view);
        } else {
            this.mSupport.getSupportDelegate().setBackground(((SwipeBackLayout) view).getChildAt(0));
        }
    }

    public void setEdgeLevel(SwipeBackLayout.EdgeLevel edgeLevel) throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
        this.mSwipeBackLayout.setEdgeLevel(edgeLevel);
    }

    public void setParallaxOffset(@FloatRange(from = 0.0d, to = 1.0d) float f2) {
        this.mSwipeBackLayout.setParallaxOffset(f2);
    }

    public void setSwipeBackEnable(boolean z2) {
        this.mSwipeBackLayout.setEnableGesture(z2);
    }

    public void setEdgeLevel(int i2) throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
        this.mSwipeBackLayout.setEdgeLevel(i2);
    }
}
