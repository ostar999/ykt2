package com.plv.foundationsdk.sign;

import android.text.TextUtils;
import com.plv.thirdpart.blankj.utilcode.util.EncryptUtils;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

/* loaded from: classes4.dex */
public class PLVSignCreator {
    public static final String ENCRYPT_RESPONSE_TYPE = "encryptResponseType";
    private static final String ENCRYPT_RESPONSE_TYPE_AES = "1";
    public static final String SIGNATURE_NONCE = "signatureNonce";
    public static String SIGN_TYPE_MD5 = "MD5";
    public static String SIGN_TYPE_SHA256 = "SHA256";
    private static String SignatureMethod = "MD5";
    private static boolean encryptResponseEnabled = false;
    private static boolean signatureNonceEnabled = false;

    private static String concatParams(Map<String, String> map) {
        String[] strArr = (String[]) map.keySet().toArray(new String[0]);
        Arrays.sort(strArr);
        StringBuilder sb = new StringBuilder();
        for (String str : strArr) {
            if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(map.get(str))) {
                sb.append(str);
                sb.append(map.get(str));
            }
        }
        return sb.toString();
    }

    public static String createSign(String str, Map<String, String> map) {
        return SIGN_TYPE_MD5.equals(SignatureMethod) ? createSignByMD5(str, map) : SIGN_TYPE_SHA256.equals(SignatureMethod) ? createSignBySHA256(str, map) : "";
    }

    public static String createSignByMD5(String str, Map<String, String> map) {
        return createSignByMD5(str, map, true);
    }

    private static String createSignBySHA256(String str, Map<String, String> map) {
        map.put("signatureMethod", SIGN_TYPE_SHA256);
        return EncryptUtils.encryptSHA256ToString(str + concatParams(map) + str).toUpperCase();
    }

    public static String[] createSignWithEncrypt(String str, Map<String, String> map) {
        String encryptResponseTypeBySW = getEncryptResponseTypeBySW();
        map.put(ENCRYPT_RESPONSE_TYPE, encryptResponseTypeBySW);
        return new String[]{createSign(str, map), encryptResponseTypeBySW};
    }

    public static String[] createSignWithSignatureNonce(String str, Map<String, String> map) {
        String strGenerateSignatureNonceBySW = generateSignatureNonceBySW();
        map.put(SIGNATURE_NONCE, strGenerateSignatureNonceBySW);
        return new String[]{createSign(str, map), strGenerateSignatureNonceBySW};
    }

    public static String[] createSignWithSignatureNonceEncrypt(String str, Map<String, String> map) {
        String strGenerateSignatureNonceBySW = generateSignatureNonceBySW();
        map.put(SIGNATURE_NONCE, strGenerateSignatureNonceBySW);
        String encryptResponseTypeBySW = getEncryptResponseTypeBySW();
        map.put(ENCRYPT_RESPONSE_TYPE, encryptResponseTypeBySW);
        return new String[]{createSign(str, map), strGenerateSignatureNonceBySW, encryptResponseTypeBySW};
    }

    public static String generateSignatureNonceBySW() {
        if (signatureNonceEnabled) {
            return generateUUID();
        }
        return null;
    }

    private static String generateUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static String getEncryptResponseTypeBySW() {
        if (encryptResponseEnabled) {
            return "1";
        }
        return null;
    }

    public static int getSecurity() {
        return (SIGN_TYPE_SHA256.equals(getSignatureMethod()) || isSignatureNonceEnabled() || isEncryptResponseEnabled()) ? 1 : 0;
    }

    public static String getSignatureMethod() {
        return SignatureMethod;
    }

    public static boolean isEncryptResponseEnabled() {
        return encryptResponseEnabled;
    }

    public static boolean isSignatureNonceEnabled() {
        return signatureNonceEnabled;
    }

    public static void setEncryptResponseEnabled(boolean z2) {
        encryptResponseEnabled = z2;
    }

    public static void setSignatureMethod(String str) {
        SignatureMethod = str;
    }

    public static void setSignatureNonceEnabled(boolean z2) {
        signatureNonceEnabled = z2;
    }

    public static String createSignByMD5(String str, Map<String, String> map, boolean z2) {
        if (z2) {
            map.put("signatureMethod", SIGN_TYPE_MD5);
        }
        return EncryptUtils.encryptMD5ToString(str + concatParams(map) + str).toUpperCase();
    }
}
