package org.apache.http.client.protocol;

import java.io.IOException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.annotation.Immutable;
import org.apache.http.auth.AuthScheme;
import org.apache.http.auth.AuthState;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.auth.ContextAwareAuthScheme;
import org.apache.http.auth.Credentials;
import org.apache.http.protocol.HttpContext;
import org.eclipse.jetty.http.HttpMethods;

@Immutable
/* loaded from: classes9.dex */
public class RequestTargetAuthentication implements HttpRequestInterceptor {
    private final Log log = LogFactory.getLog(getClass());

    @Override // org.apache.http.HttpRequestInterceptor
    public void process(HttpRequest httpRequest, HttpContext httpContext) throws HttpException, IOException {
        if (httpRequest == null) {
            throw new IllegalArgumentException("HTTP request may not be null");
        }
        if (httpContext == null) {
            throw new IllegalArgumentException("HTTP context may not be null");
        }
        if (httpRequest.getRequestLine().getMethod().equalsIgnoreCase(HttpMethods.CONNECT) || httpRequest.containsHeader("Authorization")) {
            return;
        }
        AuthState authState = (AuthState) httpContext.getAttribute(ClientContext.TARGET_AUTH_STATE);
        if (authState == null) {
            this.log.debug("Target auth state not set in the context");
            return;
        }
        AuthScheme authScheme = authState.getAuthScheme();
        if (authScheme == null) {
            return;
        }
        Credentials credentials = authState.getCredentials();
        if (credentials == null) {
            this.log.debug("User credentials not available");
            return;
        }
        if (authState.getAuthScope() == null && authScheme.isConnectionBased()) {
            return;
        }
        try {
            httpRequest.addHeader(authScheme instanceof ContextAwareAuthScheme ? ((ContextAwareAuthScheme) authScheme).authenticate(credentials, httpRequest, httpContext) : authScheme.authenticate(credentials, httpRequest));
        } catch (AuthenticationException e2) {
            if (this.log.isErrorEnabled()) {
                this.log.error("Authentication error: " + e2.getMessage());
            }
        }
    }
}
