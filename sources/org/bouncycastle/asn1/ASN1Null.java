package org.bouncycastle.asn1;

import com.plv.livescenes.hiclass.vo.PLVHCLessonSimpleInfoResultVO;
import java.io.IOException;

/* loaded from: classes9.dex */
public abstract class ASN1Null extends ASN1Object {
    @Override // org.bouncycastle.asn1.ASN1Object
    public boolean asn1Equals(DERObject dERObject) {
        return dERObject instanceof ASN1Null;
    }

    @Override // org.bouncycastle.asn1.ASN1Object, org.bouncycastle.asn1.DERObject
    public abstract void encode(DEROutputStream dEROutputStream) throws IOException;

    @Override // org.bouncycastle.asn1.ASN1Object, org.bouncycastle.asn1.DERObject, org.bouncycastle.asn1.ASN1Encodable
    public int hashCode() {
        return -1;
    }

    public String toString() {
        return PLVHCLessonSimpleInfoResultVO.DataVO.WATCH_CONDITION_NULL;
    }
}
