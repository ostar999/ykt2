package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.Feature;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;

/* loaded from: classes3.dex */
public final class zzd implements Parcelable.Creator<GetServiceRequest> {
    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ GetServiceRequest createFromParcel(Parcel parcel) {
        int iValidateObjectHeader = SafeParcelReader.validateObjectHeader(parcel);
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        boolean z2 = false;
        String strCreateString = null;
        IBinder iBinder = null;
        Scope[] scopeArr = null;
        Bundle bundleCreateBundle = null;
        Account account = null;
        Feature[] featureArr = null;
        Feature[] featureArr2 = null;
        while (parcel.dataPosition() < iValidateObjectHeader) {
            int header = SafeParcelReader.readHeader(parcel);
            switch (SafeParcelReader.getFieldId(header)) {
                case 1:
                    i2 = SafeParcelReader.readInt(parcel, header);
                    break;
                case 2:
                    i3 = SafeParcelReader.readInt(parcel, header);
                    break;
                case 3:
                    i4 = SafeParcelReader.readInt(parcel, header);
                    break;
                case 4:
                    strCreateString = SafeParcelReader.createString(parcel, header);
                    break;
                case 5:
                    iBinder = SafeParcelReader.readIBinder(parcel, header);
                    break;
                case 6:
                    scopeArr = (Scope[]) SafeParcelReader.createTypedArray(parcel, header, Scope.CREATOR);
                    break;
                case 7:
                    bundleCreateBundle = SafeParcelReader.createBundle(parcel, header);
                    break;
                case 8:
                    account = (Account) SafeParcelReader.createParcelable(parcel, header, Account.CREATOR);
                    break;
                case 9:
                default:
                    SafeParcelReader.skipUnknownField(parcel, header);
                    break;
                case 10:
                    featureArr = (Feature[]) SafeParcelReader.createTypedArray(parcel, header, Feature.CREATOR);
                    break;
                case 11:
                    featureArr2 = (Feature[]) SafeParcelReader.createTypedArray(parcel, header, Feature.CREATOR);
                    break;
                case 12:
                    z2 = SafeParcelReader.readBoolean(parcel, header);
                    break;
            }
        }
        SafeParcelReader.ensureAtEnd(parcel, iValidateObjectHeader);
        return new GetServiceRequest(i2, i3, i4, strCreateString, iBinder, scopeArr, bundleCreateBundle, account, featureArr, featureArr2, z2);
    }

    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ GetServiceRequest[] newArray(int i2) {
        return new GetServiceRequest[i2];
    }
}
