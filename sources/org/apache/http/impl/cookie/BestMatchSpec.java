package org.apache.http.impl.cookie;

import java.util.List;
import org.apache.http.FormattedHeader;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.ParseException;
import org.apache.http.annotation.NotThreadSafe;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.cookie.ClientCookie;
import org.apache.http.cookie.Cookie;
import org.apache.http.cookie.CookieOrigin;
import org.apache.http.cookie.CookieSpec;
import org.apache.http.cookie.MalformedCookieException;
import org.apache.http.cookie.SetCookie2;
import org.apache.http.message.ParserCursor;
import org.apache.http.util.CharArrayBuffer;

@NotThreadSafe
/* loaded from: classes9.dex */
public class BestMatchSpec implements CookieSpec {
    private BrowserCompatSpec compat;
    private final String[] datepatterns;
    private RFC2109Spec obsoleteStrict;
    private final boolean oneHeader;
    private RFC2965Spec strict;

    public BestMatchSpec(String[] strArr, boolean z2) {
        this.datepatterns = strArr == null ? null : (String[]) strArr.clone();
        this.oneHeader = z2;
    }

    private BrowserCompatSpec getCompat() {
        if (this.compat == null) {
            this.compat = new BrowserCompatSpec(this.datepatterns);
        }
        return this.compat;
    }

    private RFC2109Spec getObsoleteStrict() {
        if (this.obsoleteStrict == null) {
            this.obsoleteStrict = new RFC2109Spec(this.datepatterns, this.oneHeader);
        }
        return this.obsoleteStrict;
    }

    private RFC2965Spec getStrict() {
        if (this.strict == null) {
            this.strict = new RFC2965Spec(this.datepatterns, this.oneHeader);
        }
        return this.strict;
    }

    @Override // org.apache.http.cookie.CookieSpec
    public List<Header> formatCookies(List<Cookie> list) {
        if (list == null) {
            throw new IllegalArgumentException("List of cookies may not be null");
        }
        int version = Integer.MAX_VALUE;
        boolean z2 = true;
        for (Cookie cookie : list) {
            if (!(cookie instanceof SetCookie2)) {
                z2 = false;
            }
            if (cookie.getVersion() < version) {
                version = cookie.getVersion();
            }
        }
        return version > 0 ? z2 ? getStrict().formatCookies(list) : getObsoleteStrict().formatCookies(list) : getCompat().formatCookies(list);
    }

    @Override // org.apache.http.cookie.CookieSpec
    public int getVersion() {
        return getStrict().getVersion();
    }

    @Override // org.apache.http.cookie.CookieSpec
    public Header getVersionHeader() {
        return getStrict().getVersionHeader();
    }

    @Override // org.apache.http.cookie.CookieSpec
    public boolean match(Cookie cookie, CookieOrigin cookieOrigin) {
        if (cookie == null) {
            throw new IllegalArgumentException("Cookie may not be null");
        }
        if (cookieOrigin != null) {
            return cookie.getVersion() > 0 ? cookie instanceof SetCookie2 ? getStrict().match(cookie, cookieOrigin) : getObsoleteStrict().match(cookie, cookieOrigin) : getCompat().match(cookie, cookieOrigin);
        }
        throw new IllegalArgumentException("Cookie origin may not be null");
    }

    @Override // org.apache.http.cookie.CookieSpec
    public List<Cookie> parse(Header header, CookieOrigin cookieOrigin) throws ParseException, MalformedCookieException {
        CharArrayBuffer charArrayBuffer;
        ParserCursor parserCursor;
        if (header == null) {
            throw new IllegalArgumentException("Header may not be null");
        }
        if (cookieOrigin == null) {
            throw new IllegalArgumentException("Cookie origin may not be null");
        }
        HeaderElement[] elements = header.getElements();
        boolean z2 = false;
        boolean z3 = false;
        for (HeaderElement headerElement : elements) {
            if (headerElement.getParameterByName("version") != null) {
                z3 = true;
            }
            if (headerElement.getParameterByName(ClientCookie.EXPIRES_ATTR) != null) {
                z2 = true;
            }
        }
        if (!z2 && z3) {
            return "Set-Cookie2".equals(header.getName()) ? getStrict().parse(elements, cookieOrigin) : getObsoleteStrict().parse(elements, cookieOrigin);
        }
        NetscapeDraftHeaderParser netscapeDraftHeaderParser = NetscapeDraftHeaderParser.DEFAULT;
        if (header instanceof FormattedHeader) {
            FormattedHeader formattedHeader = (FormattedHeader) header;
            charArrayBuffer = formattedHeader.getBuffer();
            parserCursor = new ParserCursor(formattedHeader.getValuePos(), charArrayBuffer.length());
        } else {
            String value = header.getValue();
            if (value == null) {
                throw new MalformedCookieException("Header value is null");
            }
            charArrayBuffer = new CharArrayBuffer(value.length());
            charArrayBuffer.append(value);
            parserCursor = new ParserCursor(0, charArrayBuffer.length());
        }
        return getCompat().parse(new HeaderElement[]{netscapeDraftHeaderParser.parseHeader(charArrayBuffer, parserCursor)}, cookieOrigin);
    }

    public String toString() {
        return CookiePolicy.BEST_MATCH;
    }

    @Override // org.apache.http.cookie.CookieSpec
    public void validate(Cookie cookie, CookieOrigin cookieOrigin) throws MalformedCookieException {
        if (cookie == null) {
            throw new IllegalArgumentException("Cookie may not be null");
        }
        if (cookieOrigin == null) {
            throw new IllegalArgumentException("Cookie origin may not be null");
        }
        if (cookie.getVersion() <= 0) {
            getCompat().validate(cookie, cookieOrigin);
        } else if (cookie instanceof SetCookie2) {
            getStrict().validate(cookie, cookieOrigin);
        } else {
            getObsoleteStrict().validate(cookie, cookieOrigin);
        }
    }

    public BestMatchSpec() {
        this(null, false);
    }
}
