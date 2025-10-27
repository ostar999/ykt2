package org.bouncycastle.crypto.signers;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.DSA;
import org.bouncycastle.crypto.params.ECKeyParameters;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.crypto.params.ParametersWithRandom;
import org.bouncycastle.math.ec.ECAlgorithms;
import org.bouncycastle.math.ec.ECConstants;

/* loaded from: classes9.dex */
public class ECGOST3410Signer implements DSA {
    ECKeyParameters key;
    SecureRandom random;

    @Override // org.bouncycastle.crypto.DSA
    public BigInteger[] generateSignature(byte[] bArr) {
        int length = bArr.length;
        byte[] bArr2 = new byte[length];
        for (int i2 = 0; i2 != length; i2++) {
            bArr2[i2] = bArr[(length - 1) - i2];
        }
        BigInteger bigInteger = new BigInteger(1, bArr2);
        BigInteger n2 = this.key.getParameters().getN();
        while (true) {
            BigInteger bigInteger2 = new BigInteger(n2.bitLength(), this.random);
            BigInteger bigInteger3 = ECConstants.ZERO;
            if (!bigInteger2.equals(bigInteger3)) {
                BigInteger bigIntegerMod = this.key.getParameters().getG().multiply(bigInteger2).getX().toBigInteger().mod(n2);
                if (bigIntegerMod.equals(bigInteger3)) {
                    continue;
                } else {
                    BigInteger bigIntegerMod2 = bigInteger2.multiply(bigInteger).add(((ECPrivateKeyParameters) this.key).getD().multiply(bigIntegerMod)).mod(n2);
                    if (!bigIntegerMod2.equals(bigInteger3)) {
                        return new BigInteger[]{bigIntegerMod, bigIntegerMod2};
                    }
                }
            }
        }
    }

    @Override // org.bouncycastle.crypto.DSA
    public void init(boolean z2, CipherParameters cipherParameters) {
        ECKeyParameters eCKeyParameters;
        if (!z2) {
            eCKeyParameters = (ECPublicKeyParameters) cipherParameters;
        } else {
            if (cipherParameters instanceof ParametersWithRandom) {
                ParametersWithRandom parametersWithRandom = (ParametersWithRandom) cipherParameters;
                this.random = parametersWithRandom.getRandom();
                this.key = (ECPrivateKeyParameters) parametersWithRandom.getParameters();
                return;
            }
            this.random = new SecureRandom();
            eCKeyParameters = (ECPrivateKeyParameters) cipherParameters;
        }
        this.key = eCKeyParameters;
    }

    @Override // org.bouncycastle.crypto.DSA
    public boolean verifySignature(byte[] bArr, BigInteger bigInteger, BigInteger bigInteger2) {
        int length = bArr.length;
        byte[] bArr2 = new byte[length];
        for (int i2 = 0; i2 != length; i2++) {
            bArr2[i2] = bArr[(length - 1) - i2];
        }
        BigInteger bigInteger3 = new BigInteger(1, bArr2);
        BigInteger n2 = this.key.getParameters().getN();
        BigInteger bigInteger4 = ECConstants.ONE;
        if (bigInteger.compareTo(bigInteger4) < 0 || bigInteger.compareTo(n2) >= 0 || bigInteger2.compareTo(bigInteger4) < 0 || bigInteger2.compareTo(n2) >= 0) {
            return false;
        }
        BigInteger bigIntegerModInverse = bigInteger3.modInverse(n2);
        return ECAlgorithms.sumOfTwoMultiplies(this.key.getParameters().getG(), bigInteger2.multiply(bigIntegerModInverse).mod(n2), ((ECPublicKeyParameters) this.key).getQ(), n2.subtract(bigInteger).multiply(bigIntegerModInverse).mod(n2)).getX().toBigInteger().mod(n2).equals(bigInteger);
    }
}
