package com.heytap.mcssdk;

import android.content.Context;
import android.content.Intent;
import com.heytap.mcssdk.utils.Utils;
import com.heytap.mcssdk.utils.d;
import com.heytap.mcssdk.utils.f;
import com.heytap.msp.push.callback.IDataMessageCallBackService;
import com.heytap.msp.push.mode.BaseMode;
import java.util.List;

/* loaded from: classes4.dex */
public class b {
    public static void a(final Context context, final Intent intent, final IDataMessageCallBackService iDataMessageCallBackService) {
        if (context == null) {
            d.e("context is null , please check param of parseIntent()");
            return;
        }
        if (intent == null) {
            d.e("intent is null , please check param of parseIntent()");
            return;
        }
        if (iDataMessageCallBackService == null) {
            d.e("callback is null , please check param of parseIntent()");
        } else if (Utils.isSupportPushByClient(context)) {
            f.a(new Runnable() { // from class: com.heytap.mcssdk.b.1
                @Override // java.lang.Runnable
                public void run() throws NumberFormatException {
                    List<BaseMode> listA = com.heytap.mcssdk.d.c.a(context, intent);
                    if (listA == null) {
                        return;
                    }
                    for (BaseMode baseMode : listA) {
                        if (baseMode != null) {
                            for (com.heytap.mcssdk.e.c cVar : PushService.getInstance().getProcessors()) {
                                if (cVar != null) {
                                    cVar.a(context, baseMode, iDataMessageCallBackService);
                                }
                            }
                        }
                    }
                }
            });
        } else {
            d.e("push is null ,please check system has push");
        }
    }
}
