package org.eclipse.jetty.security.authentication;

import java.io.IOException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.jetty.http.HttpHeaders;
import org.eclipse.jetty.security.ServerAuthException;
import org.eclipse.jetty.security.UserAuthentication;
import org.eclipse.jetty.server.Authentication;
import org.eclipse.jetty.server.UserIdentity;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;
import org.eclipse.jetty.util.security.Constraint;

/* loaded from: classes9.dex */
public class SpnegoAuthenticator extends LoginAuthenticator {
    private static final Logger LOG = Log.getLogger((Class<?>) SpnegoAuthenticator.class);
    private String _authMethod;

    public SpnegoAuthenticator() {
        this._authMethod = Constraint.__SPNEGO_AUTH;
    }

    @Override // org.eclipse.jetty.security.Authenticator
    public String getAuthMethod() {
        return this._authMethod;
    }

    @Override // org.eclipse.jetty.security.Authenticator
    public boolean secureResponse(ServletRequest servletRequest, ServletResponse servletResponse, boolean z2, Authentication.User user) throws ServerAuthException {
        return true;
    }

    @Override // org.eclipse.jetty.security.Authenticator
    public Authentication validateRequest(ServletRequest servletRequest, ServletResponse servletResponse, boolean z2) throws ServerAuthException {
        UserIdentity userIdentityLogin;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        String header = ((HttpServletRequest) servletRequest).getHeader("Authorization");
        if (!z2) {
            return new DeferredAuthentication(this);
        }
        if (header != null) {
            return (!header.startsWith(HttpHeaders.NEGOTIATE) || (userIdentityLogin = login(null, header.substring(10), servletRequest)) == null) ? Authentication.UNAUTHENTICATED : new UserAuthentication(getAuthMethod(), userIdentityLogin);
        }
        try {
            if (DeferredAuthentication.isDeferred(httpServletResponse)) {
                return Authentication.UNAUTHENTICATED;
            }
            LOG.debug("SpengoAuthenticator: sending challenge", new Object[0]);
            httpServletResponse.setHeader("WWW-Authenticate", HttpHeaders.NEGOTIATE);
            httpServletResponse.sendError(401);
            return Authentication.SEND_CONTINUE;
        } catch (IOException e2) {
            throw new ServerAuthException(e2);
        }
    }

    public SpnegoAuthenticator(String str) {
        this._authMethod = str;
    }
}
