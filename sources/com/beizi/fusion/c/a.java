package com.beizi.fusion.c;

import android.content.Context;
import android.text.TextUtils;
import com.beizi.fusion.model.AdSpacesBean;
import com.beizi.fusion.model.Manager;
import com.beizi.fusion.model.ResponseInfo;
import java.util.List;

/* loaded from: classes2.dex */
public class a {

    /* renamed from: a, reason: collision with root package name */
    private static int f4822a;

    public static AdSpacesBean a(Context context, String str, String str2) {
        if (context != null && !TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
            ResponseInfo responseInfo = ResponseInfo.getInstance(context);
            if (!responseInfo.isInit()) {
                responseInfo.init();
            }
            Manager manager = responseInfo.getManager();
            if (manager != null) {
                List<AdSpacesBean> adSpaces = manager.getAdSpaces();
                if (adSpaces != null && adSpaces.size() > 0) {
                    for (int i2 = 0; i2 < adSpaces.size(); i2++) {
                        AdSpacesBean adSpacesBean = adSpaces.get(i2);
                        if (str.equals(adSpacesBean.getSpaceId())) {
                            return adSpacesBean;
                        }
                    }
                }
                a(2);
                return null;
            }
            a(1);
        }
        return null;
    }

    public static int a() {
        int i2 = f4822a;
        a(0);
        return i2;
    }

    public static void a(int i2) {
        f4822a = i2;
    }
}
