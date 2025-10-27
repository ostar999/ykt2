package com.huawei.hms.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Pair;
import com.alipay.security.mobile.module.http.model.c;
import com.huawei.hms.common.PackageConstants;
import com.huawei.hms.support.log.HMSLog;
import com.huawei.hms.utils.PackageManagerHelper;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes4.dex */
public class HMSPackageManager {

    /* renamed from: l, reason: collision with root package name */
    public static HMSPackageManager f8132l;

    /* renamed from: m, reason: collision with root package name */
    public static final Object f8133m = new Object();

    /* renamed from: n, reason: collision with root package name */
    public static final Object f8134n = new Object();

    /* renamed from: o, reason: collision with root package name */
    public static final Object f8135o = new Object();

    /* renamed from: a, reason: collision with root package name */
    public final Context f8136a;

    /* renamed from: b, reason: collision with root package name */
    public final PackageManagerHelper f8137b;

    /* renamed from: c, reason: collision with root package name */
    public String f8138c;

    /* renamed from: d, reason: collision with root package name */
    public String f8139d;

    /* renamed from: e, reason: collision with root package name */
    public int f8140e;

    /* renamed from: f, reason: collision with root package name */
    public String f8141f;

    /* renamed from: g, reason: collision with root package name */
    public String f8142g;

    /* renamed from: h, reason: collision with root package name */
    public String f8143h;

    /* renamed from: i, reason: collision with root package name */
    public int f8144i;

    /* renamed from: j, reason: collision with root package name */
    public int f8145j;

    /* renamed from: k, reason: collision with root package name */
    public long f8146k;

    public class a implements Runnable {
        public a() {
        }

        @Override // java.lang.Runnable
        public void run() {
            HMSLog.i("HMSPackageManager", "enter asyncOnceCheckMDMState");
            Iterator<ResolveInfo> it = HMSPackageManager.this.f8136a.getPackageManager().queryIntentServices(new Intent("com.huawei.hms.core.aidlservice"), 128).iterator();
            while (it.hasNext()) {
                if ("com.huawei.hwid".equals(it.next().serviceInfo.applicationInfo.packageName)) {
                    HMSPackageManager.this.c();
                }
            }
            HMSLog.i("HMSPackageManager", "quit asyncOnceCheckMDMState");
        }
    }

    public static class b implements Comparable<b> {

        /* renamed from: a, reason: collision with root package name */
        public String f8148a;

        /* renamed from: b, reason: collision with root package name */
        public String f8149b;

        /* renamed from: c, reason: collision with root package name */
        public String f8150c;

        /* renamed from: d, reason: collision with root package name */
        public String f8151d;

        /* renamed from: e, reason: collision with root package name */
        public String f8152e;

        /* renamed from: f, reason: collision with root package name */
        public Long f8153f;

        public b(String str, String str2, String str3, String str4, String str5, long j2) {
            this.f8148a = str;
            this.f8149b = str2;
            this.f8150c = str3;
            this.f8151d = str4;
            this.f8152e = str5;
            this.f8153f = Long.valueOf(j2);
        }

        @Override // java.lang.Comparable
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public int compareTo(b bVar) {
            return TextUtils.equals(this.f8152e, bVar.f8152e) ? this.f8153f.compareTo(bVar.f8153f) : this.f8152e.compareTo(bVar.f8152e);
        }
    }

    public HMSPackageManager(Context context) {
        this.f8136a = context;
        this.f8137b = new PackageManagerHelper(context);
    }

    public static HMSPackageManager getInstance(Context context) {
        synchronized (f8133m) {
            if (f8132l == null) {
                if (context.getApplicationContext() != null) {
                    f8132l = new HMSPackageManager(context.getApplicationContext());
                } else {
                    f8132l = new HMSPackageManager(context);
                }
                f8132l.j();
                f8132l.a();
            }
        }
        return f8132l;
    }

    public final int c() {
        synchronized (f8135o) {
            HMSLog.i("HMSPackageManager", "enter checkHmsIsSpoof");
            if (!(this.f8145j == 3 || this.f8146k != this.f8137b.getPackageFirstInstallTime("com.huawei.hwid"))) {
                HMSLog.i("HMSPackageManager", "quit checkHmsIsSpoof cached state: " + a(this.f8145j));
                return this.f8145j;
            }
            this.f8145j = b() ? 2 : 1;
            this.f8146k = this.f8137b.getPackageFirstInstallTime("com.huawei.hwid");
            HMSLog.i("HMSPackageManager", "quit checkHmsIsSpoof state: " + a(this.f8145j));
            return this.f8145j;
        }
    }

    public final void d() {
        synchronized (f8134n) {
            this.f8141f = null;
            this.f8142g = null;
            this.f8143h = null;
            this.f8144i = 0;
        }
    }

    public final void e() {
        synchronized (f8134n) {
            this.f8138c = null;
            this.f8139d = null;
            this.f8140e = 0;
        }
    }

    public final Pair<String, String> f() throws PackageManager.NameNotFoundException {
        List<ResolveInfo> listQueryIntentServices = this.f8136a.getPackageManager().queryIntentServices(new Intent("com.huawei.hms.core.aidlservice"), 128);
        if (listQueryIntentServices.size() == 0) {
            return null;
        }
        Iterator<ResolveInfo> it = listQueryIntentServices.iterator();
        while (it.hasNext()) {
            ServiceInfo serviceInfo = it.next().serviceInfo;
            String str = serviceInfo.applicationInfo.packageName;
            Bundle bundle = serviceInfo.metaData;
            if (bundle == null) {
                HMSLog.e("HMSPackageManager", "skip package " + str + " for metadata is null");
            } else if (!bundle.containsKey("hms_app_signer")) {
                HMSLog.e("HMSPackageManager", "skip package " + str + " for no signer");
            } else if (bundle.containsKey("hms_app_cert_chain")) {
                String packageSignature = this.f8137b.getPackageSignature(str);
                if (a(str + "&" + packageSignature, bundle.getString("hms_app_signer"), bundle.getString("hms_app_cert_chain"))) {
                    return new Pair<>(str, packageSignature);
                }
                HMSLog.e("HMSPackageManager", "checkSigner failed");
            } else {
                HMSLog.e("HMSPackageManager", "skip package " + str + " for no cert chain");
            }
        }
        return null;
    }

    public final Pair<String, String> g() throws PackageManager.NameNotFoundException {
        Pair<String, String> pairF = f();
        if (pairF != null) {
            HMSLog.i("HMSPackageManager", "aidlService pkgName: " + ((String) pairF.first));
            this.f8143h = "com.huawei.hms.core.aidlservice";
            return pairF;
        }
        ArrayList<b> arrayListH = h();
        if (arrayListH == null) {
            HMSLog.e("HMSPackageManager", "PackagePriorityInfo list is null");
            return null;
        }
        Iterator<b> it = arrayListH.iterator();
        while (it.hasNext()) {
            b next = it.next();
            String str = next.f8148a;
            String str2 = next.f8149b;
            String str3 = next.f8150c;
            String str4 = next.f8151d;
            String packageSignature = this.f8137b.getPackageSignature(str);
            if (a(str + "&" + packageSignature + "&" + str2, str3, str4)) {
                HMSLog.i("HMSPackageManager", "result: " + str + ", " + str2 + ", " + next.f8153f);
                this.f8143h = PackageConstants.GENERAL_SERVICES_ACTION;
                b(str2);
                return new Pair<>(str, packageSignature);
            }
        }
        return null;
    }

    public String getHMSFingerprint() {
        String str = this.f8139d;
        return str == null ? "B92825C2BD5D6D6D1E7F39EECD17843B7D9016F611136B75441BC6F4D3F00F05" : str;
    }

    public String getHMSPackageName() {
        HMSLog.i("HMSPackageManager", "Enter getHMSPackageName");
        refresh();
        String str = this.f8138c;
        if (str != null) {
            if (PackageManagerHelper.PackageStates.NOT_INSTALLED.equals(this.f8137b.getPackageStates(str))) {
                HMSLog.i("HMSPackageManager", "The package name is not installed and needs to be refreshed again");
                i();
            }
            String str2 = this.f8138c;
            if (str2 != null) {
                return str2;
            }
        }
        if (!PackageManagerHelper.PackageStates.NOT_INSTALLED.equals(this.f8137b.getPackageStates("com.huawei.hwid"))) {
            "B92825C2BD5D6D6D1E7F39EECD17843B7D9016F611136B75441BC6F4D3F00F05".equalsIgnoreCase(this.f8137b.getPackageSignature("com.huawei.hwid"));
        }
        return "com.huawei.hwid";
    }

    public String getHMSPackageNameForMultiService() {
        HMSLog.i("HMSPackageManager", "Enter getHMSPackageNameForMultiService");
        refreshForMultiService();
        String str = this.f8141f;
        if (str == null) {
            return "com.huawei.hwid";
        }
        if (PackageManagerHelper.PackageStates.NOT_INSTALLED.equals(this.f8137b.getPackageStates(str))) {
            HMSLog.i("HMSPackageManager", "The package name is not installed and needs to be refreshed again");
            j();
        }
        String str2 = this.f8141f;
        return str2 != null ? str2 : "com.huawei.hwid";
    }

    public PackageManagerHelper.PackageStates getHMSPackageStates() {
        synchronized (f8133m) {
            refresh();
            PackageManagerHelper.PackageStates packageStates = this.f8137b.getPackageStates(this.f8138c);
            PackageManagerHelper.PackageStates packageStates2 = PackageManagerHelper.PackageStates.NOT_INSTALLED;
            if (packageStates == packageStates2) {
                e();
                return packageStates2;
            }
            boolean z2 = false;
            if ("com.huawei.hwid".equals(this.f8138c) && c() == 1) {
                return PackageManagerHelper.PackageStates.SPOOF;
            }
            if (packageStates == PackageManagerHelper.PackageStates.ENABLED && !this.f8139d.equals(this.f8137b.getPackageSignature(this.f8138c))) {
                z2 = true;
            }
            return z2 ? packageStates2 : packageStates;
        }
    }

    public PackageManagerHelper.PackageStates getHMSPackageStatesForMultiService() {
        synchronized (f8133m) {
            refreshForMultiService();
            PackageManagerHelper.PackageStates packageStates = this.f8137b.getPackageStates(this.f8141f);
            PackageManagerHelper.PackageStates packageStates2 = PackageManagerHelper.PackageStates.NOT_INSTALLED;
            if (packageStates == packageStates2) {
                d();
                return packageStates2;
            }
            boolean z2 = false;
            if ("com.huawei.hwid".equals(this.f8141f) && c() == 1) {
                return PackageManagerHelper.PackageStates.SPOOF;
            }
            if (packageStates == PackageManagerHelper.PackageStates.ENABLED && !this.f8142g.equals(this.f8137b.getPackageSignature(this.f8141f))) {
                z2 = true;
            }
            return z2 ? packageStates2 : packageStates;
        }
    }

    public int getHmsMultiServiceVersion() {
        return this.f8137b.getPackageVersionCode(getHMSPackageNameForMultiService());
    }

    public int getHmsVersionCode() {
        return this.f8137b.getPackageVersionCode(getHMSPackageName());
    }

    public String getInnerServiceAction() {
        return PackageConstants.INTERNAL_SERVICES_ACTION;
    }

    public String getServiceAction() {
        return !TextUtils.isEmpty(this.f8143h) ? this.f8143h : "com.huawei.hms.core.aidlservice";
    }

    public final ArrayList<b> h() throws PackageManager.NameNotFoundException {
        List<ResolveInfo> listQueryIntentServices = this.f8136a.getPackageManager().queryIntentServices(new Intent(PackageConstants.GENERAL_SERVICES_ACTION), 128);
        if (listQueryIntentServices == null || listQueryIntentServices.isEmpty()) {
            HMSLog.e("HMSPackageManager", "resolveInfoList is null or empty");
            return null;
        }
        ArrayList<b> arrayList = new ArrayList<>();
        for (ResolveInfo resolveInfo : listQueryIntentServices) {
            String str = resolveInfo.serviceInfo.applicationInfo.packageName;
            long packageFirstInstallTime = this.f8137b.getPackageFirstInstallTime(str);
            Bundle bundle = resolveInfo.serviceInfo.metaData;
            if (bundle == null) {
                HMSLog.e("HMSPackageManager", "package " + str + " get metaData is null");
            } else {
                String strA = a(bundle, "hms_app_checker_config");
                String strA2 = a(strA);
                if (TextUtils.isEmpty(strA2)) {
                    HMSLog.i("HMSPackageManager", "get priority fail. hmsCheckerCfg: " + strA);
                } else {
                    String strA3 = a(bundle, "hms_app_signer_v2");
                    if (TextUtils.isEmpty(strA3)) {
                        HMSLog.i("HMSPackageManager", "get signerV2 fail.");
                    } else {
                        String strA4 = a(bundle, "hms_app_cert_chain");
                        if (TextUtils.isEmpty(strA4)) {
                            HMSLog.i("HMSPackageManager", "get certChain fail.");
                        } else {
                            HMSLog.i("HMSPackageManager", "add: " + str + ", " + strA + ", " + packageFirstInstallTime);
                            arrayList.add(new b(str, strA, strA3, strA4, strA2, packageFirstInstallTime));
                        }
                    }
                }
            }
        }
        Collections.sort(arrayList);
        return arrayList;
    }

    public boolean hmsVerHigherThan(int i2) throws PackageManager.NameNotFoundException {
        if (this.f8140e >= i2 || !k()) {
            return true;
        }
        int packageVersionCode = this.f8137b.getPackageVersionCode(getHMSPackageName());
        this.f8140e = packageVersionCode;
        return packageVersionCode >= i2;
    }

    public final void i() {
        synchronized (f8134n) {
            Pair<String, String> pairF = f();
            if (pairF == null) {
                HMSLog.e("HMSPackageManager", "<initHmsPackageInfo> Failed to find HMS apk");
                e();
                return;
            }
            this.f8138c = (String) pairF.first;
            this.f8139d = (String) pairF.second;
            this.f8140e = this.f8137b.getPackageVersionCode(getHMSPackageName());
            HMSLog.i("HMSPackageManager", "<initHmsPackageInfo> Succeed to find HMS apk: " + this.f8138c + " version: " + this.f8140e);
        }
    }

    public boolean isApkNeedUpdate(int i2) {
        int hmsVersionCode = getHmsVersionCode();
        HMSLog.i("HMSPackageManager", "current versionCode:" + hmsVersionCode + ", target version requirements: " + i2);
        return hmsVersionCode < i2;
    }

    public boolean isApkUpdateNecessary(int i2) {
        int hmsVersionCode = getHmsVersionCode();
        HMSLog.i("HMSPackageManager", "current versionCode:" + hmsVersionCode + ", minimum version requirements: " + i2);
        return k() && hmsVersionCode < i2;
    }

    public final void j() {
        synchronized (f8134n) {
            Pair<String, String> pairG = g();
            if (pairG == null) {
                HMSLog.e("HMSPackageManager", "<initHmsPackageInfoForMultiService> Failed to find HMS apk");
                d();
                return;
            }
            this.f8141f = (String) pairG.first;
            this.f8142g = (String) pairG.second;
            this.f8144i = this.f8137b.getPackageVersionCode(getHMSPackageNameForMultiService());
            HMSLog.i("HMSPackageManager", "<initHmsPackageInfoForMultiService> Succeed to find HMS apk: " + this.f8141f + " version: " + this.f8144i);
        }
    }

    public final boolean k() {
        Bundle bundle;
        PackageManager packageManager = this.f8136a.getPackageManager();
        if (packageManager == null) {
            HMSLog.e("HMSPackageManager", "In isMinApkVersionEffective, Failed to get 'PackageManager' instance.");
            return true;
        }
        try {
        } catch (PackageManager.NameNotFoundException unused) {
            HMSLog.e("HMSPackageManager", "In isMinApkVersionEffective, Failed to read meta data for HMSCore API level.");
        } catch (RuntimeException e2) {
            HMSLog.e("HMSPackageManager", "In isMinApkVersionEffective, Failed to read meta data for HMSCore API level.", e2);
        }
        if (!TextUtils.isEmpty(this.f8143h) && (this.f8143h.equals(PackageConstants.GENERAL_SERVICES_ACTION) || this.f8143h.equals(PackageConstants.INTERNAL_SERVICES_ACTION))) {
            HMSLog.i("HMSPackageManager", "action = " + this.f8143h + " exist");
            return false;
        }
        ApplicationInfo applicationInfo = packageManager.getPackageInfo(getHMSPackageName(), 128).applicationInfo;
        if (applicationInfo != null && (bundle = applicationInfo.metaData) != null && bundle.containsKey("com.huawei.hms.kit.api_level:hmscore") && (getHmsVersionCode() >= 50000000 || getHmsVersionCode() <= 19999999)) {
            HMSLog.i("HMSPackageManager", "MinApkVersion is disabled.");
            return false;
        }
        return true;
    }

    public void refresh() {
        if (TextUtils.isEmpty(this.f8138c) || TextUtils.isEmpty(this.f8139d)) {
            i();
        }
    }

    public void refreshForMultiService() {
        if (TextUtils.isEmpty(this.f8141f) || TextUtils.isEmpty(this.f8142g)) {
            j();
        }
    }

    public void resetMultiServiceState() {
        d();
    }

    public final String a(Bundle bundle, String str) {
        if (bundle.containsKey(str)) {
            return bundle.getString(str);
        }
        HMSLog.e("HMSPackageManager", "no " + str + " in metaData");
        return null;
    }

    public final void b(String str) {
        String strA = a(str);
        if (TextUtils.isEmpty(strA)) {
            return;
        }
        strA.substring(9);
    }

    public final String a(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        int iIndexOf = str.indexOf("priority=");
        if (iIndexOf == -1) {
            HMSLog.e("HMSPackageManager", "get indexOfIdentifier -1");
            return null;
        }
        int iIndexOf2 = str.indexOf(",", iIndexOf);
        if (iIndexOf2 == -1) {
            iIndexOf2 = str.length();
        }
        return str.substring(iIndexOf, iIndexOf2);
    }

    public final boolean b() {
        String hmsPath = ReadApkFileUtil.getHmsPath(this.f8136a);
        if (hmsPath == null) {
            HMSLog.i("HMSPackageManager", "hmsPath is null!");
            return false;
        }
        if (!ReadApkFileUtil.isCertFound(hmsPath)) {
            HMSLog.i("HMSPackageManager", "NO huawer.cer in HMS!");
            return false;
        }
        if (!ReadApkFileUtil.checkSignature()) {
            HMSLog.i("HMSPackageManager", "checkSignature fail!");
            return false;
        }
        if (ReadApkFileUtil.verifyApkHash(hmsPath)) {
            return true;
        }
        HMSLog.i("HMSPackageManager", "verifyApkHash fail!");
        return false;
    }

    public final boolean a(String str, String str2, String str3) {
        if (!TextUtils.isEmpty(str2) && !TextUtils.isEmpty(str3)) {
            List<X509Certificate> listB = com.huawei.hms.device.a.b(str3);
            if (listB.size() == 0) {
                HMSLog.e("HMSPackageManager", "certChain is empty");
                return false;
            }
            if (!com.huawei.hms.device.a.a(com.huawei.hms.device.a.a(this.f8136a), listB)) {
                HMSLog.e("HMSPackageManager", "failed to verify cert chain");
                return false;
            }
            X509Certificate x509Certificate = listB.get(listB.size() - 1);
            if (!com.huawei.hms.device.a.a(x509Certificate, "Huawei CBG HMS")) {
                HMSLog.e("HMSPackageManager", "CN is invalid");
                return false;
            }
            if (!com.huawei.hms.device.a.b(x509Certificate, "Huawei CBG Cloud Security Signer")) {
                HMSLog.e("HMSPackageManager", "OU is invalid");
                return false;
            }
            if (com.huawei.hms.device.a.a(x509Certificate, str, str2)) {
                return true;
            }
            HMSLog.e("HMSPackageManager", "signature is invalid: " + str);
            return false;
        }
        HMSLog.e("HMSPackageManager", "args is invalid");
        return false;
    }

    public final void a() {
        new Thread(new a(), "Thread-asyncOnceCheckMDMState").start();
    }

    public static String a(int i2) {
        if (i2 == 1) {
            return "SPOOFED";
        }
        if (i2 == 2) {
            return c.f3449g;
        }
        if (i2 == 3) {
            return "UNCHECKED";
        }
        HMSLog.e("HMSPackageManager", "invalid checkMDM state: " + i2);
        return "";
    }
}
