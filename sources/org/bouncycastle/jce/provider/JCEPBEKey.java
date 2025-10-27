package org.bouncycastle.jce.provider;

import javax.crypto.interfaces.PBEKey;
import javax.crypto.spec.PBEKeySpec;
import org.bouncycastle.asn1.DERObjectIdentifier;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.PBEParametersGenerator;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;

/* loaded from: classes9.dex */
public class JCEPBEKey implements PBEKey {
    String algorithm;
    int digest;
    int ivSize;
    int keySize;
    DERObjectIdentifier oid;
    CipherParameters param;
    PBEKeySpec pbeKeySpec;
    boolean tryWrong = false;
    int type;

    public JCEPBEKey(String str, DERObjectIdentifier dERObjectIdentifier, int i2, int i3, int i4, int i5, PBEKeySpec pBEKeySpec, CipherParameters cipherParameters) {
        this.algorithm = str;
        this.oid = dERObjectIdentifier;
        this.type = i2;
        this.digest = i3;
        this.keySize = i4;
        this.ivSize = i5;
        this.pbeKeySpec = pBEKeySpec;
        this.param = cipherParameters;
    }

    @Override // java.security.Key
    public String getAlgorithm() {
        return this.algorithm;
    }

    public int getDigest() {
        return this.digest;
    }

    @Override // java.security.Key
    public byte[] getEncoded() {
        CipherParameters parameters = this.param;
        if (parameters == null) {
            return this.type == 2 ? PBEParametersGenerator.PKCS12PasswordToBytes(this.pbeKeySpec.getPassword()) : PBEParametersGenerator.PKCS5PasswordToBytes(this.pbeKeySpec.getPassword());
        }
        if (parameters instanceof ParametersWithIV) {
            parameters = ((ParametersWithIV) parameters).getParameters();
        }
        return ((KeyParameter) parameters).getKey();
    }

    @Override // java.security.Key
    public String getFormat() {
        return "RAW";
    }

    @Override // javax.crypto.interfaces.PBEKey
    public int getIterationCount() {
        return this.pbeKeySpec.getIterationCount();
    }

    public int getIvSize() {
        return this.ivSize;
    }

    public int getKeySize() {
        return this.keySize;
    }

    public DERObjectIdentifier getOID() {
        return this.oid;
    }

    public CipherParameters getParam() {
        return this.param;
    }

    @Override // javax.crypto.interfaces.PBEKey
    public char[] getPassword() {
        return this.pbeKeySpec.getPassword();
    }

    @Override // javax.crypto.interfaces.PBEKey
    public byte[] getSalt() {
        return this.pbeKeySpec.getSalt();
    }

    public int getType() {
        return this.type;
    }

    public void setTryWrongPKCS12Zero(boolean z2) {
        this.tryWrong = z2;
    }

    public boolean shouldTryWrongPKCS12() {
        return this.tryWrong;
    }
}
