package org.eclipse.jetty.security.authentication;

import java.io.IOException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.jetty.security.ServerAuthException;
import org.eclipse.jetty.security.UserAuthentication;
import org.eclipse.jetty.server.Authentication;
import org.eclipse.jetty.server.UserIdentity;
import org.eclipse.jetty.util.B64Code;
import org.eclipse.jetty.util.security.Constraint;

/* loaded from: classes9.dex */
public class BasicAuthenticator extends LoginAuthenticator {
    @Override // org.eclipse.jetty.security.Authenticator
    public String getAuthMethod() {
        return Constraint.__BASIC_AUTH;
    }

    @Override // org.eclipse.jetty.security.Authenticator
    public boolean secureResponse(ServletRequest servletRequest, ServletResponse servletResponse, boolean z2, Authentication.User user) throws ServerAuthException {
        return true;
    }

    @Override // org.eclipse.jetty.security.Authenticator
    public Authentication validateRequest(ServletRequest servletRequest, ServletResponse servletResponse, boolean z2) throws ServerAuthException {
        int iIndexOf;
        String strDecode;
        int iIndexOf2;
        UserIdentity userIdentityLogin;
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        String header = httpServletRequest.getHeader("Authorization");
        try {
            if (!z2) {
                return new DeferredAuthentication(this);
            }
            if (header != null && (iIndexOf = header.indexOf(32)) > 0 && "basic".equalsIgnoreCase(header.substring(0, iIndexOf)) && (iIndexOf2 = (strDecode = B64Code.decode(header.substring(iIndexOf + 1), "ISO-8859-1")).indexOf(58)) > 0 && (userIdentityLogin = login(strDecode.substring(0, iIndexOf2), strDecode.substring(iIndexOf2 + 1), httpServletRequest)) != null) {
                return new UserAuthentication(getAuthMethod(), userIdentityLogin);
            }
            if (DeferredAuthentication.isDeferred(httpServletResponse)) {
                return Authentication.UNAUTHENTICATED;
            }
            httpServletResponse.setHeader("WWW-Authenticate", "basic realm=\"" + this._loginService.getName() + '\"');
            httpServletResponse.sendError(401);
            return Authentication.SEND_CONTINUE;
        } catch (IOException e2) {
            throw new ServerAuthException(e2);
        }
    }
}
