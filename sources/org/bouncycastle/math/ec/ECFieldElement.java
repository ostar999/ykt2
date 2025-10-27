package org.bouncycastle.math.ec;

import java.math.BigInteger;
import java.util.Random;

/* loaded from: classes9.dex */
public abstract class ECFieldElement implements ECConstants {

    public static class F2m extends ECFieldElement {
        public static final int GNB = 1;
        public static final int PPB = 3;
        public static final int TPB = 2;

        /* renamed from: k1, reason: collision with root package name */
        private int f27933k1;
        private int k2;
        private int k3;

        /* renamed from: m, reason: collision with root package name */
        private int f27934m;
        private int representation;

        /* renamed from: t, reason: collision with root package name */
        private int f27935t;

        /* renamed from: x, reason: collision with root package name */
        private IntArray f27936x;

        public F2m(int i2, int i3, int i4, int i5, BigInteger bigInteger) {
            int i6;
            int i7 = (i2 + 31) >> 5;
            this.f27935t = i7;
            this.f27936x = new IntArray(bigInteger, i7);
            if (i4 == 0 && i5 == 0) {
                i6 = 2;
            } else {
                if (i4 >= i5) {
                    throw new IllegalArgumentException("k2 must be smaller than k3");
                }
                if (i4 <= 0) {
                    throw new IllegalArgumentException("k2 must be larger than 0");
                }
                i6 = 3;
            }
            this.representation = i6;
            if (bigInteger.signum() < 0) {
                throw new IllegalArgumentException("x value cannot be negative");
            }
            this.f27934m = i2;
            this.f27933k1 = i3;
            this.k2 = i4;
            this.k3 = i5;
        }

        private F2m(int i2, int i3, int i4, int i5, IntArray intArray) {
            this.f27935t = (i2 + 31) >> 5;
            this.f27936x = intArray;
            this.f27934m = i2;
            this.f27933k1 = i3;
            this.k2 = i4;
            this.k3 = i5;
            this.representation = (i4 == 0 && i5 == 0) ? 2 : 3;
        }

        public F2m(int i2, int i3, BigInteger bigInteger) {
            this(i2, i3, 0, 0, bigInteger);
        }

        public static void checkFieldElements(ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2) {
            if (!(eCFieldElement instanceof F2m) || !(eCFieldElement2 instanceof F2m)) {
                throw new IllegalArgumentException("Field elements are not both instances of ECFieldElement.F2m");
            }
            F2m f2m = (F2m) eCFieldElement;
            F2m f2m2 = (F2m) eCFieldElement2;
            if (f2m.f27934m != f2m2.f27934m || f2m.f27933k1 != f2m2.f27933k1 || f2m.k2 != f2m2.k2 || f2m.k3 != f2m2.k3) {
                throw new IllegalArgumentException("Field elements are not elements of the same field F2m");
            }
            if (f2m.representation != f2m2.representation) {
                throw new IllegalArgumentException("One of the field elements are not elements has incorrect representation");
            }
        }

        @Override // org.bouncycastle.math.ec.ECFieldElement
        public ECFieldElement add(ECFieldElement eCFieldElement) {
            IntArray intArray = (IntArray) this.f27936x.clone();
            intArray.addShifted(((F2m) eCFieldElement).f27936x, 0);
            return new F2m(this.f27934m, this.f27933k1, this.k2, this.k3, intArray);
        }

        @Override // org.bouncycastle.math.ec.ECFieldElement
        public ECFieldElement divide(ECFieldElement eCFieldElement) {
            return multiply(eCFieldElement.invert());
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof F2m)) {
                return false;
            }
            F2m f2m = (F2m) obj;
            return this.f27934m == f2m.f27934m && this.f27933k1 == f2m.f27933k1 && this.k2 == f2m.k2 && this.k3 == f2m.k3 && this.representation == f2m.representation && this.f27936x.equals(f2m.f27936x);
        }

        @Override // org.bouncycastle.math.ec.ECFieldElement
        public String getFieldName() {
            return "F2m";
        }

        @Override // org.bouncycastle.math.ec.ECFieldElement
        public int getFieldSize() {
            return this.f27934m;
        }

        public int getK1() {
            return this.f27933k1;
        }

        public int getK2() {
            return this.k2;
        }

        public int getK3() {
            return this.k3;
        }

        public int getM() {
            return this.f27934m;
        }

        public int getRepresentation() {
            return this.representation;
        }

        public int hashCode() {
            return (((this.f27936x.hashCode() ^ this.f27934m) ^ this.f27933k1) ^ this.k2) ^ this.k3;
        }

        @Override // org.bouncycastle.math.ec.ECFieldElement
        public ECFieldElement invert() {
            IntArray intArray = (IntArray) this.f27936x.clone();
            IntArray intArray2 = new IntArray(this.f27935t);
            intArray2.setBit(this.f27934m);
            intArray2.setBit(0);
            intArray2.setBit(this.f27933k1);
            if (this.representation == 3) {
                intArray2.setBit(this.k2);
                intArray2.setBit(this.k3);
            }
            IntArray intArray3 = new IntArray(this.f27935t);
            intArray3.setBit(0);
            IntArray intArray4 = new IntArray(this.f27935t);
            while (!intArray.isZero()) {
                int iBitLength = intArray.bitLength() - intArray2.bitLength();
                if (iBitLength < 0) {
                    iBitLength = -iBitLength;
                    IntArray intArray5 = intArray2;
                    intArray2 = intArray;
                    intArray = intArray5;
                    IntArray intArray6 = intArray4;
                    intArray4 = intArray3;
                    intArray3 = intArray6;
                }
                int i2 = iBitLength >> 5;
                int i3 = iBitLength & 31;
                intArray.addShifted(intArray2.shiftLeft(i3), i2);
                intArray3.addShifted(intArray4.shiftLeft(i3), i2);
            }
            return new F2m(this.f27934m, this.f27933k1, this.k2, this.k3, intArray4);
        }

        @Override // org.bouncycastle.math.ec.ECFieldElement
        public ECFieldElement multiply(ECFieldElement eCFieldElement) {
            IntArray intArrayMultiply = this.f27936x.multiply(((F2m) eCFieldElement).f27936x, this.f27934m);
            intArrayMultiply.reduce(this.f27934m, new int[]{this.f27933k1, this.k2, this.k3});
            return new F2m(this.f27934m, this.f27933k1, this.k2, this.k3, intArrayMultiply);
        }

        @Override // org.bouncycastle.math.ec.ECFieldElement
        public ECFieldElement negate() {
            return this;
        }

        @Override // org.bouncycastle.math.ec.ECFieldElement
        public ECFieldElement sqrt() {
            throw new RuntimeException("Not implemented");
        }

        @Override // org.bouncycastle.math.ec.ECFieldElement
        public ECFieldElement square() {
            IntArray intArraySquare = this.f27936x.square(this.f27934m);
            intArraySquare.reduce(this.f27934m, new int[]{this.f27933k1, this.k2, this.k3});
            return new F2m(this.f27934m, this.f27933k1, this.k2, this.k3, intArraySquare);
        }

        @Override // org.bouncycastle.math.ec.ECFieldElement
        public ECFieldElement subtract(ECFieldElement eCFieldElement) {
            return add(eCFieldElement);
        }

        @Override // org.bouncycastle.math.ec.ECFieldElement
        public BigInteger toBigInteger() {
            return this.f27936x.toBigInteger();
        }
    }

    public static class Fp extends ECFieldElement {

        /* renamed from: q, reason: collision with root package name */
        BigInteger f27937q;

        /* renamed from: x, reason: collision with root package name */
        BigInteger f27938x;

        public Fp(BigInteger bigInteger, BigInteger bigInteger2) {
            this.f27938x = bigInteger2;
            if (bigInteger2.compareTo(bigInteger) >= 0) {
                throw new IllegalArgumentException("x value too large in field element");
            }
            this.f27937q = bigInteger;
        }

        private static BigInteger[] lucasSequence(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3, BigInteger bigInteger4) {
            int iBitLength = bigInteger4.bitLength();
            int lowestSetBit = bigInteger4.getLowestSetBit();
            BigInteger bigIntegerMod = ECConstants.ONE;
            BigInteger bigIntegerMod2 = bigInteger2;
            BigInteger bigIntegerMod3 = bigIntegerMod;
            BigInteger bigIntegerMod4 = ECConstants.TWO;
            BigInteger bigIntegerMod5 = bigIntegerMod3;
            for (int i2 = iBitLength - 1; i2 >= lowestSetBit + 1; i2--) {
                bigIntegerMod = bigIntegerMod.multiply(bigIntegerMod5).mod(bigInteger);
                if (bigInteger4.testBit(i2)) {
                    bigIntegerMod5 = bigIntegerMod.multiply(bigInteger3).mod(bigInteger);
                    bigIntegerMod3 = bigIntegerMod3.multiply(bigIntegerMod2).mod(bigInteger);
                    bigIntegerMod4 = bigIntegerMod2.multiply(bigIntegerMod4).subtract(bigInteger2.multiply(bigIntegerMod)).mod(bigInteger);
                    bigIntegerMod2 = bigIntegerMod2.multiply(bigIntegerMod2).subtract(bigIntegerMod5.shiftLeft(1)).mod(bigInteger);
                } else {
                    BigInteger bigIntegerMod6 = bigIntegerMod3.multiply(bigIntegerMod4).subtract(bigIntegerMod).mod(bigInteger);
                    BigInteger bigIntegerMod7 = bigIntegerMod2.multiply(bigIntegerMod4).subtract(bigInteger2.multiply(bigIntegerMod)).mod(bigInteger);
                    bigIntegerMod4 = bigIntegerMod4.multiply(bigIntegerMod4).subtract(bigIntegerMod.shiftLeft(1)).mod(bigInteger);
                    bigIntegerMod2 = bigIntegerMod7;
                    bigIntegerMod3 = bigIntegerMod6;
                    bigIntegerMod5 = bigIntegerMod;
                }
            }
            BigInteger bigIntegerMod8 = bigIntegerMod.multiply(bigIntegerMod5).mod(bigInteger);
            BigInteger bigIntegerMod9 = bigIntegerMod8.multiply(bigInteger3).mod(bigInteger);
            BigInteger bigIntegerMod10 = bigIntegerMod3.multiply(bigIntegerMod4).subtract(bigIntegerMod8).mod(bigInteger);
            BigInteger bigIntegerMod11 = bigIntegerMod2.multiply(bigIntegerMod4).subtract(bigInteger2.multiply(bigIntegerMod8)).mod(bigInteger);
            BigInteger bigIntegerMod12 = bigIntegerMod8.multiply(bigIntegerMod9).mod(bigInteger);
            for (int i3 = 1; i3 <= lowestSetBit; i3++) {
                bigIntegerMod10 = bigIntegerMod10.multiply(bigIntegerMod11).mod(bigInteger);
                bigIntegerMod11 = bigIntegerMod11.multiply(bigIntegerMod11).subtract(bigIntegerMod12.shiftLeft(1)).mod(bigInteger);
                bigIntegerMod12 = bigIntegerMod12.multiply(bigIntegerMod12).mod(bigInteger);
            }
            return new BigInteger[]{bigIntegerMod10, bigIntegerMod11};
        }

        @Override // org.bouncycastle.math.ec.ECFieldElement
        public ECFieldElement add(ECFieldElement eCFieldElement) {
            return new Fp(this.f27937q, this.f27938x.add(eCFieldElement.toBigInteger()).mod(this.f27937q));
        }

        @Override // org.bouncycastle.math.ec.ECFieldElement
        public ECFieldElement divide(ECFieldElement eCFieldElement) {
            return new Fp(this.f27937q, this.f27938x.multiply(eCFieldElement.toBigInteger().modInverse(this.f27937q)).mod(this.f27937q));
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof Fp)) {
                return false;
            }
            Fp fp = (Fp) obj;
            return this.f27937q.equals(fp.f27937q) && this.f27938x.equals(fp.f27938x);
        }

        @Override // org.bouncycastle.math.ec.ECFieldElement
        public String getFieldName() {
            return "Fp";
        }

        @Override // org.bouncycastle.math.ec.ECFieldElement
        public int getFieldSize() {
            return this.f27937q.bitLength();
        }

        public BigInteger getQ() {
            return this.f27937q;
        }

        public int hashCode() {
            return this.f27937q.hashCode() ^ this.f27938x.hashCode();
        }

        @Override // org.bouncycastle.math.ec.ECFieldElement
        public ECFieldElement invert() {
            BigInteger bigInteger = this.f27937q;
            return new Fp(bigInteger, this.f27938x.modInverse(bigInteger));
        }

        @Override // org.bouncycastle.math.ec.ECFieldElement
        public ECFieldElement multiply(ECFieldElement eCFieldElement) {
            return new Fp(this.f27937q, this.f27938x.multiply(eCFieldElement.toBigInteger()).mod(this.f27937q));
        }

        @Override // org.bouncycastle.math.ec.ECFieldElement
        public ECFieldElement negate() {
            return new Fp(this.f27937q, this.f27938x.negate().mod(this.f27937q));
        }

        @Override // org.bouncycastle.math.ec.ECFieldElement
        public ECFieldElement sqrt() {
            if (!this.f27937q.testBit(0)) {
                throw new RuntimeException("not done yet");
            }
            if (this.f27937q.testBit(1)) {
                BigInteger bigInteger = this.f27937q;
                Fp fp = new Fp(bigInteger, this.f27938x.modPow(bigInteger.shiftRight(2).add(ECConstants.ONE), this.f27937q));
                if (fp.square().equals(this)) {
                    return fp;
                }
                return null;
            }
            BigInteger bigInteger2 = this.f27937q;
            BigInteger bigInteger3 = ECConstants.ONE;
            BigInteger bigIntegerSubtract = bigInteger2.subtract(bigInteger3);
            BigInteger bigIntegerShiftRight = bigIntegerSubtract.shiftRight(1);
            if (!this.f27938x.modPow(bigIntegerShiftRight, this.f27937q).equals(bigInteger3)) {
                return null;
            }
            BigInteger bigIntegerAdd = bigIntegerSubtract.shiftRight(2).shiftLeft(1).add(bigInteger3);
            BigInteger bigInteger4 = this.f27938x;
            BigInteger bigIntegerMod = bigInteger4.shiftLeft(2).mod(this.f27937q);
            Random random = new Random();
            while (true) {
                BigInteger bigInteger5 = new BigInteger(this.f27937q.bitLength(), random);
                if (bigInteger5.compareTo(this.f27937q) < 0 && bigInteger5.multiply(bigInteger5).subtract(bigIntegerMod).modPow(bigIntegerShiftRight, this.f27937q).equals(bigIntegerSubtract)) {
                    BigInteger[] bigIntegerArrLucasSequence = lucasSequence(this.f27937q, bigInteger5, bigInteger4, bigIntegerAdd);
                    BigInteger bigInteger6 = bigIntegerArrLucasSequence[0];
                    BigInteger bigIntegerAdd2 = bigIntegerArrLucasSequence[1];
                    if (bigIntegerAdd2.multiply(bigIntegerAdd2).mod(this.f27937q).equals(bigIntegerMod)) {
                        if (bigIntegerAdd2.testBit(0)) {
                            bigIntegerAdd2 = bigIntegerAdd2.add(this.f27937q);
                        }
                        return new Fp(this.f27937q, bigIntegerAdd2.shiftRight(1));
                    }
                    if (!bigInteger6.equals(ECConstants.ONE) && !bigInteger6.equals(bigIntegerSubtract)) {
                        return null;
                    }
                }
            }
        }

        @Override // org.bouncycastle.math.ec.ECFieldElement
        public ECFieldElement square() {
            BigInteger bigInteger = this.f27937q;
            BigInteger bigInteger2 = this.f27938x;
            return new Fp(bigInteger, bigInteger2.multiply(bigInteger2).mod(this.f27937q));
        }

        @Override // org.bouncycastle.math.ec.ECFieldElement
        public ECFieldElement subtract(ECFieldElement eCFieldElement) {
            return new Fp(this.f27937q, this.f27938x.subtract(eCFieldElement.toBigInteger()).mod(this.f27937q));
        }

        @Override // org.bouncycastle.math.ec.ECFieldElement
        public BigInteger toBigInteger() {
            return this.f27938x;
        }
    }

    public abstract ECFieldElement add(ECFieldElement eCFieldElement);

    public abstract ECFieldElement divide(ECFieldElement eCFieldElement);

    public abstract String getFieldName();

    public abstract int getFieldSize();

    public abstract ECFieldElement invert();

    public abstract ECFieldElement multiply(ECFieldElement eCFieldElement);

    public abstract ECFieldElement negate();

    public abstract ECFieldElement sqrt();

    public abstract ECFieldElement square();

    public abstract ECFieldElement subtract(ECFieldElement eCFieldElement);

    public abstract BigInteger toBigInteger();

    public String toString() {
        return toBigInteger().toString(2);
    }
}
