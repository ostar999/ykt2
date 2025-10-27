package net.lingala.zip4j.crypto;

import com.google.common.base.Ascii;
import net.lingala.zip4j.crypto.engine.ZipCryptoEngine;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.FileHeader;

/* loaded from: classes9.dex */
public class StandardDecrypter implements IDecrypter {
    private byte[] crc = new byte[4];
    private FileHeader fileHeader;
    private ZipCryptoEngine zipCryptoEngine;

    public StandardDecrypter(FileHeader fileHeader, byte[] bArr) throws ZipException {
        if (fileHeader == null) {
            throw new ZipException("one of more of the input parameters were null in StandardDecryptor");
        }
        this.fileHeader = fileHeader;
        this.zipCryptoEngine = new ZipCryptoEngine();
        init(bArr);
    }

    @Override // net.lingala.zip4j.crypto.IDecrypter
    public int decryptData(byte[] bArr) throws ZipException {
        return decryptData(bArr, 0, bArr.length);
    }

    public void init(byte[] bArr) throws ZipException {
        byte[] crcBuff = this.fileHeader.getCrcBuff();
        byte[] bArr2 = this.crc;
        bArr2[3] = (byte) (crcBuff[3] & 255);
        byte b3 = crcBuff[3];
        byte b4 = (byte) ((b3 >> 8) & 255);
        bArr2[2] = b4;
        byte b5 = (byte) ((b3 >> 16) & 255);
        bArr2[1] = b5;
        byte b6 = (byte) ((b3 >> Ascii.CAN) & 255);
        int i2 = 0;
        bArr2[0] = b6;
        if (b4 > 0 || b5 > 0 || b6 > 0) {
            throw new IllegalStateException("Invalid CRC in File Header");
        }
        if (this.fileHeader.getPassword() == null || this.fileHeader.getPassword().length <= 0) {
            throw new ZipException("Wrong password!", 5);
        }
        this.zipCryptoEngine.initKeys(this.fileHeader.getPassword());
        try {
            byte b7 = bArr[0];
            while (i2 < 12) {
                ZipCryptoEngine zipCryptoEngine = this.zipCryptoEngine;
                zipCryptoEngine.updateKeys((byte) (zipCryptoEngine.decryptByte() ^ b7));
                i2++;
                if (i2 != 12) {
                    b7 = bArr[i2];
                }
            }
        } catch (Exception e2) {
            throw new ZipException(e2);
        }
    }

    @Override // net.lingala.zip4j.crypto.IDecrypter
    public int decryptData(byte[] bArr, int i2, int i3) throws ZipException {
        if (i2 < 0 || i3 < 0) {
            throw new ZipException("one of the input parameters were null in standard decrpyt data");
        }
        for (int i4 = i2; i4 < i2 + i3; i4++) {
            try {
                byte bDecryptByte = (byte) (((bArr[i4] & 255) ^ this.zipCryptoEngine.decryptByte()) & 255);
                this.zipCryptoEngine.updateKeys(bDecryptByte);
                bArr[i4] = bDecryptByte;
            } catch (Exception e2) {
                throw new ZipException(e2);
            }
        }
        return i3;
    }
}
