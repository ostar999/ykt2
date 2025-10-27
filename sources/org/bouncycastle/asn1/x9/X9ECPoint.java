package org.bouncycastle.asn1.x9;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.DERObject;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.math.ec.ECCurve;
import org.bouncycastle.math.ec.ECPoint;

/* loaded from: classes9.dex */
public class X9ECPoint extends ASN1Encodable {

    /* renamed from: p, reason: collision with root package name */
    ECPoint f27790p;

    public X9ECPoint(ECCurve eCCurve, ASN1OctetString aSN1OctetString) {
        this.f27790p = eCCurve.decodePoint(aSN1OctetString.getOctets());
    }

    public X9ECPoint(ECPoint eCPoint) {
        this.f27790p = eCPoint;
    }

    public ECPoint getPoint() {
        return this.f27790p;
    }

    @Override // org.bouncycastle.asn1.ASN1Encodable
    public DERObject toASN1Object() {
        return new DEROctetString(this.f27790p.getEncoded());
    }
}
