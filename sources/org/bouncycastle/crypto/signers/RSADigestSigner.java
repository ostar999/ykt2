package org.bouncycastle.crypto.signers;

import java.util.Hashtable;
import org.bouncycastle.asn1.DERNull;
import org.bouncycastle.asn1.DERObjectIdentifier;
import org.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.asn1.teletrust.TeleTrusTObjectIdentifiers;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.asn1.x509.DigestInfo;
import org.bouncycastle.asn1.x509.X509ObjectIdentifiers;
import org.bouncycastle.crypto.AsymmetricBlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.CryptoException;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.Signer;
import org.bouncycastle.crypto.encodings.PKCS1Encoding;
import org.bouncycastle.crypto.engines.RSABlindedEngine;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.crypto.params.ParametersWithRandom;

/* loaded from: classes9.dex */
public class RSADigestSigner implements Signer {
    private static final Hashtable oidMap;
    private final AlgorithmIdentifier algId;
    private final Digest digest;
    private boolean forSigning;
    private final AsymmetricBlockCipher rsaEngine = new PKCS1Encoding(new RSABlindedEngine());

    static {
        Hashtable hashtable = new Hashtable();
        oidMap = hashtable;
        hashtable.put("RIPEMD128", TeleTrusTObjectIdentifiers.ripemd128);
        hashtable.put("RIPEMD160", TeleTrusTObjectIdentifiers.ripemd160);
        hashtable.put("RIPEMD256", TeleTrusTObjectIdentifiers.ripemd256);
        hashtable.put("SHA-1", X509ObjectIdentifiers.id_SHA1);
        hashtable.put("SHA-224", NISTObjectIdentifiers.id_sha224);
        hashtable.put("SHA-256", NISTObjectIdentifiers.id_sha256);
        hashtable.put("SHA-384", NISTObjectIdentifiers.id_sha384);
        hashtable.put("SHA-512", NISTObjectIdentifiers.id_sha512);
        hashtable.put("MD2", PKCSObjectIdentifiers.md2);
        hashtable.put("MD4", PKCSObjectIdentifiers.md4);
        hashtable.put("MD5", PKCSObjectIdentifiers.md5);
    }

    public RSADigestSigner(Digest digest) {
        this.digest = digest;
        this.algId = new AlgorithmIdentifier((DERObjectIdentifier) oidMap.get(digest.getAlgorithmName()), DERNull.INSTANCE);
    }

    private byte[] derEncode(byte[] bArr) {
        return new DigestInfo(this.algId, bArr).getDEREncoded();
    }

    @Override // org.bouncycastle.crypto.Signer
    public byte[] generateSignature() throws DataLengthException, CryptoException {
        if (!this.forSigning) {
            throw new IllegalStateException("RSADigestSigner not initialised for signature generation.");
        }
        byte[] bArr = new byte[this.digest.getDigestSize()];
        this.digest.doFinal(bArr, 0);
        byte[] bArrDerEncode = derEncode(bArr);
        return this.rsaEngine.processBlock(bArrDerEncode, 0, bArrDerEncode.length);
    }

    public String getAlgorithmName() {
        return this.digest.getAlgorithmName() + "withRSA";
    }

    @Override // org.bouncycastle.crypto.Signer
    public void init(boolean z2, CipherParameters cipherParameters) {
        this.forSigning = z2;
        AsymmetricKeyParameter asymmetricKeyParameter = cipherParameters instanceof ParametersWithRandom ? (AsymmetricKeyParameter) ((ParametersWithRandom) cipherParameters).getParameters() : (AsymmetricKeyParameter) cipherParameters;
        if (z2 && !asymmetricKeyParameter.isPrivate()) {
            throw new IllegalArgumentException("signing requires private key");
        }
        if (!z2 && asymmetricKeyParameter.isPrivate()) {
            throw new IllegalArgumentException("verification requires public key");
        }
        reset();
        this.rsaEngine.init(z2, cipherParameters);
    }

    @Override // org.bouncycastle.crypto.Signer
    public void reset() {
        this.digest.reset();
    }

    @Override // org.bouncycastle.crypto.Signer
    public void update(byte b3) {
        this.digest.update(b3);
    }

    @Override // org.bouncycastle.crypto.Signer
    public void update(byte[] bArr, int i2, int i3) {
        this.digest.update(bArr, i2, i3);
    }

    @Override // org.bouncycastle.crypto.Signer
    public boolean verifySignature(byte[] bArr) {
        byte[] bArrProcessBlock;
        byte[] bArrDerEncode;
        if (this.forSigning) {
            throw new IllegalStateException("RSADigestSigner not initialised for verification");
        }
        int digestSize = this.digest.getDigestSize();
        byte[] bArr2 = new byte[digestSize];
        this.digest.doFinal(bArr2, 0);
        try {
            bArrProcessBlock = this.rsaEngine.processBlock(bArr, 0, bArr.length);
            bArrDerEncode = derEncode(bArr2);
        } catch (Exception unused) {
        }
        if (bArrProcessBlock.length != bArrDerEncode.length) {
            if (bArrProcessBlock.length == bArrDerEncode.length - 2) {
                int length = (bArrProcessBlock.length - digestSize) - 2;
                int length2 = (bArrDerEncode.length - digestSize) - 2;
                bArrDerEncode[1] = (byte) (bArrDerEncode[1] - 2);
                bArrDerEncode[3] = (byte) (bArrDerEncode[3] - 2);
                for (int i2 = 0; i2 < digestSize; i2++) {
                    if (bArrProcessBlock[length + i2] != bArrDerEncode[length2 + i2]) {
                        return false;
                    }
                }
                for (int i3 = 0; i3 < length; i3++) {
                    if (bArrProcessBlock[i3] != bArrDerEncode[i3]) {
                        return false;
                    }
                }
            }
            return false;
        }
        for (int i4 = 0; i4 < bArrProcessBlock.length; i4++) {
            if (bArrProcessBlock[i4] != bArrDerEncode[i4]) {
                return false;
            }
        }
        return true;
    }
}
