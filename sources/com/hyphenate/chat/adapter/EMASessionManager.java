package com.hyphenate.chat.adapter;

/* loaded from: classes4.dex */
public class EMASessionManager extends EMABase {
    public String decrypt(String str) {
        return nativeDecrypt(str);
    }

    public String encrypt(String str) {
        return nativeEncrypt(str);
    }

    public byte[] getEncryptionKey(String str, String str2) {
        return nativeGetEncryptionKey(str, str2);
    }

    public native String nativeDecrypt(String str);

    public native String nativeEncrypt(String str);

    public native byte[] nativeGetEncryptionKey(String str, String str2);
}
