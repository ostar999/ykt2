package com.google.android.gms.common.internal;

import android.os.IInterface;
import android.os.RemoteException;
import com.google.android.gms.dynamic.IObjectWrapper;

/* loaded from: classes3.dex */
public interface ISignInButtonCreator extends IInterface {
    IObjectWrapper newSignInButton(IObjectWrapper iObjectWrapper, int i2, int i3) throws RemoteException;

    IObjectWrapper newSignInButtonFromConfig(IObjectWrapper iObjectWrapper, SignInButtonConfig signInButtonConfig) throws RemoteException;
}
