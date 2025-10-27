package com.huawei.hms.push;

import android.content.Context;
import com.huawei.hmf.tasks.Task;
import com.huawei.hmf.tasks.TaskCompletionSource;
import com.huawei.hms.aaid.constant.ErrorEnum;
import com.huawei.hms.aaid.task.PushClientBuilder;
import com.huawei.hms.api.Api;
import com.huawei.hms.api.HuaweiApiAvailability;
import com.huawei.hms.common.ApiException;
import com.huawei.hms.common.HuaweiApi;
import com.huawei.hms.common.internal.Preconditions;
import com.huawei.hms.push.task.ConsentTask;
import com.huawei.hms.push.utils.PushBiUtil;
import com.huawei.hms.support.api.entity.push.EnableConsentReq;
import com.huawei.hms.support.api.entity.push.PushNaming;
import com.huawei.hms.utils.JsonUtil;

/* loaded from: classes4.dex */
public class HmsConsent {

    /* renamed from: a, reason: collision with root package name */
    public HuaweiApi<Api.ApiOptions.NoOptions> f7946a;

    /* renamed from: b, reason: collision with root package name */
    public Context f7947b;

    public HmsConsent(Context context) {
        Preconditions.checkNotNull(context);
        this.f7947b = context;
        HuaweiApi<Api.ApiOptions.NoOptions> huaweiApi = new HuaweiApi<>(context, (Api<Api.ApiOptions>) new Api(HuaweiApiAvailability.HMS_API_NAME_PUSH), (Api.ApiOptions) null, new PushClientBuilder());
        this.f7946a = huaweiApi;
        huaweiApi.setKitSdkVersion(60500300);
    }

    public static HmsConsent getInstance(Context context) {
        return new HmsConsent(context);
    }

    public final Task<Void> a(boolean z2) throws ApiException {
        TaskCompletionSource taskCompletionSource;
        int externalCode;
        String strReportEntry = PushBiUtil.reportEntry(this.f7947b, PushNaming.PUSH_CONSENT);
        try {
            if (!s.d(this.f7947b)) {
                throw ErrorEnum.ERROR_OPERATION_NOT_SUPPORTED.toApiException();
            }
            EnableConsentReq enableConsentReq = new EnableConsentReq();
            enableConsentReq.setPackageName(this.f7947b.getPackageName());
            enableConsentReq.setEnable(z2);
            return this.f7946a.doWrite(new ConsentTask(PushNaming.PUSH_CONSENT, JsonUtil.createJsonString(enableConsentReq), strReportEntry));
        } catch (ApiException e2) {
            TaskCompletionSource taskCompletionSource2 = new TaskCompletionSource();
            taskCompletionSource2.setException(e2);
            externalCode = e2.getStatusCode();
            taskCompletionSource = taskCompletionSource2;
            PushBiUtil.reportExit(this.f7947b, PushNaming.PUSH_CONSENT, strReportEntry, externalCode);
            return taskCompletionSource.getTask();
        } catch (Exception unused) {
            taskCompletionSource = new TaskCompletionSource();
            ErrorEnum errorEnum = ErrorEnum.ERROR_INTERNAL_ERROR;
            taskCompletionSource.setException(errorEnum.toApiException());
            externalCode = errorEnum.getExternalCode();
            PushBiUtil.reportExit(this.f7947b, PushNaming.PUSH_CONSENT, strReportEntry, externalCode);
            return taskCompletionSource.getTask();
        }
    }

    public Task<Void> consentOff() {
        return a(false);
    }

    public Task<Void> consentOn() {
        return a(true);
    }
}
