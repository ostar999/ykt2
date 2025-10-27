package com.psychiatrygarden;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.util.ArrayMap;
import android.util.Log;
import androidx.annotation.NonNull;
import com.google.android.exoplayer2.ExoPlayer;
import com.plv.thirdpart.blankj.utilcode.util.ToastUtils;
import com.psychiatrygarden.utils.LogUtils;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.Thread;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

/* loaded from: classes5.dex */
public class CrashHandler implements Thread.UncaughtExceptionHandler {
    private static final String TAG = "CrashHandler";
    private Context mContext;
    private Thread.UncaughtExceptionHandler mDefaultHandler;
    private final Map<String, String> infos = new ArrayMap();

    @SuppressLint({"SimpleDateFormat"})
    private final DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss SSS");

    private CrashHandler() {
    }

    private void collectDeviceInfo(Context ctx) throws IllegalAccessException, PackageManager.NameNotFoundException, SecurityException, IllegalArgumentException {
        try {
            PackageInfo packageInfo = ctx.getPackageManager().getPackageInfo(ctx.getPackageName(), 1);
            if (packageInfo != null) {
                String str = packageInfo.versionName;
                if (str == null) {
                    str = "null";
                }
                String str2 = packageInfo.versionCode + "";
                this.infos.put("versionName", str);
                this.infos.put("versionCode", str2);
            }
        } catch (PackageManager.NameNotFoundException e2) {
            Log.e(TAG, "an error occured when collect package info", e2);
        }
        for (Field field : Build.class.getDeclaredFields()) {
            try {
                field.setAccessible(true);
                Map<String, String> map = this.infos;
                String name = field.getName();
                Object obj = field.get(null);
                Objects.requireNonNull(obj);
                map.put(name, obj.toString());
                Log.d(TAG, field.getName() + " : " + field.get(null));
            } catch (Exception e3) {
                Log.e(TAG, "an error occured when collect crash info", e3);
            }
        }
    }

    public static synchronized CrashHandler getInstance() {
        return new CrashHandler();
    }

    private boolean handleException(Throwable ex) throws Throwable {
        new Thread(new Runnable() { // from class: com.psychiatrygarden.i
            @Override // java.lang.Runnable
            public final void run() {
                CrashHandler.lambda$handleException$0();
            }
        }).start();
        collectDeviceInfo(this.mContext);
        saveCrashInfo2File(ex);
        return ex != null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$handleException$0() {
        Looper.prepare();
        ToastUtils.showShort("很抱歉,程序出现异常,即将退出");
        Looper.loop();
    }

    private void saveCrashInfo2File(Throwable ex) throws Throwable {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : this.infos.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(key);
            sb.append("=");
            sb.append(value);
            sb.append("\n");
        }
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        ex.printStackTrace(printWriter);
        for (Throwable cause = ex.getCause(); cause != null; cause = cause.getCause()) {
            cause.printStackTrace(printWriter);
        }
        printWriter.close();
        sb.append(stringWriter.toString());
        try {
            String str = this.formatter.format(new Date());
            if (Environment.getExternalStorageState().equals("mounted")) {
                String str2 = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath() + (File.separator + "ykb_app_crash_" + str + ".log");
                File file = new File(str2);
                if (!file.exists() && file.createNewFile()) {
                    LogUtils.i(getClass().getSimpleName(), "File created success!");
                }
                FileOutputStream fileOutputStream = new FileOutputStream(str2);
                fileOutputStream.write(sb.toString().getBytes(StandardCharsets.UTF_8));
                fileOutputStream.close();
            }
        } catch (Exception e2) {
            Log.e(TAG, "An error occured while writing file...", e2);
        }
    }

    public void init(Context context) {
        this.mContext = context;
        this.mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override // java.lang.Thread.UncaughtExceptionHandler
    public void uncaughtException(@NonNull Thread thread, Throwable ex) throws InterruptedException {
        Thread.UncaughtExceptionHandler uncaughtExceptionHandler;
        LogUtils.e(getClass().getSimpleName(), ex.getMessage());
        if (!handleException(ex) && (uncaughtExceptionHandler = this.mDefaultHandler) != null) {
            uncaughtExceptionHandler.uncaughtException(thread, ex);
            System.out.println(ex.getMessage());
        } else {
            try {
                Thread.sleep(ExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS);
            } catch (InterruptedException e2) {
                Log.e(TAG, "error : ", e2);
            }
        }
    }
}
