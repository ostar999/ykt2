package org.bouncycastle.asn1;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes9.dex */
public class ASN1StreamParser {
    private final InputStream _in;
    private final int _limit;

    public ASN1StreamParser(InputStream inputStream) {
        this(inputStream, ASN1InputStream.findLimit(inputStream));
    }

    public ASN1StreamParser(InputStream inputStream, int i2) {
        this._in = inputStream;
        this._limit = i2;
    }

    public ASN1StreamParser(byte[] bArr) {
        this(new ByteArrayInputStream(bArr), bArr.length);
    }

    private void set00Check(boolean z2) {
        InputStream inputStream = this._in;
        if (inputStream instanceof IndefiniteLengthInputStream) {
            ((IndefiniteLengthInputStream) inputStream).setEofOn00(z2);
        }
    }

    public DEREncodable readImplicit(boolean z2, int i2) throws IOException {
        InputStream inputStream = this._in;
        if (inputStream instanceof IndefiniteLengthInputStream) {
            if (z2) {
                return readIndef(i2);
            }
            throw new IOException("indefinite length primitive encoding encountered");
        }
        if (z2) {
            if (i2 == 4) {
                return new BEROctetStringParser(this);
            }
            if (i2 == 16) {
                return new DERSequenceParser(this);
            }
            if (i2 == 17) {
                return new DERSetParser(this);
            }
        } else {
            if (i2 == 4) {
                return new DEROctetStringParser((DefiniteLengthInputStream) inputStream);
            }
            if (i2 == 16) {
                throw new ASN1Exception("sets must use constructed encoding (see X.690 8.11.1/8.12.1)");
            }
            if (i2 == 17) {
                throw new ASN1Exception("sequences must use constructed encoding (see X.690 8.9.1/8.10.1)");
            }
        }
        throw new RuntimeException("implicit tagging not implemented");
    }

    public DEREncodable readIndef(int i2) throws IOException {
        if (i2 == 4) {
            return new BEROctetStringParser(this);
        }
        if (i2 == 8) {
            return new DERExternalParser(this);
        }
        if (i2 == 16) {
            return new BERSequenceParser(this);
        }
        if (i2 == 17) {
            return new BERSetParser(this);
        }
        throw new ASN1Exception("unknown BER object encountered: 0x" + Integer.toHexString(i2));
    }

    public DEREncodable readObject() throws IOException {
        int i2 = this._in.read();
        if (i2 == -1) {
            return null;
        }
        set00Check(false);
        int tagNumber = ASN1InputStream.readTagNumber(this._in, i2);
        boolean z2 = (i2 & 32) != 0;
        int length = ASN1InputStream.readLength(this._in, this._limit);
        if (length < 0) {
            if (!z2) {
                throw new IOException("indefinite length primitive encoding encountered");
            }
            ASN1StreamParser aSN1StreamParser = new ASN1StreamParser(new IndefiniteLengthInputStream(this._in, this._limit), this._limit);
            return (i2 & 64) != 0 ? new BERApplicationSpecificParser(tagNumber, aSN1StreamParser) : (i2 & 128) != 0 ? new BERTaggedObjectParser(true, tagNumber, aSN1StreamParser) : aSN1StreamParser.readIndef(tagNumber);
        }
        DefiniteLengthInputStream definiteLengthInputStream = new DefiniteLengthInputStream(this._in, length);
        if ((i2 & 64) != 0) {
            return new DERApplicationSpecific(z2, tagNumber, definiteLengthInputStream.toByteArray());
        }
        if ((i2 & 128) != 0) {
            return new BERTaggedObjectParser(z2, tagNumber, new ASN1StreamParser(definiteLengthInputStream));
        }
        if (z2) {
            return tagNumber != 4 ? tagNumber != 8 ? tagNumber != 16 ? tagNumber != 17 ? new DERUnknownTag(true, tagNumber, definiteLengthInputStream.toByteArray()) : new DERSetParser(new ASN1StreamParser(definiteLengthInputStream)) : new DERSequenceParser(new ASN1StreamParser(definiteLengthInputStream)) : new DERExternalParser(new ASN1StreamParser(definiteLengthInputStream)) : new BEROctetStringParser(new ASN1StreamParser(definiteLengthInputStream));
        }
        if (tagNumber == 4) {
            return new DEROctetStringParser(definiteLengthInputStream);
        }
        try {
            return ASN1InputStream.createPrimitiveDERObject(tagNumber, definiteLengthInputStream.toByteArray());
        } catch (IllegalArgumentException e2) {
            throw new ASN1Exception("corrupted stream detected", e2);
        }
    }

    public DERObject readTaggedObject(boolean z2, int i2) throws IOException {
        if (!z2) {
            return new DERTaggedObject(false, i2, new DEROctetString(((DefiniteLengthInputStream) this._in).toByteArray()));
        }
        ASN1EncodableVector vector = readVector();
        return this._in instanceof IndefiniteLengthInputStream ? vector.size() == 1 ? new BERTaggedObject(true, i2, vector.get(0)) : new BERTaggedObject(false, i2, BERFactory.createSequence(vector)) : vector.size() == 1 ? new DERTaggedObject(true, i2, vector.get(0)) : new DERTaggedObject(false, i2, DERFactory.createSequence(vector));
    }

    public ASN1EncodableVector readVector() throws IOException {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        while (true) {
            DEREncodable object = readObject();
            if (object == null) {
                return aSN1EncodableVector;
            }
            aSN1EncodableVector.add(object instanceof InMemoryRepresentable ? ((InMemoryRepresentable) object).getLoadedObject() : object.getDERObject());
        }
    }
}
