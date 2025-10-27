package com.psychiatrygarden.activity.purchase.util;

import android.graphics.Bitmap;
import android.os.Environment;
import com.psychiatrygarden.ProjectApp;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/* loaded from: classes5.dex */
public class FileUtils {
    public static String SDPATH = ProjectApp.instance().getStoragePath();

    /* renamed from: f, reason: collision with root package name */
    public static File f13717f;

    public static File createSDDir(String dirName) throws IOException {
        File file = new File(SDPATH + dirName);
        if (Environment.getExternalStorageState().equals("mounted")) {
            System.out.println("createSDDir:" + file.getAbsolutePath());
            System.out.println("createSDDir:" + file.mkdir());
        }
        return file;
    }

    public static void delFile(String fileName) {
        File file = new File(SDPATH + fileName);
        if (file.isFile()) {
            file.delete();
        }
        file.exists();
    }

    public static void deleteDir() {
        File file = new File(SDPATH);
        if (file.exists() && file.isDirectory()) {
            for (File file2 : file.listFiles()) {
                if (file2.isFile()) {
                    file2.delete();
                } else if (file2.isDirectory()) {
                    deleteDir();
                }
            }
            file.delete();
        }
    }

    public static boolean fileIsExists(String path) {
        try {
            return new File(path).exists();
        } catch (Exception unused) {
            return false;
        }
    }

    public static boolean isFileExist(String fileName) {
        File file = new File(SDPATH + fileName);
        file.isFile();
        return file.exists();
    }

    public static String saveBitmap(Bitmap bm, String picName) throws IOException {
        try {
            if (!isFileExist("")) {
                createSDDir("");
            }
            File file = new File(SDPATH, picName + ".JPEG");
            f13717f = file;
            if (file.exists()) {
                f13717f.delete();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(f13717f);
            bm.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (FileNotFoundException e2) {
            e2.printStackTrace();
        } catch (IOException e3) {
            e3.printStackTrace();
        } catch (Exception e4) {
            e4.printStackTrace();
        }
        return f13717f.getAbsolutePath();
    }
}
