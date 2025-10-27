package org.apache.http.impl.client;

import java.security.Principal;
import javax.net.ssl.SSLSession;
import org.apache.http.annotation.Immutable;
import org.apache.http.auth.AuthScheme;
import org.apache.http.auth.AuthState;
import org.apache.http.auth.Credentials;
import org.apache.http.client.UserTokenHandler;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.conn.HttpRoutedConnection;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;

@Immutable
/* loaded from: classes9.dex */
public class DefaultUserTokenHandler implements UserTokenHandler {
    private static Principal getAuthPrincipal(AuthState authState) {
        Credentials credentials;
        AuthScheme authScheme = authState.getAuthScheme();
        if (authScheme == null || !authScheme.isComplete() || !authScheme.isConnectionBased() || (credentials = authState.getCredentials()) == null) {
            return null;
        }
        return credentials.getUserPrincipal();
    }

    @Override // org.apache.http.client.UserTokenHandler
    public Object getUserToken(HttpContext httpContext) {
        Principal authPrincipal;
        SSLSession sSLSession;
        AuthState authState = (AuthState) httpContext.getAttribute(ClientContext.TARGET_AUTH_STATE);
        if (authState != null) {
            authPrincipal = getAuthPrincipal(authState);
            if (authPrincipal == null) {
                authPrincipal = getAuthPrincipal((AuthState) httpContext.getAttribute(ClientContext.PROXY_AUTH_STATE));
            }
        } else {
            authPrincipal = null;
        }
        if (authPrincipal != null) {
            return authPrincipal;
        }
        HttpRoutedConnection httpRoutedConnection = (HttpRoutedConnection) httpContext.getAttribute(ExecutionContext.HTTP_CONNECTION);
        return (!httpRoutedConnection.isOpen() || (sSLSession = httpRoutedConnection.getSSLSession()) == null) ? authPrincipal : sSLSession.getLocalPrincipal();
    }
}
