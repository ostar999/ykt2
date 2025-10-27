package com.tencent.smtt.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.text.TextUtils;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Properties;

/* loaded from: classes6.dex */
public class o {

    /* renamed from: c, reason: collision with root package name */
    private static o f21578c;

    /* renamed from: a, reason: collision with root package name */
    private Context f21579a;

    /* renamed from: b, reason: collision with root package name */
    private File f21580b = null;

    /* renamed from: d, reason: collision with root package name */
    private String f21581d = "https://log.tbs.qq.com/ajax?c=pu&v=2&k=";

    /* renamed from: e, reason: collision with root package name */
    private String f21582e = "https://log.tbs.qq.com/ajax?c=pu&tk=";

    /* renamed from: f, reason: collision with root package name */
    private String f21583f = "https://log.tbs.qq.com/ajax?c=dl&k=";

    /* renamed from: g, reason: collision with root package name */
    private String f21584g = "https://cfg.imtt.qq.com/tbs?v=2&mk=";

    /* renamed from: h, reason: collision with root package name */
    private String f21585h = "https://log.tbs.qq.com/ajax?c=ul&v=2&k=";

    /* renamed from: i, reason: collision with root package name */
    private String f21586i = "https://mqqad.html5.qq.com/adjs";

    /* renamed from: j, reason: collision with root package name */
    private String f21587j = "https://log.tbs.qq.com/ajax?c=ucfu&k=";

    /* renamed from: k, reason: collision with root package name */
    private String f21588k = "";

    /* renamed from: l, reason: collision with root package name */
    private String f21589l = "";

    /* renamed from: m, reason: collision with root package name */
    private String f21590m = "";

    /* renamed from: n, reason: collision with root package name */
    private String f21591n = "https://tbsrecovery.imtt.qq.com/getconfig";

    @TargetApi(11)
    private o(Context context) {
        this.f21579a = null;
        TbsLog.w("TbsCommonConfig", "TbsCommonConfig constructing...");
        this.f21579a = context.getApplicationContext();
        k();
    }

    public static synchronized o a() {
        return f21578c;
    }

    public static synchronized o a(Context context) {
        if (f21578c == null) {
            f21578c = new o(context);
        }
        return f21578c;
    }

    private synchronized void k() {
        File fileL;
        BufferedInputStream bufferedInputStream = null;
        try {
            fileL = l();
        } catch (Throwable th) {
            th = th;
        }
        if (fileL == null) {
            TbsLog.e("TbsCommonConfig", "Config file is null, default values will be applied");
            return;
        }
        BufferedInputStream bufferedInputStream2 = new BufferedInputStream(new FileInputStream(fileL));
        try {
            Properties properties = new Properties();
            properties.load(bufferedInputStream2);
            String property = properties.getProperty("pv_post_url", "");
            if (!"".equals(property)) {
                this.f21581d = property;
            }
            String property2 = properties.getProperty("tbs_download_stat_post_url", "");
            if (!"".equals(property2)) {
                this.f21583f = property2;
            }
            String property3 = properties.getProperty("tbs_downloader_post_url", "");
            TbsLog.i("TbsCommonConfig", "KEY_TBS_DOWNLOADER_POST_URL is " + property3);
            if (!"".equals(property3)) {
                this.f21584g = property3;
            }
            String property4 = properties.getProperty("tbs_downloader_response_query", "");
            TbsLog.i("TbsCommonConfig", "KEY_TBS_DOWNLOADER_RESPONSE_QUERY is " + property4);
            if (!"".equals(property4)) {
                this.f21588k = property4;
            }
            String property5 = properties.getProperty("tbs_downloader_response_update", "");
            TbsLog.i("TbsCommonConfig", "KEY_TBS_DOWNLOADER_RESPONSE_UPDATE is " + property5);
            if (!"".equals(property5)) {
                this.f21589l = property5;
            }
            String property6 = properties.getProperty("tbs_downloader_response_download", "");
            TbsLog.i("TbsCommonConfig", "KEY_TBS_DOWNLOADER_RESPONSE_DOWNLOAD is " + property6);
            if (!"".equals(property6)) {
                this.f21590m = property6;
            }
            String property7 = properties.getProperty("tbs_log_post_url", "");
            if (!"".equals(property7)) {
                this.f21585h = property7;
            }
            String property8 = properties.getProperty("tips_url", "");
            if (!"".equals(property8)) {
                this.f21586i = property8;
            }
            String property9 = properties.getProperty("tbs_cmd_post_url", "");
            if (!"".equals(property9)) {
                this.f21587j = property9;
            }
            String property10 = properties.getProperty("tbs_emergency_post_url", "");
            if (!"".equals(property10)) {
                this.f21591n = property10;
            }
            String property11 = properties.getProperty("pv_post_url_tk", "");
            if (!"".equals(property11)) {
                this.f21582e = property11;
            }
            try {
                bufferedInputStream2.close();
            } catch (IOException e2) {
                e = e2;
                e.printStackTrace();
            }
        } catch (Throwable th2) {
            th = th2;
            bufferedInputStream = bufferedInputStream2;
            try {
                StringWriter stringWriter = new StringWriter();
                th.printStackTrace(new PrintWriter(stringWriter));
                TbsLog.e("TbsCommonConfig", "exceptions occurred1:" + stringWriter.toString());
                if (bufferedInputStream != null) {
                    try {
                        bufferedInputStream.close();
                    } catch (IOException e3) {
                        e = e3;
                        e.printStackTrace();
                    }
                }
            } finally {
            }
        }
    }

    private File l() {
        File file;
        File file2;
        File file3 = null;
        try {
            if (this.f21580b == null) {
                if (TextUtils.isEmpty(this.f21579a.getApplicationContext().getApplicationInfo().packageName)) {
                    file = new File(FileUtil.a(this.f21579a, 8));
                } else {
                    File file4 = new File(FileUtil.a(this.f21579a, 8));
                    this.f21580b = file4;
                    if (!file4.isDirectory()) {
                        file = new File(FileUtil.a(this.f21579a, 5));
                    }
                    file2 = this.f21580b;
                    if (file2 != null || !file2.isDirectory()) {
                        return null;
                    }
                }
                this.f21580b = file;
                file2 = this.f21580b;
                if (file2 != null) {
                }
                return null;
            }
            TbsLog.i("TbsCommonConfig", "mTbsConfigDir is " + this.f21580b.getAbsolutePath());
            File file5 = new File(this.f21580b, "tbsnet.conf");
            if (!file5.exists()) {
                TbsLog.e("TbsCommonConfig", "Get file(" + file5.getCanonicalPath() + ") failed!");
                return null;
            }
            try {
                TbsLog.w("TbsCommonConfig", "pathc:" + file5.getCanonicalPath());
                return file5;
            } catch (Throwable th) {
                th = th;
                file3 = file5;
                StringWriter stringWriter = new StringWriter();
                th.printStackTrace(new PrintWriter(stringWriter));
                TbsLog.e("TbsCommonConfig", "exceptions occurred2:" + stringWriter.toString());
                return file3;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public String b() {
        return this.f21581d;
    }

    public String c() {
        return this.f21583f;
    }

    public String d() {
        return this.f21588k;
    }

    public String e() {
        return this.f21589l;
    }

    public String f() {
        return this.f21590m;
    }

    public String g() {
        return this.f21584g;
    }

    public String h() {
        return this.f21585h;
    }

    public String i() {
        return this.f21582e;
    }

    public String j() {
        return this.f21591n;
    }
}
