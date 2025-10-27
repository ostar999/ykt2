package com.meizu.cloud.pushsdk.b.g;

import com.umeng.analytics.pro.am;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/* loaded from: classes4.dex */
public class d implements Serializable, Comparable<d> {

    /* renamed from: a, reason: collision with root package name */
    static final char[] f9181a = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /* renamed from: b, reason: collision with root package name */
    public static final d f9182b = a(new byte[0]);
    private static final long serialVersionUID = 1;

    /* renamed from: c, reason: collision with root package name */
    final byte[] f9183c;

    /* renamed from: d, reason: collision with root package name */
    transient int f9184d;

    /* renamed from: e, reason: collision with root package name */
    transient String f9185e;

    public d(byte[] bArr) {
        this.f9183c = bArr;
    }

    public static d a(InputStream inputStream, int i2) throws IOException {
        if (inputStream == null) {
            throw new IllegalArgumentException("in == null");
        }
        if (i2 < 0) {
            throw new IllegalArgumentException("byteCount < 0: " + i2);
        }
        byte[] bArr = new byte[i2];
        int i3 = 0;
        while (i3 < i2) {
            int i4 = inputStream.read(bArr, i3, i2 - i3);
            if (i4 == -1) {
                throw new EOFException();
            }
            i3 += i4;
        }
        return new d(bArr);
    }

    public static d a(String str) {
        if (str == null) {
            throw new IllegalArgumentException("s == null");
        }
        d dVar = new d(str.getBytes(n.f9211a));
        dVar.f9185e = str;
        return dVar;
    }

    public static d a(byte... bArr) {
        if (bArr != null) {
            return new d((byte[]) bArr.clone());
        }
        throw new IllegalArgumentException("data == null");
    }

    private d b(String str) {
        try {
            return a(MessageDigest.getInstance(str).digest(this.f9183c));
        } catch (NoSuchAlgorithmException e2) {
            throw new AssertionError(e2);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IllegalAccessException, NoSuchFieldException, IOException, SecurityException, IllegalArgumentException {
        d dVarA = a(objectInputStream, objectInputStream.readInt());
        try {
            Field declaredField = d.class.getDeclaredField(am.aF);
            declaredField.setAccessible(true);
            declaredField.set(this, dVarA.f9183c);
        } catch (IllegalAccessException unused) {
            throw new AssertionError();
        } catch (NoSuchFieldException unused2) {
            throw new AssertionError();
        }
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.writeInt(this.f9183c.length);
        objectOutputStream.write(this.f9183c);
    }

    public byte a(int i2) {
        return this.f9183c[i2];
    }

    @Override // java.lang.Comparable
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public int compareTo(d dVar) {
        int iD = d();
        int iD2 = dVar.d();
        int iMin = Math.min(iD, iD2);
        for (int i2 = 0; i2 < iMin; i2++) {
            int iA = a(i2) & 255;
            int iA2 = dVar.a(i2) & 255;
            if (iA != iA2) {
                return iA < iA2 ? -1 : 1;
            }
        }
        if (iD == iD2) {
            return 0;
        }
        return iD < iD2 ? -1 : 1;
    }

    public String a() {
        String str = this.f9185e;
        if (str != null) {
            return str;
        }
        String str2 = new String(this.f9183c, n.f9211a);
        this.f9185e = str2;
        return str2;
    }

    public void a(a aVar) {
        byte[] bArr = this.f9183c;
        aVar.c(bArr, 0, bArr.length);
    }

    public boolean a(int i2, byte[] bArr, int i3, int i4) {
        byte[] bArr2 = this.f9183c;
        return i2 <= bArr2.length - i4 && i3 <= bArr.length - i4 && n.a(bArr2, i2, bArr, i3, i4);
    }

    public d b() {
        return b("MD5");
    }

    public String c() {
        byte[] bArr = this.f9183c;
        char[] cArr = new char[bArr.length * 2];
        int i2 = 0;
        for (byte b3 : bArr) {
            int i3 = i2 + 1;
            char[] cArr2 = f9181a;
            cArr[i2] = cArr2[(b3 >> 4) & 15];
            i2 = i3 + 1;
            cArr[i3] = cArr2[b3 & 15];
        }
        return new String(cArr);
    }

    public int d() {
        return this.f9183c.length;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof d) {
            d dVar = (d) obj;
            int iD = dVar.d();
            byte[] bArr = this.f9183c;
            if (iD == bArr.length && dVar.a(0, bArr, 0, bArr.length)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        int i2 = this.f9184d;
        if (i2 != 0) {
            return i2;
        }
        int iHashCode = Arrays.hashCode(this.f9183c);
        this.f9184d = iHashCode;
        return iHashCode;
    }

    public String toString() {
        byte[] bArr = this.f9183c;
        return bArr.length == 0 ? "ByteString[size=0]" : bArr.length <= 16 ? String.format("ByteString[size=%s data=%s]", Integer.valueOf(bArr.length), c()) : String.format("ByteString[size=%s md5=%s]", Integer.valueOf(bArr.length), b().c());
    }
}
