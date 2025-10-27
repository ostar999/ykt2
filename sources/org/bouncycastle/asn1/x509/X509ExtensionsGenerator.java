package org.bouncycastle.asn1.x509;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Vector;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.DEREncodable;
import org.bouncycastle.asn1.DERObjectIdentifier;
import org.bouncycastle.asn1.DEROctetString;

/* loaded from: classes9.dex */
public class X509ExtensionsGenerator {
    private Hashtable extensions = new Hashtable();
    private Vector extOrdering = new Vector();

    public void addExtension(DERObjectIdentifier dERObjectIdentifier, boolean z2, DEREncodable dEREncodable) {
        try {
            addExtension(dERObjectIdentifier, z2, dEREncodable.getDERObject().getEncoded(ASN1Encodable.DER));
        } catch (IOException e2) {
            throw new IllegalArgumentException("error encoding value: " + e2);
        }
    }

    public void addExtension(DERObjectIdentifier dERObjectIdentifier, boolean z2, byte[] bArr) {
        if (!this.extensions.containsKey(dERObjectIdentifier)) {
            this.extOrdering.addElement(dERObjectIdentifier);
            this.extensions.put(dERObjectIdentifier, new X509Extension(z2, new DEROctetString(bArr)));
        } else {
            throw new IllegalArgumentException("extension " + dERObjectIdentifier + " already added");
        }
    }

    public X509Extensions generate() {
        return new X509Extensions(this.extOrdering, this.extensions);
    }

    public boolean isEmpty() {
        return this.extOrdering.isEmpty();
    }

    public void reset() {
        this.extensions = new Hashtable();
        this.extOrdering = new Vector();
    }
}
