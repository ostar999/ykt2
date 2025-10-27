package org.apache.http.auth;

import java.security.Principal;

/* loaded from: classes9.dex */
public interface Credentials {
    String getPassword();

    Principal getUserPrincipal();
}
