package org.bouncycastle.openpgp.examples;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.security.GeneralSecurityException;
import java.security.Security;
import org.bouncycastle.bcpg.ArmoredOutputStream;
import org.bouncycastle.bcpg.BCPGOutputStream;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openpgp.PGPCompressedData;
import org.bouncycastle.openpgp.PGPException;
import org.bouncycastle.openpgp.PGPObjectFactory;
import org.bouncycastle.openpgp.PGPPrivateKey;
import org.bouncycastle.openpgp.PGPPublicKeyRingCollection;
import org.bouncycastle.openpgp.PGPSecretKey;
import org.bouncycastle.openpgp.PGPSignature;
import org.bouncycastle.openpgp.PGPSignatureGenerator;
import org.bouncycastle.openpgp.PGPSignatureList;
import org.bouncycastle.openpgp.PGPUtil;

/* loaded from: classes9.dex */
public class DetachedSignatureProcessor {
    private static void createSignature(String str, InputStream inputStream, OutputStream outputStream, char[] cArr, boolean z2) throws GeneralSecurityException, IOException, PGPException {
        if (z2) {
            outputStream = new ArmoredOutputStream(outputStream);
        }
        PGPSecretKey secretKey = PGPExampleUtil.readSecretKey(inputStream);
        PGPPrivateKey pGPPrivateKeyExtractPrivateKey = secretKey.extractPrivateKey(cArr, "BC");
        PGPSignatureGenerator pGPSignatureGenerator = new PGPSignatureGenerator(secretKey.getPublicKey().getAlgorithm(), 2, "BC");
        pGPSignatureGenerator.initSign(0, pGPPrivateKeyExtractPrivateKey);
        BCPGOutputStream bCPGOutputStream = new BCPGOutputStream(outputStream);
        BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(str));
        while (true) {
            int i2 = bufferedInputStream.read();
            if (i2 < 0) {
                break;
            } else {
                pGPSignatureGenerator.update((byte) i2);
            }
        }
        bufferedInputStream.close();
        pGPSignatureGenerator.generate().encode(bCPGOutputStream);
        if (z2) {
            outputStream.close();
        }
    }

    private static void createSignature(String str, String str2, String str3, char[] cArr, boolean z2) throws GeneralSecurityException, IOException, PGPException {
        BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(str2));
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(str3));
        createSignature(str, bufferedInputStream, bufferedOutputStream, cArr, z2);
        bufferedOutputStream.close();
        bufferedInputStream.close();
    }

    public static void main(String[] strArr) throws Exception {
        Security.addProvider(new BouncyCastleProvider());
        if (!strArr[0].equals("-s")) {
            if (strArr[0].equals("-v")) {
                verifySignature(strArr[1], strArr[2], strArr[3]);
                return;
            } else {
                System.err.println("usage: DetachedSignatureProcessor [-s [-a] file keyfile passPhrase]|[-v file sigFile keyFile]");
                return;
            }
        }
        if (strArr[1].equals("-a")) {
            createSignature(strArr[2], strArr[3], strArr[2] + ".asc", strArr[4].toCharArray(), true);
            return;
        }
        createSignature(strArr[1], strArr[2], strArr[1] + ".bpg", strArr[3].toCharArray(), false);
    }

    private static void verifySignature(String str, InputStream inputStream, InputStream inputStream2) throws GeneralSecurityException, IOException, PGPException {
        PrintStream printStream;
        String str2;
        Object objNextObject = new PGPObjectFactory(PGPUtil.getDecoderStream(inputStream)).nextObject();
        if (objNextObject instanceof PGPCompressedData) {
            objNextObject = new PGPObjectFactory(((PGPCompressedData) objNextObject).getDataStream()).nextObject();
        }
        PGPPublicKeyRingCollection pGPPublicKeyRingCollection = new PGPPublicKeyRingCollection(PGPUtil.getDecoderStream(inputStream2));
        BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(str));
        PGPSignature pGPSignature = ((PGPSignatureList) objNextObject).get(0);
        pGPSignature.initVerify(pGPPublicKeyRingCollection.getPublicKey(pGPSignature.getKeyID()), "BC");
        while (true) {
            int i2 = bufferedInputStream.read();
            if (i2 < 0) {
                break;
            } else {
                pGPSignature.update((byte) i2);
            }
        }
        bufferedInputStream.close();
        if (pGPSignature.verify()) {
            printStream = System.out;
            str2 = "signature verified.";
        } else {
            printStream = System.out;
            str2 = "signature verification failed.";
        }
        printStream.println(str2);
    }

    private static void verifySignature(String str, String str2, String str3) throws GeneralSecurityException, IOException, PGPException {
        BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(str2));
        BufferedInputStream bufferedInputStream2 = new BufferedInputStream(new FileInputStream(str3));
        verifySignature(str, bufferedInputStream, bufferedInputStream2);
        bufferedInputStream2.close();
        bufferedInputStream.close();
    }
}
