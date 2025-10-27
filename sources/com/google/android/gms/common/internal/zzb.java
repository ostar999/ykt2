package com.google.android.gms.common.internal;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.Feature;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

@SafeParcelable.Class(creator = "ConnectionInfoCreator")
/* loaded from: classes3.dex */
public final class zzb extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzb> CREATOR = new zzc();

    @SafeParcelable.Field(id = 1)
    Bundle zzda;

    @SafeParcelable.Field(id = 2)
    Feature[] zzdb;

    @SafeParcelable.Constructor
    public zzb(@SafeParcelable.Param(id = 1) Bundle bundle, @SafeParcelable.Param(id = 2) Feature[] featureArr) {
        this.zzda = bundle;
        this.zzdb = featureArr;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i2) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeBundle(parcel, 1, this.zzda, false);
        SafeParcelWriter.writeTypedArray(parcel, 2, this.zzdb, i2, false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    public zzb() {
    }
}
