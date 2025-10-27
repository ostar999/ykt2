package com.google.android.gms.common;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.IntentSender;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.Nullable;
import com.alipay.security.mobile.module.http.model.c;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.huawei.hms.support.hianalytics.HiAnalyticsConstant;

@SafeParcelable.Class(creator = "ConnectionResultCreator")
/* loaded from: classes3.dex */
public final class ConnectionResult extends AbstractSafeParcelable {
    public static final int API_UNAVAILABLE = 16;
    public static final int CANCELED = 13;
    public static final int DEVELOPER_ERROR = 10;

    @Deprecated
    public static final int DRIVE_EXTERNAL_STORAGE_REQUIRED = 1500;
    public static final int INTERNAL_ERROR = 8;
    public static final int INTERRUPTED = 15;
    public static final int INVALID_ACCOUNT = 5;
    public static final int LICENSE_CHECK_FAILED = 11;
    public static final int NETWORK_ERROR = 7;
    public static final int RESOLUTION_REQUIRED = 6;
    public static final int RESTRICTED_PROFILE = 20;
    public static final int SERVICE_DISABLED = 3;
    public static final int SERVICE_INVALID = 9;
    public static final int SERVICE_MISSING = 1;
    public static final int SERVICE_MISSING_PERMISSION = 19;
    public static final int SERVICE_UPDATING = 18;
    public static final int SERVICE_VERSION_UPDATE_REQUIRED = 2;
    public static final int SIGN_IN_FAILED = 17;
    public static final int SIGN_IN_REQUIRED = 4;
    public static final int SUCCESS = 0;
    public static final int TIMEOUT = 14;

    @KeepForSdk
    public static final int UNKNOWN = -1;

    @SafeParcelable.VersionField(id = 1)
    private final int zzg;

    @SafeParcelable.Field(getter = "getErrorCode", id = 2)
    private final int zzh;

    @SafeParcelable.Field(getter = "getResolution", id = 3)
    private final PendingIntent zzi;

    @SafeParcelable.Field(getter = "getErrorMessage", id = 4)
    private final String zzj;

    @KeepForSdk
    public static final ConnectionResult RESULT_SUCCESS = new ConnectionResult(0);
    public static final Parcelable.Creator<ConnectionResult> CREATOR = new zza();

    @SafeParcelable.Constructor
    public ConnectionResult(@SafeParcelable.Param(id = 1) int i2, @SafeParcelable.Param(id = 2) int i3, @SafeParcelable.Param(id = 3) PendingIntent pendingIntent, @SafeParcelable.Param(id = 4) String str) {
        this.zzg = i2;
        this.zzh = i3;
        this.zzi = pendingIntent;
        this.zzj = str;
    }

    public static String zza(int i2) {
        if (i2 == 99) {
            return "UNFINISHED";
        }
        if (i2 == 1500) {
            return "DRIVE_EXTERNAL_STORAGE_REQUIRED";
        }
        switch (i2) {
            case -1:
                return "UNKNOWN";
            case 0:
                return c.f3449g;
            case 1:
                return "SERVICE_MISSING";
            case 2:
                return "SERVICE_VERSION_UPDATE_REQUIRED";
            case 3:
                return "SERVICE_DISABLED";
            case 4:
                return "SIGN_IN_REQUIRED";
            case 5:
                return "INVALID_ACCOUNT";
            case 6:
                return "RESOLUTION_REQUIRED";
            case 7:
                return "NETWORK_ERROR";
            case 8:
                return "INTERNAL_ERROR";
            case 9:
                return "SERVICE_INVALID";
            case 10:
                return "DEVELOPER_ERROR";
            case 11:
                return "LICENSE_CHECK_FAILED";
            default:
                switch (i2) {
                    case 13:
                        return "CANCELED";
                    case 14:
                        return "TIMEOUT";
                    case 15:
                        return "INTERRUPTED";
                    case 16:
                        return "API_UNAVAILABLE";
                    case 17:
                        return "SIGN_IN_FAILED";
                    case 18:
                        return "SERVICE_UPDATING";
                    case 19:
                        return "SERVICE_MISSING_PERMISSION";
                    case 20:
                        return "RESTRICTED_PROFILE";
                    case 21:
                        return "API_VERSION_UPDATE_REQUIRED";
                    default:
                        StringBuilder sb = new StringBuilder(31);
                        sb.append("UNKNOWN_ERROR_CODE(");
                        sb.append(i2);
                        sb.append(")");
                        return sb.toString();
                }
        }
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ConnectionResult)) {
            return false;
        }
        ConnectionResult connectionResult = (ConnectionResult) obj;
        return this.zzh == connectionResult.zzh && Objects.equal(this.zzi, connectionResult.zzi) && Objects.equal(this.zzj, connectionResult.zzj);
    }

    public final int getErrorCode() {
        return this.zzh;
    }

    @Nullable
    public final String getErrorMessage() {
        return this.zzj;
    }

    @Nullable
    public final PendingIntent getResolution() {
        return this.zzi;
    }

    public final boolean hasResolution() {
        return (this.zzh == 0 || this.zzi == null) ? false : true;
    }

    public final int hashCode() {
        return Objects.hashCode(Integer.valueOf(this.zzh), this.zzi, this.zzj);
    }

    public final boolean isSuccess() {
        return this.zzh == 0;
    }

    public final void startResolutionForResult(Activity activity, int i2) throws IntentSender.SendIntentException {
        if (hasResolution()) {
            activity.startIntentSenderForResult(this.zzi.getIntentSender(), i2, null, 0, 0, 0);
        }
    }

    public final String toString() {
        return Objects.toStringHelper(this).add(HiAnalyticsConstant.HaKey.BI_KEY_RESULT, zza(this.zzh)).add("resolution", this.zzi).add("message", this.zzj).toString();
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i2) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 1, this.zzg);
        SafeParcelWriter.writeInt(parcel, 2, getErrorCode());
        SafeParcelWriter.writeParcelable(parcel, 3, getResolution(), i2, false);
        SafeParcelWriter.writeString(parcel, 4, getErrorMessage(), false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    public ConnectionResult(int i2) {
        this(i2, null, null);
    }

    public ConnectionResult(int i2, PendingIntent pendingIntent) {
        this(i2, pendingIntent, null);
    }

    public ConnectionResult(int i2, PendingIntent pendingIntent, String str) {
        this(1, i2, pendingIntent, str);
    }
}
