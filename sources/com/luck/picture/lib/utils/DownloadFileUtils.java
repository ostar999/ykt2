package com.luck.picture.lib.utils;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import com.luck.picture.lib.basic.PictureContentResolver;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.interfaces.OnCallbackListener;
import com.luck.picture.lib.thread.PictureThreadUtils;
import java.io.File;
import java.io.FileInputStream;
import java.net.URL;

/* loaded from: classes4.dex */
public class DownloadFileUtils {
    public static void saveLocalFile(final Context context, final String str, final String str2, final OnCallbackListener<String> onCallbackListener) {
        PictureThreadUtils.executeByIo(new PictureThreadUtils.SimpleTask<String>() { // from class: com.luck.picture.lib.utils.DownloadFileUtils.1
            @Override // com.luck.picture.lib.thread.PictureThreadUtils.Task
            public String doInBackground() throws Throwable {
                Uri uriInsert;
                ContentValues contentValues = new ContentValues();
                String string = ValueOf.toString(Long.valueOf(System.currentTimeMillis()));
                if (PictureMimeType.isHasVideo(str2)) {
                    contentValues.put("_display_name", DateUtils.getCreateFileName("VID_"));
                    contentValues.put("mime_type", (TextUtils.isEmpty(str2) || str2.startsWith("image")) ? "video/mp4" : str2);
                    if (SdkVersionUtils.isQ()) {
                        contentValues.put("datetaken", string);
                        contentValues.put("relative_path", Environment.DIRECTORY_MOVIES);
                    } else {
                        contentValues.put("_data", Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES).getAbsolutePath() + File.separator + DateUtils.getCreateFileName("VID_") + ".mp4");
                    }
                    uriInsert = context.getContentResolver().insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, contentValues);
                } else {
                    contentValues.put("_display_name", DateUtils.getCreateFileName("IMG_"));
                    contentValues.put("mime_type", (TextUtils.isEmpty(str2) || str2.startsWith("video")) ? "image/jpeg" : str2);
                    if (SdkVersionUtils.isQ()) {
                        contentValues.put("datetaken", string);
                        contentValues.put("relative_path", "DCIM/Camera");
                    } else if (PictureMimeType.isHasGif(str2) || PictureMimeType.isUrlHasGif(str)) {
                        contentValues.put("_data", Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath() + File.separator + DateUtils.getCreateFileName("IMG_") + PictureMimeType.GIF);
                    }
                    uriInsert = context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
                }
                if (uriInsert == null) {
                    return null;
                }
                if (PictureFileUtils.writeFileFromIS(PictureMimeType.isHasHttp(str) ? new URL(str).openStream() : PictureMimeType.isContent(str) ? PictureContentResolver.getContentResolverOpenInputStream(context, Uri.parse(str)) : new FileInputStream(str), PictureContentResolver.getContentResolverOpenOutputStream(context, uriInsert))) {
                    return PictureFileUtils.getPath(context, uriInsert);
                }
                return null;
            }

            @Override // com.luck.picture.lib.thread.PictureThreadUtils.Task
            public void onSuccess(String str3) {
                PictureThreadUtils.cancel(this);
                OnCallbackListener onCallbackListener2 = onCallbackListener;
                if (onCallbackListener2 != null) {
                    onCallbackListener2.onCall(str3);
                }
            }
        });
    }
}
