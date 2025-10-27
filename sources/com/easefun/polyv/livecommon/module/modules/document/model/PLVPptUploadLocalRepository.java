package com.easefun.polyv.livecommon.module.modules.document.model;

import android.text.TextUtils;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.easefun.polyv.livecommon.module.modules.document.model.vo.PLVPptUploadLocalCacheVO;
import com.plv.thirdpart.blankj.utilcode.util.SPUtils;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class PLVPptUploadLocalRepository {
    private static final String SP_NAME = "polyv_ppt_upload_local_cache";
    private static final String TAG = "PLVPptUploadLocalRepository";

    @Nullable
    public PLVPptUploadLocalCacheVO getCache(String fileId) {
        String string = SPUtils.getInstance(SP_NAME).getString(fileId);
        if (TextUtils.isEmpty(string)) {
            return null;
        }
        return PLVPptUploadLocalCacheVO.Serializer.fromJson(string);
    }

    @NonNull
    public List<PLVPptUploadLocalCacheVO> listCache() {
        PLVPptUploadLocalCacheVO pLVPptUploadLocalCacheVOFromJson;
        ArrayList arrayList = new ArrayList();
        for (Object obj : SPUtils.getInstance(SP_NAME).getAll().values()) {
            if ((obj instanceof String) && (pLVPptUploadLocalCacheVOFromJson = PLVPptUploadLocalCacheVO.Serializer.fromJson((String) obj)) != null) {
                arrayList.add(pLVPptUploadLocalCacheVOFromJson);
            }
        }
        return arrayList;
    }

    public void removeCache(String fileId) {
        if (TextUtils.isEmpty(fileId)) {
            Log.w(TAG, "file id is empty.");
        } else {
            SPUtils.getInstance(SP_NAME).remove(fileId);
        }
    }

    public void saveCache(PLVPptUploadLocalCacheVO vo) {
        if (vo == null || TextUtils.isEmpty(vo.getFileId())) {
            Log.w(TAG, "file id is empty.");
        } else {
            SPUtils.getInstance(SP_NAME).put(vo.getFileId(), PLVPptUploadLocalCacheVO.Serializer.toJson(vo));
        }
    }
}
