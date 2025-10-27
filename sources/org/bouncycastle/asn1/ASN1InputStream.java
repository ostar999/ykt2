package org.bouncycastle.asn1;

import java.io.ByteArrayInputStream;
import java.io.EOFException;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.bouncycastle.util.io.Streams;

/* loaded from: classes9.dex */
public class ASN1InputStream extends FilterInputStream implements DERTags {
    private final boolean lazyEvaluate;
    private final int limit;

    public ASN1InputStream(InputStream inputStream) {
        this(inputStream, findLimit(inputStream));
    }

    public ASN1InputStream(InputStream inputStream, int i2) {
        this(inputStream, i2, false);
    }

    public ASN1InputStream(InputStream inputStream, int i2, boolean z2) {
        super(inputStream);
        this.limit = i2;
        this.lazyEvaluate = z2;
    }

    public ASN1InputStream(byte[] bArr) {
        this(new ByteArrayInputStream(bArr), bArr.length);
    }

    public ASN1InputStream(byte[] bArr, boolean z2) {
        this(new ByteArrayInputStream(bArr), bArr.length, z2);
    }

    public static DERObject createPrimitiveDERObject(int i2, byte[] bArr) {
        switch (i2) {
            case 1:
                return new ASN1Boolean(bArr);
            case 2:
                return new ASN1Integer(bArr);
            case 3:
                return DERBitString.fromOctetString(bArr);
            case 4:
                return new DEROctetString(bArr);
            case 5:
                return DERNull.INSTANCE;
            case 6:
                return new ASN1ObjectIdentifier(bArr);
            case 7:
            case 8:
            case 9:
            case 11:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 21:
            case 25:
            case 29:
            default:
                return new DERUnknownTag(false, i2, bArr);
            case 10:
                return new ASN1Enumerated(bArr);
            case 12:
                return new DERUTF8String(bArr);
            case 18:
                return new DERNumericString(bArr);
            case 19:
                return new DERPrintableString(bArr);
            case 20:
                return new DERT61String(bArr);
            case 22:
                return new DERIA5String(bArr);
            case 23:
                return new ASN1UTCTime(bArr);
            case 24:
                return new ASN1GeneralizedTime(bArr);
            case 26:
                return new DERVisibleString(bArr);
            case 27:
                return new DERGeneralString(bArr);
            case 28:
                return new DERUniversalString(bArr);
            case 30:
                return new DERBMPString(bArr);
        }
    }

    public static int findLimit(InputStream inputStream) {
        if (inputStream instanceof LimitedInputStream) {
            return ((LimitedInputStream) inputStream).getRemaining();
        }
        if (inputStream instanceof ByteArrayInputStream) {
            return ((ByteArrayInputStream) inputStream).available();
        }
        return Integer.MAX_VALUE;
    }

    public static int readLength(InputStream inputStream, int i2) throws IOException {
        int i3 = inputStream.read();
        if (i3 < 0) {
            throw new EOFException("EOF found when length expected");
        }
        if (i3 == 128) {
            return -1;
        }
        if (i3 <= 127) {
            return i3;
        }
        int i4 = i3 & 127;
        if (i4 > 4) {
            throw new IOException("DER length more than 4 bytes: " + i4);
        }
        int i5 = 0;
        for (int i6 = 0; i6 < i4; i6++) {
            int i7 = inputStream.read();
            if (i7 < 0) {
                throw new EOFException("EOF found reading length");
            }
            i5 = (i5 << 8) + i7;
        }
        if (i5 < 0) {
            throw new IOException("corrupted stream - negative length found");
        }
        if (i5 < i2) {
            return i5;
        }
        throw new IOException("corrupted stream - out of bounds length found");
    }

    public static int readTagNumber(InputStream inputStream, int i2) throws IOException {
        int i3 = i2 & 31;
        if (i3 != 31) {
            return i3;
        }
        int i4 = inputStream.read();
        if ((i4 & 127) == 0) {
            throw new IOException("corrupted stream - invalid high tag number found");
        }
        int i5 = 0;
        while (i4 >= 0 && (i4 & 128) != 0) {
            i5 = ((i4 & 127) | i5) << 7;
            i4 = inputStream.read();
        }
        if (i4 >= 0) {
            return i5 | (i4 & 127);
        }
        throw new EOFException("EOF found inside tag value.");
    }

    public ASN1EncodableVector buildDEREncodableVector(DefiniteLengthInputStream definiteLengthInputStream) throws IOException {
        return new ASN1InputStream(definiteLengthInputStream).buildEncodableVector();
    }

    public ASN1EncodableVector buildEncodableVector() throws IOException {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        while (true) {
            DERObject object = readObject();
            if (object == null) {
                return aSN1EncodableVector;
            }
            aSN1EncodableVector.add(object);
        }
    }

    public DERObject buildObject(int i2, int i3, int i4) throws IOException {
        boolean z2 = (i2 & 32) != 0;
        DefiniteLengthInputStream definiteLengthInputStream = new DefiniteLengthInputStream(this, i4);
        return (i2 & 64) != 0 ? new DERApplicationSpecific(z2, i3, definiteLengthInputStream.toByteArray()) : (i2 & 128) != 0 ? new ASN1StreamParser(definiteLengthInputStream).readTaggedObject(z2, i3) : z2 ? i3 != 4 ? i3 != 8 ? i3 != 16 ? i3 != 17 ? new DERUnknownTag(true, i3, definiteLengthInputStream.toByteArray()) : DERFactory.createSet(buildDEREncodableVector(definiteLengthInputStream), false) : this.lazyEvaluate ? new LazyDERSequence(definiteLengthInputStream.toByteArray()) : DERFactory.createSequence(buildDEREncodableVector(definiteLengthInputStream)) : new DERExternal(buildDEREncodableVector(definiteLengthInputStream)) : new BERConstructedOctetString(buildDEREncodableVector(definiteLengthInputStream).f27759v) : createPrimitiveDERObject(i3, definiteLengthInputStream.toByteArray());
    }

    public void readFully(byte[] bArr) throws IOException {
        if (Streams.readFully(this, bArr) != bArr.length) {
            throw new EOFException("EOF encountered in middle of object");
        }
    }

    public int readLength() throws IOException {
        return readLength(this, this.limit);
    }

    public DERObject readObject() throws IOException {
        int i2 = read();
        if (i2 <= 0) {
            if (i2 != 0) {
                return null;
            }
            throw new IOException("unexpected end-of-contents marker");
        }
        int tagNumber = readTagNumber(this, i2);
        boolean z2 = (i2 & 32) != 0;
        int length = readLength();
        if (length >= 0) {
            try {
                return buildObject(i2, tagNumber, length);
            } catch (IllegalArgumentException e2) {
                throw new ASN1Exception("corrupted stream detected", e2);
            }
        }
        if (!z2) {
            throw new IOException("indefinite length primitive encoding encountered");
        }
        ASN1StreamParser aSN1StreamParser = new ASN1StreamParser(new IndefiniteLengthInputStream(this, this.limit), this.limit);
        if ((i2 & 64) != 0) {
            return new BERApplicationSpecificParser(tagNumber, aSN1StreamParser).getLoadedObject();
        }
        if ((i2 & 128) != 0) {
            return new BERTaggedObjectParser(true, tagNumber, aSN1StreamParser).getLoadedObject();
        }
        if (tagNumber == 4) {
            return new BEROctetStringParser(aSN1StreamParser).getLoadedObject();
        }
        if (tagNumber == 8) {
            return new DERExternalParser(aSN1StreamParser).getLoadedObject();
        }
        if (tagNumber == 16) {
            return new BERSequenceParser(aSN1StreamParser).getLoadedObject();
        }
        if (tagNumber == 17) {
            return new BERSetParser(aSN1StreamParser).getLoadedObject();
        }
        throw new IOException("unknown BER object encountered");
    }
}
