package com.google.android.gms.common.api;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.IntentSender;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.Nullable;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.util.VisibleForTesting;
import com.huawei.hms.support.hianalytics.HiAnalyticsConstant;

@KeepForSdk
@SafeParcelable.Class(creator = "StatusCreator")
/* loaded from: classes3.dex */
public final class Status extends AbstractSafeParcelable implements Result, ReflectedParcelable {

    @SafeParcelable.VersionField(id = 1000)
    private final int zzg;

    @SafeParcelable.Field(getter = "getStatusCode", id = 1)
    private final int zzh;

    @Nullable
    @SafeParcelable.Field(getter = "getPendingIntent", id = 3)
    private final PendingIntent zzi;

    @Nullable
    @SafeParcelable.Field(getter = "getStatusMessage", id = 2)
    private final String zzj;

    @VisibleForTesting
    @KeepForSdk
    public static final Status RESULT_SUCCESS = new Status(0);

    @KeepForSdk
    public static final Status RESULT_INTERRUPTED = new Status(14);

    @KeepForSdk
    public static final Status RESULT_INTERNAL_ERROR = new Status(8);

    @KeepForSdk
    public static final Status RESULT_TIMEOUT = new Status(15);

    @KeepForSdk
    public static final Status RESULT_CANCELED = new Status(16);
    private static final Status zzar = new Status(17);

    @KeepForSdk
    public static final Status RESULT_DEAD_CLIENT = new Status(18);
    public static final Parcelable.Creator<Status> CREATOR = new zzb();

    @SafeParcelable.Constructor
    @KeepForSdk
    public Status(@SafeParcelable.Param(id = 1000) int i2, @SafeParcelable.Param(id = 1) int i3, @Nullable @SafeParcelable.Param(id = 2) String str, @Nullable @SafeParcelable.Param(id = 3) PendingIntent pendingIntent) {
        this.zzg = i2;
        this.zzh = i3;
        this.zzj = str;
        this.zzi = pendingIntent;
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof Status)) {
            return false;
        }
        Status status = (Status) obj;
        return this.zzg == status.zzg && this.zzh == status.zzh && Objects.equal(this.zzj, status.zzj) && Objects.equal(this.zzi, status.zzi);
    }

    public final PendingIntent getResolution() {
        return this.zzi;
    }

    @Override // com.google.android.gms.common.api.Result
    @KeepForSdk
    public final Status getStatus() {
        return this;
    }

    public final int getStatusCode() {
        return this.zzh;
    }

    @Nullable
    public final String getStatusMessage() {
        return this.zzj;
    }

    @VisibleForTesting
    public final boolean hasResolution() {
        return this.zzi != null;
    }

    public final int hashCode() {
        return Objects.hashCode(Integer.valueOf(this.zzg), Integer.valueOf(this.zzh), this.zzj, this.zzi);
    }

    public final boolean isCanceled() {
        return this.zzh == 16;
    }

    public final boolean isInterrupted() {
        return this.zzh == 14;
    }

    public final boolean isSuccess() {
        return this.zzh <= 0;
    }

    public final void startResolutionForResult(Activity activity, int i2) throws IntentSender.SendIntentException {
        if (hasResolution()) {
            activity.startIntentSenderForResult(this.zzi.getIntentSender(), i2, null, 0, 0, 0);
        }
    }

    public final String toString() {
        return Objects.toStringHelper(this).add(HiAnalyticsConstant.HaKey.BI_KEY_RESULT, zzg()).add("resolution", this.zzi).toString();
    }

    @Override // android.os.Parcelable
    @KeepForSdk
    public final void writeToParcel(Parcel parcel, int i2) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 1, getStatusCode());
        SafeParcelWriter.writeString(parcel, 2, getStatusMessage(), false);
        SafeParcelWriter.writeParcelable(parcel, 3, this.zzi, i2, false);
        SafeParcelWriter.writeInt(parcel, 1000, this.zzg);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    public final String zzg() {
        String str = this.zzj;
        return str != null ? str : CommonStatusCodes.getStatusCodeString(this.zzh);
    }

    @KeepForSdk
    public Status(int i2) {
        this(i2, null);
    }

    @KeepForSdk
    public Status(int i2, @Nullable String str) {
        this(1, i2, str, null);
    }

    @KeepForSdk
    public Status(int i2, @Nullable String str, @Nullable PendingIntent pendingIntent) {
        this(1, i2, str, pendingIntent);
    }
}
