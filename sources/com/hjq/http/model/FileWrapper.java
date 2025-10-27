package com.hjq.http.model;

import androidx.annotation.NonNull;
import com.hjq.http.EasyLog;
import com.hjq.http.EasyUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/* loaded from: classes4.dex */
public class FileWrapper extends File {
    public FileWrapper(@NonNull File file) {
        super(file.getPath());
    }

    public static boolean createFolder(File file) {
        if (file.exists()) {
            if (file.isDirectory()) {
                return true;
            }
            file.delete();
        }
        return file.mkdirs();
    }

    public static String getFileMd5(InputStream inputStream) throws Throwable {
        Throwable th;
        DigestInputStream digestInputStream;
        if (inputStream == null) {
            return "";
        }
        try {
            try {
                digestInputStream = new DigestInputStream(inputStream, MessageDigest.getInstance("MD5"));
            } catch (IOException e2) {
                e = e2;
                digestInputStream = null;
                EasyLog.print(e);
                EasyUtils.closeStream(inputStream);
                EasyUtils.closeStream(digestInputStream);
                return null;
            } catch (NoSuchAlgorithmException e3) {
                e = e3;
                digestInputStream = null;
                EasyLog.print(e);
                EasyUtils.closeStream(inputStream);
                EasyUtils.closeStream(digestInputStream);
                return null;
            } catch (Throwable th2) {
                th = th2;
                EasyUtils.closeStream(inputStream);
                EasyUtils.closeStream(null);
                throw th;
            }
            try {
                while (digestInputStream.read(new byte[262144]) > 0) {
                }
                byte[] bArrDigest = digestInputStream.getMessageDigest().digest();
                StringBuilder sb = new StringBuilder();
                for (byte b3 : bArrDigest) {
                    sb.append(String.format("%02X", Byte.valueOf(b3)));
                }
                String lowerCase = sb.toString().toLowerCase();
                EasyUtils.closeStream(inputStream);
                EasyUtils.closeStream(digestInputStream);
                return lowerCase;
            } catch (IOException e4) {
                e = e4;
                EasyLog.print(e);
                EasyUtils.closeStream(inputStream);
                EasyUtils.closeStream(digestInputStream);
                return null;
            } catch (NoSuchAlgorithmException e5) {
                e = e5;
                EasyLog.print(e);
                EasyUtils.closeStream(inputStream);
                EasyUtils.closeStream(digestInputStream);
                return null;
            }
        } catch (Throwable th3) {
            th = th3;
            EasyUtils.closeStream(inputStream);
            EasyUtils.closeStream(null);
            throw th;
        }
    }

    public InputStream openInputStream() throws FileNotFoundException {
        return new FileInputStream(this);
    }

    public OutputStream openOutputStream() throws FileNotFoundException {
        return new FileOutputStream(this);
    }
}
