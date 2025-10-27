package net.lingala.zip4j.crypto.PBKDF2;

import net.lingala.zip4j.util.Raw;

/* loaded from: classes9.dex */
public class PBKDF2Engine {
    protected PBKDF2Parameters parameters;
    protected PRF prf;

    public PBKDF2Engine() {
        this.parameters = null;
        this.prf = null;
    }

    public void INT(byte[] bArr, int i2, int i3) {
        bArr[i2 + 0] = (byte) (i3 / 16777216);
        bArr[i2 + 1] = (byte) (i3 / 65536);
        bArr[i2 + 2] = (byte) (i3 / 256);
        bArr[i2 + 3] = (byte) i3;
    }

    public byte[] PBKDF2(PRF prf, byte[] bArr, int i2, int i3) {
        byte[] bArr2 = bArr == null ? new byte[0] : bArr;
        int hLen = prf.getHLen();
        int iCeil = ceil(i3, hLen);
        int i4 = i3 - ((iCeil - 1) * hLen);
        byte[] bArr3 = new byte[iCeil * hLen];
        int i5 = 0;
        for (int i6 = 1; i6 <= iCeil; i6++) {
            _F(bArr3, i5, prf, bArr2, i2, i6);
            i5 += hLen;
        }
        if (i4 >= hLen) {
            return bArr3;
        }
        byte[] bArr4 = new byte[i3];
        System.arraycopy(bArr3, 0, bArr4, 0, i3);
        return bArr4;
    }

    public void _F(byte[] bArr, int i2, PRF prf, byte[] bArr2, int i3, int i4) {
        int hLen = prf.getHLen();
        byte[] bArr3 = new byte[hLen];
        byte[] bArrDoFinal = new byte[bArr2.length + 4];
        System.arraycopy(bArr2, 0, bArrDoFinal, 0, bArr2.length);
        INT(bArrDoFinal, bArr2.length, i4);
        for (int i5 = 0; i5 < i3; i5++) {
            bArrDoFinal = prf.doFinal(bArrDoFinal);
            xor(bArr3, bArrDoFinal);
        }
        System.arraycopy(bArr3, 0, bArr, i2, hLen);
    }

    public void assertPRF(byte[] bArr) {
        if (this.prf == null) {
            this.prf = new MacBasedPRF(this.parameters.getHashAlgorithm());
        }
        this.prf.init(bArr);
    }

    public int ceil(int i2, int i3) {
        return (i2 / i3) + (i2 % i3 > 0 ? 1 : 0);
    }

    public byte[] deriveKey(char[] cArr) {
        return deriveKey(cArr, 0);
    }

    public PBKDF2Parameters getParameters() {
        return this.parameters;
    }

    public PRF getPseudoRandomFunction() {
        return this.prf;
    }

    public void setParameters(PBKDF2Parameters pBKDF2Parameters) {
        this.parameters = pBKDF2Parameters;
    }

    public void setPseudoRandomFunction(PRF prf) {
        this.prf = prf;
    }

    public boolean verifyKey(char[] cArr) {
        byte[] bArrDeriveKey;
        byte[] derivedKey = getParameters().getDerivedKey();
        if (derivedKey == null || derivedKey.length == 0 || (bArrDeriveKey = deriveKey(cArr, derivedKey.length)) == null || bArrDeriveKey.length != derivedKey.length) {
            return false;
        }
        for (int i2 = 0; i2 < bArrDeriveKey.length; i2++) {
            if (bArrDeriveKey[i2] != derivedKey[i2]) {
                return false;
            }
        }
        return true;
    }

    public void xor(byte[] bArr, byte[] bArr2) {
        for (int i2 = 0; i2 < bArr.length; i2++) {
            bArr[i2] = (byte) (bArr[i2] ^ bArr2[i2]);
        }
    }

    public byte[] deriveKey(char[] cArr, int i2) {
        cArr.getClass();
        assertPRF(Raw.convertCharArrayToByteArray(cArr));
        if (i2 == 0) {
            i2 = this.prf.getHLen();
        }
        return PBKDF2(this.prf, this.parameters.getSalt(), this.parameters.getIterationCount(), i2);
    }

    public PBKDF2Engine(PBKDF2Parameters pBKDF2Parameters) {
        this.parameters = pBKDF2Parameters;
        this.prf = null;
    }

    public PBKDF2Engine(PBKDF2Parameters pBKDF2Parameters, PRF prf) {
        this.parameters = pBKDF2Parameters;
        this.prf = prf;
    }
}
