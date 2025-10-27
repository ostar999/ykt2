package com.catchpig.mvvm.utils.screenshot;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.view.View;
import com.luck.picture.lib.config.PictureMimeType;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/* loaded from: classes2.dex */
public class ScreenshotUtil {
    private static boolean saveImageToGallery(Bitmap bitmap, Activity activity) throws IOException {
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "ydddoctor");
        if (!file.exists()) {
            file.mkdir();
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(new File(file, System.currentTimeMillis() + PictureMimeType.PNG));
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            activity.sendBroadcast(new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", Uri.parse("file://" + file.getAbsolutePath())));
            return true;
        } catch (FileNotFoundException e2) {
            e2.printStackTrace();
            return false;
        } catch (IOException e3) {
            e3.printStackTrace();
            return false;
        }
    }

    public static boolean saveScreenshot(View view, Activity activity) throws IOException {
        view.setDrawingCacheEnabled(true);
        boolean zSaveImageToGallery = saveImageToGallery(view.getDrawingCache(), activity);
        view.setDrawingCacheEnabled(false);
        view.destroyDrawingCache();
        return zSaveImageToGallery;
    }
}
