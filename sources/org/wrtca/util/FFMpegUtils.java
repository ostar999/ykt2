package org.wrtca.util;

/* loaded from: classes9.dex */
public class FFMpegUtils {
    public static boolean captureThumbnails(String str, String str2, String str3) {
        return false;
    }

    public static boolean captureThumbnails(String str, String str2, String str3, String str4) {
        String str5;
        FileUtils.deleteFile(str2);
        if (str4 == null) {
            str5 = "";
        } else {
            str5 = " -ss " + str4;
        }
        String.format("ffmpeg -d stdout -loglevel verbose -i \"%s\"%s -s %s -vframes 1 \"%s\"", str, str5, str3, str2);
        return false;
    }

    public static String getCaptureThumbnailsCMD(String str, String str2, String str3) {
        String str4;
        if (str3 == null) {
            str4 = "";
        } else {
            str4 = " -ss " + str3;
        }
        return String.format("ffmpeg  -i  %s  %s  -vframes 1  %s ", str, str4, str2);
    }
}
