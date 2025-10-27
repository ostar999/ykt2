package com.tencent.liteav.network.a.a;

import cn.hutool.core.text.StrPool;
import com.tencent.liteav.network.a.e;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.IDN;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashSet;

/* loaded from: classes6.dex */
public final class b {
    public static byte[] a(String str, int i2) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(512);
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        com.tencent.liteav.network.a.b.a aVar = new com.tencent.liteav.network.a.b.a();
        aVar.a(8);
        try {
            dataOutputStream.writeShort((short) i2);
            dataOutputStream.writeShort((short) aVar.a());
            dataOutputStream.writeShort(1);
            dataOutputStream.writeShort(0);
            dataOutputStream.writeShort(0);
            dataOutputStream.writeShort(0);
            dataOutputStream.flush();
            b(byteArrayOutputStream, str);
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e2) {
            throw new AssertionError(e2);
        }
    }

    private static void b(OutputStream outputStream, String str) throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        a(outputStream, str);
        dataOutputStream.writeShort(1);
        dataOutputStream.writeShort(1);
    }

    private static e[] b(DataInputStream dataInputStream, byte[] bArr, int i2) throws IOException {
        e[] eVarArr = new e[i2];
        int i3 = 0;
        while (true) {
            int i4 = i2 - 1;
            if (i2 <= 0) {
                return eVarArr;
            }
            eVarArr[i3] = b(dataInputStream, bArr);
            i3++;
            i2 = i4;
        }
    }

    private static e b(DataInputStream dataInputStream, byte[] bArr) throws IOException {
        String hostAddress;
        a(dataInputStream, bArr);
        int unsignedShort = dataInputStream.readUnsignedShort();
        dataInputStream.readUnsignedShort();
        long unsignedShort2 = (dataInputStream.readUnsignedShort() << 16) + dataInputStream.readUnsignedShort();
        int unsignedShort3 = dataInputStream.readUnsignedShort();
        if (unsignedShort == 1) {
            byte[] bArr2 = new byte[4];
            dataInputStream.readFully(bArr2);
            hostAddress = InetAddress.getByAddress(bArr2).getHostAddress();
        } else if (unsignedShort != 5) {
            for (int i2 = 0; i2 < unsignedShort3; i2++) {
                dataInputStream.readByte();
            }
            hostAddress = null;
        } else {
            hostAddress = a(dataInputStream, bArr);
        }
        if (hostAddress != null) {
            return new e(hostAddress, unsignedShort, (int) unsignedShort2, System.currentTimeMillis() / 1000);
        }
        throw new UnknownHostException("no record");
    }

    private static void a(OutputStream outputStream, String str) throws IOException {
        for (String str2 : str.split("[.。．｡]")) {
            byte[] bytes = IDN.toASCII(str2).getBytes();
            outputStream.write(bytes.length);
            outputStream.write(bytes, 0, bytes.length);
        }
        outputStream.write(0);
    }

    public static e[] a(byte[] bArr, int i2, String str) throws IOException {
        DataInputStream dataInputStream = new DataInputStream(new ByteArrayInputStream(bArr));
        int unsignedShort = dataInputStream.readUnsignedShort();
        if (unsignedShort == i2) {
            int unsignedShort2 = dataInputStream.readUnsignedShort();
            boolean z2 = ((unsignedShort2 >> 8) & 1) == 1;
            if ((((unsignedShort2 >> 7) & 1) == 1) && z2) {
                int unsignedShort3 = dataInputStream.readUnsignedShort();
                int unsignedShort4 = dataInputStream.readUnsignedShort();
                dataInputStream.readUnsignedShort();
                dataInputStream.readUnsignedShort();
                a(dataInputStream, bArr, unsignedShort3);
                return b(dataInputStream, bArr, unsignedShort4);
            }
            throw new com.tencent.liteav.network.a.a(str, "the dns server cant support recursion ");
        }
        throw new com.tencent.liteav.network.a.a(str, "the answer id " + unsignedShort + " is not match " + i2);
    }

    private static String a(DataInputStream dataInputStream, byte[] bArr) throws IOException {
        int unsignedByte = dataInputStream.readUnsignedByte();
        if ((unsignedByte & 192) == 192) {
            int unsignedByte2 = ((unsignedByte & 63) << 8) + dataInputStream.readUnsignedByte();
            HashSet hashSet = new HashSet();
            hashSet.add(Integer.valueOf(unsignedByte2));
            return a(bArr, unsignedByte2, (HashSet<Integer>) hashSet);
        }
        if (unsignedByte == 0) {
            return "";
        }
        byte[] bArr2 = new byte[unsignedByte];
        dataInputStream.readFully(bArr2);
        String unicode = IDN.toUnicode(new String(bArr2));
        String strA = a(dataInputStream, bArr);
        if (strA.length() <= 0) {
            return unicode;
        }
        return unicode + StrPool.DOT + strA;
    }

    private static String a(byte[] bArr, int i2, HashSet<Integer> hashSet) throws IOException {
        int i3 = bArr[i2] & 255;
        if ((i3 & 192) == 192) {
            int i4 = ((i3 & 63) << 8) + (bArr[i2 + 1] & 255);
            if (!hashSet.contains(Integer.valueOf(i4))) {
                hashSet.add(Integer.valueOf(i4));
                return a(bArr, i4, hashSet);
            }
            throw new com.tencent.liteav.network.a.a("", "Cyclic offsets detected.");
        }
        if (i3 == 0) {
            return "";
        }
        int i5 = i2 + 1;
        String str = new String(bArr, i5, i3);
        String strA = a(bArr, i5 + i3, hashSet);
        if (strA.length() <= 0) {
            return str;
        }
        return str + StrPool.DOT + strA;
    }

    private static void a(DataInputStream dataInputStream, byte[] bArr, int i2) throws IOException {
        while (true) {
            int i3 = i2 - 1;
            if (i2 <= 0) {
                return;
            }
            a(dataInputStream, bArr);
            dataInputStream.readUnsignedShort();
            dataInputStream.readUnsignedShort();
            i2 = i3;
        }
    }
}
