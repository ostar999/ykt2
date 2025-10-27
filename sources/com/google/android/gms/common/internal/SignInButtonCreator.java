package com.google.android.gms.common.internal;

import android.content.Context;
import android.os.IBinder;
import android.os.IInterface;
import android.view.View;
import com.google.android.gms.dynamic.ObjectWrapper;
import com.google.android.gms.dynamic.RemoteCreator;

/* loaded from: classes3.dex */
public final class SignInButtonCreator extends RemoteCreator<ISignInButtonCreator> {
    private static final SignInButtonCreator zapt = new SignInButtonCreator();

    private SignInButtonCreator() {
        super("com.google.android.gms.common.ui.SignInButtonCreatorImpl");
    }

    public static View createView(Context context, int i2, int i3) throws RemoteCreator.RemoteCreatorException {
        return zapt.zaa(context, i2, i3);
    }

    private final View zaa(Context context, int i2, int i3) throws RemoteCreator.RemoteCreatorException {
        try {
            SignInButtonConfig signInButtonConfig = new SignInButtonConfig(i2, i3, null);
            return (View) ObjectWrapper.unwrap(getRemoteCreatorInstance(context).newSignInButtonFromConfig(ObjectWrapper.wrap(context), signInButtonConfig));
        } catch (Exception e2) {
            StringBuilder sb = new StringBuilder(64);
            sb.append("Could not get button with size ");
            sb.append(i2);
            sb.append(" and color ");
            sb.append(i3);
            throw new RemoteCreator.RemoteCreatorException(sb.toString(), e2);
        }
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.google.android.gms.dynamic.RemoteCreator
    public final ISignInButtonCreator getRemoteCreator(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.common.internal.ISignInButtonCreator");
        return iInterfaceQueryLocalInterface instanceof ISignInButtonCreator ? (ISignInButtonCreator) iInterfaceQueryLocalInterface : new zah(iBinder);
    }
}
