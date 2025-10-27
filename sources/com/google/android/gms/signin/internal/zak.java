package com.google.android.gms.signin.internal;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.Nullable;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.ResolveAccountResponse;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

@SafeParcelable.Class(creator = "SignInResponseCreator")
/* loaded from: classes3.dex */
public final class zak extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zak> CREATOR = new zaj();

    @SafeParcelable.VersionField(id = 1)
    private final int versionCode;

    @SafeParcelable.Field(getter = "getConnectionResult", id = 2)
    private final ConnectionResult zapo;

    @Nullable
    @SafeParcelable.Field(getter = "getResolveAccountResponse", id = 3)
    private final ResolveAccountResponse zata;

    @SafeParcelable.Constructor
    public zak(@SafeParcelable.Param(id = 1) int i2, @SafeParcelable.Param(id = 2) ConnectionResult connectionResult, @Nullable @SafeParcelable.Param(id = 3) ResolveAccountResponse resolveAccountResponse) {
        this.versionCode = i2;
        this.zapo = connectionResult;
        this.zata = resolveAccountResponse;
    }

    public final ConnectionResult getConnectionResult() {
        return this.zapo;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i2) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 1, this.versionCode);
        SafeParcelWriter.writeParcelable(parcel, 2, this.zapo, i2, false);
        SafeParcelWriter.writeParcelable(parcel, 3, this.zata, i2, false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    @Nullable
    public final ResolveAccountResponse zacv() {
        return this.zata;
    }

    public zak(int i2) {
        this(new ConnectionResult(8, null), null);
    }

    private zak(ConnectionResult connectionResult, @Nullable ResolveAccountResponse resolveAccountResponse) {
        this(1, connectionResult, null);
    }
}
