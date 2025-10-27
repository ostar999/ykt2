package org.apache.http.client.params;

import org.apache.http.annotation.Immutable;
import org.apache.http.params.HttpParams;

@Immutable
/* loaded from: classes9.dex */
public class HttpClientParams {
    private HttpClientParams() {
    }

    public static String getCookiePolicy(HttpParams httpParams) {
        if (httpParams == null) {
            throw new IllegalArgumentException("HTTP parameters may not be null");
        }
        String str = (String) httpParams.getParameter(ClientPNames.COOKIE_POLICY);
        return str == null ? CookiePolicy.BEST_MATCH : str;
    }

    public static boolean isAuthenticating(HttpParams httpParams) {
        if (httpParams != null) {
            return httpParams.getBooleanParameter(ClientPNames.HANDLE_AUTHENTICATION, true);
        }
        throw new IllegalArgumentException("HTTP parameters may not be null");
    }

    public static boolean isRedirecting(HttpParams httpParams) {
        if (httpParams != null) {
            return httpParams.getBooleanParameter(ClientPNames.HANDLE_REDIRECTS, true);
        }
        throw new IllegalArgumentException("HTTP parameters may not be null");
    }

    public static void setAuthenticating(HttpParams httpParams, boolean z2) {
        if (httpParams == null) {
            throw new IllegalArgumentException("HTTP parameters may not be null");
        }
        httpParams.setBooleanParameter(ClientPNames.HANDLE_AUTHENTICATION, z2);
    }

    public static void setCookiePolicy(HttpParams httpParams, String str) {
        if (httpParams == null) {
            throw new IllegalArgumentException("HTTP parameters may not be null");
        }
        httpParams.setParameter(ClientPNames.COOKIE_POLICY, str);
    }

    public static void setRedirecting(HttpParams httpParams, boolean z2) {
        if (httpParams == null) {
            throw new IllegalArgumentException("HTTP parameters may not be null");
        }
        httpParams.setBooleanParameter(ClientPNames.HANDLE_REDIRECTS, z2);
    }
}
