package net.lingala.zip4j.crypto.PBKDF2;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/* loaded from: classes9.dex */
public class MacBasedPRF implements PRF {
    protected int hLen;
    protected Mac mac;
    protected String macAlgorithm;

    public MacBasedPRF(String str) throws NoSuchAlgorithmException {
        this.macAlgorithm = str;
        try {
            Mac mac = Mac.getInstance(str);
            this.mac = mac;
            this.hLen = mac.getMacLength();
        } catch (NoSuchAlgorithmException e2) {
            throw new RuntimeException(e2);
        }
    }

    @Override // net.lingala.zip4j.crypto.PBKDF2.PRF
    public byte[] doFinal(byte[] bArr) {
        return this.mac.doFinal(bArr);
    }

    @Override // net.lingala.zip4j.crypto.PBKDF2.PRF
    public int getHLen() {
        return this.hLen;
    }

    @Override // net.lingala.zip4j.crypto.PBKDF2.PRF
    public void init(byte[] bArr) throws InvalidKeyException {
        try {
            this.mac.init(new SecretKeySpec(bArr, this.macAlgorithm));
        } catch (InvalidKeyException e2) {
            throw new RuntimeException(e2);
        }
    }

    public void update(byte[] bArr) throws IllegalStateException {
        try {
            this.mac.update(bArr);
        } catch (IllegalStateException e2) {
            throw new RuntimeException(e2);
        }
    }

    public byte[] doFinal() {
        return this.mac.doFinal();
    }

    public void update(byte[] bArr, int i2, int i3) throws IllegalStateException {
        try {
            this.mac.update(bArr, i2, i3);
        } catch (IllegalStateException e2) {
            throw new RuntimeException(e2);
        }
    }

    public MacBasedPRF(String str, String str2) throws NoSuchAlgorithmException, NoSuchProviderException {
        this.macAlgorithm = str;
        try {
            Mac mac = Mac.getInstance(str, str2);
            this.mac = mac;
            this.hLen = mac.getMacLength();
        } catch (NoSuchAlgorithmException e2) {
            throw new RuntimeException(e2);
        } catch (NoSuchProviderException e3) {
            throw new RuntimeException(e3);
        }
    }
}
