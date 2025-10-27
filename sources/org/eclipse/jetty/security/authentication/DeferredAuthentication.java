package org.eclipse.jetty.security.authentication;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Collections;
import java.util.Locale;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.jetty.security.IdentityService;
import org.eclipse.jetty.security.ServerAuthException;
import org.eclipse.jetty.security.UserAuthentication;
import org.eclipse.jetty.server.Authentication;
import org.eclipse.jetty.server.UserIdentity;
import org.eclipse.jetty.util.IO;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

/* loaded from: classes9.dex */
public class DeferredAuthentication implements Authentication.Deferred {
    private static final Logger LOG = Log.getLogger((Class<?>) DeferredAuthentication.class);
    static final HttpServletResponse __deferredResponse = new HttpServletResponse() { // from class: org.eclipse.jetty.security.authentication.DeferredAuthentication.1
        public void addCookie(Cookie cookie) {
        }

        public void addDateHeader(String str, long j2) {
        }

        public void addHeader(String str, String str2) {
        }

        public void addIntHeader(String str, int i2) {
        }

        public boolean containsHeader(String str) {
            return false;
        }

        public String encodeRedirectURL(String str) {
            return null;
        }

        public String encodeRedirectUrl(String str) {
            return null;
        }

        public String encodeURL(String str) {
            return null;
        }

        public String encodeUrl(String str) {
            return null;
        }

        public void flushBuffer() throws IOException {
        }

        public int getBufferSize() {
            return 1024;
        }

        public String getCharacterEncoding() {
            return null;
        }

        public String getContentType() {
            return null;
        }

        public String getHeader(String str) {
            return null;
        }

        public Collection<String> getHeaderNames() {
            return Collections.emptyList();
        }

        public Collection<String> getHeaders(String str) {
            return Collections.emptyList();
        }

        public Locale getLocale() {
            return null;
        }

        public ServletOutputStream getOutputStream() throws IOException {
            return DeferredAuthentication.__nullOut;
        }

        public int getStatus() {
            return 0;
        }

        public PrintWriter getWriter() throws IOException {
            return IO.getNullPrintWriter();
        }

        public boolean isCommitted() {
            return true;
        }

        public void reset() {
        }

        public void resetBuffer() {
        }

        public void sendError(int i2) throws IOException {
        }

        public void sendError(int i2, String str) throws IOException {
        }

        public void sendRedirect(String str) throws IOException {
        }

        public void setBufferSize(int i2) {
        }

        public void setCharacterEncoding(String str) {
        }

        public void setContentLength(int i2) {
        }

        public void setContentType(String str) {
        }

        public void setDateHeader(String str, long j2) {
        }

        public void setHeader(String str, String str2) {
        }

        public void setIntHeader(String str, int i2) {
        }

        public void setLocale(Locale locale) {
        }

        public void setStatus(int i2) {
        }

        public void setStatus(int i2, String str) {
        }
    };
    private static ServletOutputStream __nullOut = new ServletOutputStream() { // from class: org.eclipse.jetty.security.authentication.DeferredAuthentication.2
        public void print(String str) throws IOException {
        }

        public void println(String str) throws IOException {
        }

        public void write(int i2) throws IOException {
        }
    };
    protected final LoginAuthenticator _authenticator;
    private Object _previousAssociation;

    public DeferredAuthentication(LoginAuthenticator loginAuthenticator) {
        if (loginAuthenticator == null) {
            throw new NullPointerException("No Authenticator");
        }
        this._authenticator = loginAuthenticator;
    }

    public static boolean isDeferred(HttpServletResponse httpServletResponse) {
        return httpServletResponse == __deferredResponse;
    }

    @Override // org.eclipse.jetty.server.Authentication.Deferred
    public Authentication authenticate(ServletRequest servletRequest) {
        try {
            Authentication authenticationValidateRequest = this._authenticator.validateRequest(servletRequest, __deferredResponse, true);
            if (authenticationValidateRequest != null && (authenticationValidateRequest instanceof Authentication.User) && !(authenticationValidateRequest instanceof Authentication.ResponseSent)) {
                IdentityService identityService = this._authenticator.getLoginService().getIdentityService();
                if (identityService != null) {
                    this._previousAssociation = identityService.associate(((Authentication.User) authenticationValidateRequest).getUserIdentity());
                }
                return authenticationValidateRequest;
            }
        } catch (ServerAuthException e2) {
            LOG.debug(e2);
        }
        return this;
    }

    public Object getPreviousAssociation() {
        return this._previousAssociation;
    }

    @Override // org.eclipse.jetty.server.Authentication.Deferred
    public Authentication login(String str, Object obj, ServletRequest servletRequest) {
        UserIdentity userIdentityLogin = this._authenticator.login(str, obj, servletRequest);
        if (userIdentityLogin == null) {
            return null;
        }
        IdentityService identityService = this._authenticator.getLoginService().getIdentityService();
        UserAuthentication userAuthentication = new UserAuthentication("API", userIdentityLogin);
        if (identityService != null) {
            this._previousAssociation = identityService.associate(userIdentityLogin);
        }
        return userAuthentication;
    }

    @Override // org.eclipse.jetty.server.Authentication.Deferred
    public Authentication authenticate(ServletRequest servletRequest, ServletResponse servletResponse) {
        try {
            IdentityService identityService = this._authenticator.getLoginService().getIdentityService();
            Authentication authenticationValidateRequest = this._authenticator.validateRequest(servletRequest, servletResponse, true);
            if ((authenticationValidateRequest instanceof Authentication.User) && identityService != null) {
                this._previousAssociation = identityService.associate(((Authentication.User) authenticationValidateRequest).getUserIdentity());
            }
            return authenticationValidateRequest;
        } catch (ServerAuthException e2) {
            LOG.debug(e2);
            return this;
        }
    }
}
