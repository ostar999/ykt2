package com.hyphenate.chat;

/* loaded from: classes4.dex */
interface EMEncryptProvider {
    byte[] decrypt(byte[] bArr, String str);

    byte[] encrypt(byte[] bArr, String str);
}
