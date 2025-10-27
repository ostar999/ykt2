package cn.webdemo.com.supporfragment.fragmentation_swipeback;

import android.os.Bundle;
import android.view.View;
import androidx.annotation.FloatRange;
import androidx.annotation.Nullable;
import cn.webdemo.com.supporfragment.base.SupportFragment;
import cn.webdemo.com.supporfragment.fragmentation.SwipeBackLayout;
import cn.webdemo.com.supporfragment.fragmentation_swipeback.core.ISwipeBackFragment;
import cn.webdemo.com.supporfragment.fragmentation_swipeback.core.SwipeBackFragmentDelegate;

/* loaded from: classes.dex */
public class SwipeBackFragment extends SupportFragment implements ISwipeBackFragment {
    final SwipeBackFragmentDelegate mDelegate = new SwipeBackFragmentDelegate(this);

    @Override // cn.webdemo.com.supporfragment.fragmentation_swipeback.core.ISwipeBackFragment
    public View attachToSwipeBack(View view) {
        return this.mDelegate.attachToSwipeBack(view);
    }

    @Override // cn.webdemo.com.supporfragment.fragmentation_swipeback.core.ISwipeBackFragment
    public SwipeBackLayout getSwipeBackLayout() {
        return this.mDelegate.getSwipeBackLayout();
    }

    @Override // cn.webdemo.com.supporfragment.base.SupportFragment, androidx.fragment.app.Fragment
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        this.mDelegate.onCreate(bundle);
    }

    @Override // cn.webdemo.com.supporfragment.base.SupportFragment, androidx.fragment.app.Fragment
    public void onDestroyView() {
        this.mDelegate.onDestroyView();
        super.onDestroyView();
    }

    @Override // cn.webdemo.com.supporfragment.base.SupportFragment, androidx.fragment.app.Fragment
    public void onHiddenChanged(boolean z2) {
        super.onHiddenChanged(z2);
        this.mDelegate.onHiddenChanged(z2);
    }

    @Override // androidx.fragment.app.Fragment
    public void onViewCreated(View view, @Nullable Bundle bundle) {
        super.onViewCreated(view, bundle);
        this.mDelegate.onViewCreated(view, bundle);
    }

    @Override // cn.webdemo.com.supporfragment.fragmentation_swipeback.core.ISwipeBackFragment
    public void setEdgeLevel(SwipeBackLayout.EdgeLevel edgeLevel) throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
        this.mDelegate.setEdgeLevel(edgeLevel);
    }

    @Override // cn.webdemo.com.supporfragment.fragmentation_swipeback.core.ISwipeBackFragment
    public void setParallaxOffset(@FloatRange(from = 0.0d, to = 1.0d) float f2) {
        this.mDelegate.setParallaxOffset(f2);
    }

    @Override // cn.webdemo.com.supporfragment.fragmentation_swipeback.core.ISwipeBackFragment
    public void setSwipeBackEnable(boolean z2) {
        this.mDelegate.setSwipeBackEnable(z2);
    }

    @Override // cn.webdemo.com.supporfragment.fragmentation_swipeback.core.ISwipeBackFragment
    public void setEdgeLevel(int i2) throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
        this.mDelegate.setEdgeLevel(i2);
    }
}
