package com.luck.picture.lib.utils;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import cn.hutool.core.text.StrPool;
import com.luck.picture.lib.config.PictureSelectionConfig;
import java.io.File;

/* loaded from: classes4.dex */
public class MediaStoreUtils {
    public static ContentValues buildImageContentValues(String str, String str2) {
        String string = ValueOf.toString(Long.valueOf(System.currentTimeMillis()));
        ContentValues contentValues = new ContentValues(3);
        if (TextUtils.isEmpty(str) || str.lastIndexOf(StrPool.DOT) == -1) {
            contentValues.put("_display_name", DateUtils.getCreateFileName("IMG_"));
        } else {
            contentValues.put("_display_name", str.replaceAll(str.substring(str.lastIndexOf(StrPool.DOT)), ""));
        }
        if (TextUtils.isEmpty(str2) || str2.startsWith("video")) {
            str2 = "image/jpeg";
        }
        contentValues.put("mime_type", str2);
        if (SdkVersionUtils.isQ()) {
            contentValues.put("datetaken", string);
            contentValues.put("relative_path", "DCIM/Camera");
        }
        return contentValues;
    }

    public static ContentValues buildVideoContentValues(String str, String str2) {
        String string = ValueOf.toString(Long.valueOf(System.currentTimeMillis()));
        ContentValues contentValues = new ContentValues(3);
        if (TextUtils.isEmpty(str) || str.lastIndexOf(StrPool.DOT) == -1) {
            contentValues.put("_display_name", DateUtils.getCreateFileName("VID_"));
        } else {
            contentValues.put("_display_name", str.replaceAll(str.substring(str.lastIndexOf(StrPool.DOT)), ""));
        }
        if (TextUtils.isEmpty(str2) || str2.startsWith("image")) {
            str2 = "video/mp4";
        }
        contentValues.put("mime_type", str2);
        if (SdkVersionUtils.isQ()) {
            contentValues.put("datetaken", string);
            contentValues.put("relative_path", Environment.DIRECTORY_MOVIES);
        }
        return contentValues;
    }

    public static Uri createCameraOutImageUri(Context context, PictureSelectionConfig pictureSelectionConfig) {
        String str;
        if (TextUtils.isEmpty(pictureSelectionConfig.outPutCameraImageFileName)) {
            str = "";
        } else if (pictureSelectionConfig.isOnlyCamera) {
            str = pictureSelectionConfig.outPutCameraImageFileName;
        } else {
            str = System.currentTimeMillis() + StrPool.UNDERLINE + pictureSelectionConfig.outPutCameraImageFileName;
        }
        if (SdkVersionUtils.isQ() && TextUtils.isEmpty(pictureSelectionConfig.outPutCameraDir)) {
            Uri uriCreateImageUri = createImageUri(context, str, pictureSelectionConfig.cameraImageFormatForQ);
            pictureSelectionConfig.cameraPath = uriCreateImageUri != null ? uriCreateImageUri.toString() : null;
            return uriCreateImageUri;
        }
        File fileCreateCameraFile = PictureFileUtils.createCameraFile(context, 1, str, pictureSelectionConfig.cameraImageFormat, pictureSelectionConfig.outPutCameraDir);
        pictureSelectionConfig.cameraPath = fileCreateCameraFile.getAbsolutePath();
        return PictureFileUtils.parUri(context, fileCreateCameraFile);
    }

    public static Uri createCameraOutVideoUri(Context context, PictureSelectionConfig pictureSelectionConfig) {
        String str;
        if (TextUtils.isEmpty(pictureSelectionConfig.outPutCameraVideoFileName)) {
            str = "";
        } else if (pictureSelectionConfig.isOnlyCamera) {
            str = pictureSelectionConfig.outPutCameraVideoFileName;
        } else {
            str = System.currentTimeMillis() + StrPool.UNDERLINE + pictureSelectionConfig.outPutCameraVideoFileName;
        }
        if (SdkVersionUtils.isQ() && TextUtils.isEmpty(pictureSelectionConfig.outPutCameraDir)) {
            Uri uriCreateVideoUri = createVideoUri(context, str, pictureSelectionConfig.cameraVideoFormatForQ);
            pictureSelectionConfig.cameraPath = uriCreateVideoUri != null ? uriCreateVideoUri.toString() : "";
            return uriCreateVideoUri;
        }
        File fileCreateCameraFile = PictureFileUtils.createCameraFile(context, 2, str, pictureSelectionConfig.cameraVideoFormat, pictureSelectionConfig.outPutCameraDir);
        pictureSelectionConfig.cameraPath = fileCreateCameraFile.getAbsolutePath();
        return PictureFileUtils.parUri(context, fileCreateCameraFile);
    }

    public static Uri createImageUri(Context context, String str, String str2) {
        Context applicationContext = context.getApplicationContext();
        Uri[] uriArr = {null};
        String externalStorageState = Environment.getExternalStorageState();
        ContentValues contentValuesBuildImageContentValues = buildImageContentValues(str, str2);
        if (externalStorageState.equals("mounted")) {
            uriArr[0] = applicationContext.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValuesBuildImageContentValues);
        } else {
            uriArr[0] = applicationContext.getContentResolver().insert(MediaStore.Images.Media.INTERNAL_CONTENT_URI, contentValuesBuildImageContentValues);
        }
        return uriArr[0];
    }

    public static Uri createVideoUri(Context context, String str, String str2) {
        Context applicationContext = context.getApplicationContext();
        Uri[] uriArr = {null};
        String externalStorageState = Environment.getExternalStorageState();
        ContentValues contentValuesBuildVideoContentValues = buildVideoContentValues(str, str2);
        if (externalStorageState.equals("mounted")) {
            uriArr[0] = applicationContext.getContentResolver().insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, contentValuesBuildVideoContentValues);
        } else {
            uriArr[0] = applicationContext.getContentResolver().insert(MediaStore.Video.Media.INTERNAL_CONTENT_URI, contentValuesBuildVideoContentValues);
        }
        return uriArr[0];
    }
}
