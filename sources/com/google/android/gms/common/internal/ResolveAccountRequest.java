package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.Nullable;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

@SafeParcelable.Class(creator = "ResolveAccountRequestCreator")
/* loaded from: classes3.dex */
public class ResolveAccountRequest extends AbstractSafeParcelable {
    public static final Parcelable.Creator<ResolveAccountRequest> CREATOR = new zam();

    @SafeParcelable.VersionField(id = 1)
    private final int zali;

    @SafeParcelable.Field(getter = "getSessionId", id = 3)
    private final int zapl;

    @SafeParcelable.Field(getter = "getSignInAccountHint", id = 4)
    private final GoogleSignInAccount zapm;

    @SafeParcelable.Field(getter = "getAccount", id = 2)
    private final Account zax;

    @SafeParcelable.Constructor
    public ResolveAccountRequest(@SafeParcelable.Param(id = 1) int i2, @SafeParcelable.Param(id = 2) Account account, @SafeParcelable.Param(id = 3) int i3, @SafeParcelable.Param(id = 4) GoogleSignInAccount googleSignInAccount) {
        this.zali = i2;
        this.zax = account;
        this.zapl = i3;
        this.zapm = googleSignInAccount;
    }

    public Account getAccount() {
        return this.zax;
    }

    public int getSessionId() {
        return this.zapl;
    }

    @Nullable
    public GoogleSignInAccount getSignInAccountHint() {
        return this.zapm;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 1, this.zali);
        SafeParcelWriter.writeParcelable(parcel, 2, getAccount(), i2, false);
        SafeParcelWriter.writeInt(parcel, 3, getSessionId());
        SafeParcelWriter.writeParcelable(parcel, 4, getSignInAccountHint(), i2, false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    public ResolveAccountRequest(Account account, int i2, GoogleSignInAccount googleSignInAccount) {
        this(2, account, i2, googleSignInAccount);
    }
}
