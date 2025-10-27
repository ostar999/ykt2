package com.google.firebase.appindexing.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;

/* loaded from: classes4.dex */
public final class zzx implements Parcelable.Creator<zzy> {
    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ zzy createFromParcel(Parcel parcel) {
        int iValidateObjectHeader = SafeParcelReader.validateObjectHeader(parcel);
        int i2 = 0;
        Thing[] thingArr = null;
        String[] strArrCreateStringArray = null;
        String[] strArrCreateStringArray2 = null;
        zza zzaVar = null;
        String strCreateString = null;
        String strCreateString2 = null;
        while (parcel.dataPosition() < iValidateObjectHeader) {
            int header = SafeParcelReader.readHeader(parcel);
            switch (SafeParcelReader.getFieldId(header)) {
                case 1:
                    i2 = SafeParcelReader.readInt(parcel, header);
                    break;
                case 2:
                    thingArr = (Thing[]) SafeParcelReader.createTypedArray(parcel, header, Thing.CREATOR);
                    break;
                case 3:
                    strArrCreateStringArray = SafeParcelReader.createStringArray(parcel, header);
                    break;
                case 4:
                default:
                    SafeParcelReader.skipUnknownField(parcel, header);
                    break;
                case 5:
                    strArrCreateStringArray2 = SafeParcelReader.createStringArray(parcel, header);
                    break;
                case 6:
                    zzaVar = (zza) SafeParcelReader.createParcelable(parcel, header, zza.CREATOR);
                    break;
                case 7:
                    strCreateString = SafeParcelReader.createString(parcel, header);
                    break;
                case 8:
                    strCreateString2 = SafeParcelReader.createString(parcel, header);
                    break;
            }
        }
        SafeParcelReader.ensureAtEnd(parcel, iValidateObjectHeader);
        return new zzy(i2, thingArr, strArrCreateStringArray, strArrCreateStringArray2, zzaVar, strCreateString, strCreateString2);
    }

    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ zzy[] newArray(int i2) {
        return new zzy[i2];
    }
}
