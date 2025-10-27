package cn.hutool.crypto.digest.mac;

import java.io.InputStream;
import m0.a;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.DataLengthException;

/* loaded from: classes.dex */
public class BCMacEngine implements MacEngine {
    private org.bouncycastle.crypto.Mac mac;

    public BCMacEngine(org.bouncycastle.crypto.Mac mac, CipherParameters cipherParameters) throws IllegalArgumentException {
        init(mac, cipherParameters);
    }

    @Override // cn.hutool.crypto.digest.mac.MacEngine
    public /* synthetic */ byte[] digest(InputStream inputStream, int i2) {
        return a.a(this, inputStream, i2);
    }

    @Override // cn.hutool.crypto.digest.mac.MacEngine
    public byte[] doFinal() throws IllegalStateException, DataLengthException {
        byte[] bArr = new byte[getMacLength()];
        this.mac.doFinal(bArr, 0);
        return bArr;
    }

    @Override // cn.hutool.crypto.digest.mac.MacEngine
    public String getAlgorithm() {
        return this.mac.getAlgorithmName();
    }

    public org.bouncycastle.crypto.Mac getMac() {
        return this.mac;
    }

    @Override // cn.hutool.crypto.digest.mac.MacEngine
    public int getMacLength() {
        return this.mac.getMacSize();
    }

    public BCMacEngine init(org.bouncycastle.crypto.Mac mac, CipherParameters cipherParameters) throws IllegalArgumentException {
        mac.init(cipherParameters);
        this.mac = mac;
        return this;
    }

    @Override // cn.hutool.crypto.digest.mac.MacEngine
    public void reset() {
        this.mac.reset();
    }

    @Override // cn.hutool.crypto.digest.mac.MacEngine
    public /* synthetic */ void update(byte[] bArr) {
        a.b(this, bArr);
    }

    @Override // cn.hutool.crypto.digest.mac.MacEngine
    public void update(byte[] bArr, int i2, int i3) throws IllegalStateException, DataLengthException {
        this.mac.update(bArr, i2, i3);
    }
}
