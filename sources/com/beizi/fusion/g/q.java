package com.beizi.fusion.g;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.os.Process;
import android.text.TextUtils;
import android.util.Log;
import cn.hutool.core.text.StrPool;
import com.beizi.fusion.BeiZis;
import com.beizi.fusion.model.ResponseInfo;
import com.mobile.auth.gatewayauth.Constant;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.Thread;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class q implements Thread.UncaughtExceptionHandler {

    /* renamed from: a, reason: collision with root package name */
    private static q f5235a = new q();

    /* renamed from: b, reason: collision with root package name */
    private Thread.UncaughtExceptionHandler f5236b;

    /* renamed from: c, reason: collision with root package name */
    private Context f5237c;

    private q() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(Throwable th) throws JSONException, PackageManager.NameNotFoundException {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        th.printStackTrace(printWriter);
        for (Throwable cause = th.getCause(); cause != null; cause = cause.getCause()) {
            cause.printStackTrace(printWriter);
        }
        printWriter.close();
        String string = stringWriter.toString();
        a(string);
        b(string);
    }

    @Override // java.lang.Thread.UncaughtExceptionHandler
    public void uncaughtException(Thread thread, Throwable th) {
        try {
            a(th);
        } catch (Exception e2) {
            e2.printStackTrace();
        } catch (Throwable th2) {
            th2.printStackTrace();
        }
        Thread.UncaughtExceptionHandler uncaughtExceptionHandler = this.f5236b;
        if (uncaughtExceptionHandler != null) {
            uncaughtExceptionHandler.uncaughtException(thread, th);
        } else {
            Process.killProcess(Process.myPid());
        }
    }

    public static q a() {
        return f5235a;
    }

    public void a(Context context) {
        this.f5236b = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
        this.f5237c = context.getApplicationContext();
    }

    private void a(String str) throws JSONException, PackageManager.NameNotFoundException {
        String strA;
        String strA2;
        try {
            PackageInfo packageInfo = this.f5237c.getPackageManager().getPackageInfo(this.f5237c.getPackageName(), 1);
            JSONObject jSONObject = new JSONObject();
            JSONObject jSONObject2 = new JSONObject();
            jSONObject.put("appId", com.beizi.fusion.d.b.a().b());
            jSONObject.put("packageName", this.f5237c.getPackageName());
            jSONObject.put("versionName", packageInfo.versionName);
            jSONObject.put("versionCode", String.valueOf(packageInfo.versionCode));
            jSONObject.put(com.heytap.mcssdk.constant.b.C, "4.90.2.36");
            jSONObject2.put("osVersion", Build.VERSION.RELEASE + StrPool.UNDERLINE + Build.VERSION.SDK_INT);
            jSONObject2.put(Constant.LOGIN_ACTIVITY_VENDOR_KEY, Build.MANUFACTURER);
            jSONObject2.put("model", Build.MODEL);
            jSONObject2.put(com.umeng.analytics.pro.am.f22460w, Build.CPU_ABI);
            jSONObject.put(com.alipay.sdk.packet.d.f3298n, jSONObject2);
            jSONObject.put("crashMessage", str);
            if (!TextUtils.isEmpty(ResponseInfo.getInstance(this.f5237c).getCrashUrl()) && ResponseInfo.getInstance(this.f5237c).getCrashUrl().startsWith("http")) {
                strA = ResponseInfo.getInstance(this.f5237c).getCrashUrl();
            } else {
                strA = f.a(BeiZis.getTransferProtocol() ? "aHR0cHM6Ly9hcGktaHRwLmJlaXppLmJpei9tYi9zZGsvY3Jhc2gvdjE=" : "aHR0cDovL2FwaS5odHAuYWQtc2NvcGUuY29tLmNuOjQ1NjAwL21iL3Nkay9jcmFzaC92MQ==");
                if (TextUtils.isEmpty(strA)) {
                    return;
                }
            }
            if (TextUtils.isEmpty(strA) || (strA2 = z.a(strA, jSONObject.toString().getBytes())) == null) {
                return;
            }
            ac.a("lance", "post:" + strA2);
        } catch (PackageManager.NameNotFoundException e2) {
            e = e2;
            e.printStackTrace();
        } catch (JSONException e3) {
            e = e3;
            e.printStackTrace();
        } catch (Exception e4) {
            e4.printStackTrace();
        }
    }

    private void b(String str) {
        try {
        } catch (Exception unused) {
        } catch (Throwable th) {
            th.printStackTrace();
        }
        if (!Environment.getExternalStorageState().equals("mounted")) {
            Log.e("CrashHandler", "sdcard unmounted,skip dump exception");
            return;
        }
        Context context = this.f5237c;
        if (context == null) {
            return;
        }
        File fileA = j.a(context);
        ac.a("BeiZis", "CrashHandler storagePath == " + fileA);
        if (fileA != null) {
            String str2 = fileA.getPath() + "/Beizi/log/";
            File file = new File(str2);
            if (!file.exists()) {
                file.mkdirs();
            }
            String str3 = new SimpleDateFormat("yyyy-MM-dd HH:MM:SS").format(new Date(System.currentTimeMillis()));
            File file2 = new File(str2 + "crash_" + str3 + ".trace");
            file2.createNewFile();
            FileWriter fileWriter = new FileWriter(file2, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(str3);
            bufferedWriter.write("\r\n");
            bufferedWriter.write("------------------crash----------------------");
            bufferedWriter.write("\r\n");
            bufferedWriter.write(str);
            bufferedWriter.write("\r\n");
            bufferedWriter.write("-------------------end-----------------------");
            bufferedWriter.newLine();
            bufferedWriter.close();
            fileWriter.close();
        }
        ac.a("lance", "writeLog ok");
    }

    private boolean a(final Throwable th) throws InterruptedException {
        if (th == null) {
            return false;
        }
        new Thread(new Runnable() { // from class: com.beizi.fusion.g.q.1
            @Override // java.lang.Runnable
            public void run() throws JSONException, PackageManager.NameNotFoundException {
                Looper.prepare();
                q.this.b(th);
                Looper.loop();
            }
        }).start();
        try {
            Thread.sleep(1000L);
            return true;
        } catch (InterruptedException e2) {
            e2.printStackTrace();
            return true;
        }
    }
}
