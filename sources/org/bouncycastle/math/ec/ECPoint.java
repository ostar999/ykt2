package org.bouncycastle.math.ec;

import java.math.BigInteger;
import org.bouncycastle.asn1.x9.X9IntegerConverter;
import org.bouncycastle.math.ec.ECCurve;
import org.bouncycastle.math.ec.ECFieldElement;

/* loaded from: classes9.dex */
public abstract class ECPoint {
    private static X9IntegerConverter converter = new X9IntegerConverter();
    ECCurve curve;
    protected ECMultiplier multiplier = null;
    protected PreCompInfo preCompInfo = null;
    protected boolean withCompression;

    /* renamed from: x, reason: collision with root package name */
    ECFieldElement f27939x;

    /* renamed from: y, reason: collision with root package name */
    ECFieldElement f27940y;

    public static class F2m extends ECPoint {
        public F2m(ECCurve eCCurve, ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2) {
            this(eCCurve, eCFieldElement, eCFieldElement2, false);
        }

        public F2m(ECCurve eCCurve, ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2, boolean z2) {
            super(eCCurve, eCFieldElement, eCFieldElement2);
            if ((eCFieldElement != null && eCFieldElement2 == null) || (eCFieldElement == null && eCFieldElement2 != null)) {
                throw new IllegalArgumentException("Exactly one of the field elements is null");
            }
            if (eCFieldElement != null) {
                ECFieldElement.F2m.checkFieldElements(this.f27939x, this.f27940y);
                if (eCCurve != null) {
                    ECFieldElement.F2m.checkFieldElements(this.f27939x, this.curve.getA());
                }
            }
            this.withCompression = z2;
        }

        private static void checkPoints(ECPoint eCPoint, ECPoint eCPoint2) {
            if (!eCPoint.curve.equals(eCPoint2.curve)) {
                throw new IllegalArgumentException("Only points on the same curve can be added or subtracted");
            }
        }

        @Override // org.bouncycastle.math.ec.ECPoint
        public ECPoint add(ECPoint eCPoint) {
            checkPoints(this, eCPoint);
            return addSimple((F2m) eCPoint);
        }

        public F2m addSimple(F2m f2m) {
            if (isInfinity()) {
                return f2m;
            }
            if (f2m.isInfinity()) {
                return this;
            }
            ECFieldElement.F2m f2m2 = (ECFieldElement.F2m) f2m.getX();
            ECFieldElement.F2m f2m3 = (ECFieldElement.F2m) f2m.getY();
            if (this.f27939x.equals(f2m2)) {
                return (F2m) (this.f27940y.equals(f2m3) ? twice() : this.curve.getInfinity());
            }
            ECFieldElement eCFieldElement = (ECFieldElement.F2m) this.f27940y.add(f2m3).divide(this.f27939x.add(f2m2));
            ECFieldElement.F2m f2m4 = (ECFieldElement.F2m) eCFieldElement.square().add(eCFieldElement).add(this.f27939x).add(f2m2).add(this.curve.getA());
            return new F2m(this.curve, f2m4, (ECFieldElement.F2m) eCFieldElement.multiply(this.f27939x.add(f2m4)).add(f2m4).add(this.f27940y), this.withCompression);
        }

        @Override // org.bouncycastle.math.ec.ECPoint
        public synchronized void assertECMultiplier() {
            if (this.multiplier == null) {
                this.multiplier = ((ECCurve.F2m) this.curve).isKoblitz() ? new WTauNafMultiplier() : new WNafMultiplier();
            }
        }

        @Override // org.bouncycastle.math.ec.ECPoint
        public byte[] getEncoded() {
            if (isInfinity()) {
                return new byte[1];
            }
            int byteLength = ECPoint.converter.getByteLength(this.f27939x);
            byte[] bArrIntegerToBytes = ECPoint.converter.integerToBytes(getX().toBigInteger(), byteLength);
            if (!this.withCompression) {
                byte[] bArrIntegerToBytes2 = ECPoint.converter.integerToBytes(getY().toBigInteger(), byteLength);
                byte[] bArr = new byte[byteLength + byteLength + 1];
                bArr[0] = 4;
                System.arraycopy(bArrIntegerToBytes, 0, bArr, 1, byteLength);
                System.arraycopy(bArrIntegerToBytes2, 0, bArr, byteLength + 1, byteLength);
                return bArr;
            }
            byte[] bArr2 = new byte[byteLength + 1];
            bArr2[0] = 2;
            if (!getX().toBigInteger().equals(ECConstants.ZERO) && getY().multiply(getX().invert()).toBigInteger().testBit(0)) {
                bArr2[0] = 3;
            }
            System.arraycopy(bArrIntegerToBytes, 0, bArr2, 1, byteLength);
            return bArr2;
        }

        @Override // org.bouncycastle.math.ec.ECPoint
        public ECPoint negate() {
            return new F2m(this.curve, getX(), getY().add(getX()), this.withCompression);
        }

        @Override // org.bouncycastle.math.ec.ECPoint
        public ECPoint subtract(ECPoint eCPoint) {
            checkPoints(this, eCPoint);
            return subtractSimple((F2m) eCPoint);
        }

        public F2m subtractSimple(F2m f2m) {
            return f2m.isInfinity() ? this : addSimple((F2m) f2m.negate());
        }

        @Override // org.bouncycastle.math.ec.ECPoint
        public ECPoint twice() {
            if (isInfinity()) {
                return this;
            }
            if (this.f27939x.toBigInteger().signum() == 0) {
                return this.curve.getInfinity();
            }
            ECFieldElement eCFieldElement = this.f27939x;
            ECFieldElement eCFieldElement2 = (ECFieldElement.F2m) eCFieldElement.add(this.f27940y.divide(eCFieldElement));
            ECFieldElement.F2m f2m = (ECFieldElement.F2m) eCFieldElement2.square().add(eCFieldElement2).add(this.curve.getA());
            return new F2m(this.curve, f2m, (ECFieldElement.F2m) this.f27939x.square().add(f2m.multiply(eCFieldElement2.add(this.curve.fromBigInteger(ECConstants.ONE)))), this.withCompression);
        }
    }

    public static class Fp extends ECPoint {
        public Fp(ECCurve eCCurve, ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2) {
            this(eCCurve, eCFieldElement, eCFieldElement2, false);
        }

        public Fp(ECCurve eCCurve, ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2, boolean z2) {
            super(eCCurve, eCFieldElement, eCFieldElement2);
            if ((eCFieldElement != null && eCFieldElement2 == null) || (eCFieldElement == null && eCFieldElement2 != null)) {
                throw new IllegalArgumentException("Exactly one of the field elements is null");
            }
            this.withCompression = z2;
        }

        @Override // org.bouncycastle.math.ec.ECPoint
        public ECPoint add(ECPoint eCPoint) {
            if (isInfinity()) {
                return eCPoint;
            }
            if (eCPoint.isInfinity()) {
                return this;
            }
            if (this.f27939x.equals(eCPoint.f27939x)) {
                return this.f27940y.equals(eCPoint.f27940y) ? twice() : this.curve.getInfinity();
            }
            ECFieldElement eCFieldElementDivide = eCPoint.f27940y.subtract(this.f27940y).divide(eCPoint.f27939x.subtract(this.f27939x));
            ECFieldElement eCFieldElementSubtract = eCFieldElementDivide.square().subtract(this.f27939x).subtract(eCPoint.f27939x);
            return new Fp(this.curve, eCFieldElementSubtract, eCFieldElementDivide.multiply(this.f27939x.subtract(eCFieldElementSubtract)).subtract(this.f27940y));
        }

        @Override // org.bouncycastle.math.ec.ECPoint
        public synchronized void assertECMultiplier() {
            if (this.multiplier == null) {
                this.multiplier = new WNafMultiplier();
            }
        }

        @Override // org.bouncycastle.math.ec.ECPoint
        public byte[] getEncoded() {
            if (isInfinity()) {
                return new byte[1];
            }
            int byteLength = ECPoint.converter.getByteLength(this.f27939x);
            if (this.withCompression) {
                byte b3 = getY().toBigInteger().testBit(0) ? (byte) 3 : (byte) 2;
                byte[] bArrIntegerToBytes = ECPoint.converter.integerToBytes(getX().toBigInteger(), byteLength);
                byte[] bArr = new byte[bArrIntegerToBytes.length + 1];
                bArr[0] = b3;
                System.arraycopy(bArrIntegerToBytes, 0, bArr, 1, bArrIntegerToBytes.length);
                return bArr;
            }
            byte[] bArrIntegerToBytes2 = ECPoint.converter.integerToBytes(getX().toBigInteger(), byteLength);
            byte[] bArrIntegerToBytes3 = ECPoint.converter.integerToBytes(getY().toBigInteger(), byteLength);
            byte[] bArr2 = new byte[bArrIntegerToBytes2.length + bArrIntegerToBytes3.length + 1];
            bArr2[0] = 4;
            System.arraycopy(bArrIntegerToBytes2, 0, bArr2, 1, bArrIntegerToBytes2.length);
            System.arraycopy(bArrIntegerToBytes3, 0, bArr2, bArrIntegerToBytes2.length + 1, bArrIntegerToBytes3.length);
            return bArr2;
        }

        @Override // org.bouncycastle.math.ec.ECPoint
        public ECPoint negate() {
            return new Fp(this.curve, this.f27939x, this.f27940y.negate(), this.withCompression);
        }

        @Override // org.bouncycastle.math.ec.ECPoint
        public ECPoint subtract(ECPoint eCPoint) {
            return eCPoint.isInfinity() ? this : add(eCPoint.negate());
        }

        @Override // org.bouncycastle.math.ec.ECPoint
        public ECPoint twice() {
            if (isInfinity()) {
                return this;
            }
            if (this.f27940y.toBigInteger().signum() == 0) {
                return this.curve.getInfinity();
            }
            ECFieldElement eCFieldElementFromBigInteger = this.curve.fromBigInteger(BigInteger.valueOf(2L));
            ECFieldElement eCFieldElementDivide = this.f27939x.square().multiply(this.curve.fromBigInteger(BigInteger.valueOf(3L))).add(this.curve.f27926a).divide(this.f27940y.multiply(eCFieldElementFromBigInteger));
            ECFieldElement eCFieldElementSubtract = eCFieldElementDivide.square().subtract(this.f27939x.multiply(eCFieldElementFromBigInteger));
            return new Fp(this.curve, eCFieldElementSubtract, eCFieldElementDivide.multiply(this.f27939x.subtract(eCFieldElementSubtract)).subtract(this.f27940y), this.withCompression);
        }
    }

    public ECPoint(ECCurve eCCurve, ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2) {
        this.curve = eCCurve;
        this.f27939x = eCFieldElement;
        this.f27940y = eCFieldElement2;
    }

    public abstract ECPoint add(ECPoint eCPoint);

    public synchronized void assertECMultiplier() {
        if (this.multiplier == null) {
            this.multiplier = new FpNafMultiplier();
        }
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ECPoint)) {
            return false;
        }
        ECPoint eCPoint = (ECPoint) obj;
        return isInfinity() ? eCPoint.isInfinity() : this.f27939x.equals(eCPoint.f27939x) && this.f27940y.equals(eCPoint.f27940y);
    }

    public ECCurve getCurve() {
        return this.curve;
    }

    public abstract byte[] getEncoded();

    public ECFieldElement getX() {
        return this.f27939x;
    }

    public ECFieldElement getY() {
        return this.f27940y;
    }

    public int hashCode() {
        if (isInfinity()) {
            return 0;
        }
        return this.f27939x.hashCode() ^ this.f27940y.hashCode();
    }

    public boolean isCompressed() {
        return this.withCompression;
    }

    public boolean isInfinity() {
        return this.f27939x == null && this.f27940y == null;
    }

    public ECPoint multiply(BigInteger bigInteger) {
        if (bigInteger.signum() < 0) {
            throw new IllegalArgumentException("The multiplicator cannot be negative");
        }
        if (isInfinity()) {
            return this;
        }
        if (bigInteger.signum() == 0) {
            return this.curve.getInfinity();
        }
        assertECMultiplier();
        return this.multiplier.multiply(this, bigInteger, this.preCompInfo);
    }

    public abstract ECPoint negate();

    public void setPreCompInfo(PreCompInfo preCompInfo) {
        this.preCompInfo = preCompInfo;
    }

    public abstract ECPoint subtract(ECPoint eCPoint);

    public abstract ECPoint twice();
}
