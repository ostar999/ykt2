package com.google.android.gms.common.stats;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import cn.hutool.core.text.StrPool;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import java.util.List;

@SafeParcelable.Class(creator = "WakeLockEventCreator")
/* loaded from: classes3.dex */
public final class WakeLockEvent extends StatsEvent {
    public static final Parcelable.Creator<WakeLockEvent> CREATOR = new zza();
    private long durationMillis;

    @SafeParcelable.VersionField(id = 1)
    private final int versionCode;

    @SafeParcelable.Field(getter = "getTimeMillis", id = 2)
    private final long zzfo;

    @SafeParcelable.Field(getter = "getEventType", id = 11)
    private int zzfp;

    @SafeParcelable.Field(getter = "getWakeLockName", id = 4)
    private final String zzfq;

    @SafeParcelable.Field(getter = "getSecondaryWakeLockName", id = 10)
    private final String zzfr;

    @SafeParcelable.Field(getter = "getCodePackage", id = 17)
    private final String zzfs;

    @SafeParcelable.Field(getter = "getWakeLockType", id = 5)
    private final int zzft;

    @SafeParcelable.Field(getter = "getCallingPackages", id = 6)
    private final List<String> zzfu;

    @SafeParcelable.Field(getter = "getEventKey", id = 12)
    private final String zzfv;

    @SafeParcelable.Field(getter = "getElapsedRealtime", id = 8)
    private final long zzfw;

    @SafeParcelable.Field(getter = "getDeviceState", id = 14)
    private int zzfx;

    @SafeParcelable.Field(getter = "getHostPackage", id = 13)
    private final String zzfy;

    @SafeParcelable.Field(getter = "getBeginPowerPercentage", id = 15)
    private final float zzfz;

    @SafeParcelable.Field(getter = "getTimeout", id = 16)
    private final long zzga;

    @SafeParcelable.Field(getter = "getAcquiredWithTimeout", id = 18)
    private final boolean zzgb;

    @SafeParcelable.Constructor
    public WakeLockEvent(@SafeParcelable.Param(id = 1) int i2, @SafeParcelable.Param(id = 2) long j2, @SafeParcelable.Param(id = 11) int i3, @SafeParcelable.Param(id = 4) String str, @SafeParcelable.Param(id = 5) int i4, @SafeParcelable.Param(id = 6) List<String> list, @SafeParcelable.Param(id = 12) String str2, @SafeParcelable.Param(id = 8) long j3, @SafeParcelable.Param(id = 14) int i5, @SafeParcelable.Param(id = 10) String str3, @SafeParcelable.Param(id = 13) String str4, @SafeParcelable.Param(id = 15) float f2, @SafeParcelable.Param(id = 16) long j4, @SafeParcelable.Param(id = 17) String str5, @SafeParcelable.Param(id = 18) boolean z2) {
        this.versionCode = i2;
        this.zzfo = j2;
        this.zzfp = i3;
        this.zzfq = str;
        this.zzfr = str3;
        this.zzfs = str5;
        this.zzft = i4;
        this.durationMillis = -1L;
        this.zzfu = list;
        this.zzfv = str2;
        this.zzfw = j3;
        this.zzfx = i5;
        this.zzfy = str4;
        this.zzfz = f2;
        this.zzga = j4;
        this.zzgb = z2;
    }

    @Override // com.google.android.gms.common.stats.StatsEvent
    public final int getEventType() {
        return this.zzfp;
    }

    @Override // com.google.android.gms.common.stats.StatsEvent
    public final long getTimeMillis() {
        return this.zzfo;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i2) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 1, this.versionCode);
        SafeParcelWriter.writeLong(parcel, 2, getTimeMillis());
        SafeParcelWriter.writeString(parcel, 4, this.zzfq, false);
        SafeParcelWriter.writeInt(parcel, 5, this.zzft);
        SafeParcelWriter.writeStringList(parcel, 6, this.zzfu, false);
        SafeParcelWriter.writeLong(parcel, 8, this.zzfw);
        SafeParcelWriter.writeString(parcel, 10, this.zzfr, false);
        SafeParcelWriter.writeInt(parcel, 11, getEventType());
        SafeParcelWriter.writeString(parcel, 12, this.zzfv, false);
        SafeParcelWriter.writeString(parcel, 13, this.zzfy, false);
        SafeParcelWriter.writeInt(parcel, 14, this.zzfx);
        SafeParcelWriter.writeFloat(parcel, 15, this.zzfz);
        SafeParcelWriter.writeLong(parcel, 16, this.zzga);
        SafeParcelWriter.writeString(parcel, 17, this.zzfs, false);
        SafeParcelWriter.writeBoolean(parcel, 18, this.zzgb);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    @Override // com.google.android.gms.common.stats.StatsEvent
    public final long zzu() {
        return this.durationMillis;
    }

    @Override // com.google.android.gms.common.stats.StatsEvent
    public final String zzv() {
        String str = this.zzfq;
        int i2 = this.zzft;
        List<String> list = this.zzfu;
        String strJoin = list == null ? "" : TextUtils.join(",", list);
        int i3 = this.zzfx;
        String str2 = this.zzfr;
        if (str2 == null) {
            str2 = "";
        }
        String str3 = this.zzfy;
        if (str3 == null) {
            str3 = "";
        }
        float f2 = this.zzfz;
        String str4 = this.zzfs;
        String str5 = str4 != null ? str4 : "";
        boolean z2 = this.zzgb;
        StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 51 + String.valueOf(strJoin).length() + String.valueOf(str2).length() + String.valueOf(str3).length() + String.valueOf(str5).length());
        sb.append(StrPool.TAB);
        sb.append(str);
        sb.append(StrPool.TAB);
        sb.append(i2);
        sb.append(StrPool.TAB);
        sb.append(strJoin);
        sb.append(StrPool.TAB);
        sb.append(i3);
        sb.append(StrPool.TAB);
        sb.append(str2);
        sb.append(StrPool.TAB);
        sb.append(str3);
        sb.append(StrPool.TAB);
        sb.append(f2);
        sb.append(StrPool.TAB);
        sb.append(str5);
        sb.append(StrPool.TAB);
        sb.append(z2);
        return sb.toString();
    }

    public WakeLockEvent(long j2, int i2, String str, int i3, List<String> list, String str2, long j3, int i4, String str3, String str4, float f2, long j4, String str5, boolean z2) {
        this(2, j2, i2, str, i3, list, str2, j3, i4, str3, str4, f2, j4, str5, z2);
    }
}
