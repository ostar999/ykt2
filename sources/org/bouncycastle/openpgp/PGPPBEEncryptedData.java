package org.bouncycastle.openpgp;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
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
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.bouncycastle.bcpg.BCPGInputStream;
import org.bouncycastle.bcpg.InputStreamPacket;
import org.bouncycastle.bcpg.SymmetricEncIntegrityPacket;
import org.bouncycastle.bcpg.SymmetricKeyEncSessionPacket;
import org.bouncycastle.openpgp.PGPEncryptedData;

/* loaded from: classes9.dex */
public class PGPPBEEncryptedData extends PGPEncryptedData {
    SymmetricKeyEncSessionPacket keyData;

    public PGPPBEEncryptedData(SymmetricKeyEncSessionPacket symmetricKeyEncSessionPacket, InputStreamPacket inputStreamPacket) {
        super(inputStreamPacket);
        this.keyData = symmetricKeyEncSessionPacket;
    }

    private Cipher createStreamCipher(int i2, Provider provider) throws NoSuchPaddingException, NoSuchAlgorithmException, NoSuchProviderException, PGPException {
        return Cipher.getInstance(PGPUtil.getSymmetricCipherName(i2) + "/" + (this.encData instanceof SymmetricEncIntegrityPacket ? "CFB" : "OpenPGPCFB") + "/NoPadding", provider);
    }

    public InputStream getDataStream(char[] cArr, String str) throws PGPException, NoSuchProviderException {
        return getDataStream(cArr, PGPUtil.getProvider(str));
    }

    public InputStream getDataStream(char[] cArr, Provider provider) throws BadPaddingException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeyException, IOException, PGPException, InvalidAlgorithmParameterException {
        try {
            int encAlgorithm = this.keyData.getEncAlgorithm();
            SecretKey secretKeyMakeKeyFromPassPhrase = PGPUtil.makeKeyFromPassPhrase(encAlgorithm, this.keyData.getS2K(), cArr, provider);
            byte[] secKeyData = this.keyData.getSecKeyData();
            boolean z2 = false;
            if (secKeyData != null && secKeyData.length > 0) {
                Cipher cipher = Cipher.getInstance(PGPUtil.getSymmetricCipherName(encAlgorithm) + "/CFB/NoPadding", provider);
                cipher.init(2, secretKeyMakeKeyFromPassPhrase, new IvParameterSpec(new byte[cipher.getBlockSize()]));
                byte[] bArrDoFinal = cipher.doFinal(secKeyData);
                encAlgorithm = bArrDoFinal[0];
                secretKeyMakeKeyFromPassPhrase = new SecretKeySpec(bArrDoFinal, 1, bArrDoFinal.length - 1, PGPUtil.getSymmetricCipherName(encAlgorithm));
            }
            Cipher cipherCreateStreamCipher = createStreamCipher(encAlgorithm, provider);
            int blockSize = cipherCreateStreamCipher.getBlockSize();
            byte[] bArr = new byte[blockSize];
            cipherCreateStreamCipher.init(2, secretKeyMakeKeyFromPassPhrase, new IvParameterSpec(bArr));
            this.encStream = new BCPGInputStream(new CipherInputStream(this.encData.getInputStream(), cipherCreateStreamCipher));
            if (this.encData instanceof SymmetricEncIntegrityPacket) {
                this.truncStream = new PGPEncryptedData.TruncatedStream(this.encStream);
                this.encStream = new DigestInputStream(this.truncStream, MessageDigest.getInstance(PGPUtil.getDigestName(2), provider));
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
            boolean z3 = bArr[blockSize + (-2)] == ((byte) i4) && bArr[blockSize - 1] == ((byte) i5);
            if (i4 == 0 && i5 == 0) {
                z2 = true;
            }
            if (!z3 && !z2) {
                throw new PGPDataValidationException("data check failed.");
            }
            return this.encStream;
        } catch (PGPException e2) {
            throw e2;
        } catch (Exception e3) {
            throw new PGPException("Exception creating cipher", e3);
        }
    }

    @Override // org.bouncycastle.openpgp.PGPEncryptedData
    public InputStream getInputStream() {
        return this.encData.getInputStream();
    }
}
