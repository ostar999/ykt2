package org.bouncycastle.asn1.x509;

import cn.hutool.core.text.StrPool;
import k.a;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DERBitString;
import org.bouncycastle.asn1.DERBoolean;
import org.bouncycastle.asn1.DERObject;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.DERTaggedObject;

/* loaded from: classes9.dex */
public class IssuingDistributionPoint extends ASN1Encodable {
    private DistributionPointName distributionPoint;
    private boolean indirectCRL;
    private boolean onlyContainsAttributeCerts;
    private boolean onlyContainsCACerts;
    private boolean onlyContainsUserCerts;
    private ReasonFlags onlySomeReasons;
    private ASN1Sequence seq;

    public IssuingDistributionPoint(ASN1Sequence aSN1Sequence) {
        this.seq = aSN1Sequence;
        for (int i2 = 0; i2 != aSN1Sequence.size(); i2++) {
            ASN1TaggedObject aSN1TaggedObject = ASN1TaggedObject.getInstance(aSN1Sequence.getObjectAt(i2));
            int tagNo = aSN1TaggedObject.getTagNo();
            if (tagNo == 0) {
                this.distributionPoint = DistributionPointName.getInstance(aSN1TaggedObject, true);
            } else if (tagNo == 1) {
                this.onlyContainsUserCerts = DERBoolean.getInstance(aSN1TaggedObject, false).isTrue();
            } else if (tagNo == 2) {
                this.onlyContainsCACerts = DERBoolean.getInstance(aSN1TaggedObject, false).isTrue();
            } else if (tagNo == 3) {
                this.onlySomeReasons = new ReasonFlags(DERBitString.getInstance(aSN1TaggedObject, false));
            } else if (tagNo == 4) {
                this.indirectCRL = DERBoolean.getInstance(aSN1TaggedObject, false).isTrue();
            } else {
                if (tagNo != 5) {
                    throw new IllegalArgumentException("unknown tag in IssuingDistributionPoint");
                }
                this.onlyContainsAttributeCerts = DERBoolean.getInstance(aSN1TaggedObject, false).isTrue();
            }
        }
    }

    public IssuingDistributionPoint(DistributionPointName distributionPointName, boolean z2, boolean z3, ReasonFlags reasonFlags, boolean z4, boolean z5) {
        this.distributionPoint = distributionPointName;
        this.indirectCRL = z4;
        this.onlyContainsAttributeCerts = z5;
        this.onlyContainsCACerts = z3;
        this.onlyContainsUserCerts = z2;
        this.onlySomeReasons = reasonFlags;
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        if (distributionPointName != null) {
            aSN1EncodableVector.add(new DERTaggedObject(true, 0, distributionPointName));
        }
        if (z2) {
            aSN1EncodableVector.add(new DERTaggedObject(false, 1, new DERBoolean(true)));
        }
        if (z3) {
            aSN1EncodableVector.add(new DERTaggedObject(false, 2, new DERBoolean(true)));
        }
        if (reasonFlags != null) {
            aSN1EncodableVector.add(new DERTaggedObject(false, 3, reasonFlags));
        }
        if (z4) {
            aSN1EncodableVector.add(new DERTaggedObject(false, 4, new DERBoolean(true)));
        }
        if (z5) {
            aSN1EncodableVector.add(new DERTaggedObject(false, 5, new DERBoolean(true)));
        }
        this.seq = new DERSequence(aSN1EncodableVector);
    }

    private void appendObject(StringBuffer stringBuffer, String str, String str2, String str3) {
        stringBuffer.append("    ");
        stringBuffer.append(str2);
        stringBuffer.append(":");
        stringBuffer.append(str);
        stringBuffer.append("    ");
        stringBuffer.append("    ");
        stringBuffer.append(str3);
        stringBuffer.append(str);
    }

    private String booleanToString(boolean z2) {
        return z2 ? a.f27523u : a.f27524v;
    }

    public static IssuingDistributionPoint getInstance(Object obj) {
        if (obj == null || (obj instanceof IssuingDistributionPoint)) {
            return (IssuingDistributionPoint) obj;
        }
        if (obj instanceof ASN1Sequence) {
            return new IssuingDistributionPoint((ASN1Sequence) obj);
        }
        throw new IllegalArgumentException("unknown object in factory: " + obj.getClass().getName());
    }

    public static IssuingDistributionPoint getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z2) {
        return getInstance(ASN1Sequence.getInstance(aSN1TaggedObject, z2));
    }

    public DistributionPointName getDistributionPoint() {
        return this.distributionPoint;
    }

    public ReasonFlags getOnlySomeReasons() {
        return this.onlySomeReasons;
    }

    public boolean isIndirectCRL() {
        return this.indirectCRL;
    }

    public boolean onlyContainsAttributeCerts() {
        return this.onlyContainsAttributeCerts;
    }

    public boolean onlyContainsCACerts() {
        return this.onlyContainsCACerts;
    }

    public boolean onlyContainsUserCerts() {
        return this.onlyContainsUserCerts;
    }

    @Override // org.bouncycastle.asn1.ASN1Encodable
    public DERObject toASN1Object() {
        return this.seq;
    }

    public String toString() {
        String property = System.getProperty("line.separator");
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("IssuingDistributionPoint: [");
        stringBuffer.append(property);
        DistributionPointName distributionPointName = this.distributionPoint;
        if (distributionPointName != null) {
            appendObject(stringBuffer, property, "distributionPoint", distributionPointName.toString());
        }
        boolean z2 = this.onlyContainsUserCerts;
        if (z2) {
            appendObject(stringBuffer, property, "onlyContainsUserCerts", booleanToString(z2));
        }
        boolean z3 = this.onlyContainsCACerts;
        if (z3) {
            appendObject(stringBuffer, property, "onlyContainsCACerts", booleanToString(z3));
        }
        ReasonFlags reasonFlags = this.onlySomeReasons;
        if (reasonFlags != null) {
            appendObject(stringBuffer, property, "onlySomeReasons", reasonFlags.toString());
        }
        boolean z4 = this.onlyContainsAttributeCerts;
        if (z4) {
            appendObject(stringBuffer, property, "onlyContainsAttributeCerts", booleanToString(z4));
        }
        boolean z5 = this.indirectCRL;
        if (z5) {
            appendObject(stringBuffer, property, "indirectCRL", booleanToString(z5));
        }
        stringBuffer.append(StrPool.BRACKET_END);
        stringBuffer.append(property);
        return stringBuffer.toString();
    }
}
