package com.psychiatrygarden.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import com.just.agentweb.AgentWebPermissions;
import com.psychiatrygarden.ProjectApp;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes6.dex */
public final class Storage {
    private static final String APP_ROOT = "yikaobang";

    public static File getAppRootDir() {
        File file = null;
        if (!Environment.getExternalStorageState().equals("mounted")) {
            return null;
        }
        try {
            File file2 = new File(Build.VERSION.SDK_INT >= 29 ? ProjectApp.instance().getExternalFilesDir(null).getAbsolutePath() : Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath(), "yikaobang");
            try {
                if (!file2.exists()) {
                    file2.mkdirs();
                }
                return file2;
            } catch (Exception e2) {
                e = e2;
                file = file2;
                Log.e(AgentWebPermissions.ACTION_STORAGE, "root error", e);
                return file;
            }
        } catch (Exception e3) {
            e = e3;
        }
    }

    public static File getEncryptedFile() {
        return new File(getAppRootDir(), "encryptedApp.dat");
    }

    public static File getNoMediaFile() {
        return new File(getAppRootDir() + "/VideoDownload2", ".nomedia");
    }

    public static File getVideoDir(Context context) {
        File appRootDir = getAppRootDir();
        if (appRootDir == null || !appRootDir.isDirectory()) {
            return context.getFilesDir();
        }
        File file = new File(appRootDir, "video");
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }

    public static boolean moveEncryptedToStorage(AssetManager assetsManager) throws IOException {
        String[] list;
        File encryptedFile;
        try {
            list = assetsManager.list("");
            encryptedFile = getEncryptedFile();
        } catch (IOException e2) {
            Log.e(AgentWebPermissions.ACTION_STORAGE, "Error copying asset files ", e2);
        }
        if (encryptedFile != null && encryptedFile.exists() && encryptedFile.length() > 0) {
            return true;
        }
        for (String str : list) {
            if (TextUtils.equals(str, encryptedFile.getName())) {
                InputStream inputStreamOpen = assetsManager.open(str);
                FileOutputStream fileOutputStream = new FileOutputStream(encryptedFile);
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
            }
        }
        return false;
    }

    public static boolean moveNomediaToStorage(AssetManager assetsManager) throws IOException {
        try {
            String[] list = assetsManager.list("");
            File noMediaFile = getNoMediaFile();
            for (String str : list) {
                if (TextUtils.equals(str, noMediaFile.getName())) {
                    InputStream inputStreamOpen = assetsManager.open(str);
                    FileOutputStream fileOutputStream = new FileOutputStream(noMediaFile);
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
                }
            }
        } catch (IOException e2) {
            e2.printStackTrace();
            Log.e(AgentWebPermissions.ACTION_STORAGE, "Error copying asset files ", e2);
        }
        return false;
    }
}
