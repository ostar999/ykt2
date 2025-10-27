package org.apache.http.auth.params;

import org.apache.http.annotation.Immutable;
import org.apache.http.params.HttpParams;

@Immutable
/* loaded from: classes9.dex */
public final class AuthParams {
    private AuthParams() {
    }

    public static String getCredentialCharset(HttpParams httpParams) {
        if (httpParams == null) {
            throw new IllegalArgumentException("HTTP parameters may not be null");
        }
        String str = (String) httpParams.getParameter(AuthPNames.CREDENTIAL_CHARSET);
        return str == null ? "US-ASCII" : str;
    }

    public static void setCredentialCharset(HttpParams httpParams, String str) {
        if (httpParams == null) {
            throw new IllegalArgumentException("HTTP parameters may not be null");
        }
        httpParams.setParameter(AuthPNames.CREDENTIAL_CHARSET, str);
    }
}
