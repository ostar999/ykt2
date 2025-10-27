package com.easefun.polyv.livecommon.ui.widget.magicindicator;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import com.easefun.polyv.livecommon.ui.widget.magicindicator.abs.IPLVPagerNavigator;

/* loaded from: classes3.dex */
public class PLVMagicIndicator extends FrameLayout {
    private IPLVPagerNavigator mNavigator;

    public PLVMagicIndicator(Context context) {
        super(context);
    }

    public IPLVPagerNavigator getNavigator() {
        return this.mNavigator;
    }

    public void onPageScrollStateChanged(int state) {
        IPLVPagerNavigator iPLVPagerNavigator = this.mNavigator;
        if (iPLVPagerNavigator != null) {
            iPLVPagerNavigator.onPageScrollStateChanged(state);
        }
    }

    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        IPLVPagerNavigator iPLVPagerNavigator = this.mNavigator;
        if (iPLVPagerNavigator != null) {
            iPLVPagerNavigator.onPageScrolled(position, positionOffset, positionOffsetPixels);
        }
    }

    public void onPageSelected(int position) {
        IPLVPagerNavigator iPLVPagerNavigator = this.mNavigator;
        if (iPLVPagerNavigator != null) {
            iPLVPagerNavigator.onPageSelected(position);
        }
    }

    public void setNavigator(IPLVPagerNavigator navigator) {
        IPLVPagerNavigator iPLVPagerNavigator = this.mNavigator;
        if (iPLVPagerNavigator == navigator) {
            return;
        }
        if (iPLVPagerNavigator != null) {
            iPLVPagerNavigator.onDetachFromMagicIndicator();
        }
        this.mNavigator = navigator;
        removeAllViews();
        if (this.mNavigator instanceof View) {
            addView((View) this.mNavigator, new FrameLayout.LayoutParams(-1, -1));
            this.mNavigator.onAttachToMagicIndicator();
        }
    }

    public PLVMagicIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
}
