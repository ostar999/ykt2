package com.google.android.gms.signin.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.ResolveAccountRequest;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;

/* loaded from: classes3.dex */
public final class zah implements Parcelable.Creator<zai> {
    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ zai createFromParcel(Parcel parcel) {
        int iValidateObjectHeader = SafeParcelReader.validateObjectHeader(parcel);
        int i2 = 0;
        ResolveAccountRequest resolveAccountRequest = null;
        while (parcel.dataPosition() < iValidateObjectHeader) {
            int header = SafeParcelReader.readHeader(parcel);
            int fieldId = SafeParcelReader.getFieldId(header);
            if (fieldId == 1) {
                i2 = SafeParcelReader.readInt(parcel, header);
            } else if (fieldId != 2) {
                SafeParcelReader.skipUnknownField(parcel, header);
            } else {
                resolveAccountRequest = (ResolveAccountRequest) SafeParcelReader.createParcelable(parcel, header, ResolveAccountRequest.CREATOR);
            }
        }
        SafeParcelReader.ensureAtEnd(parcel, iValidateObjectHeader);
        return new zai(i2, resolveAccountRequest);
    }

    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ zai[] newArray(int i2) {
        return new zai[i2];
    }
}
