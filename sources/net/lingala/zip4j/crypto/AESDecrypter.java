package net.lingala.zip4j.crypto;

import java.security.InvalidKeyException;
import java.util.Arrays;
import net.lingala.zip4j.crypto.PBKDF2.MacBasedPRF;
import net.lingala.zip4j.crypto.PBKDF2.PBKDF2Engine;
import net.lingala.zip4j.crypto.PBKDF2.PBKDF2Parameters;
import net.lingala.zip4j.crypto.engine.AESEngine;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.AESExtraDataRecord;
import net.lingala.zip4j.model.LocalFileHeader;
import net.lingala.zip4j.util.Raw;

/* loaded from: classes9.dex */
public class AESDecrypter implements IDecrypter {
    private int KEY_LENGTH;
    private int MAC_LENGTH;
    private int SALT_LENGTH;
    private AESEngine aesEngine;
    private byte[] aesKey;
    private byte[] counterBlock;
    private byte[] derivedPasswordVerifier;
    private byte[] iv;
    private LocalFileHeader localFileHeader;
    private MacBasedPRF mac;
    private byte[] macKey;
    private byte[] storedMac;
    private final int PASSWORD_VERIFIER_LENGTH = 2;
    private int nonce = 1;
    private int loopCount = 0;

    public AESDecrypter(LocalFileHeader localFileHeader, byte[] bArr, byte[] bArr2) throws ZipException, InvalidKeyException {
        if (localFileHeader == null) {
            throw new ZipException("one of the input parameters is null in AESDecryptor Constructor");
        }
        this.localFileHeader = localFileHeader;
        this.storedMac = null;
        this.iv = new byte[16];
        this.counterBlock = new byte[16];
        init(bArr, bArr2);
    }

    private byte[] deriveKey(byte[] bArr, char[] cArr) throws ZipException {
        try {
            return new PBKDF2Engine(new PBKDF2Parameters("HmacSHA1", "ISO-8859-1", bArr, 1000)).deriveKey(cArr, this.KEY_LENGTH + this.MAC_LENGTH + 2);
        } catch (Exception e2) {
            throw new ZipException(e2);
        }
    }

    private void init(byte[] bArr, byte[] bArr2) throws ZipException, InvalidKeyException {
        LocalFileHeader localFileHeader = this.localFileHeader;
        if (localFileHeader == null) {
            throw new ZipException("invalid file header in init method of AESDecryptor");
        }
        AESExtraDataRecord aesExtraDataRecord = localFileHeader.getAesExtraDataRecord();
        if (aesExtraDataRecord == null) {
            throw new ZipException("invalid aes extra data record - in init method of AESDecryptor");
        }
        int aesStrength = aesExtraDataRecord.getAesStrength();
        if (aesStrength == 1) {
            this.KEY_LENGTH = 16;
            this.MAC_LENGTH = 16;
            this.SALT_LENGTH = 8;
        } else if (aesStrength == 2) {
            this.KEY_LENGTH = 24;
            this.MAC_LENGTH = 24;
            this.SALT_LENGTH = 12;
        } else {
            if (aesStrength != 3) {
                throw new ZipException("invalid aes key strength for file: " + this.localFileHeader.getFileName());
            }
            this.KEY_LENGTH = 32;
            this.MAC_LENGTH = 32;
            this.SALT_LENGTH = 16;
        }
        if (this.localFileHeader.getPassword() == null || this.localFileHeader.getPassword().length <= 0) {
            throw new ZipException("empty or null password provided for AES Decryptor");
        }
        byte[] bArrDeriveKey = deriveKey(bArr, this.localFileHeader.getPassword());
        if (bArrDeriveKey != null) {
            int length = bArrDeriveKey.length;
            int i2 = this.KEY_LENGTH;
            int i3 = this.MAC_LENGTH;
            if (length == i2 + i3 + 2) {
                byte[] bArr3 = new byte[i2];
                this.aesKey = bArr3;
                this.macKey = new byte[i3];
                this.derivedPasswordVerifier = new byte[2];
                System.arraycopy(bArrDeriveKey, 0, bArr3, 0, i2);
                System.arraycopy(bArrDeriveKey, this.KEY_LENGTH, this.macKey, 0, this.MAC_LENGTH);
                System.arraycopy(bArrDeriveKey, this.KEY_LENGTH + this.MAC_LENGTH, this.derivedPasswordVerifier, 0, 2);
                byte[] bArr4 = this.derivedPasswordVerifier;
                if (bArr4 == null) {
                    throw new ZipException("invalid derived password verifier for AES");
                }
                if (!Arrays.equals(bArr2, bArr4)) {
                    throw new ZipException("Wrong Password for file: " + this.localFileHeader.getFileName(), 5);
                }
                this.aesEngine = new AESEngine(this.aesKey);
                MacBasedPRF macBasedPRF = new MacBasedPRF("HmacSHA1");
                this.mac = macBasedPRF;
                macBasedPRF.init(this.macKey);
                return;
            }
        }
        throw new ZipException("invalid derived key");
    }

    @Override // net.lingala.zip4j.crypto.IDecrypter
    public int decryptData(byte[] bArr, int i2, int i3) throws ZipException {
        if (this.aesEngine == null) {
            throw new ZipException("AES not initialized properly");
        }
        int i4 = i2;
        while (true) {
            int i5 = i2 + i3;
            if (i4 >= i5) {
                return i3;
            }
            int i6 = i4 + 16;
            int i7 = i6 <= i5 ? 16 : i5 - i4;
            try {
                this.loopCount = i7;
                this.mac.update(bArr, i4, i7);
                Raw.prepareBuffAESIVBytes(this.iv, this.nonce, 16);
                this.aesEngine.processBlock(this.iv, this.counterBlock);
                for (int i8 = 0; i8 < this.loopCount; i8++) {
                    int i9 = i4 + i8;
                    bArr[i9] = (byte) (bArr[i9] ^ this.counterBlock[i8]);
                }
                this.nonce++;
                i4 = i6;
            } catch (ZipException e2) {
                throw e2;
            } catch (Exception e3) {
                throw new ZipException(e3);
            }
        }
    }

    public byte[] getCalculatedAuthenticationBytes() {
        return this.mac.doFinal();
    }

    public int getPasswordVerifierLength() {
        return 2;
    }

    public int getSaltLength() {
        return this.SALT_LENGTH;
    }

    public byte[] getStoredMac() {
        return this.storedMac;
    }

    public void setStoredMac(byte[] bArr) {
        this.storedMac = bArr;
    }

    @Override // net.lingala.zip4j.crypto.IDecrypter
    public int decryptData(byte[] bArr) throws ZipException {
        return decryptData(bArr, 0, bArr.length);
    }
}
