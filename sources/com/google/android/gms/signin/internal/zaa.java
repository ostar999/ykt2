package com.google.android.gms.signin.internal;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;

/* loaded from: classes3.dex */
public final class zaa implements Parcelable.Creator<zab> {
    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ zab createFromParcel(Parcel parcel) {
        int iValidateObjectHeader = SafeParcelReader.validateObjectHeader(parcel);
        int i2 = 0;
        Intent intent = null;
        int i3 = 0;
        while (parcel.dataPosition() < iValidateObjectHeader) {
            int header = SafeParcelReader.readHeader(parcel);
            int fieldId = SafeParcelReader.getFieldId(header);
            if (fieldId == 1) {
                i2 = SafeParcelReader.readInt(parcel, header);
            } else if (fieldId == 2) {
                i3 = SafeParcelReader.readInt(parcel, header);
            } else if (fieldId != 3) {
                SafeParcelReader.skipUnknownField(parcel, header);
            } else {
                intent = (Intent) SafeParcelReader.createParcelable(parcel, header, Intent.CREATOR);
            }
        }
        SafeParcelReader.ensureAtEnd(parcel, iValidateObjectHeader);
        return new zab(i2, i3, intent);
    }

    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ zab[] newArray(int i2) {
        return new zab[i2];
    }
}
