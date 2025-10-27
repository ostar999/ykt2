package cn.hutool.crypto.digest.mac;

import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.engines.SM4Engine;

/* loaded from: classes.dex */
public class SM4MacEngine extends CBCBlockCipherMacEngine {
    private static final int MAC_SIZE = 128;

    public SM4MacEngine(CipherParameters cipherParameters) {
        super((BlockCipher) new SM4Engine(), 128, cipherParameters);
    }
}
