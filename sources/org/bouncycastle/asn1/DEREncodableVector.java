package org.bouncycastle.asn1;

import java.util.Vector;

/* loaded from: classes9.dex */
public class DEREncodableVector {

    /* renamed from: v, reason: collision with root package name */
    Vector f27760v = new Vector();

    public void add(DEREncodable dEREncodable) {
        this.f27760v.addElement(dEREncodable);
    }

    public DEREncodable get(int i2) {
        return (DEREncodable) this.f27760v.elementAt(i2);
    }

    public int size() {
        return this.f27760v.size();
    }
}
