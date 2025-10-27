package org.bouncycastle.crypto.tls;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.bouncycastle.asn1.x509.KeyUsage;
import org.bouncycastle.asn1.x509.X509CertificateStructure;
import org.bouncycastle.asn1.x509.X509Extension;
import org.bouncycastle.asn1.x509.X509Extensions;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.digests.MD5Digest;
import org.bouncycastle.crypto.digests.SHA1Digest;
import org.bouncycastle.crypto.macs.HMac;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.util.Strings;
import org.bouncycastle.util.io.Streams;

/* loaded from: classes9.dex */
public class TlsUtils {
    public static byte[] PRF(byte[] bArr, String str, byte[] bArr2, int i2) {
        byte[] byteArray = Strings.toByteArray(str);
        int length = (bArr.length + 1) / 2;
        byte[] bArr3 = new byte[length];
        byte[] bArr4 = new byte[length];
        System.arraycopy(bArr, 0, bArr3, 0, length);
        System.arraycopy(bArr, bArr.length - length, bArr4, 0, length);
        byte[] bArrConcat = concat(byteArray, bArr2);
        byte[] bArr5 = new byte[i2];
        byte[] bArr6 = new byte[i2];
        hmac_hash(new MD5Digest(), bArr3, bArrConcat, bArr6);
        hmac_hash(new SHA1Digest(), bArr4, bArrConcat, bArr5);
        for (int i3 = 0; i3 < i2; i3++) {
            bArr5[i3] = (byte) (bArr5[i3] ^ bArr6[i3]);
        }
        return bArr5;
    }

    public static void checkVersion(InputStream inputStream, TlsProtocolHandler tlsProtocolHandler) throws IOException {
        int i2 = inputStream.read();
        int i3 = inputStream.read();
        if (i2 != 3 || i3 != 1) {
            throw new TlsFatalAlert((short) 70);
        }
    }

    public static void checkVersion(byte[] bArr, TlsProtocolHandler tlsProtocolHandler) throws IOException {
        if (bArr[0] != 3 || bArr[1] != 1) {
            throw new TlsFatalAlert((short) 70);
        }
    }

    public static byte[] concat(byte[] bArr, byte[] bArr2) {
        byte[] bArr3 = new byte[bArr.length + bArr2.length];
        System.arraycopy(bArr, 0, bArr3, 0, bArr.length);
        System.arraycopy(bArr2, 0, bArr3, bArr.length, bArr2.length);
        return bArr3;
    }

    private static void hmac_hash(Digest digest, byte[] bArr, byte[] bArr2, byte[] bArr3) {
        HMac hMac = new HMac(digest);
        KeyParameter keyParameter = new KeyParameter(bArr);
        int digestSize = digest.getDigestSize();
        int length = ((bArr3.length + digestSize) - 1) / digestSize;
        int macSize = hMac.getMacSize();
        byte[] bArr4 = new byte[macSize];
        byte[] bArr5 = new byte[hMac.getMacSize()];
        byte[] bArr6 = bArr2;
        int i2 = 0;
        while (i2 < length) {
            hMac.init(keyParameter);
            hMac.update(bArr6, 0, bArr6.length);
            hMac.doFinal(bArr4, 0);
            hMac.init(keyParameter);
            hMac.update(bArr4, 0, macSize);
            hMac.update(bArr2, 0, bArr2.length);
            hMac.doFinal(bArr5, 0);
            int i3 = digestSize * i2;
            System.arraycopy(bArr5, 0, bArr3, i3, Math.min(digestSize, bArr3.length - i3));
            i2++;
            bArr6 = bArr4;
        }
    }

    public static void readFully(byte[] bArr, InputStream inputStream) throws IOException {
        if (Streams.readFully(inputStream, bArr) != bArr.length) {
            throw new EOFException();
        }
    }

    public static byte[] readOpaque16(InputStream inputStream) throws IOException {
        byte[] bArr = new byte[readUint16(inputStream)];
        readFully(bArr, inputStream);
        return bArr;
    }

    public static byte[] readOpaque8(InputStream inputStream) throws IOException {
        byte[] bArr = new byte[readUint8(inputStream)];
        readFully(bArr, inputStream);
        return bArr;
    }

    public static int readUint16(InputStream inputStream) throws IOException {
        int i2 = inputStream.read();
        int i3 = inputStream.read();
        if ((i2 | i3) >= 0) {
            return i3 | (i2 << 8);
        }
        throw new EOFException();
    }

    public static int readUint24(InputStream inputStream) throws IOException {
        int i2 = inputStream.read();
        int i3 = inputStream.read();
        int i4 = inputStream.read();
        if ((i2 | i3 | i4) >= 0) {
            return i4 | (i2 << 16) | (i3 << 8);
        }
        throw new EOFException();
    }

    public static long readUint32(InputStream inputStream) throws IOException {
        int i2 = inputStream.read();
        int i3 = inputStream.read();
        int i4 = inputStream.read();
        int i5 = inputStream.read();
        if ((i2 | i3 | i4 | i5) < 0) {
            throw new EOFException();
        }
        return (i3 << 16) | (i2 << 24) | (i4 << 8) | i5;
    }

    public static short readUint8(InputStream inputStream) throws IOException {
        int i2 = inputStream.read();
        if (i2 != -1) {
            return (short) i2;
        }
        throw new EOFException();
    }

    public static void validateKeyUsage(X509CertificateStructure x509CertificateStructure, int i2) throws IOException {
        X509Extension extension;
        X509Extensions extensions = x509CertificateStructure.getTBSCertificate().getExtensions();
        if (extensions != null && (extension = extensions.getExtension(X509Extension.keyUsage)) != null && (KeyUsage.getInstance(extension).getBytes()[0] & 255 & i2) != i2) {
            throw new TlsFatalAlert((short) 46);
        }
    }

    public static void writeGMTUnixTime(byte[] bArr, int i2) {
        int iCurrentTimeMillis = (int) (System.currentTimeMillis() / 1000);
        bArr[i2] = (byte) (iCurrentTimeMillis >> 24);
        bArr[i2 + 1] = (byte) (iCurrentTimeMillis >> 16);
        bArr[i2 + 2] = (byte) (iCurrentTimeMillis >> 8);
        bArr[i2 + 3] = (byte) iCurrentTimeMillis;
    }

    public static void writeOpaque16(byte[] bArr, OutputStream outputStream) throws IOException {
        writeUint16(bArr.length, outputStream);
        outputStream.write(bArr);
    }

    public static void writeOpaque24(byte[] bArr, OutputStream outputStream) throws IOException {
        writeUint24(bArr.length, outputStream);
        outputStream.write(bArr);
    }

    public static void writeOpaque8(byte[] bArr, OutputStream outputStream) throws IOException {
        writeUint8((short) bArr.length, outputStream);
        outputStream.write(bArr);
    }

    public static void writeUint16(int i2, OutputStream outputStream) throws IOException {
        outputStream.write(i2 >> 8);
        outputStream.write(i2);
    }

    public static void writeUint16(int i2, byte[] bArr, int i3) {
        bArr[i3] = (byte) (i2 >> 8);
        bArr[i3 + 1] = (byte) i2;
    }

    public static void writeUint16Array(int[] iArr, OutputStream outputStream) throws IOException {
        for (int i2 : iArr) {
            writeUint16(i2, outputStream);
        }
    }

    public static void writeUint24(int i2, OutputStream outputStream) throws IOException {
        outputStream.write(i2 >> 16);
        outputStream.write(i2 >> 8);
        outputStream.write(i2);
    }

    public static void writeUint24(int i2, byte[] bArr, int i3) {
        bArr[i3] = (byte) (i2 >> 16);
        bArr[i3 + 1] = (byte) (i2 >> 8);
        bArr[i3 + 2] = (byte) i2;
    }

    public static void writeUint32(long j2, OutputStream outputStream) throws IOException {
        outputStream.write((int) (j2 >> 24));
        outputStream.write((int) (j2 >> 16));
        outputStream.write((int) (j2 >> 8));
        outputStream.write((int) j2);
    }

    public static void writeUint32(long j2, byte[] bArr, int i2) {
        bArr[i2] = (byte) (j2 >> 24);
        bArr[i2 + 1] = (byte) (j2 >> 16);
        bArr[i2 + 2] = (byte) (j2 >> 8);
        bArr[i2 + 3] = (byte) j2;
    }

    public static void writeUint64(long j2, OutputStream outputStream) throws IOException {
        outputStream.write((int) (j2 >> 56));
        outputStream.write((int) (j2 >> 48));
        outputStream.write((int) (j2 >> 40));
        outputStream.write((int) (j2 >> 32));
        outputStream.write((int) (j2 >> 24));
        outputStream.write((int) (j2 >> 16));
        outputStream.write((int) (j2 >> 8));
        outputStream.write((int) j2);
    }

    public static void writeUint64(long j2, byte[] bArr, int i2) {
        bArr[i2] = (byte) (j2 >> 56);
        bArr[i2 + 1] = (byte) (j2 >> 48);
        bArr[i2 + 2] = (byte) (j2 >> 40);
        bArr[i2 + 3] = (byte) (j2 >> 32);
        bArr[i2 + 4] = (byte) (j2 >> 24);
        bArr[i2 + 5] = (byte) (j2 >> 16);
        bArr[i2 + 6] = (byte) (j2 >> 8);
        bArr[i2 + 7] = (byte) j2;
    }

    public static void writeUint8(short s2, OutputStream outputStream) throws IOException {
        outputStream.write(s2);
    }

    public static void writeUint8(short s2, byte[] bArr, int i2) {
        bArr[i2] = (byte) s2;
    }

    public static void writeUint8Array(short[] sArr, OutputStream outputStream) throws IOException {
        for (short s2 : sArr) {
            writeUint8(s2, outputStream);
        }
    }

    public static void writeVersion(OutputStream outputStream) throws IOException {
        outputStream.write(3);
        outputStream.write(1);
    }

    public static void writeVersion(byte[] bArr, int i2) throws IOException {
        bArr[i2] = 3;
        bArr[i2 + 1] = 1;
    }
}
