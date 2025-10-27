package com.google.firebase.appindexing.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

@SafeParcelable.Class(creator = "MetadataImplCreator")
@SafeParcelable.Reserved({1000})
/* loaded from: classes4.dex */
public final class zzc extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzc> CREATOR = new zzv();

    @SafeParcelable.Field(getter = "getEventStatus", id = 1)
    private int zzaq;

    @SafeParcelable.Field(getter = "isUploadable", id = 2)
    private final boolean zzel;

    @SafeParcelable.Field(getter = "isContextOnly", id = 6)
    private final boolean zzem;

    @SafeParcelable.Field(getter = "getCompletionToken", id = 3)
    private final String zzes;

    @SafeParcelable.Field(getter = "getAccountName", id = 4)
    private final String zzet;

    @SafeParcelable.Field(getter = "getSsbContext", id = 5)
    private final byte[] zzeu;

    @SafeParcelable.Constructor
    public zzc(@SafeParcelable.Param(id = 1) int i2, @SafeParcelable.Param(id = 2) boolean z2, @SafeParcelable.Param(id = 3) String str, @SafeParcelable.Param(id = 4) String str2, @SafeParcelable.Param(id = 5) byte[] bArr, @SafeParcelable.Param(id = 6) boolean z3) {
        this.zzaq = i2;
        this.zzel = z2;
        this.zzes = str;
        this.zzet = str2;
        this.zzeu = bArr;
        this.zzem = z3;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("MetadataImpl { ");
        sb.append("{ eventStatus: '");
        sb.append(this.zzaq);
        sb.append("' } ");
        sb.append("{ uploadable: '");
        sb.append(this.zzel);
        sb.append("' } ");
        if (this.zzes != null) {
            sb.append("{ completionToken: '");
            sb.append(this.zzes);
            sb.append("' } ");
        }
        if (this.zzet != null) {
            sb.append("{ accountName: '");
            sb.append(this.zzet);
            sb.append("' } ");
        }
        if (this.zzeu != null) {
            sb.append("{ ssbContext: [ ");
            for (byte b3 : this.zzeu) {
                sb.append("0x");
                sb.append(Integer.toHexString(b3));
                sb.append(" ");
            }
            sb.append("] } ");
        }
        sb.append("{ contextOnly: '");
        sb.append(this.zzem);
        sb.append("' } ");
        sb.append("}");
        return sb.toString();
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i2) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 1, this.zzaq);
        SafeParcelWriter.writeBoolean(parcel, 2, this.zzel);
        SafeParcelWriter.writeString(parcel, 3, this.zzes, false);
        SafeParcelWriter.writeString(parcel, 4, this.zzet, false);
        SafeParcelWriter.writeByteArray(parcel, 5, this.zzeu, false);
        SafeParcelWriter.writeBoolean(parcel, 6, this.zzem);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    public final void zzf(int i2) {
        this.zzaq = i2;
    }

    public zzc(boolean z2, String str, String str2, byte[] bArr, boolean z3) {
        this.zzaq = 0;
        this.zzel = z2;
        this.zzes = null;
        this.zzet = null;
        this.zzeu = null;
        this.zzem = false;
    }
}
