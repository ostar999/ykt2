package com.luck.picture.lib.utils;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.webkit.MimeTypeMap;
import androidx.exifinterface.media.ExifInterface;
import com.luck.picture.lib.app.PictureAppMaster;
import com.luck.picture.lib.basic.PictureContentResolver;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.MediaExtraInfo;
import com.umeng.analytics.pro.aq;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;

/* loaded from: classes4.dex */
public class MediaUtils {
    public static final String QUERY_ARG_SQL_LIMIT = "android:query-arg-sql-limit";

    public static Bundle createQueryArgsBundle(String str, String[] strArr, int i2, int i3) {
        Bundle bundle = new Bundle();
        if (Build.VERSION.SDK_INT >= 26) {
            bundle.putString("android:query-arg-sql-selection", str);
            bundle.putStringArray("android:query-arg-sql-selection-args", strArr);
            bundle.putString("android:query-arg-sql-sort-order", "_id DESC");
            if (SdkVersionUtils.isR()) {
                bundle.putString(QUERY_ARG_SQL_LIMIT, i2 + " offset " + i3);
            }
        }
        return bundle;
    }

    public static void deleteUri(Context context, String str) {
        try {
            if (PictureMimeType.isContent(str)) {
                context.getContentResolver().delete(Uri.parse(str), null, null);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public static long generateCameraBucketId(Context context, File file, String str) {
        return TextUtils.isEmpty(str) ? getCameraFirstBucketId(context) : file.getParentFile() != null ? file.getParentFile().getName().hashCode() : getCameraFirstBucketId(context);
    }

    public static String generateCameraFolderName(String str) {
        File file = new File(str);
        return file.getParentFile() != null ? file.getParentFile().getName() : "Camera";
    }

    public static long generateSoundsBucketId(Context context, File file, String str) {
        return TextUtils.isEmpty(str) ? getSoundsFirstBucketId(context) : file.getParentFile() != null ? file.getParentFile().getName().hashCode() : getSoundsFirstBucketId(context);
    }

    public static MediaExtraInfo getAudioSize(Context context, String str) throws IOException {
        MediaExtraInfo mediaExtraInfo = new MediaExtraInfo();
        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
        try {
            try {
                if (PictureMimeType.isContent(str)) {
                    mediaMetadataRetriever.setDataSource(context, Uri.parse(str));
                } else {
                    mediaMetadataRetriever.setDataSource(str);
                }
                mediaExtraInfo.setDuration(ValueOf.toLong(mediaMetadataRetriever.extractMetadata(9)));
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            return mediaExtraInfo;
        } finally {
            mediaMetadataRetriever.release();
        }
    }

    public static long getCameraFirstBucketId(Context context) {
        Cursor cursorQuery = null;
        try {
            try {
                String[] strArr = {PictureFileUtils.getDCIMCameraPath() + "%"};
                cursorQuery = SdkVersionUtils.isR() ? context.getApplicationContext().getContentResolver().query(MediaStore.Files.getContentUri("external"), null, createQueryArgsBundle("_data like ?", strArr, 1, 0), null) : context.getApplicationContext().getContentResolver().query(MediaStore.Files.getContentUri("external"), null, "_data like ?", strArr, "_id DESC limit 1 offset 0");
            } catch (Exception e2) {
                e2.printStackTrace();
                if (cursorQuery == null) {
                    return -1L;
                }
            }
            if (cursorQuery != null && cursorQuery.getCount() > 0 && cursorQuery.moveToFirst()) {
                long j2 = cursorQuery.getLong(cursorQuery.getColumnIndex("bucket_id"));
                cursorQuery.close();
                return j2;
            }
            if (cursorQuery == null) {
                return -1L;
            }
            cursorQuery.close();
            return -1L;
        } catch (Throwable th) {
            if (cursorQuery != null) {
                cursorQuery.close();
            }
            throw th;
        }
    }

    public static int getDCIMLastImageId(Context context) {
        Cursor cursorQuery = null;
        try {
            try {
                String[] strArr = {PictureFileUtils.getDCIMCameraPath() + "%"};
                cursorQuery = SdkVersionUtils.isR() ? context.getApplicationContext().getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, createQueryArgsBundle("_data like ?", strArr, 1, 0), null) : context.getApplicationContext().getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, "_data like ?", strArr, "_id DESC limit 1 offset 0");
                if (cursorQuery == null || cursorQuery.getCount() <= 0 || !cursorQuery.moveToFirst()) {
                    if (cursorQuery != null) {
                        cursorQuery.close();
                    }
                    return -1;
                }
                int i2 = DateUtils.dateDiffer(cursorQuery.getLong(cursorQuery.getColumnIndex("date_added"))) <= 1 ? cursorQuery.getInt(cursorQuery.getColumnIndex(aq.f22519d)) : -1;
                cursorQuery.close();
                return i2;
            } catch (Exception e2) {
                e2.printStackTrace();
                if (cursorQuery != null) {
                    cursorQuery.close();
                }
                return -1;
            }
        } catch (Throwable th) {
            if (cursorQuery != null) {
                cursorQuery.close();
            }
            throw th;
        }
    }

    public static MediaExtraInfo getImageSize(Context context, String str) {
        MediaExtraInfo mediaExtraInfo = new MediaExtraInfo();
        try {
            ExifInterface exifInterface = PictureMimeType.isContent(str) ? new ExifInterface(PictureContentResolver.getContentResolverOpenInputStream(context, Uri.parse(str))) : new ExifInterface(str);
            mediaExtraInfo.setWidth(exifInterface.getAttributeInt(ExifInterface.TAG_IMAGE_WIDTH, 1));
            mediaExtraInfo.setHeight(exifInterface.getAttributeInt(ExifInterface.TAG_IMAGE_LENGTH, 1));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        return mediaExtraInfo;
    }

    private static String getMimeType(File file) {
        return URLConnection.getFileNameMap().getContentTypeFor(file.getName());
    }

    public static String getMimeTypeFromMediaUrl(String str) {
        String mimeTypeFromExtension = MimeTypeMap.getSingleton().getMimeTypeFromExtension(MimeTypeMap.getFileExtensionFromUrl(str).toLowerCase());
        if (TextUtils.isEmpty(mimeTypeFromExtension)) {
            mimeTypeFromExtension = getMimeType(new File(str));
        }
        return TextUtils.isEmpty(mimeTypeFromExtension) ? "image/jpeg" : mimeTypeFromExtension;
    }

    public static String getRealPathUri(long j2, String str) {
        return ContentUris.withAppendedId(PictureMimeType.isHasImage(str) ? MediaStore.Images.Media.EXTERNAL_CONTENT_URI : PictureMimeType.isHasVideo(str) ? MediaStore.Video.Media.EXTERNAL_CONTENT_URI : PictureMimeType.isHasAudio(str) ? MediaStore.Audio.Media.EXTERNAL_CONTENT_URI : MediaStore.Files.getContentUri("external"), j2).toString();
    }

    public static long getSoundsFirstBucketId(Context context) {
        Cursor cursorQuery = null;
        try {
            try {
                String[] strArr = {PictureFileUtils.getSoundsPath() + "%"};
                cursorQuery = SdkVersionUtils.isR() ? context.getApplicationContext().getContentResolver().query(MediaStore.Files.getContentUri("external"), null, createQueryArgsBundle("_data like ?", strArr, 1, 0), null) : context.getApplicationContext().getContentResolver().query(MediaStore.Files.getContentUri("external"), null, "_data like ?", strArr, "_id DESC limit 1 offset 0");
            } catch (Exception e2) {
                e2.printStackTrace();
                if (cursorQuery == null) {
                    return -1L;
                }
            }
            if (cursorQuery != null && cursorQuery.getCount() > 0 && cursorQuery.moveToFirst()) {
                long j2 = cursorQuery.getLong(cursorQuery.getColumnIndex("bucket_id"));
                cursorQuery.close();
                return j2;
            }
            if (cursorQuery == null) {
                return -1L;
            }
            cursorQuery.close();
            return -1L;
        } catch (Throwable th) {
            if (cursorQuery != null) {
                cursorQuery.close();
            }
            throw th;
        }
    }

    public static MediaExtraInfo getVideoSize(Context context, String str) throws IOException {
        int i2;
        int i3;
        MediaExtraInfo mediaExtraInfo = new MediaExtraInfo();
        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
        try {
            try {
                if (PictureMimeType.isContent(str)) {
                    mediaMetadataRetriever.setDataSource(context, Uri.parse(str));
                } else {
                    mediaMetadataRetriever.setDataSource(str);
                }
                String strExtractMetadata = mediaMetadataRetriever.extractMetadata(24);
                if (TextUtils.equals("90", strExtractMetadata) || TextUtils.equals("270", strExtractMetadata)) {
                    int i4 = ValueOf.toInt(mediaMetadataRetriever.extractMetadata(18));
                    i2 = i4;
                    i3 = ValueOf.toInt(mediaMetadataRetriever.extractMetadata(19));
                } else {
                    i3 = ValueOf.toInt(mediaMetadataRetriever.extractMetadata(18));
                    i2 = ValueOf.toInt(mediaMetadataRetriever.extractMetadata(19));
                }
                mediaExtraInfo.setWidth(i3);
                mediaExtraInfo.setHeight(i2);
                mediaExtraInfo.setOrientation(strExtractMetadata);
                mediaExtraInfo.setDuration(ValueOf.toLong(mediaMetadataRetriever.extractMetadata(9)));
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            return mediaExtraInfo;
        } finally {
            mediaMetadataRetriever.release();
        }
    }

    public static boolean isLongImage(int i2, int i3) {
        return i2 > 0 && i3 > 0 && i3 > i2 * 3;
    }

    public static void removeMedia(Context context, int i2) {
        try {
            context.getApplicationContext().getContentResolver().delete(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "_id=?", new String[]{Long.toString(i2)});
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public static MediaExtraInfo getImageSize(String str) {
        InputStream fileInputStream;
        MediaExtraInfo mediaExtraInfo = new MediaExtraInfo();
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            if (PictureMimeType.isContent(str)) {
                fileInputStream = PictureContentResolver.getContentResolverOpenInputStream(PictureAppMaster.getInstance().getAppContext(), Uri.parse(str));
            } else {
                fileInputStream = new FileInputStream(str);
            }
            BitmapFactory.decodeStream(fileInputStream, null, options);
            mediaExtraInfo.setWidth(options.outWidth);
            mediaExtraInfo.setHeight(options.outHeight);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        return mediaExtraInfo;
    }
}
