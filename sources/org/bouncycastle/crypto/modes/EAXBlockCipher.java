package org.bouncycastle.crypto.modes;

import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.Mac;
import org.bouncycastle.crypto.macs.CMac;
import org.bouncycastle.crypto.params.AEADParameters;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.bouncycastle.util.Arrays;

/* loaded from: classes9.dex */
public class EAXBlockCipher implements AEADBlockCipher {
    private static final byte cTAG = 2;
    private static final byte hTAG = 1;
    private static final byte nTAG = 0;
    private byte[] associatedTextMac;
    private int blockSize;
    private byte[] bufBlock;
    private int bufOff;
    private SICBlockCipher cipher;
    private boolean forEncryption;
    private Mac mac;
    private byte[] macBlock;
    private int macSize;
    private byte[] nonceMac;

    public EAXBlockCipher(BlockCipher blockCipher) {
        this.blockSize = blockCipher.getBlockSize();
        CMac cMac = new CMac(blockCipher);
        this.mac = cMac;
        int i2 = this.blockSize;
        this.macBlock = new byte[i2];
        this.bufBlock = new byte[i2 * 2];
        this.associatedTextMac = new byte[cMac.getMacSize()];
        this.nonceMac = new byte[this.mac.getMacSize()];
        this.cipher = new SICBlockCipher(blockCipher);
    }

    private void calculateMac() throws IllegalStateException, DataLengthException {
        byte[] bArr = new byte[this.blockSize];
        int i2 = 0;
        this.mac.doFinal(bArr, 0);
        while (true) {
            byte[] bArr2 = this.macBlock;
            if (i2 >= bArr2.length) {
                return;
            }
            bArr2[i2] = (byte) ((this.nonceMac[i2] ^ this.associatedTextMac[i2]) ^ bArr[i2]);
            i2++;
        }
    }

    private int process(byte b3, byte[] bArr, int i2) throws IllegalStateException, DataLengthException {
        int iProcessBlock;
        byte[] bArr2 = this.bufBlock;
        int i3 = this.bufOff;
        int i4 = i3 + 1;
        this.bufOff = i4;
        bArr2[i3] = b3;
        if (i4 != bArr2.length) {
            return 0;
        }
        if (this.forEncryption) {
            iProcessBlock = this.cipher.processBlock(bArr2, 0, bArr, i2);
            this.mac.update(bArr, i2, this.blockSize);
        } else {
            this.mac.update(bArr2, 0, this.blockSize);
            iProcessBlock = this.cipher.processBlock(this.bufBlock, 0, bArr, i2);
        }
        int i5 = this.blockSize;
        this.bufOff = i5;
        byte[] bArr3 = this.bufBlock;
        System.arraycopy(bArr3, i5, bArr3, 0, i5);
        return iProcessBlock;
    }

    private void reset(boolean z2) throws IllegalStateException, DataLengthException {
        this.cipher.reset();
        this.mac.reset();
        this.bufOff = 0;
        Arrays.fill(this.bufBlock, (byte) 0);
        if (z2) {
            Arrays.fill(this.macBlock, (byte) 0);
        }
        int i2 = this.blockSize;
        byte[] bArr = new byte[i2];
        bArr[i2 - 1] = 2;
        this.mac.update(bArr, 0, i2);
    }

    private boolean verifyMac(byte[] bArr, int i2) {
        for (int i3 = 0; i3 < this.macSize; i3++) {
            if (this.macBlock[i3] != bArr[i2 + i3]) {
                return false;
            }
        }
        return true;
    }

    @Override // org.bouncycastle.crypto.modes.AEADBlockCipher
    public int doFinal(byte[] bArr, int i2) throws InvalidCipherTextException, IllegalStateException, DataLengthException {
        int i3 = this.bufOff;
        byte[] bArr2 = this.bufBlock;
        byte[] bArr3 = new byte[bArr2.length];
        this.bufOff = 0;
        if (this.forEncryption) {
            this.cipher.processBlock(bArr2, 0, bArr3, 0);
            SICBlockCipher sICBlockCipher = this.cipher;
            byte[] bArr4 = this.bufBlock;
            int i4 = this.blockSize;
            sICBlockCipher.processBlock(bArr4, i4, bArr3, i4);
            System.arraycopy(bArr3, 0, bArr, i2, i3);
            this.mac.update(bArr3, 0, i3);
            calculateMac();
            System.arraycopy(this.macBlock, 0, bArr, i2 + i3, this.macSize);
            reset(false);
            return i3 + this.macSize;
        }
        int i5 = this.macSize;
        if (i3 > i5) {
            this.mac.update(bArr2, 0, i3 - i5);
            this.cipher.processBlock(this.bufBlock, 0, bArr3, 0);
            SICBlockCipher sICBlockCipher2 = this.cipher;
            byte[] bArr5 = this.bufBlock;
            int i6 = this.blockSize;
            sICBlockCipher2.processBlock(bArr5, i6, bArr3, i6);
            System.arraycopy(bArr3, 0, bArr, i2, i3 - this.macSize);
        }
        calculateMac();
        if (!verifyMac(this.bufBlock, i3 - this.macSize)) {
            throw new InvalidCipherTextException("mac check in EAX failed");
        }
        reset(false);
        return i3 - this.macSize;
    }

    @Override // org.bouncycastle.crypto.modes.AEADBlockCipher
    public String getAlgorithmName() {
        return this.cipher.getUnderlyingCipher().getAlgorithmName() + "/EAX";
    }

    public int getBlockSize() {
        return this.cipher.getBlockSize();
    }

    @Override // org.bouncycastle.crypto.modes.AEADBlockCipher
    public byte[] getMac() {
        int i2 = this.macSize;
        byte[] bArr = new byte[i2];
        System.arraycopy(this.macBlock, 0, bArr, 0, i2);
        return bArr;
    }

    @Override // org.bouncycastle.crypto.modes.AEADBlockCipher
    public int getOutputSize(int i2) {
        return this.forEncryption ? i2 + this.bufOff + this.macSize : (i2 + this.bufOff) - this.macSize;
    }

    @Override // org.bouncycastle.crypto.modes.AEADBlockCipher
    public BlockCipher getUnderlyingCipher() {
        return this.cipher.getUnderlyingCipher();
    }

    @Override // org.bouncycastle.crypto.modes.AEADBlockCipher
    public int getUpdateOutputSize(int i2) {
        int i3 = i2 + this.bufOff;
        int i4 = this.blockSize;
        return (i3 / i4) * i4;
    }

    @Override // org.bouncycastle.crypto.modes.AEADBlockCipher
    public void init(boolean z2, CipherParameters cipherParameters) throws IllegalStateException, DataLengthException, IllegalArgumentException {
        byte[] iv;
        byte[] associatedText;
        CipherParameters parameters;
        this.forEncryption = z2;
        if (cipherParameters instanceof AEADParameters) {
            AEADParameters aEADParameters = (AEADParameters) cipherParameters;
            iv = aEADParameters.getNonce();
            associatedText = aEADParameters.getAssociatedText();
            this.macSize = aEADParameters.getMacSize() / 8;
            parameters = aEADParameters.getKey();
        } else {
            if (!(cipherParameters instanceof ParametersWithIV)) {
                throw new IllegalArgumentException("invalid parameters passed to EAX");
            }
            ParametersWithIV parametersWithIV = (ParametersWithIV) cipherParameters;
            iv = parametersWithIV.getIV();
            associatedText = new byte[0];
            this.macSize = this.mac.getMacSize() / 2;
            parameters = parametersWithIV.getParameters();
        }
        byte[] bArr = new byte[this.blockSize];
        this.mac.init(parameters);
        int i2 = this.blockSize;
        bArr[i2 - 1] = 1;
        this.mac.update(bArr, 0, i2);
        this.mac.update(associatedText, 0, associatedText.length);
        this.mac.doFinal(this.associatedTextMac, 0);
        int i3 = this.blockSize;
        bArr[i3 - 1] = 0;
        this.mac.update(bArr, 0, i3);
        this.mac.update(iv, 0, iv.length);
        this.mac.doFinal(this.nonceMac, 0);
        int i4 = this.blockSize;
        bArr[i4 - 1] = 2;
        this.mac.update(bArr, 0, i4);
        this.cipher.init(true, new ParametersWithIV(parameters, this.nonceMac));
    }

    @Override // org.bouncycastle.crypto.modes.AEADBlockCipher
    public int processByte(byte b3, byte[] bArr, int i2) throws DataLengthException {
        return process(b3, bArr, i2);
    }

    @Override // org.bouncycastle.crypto.modes.AEADBlockCipher
    public int processBytes(byte[] bArr, int i2, int i3, byte[] bArr2, int i4) throws DataLengthException {
        int iProcess = 0;
        for (int i5 = 0; i5 != i3; i5++) {
            iProcess += process(bArr[i2 + i5], bArr2, i4 + iProcess);
        }
        return iProcess;
    }

    @Override // org.bouncycastle.crypto.modes.AEADBlockCipher
    public void reset() throws IllegalStateException, DataLengthException {
        reset(true);
    }
}
