package org.bouncycastle.openpgp.examples;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.security.Security;
import java.util.Iterator;
import org.bouncycastle.bcpg.ArmoredOutputStream;
import org.bouncycastle.bcpg.BCPGOutputStream;
import org.bouncycastle.bcpg.sig.NotationData;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openpgp.PGPPrivateKey;
import org.bouncycastle.openpgp.PGPPublicKey;
import org.bouncycastle.openpgp.PGPPublicKeyRing;
import org.bouncycastle.openpgp.PGPSecretKey;
import org.bouncycastle.openpgp.PGPSecretKeyRing;
import org.bouncycastle.openpgp.PGPSignature;
import org.bouncycastle.openpgp.PGPSignatureGenerator;
import org.bouncycastle.openpgp.PGPSignatureSubpacketGenerator;
import org.bouncycastle.openpgp.PGPUtil;

/* loaded from: classes9.dex */
public class DirectKeySignature {
    public static void main(String[] strArr) throws Exception {
        Security.addProvider(new BouncyCastleProvider());
        if (strArr.length != 1) {
            if (strArr.length != 5) {
                System.err.println("usage: DirectKeySignature secretKeyFile secretKeyPass publicKeyFile(key to be signed) NotationName NotationValue");
                System.err.println("or: DirectKeySignature signedPublicKeyFile");
                return;
            }
            PGPSecretKeyRing pGPSecretKeyRing = new PGPSecretKeyRing(PGPUtil.getDecoderStream(new FileInputStream(strArr[0])));
            PGPPublicKeyRing pGPPublicKeyRing = new PGPPublicKeyRing(new ByteArrayInputStream(signPublicKey(pGPSecretKeyRing.getSecretKey(), strArr[1], new PGPPublicKeyRing(PGPUtil.getDecoderStream(new FileInputStream(strArr[2]))).getPublicKey(), strArr[3], strArr[4], true)));
            ArmoredOutputStream armoredOutputStream = new ArmoredOutputStream(new FileOutputStream("SignedKey.asc"));
            pGPPublicKeyRing.encode(armoredOutputStream);
            armoredOutputStream.flush();
            armoredOutputStream.close();
            return;
        }
        Iterator signaturesOfType = new PGPPublicKeyRing(PGPUtil.getDecoderStream(new FileInputStream(strArr[0]))).getPublicKey().getSignaturesOfType(31);
        while (signaturesOfType.hasNext()) {
            PGPSignature pGPSignature = (PGPSignature) signaturesOfType.next();
            System.out.println("Signature date is: " + pGPSignature.getHashedSubPackets().getSignatureCreationTime());
            NotationData[] notationDataOccurences = pGPSignature.getHashedSubPackets().getNotationDataOccurences();
            for (int i2 = 0; i2 < notationDataOccurences.length; i2++) {
                System.out.println("Found Notaion named '" + notationDataOccurences[i2].getNotationName() + "' with content '" + notationDataOccurences[i2].getNotationValue() + "'.");
            }
        }
    }

    private static byte[] signPublicKey(PGPSecretKey pGPSecretKey, String str, PGPPublicKey pGPPublicKey, String str2, String str3, boolean z2) throws Exception {
        OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        if (z2) {
            byteArrayOutputStream = new ArmoredOutputStream(byteArrayOutputStream);
        }
        PGPPrivateKey pGPPrivateKeyExtractPrivateKey = pGPSecretKey.extractPrivateKey(str.toCharArray(), "BC");
        PGPSignatureGenerator pGPSignatureGenerator = new PGPSignatureGenerator(pGPSecretKey.getPublicKey().getAlgorithm(), 2, "BC");
        pGPSignatureGenerator.initSign(31, pGPPrivateKeyExtractPrivateKey);
        BCPGOutputStream bCPGOutputStream = new BCPGOutputStream(byteArrayOutputStream);
        pGPSignatureGenerator.generateOnePassVersion(false).encode(bCPGOutputStream);
        PGPSignatureSubpacketGenerator pGPSignatureSubpacketGenerator = new PGPSignatureSubpacketGenerator();
        pGPSignatureSubpacketGenerator.setNotationData(true, true, str2, str3);
        pGPSignatureGenerator.setHashedSubpackets(pGPSignatureSubpacketGenerator.generate());
        bCPGOutputStream.flush();
        if (z2) {
            byteArrayOutputStream.close();
        }
        return PGPPublicKey.addCertification(pGPPublicKey, pGPSignatureGenerator.generate()).getEncoded();
    }
}
