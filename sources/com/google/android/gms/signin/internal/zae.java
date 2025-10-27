package com.google.android.gms.signin.internal;

import android.os.IInterface;
import android.os.RemoteException;
import com.google.android.gms.common.internal.IAccountAccessor;

/* loaded from: classes3.dex */
public interface zae extends IInterface {
    void zaa(IAccountAccessor iAccountAccessor, int i2, boolean z2) throws RemoteException;

    void zaa(zai zaiVar, zac zacVar) throws RemoteException;

    void zam(int i2) throws RemoteException;
}
