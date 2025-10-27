package org.apache.http.auth;

import org.apache.http.params.HttpParams;

/* loaded from: classes9.dex */
public interface AuthSchemeFactory {
    AuthScheme newInstance(HttpParams httpParams);
}
