package org.bouncycastle.asn1;

import java.io.IOException;
import org.bouncycastle.util.Arrays;

/* loaded from: classes9.dex */
public class DERUnknownTag extends DERObject {
    private byte[] data;
    private boolean isConstructed;
    private int tag;

    public DERUnknownTag(int i2, byte[] bArr) {
        this(false, i2, bArr);
    }

    public DERUnknownTag(boolean z2, int i2, byte[] bArr) {
        this.isConstructed = z2;
        this.tag = i2;
        this.data = bArr;
    }

    @Override // org.bouncycastle.asn1.DERObject
    public void encode(DEROutputStream dEROutputStream) throws IOException {
        dEROutputStream.writeEncoded(this.isConstructed ? 32 : 0, this.tag, this.data);
    }

    @Override // org.bouncycastle.asn1.DERObject, org.bouncycastle.asn1.ASN1Encodable
    public boolean equals(Object obj) {
        if (!(obj instanceof DERUnknownTag)) {
            return false;
        }
        DERUnknownTag dERUnknownTag = (DERUnknownTag) obj;
        return this.isConstructed == dERUnknownTag.isConstructed && this.tag == dERUnknownTag.tag && Arrays.areEqual(this.data, dERUnknownTag.data);
    }

    public byte[] getData() {
        return this.data;
    }

    public int getTag() {
        return this.tag;
    }

    @Override // org.bouncycastle.asn1.DERObject, org.bouncycastle.asn1.ASN1Encodable
    public int hashCode() {
        return ((this.isConstructed ? -1 : 0) ^ this.tag) ^ Arrays.hashCode(this.data);
    }

    public boolean isConstructed() {
        return this.isConstructed;
    }
}
