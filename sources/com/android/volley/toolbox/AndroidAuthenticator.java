package com.android.volley.toolbox;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.android.volley.AuthFailureError;
import com.huawei.hms.support.api.entity.core.CommonCode;
import java.io.IOException;

/* loaded from: classes2.dex */
public class AndroidAuthenticator implements Authenticator {
    private final Account mAccount;
    private final String mAuthTokenType;
    private final Context mContext;
    private final boolean mNotifyAuthFailure;

    public AndroidAuthenticator(Context context, Account account, String str) {
        this(context, account, str, false);
    }

    public Account getAccount() {
        return this.mAccount;
    }

    @Override // com.android.volley.toolbox.Authenticator
    public String getAuthToken() throws OperationCanceledException, IOException, AuthFailureError, AuthenticatorException {
        String string;
        AccountManagerFuture<Bundle> authToken = AccountManager.get(this.mContext).getAuthToken(this.mAccount, this.mAuthTokenType, this.mNotifyAuthFailure, null, null);
        try {
            Bundle result = authToken.getResult();
            if (!authToken.isDone() || authToken.isCancelled()) {
                string = null;
            } else {
                if (result.containsKey(CommonCode.Resolution.HAS_RESOLUTION_FROM_APK)) {
                    throw new AuthFailureError((Intent) result.getParcelable(CommonCode.Resolution.HAS_RESOLUTION_FROM_APK));
                }
                string = result.getString("authtoken");
            }
            if (string != null) {
                return string;
            }
            throw new AuthFailureError("Got null auth token for type: " + this.mAuthTokenType);
        } catch (Exception e2) {
            throw new AuthFailureError("Error while retrieving auth token", e2);
        }
    }

    @Override // com.android.volley.toolbox.Authenticator
    public void invalidateAuthToken(String str) {
        AccountManager.get(this.mContext).invalidateAuthToken(this.mAccount.type, str);
    }

    public AndroidAuthenticator(Context context, Account account, String str, boolean z2) {
        this.mContext = context;
        this.mAccount = account;
        this.mAuthTokenType = str;
        this.mNotifyAuthFailure = z2;
    }
}
