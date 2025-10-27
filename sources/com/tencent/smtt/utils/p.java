package com.tencent.smtt.utils;

import android.content.Context;
import com.tencent.smtt.sdk.QbSdk;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/* loaded from: classes6.dex */
public class p {

    /* renamed from: e, reason: collision with root package name */
    private static p f21592e;

    /* renamed from: a, reason: collision with root package name */
    public boolean f21593a;

    /* renamed from: b, reason: collision with root package name */
    private Context f21594b;

    /* renamed from: c, reason: collision with root package name */
    private File f21595c;

    /* renamed from: d, reason: collision with root package name */
    private boolean f21596d;

    public static synchronized p a() {
        return f21592e;
    }

    private File c() {
        try {
            if (this.f21595c == null) {
                File file = new File(QbSdk.getTbsFolderDir(this.f21594b), "core_private");
                this.f21595c = file;
                if (!file.isDirectory()) {
                    return null;
                }
            }
            File file2 = new File(this.f21595c, "debug.conf");
            if (!file2.exists()) {
                file2.createNewFile();
            }
            return file2;
        } catch (Throwable th) {
            th.printStackTrace();
            return null;
        }
    }

    public void a(boolean z2) throws IOException {
        this.f21596d = z2;
        b();
    }

    public void b() throws IOException {
        BufferedInputStream bufferedInputStream;
        Throwable th;
        BufferedOutputStream bufferedOutputStream;
        Properties properties;
        File fileC = c();
        if (fileC == null) {
            return;
        }
        try {
            try {
                bufferedInputStream = new BufferedInputStream(new FileInputStream(fileC));
                try {
                    properties = new Properties();
                    properties.load(bufferedInputStream);
                    properties.setProperty("setting_forceUseSystemWebview", Boolean.toString(this.f21593a));
                    properties.setProperty("result_systemWebviewForceUsed", Boolean.toString(this.f21596d));
                    bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(fileC));
                } catch (Throwable th2) {
                    th = th2;
                    bufferedOutputStream = null;
                }
                try {
                    properties.store(bufferedOutputStream, (String) null);
                    try {
                        bufferedInputStream.close();
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                    bufferedOutputStream.close();
                } catch (Throwable th3) {
                    th = th3;
                    try {
                        th.printStackTrace();
                        try {
                            bufferedInputStream.close();
                        } catch (Exception e3) {
                            e3.printStackTrace();
                        }
                        bufferedOutputStream.close();
                    } finally {
                    }
                }
            } catch (Throwable th4) {
                bufferedInputStream = null;
                th = th4;
                bufferedOutputStream = null;
            }
        } catch (Exception e4) {
            e4.printStackTrace();
        }
    }
}
