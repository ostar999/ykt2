package org.bouncycastle.openpgp.examples;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchProviderException;
import java.util.Iterator;
import org.bouncycastle.openpgp.PGPCompressedDataGenerator;
import org.bouncycastle.openpgp.PGPException;
import org.bouncycastle.openpgp.PGPPrivateKey;
import org.bouncycastle.openpgp.PGPPublicKey;
import org.bouncycastle.openpgp.PGPPublicKeyRing;
import org.bouncycastle.openpgp.PGPPublicKeyRingCollection;
import org.bouncycastle.openpgp.PGPSecretKey;
import org.bouncycastle.openpgp.PGPSecretKeyRing;
import org.bouncycastle.openpgp.PGPSecretKeyRingCollection;
import org.bouncycastle.openpgp.PGPUtil;

/* loaded from: classes9.dex */
class PGPExampleUtil {
    public static byte[] compressFile(String str, int i2) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PGPCompressedDataGenerator pGPCompressedDataGenerator = new PGPCompressedDataGenerator(i2);
        PGPUtil.writeFileToLiteralData(pGPCompressedDataGenerator.open(byteArrayOutputStream), 'b', new File(str));
        pGPCompressedDataGenerator.close();
        return byteArrayOutputStream.toByteArray();
    }

    public static PGPPrivateKey findSecretKey(PGPSecretKeyRingCollection pGPSecretKeyRingCollection, long j2, char[] cArr) throws PGPException, NoSuchProviderException {
        PGPSecretKey secretKey = pGPSecretKeyRingCollection.getSecretKey(j2);
        if (secretKey == null) {
            return null;
        }
        return secretKey.extractPrivateKey(cArr, "BC");
    }

    public static PGPPublicKey readPublicKey(InputStream inputStream) throws IOException, PGPException {
        Iterator keyRings = new PGPPublicKeyRingCollection(PGPUtil.getDecoderStream(inputStream)).getKeyRings();
        while (keyRings.hasNext()) {
            Iterator publicKeys = ((PGPPublicKeyRing) keyRings.next()).getPublicKeys();
            while (publicKeys.hasNext()) {
                PGPPublicKey pGPPublicKey = (PGPPublicKey) publicKeys.next();
                if (pGPPublicKey.isEncryptionKey()) {
                    return pGPPublicKey;
                }
            }
        }
        throw new IllegalArgumentException("Can't find encryption key in key ring.");
    }

    public static PGPPublicKey readPublicKey(String str) throws IOException, PGPException {
        BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(str));
        PGPPublicKey publicKey = readPublicKey(bufferedInputStream);
        bufferedInputStream.close();
        return publicKey;
    }

    public static PGPSecretKey readSecretKey(InputStream inputStream) throws IOException, PGPException {
        Iterator keyRings = new PGPSecretKeyRingCollection(PGPUtil.getDecoderStream(inputStream)).getKeyRings();
        while (keyRings.hasNext()) {
            Iterator secretKeys = ((PGPSecretKeyRing) keyRings.next()).getSecretKeys();
            while (secretKeys.hasNext()) {
                PGPSecretKey pGPSecretKey = (PGPSecretKey) secretKeys.next();
                if (pGPSecretKey.isSigningKey()) {
                    return pGPSecretKey;
                }
            }
        }
        throw new IllegalArgumentException("Can't find signing key in key ring.");
    }

    public static PGPSecretKey readSecretKey(String str) throws IOException, PGPException {
        BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(str));
        PGPSecretKey secretKey = readSecretKey(bufferedInputStream);
        bufferedInputStream.close();
        return secretKey;
    }
}
