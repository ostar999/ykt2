package org.bouncycastle.jce.provider.asymmetric.ec;

import java.math.BigInteger;
import java.security.spec.ECField;
import java.security.spec.ECFieldF2m;
import java.security.spec.ECFieldFp;
import java.security.spec.ECParameterSpec;
import java.security.spec.EllipticCurve;
import org.bouncycastle.jce.spec.ECNamedCurveParameterSpec;
import org.bouncycastle.jce.spec.ECNamedCurveSpec;
import org.bouncycastle.math.ec.ECCurve;
import org.bouncycastle.math.ec.ECPoint;

/* loaded from: classes9.dex */
public class EC5Util {
    public static EllipticCurve convertCurve(ECCurve eCCurve, byte[] bArr) {
        if (eCCurve instanceof ECCurve.Fp) {
            return new EllipticCurve(new ECFieldFp(((ECCurve.Fp) eCCurve).getQ()), eCCurve.getA().toBigInteger(), eCCurve.getB().toBigInteger(), null);
        }
        ECCurve.F2m f2m = (ECCurve.F2m) eCCurve;
        return f2m.isTrinomial() ? new EllipticCurve(new ECFieldF2m(f2m.getM(), new int[]{f2m.getK1()}), eCCurve.getA().toBigInteger(), eCCurve.getB().toBigInteger(), null) : new EllipticCurve(new ECFieldF2m(f2m.getM(), new int[]{f2m.getK3(), f2m.getK2(), f2m.getK1()}), eCCurve.getA().toBigInteger(), eCCurve.getB().toBigInteger(), null);
    }

    public static ECCurve convertCurve(EllipticCurve ellipticCurve) {
        ECField field = ellipticCurve.getField();
        BigInteger a3 = ellipticCurve.getA();
        BigInteger b3 = ellipticCurve.getB();
        if (field instanceof ECFieldFp) {
            return new ECCurve.Fp(((ECFieldFp) field).getP(), a3, b3);
        }
        ECFieldF2m eCFieldF2m = (ECFieldF2m) field;
        int m2 = eCFieldF2m.getM();
        int[] iArrConvertMidTerms = ECUtil.convertMidTerms(eCFieldF2m.getMidTermsOfReductionPolynomial());
        return new ECCurve.F2m(m2, iArrConvertMidTerms[0], iArrConvertMidTerms[1], iArrConvertMidTerms[2], a3, b3);
    }

    public static ECPoint convertPoint(ECParameterSpec eCParameterSpec, java.security.spec.ECPoint eCPoint, boolean z2) {
        return convertPoint(convertCurve(eCParameterSpec.getCurve()), eCPoint, z2);
    }

    public static ECPoint convertPoint(ECCurve eCCurve, java.security.spec.ECPoint eCPoint, boolean z2) {
        return eCCurve.createPoint(eCPoint.getAffineX(), eCPoint.getAffineY(), z2);
    }

    public static ECParameterSpec convertSpec(EllipticCurve ellipticCurve, org.bouncycastle.jce.spec.ECParameterSpec eCParameterSpec) {
        return eCParameterSpec instanceof ECNamedCurveParameterSpec ? new ECNamedCurveSpec(((ECNamedCurveParameterSpec) eCParameterSpec).getName(), ellipticCurve, new java.security.spec.ECPoint(eCParameterSpec.getG().getX().toBigInteger(), eCParameterSpec.getG().getY().toBigInteger()), eCParameterSpec.getN(), eCParameterSpec.getH()) : new ECParameterSpec(ellipticCurve, new java.security.spec.ECPoint(eCParameterSpec.getG().getX().toBigInteger(), eCParameterSpec.getG().getY().toBigInteger()), eCParameterSpec.getN(), eCParameterSpec.getH().intValue());
    }

    public static org.bouncycastle.jce.spec.ECParameterSpec convertSpec(ECParameterSpec eCParameterSpec, boolean z2) {
        ECCurve eCCurveConvertCurve = convertCurve(eCParameterSpec.getCurve());
        return new org.bouncycastle.jce.spec.ECParameterSpec(eCCurveConvertCurve, convertPoint(eCCurveConvertCurve, eCParameterSpec.getGenerator(), z2), eCParameterSpec.getOrder(), BigInteger.valueOf(eCParameterSpec.getCofactor()), eCParameterSpec.getCurve().getSeed());
    }
}
