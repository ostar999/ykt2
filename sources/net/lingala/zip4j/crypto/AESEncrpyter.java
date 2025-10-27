package net.lingala.zip4j.crypto;

import java.security.InvalidKeyException;
import java.util.Random;
import net.lingala.zip4j.crypto.PBKDF2.MacBasedPRF;
import net.lingala.zip4j.crypto.PBKDF2.PBKDF2Engine;
import net.lingala.zip4j.crypto.PBKDF2.PBKDF2Parameters;
import net.lingala.zip4j.crypto.engine.AESEngine;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.util.Raw;

/* loaded from: classes9.dex */
public class AESEncrpyter implements IEncrypter {
    private int KEY_LENGTH;
    private int MAC_LENGTH;
    private int SALT_LENGTH;
    private AESEngine aesEngine;
    private byte[] aesKey;
    private byte[] counterBlock;
    private byte[] derivedPasswordVerifier;
    private boolean finished;
    private byte[] iv;
    private int keyStrength;
    private MacBasedPRF mac;
    private byte[] macKey;
    private char[] password;
    private byte[] saltBytes;
    private final int PASSWORD_VERIFIER_LENGTH = 2;
    private int nonce = 1;
    private int loopCount = 0;

    public AESEncrpyter(char[] cArr, int i2) throws ZipException, InvalidKeyException {
        if (cArr == null || cArr.length == 0) {
            throw new ZipException("input password is empty or null in AES encrypter constructor");
        }
        if (i2 != 1 && i2 != 3) {
            throw new ZipException("Invalid key strength in AES encrypter constructor");
        }
        this.password = cArr;
        this.keyStrength = i2;
        this.finished = false;
        this.counterBlock = new byte[16];
        this.iv = new byte[16];
        init();
    }

    private byte[] deriveKey(byte[] bArr, char[] cArr) throws ZipException {
        try {
            return new PBKDF2Engine(new PBKDF2Parameters("HmacSHA1", "ISO-8859-1", bArr, 1000)).deriveKey(cArr, this.KEY_LENGTH + this.MAC_LENGTH + 2);
        } catch (Exception e2) {
            throw new ZipException(e2);
        }
    }

    private static byte[] generateSalt(int i2) throws ZipException {
        if (i2 != 8 && i2 != 16) {
            throw new ZipException("invalid salt size, cannot generate salt");
        }
        int i3 = i2 == 8 ? 2 : 0;
        if (i2 == 16) {
            i3 = 4;
        }
        byte[] bArr = new byte[i2];
        for (int i4 = 0; i4 < i3; i4++) {
            int iNextInt = new Random().nextInt();
            int i5 = i4 * 4;
            bArr[i5 + 0] = (byte) (iNextInt >> 24);
            bArr[i5 + 1] = (byte) (iNextInt >> 16);
            bArr[i5 + 2] = (byte) (iNextInt >> 8);
            bArr[i5 + 3] = (byte) iNextInt;
        }
        return bArr;
    }

    private void init() throws ZipException, InvalidKeyException {
        int i2 = this.keyStrength;
        if (i2 == 1) {
            this.KEY_LENGTH = 16;
            this.MAC_LENGTH = 16;
            this.SALT_LENGTH = 8;
        } else {
            if (i2 != 3) {
                throw new ZipException("invalid aes key strength, cannot determine key sizes");
            }
            this.KEY_LENGTH = 32;
            this.MAC_LENGTH = 32;
            this.SALT_LENGTH = 16;
        }
        byte[] bArrGenerateSalt = generateSalt(this.SALT_LENGTH);
        this.saltBytes = bArrGenerateSalt;
        byte[] bArrDeriveKey = deriveKey(bArrGenerateSalt, this.password);
        if (bArrDeriveKey != null) {
            int length = bArrDeriveKey.length;
            int i3 = this.KEY_LENGTH;
            int i4 = this.MAC_LENGTH;
            if (length == i3 + i4 + 2) {
                byte[] bArr = new byte[i3];
                this.aesKey = bArr;
                this.macKey = new byte[i4];
                this.derivedPasswordVerifier = new byte[2];
                System.arraycopy(bArrDeriveKey, 0, bArr, 0, i3);
                System.arraycopy(bArrDeriveKey, this.KEY_LENGTH, this.macKey, 0, this.MAC_LENGTH);
                System.arraycopy(bArrDeriveKey, this.KEY_LENGTH + this.MAC_LENGTH, this.derivedPasswordVerifier, 0, 2);
                this.aesEngine = new AESEngine(this.aesKey);
                MacBasedPRF macBasedPRF = new MacBasedPRF("HmacSHA1");
                this.mac = macBasedPRF;
                macBasedPRF.init(this.macKey);
                return;
            }
        }
        throw new ZipException("invalid key generated, cannot decrypt file");
    }

    @Override // net.lingala.zip4j.crypto.IEncrypter
    public int encryptData(byte[] bArr) throws ZipException {
        if (bArr != null) {
            return encryptData(bArr, 0, bArr.length);
        }
        throw new ZipException("input bytes are null, cannot perform AES encrpytion");
    }

    public byte[] getDerivedPasswordVerifier() {
        return this.derivedPasswordVerifier;
    }

    public byte[] getFinalMac() {
        byte[] bArr = new byte[10];
        System.arraycopy(this.mac.doFinal(), 0, bArr, 0, 10);
        return bArr;
    }

    public int getPasswordVeriifierLength() {
        return 2;
    }

    public byte[] getSaltBytes() {
        return this.saltBytes;
    }

    public int getSaltLength() {
        return this.SALT_LENGTH;
    }

    public void setDerivedPasswordVerifier(byte[] bArr) {
        this.derivedPasswordVerifier = bArr;
    }

    public void setSaltBytes(byte[] bArr) {
        this.saltBytes = bArr;
    }

    @Override // net.lingala.zip4j.crypto.IEncrypter
    public int encryptData(byte[] bArr, int i2, int i3) throws ZipException, IllegalStateException {
        int i4;
        if (!this.finished) {
            if (i3 % 16 != 0) {
                this.finished = true;
            }
            int i5 = i2;
            while (true) {
                int i6 = i2 + i3;
                if (i5 >= i6) {
                    return i3;
                }
                int i7 = i5 + 16;
                this.loopCount = i7 <= i6 ? 16 : i6 - i5;
                Raw.prepareBuffAESIVBytes(this.iv, this.nonce, 16);
                this.aesEngine.processBlock(this.iv, this.counterBlock);
                int i8 = 0;
                while (true) {
                    i4 = this.loopCount;
                    if (i8 < i4) {
                        int i9 = i5 + i8;
                        bArr[i9] = (byte) (bArr[i9] ^ this.counterBlock[i8]);
                        i8++;
                    }
                }
                this.mac.update(bArr, i5, i4);
                this.nonce++;
                i5 = i7;
            }
        } else {
            throw new ZipException("AES Encrypter is in finished state (A non 16 byte block has already been passed to encrypter)");
        }
    }
}
