package com.google.firebase.appindexing.internal;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import com.google.firebase.appindexing.internal.Thing;

/* loaded from: classes4.dex */
public final class zzw implements Parcelable.Creator<Thing.zza> {
    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ Thing.zza createFromParcel(Parcel parcel) {
        int iValidateObjectHeader = SafeParcelReader.validateObjectHeader(parcel);
        boolean z2 = false;
        String strCreateString = null;
        Bundle bundleCreateBundle = null;
        int i2 = 0;
        while (parcel.dataPosition() < iValidateObjectHeader) {
            int header = SafeParcelReader.readHeader(parcel);
            int fieldId = SafeParcelReader.getFieldId(header);
            if (fieldId == 1) {
                z2 = SafeParcelReader.readBoolean(parcel, header);
            } else if (fieldId == 2) {
                i2 = SafeParcelReader.readInt(parcel, header);
            } else if (fieldId == 3) {
                strCreateString = SafeParcelReader.createString(parcel, header);
            } else if (fieldId != 4) {
                SafeParcelReader.skipUnknownField(parcel, header);
            } else {
                bundleCreateBundle = SafeParcelReader.createBundle(parcel, header);
            }
        }
        SafeParcelReader.ensureAtEnd(parcel, iValidateObjectHeader);
        return new Thing.zza(z2, i2, strCreateString, bundleCreateBundle);
    }

    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ Thing.zza[] newArray(int i2) {
        return new Thing.zza[i2];
    }
}
