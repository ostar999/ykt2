package org.bouncycastle.jce;

import java.io.IOException;
import java.security.Principal;
import java.util.Hashtable;
import java.util.Vector;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.x509.X509Name;

/* loaded from: classes9.dex */
public class X509Principal extends X509Name implements Principal {
    public X509Principal(String str) {
        super(str);
    }

    public X509Principal(Hashtable hashtable) {
        super(hashtable);
    }

    public X509Principal(Vector vector, Hashtable hashtable) {
        super(vector, hashtable);
    }

    public X509Principal(Vector vector, Vector vector2) {
        super(vector, vector2);
    }

    public X509Principal(X509Name x509Name) {
        super((ASN1Sequence) x509Name.getDERObject());
    }

    public X509Principal(boolean z2, String str) {
        super(z2, str);
    }

    public X509Principal(boolean z2, Hashtable hashtable, String str) {
        super(z2, hashtable, str);
    }

    public X509Principal(byte[] bArr) throws IOException {
        super(readSequence(new ASN1InputStream(bArr)));
    }

    private static ASN1Sequence readSequence(ASN1InputStream aSN1InputStream) throws IOException {
        try {
            return ASN1Sequence.getInstance(aSN1InputStream.readObject());
        } catch (IllegalArgumentException e2) {
            throw new IOException("not an ASN.1 Sequence: " + e2);
        }
    }

    @Override // org.bouncycastle.asn1.ASN1Encodable
    public byte[] getEncoded() {
        try {
            return getEncoded(ASN1Encodable.DER);
        } catch (IOException e2) {
            throw new RuntimeException(e2.toString());
        }
    }

    @Override // java.security.Principal
    public String getName() {
        return toString();
    }
}
