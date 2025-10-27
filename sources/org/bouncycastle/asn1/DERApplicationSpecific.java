package org.bouncycastle.asn1;

import com.google.common.base.Ascii;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import org.bouncycastle.util.Arrays;

/* loaded from: classes9.dex */
public class DERApplicationSpecific extends ASN1Object {
    private final boolean isConstructed;
    private final byte[] octets;
    private final int tag;

    public DERApplicationSpecific(int i2, ASN1EncodableVector aSN1EncodableVector) throws IOException {
        this.tag = i2;
        this.isConstructed = true;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        for (int i3 = 0; i3 != aSN1EncodableVector.size(); i3++) {
            try {
                byteArrayOutputStream.write(((ASN1Encodable) aSN1EncodableVector.get(i3)).getEncoded());
            } catch (IOException e2) {
                throw new ASN1ParsingException("malformed object: " + e2, e2);
            }
        }
        this.octets = byteArrayOutputStream.toByteArray();
    }

    public DERApplicationSpecific(int i2, DEREncodable dEREncodable) throws IOException {
        this(true, i2, dEREncodable);
    }

    public DERApplicationSpecific(int i2, byte[] bArr) {
        this(false, i2, bArr);
    }

    public DERApplicationSpecific(boolean z2, int i2, DEREncodable dEREncodable) throws IOException {
        byte[] dEREncoded = dEREncodable.getDERObject().getDEREncoded();
        this.isConstructed = z2;
        this.tag = i2;
        if (z2) {
            this.octets = dEREncoded;
            return;
        }
        int lengthOfLength = getLengthOfLength(dEREncoded);
        int length = dEREncoded.length - lengthOfLength;
        byte[] bArr = new byte[length];
        System.arraycopy(dEREncoded, lengthOfLength, bArr, 0, length);
        this.octets = bArr;
    }

    public DERApplicationSpecific(boolean z2, int i2, byte[] bArr) {
        this.isConstructed = z2;
        this.tag = i2;
        this.octets = bArr;
    }

    private int getLengthOfLength(byte[] bArr) {
        int i2 = 2;
        while ((bArr[i2 - 1] & 128) != 0) {
            i2++;
        }
        return i2;
    }

    private byte[] replaceTagNumber(int i2, byte[] bArr) throws IOException {
        int i3;
        if ((bArr[0] & Ascii.US) == 31) {
            int i4 = bArr[1] & 255;
            if ((i4 & 127) == 0) {
                throw new ASN1ParsingException("corrupted stream - invalid high tag number found");
            }
            i3 = 2;
            while (i4 >= 0 && (i4 & 128) != 0) {
                int i5 = i3 + 1;
                int i6 = bArr[i3] & 255;
                i3 = i5;
                i4 = i6;
            }
        } else {
            i3 = 1;
        }
        int length = (bArr.length - i3) + 1;
        byte[] bArr2 = new byte[length];
        System.arraycopy(bArr, i3, bArr2, 1, length - 1);
        bArr2[0] = (byte) i2;
        return bArr2;
    }

    @Override // org.bouncycastle.asn1.ASN1Object
    public boolean asn1Equals(DERObject dERObject) {
        if (!(dERObject instanceof DERApplicationSpecific)) {
            return false;
        }
        DERApplicationSpecific dERApplicationSpecific = (DERApplicationSpecific) dERObject;
        return this.isConstructed == dERApplicationSpecific.isConstructed && this.tag == dERApplicationSpecific.tag && Arrays.areEqual(this.octets, dERApplicationSpecific.octets);
    }

    @Override // org.bouncycastle.asn1.ASN1Object, org.bouncycastle.asn1.DERObject
    public void encode(DEROutputStream dEROutputStream) throws IOException {
        dEROutputStream.writeEncoded(this.isConstructed ? 96 : 64, this.tag, this.octets);
    }

    public int getApplicationTag() {
        return this.tag;
    }

    public byte[] getContents() {
        return this.octets;
    }

    public DERObject getObject() throws IOException {
        return new ASN1InputStream(getContents()).readObject();
    }

    public DERObject getObject(int i2) throws IOException {
        if (i2 >= 31) {
            throw new IOException("unsupported tag number");
        }
        byte[] encoded = getEncoded();
        byte[] bArrReplaceTagNumber = replaceTagNumber(i2, encoded);
        if ((encoded[0] & 32) != 0) {
            bArrReplaceTagNumber[0] = (byte) (bArrReplaceTagNumber[0] | 32);
        }
        return new ASN1InputStream(bArrReplaceTagNumber).readObject();
    }

    @Override // org.bouncycastle.asn1.ASN1Object, org.bouncycastle.asn1.DERObject, org.bouncycastle.asn1.ASN1Encodable
    public int hashCode() {
        boolean z2 = this.isConstructed;
        return ((z2 ? 1 : 0) ^ this.tag) ^ Arrays.hashCode(this.octets);
    }

    public boolean isConstructed() {
        return this.isConstructed;
    }
}
