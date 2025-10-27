package cn.hutool.crypto.digest.mac;

import cn.hutool.crypto.CryptoException;
import cn.hutool.crypto.SecureUtil;
import java.io.InputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import m0.a;

/* loaded from: classes.dex */
public class DefaultHMacEngine implements MacEngine {
    private javax.crypto.Mac mac;

    public DefaultHMacEngine(String str, byte[] bArr) {
        this(str, bArr == null ? null : new SecretKeySpec(bArr, str));
    }

    @Override // cn.hutool.crypto.digest.mac.MacEngine
    public /* synthetic */ byte[] digest(InputStream inputStream, int i2) {
        return a.a(this, inputStream, i2);
    }

    @Override // cn.hutool.crypto.digest.mac.MacEngine
    public byte[] doFinal() {
        return this.mac.doFinal();
    }

    @Override // cn.hutool.crypto.digest.mac.MacEngine
    public String getAlgorithm() {
        return this.mac.getAlgorithm();
    }

    public javax.crypto.Mac getMac() {
        return this.mac;
    }

    @Override // cn.hutool.crypto.digest.mac.MacEngine
    public int getMacLength() {
        return this.mac.getMacLength();
    }

    public DefaultHMacEngine init(String str, byte[] bArr) {
        return init(str, bArr == null ? null : new SecretKeySpec(bArr, str));
    }

    @Override // cn.hutool.crypto.digest.mac.MacEngine
    public void reset() {
        this.mac.reset();
    }

    @Override // cn.hutool.crypto.digest.mac.MacEngine
    public void update(byte[] bArr) throws IllegalStateException {
        this.mac.update(bArr);
    }

    public DefaultHMacEngine(String str, Key key) {
        this(str, key, null);
    }

    public DefaultHMacEngine init(String str, Key key) {
        return init(str, key, null);
    }

    @Override // cn.hutool.crypto.digest.mac.MacEngine
    public void update(byte[] bArr, int i2, int i3) throws IllegalStateException {
        this.mac.update(bArr, i2, i3);
    }

    public DefaultHMacEngine(String str, Key key, AlgorithmParameterSpec algorithmParameterSpec) throws InvalidKeyException, InvalidAlgorithmParameterException {
        init(str, key, algorithmParameterSpec);
    }

    public DefaultHMacEngine init(String str, Key key, AlgorithmParameterSpec algorithmParameterSpec) throws InvalidKeyException, InvalidAlgorithmParameterException {
        try {
            this.mac = SecureUtil.createMac(str);
            if (key == null) {
                key = SecureUtil.generateKey(str);
            }
            if (algorithmParameterSpec != null) {
                this.mac.init(key, algorithmParameterSpec);
            } else {
                this.mac.init(key);
            }
            return this;
        } catch (Exception e2) {
            throw new CryptoException(e2);
        }
    }
}
