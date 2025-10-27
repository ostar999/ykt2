package com.google.android.gms.common.api;

import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.ShowFirstParty;
import com.yikaobang.yixue.R2;

@KeepForSdk
/* loaded from: classes3.dex */
public class BooleanResult implements Result {
    private final Status mStatus;
    private final boolean zabi;

    @ShowFirstParty
    @KeepForSdk
    public BooleanResult(Status status, boolean z2) {
        this.mStatus = (Status) Preconditions.checkNotNull(status, "Status must not be null");
        this.zabi = z2;
    }

    @KeepForSdk
    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof BooleanResult)) {
            return false;
        }
        BooleanResult booleanResult = (BooleanResult) obj;
        return this.mStatus.equals(booleanResult.mStatus) && this.zabi == booleanResult.zabi;
    }

    @Override // com.google.android.gms.common.api.Result
    @KeepForSdk
    public Status getStatus() {
        return this.mStatus;
    }

    @KeepForSdk
    public boolean getValue() {
        return this.zabi;
    }

    @KeepForSdk
    public final int hashCode() {
        return ((this.mStatus.hashCode() + R2.attr.bl_checkable_gradient_endColor) * 31) + (this.zabi ? 1 : 0);
    }
}
