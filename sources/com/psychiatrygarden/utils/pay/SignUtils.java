package com.psychiatrygarden.utils.pay;

import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;

/* loaded from: classes6.dex */
public class SignUtils {
    private static final String ALGORITHM = "RSA";
    private static final String DEFAULT_CHARSET = "UTF-8";
    private static final String SIGN_ALGORITHMS = "SHA1WithRSA";

    public static String sign(String content, String privateKey) throws InvalidKeySpecException, NoSuchAlgorithmException, SignatureException, InvalidKeyException {
        try {
            PrivateKey privateKeyGeneratePrivate = KeyFactory.getInstance(ALGORITHM).generatePrivate(new PKCS8EncodedKeySpec(Base64.decode(privateKey)));
            Signature signature = Signature.getInstance(SIGN_ALGORITHMS);
            signature.initSign(privateKeyGeneratePrivate);
            signature.update(content.getBytes("UTF-8"));
            return Base64.encode(signature.sign());
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }
}
