package com.google.android.gms.common.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

@SafeParcelable.Class(creator = "SignInButtonConfigCreator")
/* loaded from: classes3.dex */
public class SignInButtonConfig extends AbstractSafeParcelable {
    public static final Parcelable.Creator<SignInButtonConfig> CREATOR = new zao();

    @SafeParcelable.VersionField(id = 1)
    private final int zali;

    @SafeParcelable.Field(getter = "getScopes", id = 4)
    @Deprecated
    private final Scope[] zaoc;

    @SafeParcelable.Field(getter = "getButtonSize", id = 2)
    private final int zapr;

    @SafeParcelable.Field(getter = "getColorScheme", id = 3)
    private final int zaps;

    @SafeParcelable.Constructor
    public SignInButtonConfig(@SafeParcelable.Param(id = 1) int i2, @SafeParcelable.Param(id = 2) int i3, @SafeParcelable.Param(id = 3) int i4, @SafeParcelable.Param(id = 4) Scope[] scopeArr) {
        this.zali = i2;
        this.zapr = i3;
        this.zaps = i4;
        this.zaoc = scopeArr;
    }

    public int getButtonSize() {
        return this.zapr;
    }

    public int getColorScheme() {
        return this.zaps;
    }

    @Deprecated
    public Scope[] getScopes() {
        return this.zaoc;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 1, this.zali);
        SafeParcelWriter.writeInt(parcel, 2, getButtonSize());
        SafeParcelWriter.writeInt(parcel, 3, getColorScheme());
        SafeParcelWriter.writeTypedArray(parcel, 4, getScopes(), i2, false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    public SignInButtonConfig(int i2, int i3, Scope[] scopeArr) {
        this(1, i2, i3, null);
    }
}
