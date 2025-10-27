package org.bouncycastle.openpgp.examples;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.security.SignatureException;
import java.util.Iterator;
import org.bouncycastle.bcpg.ArmoredOutputStream;
import org.bouncycastle.bcpg.BCPGOutputStream;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openpgp.PGPCompressedData;
import org.bouncycastle.openpgp.PGPCompressedDataGenerator;
import org.bouncycastle.openpgp.PGPException;
import org.bouncycastle.openpgp.PGPLiteralData;
import org.bouncycastle.openpgp.PGPLiteralDataGenerator;
import org.bouncycastle.openpgp.PGPObjectFactory;
import org.bouncycastle.openpgp.PGPOnePassSignature;
import org.bouncycastle.openpgp.PGPOnePassSignatureList;
import org.bouncycastle.openpgp.PGPPrivateKey;
import org.bouncycastle.openpgp.PGPPublicKey;
import org.bouncycastle.openpgp.PGPPublicKeyRingCollection;
import org.bouncycastle.openpgp.PGPSecretKey;
import org.bouncycastle.openpgp.PGPSignatureGenerator;
import org.bouncycastle.openpgp.PGPSignatureList;
import org.bouncycastle.openpgp.PGPSignatureSubpacketGenerator;
import org.bouncycastle.openpgp.PGPUtil;

/* loaded from: classes9.dex */
public class SignedFileProcessor {
    public static void main(String[] strArr) throws Exception {
        Security.addProvider(new BouncyCastleProvider());
        if (!strArr[0].equals("-s")) {
            if (strArr[0].equals("-v")) {
                verifyFile(new FileInputStream(strArr[1]), new FileInputStream(strArr[2]));
                return;
            } else {
                System.err.println("usage: SignedFileProcessor -v|-s [-a] file keyfile [passPhrase]");
                return;
            }
        }
        if (strArr[1].equals("-a")) {
            signFile(strArr[2], new FileInputStream(strArr[3]), new FileOutputStream(strArr[2] + ".asc"), strArr[4].toCharArray(), true);
            return;
        }
        signFile(strArr[1], new FileInputStream(strArr[2]), new FileOutputStream(strArr[1] + ".bpg"), strArr[3].toCharArray(), false);
    }

    private static void signFile(String str, InputStream inputStream, OutputStream outputStream, char[] cArr, boolean z2) throws SignatureException, NoSuchAlgorithmException, IOException, PGPException, NoSuchProviderException {
        if (z2) {
            outputStream = new ArmoredOutputStream(outputStream);
        }
        PGPSecretKey secretKey = PGPExampleUtil.readSecretKey(inputStream);
        PGPPrivateKey pGPPrivateKeyExtractPrivateKey = secretKey.extractPrivateKey(cArr, "BC");
        PGPSignatureGenerator pGPSignatureGenerator = new PGPSignatureGenerator(secretKey.getPublicKey().getAlgorithm(), 2, "BC");
        pGPSignatureGenerator.initSign(0, pGPPrivateKeyExtractPrivateKey);
        Iterator userIDs = secretKey.getPublicKey().getUserIDs();
        if (userIDs.hasNext()) {
            PGPSignatureSubpacketGenerator pGPSignatureSubpacketGenerator = new PGPSignatureSubpacketGenerator();
            pGPSignatureSubpacketGenerator.setSignerUserID(false, (String) userIDs.next());
            pGPSignatureGenerator.setHashedSubpackets(pGPSignatureSubpacketGenerator.generate());
        }
        PGPCompressedDataGenerator pGPCompressedDataGenerator = new PGPCompressedDataGenerator(2);
        BCPGOutputStream bCPGOutputStream = new BCPGOutputStream(pGPCompressedDataGenerator.open(outputStream));
        pGPSignatureGenerator.generateOnePassVersion(false).encode(bCPGOutputStream);
        File file = new File(str);
        PGPLiteralDataGenerator pGPLiteralDataGenerator = new PGPLiteralDataGenerator();
        OutputStream outputStreamOpen = pGPLiteralDataGenerator.open(bCPGOutputStream, 'b', file);
        FileInputStream fileInputStream = new FileInputStream(file);
        while (true) {
            int i2 = fileInputStream.read();
            if (i2 < 0) {
                break;
            }
            outputStreamOpen.write(i2);
            pGPSignatureGenerator.update((byte) i2);
        }
        pGPLiteralDataGenerator.close();
        pGPSignatureGenerator.generate().encode(bCPGOutputStream);
        pGPCompressedDataGenerator.close();
        if (z2) {
            outputStream.close();
        }
    }

    private static void verifyFile(InputStream inputStream, InputStream inputStream2) throws Exception {
        PrintStream printStream;
        String str;
        PGPObjectFactory pGPObjectFactory = new PGPObjectFactory(((PGPCompressedData) new PGPObjectFactory(PGPUtil.getDecoderStream(inputStream)).nextObject()).getDataStream());
        PGPOnePassSignature pGPOnePassSignature = ((PGPOnePassSignatureList) pGPObjectFactory.nextObject()).get(0);
        PGPLiteralData pGPLiteralData = (PGPLiteralData) pGPObjectFactory.nextObject();
        InputStream inputStream3 = pGPLiteralData.getInputStream();
        PGPPublicKey publicKey = new PGPPublicKeyRingCollection(PGPUtil.getDecoderStream(inputStream2)).getPublicKey(pGPOnePassSignature.getKeyID());
        FileOutputStream fileOutputStream = new FileOutputStream(pGPLiteralData.getFileName());
        pGPOnePassSignature.initVerify(publicKey, "BC");
        while (true) {
            int i2 = inputStream3.read();
            if (i2 < 0) {
                break;
            }
            pGPOnePassSignature.update((byte) i2);
            fileOutputStream.write(i2);
        }
        fileOutputStream.close();
        if (pGPOnePassSignature.verify(((PGPSignatureList) pGPObjectFactory.nextObject()).get(0))) {
            printStream = System.out;
            str = "signature verified.";
        } else {
            printStream = System.out;
            str = "signature verification failed.";
        }
        printStream.println(str);
    }
}
