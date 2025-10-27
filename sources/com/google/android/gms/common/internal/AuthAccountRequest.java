package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.IAccountAccessor;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import javax.annotation.Nullable;

@SafeParcelable.Class(creator = "AuthAccountRequestCreator")
/* loaded from: classes3.dex */
public class AuthAccountRequest extends AbstractSafeParcelable {
    public static final Parcelable.Creator<AuthAccountRequest> CREATOR = new zaa();

    @SafeParcelable.VersionField(id = 1)
    private final int zali;

    @SafeParcelable.Field(id = 2)
    @Deprecated
    private final IBinder zaob;

    @SafeParcelable.Field(id = 3)
    private final Scope[] zaoc;

    @SafeParcelable.Field(id = 4)
    private Integer zaod;

    @SafeParcelable.Field(id = 5)
    private Integer zaoe;

    @SafeParcelable.Field(id = 6, type = "android.accounts.Account")
    private Account zax;

    @SafeParcelable.Constructor
    public AuthAccountRequest(@SafeParcelable.Param(id = 1) int i2, @SafeParcelable.Param(id = 2) IBinder iBinder, @SafeParcelable.Param(id = 3) Scope[] scopeArr, @SafeParcelable.Param(id = 4) Integer num, @SafeParcelable.Param(id = 5) Integer num2, @SafeParcelable.Param(id = 6) Account account) {
        this.zali = i2;
        this.zaob = iBinder;
        this.zaoc = scopeArr;
        this.zaod = num;
        this.zaoe = num2;
        this.zax = account;
    }

    public Account getAccount() {
        Account account = this.zax;
        if (account != null) {
            return account;
        }
        IBinder iBinder = this.zaob;
        if (iBinder != null) {
            return AccountAccessor.getAccountBinderSafe(IAccountAccessor.Stub.asInterface(iBinder));
        }
        return null;
    }

    @Nullable
    public Integer getOauthPolicy() {
        return this.zaod;
    }

    @Nullable
    public Integer getPolicyAction() {
        return this.zaoe;
    }

    public Set<Scope> getScopes() {
        return new HashSet(Arrays.asList(this.zaoc));
    }

    public AuthAccountRequest setOauthPolicy(@Nullable Integer num) {
        this.zaod = num;
        return this;
    }

    public AuthAccountRequest setPolicyAction(@Nullable Integer num) {
        this.zaoe = num;
        return this;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        int iBeginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeInt(parcel, 1, this.zali);
        SafeParcelWriter.writeIBinder(parcel, 2, this.zaob, false);
        SafeParcelWriter.writeTypedArray(parcel, 3, this.zaoc, i2, false);
        SafeParcelWriter.writeIntegerObject(parcel, 4, this.zaod, false);
        SafeParcelWriter.writeIntegerObject(parcel, 5, this.zaoe, false);
        SafeParcelWriter.writeParcelable(parcel, 6, this.zax, i2, false);
        SafeParcelWriter.finishObjectHeader(parcel, iBeginObjectHeader);
    }

    @Deprecated
    public AuthAccountRequest(IAccountAccessor iAccountAccessor, Set<Scope> set) {
        this(3, iAccountAccessor.asBinder(), (Scope[]) set.toArray(new Scope[set.size()]), null, null, null);
    }

    public AuthAccountRequest(Account account, Set<Scope> set) {
        this(3, null, (Scope[]) set.toArray(new Scope[set.size()]), null, null, (Account) Preconditions.checkNotNull(account));
    }
}
