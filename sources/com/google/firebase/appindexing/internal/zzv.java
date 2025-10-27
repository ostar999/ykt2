package com.google.firebase.appindexing.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;

/* loaded from: classes4.dex */
public final class zzv implements Parcelable.Creator<zzc> {
    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ zzc createFromParcel(Parcel parcel) {
        int iValidateObjectHeader = SafeParcelReader.validateObjectHeader(parcel);
        int i2 = 0;
        boolean z2 = false;
        boolean z3 = false;
        String strCreateString = null;
        String strCreateString2 = null;
        byte[] bArrCreateByteArray = null;
        while (parcel.dataPosition() < iValidateObjectHeader) {
            int header = SafeParcelReader.readHeader(parcel);
            switch (SafeParcelReader.getFieldId(header)) {
                case 1:
                    i2 = SafeParcelReader.readInt(parcel, header);
                    break;
                case 2:
                    z2 = SafeParcelReader.readBoolean(parcel, header);
                    break;
                case 3:
                    strCreateString = SafeParcelReader.createString(parcel, header);
                    break;
                case 4:
                    strCreateString2 = SafeParcelReader.createString(parcel, header);
                    break;
                case 5:
                    bArrCreateByteArray = SafeParcelReader.createByteArray(parcel, header);
                    break;
                case 6:
                    z3 = SafeParcelReader.readBoolean(parcel, header);
                    break;
                default:
                    SafeParcelReader.skipUnknownField(parcel, header);
                    break;
            }
        }
        SafeParcelReader.ensureAtEnd(parcel, iValidateObjectHeader);
        return new zzc(i2, z2, strCreateString, strCreateString2, bArrCreateByteArray, z3);
    }

    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ zzc[] newArray(int i2) {
        return new zzc[i2];
    }
}
