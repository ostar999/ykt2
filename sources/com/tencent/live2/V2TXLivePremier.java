package com.tencent.live2;

import android.content.Context;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.util.TXCCommonUtil;
import com.tencent.live2.V2TXLiveDef;
import com.tencent.rtmp.TXLiveBase;
import com.tencent.rtmp.TXLiveBaseListener;

/* loaded from: classes6.dex */
public class V2TXLivePremier {
    private static b sObserver;

    public static class a extends TXLiveBaseListener {
        private a() {
        }

        @Override // com.tencent.rtmp.TXLiveBaseListener
        public void onLicenceLoaded(int i2, String str) {
            b bVar = V2TXLivePremier.sObserver;
            if (bVar != null) {
                bVar.b(i2, str);
            }
        }

        @Override // com.tencent.rtmp.TXLiveBaseListener
        public void onLog(int i2, String str, String str2) {
            b bVar = V2TXLivePremier.sObserver;
            if (bVar != null) {
                bVar.a(i2, str2);
            }
        }
    }

    public static abstract class b {
        public void a(int i2, String str) {
        }

        public void b(int i2, String str) {
        }
    }

    public static String getSDKVersionStr() {
        return TXLiveBase.getSDKVersionStr();
    }

    public static void setEnvironment(String str) {
        TXLiveBase.getInstance();
        TXLiveBase.setGlobalEnv(str);
    }

    public static void setLicence(Context context, String str, String str2) {
        TXLiveBase.getInstance().setLicence(context, str, str2);
    }

    public static void setLogConfig(V2TXLiveDef.V2TXLiveLogConfig v2TXLiveLogConfig) {
        if (v2TXLiveLogConfig == null) {
            return;
        }
        TXLiveBase.getInstance();
        TXLiveBase.setConsoleEnabled(v2TXLiveLogConfig.enableConsole);
        TXLiveBase.getInstance();
        TXLiveBase.setLogLevel(v2TXLiveLogConfig.logLevel);
        TXCLog.setLogToFileEnabled(v2TXLiveLogConfig.enableLogFile);
        TXCLog.setLogDirPath(v2TXLiveLogConfig.logPath);
        if (v2TXLiveLogConfig.enableObserver) {
            TXLiveBase.setListener(new a());
        } else {
            TXLiveBase.setListener(null);
        }
    }

    public static void setObserver(b bVar) {
        sObserver = bVar;
    }

    public static void setSocks5Proxy(String str, int i2, String str2, String str3) {
        TXCCommonUtil.setSocks5Proxy(str, i2, str2, str3);
    }
}
