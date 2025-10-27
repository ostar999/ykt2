package net.lingala.zip4j.crypto;

import java.util.Random;
import net.lingala.zip4j.crypto.engine.ZipCryptoEngine;
import net.lingala.zip4j.exception.ZipException;

/* loaded from: classes9.dex */
public class StandardEncrypter implements IEncrypter {
    private byte[] headerBytes;
    private ZipCryptoEngine zipCryptoEngine;

    public StandardEncrypter(char[] cArr, int i2) throws ZipException {
        if (cArr == null || cArr.length <= 0) {
            throw new ZipException("input password is null or empty in standard encrpyter constructor");
        }
        this.zipCryptoEngine = new ZipCryptoEngine();
        this.headerBytes = new byte[12];
        init(cArr, i2);
    }

    private void init(char[] cArr, int i2) throws ZipException {
        if (cArr == null || cArr.length <= 0) {
            throw new ZipException("input password is null or empty, cannot initialize standard encrypter");
        }
        this.zipCryptoEngine.initKeys(cArr);
        this.headerBytes = generateRandomBytes(12);
        this.zipCryptoEngine.initKeys(cArr);
        byte[] bArr = this.headerBytes;
        bArr[11] = (byte) (i2 >>> 24);
        bArr[10] = (byte) (i2 >>> 16);
        if (bArr.length < 12) {
            throw new ZipException("invalid header bytes generated, cannot perform standard encryption");
        }
        encryptData(bArr);
    }

    public byte encryptByte(byte b3) {
        byte bDecryptByte = (byte) ((this.zipCryptoEngine.decryptByte() & 255) ^ b3);
        this.zipCryptoEngine.updateKeys(b3);
        return bDecryptByte;
    }

    @Override // net.lingala.zip4j.crypto.IEncrypter
    public int encryptData(byte[] bArr) throws ZipException {
        bArr.getClass();
        return encryptData(bArr, 0, bArr.length);
    }

    public byte[] generateRandomBytes(int i2) throws ZipException {
        if (i2 <= 0) {
            throw new ZipException("size is either 0 or less than 0, cannot generate header for standard encryptor");
        }
        byte[] bArr = new byte[i2];
        Random random = new Random();
        for (int i3 = 0; i3 < i2; i3++) {
            bArr[i3] = encryptByte((byte) random.nextInt(256));
        }
        return bArr;
    }

    public byte[] getHeaderBytes() {
        return this.headerBytes;
    }

    @Override // net.lingala.zip4j.crypto.IEncrypter
    public int encryptData(byte[] bArr, int i2, int i3) throws ZipException {
        if (i3 < 0) {
            throw new ZipException("invalid length specified to decrpyt data");
        }
        for (int i4 = i2; i4 < i2 + i3; i4++) {
            try {
                bArr[i4] = encryptByte(bArr[i4]);
            } catch (Exception e2) {
                throw new ZipException(e2);
            }
        }
        return i3;
    }
}
