package org.bouncycastle.crypto.agreement.kdf;

import org.apache.commons.compress.archivers.tar.TarConstants;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.DERObjectIdentifier;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.DERTaggedObject;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.DerivationFunction;
import org.bouncycastle.crypto.DerivationParameters;
import org.bouncycastle.crypto.Digest;

/* loaded from: classes9.dex */
public class DHKEKGenerator implements DerivationFunction {
    private DERObjectIdentifier algorithm;
    private final Digest digest;
    private int keySize;
    private byte[] partyAInfo;

    /* renamed from: z, reason: collision with root package name */
    private byte[] f27808z;

    public DHKEKGenerator(Digest digest) {
        this.digest = digest;
    }

    private byte[] integerToBytes(int i2) {
        return new byte[]{(byte) (i2 >> 24), (byte) (i2 >> 16), (byte) (i2 >> 8), (byte) i2};
    }

    @Override // org.bouncycastle.crypto.DerivationFunction
    public int generateBytes(byte[] bArr, int i2, int i3) throws DataLengthException, IllegalArgumentException {
        if (bArr.length - i3 < i2) {
            throw new DataLengthException("output buffer too small");
        }
        long j2 = i3;
        int digestSize = this.digest.getDigestSize();
        if (j2 > TarConstants.MAXSIZE) {
            throw new IllegalArgumentException("Output length too large");
        }
        long j3 = digestSize;
        int i4 = (int) (((j2 + j3) - 1) / j3);
        byte[] bArr2 = new byte[this.digest.getDigestSize()];
        int i5 = 1;
        for (int i6 = 0; i6 < i4; i6++) {
            Digest digest = this.digest;
            byte[] bArr3 = this.f27808z;
            digest.update(bArr3, 0, bArr3.length);
            ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
            ASN1EncodableVector aSN1EncodableVector2 = new ASN1EncodableVector();
            aSN1EncodableVector2.add(this.algorithm);
            aSN1EncodableVector2.add(new DEROctetString(integerToBytes(i5)));
            aSN1EncodableVector.add(new DERSequence(aSN1EncodableVector2));
            if (this.partyAInfo != null) {
                aSN1EncodableVector.add(new DERTaggedObject(true, 0, new DEROctetString(this.partyAInfo)));
            }
            aSN1EncodableVector.add(new DERTaggedObject(true, 2, new DEROctetString(integerToBytes(this.keySize))));
            byte[] dEREncoded = new DERSequence(aSN1EncodableVector).getDEREncoded();
            this.digest.update(dEREncoded, 0, dEREncoded.length);
            this.digest.doFinal(bArr2, 0);
            if (i3 > digestSize) {
                System.arraycopy(bArr2, 0, bArr, i2, digestSize);
                i2 += digestSize;
                i3 -= digestSize;
            } else {
                System.arraycopy(bArr2, 0, bArr, i2, i3);
            }
            i5++;
        }
        this.digest.reset();
        return i3;
    }

    @Override // org.bouncycastle.crypto.DerivationFunction
    public Digest getDigest() {
        return this.digest;
    }

    @Override // org.bouncycastle.crypto.DerivationFunction
    public void init(DerivationParameters derivationParameters) {
        DHKDFParameters dHKDFParameters = (DHKDFParameters) derivationParameters;
        this.algorithm = dHKDFParameters.getAlgorithm();
        this.keySize = dHKDFParameters.getKeySize();
        this.f27808z = dHKDFParameters.getZ();
        this.partyAInfo = dHKDFParameters.getExtraInfo();
    }
}
