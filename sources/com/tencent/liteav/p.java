package com.tencent.liteav;

import android.content.Context;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.live2.V2TXLivePlayer;
import com.tencent.live2.impl.a;

/* loaded from: classes6.dex */
public class p {
    public static n a(Context context, int i2) {
        if (i2 != 2 && i2 != 4 && i2 != 4 && i2 != 6 && i2 != 3) {
            return new f(context);
        }
        TXCLog.e("TXSDKUtil", "create player error not support type : " + i2);
        return null;
    }

    public static String a() {
        return "";
    }

    public static boolean a(String str) {
        return false;
    }

    public static V2TXLivePlayer a(Context context, V2TXLivePlayer v2TXLivePlayer, a.c cVar) {
        if (cVar == a.c.V2TXLiveProtocolTypeRTMP) {
            return new com.tencent.live2.a.a(v2TXLivePlayer, context);
        }
        TXCLog.e("TXSDKUtil", "createV2Player error not support type : " + cVar);
        return null;
    }
}
