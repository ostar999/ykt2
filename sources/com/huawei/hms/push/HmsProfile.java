package com.huawei.hms.push;

import android.content.Context;
import android.text.TextUtils;
import com.huawei.agconnect.config.AGConnectServicesConfig;
import com.huawei.hmf.tasks.Task;
import com.huawei.hmf.tasks.TaskCompletionSource;
import com.huawei.hms.aaid.constant.ErrorEnum;
import com.huawei.hms.aaid.task.PushClientBuilder;
import com.huawei.hms.api.Api;
import com.huawei.hms.api.HuaweiApiAvailability;
import com.huawei.hms.common.ApiException;
import com.huawei.hms.common.HuaweiApi;
import com.huawei.hms.common.internal.Preconditions;
import com.huawei.hms.push.task.ProfileTask;
import com.huawei.hms.push.utils.PushBiUtil;
import com.huawei.hms.support.api.entity.push.ProfileReq;
import com.huawei.hms.support.api.entity.push.PushNaming;
import com.huawei.hms.support.log.HMSLog;
import com.huawei.hms.utils.JsonUtil;
import com.huawei.secure.android.common.encrypt.hash.SHA;

/* loaded from: classes4.dex */
public class HmsProfile {
    public static final int CUSTOM_PROFILE = 2;
    public static final int HUAWEI_PROFILE = 1;

    /* renamed from: a, reason: collision with root package name */
    public static final String f7953a = "HmsProfile";

    /* renamed from: b, reason: collision with root package name */
    public Context f7954b;

    /* renamed from: c, reason: collision with root package name */
    public HuaweiApi<Api.ApiOptions.NoOptions> f7955c;

    public HmsProfile(Context context) {
        this.f7954b = null;
        Preconditions.checkNotNull(context);
        this.f7954b = context;
        HuaweiApi<Api.ApiOptions.NoOptions> huaweiApi = new HuaweiApi<>(context, (Api<Api.ApiOptions>) new Api(HuaweiApiAvailability.HMS_API_NAME_PUSH), (Api.ApiOptions) null, new PushClientBuilder());
        this.f7955c = huaweiApi;
        huaweiApi.setKitSdkVersion(60500300);
    }

    public static HmsProfile getInstance(Context context) {
        return new HmsProfile(context);
    }

    public final Task<Void> a(int i2, String str, int i3, String str2) {
        if (!isSupportProfile()) {
            TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
            taskCompletionSource.setException(ErrorEnum.ERROR_OPERATION_NOT_SUPPORTED.toApiException());
            return taskCompletionSource.getTask();
        }
        if (!TextUtils.isEmpty(str)) {
            String strA = a(this.f7954b);
            if (TextUtils.isEmpty(strA)) {
                HMSLog.i(f7953a, "agc connect services config missing project id.");
                TaskCompletionSource taskCompletionSource2 = new TaskCompletionSource();
                taskCompletionSource2.setException(ErrorEnum.ERROR_MISSING_PROJECT_ID.toApiException());
                return taskCompletionSource2.getTask();
            }
            if (str.equals(strA)) {
                str = "";
            }
        }
        ProfileReq profileReq = new ProfileReq();
        if (i2 == 0) {
            profileReq.setOperation(0);
            profileReq.setType(i3);
        } else {
            profileReq.setOperation(1);
        }
        String strReportEntry = PushBiUtil.reportEntry(this.f7954b, PushNaming.PUSH_PROFILE);
        try {
            profileReq.setSubjectId(str);
            profileReq.setProfileId(SHA.sha256Encrypt(str2));
            profileReq.setPkgName(this.f7954b.getPackageName());
            return this.f7955c.doWrite(new ProfileTask(PushNaming.PUSH_PROFILE, JsonUtil.createJsonString(profileReq), strReportEntry));
        } catch (Exception e2) {
            if (e2.getCause() instanceof ApiException) {
                TaskCompletionSource taskCompletionSource3 = new TaskCompletionSource();
                ApiException apiException = (ApiException) e2.getCause();
                taskCompletionSource3.setException(apiException);
                PushBiUtil.reportExit(this.f7954b, PushNaming.PUSH_PROFILE, strReportEntry, apiException.getStatusCode());
                return taskCompletionSource3.getTask();
            }
            TaskCompletionSource taskCompletionSource4 = new TaskCompletionSource();
            Context context = this.f7954b;
            ErrorEnum errorEnum = ErrorEnum.ERROR_INTERNAL_ERROR;
            PushBiUtil.reportExit(context, PushNaming.PUSH_PROFILE, strReportEntry, errorEnum);
            taskCompletionSource4.setException(errorEnum.toApiException());
            return taskCompletionSource4.getTask();
        }
    }

    public Task<Void> addProfile(int i2, String str) {
        return addProfile("", i2, str);
    }

    public final boolean b(Context context) {
        return s.b(context) >= 110001400;
    }

    public Task<Void> deleteProfile(String str) {
        return deleteProfile("", str);
    }

    public boolean isSupportProfile() {
        if (!s.d(this.f7954b)) {
            return true;
        }
        if (s.c()) {
            HMSLog.i(f7953a, "current EMUI version below 9.1, not support profile operation.");
            return false;
        }
        if (b(this.f7954b)) {
            return true;
        }
        HMSLog.i(f7953a, "current HwPushService.apk version below 11.0.1.400,please upgrade your HwPushService.apk version.");
        return false;
    }

    public Task<Void> addProfile(String str, int i2, String str2) {
        if (i2 != 1 && i2 != 2) {
            HMSLog.i(f7953a, "add profile type undefined.");
            TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
            taskCompletionSource.setException(ErrorEnum.ERROR_PUSH_ARGUMENTS_INVALID.toApiException());
            return taskCompletionSource.getTask();
        }
        if (!TextUtils.isEmpty(str2)) {
            return a(0, str, i2, str2);
        }
        HMSLog.i(f7953a, "add profile params is empty.");
        TaskCompletionSource taskCompletionSource2 = new TaskCompletionSource();
        taskCompletionSource2.setException(ErrorEnum.ERROR_PUSH_ARGUMENTS_INVALID.toApiException());
        return taskCompletionSource2.getTask();
    }

    public Task<Void> deleteProfile(String str, String str2) {
        if (!TextUtils.isEmpty(str2)) {
            return a(1, str, -1, str2);
        }
        HMSLog.e(f7953a, "del profile params is empty.");
        TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
        taskCompletionSource.setException(ErrorEnum.ERROR_PUSH_ARGUMENTS_INVALID.toApiException());
        return taskCompletionSource.getTask();
    }

    public static String a(Context context) {
        return AGConnectServicesConfig.fromContext(context).getString("client/project_id");
    }
}
