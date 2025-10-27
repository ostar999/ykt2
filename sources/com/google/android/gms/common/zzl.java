package com.google.android.gms.common;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;

/* loaded from: classes3.dex */
public final class zzl implements Parcelable.Creator<zzk> {
    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ zzk createFromParcel(Parcel parcel) {
        int iValidateObjectHeader = SafeParcelReader.validateObjectHeader(parcel);
        String strCreateString = null;
        boolean z2 = false;
        boolean z3 = false;
        IBinder iBinder = null;
        while (parcel.dataPosition() < iValidateObjectHeader) {
            int header = SafeParcelReader.readHeader(parcel);
            int fieldId = SafeParcelReader.getFieldId(header);
            if (fieldId == 1) {
                strCreateString = SafeParcelReader.createString(parcel, header);
            } else if (fieldId == 2) {
                iBinder = SafeParcelReader.readIBinder(parcel, header);
            } else if (fieldId == 3) {
                z2 = SafeParcelReader.readBoolean(parcel, header);
            } else if (fieldId != 4) {
                SafeParcelReader.skipUnknownField(parcel, header);
            } else {
                z3 = SafeParcelReader.readBoolean(parcel, header);
            }
        }
        SafeParcelReader.ensureAtEnd(parcel, iValidateObjectHeader);
        return new zzk(strCreateString, iBinder, z2, z3);
    }

    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ zzk[] newArray(int i2) {
        return new zzk[i2];
    }
}
