package org.bouncycastle.asn1;

import java.io.IOException;
import java.util.Enumeration;

/* loaded from: classes9.dex */
public class LazyDERSequence extends DERSequence {
    private byte[] encoded;
    private boolean parsed = false;
    private int size = -1;

    public LazyDERSequence(byte[] bArr) throws IOException {
        this.encoded = bArr;
    }

    private void parse() {
        LazyDERConstructionEnumeration lazyDERConstructionEnumeration = new LazyDERConstructionEnumeration(this.encoded);
        while (lazyDERConstructionEnumeration.hasMoreElements()) {
            addObject((DEREncodable) lazyDERConstructionEnumeration.nextElement());
        }
        this.parsed = true;
    }

    @Override // org.bouncycastle.asn1.DERSequence, org.bouncycastle.asn1.ASN1Sequence, org.bouncycastle.asn1.ASN1Object, org.bouncycastle.asn1.DERObject
    public void encode(DEROutputStream dEROutputStream) throws IOException {
        dEROutputStream.writeEncoded(48, this.encoded);
    }

    @Override // org.bouncycastle.asn1.ASN1Sequence
    public synchronized DEREncodable getObjectAt(int i2) {
        if (!this.parsed) {
            parse();
        }
        return super.getObjectAt(i2);
    }

    @Override // org.bouncycastle.asn1.ASN1Sequence
    public synchronized Enumeration getObjects() {
        if (this.parsed) {
            return super.getObjects();
        }
        return new LazyDERConstructionEnumeration(this.encoded);
    }

    @Override // org.bouncycastle.asn1.ASN1Sequence
    public int size() {
        if (this.size < 0) {
            LazyDERConstructionEnumeration lazyDERConstructionEnumeration = new LazyDERConstructionEnumeration(this.encoded);
            int i2 = 0;
            while (true) {
                this.size = i2;
                if (!lazyDERConstructionEnumeration.hasMoreElements()) {
                    break;
                }
                lazyDERConstructionEnumeration.nextElement();
                i2 = this.size + 1;
            }
        }
        return this.size;
    }
}
