package org.bouncycastle.jce.provider;

import com.plv.livescenes.hiclass.vo.PLVHCLessonSimpleInfoResultVO;
import java.io.ByteArrayOutputStream;
import java.security.AlgorithmParameters;
import java.security.InvalidKeyException;
import java.security.InvalidParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.SignatureException;
import java.security.SignatureSpi;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidParameterSpecException;
import java.security.spec.MGF1ParameterSpec;
import java.security.spec.PSSParameterSpec;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.crypto.AsymmetricBlockCipher;
import org.bouncycastle.crypto.CryptoException;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.engines.RSABlindedEngine;
import org.bouncycastle.crypto.params.ParametersWithRandom;
import org.bouncycastle.crypto.signers.PSSSigner;

/* loaded from: classes9.dex */
public class JDKPSSSigner extends SignatureSpi {
    private Digest contentDigest;
    private AlgorithmParameters engineParams;
    private boolean isRaw;
    private Digest mgfDigest;
    private PSSParameterSpec originalSpec;
    private PSSParameterSpec paramSpec;
    private PSSSigner pss;
    private int saltLength;
    private AsymmetricBlockCipher signer;
    private byte trailer;

    public class NullPssDigest implements Digest {
        private Digest baseDigest;
        private ByteArrayOutputStream bOut = new ByteArrayOutputStream();
        private boolean oddTime = true;

        public NullPssDigest(Digest digest) {
            this.baseDigest = digest;
        }

        @Override // org.bouncycastle.crypto.Digest
        public int doFinal(byte[] bArr, int i2) {
            byte[] byteArray = this.bOut.toByteArray();
            if (this.oddTime) {
                System.arraycopy(byteArray, 0, bArr, i2, byteArray.length);
            } else {
                this.baseDigest.update(byteArray, 0, byteArray.length);
                this.baseDigest.doFinal(bArr, i2);
            }
            reset();
            this.oddTime = !this.oddTime;
            return byteArray.length;
        }

        @Override // org.bouncycastle.crypto.Digest
        public String getAlgorithmName() {
            return PLVHCLessonSimpleInfoResultVO.DataVO.WATCH_CONDITION_NULL;
        }

        @Override // org.bouncycastle.crypto.Digest
        public int getDigestSize() {
            return this.baseDigest.getDigestSize();
        }

        @Override // org.bouncycastle.crypto.Digest
        public void reset() {
            this.bOut.reset();
            this.baseDigest.reset();
        }

        @Override // org.bouncycastle.crypto.Digest
        public void update(byte b3) {
            this.bOut.write(b3);
        }

        @Override // org.bouncycastle.crypto.Digest
        public void update(byte[] bArr, int i2, int i3) {
            this.bOut.write(bArr, i2, i3);
        }
    }

    public static class PSSwithRSA extends JDKPSSSigner {
        public PSSwithRSA() {
            super(new RSABlindedEngine(), null);
        }
    }

    public static class SHA1withRSA extends JDKPSSSigner {
        public SHA1withRSA() {
            super(new RSABlindedEngine(), PSSParameterSpec.DEFAULT);
        }
    }

    public static class SHA224withRSA extends JDKPSSSigner {
        public SHA224withRSA() {
            super(new RSABlindedEngine(), new PSSParameterSpec("SHA-224", "MGF1", new MGF1ParameterSpec("SHA-224"), 28, 1));
        }
    }

    public static class SHA256withRSA extends JDKPSSSigner {
        public SHA256withRSA() {
            super(new RSABlindedEngine(), new PSSParameterSpec("SHA-256", "MGF1", new MGF1ParameterSpec("SHA-256"), 32, 1));
        }
    }

    public static class SHA384withRSA extends JDKPSSSigner {
        public SHA384withRSA() {
            super(new RSABlindedEngine(), new PSSParameterSpec("SHA-384", "MGF1", new MGF1ParameterSpec("SHA-384"), 48, 1));
        }
    }

    public static class SHA512withRSA extends JDKPSSSigner {
        public SHA512withRSA() {
            super(new RSABlindedEngine(), new PSSParameterSpec("SHA-512", "MGF1", new MGF1ParameterSpec("SHA-512"), 64, 1));
        }
    }

    public static class nonePSS extends JDKPSSSigner {
        public nonePSS() {
            super(new RSABlindedEngine(), null, true);
        }
    }

    public JDKPSSSigner(AsymmetricBlockCipher asymmetricBlockCipher, PSSParameterSpec pSSParameterSpec) {
        this(asymmetricBlockCipher, pSSParameterSpec, false);
    }

    public JDKPSSSigner(AsymmetricBlockCipher asymmetricBlockCipher, PSSParameterSpec pSSParameterSpec, boolean z2) {
        this.signer = asymmetricBlockCipher;
        this.originalSpec = pSSParameterSpec;
        if (pSSParameterSpec == null) {
            this.paramSpec = PSSParameterSpec.DEFAULT;
        } else {
            this.paramSpec = pSSParameterSpec;
        }
        this.mgfDigest = JCEDigestUtil.getDigest(this.paramSpec.getDigestAlgorithm());
        this.saltLength = this.paramSpec.getSaltLength();
        this.trailer = getTrailer(this.paramSpec.getTrailerField());
        this.isRaw = z2;
        setupContentDigest();
    }

    private byte getTrailer(int i2) {
        if (i2 == 1) {
            return PSSSigner.TRAILER_IMPLICIT;
        }
        throw new IllegalArgumentException("unknown trailer field");
    }

    private void setupContentDigest() {
        this.contentDigest = this.isRaw ? new NullPssDigest(this.mgfDigest) : this.mgfDigest;
    }

    @Override // java.security.SignatureSpi
    public Object engineGetParameter(String str) {
        throw new UnsupportedOperationException("engineGetParameter unsupported");
    }

    @Override // java.security.SignatureSpi
    public AlgorithmParameters engineGetParameters() throws NoSuchAlgorithmException, InvalidParameterSpecException, NoSuchProviderException {
        if (this.engineParams == null && this.paramSpec != null) {
            try {
                AlgorithmParameters algorithmParameters = AlgorithmParameters.getInstance("PSS", BouncyCastleProvider.PROVIDER_NAME);
                this.engineParams = algorithmParameters;
                algorithmParameters.init(this.paramSpec);
            } catch (Exception e2) {
                throw new RuntimeException(e2.toString());
            }
        }
        return this.engineParams;
    }

    @Override // java.security.SignatureSpi
    public void engineInitSign(PrivateKey privateKey) throws InvalidKeyException {
        if (!(privateKey instanceof RSAPrivateKey)) {
            throw new InvalidKeyException("Supplied key is not a RSAPrivateKey instance");
        }
        PSSSigner pSSSigner = new PSSSigner(this.signer, this.contentDigest, this.mgfDigest, this.saltLength, this.trailer);
        this.pss = pSSSigner;
        pSSSigner.init(true, RSAUtil.generatePrivateKeyParameter((RSAPrivateKey) privateKey));
    }

    @Override // java.security.SignatureSpi
    public void engineInitSign(PrivateKey privateKey, SecureRandom secureRandom) throws InvalidKeyException {
        if (!(privateKey instanceof RSAPrivateKey)) {
            throw new InvalidKeyException("Supplied key is not a RSAPrivateKey instance");
        }
        PSSSigner pSSSigner = new PSSSigner(this.signer, this.contentDigest, this.mgfDigest, this.saltLength, this.trailer);
        this.pss = pSSSigner;
        pSSSigner.init(true, new ParametersWithRandom(RSAUtil.generatePrivateKeyParameter((RSAPrivateKey) privateKey), secureRandom));
    }

    @Override // java.security.SignatureSpi
    public void engineInitVerify(PublicKey publicKey) throws InvalidKeyException {
        if (!(publicKey instanceof RSAPublicKey)) {
            throw new InvalidKeyException("Supplied key is not a RSAPublicKey instance");
        }
        PSSSigner pSSSigner = new PSSSigner(this.signer, this.contentDigest, this.mgfDigest, this.saltLength, this.trailer);
        this.pss = pSSSigner;
        pSSSigner.init(false, RSAUtil.generatePublicKeyParameter((RSAPublicKey) publicKey));
    }

    @Override // java.security.SignatureSpi
    public void engineSetParameter(String str, Object obj) {
        throw new UnsupportedOperationException("engineSetParameter unsupported");
    }

    @Override // java.security.SignatureSpi
    public void engineSetParameter(AlgorithmParameterSpec algorithmParameterSpec) throws InvalidParameterException {
        if (!(algorithmParameterSpec instanceof PSSParameterSpec)) {
            throw new InvalidParameterException("Only PSSParameterSpec supported");
        }
        PSSParameterSpec pSSParameterSpec = (PSSParameterSpec) algorithmParameterSpec;
        PSSParameterSpec pSSParameterSpec2 = this.originalSpec;
        if (pSSParameterSpec2 != null && !JCEDigestUtil.isSameDigest(pSSParameterSpec2.getDigestAlgorithm(), pSSParameterSpec.getDigestAlgorithm())) {
            throw new InvalidParameterException("parameter must be using " + this.originalSpec.getDigestAlgorithm());
        }
        if (!pSSParameterSpec.getMGFAlgorithm().equalsIgnoreCase("MGF1") && !pSSParameterSpec.getMGFAlgorithm().equals(PKCSObjectIdentifiers.id_mgf1.getId())) {
            throw new InvalidParameterException("unknown mask generation function specified");
        }
        if (!(pSSParameterSpec.getMGFParameters() instanceof MGF1ParameterSpec)) {
            throw new InvalidParameterException("unkown MGF parameters");
        }
        MGF1ParameterSpec mGF1ParameterSpec = (MGF1ParameterSpec) pSSParameterSpec.getMGFParameters();
        if (!JCEDigestUtil.isSameDigest(mGF1ParameterSpec.getDigestAlgorithm(), pSSParameterSpec.getDigestAlgorithm())) {
            throw new InvalidParameterException("digest algorithm for MGF should be the same as for PSS parameters.");
        }
        Digest digest = JCEDigestUtil.getDigest(mGF1ParameterSpec.getDigestAlgorithm());
        if (digest == null) {
            throw new InvalidParameterException("no match on MGF digest algorithm: " + mGF1ParameterSpec.getDigestAlgorithm());
        }
        this.engineParams = null;
        this.paramSpec = pSSParameterSpec;
        this.mgfDigest = digest;
        this.saltLength = pSSParameterSpec.getSaltLength();
        this.trailer = getTrailer(this.paramSpec.getTrailerField());
        setupContentDigest();
    }

    @Override // java.security.SignatureSpi
    public byte[] engineSign() throws SignatureException {
        try {
            return this.pss.generateSignature();
        } catch (CryptoException e2) {
            throw new SignatureException(e2.getMessage());
        }
    }

    @Override // java.security.SignatureSpi
    public void engineUpdate(byte b3) throws SignatureException {
        this.pss.update(b3);
    }

    @Override // java.security.SignatureSpi
    public void engineUpdate(byte[] bArr, int i2, int i3) throws SignatureException {
        this.pss.update(bArr, i2, i3);
    }

    @Override // java.security.SignatureSpi
    public boolean engineVerify(byte[] bArr) throws SignatureException {
        return this.pss.verifySignature(bArr);
    }
}
