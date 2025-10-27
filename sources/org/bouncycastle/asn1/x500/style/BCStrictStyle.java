package org.bouncycastle.asn1.x500.style;

import org.bouncycastle.asn1.x500.RDN;
import org.bouncycastle.asn1.x500.X500Name;

/* loaded from: classes9.dex */
public class BCStrictStyle extends BCStyle {
    @Override // org.bouncycastle.asn1.x500.style.BCStyle, org.bouncycastle.asn1.x500.X500NameStyle
    public boolean areEqual(X500Name x500Name, X500Name x500Name2) {
        RDN[] rDNs = x500Name.getRDNs();
        RDN[] rDNs2 = x500Name2.getRDNs();
        if (rDNs.length != rDNs2.length) {
            return false;
        }
        for (int i2 = 0; i2 != rDNs.length; i2++) {
            if (rdnAreEqual(rDNs[i2], rDNs2[i2])) {
                return false;
            }
        }
        return true;
    }
}
