package org.eclipse.jetty.server;

import javax.servlet.http.Cookie;
import org.eclipse.jetty.util.LazyList;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

/* loaded from: classes9.dex */
public class CookieCutter {
    private static final Logger LOG = Log.getLogger((Class<?>) CookieCutter.class);
    private Cookie[] _cookies;
    int _fields;
    private Cookie[] _lastCookies;
    Object _lazyFields;

    public void addCookieField(String str) {
        if (str == null) {
            return;
        }
        String strTrim = str.trim();
        if (strTrim.length() == 0) {
            return;
        }
        int size = LazyList.size(this._lazyFields);
        int i2 = this._fields;
        if (size > i2) {
            if (!strTrim.equals(LazyList.get(this._lazyFields, i2))) {
                while (true) {
                    int size2 = LazyList.size(this._lazyFields);
                    int i3 = this._fields;
                    if (size2 <= i3) {
                        break;
                    } else {
                        this._lazyFields = LazyList.remove(this._lazyFields, i3);
                    }
                }
            } else {
                this._fields++;
                return;
            }
        }
        this._cookies = null;
        this._lastCookies = null;
        Object obj = this._lazyFields;
        int i4 = this._fields;
        this._fields = i4 + 1;
        this._lazyFields = LazyList.add(obj, i4, strTrim);
    }

    public Cookie[] getCookies() throws NumberFormatException {
        Object obj;
        Cookie[] cookieArr = this._cookies;
        if (cookieArr != null) {
            return cookieArr;
        }
        if (this._lastCookies == null || (obj = this._lazyFields) == null || this._fields != LazyList.size(obj)) {
            parseFields();
        } else {
            this._cookies = this._lastCookies;
        }
        Cookie[] cookieArr2 = this._cookies;
        this._lastCookies = cookieArr2;
        return cookieArr2;
    }

    /* JADX WARN: Removed duplicated region for block: B:103:0x0180  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x00fc A[ADDED_TO_REGION] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void parseFields() throws java.lang.NumberFormatException {
        /*
            Method dump skipped, instructions count: 416
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.server.CookieCutter.parseFields():void");
    }

    public void reset() {
        this._cookies = null;
        this._fields = 0;
    }

    public void setCookies(Cookie[] cookieArr) {
        this._cookies = cookieArr;
        this._lastCookies = null;
        this._lazyFields = null;
        this._fields = 0;
    }
}
