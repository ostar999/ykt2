package net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.badge;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IMeasurablePagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;

/* loaded from: classes9.dex */
public class BadgePagerTitleView extends FrameLayout implements IMeasurablePagerTitleView {
    private boolean mAutoCancelBadge;
    private View mBadgeView;
    private IPagerTitleView mInnerPagerTitleView;
    private BadgeRule mXBadgeRule;
    private BadgeRule mYBadgeRule;

    public BadgePagerTitleView(Context context) {
        super(context);
        this.mAutoCancelBadge = true;
    }

    public View getBadgeView() {
        return this.mBadgeView;
    }

    @Override // net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IMeasurablePagerTitleView
    public int getContentBottom() {
        IPagerTitleView iPagerTitleView = this.mInnerPagerTitleView;
        return iPagerTitleView instanceof IMeasurablePagerTitleView ? ((IMeasurablePagerTitleView) iPagerTitleView).getContentBottom() : getBottom();
    }

    @Override // net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IMeasurablePagerTitleView
    public int getContentLeft() {
        return this.mInnerPagerTitleView instanceof IMeasurablePagerTitleView ? getLeft() + ((IMeasurablePagerTitleView) this.mInnerPagerTitleView).getContentLeft() : getLeft();
    }

    @Override // net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IMeasurablePagerTitleView
    public int getContentRight() {
        return this.mInnerPagerTitleView instanceof IMeasurablePagerTitleView ? getLeft() + ((IMeasurablePagerTitleView) this.mInnerPagerTitleView).getContentRight() : getRight();
    }

    @Override // net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IMeasurablePagerTitleView
    public int getContentTop() {
        IPagerTitleView iPagerTitleView = this.mInnerPagerTitleView;
        return iPagerTitleView instanceof IMeasurablePagerTitleView ? ((IMeasurablePagerTitleView) iPagerTitleView).getContentTop() : getTop();
    }

    public IPagerTitleView getInnerPagerTitleView() {
        return this.mInnerPagerTitleView;
    }

    public BadgeRule getXBadgeRule() {
        return this.mXBadgeRule;
    }

    public BadgeRule getYBadgeRule() {
        return this.mYBadgeRule;
    }

    public boolean isAutoCancelBadge() {
        return this.mAutoCancelBadge;
    }

    @Override // net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
    public void onDeselected(int i2, int i3) {
        IPagerTitleView iPagerTitleView = this.mInnerPagerTitleView;
        if (iPagerTitleView != null) {
            iPagerTitleView.onDeselected(i2, i3);
        }
    }

    @Override // net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
    public void onEnter(int i2, int i3, float f2, boolean z2) {
        IPagerTitleView iPagerTitleView = this.mInnerPagerTitleView;
        if (iPagerTitleView != null) {
            iPagerTitleView.onEnter(i2, i3, f2, z2);
        }
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public void onLayout(boolean z2, int i2, int i3, int i4, int i5) {
        super.onLayout(z2, i2, i3, i4, i5);
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
        IPagerTitleView iPagerTitleView = this.mInnerPagerTitleView;
        if (iPagerTitleView instanceof IMeasurablePagerTitleView) {
            IMeasurablePagerTitleView iMeasurablePagerTitleView = (IMeasurablePagerTitleView) iPagerTitleView;
            iArr[4] = iMeasurablePagerTitleView.getContentLeft();
            iArr[5] = iMeasurablePagerTitleView.getContentTop();
            iArr[6] = iMeasurablePagerTitleView.getContentRight();
            iArr[7] = iMeasurablePagerTitleView.getContentBottom();
        } else {
            for (int i6 = 4; i6 < 8; i6++) {
                iArr[i6] = iArr[i6 - 4];
            }
        }
        iArr[8] = view.getWidth() / 2;
        iArr[9] = view.getHeight() / 2;
        iArr[10] = iArr[4] / 2;
        iArr[11] = iArr[5] / 2;
        int i7 = iArr[6];
        iArr[12] = i7 + ((iArr[2] - i7) / 2);
        int i8 = iArr[7];
        iArr[13] = i8 + ((iArr[3] - i8) / 2);
        BadgeRule badgeRule = this.mXBadgeRule;
        if (badgeRule != null) {
            int offset = iArr[badgeRule.getAnchor().ordinal()] + this.mXBadgeRule.getOffset();
            View view2 = this.mBadgeView;
            view2.offsetLeftAndRight(offset - view2.getLeft());
        }
        BadgeRule badgeRule2 = this.mYBadgeRule;
        if (badgeRule2 != null) {
            int offset2 = iArr[badgeRule2.getAnchor().ordinal()] + this.mYBadgeRule.getOffset();
            View view3 = this.mBadgeView;
            view3.offsetTopAndBottom(offset2 - view3.getTop());
        }
    }

    @Override // net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
    public void onLeave(int i2, int i3, float f2, boolean z2) {
        IPagerTitleView iPagerTitleView = this.mInnerPagerTitleView;
        if (iPagerTitleView != null) {
            iPagerTitleView.onLeave(i2, i3, f2, z2);
        }
    }

    @Override // net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
    public void onSelected(int i2, int i3) {
        IPagerTitleView iPagerTitleView = this.mInnerPagerTitleView;
        if (iPagerTitleView != null) {
            iPagerTitleView.onSelected(i2, i3);
        }
        if (this.mAutoCancelBadge) {
            setBadgeView(null);
        }
    }

    public void setAutoCancelBadge(boolean z2) {
        this.mAutoCancelBadge = z2;
    }

    public void setBadgeView(View view) {
        if (this.mBadgeView == view) {
            return;
        }
        this.mBadgeView = view;
        removeAllViews();
        if (this.mInnerPagerTitleView instanceof View) {
            addView((View) this.mInnerPagerTitleView, new FrameLayout.LayoutParams(-1, -1));
        }
        if (this.mBadgeView != null) {
            addView(this.mBadgeView, new FrameLayout.LayoutParams(-2, -2));
        }
    }

    public void setInnerPagerTitleView(IPagerTitleView iPagerTitleView) {
        if (this.mInnerPagerTitleView == iPagerTitleView) {
            return;
        }
        this.mInnerPagerTitleView = iPagerTitleView;
        removeAllViews();
        if (this.mInnerPagerTitleView instanceof View) {
            addView((View) this.mInnerPagerTitleView, new FrameLayout.LayoutParams(-1, -1));
        }
        if (this.mBadgeView != null) {
            addView(this.mBadgeView, new FrameLayout.LayoutParams(-2, -2));
        }
    }

    public void setXBadgeRule(BadgeRule badgeRule) {
        BadgeAnchor anchor;
        if (badgeRule != null && (anchor = badgeRule.getAnchor()) != BadgeAnchor.LEFT && anchor != BadgeAnchor.RIGHT && anchor != BadgeAnchor.CONTENT_LEFT && anchor != BadgeAnchor.CONTENT_RIGHT && anchor != BadgeAnchor.CENTER_X && anchor != BadgeAnchor.LEFT_EDGE_CENTER_X && anchor != BadgeAnchor.RIGHT_EDGE_CENTER_X) {
            throw new IllegalArgumentException("x badge rule is wrong.");
        }
        this.mXBadgeRule = badgeRule;
    }

    public void setYBadgeRule(BadgeRule badgeRule) {
        BadgeAnchor anchor;
        if (badgeRule != null && (anchor = badgeRule.getAnchor()) != BadgeAnchor.TOP && anchor != BadgeAnchor.BOTTOM && anchor != BadgeAnchor.CONTENT_TOP && anchor != BadgeAnchor.CONTENT_BOTTOM && anchor != BadgeAnchor.CENTER_Y && anchor != BadgeAnchor.TOP_EDGE_CENTER_Y && anchor != BadgeAnchor.BOTTOM_EDGE_CENTER_Y) {
            throw new IllegalArgumentException("y badge rule is wrong.");
        }
        this.mYBadgeRule = badgeRule;
    }
}
