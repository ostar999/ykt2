package cn.webdemo.com.supporfragment.fragmentation_swipeback.core;

import cn.webdemo.com.supporfragment.fragmentation.SwipeBackLayout;

/* loaded from: classes.dex */
public interface ISwipeBackActivity {
    SwipeBackLayout getSwipeBackLayout();

    void setEdgeLevel(int i2);

    void setEdgeLevel(SwipeBackLayout.EdgeLevel edgeLevel);

    void setSwipeBackEnable(boolean z2);

    boolean swipeBackPriority();
}
