package org.bouncycastle.crypto.agreement.kdf;

import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.DERNull;
import org.bouncycastle.asn1.DERObjectIdentifier;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.DERTaggedObject;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.DerivationFunction;
import org.bouncycastle.crypto.DerivationParameters;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.generators.KDF2BytesGenerator;
import org.bouncycastle.crypto.params.KDFParameters;

/* loaded from: classes9.dex */
public class ECDHKEKGenerator implements DerivationFunction {
    private DERObjectIdentifier algorithm;
    private DerivationFunction kdf;
    private int keySize;

    /* renamed from: z, reason: collision with root package name */
    private byte[] f27809z;

    public ECDHKEKGenerator(Digest digest) {
        this.kdf = new KDF2BytesGenerator(digest);
    }

    private byte[] integerToBytes(int i2) {
        return new byte[]{(byte) (i2 >> 24), (byte) (i2 >> 16), (byte) (i2 >> 8), (byte) i2};
    }

    @Override // org.bouncycastle.crypto.DerivationFunction
    public int generateBytes(byte[] bArr, int i2, int i3) throws DataLengthException, IllegalArgumentException {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(new AlgorithmIdentifier(this.algorithm, new DERNull()));
        aSN1EncodableVector.add(new DERTaggedObject(true, 2, new DEROctetString(integerToBytes(this.keySize))));
        this.kdf.init(new KDFParameters(this.f27809z, new DERSequence(aSN1EncodableVector).getDEREncoded()));
        return this.kdf.generateBytes(bArr, i2, i3);
    }

    @Override // org.bouncycastle.crypto.DerivationFunction
    public Digest getDigest() {
        return this.kdf.getDigest();
    }

    @Override // org.bouncycastle.crypto.DerivationFunction
    public void init(DerivationParameters derivationParameters) {
        DHKDFParameters dHKDFParameters = (DHKDFParameters) derivationParameters;
        this.algorithm = dHKDFParameters.getAlgorithm();
        this.keySize = dHKDFParameters.getKeySize();
        this.f27809z = dHKDFParameters.getZ();
    }
}
