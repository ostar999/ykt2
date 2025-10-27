package org.apache.http.impl.auth;

import org.apache.http.annotation.Immutable;
import org.apache.http.auth.AuthScheme;
import org.apache.http.auth.AuthSchemeFactory;
import org.apache.http.params.HttpParams;

@Immutable
/* loaded from: classes9.dex */
public class BasicSchemeFactory implements AuthSchemeFactory {
    @Override // org.apache.http.auth.AuthSchemeFactory
    public AuthScheme newInstance(HttpParams httpParams) {
        return new BasicScheme();
    }
}
