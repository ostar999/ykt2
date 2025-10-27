package org.bouncycastle.asn1;

import java.io.IOException;

/* loaded from: classes9.dex */
public class BERSetParser implements ASN1SetParser {
    private ASN1StreamParser _parser;

    public BERSetParser(ASN1StreamParser aSN1StreamParser) {
        this._parser = aSN1StreamParser;
    }

    @Override // org.bouncycastle.asn1.DEREncodable
    public DERObject getDERObject() {
        try {
            return getLoadedObject();
        } catch (IOException e2) {
            throw new ASN1ParsingException(e2.getMessage(), e2);
        }
    }

    @Override // org.bouncycastle.asn1.InMemoryRepresentable
    public DERObject getLoadedObject() throws IOException {
        return new BERSet(this._parser.readVector(), false);
    }

    @Override // org.bouncycastle.asn1.ASN1SetParser
    public DEREncodable readObject() throws IOException {
        return this._parser.readObject();
    }
}
