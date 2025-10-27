package com.google.android.gms.auth.api.signin.internal;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelReader;

/* loaded from: classes3.dex */
public final class zaa implements Parcelable.Creator<GoogleSignInOptionsExtensionParcelable> {
    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ GoogleSignInOptionsExtensionParcelable createFromParcel(Parcel parcel) {
        int iValidateObjectHeader = SafeParcelReader.validateObjectHeader(parcel);
        int i2 = 0;
        Bundle bundleCreateBundle = null;
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
                bundleCreateBundle = SafeParcelReader.createBundle(parcel, header);
            }
        }
        SafeParcelReader.ensureAtEnd(parcel, iValidateObjectHeader);
        return new GoogleSignInOptionsExtensionParcelable(i2, i3, bundleCreateBundle);
    }

    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ GoogleSignInOptionsExtensionParcelable[] newArray(int i2) {
        return new GoogleSignInOptionsExtensionParcelable[i2];
    }
}
