package com.just.agentweb;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.webkit.WebView;
import cn.hutool.core.text.StrPool;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileLock;
import java.util.HashSet;
import java.util.Iterator;
import net.lingala.zip4j.util.InternalZipConstants;

/* loaded from: classes4.dex */
public class AgentWebCompat {
    private static void createFile(File file, boolean z2) throws IOException {
        if (z2) {
            try {
                if (file.exists()) {
                    return;
                }
                file.createNewFile();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public static void setDataDirectorySuffix(Context context) {
        StringBuilder sb;
        if (Build.VERSION.SDK_INT < 28) {
            return;
        }
        try {
            HashSet hashSet = new HashSet();
            String absolutePath = context.getDataDir().getAbsolutePath();
            String currentProcessName = ProcessUtils.getCurrentProcessName(context);
            if (TextUtils.equals(context.getPackageName(), currentProcessName)) {
                String str = StrPool.UNDERLINE + currentProcessName;
                hashSet.add(absolutePath + "/app_webview/webview_data.lock");
                hashSet.add(absolutePath + "/app_webview" + str + "/webview_data.lock");
                if (RomUtils.isHuawei()) {
                    hashSet.add(absolutePath + "/app_hws_webview/webview_data.lock");
                    sb = new StringBuilder();
                    sb.append(absolutePath);
                    sb.append("/app_hws_webview");
                    sb.append(str);
                    sb.append("/webview_data.lock");
                    hashSet.add(sb.toString());
                }
            } else {
                if (TextUtils.isEmpty(currentProcessName)) {
                    currentProcessName = context.getPackageName();
                }
                WebView.setDataDirectorySuffix(currentProcessName);
                String str2 = StrPool.UNDERLINE + currentProcessName;
                hashSet.add(absolutePath + "/app_webview" + str2 + "/webview_data.lock");
                if (RomUtils.isHuawei()) {
                    sb = new StringBuilder();
                    sb.append(absolutePath);
                    sb.append("/app_hws_webview");
                    sb.append(str2);
                    sb.append("/webview_data.lock");
                    hashSet.add(sb.toString());
                }
            }
            Iterator it = hashSet.iterator();
            while (it.hasNext()) {
                File file = new File((String) it.next());
                if (file.exists()) {
                    tryLockOrRecreateFile(file);
                    return;
                }
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    @TargetApi(28)
    private static void tryLockOrRecreateFile(File file) throws IOException {
        try {
            FileLock fileLockTryLock = new RandomAccessFile(file, InternalZipConstants.WRITE_MODE).getChannel().tryLock();
            if (fileLockTryLock != null) {
                fileLockTryLock.close();
            } else {
                createFile(file, file.delete());
            }
        } catch (Exception e2) {
            e2.printStackTrace();
            createFile(file, file.exists() ? file.delete() : false);
        }
    }
}
