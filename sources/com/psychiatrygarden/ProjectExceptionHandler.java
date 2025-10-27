package com.psychiatrygarden;

import android.content.Context;
import com.psychiatrygarden.utils.LogUtils;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.Thread;

/* loaded from: classes5.dex */
public class ProjectExceptionHandler implements Thread.UncaughtExceptionHandler {
    private static ProjectExceptionHandler appStoreExceptionHandler;
    private Thread.UncaughtExceptionHandler defaultExceptionHandler;
    private Context mContext;

    private ProjectExceptionHandler() {
    }

    private String getErrorInfo(Throwable arg1) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        arg1.printStackTrace(printWriter);
        printWriter.close();
        return stringWriter.toString();
    }

    public static ProjectExceptionHandler getInstance() {
        if (appStoreExceptionHandler == null) {
            appStoreExceptionHandler = new ProjectExceptionHandler();
        }
        return appStoreExceptionHandler;
    }

    public void init(Context context) {
        this.mContext = context;
        this.defaultExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override // java.lang.Thread.UncaughtExceptionHandler
    public void uncaughtException(Thread thread, Throwable exception) throws Throwable {
        exception.printStackTrace();
        try {
            LogUtils.e(ProjectApp.LOG_TAG, "exception >>>>>>>" + exception.getLocalizedMessage());
            String errorInfo = getErrorInfo(exception);
            LogUtils.e(ProjectApp.LOG_TAG, errorInfo);
            LogUtils.ee(ProjectApp.LOG_TAG, errorInfo);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        Thread.UncaughtExceptionHandler uncaughtExceptionHandler = this.defaultExceptionHandler;
        if (uncaughtExceptionHandler != null) {
            uncaughtExceptionHandler.uncaughtException(thread, exception);
        } else {
            System.exit(0);
        }
    }
}
