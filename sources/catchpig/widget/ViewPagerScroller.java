package catchpig.widget;

import android.content.Context;
import android.widget.Scroller;
import androidx.viewpager.widget.ViewPager;
import java.lang.reflect.Field;

/* loaded from: classes.dex */
public class ViewPagerScroller extends Scroller {
    private int mScrollDuration;

    public ViewPagerScroller(Context context) {
        super(context);
    }

    public void initViewPagerScroll(ViewPager viewPager) throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
        try {
            Field declaredField = ViewPager.class.getDeclaredField("mScroller");
            declaredField.setAccessible(true);
            declaredField.set(viewPager, this);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public void setScrollDuration(int duration) {
        this.mScrollDuration = duration;
    }

    @Override // android.widget.Scroller
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        super.startScroll(startX, startY, dx, dy, this.mScrollDuration);
    }

    @Override // android.widget.Scroller
    public void startScroll(int startX, int startY, int dx, int dy) {
        super.startScroll(startX, startY, dx, dy, this.mScrollDuration);
    }
}
