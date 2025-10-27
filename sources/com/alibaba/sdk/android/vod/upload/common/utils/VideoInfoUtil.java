package com.alibaba.sdk.android.vod.upload.common.utils;

import android.content.Context;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import com.alibaba.sdk.android.vod.upload.model.UserData;
import com.aliyun.vod.common.utils.StringUtils;
import java.io.File;

/* loaded from: classes2.dex */
public class VideoInfoUtil {
    private static final String VOD_SOURCE_SHORT_VIDEO = "short_video";

    /* JADX WARN: Multi-variable type inference failed */
    public static UserData getVideoBitrate(Context context, String str) throws Throwable {
        MediaMetadataRetriever mediaMetadataRetriever;
        UserData userData = new UserData();
        MediaMetadataRetriever mediaMetadataRetriever2 = null;
        MediaMetadataRetriever mediaMetadataRetriever3 = null;
        MediaMetadataRetriever mediaMetadataRetriever4 = null;
        try {
            try {
                MediaMetadataRetriever mediaMetadataRetriever5 = new MediaMetadataRetriever();
                try {
                    boolean zIsUriPath = StringUtils.isUriPath(str);
                    if (zIsUriPath != 0) {
                        mediaMetadataRetriever5.setDataSource(context, Uri.parse(str));
                    } else {
                        mediaMetadataRetriever5.setDataSource(new File(str).getAbsolutePath());
                    }
                    userData.setBitrate(String.valueOf(Integer.parseInt(mediaMetadataRetriever5.extractMetadata(20)) / 1024));
                    userData.setDuration(String.valueOf(Integer.parseInt(mediaMetadataRetriever5.extractMetadata(9)) / 1000));
                    userData.setFps(mediaMetadataRetriever5.extractMetadata(25));
                    userData.setWidth(mediaMetadataRetriever5.extractMetadata(18));
                    userData.setHeight(mediaMetadataRetriever5.extractMetadata(19));
                    userData.setSource(VOD_SOURCE_SHORT_VIDEO);
                    mediaMetadataRetriever5.release();
                    mediaMetadataRetriever2 = zIsUriPath;
                } catch (Error e2) {
                    e = e2;
                    mediaMetadataRetriever3 = mediaMetadataRetriever5;
                    e.printStackTrace();
                    mediaMetadataRetriever2 = mediaMetadataRetriever3;
                    if (mediaMetadataRetriever3 != null) {
                        mediaMetadataRetriever = mediaMetadataRetriever3;
                        mediaMetadataRetriever.release();
                        mediaMetadataRetriever2 = mediaMetadataRetriever;
                    }
                    return userData;
                } catch (Exception e3) {
                    e = e3;
                    mediaMetadataRetriever4 = mediaMetadataRetriever5;
                    e.printStackTrace();
                    mediaMetadataRetriever2 = mediaMetadataRetriever4;
                    mediaMetadataRetriever = mediaMetadataRetriever4;
                    if (mediaMetadataRetriever4 != null) {
                        mediaMetadataRetriever.release();
                        mediaMetadataRetriever2 = mediaMetadataRetriever;
                    }
                    return userData;
                } catch (Throwable th) {
                    th = th;
                    mediaMetadataRetriever2 = mediaMetadataRetriever5;
                    if (mediaMetadataRetriever2 != null) {
                        mediaMetadataRetriever2.release();
                    }
                    throw th;
                }
            } catch (Error e4) {
                e = e4;
            } catch (Exception e5) {
                e = e5;
            }
            return userData;
        } catch (Throwable th2) {
            th = th2;
        }
    }
}
