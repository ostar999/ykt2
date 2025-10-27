package org.bouncycastle.crypto.engines;

import java.math.BigInteger;
import java.util.Vector;
import org.bouncycastle.crypto.AsymmetricBlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.params.NaccacheSternKeyParameters;
import org.bouncycastle.crypto.params.NaccacheSternPrivateKeyParameters;
import org.bouncycastle.crypto.params.ParametersWithRandom;
import org.bouncycastle.util.Arrays;

/* loaded from: classes9.dex */
public class NaccacheSternEngine implements AsymmetricBlockCipher {
    private boolean forEncryption;
    private NaccacheSternKeyParameters key;
    private static BigInteger ZERO = BigInteger.valueOf(0);
    private static BigInteger ONE = BigInteger.valueOf(1);
    private Vector[] lookup = null;
    private boolean debug = false;

    private static BigInteger chineseRemainder(Vector vector, Vector vector2) {
        BigInteger bigIntegerAdd = ZERO;
        BigInteger bigIntegerMultiply = ONE;
        for (int i2 = 0; i2 < vector2.size(); i2++) {
            bigIntegerMultiply = bigIntegerMultiply.multiply((BigInteger) vector2.elementAt(i2));
        }
        for (int i3 = 0; i3 < vector2.size(); i3++) {
            BigInteger bigInteger = (BigInteger) vector2.elementAt(i3);
            BigInteger bigIntegerDivide = bigIntegerMultiply.divide(bigInteger);
            bigIntegerAdd = bigIntegerAdd.add(bigIntegerDivide.multiply(bigIntegerDivide.modInverse(bigInteger)).multiply((BigInteger) vector.elementAt(i3)));
        }
        return bigIntegerAdd.mod(bigIntegerMultiply);
    }

    public byte[] addCryptedBlocks(byte[] bArr, byte[] bArr2) throws InvalidCipherTextException {
        if (this.forEncryption) {
            if (bArr.length > getOutputBlockSize() || bArr2.length > getOutputBlockSize()) {
                throw new InvalidCipherTextException("BlockLength too large for simple addition.\n");
            }
        } else if (bArr.length > getInputBlockSize() || bArr2.length > getInputBlockSize()) {
            throw new InvalidCipherTextException("BlockLength too large for simple addition.\n");
        }
        BigInteger bigInteger = new BigInteger(1, bArr);
        BigInteger bigInteger2 = new BigInteger(1, bArr2);
        BigInteger bigIntegerMod = bigInteger.multiply(bigInteger2).mod(this.key.getModulus());
        if (this.debug) {
            System.out.println("c(m1) as BigInteger:....... " + bigInteger);
            System.out.println("c(m2) as BigInteger:....... " + bigInteger2);
            System.out.println("c(m1)*c(m2)%n = c(m1+m2)%n: " + bigIntegerMod);
        }
        byte[] byteArray = this.key.getModulus().toByteArray();
        Arrays.fill(byteArray, (byte) 0);
        System.arraycopy(bigIntegerMod.toByteArray(), 0, byteArray, byteArray.length - bigIntegerMod.toByteArray().length, bigIntegerMod.toByteArray().length);
        return byteArray;
    }

    public byte[] encrypt(BigInteger bigInteger) {
        byte[] byteArray = this.key.getModulus().toByteArray();
        Arrays.fill(byteArray, (byte) 0);
        byte[] byteArray2 = this.key.getG().modPow(bigInteger, this.key.getModulus()).toByteArray();
        System.arraycopy(byteArray2, 0, byteArray, byteArray.length - byteArray2.length, byteArray2.length);
        if (this.debug) {
            System.out.println("Encrypted value is:  " + new BigInteger(byteArray));
        }
        return byteArray;
    }

    @Override // org.bouncycastle.crypto.AsymmetricBlockCipher
    public int getInputBlockSize() {
        return this.forEncryption ? ((this.key.getLowerSigmaBound() + 7) / 8) - 1 : this.key.getModulus().toByteArray().length;
    }

    @Override // org.bouncycastle.crypto.AsymmetricBlockCipher
    public int getOutputBlockSize() {
        return this.forEncryption ? this.key.getModulus().toByteArray().length : ((this.key.getLowerSigmaBound() + 7) / 8) - 1;
    }

    @Override // org.bouncycastle.crypto.AsymmetricBlockCipher
    public void init(boolean z2, CipherParameters cipherParameters) {
        this.forEncryption = z2;
        if (cipherParameters instanceof ParametersWithRandom) {
            cipherParameters = ((ParametersWithRandom) cipherParameters).getParameters();
        }
        this.key = (NaccacheSternKeyParameters) cipherParameters;
        if (this.forEncryption) {
            return;
        }
        if (this.debug) {
            System.out.println("Constructing lookup Array");
        }
        NaccacheSternPrivateKeyParameters naccacheSternPrivateKeyParameters = (NaccacheSternPrivateKeyParameters) this.key;
        Vector smallPrimes = naccacheSternPrivateKeyParameters.getSmallPrimes();
        this.lookup = new Vector[smallPrimes.size()];
        for (int i2 = 0; i2 < smallPrimes.size(); i2++) {
            BigInteger bigInteger = (BigInteger) smallPrimes.elementAt(i2);
            int iIntValue = bigInteger.intValue();
            this.lookup[i2] = new Vector();
            this.lookup[i2].addElement(ONE);
            if (this.debug) {
                System.out.println("Constructing lookup ArrayList for " + iIntValue);
            }
            BigInteger bigIntegerAdd = ZERO;
            for (int i3 = 1; i3 < iIntValue; i3++) {
                bigIntegerAdd = bigIntegerAdd.add(naccacheSternPrivateKeyParameters.getPhi_n());
                this.lookup[i2].addElement(naccacheSternPrivateKeyParameters.getG().modPow(bigIntegerAdd.divide(bigInteger), naccacheSternPrivateKeyParameters.getModulus()));
            }
        }
    }

    @Override // org.bouncycastle.crypto.AsymmetricBlockCipher
    public byte[] processBlock(byte[] bArr, int i2, int i3) throws InvalidCipherTextException {
        if (this.key == null) {
            throw new IllegalStateException("NaccacheStern engine not initialised");
        }
        if (i3 > getInputBlockSize() + 1) {
            throw new DataLengthException("input too large for Naccache-Stern cipher.\n");
        }
        if (!this.forEncryption && i3 < getInputBlockSize()) {
            throw new InvalidCipherTextException("BlockLength does not match modulus for Naccache-Stern cipher.\n");
        }
        if (i2 != 0 || i3 != bArr.length) {
            byte[] bArr2 = new byte[i3];
            System.arraycopy(bArr, i2, bArr2, 0, i3);
            bArr = bArr2;
        }
        BigInteger bigInteger = new BigInteger(1, bArr);
        if (this.debug) {
            System.out.println("input as BigInteger: " + bigInteger);
        }
        if (this.forEncryption) {
            return encrypt(bigInteger);
        }
        Vector vector = new Vector();
        NaccacheSternPrivateKeyParameters naccacheSternPrivateKeyParameters = (NaccacheSternPrivateKeyParameters) this.key;
        Vector smallPrimes = naccacheSternPrivateKeyParameters.getSmallPrimes();
        for (int i4 = 0; i4 < smallPrimes.size(); i4++) {
            BigInteger bigIntegerModPow = bigInteger.modPow(naccacheSternPrivateKeyParameters.getPhi_n().divide((BigInteger) smallPrimes.elementAt(i4)), naccacheSternPrivateKeyParameters.getModulus());
            Vector vector2 = this.lookup[i4];
            if (vector2.size() != ((BigInteger) smallPrimes.elementAt(i4)).intValue()) {
                if (this.debug) {
                    System.out.println("Prime is " + smallPrimes.elementAt(i4) + ", lookup table has size " + vector2.size());
                }
                throw new InvalidCipherTextException("Error in lookup Array for " + ((BigInteger) smallPrimes.elementAt(i4)).intValue() + ": Size mismatch. Expected ArrayList with length " + ((BigInteger) smallPrimes.elementAt(i4)).intValue() + " but found ArrayList of length " + this.lookup[i4].size());
            }
            int iIndexOf = vector2.indexOf(bigIntegerModPow);
            if (iIndexOf == -1) {
                if (this.debug) {
                    System.out.println("Actual prime is " + smallPrimes.elementAt(i4));
                    System.out.println("Decrypted value is " + bigIntegerModPow);
                    System.out.println("LookupList for " + smallPrimes.elementAt(i4) + " with size " + this.lookup[i4].size() + " is: ");
                    for (int i5 = 0; i5 < this.lookup[i4].size(); i5++) {
                        System.out.println(this.lookup[i4].elementAt(i5));
                    }
                }
                throw new InvalidCipherTextException("Lookup failed");
            }
            vector.addElement(BigInteger.valueOf(iIndexOf));
        }
        return chineseRemainder(vector, smallPrimes).toByteArray();
    }

    public byte[] processData(byte[] bArr) throws InvalidCipherTextException {
        byte[] bArrProcessBlock;
        if (this.debug) {
            System.out.println();
        }
        if (bArr.length <= getInputBlockSize()) {
            if (this.debug) {
                System.out.println("data size is less then input block size, processing directly");
            }
            return processBlock(bArr, 0, bArr.length);
        }
        int inputBlockSize = getInputBlockSize();
        int outputBlockSize = getOutputBlockSize();
        if (this.debug) {
            System.out.println("Input blocksize is:  " + inputBlockSize + " bytes");
            System.out.println("Output blocksize is: " + outputBlockSize + " bytes");
            System.out.println("Data has length:.... " + bArr.length + " bytes");
        }
        byte[] bArr2 = new byte[((bArr.length / inputBlockSize) + 1) * outputBlockSize];
        int length = 0;
        int length2 = 0;
        while (length < bArr.length) {
            int i2 = length + inputBlockSize;
            if (i2 < bArr.length) {
                bArrProcessBlock = processBlock(bArr, length, inputBlockSize);
                length = i2;
            } else {
                bArrProcessBlock = processBlock(bArr, length, bArr.length - length);
                length += bArr.length - length;
            }
            if (this.debug) {
                System.out.println("new datapos is " + length);
            }
            if (bArrProcessBlock == null) {
                if (this.debug) {
                    System.out.println("cipher returned null");
                }
                throw new InvalidCipherTextException("cipher returned null");
            }
            System.arraycopy(bArrProcessBlock, 0, bArr2, length2, bArrProcessBlock.length);
            length2 += bArrProcessBlock.length;
        }
        byte[] bArr3 = new byte[length2];
        System.arraycopy(bArr2, 0, bArr3, 0, length2);
        if (this.debug) {
            System.out.println("returning " + length2 + " bytes");
        }
        return bArr3;
    }

    public void setDebug(boolean z2) {
        this.debug = z2;
    }
}
