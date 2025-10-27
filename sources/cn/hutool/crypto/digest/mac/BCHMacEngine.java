package cn.hutool.crypto.digest.mac;

import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.macs.HMac;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;

/* loaded from: classes.dex */
public class BCHMacEngine extends BCMacEngine {
    public BCHMacEngine(Digest digest, byte[] bArr, byte[] bArr2) {
        this(digest, new ParametersWithIV(new KeyParameter(bArr), bArr2));
    }

    public BCHMacEngine init(Digest digest, CipherParameters cipherParameters) {
        return (BCHMacEngine) init(new HMac(digest), cipherParameters);
    }

    public BCHMacEngine(Digest digest, byte[] bArr) {
        this(digest, new KeyParameter(bArr));
    }

    public BCHMacEngine(Digest digest, CipherParameters cipherParameters) {
        this(new HMac(digest), cipherParameters);
    }

    public BCHMacEngine(HMac hMac, CipherParameters cipherParameters) {
        super(hMac, cipherParameters);
    }
}
