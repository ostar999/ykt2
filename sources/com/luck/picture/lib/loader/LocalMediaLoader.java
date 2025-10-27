package com.luck.picture.lib.loader;

import android.content.Context;
import android.text.TextUtils;
import com.luck.picture.lib.config.PictureSelectionConfig;
import com.luck.picture.lib.entity.LocalMediaFolder;
import com.luck.picture.lib.interfaces.OnQueryAlbumListener;
import com.luck.picture.lib.interfaces.OnQueryAllAlbumListener;
import com.luck.picture.lib.thread.PictureThreadUtils;
import java.util.List;

/* loaded from: classes4.dex */
public final class LocalMediaLoader extends IBridgeMediaLoader {
    public LocalMediaLoader(Context context, PictureSelectionConfig pictureSelectionConfig) {
        this.mContext = context;
        this.config = pictureSelectionConfig;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public LocalMediaFolder getImageFolder(String str, String str2, String str3, List<LocalMediaFolder> list) {
        for (LocalMediaFolder localMediaFolder : list) {
            String folderName = localMediaFolder.getFolderName();
            if (!TextUtils.isEmpty(folderName) && TextUtils.equals(folderName, str3)) {
                return localMediaFolder;
            }
        }
        LocalMediaFolder localMediaFolder2 = new LocalMediaFolder();
        localMediaFolder2.setFolderName(str3);
        localMediaFolder2.setFirstImagePath(str);
        localMediaFolder2.setFirstMimeType(str2);
        list.add(localMediaFolder2);
        return localMediaFolder2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String getSelection() {
        String durationCondition = getDurationCondition();
        String fileSizeCondition = getFileSizeCondition();
        String queryMimeCondition = getQueryMimeCondition();
        int i2 = this.config.chooseMode;
        if (i2 == 0) {
            return getSelectionArgsForAllMediaCondition(durationCondition, fileSizeCondition, queryMimeCondition);
        }
        if (i2 == 1) {
            return getSelectionArgsForImageMediaCondition(fileSizeCondition, queryMimeCondition);
        }
        if (i2 == 2) {
            return getSelectionArgsForVideoOrAudioMediaCondition(fileSizeCondition, queryMimeCondition);
        }
        if (i2 != 3) {
            return null;
        }
        return getSelectionArgsForVideoOrAudioMediaCondition(durationCondition, queryMimeCondition);
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
        return "(media_type=?" + str3 + " OR media_type=? AND " + str + ") AND " + str2;
    }

    private static String getSelectionArgsForImageMediaCondition(String str, String str2) {
        return "media_type=?" + str2 + " AND " + str;
    }

    private static String getSelectionArgsForVideoOrAudioMediaCondition(String str, String str2) {
        return "media_type=?" + str2 + " AND " + str;
    }

    @Override // com.luck.picture.lib.loader.IBridgeMediaLoader
    public void loadAllMedia(final OnQueryAllAlbumListener<LocalMediaFolder> onQueryAllAlbumListener) {
        PictureThreadUtils.executeByIo(new PictureThreadUtils.SimpleTask<List<LocalMediaFolder>>() { // from class: com.luck.picture.lib.loader.LocalMediaLoader.2
            /* JADX WARN: Removed duplicated region for block: B:101:0x029f A[DONT_GENERATE, PHI: r3
              0x029f: PHI (r3v13 java.util.ArrayList) = (r3v4 java.util.ArrayList), (r3v14 java.util.ArrayList) binds: [B:91:0x0289, B:100:0x029d] A[DONT_GENERATE, DONT_INLINE]] */
            /* JADX WARN: Removed duplicated region for block: B:110:0x01cb A[EDGE_INSN: B:110:0x01cb->B:64:0x01cb BREAK  A[LOOP:0: B:105:0x0094->B:80:0x0264], SYNTHETIC] */
            /* JADX WARN: Removed duplicated region for block: B:53:0x0169  */
            /* JADX WARN: Removed duplicated region for block: B:80:0x0264 A[LOOP:0: B:105:0x0094->B:80:0x0264, LOOP_END] */
            /* JADX WARN: Removed duplicated region for block: B:97:0x0296  */
            @Override // com.luck.picture.lib.thread.PictureThreadUtils.Task
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public java.util.List<com.luck.picture.lib.entity.LocalMediaFolder> doInBackground() {
                /*
                    Method dump skipped, instructions count: 675
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException("Method not decompiled: com.luck.picture.lib.loader.LocalMediaLoader.AnonymousClass2.doInBackground():java.util.List");
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
    public void loadOnlyInAppDirAllMedia(final OnQueryAlbumListener<LocalMediaFolder> onQueryAlbumListener) {
        PictureThreadUtils.executeByIo(new PictureThreadUtils.SimpleTask<LocalMediaFolder>() { // from class: com.luck.picture.lib.loader.LocalMediaLoader.1
            @Override // com.luck.picture.lib.thread.PictureThreadUtils.Task
            public LocalMediaFolder doInBackground() {
                LocalMediaLoader localMediaLoader = LocalMediaLoader.this;
                return SandboxFileLoader.loadInAppSandboxFolderFile(localMediaLoader.mContext, localMediaLoader.config.sandboxDir);
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
}
