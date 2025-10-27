package org.bouncycastle.openpgp;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.DigestInputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Provider;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.bouncycastle.bcpg.BCPGInputStream;
import org.bouncycastle.bcpg.InputStreamPacket;
import org.bouncycastle.bcpg.PublicKeyEncSessionPacket;
import org.bouncycastle.bcpg.SymmetricEncIntegrityPacket;
import org.bouncycastle.jce.interfaces.ElGamalKey;
import org.bouncycastle.openpgp.PGPEncryptedData;

/* loaded from: classes9.dex */
public class PGPPublicKeyEncryptedData extends PGPEncryptedData {
    PublicKeyEncSessionPacket keyData;

    public PGPPublicKeyEncryptedData(PublicKeyEncSessionPacket publicKeyEncSessionPacket, InputStreamPacket inputStreamPacket) {
        super(inputStreamPacket);
        this.keyData = publicKeyEncSessionPacket;
    }

    private boolean confirmCheckSum(byte[] bArr) {
        int i2 = 0;
        for (int i3 = 1; i3 != bArr.length - 2; i3++) {
            i2 += bArr[i3] & 255;
        }
        return bArr[bArr.length + (-2)] == ((byte) (i2 >> 8)) && bArr[bArr.length - 1] == ((byte) i2);
    }

    private byte[] fetchSymmetricKeyData(PGPPrivateKey pGPPrivateKey, Provider provider) throws BadPaddingException, IllegalBlockSizeException, InvalidKeyException, PGPException {
        Cipher keyCipher = getKeyCipher(this.keyData.getAlgorithm(), provider);
        try {
            keyCipher.init(2, pGPPrivateKey.getKey());
            BigInteger[] encSessionKey = this.keyData.getEncSessionKey();
            if (this.keyData.getAlgorithm() == 2 || this.keyData.getAlgorithm() == 1) {
                byte[] byteArray = encSessionKey[0].toByteArray();
                if (byteArray[0] == 0) {
                    keyCipher.update(byteArray, 1, byteArray.length - 1);
                } else {
                    keyCipher.update(byteArray);
                }
            } else {
                int iBitLength = (((ElGamalKey) pGPPrivateKey.getKey()).getParameters().getP().bitLength() + 7) / 8;
                byte[] bArr = new byte[iBitLength];
                byte[] byteArray2 = encSessionKey[0].toByteArray();
                if (byteArray2.length > iBitLength) {
                    keyCipher.update(byteArray2, 1, byteArray2.length - 1);
                } else {
                    System.arraycopy(byteArray2, 0, bArr, iBitLength - byteArray2.length, byteArray2.length);
                    keyCipher.update(bArr);
                }
                byte[] byteArray3 = encSessionKey[1].toByteArray();
                for (int i2 = 0; i2 != iBitLength; i2++) {
                    bArr[i2] = 0;
                }
                if (byteArray3.length > iBitLength) {
                    keyCipher.update(byteArray3, 1, byteArray3.length - 1);
                } else {
                    System.arraycopy(byteArray3, 0, bArr, iBitLength - byteArray3.length, byteArray3.length);
                    keyCipher.update(bArr);
                }
            }
            try {
                byte[] bArrDoFinal = keyCipher.doFinal();
                if (confirmCheckSum(bArrDoFinal)) {
                    return bArrDoFinal;
                }
                throw new PGPKeyValidationException("key checksum failed");
            } catch (Exception e2) {
                throw new PGPException("exception decrypting secret key", e2);
            }
        } catch (InvalidKeyException e3) {
            throw new PGPException("error setting asymmetric cipher", e3);
        }
    }

    private static Cipher getKeyCipher(int i2, Provider provider) throws PGPException {
        try {
            if (i2 == 1 || i2 == 2) {
                return Cipher.getInstance("RSA/ECB/PKCS1Padding", provider);
            }
            if (i2 == 16 || i2 == 20) {
                return Cipher.getInstance("ElGamal/ECB/PKCS1Padding", provider);
            }
            throw new PGPException("unknown asymmetric algorithm: " + i2);
        } catch (PGPException e2) {
            throw e2;
        } catch (Exception e3) {
            throw new PGPException("Exception creating cipher", e3);
        }
    }

    public InputStream getDataStream(PGPPrivateKey pGPPrivateKey, String str) throws PGPException, NoSuchProviderException {
        return getDataStream(pGPPrivateKey, str, str);
    }

    public InputStream getDataStream(PGPPrivateKey pGPPrivateKey, String str, String str2) throws PGPException, NoSuchProviderException {
        return getDataStream(pGPPrivateKey, PGPUtil.getProvider(str), PGPUtil.getProvider(str2));
    }

    public InputStream getDataStream(PGPPrivateKey pGPPrivateKey, Provider provider) throws PGPException {
        return getDataStream(pGPPrivateKey, provider, provider);
    }

    public InputStream getDataStream(PGPPrivateKey pGPPrivateKey, Provider provider, Provider provider2) throws BadPaddingException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeyException, IOException, PGPException, InvalidAlgorithmParameterException {
        byte[] bArrFetchSymmetricKeyData = fetchSymmetricKeyData(pGPPrivateKey, provider);
        try {
            Cipher cipher = Cipher.getInstance(this.encData instanceof SymmetricEncIntegrityPacket ? PGPUtil.getSymmetricCipherName(bArrFetchSymmetricKeyData[0]) + "/CFB/NoPadding" : PGPUtil.getSymmetricCipherName(bArrFetchSymmetricKeyData[0]) + "/OpenPGPCFB/NoPadding", provider2);
            if (cipher == null) {
                return this.encData.getInputStream();
            }
            try {
                SecretKeySpec secretKeySpec = new SecretKeySpec(bArrFetchSymmetricKeyData, 1, bArrFetchSymmetricKeyData.length - 3, PGPUtil.getSymmetricCipherName(bArrFetchSymmetricKeyData[0]));
                int blockSize = cipher.getBlockSize();
                byte[] bArr = new byte[blockSize];
                cipher.init(2, secretKeySpec, new IvParameterSpec(bArr));
                this.encStream = new BCPGInputStream(new CipherInputStream(this.encData.getInputStream(), cipher));
                if (this.encData instanceof SymmetricEncIntegrityPacket) {
                    this.truncStream = new PGPEncryptedData.TruncatedStream(this.encStream);
                    this.encStream = new DigestInputStream(this.truncStream, MessageDigest.getInstance(PGPUtil.getDigestName(2), provider2));
                }
                for (int i2 = 0; i2 != blockSize; i2++) {
                    int i3 = this.encStream.read();
                    if (i3 < 0) {
                        throw new EOFException("unexpected end of stream.");
                    }
                    bArr[i2] = (byte) i3;
                }
                int i4 = this.encStream.read();
                int i5 = this.encStream.read();
                if (i4 < 0 || i5 < 0) {
                    throw new EOFException("unexpected end of stream.");
                }
                return this.encStream;
            } catch (PGPException e2) {
                throw e2;
            } catch (Exception e3) {
                throw new PGPException("Exception starting decryption", e3);
            }
        } catch (PGPException e4) {
            throw e4;
        } catch (Exception e5) {
            throw new PGPException("exception creating cipher", e5);
        }
    }

    public long getKeyID() {
        return this.keyData.getKeyID();
    }

    public int getSymmetricAlgorithm(PGPPrivateKey pGPPrivateKey, String str) throws PGPException, NoSuchProviderException {
        return getSymmetricAlgorithm(pGPPrivateKey, PGPUtil.getProvider(str));
    }

    public int getSymmetricAlgorithm(PGPPrivateKey pGPPrivateKey, Provider provider) throws PGPException, NoSuchProviderException {
        return fetchSymmetricKeyData(pGPPrivateKey, provider)[0];
    }
}
