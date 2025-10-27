package com.huawei.hms.aaid;

import android.content.Context;
import com.huawei.hmf.tasks.Task;
import com.huawei.hmf.tasks.TaskCompletionSource;
import com.huawei.hms.aaid.constant.ErrorEnum;
import com.huawei.hms.aaid.entity.TokenReq;
import com.huawei.hms.aaid.entity.TokenResult;
import com.huawei.hms.aaid.plugin.ProxyCenter;
import com.huawei.hms.aaid.task.PushClientBuilder;
import com.huawei.hms.aaid.utils.PushPreferences;
import com.huawei.hms.api.Api;
import com.huawei.hms.api.HuaweiApiAvailability;
import com.huawei.hms.common.ApiException;
import com.huawei.hms.common.HuaweiApi;
import com.huawei.hms.common.internal.Preconditions;
import com.huawei.hms.opendevice.l;
import com.huawei.hms.opendevice.o;
import com.huawei.hms.opendevice.q;
import com.huawei.hms.support.log.HMSLog;
import java.util.UUID;

@Deprecated
/* loaded from: classes4.dex */
public class HmsInstanceIdEx {
    public static final String TAG = "HmsInstanceIdEx";

    /* renamed from: a, reason: collision with root package name */
    public Context f7416a;

    /* renamed from: b, reason: collision with root package name */
    public PushPreferences f7417b;

    /* renamed from: c, reason: collision with root package name */
    public HuaweiApi<Api.ApiOptions.NoOptions> f7418c;

    public HmsInstanceIdEx(Context context) {
        this.f7417b = null;
        this.f7416a = context;
        this.f7417b = new PushPreferences(context, "aaid");
        HuaweiApi<Api.ApiOptions.NoOptions> huaweiApi = new HuaweiApi<>(context, (Api<Api.ApiOptions>) new Api(HuaweiApiAvailability.HMS_API_NAME_PUSH), (Api.ApiOptions) null, new PushClientBuilder());
        this.f7418c = huaweiApi;
        huaweiApi.setKitSdkVersion(60300305);
    }

    public static HmsInstanceIdEx getInstance(Context context) {
        Preconditions.checkNotNull(context);
        return new HmsInstanceIdEx(context);
    }

    public final String a(String str) {
        return "creationTime" + str;
    }

    public void deleteAAID(String str) throws ApiException {
        if (str == null) {
            throw ErrorEnum.ERROR_ARGUMENTS_INVALID.toApiException();
        }
        try {
            if (this.f7417b.containsKey(str)) {
                this.f7417b.removeKey(str);
                this.f7417b.removeKey(a(str));
            }
        } catch (RuntimeException unused) {
            throw ErrorEnum.ERROR_INTERNAL_ERROR.toApiException();
        } catch (Exception unused2) {
            throw ErrorEnum.ERROR_INTERNAL_ERROR.toApiException();
        }
    }

    public String getAAId(String str) throws ApiException {
        if (str == null) {
            throw ErrorEnum.ERROR_ARGUMENTS_INVALID.toApiException();
        }
        try {
            if (this.f7417b.containsKey(str)) {
                return this.f7417b.getString(str);
            }
            String string = UUID.randomUUID().toString();
            this.f7417b.saveString(str, string);
            this.f7417b.saveLong(a(str), Long.valueOf(System.currentTimeMillis()));
            return string;
        } catch (RuntimeException unused) {
            throw ErrorEnum.ERROR_INTERNAL_ERROR.toApiException();
        } catch (Exception unused2) {
            throw ErrorEnum.ERROR_INTERNAL_ERROR.toApiException();
        }
    }

    public long getCreationTime(String str) throws ApiException {
        if (str == null) {
            throw ErrorEnum.ERROR_ARGUMENTS_INVALID.toApiException();
        }
        try {
            if (!this.f7417b.containsKey(a(str))) {
                getAAId(str);
            }
            return this.f7417b.getLong(a(str));
        } catch (RuntimeException unused) {
            throw ErrorEnum.ERROR_INTERNAL_ERROR.toApiException();
        } catch (Exception unused2) {
            throw ErrorEnum.ERROR_INTERNAL_ERROR.toApiException();
        }
    }

    public Task<TokenResult> getToken() {
        if (ProxyCenter.getProxy() != null) {
            try {
                HMSLog.i(TAG, "use proxy get token, please check HmsMessageService.onNewToken receive result.");
                ProxyCenter.getProxy().getToken(this.f7416a, null, null);
                TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
                taskCompletionSource.setResult(new TokenResult());
                return taskCompletionSource.getTask();
            } catch (ApiException e2) {
                return a(e2);
            } catch (Exception unused) {
                return a(ErrorEnum.ERROR_INTERNAL_ERROR.toApiException());
            }
        }
        String strA = q.a(this.f7416a, "push.gettoken");
        try {
            TokenReq tokenReqB = o.b(this.f7416a, null, null);
            tokenReqB.setAaid(HmsInstanceId.getInstance(this.f7416a).getId());
            return this.f7418c.doWrite(new l("push.gettoken", tokenReqB, this.f7416a, strA));
        } catch (RuntimeException unused2) {
            Context context = this.f7416a;
            ErrorEnum errorEnum = ErrorEnum.ERROR_INTERNAL_ERROR;
            q.a(context, "push.gettoken", strA, errorEnum);
            return a(errorEnum.toApiException());
        } catch (Exception unused3) {
            Context context2 = this.f7416a;
            ErrorEnum errorEnum2 = ErrorEnum.ERROR_INTERNAL_ERROR;
            q.a(context2, "push.gettoken", strA, errorEnum2);
            return a(errorEnum2.toApiException());
        }
    }

    public final Task<TokenResult> a(Exception exc) {
        TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
        taskCompletionSource.setException(exc);
        return taskCompletionSource.getTask();
    }
}
