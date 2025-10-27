package com.plv.livescenes.download;

import java.io.File;

/* loaded from: classes4.dex */
public class PLVDownloadDirManager {
    public static final String JS_PREFIX = "js";
    public static final String PPT_PREFIX = "ppt";
    public static final String VIDEO_PREFIX = "video";

    public static String getJSPath(String str, String str2) {
        return getRootDir(str, str2) + File.separator + JS_PREFIX;
    }

    public static String getPPTPath(String str, String str2) {
        return getRootDir(str, str2) + File.separator + "ppt";
    }

    public static String getRootDir(String str, String str2) {
        return str2 + File.separator + str;
    }

    public static String getVideoPath(String str, String str2) {
        return getRootDir(str, str2) + File.separator + "video";
    }
}
