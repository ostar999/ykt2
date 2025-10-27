package com.easefun.polyv.livecommon.ui.widget.magicindicator.buildins.commonnavigator.titles.badge;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import com.easefun.polyv.livecommon.ui.widget.magicindicator.buildins.commonnavigator.abs.IPLVMeasurablePagerTitleView;
import com.easefun.polyv.livecommon.ui.widget.magicindicator.buildins.commonnavigator.abs.IPLVPagerTitleView;

/* loaded from: classes3.dex */
public class PLVBadgePagerTitleView extends FrameLayout implements IPLVMeasurablePagerTitleView {
    private boolean mAutoCancelBadge;
    private View mBadgeView;
    private IPLVPagerTitleView mInnerPagerTitleView;
    private PLVBadgeRule mXBadgeRule;
    private PLVBadgeRule mYBadgeRule;

    public PLVBadgePagerTitleView(Context context) {
        super(context);
        this.mAutoCancelBadge = true;
    }

    public View getBadgeView() {
        return this.mBadgeView;
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.magicindicator.buildins.commonnavigator.abs.IPLVMeasurablePagerTitleView
    public int getContentBottom() {
        IPLVPagerTitleView iPLVPagerTitleView = this.mInnerPagerTitleView;
        return iPLVPagerTitleView instanceof IPLVMeasurablePagerTitleView ? ((IPLVMeasurablePagerTitleView) iPLVPagerTitleView).getContentBottom() : getBottom();
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.magicindicator.buildins.commonnavigator.abs.IPLVMeasurablePagerTitleView
    public int getContentLeft() {
        return this.mInnerPagerTitleView instanceof IPLVMeasurablePagerTitleView ? getLeft() + ((IPLVMeasurablePagerTitleView) this.mInnerPagerTitleView).getContentLeft() : getLeft();
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.magicindicator.buildins.commonnavigator.abs.IPLVMeasurablePagerTitleView
    public int getContentRight() {
        return this.mInnerPagerTitleView instanceof IPLVMeasurablePagerTitleView ? getLeft() + ((IPLVMeasurablePagerTitleView) this.mInnerPagerTitleView).getContentRight() : getRight();
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.magicindicator.buildins.commonnavigator.abs.IPLVMeasurablePagerTitleView
    public int getContentTop() {
        IPLVPagerTitleView iPLVPagerTitleView = this.mInnerPagerTitleView;
        return iPLVPagerTitleView instanceof IPLVMeasurablePagerTitleView ? ((IPLVMeasurablePagerTitleView) iPLVPagerTitleView).getContentTop() : getTop();
    }

    public IPLVPagerTitleView getInnerPagerTitleView() {
        return this.mInnerPagerTitleView;
    }

    public PLVBadgeRule getXBadgeRule() {
        return this.mXBadgeRule;
    }

    public PLVBadgeRule getYBadgeRule() {
        return this.mYBadgeRule;
    }

    public boolean isAutoCancelBadge() {
        return this.mAutoCancelBadge;
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.magicindicator.buildins.commonnavigator.abs.IPLVPagerTitleView
    public void onDeselected(int index, int totalCount) {
        IPLVPagerTitleView iPLVPagerTitleView = this.mInnerPagerTitleView;
        if (iPLVPagerTitleView != null) {
            iPLVPagerTitleView.onDeselected(index, totalCount);
        }
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.magicindicator.buildins.commonnavigator.abs.IPLVPagerTitleView
    public void onEnter(int index, int totalCount, float enterPercent, boolean leftToRight) {
        IPLVPagerTitleView iPLVPagerTitleView = this.mInnerPagerTitleView;
        if (iPLVPagerTitleView != null) {
            iPLVPagerTitleView.onEnter(index, totalCount, enterPercent, leftToRight);
        }
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public void onLayout(boolean changed, int left, int top2, int right, int bottom) {
        super.onLayout(changed, left, top2, right, bottom);
        Object obj = this.mInnerPagerTitleView;
        if (!(obj instanceof View) || this.mBadgeView == null) {
            return;
        }
        int[] iArr = new int[14];
        View view = (View) obj;
        iArr[0] = view.getLeft();
        iArr[1] = view.getTop();
        iArr[2] = view.getRight();
        iArr[3] = view.getBottom();
        IPLVPagerTitleView iPLVPagerTitleView = this.mInnerPagerTitleView;
        if (iPLVPagerTitleView instanceof IPLVMeasurablePagerTitleView) {
            IPLVMeasurablePagerTitleView iPLVMeasurablePagerTitleView = (IPLVMeasurablePagerTitleView) iPLVPagerTitleView;
            iArr[4] = iPLVMeasurablePagerTitleView.getContentLeft();
            iArr[5] = iPLVMeasurablePagerTitleView.getContentTop();
            iArr[6] = iPLVMeasurablePagerTitleView.getContentRight();
            iArr[7] = iPLVMeasurablePagerTitleView.getContentBottom();
        } else {
            for (int i2 = 4; i2 < 8; i2++) {
                iArr[i2] = iArr[i2 - 4];
            }
        }
        iArr[8] = view.getWidth() / 2;
        iArr[9] = view.getHeight() / 2;
        iArr[10] = iArr[4] / 2;
        iArr[11] = iArr[5] / 2;
        int i3 = iArr[6];
        iArr[12] = i3 + ((iArr[2] - i3) / 2);
        int i4 = iArr[7];
        iArr[13] = i4 + ((iArr[3] - i4) / 2);
        PLVBadgeRule pLVBadgeRule = this.mXBadgeRule;
        if (pLVBadgeRule != null) {
            int offset = iArr[pLVBadgeRule.getAnchor().ordinal()] + this.mXBadgeRule.getOffset();
            View view2 = this.mBadgeView;
            view2.offsetLeftAndRight(offset - view2.getLeft());
        }
        PLVBadgeRule pLVBadgeRule2 = this.mYBadgeRule;
        if (pLVBadgeRule2 != null) {
            int offset2 = iArr[pLVBadgeRule2.getAnchor().ordinal()] + this.mYBadgeRule.getOffset();
            View view3 = this.mBadgeView;
            view3.offsetTopAndBottom(offset2 - view3.getTop());
        }
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.magicindicator.buildins.commonnavigator.abs.IPLVPagerTitleView
    public void onLeave(int index, int totalCount, float leavePercent, boolean leftToRight) {
        IPLVPagerTitleView iPLVPagerTitleView = this.mInnerPagerTitleView;
        if (iPLVPagerTitleView != null) {
            iPLVPagerTitleView.onLeave(index, totalCount, leavePercent, leftToRight);
        }
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.magicindicator.buildins.commonnavigator.abs.IPLVPagerTitleView
    public void onSelected(int index, int totalCount) {
        IPLVPagerTitleView iPLVPagerTitleView = this.mInnerPagerTitleView;
        if (iPLVPagerTitleView != null) {
            iPLVPagerTitleView.onSelected(index, totalCount);
        }
        if (this.mAutoCancelBadge) {
            setBadgeView(null);
        }
    }

    public void setAutoCancelBadge(boolean autoCancelBadge) {
        this.mAutoCancelBadge = autoCancelBadge;
    }

    public void setBadgeView(View badgeView) {
        if (this.mBadgeView == badgeView) {
            return;
        }
        this.mBadgeView = badgeView;
        removeAllViews();
        if (this.mInnerPagerTitleView instanceof View) {
            addView((View) this.mInnerPagerTitleView, new FrameLayout.LayoutParams(-1, -1));
        }
        if (this.mBadgeView != null) {
            addView(this.mBadgeView, new FrameLayout.LayoutParams(-2, -2));
        }
    }

    public void setInnerPagerTitleView(IPLVPagerTitleView innerPagerTitleView) {
        if (this.mInnerPagerTitleView == innerPagerTitleView) {
            return;
        }
        this.mInnerPagerTitleView = innerPagerTitleView;
        removeAllViews();
        if (this.mInnerPagerTitleView instanceof View) {
            addView((View) this.mInnerPagerTitleView, new FrameLayout.LayoutParams(-1, -1));
        }
        if (this.mBadgeView != null) {
            addView(this.mBadgeView, new FrameLayout.LayoutParams(-2, -2));
        }
    }

    public void setXBadgeRule(PLVBadgeRule badgeRule) {
        PLVBadgeAnchor anchor;
        if (badgeRule != null && (anchor = badgeRule.getAnchor()) != PLVBadgeAnchor.LEFT && anchor != PLVBadgeAnchor.RIGHT && anchor != PLVBadgeAnchor.CONTENT_LEFT && anchor != PLVBadgeAnchor.CONTENT_RIGHT && anchor != PLVBadgeAnchor.CENTER_X && anchor != PLVBadgeAnchor.LEFT_EDGE_CENTER_X && anchor != PLVBadgeAnchor.RIGHT_EDGE_CENTER_X) {
            throw new IllegalArgumentException("x badge rule is wrong.");
        }
        this.mXBadgeRule = badgeRule;
    }

    public void setYBadgeRule(PLVBadgeRule badgeRule) {
        PLVBadgeAnchor anchor;
        if (badgeRule != null && (anchor = badgeRule.getAnchor()) != PLVBadgeAnchor.TOP && anchor != PLVBadgeAnchor.BOTTOM && anchor != PLVBadgeAnchor.CONTENT_TOP && anchor != PLVBadgeAnchor.CONTENT_BOTTOM && anchor != PLVBadgeAnchor.CENTER_Y && anchor != PLVBadgeAnchor.TOP_EDGE_CENTER_Y && anchor != PLVBadgeAnchor.BOTTOM_EDGE_CENTER_Y) {
            throw new IllegalArgumentException("y badge rule is wrong.");
        }
        this.mYBadgeRule = badgeRule;
    }
}
