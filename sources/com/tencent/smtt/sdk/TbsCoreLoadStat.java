package com.tencent.smtt.sdk;

import android.content.Context;
import com.tencent.smtt.utils.TbsLog;

/* loaded from: classes6.dex */
public class TbsCoreLoadStat {
    public static final int ERROR_CODE_INIT = -1;

    /* renamed from: a, reason: collision with root package name */
    private static TbsCoreLoadStat f20914a = null;
    public static String mErrorMessage = "";
    public static volatile int mLoadErrorCode = -1;

    private TbsCoreLoadStat() {
    }

    public static TbsCoreLoadStat getInstance() {
        if (f20914a == null) {
            f20914a = new TbsCoreLoadStat();
        }
        return f20914a;
    }

    public static int getLoadErrorCode() {
        return mLoadErrorCode;
    }

    public static String getLoadErrorMessage() {
        return mErrorMessage;
    }

    public void a(Context context, int i2) {
        TbsLog.e(TbsListener.tag_load_error, "" + i2);
    }

    public synchronized void a(Context context, int i2, Throwable th) {
        String str;
        String str2;
        TbsLog.e("TbsCoreLoadStat", "[loadError] errorCode: " + i2 + ", details:" + String.valueOf(th));
        if (th != null) {
            if (mLoadErrorCode == -1) {
                mLoadErrorCode = i2;
                mErrorMessage = String.valueOf(th);
                TbsLogReport.getInstance(context).setLoadErrorCode(i2, th);
                str = "TbsCoreLoadStat";
                str2 = mLoadErrorCode + " report success!";
            } else {
                str = "TbsCoreLoadStat";
                str2 = mLoadErrorCode + " is reported, others will be saved in local TbsLog!";
            }
            TbsLog.i(str, str2);
        }
    }
}
