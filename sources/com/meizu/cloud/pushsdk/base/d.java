package com.meizu.cloud.pushsdk.base;

/* loaded from: classes4.dex */
class d {

    /* renamed from: a, reason: collision with root package name */
    private static final char[] f9249a = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/'};

    /* renamed from: b, reason: collision with root package name */
    private static final char f9250b = (char) Integer.parseInt("00000011", 2);

    /* renamed from: c, reason: collision with root package name */
    private static final char f9251c = (char) Integer.parseInt("00001111", 2);

    /* renamed from: d, reason: collision with root package name */
    private static final char f9252d = (char) Integer.parseInt("00111111", 2);

    /* renamed from: e, reason: collision with root package name */
    private String f9253e;

    /* renamed from: f, reason: collision with root package name */
    private char[] f9254f;

    /* renamed from: g, reason: collision with root package name */
    private int f9255g = 0;

    public d(String str) {
        this.f9253e = str;
        a();
    }

    private void a() {
        char[] cArr = new char[f9249a.length];
        int i2 = 0;
        this.f9255g = this.f9253e.charAt(0) % '\r';
        while (true) {
            char[] cArr2 = f9249a;
            if (i2 >= cArr2.length) {
                this.f9254f = cArr;
                return;
            } else {
                cArr[i2] = cArr2[(this.f9255g + i2) % cArr2.length];
                i2++;
            }
        }
    }

    public String a(byte[] bArr) {
        String str;
        if (bArr == null || bArr.length == 0) {
            return null;
        }
        StringBuilder sb = new StringBuilder(((bArr.length + 2) / 3) * 4);
        int length = bArr.length;
        int i2 = 0;
        while (i2 < length) {
            int i3 = i2 + 1;
            int i4 = bArr[i2] & 255;
            if (i3 == length) {
                sb.append(this.f9254f[i4 >>> 2]);
                sb.append(this.f9254f[(f9250b & i4) << 4]);
                str = "==";
            } else {
                int i5 = i3 + 1;
                int i6 = bArr[i3] & 255;
                if (i5 == length) {
                    sb.append(this.f9254f[i4 >>> 2]);
                    sb.append(this.f9254f[((f9250b & i4) << 4) | (i6 >>> 4)]);
                    sb.append(this.f9254f[(f9251c & i6) << 2]);
                    str = "=";
                } else {
                    int i7 = i5 + 1;
                    int i8 = bArr[i5] & 255;
                    sb.append(this.f9254f[i4 >>> 2]);
                    sb.append(this.f9254f[((i4 & f9250b) << 4) | (i6 >>> 4)]);
                    sb.append(this.f9254f[((i6 & f9251c) << 2) | (i8 >>> 6)]);
                    sb.append(this.f9254f[f9252d & i8]);
                    i2 = i7;
                }
            }
            sb.append(str);
            break;
        }
        return sb.toString();
    }
}
