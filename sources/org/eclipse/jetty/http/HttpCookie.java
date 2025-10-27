package org.eclipse.jetty.http;

/* loaded from: classes9.dex */
public class HttpCookie {
    private final String _comment;
    private final String _domain;
    private final boolean _httpOnly;
    private final int _maxAge;
    private final String _name;
    private final String _path;
    private final boolean _secure;
    private final String _value;
    private final int _version;

    public HttpCookie(String str, String str2) {
        this._name = str;
        this._value = str2;
        this._comment = null;
        this._domain = null;
        this._httpOnly = false;
        this._maxAge = -1;
        this._path = null;
        this._secure = false;
        this._version = 0;
    }

    public String getComment() {
        return this._comment;
    }

    public String getDomain() {
        return this._domain;
    }

    public int getMaxAge() {
        return this._maxAge;
    }

    public String getName() {
        return this._name;
    }

    public String getPath() {
        return this._path;
    }

    public String getValue() {
        return this._value;
    }

    public int getVersion() {
        return this._version;
    }

    public boolean isHttpOnly() {
        return this._httpOnly;
    }

    public boolean isSecure() {
        return this._secure;
    }

    public HttpCookie(String str, String str2, String str3, String str4) {
        this._name = str;
        this._value = str2;
        this._comment = null;
        this._domain = str3;
        this._httpOnly = false;
        this._maxAge = -1;
        this._path = str4;
        this._secure = false;
        this._version = 0;
    }

    public HttpCookie(String str, String str2, int i2) {
        this._name = str;
        this._value = str2;
        this._comment = null;
        this._domain = null;
        this._httpOnly = false;
        this._maxAge = i2;
        this._path = null;
        this._secure = false;
        this._version = 0;
    }

    public HttpCookie(String str, String str2, String str3, String str4, int i2, boolean z2, boolean z3) {
        this._comment = null;
        this._domain = str3;
        this._httpOnly = z2;
        this._maxAge = i2;
        this._name = str;
        this._path = str4;
        this._secure = z3;
        this._value = str2;
        this._version = 0;
    }

    public HttpCookie(String str, String str2, String str3, String str4, int i2, boolean z2, boolean z3, String str5, int i3) {
        this._comment = str5;
        this._domain = str3;
        this._httpOnly = z2;
        this._maxAge = i2;
        this._name = str;
        this._path = str4;
        this._secure = z3;
        this._value = str2;
        this._version = i3;
    }
}
