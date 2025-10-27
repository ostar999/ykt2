package com.huawei.hms.opendevice;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import com.huawei.hms.aaid.HmsInstanceId;
import com.huawei.hms.aaid.constant.ErrorEnum;
import com.huawei.hms.common.ApiException;
import com.huawei.hms.push.constant.RemoteMessageConst;
import com.huawei.hms.support.log.HMSLog;
import com.huawei.hms.utils.Util;

/* loaded from: classes4.dex */
public class f implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    public Context f7907a;

    public f(Context context) {
        this.f7907a = context;
    }

    @Override // java.lang.Runnable
    public void run() {
        String token;
        Bundle bundle;
        try {
            int internalCode = ErrorEnum.SUCCESS.getInternalCode();
            ApiException apiException = null;
            try {
                token = HmsInstanceId.getInstance(this.f7907a).getToken(Util.getAppId(this.f7907a), null);
                try {
                    HMSLog.i("AutoInit", "Push init succeed");
                    if (TextUtils.isEmpty(token)) {
                        return;
                    }
                } catch (ApiException e2) {
                    e = e2;
                    apiException = e;
                    internalCode = apiException.getStatusCode();
                    HMSLog.e("AutoInit", "new Push init failed");
                    bundle = this.f7907a.getPackageManager().getApplicationInfo(this.f7907a.getPackageName(), 128).metaData;
                    if (bundle != null) {
                    }
                    HMSLog.i("AutoInit", "push kit sdk not exists");
                    return;
                }
            } catch (ApiException e3) {
                e = e3;
                token = null;
            }
            try {
                bundle = this.f7907a.getPackageManager().getApplicationInfo(this.f7907a.getPackageName(), 128).metaData;
                if (bundle != null || bundle.getString("com.huawei.hms.client.service.name:push") == null) {
                    HMSLog.i("AutoInit", "push kit sdk not exists");
                    return;
                }
                Intent intent = new Intent("com.huawei.push.action.MESSAGING_EVENT");
                intent.setPackage(this.f7907a.getPackageName());
                Bundle bundle2 = new Bundle();
                bundle2.putString("message_type", "new_token");
                bundle2.putString(RemoteMessageConst.DEVICE_TOKEN, token);
                bundle2.putInt("error", internalCode);
                if (apiException != null) {
                    bundle2.putSerializable("exception_key", apiException);
                }
                if (new h().a(this.f7907a, bundle2, intent)) {
                    return;
                }
                HMSLog.e("AutoInit", "start service failed");
            } catch (PackageManager.NameNotFoundException unused) {
                HMSLog.i("AutoInit", "push kit sdk not exists");
            }
        } catch (Exception e4) {
            HMSLog.e("AutoInit", "Push init failed", e4);
        }
    }
}
