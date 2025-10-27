package com.meizu.cloud.pushsdk.b.c;

import cn.hutool.core.text.StrPool;
import com.yikaobang.yixue.R2;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import okhttp3.HttpUrl;
import org.eclipse.jetty.util.URIUtil;
import org.slf4j.Marker;

/* loaded from: classes4.dex */
public class f {

    /* renamed from: a, reason: collision with root package name */
    private static final char[] f9081a = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    /* renamed from: b, reason: collision with root package name */
    private final String f9082b;

    /* renamed from: c, reason: collision with root package name */
    private final String f9083c;

    /* renamed from: d, reason: collision with root package name */
    private final String f9084d;

    /* renamed from: e, reason: collision with root package name */
    private final String f9085e;

    /* renamed from: f, reason: collision with root package name */
    private final int f9086f;

    /* renamed from: g, reason: collision with root package name */
    private final List<String> f9087g;

    /* renamed from: h, reason: collision with root package name */
    private final List<String> f9088h;

    /* renamed from: i, reason: collision with root package name */
    private final String f9089i;

    /* renamed from: j, reason: collision with root package name */
    private final String f9090j;

    /* renamed from: com.meizu.cloud.pushsdk.b.c.f$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {

        /* renamed from: a, reason: collision with root package name */
        static final /* synthetic */ int[] f9091a;

        static {
            int[] iArr = new int[a.EnumC0194a.values().length];
            f9091a = iArr;
            try {
                iArr[a.EnumC0194a.SUCCESS.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f9091a[a.EnumC0194a.INVALID_HOST.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f9091a[a.EnumC0194a.UNSUPPORTED_SCHEME.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f9091a[a.EnumC0194a.MISSING_SCHEME.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                f9091a[a.EnumC0194a.INVALID_PORT.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    public static final class a {

        /* renamed from: a, reason: collision with root package name */
        String f9092a;

        /* renamed from: d, reason: collision with root package name */
        String f9095d;

        /* renamed from: f, reason: collision with root package name */
        final List<String> f9097f;

        /* renamed from: g, reason: collision with root package name */
        List<String> f9098g;

        /* renamed from: h, reason: collision with root package name */
        String f9099h;

        /* renamed from: b, reason: collision with root package name */
        String f9093b = "";

        /* renamed from: c, reason: collision with root package name */
        String f9094c = "";

        /* renamed from: e, reason: collision with root package name */
        int f9096e = -1;

        /* renamed from: com.meizu.cloud.pushsdk.b.c.f$a$a, reason: collision with other inner class name */
        public enum EnumC0194a {
            SUCCESS,
            MISSING_SCHEME,
            UNSUPPORTED_SCHEME,
            INVALID_PORT,
            INVALID_HOST
        }

        public a() {
            ArrayList arrayList = new ArrayList();
            this.f9097f = arrayList;
            arrayList.add("");
        }

        private static String a(byte[] bArr) {
            int i2 = -1;
            int i3 = 0;
            int i4 = 0;
            int i5 = 0;
            while (i4 < bArr.length) {
                int i6 = i4;
                while (i6 < 16 && bArr[i6] == 0 && bArr[i6 + 1] == 0) {
                    i6 += 2;
                }
                int i7 = i6 - i4;
                if (i7 > i5) {
                    i2 = i4;
                    i5 = i7;
                }
                i4 = i6 + 2;
            }
            com.meizu.cloud.pushsdk.b.g.a aVar = new com.meizu.cloud.pushsdk.b.g.a();
            while (i3 < bArr.length) {
                if (i3 == i2) {
                    aVar.b(58);
                    i3 += i5;
                    if (i3 == 16) {
                        aVar.b(58);
                    }
                } else {
                    if (i3 > 0) {
                        aVar.b(58);
                    }
                    aVar.d(((bArr[i3] & 255) << 8) | (bArr[i3 + 1] & 255));
                    i3 += 2;
                }
            }
            return aVar.h();
        }

        private void a(String str, int i2, int i3) {
            if (i2 == i3) {
                return;
            }
            char cCharAt = str.charAt(i2);
            if (cCharAt == '/' || cCharAt == '\\') {
                this.f9097f.clear();
                this.f9097f.add("");
                i2++;
            } else {
                List<String> list = this.f9097f;
                list.set(list.size() - 1, "");
            }
            while (true) {
                int i4 = i2;
                if (i4 >= i3) {
                    return;
                }
                i2 = m.a(str, i4, i3, "/\\");
                boolean z2 = i2 < i3;
                a(str, i4, i2, z2, true);
                if (z2) {
                    i2++;
                }
            }
        }

        private void a(String str, int i2, int i3, boolean z2, boolean z3) {
            String strA = f.a(str, i2, i3, HttpUrl.PATH_SEGMENT_ENCODE_SET, z3, false, false, true);
            if (b(strA)) {
                return;
            }
            if (c(strA)) {
                c();
                return;
            }
            if (this.f9097f.get(r10.size() - 1).isEmpty()) {
                this.f9097f.set(r10.size() - 1, strA);
            } else {
                this.f9097f.add(strA);
            }
            if (z2) {
                this.f9097f.add("");
            }
        }

        private static boolean a(String str, int i2, int i3, byte[] bArr, int i4) {
            int i5 = i4;
            while (i2 < i3) {
                if (i5 == bArr.length) {
                    return false;
                }
                if (i5 != i4) {
                    if (str.charAt(i2) != '.') {
                        return false;
                    }
                    i2++;
                }
                int i6 = i2;
                int i7 = 0;
                while (i6 < i3) {
                    char cCharAt = str.charAt(i6);
                    if (cCharAt < '0' || cCharAt > '9') {
                        break;
                    }
                    if ((i7 == 0 && i2 != i6) || (i7 = ((i7 * 10) + cCharAt) - 48) > 255) {
                        return false;
                    }
                    i6++;
                }
                if (i6 - i2 == 0) {
                    return false;
                }
                bArr[i5] = (byte) i7;
                i5++;
                i2 = i6;
            }
            return i5 == i4 + 4;
        }

        private static int b(String str, int i2, int i3) {
            if (i3 - i2 < 2) {
                return -1;
            }
            char cCharAt = str.charAt(i2);
            if ((cCharAt >= 'a' && cCharAt <= 'z') || (cCharAt >= 'A' && cCharAt <= 'Z')) {
                while (true) {
                    i2++;
                    if (i2 >= i3) {
                        break;
                    }
                    char cCharAt2 = str.charAt(i2);
                    if (cCharAt2 < 'a' || cCharAt2 > 'z') {
                        if (cCharAt2 < 'A' || cCharAt2 > 'Z') {
                            if (cCharAt2 < '0' || cCharAt2 > '9') {
                                if (cCharAt2 != '+' && cCharAt2 != '-' && cCharAt2 != '.') {
                                    if (cCharAt2 == ':') {
                                        return i2;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            return -1;
        }

        private boolean b(String str) {
            return str.equals(StrPool.DOT) || str.equalsIgnoreCase("%2e");
        }

        private static int c(String str, int i2, int i3) {
            int i4 = 0;
            while (i2 < i3) {
                char cCharAt = str.charAt(i2);
                if (cCharAt != '\\' && cCharAt != '/') {
                    break;
                }
                i4++;
                i2++;
            }
            return i4;
        }

        private void c() {
            if (!this.f9097f.remove(r0.size() - 1).isEmpty() || this.f9097f.isEmpty()) {
                this.f9097f.add("");
            } else {
                this.f9097f.set(r0.size() - 1, "");
            }
        }

        private boolean c(String str) {
            return str.equals(StrPool.DOUBLE_DOT) || str.equalsIgnoreCase("%2e.") || str.equalsIgnoreCase(".%2e") || str.equalsIgnoreCase("%2e%2e");
        }

        private static int d(String str, int i2, int i3) {
            while (i2 < i3) {
                char cCharAt = str.charAt(i2);
                if (cCharAt == ':') {
                    return i2;
                }
                if (cCharAt == '[') {
                    do {
                        i2++;
                        if (i2 < i3) {
                        }
                    } while (str.charAt(i2) != ']');
                }
                i2++;
            }
            return i3;
        }

        private static String e(String str, int i2, int i3) {
            String strA = f.a(str, i2, i3, false);
            if (!strA.contains(":")) {
                return m.a(strA);
            }
            InetAddress inetAddressF = (strA.startsWith(StrPool.BRACKET_START) && strA.endsWith(StrPool.BRACKET_END)) ? f(strA, 1, strA.length() - 1) : f(strA, 0, strA.length());
            if (inetAddressF == null) {
                return null;
            }
            byte[] address = inetAddressF.getAddress();
            if (address.length == 16) {
                return a(address);
            }
            throw new AssertionError();
        }

        /* JADX WARN: Code restructure failed: missing block: B:42:0x007a, code lost:
        
            if (r4 == 16) goto L50;
         */
        /* JADX WARN: Code restructure failed: missing block: B:43:0x007c, code lost:
        
            if (r5 != (-1)) goto L45;
         */
        /* JADX WARN: Code restructure failed: missing block: B:44:0x007e, code lost:
        
            return null;
         */
        /* JADX WARN: Code restructure failed: missing block: B:45:0x007f, code lost:
        
            r11 = r4 - r5;
            java.lang.System.arraycopy(r1, r5, r1, 16 - r11, r11);
            java.util.Arrays.fill(r1, r5, (16 - r4) + r5, (byte) 0);
         */
        /* JADX WARN: Code restructure failed: missing block: B:47:0x008f, code lost:
        
            return java.net.InetAddress.getByAddress(r1);
         */
        /* JADX WARN: Code restructure failed: missing block: B:49:0x0095, code lost:
        
            throw new java.lang.AssertionError();
         */
        /* JADX WARN: Removed duplicated region for block: B:31:0x004f  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        private static java.net.InetAddress f(java.lang.String r11, int r12, int r13) {
            /*
                r0 = 16
                byte[] r1 = new byte[r0]
                r2 = 0
                r3 = -1
                r4 = r2
                r5 = r3
                r6 = r5
            L9:
                r7 = 0
                if (r12 >= r13) goto L7a
                if (r4 != r0) goto Lf
                return r7
            Lf:
                int r8 = r12 + 2
                if (r8 > r13) goto L27
                java.lang.String r9 = "::"
                r10 = 2
                boolean r9 = r11.regionMatches(r12, r9, r2, r10)
                if (r9 == 0) goto L27
                if (r5 == r3) goto L1f
                return r7
            L1f:
                int r4 = r4 + 2
                r5 = r4
                if (r8 != r13) goto L25
                goto L7a
            L25:
                r6 = r8
                goto L4b
            L27:
                if (r4 == 0) goto L4a
                java.lang.String r8 = ":"
                r9 = 1
                boolean r8 = r11.regionMatches(r12, r8, r2, r9)
                if (r8 == 0) goto L35
                int r12 = r12 + 1
                goto L4a
            L35:
                java.lang.String r8 = "."
                boolean r12 = r11.regionMatches(r12, r8, r2, r9)
                if (r12 == 0) goto L49
                int r12 = r4 + (-2)
                boolean r11 = a(r11, r6, r13, r1, r12)
                if (r11 != 0) goto L46
                return r7
            L46:
                int r4 = r4 + 2
                goto L7a
            L49:
                return r7
            L4a:
                r6 = r12
            L4b:
                r8 = r2
                r12 = r6
            L4d:
                if (r12 >= r13) goto L60
                char r9 = r11.charAt(r12)
                int r9 = com.meizu.cloud.pushsdk.b.c.f.a(r9)
                if (r9 != r3) goto L5a
                goto L60
            L5a:
                int r8 = r8 << 4
                int r8 = r8 + r9
                int r12 = r12 + 1
                goto L4d
            L60:
                int r9 = r12 - r6
                if (r9 == 0) goto L79
                r10 = 4
                if (r9 <= r10) goto L68
                goto L79
            L68:
                int r7 = r4 + 1
                int r9 = r8 >>> 8
                r9 = r9 & 255(0xff, float:3.57E-43)
                byte r9 = (byte) r9
                r1[r4] = r9
                int r4 = r7 + 1
                r8 = r8 & 255(0xff, float:3.57E-43)
                byte r8 = (byte) r8
                r1[r7] = r8
                goto L9
            L79:
                return r7
            L7a:
                if (r4 == r0) goto L8b
                if (r5 != r3) goto L7f
                return r7
            L7f:
                int r11 = r4 - r5
                int r12 = 16 - r11
                java.lang.System.arraycopy(r1, r5, r1, r12, r11)
                int r0 = r0 - r4
                int r0 = r0 + r5
                java.util.Arrays.fill(r1, r5, r0, r2)
            L8b:
                java.net.InetAddress r11 = java.net.InetAddress.getByAddress(r1)     // Catch: java.net.UnknownHostException -> L90
                return r11
            L90:
                java.lang.AssertionError r11 = new java.lang.AssertionError
                r11.<init>()
                throw r11
            */
            throw new UnsupportedOperationException("Method not decompiled: com.meizu.cloud.pushsdk.b.c.f.a.f(java.lang.String, int, int):java.net.InetAddress");
        }

        private static int g(String str, int i2, int i3) throws NumberFormatException {
            int i4;
            try {
                i4 = Integer.parseInt(f.a(str, i2, i3, "", false, false, false, true));
            } catch (NumberFormatException unused) {
            }
            if (i4 <= 0 || i4 > 65535) {
                return -1;
            }
            return i4;
        }

        public int a() {
            int i2 = this.f9096e;
            return i2 != -1 ? i2 : f.a(this.f9092a);
        }

        public EnumC0194a a(f fVar, String str) throws NumberFormatException {
            int iA;
            int i2;
            int iA2 = m.a(str, 0, str.length());
            int iB = m.b(str, iA2, str.length());
            if (b(str, iA2, iB) != -1) {
                if (str.regionMatches(true, iA2, URIUtil.HTTPS_COLON, 0, 6)) {
                    this.f9092a = "https";
                    iA2 += 6;
                } else {
                    if (!str.regionMatches(true, iA2, URIUtil.HTTP_COLON, 0, 5)) {
                        return EnumC0194a.UNSUPPORTED_SCHEME;
                    }
                    this.f9092a = "http";
                    iA2 += 5;
                }
            } else {
                if (fVar == null) {
                    return EnumC0194a.MISSING_SCHEME;
                }
                this.f9092a = fVar.f9082b;
            }
            int iC = c(str, iA2, iB);
            char c3 = '?';
            char c4 = '#';
            if (iC >= 2 || fVar == null || !fVar.f9082b.equals(this.f9092a)) {
                boolean z2 = false;
                boolean z3 = false;
                int i3 = iA2 + iC;
                while (true) {
                    iA = m.a(str, i3, iB, "@/\\?#");
                    char cCharAt = iA != iB ? str.charAt(iA) : (char) 65535;
                    if (cCharAt == 65535 || cCharAt == c4 || cCharAt == '/' || cCharAt == '\\' || cCharAt == c3) {
                        break;
                    }
                    if (cCharAt == '@') {
                        if (z2) {
                            i2 = iA;
                            this.f9094c += "%40" + f.a(str, i3, i2, " \"':;<=>@[]^`{}|/\\?#", true, false, false, true);
                        } else {
                            int iA3 = m.a(str, i3, iA, ':');
                            i2 = iA;
                            String strA = f.a(str, i3, iA3, " \"':;<=>@[]^`{}|/\\?#", true, false, false, true);
                            if (z3) {
                                strA = this.f9093b + "%40" + strA;
                            }
                            this.f9093b = strA;
                            if (iA3 != i2) {
                                this.f9094c = f.a(str, iA3 + 1, i2, " \"':;<=>@[]^`{}|/\\?#", true, false, false, true);
                                z2 = true;
                            }
                            z3 = true;
                        }
                        i3 = i2 + 1;
                    }
                    c3 = '?';
                    c4 = '#';
                }
                int iD = d(str, i3, iA);
                int i4 = iD + 1;
                this.f9095d = e(str, i3, iD);
                if (i4 < iA) {
                    int iG = g(str, i4, iA);
                    this.f9096e = iG;
                    if (iG == -1) {
                        return EnumC0194a.INVALID_PORT;
                    }
                } else {
                    this.f9096e = f.a(this.f9092a);
                }
                if (this.f9095d == null) {
                    return EnumC0194a.INVALID_HOST;
                }
                iA2 = iA;
            } else {
                this.f9093b = fVar.b();
                this.f9094c = fVar.c();
                this.f9095d = fVar.f9085e;
                this.f9096e = fVar.f9086f;
                this.f9097f.clear();
                this.f9097f.addAll(fVar.d());
                if (iA2 == iB || str.charAt(iA2) == '#') {
                    a(fVar.e());
                }
            }
            int iA4 = m.a(str, iA2, iB, "?#");
            a(str, iA2, iA4);
            if (iA4 < iB && str.charAt(iA4) == '?') {
                int iA5 = m.a(str, iA4, iB, '#');
                this.f9098g = f.b(f.a(str, iA4 + 1, iA5, HttpUrl.QUERY_ENCODE_SET, true, false, true, true));
                iA4 = iA5;
            }
            if (iA4 < iB && str.charAt(iA4) == '#') {
                this.f9099h = f.a(str, 1 + iA4, iB, "", true, false, false, false);
            }
            return EnumC0194a.SUCCESS;
        }

        public a a(String str) {
            this.f9098g = str != null ? f.b(f.a(str, HttpUrl.QUERY_ENCODE_SET, true, false, true, true)) : null;
            return this;
        }

        public a a(String str, String str2) {
            if (str == null) {
                throw new IllegalArgumentException("name == null");
            }
            if (this.f9098g == null) {
                this.f9098g = new ArrayList();
            }
            this.f9098g.add(f.a(str, HttpUrl.QUERY_COMPONENT_REENCODE_SET, false, false, true, true));
            this.f9098g.add(str2 != null ? f.a(str2, HttpUrl.QUERY_COMPONENT_REENCODE_SET, false, false, true, true) : null);
            return this;
        }

        public f b() {
            if (this.f9092a == null) {
                throw new IllegalStateException("scheme == null");
            }
            if (this.f9095d != null) {
                return new f(this, null);
            }
            throw new IllegalStateException("host == null");
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(this.f9092a);
            sb.append("://");
            if (!this.f9093b.isEmpty() || !this.f9094c.isEmpty()) {
                sb.append(this.f9093b);
                if (!this.f9094c.isEmpty()) {
                    sb.append(':');
                    sb.append(this.f9094c);
                }
                sb.append('@');
            }
            if (this.f9095d.indexOf(58) != -1) {
                sb.append('[');
                sb.append(this.f9095d);
                sb.append(']');
            } else {
                sb.append(this.f9095d);
            }
            int iA = a();
            if (iA != f.a(this.f9092a)) {
                sb.append(':');
                sb.append(iA);
            }
            f.a(sb, this.f9097f);
            if (this.f9098g != null) {
                sb.append('?');
                f.b(sb, this.f9098g);
            }
            if (this.f9099h != null) {
                sb.append('#');
                sb.append(this.f9099h);
            }
            return sb.toString();
        }
    }

    private f(a aVar) {
        this.f9082b = aVar.f9092a;
        this.f9083c = a(aVar.f9093b, false);
        this.f9084d = a(aVar.f9094c, false);
        this.f9085e = aVar.f9095d;
        this.f9086f = aVar.a();
        this.f9087g = a(aVar.f9097f, false);
        List<String> list = aVar.f9098g;
        this.f9088h = list != null ? a(list, true) : null;
        String str = aVar.f9099h;
        this.f9089i = str != null ? a(str, false) : null;
        this.f9090j = aVar.toString();
    }

    public /* synthetic */ f(a aVar, AnonymousClass1 anonymousClass1) {
        this(aVar);
    }

    public static int a(char c3) {
        if (c3 >= '0' && c3 <= '9') {
            return c3 - '0';
        }
        char c4 = 'a';
        if (c3 < 'a' || c3 > 'f') {
            c4 = 'A';
            if (c3 < 'A' || c3 > 'F') {
                return -1;
            }
        }
        return (c3 - c4) + 10;
    }

    public static int a(String str) {
        if (str.equals("http")) {
            return 80;
        }
        if (str.equals("https")) {
            return R2.attr.banner_indicator_selected_color;
        }
        return -1;
    }

    public static String a(String str, int i2, int i3, String str2, boolean z2, boolean z3, boolean z4, boolean z5) {
        int iCharCount = i2;
        while (iCharCount < i3) {
            int iCodePointAt = str.codePointAt(iCharCount);
            if (iCodePointAt < 32 || iCodePointAt == 127 || (iCodePointAt >= 128 && z5)) {
                com.meizu.cloud.pushsdk.b.g.a aVar = new com.meizu.cloud.pushsdk.b.g.a();
                aVar.a(str, i2, iCharCount);
                a(aVar, str, iCharCount, i3, str2, z2, z3, z4, z5);
                return aVar.h();
            }
            if (str2.indexOf(iCodePointAt) != -1 || ((iCodePointAt == 37 && (!z2 || (z3 && !a(str, iCharCount, i3)))) || (iCodePointAt == 43 && z4))) {
                com.meizu.cloud.pushsdk.b.g.a aVar2 = new com.meizu.cloud.pushsdk.b.g.a();
                aVar2.a(str, i2, iCharCount);
                a(aVar2, str, iCharCount, i3, str2, z2, z3, z4, z5);
                return aVar2.h();
            }
            iCharCount += Character.charCount(iCodePointAt);
        }
        return str.substring(i2, i3);
    }

    public static String a(String str, int i2, int i3, boolean z2) {
        for (int i4 = i2; i4 < i3; i4++) {
            char cCharAt = str.charAt(i4);
            if (cCharAt == '%' || (cCharAt == '+' && z2)) {
                com.meizu.cloud.pushsdk.b.g.a aVar = new com.meizu.cloud.pushsdk.b.g.a();
                aVar.a(str, i2, i4);
                a(aVar, str, i4, i3, z2);
                return aVar.h();
            }
        }
        return str.substring(i2, i3);
    }

    public static String a(String str, String str2, boolean z2, boolean z3, boolean z4, boolean z5) {
        return a(str, 0, str.length(), str2, z2, z3, z4, z5);
    }

    public static String a(String str, boolean z2) {
        return a(str, 0, str.length(), z2);
    }

    private List<String> a(List<String> list, boolean z2) {
        ArrayList arrayList = new ArrayList(list.size());
        Iterator<String> it = list.iterator();
        while (it.hasNext()) {
            String next = it.next();
            arrayList.add(next != null ? a(next, z2) : null);
        }
        return Collections.unmodifiableList(arrayList);
    }

    public static void a(com.meizu.cloud.pushsdk.b.g.a aVar, String str, int i2, int i3, String str2, boolean z2, boolean z3, boolean z4, boolean z5) {
        com.meizu.cloud.pushsdk.b.g.a aVar2 = null;
        while (i2 < i3) {
            int iCodePointAt = str.codePointAt(i2);
            if (!z2 || (iCodePointAt != 9 && iCodePointAt != 10 && iCodePointAt != 12 && iCodePointAt != 13)) {
                if (iCodePointAt == 43 && z4) {
                    aVar.b(z2 ? Marker.ANY_NON_NULL_MARKER : "%2B");
                } else if (iCodePointAt < 32 || iCodePointAt == 127 || ((iCodePointAt >= 128 && z5) || str2.indexOf(iCodePointAt) != -1 || (iCodePointAt == 37 && (!z2 || (z3 && !a(str, i2, i3)))))) {
                    if (aVar2 == null) {
                        aVar2 = new com.meizu.cloud.pushsdk.b.g.a();
                    }
                    aVar2.a(iCodePointAt);
                    while (!aVar2.c()) {
                        int iF = aVar2.f() & 255;
                        aVar.b(37);
                        char[] cArr = f9081a;
                        aVar.b((int) cArr[(iF >> 4) & 15]);
                        aVar.b((int) cArr[iF & 15]);
                    }
                } else {
                    aVar.a(iCodePointAt);
                }
            }
            i2 += Character.charCount(iCodePointAt);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0039  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void a(com.meizu.cloud.pushsdk.b.g.a r5, java.lang.String r6, int r7, int r8, boolean r9) {
        /*
        L0:
            if (r7 >= r8) goto L42
            int r0 = r6.codePointAt(r7)
            r1 = 37
            if (r0 != r1) goto L2d
            int r1 = r7 + 2
            if (r1 >= r8) goto L2d
            int r2 = r7 + 1
            char r2 = r6.charAt(r2)
            int r2 = a(r2)
            char r3 = r6.charAt(r1)
            int r3 = a(r3)
            r4 = -1
            if (r2 == r4) goto L39
            if (r3 == r4) goto L39
            int r7 = r2 << 4
            int r7 = r7 + r3
            r5.b(r7)
            r7 = r1
            goto L3c
        L2d:
            r1 = 43
            if (r0 != r1) goto L39
            if (r9 == 0) goto L39
            r1 = 32
            r5.b(r1)
            goto L3c
        L39:
            r5.a(r0)
        L3c:
            int r0 = java.lang.Character.charCount(r0)
            int r7 = r7 + r0
            goto L0
        L42:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.meizu.cloud.pushsdk.b.c.f.a(com.meizu.cloud.pushsdk.b.g.a, java.lang.String, int, int, boolean):void");
    }

    public static void a(StringBuilder sb, List<String> list) {
        int size = list.size();
        for (int i2 = 0; i2 < size; i2++) {
            sb.append('/');
            sb.append(list.get(i2));
        }
    }

    public static boolean a(String str, int i2, int i3) {
        int i4 = i2 + 2;
        return i4 < i3 && str.charAt(i2) == '%' && a(str.charAt(i2 + 1)) != -1 && a(str.charAt(i4)) != -1;
    }

    public static List<String> b(String str) {
        String strSubstring;
        ArrayList arrayList = new ArrayList();
        int i2 = 0;
        while (i2 <= str.length()) {
            int iIndexOf = str.indexOf(38, i2);
            if (iIndexOf == -1) {
                iIndexOf = str.length();
            }
            int iIndexOf2 = str.indexOf(61, i2);
            if (iIndexOf2 == -1 || iIndexOf2 > iIndexOf) {
                arrayList.add(str.substring(i2, iIndexOf));
                strSubstring = null;
            } else {
                arrayList.add(str.substring(i2, iIndexOf2));
                strSubstring = str.substring(iIndexOf2 + 1, iIndexOf);
            }
            arrayList.add(strSubstring);
            i2 = iIndexOf + 1;
        }
        return arrayList;
    }

    public static void b(StringBuilder sb, List<String> list) {
        int size = list.size();
        for (int i2 = 0; i2 < size; i2 += 2) {
            String str = list.get(i2);
            String str2 = list.get(i2 + 1);
            if (i2 > 0) {
                sb.append('&');
            }
            sb.append(str);
            if (str2 != null) {
                sb.append('=');
                sb.append(str2);
            }
        }
    }

    public static f c(String str) {
        a aVar = new a();
        if (aVar.a((f) null, str) == a.EnumC0194a.SUCCESS) {
            return aVar.b();
        }
        return null;
    }

    public boolean a() {
        return this.f9082b.equals("https");
    }

    public String b() {
        if (this.f9083c.isEmpty()) {
            return "";
        }
        int length = this.f9082b.length() + 3;
        String str = this.f9090j;
        return this.f9090j.substring(length, m.a(str, length, str.length(), ":@"));
    }

    public String c() {
        if (this.f9084d.isEmpty()) {
            return "";
        }
        return this.f9090j.substring(this.f9090j.indexOf(58, this.f9082b.length() + 3) + 1, this.f9090j.indexOf(64));
    }

    public List<String> d() {
        int iIndexOf = this.f9090j.indexOf(47, this.f9082b.length() + 3);
        String str = this.f9090j;
        int iA = m.a(str, iIndexOf, str.length(), "?#");
        ArrayList arrayList = new ArrayList();
        while (iIndexOf < iA) {
            int i2 = iIndexOf + 1;
            int iA2 = m.a(this.f9090j, i2, iA, '/');
            arrayList.add(this.f9090j.substring(i2, iA2));
            iIndexOf = iA2;
        }
        return arrayList;
    }

    public String e() {
        if (this.f9088h == null) {
            return null;
        }
        int iIndexOf = this.f9090j.indexOf(63) + 1;
        String str = this.f9090j;
        return this.f9090j.substring(iIndexOf, m.a(str, iIndexOf + 1, str.length(), '#'));
    }

    public boolean equals(Object obj) {
        return (obj instanceof f) && ((f) obj).f9090j.equals(this.f9090j);
    }

    public String f() {
        if (this.f9089i == null) {
            return null;
        }
        return this.f9090j.substring(this.f9090j.indexOf(35) + 1);
    }

    public a g() {
        a aVar = new a();
        aVar.f9092a = this.f9082b;
        aVar.f9093b = b();
        aVar.f9094c = c();
        aVar.f9095d = this.f9085e;
        aVar.f9096e = this.f9086f != a(this.f9082b) ? this.f9086f : -1;
        aVar.f9097f.clear();
        aVar.f9097f.addAll(d());
        aVar.a(e());
        aVar.f9099h = f();
        return aVar;
    }

    public int hashCode() {
        return this.f9090j.hashCode();
    }

    public String toString() {
        return this.f9090j;
    }
}
