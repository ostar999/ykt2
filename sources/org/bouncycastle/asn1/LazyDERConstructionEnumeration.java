package org.bouncycastle.asn1;

import java.io.IOException;
import java.util.Enumeration;

/* loaded from: classes9.dex */
class LazyDERConstructionEnumeration implements Enumeration {
    private ASN1InputStream aIn;
    private Object nextObj = readObject();

    public LazyDERConstructionEnumeration(byte[] bArr) {
        this.aIn = new ASN1InputStream(bArr, true);
    }

    private Object readObject() {
        try {
            return this.aIn.readObject();
        } catch (IOException e2) {
            throw new ASN1ParsingException("malformed DER construction: " + e2, e2);
        }
    }

    @Override // java.util.Enumeration
    public boolean hasMoreElements() {
        return this.nextObj != null;
    }

    @Override // java.util.Enumeration
    public Object nextElement() {
        Object obj = this.nextObj;
        this.nextObj = readObject();
        return obj;
    }
}
