package org.apache.http.impl.cookie;

import java.util.Date;
import org.apache.http.annotation.Immutable;
import org.apache.http.cookie.MalformedCookieException;
import org.apache.http.cookie.SetCookie;

@Immutable
/* loaded from: classes9.dex */
public class BasicMaxAgeHandler extends AbstractCookieAttributeHandler {
    @Override // org.apache.http.cookie.CookieAttributeHandler
    public void parse(SetCookie setCookie, String str) throws MalformedCookieException, NumberFormatException {
        if (setCookie == null) {
            throw new IllegalArgumentException("Cookie may not be null");
        }
        if (str == null) {
            throw new MalformedCookieException("Missing value for max-age attribute");
        }
        try {
            int i2 = Integer.parseInt(str);
            if (i2 >= 0) {
                setCookie.setExpiryDate(new Date(System.currentTimeMillis() + (i2 * 1000)));
                return;
            }
            throw new MalformedCookieException("Negative max-age attribute: " + str);
        } catch (NumberFormatException unused) {
            throw new MalformedCookieException("Invalid max-age attribute: " + str);
        }
    }
}
