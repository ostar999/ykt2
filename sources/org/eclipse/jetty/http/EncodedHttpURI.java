package org.eclipse.jetty.http;

import java.io.UnsupportedEncodingException;
import org.eclipse.jetty.util.MultiMap;
import org.eclipse.jetty.util.StringUtil;
import org.eclipse.jetty.util.TypeUtil;
import org.eclipse.jetty.util.URIUtil;
import org.eclipse.jetty.util.UrlEncoded;
import org.eclipse.jetty.util.Utf8StringBuffer;

/* loaded from: classes9.dex */
public class EncodedHttpURI extends HttpURI {
    private final String _encoding;

    public EncodedHttpURI(String str) {
        this._encoding = str;
    }

    @Override // org.eclipse.jetty.http.HttpURI
    public void decodeQueryTo(MultiMap multiMap) {
        int i2 = this._query;
        if (i2 == this._fragment) {
            return;
        }
        UrlEncoded.decodeTo(StringUtil.toString(this._raw, i2 + 1, (r1 - i2) - 1, this._encoding), multiMap, this._encoding);
    }

    @Override // org.eclipse.jetty.http.HttpURI
    public String getAuthority() {
        int i2 = this._authority;
        int i3 = this._path;
        if (i2 == i3) {
            return null;
        }
        return StringUtil.toString(this._raw, i2, i3 - i2, this._encoding);
    }

    @Override // org.eclipse.jetty.http.HttpURI
    public String getCompletePath() {
        int i2 = this._path;
        int i3 = this._end;
        if (i2 == i3) {
            return null;
        }
        return StringUtil.toString(this._raw, i2, i3 - i2, this._encoding);
    }

    @Override // org.eclipse.jetty.http.HttpURI
    public String getDecodedPath() {
        int i2 = this._path;
        int i3 = this._param;
        if (i2 == i3) {
            return null;
        }
        return URIUtil.decodePath(this._raw, i2, i3 - i2);
    }

    @Override // org.eclipse.jetty.http.HttpURI
    public String getFragment() {
        int i2 = this._fragment;
        if (i2 == this._end) {
            return null;
        }
        return StringUtil.toString(this._raw, i2 + 1, (r1 - i2) - 1, this._encoding);
    }

    @Override // org.eclipse.jetty.http.HttpURI
    public String getHost() {
        int i2 = this._host;
        int i3 = this._port;
        if (i2 == i3) {
            return null;
        }
        return StringUtil.toString(this._raw, i2, i3 - i2, this._encoding);
    }

    @Override // org.eclipse.jetty.http.HttpURI
    public String getParam() {
        int i2 = this._param;
        if (i2 == this._query) {
            return null;
        }
        return StringUtil.toString(this._raw, i2 + 1, (r1 - i2) - 1, this._encoding);
    }

    @Override // org.eclipse.jetty.http.HttpURI
    public String getPath() {
        int i2 = this._path;
        int i3 = this._param;
        if (i2 == i3) {
            return null;
        }
        return StringUtil.toString(this._raw, i2, i3 - i2, this._encoding);
    }

    @Override // org.eclipse.jetty.http.HttpURI
    public String getPathAndParam() {
        int i2 = this._path;
        int i3 = this._query;
        if (i2 == i3) {
            return null;
        }
        return StringUtil.toString(this._raw, i2, i3 - i2, this._encoding);
    }

    @Override // org.eclipse.jetty.http.HttpURI
    public int getPort() {
        int i2 = this._port;
        if (i2 == this._path) {
            return -1;
        }
        return TypeUtil.parseInt(this._raw, i2 + 1, (r1 - i2) - 1, 10);
    }

    @Override // org.eclipse.jetty.http.HttpURI
    public String getQuery() {
        int i2 = this._query;
        if (i2 == this._fragment) {
            return null;
        }
        return StringUtil.toString(this._raw, i2 + 1, (r1 - i2) - 1, this._encoding);
    }

    @Override // org.eclipse.jetty.http.HttpURI
    public String getScheme() {
        int i2 = this._scheme;
        int i3 = this._authority;
        if (i2 == i3) {
            return null;
        }
        int i4 = i3 - i2;
        if (i4 == 5) {
            byte[] bArr = this._raw;
            if (bArr[i2] == 104 && bArr[i2 + 1] == 116 && bArr[i2 + 2] == 116 && bArr[i2 + 3] == 112) {
                return "http";
            }
        }
        if (i4 == 6) {
            byte[] bArr2 = this._raw;
            if (bArr2[i2] == 104 && bArr2[i2 + 1] == 116 && bArr2[i2 + 2] == 116 && bArr2[i2 + 3] == 112 && bArr2[i2 + 4] == 115) {
                return "https";
            }
        }
        return StringUtil.toString(this._raw, i2, (i3 - i2) - 1, this._encoding);
    }

    @Override // org.eclipse.jetty.http.HttpURI
    public boolean hasQuery() {
        return this._fragment > this._query;
    }

    @Override // org.eclipse.jetty.http.HttpURI
    public String toString() {
        if (this._rawString == null) {
            byte[] bArr = this._raw;
            int i2 = this._scheme;
            this._rawString = StringUtil.toString(bArr, i2, this._end - i2, this._encoding);
        }
        return this._rawString;
    }

    public void writeTo(Utf8StringBuffer utf8StringBuffer) {
        utf8StringBuffer.getStringBuffer().append(toString());
    }

    @Override // org.eclipse.jetty.http.HttpURI
    public void decodeQueryTo(MultiMap multiMap, String str) throws UnsupportedEncodingException {
        int i2 = this._query;
        if (i2 == this._fragment) {
            return;
        }
        if (str == null) {
            str = this._encoding;
        }
        UrlEncoded.decodeTo(StringUtil.toString(this._raw, i2 + 1, (r1 - i2) - 1, str), multiMap, str);
    }
}
