package org.bouncycastle.asn1.x509;

/* loaded from: classes9.dex */
public class X509NameTokenizer {
    private StringBuffer buf;
    private int index;
    private char seperator;
    private String value;

    public X509NameTokenizer(String str) {
        this(str, ',');
    }

    public X509NameTokenizer(String str, char c3) {
        this.buf = new StringBuffer();
        this.value = str;
        this.index = -1;
        this.seperator = c3;
    }

    public boolean hasMoreTokens() {
        return this.index != this.value.length();
    }

    /* JADX WARN: Removed duplicated region for block: B:28:0x0059  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x005f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.String nextToken() {
        /*
            r8 = this;
            int r0 = r8.index
            java.lang.String r1 = r8.value
            int r1 = r1.length()
            if (r0 != r1) goto Lc
            r0 = 0
            return r0
        Lc:
            int r0 = r8.index
            r1 = 1
            int r0 = r0 + r1
            java.lang.StringBuffer r2 = r8.buf
            r3 = 0
            r2.setLength(r3)
            r2 = r3
            r4 = r2
        L18:
            java.lang.String r5 = r8.value
            int r5 = r5.length()
            if (r0 == r5) goto L71
            java.lang.String r5 = r8.value
            char r5 = r5.charAt(r0)
            r6 = 34
            if (r5 != r6) goto L30
            if (r2 != 0) goto L68
            r4 = r4 ^ 1
        L2e:
            r2 = r3
            goto L6e
        L30:
            r6 = 92
            if (r2 != 0) goto L46
            if (r4 == 0) goto L37
            goto L46
        L37:
            if (r5 != r6) goto L3b
            r2 = r1
            goto L6e
        L3b:
            char r6 = r8.seperator
            if (r5 != r6) goto L40
            goto L71
        L40:
            java.lang.StringBuffer r6 = r8.buf
            r6.append(r5)
            goto L6e
        L46:
            r2 = 35
            if (r5 != r2) goto L5f
            java.lang.StringBuffer r2 = r8.buf
            int r7 = r2.length()
            int r7 = r7 - r1
            char r2 = r2.charAt(r7)
            r7 = 61
            if (r2 != r7) goto L5f
        L59:
            java.lang.StringBuffer r2 = r8.buf
            r2.append(r6)
            goto L68
        L5f:
            r2 = 43
            if (r5 != r2) goto L68
            char r7 = r8.seperator
            if (r7 == r2) goto L68
            goto L59
        L68:
            java.lang.StringBuffer r2 = r8.buf
            r2.append(r5)
            goto L2e
        L6e:
            int r0 = r0 + 1
            goto L18
        L71:
            r8.index = r0
            java.lang.StringBuffer r0 = r8.buf
            java.lang.String r0 = r0.toString()
            java.lang.String r0 = r0.trim()
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.asn1.x509.X509NameTokenizer.nextToken():java.lang.String");
    }
}
