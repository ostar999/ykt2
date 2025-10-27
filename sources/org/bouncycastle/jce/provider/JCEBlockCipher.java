package org.bouncycastle.jce.provider;

import cn.hutool.core.text.StrPool;
import java.io.IOException;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.InvalidParameterException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidParameterSpecException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.crypto.spec.RC2ParameterSpec;
import javax.crypto.spec.RC5ParameterSpec;
import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.BufferedBlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.engines.AESFastEngine;
import org.bouncycastle.crypto.engines.DESEngine;
import org.bouncycastle.crypto.engines.DESedeEngine;
import org.bouncycastle.crypto.engines.GOST28147Engine;
import org.bouncycastle.crypto.engines.RC2Engine;
import org.bouncycastle.crypto.engines.TwofishEngine;
import org.bouncycastle.crypto.modes.AEADBlockCipher;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.modes.CCMBlockCipher;
import org.bouncycastle.crypto.modes.CFBBlockCipher;
import org.bouncycastle.crypto.modes.CTSBlockCipher;
import org.bouncycastle.crypto.modes.EAXBlockCipher;
import org.bouncycastle.crypto.modes.GCMBlockCipher;
import org.bouncycastle.crypto.modes.GOFBBlockCipher;
import org.bouncycastle.crypto.modes.OFBBlockCipher;
import org.bouncycastle.crypto.modes.OpenPGPCFBBlockCipher;
import org.bouncycastle.crypto.modes.PGPCFBBlockCipher;
import org.bouncycastle.crypto.modes.SICBlockCipher;
import org.bouncycastle.crypto.paddings.BlockCipherPadding;
import org.bouncycastle.crypto.paddings.ISO10126d2Padding;
import org.bouncycastle.crypto.paddings.ISO7816d4Padding;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.paddings.TBCPadding;
import org.bouncycastle.crypto.paddings.X923Padding;
import org.bouncycastle.crypto.paddings.ZeroBytePadding;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.bouncycastle.crypto.params.ParametersWithRandom;
import org.bouncycastle.crypto.params.ParametersWithSBox;
import org.bouncycastle.crypto.params.RC2Parameters;
import org.bouncycastle.crypto.params.RC5Parameters;
import org.bouncycastle.jce.provider.PBE;
import org.bouncycastle.jce.spec.GOST28147ParameterSpec;
import org.bouncycastle.util.Strings;

/* loaded from: classes9.dex */
public class JCEBlockCipher extends WrapCipherSpi implements PBE {
    private Class[] availableSpecs;
    private BlockCipher baseEngine;
    private GenericBlockCipher cipher;
    private int ivLength;
    private ParametersWithIV ivParam;
    private String modeName;
    private boolean padded;
    private String pbeAlgorithm;
    private PBEParameterSpec pbeSpec;

    public static class AEADGenericBlockCipher implements GenericBlockCipher {
        private AEADBlockCipher cipher;

        public AEADGenericBlockCipher(AEADBlockCipher aEADBlockCipher) {
            this.cipher = aEADBlockCipher;
        }

        @Override // org.bouncycastle.jce.provider.JCEBlockCipher.GenericBlockCipher
        public int doFinal(byte[] bArr, int i2) throws InvalidCipherTextException, IllegalStateException {
            return this.cipher.doFinal(bArr, i2);
        }

        @Override // org.bouncycastle.jce.provider.JCEBlockCipher.GenericBlockCipher
        public String getAlgorithmName() {
            return this.cipher.getUnderlyingCipher().getAlgorithmName();
        }

        @Override // org.bouncycastle.jce.provider.JCEBlockCipher.GenericBlockCipher
        public int getOutputSize(int i2) {
            return this.cipher.getOutputSize(i2);
        }

        @Override // org.bouncycastle.jce.provider.JCEBlockCipher.GenericBlockCipher
        public BlockCipher getUnderlyingCipher() {
            return this.cipher.getUnderlyingCipher();
        }

        @Override // org.bouncycastle.jce.provider.JCEBlockCipher.GenericBlockCipher
        public int getUpdateOutputSize(int i2) {
            return this.cipher.getUpdateOutputSize(i2);
        }

        @Override // org.bouncycastle.jce.provider.JCEBlockCipher.GenericBlockCipher
        public void init(boolean z2, CipherParameters cipherParameters) throws IllegalArgumentException {
            this.cipher.init(z2, cipherParameters);
        }

        @Override // org.bouncycastle.jce.provider.JCEBlockCipher.GenericBlockCipher
        public int processByte(byte b3, byte[] bArr, int i2) throws DataLengthException {
            return this.cipher.processByte(b3, bArr, i2);
        }

        @Override // org.bouncycastle.jce.provider.JCEBlockCipher.GenericBlockCipher
        public int processBytes(byte[] bArr, int i2, int i3, byte[] bArr2, int i4) throws DataLengthException {
            return this.cipher.processBytes(bArr, i2, i3, bArr2, i4);
        }

        @Override // org.bouncycastle.jce.provider.JCEBlockCipher.GenericBlockCipher
        public boolean wrapOnNoPadding() {
            return false;
        }
    }

    public static class BufferedGenericBlockCipher implements GenericBlockCipher {
        private BufferedBlockCipher cipher;

        public BufferedGenericBlockCipher(BlockCipher blockCipher) {
            this.cipher = new PaddedBufferedBlockCipher(blockCipher);
        }

        public BufferedGenericBlockCipher(BlockCipher blockCipher, BlockCipherPadding blockCipherPadding) {
            this.cipher = new PaddedBufferedBlockCipher(blockCipher, blockCipherPadding);
        }

        public BufferedGenericBlockCipher(BufferedBlockCipher bufferedBlockCipher) {
            this.cipher = bufferedBlockCipher;
        }

        @Override // org.bouncycastle.jce.provider.JCEBlockCipher.GenericBlockCipher
        public int doFinal(byte[] bArr, int i2) throws InvalidCipherTextException, IllegalStateException {
            return this.cipher.doFinal(bArr, i2);
        }

        @Override // org.bouncycastle.jce.provider.JCEBlockCipher.GenericBlockCipher
        public String getAlgorithmName() {
            return this.cipher.getUnderlyingCipher().getAlgorithmName();
        }

        @Override // org.bouncycastle.jce.provider.JCEBlockCipher.GenericBlockCipher
        public int getOutputSize(int i2) {
            return this.cipher.getOutputSize(i2);
        }

        @Override // org.bouncycastle.jce.provider.JCEBlockCipher.GenericBlockCipher
        public BlockCipher getUnderlyingCipher() {
            return this.cipher.getUnderlyingCipher();
        }

        @Override // org.bouncycastle.jce.provider.JCEBlockCipher.GenericBlockCipher
        public int getUpdateOutputSize(int i2) {
            return this.cipher.getUpdateOutputSize(i2);
        }

        @Override // org.bouncycastle.jce.provider.JCEBlockCipher.GenericBlockCipher
        public void init(boolean z2, CipherParameters cipherParameters) throws IllegalArgumentException {
            this.cipher.init(z2, cipherParameters);
        }

        @Override // org.bouncycastle.jce.provider.JCEBlockCipher.GenericBlockCipher
        public int processByte(byte b3, byte[] bArr, int i2) throws DataLengthException {
            return this.cipher.processByte(b3, bArr, i2);
        }

        @Override // org.bouncycastle.jce.provider.JCEBlockCipher.GenericBlockCipher
        public int processBytes(byte[] bArr, int i2, int i3, byte[] bArr2, int i4) throws DataLengthException {
            return this.cipher.processBytes(bArr, i2, i3, bArr2, i4);
        }

        @Override // org.bouncycastle.jce.provider.JCEBlockCipher.GenericBlockCipher
        public boolean wrapOnNoPadding() {
            return !(this.cipher instanceof CTSBlockCipher);
        }
    }

    public static class DES extends JCEBlockCipher {
        public DES() {
            super(new DESEngine());
        }
    }

    public static class DESCBC extends JCEBlockCipher {
        public DESCBC() {
            super(new CBCBlockCipher(new DESEngine()), 64);
        }
    }

    public static class GOST28147 extends JCEBlockCipher {
        public GOST28147() {
            super(new GOST28147Engine());
        }
    }

    public static class GOST28147cbc extends JCEBlockCipher {
        public GOST28147cbc() {
            super(new CBCBlockCipher(new GOST28147Engine()), 64);
        }
    }

    public interface GenericBlockCipher {
        int doFinal(byte[] bArr, int i2) throws InvalidCipherTextException, IllegalStateException;

        String getAlgorithmName();

        int getOutputSize(int i2);

        BlockCipher getUnderlyingCipher();

        int getUpdateOutputSize(int i2);

        void init(boolean z2, CipherParameters cipherParameters) throws IllegalArgumentException;

        int processByte(byte b3, byte[] bArr, int i2) throws DataLengthException;

        int processBytes(byte[] bArr, int i2, int i3, byte[] bArr2, int i4) throws DataLengthException;

        boolean wrapOnNoPadding();
    }

    public static class PBEWithAESCBC extends JCEBlockCipher {
        public PBEWithAESCBC() {
            super(new CBCBlockCipher(new AESFastEngine()));
        }
    }

    public static class PBEWithMD5AndDES extends JCEBlockCipher {
        public PBEWithMD5AndDES() {
            super(new CBCBlockCipher(new DESEngine()));
        }
    }

    public static class PBEWithMD5AndRC2 extends JCEBlockCipher {
        public PBEWithMD5AndRC2() {
            super(new CBCBlockCipher(new RC2Engine()));
        }
    }

    public static class PBEWithSHA1AndDES extends JCEBlockCipher {
        public PBEWithSHA1AndDES() {
            super(new CBCBlockCipher(new DESEngine()));
        }
    }

    public static class PBEWithSHA1AndRC2 extends JCEBlockCipher {
        public PBEWithSHA1AndRC2() {
            super(new CBCBlockCipher(new RC2Engine()));
        }
    }

    public static class PBEWithSHAAnd128BitRC2 extends JCEBlockCipher {
        public PBEWithSHAAnd128BitRC2() {
            super(new CBCBlockCipher(new RC2Engine()));
        }
    }

    public static class PBEWithSHAAnd40BitRC2 extends JCEBlockCipher {
        public PBEWithSHAAnd40BitRC2() {
            super(new CBCBlockCipher(new RC2Engine()));
        }
    }

    public static class PBEWithSHAAndDES2Key extends JCEBlockCipher {
        public PBEWithSHAAndDES2Key() {
            super(new CBCBlockCipher(new DESedeEngine()));
        }
    }

    public static class PBEWithSHAAndDES3Key extends JCEBlockCipher {
        public PBEWithSHAAndDES3Key() {
            super(new CBCBlockCipher(new DESedeEngine()));
        }
    }

    public static class PBEWithSHAAndTwofish extends JCEBlockCipher {
        public PBEWithSHAAndTwofish() {
            super(new CBCBlockCipher(new TwofishEngine()));
        }
    }

    public static class RC2 extends JCEBlockCipher {
        public RC2() {
            super(new RC2Engine());
        }
    }

    public static class RC2CBC extends JCEBlockCipher {
        public RC2CBC() {
            super(new CBCBlockCipher(new RC2Engine()), 64);
        }
    }

    public JCEBlockCipher(BlockCipher blockCipher) {
        this.availableSpecs = new Class[]{RC2ParameterSpec.class, RC5ParameterSpec.class, IvParameterSpec.class, PBEParameterSpec.class, GOST28147ParameterSpec.class};
        this.ivLength = 0;
        this.pbeSpec = null;
        this.pbeAlgorithm = null;
        this.modeName = null;
        this.baseEngine = blockCipher;
        this.cipher = new BufferedGenericBlockCipher(blockCipher);
    }

    public JCEBlockCipher(BlockCipher blockCipher, int i2) {
        this.availableSpecs = new Class[]{RC2ParameterSpec.class, RC5ParameterSpec.class, IvParameterSpec.class, PBEParameterSpec.class, GOST28147ParameterSpec.class};
        this.ivLength = 0;
        this.pbeSpec = null;
        this.pbeAlgorithm = null;
        this.modeName = null;
        this.baseEngine = blockCipher;
        this.cipher = new BufferedGenericBlockCipher(blockCipher);
        this.ivLength = i2 / 8;
    }

    public JCEBlockCipher(BufferedBlockCipher bufferedBlockCipher, int i2) {
        this.availableSpecs = new Class[]{RC2ParameterSpec.class, RC5ParameterSpec.class, IvParameterSpec.class, PBEParameterSpec.class, GOST28147ParameterSpec.class};
        this.ivLength = 0;
        this.pbeSpec = null;
        this.pbeAlgorithm = null;
        this.modeName = null;
        this.baseEngine = bufferedBlockCipher.getUnderlyingCipher();
        this.cipher = new BufferedGenericBlockCipher(bufferedBlockCipher);
        this.ivLength = i2 / 8;
    }

    private boolean isAEADModeName(String str) {
        return "CCM".equals(str) || "EAX".equals(str) || "GCM".equals(str);
    }

    @Override // org.bouncycastle.jce.provider.WrapCipherSpi, javax.crypto.CipherSpi
    public int engineDoFinal(byte[] bArr, int i2, int i3, byte[] bArr2, int i4) throws BadPaddingException, IllegalBlockSizeException {
        int iProcessBytes = i3 != 0 ? this.cipher.processBytes(bArr, i2, i3, bArr2, i4) : 0;
        try {
            return iProcessBytes + this.cipher.doFinal(bArr2, i4 + iProcessBytes);
        } catch (DataLengthException e2) {
            throw new IllegalBlockSizeException(e2.getMessage());
        } catch (InvalidCipherTextException e3) {
            throw new BadPaddingException(e3.getMessage());
        }
    }

    @Override // org.bouncycastle.jce.provider.WrapCipherSpi, javax.crypto.CipherSpi
    public byte[] engineDoFinal(byte[] bArr, int i2, int i3) throws BadPaddingException, IllegalBlockSizeException {
        int iEngineGetOutputSize = engineGetOutputSize(i3);
        byte[] bArr2 = new byte[iEngineGetOutputSize];
        int iProcessBytes = i3 != 0 ? this.cipher.processBytes(bArr, i2, i3, bArr2, 0) : 0;
        try {
            int iDoFinal = iProcessBytes + this.cipher.doFinal(bArr2, iProcessBytes);
            if (iDoFinal == iEngineGetOutputSize) {
                return bArr2;
            }
            byte[] bArr3 = new byte[iDoFinal];
            System.arraycopy(bArr2, 0, bArr3, 0, iDoFinal);
            return bArr3;
        } catch (DataLengthException e2) {
            throw new IllegalBlockSizeException(e2.getMessage());
        } catch (InvalidCipherTextException e3) {
            throw new BadPaddingException(e3.getMessage());
        }
    }

    @Override // org.bouncycastle.jce.provider.WrapCipherSpi, javax.crypto.CipherSpi
    public int engineGetBlockSize() {
        return this.baseEngine.getBlockSize();
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
        return this.cipher.getOutputSize(i2);
    }

    @Override // org.bouncycastle.jce.provider.WrapCipherSpi, javax.crypto.CipherSpi
    public AlgorithmParameters engineGetParameters() throws NoSuchAlgorithmException, InvalidParameterSpecException, IOException, NoSuchProviderException {
        if (this.engineParams == null) {
            if (this.pbeSpec != null) {
                try {
                    AlgorithmParameters algorithmParameters = AlgorithmParameters.getInstance(this.pbeAlgorithm, BouncyCastleProvider.PROVIDER_NAME);
                    this.engineParams = algorithmParameters;
                    algorithmParameters.init(this.pbeSpec);
                } catch (Exception unused) {
                    return null;
                }
            } else if (this.ivParam != null) {
                String algorithmName = this.cipher.getUnderlyingCipher().getAlgorithmName();
                if (algorithmName.indexOf(47) >= 0) {
                    algorithmName = algorithmName.substring(0, algorithmName.indexOf(47));
                }
                try {
                    AlgorithmParameters algorithmParameters2 = AlgorithmParameters.getInstance(algorithmName, BouncyCastleProvider.PROVIDER_NAME);
                    this.engineParams = algorithmParameters2;
                    algorithmParameters2.init(this.ivParam.getIV());
                } catch (Exception e2) {
                    throw new RuntimeException(e2.toString());
                }
            }
        }
        return this.engineParams;
    }

    @Override // org.bouncycastle.jce.provider.WrapCipherSpi, javax.crypto.CipherSpi
    public void engineInit(int i2, Key key, AlgorithmParameters algorithmParameters, SecureRandom secureRandom) throws InvalidParameterSpecException, InvalidKeyException, InvalidAlgorithmParameterException {
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
    public void engineInit(int i2, Key key, SecureRandom secureRandom) throws InvalidKeyException {
        try {
            engineInit(i2, key, (AlgorithmParameterSpec) null, secureRandom);
        } catch (InvalidAlgorithmParameterException e2) {
            throw new InvalidKeyException(e2.getMessage());
        }
    }

    @Override // org.bouncycastle.jce.provider.WrapCipherSpi, javax.crypto.CipherSpi
    public void engineInit(int i2, Key key, AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) throws InvalidKeyException, InvalidAlgorithmParameterException {
        CipherParameters keyParameter;
        ParametersWithIV parametersWithIV;
        CipherParameters cipherParameters;
        this.pbeSpec = null;
        this.pbeAlgorithm = null;
        this.engineParams = null;
        if (!(key instanceof SecretKey)) {
            throw new InvalidKeyException("Key for algorithm " + key.getAlgorithm() + " not suitable for symmetric enryption.");
        }
        if (algorithmParameterSpec == null && this.baseEngine.getAlgorithmName().startsWith("RC5-64")) {
            throw new InvalidAlgorithmParameterException("RC5 requires an RC5ParametersSpec to be passed in.");
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
                this.pbeSpec = (PBEParameterSpec) algorithmParameterSpec;
                keyParameter = PBE.Util.makePBEParameters(jCEPBEKey, algorithmParameterSpec, this.cipher.getUnderlyingCipher().getAlgorithmName());
            }
            if (keyParameter instanceof ParametersWithIV) {
                this.ivParam = (ParametersWithIV) keyParameter;
            }
        } else if (algorithmParameterSpec == null) {
            keyParameter = new KeyParameter(key.getEncoded());
        } else if (algorithmParameterSpec instanceof IvParameterSpec) {
            if (this.ivLength != 0) {
                IvParameterSpec ivParameterSpec = (IvParameterSpec) algorithmParameterSpec;
                if (ivParameterSpec.getIV().length != this.ivLength && !isAEADModeName(this.modeName)) {
                    throw new InvalidAlgorithmParameterException("IV must be " + this.ivLength + " bytes long.");
                }
                ParametersWithIV parametersWithIV2 = new ParametersWithIV(new KeyParameter(key.getEncoded()), ivParameterSpec.getIV());
                this.ivParam = parametersWithIV2;
                cipherParameters = parametersWithIV2;
                keyParameter = cipherParameters;
            } else {
                String str = this.modeName;
                if (str != null && str.equals("ECB")) {
                    throw new InvalidAlgorithmParameterException("ECB mode does not use an IV");
                }
                keyParameter = new KeyParameter(key.getEncoded());
            }
        } else if (algorithmParameterSpec instanceof GOST28147ParameterSpec) {
            GOST28147ParameterSpec gOST28147ParameterSpec = (GOST28147ParameterSpec) algorithmParameterSpec;
            CipherParameters parametersWithSBox = new ParametersWithSBox(new KeyParameter(key.getEncoded()), gOST28147ParameterSpec.getSbox());
            cipherParameters = parametersWithSBox;
            if (gOST28147ParameterSpec.getIV() != null) {
                cipherParameters = parametersWithSBox;
                if (this.ivLength != 0) {
                    parametersWithIV = new ParametersWithIV(parametersWithSBox, gOST28147ParameterSpec.getIV());
                    this.ivParam = parametersWithIV;
                    keyParameter = parametersWithIV;
                }
            }
            keyParameter = cipherParameters;
        } else {
            if (algorithmParameterSpec instanceof RC2ParameterSpec) {
                RC2ParameterSpec rC2ParameterSpec = (RC2ParameterSpec) algorithmParameterSpec;
                CipherParameters rC2Parameters = new RC2Parameters(key.getEncoded(), rC2ParameterSpec.getEffectiveKeyBits());
                cipherParameters = rC2Parameters;
                if (rC2ParameterSpec.getIV() != null) {
                    cipherParameters = rC2Parameters;
                    if (this.ivLength != 0) {
                        parametersWithIV = new ParametersWithIV(rC2Parameters, rC2ParameterSpec.getIV());
                    }
                }
                keyParameter = cipherParameters;
            } else {
                if (!(algorithmParameterSpec instanceof RC5ParameterSpec)) {
                    throw new InvalidAlgorithmParameterException("unknown parameter type.");
                }
                RC5ParameterSpec rC5ParameterSpec = (RC5ParameterSpec) algorithmParameterSpec;
                RC5Parameters rC5Parameters = new RC5Parameters(key.getEncoded(), rC5ParameterSpec.getRounds());
                if (!this.baseEngine.getAlgorithmName().startsWith("RC5")) {
                    throw new InvalidAlgorithmParameterException("RC5 parameters passed to a cipher that is not RC5.");
                }
                if (this.baseEngine.getAlgorithmName().equals("RC5-32")) {
                    if (rC5ParameterSpec.getWordSize() != 32) {
                        throw new InvalidAlgorithmParameterException("RC5 already set up for a word size of 32 not " + rC5ParameterSpec.getWordSize() + StrPool.DOT);
                    }
                } else if (this.baseEngine.getAlgorithmName().equals("RC5-64") && rC5ParameterSpec.getWordSize() != 64) {
                    throw new InvalidAlgorithmParameterException("RC5 already set up for a word size of 64 not " + rC5ParameterSpec.getWordSize() + StrPool.DOT);
                }
                if (rC5ParameterSpec.getIV() == null || this.ivLength == 0) {
                    keyParameter = rC5Parameters;
                } else {
                    parametersWithIV = new ParametersWithIV(rC5Parameters, rC5ParameterSpec.getIV());
                }
            }
            this.ivParam = parametersWithIV;
            keyParameter = parametersWithIV;
        }
        if (this.ivLength != 0 && !(keyParameter instanceof ParametersWithIV)) {
            SecureRandom secureRandom2 = secureRandom == null ? new SecureRandom() : secureRandom;
            if (i2 == 1 || i2 == 3) {
                byte[] bArr = new byte[this.ivLength];
                secureRandom2.nextBytes(bArr);
                ParametersWithIV parametersWithIV3 = new ParametersWithIV(keyParameter, bArr);
                this.ivParam = parametersWithIV3;
                keyParameter = parametersWithIV3;
            } else if (this.cipher.getUnderlyingCipher().getAlgorithmName().indexOf("PGPCFB") < 0) {
                throw new InvalidAlgorithmParameterException("no IV set when one expected");
            }
        }
        if (secureRandom != null && this.padded) {
            keyParameter = new ParametersWithRandom(keyParameter, secureRandom);
        }
        try {
            if (i2 != 1) {
                if (i2 != 2) {
                    if (i2 != 3) {
                        if (i2 != 4) {
                            throw new InvalidParameterException("unknown opmode " + i2 + " passed");
                        }
                    }
                }
                this.cipher.init(false, keyParameter);
                return;
            }
            this.cipher.init(true, keyParameter);
        } catch (Exception e2) {
            throw new InvalidKeyException(e2.getMessage());
        }
    }

    @Override // org.bouncycastle.jce.provider.WrapCipherSpi, javax.crypto.CipherSpi
    public void engineSetMode(String str) throws NoSuchAlgorithmException, NumberFormatException {
        GenericBlockCipher aEADGenericBlockCipher;
        BufferedGenericBlockCipher bufferedGenericBlockCipher;
        String upperCase = Strings.toUpperCase(str);
        this.modeName = upperCase;
        if (upperCase.equals("ECB")) {
            this.ivLength = 0;
            aEADGenericBlockCipher = new BufferedGenericBlockCipher(this.baseEngine);
        } else if (this.modeName.equals("CBC")) {
            this.ivLength = this.baseEngine.getBlockSize();
            aEADGenericBlockCipher = new BufferedGenericBlockCipher(new CBCBlockCipher(this.baseEngine));
        } else if (this.modeName.startsWith("OFB")) {
            this.ivLength = this.baseEngine.getBlockSize();
            if (this.modeName.length() != 3) {
                bufferedGenericBlockCipher = new BufferedGenericBlockCipher(new OFBBlockCipher(this.baseEngine, Integer.parseInt(this.modeName.substring(3))));
                this.cipher = bufferedGenericBlockCipher;
                return;
            }
            BlockCipher blockCipher = this.baseEngine;
            aEADGenericBlockCipher = new BufferedGenericBlockCipher(new OFBBlockCipher(blockCipher, blockCipher.getBlockSize() * 8));
        } else if (this.modeName.startsWith("CFB")) {
            this.ivLength = this.baseEngine.getBlockSize();
            if (this.modeName.length() != 3) {
                bufferedGenericBlockCipher = new BufferedGenericBlockCipher(new CFBBlockCipher(this.baseEngine, Integer.parseInt(this.modeName.substring(3))));
                this.cipher = bufferedGenericBlockCipher;
                return;
            }
            BlockCipher blockCipher2 = this.baseEngine;
            aEADGenericBlockCipher = new BufferedGenericBlockCipher(new CFBBlockCipher(blockCipher2, blockCipher2.getBlockSize() * 8));
        } else {
            if (this.modeName.startsWith("PGP")) {
                boolean zEqualsIgnoreCase = this.modeName.equalsIgnoreCase("PGPCFBwithIV");
                this.ivLength = this.baseEngine.getBlockSize();
                bufferedGenericBlockCipher = new BufferedGenericBlockCipher(new PGPCFBBlockCipher(this.baseEngine, zEqualsIgnoreCase));
                this.cipher = bufferedGenericBlockCipher;
                return;
            }
            if (this.modeName.equalsIgnoreCase("OpenPGPCFB")) {
                this.ivLength = 0;
                aEADGenericBlockCipher = new BufferedGenericBlockCipher(new OpenPGPCFBBlockCipher(this.baseEngine));
            } else if (this.modeName.startsWith("SIC")) {
                int blockSize = this.baseEngine.getBlockSize();
                this.ivLength = blockSize;
                if (blockSize < 16) {
                    throw new IllegalArgumentException("Warning: SIC-Mode can become a twotime-pad if the blocksize of the cipher is too small. Use a cipher with a block size of at least 128 bits (e.g. AES)");
                }
                aEADGenericBlockCipher = new BufferedGenericBlockCipher(new BufferedBlockCipher(new SICBlockCipher(this.baseEngine)));
            } else if (this.modeName.startsWith("CTR")) {
                this.ivLength = this.baseEngine.getBlockSize();
                aEADGenericBlockCipher = new BufferedGenericBlockCipher(new BufferedBlockCipher(new SICBlockCipher(this.baseEngine)));
            } else if (this.modeName.startsWith("GOFB")) {
                this.ivLength = this.baseEngine.getBlockSize();
                aEADGenericBlockCipher = new BufferedGenericBlockCipher(new BufferedBlockCipher(new GOFBBlockCipher(this.baseEngine)));
            } else if (this.modeName.startsWith("CTS")) {
                this.ivLength = this.baseEngine.getBlockSize();
                aEADGenericBlockCipher = new BufferedGenericBlockCipher(new CTSBlockCipher(new CBCBlockCipher(this.baseEngine)));
            } else if (this.modeName.startsWith("CCM")) {
                this.ivLength = this.baseEngine.getBlockSize();
                aEADGenericBlockCipher = new AEADGenericBlockCipher(new CCMBlockCipher(this.baseEngine));
            } else if (this.modeName.startsWith("EAX")) {
                this.ivLength = this.baseEngine.getBlockSize();
                aEADGenericBlockCipher = new AEADGenericBlockCipher(new EAXBlockCipher(this.baseEngine));
            } else {
                if (!this.modeName.startsWith("GCM")) {
                    throw new NoSuchAlgorithmException("can't support mode " + str);
                }
                this.ivLength = this.baseEngine.getBlockSize();
                aEADGenericBlockCipher = new AEADGenericBlockCipher(new GCMBlockCipher(this.baseEngine));
            }
        }
        this.cipher = aEADGenericBlockCipher;
    }

    @Override // org.bouncycastle.jce.provider.WrapCipherSpi, javax.crypto.CipherSpi
    public void engineSetPadding(String str) throws NoSuchPaddingException {
        BufferedGenericBlockCipher bufferedGenericBlockCipher;
        String upperCase = Strings.toUpperCase(str);
        if (upperCase.equals("NOPADDING")) {
            if (!this.cipher.wrapOnNoPadding()) {
                return;
            } else {
                bufferedGenericBlockCipher = new BufferedGenericBlockCipher(new BufferedBlockCipher(this.cipher.getUnderlyingCipher()));
            }
        } else if (upperCase.equals("WITHCTS")) {
            bufferedGenericBlockCipher = new BufferedGenericBlockCipher(new CTSBlockCipher(this.cipher.getUnderlyingCipher()));
        } else {
            this.padded = true;
            if (isAEADModeName(this.modeName)) {
                throw new NoSuchPaddingException("Only NoPadding can be used with AEAD modes.");
            }
            if (upperCase.equals("PKCS5PADDING") || upperCase.equals("PKCS7PADDING")) {
                bufferedGenericBlockCipher = new BufferedGenericBlockCipher(this.cipher.getUnderlyingCipher());
            } else if (upperCase.equals("ZEROBYTEPADDING")) {
                bufferedGenericBlockCipher = new BufferedGenericBlockCipher(this.cipher.getUnderlyingCipher(), new ZeroBytePadding());
            } else if (upperCase.equals("ISO10126PADDING") || upperCase.equals("ISO10126-2PADDING")) {
                bufferedGenericBlockCipher = new BufferedGenericBlockCipher(this.cipher.getUnderlyingCipher(), new ISO10126d2Padding());
            } else if (upperCase.equals("X9.23PADDING") || upperCase.equals("X923PADDING")) {
                bufferedGenericBlockCipher = new BufferedGenericBlockCipher(this.cipher.getUnderlyingCipher(), new X923Padding());
            } else if (upperCase.equals("ISO7816-4PADDING") || upperCase.equals("ISO9797-1PADDING")) {
                bufferedGenericBlockCipher = new BufferedGenericBlockCipher(this.cipher.getUnderlyingCipher(), new ISO7816d4Padding());
            } else {
                if (!upperCase.equals("TBCPADDING")) {
                    throw new NoSuchPaddingException("Padding " + str + " unknown.");
                }
                bufferedGenericBlockCipher = new BufferedGenericBlockCipher(this.cipher.getUnderlyingCipher(), new TBCPadding());
            }
        }
        this.cipher = bufferedGenericBlockCipher;
    }

    @Override // org.bouncycastle.jce.provider.WrapCipherSpi, javax.crypto.CipherSpi
    public int engineUpdate(byte[] bArr, int i2, int i3, byte[] bArr2, int i4) throws ShortBufferException {
        try {
            return this.cipher.processBytes(bArr, i2, i3, bArr2, i4);
        } catch (DataLengthException e2) {
            throw new ShortBufferException(e2.getMessage());
        }
    }

    @Override // org.bouncycastle.jce.provider.WrapCipherSpi, javax.crypto.CipherSpi
    public byte[] engineUpdate(byte[] bArr, int i2, int i3) throws DataLengthException {
        int updateOutputSize = this.cipher.getUpdateOutputSize(i3);
        if (updateOutputSize <= 0) {
            this.cipher.processBytes(bArr, i2, i3, null, 0);
            return null;
        }
        byte[] bArr2 = new byte[updateOutputSize];
        int iProcessBytes = this.cipher.processBytes(bArr, i2, i3, bArr2, 0);
        if (iProcessBytes == 0) {
            return null;
        }
        if (iProcessBytes == updateOutputSize) {
            return bArr2;
        }
        byte[] bArr3 = new byte[iProcessBytes];
        System.arraycopy(bArr2, 0, bArr3, 0, iProcessBytes);
        return bArr3;
    }
}
