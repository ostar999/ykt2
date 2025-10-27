package org.bouncycastle.asn1.pkcs;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.BERSequence;
import org.bouncycastle.asn1.DERObject;

/* loaded from: classes9.dex */
public class AuthenticatedSafe extends ASN1Encodable {
    ContentInfo[] info;

    public AuthenticatedSafe(ASN1Sequence aSN1Sequence) {
        this.info = new ContentInfo[aSN1Sequence.size()];
        int i2 = 0;
        while (true) {
            ContentInfo[] contentInfoArr = this.info;
            if (i2 == contentInfoArr.length) {
                return;
            }
            contentInfoArr[i2] = ContentInfo.getInstance(aSN1Sequence.getObjectAt(i2));
            i2++;
        }
    }

    public AuthenticatedSafe(ContentInfo[] contentInfoArr) {
        this.info = contentInfoArr;
    }

    public ContentInfo[] getContentInfo() {
        return this.info;
    }

    @Override // org.bouncycastle.asn1.ASN1Encodable
    public DERObject toASN1Object() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        int i2 = 0;
        while (true) {
            ContentInfo[] contentInfoArr = this.info;
            if (i2 == contentInfoArr.length) {
                return new BERSequence(aSN1EncodableVector);
            }
            aSN1EncodableVector.add(contentInfoArr[i2]);
            i2++;
        }
    }
}
