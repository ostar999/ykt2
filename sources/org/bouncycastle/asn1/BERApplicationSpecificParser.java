package org.bouncycastle.asn1;

import java.io.IOException;

/* loaded from: classes9.dex */
public class BERApplicationSpecificParser implements ASN1ApplicationSpecificParser {
    private final ASN1StreamParser parser;
    private final int tag;

    public BERApplicationSpecificParser(int i2, ASN1StreamParser aSN1StreamParser) {
        this.tag = i2;
        this.parser = aSN1StreamParser;
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
        return new BERApplicationSpecific(this.tag, this.parser.readVector());
    }

    @Override // org.bouncycastle.asn1.ASN1ApplicationSpecificParser
    public DEREncodable readObject() throws IOException {
        return this.parser.readObject();
    }
}
