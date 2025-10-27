package cn.lightsky.infiniteindicator;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;

/* loaded from: classes.dex */
public class DurationScroller extends Scroller {
    private double scrollFactor;

    public DurationScroller(Context context) {
        super(context);
        this.scrollFactor = 1.2d;
    }

    public void setScrollDurationFactor(double d3) {
        this.scrollFactor = d3;
    }

    @Override // android.widget.Scroller
    public void startScroll(int i2, int i3, int i4, int i5, int i6) {
        super.startScroll(i2, i3, i4, i5, (int) (i6 * this.scrollFactor));
    }

    public DurationScroller(Context context, Interpolator interpolator) {
        super(context, interpolator);
        this.scrollFactor = 1.2d;
    }
}
