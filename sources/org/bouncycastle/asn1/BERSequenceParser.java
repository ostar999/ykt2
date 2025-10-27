package org.bouncycastle.asn1;

import java.io.IOException;

/* loaded from: classes9.dex */
public class BERSequenceParser implements ASN1SequenceParser {
    private ASN1StreamParser _parser;

    public BERSequenceParser(ASN1StreamParser aSN1StreamParser) {
        this._parser = aSN1StreamParser;
    }

    @Override // org.bouncycastle.asn1.DEREncodable
    public DERObject getDERObject() {
        try {
            return getLoadedObject();
        } catch (IOException e2) {
            throw new IllegalStateException(e2.getMessage());
        }
    }

    @Override // org.bouncycastle.asn1.InMemoryRepresentable
    public DERObject getLoadedObject() throws IOException {
        return new BERSequence(this._parser.readVector());
    }

    @Override // org.bouncycastle.asn1.ASN1SequenceParser
    public DEREncodable readObject() throws IOException {
        return this._parser.readObject();
    }
}
