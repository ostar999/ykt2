package org.bouncycastle.jce.provider;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.MacSpi;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEParameterSpec;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.Mac;
import org.bouncycastle.crypto.digests.MD2Digest;
import org.bouncycastle.crypto.digests.MD4Digest;
import org.bouncycastle.crypto.digests.MD5Digest;
import org.bouncycastle.crypto.digests.RIPEMD128Digest;
import org.bouncycastle.crypto.digests.RIPEMD160Digest;
import org.bouncycastle.crypto.digests.SHA1Digest;
import org.bouncycastle.crypto.digests.SHA224Digest;
import org.bouncycastle.crypto.digests.SHA256Digest;
import org.bouncycastle.crypto.digests.SHA384Digest;
import org.bouncycastle.crypto.digests.SHA512Digest;
import org.bouncycastle.crypto.digests.TigerDigest;
import org.bouncycastle.crypto.engines.DESEngine;
import org.bouncycastle.crypto.engines.RC2Engine;
import org.bouncycastle.crypto.macs.CBCBlockCipherMac;
import org.bouncycastle.crypto.macs.CFBBlockCipherMac;
import org.bouncycastle.crypto.macs.GOST28147Mac;
import org.bouncycastle.crypto.macs.HMac;
import org.bouncycastle.crypto.macs.ISO9797Alg3Mac;
import org.bouncycastle.crypto.macs.OldHMac;
import org.bouncycastle.crypto.paddings.ISO7816d4Padding;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.bouncycastle.jce.provider.PBE;

/* loaded from: classes9.dex */
public class JCEMac extends MacSpi implements PBE {
    private int keySize;
    private Mac macEngine;
    private int pbeHash;
    private int pbeType;

    public static class DES extends JCEMac {
        public DES() {
            super(new CBCBlockCipherMac(new DESEngine()));
        }
    }

    public static class DES9797Alg3 extends JCEMac {
        public DES9797Alg3() {
            super(new ISO9797Alg3Mac(new DESEngine()));
        }
    }

    public static class DES9797Alg3with7816d4 extends JCEMac {
        public DES9797Alg3with7816d4() {
            super(new ISO9797Alg3Mac(new DESEngine(), new ISO7816d4Padding()));
        }
    }

    public static class DESCFB8 extends JCEMac {
        public DESCFB8() {
            super(new CFBBlockCipherMac(new DESEngine()));
        }
    }

    public static class GOST28147 extends JCEMac {
        public GOST28147() {
            super(new GOST28147Mac());
        }
    }

    public static class MD2 extends JCEMac {
        public MD2() {
            super(new HMac(new MD2Digest()));
        }
    }

    public static class MD4 extends JCEMac {
        public MD4() {
            super(new HMac(new MD4Digest()));
        }
    }

    public static class MD5 extends JCEMac {
        public MD5() {
            super(new HMac(new MD5Digest()));
        }
    }

    public static class OldSHA384 extends JCEMac {
        public OldSHA384() {
            super(new OldHMac(new SHA384Digest()));
        }
    }

    public static class OldSHA512 extends JCEMac {
        public OldSHA512() {
            super(new OldHMac(new SHA512Digest()));
        }
    }

    public static class PBEWithRIPEMD160 extends JCEMac {
        public PBEWithRIPEMD160() {
            super(new HMac(new RIPEMD160Digest()), 2, 2, 160);
        }
    }

    public static class PBEWithSHA extends JCEMac {
        public PBEWithSHA() {
            super(new HMac(new SHA1Digest()), 2, 1, 160);
        }
    }

    public static class PBEWithTiger extends JCEMac {
        public PBEWithTiger() {
            super(new HMac(new TigerDigest()), 2, 3, 192);
        }
    }

    public static class RC2 extends JCEMac {
        public RC2() {
            super(new CBCBlockCipherMac(new RC2Engine()));
        }
    }

    public static class RC2CFB8 extends JCEMac {
        public RC2CFB8() {
            super(new CFBBlockCipherMac(new RC2Engine()));
        }
    }

    public static class RIPEMD128 extends JCEMac {
        public RIPEMD128() {
            super(new HMac(new RIPEMD128Digest()));
        }
    }

    public static class RIPEMD160 extends JCEMac {
        public RIPEMD160() {
            super(new HMac(new RIPEMD160Digest()));
        }
    }

    public static class SHA1 extends JCEMac {
        public SHA1() {
            super(new HMac(new SHA1Digest()));
        }
    }

    public static class SHA224 extends JCEMac {
        public SHA224() {
            super(new HMac(new SHA224Digest()));
        }
    }

    public static class SHA256 extends JCEMac {
        public SHA256() {
            super(new HMac(new SHA256Digest()));
        }
    }

    public static class SHA384 extends JCEMac {
        public SHA384() {
            super(new HMac(new SHA384Digest()));
        }
    }

    public static class SHA512 extends JCEMac {
        public SHA512() {
            super(new HMac(new SHA512Digest()));
        }
    }

    public static class Tiger extends JCEMac {
        public Tiger() {
            super(new HMac(new TigerDigest()));
        }
    }

    public JCEMac(Mac mac) {
        this.pbeType = 2;
        this.pbeHash = 1;
        this.keySize = 160;
        this.macEngine = mac;
    }

    public JCEMac(Mac mac, int i2, int i3, int i4) {
        this.macEngine = mac;
        this.pbeType = i2;
        this.pbeHash = i3;
        this.keySize = i4;
    }

    @Override // javax.crypto.MacSpi
    public byte[] engineDoFinal() throws IllegalStateException, DataLengthException {
        byte[] bArr = new byte[engineGetMacLength()];
        this.macEngine.doFinal(bArr, 0);
        return bArr;
    }

    @Override // javax.crypto.MacSpi
    public int engineGetMacLength() {
        return this.macEngine.getMacSize();
    }

    @Override // javax.crypto.MacSpi
    public void engineInit(Key key, AlgorithmParameterSpec algorithmParameterSpec) throws InvalidKeyException, IllegalArgumentException, InvalidAlgorithmParameterException {
        CipherParameters keyParameter;
        if (key == null) {
            throw new InvalidKeyException("key is null");
        }
        if (key instanceof JCEPBEKey) {
            JCEPBEKey jCEPBEKey = (JCEPBEKey) key;
            if (jCEPBEKey.getParam() != null) {
                keyParameter = jCEPBEKey.getParam();
            } else {
                if (!(algorithmParameterSpec instanceof PBEParameterSpec)) {
                    throw new InvalidAlgorithmParameterException("PBE requires PBE parameters to be set.");
                }
                keyParameter = PBE.Util.makePBEMacParameters(jCEPBEKey, algorithmParameterSpec);
            }
        } else if (algorithmParameterSpec instanceof IvParameterSpec) {
            keyParameter = new ParametersWithIV(new KeyParameter(key.getEncoded()), ((IvParameterSpec) algorithmParameterSpec).getIV());
        } else {
            if (algorithmParameterSpec != null) {
                throw new InvalidAlgorithmParameterException("unknown parameter type.");
            }
            keyParameter = new KeyParameter(key.getEncoded());
        }
        this.macEngine.init(keyParameter);
    }

    @Override // javax.crypto.MacSpi
    public void engineReset() {
        this.macEngine.reset();
    }

    @Override // javax.crypto.MacSpi
    public void engineUpdate(byte b3) throws IllegalStateException {
        this.macEngine.update(b3);
    }

    @Override // javax.crypto.MacSpi
    public void engineUpdate(byte[] bArr, int i2, int i3) throws IllegalStateException, DataLengthException {
        this.macEngine.update(bArr, i2, i3);
    }
}
