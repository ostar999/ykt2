package org.bouncycastle.asn1.x509;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERObject;
import org.bouncycastle.asn1.DERObjectIdentifier;
import org.bouncycastle.asn1.DERSequence;

/* loaded from: classes9.dex */
public class AuthorityInformationAccess extends ASN1Encodable {
    private AccessDescription[] descriptions;

    public AuthorityInformationAccess(ASN1Sequence aSN1Sequence) {
        if (aSN1Sequence.size() < 1) {
            throw new IllegalArgumentException("sequence may not be empty");
        }
        this.descriptions = new AccessDescription[aSN1Sequence.size()];
        for (int i2 = 0; i2 != aSN1Sequence.size(); i2++) {
            this.descriptions[i2] = AccessDescription.getInstance(aSN1Sequence.getObjectAt(i2));
        }
    }

    public AuthorityInformationAccess(DERObjectIdentifier dERObjectIdentifier, GeneralName generalName) {
        this.descriptions = new AccessDescription[]{new AccessDescription(dERObjectIdentifier, generalName)};
    }

    public static AuthorityInformationAccess getInstance(Object obj) {
        if (obj instanceof AuthorityInformationAccess) {
            return (AuthorityInformationAccess) obj;
        }
        if (obj instanceof ASN1Sequence) {
            return new AuthorityInformationAccess((ASN1Sequence) obj);
        }
        if (obj instanceof X509Extension) {
            return getInstance(X509Extension.convertValueToObject((X509Extension) obj));
        }
        throw new IllegalArgumentException("unknown object in factory: " + obj.getClass().getName());
    }

    public AccessDescription[] getAccessDescriptions() {
        return this.descriptions;
    }

    @Override // org.bouncycastle.asn1.ASN1Encodable
    public DERObject toASN1Object() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        int i2 = 0;
        while (true) {
            AccessDescription[] accessDescriptionArr = this.descriptions;
            if (i2 == accessDescriptionArr.length) {
                return new DERSequence(aSN1EncodableVector);
            }
            aSN1EncodableVector.add(accessDescriptionArr[i2]);
            i2++;
        }
    }

    public String toString() {
        return "AuthorityInformationAccess: Oid(" + this.descriptions[0].getAccessMethod().getId() + ")";
    }
}
