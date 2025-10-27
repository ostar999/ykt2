package com.google.android.gms.signin.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.ResolveAccountRequest;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

@SafeParcelable.Class(creator = "SignInRequestCreator")
/* loaded from: classes3.dex */
public final class zai extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zai> CREATOR = new zah();

    @SafeParcelable.VersionField(id = 1)
    private final int zali;

    @SafeParcelable.Field(getter = "getResolveAccountRequest", id = 2)
    private final ResolveAccountRequest zasz;

    @SafeParcelable.Constructor
    public zai(@SafeParcelable.Param(id = 1) int i2, @SafeParcelable.Param(id = 2) ResolveAccountRequest resolveAccountRequest) {
        this.zali = i2;
        this.zasz = resolveAccountRequest;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i2) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 1, this.zali);
        SafeParcelWriter.writeParcelable(parcel, 2, this.zasz, i2, false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    public zai(ResolveAccountRequest resolveAccountRequest) {
        this(1, resolveAccountRequest);
    }
}
