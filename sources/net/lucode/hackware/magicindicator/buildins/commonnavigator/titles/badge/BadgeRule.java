package net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.badge;

/* loaded from: classes9.dex */
public class BadgeRule {
    private BadgeAnchor mAnchor;
    private int mOffset;

    public BadgeRule(BadgeAnchor badgeAnchor, int i2) {
        this.mAnchor = badgeAnchor;
        this.mOffset = i2;
    }

    public BadgeAnchor getAnchor() {
        return this.mAnchor;
    }

    public int getOffset() {
        return this.mOffset;
    }

    public void setAnchor(BadgeAnchor badgeAnchor) {
        this.mAnchor = badgeAnchor;
    }

    public void setOffset(int i2) {
        this.mOffset = i2;
    }
}
