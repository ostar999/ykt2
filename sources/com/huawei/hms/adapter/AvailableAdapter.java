package com.huawei.hms.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import com.huawei.hms.activity.BridgeActivity;
import com.huawei.hms.adapter.internal.CommonCode;
import com.huawei.hms.adapter.sysobs.SystemManager;
import com.huawei.hms.adapter.sysobs.SystemObserver;
import com.huawei.hms.adapter.ui.NotInstalledHmsAdapter;
import com.huawei.hms.adapter.ui.UpdateAdapter;
import com.huawei.hms.support.log.HMSLog;
import com.huawei.hms.update.note.AppSpoofResolution;
import com.huawei.hms.utils.Checker;
import com.huawei.hms.utils.HMSPackageManager;
import com.huawei.hms.utils.PackageManagerHelper;
import com.huawei.hms.utils.UIUtil;

/* loaded from: classes4.dex */
public class AvailableAdapter {

    /* renamed from: a, reason: collision with root package name */
    public final int f7424a;

    /* renamed from: b, reason: collision with root package name */
    public AvailableCallBack f7425b;

    /* renamed from: d, reason: collision with root package name */
    public SystemObserver f7427d = new a();

    /* renamed from: c, reason: collision with root package name */
    public boolean f7426c = false;

    public interface AvailableCallBack {
        void onComplete(int i2);
    }

    public class a implements SystemObserver {
        public a() {
        }

        @Override // com.huawei.hms.adapter.sysobs.SystemObserver
        public boolean onNoticeResult(int i2) {
            AvailableCallBack availableCallBackA = AvailableAdapter.this.a();
            if (availableCallBackA == null) {
                HMSLog.e("AvailableAdapter", "onNoticeResult baseCallBack null");
                return true;
            }
            availableCallBackA.onComplete(i2);
            return true;
        }

        @Override // com.huawei.hms.adapter.sysobs.SystemObserver
        public boolean onSolutionResult(Intent intent, String str) {
            return false;
        }

        @Override // com.huawei.hms.adapter.sysobs.SystemObserver
        public boolean onUpdateResult(int i2) {
            AvailableCallBack availableCallBackA = AvailableAdapter.this.a();
            if (availableCallBackA == null) {
                HMSLog.e("AvailableAdapter", "onUpdateResult baseCallBack null");
                return true;
            }
            availableCallBackA.onComplete(i2);
            return true;
        }
    }

    public AvailableAdapter(int i2) {
        this.f7424a = i2;
    }

    public int checkHuaweiMobileServicesForUpdate(Context context) {
        Checker.checkNonNull(context, "context must not be null.");
        int iA = a(context);
        if (iA != 0 || !HMSPackageManager.getInstance(context).isApkNeedUpdate(this.f7424a)) {
            return iA;
        }
        HMSLog.i("AvailableAdapter", "The current version does not meet the target version requirements");
        return 2;
    }

    public int isHuaweiMobileServicesAvailable(Context context) {
        Checker.checkNonNull(context, "context must not be null.");
        int iA = a(context);
        if (iA != 0 || !HMSPackageManager.getInstance(context).isApkUpdateNecessary(this.f7424a)) {
            return iA;
        }
        HMSLog.i("AvailableAdapter", "The current version does not meet the minimum version requirements");
        return 2;
    }

    public boolean isUserNoticeError(int i2) {
        return i2 == 29;
    }

    public boolean isUserResolvableError(int i2) {
        return i2 == 1 || i2 == 2;
    }

    public void setCalledBySolutionInstallHms(boolean z2) {
        this.f7426c = z2;
    }

    public void startNotice(Activity activity, AvailableCallBack availableCallBack) {
        if (activity == null || availableCallBack == null) {
            return;
        }
        if (UIUtil.isBackground(activity)) {
            HMSLog.i("AvailableAdapter", "current app is in Background");
            availableCallBack.onComplete(28);
        } else {
            HMSLog.i("AvailableAdapter", "startNotice");
            this.f7425b = availableCallBack;
            SystemManager.getSystemNotifier().registerObserver(this.f7427d);
            activity.startActivity(BridgeActivity.getIntentStartBridgeActivity(activity, AppSpoofResolution.class.getName()));
        }
    }

    public void startResolution(Activity activity, AvailableCallBack availableCallBack) {
        if (activity == null || availableCallBack == null) {
            return;
        }
        if (UIUtil.isBackground(activity)) {
            HMSLog.i("AvailableAdapter", "current app is in Background");
            availableCallBack.onComplete(28);
            return;
        }
        if (!a(activity)) {
            a(activity, availableCallBack);
            return;
        }
        HMSLog.i("AvailableAdapter", "startResolution");
        this.f7425b = availableCallBack;
        SystemManager.getSystemNotifier().registerObserver(this.f7427d);
        Intent intentStartBridgeActivity = BridgeActivity.getIntentStartBridgeActivity(activity, UpdateAdapter.class.getName());
        intentStartBridgeActivity.putExtra(CommonCode.MapKey.UPDATE_VERSION, this.f7424a);
        if (this.f7426c) {
            intentStartBridgeActivity.putExtra("installHMS", "installHMS");
        }
        intentStartBridgeActivity.putExtra(CommonCode.MapKey.NEW_UPDATE, true);
        activity.startActivity(intentStartBridgeActivity);
    }

    public final int a(Context context) {
        PackageManagerHelper.PackageStates hMSPackageStatesForMultiService = HMSPackageManager.getInstance(context).getHMSPackageStatesForMultiService();
        if (PackageManagerHelper.PackageStates.NOT_INSTALLED.equals(hMSPackageStatesForMultiService)) {
            HMSLog.i("AvailableAdapter", "HMS is not installed");
            return 1;
        }
        if (PackageManagerHelper.PackageStates.SPOOF.equals(hMSPackageStatesForMultiService)) {
            HMSLog.i("AvailableAdapter", "HMS is spoofed");
            return 29;
        }
        if (!PackageManagerHelper.PackageStates.DISABLED.equals(hMSPackageStatesForMultiService)) {
            return 0;
        }
        HMSLog.i("AvailableAdapter", "HMS is disabled");
        return 3;
    }

    public final void a(Activity activity, AvailableCallBack availableCallBack) {
        HMSLog.i("AvailableAdapter", "<showHmsApkNotInstalledDialog> startResolution");
        if (NotInstalledHmsAdapter.getShowLock()) {
            this.f7425b = availableCallBack;
            SystemManager.getSystemNotifier().registerObserver(this.f7427d);
            activity.startActivity(BridgeActivity.getIntentStartBridgeActivity(activity, NotInstalledHmsAdapter.class.getName()));
            return;
        }
        availableCallBack.onComplete(31);
    }

    public final boolean a(Activity activity) {
        if (HMSPackageManager.getInstance(activity).getHmsVersionCode() < 40000000) {
            return false;
        }
        HMSLog.i("AvailableAdapter", "enter 4.0 HmsCore upgrade process");
        return true;
    }

    public final AvailableCallBack a() {
        return this.f7425b;
    }
}
