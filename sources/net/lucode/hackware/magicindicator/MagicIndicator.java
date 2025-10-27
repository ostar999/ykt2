package net.lucode.hackware.magicindicator;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import net.lucode.hackware.magicindicator.abs.IPagerNavigator;

/* loaded from: classes9.dex */
public class MagicIndicator extends FrameLayout {
    private IPagerNavigator mNavigator;

    public MagicIndicator(Context context) {
        super(context);
    }

    public IPagerNavigator getNavigator() {
        return this.mNavigator;
    }

    public void onPageScrollStateChanged(int i2) {
        IPagerNavigator iPagerNavigator = this.mNavigator;
        if (iPagerNavigator != null) {
            iPagerNavigator.onPageScrollStateChanged(i2);
        }
    }

    public void onPageScrolled(int i2, float f2, int i3) {
        IPagerNavigator iPagerNavigator = this.mNavigator;
        if (iPagerNavigator != null) {
            iPagerNavigator.onPageScrolled(i2, f2, i3);
        }
    }

    public void onPageSelected(int i2) {
        IPagerNavigator iPagerNavigator = this.mNavigator;
        if (iPagerNavigator != null) {
            iPagerNavigator.onPageSelected(i2);
        }
    }

    public void setNavigator(IPagerNavigator iPagerNavigator) {
        IPagerNavigator iPagerNavigator2 = this.mNavigator;
        if (iPagerNavigator2 == iPagerNavigator) {
            return;
        }
        if (iPagerNavigator2 != null) {
            iPagerNavigator2.onDetachFromMagicIndicator();
        }
        this.mNavigator = iPagerNavigator;
        removeAllViews();
        if (this.mNavigator instanceof View) {
            addView((View) this.mNavigator, new FrameLayout.LayoutParams(-1, -1));
            this.mNavigator.onAttachToMagicIndicator();
        }
    }

    public MagicIndicator(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }
}
