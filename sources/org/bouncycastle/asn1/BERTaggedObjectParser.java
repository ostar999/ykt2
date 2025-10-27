package org.bouncycastle.asn1;

import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes9.dex */
public class BERTaggedObjectParser implements ASN1TaggedObjectParser {
    private boolean _constructed;
    private ASN1StreamParser _parser;
    private int _tagNumber;

    public BERTaggedObjectParser(int i2, int i3, InputStream inputStream) {
        this((i2 & 32) != 0, i3, new ASN1StreamParser(inputStream));
    }

    public BERTaggedObjectParser(boolean z2, int i2, ASN1StreamParser aSN1StreamParser) {
        this._constructed = z2;
        this._tagNumber = i2;
        this._parser = aSN1StreamParser;
    }

    @Override // org.bouncycastle.asn1.DEREncodable
    public DERObject getDERObject() {
        try {
            return getLoadedObject();
        } catch (IOException e2) {
            throw new ASN1ParsingException(e2.getMessage());
        }
    }

    @Override // org.bouncycastle.asn1.InMemoryRepresentable
    public DERObject getLoadedObject() throws IOException {
        return this._parser.readTaggedObject(this._constructed, this._tagNumber);
    }

    @Override // org.bouncycastle.asn1.ASN1TaggedObjectParser
    public DEREncodable getObjectParser(int i2, boolean z2) throws IOException {
        if (!z2) {
            return this._parser.readImplicit(this._constructed, i2);
        }
        if (this._constructed) {
            return this._parser.readObject();
        }
        throw new IOException("Explicit tags must be constructed (see X.690 8.14.2)");
    }

    @Override // org.bouncycastle.asn1.ASN1TaggedObjectParser
    public int getTagNo() {
        return this._tagNumber;
    }

    public boolean isConstructed() {
        return this._constructed;
    }
}
