package org.bouncycastle.asn1;

import java.io.IOException;

/* loaded from: classes9.dex */
public class DERExternalParser implements DEREncodable, InMemoryRepresentable {
    private ASN1StreamParser _parser;

    public DERExternalParser(ASN1StreamParser aSN1StreamParser) {
        this._parser = aSN1StreamParser;
    }

    @Override // org.bouncycastle.asn1.DEREncodable
    public DERObject getDERObject() {
        try {
            return getLoadedObject();
        } catch (IOException e2) {
            throw new ASN1ParsingException("unable to get DER object", e2);
        } catch (IllegalArgumentException e3) {
            throw new ASN1ParsingException("unable to get DER object", e3);
        }
    }

    @Override // org.bouncycastle.asn1.InMemoryRepresentable
    public DERObject getLoadedObject() throws IOException {
        try {
            return new DERExternal(this._parser.readVector());
        } catch (IllegalArgumentException e2) {
            throw new ASN1Exception(e2.getMessage(), e2);
        }
    }

    public DEREncodable readObject() throws IOException {
        return this._parser.readObject();
    }
}
