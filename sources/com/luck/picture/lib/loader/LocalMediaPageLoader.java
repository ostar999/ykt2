package com.luck.picture.lib.loader;

import android.content.Context;
import android.database.Cursor;
import android.text.TextUtils;
import com.luck.picture.lib.config.PictureSelectionConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.entity.LocalMediaFolder;
import com.luck.picture.lib.entity.MediaData;
import com.luck.picture.lib.interfaces.OnQueryAlbumListener;
import com.luck.picture.lib.interfaces.OnQueryAllAlbumListener;
import com.luck.picture.lib.interfaces.OnQueryDataResultListener;
import com.luck.picture.lib.thread.PictureThreadUtils;
import com.luck.picture.lib.utils.MediaUtils;
import com.luck.picture.lib.utils.SdkVersionUtils;
import com.luck.picture.lib.utils.ValueOf;
import com.umeng.analytics.pro.aq;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public final class LocalMediaPageLoader extends IBridgeMediaLoader {
    private static final String[] PROJECTION_29 = {aq.f22519d, "bucket_id", "bucket_display_name", "mime_type"};
    private static final String[] ALL_PROJECTION = {aq.f22519d, "_data", "bucket_id", "bucket_display_name", "mime_type", "COUNT(*) AS count"};

    public LocalMediaPageLoader(Context context, PictureSelectionConfig pictureSelectionConfig) {
        this.mContext = context;
        this.config = pictureSelectionConfig;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String getFirstCoverMimeType(Cursor cursor) {
        return cursor.getString(cursor.getColumnIndexOrThrow("mime_type"));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String getFirstUri(Cursor cursor) {
        return MediaUtils.getRealPathUri(cursor.getLong(cursor.getColumnIndexOrThrow(aq.f22519d)), cursor.getString(cursor.getColumnIndexOrThrow("mime_type")));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String getFirstUrl(Cursor cursor) {
        return cursor.getString(cursor.getColumnIndexOrThrow("_data"));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String getPageSelection(long j2) {
        String durationCondition = getDurationCondition();
        String fileSizeCondition = getFileSizeCondition();
        String queryMimeCondition = getQueryMimeCondition();
        int i2 = this.config.chooseMode;
        if (i2 == 0) {
            return getPageSelectionArgsForAllMediaCondition(j2, queryMimeCondition, durationCondition, fileSizeCondition);
        }
        if (i2 == 1) {
            return getPageSelectionArgsForImageMediaCondition(j2, queryMimeCondition, fileSizeCondition);
        }
        if (i2 == 2 || i2 == 3) {
            return getPageSelectionArgsForVideoOrAudioMediaCondition(j2, queryMimeCondition, durationCondition, fileSizeCondition);
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String[] getPageSelectionArgs(long j2) {
        int i2 = this.config.chooseMode;
        if (i2 == 0) {
            return j2 == -1 ? new String[]{String.valueOf(1), String.valueOf(3)} : new String[]{String.valueOf(1), String.valueOf(3), ValueOf.toString(Long.valueOf(j2))};
        }
        if (i2 == 1) {
            return getSelectionArgsForPageSingleMediaType(1, j2);
        }
        if (i2 == 2) {
            return getSelectionArgsForPageSingleMediaType(3, j2);
        }
        if (i2 != 3) {
            return null;
        }
        return getSelectionArgsForPageSingleMediaType(2, j2);
    }

    private static String getPageSelectionArgsForAllMediaCondition(long j2, String str, String str2, String str3) {
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        sb.append("media_type");
        sb.append("=?");
        sb.append(str);
        sb.append(" OR ");
        sb.append("media_type");
        sb.append("=? AND ");
        sb.append(str2);
        sb.append(") AND ");
        if (j2 == -1) {
            sb.append(str3);
            return sb.toString();
        }
        sb.append("bucket_id");
        sb.append("=? AND ");
        sb.append(str3);
        return sb.toString();
    }

    private static String getPageSelectionArgsForImageMediaCondition(long j2, String str, String str2) {
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        sb.append("media_type");
        sb.append("=?");
        if (j2 == -1) {
            sb.append(str);
            sb.append(") AND ");
            sb.append(str2);
            return sb.toString();
        }
        sb.append(str);
        sb.append(") AND ");
        sb.append("bucket_id");
        sb.append("=? AND ");
        sb.append(str2);
        return sb.toString();
    }

    private static String getPageSelectionArgsForVideoOrAudioMediaCondition(long j2, String str, String str2, String str3) {
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        sb.append("media_type");
        sb.append("=?");
        sb.append(str);
        sb.append(" AND ");
        sb.append(str2);
        sb.append(") AND ");
        if (j2 == -1) {
            sb.append(str3);
            return sb.toString();
        }
        sb.append("bucket_id");
        sb.append("=? AND ");
        sb.append(str3);
        return sb.toString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String getSelection() {
        String fileSizeCondition = getFileSizeCondition();
        String queryMimeCondition = getQueryMimeCondition();
        int i2 = this.config.chooseMode;
        if (i2 == 0) {
            return getSelectionArgsForAllMediaCondition(getDurationCondition(), fileSizeCondition, queryMimeCondition);
        }
        if (i2 == 1) {
            return getSelectionArgsForImageMediaCondition(queryMimeCondition, fileSizeCondition);
        }
        if (i2 == 2 || i2 == 3) {
            return getSelectionArgsForVideoOrAudioMediaCondition(queryMimeCondition, fileSizeCondition);
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String[] getSelectionArgs() {
        int i2 = this.config.chooseMode;
        if (i2 == 0) {
            return IBridgeMediaLoader.getSelectionArgsForAllMediaType();
        }
        if (i2 == 1) {
            return IBridgeMediaLoader.getSelectionArgsForSingleMediaType(1);
        }
        if (i2 == 2) {
            return IBridgeMediaLoader.getSelectionArgsForSingleMediaType(3);
        }
        if (i2 != 3) {
            return null;
        }
        return IBridgeMediaLoader.getSelectionArgsForSingleMediaType(2);
    }

    private static String getSelectionArgsForAllMediaCondition(String str, String str2, String str3) {
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        sb.append("media_type");
        sb.append("=?");
        sb.append(str3);
        sb.append(" OR ");
        sb.append("media_type");
        sb.append("=? AND ");
        sb.append(str);
        sb.append(") AND ");
        sb.append(str2);
        if (SdkVersionUtils.isQ()) {
            return sb.toString();
        }
        sb.append(")");
        sb.append(" GROUP BY (bucket_id");
        return sb.toString();
    }

    private static String getSelectionArgsForImageMediaCondition(String str, String str2) {
        StringBuilder sb = new StringBuilder();
        if (SdkVersionUtils.isQ()) {
            sb.append("media_type");
            sb.append("=?");
            sb.append(str);
            sb.append(" AND ");
            sb.append(str2);
            return sb.toString();
        }
        sb.append("(");
        sb.append("media_type");
        sb.append("=?");
        sb.append(str);
        sb.append(") AND ");
        sb.append(str2);
        sb.append(")");
        sb.append(" GROUP BY (bucket_id");
        return sb.toString();
    }

    private static String[] getSelectionArgsForPageSingleMediaType(int i2, long j2) {
        return j2 == -1 ? new String[]{String.valueOf(i2)} : new String[]{String.valueOf(i2), ValueOf.toString(Long.valueOf(j2))};
    }

    private static String getSelectionArgsForVideoOrAudioMediaCondition(String str, String str2) {
        StringBuilder sb = new StringBuilder();
        if (SdkVersionUtils.isQ()) {
            sb.append("media_type");
            sb.append("=?");
            sb.append(str);
            sb.append(" AND ");
            sb.append(str2);
            return sb.toString();
        }
        sb.append("(");
        sb.append("media_type");
        sb.append("=?");
        sb.append(str);
        sb.append(") AND ");
        sb.append(str2);
        sb.append(")");
        sb.append(" GROUP BY (bucket_id");
        return sb.toString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void synchronousFirstCover(List<LocalMediaFolder> list) throws Throwable {
        for (int i2 = 0; i2 < list.size(); i2++) {
            LocalMediaFolder localMediaFolder = list.get(i2);
            if (localMediaFolder != null) {
                String firstCover = getFirstCover(localMediaFolder.getBucketId());
                if (!TextUtils.isEmpty(firstCover)) {
                    localMediaFolder.setFirstImagePath(firstCover);
                }
            }
        }
    }

    @Override // com.luck.picture.lib.loader.IBridgeMediaLoader
    public String getFirstCover(long j2) throws Throwable {
        Cursor cursor;
        Cursor cursor2 = null;
        try {
            Cursor cursorQuery = SdkVersionUtils.isR() ? this.mContext.getContentResolver().query(IBridgeMediaLoader.QUERY_URI, new String[]{aq.f22519d, "mime_type", "_data"}, MediaUtils.createQueryArgsBundle(getPageSelection(j2), getPageSelectionArgs(j2), 1, 0), null) : this.mContext.getContentResolver().query(IBridgeMediaLoader.QUERY_URI, new String[]{aq.f22519d, "mime_type", "_data"}, getPageSelection(j2), getPageSelectionArgs(j2), "_id DESC limit 1 offset 0");
            if (cursorQuery != null) {
                try {
                    if (cursorQuery.getCount() > 0) {
                        if (!cursorQuery.moveToFirst()) {
                            if (!cursorQuery.isClosed()) {
                                cursorQuery.close();
                            }
                            return null;
                        }
                        String realPathUri = SdkVersionUtils.isQ() ? MediaUtils.getRealPathUri(cursorQuery.getLong(cursorQuery.getColumnIndexOrThrow(aq.f22519d)), cursorQuery.getString(cursorQuery.getColumnIndexOrThrow("mime_type"))) : cursorQuery.getString(cursorQuery.getColumnIndexOrThrow("_data"));
                        if (!cursorQuery.isClosed()) {
                            cursorQuery.close();
                        }
                        return realPathUri;
                    }
                } catch (Exception e2) {
                    cursor = cursorQuery;
                    e = e2;
                    try {
                        e.printStackTrace();
                        if (cursor != null && !cursor.isClosed()) {
                            cursor.close();
                        }
                        return null;
                    } catch (Throwable th) {
                        th = th;
                        cursor2 = cursor;
                        if (cursor2 != null && !cursor2.isClosed()) {
                            cursor2.close();
                        }
                        throw th;
                    }
                } catch (Throwable th2) {
                    cursor2 = cursorQuery;
                    th = th2;
                    if (cursor2 != null) {
                        cursor2.close();
                    }
                    throw th;
                }
            }
            if (cursorQuery != null && !cursorQuery.isClosed()) {
                cursorQuery.close();
            }
        } catch (Exception e3) {
            e = e3;
            cursor = null;
        } catch (Throwable th3) {
            th = th3;
        }
        return null;
    }

    @Override // com.luck.picture.lib.loader.IBridgeMediaLoader
    public void loadAllMedia(final OnQueryAllAlbumListener<LocalMediaFolder> onQueryAllAlbumListener) {
        PictureThreadUtils.executeByIo(new PictureThreadUtils.SimpleTask<List<LocalMediaFolder>>() { // from class: com.luck.picture.lib.loader.LocalMediaPageLoader.3
            /* JADX WARN: Removed duplicated region for block: B:94:0x029e  */
            @Override // com.luck.picture.lib.thread.PictureThreadUtils.Task
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public java.util.List<com.luck.picture.lib.entity.LocalMediaFolder> doInBackground() throws java.lang.Throwable {
                /*
                    Method dump skipped, instructions count: 679
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException("Method not decompiled: com.luck.picture.lib.loader.LocalMediaPageLoader.AnonymousClass3.doInBackground():java.util.List");
            }

            @Override // com.luck.picture.lib.thread.PictureThreadUtils.Task
            public void onSuccess(List<LocalMediaFolder> list) {
                PictureThreadUtils.cancel(this);
                OnQueryAllAlbumListener onQueryAllAlbumListener2 = onQueryAllAlbumListener;
                if (onQueryAllAlbumListener2 != null) {
                    onQueryAllAlbumListener2.onComplete(list);
                }
            }
        });
    }

    @Override // com.luck.picture.lib.loader.IBridgeMediaLoader
    public void loadFirstPageMedia(long j2, int i2, OnQueryDataResultListener<LocalMedia> onQueryDataResultListener) {
        loadPageMediaData(j2, 1, i2, i2, onQueryDataResultListener);
    }

    @Override // com.luck.picture.lib.loader.IBridgeMediaLoader
    public void loadOnlyInAppDirAllMedia(final OnQueryAlbumListener<LocalMediaFolder> onQueryAlbumListener) {
        PictureThreadUtils.executeByIo(new PictureThreadUtils.SimpleTask<LocalMediaFolder>() { // from class: com.luck.picture.lib.loader.LocalMediaPageLoader.2
            @Override // com.luck.picture.lib.thread.PictureThreadUtils.Task
            public LocalMediaFolder doInBackground() {
                LocalMediaPageLoader localMediaPageLoader = LocalMediaPageLoader.this;
                return SandboxFileLoader.loadInAppSandboxFolderFile(localMediaPageLoader.mContext, localMediaPageLoader.config.sandboxDir);
            }

            @Override // com.luck.picture.lib.thread.PictureThreadUtils.Task
            public void onSuccess(LocalMediaFolder localMediaFolder) {
                PictureThreadUtils.cancel(this);
                OnQueryAlbumListener onQueryAlbumListener2 = onQueryAlbumListener;
                if (onQueryAlbumListener2 != null) {
                    onQueryAlbumListener2.onComplete(localMediaFolder);
                }
            }
        });
    }

    @Override // com.luck.picture.lib.loader.IBridgeMediaLoader
    public void loadPageMediaData(long j2, int i2, int i3, OnQueryDataResultListener<LocalMedia> onQueryDataResultListener) {
        loadPageMediaData(j2, i2, i3, i3, onQueryDataResultListener);
    }

    @Override // com.luck.picture.lib.loader.IBridgeMediaLoader
    public void loadPageMediaData(final long j2, final int i2, final int i3, final int i4, final OnQueryDataResultListener<LocalMedia> onQueryDataResultListener) {
        PictureThreadUtils.executeByIo(new PictureThreadUtils.SimpleTask<MediaData>() { // from class: com.luck.picture.lib.loader.LocalMediaPageLoader.1
            /* JADX WARN: Removed duplicated region for block: B:115:0x01ec A[EDGE_INSN: B:115:0x01ec->B:74:0x01ec BREAK  A[LOOP:0: B:16:0x00e7->B:75:0x01ed], SYNTHETIC] */
            /* JADX WARN: Removed duplicated region for block: B:36:0x0145 A[Catch: all -> 0x0246, Exception -> 0x0248, PHI: r7
              0x0145: PHI (r7v5 java.lang.String) = (r7v4 java.lang.String), (r7v6 java.lang.String), (r7v6 java.lang.String) binds: [B:30:0x0130, B:32:0x013c, B:34:0x0142] A[DONT_GENERATE, DONT_INLINE], TryCatch #1 {Exception -> 0x0248, blocks: (B:3:0x0005, B:5:0x000c, B:13:0x0086, B:15:0x0092, B:16:0x00e7, B:18:0x00f5, B:19:0x00f9, B:21:0x0107, B:23:0x0114, B:25:0x011c, B:72:0x01e6, B:29:0x012c, B:31:0x0132, B:33:0x013e, B:36:0x0145, B:39:0x014c, B:41:0x0154, B:44:0x015f, B:46:0x0167, B:49:0x0172, B:51:0x0194, B:71:0x01cd, B:55:0x01a2, B:57:0x01b0, B:60:0x01b7, B:62:0x01bb, B:76:0x01fb, B:78:0x0203, B:80:0x0208, B:82:0x0216, B:84:0x021e, B:88:0x0228, B:6:0x0039, B:11:0x0064, B:10:0x0042), top: B:113:0x0005, outer: #0 }] */
            /* JADX WARN: Removed duplicated region for block: B:60:0x01b7 A[Catch: all -> 0x0246, Exception -> 0x0248, TryCatch #1 {Exception -> 0x0248, blocks: (B:3:0x0005, B:5:0x000c, B:13:0x0086, B:15:0x0092, B:16:0x00e7, B:18:0x00f5, B:19:0x00f9, B:21:0x0107, B:23:0x0114, B:25:0x011c, B:72:0x01e6, B:29:0x012c, B:31:0x0132, B:33:0x013e, B:36:0x0145, B:39:0x014c, B:41:0x0154, B:44:0x015f, B:46:0x0167, B:49:0x0172, B:51:0x0194, B:71:0x01cd, B:55:0x01a2, B:57:0x01b0, B:60:0x01b7, B:62:0x01bb, B:76:0x01fb, B:78:0x0203, B:80:0x0208, B:82:0x0216, B:84:0x021e, B:88:0x0228, B:6:0x0039, B:11:0x0064, B:10:0x0042), top: B:113:0x0005, outer: #0 }] */
            /* JADX WARN: Removed duplicated region for block: B:75:0x01ed A[LOOP:0: B:16:0x00e7->B:75:0x01ed, LOOP_END] */
            @Override // com.luck.picture.lib.thread.PictureThreadUtils.Task
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public com.luck.picture.lib.entity.MediaData doInBackground() {
                /*
                    Method dump skipped, instructions count: 643
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException("Method not decompiled: com.luck.picture.lib.loader.LocalMediaPageLoader.AnonymousClass1.doInBackground():com.luck.picture.lib.entity.MediaData");
            }

            @Override // com.luck.picture.lib.thread.PictureThreadUtils.Task
            public void onSuccess(MediaData mediaData) {
                PictureThreadUtils.cancel(this);
                OnQueryDataResultListener onQueryDataResultListener2 = onQueryDataResultListener;
                if (onQueryDataResultListener2 != null) {
                    ArrayList<LocalMedia> arrayList = mediaData.data;
                    if (arrayList == null) {
                        arrayList = new ArrayList<>();
                    }
                    onQueryDataResultListener2.onComplete(arrayList, mediaData.isHasNextMore);
                }
            }
        });
    }
}
