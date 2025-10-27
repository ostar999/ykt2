package org.bouncycastle.asn1.x9;

import java.math.BigInteger;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.DERObject;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.math.ec.ECFieldElement;

/* loaded from: classes9.dex */
public class X9FieldElement extends ASN1Encodable {
    private static X9IntegerConverter converter = new X9IntegerConverter();

    /* renamed from: f, reason: collision with root package name */
    protected ECFieldElement f27791f;

    public X9FieldElement(int i2, int i3, int i4, int i5, ASN1OctetString aSN1OctetString) {
        this(new ECFieldElement.F2m(i2, i3, i4, i5, new BigInteger(1, aSN1OctetString.getOctets())));
    }

    public X9FieldElement(BigInteger bigInteger, ASN1OctetString aSN1OctetString) {
        this(new ECFieldElement.Fp(bigInteger, new BigInteger(1, aSN1OctetString.getOctets())));
    }

    public X9FieldElement(ECFieldElement eCFieldElement) {
        this.f27791f = eCFieldElement;
    }

    public ECFieldElement getValue() {
        return this.f27791f;
    }

    @Override // org.bouncycastle.asn1.ASN1Encodable
    public DERObject toASN1Object() {
        return new DEROctetString(converter.integerToBytes(this.f27791f.toBigInteger(), converter.getByteLength(this.f27791f)));
    }
}
