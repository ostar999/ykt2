package com.google.android.gms.internal.icing;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.ShowFirstParty;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.util.VisibleForTesting;

@ShowFirstParty
@SafeParcelable.Class(creator = "DocumentSectionCreator")
@SafeParcelable.Reserved({1000})
/* loaded from: classes3.dex */
public final class zzk extends AbstractSafeParcelable {

    @SafeParcelable.Field(id = 1)
    private final String zzq;

    @SafeParcelable.Field(id = 3)
    private final zzt zzr;

    @SafeParcelable.Field(defaultValue = "-1", id = 4)
    public final int zzs;

    @SafeParcelable.Field(id = 5)
    private final byte[] zzt;
    private static final int zzo = Integer.parseInt("-1");
    public static final Parcelable.Creator<zzk> CREATOR = new zzn();
    private static final zzt zzp = new zzs("SsbContext").zzb(true).zzc("blob").zzc();

    @SafeParcelable.Constructor
    public zzk(@SafeParcelable.Param(id = 1) String str, @SafeParcelable.Param(id = 3) zzt zztVar, @SafeParcelable.Param(id = 4) int i2, @SafeParcelable.Param(id = 5) byte[] bArr) {
        String string;
        int i3 = zzo;
        boolean z2 = i2 == i3 || zzq.zza(i2) != null;
        StringBuilder sb = new StringBuilder(32);
        sb.append("Invalid section type ");
        sb.append(i2);
        Preconditions.checkArgument(z2, sb.toString());
        this.zzq = str;
        this.zzr = zztVar;
        this.zzs = i2;
        this.zzt = bArr;
        if (i2 == i3 || zzq.zza(i2) != null) {
            string = (str == null || bArr == null) ? null : "Both content and blobContent set";
        } else {
            StringBuilder sb2 = new StringBuilder(32);
            sb2.append("Invalid section type ");
            sb2.append(i2);
            string = sb2.toString();
        }
        if (string != null) {
            throw new IllegalArgumentException(string);
        }
    }

    public static zzk zza(byte[] bArr) {
        return new zzk(bArr, zzp);
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i2) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 1, this.zzq, false);
        SafeParcelWriter.writeParcelable(parcel, 3, this.zzr, i2, false);
        SafeParcelWriter.writeInt(parcel, 4, this.zzs);
        SafeParcelWriter.writeByteArray(parcel, 5, this.zzt, false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    public zzk(String str, zzt zztVar) {
        this(str, zztVar, zzo, null);
    }

    @VisibleForTesting
    public zzk(String str, zzt zztVar, String str2) {
        this(str, zztVar, zzq.zzb(str2), null);
    }

    public zzk(byte[] bArr, zzt zztVar) {
        this(null, zztVar, zzo, bArr);
    }
}
