package com.easefun.polyv.livecommon.module.modules.interact.cardpush;

import androidx.annotation.NonNull;
import com.plv.thirdpart.blankj.utilcode.util.SPUtils;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class PLVCardLookTimeLocalRepository {
    private static final String SP_NAME = "plv_card_look_time_local_cache";
    private static final String TAG = "PLVCardLookTimeLocalRepository";

    public static int getCache(String id) {
        return SPUtils.getInstance(SP_NAME).getInt(id, 0);
    }

    @NonNull
    public static List<Integer> listCache() {
        ArrayList arrayList = new ArrayList();
        for (Object obj : SPUtils.getInstance(SP_NAME).getAll().values()) {
            if (obj instanceof Integer) {
                arrayList.add((Integer) obj);
            }
        }
        return arrayList;
    }

    public static void removeCache(String id) {
        SPUtils.getInstance(SP_NAME).remove(id);
    }

    public static void saveCache(String id, int lookTime) {
        SPUtils.getInstance(SP_NAME).put(id, lookTime);
    }
}
