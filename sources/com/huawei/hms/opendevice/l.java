package com.huawei.hms.opendevice;

import android.content.Context;
import android.text.TextUtils;
import com.huawei.hmf.tasks.TaskCompletionSource;
import com.huawei.hms.aaid.HmsInstanceId;
import com.huawei.hms.aaid.constant.ErrorEnum;
import com.huawei.hms.aaid.entity.TokenReq;
import com.huawei.hms.aaid.entity.TokenResp;
import com.huawei.hms.aaid.entity.TokenResult;
import com.huawei.hms.aaid.task.PushClient;
import com.huawei.hms.common.ApiException;
import com.huawei.hms.common.internal.ResponseErrorCode;
import com.huawei.hms.common.internal.TaskApiCall;
import com.huawei.hms.support.api.client.Status;
import com.huawei.hms.support.log.HMSLog;
import com.huawei.hms.utils.JsonUtil;

/* loaded from: classes4.dex */
public class l extends TaskApiCall<PushClient, TokenResult> {

    /* renamed from: a, reason: collision with root package name */
    public Context f7916a;

    /* renamed from: b, reason: collision with root package name */
    public TokenReq f7917b;

    public l(String str, TokenReq tokenReq, Context context, String str2) {
        super(str, JsonUtil.createJsonString(tokenReq), str2);
        this.f7916a = context;
        this.f7917b = tokenReq;
    }

    @Override // com.huawei.hms.common.internal.TaskApiCall
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public void doExecute(PushClient pushClient, ResponseErrorCode responseErrorCode, String str, TaskCompletionSource<TokenResult> taskCompletionSource) {
        if (responseErrorCode.getErrorCode() != 0) {
            HMSLog.e(HmsInstanceId.TAG, "TokenTask failed, ErrorCode:" + responseErrorCode.getErrorCode());
            a(responseErrorCode, taskCompletionSource);
        } else {
            TokenResp tokenResp = (TokenResp) JsonUtil.jsonToEntity(str, new TokenResp());
            ErrorEnum errorEnumFromCode = ErrorEnum.fromCode(tokenResp.getRetCode());
            if (errorEnumFromCode != ErrorEnum.SUCCESS) {
                taskCompletionSource.setException(errorEnumFromCode.toApiException());
                HMSLog.e(HmsInstanceId.TAG, "TokenTask failed, StatusCode:" + errorEnumFromCode.getExternalCode());
            } else {
                TokenResult tokenResult = new TokenResult();
                tokenResult.setToken(tokenResp.getToken());
                tokenResult.setBelongId(tokenResp.getBelongId());
                tokenResult.setRetCode(ErrorEnum.fromCode(tokenResp.getRetCode()).getExternalCode());
                taskCompletionSource.setResult(tokenResult);
                String token = tokenResp.getToken();
                if (TextUtils.isEmpty(token)) {
                    HMSLog.i(HmsInstanceId.TAG, "GetTokenTask receive a empty token, please check HmsMessageService.onNewToken receive result.");
                    q.a(pushClient.getContext(), getUri(), responseErrorCode);
                    return;
                } else {
                    a(token, this.f7917b.getSubjectId());
                    n.a(this.f7916a, token);
                }
            }
        }
        q.a(pushClient.getContext(), getUri(), responseErrorCode);
    }

    @Override // com.huawei.hms.common.internal.TaskApiCall
    public int getMinApkVersion() {
        return this.f7917b.isMultiSender() ? 50004300 : 30000000;
    }

    public final void a(ResponseErrorCode responseErrorCode, TaskCompletionSource<TokenResult> taskCompletionSource) {
        ErrorEnum errorEnumFromCode = ErrorEnum.fromCode(responseErrorCode.getErrorCode());
        if (errorEnumFromCode != ErrorEnum.ERROR_UNKNOWN) {
            taskCompletionSource.setException(errorEnumFromCode.toApiException());
        } else {
            taskCompletionSource.setException(new ApiException(new Status(responseErrorCode.getErrorCode(), responseErrorCode.getErrorReason())));
        }
    }

    public final void a(String str, String str2) {
        if (i.a(this.f7916a).b(str2).equals(str)) {
            return;
        }
        HMSLog.i(HmsInstanceId.TAG, "receive a token, refresh the local token");
        i.a(this.f7916a).b(str2, str);
    }
}
