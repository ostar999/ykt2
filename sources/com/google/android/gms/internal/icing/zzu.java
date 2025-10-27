package com.google.android.gms.internal.icing;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

@SafeParcelable.Class(creator = "ScoringConfigCreator")
@SafeParcelable.Reserved({1000})
/* loaded from: classes3.dex */
public final class zzu extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzu> CREATOR = new zzx();

    @SafeParcelable.Field(id = 1)
    private final boolean zzai;

    @SafeParcelable.Constructor
    public zzu(@SafeParcelable.Param(id = 1) boolean z2) {
        this.zzai = z2;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof zzu) && this.zzai == ((zzu) obj).zzai;
    }

    public final int hashCode() {
        return this.zzai ? 1 : 0;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i2) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeBoolean(parcel, 1, this.zzai);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }
}
