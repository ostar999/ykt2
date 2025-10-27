package com.luck.picture.lib.loader;

import android.content.Context;
import android.net.Uri;
import android.provider.MediaStore;
import com.luck.picture.lib.config.PictureSelectionConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.entity.LocalMediaFolder;
import com.luck.picture.lib.interfaces.OnQueryAlbumListener;
import com.luck.picture.lib.interfaces.OnQueryAllAlbumListener;
import com.luck.picture.lib.interfaces.OnQueryDataResultListener;
import com.umeng.analytics.pro.aq;
import java.util.Locale;

/* loaded from: classes4.dex */
public class IBridgeMediaLoader {
    protected static final String COLUMN_COUNT = "count";
    protected static final String COLUMN_DURATION = "duration";
    protected static final String GROUP_BY_BUCKET_Id = " GROUP BY (bucket_id";
    protected static final int MAX_SORT_SIZE = 60;
    protected static final String NOT_GIF = " AND (mime_type!='image/gif' AND mime_type!='image/*')";
    protected static final String NOT_GIF_UNKNOWN = "!='image/*'";
    protected static final String ORDER_BY = "date_modified DESC";
    protected static final String TAG = "IBridgeMediaLoader";
    protected PictureSelectionConfig config;
    protected Context mContext;
    protected static final Uri QUERY_URI = MediaStore.Files.getContentUri("external");
    protected static final String COLUMN_BUCKET_DISPLAY_NAME = "bucket_display_name";
    protected static final String COLUMN_BUCKET_ID = "bucket_id";
    protected static final String[] PROJECTION = {aq.f22519d, "_data", "mime_type", "width", "height", "duration", "_size", COLUMN_BUCKET_DISPLAY_NAME, "_display_name", COLUMN_BUCKET_ID, "date_added"};

    public static String[] getSelectionArgsForAllMediaType() {
        return new String[]{String.valueOf(1), String.valueOf(3)};
    }

    public static String[] getSelectionArgsForSingleMediaType(int i2) {
        return new String[]{String.valueOf(i2)};
    }

    public String getDurationCondition() {
        int i2 = this.config.filterVideoMaxSecond;
        long j2 = i2 == 0 ? Long.MAX_VALUE : i2;
        Locale locale = Locale.CHINA;
        Object[] objArr = new Object[3];
        objArr[0] = Long.valueOf(Math.max(0L, r0.filterVideoMinSecond));
        objArr[1] = Math.max(0L, (long) this.config.filterVideoMinSecond) == 0 ? "" : "=";
        objArr[2] = Long.valueOf(j2);
        return String.format(locale, "%d <%s duration and duration <= %d", objArr);
    }

    public String getFileSizeCondition() {
        PictureSelectionConfig pictureSelectionConfig = this.config;
        long j2 = pictureSelectionConfig.filterMaxFileSize;
        if (j2 == 0) {
            j2 = Long.MAX_VALUE;
        }
        Locale locale = Locale.CHINA;
        Object[] objArr = new Object[3];
        objArr[0] = Long.valueOf(Math.max(0L, pictureSelectionConfig.filterMinFileSize));
        objArr[1] = Math.max(0L, this.config.filterMinFileSize) == 0 ? "" : "=";
        objArr[2] = Long.valueOf(j2);
        return String.format(locale, "%d <%s _size and _size <= %d", objArr);
    }

    public String getFirstCover(long j2) {
        return null;
    }

    /* JADX WARN: Removed duplicated region for block: B:31:0x0075  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0078  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.String getQueryMimeCondition() {
        /*
            r10 = this;
            com.luck.picture.lib.config.PictureSelectionConfig r0 = r10.config
            java.util.List<java.lang.String> r0 = r0.queryOnlyList
            java.util.HashSet r1 = new java.util.HashSet
            r1.<init>(r0)
            java.util.Iterator r0 = r1.iterator()
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            r3 = -1
        L13:
            boolean r4 = r0.hasNext()
            if (r4 == 0) goto L90
            java.lang.Object r4 = r0.next()
            java.lang.String r4 = (java.lang.String) r4
            boolean r5 = android.text.TextUtils.isEmpty(r4)
            if (r5 == 0) goto L26
            goto L13
        L26:
            com.luck.picture.lib.config.PictureSelectionConfig r5 = r10.config
            int r5 = r5.chooseMode
            int r6 = com.luck.picture.lib.config.SelectMimeType.ofVideo()
            java.lang.String r7 = "audio"
            java.lang.String r8 = "image"
            if (r5 != r6) goto L41
            boolean r5 = r4.startsWith(r8)
            if (r5 != 0) goto L13
            boolean r5 = r4.startsWith(r7)
            if (r5 == 0) goto L71
            goto L13
        L41:
            com.luck.picture.lib.config.PictureSelectionConfig r5 = r10.config
            int r5 = r5.chooseMode
            int r6 = com.luck.picture.lib.config.SelectMimeType.ofImage()
            java.lang.String r9 = "video"
            if (r5 != r6) goto L5a
            boolean r5 = r4.startsWith(r7)
            if (r5 != 0) goto L13
            boolean r5 = r4.startsWith(r9)
            if (r5 == 0) goto L71
            goto L13
        L5a:
            com.luck.picture.lib.config.PictureSelectionConfig r5 = r10.config
            int r5 = r5.chooseMode
            int r6 = com.luck.picture.lib.config.SelectMimeType.ofAudio()
            if (r5 != r6) goto L71
            boolean r5 = r4.startsWith(r9)
            if (r5 != 0) goto L13
            boolean r5 = r4.startsWith(r8)
            if (r5 == 0) goto L71
            goto L13
        L71:
            int r3 = r3 + 1
            if (r3 != 0) goto L78
            java.lang.String r5 = " AND "
            goto L7a
        L78:
            java.lang.String r5 = " OR "
        L7a:
            r2.append(r5)
            java.lang.String r5 = "mime_type"
            r2.append(r5)
            java.lang.String r5 = "='"
            r2.append(r5)
            r2.append(r4)
            java.lang.String r4 = "'"
            r2.append(r4)
            goto L13
        L90:
            com.luck.picture.lib.config.PictureSelectionConfig r0 = r10.config
            int r0 = r0.chooseMode
            int r3 = com.luck.picture.lib.config.SelectMimeType.ofVideo()
            if (r0 == r3) goto Laf
            com.luck.picture.lib.config.PictureSelectionConfig r0 = r10.config
            boolean r0 = r0.isGif
            if (r0 != 0) goto Laf
            java.lang.String r0 = com.luck.picture.lib.config.PictureMimeType.ofGIF()
            boolean r0 = r1.contains(r0)
            if (r0 != 0) goto Laf
            java.lang.String r0 = " AND (mime_type!='image/gif' AND mime_type!='image/*')"
            r2.append(r0)
        Laf:
            java.lang.String r0 = r2.toString()
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.luck.picture.lib.loader.IBridgeMediaLoader.getQueryMimeCondition():java.lang.String");
    }

    public void loadAllMedia(OnQueryAllAlbumListener<LocalMediaFolder> onQueryAllAlbumListener) {
    }

    public void loadFirstPageMedia(long j2, int i2, OnQueryDataResultListener<LocalMedia> onQueryDataResultListener) {
    }

    public void loadOnlyInAppDirAllMedia(OnQueryAlbumListener<LocalMediaFolder> onQueryAlbumListener) {
    }

    public void loadPageMediaData(long j2, int i2, int i3, int i4, OnQueryDataResultListener<LocalMedia> onQueryDataResultListener) {
    }

    public void loadPageMediaData(long j2, int i2, int i3, OnQueryDataResultListener<LocalMedia> onQueryDataResultListener) {
    }
}
