package org.bouncycastle.jce.provider;

import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Hashtable;
import javax.crypto.KeyAgreementSpi;
import javax.crypto.SecretKey;
import javax.crypto.ShortBufferException;
import javax.crypto.interfaces.DHPrivateKey;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DHParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.bouncycastle.crypto.params.DESParameters;
import org.bouncycastle.util.Strings;

/* loaded from: classes9.dex */
public class JCEDHKeyAgreement extends KeyAgreementSpi {
    private static final Hashtable algorithms;

    /* renamed from: g, reason: collision with root package name */
    private BigInteger f27887g;

    /* renamed from: p, reason: collision with root package name */
    private BigInteger f27888p;
    private BigInteger result;

    /* renamed from: x, reason: collision with root package name */
    private BigInteger f27889x;

    static {
        Hashtable hashtable = new Hashtable();
        algorithms = hashtable;
        Integer num = new Integer(64);
        Integer num2 = new Integer(192);
        Integer num3 = new Integer(128);
        Integer num4 = new Integer(256);
        hashtable.put("DES", num);
        hashtable.put("DESEDE", num2);
        hashtable.put("BLOWFISH", num3);
        hashtable.put("AES", num4);
    }

    private byte[] bigIntToBytes(BigInteger bigInteger) {
        byte[] byteArray = bigInteger.toByteArray();
        if (byteArray[0] != 0) {
            return byteArray;
        }
        int length = byteArray.length - 1;
        byte[] bArr = new byte[length];
        System.arraycopy(byteArray, 1, bArr, 0, length);
        return bArr;
    }

    @Override // javax.crypto.KeyAgreementSpi
    public Key engineDoPhase(Key key, boolean z2) throws IllegalStateException, InvalidKeyException {
        if (this.f27889x == null) {
            throw new IllegalStateException("Diffie-Hellman not initialised.");
        }
        if (!(key instanceof DHPublicKey)) {
            throw new InvalidKeyException("DHKeyAgreement doPhase requires DHPublicKey");
        }
        DHPublicKey dHPublicKey = (DHPublicKey) key;
        if (!dHPublicKey.getParams().getG().equals(this.f27887g) || !dHPublicKey.getParams().getP().equals(this.f27888p)) {
            throw new InvalidKeyException("DHPublicKey not for this KeyAgreement!");
        }
        if (z2) {
            this.result = dHPublicKey.getY().modPow(this.f27889x, this.f27888p);
            return null;
        }
        BigInteger bigIntegerModPow = dHPublicKey.getY().modPow(this.f27889x, this.f27888p);
        this.result = bigIntegerModPow;
        return new JCEDHPublicKey(bigIntegerModPow, dHPublicKey.getParams());
    }

    @Override // javax.crypto.KeyAgreementSpi
    public int engineGenerateSecret(byte[] bArr, int i2) throws IllegalStateException, ShortBufferException {
        if (this.f27889x == null) {
            throw new IllegalStateException("Diffie-Hellman not initialised.");
        }
        byte[] bArrBigIntToBytes = bigIntToBytes(this.result);
        if (bArr.length - i2 < bArrBigIntToBytes.length) {
            throw new ShortBufferException("DHKeyAgreement - buffer too short");
        }
        System.arraycopy(bArrBigIntToBytes, 0, bArr, i2, bArrBigIntToBytes.length);
        return bArrBigIntToBytes.length;
    }

    @Override // javax.crypto.KeyAgreementSpi
    public SecretKey engineGenerateSecret(String str) {
        if (this.f27889x == null) {
            throw new IllegalStateException("Diffie-Hellman not initialised.");
        }
        String upperCase = Strings.toUpperCase(str);
        byte[] bArrBigIntToBytes = bigIntToBytes(this.result);
        Hashtable hashtable = algorithms;
        if (!hashtable.containsKey(upperCase)) {
            return new SecretKeySpec(bArrBigIntToBytes, str);
        }
        int iIntValue = ((Integer) hashtable.get(upperCase)).intValue() / 8;
        byte[] bArr = new byte[iIntValue];
        System.arraycopy(bArrBigIntToBytes, 0, bArr, 0, iIntValue);
        if (upperCase.startsWith("DES")) {
            DESParameters.setOddParity(bArr);
        }
        return new SecretKeySpec(bArr, str);
    }

    @Override // javax.crypto.KeyAgreementSpi
    public byte[] engineGenerateSecret() throws IllegalStateException {
        if (this.f27889x != null) {
            return bigIntToBytes(this.result);
        }
        throw new IllegalStateException("Diffie-Hellman not initialised.");
    }

    @Override // javax.crypto.KeyAgreementSpi
    public void engineInit(Key key, SecureRandom secureRandom) throws InvalidKeyException {
        if (!(key instanceof DHPrivateKey)) {
            throw new InvalidKeyException("DHKeyAgreement requires DHPrivateKey");
        }
        DHPrivateKey dHPrivateKey = (DHPrivateKey) key;
        this.f27888p = dHPrivateKey.getParams().getP();
        this.f27887g = dHPrivateKey.getParams().getG();
        BigInteger x2 = dHPrivateKey.getX();
        this.result = x2;
        this.f27889x = x2;
    }

    @Override // javax.crypto.KeyAgreementSpi
    public void engineInit(Key key, AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) throws InvalidKeyException, InvalidAlgorithmParameterException {
        DHParameterSpec params;
        if (!(key instanceof DHPrivateKey)) {
            throw new InvalidKeyException("DHKeyAgreement requires DHPrivateKey for initialisation");
        }
        DHPrivateKey dHPrivateKey = (DHPrivateKey) key;
        if (algorithmParameterSpec == null) {
            this.f27888p = dHPrivateKey.getParams().getP();
            params = dHPrivateKey.getParams();
        } else {
            if (!(algorithmParameterSpec instanceof DHParameterSpec)) {
                throw new InvalidAlgorithmParameterException("DHKeyAgreement only accepts DHParameterSpec");
            }
            params = (DHParameterSpec) algorithmParameterSpec;
            this.f27888p = params.getP();
        }
        this.f27887g = params.getG();
        BigInteger x2 = dHPrivateKey.getX();
        this.result = x2;
        this.f27889x = x2;
    }
}
