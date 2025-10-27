package com.google.android.gms.internal.icing;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.ShowFirstParty;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

@ShowFirstParty
@SafeParcelable.Class(creator = "DocumentIdCreator")
@SafeParcelable.Reserved({1000})
/* loaded from: classes3.dex */
public final class zzi extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzi> CREATOR = new zzl();

    @SafeParcelable.Field(id = 1)
    private final String packageName;

    @SafeParcelable.Field(id = 2)
    private final String zzm;

    @SafeParcelable.Field(id = 3)
    private final String zzn;

    @SafeParcelable.Constructor
    public zzi(@SafeParcelable.Param(id = 1) String str, @SafeParcelable.Param(id = 2) String str2, @SafeParcelable.Param(id = 3) String str3) {
        this.packageName = str;
        this.zzm = str2;
        this.zzn = str3;
    }

    public final String toString() {
        return String.format("DocumentId[packageName=%s, corpusName=%s, uri=%s]", this.packageName, this.zzm, this.zzn);
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i2) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 1, this.packageName, false);
        SafeParcelWriter.writeString(parcel, 2, this.zzm, false);
        SafeParcelWriter.writeString(parcel, 3, this.zzn, false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }
}
