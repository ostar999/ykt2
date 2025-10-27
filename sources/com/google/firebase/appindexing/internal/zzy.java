package com.google.firebase.appindexing.internal;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.Nullable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

@SafeParcelable.Class(creator = "MutateRequestCreator")
@SafeParcelable.Reserved({4})
/* loaded from: classes4.dex */
public final class zzy extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzy> CREATOR = new zzx();

    @SafeParcelable.Field(id = 1)
    private final int type;

    @Nullable
    @SafeParcelable.Field(id = 2)
    private final Thing[] zzfo;

    @Nullable
    @SafeParcelable.Field(id = 3)
    private final String[] zzfp;

    @Nullable
    @SafeParcelable.Field(id = 5)
    private final String[] zzfq;

    @Nullable
    @SafeParcelable.Field(id = 6)
    private final zza zzfr;

    @Nullable
    @SafeParcelable.Field(id = 7)
    private final String zzfs;

    @Nullable
    @SafeParcelable.Field(id = 8)
    private final String zzft;

    @SafeParcelable.Constructor
    public zzy(@SafeParcelable.Param(id = 1) int i2, @Nullable @SafeParcelable.Param(id = 2) Thing[] thingArr, @Nullable @SafeParcelable.Param(id = 3) String[] strArr, @Nullable @SafeParcelable.Param(id = 5) String[] strArr2, @Nullable @SafeParcelable.Param(id = 6) zza zzaVar, @Nullable @SafeParcelable.Param(id = 7) String str, @Nullable @SafeParcelable.Param(id = 8) String str2) {
        if (i2 != 0 && i2 != 1 && i2 != 2 && i2 != 3 && i2 != 4 && i2 != 6 && i2 != 7) {
            i2 = 0;
        }
        this.type = i2;
        this.zzfo = thingArr;
        this.zzfp = strArr;
        this.zzfq = strArr2;
        this.zzfr = zzaVar;
        this.zzfs = str;
        this.zzft = str2;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i2) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 1, this.type);
        SafeParcelWriter.writeTypedArray(parcel, 2, this.zzfo, i2, false);
        SafeParcelWriter.writeStringArray(parcel, 3, this.zzfp, false);
        SafeParcelWriter.writeStringArray(parcel, 5, this.zzfq, false);
        SafeParcelWriter.writeParcelable(parcel, 6, this.zzfr, i2, false);
        SafeParcelWriter.writeString(parcel, 7, this.zzfs, false);
        SafeParcelWriter.writeString(parcel, 8, this.zzft, false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    public zzy(int i2, Thing[] thingArr) {
        this(1, thingArr, null, null, null, null, null);
    }
}
