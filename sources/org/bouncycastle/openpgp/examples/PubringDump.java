package org.bouncycastle.openpgp.examples;

import java.io.FileInputStream;
import java.security.Security;
import java.util.Iterator;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openpgp.PGPPublicKey;
import org.bouncycastle.openpgp.PGPPublicKeyRing;
import org.bouncycastle.openpgp.PGPPublicKeyRingCollection;
import org.bouncycastle.openpgp.PGPUtil;
import org.bouncycastle.util.encoders.Hex;

/* loaded from: classes9.dex */
public class PubringDump {
    public static String getAlgorithm(int i2) {
        if (i2 == 1) {
            return "RSA_GENERAL";
        }
        if (i2 == 2) {
            return "RSA_ENCRYPT";
        }
        if (i2 == 3) {
            return "RSA_SIGN";
        }
        switch (i2) {
            case 16:
                return "ELGAMAL_ENCRYPT";
            case 17:
                return "DSA";
            case 18:
                return "EC";
            case 19:
                return "ECDSA";
            case 20:
                return "ELGAMAL_GENERAL";
            case 21:
                return "DIFFIE_HELLMAN";
            default:
                return "unknown";
        }
    }

    public static void main(String[] strArr) throws Exception {
        Security.addProvider(new BouncyCastleProvider());
        PGPUtil.setDefaultProvider("BC");
        Iterator keyRings = new PGPPublicKeyRingCollection(PGPUtil.getDecoderStream(new FileInputStream(strArr[0]))).getKeyRings();
        while (keyRings.hasNext()) {
            PGPPublicKeyRing pGPPublicKeyRing = (PGPPublicKeyRing) keyRings.next();
            try {
                pGPPublicKeyRing.getPublicKey();
                Iterator publicKeys = pGPPublicKeyRing.getPublicKeys();
                boolean z2 = true;
                while (publicKeys.hasNext()) {
                    PGPPublicKey pGPPublicKey = (PGPPublicKey) publicKeys.next();
                    if (z2) {
                        System.out.println("Key ID: " + Long.toHexString(pGPPublicKey.getKeyID()));
                        z2 = false;
                    } else {
                        System.out.println("Key ID: " + Long.toHexString(pGPPublicKey.getKeyID()) + " (subkey)");
                    }
                    System.out.println("            Algorithm: " + getAlgorithm(pGPPublicKey.getAlgorithm()));
                    System.out.println("            Fingerprint: " + new String(Hex.encode(pGPPublicKey.getFingerprint())));
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }
}
