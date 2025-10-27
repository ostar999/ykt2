package org.bouncycastle.asn1;

import java.util.Vector;

/* loaded from: classes9.dex */
public class ASN1EncodableVector extends DEREncodableVector {

    /* renamed from: v, reason: collision with root package name */
    Vector f27759v = new Vector();

    @Override // org.bouncycastle.asn1.DEREncodableVector
    public void add(DEREncodable dEREncodable) {
        this.f27759v.addElement(dEREncodable);
    }

    @Override // org.bouncycastle.asn1.DEREncodableVector
    public DEREncodable get(int i2) {
        return (DEREncodable) this.f27759v.elementAt(i2);
    }

    @Override // org.bouncycastle.asn1.DEREncodableVector
    public int size() {
        return this.f27759v.size();
    }
}
