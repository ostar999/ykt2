package com.hyphenate.easeui.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import com.azhon.appupdate.config.Constant;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.R;
import com.hyphenate.util.EMLog;
import com.hyphenate.util.PathUtil;
import com.hyphenate.util.VersionUtils;
import com.psychiatrygarden.utils.MimeTypes;
import com.yikaobang.yixue.R2;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/* loaded from: classes4.dex */
public class EaseCompat {
    private static final String TAG = "EaseCompat";

    private static boolean checkSuffix(String str, String[] strArr) {
        if (!TextUtils.isEmpty(str) && strArr != null && strArr.length > 0) {
            for (String str2 : strArr) {
                if (str.toLowerCase().endsWith(str2)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void deleteFile(Context context, Uri uri) {
        EaseFileUtils.deleteFile(context, uri);
    }

    private static File getCameraFile() {
        File file = new File(PathUtil.getInstance().getImagePath(), EMClient.getInstance().getCurrentUser() + System.currentTimeMillis() + ".jpg");
        file.getParentFile().mkdirs();
        return file;
    }

    private static Intent getCameraIntent(Context context, File file) {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra("output", getUriForFile(context, file));
        return intent;
    }

    public static String getFileNameByUri(Context context, Uri uri) {
        return EaseFileUtils.getFileNameByUri(context, uri);
    }

    public static String getMimeType(Context context, @NonNull Uri uri) {
        return context.getContentResolver().getType(uri);
    }

    private static Intent getOpenImageIntent(Context context) {
        Intent intent;
        if (VersionUtils.isTargetQ(context)) {
            intent = new Intent("android.intent.action.OPEN_DOCUMENT");
            intent.addCategory("android.intent.category.OPENABLE");
        } else {
            intent = new Intent("android.intent.action.PICK", MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        }
        intent.addFlags(3);
        intent.setType(MimeTypes.IMAGE_ALL);
        return intent;
    }

    public static String getPath(Context context, Uri uri) {
        return EaseFileUtils.getFilePath(context, uri);
    }

    public static int getSupportedWindowType() {
        if (Build.VERSION.SDK_INT >= 26) {
            return R2.attr.index_change_img;
        }
        return 2003;
    }

    public static Uri getUriForFile(Context context, @NonNull File file) {
        if (Build.VERSION.SDK_INT < 24) {
            return Uri.fromFile(file);
        }
        return FileProvider.getUriForFile(context, context.getPackageName() + ".fileProvider", file);
    }

    private static File getVideoFile() {
        File file = new File(PathUtil.getInstance().getVideoPath(), System.currentTimeMillis() + ".mp4");
        file.getParentFile().mkdirs();
        return file;
    }

    private static Intent getVideoIntent(Context context, File file) {
        Intent intent = new Intent("android.media.action.VIDEO_CAPTURE");
        intent.putExtra("output", getUriForFile(context, file));
        return intent;
    }

    public static String getVideoThumbnail(Context context, @NonNull Uri uri) throws SecurityException, IOException, IllegalArgumentException {
        File file = new File(PathUtil.getInstance().getVideoPath(), "thvideo" + System.currentTimeMillis());
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
            mediaMetadataRetriever.setDataSource(context, uri);
            mediaMetadataRetriever.getFrameAtTime().compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            fileOutputStream.close();
            return file.getAbsolutePath();
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public static boolean isImageType(Context context, @NonNull Uri uri) {
        String mimeType = getMimeType(context, uri);
        if (TextUtils.isEmpty(mimeType)) {
            return false;
        }
        return mimeType.startsWith("image");
    }

    public static boolean isVideoFile(Context context, String str) {
        return checkSuffix(str, context.getResources().getStringArray(R.array.ease_video_file_suffix));
    }

    public static boolean isVideoType(Context context, @NonNull Uri uri) {
        String mimeType = getMimeType(context, uri);
        if (TextUtils.isEmpty(mimeType)) {
            return false;
        }
        return mimeType.startsWith("video");
    }

    public static boolean openApk(Context context, Uri uri) {
        return openApk(context, uri, getFileNameByUri(context, uri));
    }

    public static void openFile(File file, Activity activity) {
        openFile(activity, file);
    }

    public static void openImage(Activity activity, int i2) {
        activity.startActivityForResult(getOpenImageIntent(activity), i2);
    }

    public static void setIntentByType(Context context, String str, Intent intent) {
        Resources resources = context.getResources();
        if (checkSuffix(str, resources.getStringArray(R.array.ease_audio_file_suffix)) || checkSuffix(str, resources.getStringArray(R.array.ease_video_file_suffix))) {
            intent.addFlags(67108864);
            intent.putExtra("oneshot", 0);
            intent.putExtra("configchange", 0);
        } else if (checkSuffix(str, resources.getStringArray(R.array.ease_image_file_suffix))) {
            intent.addCategory("android.intent.category.DEFAULT");
            intent.addFlags(268435456);
        } else if (checkSuffix(str, resources.getStringArray(R.array.ease_excel_file_suffix)) || checkSuffix(str, resources.getStringArray(R.array.ease_word_file_suffix)) || checkSuffix(str, resources.getStringArray(R.array.ease_pdf_file_suffix))) {
            intent.addCategory("android.intent.category.DEFAULT");
            intent.addFlags(268435456);
        } else {
            intent.addCategory("android.intent.category.DEFAULT");
            intent.addFlags(268435456);
        }
    }

    public static File takePicture(Activity activity, int i2) {
        if (!EaseCommonUtils.isSdcardExist()) {
            return null;
        }
        File cameraFile = getCameraFile();
        activity.startActivityForResult(getCameraIntent(activity, cameraFile), i2);
        return cameraFile;
    }

    public static File takeVideo(Activity activity, int i2) {
        if (!EaseCommonUtils.isSdcardExist()) {
            return null;
        }
        File videoFile = getVideoFile();
        activity.startActivityForResult(getVideoIntent(activity, videoFile), i2);
        return videoFile;
    }

    public static String getMimeType(Context context, @NonNull File file) {
        return getMimeType(context, file.getName());
    }

    public static void openFile(Context context, String str) {
        if (TextUtils.isEmpty(str) || !new File(str).exists()) {
            EMLog.e(TAG, "文件不存在！");
        } else {
            openFile(context, new File(str));
        }
    }

    public static String getMimeType(Context context, String str) {
        Resources resources = context.getResources();
        return checkSuffix(str, resources.getStringArray(R.array.ease_image_file_suffix)) ? MimeTypes.IMAGE_ALL : checkSuffix(str, resources.getStringArray(R.array.ease_video_file_suffix)) ? MimeTypes.VIDEO_ALL : checkSuffix(str, resources.getStringArray(R.array.ease_audio_file_suffix)) ? MimeTypes.AUDIO_ALL : checkSuffix(str, resources.getStringArray(R.array.ease_file_file_suffix)) ? "text/plain" : checkSuffix(str, resources.getStringArray(R.array.ease_word_file_suffix)) ? MimeTypes.APP_MSWORD : checkSuffix(str, resources.getStringArray(R.array.ease_excel_file_suffix)) ? MimeTypes.APP_EXCEL : checkSuffix(str, resources.getStringArray(R.array.ease_pdf_file_suffix)) ? MimeTypes.APP_PDF : checkSuffix(str, resources.getStringArray(R.array.ease_apk_file_suffix)) ? "application/vnd.android.package-archive" : "application/octet-stream";
    }

    private static boolean openApk(Context context, Uri uri, @NonNull String str) {
        if (!str.endsWith(Constant.APK_SUFFIX)) {
            return false;
        }
        if (Build.VERSION.SDK_INT >= 24) {
            Intent intent = new Intent("android.intent.action.VIEW");
            intent.addFlags(1);
            intent.setDataAndType(uri, getMimeType(context, str));
            context.startActivity(intent);
        } else {
            Intent intent2 = new Intent("android.intent.action.VIEW");
            intent2.setFlags(268435456);
            intent2.setDataAndType(uri, getMimeType(context, str));
            context.startActivity(intent2);
        }
        return true;
    }

    public static void openImage(Fragment fragment, int i2) {
        fragment.startActivityForResult(getOpenImageIntent(fragment.getActivity()), i2);
    }

    public static void openFile(Context context, File file) {
        if (file != null && file.exists()) {
            String name = file.getName();
            String mimeType = getMimeType(context, file);
            Uri uriForFile = getUriForFile(context, file);
            if (isVideoFile(context, name)) {
                uriForFile = Uri.parse(file.getAbsolutePath());
            }
            openFile(context, uriForFile, name, mimeType);
            return;
        }
        EMLog.e(TAG, "Cannot open the file, because the file is not exit, file: " + file);
    }

    public static File takePicture(Fragment fragment, int i2) {
        if (!EaseCommonUtils.isSdcardExist()) {
            return null;
        }
        File cameraFile = getCameraFile();
        fragment.startActivityForResult(getCameraIntent(fragment.getContext(), cameraFile), i2);
        return cameraFile;
    }

    public static File takeVideo(Fragment fragment, int i2) {
        if (!EaseCommonUtils.isSdcardExist()) {
            return null;
        }
        File videoFile = getVideoFile();
        fragment.startActivityForResult(getVideoIntent(fragment.getContext(), videoFile), i2);
        return videoFile;
    }

    public static void openFile(Context context, Uri uri) {
        if (!EaseFileUtils.isFileExistByUri(context, uri)) {
            EMLog.e(TAG, "Cannot open the file, because the file is not exit, uri: " + uri);
            return;
        }
        String filePath = EaseFileUtils.getFilePath(context, uri);
        if (!TextUtils.isEmpty(filePath) && new File(filePath).exists()) {
            openFile(context, new File(filePath));
        } else {
            String fileNameByUri = getFileNameByUri(context, uri);
            openFile(context, uri, fileNameByUri, getMimeType(context, fileNameByUri));
        }
    }

    private static void openFile(Context context, Uri uri, String str, String str2) {
        if (openApk(context, uri, str)) {
            return;
        }
        EMLog.d(TAG, "openFile filename = " + str + " mimeType = " + str2);
        StringBuilder sb = new StringBuilder();
        sb.append("openFile uri = ");
        sb.append(uri != null ? uri.toString() : "uri is null");
        EMLog.d(TAG, sb.toString());
        Intent intent = new Intent("android.intent.action.VIEW");
        setIntentByType(context, str, intent);
        intent.addFlags(1);
        intent.setDataAndType(uri, str2);
        try {
            context.startActivity(intent);
        } catch (Exception e2) {
            e2.printStackTrace();
            EMLog.e(TAG, e2.getMessage());
            Toast.makeText(context, context.getString(R.string.can_not_find_app_open_file), 1).show();
        }
    }
}
