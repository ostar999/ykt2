package com.google.android.gms.internal.icing;

import android.accounts.Account;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.ShowFirstParty;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import java.util.Arrays;
import java.util.BitSet;

@ShowFirstParty
@SafeParcelable.Class(creator = "DocumentContentsCreator")
@SafeParcelable.Reserved({1000})
/* loaded from: classes3.dex */
public final class zzh extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzh> CREATOR = new zzj();

    @SafeParcelable.Field(id = 4)
    private final Account account;

    @SafeParcelable.Field(id = 2)
    private final String zzj;

    @SafeParcelable.Field(id = 3)
    private final boolean zzk;

    @SafeParcelable.Field(id = 1)
    private final zzk[] zzl;

    @SafeParcelable.Constructor
    public zzh(@SafeParcelable.Param(id = 1) zzk[] zzkVarArr, @SafeParcelable.Param(id = 2) String str, @SafeParcelable.Param(id = 3) boolean z2, @SafeParcelable.Param(id = 4) Account account) {
        this.zzl = zzkVarArr;
        this.zzj = str;
        this.zzk = z2;
        this.account = account;
    }

    public final boolean equals(Object obj) {
        if (obj instanceof zzh) {
            zzh zzhVar = (zzh) obj;
            if (Objects.equal(this.zzj, zzhVar.zzj) && Objects.equal(Boolean.valueOf(this.zzk), Boolean.valueOf(zzhVar.zzk)) && Objects.equal(this.account, zzhVar.account) && Arrays.equals(this.zzl, zzhVar.zzl)) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        return Objects.hashCode(this.zzj, Boolean.valueOf(this.zzk), this.account, Integer.valueOf(Arrays.hashCode(this.zzl)));
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i2) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeTypedArray(parcel, 1, this.zzl, i2, false);
        SafeParcelWriter.writeString(parcel, 2, this.zzj, false);
        SafeParcelWriter.writeBoolean(parcel, 3, this.zzk);
        SafeParcelWriter.writeParcelable(parcel, 4, this.account, i2, false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    public zzh(String str, boolean z2, Account account, zzk... zzkVarArr) {
        this(zzkVarArr, str, z2, account);
        if (zzkVarArr != null) {
            BitSet bitSet = new BitSet(zzq.zzy.length);
            for (zzk zzkVar : zzkVarArr) {
                int i2 = zzkVar.zzs;
                if (i2 != -1) {
                    if (bitSet.get(i2)) {
                        String strValueOf = String.valueOf(zzq.zza(i2));
                        throw new IllegalArgumentException(strValueOf.length() != 0 ? "Duplicate global search section type ".concat(strValueOf) : new String("Duplicate global search section type "));
                    }
                    bitSet.set(i2);
                }
            }
        }
    }
}
