package com.huawei.secure.android.common.ssl.hostname;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.security.auth.x500.X500Principal;

/* loaded from: classes4.dex */
public class a {

    /* renamed from: a, reason: collision with root package name */
    private final String f8381a;

    /* renamed from: b, reason: collision with root package name */
    private final int f8382b;

    /* renamed from: c, reason: collision with root package name */
    private int f8383c;

    /* renamed from: d, reason: collision with root package name */
    private int f8384d;

    /* renamed from: e, reason: collision with root package name */
    private int f8385e;

    /* renamed from: f, reason: collision with root package name */
    private int f8386f;

    /* renamed from: g, reason: collision with root package name */
    private char[] f8387g;

    public a(X500Principal x500Principal) {
        String name = x500Principal.getName("RFC2253");
        this.f8381a = name;
        this.f8382b = name.length();
    }

    /* JADX WARN: Code restructure failed: missing block: B:16:0x0051, code lost:
    
        r2 = r8.f8384d;
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x005b, code lost:
    
        return new java.lang.String(r1, r2, r8.f8385e - r2);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private java.lang.String a() {
        /*
            r8 = this;
            int r0 = r8.f8383c
            r8.f8384d = r0
            r8.f8385e = r0
        L6:
            int r0 = r8.f8383c
            int r1 = r8.f8382b
            if (r0 < r1) goto L19
            java.lang.String r0 = new java.lang.String
            char[] r1 = r8.f8387g
            int r2 = r8.f8384d
            int r3 = r8.f8385e
            int r3 = r3 - r2
            r0.<init>(r1, r2, r3)
            return r0
        L19:
            char[] r1 = r8.f8387g
            char r2 = r1[r0]
            r3 = 44
            r4 = 43
            r5 = 59
            r6 = 32
            if (r2 == r6) goto L5c
            if (r2 == r5) goto L51
            r5 = 92
            if (r2 == r5) goto L3e
            if (r2 == r4) goto L51
            if (r2 == r3) goto L51
            int r3 = r8.f8385e
            int r4 = r3 + 1
            r8.f8385e = r4
            r1[r3] = r2
            int r0 = r0 + 1
            r8.f8383c = r0
            goto L6
        L3e:
            int r0 = r8.f8385e
            int r2 = r0 + 1
            r8.f8385e = r2
            char r2 = r8.b()
            r1[r0] = r2
            int r0 = r8.f8383c
            int r0 = r0 + 1
            r8.f8383c = r0
            goto L6
        L51:
            java.lang.String r0 = new java.lang.String
            int r2 = r8.f8384d
            int r3 = r8.f8385e
            int r3 = r3 - r2
            r0.<init>(r1, r2, r3)
            return r0
        L5c:
            int r2 = r8.f8385e
            r8.f8386f = r2
            int r0 = r0 + 1
            r8.f8383c = r0
            int r0 = r2 + 1
            r8.f8385e = r0
            r1[r2] = r6
        L6a:
            int r0 = r8.f8383c
            int r1 = r8.f8382b
            if (r0 >= r1) goto L83
            char[] r2 = r8.f8387g
            char r7 = r2[r0]
            if (r7 != r6) goto L83
            int r1 = r8.f8385e
            int r7 = r1 + 1
            r8.f8385e = r7
            r2[r1] = r6
            int r0 = r0 + 1
            r8.f8383c = r0
            goto L6a
        L83:
            if (r0 == r1) goto L8f
            char[] r1 = r8.f8387g
            char r0 = r1[r0]
            if (r0 == r3) goto L8f
            if (r0 == r4) goto L8f
            if (r0 != r5) goto L6
        L8f:
            java.lang.String r0 = new java.lang.String
            char[] r1 = r8.f8387g
            int r2 = r8.f8384d
            int r3 = r8.f8386f
            int r3 = r3 - r2
            r0.<init>(r1, r2, r3)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huawei.secure.android.common.ssl.hostname.a.a():java.lang.String");
    }

    private char b() {
        int i2 = this.f8383c + 1;
        this.f8383c = i2;
        if (i2 == this.f8382b) {
            throw new IllegalStateException("Unexpected end of DN: " + this.f8381a);
        }
        char c3 = this.f8387g[i2];
        if (c3 == ' ' || c3 == '%' || c3 == '\\' || c3 == '_' || c3 == '\"' || c3 == '#') {
            return c3;
        }
        switch (c3) {
            case '*':
            case '+':
            case ',':
                return c3;
            default:
                switch (c3) {
                    case ';':
                    case '<':
                    case '=':
                    case '>':
                        return c3;
                    default:
                        return c();
                }
        }
    }

    private char c() {
        int i2;
        int i3;
        int iA = a(this.f8383c);
        this.f8383c++;
        if (iA < 128) {
            return (char) iA;
        }
        if (iA < 192 || iA > 247) {
            return '?';
        }
        if (iA <= 223) {
            i2 = iA & 31;
            i3 = 1;
        } else if (iA <= 239) {
            i2 = iA & 15;
            i3 = 2;
        } else {
            i2 = iA & 7;
            i3 = 3;
        }
        for (int i4 = 0; i4 < i3; i4++) {
            int i5 = this.f8383c + 1;
            this.f8383c = i5;
            if (i5 == this.f8382b || this.f8387g[i5] != '\\') {
                return '?';
            }
            int i6 = i5 + 1;
            this.f8383c = i6;
            int iA2 = a(i6);
            this.f8383c++;
            if ((iA2 & 192) != 128) {
                return '?';
            }
            i2 = (i2 << 6) + (iA2 & 63);
        }
        return (char) i2;
    }

    /* JADX WARN: Code restructure failed: missing block: B:28:0x0054, code lost:
    
        r6.f8385e = r0;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private java.lang.String d() {
        /*
            r6 = this;
            int r0 = r6.f8383c
            int r1 = r0 + 4
            int r2 = r6.f8382b
            java.lang.String r3 = "Unexpected end of DN: "
            if (r1 >= r2) goto L98
            r6.f8384d = r0
            int r0 = r0 + 1
            r6.f8383c = r0
        L10:
            int r0 = r6.f8383c
            int r1 = r6.f8382b
            if (r0 == r1) goto L54
            char[] r1 = r6.f8387g
            char r2 = r1[r0]
            r4 = 43
            if (r2 == r4) goto L54
            r4 = 44
            if (r2 == r4) goto L54
            r4 = 59
            if (r2 != r4) goto L27
            goto L54
        L27:
            r4 = 32
            if (r2 != r4) goto L42
            r6.f8385e = r0
            int r0 = r0 + 1
            r6.f8383c = r0
        L31:
            int r0 = r6.f8383c
            int r1 = r6.f8382b
            if (r0 >= r1) goto L56
            char[] r1 = r6.f8387g
            char r1 = r1[r0]
            if (r1 != r4) goto L56
            int r0 = r0 + 1
            r6.f8383c = r0
            goto L31
        L42:
            r4 = 65
            if (r2 < r4) goto L4f
            r4 = 70
            if (r2 > r4) goto L4f
            int r2 = r2 + 32
            char r2 = (char) r2
            r1[r0] = r2
        L4f:
            int r0 = r0 + 1
            r6.f8383c = r0
            goto L10
        L54:
            r6.f8385e = r0
        L56:
            int r0 = r6.f8385e
            int r1 = r6.f8384d
            int r0 = r0 - r1
            r2 = 5
            if (r0 < r2) goto L81
            r2 = r0 & 1
            if (r2 == 0) goto L81
            int r2 = r0 / 2
            byte[] r3 = new byte[r2]
            int r1 = r1 + 1
            r4 = 0
        L69:
            if (r4 >= r2) goto L77
            int r5 = r6.a(r1)
            byte r5 = (byte) r5
            r3[r4] = r5
            int r1 = r1 + 2
            int r4 = r4 + 1
            goto L69
        L77:
            java.lang.String r1 = new java.lang.String
            char[] r2 = r6.f8387g
            int r3 = r6.f8384d
            r1.<init>(r2, r3, r0)
            return r1
        L81:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r1.append(r3)
            java.lang.String r2 = r6.f8381a
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            r0.<init>(r1)
            throw r0
        L98:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r1.append(r3)
            java.lang.String r2 = r6.f8381a
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            r0.<init>(r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huawei.secure.android.common.ssl.hostname.a.d():java.lang.String");
    }

    private String e() {
        int i2;
        int i3;
        int i4;
        int i5;
        char c3;
        char c4;
        char c5;
        int i6;
        int i7;
        char c6;
        char c7;
        while (true) {
            i2 = this.f8383c;
            i3 = this.f8382b;
            if (i2 >= i3 || this.f8387g[i2] != ' ') {
                break;
            }
            this.f8383c = i2 + 1;
        }
        if (i2 == i3) {
            return null;
        }
        this.f8384d = i2;
        this.f8383c = i2 + 1;
        while (true) {
            i4 = this.f8383c;
            i5 = this.f8382b;
            if (i4 >= i5 || (c7 = this.f8387g[i4]) == '=' || c7 == ' ') {
                break;
            }
            this.f8383c = i4 + 1;
        }
        if (i4 >= i5) {
            throw new IllegalStateException("Unexpected end of DN: " + this.f8381a);
        }
        this.f8385e = i4;
        if (this.f8387g[i4] == ' ') {
            while (true) {
                i6 = this.f8383c;
                i7 = this.f8382b;
                if (i6 >= i7 || (c6 = this.f8387g[i6]) == '=' || c6 != ' ') {
                    break;
                }
                this.f8383c = i6 + 1;
            }
            if (this.f8387g[i6] != '=' || i6 == i7) {
                throw new IllegalStateException("Unexpected end of DN: " + this.f8381a);
            }
        }
        this.f8383c++;
        while (true) {
            int i8 = this.f8383c;
            if (i8 >= this.f8382b || this.f8387g[i8] != ' ') {
                break;
            }
            this.f8383c = i8 + 1;
        }
        int i9 = this.f8385e;
        int i10 = this.f8384d;
        if (i9 - i10 > 4) {
            char[] cArr = this.f8387g;
            if (cArr[i10 + 3] == '.' && (((c3 = cArr[i10]) == 'O' || c3 == 'o') && (((c4 = cArr[i10 + 1]) == 'I' || c4 == 'i') && ((c5 = cArr[i10 + 2]) == 'D' || c5 == 'd')))) {
                this.f8384d = i10 + 4;
            }
        }
        char[] cArr2 = this.f8387g;
        int i11 = this.f8384d;
        return new String(cArr2, i11, i9 - i11);
    }

    private String f() {
        int i2 = this.f8383c + 1;
        this.f8383c = i2;
        this.f8384d = i2;
        this.f8385e = i2;
        while (true) {
            int i3 = this.f8383c;
            if (i3 == this.f8382b) {
                throw new IllegalStateException("Unexpected end of DN: " + this.f8381a);
            }
            char[] cArr = this.f8387g;
            char c3 = cArr[i3];
            if (c3 == '\"') {
                this.f8383c = i3 + 1;
                while (true) {
                    int i4 = this.f8383c;
                    if (i4 >= this.f8382b || this.f8387g[i4] != ' ') {
                        break;
                    }
                    this.f8383c = i4 + 1;
                }
                char[] cArr2 = this.f8387g;
                int i5 = this.f8384d;
                return new String(cArr2, i5, this.f8385e - i5);
            }
            if (c3 == '\\') {
                cArr[this.f8385e] = b();
            } else {
                cArr[this.f8385e] = c3;
            }
            this.f8383c++;
            this.f8385e++;
        }
    }

    public List<String> b(String str) {
        String strF;
        this.f8383c = 0;
        this.f8384d = 0;
        this.f8385e = 0;
        this.f8386f = 0;
        this.f8387g = this.f8381a.toCharArray();
        List<String> listEmptyList = Collections.emptyList();
        String strE = e();
        if (strE == null) {
            return listEmptyList;
        }
        do {
            int i2 = this.f8383c;
            if (i2 < this.f8382b) {
                char c3 = this.f8387g[i2];
                if (c3 == '\"') {
                    strF = f();
                } else if (c3 != '#') {
                    strF = (c3 == '+' || c3 == ',' || c3 == ';') ? "" : a();
                } else {
                    strF = d();
                }
                if (str.equalsIgnoreCase(strE)) {
                    if (listEmptyList.isEmpty()) {
                        listEmptyList = new ArrayList<>();
                    }
                    listEmptyList.add(strF);
                }
                int i3 = this.f8383c;
                if (i3 < this.f8382b) {
                    char c4 = this.f8387g[i3];
                    if (c4 != ',' && c4 != ';' && c4 != '+') {
                        throw new IllegalStateException("Malformed DN: " + this.f8381a);
                    }
                    this.f8383c = i3 + 1;
                    strE = e();
                }
            }
            return listEmptyList;
        } while (strE != null);
        throw new IllegalStateException("Malformed DN: " + this.f8381a);
    }

    private int a(int i2) {
        int i3;
        int i4;
        int i5 = i2 + 1;
        if (i5 < this.f8382b) {
            char[] cArr = this.f8387g;
            char c3 = cArr[i2];
            if (c3 >= '0' && c3 <= '9') {
                i3 = c3 - '0';
            } else if (c3 >= 'a' && c3 <= 'f') {
                i3 = c3 - 'W';
            } else {
                if (c3 < 'A' || c3 > 'F') {
                    throw new IllegalStateException("Malformed DN: " + this.f8381a);
                }
                i3 = c3 - '7';
            }
            char c4 = cArr[i5];
            if (c4 >= '0' && c4 <= '9') {
                i4 = c4 - '0';
            } else if (c4 >= 'a' && c4 <= 'f') {
                i4 = c4 - 'W';
            } else {
                if (c4 < 'A' || c4 > 'F') {
                    throw new IllegalStateException("Malformed DN: " + this.f8381a);
                }
                i4 = c4 - '7';
            }
            return (i3 << 4) + i4;
        }
        throw new IllegalStateException("Malformed DN: " + this.f8381a);
    }

    public String a(String str) {
        String strF;
        this.f8383c = 0;
        this.f8384d = 0;
        this.f8385e = 0;
        this.f8386f = 0;
        this.f8387g = this.f8381a.toCharArray();
        String strE = e();
        if (strE == null) {
            return null;
        }
        do {
            int i2 = this.f8383c;
            if (i2 == this.f8382b) {
                return null;
            }
            char c3 = this.f8387g[i2];
            if (c3 == '\"') {
                strF = f();
            } else if (c3 != '#') {
                strF = (c3 == '+' || c3 == ',' || c3 == ';') ? "" : a();
            } else {
                strF = d();
            }
            if (str.equalsIgnoreCase(strE)) {
                return strF;
            }
            int i3 = this.f8383c;
            if (i3 >= this.f8382b) {
                return null;
            }
            char c4 = this.f8387g[i3];
            if (c4 != ',' && c4 != ';' && c4 != '+') {
                throw new IllegalStateException("Malformed DN: " + this.f8381a);
            }
            this.f8383c = i3 + 1;
            strE = e();
        } while (strE != null);
        throw new IllegalStateException("Malformed DN: " + this.f8381a);
    }
}
