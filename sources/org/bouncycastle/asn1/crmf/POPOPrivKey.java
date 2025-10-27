package org.bouncycastle.asn1.crmf;

import org.bouncycastle.asn1.ASN1Choice;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DERObject;
import org.bouncycastle.asn1.DERTaggedObject;

/* loaded from: classes9.dex */
public class POPOPrivKey extends ASN1Encodable implements ASN1Choice {
    public static final int agreeMAC = 3;
    public static final int dhMAC = 2;
    public static final int encryptedKey = 4;
    public static final int subsequentMessage = 1;
    public static final int thisMessage = 0;
    private ASN1Encodable obj;
    private int tagNo;

    /* JADX WARN: Removed duplicated region for block: B:17:0x003b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private POPOPrivKey(org.bouncycastle.asn1.ASN1TaggedObject r4) {
        /*
            r3 = this;
            r3.<init>()
            int r0 = r4.getTagNo()
            r3.tagNo = r0
            r1 = 0
            if (r0 == 0) goto L3b
            r2 = 1
            if (r0 == r2) goto L2a
            r2 = 2
            if (r0 == r2) goto L3b
            r2 = 3
            if (r0 == r2) goto L25
            r2 = 4
            if (r0 != r2) goto L1d
            org.bouncycastle.asn1.cms.EnvelopedData r4 = org.bouncycastle.asn1.cms.EnvelopedData.getInstance(r4, r1)
            goto L3f
        L1d:
            java.lang.IllegalArgumentException r4 = new java.lang.IllegalArgumentException
            java.lang.String r0 = "unknown tag in POPOPrivKey"
            r4.<init>(r0)
            throw r4
        L25:
            org.bouncycastle.asn1.crmf.PKMACValue r4 = org.bouncycastle.asn1.crmf.PKMACValue.getInstance(r4, r1)
            goto L3f
        L2a:
            org.bouncycastle.asn1.DERInteger r4 = org.bouncycastle.asn1.DERInteger.getInstance(r4, r1)
            java.math.BigInteger r4 = r4.getValue()
            int r4 = r4.intValue()
            org.bouncycastle.asn1.crmf.SubsequentMessage r4 = org.bouncycastle.asn1.crmf.SubsequentMessage.valueOf(r4)
            goto L3f
        L3b:
            org.bouncycastle.asn1.DERBitString r4 = org.bouncycastle.asn1.DERBitString.getInstance(r4, r1)
        L3f:
            r3.obj = r4
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.asn1.crmf.POPOPrivKey.<init>(org.bouncycastle.asn1.ASN1TaggedObject):void");
    }

    public POPOPrivKey(SubsequentMessage subsequentMessage2) {
        this.tagNo = 1;
        this.obj = subsequentMessage2;
    }

    public static POPOPrivKey getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z2) {
        return new POPOPrivKey(ASN1TaggedObject.getInstance(aSN1TaggedObject.getObject()));
    }

    public int getType() {
        return this.tagNo;
    }

    public ASN1Encodable getValue() {
        return this.obj;
    }

    @Override // org.bouncycastle.asn1.ASN1Encodable
    public DERObject toASN1Object() {
        return new DERTaggedObject(false, this.tagNo, this.obj);
    }
}
