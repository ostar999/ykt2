package com.yddmi.doctor.utils;

import android.content.Context;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Pattern;

/* loaded from: classes6.dex */
public class FucUtil {
    private static final String regEx_html = "<[^>]+>";
    private static final String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>";
    private static final String regEx_space = "\\s*|\t|\r|\n";
    private static final String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>";

    public static int compareVersion(String str, String str2) {
        try {
            if (str.equals(str2)) {
                return 0;
            }
            String[] strArrSplit = str.split("[._]");
            String[] strArrSplit2 = str2.split("[._]");
            int iMin = Math.min(strArrSplit.length, strArrSplit2.length);
            int i2 = 0;
            long j2 = 0;
            while (i2 < iMin) {
                j2 = Long.parseLong(strArrSplit[i2]) - Long.parseLong(strArrSplit2[i2]);
                if (j2 != 0) {
                    break;
                }
                i2++;
            }
            if (j2 != 0) {
                return j2 > 0 ? 1 : -1;
            }
            for (int i3 = i2; i3 < strArrSplit.length; i3++) {
                if (Long.parseLong(strArrSplit[i3]) > 0) {
                    return 1;
                }
            }
            while (i2 < strArrSplit2.length) {
                if (Long.parseLong(strArrSplit2[i2]) > 0) {
                    return -1;
                }
                i2++;
            }
            return 0;
        } catch (Exception unused) {
            return 0;
        }
    }

    public static boolean copyAssetFile(Context context, String str, String str2) throws IOException {
        try {
            InputStream inputStreamOpen = context.getAssets().open(str);
            File file = new File(str2);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            byte[] bArr = new byte[1024];
            while (true) {
                int i2 = inputStreamOpen.read(bArr);
                if (i2 == -1) {
                    inputStreamOpen.close();
                    fileOutputStream.close();
                    return true;
                }
                fileOutputStream.write(bArr, 0, i2);
            }
        } catch (IOException e2) {
            e2.printStackTrace();
            return false;
        }
    }

    public static boolean copyAssetFolder(Context context, String str, String str2) throws IOException {
        try {
            String[] list = context.getAssets().list(str);
            if (list == null) {
                return false;
            }
            if (list.length == 0) {
                return copyAssetFile(context, str, str2);
            }
            boolean zMkdirs = new File(str2).mkdirs();
            for (String str3 : list) {
                StringBuilder sb = new StringBuilder();
                sb.append(str);
                String str4 = File.separator;
                sb.append(str4);
                sb.append(str3);
                zMkdirs &= copyAssetFolder(context, sb.toString(), str2 + str4 + str3);
            }
            return zMkdirs;
        } catch (IOException e2) {
            e2.printStackTrace();
            return false;
        }
    }

    public static String delHTMLTag(String str) {
        return Pattern.compile(regEx_space, 2).matcher(Pattern.compile(regEx_html, 2).matcher(Pattern.compile(regEx_style, 2).matcher(Pattern.compile(regEx_script, 2).matcher(str).replaceAll("")).replaceAll("")).replaceAll("")).replaceAll("").trim();
    }

    public static String readFile(Context context, String str, String str2) throws IOException {
        try {
            InputStream inputStreamOpen = context.getAssets().open(str);
            int iAvailable = inputStreamOpen.available();
            byte[] bArr = new byte[iAvailable];
            inputStreamOpen.read(bArr, 0, iAvailable);
            return new String(bArr, str2);
        } catch (Exception e2) {
            e2.printStackTrace();
            return "";
        }
    }
}
