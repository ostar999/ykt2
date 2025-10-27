package com.scwang.smart.refresh.layout.constant;

/* loaded from: classes6.dex */
public enum RefreshState {
    None(0, false, false, false, false, false),
    PullDownToRefresh(1, true, false, false, false, false),
    PullUpToLoad(2, true, false, false, false, false),
    PullDownCanceled(1, false, false, false, false, false),
    PullUpCanceled(2, false, false, false, false, false),
    ReleaseToRefresh(1, true, false, false, false, true),
    ReleaseToLoad(2, true, false, false, false, true),
    ReleaseToTwoLevel(1, true, false, false, true, true),
    TwoLevelReleased(1, false, false, false, true, false),
    RefreshReleased(1, false, false, false, false, false),
    LoadReleased(2, false, false, false, false, false),
    Refreshing(1, false, true, false, false, false),
    Loading(2, false, true, false, false, false),
    TwoLevel(1, false, true, false, true, false),
    RefreshFinish(1, false, false, true, false, false),
    LoadFinish(2, false, false, true, false, false),
    TwoLevelFinish(1, false, false, true, true, false);

    public final boolean isDragging;
    public final boolean isFinishing;
    public final boolean isFooter;
    public final boolean isHeader;
    public final boolean isOpening;
    public final boolean isReleaseToOpening;
    public final boolean isTwoLevel;

    RefreshState(int i2, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6) {
        this.isHeader = i2 == 1;
        this.isFooter = i2 == 2;
        this.isDragging = z2;
        this.isOpening = z3;
        this.isFinishing = z4;
        this.isTwoLevel = z5;
        this.isReleaseToOpening = z6;
    }

    public RefreshState toFooter() {
        return (!this.isHeader || this.isTwoLevel) ? this : values()[ordinal() + 1];
    }

    public RefreshState toHeader() {
        return (!this.isFooter || this.isTwoLevel) ? this : values()[ordinal() - 1];
    }
}
