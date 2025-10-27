package org.bouncycastle.asn1.x9;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DERInteger;
import org.bouncycastle.asn1.DERObject;

/* loaded from: classes9.dex */
public class DHPublicKey extends ASN1Encodable {

    /* renamed from: y, reason: collision with root package name */
    private DERInteger f27786y;

    public DHPublicKey(DERInteger dERInteger) {
        if (dERInteger == null) {
            throw new IllegalArgumentException("'y' cannot be null");
        }
        this.f27786y = dERInteger;
    }

    public static DHPublicKey getInstance(Object obj) {
        if (obj == null || (obj instanceof DHPublicKey)) {
            return (DHPublicKey) obj;
        }
        if (obj instanceof DERInteger) {
            return new DHPublicKey((DERInteger) obj);
        }
        throw new IllegalArgumentException("Invalid DHPublicKey: " + obj.getClass().getName());
    }

    public static DHPublicKey getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z2) {
        return getInstance(DERInteger.getInstance(aSN1TaggedObject, z2));
    }

    public DERInteger getY() {
        return this.f27786y;
    }

    @Override // org.bouncycastle.asn1.ASN1Encodable
    public DERObject toASN1Object() {
        return this.f27786y;
    }
}
