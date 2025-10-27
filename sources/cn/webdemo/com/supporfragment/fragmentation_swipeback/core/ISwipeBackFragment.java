package cn.webdemo.com.supporfragment.fragmentation_swipeback.core;

import android.view.View;
import androidx.annotation.FloatRange;
import cn.webdemo.com.supporfragment.fragmentation.SwipeBackLayout;

/* loaded from: classes.dex */
public interface ISwipeBackFragment {
    View attachToSwipeBack(View view);

    SwipeBackLayout getSwipeBackLayout();

    void setEdgeLevel(int i2);

    void setEdgeLevel(SwipeBackLayout.EdgeLevel edgeLevel);

    void setParallaxOffset(@FloatRange(from = 0.0d, to = 1.0d) float f2);

    void setSwipeBackEnable(boolean z2);
}
