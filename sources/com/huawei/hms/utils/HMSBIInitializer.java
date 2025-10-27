package com.huawei.hms.utils;

import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;
import com.huawei.hianalytics.process.HiAnalyticsConfig;
import com.huawei.hianalytics.process.HiAnalyticsInstance;
import com.huawei.hianalytics.process.HiAnalyticsManager;
import com.huawei.hms.framework.network.grs.GrsApp;
import com.huawei.hms.framework.network.grs.GrsBaseInfo;
import com.huawei.hms.framework.network.grs.GrsClient;
import com.huawei.hms.framework.network.grs.IQueryUrlCallBack;
import com.huawei.hms.hatool.HmsHiAnalyticsUtils;
import com.huawei.hms.stats.c;
import com.huawei.hms.support.hianalytics.HiAnalyticsConstant;
import com.huawei.hms.support.log.HMSLog;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes4.dex */
public class HMSBIInitializer {

    /* renamed from: d, reason: collision with root package name */
    public static final Object f8124d = new Object();

    /* renamed from: e, reason: collision with root package name */
    public static HMSBIInitializer f8125e;

    /* renamed from: f, reason: collision with root package name */
    public static HiAnalyticsInstance f8126f;

    /* renamed from: a, reason: collision with root package name */
    public final Context f8127a;

    /* renamed from: b, reason: collision with root package name */
    public AtomicBoolean f8128b = new AtomicBoolean(false);

    /* renamed from: c, reason: collision with root package name */
    public boolean f8129c = c.a();

    public class a implements IQueryUrlCallBack {
        public a() {
        }

        @Override // com.huawei.hms.framework.network.grs.IQueryUrlCallBack
        public void onCallBackFail(int i2) {
            HMSLog.e("HMSBIInitializer", "get grs failed, the errorcode is " + i2);
            HMSBIInitializer.this.f8128b.set(false);
        }

        @Override // com.huawei.hms.framework.network.grs.IQueryUrlCallBack
        public void onCallBackSuccess(String str) {
            if (!TextUtils.isEmpty(str)) {
                if (HMSBIInitializer.this.f8129c) {
                    HiAnalyticsConfig hiAnalyticsConfigBuild = new HiAnalyticsConfig.Builder().setEnableImei(false).setEnableUDID(false).setEnableSN(false).setCollectURL(str).build();
                    HiAnalyticsInstance unused = HMSBIInitializer.f8126f = new HiAnalyticsInstance.Builder(HMSBIInitializer.this.f8127a).setOperConf(hiAnalyticsConfigBuild).setMaintConf(new HiAnalyticsConfig.Builder().setEnableImei(false).setEnableUDID(false).setEnableSN(false).setCollectURL(str).build()).create(HiAnalyticsConstant.HA_SERVICE_TAG);
                    HMSBIInitializer.f8126f.setAppid("com.huawei.hwid");
                } else {
                    HmsHiAnalyticsUtils.init(HMSBIInitializer.this.f8127a, false, false, false, str, "com.huawei.hwid");
                }
                HMSLog.i("HMSBIInitializer", "BI URL acquired successfully");
            }
            HMSBIInitializer.this.f8128b.set(false);
        }
    }

    public class b extends AsyncTask<String, Integer, Void> {
        public b() {
        }

        @Override // android.os.AsyncTask
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public Void doInBackground(String... strArr) {
            HMSBIInitializer.this.a(strArr[0]);
            return null;
        }

        public /* synthetic */ b(HMSBIInitializer hMSBIInitializer, a aVar) {
            this();
        }
    }

    public HMSBIInitializer(Context context) {
        this.f8127a = context;
    }

    public static HMSBIInitializer getInstance(Context context) {
        synchronized (f8124d) {
            if (f8125e == null) {
                Context applicationContext = context.getApplicationContext();
                if (applicationContext != null) {
                    f8125e = new HMSBIInitializer(applicationContext);
                } else {
                    f8125e = new HMSBIInitializer(context);
                }
            }
        }
        return f8125e;
    }

    public HiAnalyticsInstance getAnalyticsInstance() {
        return f8126f;
    }

    public void initBI() {
        boolean initFlag = !this.f8129c ? HmsHiAnalyticsUtils.getInitFlag() : HiAnalyticsManager.getInitFlag(HiAnalyticsConstant.HA_SERVICE_TAG);
        HMSLog.i("HMSBIInitializer", "Builder->biInitFlag :" + initFlag);
        if (initFlag || com.huawei.hms.stats.a.c(this.f8127a) || !this.f8128b.compareAndSet(false, true)) {
            return;
        }
        String issueCountryCode = GrsApp.getInstance().getIssueCountryCode(this.f8127a);
        if (!TextUtils.isEmpty(issueCountryCode)) {
            issueCountryCode = issueCountryCode.toUpperCase(Locale.ENGLISH);
        }
        if (!"UNKNOWN".equalsIgnoreCase(issueCountryCode) && !TextUtils.isEmpty(issueCountryCode)) {
            new b(this, null).execute(issueCountryCode);
        } else {
            HMSLog.e("HMSBIInitializer", "Failed to get device issue country");
            this.f8128b.set(false);
        }
    }

    public boolean isInit() {
        return !this.f8129c ? HmsHiAnalyticsUtils.getInitFlag() : HiAnalyticsManager.getInitFlag(HiAnalyticsConstant.HA_SERVICE_TAG);
    }

    public final void a(String str) {
        HMSLog.i("HMSBIInitializer", "Start to query GRS");
        GrsBaseInfo grsBaseInfo = new GrsBaseInfo();
        grsBaseInfo.setIssueCountry(str);
        new GrsClient(this.f8127a, grsBaseInfo).ayncGetGrsUrl("com.huawei.cloud.opensdkhianalytics", "ROOTV2", new a());
    }
}
