package org.apache.http.auth;

import cn.hutool.core.text.StrPool;
import k.a;
import org.apache.http.annotation.NotThreadSafe;

@NotThreadSafe
/* loaded from: classes9.dex */
public class AuthState {
    private AuthScheme authScheme;
    private AuthScope authScope;
    private Credentials credentials;

    public AuthScheme getAuthScheme() {
        return this.authScheme;
    }

    public AuthScope getAuthScope() {
        return this.authScope;
    }

    public Credentials getCredentials() {
        return this.credentials;
    }

    public void invalidate() {
        this.authScheme = null;
        this.authScope = null;
        this.credentials = null;
    }

    public boolean isValid() {
        return this.authScheme != null;
    }

    public void setAuthScheme(AuthScheme authScheme) {
        if (authScheme == null) {
            invalidate();
        } else {
            this.authScheme = authScheme;
        }
    }

    public void setAuthScope(AuthScope authScope) {
        this.authScope = authScope;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("auth scope [");
        sb.append(this.authScope);
        sb.append("]; credentials set [");
        sb.append(this.credentials != null ? a.f27523u : a.f27524v);
        sb.append(StrPool.BRACKET_END);
        return sb.toString();
    }
}
