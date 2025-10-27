package org.bouncycastle.math.ec;

import java.math.BigInteger;
import java.util.Random;
import org.bouncycastle.math.ec.ECFieldElement;
import org.bouncycastle.math.ec.ECPoint;

/* loaded from: classes9.dex */
public abstract class ECCurve {

    /* renamed from: a, reason: collision with root package name */
    ECFieldElement f27926a;

    /* renamed from: b, reason: collision with root package name */
    ECFieldElement f27927b;

    public static class F2m extends ECCurve {

        /* renamed from: h, reason: collision with root package name */
        private BigInteger f27928h;
        private ECPoint.F2m infinity;

        /* renamed from: k1, reason: collision with root package name */
        private int f27929k1;
        private int k2;
        private int k3;

        /* renamed from: m, reason: collision with root package name */
        private int f27930m;
        private byte mu;

        /* renamed from: n, reason: collision with root package name */
        private BigInteger f27931n;
        private BigInteger[] si;

        public F2m(int i2, int i3, int i4, int i5, BigInteger bigInteger, BigInteger bigInteger2) {
            this(i2, i3, i4, i5, bigInteger, bigInteger2, null, null);
        }

        public F2m(int i2, int i3, int i4, int i5, BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3, BigInteger bigInteger4) {
            this.mu = (byte) 0;
            this.si = null;
            this.f27930m = i2;
            this.f27929k1 = i3;
            this.k2 = i4;
            this.k3 = i5;
            this.f27931n = bigInteger3;
            this.f27928h = bigInteger4;
            if (i3 == 0) {
                throw new IllegalArgumentException("k1 must be > 0");
            }
            if (i4 == 0) {
                if (i5 != 0) {
                    throw new IllegalArgumentException("k3 must be 0 if k2 == 0");
                }
            } else {
                if (i4 <= i3) {
                    throw new IllegalArgumentException("k2 must be > k1");
                }
                if (i5 <= i4) {
                    throw new IllegalArgumentException("k3 must be > k2");
                }
            }
            this.f27926a = fromBigInteger(bigInteger);
            this.f27927b = fromBigInteger(bigInteger2);
            this.infinity = new ECPoint.F2m(this, null, null);
        }

        public F2m(int i2, int i3, BigInteger bigInteger, BigInteger bigInteger2) {
            this(i2, i3, 0, 0, bigInteger, bigInteger2, null, null);
        }

        public F2m(int i2, int i3, BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3, BigInteger bigInteger4) {
            this(i2, i3, 0, 0, bigInteger, bigInteger2, bigInteger3, bigInteger4);
        }

        private ECPoint decompressPoint(byte[] bArr, int i2) {
            ECFieldElement eCFieldElementMultiply;
            ECFieldElement.F2m f2m = new ECFieldElement.F2m(this.f27930m, this.f27929k1, this.k2, this.k3, new BigInteger(1, bArr));
            if (f2m.toBigInteger().equals(ECConstants.ZERO)) {
                eCFieldElementMultiply = (ECFieldElement.F2m) this.f27927b;
                for (int i3 = 0; i3 < this.f27930m - 1; i3++) {
                    eCFieldElementMultiply = eCFieldElementMultiply.square();
                }
            } else {
                ECFieldElement eCFieldElementSolveQuadradicEquation = solveQuadradicEquation(f2m.add(this.f27926a).add(this.f27927b.multiply(f2m.square().invert())));
                if (eCFieldElementSolveQuadradicEquation == null) {
                    throw new RuntimeException("Invalid point compression");
                }
                if (eCFieldElementSolveQuadradicEquation.toBigInteger().testBit(0) != i2) {
                    eCFieldElementSolveQuadradicEquation = eCFieldElementSolveQuadradicEquation.add(new ECFieldElement.F2m(this.f27930m, this.f27929k1, this.k2, this.k3, ECConstants.ONE));
                }
                eCFieldElementMultiply = f2m.multiply(eCFieldElementSolveQuadradicEquation);
            }
            return new ECPoint.F2m(this, f2m, eCFieldElementMultiply);
        }

        private ECFieldElement solveQuadradicEquation(ECFieldElement eCFieldElement) {
            ECFieldElement eCFieldElementAdd;
            BigInteger bigInteger;
            int i2 = this.f27930m;
            int i3 = this.f27929k1;
            int i4 = this.k2;
            int i5 = this.k3;
            BigInteger bigInteger2 = ECConstants.ZERO;
            ECFieldElement.F2m f2m = new ECFieldElement.F2m(i2, i3, i4, i5, bigInteger2);
            if (eCFieldElement.toBigInteger().equals(bigInteger2)) {
                return f2m;
            }
            Random random = new Random();
            do {
                ECFieldElement.F2m f2m2 = new ECFieldElement.F2m(this.f27930m, this.f27929k1, this.k2, this.k3, new BigInteger(this.f27930m, random));
                ECFieldElement eCFieldElementAdd2 = eCFieldElement;
                eCFieldElementAdd = f2m;
                for (int i6 = 1; i6 <= this.f27930m - 1; i6++) {
                    ECFieldElement eCFieldElementSquare = eCFieldElementAdd2.square();
                    eCFieldElementAdd = eCFieldElementAdd.square().add(eCFieldElementSquare.multiply(f2m2));
                    eCFieldElementAdd2 = eCFieldElementSquare.add(eCFieldElement);
                }
                BigInteger bigInteger3 = eCFieldElementAdd2.toBigInteger();
                bigInteger = ECConstants.ZERO;
                if (!bigInteger3.equals(bigInteger)) {
                    return null;
                }
            } while (eCFieldElementAdd.square().add(eCFieldElementAdd).toBigInteger().equals(bigInteger));
            return eCFieldElementAdd;
        }

        @Override // org.bouncycastle.math.ec.ECCurve
        public ECPoint createPoint(BigInteger bigInteger, BigInteger bigInteger2, boolean z2) {
            return new ECPoint.F2m(this, fromBigInteger(bigInteger), fromBigInteger(bigInteger2), z2);
        }

        @Override // org.bouncycastle.math.ec.ECCurve
        public ECPoint decodePoint(byte[] bArr) {
            byte b3 = bArr[0];
            if (b3 == 0) {
                if (bArr.length <= 1) {
                    return getInfinity();
                }
                throw new RuntimeException("Invalid point encoding");
            }
            if (b3 == 2 || b3 == 3) {
                int length = bArr.length - 1;
                byte[] bArr2 = new byte[length];
                System.arraycopy(bArr, 1, bArr2, 0, length);
                return bArr[0] == 2 ? decompressPoint(bArr2, 0) : decompressPoint(bArr2, 1);
            }
            if (b3 != 4 && b3 != 6 && b3 != 7) {
                throw new RuntimeException("Invalid point encoding 0x" + Integer.toString(bArr[0], 16));
            }
            int length2 = (bArr.length - 1) / 2;
            byte[] bArr3 = new byte[length2];
            int length3 = (bArr.length - 1) / 2;
            byte[] bArr4 = new byte[length3];
            System.arraycopy(bArr, 1, bArr3, 0, length2);
            System.arraycopy(bArr, length2 + 1, bArr4, 0, length3);
            return new ECPoint.F2m(this, new ECFieldElement.F2m(this.f27930m, this.f27929k1, this.k2, this.k3, new BigInteger(1, bArr3)), new ECFieldElement.F2m(this.f27930m, this.f27929k1, this.k2, this.k3, new BigInteger(1, bArr4)), false);
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof F2m)) {
                return false;
            }
            F2m f2m = (F2m) obj;
            return this.f27930m == f2m.f27930m && this.f27929k1 == f2m.f27929k1 && this.k2 == f2m.k2 && this.k3 == f2m.k3 && this.f27926a.equals(f2m.f27926a) && this.f27927b.equals(f2m.f27927b);
        }

        @Override // org.bouncycastle.math.ec.ECCurve
        public ECFieldElement fromBigInteger(BigInteger bigInteger) {
            return new ECFieldElement.F2m(this.f27930m, this.f27929k1, this.k2, this.k3, bigInteger);
        }

        @Override // org.bouncycastle.math.ec.ECCurve
        public int getFieldSize() {
            return this.f27930m;
        }

        public BigInteger getH() {
            return this.f27928h;
        }

        @Override // org.bouncycastle.math.ec.ECCurve
        public ECPoint getInfinity() {
            return this.infinity;
        }

        public int getK1() {
            return this.f27929k1;
        }

        public int getK2() {
            return this.k2;
        }

        public int getK3() {
            return this.k3;
        }

        public int getM() {
            return this.f27930m;
        }

        public synchronized byte getMu() {
            if (this.mu == 0) {
                this.mu = Tnaf.getMu(this);
            }
            return this.mu;
        }

        public BigInteger getN() {
            return this.f27931n;
        }

        public synchronized BigInteger[] getSi() {
            if (this.si == null) {
                this.si = Tnaf.getSi(this);
            }
            return this.si;
        }

        public int hashCode() {
            return ((((this.f27926a.hashCode() ^ this.f27927b.hashCode()) ^ this.f27930m) ^ this.f27929k1) ^ this.k2) ^ this.k3;
        }

        public boolean isKoblitz() {
            return (this.f27931n == null || this.f27928h == null || (!this.f27926a.toBigInteger().equals(ECConstants.ZERO) && !this.f27926a.toBigInteger().equals(ECConstants.ONE)) || !this.f27927b.toBigInteger().equals(ECConstants.ONE)) ? false : true;
        }

        public boolean isTrinomial() {
            return this.k2 == 0 && this.k3 == 0;
        }
    }

    public static class Fp extends ECCurve {
        ECPoint.Fp infinity;

        /* renamed from: q, reason: collision with root package name */
        BigInteger f27932q;

        public Fp(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3) {
            this.f27932q = bigInteger;
            this.f27926a = fromBigInteger(bigInteger2);
            this.f27927b = fromBigInteger(bigInteger3);
            this.infinity = new ECPoint.Fp(this, null, null);
        }

        @Override // org.bouncycastle.math.ec.ECCurve
        public ECPoint createPoint(BigInteger bigInteger, BigInteger bigInteger2, boolean z2) {
            return new ECPoint.Fp(this, fromBigInteger(bigInteger), fromBigInteger(bigInteger2), z2);
        }

        @Override // org.bouncycastle.math.ec.ECCurve
        public ECPoint decodePoint(byte[] bArr) {
            ECPoint.Fp fp;
            byte b3 = bArr[0];
            if (b3 == 0) {
                if (bArr.length <= 1) {
                    return getInfinity();
                }
                throw new RuntimeException("Invalid point encoding");
            }
            if (b3 == 2 || b3 == 3) {
                int i2 = b3 & 1;
                int length = bArr.length - 1;
                byte[] bArr2 = new byte[length];
                System.arraycopy(bArr, 1, bArr2, 0, length);
                ECFieldElement.Fp fp2 = new ECFieldElement.Fp(this.f27932q, new BigInteger(1, bArr2));
                ECFieldElement eCFieldElementSqrt = fp2.multiply(fp2.square().add(this.f27926a)).add(this.f27927b).sqrt();
                if (eCFieldElementSqrt == null) {
                    throw new RuntimeException("Invalid point compression");
                }
                if (eCFieldElementSqrt.toBigInteger().testBit(0) == i2) {
                    fp = new ECPoint.Fp(this, fp2, eCFieldElementSqrt, true);
                } else {
                    BigInteger bigInteger = this.f27932q;
                    fp = new ECPoint.Fp(this, fp2, new ECFieldElement.Fp(bigInteger, bigInteger.subtract(eCFieldElementSqrt.toBigInteger())), true);
                }
                return fp;
            }
            if (b3 != 4 && b3 != 6 && b3 != 7) {
                throw new RuntimeException("Invalid point encoding 0x" + Integer.toString(bArr[0], 16));
            }
            int length2 = (bArr.length - 1) / 2;
            byte[] bArr3 = new byte[length2];
            int length3 = (bArr.length - 1) / 2;
            byte[] bArr4 = new byte[length3];
            System.arraycopy(bArr, 1, bArr3, 0, length2);
            System.arraycopy(bArr, length2 + 1, bArr4, 0, length3);
            return new ECPoint.Fp(this, new ECFieldElement.Fp(this.f27932q, new BigInteger(1, bArr3)), new ECFieldElement.Fp(this.f27932q, new BigInteger(1, bArr4)));
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof Fp)) {
                return false;
            }
            Fp fp = (Fp) obj;
            return this.f27932q.equals(fp.f27932q) && this.f27926a.equals(fp.f27926a) && this.f27927b.equals(fp.f27927b);
        }

        @Override // org.bouncycastle.math.ec.ECCurve
        public ECFieldElement fromBigInteger(BigInteger bigInteger) {
            return new ECFieldElement.Fp(this.f27932q, bigInteger);
        }

        @Override // org.bouncycastle.math.ec.ECCurve
        public int getFieldSize() {
            return this.f27932q.bitLength();
        }

        @Override // org.bouncycastle.math.ec.ECCurve
        public ECPoint getInfinity() {
            return this.infinity;
        }

        public BigInteger getQ() {
            return this.f27932q;
        }

        public int hashCode() {
            return (this.f27926a.hashCode() ^ this.f27927b.hashCode()) ^ this.f27932q.hashCode();
        }
    }

    public abstract ECPoint createPoint(BigInteger bigInteger, BigInteger bigInteger2, boolean z2);

    public abstract ECPoint decodePoint(byte[] bArr);

    public abstract ECFieldElement fromBigInteger(BigInteger bigInteger);

    public ECFieldElement getA() {
        return this.f27926a;
    }

    public ECFieldElement getB() {
        return this.f27927b;
    }

    public abstract int getFieldSize();

    public abstract ECPoint getInfinity();
}
