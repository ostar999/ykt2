package org.bouncycastle.crypto.engines;

import org.bouncycastle.crypto.AsymmetricBlockCipher;
import org.bouncycastle.crypto.CipherParameters;

/* loaded from: classes9.dex */
public class RSAEngine implements AsymmetricBlockCipher {

    /* renamed from: core, reason: collision with root package name */
    private RSACoreEngine f27839core;

    @Override // org.bouncycastle.crypto.AsymmetricBlockCipher
    public int getInputBlockSize() {
        return this.f27839core.getInputBlockSize();
    }

    @Override // org.bouncycastle.crypto.AsymmetricBlockCipher
    public int getOutputBlockSize() {
        return this.f27839core.getOutputBlockSize();
    }

    @Override // org.bouncycastle.crypto.AsymmetricBlockCipher
    public void init(boolean z2, CipherParameters cipherParameters) {
        if (this.f27839core == null) {
            this.f27839core = new RSACoreEngine();
        }
        this.f27839core.init(z2, cipherParameters);
    }

    @Override // org.bouncycastle.crypto.AsymmetricBlockCipher
    public byte[] processBlock(byte[] bArr, int i2, int i3) {
        RSACoreEngine rSACoreEngine = this.f27839core;
        if (rSACoreEngine != null) {
            return rSACoreEngine.convertOutput(rSACoreEngine.processBlock(rSACoreEngine.convertInput(bArr, i2, i3)));
        }
        throw new IllegalStateException("RSA engine not initialised");
    }
}
