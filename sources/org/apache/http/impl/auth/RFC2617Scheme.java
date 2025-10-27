package org.apache.http.impl.auth;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.apache.http.HeaderElement;
import org.apache.http.ParseException;
import org.apache.http.annotation.NotThreadSafe;
import org.apache.http.auth.MalformedChallengeException;
import org.apache.http.message.BasicHeaderValueParser;
import org.apache.http.message.ParserCursor;
import org.apache.http.util.CharArrayBuffer;

@NotThreadSafe
/* loaded from: classes9.dex */
public abstract class RFC2617Scheme extends AuthSchemeBase {
    private Map<String, String> params;

    @Override // org.apache.http.auth.AuthScheme
    public String getParameter(String str) {
        if (str == null) {
            throw new IllegalArgumentException("Parameter name may not be null");
        }
        Map<String, String> map = this.params;
        if (map == null) {
            return null;
        }
        return map.get(str.toLowerCase(Locale.ENGLISH));
    }

    public Map<String, String> getParameters() {
        if (this.params == null) {
            this.params = new HashMap();
        }
        return this.params;
    }

    @Override // org.apache.http.auth.AuthScheme
    public String getRealm() {
        return getParameter("realm");
    }

    @Override // org.apache.http.impl.auth.AuthSchemeBase
    public void parseChallenge(CharArrayBuffer charArrayBuffer, int i2, int i3) throws ParseException, MalformedChallengeException {
        HeaderElement[] elements = BasicHeaderValueParser.DEFAULT.parseElements(charArrayBuffer, new ParserCursor(i2, charArrayBuffer.length()));
        if (elements.length == 0) {
            throw new MalformedChallengeException("Authentication challenge is empty");
        }
        this.params = new HashMap(elements.length);
        for (HeaderElement headerElement : elements) {
            this.params.put(headerElement.getName(), headerElement.getValue());
        }
    }
}
