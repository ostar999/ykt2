package com.hyphenate.chat;

import android.util.Base64;
import com.hyphenate.util.EMLog;
import com.hyphenate.util.EasyUtils;
import java.io.UnsupportedEncodingException;

/* loaded from: classes4.dex */
public class EMEncryptUtils {
    private static final String TAG = "encrypt";

    /* JADX WARN: Removed duplicated region for block: B:46:0x009b A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:58:0x00a0 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:68:? A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void decryptFile(java.lang.String r6, java.lang.String r7) throws java.lang.Throwable {
        /*
            java.lang.String r0 = "encrypt"
            r1 = 0
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L87 java.lang.Exception -> L8a
            r2.<init>()     // Catch: java.lang.Throwable -> L87 java.lang.Exception -> L8a
            java.lang.String r3 = "decrypt file:"
            r2.append(r3)     // Catch: java.lang.Throwable -> L87 java.lang.Exception -> L8a
            r2.append(r6)     // Catch: java.lang.Throwable -> L87 java.lang.Exception -> L8a
            java.lang.String r2 = r2.toString()     // Catch: java.lang.Throwable -> L87 java.lang.Exception -> L8a
            com.hyphenate.util.EMLog.d(r0, r2)     // Catch: java.lang.Throwable -> L87 java.lang.Exception -> L8a
            java.io.RandomAccessFile r2 = new java.io.RandomAccessFile     // Catch: java.lang.Throwable -> L87 java.lang.Exception -> L8a
            java.lang.String r3 = "r"
            r2.<init>(r6, r3)     // Catch: java.lang.Throwable -> L87 java.lang.Exception -> L8a
            long r3 = r2.length()     // Catch: java.lang.Throwable -> L7f java.lang.Exception -> L83
            int r3 = (int) r3     // Catch: java.lang.Throwable -> L7f java.lang.Exception -> L83
            byte[] r4 = new byte[r3]     // Catch: java.lang.Throwable -> L7f java.lang.Exception -> L83
            int r5 = r2.read(r4)     // Catch: java.lang.Throwable -> L7f java.lang.Exception -> L83
            if (r5 == r3) goto L4b
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L7f java.lang.Exception -> L83
            r6.<init>()     // Catch: java.lang.Throwable -> L7f java.lang.Exception -> L83
            java.lang.String r7 = "error read file, file len:"
            r6.append(r7)     // Catch: java.lang.Throwable -> L7f java.lang.Exception -> L83
            r6.append(r3)     // Catch: java.lang.Throwable -> L7f java.lang.Exception -> L83
            java.lang.String r7 = " readLen:"
            r6.append(r7)     // Catch: java.lang.Throwable -> L7f java.lang.Exception -> L83
            r6.append(r5)     // Catch: java.lang.Throwable -> L7f java.lang.Exception -> L83
            java.lang.String r6 = r6.toString()     // Catch: java.lang.Throwable -> L7f java.lang.Exception -> L83
            com.hyphenate.util.EMLog.e(r0, r6)     // Catch: java.lang.Throwable -> L7f java.lang.Exception -> L83
            r2.close()     // Catch: java.lang.Exception -> L4a
        L4a:
            return
        L4b:
            com.hyphenate.chat.EMClient r3 = com.hyphenate.chat.EMClient.getInstance()     // Catch: java.lang.Throwable -> L7f java.lang.Exception -> L83
            com.hyphenate.chat.EMEncryptProvider r3 = r3.getEncryptProvider()     // Catch: java.lang.Throwable -> L7f java.lang.Exception -> L83
            byte[] r7 = r3.decrypt(r4, r7)     // Catch: java.lang.Throwable -> L7f java.lang.Exception -> L83
            java.io.FileOutputStream r3 = new java.io.FileOutputStream     // Catch: java.lang.Throwable -> L7f java.lang.Exception -> L83
            r4 = 0
            r3.<init>(r6, r4)     // Catch: java.lang.Throwable -> L7f java.lang.Exception -> L83
            r3.write(r7)     // Catch: java.lang.Throwable -> L7b java.lang.Exception -> L7d
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L7b java.lang.Exception -> L7d
            r7.<init>()     // Catch: java.lang.Throwable -> L7b java.lang.Exception -> L7d
            java.lang.String r1 = "decrypted file:"
            r7.append(r1)     // Catch: java.lang.Throwable -> L7b java.lang.Exception -> L7d
            r7.append(r6)     // Catch: java.lang.Throwable -> L7b java.lang.Exception -> L7d
            java.lang.String r6 = r7.toString()     // Catch: java.lang.Throwable -> L7b java.lang.Exception -> L7d
            com.hyphenate.util.EMLog.d(r0, r6)     // Catch: java.lang.Throwable -> L7b java.lang.Exception -> L7d
            r2.close()     // Catch: java.lang.Exception -> L77
        L77:
            r3.close()     // Catch: java.lang.Exception -> L97
            goto L97
        L7b:
            r6 = move-exception
            goto L81
        L7d:
            r6 = move-exception
            goto L85
        L7f:
            r6 = move-exception
            r3 = r1
        L81:
            r1 = r2
            goto L99
        L83:
            r6 = move-exception
            r3 = r1
        L85:
            r1 = r2
            goto L8c
        L87:
            r6 = move-exception
            r3 = r1
            goto L99
        L8a:
            r6 = move-exception
            r3 = r1
        L8c:
            r6.printStackTrace()     // Catch: java.lang.Throwable -> L98
            if (r1 == 0) goto L94
            r1.close()     // Catch: java.lang.Exception -> L94
        L94:
            if (r3 == 0) goto L97
            goto L77
        L97:
            return
        L98:
            r6 = move-exception
        L99:
            if (r1 == 0) goto L9e
            r1.close()     // Catch: java.lang.Exception -> L9e
        L9e:
            if (r3 == 0) goto La3
            r3.close()     // Catch: java.lang.Exception -> La3
        La3:
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.hyphenate.chat.EMEncryptUtils.decryptFile(java.lang.String, java.lang.String):void");
    }

    public static String decryptMessage(String str, String str2) {
        try {
            EMLog.d(TAG, "encrypted str:" + str);
            byte[] bArrDecode = Base64.decode(str, 0);
            EMLog.d(TAG, "base64 decode bytes:" + EasyUtils.convertByteArrayToString(bArrDecode));
            byte[] bArrDecrypt = EMClient.getInstance().getEncryptProvider().decrypt(bArrDecode, str2);
            EMLog.d(TAG, "decrypt bytes:" + EasyUtils.convertByteArrayToString(bArrDecrypt));
            String str3 = new String(bArrDecrypt, "UTF-8");
            EMLog.d(TAG, "descripted str:" + str3);
            return str3;
        } catch (Exception e2) {
            e2.printStackTrace();
            return str;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:57:0x00d1 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:61:0x00d6 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:72:? A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String encryptFile(java.lang.String r6, java.lang.String r7) throws java.lang.Throwable {
        /*
            java.lang.String r0 = "try to encrypt file:"
            java.lang.String r1 = "encrypt"
            r2 = 0
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> Lbb java.lang.Exception -> Lbe
            r3.<init>()     // Catch: java.lang.Throwable -> Lbb java.lang.Exception -> Lbe
            r3.append(r0)     // Catch: java.lang.Throwable -> Lbb java.lang.Exception -> Lbe
            r3.append(r6)     // Catch: java.lang.Throwable -> Lbb java.lang.Exception -> Lbe
            java.lang.String r3 = r3.toString()     // Catch: java.lang.Throwable -> Lbb java.lang.Exception -> Lbe
            com.hyphenate.util.EMLog.d(r1, r3)     // Catch: java.lang.Throwable -> Lbb java.lang.Exception -> Lbe
            java.io.RandomAccessFile r3 = new java.io.RandomAccessFile     // Catch: java.lang.Throwable -> Lbb java.lang.Exception -> Lbe
            java.lang.String r4 = "r"
            r3.<init>(r6, r4)     // Catch: java.lang.Throwable -> Lbb java.lang.Exception -> Lbe
            long r4 = r3.length()     // Catch: java.lang.Throwable -> Lb3 java.lang.Exception -> Lb7
            int r4 = (int) r4     // Catch: java.lang.Throwable -> Lb3 java.lang.Exception -> Lb7
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> Lb3 java.lang.Exception -> Lb7
            r5.<init>()     // Catch: java.lang.Throwable -> Lb3 java.lang.Exception -> Lb7
            r5.append(r0)     // Catch: java.lang.Throwable -> Lb3 java.lang.Exception -> Lb7
            r5.append(r6)     // Catch: java.lang.Throwable -> Lb3 java.lang.Exception -> Lb7
            java.lang.String r0 = " original len:"
            r5.append(r0)     // Catch: java.lang.Throwable -> Lb3 java.lang.Exception -> Lb7
            r5.append(r4)     // Catch: java.lang.Throwable -> Lb3 java.lang.Exception -> Lb7
            java.lang.String r0 = r5.toString()     // Catch: java.lang.Throwable -> Lb3 java.lang.Exception -> Lb7
            com.hyphenate.util.EMLog.d(r1, r0)     // Catch: java.lang.Throwable -> Lb3 java.lang.Exception -> Lb7
            byte[] r0 = new byte[r4]     // Catch: java.lang.Throwable -> Lb3 java.lang.Exception -> Lb7
            int r5 = r3.read(r0)     // Catch: java.lang.Throwable -> Lb3 java.lang.Exception -> Lb7
            if (r5 == r4) goto L68
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> Lb3 java.lang.Exception -> Lb7
            r7.<init>()     // Catch: java.lang.Throwable -> Lb3 java.lang.Exception -> Lb7
            java.lang.String r0 = "error read file, file len:"
            r7.append(r0)     // Catch: java.lang.Throwable -> Lb3 java.lang.Exception -> Lb7
            r7.append(r4)     // Catch: java.lang.Throwable -> Lb3 java.lang.Exception -> Lb7
            java.lang.String r0 = " readLen:"
            r7.append(r0)     // Catch: java.lang.Throwable -> Lb3 java.lang.Exception -> Lb7
            r7.append(r5)     // Catch: java.lang.Throwable -> Lb3 java.lang.Exception -> Lb7
            java.lang.String r7 = r7.toString()     // Catch: java.lang.Throwable -> Lb3 java.lang.Exception -> Lb7
            com.hyphenate.util.EMLog.e(r1, r7)     // Catch: java.lang.Throwable -> Lb3 java.lang.Exception -> Lb7
            r3.close()     // Catch: java.lang.Throwable -> Lb3 java.lang.Exception -> Lb7
            r3.close()     // Catch: java.lang.Exception -> L67
        L67:
            return r6
        L68:
            com.hyphenate.chat.EMClient r4 = com.hyphenate.chat.EMClient.getInstance()     // Catch: java.lang.Throwable -> Lb3 java.lang.Exception -> Lb7
            com.hyphenate.chat.EMEncryptProvider r4 = r4.getEncryptProvider()     // Catch: java.lang.Throwable -> Lb3 java.lang.Exception -> Lb7
            byte[] r7 = r4.encrypt(r0, r7)     // Catch: java.lang.Throwable -> Lb3 java.lang.Exception -> Lb7
            r0 = 46
            int r0 = r6.lastIndexOf(r0)     // Catch: java.lang.Throwable -> Lb3 java.lang.Exception -> Lb7
            if (r0 < 0) goto L81
            java.lang.String r0 = r6.substring(r0)     // Catch: java.lang.Throwable -> Lb3 java.lang.Exception -> Lb7
            goto L82
        L81:
            r0 = r2
        L82:
            java.lang.String r4 = "encrypted"
            java.io.File r0 = java.io.File.createTempFile(r4, r0)     // Catch: java.lang.Throwable -> Lb3 java.lang.Exception -> Lb7
            java.io.FileOutputStream r4 = new java.io.FileOutputStream     // Catch: java.lang.Throwable -> Lb3 java.lang.Exception -> Lb7
            r4.<init>(r0)     // Catch: java.lang.Throwable -> Lb3 java.lang.Exception -> Lb7
            r4.write(r7)     // Catch: java.lang.Throwable -> Laf java.lang.Exception -> Lb1
            java.lang.String r7 = r0.getAbsolutePath()     // Catch: java.lang.Throwable -> Laf java.lang.Exception -> Lb1
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> Laf java.lang.Exception -> Lb1
            r0.<init>()     // Catch: java.lang.Throwable -> Laf java.lang.Exception -> Lb1
            java.lang.String r2 = "generated encrypted file:"
            r0.append(r2)     // Catch: java.lang.Throwable -> Laf java.lang.Exception -> Lb1
            r0.append(r7)     // Catch: java.lang.Throwable -> Laf java.lang.Exception -> Lb1
            java.lang.String r0 = r0.toString()     // Catch: java.lang.Throwable -> Laf java.lang.Exception -> Lb1
            com.hyphenate.util.EMLog.d(r1, r0)     // Catch: java.lang.Throwable -> Laf java.lang.Exception -> Lb1
            r3.close()     // Catch: java.lang.Exception -> Lab
        Lab:
            r4.close()     // Catch: java.lang.Exception -> Lae
        Lae:
            return r7
        Laf:
            r6 = move-exception
            goto Lb5
        Lb1:
            r7 = move-exception
            goto Lb9
        Lb3:
            r6 = move-exception
            r4 = r2
        Lb5:
            r2 = r3
            goto Lcf
        Lb7:
            r7 = move-exception
            r4 = r2
        Lb9:
            r2 = r3
            goto Lc0
        Lbb:
            r6 = move-exception
            r4 = r2
            goto Lcf
        Lbe:
            r7 = move-exception
            r4 = r2
        Lc0:
            r7.printStackTrace()     // Catch: java.lang.Throwable -> Lce
            if (r2 == 0) goto Lc8
            r2.close()     // Catch: java.lang.Exception -> Lc8
        Lc8:
            if (r4 == 0) goto Lcd
            r4.close()     // Catch: java.lang.Exception -> Lcd
        Lcd:
            return r6
        Lce:
            r6 = move-exception
        Lcf:
            if (r2 == 0) goto Ld4
            r2.close()     // Catch: java.lang.Exception -> Ld4
        Ld4:
            if (r4 == 0) goto Ld9
            r4.close()     // Catch: java.lang.Exception -> Ld9
        Ld9:
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.hyphenate.chat.EMEncryptUtils.encryptFile(java.lang.String, java.lang.String):java.lang.String");
    }

    public static String encryptMessage(String str, String str2) throws UnsupportedEncodingException {
        try {
            EMEncryptProvider encryptProvider = EMClient.getInstance().getEncryptProvider();
            byte[] bytes = str.getBytes("UTF-8");
            EMLog.d(TAG, "utf-8 bytes:" + EasyUtils.convertByteArrayToString(bytes));
            byte[] bArrEncrypt = encryptProvider.encrypt(bytes, str2);
            EMLog.d(TAG, "encrypted bytes:" + EasyUtils.convertByteArrayToString(bArrEncrypt));
            byte[] bArrEncode = Base64.encode(bArrEncrypt, 0);
            EMLog.d(TAG, "base64 bytes:" + EasyUtils.convertByteArrayToString(bArrEncode));
            String str3 = new String(bArrEncode);
            EMLog.d(TAG, "encrypted str:" + str3);
            return str3;
        } catch (Exception e2) {
            e2.printStackTrace();
            EMLog.e(TAG, "encryption error, send plain msg");
            return str;
        }
    }
}
