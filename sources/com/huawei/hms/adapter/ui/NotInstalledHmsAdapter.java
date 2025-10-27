package com.huawei.hms.adapter.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.KeyEvent;
import com.huawei.hms.activity.IBridgeActivityDelegate;
import com.huawei.hms.adapter.sysobs.SystemManager;
import com.huawei.hms.support.log.HMSLog;
import com.huawei.hms.update.ui.NotInstalledHmsDialogHelper;

/* loaded from: classes4.dex */
public class NotInstalledHmsAdapter implements IBridgeActivityDelegate {

    /* renamed from: c, reason: collision with root package name */
    public static final Object f7435c = new Object();

    /* renamed from: d, reason: collision with root package name */
    public static boolean f7436d;

    /* renamed from: a, reason: collision with root package name */
    public Activity f7437a;

    /* renamed from: b, reason: collision with root package name */
    public Dialog f7438b;

    public static class a implements DialogInterface.OnCancelListener {

        /* renamed from: a, reason: collision with root package name */
        public final Activity f7439a;

        public a(Activity activity) {
            this.f7439a = activity;
        }

        @Override // android.content.DialogInterface.OnCancelListener
        public void onCancel(DialogInterface dialogInterface) {
            HMSLog.i("NotInstalledHmsAdapter", "<Dialog onCancel>");
            SystemManager.getInstance().notifyUpdateResult(13);
            this.f7439a.finish();
        }
    }

    public static class b implements DialogInterface.OnClickListener {

        /* renamed from: a, reason: collision with root package name */
        public final Activity f7440a;

        public b(Activity activity) {
            this.f7440a = activity;
        }

        @Override // android.content.DialogInterface.OnClickListener
        public void onClick(DialogInterface dialogInterface, int i2) {
            HMSLog.i("NotInstalledHmsAdapter", "<Dialog onClick>");
            SystemManager.getInstance().notifyUpdateResult(30);
            this.f7440a.finish();
        }
    }

    public static boolean getShowLock() {
        synchronized (f7435c) {
            HMSLog.i("NotInstalledHmsAdapter", "<canShowDialog> sIsShowingDialog: " + f7436d);
            if (f7436d) {
                return false;
            }
            f7436d = true;
            return true;
        }
    }

    public final void a(Activity activity) {
        Dialog dialog = this.f7438b;
        if (dialog != null && dialog.isShowing()) {
            this.f7438b.setOnCancelListener(null);
            this.f7438b.cancel();
        }
        this.f7438b = NotInstalledHmsDialogHelper.getDialogBuilder(activity).setPositiveButton(NotInstalledHmsDialogHelper.getConfirmResId(activity), new b(activity)).setOnCancelListener(new a(activity)).show();
    }

    @Override // com.huawei.hms.activity.IBridgeActivityDelegate
    public int getRequestCode() {
        HMSLog.i("NotInstalledHmsAdapter", "<getRequestCode>");
        return 0;
    }

    @Override // com.huawei.hms.activity.IBridgeActivityDelegate
    public void onBridgeActivityCreate(Activity activity) {
        HMSLog.i("NotInstalledHmsAdapter", "<onBridgeActivityCreate>");
        if (activity == null || activity.isFinishing()) {
            HMSLog.e("NotInstalledHmsAdapter", "<onBridgeActivityCreate> activity is null or finishing");
        } else {
            this.f7437a = activity;
            a(activity);
        }
    }

    @Override // com.huawei.hms.activity.IBridgeActivityDelegate
    public void onBridgeActivityDestroy() {
        HMSLog.i("NotInstalledHmsAdapter", "<onBridgeActivityDestroy>");
        synchronized (f7435c) {
            f7436d = false;
        }
    }

    @Override // com.huawei.hms.activity.IBridgeActivityDelegate
    public boolean onBridgeActivityResult(int i2, int i3, Intent intent) {
        HMSLog.i("NotInstalledHmsAdapter", "<onBridgeActivityResult>");
        return false;
    }

    @Override // com.huawei.hms.activity.IBridgeActivityDelegate
    public void onBridgeConfigurationChanged() {
        HMSLog.i("NotInstalledHmsAdapter", "<onBridgeConfigurationChanged>");
        Activity activity = this.f7437a;
        if (activity == null || activity.isFinishing()) {
            HMSLog.e("NotInstalledHmsAdapter", "<onBridgeConfigurationChanged> mActivity is null or finishing");
        } else {
            a(this.f7437a);
        }
    }

    @Override // com.huawei.hms.activity.IBridgeActivityDelegate
    public void onKeyUp(int i2, KeyEvent keyEvent) {
        HMSLog.i("NotInstalledHmsAdapter", "<onKeyUp>");
    }
}
