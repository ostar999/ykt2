package com.beizi.ad.internal.utilities;

import android.text.TextUtils;
import com.beizi.ad.a.a.c;
import com.beizi.ad.a.a.i;
import java.util.List;

/* loaded from: classes2.dex */
public class ReportEventUtil {
    private static final String TAG = "ReportEventUtil";

    public static void report(List<String> list) {
        if (list == null || list.size() <= 0) {
            return;
        }
        for (int i2 = 0; i2 < list.size(); i2++) {
            String str = list.get(i2);
            i.a(TAG, "ReportEventUtil:" + str);
            if (!TextUtils.isEmpty(str)) {
                new com.beizi.ad.internal.i(UrlUtil.replaceToTouchEventUrl(str, "", "", "", "", "", "", "")).executeOnExecutor(c.b().d(), new Void[0]);
            }
        }
    }
}
