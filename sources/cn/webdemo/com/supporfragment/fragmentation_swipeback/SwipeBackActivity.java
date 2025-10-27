package cn.webdemo.com.supporfragment.fragmentation_swipeback;

import android.os.Bundle;
import cn.webdemo.com.supporfragment.base.SupportActivity;
import cn.webdemo.com.supporfragment.fragmentation.SwipeBackLayout;
import cn.webdemo.com.supporfragment.fragmentation_swipeback.core.ISwipeBackActivity;
import cn.webdemo.com.supporfragment.fragmentation_swipeback.core.SwipeBackActivityDelegate;

/* loaded from: classes.dex */
public class SwipeBackActivity extends SupportActivity implements ISwipeBackActivity {
    final SwipeBackActivityDelegate mDelegate = new SwipeBackActivityDelegate(this);

    @Override // cn.webdemo.com.supporfragment.fragmentation_swipeback.core.ISwipeBackActivity
    public SwipeBackLayout getSwipeBackLayout() {
        return this.mDelegate.getSwipeBackLayout();
    }

    @Override // cn.webdemo.com.supporfragment.base.SupportActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mDelegate.onCreate(bundle);
    }

    @Override // cn.webdemo.com.supporfragment.base.SupportActivity, androidx.appcompat.app.AppCompatActivity, android.app.Activity
    public void onPostCreate(Bundle bundle) {
        super.onPostCreate(bundle);
        this.mDelegate.onPostCreate(bundle);
    }

    @Override // cn.webdemo.com.supporfragment.fragmentation_swipeback.core.ISwipeBackActivity
    public void setEdgeLevel(SwipeBackLayout.EdgeLevel edgeLevel) throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
        this.mDelegate.setEdgeLevel(edgeLevel);
    }

    @Override // cn.webdemo.com.supporfragment.fragmentation_swipeback.core.ISwipeBackActivity
    public void setSwipeBackEnable(boolean z2) {
        this.mDelegate.setSwipeBackEnable(z2);
    }

    @Override // cn.webdemo.com.supporfragment.fragmentation_swipeback.core.ISwipeBackActivity
    public boolean swipeBackPriority() {
        return this.mDelegate.swipeBackPriority();
    }

    @Override // cn.webdemo.com.supporfragment.fragmentation_swipeback.core.ISwipeBackActivity
    public void setEdgeLevel(int i2) throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
        this.mDelegate.setEdgeLevel(i2);
    }
}
