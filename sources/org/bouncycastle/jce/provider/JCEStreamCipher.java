package org.bouncycastle.jce.provider;

import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidParameterSpecException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.crypto.spec.RC2ParameterSpec;
import javax.crypto.spec.RC5ParameterSpec;
import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.StreamBlockCipher;
import org.bouncycastle.crypto.StreamCipher;
import org.bouncycastle.crypto.engines.BlowfishEngine;
import org.bouncycastle.crypto.engines.DESEngine;
import org.bouncycastle.crypto.engines.DESedeEngine;
import org.bouncycastle.crypto.engines.RC4Engine;
import org.bouncycastle.crypto.engines.SkipjackEngine;
import org.bouncycastle.crypto.engines.TwofishEngine;
import org.bouncycastle.crypto.modes.CFBBlockCipher;
import org.bouncycastle.crypto.modes.OFBBlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.bouncycastle.jce.provider.PBE;

/* loaded from: classes9.dex */
public class JCEStreamCipher extends WrapCipherSpi implements PBE {
    private StreamCipher cipher;
    private int ivLength;
    private ParametersWithIV ivParam;
    private Class[] availableSpecs = {RC2ParameterSpec.class, RC5ParameterSpec.class, IvParameterSpec.class, PBEParameterSpec.class};
    private PBEParameterSpec pbeSpec = null;
    private String pbeAlgorithm = null;

    public static class Blowfish_CFB8 extends JCEStreamCipher {
        public Blowfish_CFB8() {
            super(new CFBBlockCipher(new BlowfishEngine(), 8), 64);
        }
    }

    public static class Blowfish_OFB8 extends JCEStreamCipher {
        public Blowfish_OFB8() {
            super(new OFBBlockCipher(new BlowfishEngine(), 8), 64);
        }
    }

    public static class DES_CFB8 extends JCEStreamCipher {
        public DES_CFB8() {
            super(new CFBBlockCipher(new DESEngine(), 8), 64);
        }
    }

    public static class DES_OFB8 extends JCEStreamCipher {
        public DES_OFB8() {
            super(new OFBBlockCipher(new DESEngine(), 8), 64);
        }
    }

    public static class DESede_CFB8 extends JCEStreamCipher {
        public DESede_CFB8() {
            super(new CFBBlockCipher(new DESedeEngine(), 8), 64);
        }
    }

    public static class DESede_OFB8 extends JCEStreamCipher {
        public DESede_OFB8() {
            super(new OFBBlockCipher(new DESedeEngine(), 8), 64);
        }
    }

    public static class PBEWithSHAAnd128BitRC4 extends JCEStreamCipher {
        public PBEWithSHAAnd128BitRC4() {
            super(new RC4Engine(), 0);
        }
    }

    public static class PBEWithSHAAnd40BitRC4 extends JCEStreamCipher {
        public PBEWithSHAAnd40BitRC4() {
            super(new RC4Engine(), 0);
        }
    }

    public static class Skipjack_CFB8 extends JCEStreamCipher {
        public Skipjack_CFB8() {
            super(new CFBBlockCipher(new SkipjackEngine(), 8), 64);
        }
    }

    public static class Skipjack_OFB8 extends JCEStreamCipher {
        public Skipjack_OFB8() {
            super(new OFBBlockCipher(new SkipjackEngine(), 8), 64);
        }
    }

    public static class Twofish_CFB8 extends JCEStreamCipher {
        public Twofish_CFB8() {
            super(new CFBBlockCipher(new TwofishEngine(), 8), 128);
        }
    }

    public static class Twofish_OFB8 extends JCEStreamCipher {
        public Twofish_OFB8() {
            super(new OFBBlockCipher(new TwofishEngine(), 8), 128);
        }
    }

    public JCEStreamCipher(BlockCipher blockCipher, int i2) {
        this.ivLength = i2;
        this.cipher = new StreamBlockCipher(blockCipher);
    }

    public JCEStreamCipher(StreamCipher streamCipher, int i2) {
        this.cipher = streamCipher;
        this.ivLength = i2;
    }

    @Override // org.bouncycastle.jce.provider.WrapCipherSpi, javax.crypto.CipherSpi
    public int engineDoFinal(byte[] bArr, int i2, int i3, byte[] bArr2, int i4) throws DataLengthException {
        if (i3 != 0) {
            this.cipher.processBytes(bArr, i2, i3, bArr2, i4);
        }
        this.cipher.reset();
        return i3;
    }

    @Override // org.bouncycastle.jce.provider.WrapCipherSpi, javax.crypto.CipherSpi
    public byte[] engineDoFinal(byte[] bArr, int i2, int i3) throws DataLengthException {
        if (i3 == 0) {
            this.cipher.reset();
            return new byte[0];
        }
        byte[] bArrEngineUpdate = engineUpdate(bArr, i2, i3);
        this.cipher.reset();
        return bArrEngineUpdate;
    }

    @Override // org.bouncycastle.jce.provider.WrapCipherSpi, javax.crypto.CipherSpi
    public int engineGetBlockSize() {
        return 0;
    }

    @Override // org.bouncycastle.jce.provider.WrapCipherSpi, javax.crypto.CipherSpi
    public byte[] engineGetIV() {
        ParametersWithIV parametersWithIV = this.ivParam;
        if (parametersWithIV != null) {
            return parametersWithIV.getIV();
        }
        return null;
    }

    @Override // org.bouncycastle.jce.provider.WrapCipherSpi, javax.crypto.CipherSpi
    public int engineGetKeySize(Key key) {
        return key.getEncoded().length * 8;
    }

    @Override // org.bouncycastle.jce.provider.WrapCipherSpi, javax.crypto.CipherSpi
    public int engineGetOutputSize(int i2) {
        return i2;
    }

    @Override // org.bouncycastle.jce.provider.WrapCipherSpi, javax.crypto.CipherSpi
    public AlgorithmParameters engineGetParameters() throws NoSuchAlgorithmException, InvalidParameterSpecException, NoSuchProviderException {
        AlgorithmParameters algorithmParameters = this.engineParams;
        if (algorithmParameters != null || this.pbeSpec == null) {
            return algorithmParameters;
        }
        try {
            AlgorithmParameters algorithmParameters2 = AlgorithmParameters.getInstance(this.pbeAlgorithm, BouncyCastleProvider.PROVIDER_NAME);
            algorithmParameters2.init(this.pbeSpec);
            return algorithmParameters2;
        } catch (Exception unused) {
            return null;
        }
    }

    @Override // org.bouncycastle.jce.provider.WrapCipherSpi, javax.crypto.CipherSpi
    public void engineInit(int i2, Key key, AlgorithmParameters algorithmParameters, SecureRandom secureRandom) throws InvalidParameterSpecException, InvalidKeyException, IllegalArgumentException, InvalidAlgorithmParameterException {
        AlgorithmParameterSpec parameterSpec = null;
        if (algorithmParameters != null) {
            int i3 = 0;
            while (true) {
                Class[] clsArr = this.availableSpecs;
                if (i3 == clsArr.length) {
                    break;
                }
                try {
                    parameterSpec = algorithmParameters.getParameterSpec(clsArr[i3]);
                    break;
                } catch (Exception unused) {
                    i3++;
                }
            }
            if (parameterSpec == null) {
                throw new InvalidAlgorithmParameterException("can't handle parameter " + algorithmParameters.toString());
            }
        }
        engineInit(i2, key, parameterSpec, secureRandom);
        this.engineParams = algorithmParameters;
    }

    @Override // org.bouncycastle.jce.provider.WrapCipherSpi, javax.crypto.CipherSpi
    public void engineInit(int i2, Key key, SecureRandom secureRandom) throws InvalidKeyException, IllegalArgumentException {
        try {
            engineInit(i2, key, (AlgorithmParameterSpec) null, secureRandom);
        } catch (InvalidAlgorithmParameterException e2) {
            throw new InvalidKeyException(e2.getMessage());
        }
    }

    @Override // org.bouncycastle.jce.provider.WrapCipherSpi, javax.crypto.CipherSpi
    public void engineInit(int i2, Key key, AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) throws InvalidKeyException, IllegalArgumentException, InvalidAlgorithmParameterException {
        CipherParameters keyParameter;
        this.pbeSpec = null;
        this.pbeAlgorithm = null;
        this.engineParams = null;
        if (!(key instanceof SecretKey)) {
            throw new InvalidKeyException("Key for algorithm " + key.getAlgorithm() + " not suitable for symmetric enryption.");
        }
        if (key instanceof JCEPBEKey) {
            JCEPBEKey jCEPBEKey = (JCEPBEKey) key;
            this.pbeAlgorithm = jCEPBEKey.getOID() != null ? jCEPBEKey.getOID().getId() : jCEPBEKey.getAlgorithm();
            if (jCEPBEKey.getParam() != null) {
                keyParameter = jCEPBEKey.getParam();
                this.pbeSpec = new PBEParameterSpec(jCEPBEKey.getSalt(), jCEPBEKey.getIterationCount());
            } else {
                if (!(algorithmParameterSpec instanceof PBEParameterSpec)) {
                    throw new InvalidAlgorithmParameterException("PBE requires PBE parameters to be set.");
                }
                CipherParameters cipherParametersMakePBEParameters = PBE.Util.makePBEParameters(jCEPBEKey, algorithmParameterSpec, this.cipher.getAlgorithmName());
                this.pbeSpec = (PBEParameterSpec) algorithmParameterSpec;
                keyParameter = cipherParametersMakePBEParameters;
            }
            if (jCEPBEKey.getIvSize() != 0) {
                this.ivParam = (ParametersWithIV) keyParameter;
            }
        } else if (algorithmParameterSpec == null) {
            keyParameter = new KeyParameter(key.getEncoded());
        } else {
            if (!(algorithmParameterSpec instanceof IvParameterSpec)) {
                throw new IllegalArgumentException("unknown parameter type.");
            }
            ParametersWithIV parametersWithIV = new ParametersWithIV(new KeyParameter(key.getEncoded()), ((IvParameterSpec) algorithmParameterSpec).getIV());
            this.ivParam = parametersWithIV;
            keyParameter = parametersWithIV;
        }
        if (this.ivLength != 0 && !(keyParameter instanceof ParametersWithIV)) {
            if (secureRandom == null) {
                secureRandom = new SecureRandom();
            }
            if (i2 != 1 && i2 != 3) {
                throw new InvalidAlgorithmParameterException("no IV set when one expected");
            }
            byte[] bArr = new byte[this.ivLength];
            secureRandom.nextBytes(bArr);
            ParametersWithIV parametersWithIV2 = new ParametersWithIV(keyParameter, bArr);
            this.ivParam = parametersWithIV2;
            keyParameter = parametersWithIV2;
        }
        if (i2 != 1) {
            if (i2 != 2) {
                if (i2 != 3) {
                    if (i2 != 4) {
                        System.out.println("eeek!");
                        return;
                    }
                }
            }
            this.cipher.init(false, keyParameter);
            return;
        }
        this.cipher.init(true, keyParameter);
    }

    @Override // org.bouncycastle.jce.provider.WrapCipherSpi, javax.crypto.CipherSpi
    public void engineSetMode(String str) {
        if (str.equalsIgnoreCase("ECB")) {
            return;
        }
        throw new IllegalArgumentException("can't support mode " + str);
    }

    @Override // org.bouncycastle.jce.provider.WrapCipherSpi, javax.crypto.CipherSpi
    public void engineSetPadding(String str) throws NoSuchPaddingException {
        if (str.equalsIgnoreCase("NoPadding")) {
            return;
        }
        throw new NoSuchPaddingException("Padding " + str + " unknown.");
    }

    @Override // org.bouncycastle.jce.provider.WrapCipherSpi, javax.crypto.CipherSpi
    public int engineUpdate(byte[] bArr, int i2, int i3, byte[] bArr2, int i4) throws ShortBufferException {
        try {
            this.cipher.processBytes(bArr, i2, i3, bArr2, i4);
            return i3;
        } catch (DataLengthException e2) {
            throw new ShortBufferException(e2.getMessage());
        }
    }

    @Override // org.bouncycastle.jce.provider.WrapCipherSpi, javax.crypto.CipherSpi
    public byte[] engineUpdate(byte[] bArr, int i2, int i3) throws DataLengthException {
        byte[] bArr2 = new byte[i3];
        this.cipher.processBytes(bArr, i2, i3, bArr2, 0);
        return bArr2;
    }
}
