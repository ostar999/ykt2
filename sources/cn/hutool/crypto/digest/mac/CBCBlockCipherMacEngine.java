package cn.hutool.crypto.digest.mac;

import java.security.Key;
import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.macs.CBCBlockCipherMac;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;

/* loaded from: classes.dex */
public class CBCBlockCipherMacEngine extends BCMacEngine {
    public CBCBlockCipherMacEngine(BlockCipher blockCipher, int i2, Key key, byte[] bArr) {
        this(blockCipher, i2, key.getEncoded(), bArr);
    }

    public CBCBlockCipherMacEngine init(BlockCipher blockCipher, CipherParameters cipherParameters) {
        return (CBCBlockCipherMacEngine) init(new CBCBlockCipherMac(blockCipher), cipherParameters);
    }

    public CBCBlockCipherMacEngine(BlockCipher blockCipher, int i2, byte[] bArr, byte[] bArr2) {
        this(blockCipher, i2, new ParametersWithIV(new KeyParameter(bArr), bArr2));
    }

    public CBCBlockCipherMacEngine(BlockCipher blockCipher, int i2, Key key) {
        this(blockCipher, i2, key.getEncoded());
    }

    public CBCBlockCipherMacEngine(BlockCipher blockCipher, int i2, byte[] bArr) {
        this(blockCipher, i2, new KeyParameter(bArr));
    }

    public CBCBlockCipherMacEngine(BlockCipher blockCipher, int i2, CipherParameters cipherParameters) {
        this(new CBCBlockCipherMac(blockCipher, i2), cipherParameters);
    }

    public CBCBlockCipherMacEngine(CBCBlockCipherMac cBCBlockCipherMac, CipherParameters cipherParameters) {
        super(cBCBlockCipherMac, cipherParameters);
    }
}
