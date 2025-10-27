package com.xiaomi.push.service;

/* loaded from: classes6.dex */
public class bc {

    /* renamed from: a, reason: collision with root package name */
    private static int f25642a = 8;

    /* renamed from: d, reason: collision with root package name */
    private int f25645d = -666;

    /* renamed from: a, reason: collision with other field name */
    private byte[] f1026a = new byte[256];

    /* renamed from: c, reason: collision with root package name */
    private int f25644c = 0;

    /* renamed from: b, reason: collision with root package name */
    private int f25643b = 0;

    public static int a(byte b3) {
        return b3 >= 0 ? b3 : b3 + 256;
    }

    private void a() {
        this.f25644c = 0;
        this.f25643b = 0;
    }

    private void a(int i2, byte[] bArr, boolean z2) {
        int length = bArr.length;
        for (int i3 = 0; i3 < 256; i3++) {
            this.f1026a[i3] = (byte) i3;
        }
        this.f25644c = 0;
        this.f25643b = 0;
        while (true) {
            int i4 = this.f25643b;
            if (i4 >= i2) {
                break;
            }
            int iA = ((this.f25644c + a(this.f1026a[i4])) + a(bArr[this.f25643b % length])) % 256;
            this.f25644c = iA;
            a(this.f1026a, this.f25643b, iA);
            this.f25643b++;
        }
        if (i2 != 256) {
            this.f25645d = ((this.f25644c + a(this.f1026a[i2])) + a(bArr[i2 % length])) % 256;
        }
        if (z2) {
            StringBuilder sb = new StringBuilder();
            sb.append("S_");
            int i5 = i2 - 1;
            sb.append(i5);
            sb.append(":");
            for (int i6 = 0; i6 <= i2; i6++) {
                sb.append(" ");
                sb.append(a(this.f1026a[i6]));
            }
            sb.append("   j_");
            sb.append(i5);
            sb.append("=");
            sb.append(this.f25644c);
            sb.append("   j_");
            sb.append(i2);
            sb.append("=");
            sb.append(this.f25645d);
            sb.append("   S_");
            sb.append(i5);
            sb.append("[j_");
            sb.append(i5);
            sb.append("]=");
            sb.append(a(this.f1026a[this.f25644c]));
            sb.append("   S_");
            sb.append(i5);
            sb.append("[j_");
            sb.append(i2);
            sb.append("]=");
            sb.append(a(this.f1026a[this.f25645d]));
            if (this.f1026a[1] != 0) {
                sb.append("   S[1]!=0");
            }
            com.xiaomi.channel.commonutils.logger.b.m117a(sb.toString());
        }
    }

    private void a(byte[] bArr) {
        a(256, bArr, false);
    }

    private static void a(byte[] bArr, int i2, int i3) {
        byte b3 = bArr[i2];
        bArr[i2] = bArr[i3];
        bArr[i3] = b3;
    }

    public static byte[] a(String str, String str2) {
        byte[] bArrA = com.xiaomi.push.av.a(str);
        byte[] bytes = str2.getBytes();
        byte[] bArr = new byte[bArrA.length + 1 + bytes.length];
        for (int i2 = 0; i2 < bArrA.length; i2++) {
            bArr[i2] = bArrA[i2];
        }
        bArr[bArrA.length] = 95;
        for (int i3 = 0; i3 < bytes.length; i3++) {
            bArr[bArrA.length + 1 + i3] = bytes[i3];
        }
        return bArr;
    }

    public static byte[] a(byte[] bArr, String str) {
        return a(bArr, com.xiaomi.push.av.a(str));
    }

    public static byte[] a(byte[] bArr, byte[] bArr2) {
        byte[] bArr3 = new byte[bArr2.length];
        bc bcVar = new bc();
        bcVar.a(bArr);
        bcVar.a();
        for (int i2 = 0; i2 < bArr2.length; i2++) {
            bArr3[i2] = (byte) (bArr2[i2] ^ bcVar.m726a());
        }
        return bArr3;
    }

    public static byte[] a(byte[] bArr, byte[] bArr2, boolean z2, int i2, int i3) {
        byte[] bArr3;
        int i4;
        if (i2 < 0 || i2 > bArr2.length || i2 + i3 > bArr2.length) {
            throw new IllegalArgumentException("start = " + i2 + " len = " + i3);
        }
        if (z2) {
            bArr3 = bArr2;
            i4 = i2;
        } else {
            bArr3 = new byte[i3];
            i4 = 0;
        }
        bc bcVar = new bc();
        bcVar.a(bArr);
        bcVar.a();
        for (int i5 = 0; i5 < i3; i5++) {
            bArr3[i4 + i5] = (byte) (bArr2[i2 + i5] ^ bcVar.m726a());
        }
        return bArr3;
    }

    /* renamed from: a, reason: collision with other method in class */
    public byte m726a() {
        int i2 = (this.f25643b + 1) % 256;
        this.f25643b = i2;
        int iA = (this.f25644c + a(this.f1026a[i2])) % 256;
        this.f25644c = iA;
        a(this.f1026a, this.f25643b, iA);
        byte[] bArr = this.f1026a;
        return bArr[(a(bArr[this.f25643b]) + a(this.f1026a[this.f25644c])) % 256];
    }
}
