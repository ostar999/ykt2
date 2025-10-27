package org.apache.http.impl.auth;

import org.apache.http.FormattedHeader;
import org.apache.http.Header;
import org.apache.http.HttpRequest;
import org.apache.http.annotation.NotThreadSafe;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.auth.ContextAwareAuthScheme;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.MalformedChallengeException;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.CharArrayBuffer;

@NotThreadSafe
/* loaded from: classes9.dex */
public abstract class AuthSchemeBase implements ContextAwareAuthScheme {
    private boolean proxy;

    @Override // org.apache.http.auth.ContextAwareAuthScheme
    public Header authenticate(Credentials credentials, HttpRequest httpRequest, HttpContext httpContext) throws AuthenticationException {
        return authenticate(credentials, httpRequest);
    }

    public boolean isProxy() {
        return this.proxy;
    }

    public abstract void parseChallenge(CharArrayBuffer charArrayBuffer, int i2, int i3) throws MalformedChallengeException;

    @Override // org.apache.http.auth.AuthScheme
    public void processChallenge(Header header) throws MalformedChallengeException {
        CharArrayBuffer charArrayBuffer;
        if (header == null) {
            throw new IllegalArgumentException("Header may not be null");
        }
        String name = header.getName();
        int valuePos = 0;
        if (name.equalsIgnoreCase("WWW-Authenticate")) {
            this.proxy = false;
        } else {
            if (!name.equalsIgnoreCase("Proxy-Authenticate")) {
                throw new MalformedChallengeException("Unexpected header name: " + name);
            }
            this.proxy = true;
        }
        if (header instanceof FormattedHeader) {
            FormattedHeader formattedHeader = (FormattedHeader) header;
            charArrayBuffer = formattedHeader.getBuffer();
            valuePos = formattedHeader.getValuePos();
        } else {
            String value = header.getValue();
            if (value == null) {
                throw new MalformedChallengeException("Header value is null");
            }
            charArrayBuffer = new CharArrayBuffer(value.length());
            charArrayBuffer.append(value);
        }
        while (valuePos < charArrayBuffer.length() && HTTP.isWhitespace(charArrayBuffer.charAt(valuePos))) {
            valuePos++;
        }
        int i2 = valuePos;
        while (i2 < charArrayBuffer.length() && !HTTP.isWhitespace(charArrayBuffer.charAt(i2))) {
            i2++;
        }
        String strSubstring = charArrayBuffer.substring(valuePos, i2);
        if (strSubstring.equalsIgnoreCase(getSchemeName())) {
            parseChallenge(charArrayBuffer, i2, charArrayBuffer.length());
            return;
        }
        throw new MalformedChallengeException("Invalid scheme identifier: " + strSubstring);
    }

    public String toString() {
        return getSchemeName();
    }
}
