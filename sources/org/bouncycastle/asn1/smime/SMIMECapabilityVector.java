package org.bouncycastle.asn1.smime;

import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.DEREncodable;
import org.bouncycastle.asn1.DERInteger;
import org.bouncycastle.asn1.DERObjectIdentifier;
import org.bouncycastle.asn1.DERSequence;

/* loaded from: classes9.dex */
public class SMIMECapabilityVector {
    private ASN1EncodableVector capabilities = new ASN1EncodableVector();

    public void addCapability(DERObjectIdentifier dERObjectIdentifier) {
        this.capabilities.add(new DERSequence(dERObjectIdentifier));
    }

    public void addCapability(DERObjectIdentifier dERObjectIdentifier, int i2) {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(dERObjectIdentifier);
        aSN1EncodableVector.add(new DERInteger(i2));
        this.capabilities.add(new DERSequence(aSN1EncodableVector));
    }

    public void addCapability(DERObjectIdentifier dERObjectIdentifier, DEREncodable dEREncodable) {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(dERObjectIdentifier);
        aSN1EncodableVector.add(dEREncodable);
        this.capabilities.add(new DERSequence(aSN1EncodableVector));
    }

    public ASN1EncodableVector toASN1EncodableVector() {
        return this.capabilities;
    }
}
