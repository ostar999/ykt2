package cn.hutool.crypto.digest;

import cn.hutool.crypto.digest.mac.Mac;
import cn.hutool.crypto.digest.mac.MacEngine;
import cn.hutool.crypto.digest.mac.MacEngineFactory;
import java.security.Key;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/* loaded from: classes.dex */
public class HMac extends Mac {
    private static final long serialVersionUID = 1;

    public HMac(HmacAlgorithm hmacAlgorithm) {
        this(hmacAlgorithm, (Key) null);
    }

    public HMac(HmacAlgorithm hmacAlgorithm, byte[] bArr) {
        this(hmacAlgorithm.getValue(), bArr);
    }

    public HMac(HmacAlgorithm hmacAlgorithm, Key key) {
        this(hmacAlgorithm.getValue(), key);
    }

    public HMac(String str, byte[] bArr) {
        this(str, new SecretKeySpec(bArr, str));
    }

    public HMac(String str, Key key) {
        this(str, key, null);
    }

    public HMac(String str, Key key, AlgorithmParameterSpec algorithmParameterSpec) {
        this(MacEngineFactory.createEngine(str, key, algorithmParameterSpec));
    }

    public HMac(MacEngine macEngine) {
        super(macEngine);
    }
}
