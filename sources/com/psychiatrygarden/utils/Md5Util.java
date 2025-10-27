package com.psychiatrygarden.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/* loaded from: classes6.dex */
public class Md5Util {
    public static String MD5Encode(String sourceString) {
        String str = null;
        try {
            String str2 = new String(sourceString);
            try {
                return byte2hexString(MessageDigest.getInstance("MD5").digest(str2.getBytes("UTF-8")));
            } catch (Exception unused) {
                str = str2;
                return str;
            }
        } catch (Exception unused2) {
        }
    }

    public static final String byte2hexString(byte[] bytes) {
        StringBuffer stringBuffer = new StringBuffer(bytes.length * 2);
        for (int i2 = 0; i2 < bytes.length; i2++) {
            if ((bytes[i2] & 255) < 16) {
                stringBuffer.append("0");
            }
            stringBuffer.append(Long.toString(bytes[i2] & 255, 16));
        }
        return stringBuffer.toString();
    }

    public static final String byte2hexString16(byte[] bytes) {
        StringBuffer stringBuffer = new StringBuffer(bytes.length * 2);
        for (int i2 = 0; i2 < bytes.length; i2++) {
            if ((bytes[i2] & 255) < 16) {
                stringBuffer.append("0");
            }
            stringBuffer.append(Long.toString(bytes[i2] & 255, 16));
        }
        return stringBuffer.toString().substring(8, 24);
    }

    public static String getMD5(File file) throws Throwable {
        FileInputStream fileInputStream;
        MessageDigest messageDigest;
        FileInputStream fileInputStream2 = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            fileInputStream = new FileInputStream(file);
        } catch (Exception e2) {
            e = e2;
            fileInputStream = null;
        } catch (Throwable th) {
            th = th;
            try {
                fileInputStream2.close();
            } catch (IOException e3) {
                e3.printStackTrace();
            }
            throw th;
        }
        try {
            try {
                byte[] bArr = new byte[2048];
                System.currentTimeMillis();
                while (true) {
                    int i2 = fileInputStream.read(bArr);
                    if (i2 == -1) {
                        break;
                    }
                    messageDigest.update(bArr, 0, i2);
                }
                String strByte2hexString = byte2hexString(messageDigest.digest());
                try {
                    fileInputStream.close();
                } catch (IOException e4) {
                    e4.printStackTrace();
                }
                return strByte2hexString;
            } catch (Throwable th2) {
                th = th2;
                fileInputStream2 = fileInputStream;
                fileInputStream2.close();
                throw th;
            }
        } catch (Exception e5) {
            e = e5;
            e.printStackTrace();
            try {
                fileInputStream.close();
            } catch (IOException e6) {
                e6.printStackTrace();
            }
            return null;
        }
    }

    public static String getMD52(String message) {
        try {
            return byte2hexString16(MessageDigest.getInstance("MD5").digest(message.getBytes("utf-8")));
        } catch (UnsupportedEncodingException e2) {
            e2.printStackTrace();
            return null;
        } catch (NoSuchAlgorithmException e3) {
            e3.printStackTrace();
            return null;
        }
    }

    public static String getMD5(String message) {
        try {
            return byte2hexString(MessageDigest.getInstance("MD5").digest(message.getBytes("utf-8")));
        } catch (UnsupportedEncodingException e2) {
            e2.printStackTrace();
            return null;
        } catch (NoSuchAlgorithmException e3) {
            e3.printStackTrace();
            return null;
        }
    }
}
