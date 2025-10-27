package org.json.alipay;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

/* loaded from: classes9.dex */
public final class c {

    /* renamed from: a, reason: collision with root package name */
    private int f27978a;

    /* renamed from: b, reason: collision with root package name */
    private Reader f27979b;

    /* renamed from: c, reason: collision with root package name */
    private char f27980c;

    /* renamed from: d, reason: collision with root package name */
    private boolean f27981d;

    private c(Reader reader) {
        this.f27979b = reader.markSupported() ? reader : new BufferedReader(reader);
        this.f27981d = false;
        this.f27978a = 0;
    }

    public c(String str) {
        this(new StringReader(str));
    }

    private String a(int i2) throws JSONException, IOException {
        if (i2 == 0) {
            return "";
        }
        char[] cArr = new char[i2];
        int i3 = 0;
        if (this.f27981d) {
            this.f27981d = false;
            cArr[0] = this.f27980c;
            i3 = 1;
        }
        while (i3 < i2) {
            try {
                int i4 = this.f27979b.read(cArr, i3, i2 - i3);
                if (i4 == -1) {
                    break;
                }
                i3 += i4;
            } catch (IOException e2) {
                throw new JSONException(e2);
            }
        }
        this.f27978a += i3;
        if (i3 < i2) {
            throw a("Substring bounds error");
        }
        this.f27980c = cArr[i2 - 1];
        return new String(cArr);
    }

    public final JSONException a(String str) {
        return new JSONException(str + toString());
    }

    public final void a() {
        int i2;
        if (this.f27981d || (i2 = this.f27978a) <= 0) {
            throw new JSONException("Stepping back two steps is not supported");
        }
        this.f27978a = i2 - 1;
        this.f27981d = true;
    }

    public final char b() throws JSONException, IOException {
        if (this.f27981d) {
            this.f27981d = false;
            char c3 = this.f27980c;
            if (c3 != 0) {
                this.f27978a++;
            }
            return c3;
        }
        try {
            int i2 = this.f27979b.read();
            if (i2 <= 0) {
                this.f27980c = (char) 0;
                return (char) 0;
            }
            this.f27978a++;
            char c4 = (char) i2;
            this.f27980c = c4;
            return c4;
        } catch (IOException e2) {
            throw new JSONException(e2);
        }
    }

    public final char c() {
        char cB;
        char cB2;
        char cB3;
        while (true) {
            cB = b();
            if (cB == '/') {
                char cB4 = b();
                if (cB4 == '*') {
                    while (true) {
                        char cB5 = b();
                        if (cB5 == 0) {
                            throw a("Unclosed comment");
                        }
                        if (cB5 == '*') {
                            if (b() != '/') {
                                a();
                            }
                        }
                    }
                } else {
                    if (cB4 != '/') {
                        a();
                        return '/';
                    }
                    do {
                        cB2 = b();
                        if (cB2 == '\n' || cB2 == '\r') {
                            break;
                        }
                    } while (cB2 != 0);
                }
            } else if (cB == '#') {
                do {
                    cB3 = b();
                    if (cB3 == '\n' || cB3 == '\r') {
                        break;
                    }
                } while (cB3 != 0);
            } else if (cB == 0 || cB > ' ') {
                break;
            }
        }
        return cB;
    }

    /* JADX WARN: Code restructure failed: missing block: B:103:0x0146, code lost:
    
        throw a("Unterminated string");
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object d() {
        /*
            Method dump skipped, instructions count: 327
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.json.alipay.c.d():java.lang.Object");
    }

    public final String toString() {
        return " at character " + this.f27978a;
    }
}
