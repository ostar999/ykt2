package com.google.android.gms.common.server;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;

/* loaded from: classes3.dex */
public final class zaa implements Parcelable.Creator<FavaDiagnosticsEntity> {
    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ FavaDiagnosticsEntity createFromParcel(Parcel parcel) {
        int iValidateObjectHeader = SafeParcelReader.validateObjectHeader(parcel);
        int i2 = 0;
        String strCreateString = null;
        int i3 = 0;
        while (parcel.dataPosition() < iValidateObjectHeader) {
            int header = SafeParcelReader.readHeader(parcel);
            int fieldId = SafeParcelReader.getFieldId(header);
            if (fieldId == 1) {
                i2 = SafeParcelReader.readInt(parcel, header);
            } else if (fieldId == 2) {
                strCreateString = SafeParcelReader.createString(parcel, header);
            } else if (fieldId != 3) {
                SafeParcelReader.skipUnknownField(parcel, header);
            } else {
                i3 = SafeParcelReader.readInt(parcel, header);
            }
        }
        SafeParcelReader.ensureAtEnd(parcel, iValidateObjectHeader);
        return new FavaDiagnosticsEntity(i2, strCreateString, i3);
    }

    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ FavaDiagnosticsEntity[] newArray(int i2) {
        return new FavaDiagnosticsEntity[i2];
    }
}
