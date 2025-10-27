package com.hyphenate.cloud;

import android.text.format.Time;
import cn.hutool.core.text.StrPool;
import java.util.Map;
import java.util.Properties;

/* loaded from: classes4.dex */
public abstract class CloudFileManager {
    protected static final String TAG = "CloudFileManager";
    public static CloudFileManager instance;
    protected Properties sessionContext;

    public abstract void downloadFile(String str, String str2, String str3, Map<String, String> map, CloudOperationCallback cloudOperationCallback);

    public String getRemoteFileName(String str, String str2) {
        Time time = new Time();
        time.setToNow();
        String strSubstring = str2.substring(str2.lastIndexOf(StrPool.DOT), str2.length());
        return (str + time.toString().substring(0, 15)) + strSubstring;
    }

    public abstract void uploadFileInBackground(String str, String str2, String str3, String str4, Map<String, String> map, CloudOperationCallback cloudOperationCallback);
}
