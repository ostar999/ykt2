package org.bouncycastle.asn1.cms;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.BERSequence;
import org.bouncycastle.asn1.DERIA5String;
import org.bouncycastle.asn1.DERInteger;
import org.bouncycastle.asn1.DERObject;

/* loaded from: classes9.dex */
public class TimeStampedData extends ASN1Encodable {
    private ASN1OctetString content;
    private DERIA5String dataUri;
    private MetaData metaData;
    private Evidence temporalEvidence;
    private DERInteger version;

    private TimeStampedData(ASN1Sequence aSN1Sequence) {
        this.version = DERInteger.getInstance(aSN1Sequence.getObjectAt(0));
        int i2 = 1;
        if (aSN1Sequence.getObjectAt(1) instanceof DERIA5String) {
            this.dataUri = DERIA5String.getInstance(aSN1Sequence.getObjectAt(1));
            i2 = 2;
        }
        if ((aSN1Sequence.getObjectAt(i2) instanceof MetaData) || (aSN1Sequence.getObjectAt(i2) instanceof ASN1Sequence)) {
            this.metaData = MetaData.getInstance(aSN1Sequence.getObjectAt(i2));
            i2++;
        }
        if (aSN1Sequence.getObjectAt(i2) instanceof ASN1OctetString) {
            this.content = ASN1OctetString.getInstance(aSN1Sequence.getObjectAt(i2));
            i2++;
        }
        this.temporalEvidence = Evidence.getInstance(aSN1Sequence.getObjectAt(i2));
    }

    public TimeStampedData(DERIA5String dERIA5String, MetaData metaData, ASN1OctetString aSN1OctetString, Evidence evidence) {
        this.version = new DERInteger(1);
        this.dataUri = dERIA5String;
        this.metaData = metaData;
        this.content = aSN1OctetString;
        this.temporalEvidence = evidence;
    }

    public static TimeStampedData getInstance(Object obj) {
        if (obj instanceof TimeStampedData) {
            return (TimeStampedData) obj;
        }
        if (obj != null) {
            return new TimeStampedData(ASN1Sequence.getInstance(obj));
        }
        return null;
    }

    public ASN1OctetString getContent() {
        return this.content;
    }

    public DERIA5String getDataUri() {
        return this.dataUri;
    }

    public MetaData getMetaData() {
        return this.metaData;
    }

    public Evidence getTemporalEvidence() {
        return this.temporalEvidence;
    }

    @Override // org.bouncycastle.asn1.ASN1Encodable
    public DERObject toASN1Object() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(this.version);
        DERIA5String dERIA5String = this.dataUri;
        if (dERIA5String != null) {
            aSN1EncodableVector.add(dERIA5String);
        }
        MetaData metaData = this.metaData;
        if (metaData != null) {
            aSN1EncodableVector.add(metaData);
        }
        ASN1OctetString aSN1OctetString = this.content;
        if (aSN1OctetString != null) {
            aSN1EncodableVector.add(aSN1OctetString);
        }
        aSN1EncodableVector.add(this.temporalEvidence);
        return new BERSequence(aSN1EncodableVector);
    }
}
