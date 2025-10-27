package com.luck.picture.lib.loader;

import android.content.Context;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.entity.LocalMediaFolder;
import com.luck.picture.lib.utils.SortUtils;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

/* loaded from: classes4.dex */
public final class SandboxFileLoader {
    /* JADX WARN: Removed duplicated region for block: B:32:0x0076  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.util.ArrayList<com.luck.picture.lib.entity.LocalMedia> loadInAppSandboxFile(android.content.Context r29, java.lang.String r30) throws java.security.NoSuchAlgorithmException, java.io.IOException {
        /*
            Method dump skipped, instructions count: 372
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.luck.picture.lib.loader.SandboxFileLoader.loadInAppSandboxFile(android.content.Context, java.lang.String):java.util.ArrayList");
    }

    public static LocalMediaFolder loadInAppSandboxFolderFile(Context context, String str) throws NoSuchAlgorithmException, IOException {
        ArrayList<LocalMedia> arrayListLoadInAppSandboxFile = loadInAppSandboxFile(context, str);
        if (arrayListLoadInAppSandboxFile == null || arrayListLoadInAppSandboxFile.size() <= 0) {
            return null;
        }
        SortUtils.sortLocalMediaAddedTime(arrayListLoadInAppSandboxFile);
        LocalMedia localMedia = arrayListLoadInAppSandboxFile.get(0);
        LocalMediaFolder localMediaFolder = new LocalMediaFolder();
        localMediaFolder.setFolderName(localMedia.getParentFolderName());
        localMediaFolder.setFirstImagePath(localMedia.getPath());
        localMediaFolder.setFirstMimeType(localMedia.getMimeType());
        localMediaFolder.setBucketId(localMedia.getBucketId());
        localMediaFolder.setFolderTotalNum(arrayListLoadInAppSandboxFile.size());
        localMediaFolder.setData(arrayListLoadInAppSandboxFile);
        return localMediaFolder;
    }
}
