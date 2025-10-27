package org.eclipse.jetty.http;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import org.eclipse.jetty.util.MultiMap;
import org.eclipse.jetty.util.StringUtil;
import org.eclipse.jetty.util.TypeUtil;
import org.eclipse.jetty.util.URIUtil;
import org.eclipse.jetty.util.UrlEncoded;
import org.eclipse.jetty.util.Utf8StringBuilder;

/* loaded from: classes9.dex */
public class HttpURI {
    private static final int ASTERISK = 10;
    private static final int AUTH = 4;
    private static final int AUTH_OR_PATH = 1;
    private static final int IPV6 = 5;
    private static final int PARAM = 8;
    private static final int PATH = 7;
    private static final int PORT = 6;
    private static final int QUERY = 9;
    private static final int SCHEME_OR_PATH = 2;
    private static final int START = 0;
    private static final byte[] __empty = new byte[0];
    int _authority;
    boolean _encoded;
    int _end;
    int _fragment;
    int _host;
    int _param;
    boolean _partial;
    int _path;
    int _port;
    int _portValue;
    int _query;
    byte[] _raw;
    String _rawString;
    int _scheme;
    final Utf8StringBuilder _utf8b;

    public HttpURI() {
        this._partial = false;
        this._raw = __empty;
        this._encoded = false;
        this._utf8b = new Utf8StringBuilder(64);
    }

    private void parse2(byte[] bArr, int i2, int i3) {
        char c3 = 0;
        this._encoded = false;
        this._raw = bArr;
        int i4 = i2 + i3;
        this._end = i4;
        this._scheme = i2;
        this._authority = i2;
        this._host = i2;
        this._port = i2;
        this._portValue = -1;
        this._path = i2;
        this._param = i4;
        this._query = i4;
        this._fragment = i4;
        int i5 = i2;
        int i6 = i5;
        while (i5 < i4) {
            byte[] bArr2 = this._raw;
            char c4 = (char) (bArr2[i5] & 255);
            int i7 = i5 + 1;
            switch (c3) {
                case 0:
                    if (c4 == '#') {
                        this._param = i5;
                        this._query = i5;
                        this._fragment = i5;
                    } else if (c4 == '*') {
                        this._path = i5;
                        i6 = i5;
                        i5 = i7;
                        c3 = '\n';
                    } else if (c4 == '/') {
                        i6 = i5;
                        i5 = i7;
                        c3 = 1;
                    } else if (c4 == ';') {
                        this._param = i5;
                        i6 = i5;
                        i5 = i7;
                        c3 = '\b';
                    } else if (c4 != '?') {
                        c3 = 2;
                    } else {
                        this._param = i5;
                        this._query = i5;
                        i6 = i5;
                        i5 = i7;
                        c3 = '\t';
                    }
                    i6 = i5;
                    i5 = i7;
                case 1:
                    if ((this._partial || this._scheme != this._authority) && c4 == '/') {
                        this._host = i7;
                        int i8 = this._end;
                        this._port = i8;
                        this._path = i8;
                        i5 = i7;
                        c3 = 4;
                    } else if (c4 == ';' || c4 == '?' || c4 == '#') {
                        i5 = i7 - 1;
                        c3 = 7;
                    } else {
                        this._host = i6;
                        this._port = i6;
                        i5 = i7;
                        c3 = 7;
                    }
                    break;
                case 2:
                    if (i3 > 6 && c4 == 't') {
                        int i9 = i2 + 3;
                        if (bArr2[i9] == 58) {
                            i7 = i2 + 4;
                        } else {
                            i9 = i2 + 4;
                            if (bArr2[i9] == 58) {
                                i7 = i2 + 5;
                            } else {
                                i9 = i2 + 5;
                                if (bArr2[i9] == 58) {
                                    i7 = i2 + 6;
                                }
                            }
                        }
                        i5 = i9;
                        c4 = ':';
                    }
                    if (c4 == '#') {
                        this._param = i5;
                        this._query = i5;
                        this._fragment = i5;
                    } else if (c4 == '/') {
                        i5 = i7;
                        c3 = 7;
                    } else if (c4 == '?') {
                        this._param = i5;
                        this._query = i5;
                        i5 = i7;
                        c3 = '\t';
                    } else if (c4 == ':') {
                        int i10 = i7 + 1;
                        this._authority = i7;
                        this._path = i7;
                        if (((char) (bArr2[i10] & 255)) == '/') {
                            i5 = i10;
                            i6 = i7;
                            c3 = 1;
                        } else {
                            this._host = i7;
                            this._port = i7;
                            i5 = i10;
                            i6 = i7;
                            c3 = 7;
                        }
                    } else if (c4 == ';') {
                        this._param = i5;
                        i5 = i7;
                        c3 = '\b';
                    }
                    i5 = i7;
                    break;
                case 3:
                default:
                    i5 = i7;
                case 4:
                    if (c4 == '/') {
                        this._path = i5;
                        this._port = i5;
                        i6 = i5;
                        c3 = 7;
                    } else if (c4 == ':') {
                        this._port = i5;
                        c3 = 6;
                    } else if (c4 == '@') {
                        this._host = i7;
                    } else if (c4 == '[') {
                        c3 = 5;
                    }
                    i5 = i7;
                case 5:
                    if (c4 == '/') {
                        throw new IllegalArgumentException("No closing ']' for " + StringUtil.toString(this._raw, i2, i3, URIUtil.__CHARSET));
                    }
                    if (c4 == ']') {
                        c3 = 4;
                    }
                    i5 = i7;
                case 6:
                    if (c4 == '/') {
                        this._path = i5;
                        if (this._port <= this._authority) {
                            this._port = i5;
                        }
                        i6 = i5;
                        i5 = i7;
                        c3 = 7;
                    } else {
                        i5 = i7;
                    }
                case 7:
                    if (c4 == '#') {
                        this._param = i5;
                        this._query = i5;
                        this._fragment = i5;
                    } else if (c4 == '%') {
                        this._encoded = true;
                    } else if (c4 == ';') {
                        this._param = i5;
                        c3 = '\b';
                    } else if (c4 == '?') {
                        this._param = i5;
                        this._query = i5;
                        c3 = '\t';
                    }
                    i5 = i7;
                case '\b':
                    if (c4 == '#') {
                        this._query = i5;
                        this._fragment = i5;
                    } else if (c4 == '?') {
                        this._query = i5;
                        c3 = '\t';
                    }
                    i5 = i7;
                case '\t':
                    if (c4 == '#') {
                        this._fragment = i5;
                    }
                    i5 = i7;
                case '\n':
                    throw new IllegalArgumentException("only '*'");
            }
        }
        int i11 = this._port;
        int i12 = this._path;
        if (i11 < i12) {
            this._portValue = TypeUtil.parseInt(this._raw, i11 + 1, (i12 - i11) - 1, 10);
        }
    }

    private String toUtf8String(int i2, int i3) {
        this._utf8b.reset();
        this._utf8b.append(this._raw, i2, i3);
        return this._utf8b.toString();
    }

    public void clear() {
        this._end = 0;
        this._fragment = 0;
        this._query = 0;
        this._param = 0;
        this._path = 0;
        this._port = 0;
        this._host = 0;
        this._authority = 0;
        this._scheme = 0;
        this._raw = __empty;
        this._rawString = "";
        this._encoded = false;
    }

    public void decodeQueryTo(MultiMap multiMap) {
        if (this._query == this._fragment) {
            return;
        }
        this._utf8b.reset();
        UrlEncoded.decodeUtf8To(this._raw, this._query + 1, (this._fragment - r1) - 1, multiMap, this._utf8b);
    }

    public String getAuthority() {
        int i2 = this._authority;
        int i3 = this._path;
        if (i2 == i3) {
            return null;
        }
        return toUtf8String(i2, i3 - i2);
    }

    public String getCompletePath() {
        int i2 = this._path;
        int i3 = this._end;
        if (i2 == i3) {
            return null;
        }
        return toUtf8String(i2, i3 - i2);
    }

    public String getDecodedPath() {
        int i2 = this._path;
        int i3 = this._param;
        if (i2 == i3) {
            return null;
        }
        int i4 = i3 - i2;
        boolean z2 = false;
        while (i2 < this._param) {
            byte b3 = this._raw[i2];
            if (b3 == 37) {
                if (!z2) {
                    this._utf8b.reset();
                    Utf8StringBuilder utf8StringBuilder = this._utf8b;
                    byte[] bArr = this._raw;
                    int i5 = this._path;
                    utf8StringBuilder.append(bArr, i5, i2 - i5);
                    z2 = true;
                }
                int i6 = i2 + 2;
                int i7 = this._param;
                if (i6 >= i7) {
                    throw new IllegalArgumentException("Bad % encoding: " + this);
                }
                byte[] bArr2 = this._raw;
                int i8 = i2 + 1;
                if (bArr2[i8] == 117) {
                    i2 += 5;
                    if (i2 >= i7) {
                        throw new IllegalArgumentException("Bad %u encoding: " + this);
                    }
                    try {
                        this._utf8b.getStringBuilder().append(new String(Character.toChars(TypeUtil.parseInt(bArr2, i6, 4, 16))));
                    } catch (Exception e2) {
                        throw new RuntimeException(e2);
                    }
                } else {
                    this._utf8b.append((byte) (TypeUtil.parseInt(bArr2, i8, 2, 16) & 255));
                    i2 = i6;
                }
            } else if (z2) {
                this._utf8b.append(b3);
            }
            i2++;
        }
        return !z2 ? toUtf8String(this._path, i4) : this._utf8b.toString();
    }

    public String getFragment() {
        int i2 = this._fragment;
        if (i2 == this._end) {
            return null;
        }
        return toUtf8String(i2 + 1, (r1 - i2) - 1);
    }

    public String getHost() {
        int i2 = this._host;
        int i3 = this._port;
        if (i2 == i3) {
            return null;
        }
        return toUtf8String(i2, i3 - i2);
    }

    public String getParam() {
        int i2 = this._param;
        if (i2 == this._query) {
            return null;
        }
        return toUtf8String(i2 + 1, (r1 - i2) - 1);
    }

    public String getPath() {
        int i2 = this._path;
        int i3 = this._param;
        if (i2 == i3) {
            return null;
        }
        return toUtf8String(i2, i3 - i2);
    }

    public String getPathAndParam() {
        int i2 = this._path;
        int i3 = this._query;
        if (i2 == i3) {
            return null;
        }
        return toUtf8String(i2, i3 - i2);
    }

    public int getPort() {
        return this._portValue;
    }

    public String getQuery() {
        int i2 = this._query;
        if (i2 == this._fragment) {
            return null;
        }
        return toUtf8String(i2 + 1, (r1 - i2) - 1);
    }

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
        return toUtf8String(i2, (i3 - i2) - 1);
    }

    public boolean hasQuery() {
        return this._fragment > this._query;
    }

    public void parse(String str) {
        byte[] bytes = str.getBytes();
        parse2(bytes, 0, bytes.length);
        this._rawString = str;
    }

    public void parseConnect(byte[] bArr, int i2, int i3) {
        this._rawString = null;
        this._encoded = false;
        this._raw = bArr;
        int i4 = i2 + i3;
        this._end = i4;
        this._scheme = i2;
        this._authority = i2;
        this._host = i2;
        this._port = i4;
        this._portValue = -1;
        this._path = i4;
        this._param = i4;
        this._query = i4;
        this._fragment = i4;
        int i5 = i2;
        char c3 = 4;
        while (true) {
            if (i5 >= i4) {
                break;
            }
            char c4 = (char) (this._raw[i5] & 255);
            int i6 = i5 + 1;
            if (c3 == 4) {
                if (c4 == ':') {
                    this._port = i5;
                    break;
                } else if (c4 == '[') {
                    c3 = 5;
                }
            } else if (c3 != 5) {
                continue;
            } else {
                if (c4 == '/') {
                    throw new IllegalArgumentException("No closing ']' for " + StringUtil.toString(this._raw, i2, i3, URIUtil.__CHARSET));
                }
                if (c4 == ']') {
                    c3 = 4;
                }
            }
            i5 = i6;
        }
        int i7 = this._port;
        if (i7 >= this._path) {
            throw new IllegalArgumentException("No port");
        }
        this._portValue = TypeUtil.parseInt(this._raw, i7 + 1, (r10 - i7) - 1, 10);
        this._path = i2;
    }

    public String toString() {
        if (this._rawString == null) {
            int i2 = this._scheme;
            this._rawString = toUtf8String(i2, this._end - i2);
        }
        return this._rawString;
    }

    public void writeTo(Utf8StringBuilder utf8StringBuilder) {
        byte[] bArr = this._raw;
        int i2 = this._scheme;
        utf8StringBuilder.append(bArr, i2, this._end - i2);
    }

    public String getQuery(String str) {
        int i2 = this._query;
        if (i2 == this._fragment) {
            return null;
        }
        return StringUtil.toString(this._raw, i2 + 1, (r1 - i2) - 1, str);
    }

    public void decodeQueryTo(MultiMap multiMap, String str) throws UnsupportedEncodingException {
        if (this._query == this._fragment) {
            return;
        }
        if (str != null && !StringUtil.isUTF8(str)) {
            UrlEncoded.decodeTo(StringUtil.toString(this._raw, this._query + 1, (this._fragment - r1) - 1, str), multiMap, str);
        } else {
            UrlEncoded.decodeUtf8To(this._raw, this._query + 1, (this._fragment - r0) - 1, multiMap);
        }
    }

    public void parse(byte[] bArr, int i2, int i3) {
        this._rawString = null;
        parse2(bArr, i2, i3);
    }

    public HttpURI(boolean z2) {
        this._partial = false;
        this._raw = __empty;
        this._encoded = false;
        this._utf8b = new Utf8StringBuilder(64);
        this._partial = z2;
    }

    public HttpURI(String str) throws UnsupportedEncodingException {
        this._partial = false;
        this._raw = __empty;
        this._encoded = false;
        this._utf8b = new Utf8StringBuilder(64);
        this._rawString = str;
        try {
            byte[] bytes = str.getBytes("UTF-8");
            parse(bytes, 0, bytes.length);
        } catch (UnsupportedEncodingException e2) {
            throw new RuntimeException(e2.getMessage());
        }
    }

    public String getDecodedPath(String str) throws UnsupportedEncodingException {
        int i2 = this._path;
        int i3 = this._param;
        byte[] bArr = null;
        if (i2 == i3) {
            return null;
        }
        int i4 = i3 - i2;
        int length = 0;
        while (true) {
            int i5 = this._param;
            if (i2 >= i5) {
                if (bArr == null) {
                    byte[] bArr2 = this._raw;
                    int i6 = this._path;
                    return StringUtil.toString(bArr2, i6, i5 - i6, str);
                }
                return StringUtil.toString(bArr, 0, length, str);
            }
            byte[] bArr3 = this._raw;
            byte b3 = bArr3[i2];
            if (b3 == 37) {
                if (bArr == null) {
                    bArr = new byte[i4];
                    System.arraycopy(bArr3, this._path, bArr, 0, length);
                }
                int i7 = i2 + 2;
                int i8 = this._param;
                if (i7 < i8) {
                    byte[] bArr4 = this._raw;
                    int i9 = i2 + 1;
                    if (bArr4[i9] == 117) {
                        i2 += 5;
                        if (i2 < i8) {
                            try {
                                byte[] bytes = new String(Character.toChars(TypeUtil.parseInt(bArr4, i7, 4, 16))).getBytes(str);
                                System.arraycopy(bytes, 0, bArr, length, bytes.length);
                                length += bytes.length;
                            } catch (Exception e2) {
                                throw new RuntimeException(e2);
                            }
                        } else {
                            throw new IllegalArgumentException("Bad %u encoding: " + this);
                        }
                    } else {
                        bArr[length] = (byte) (TypeUtil.parseInt(bArr4, i9, 2, 16) & 255);
                        i2 = i7;
                        length++;
                    }
                } else {
                    throw new IllegalArgumentException("Bad % encoding: " + this);
                }
            } else if (bArr == null) {
                length++;
            } else {
                bArr[length] = b3;
                length++;
            }
            i2++;
        }
    }

    public HttpURI(byte[] bArr, int i2, int i3) {
        this._partial = false;
        this._raw = __empty;
        this._encoded = false;
        this._utf8b = new Utf8StringBuilder(64);
        parse2(bArr, i2, i3);
    }

    public HttpURI(URI uri) {
        this._partial = false;
        this._raw = __empty;
        this._encoded = false;
        this._utf8b = new Utf8StringBuilder(64);
        parse(uri.toASCIIString());
    }
}
