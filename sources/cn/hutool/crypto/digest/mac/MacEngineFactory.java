package cn.hutool.crypto.digest.mac;

import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.digest.HmacAlgorithm;
import java.security.Key;
import java.security.spec.AlgorithmParameterSpec;

/* loaded from: classes.dex */
public class MacEngineFactory {
    public static MacEngine createEngine(String str, Key key) {
        return createEngine(str, key, null);
    }

    public static MacEngine createEngine(String str, Key key, AlgorithmParameterSpec algorithmParameterSpec) {
        return str.equalsIgnoreCase(HmacAlgorithm.HmacSM3.getValue()) ? SmUtil.createHmacSm3Engine(key.getEncoded()) : new DefaultHMacEngine(str, key, algorithmParameterSpec);
    }
}
