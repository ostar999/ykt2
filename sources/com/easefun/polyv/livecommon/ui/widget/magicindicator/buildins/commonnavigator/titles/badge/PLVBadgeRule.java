package com.easefun.polyv.livecommon.ui.widget.magicindicator.buildins.commonnavigator.titles.badge;

/* loaded from: classes3.dex */
public class PLVBadgeRule {
    private PLVBadgeAnchor mAnchor;
    private int mOffset;

    public PLVBadgeRule(PLVBadgeAnchor anchor, int offset) {
        this.mAnchor = anchor;
        this.mOffset = offset;
    }

    public PLVBadgeAnchor getAnchor() {
        return this.mAnchor;
    }

    public int getOffset() {
        return this.mOffset;
    }

    public void setAnchor(PLVBadgeAnchor anchor) {
        this.mAnchor = anchor;
    }

    public void setOffset(int offset) {
        this.mOffset = offset;
    }
}
