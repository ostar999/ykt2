package com.tencent.tbs.logger.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

/* loaded from: classes6.dex */
public class a {

    /* renamed from: a, reason: collision with root package name */
    public static String f21662a;

    public static void a(File file) throws IOException {
        File[] fileArrListFiles;
        if (file == null) {
            return;
        }
        if (!file.exists()) {
            throw new IllegalArgumentException(file + " does not exist");
        }
        if (!file.isDirectory()) {
            throw new IllegalArgumentException(file + " is not a directory");
        }
        IOException e2 = null;
        try {
            fileArrListFiles = file.listFiles();
        } catch (Error e3) {
            e3.printStackTrace();
            fileArrListFiles = null;
        }
        if (fileArrListFiles == null) {
            throw new IOException("Failed to list contents of " + file);
        }
        for (File file2 : fileArrListFiles) {
            try {
                b(file2);
            } catch (IOException e4) {
                e2 = e4;
            }
        }
        if (e2 != null) {
            throw e2;
        }
    }

    public static byte[] a(String str, String str2) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException {
        byte[] bArrUpdate;
        try {
            try {
                byte[] bytes = str2.getBytes("UTF-8");
                Cipher cipher = Cipher.getInstance("RC4");
                cipher.init(1, new SecretKeySpec(str.getBytes("UTF-8"), "RC4"));
                bArrUpdate = cipher.update(bytes);
            } catch (Exception e2) {
                e2.printStackTrace();
                bArrUpdate = null;
            }
            if (bArrUpdate == null) {
                return null;
            }
            String str3 = String.format(Locale.US, "%03d", Integer.valueOf(bArrUpdate.length));
            byte[] bArr = new byte[bArrUpdate.length + 3];
            bArr[0] = (byte) str3.charAt(0);
            bArr[1] = (byte) str3.charAt(1);
            bArr[2] = (byte) str3.charAt(2);
            System.arraycopy(bArrUpdate, 0, bArr, 3, bArrUpdate.length);
            return bArr;
        } catch (Exception e3) {
            e3.printStackTrace();
            return null;
        }
    }

    public static void b(File file) throws IOException {
        if (file == null) {
            return;
        }
        if (file.isDirectory() && file.getCanonicalPath().equals(file.getAbsolutePath())) {
            a(file);
        }
        boolean zExists = file.exists();
        if (file.delete()) {
            return;
        }
        if (zExists) {
            throw new IOException("Unable to delete file: " + file);
        }
        throw new FileNotFoundException("File does not exist: " + file);
    }

    public static synchronized byte[] a(String str, String str2, byte[] bArr) {
        byte[] bArrUpdate;
        try {
            byte[] bytes = str2.getBytes("UTF-8");
            Cipher cipher = Cipher.getInstance("RC4");
            cipher.init(1, new SecretKeySpec(str.getBytes("UTF-8"), "RC4"));
            bArrUpdate = cipher.update(bytes);
        } catch (Exception e2) {
            e2.printStackTrace();
            bArrUpdate = null;
        }
        if (bArrUpdate == null) {
            return str2.getBytes();
        }
        byte[] bArr2 = new byte[bArr.length + bArrUpdate.length + 2];
        System.arraycopy(bArr, 0, bArr2, 0, bArr.length);
        int length = bArr.length + 0;
        System.arraycopy(bArrUpdate, 0, bArr2, length, bArrUpdate.length);
        System.arraycopy(new byte[]{10, 10}, 0, bArr2, length + bArrUpdate.length, 2);
        return bArr2;
    }
}
