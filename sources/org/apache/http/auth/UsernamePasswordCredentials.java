package org.apache.http.auth;

import java.io.Serializable;
import java.security.Principal;
import org.apache.http.annotation.Immutable;
import org.apache.http.util.LangUtils;

@Immutable
/* loaded from: classes9.dex */
public class UsernamePasswordCredentials implements Credentials, Serializable {
    private static final long serialVersionUID = 243343858802739403L;
    private final String password;
    private final BasicUserPrincipal principal;

    public UsernamePasswordCredentials(String str) {
        if (str == null) {
            throw new IllegalArgumentException("Username:password string may not be null");
        }
        int iIndexOf = str.indexOf(58);
        if (iIndexOf >= 0) {
            this.principal = new BasicUserPrincipal(str.substring(0, iIndexOf));
            this.password = str.substring(iIndexOf + 1);
        } else {
            this.principal = new BasicUserPrincipal(str);
            this.password = null;
        }
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof UsernamePasswordCredentials) && LangUtils.equals(this.principal, ((UsernamePasswordCredentials) obj).principal);
    }

    @Override // org.apache.http.auth.Credentials
    public String getPassword() {
        return this.password;
    }

    public String getUserName() {
        return this.principal.getName();
    }

    @Override // org.apache.http.auth.Credentials
    public Principal getUserPrincipal() {
        return this.principal;
    }

    public int hashCode() {
        return this.principal.hashCode();
    }

    public String toString() {
        return this.principal.toString();
    }

    public UsernamePasswordCredentials(String str, String str2) {
        if (str != null) {
            this.principal = new BasicUserPrincipal(str);
            this.password = str2;
            return;
        }
        throw new IllegalArgumentException("Username may not be null");
    }
}
