package com.hyphenate.util;

import android.content.Context;
import android.os.Environment;
import java.io.File;

/* loaded from: classes4.dex */
public class PathUtil {
    public static final String filePathName = "/file/";
    public static final String historyPathName = "/chat/";
    public static final String imagePathName = "/image/";
    private static PathUtil instance = null;
    public static final String meetingPathName = "/meeting/";
    public static final String netdiskDownloadPathName = "/netdisk/";
    public static String pathPrefix = null;
    private static File storageDir = null;
    public static final String videoPathName = "/video/";
    public static final String voicePathName = "/voice/";
    private File filePath;
    private File voicePath = null;
    private File imagePath = null;
    private File historyPath = null;
    private File videoPath = null;

    private PathUtil() {
    }

    private static File generateFiePath(String str, String str2, Context context) {
        String str3;
        if (str == null) {
            str3 = pathPrefix + str2 + filePathName;
        } else {
            str3 = pathPrefix + str + "/" + str2 + filePathName;
        }
        return new File(getStorageDir(context), str3);
    }

    private static File generateHistoryPath(String str, String str2, Context context) {
        String str3;
        if (str == null) {
            str3 = pathPrefix + str2 + historyPathName;
        } else {
            str3 = pathPrefix + str + "/" + str2 + historyPathName;
        }
        return new File(getStorageDir(context), str3);
    }

    private static File generateImagePath(String str, String str2, Context context) {
        String str3;
        if (str == null) {
            str3 = pathPrefix + str2 + imagePathName;
        } else {
            str3 = pathPrefix + str + "/" + str2 + imagePathName;
        }
        return new File(getStorageDir(context), str3);
    }

    private static File generateVideoPath(String str, String str2, Context context) {
        String str3;
        if (str == null) {
            str3 = pathPrefix + str2 + videoPathName;
        } else {
            str3 = pathPrefix + str + "/" + str2 + videoPathName;
        }
        return new File(getStorageDir(context), str3);
    }

    private static File generateVoicePath(String str, String str2, Context context) {
        String str3;
        if (str == null) {
            str3 = pathPrefix + str2 + voicePathName;
        } else {
            str3 = pathPrefix + str + "/" + str2 + voicePathName;
        }
        return new File(getStorageDir(context), str3);
    }

    public static PathUtil getInstance() {
        if (instance == null) {
            instance = new PathUtil();
        }
        return instance;
    }

    private static File getStorageDir(Context context) {
        if (storageDir == null) {
            File externalStorageDirectory = Environment.getExternalStorageDirectory();
            if (externalStorageDirectory.exists()) {
                return externalStorageDirectory;
            }
            storageDir = context.getFilesDir();
        }
        return storageDir;
    }

    public static File getTempPath(File file) {
        return new File(file.getAbsoluteFile() + ".tmp");
    }

    public File getFilePath() {
        return this.filePath;
    }

    public File getHistoryPath() {
        return this.historyPath;
    }

    public File getImagePath() {
        return this.imagePath;
    }

    public File getVideoPath() {
        return this.videoPath;
    }

    public File getVoicePath() {
        return this.voicePath;
    }

    public void initDirs(String str, String str2, Context context) {
        pathPrefix = "/Android/data/" + context.getPackageName() + "/";
        File fileGenerateVoicePath = generateVoicePath(str, str2, context);
        this.voicePath = fileGenerateVoicePath;
        if (!fileGenerateVoicePath.exists()) {
            this.voicePath.mkdirs();
        }
        File fileGenerateImagePath = generateImagePath(str, str2, context);
        this.imagePath = fileGenerateImagePath;
        if (!fileGenerateImagePath.exists()) {
            this.imagePath.mkdirs();
        }
        File fileGenerateHistoryPath = generateHistoryPath(str, str2, context);
        this.historyPath = fileGenerateHistoryPath;
        if (!fileGenerateHistoryPath.exists()) {
            this.historyPath.mkdirs();
        }
        File fileGenerateVideoPath = generateVideoPath(str, str2, context);
        this.videoPath = fileGenerateVideoPath;
        if (!fileGenerateVideoPath.exists()) {
            this.videoPath.mkdirs();
        }
        File fileGenerateFiePath = generateFiePath(str, str2, context);
        this.filePath = fileGenerateFiePath;
        if (fileGenerateFiePath.exists()) {
            return;
        }
        this.filePath.mkdirs();
    }
}
