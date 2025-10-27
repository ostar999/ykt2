package com.google.android.gms.internal.icing;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.ShowFirstParty;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import java.util.ArrayList;

@ShowFirstParty
@SafeParcelable.Class(creator = "FeatureCreator")
@SafeParcelable.Reserved({1000})
/* loaded from: classes3.dex */
public final class zzm extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzm> CREATOR = new zzp();

    @SafeParcelable.Field(id = 1)
    private final int id;

    @SafeParcelable.Field(id = 2)
    private final Bundle zzu;

    @SafeParcelable.Constructor
    public zzm(@SafeParcelable.Param(id = 1) int i2, @SafeParcelable.Param(id = 2) Bundle bundle) {
        this.id = i2;
        this.zzu = bundle;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzm)) {
            return false;
        }
        zzm zzmVar = (zzm) obj;
        if (this.id != zzmVar.id) {
            return false;
        }
        Bundle bundle = this.zzu;
        if (bundle == null) {
            return zzmVar.zzu == null;
        }
        if (zzmVar.zzu == null || bundle.size() != zzmVar.zzu.size()) {
            return false;
        }
        for (String str : this.zzu.keySet()) {
            if (!zzmVar.zzu.containsKey(str) || !Objects.equal(this.zzu.getString(str), zzmVar.zzu.getString(str))) {
                return false;
            }
        }
        return true;
    }

    public final int hashCode() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(Integer.valueOf(this.id));
        Bundle bundle = this.zzu;
        if (bundle != null) {
            for (String str : bundle.keySet()) {
                arrayList.add(str);
                arrayList.add(this.zzu.getString(str));
            }
        }
        return Objects.hashCode(arrayList.toArray(new Object[0]));
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i2) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 1, this.id);
        SafeParcelWriter.writeBundle(parcel, 2, this.zzu, false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }
}
