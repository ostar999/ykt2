package org.bouncycastle.crypto.agreement.kdf;

import org.bouncycastle.asn1.DERObjectIdentifier;
import org.bouncycastle.crypto.DerivationParameters;

/* loaded from: classes9.dex */
public class DHKDFParameters implements DerivationParameters {
    private final DERObjectIdentifier algorithm;
    private final byte[] extraInfo;
    private final int keySize;

    /* renamed from: z, reason: collision with root package name */
    private final byte[] f27807z;

    public DHKDFParameters(DERObjectIdentifier dERObjectIdentifier, int i2, byte[] bArr) {
        this.algorithm = dERObjectIdentifier;
        this.keySize = i2;
        this.f27807z = bArr;
        this.extraInfo = null;
    }

    public DHKDFParameters(DERObjectIdentifier dERObjectIdentifier, int i2, byte[] bArr, byte[] bArr2) {
        this.algorithm = dERObjectIdentifier;
        this.keySize = i2;
        this.f27807z = bArr;
        this.extraInfo = bArr2;
    }

    public DERObjectIdentifier getAlgorithm() {
        return this.algorithm;
    }

    public byte[] getExtraInfo() {
        return this.extraInfo;
    }

    public int getKeySize() {
        return this.keySize;
    }

    public byte[] getZ() {
        return this.f27807z;
    }
}
