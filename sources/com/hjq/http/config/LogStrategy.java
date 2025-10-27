package com.hjq.http.config;

import android.text.TextUtils;
import android.util.Log;
import com.hjq.http.EasyConfig;
import com.hjq.http.EasyHttp;
import com.yikaobang.yixue.R2;
import x0.a;

/* loaded from: classes4.dex */
public final class LogStrategy implements ILogStrategy {
    @Override // com.hjq.http.config.ILogStrategy
    public void json(String str) {
        String strB = a.b(str);
        if (TextUtils.isEmpty(strB)) {
            return;
        }
        String strReplace = " \n" + strB;
        if (strReplace.length() <= R2.attr.search) {
            print(strReplace);
            return;
        }
        while (strReplace.length() > 3072) {
            String strSubstring = strReplace.substring(0, R2.attr.search);
            strReplace = strReplace.replace(strSubstring, "");
            print(strSubstring);
        }
        print(strReplace);
    }

    @Override // com.hjq.http.config.ILogStrategy
    public /* synthetic */ void print() {
        a.a(this);
    }

    @Override // com.hjq.http.config.ILogStrategy
    public void print(String str) {
        String logTag = EasyConfig.getInstance().getLogTag();
        if (str == null) {
            str = "null";
        }
        Log.i(logTag, str);
    }

    @Override // com.hjq.http.config.ILogStrategy
    public void print(String str, String str2) {
        print(str + " = " + str2);
    }

    @Override // com.hjq.http.config.ILogStrategy
    public void print(Throwable th) {
        Log.e(EasyConfig.getInstance().getLogTag(), th.getMessage(), th);
    }

    @Override // com.hjq.http.config.ILogStrategy
    public void print(StackTraceElement[] stackTraceElementArr) {
        for (StackTraceElement stackTraceElement : stackTraceElementArr) {
            int lineNumber = stackTraceElement.getLineNumber();
            String className = stackTraceElement.getClassName();
            if (lineNumber > 0 && !className.startsWith(EasyHttp.class.getPackage().getName())) {
                print("RequestCode = (" + stackTraceElement.getFileName() + ":" + lineNumber + ") ");
                return;
            }
        }
    }
}
