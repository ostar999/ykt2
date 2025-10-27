package com.google.android.gms.common.server.response;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;
import java.util.ArrayList;

/* loaded from: classes3.dex */
public final class zao implements Parcelable.Creator<zaj> {
    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ zaj createFromParcel(Parcel parcel) {
        int iValidateObjectHeader = SafeParcelReader.validateObjectHeader(parcel);
        int i2 = 0;
        ArrayList arrayListCreateTypedList = null;
        String strCreateString = null;
        while (parcel.dataPosition() < iValidateObjectHeader) {
            int header = SafeParcelReader.readHeader(parcel);
            int fieldId = SafeParcelReader.getFieldId(header);
            if (fieldId == 1) {
                i2 = SafeParcelReader.readInt(parcel, header);
            } else if (fieldId == 2) {
                arrayListCreateTypedList = SafeParcelReader.createTypedList(parcel, header, zam.CREATOR);
            } else if (fieldId != 3) {
                SafeParcelReader.skipUnknownField(parcel, header);
            } else {
                strCreateString = SafeParcelReader.createString(parcel, header);
            }
        }
        SafeParcelReader.ensureAtEnd(parcel, iValidateObjectHeader);
        return new zaj(i2, arrayListCreateTypedList, strCreateString);
    }

    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ zaj[] newArray(int i2) {
        return new zaj[i2];
    }
}
