package cn.hutool.crypto;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.Cipher;

/* loaded from: classes.dex */
public class CipherWrapper {
    private final Cipher cipher;
    private AlgorithmParameterSpec params;
    private SecureRandom random;

    public CipherWrapper(String str) {
        this(SecureUtil.createCipher(str));
    }

    public Cipher getCipher() {
        return this.cipher;
    }

    public AlgorithmParameterSpec getParams() {
        return this.params;
    }

    public CipherWrapper initMode(int i2, Key key) throws InvalidKeyException, InvalidAlgorithmParameterException {
        Cipher cipher = this.cipher;
        AlgorithmParameterSpec algorithmParameterSpec = this.params;
        SecureRandom secureRandom = this.random;
        if (algorithmParameterSpec != null) {
            if (secureRandom != null) {
                cipher.init(i2, key, algorithmParameterSpec, secureRandom);
            } else {
                cipher.init(i2, key, algorithmParameterSpec);
            }
        } else if (secureRandom != null) {
            cipher.init(i2, key, secureRandom);
        } else {
            cipher.init(i2, key);
        }
        return this;
    }

    public CipherWrapper setParams(AlgorithmParameterSpec algorithmParameterSpec) {
        this.params = algorithmParameterSpec;
        return this;
    }

    public CipherWrapper setRandom(SecureRandom secureRandom) {
        this.random = secureRandom;
        return this;
    }

    public CipherWrapper(Cipher cipher) {
        this.cipher = cipher;
    }
}
