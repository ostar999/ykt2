package com.huawei.hms.push;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import com.github.liuyueyi.quick.transfer.dictionary.DictionaryFactory;
import com.huawei.hmf.tasks.Task;
import com.huawei.hmf.tasks.TaskCompletionSource;
import com.huawei.hmf.tasks.Tasks;
import com.huawei.hms.aaid.constant.ErrorEnum;
import com.huawei.hms.aaid.encrypt.PushEncrypter;
import com.huawei.hms.aaid.init.AutoInitHelper;
import com.huawei.hms.aaid.plugin.ProxyCenter;
import com.huawei.hms.aaid.task.PushClientBuilder;
import com.huawei.hms.aaid.utils.BaseUtils;
import com.huawei.hms.aaid.utils.PushPreferences;
import com.huawei.hms.android.HwBuildEx;
import com.huawei.hms.api.Api;
import com.huawei.hms.api.HuaweiApiAvailability;
import com.huawei.hms.common.ApiException;
import com.huawei.hms.common.HuaweiApi;
import com.huawei.hms.common.internal.Preconditions;
import com.huawei.hms.push.task.BaseVoidTask;
import com.huawei.hms.push.task.IntentCallable;
import com.huawei.hms.push.task.SendUpStreamTask;
import com.huawei.hms.push.task.SubscribeTask;
import com.huawei.hms.push.utils.PushBiUtil;
import com.huawei.hms.support.api.entity.push.EnableNotifyReq;
import com.huawei.hms.support.api.entity.push.PushNaming;
import com.huawei.hms.support.api.entity.push.SubscribeReq;
import com.huawei.hms.support.api.entity.push.UpSendMsgReq;
import com.huawei.hms.support.log.HMSLog;
import com.huawei.hms.utils.JsonUtil;
import com.huawei.hms.utils.NetWorkUtil;
import com.vivo.push.PushClientConstants;
import java.util.regex.Pattern;

/* loaded from: classes4.dex */
public class HmsMessaging {
    public static final String DEFAULT_TOKEN_SCOPE = "HCM";

    /* renamed from: a, reason: collision with root package name */
    public static final Pattern f7950a = Pattern.compile("[\\u4e00-\\u9fa5\\w-_.~%]{1,900}");

    /* renamed from: b, reason: collision with root package name */
    public Context f7951b;

    /* renamed from: c, reason: collision with root package name */
    public HuaweiApi<Api.ApiOptions.NoOptions> f7952c;

    public HmsMessaging(Context context) {
        Preconditions.checkNotNull(context);
        this.f7951b = context;
        HuaweiApi<Api.ApiOptions.NoOptions> huaweiApi = new HuaweiApi<>(context, (Api<Api.ApiOptions>) new Api(HuaweiApiAvailability.HMS_API_NAME_PUSH), (Api.ApiOptions) null, new PushClientBuilder());
        this.f7952c = huaweiApi;
        huaweiApi.setKitSdkVersion(60500300);
    }

    public static synchronized HmsMessaging getInstance(Context context) {
        return new HmsMessaging(context);
    }

    public final Task<Void> a(String str, String str2) throws ApiException {
        String strReportEntry = PushBiUtil.reportEntry(this.f7951b, PushNaming.SUBSCRIBE);
        if (str == null || !f7950a.matcher(str).matches()) {
            PushBiUtil.reportExit(this.f7951b, PushNaming.SUBSCRIBE, strReportEntry, ErrorEnum.ERROR_ARGUMENTS_INVALID);
            HMSLog.e("HmsMessaging", "Invalid topic: topic should match the format:[\\u4e00-\\u9fa5\\w-_.~%]{1,900}");
            throw new IllegalArgumentException("Invalid topic: topic should match the format:[\\u4e00-\\u9fa5\\w-_.~%]{1,900}");
        }
        if (ProxyCenter.getProxy() != null) {
            HMSLog.i("HmsMessaging", "use proxy subscribe.");
            return TextUtils.equals(str2, "Sub") ? ProxyCenter.getProxy().subscribe(this.f7951b, str, strReportEntry) : ProxyCenter.getProxy().unsubscribe(this.f7951b, str, strReportEntry);
        }
        try {
            ErrorEnum errorEnumA = d.a(this.f7951b);
            if (errorEnumA != ErrorEnum.SUCCESS) {
                throw errorEnumA.toApiException();
            }
            if (NetWorkUtil.getNetworkType(this.f7951b) == 0) {
                HMSLog.e("HmsMessaging", "no network");
                throw ErrorEnum.ERROR_NO_NETWORK.toApiException();
            }
            SubscribeReq subscribeReq = new SubscribeReq(this.f7951b, str2, str);
            subscribeReq.setToken(BaseUtils.getLocalToken(this.f7951b, null));
            return s.b() ? this.f7952c.doWrite(new BaseVoidTask(PushNaming.SUBSCRIBE, JsonUtil.createJsonString(subscribeReq), strReportEntry)) : this.f7952c.doWrite(new SubscribeTask(PushNaming.SUBSCRIBE, JsonUtil.createJsonString(subscribeReq), strReportEntry));
        } catch (ApiException e2) {
            TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
            taskCompletionSource.setException(e2);
            PushBiUtil.reportExit(this.f7951b, PushNaming.SUBSCRIBE, strReportEntry, e2.getStatusCode());
            return taskCompletionSource.getTask();
        } catch (Exception unused) {
            TaskCompletionSource taskCompletionSource2 = new TaskCompletionSource();
            ErrorEnum errorEnum = ErrorEnum.ERROR_INTERNAL_ERROR;
            taskCompletionSource2.setException(errorEnum.toApiException());
            PushBiUtil.reportExit(this.f7951b, PushNaming.SUBSCRIBE, strReportEntry, errorEnum);
            return taskCompletionSource2.getTask();
        }
    }

    public boolean isAutoInitEnabled() {
        return AutoInitHelper.isAutoInitEnabled(this.f7951b);
    }

    public void send(RemoteMessage remoteMessage) {
        if (ProxyCenter.getProxy() != null) {
            HMSLog.e("HmsMessaging", "Operation(send) unsupported");
            throw new UnsupportedOperationException("Operation(send) unsupported");
        }
        HMSLog.i("HmsMessaging", "send upstream message");
        a(remoteMessage);
    }

    public void setAutoInitEnabled(boolean z2) {
        AutoInitHelper.setAutoInitEnabled(this.f7951b, z2);
    }

    public Task<Void> subscribe(String str) {
        HMSLog.i("HmsMessaging", "invoke subscribe");
        return a(str, "Sub");
    }

    public Task<Void> turnOffPush() {
        if (ProxyCenter.getProxy() != null) {
            HMSLog.i("HmsMessaging", "turn off for proxy");
            return ProxyCenter.getProxy().turnOff(this.f7951b, null);
        }
        HMSLog.i("HmsMessaging", "invoke turnOffPush");
        return a(false);
    }

    public Task<Void> turnOnPush() {
        if (ProxyCenter.getProxy() != null) {
            HMSLog.i("HmsMessaging", "turn on for proxy");
            return ProxyCenter.getProxy().turnOn(this.f7951b, null);
        }
        HMSLog.i("HmsMessaging", "invoke turnOnPush");
        return a(true);
    }

    public Task<Void> unsubscribe(String str) {
        HMSLog.i("HmsMessaging", "invoke unsubscribe");
        return a(str, "UnSub");
    }

    public final void a(RemoteMessage remoteMessage) {
        String strReportEntry = PushBiUtil.reportEntry(this.f7951b, PushNaming.UPSEND_MSG);
        ErrorEnum errorEnumA = d.a(this.f7951b);
        if (errorEnumA == ErrorEnum.SUCCESS) {
            if (!TextUtils.isEmpty(remoteMessage.getTo())) {
                if (!TextUtils.isEmpty(remoteMessage.getMessageId())) {
                    if (!TextUtils.isEmpty(remoteMessage.getData())) {
                        UpSendMsgReq upSendMsgReq = new UpSendMsgReq();
                        upSendMsgReq.setPackageName(this.f7951b.getPackageName());
                        upSendMsgReq.setMessageId(remoteMessage.getMessageId());
                        upSendMsgReq.setTo(remoteMessage.getTo());
                        upSendMsgReq.setData(remoteMessage.getData());
                        upSendMsgReq.setMessageType(remoteMessage.getMessageType());
                        upSendMsgReq.setTtl(remoteMessage.getTtl());
                        upSendMsgReq.setCollapseKey(remoteMessage.getCollapseKey());
                        upSendMsgReq.setSendMode(remoteMessage.getSendMode());
                        upSendMsgReq.setReceiptMode(remoteMessage.getReceiptMode());
                        if (s.b()) {
                            this.f7952c.doWrite(new BaseVoidTask(PushNaming.UPSEND_MSG, JsonUtil.createJsonString(upSendMsgReq), strReportEntry));
                            return;
                        } else {
                            a(upSendMsgReq, strReportEntry);
                            return;
                        }
                    }
                    HMSLog.e("HmsMessaging", "Mandatory parameter 'data' missing");
                    PushBiUtil.reportExit(this.f7951b, PushNaming.UPSEND_MSG, strReportEntry, ErrorEnum.ERROR_ARGUMENTS_INVALID);
                    throw new IllegalArgumentException("Mandatory parameter 'data' missing");
                }
                HMSLog.e("HmsMessaging", "Mandatory parameter 'message_id' missing");
                PushBiUtil.reportExit(this.f7951b, PushNaming.UPSEND_MSG, strReportEntry, ErrorEnum.ERROR_ARGUMENTS_INVALID);
                throw new IllegalArgumentException("Mandatory parameter 'message_id' missing");
            }
            HMSLog.e("HmsMessaging", "Mandatory parameter 'to' missing");
            PushBiUtil.reportExit(this.f7951b, PushNaming.UPSEND_MSG, strReportEntry, ErrorEnum.ERROR_ARGUMENTS_INVALID);
            throw new IllegalArgumentException("Mandatory parameter 'to' missing");
        }
        HMSLog.e("HmsMessaging", "Message sent failed:" + errorEnumA.getExternalCode() + ':' + errorEnumA.getMessage());
        PushBiUtil.reportExit(this.f7951b, PushNaming.UPSEND_MSG, strReportEntry, errorEnumA);
        throw new UnsupportedOperationException(errorEnumA.getMessage());
    }

    public final Task<Void> a(boolean z2) {
        String strReportEntry = PushBiUtil.reportEntry(this.f7951b, PushNaming.SET_NOTIFY_FLAG);
        if (s.d(this.f7951b) && !s.b()) {
            if (HwBuildEx.VERSION.EMUI_SDK_INT < 12) {
                HMSLog.e("HmsMessaging", "operation not available on Huawei device with EMUI lower than 5.1");
                TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
                ErrorEnum errorEnum = ErrorEnum.ERROR_OPERATION_NOT_SUPPORTED;
                taskCompletionSource.setException(errorEnum.toApiException());
                PushBiUtil.reportExit(this.f7951b, PushNaming.SET_NOTIFY_FLAG, strReportEntry, errorEnum);
                return taskCompletionSource.getTask();
            }
            if (s.b(this.f7951b) < 90101310) {
                HMSLog.i("HmsMessaging", "turn on/off with broadcast v1");
                Intent intentPutExtra = new Intent("com.huawei.intent.action.SELF_SHOW_FLAG").putExtra("enalbeFlag", PushEncrypter.encrypterOld(this.f7951b, this.f7951b.getPackageName() + DictionaryFactory.SHARP + z2));
                intentPutExtra.setPackage("android");
                return Tasks.callInBackground(new IntentCallable(this.f7951b, intentPutExtra, strReportEntry));
            }
            HMSLog.i("HmsMessaging", "turn on/off with broadcast v2");
            new PushPreferences(this.f7951b, "push_notify_flag").saveBoolean("notify_msg_enable", !z2);
            Uri uri = Uri.parse("content://" + this.f7951b.getPackageName() + ".huawei.push.provider/push_notify_flag.xml");
            Intent intent = new Intent("com.huawei.android.push.intent.SDK_COMMAND");
            intent.putExtra("type", "enalbeFlag");
            intent.putExtra(PushClientConstants.TAG_PKG_NAME, this.f7951b.getPackageName());
            intent.putExtra("url", uri);
            intent.setPackage("android");
            return Tasks.callInBackground(new IntentCallable(this.f7951b, intent, strReportEntry));
        }
        HMSLog.i("HmsMessaging", "turn on/off with AIDL");
        EnableNotifyReq enableNotifyReq = new EnableNotifyReq();
        enableNotifyReq.setPackageName(this.f7951b.getPackageName());
        enableNotifyReq.setEnable(z2);
        return this.f7952c.doWrite(new BaseVoidTask(PushNaming.SET_NOTIFY_FLAG, JsonUtil.createJsonString(enableNotifyReq), strReportEntry));
    }

    public final void a(UpSendMsgReq upSendMsgReq, String str) {
        upSendMsgReq.setToken(BaseUtils.getLocalToken(this.f7951b, null));
        try {
            this.f7952c.doWrite(new SendUpStreamTask(PushNaming.UPSEND_MSG, JsonUtil.createJsonString(upSendMsgReq), str, upSendMsgReq.getPackageName(), upSendMsgReq.getMessageId()));
        } catch (Exception e2) {
            if (e2.getCause() instanceof ApiException) {
                PushBiUtil.reportExit(this.f7951b, PushNaming.UPSEND_MSG, str, ((ApiException) e2.getCause()).getStatusCode());
            } else {
                PushBiUtil.reportExit(this.f7951b, PushNaming.UPSEND_MSG, str, ErrorEnum.ERROR_INTERNAL_ERROR);
            }
        }
    }
}
