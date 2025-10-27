package com.google.firebase.appindexing.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

@SafeParcelable.Class(creator = "CallStatusCreator")
/* loaded from: classes4.dex */
public final class zzg extends AbstractSafeParcelable {

    @SafeParcelable.Field(id = 1)
    public final int status;
    private static final zzg zzew = new zzg(1);
    private static final zzg zzex = new zzg(2);
    private static final zzg zzey = new zzg(3);
    public static final Parcelable.Creator<zzg> CREATOR = new zzf();

    @SafeParcelable.Constructor
    public zzg(@SafeParcelable.Param(id = 1) int i2) {
        this.status = i2;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i2) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 1, this.status);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }
}
