package com.kaelli.niceratingbar;

/* loaded from: classes4.dex */
public enum RatingStatus {
    Disable(0),
    Enable(1);

    int mStatus;

    RatingStatus(int i2) {
        this.mStatus = i2;
    }

    public static RatingStatus getStatus(int i2) {
        RatingStatus ratingStatus = Disable;
        return i2 == ratingStatus.mStatus ? ratingStatus : Enable;
    }
}
