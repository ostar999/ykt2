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
public class ECDSASigner implements ECConstants, DSA {
    ECKeyParameters key;
    SecureRandom random;

    private BigInteger calculateE(BigInteger bigInteger, byte[] bArr) {
        if (bigInteger.bitLength() > bArr.length * 8) {
            return new BigInteger(1, bArr);
        }
        int length = bArr.length * 8;
        BigInteger bigInteger2 = new BigInteger(1, bArr);
        return length - bigInteger.bitLength() > 0 ? bigInteger2.shiftRight(length - bigInteger.bitLength()) : bigInteger2;
    }

    @Override // org.bouncycastle.crypto.DSA
    public BigInteger[] generateSignature(byte[] bArr) {
        BigInteger bigInteger;
        BigInteger bigInteger2;
        BigInteger bigIntegerMod;
        BigInteger bigIntegerMod2;
        BigInteger n2 = this.key.getParameters().getN();
        BigInteger bigIntegerCalculateE = calculateE(n2, bArr);
        do {
            int iBitLength = n2.bitLength();
            while (true) {
                bigInteger = new BigInteger(iBitLength, this.random);
                bigInteger2 = ECConstants.ZERO;
                if (!bigInteger.equals(bigInteger2) && bigInteger.compareTo(n2) < 0) {
                    bigIntegerMod = this.key.getParameters().getG().multiply(bigInteger).getX().toBigInteger().mod(n2);
                    if (!bigIntegerMod.equals(bigInteger2)) {
                        break;
                    }
                }
            }
            bigIntegerMod2 = bigInteger.modInverse(n2).multiply(bigIntegerCalculateE.add(((ECPrivateKeyParameters) this.key).getD().multiply(bigIntegerMod))).mod(n2);
        } while (bigIntegerMod2.equals(bigInteger2));
        return new BigInteger[]{bigIntegerMod, bigIntegerMod2};
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
        BigInteger n2 = this.key.getParameters().getN();
        BigInteger bigIntegerCalculateE = calculateE(n2, bArr);
        BigInteger bigInteger3 = ECConstants.ONE;
        if (bigInteger.compareTo(bigInteger3) < 0 || bigInteger.compareTo(n2) >= 0 || bigInteger2.compareTo(bigInteger3) < 0 || bigInteger2.compareTo(n2) >= 0) {
            return false;
        }
        BigInteger bigIntegerModInverse = bigInteger2.modInverse(n2);
        return ECAlgorithms.sumOfTwoMultiplies(this.key.getParameters().getG(), bigIntegerCalculateE.multiply(bigIntegerModInverse).mod(n2), ((ECPublicKeyParameters) this.key).getQ(), bigInteger.multiply(bigIntegerModInverse).mod(n2)).getX().toBigInteger().mod(n2).equals(bigInteger);
    }
}
