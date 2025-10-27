package com.psychiatrygarden.activity.chooseSchool.util;

import android.content.Context;
import com.aliyun.sls.android.producer.Log;
import com.aliyun.sls.android.producer.LogProducerCallback;
import com.aliyun.sls.android.producer.LogProducerClient;
import com.aliyun.sls.android.producer.LogProducerConfig;
import com.aliyun.sls.android.producer.LogProducerException;
import com.aliyun.vod.log.struct.AliyunLogKey;
import com.google.gson.Gson;
import com.huawei.hms.push.HmsMessageService;
import com.just.agentweb.DefaultWebClient;
import com.mobile.auth.gatewayauth.ResultCode;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.bean.AliyunLogCredentials;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.AliyunEvent;
import com.psychiatrygarden.utils.AndroidBaseUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.DesUtil;
import com.psychiatrygarden.utils.Md5Util;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import com.yikaobang.yixue.BuildConfig;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class AliYunLogUtil {
    private static LogProducerClient chooseSchoolClient;
    private static LogProducerConfig chooseSchoolConfig;
    private static AliYunLogUtil instance;
    private boolean aliyunChooseSchoolKeyLoading = false;
    private int aliyunKeyLoadChooseSchoolTimes = 0;

    private AliYunLogUtil() {
    }

    private void chooseSchoolAddLog(String activity_class, String activity_class_alias, String start_timestamp, String end_timestamp, String target_id, String target_alias, String target_data, String event_type, String touch_name, String share_type) {
        Log log = new Log();
        log.putContent(AliyunLogKey.KEY_UUID, Md5Util.MD5Encode(AndroidBaseUtils.getIMEI(ProjectApp.instance()) + System.currentTimeMillis() + CommonUtil.generate(10) + activity_class));
        log.putContent("user_id", UserConfig.getUserId());
        log.putContent("app_id", SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, ProjectApp.instance(), "1"));
        log.putContent(HmsMessageService.SUBJECT_ID, "" + SharePreferencesUtils.readStrConfig(CommonParameter.identity_id, ProjectApp.instance()));
        log.putContent("app_type", BuildConfig.FLAVOR);
        log.putContent("app_client", "android");
        log.putContent("event_type", event_type);
        log.putContent("activity_class", activity_class);
        log.putContent("activity_class_alias", activity_class_alias);
        log.putContent("device_model", AndroidBaseUtils.getDeviceName());
        try {
            log.putContent("app_version", AndroidBaseUtils.getAPPVersionCode(ProjectApp.instance()));
        } catch (Exception unused) {
        }
        log.putContent("start_timestamp", start_timestamp);
        if (!end_timestamp.isEmpty()) {
            log.putContent("end_timestamp", end_timestamp);
        }
        if (!target_id.isEmpty()) {
            log.putContent("target_id", target_id);
        }
        if (!target_alias.isEmpty()) {
            log.putContent("target_alias", target_alias);
        }
        if (!target_data.isEmpty()) {
            log.putContent("target_data", target_data);
        }
        if (!touch_name.isEmpty()) {
            log.putContent("touch_name", touch_name);
        }
        if (!share_type.isEmpty()) {
            log.putContent("share_type", share_type);
        }
        for (Map.Entry<String, String> entry : log.getContent().entrySet()) {
            entry.getKey();
            entry.getValue();
        }
        LogProducerClient logProducerClient = chooseSchoolClient;
        if (logProducerClient != null) {
            logProducerClient.addLog(log);
        }
    }

    public static AliYunLogUtil getInstance() {
        if (instance == null) {
            synchronized (AliYunLogUtil.class) {
                if (instance == null) {
                    instance = new AliYunLogUtil();
                }
            }
        }
        return instance;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initChooseSchoolProducer(final Context context, String endpoint, String project, String logstore) {
        try {
            LogProducerConfig logProducerConfig = new LogProducerConfig(context, DefaultWebClient.HTTPS_SCHEME + endpoint, project, logstore);
            chooseSchoolConfig = logProducerConfig;
            logProducerConfig.setDropDelayLog(0);
            chooseSchoolConfig.setDropUnauthorizedLog(0);
            chooseSchoolClient = new LogProducerClient(chooseSchoolConfig, new LogProducerCallback() { // from class: com.psychiatrygarden.activity.chooseSchool.util.a
                @Override // com.aliyun.sls.android.producer.LogProducerCallback
                public final void onCall(int i2, String str, String str2, int i3, int i4) {
                    this.f11429a.lambda$initChooseSchoolProducer$0(context, i2, str, str2, i3, i4);
                }
            });
        } catch (LogProducerException e2) {
            e2.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initChooseSchoolProducer$0(Context context, int i2, String str, String str2, int i3, int i4) {
        StringBuilder sb = new StringBuilder();
        sb.append("阿里云log 信息：");
        sb.append(i2 == 0 ? ResultCode.MSG_SUCCESS : ResultCode.MSG_FAILED);
        sb.append(i2);
        sb.append("  错误信息：");
        sb.append(str2);
        android.util.Log.e("wwwwwwwwwww", sb.toString());
        if (i2 == 6 && !this.aliyunChooseSchoolKeyLoading && this.aliyunKeyLoadChooseSchoolTimes < 1) {
            getAliYunChooseSchoolCredentials(context);
        } else if (i2 == 0) {
            this.aliyunKeyLoadChooseSchoolTimes = 0;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateAccessKey(String accessKeyId, String accessKeySecret, String securityToken) {
        if (securityToken != null && !"".equals(securityToken)) {
            chooseSchoolConfig.resetSecurityToken(accessKeyId, accessKeySecret, securityToken);
        } else {
            chooseSchoolConfig.setAccessKeyId(accessKeyId);
            chooseSchoolConfig.setAccessKeySecret(accessKeySecret);
        }
    }

    public void addLog(AliyunEvent eventType, String targetId, String targetName) {
        try {
            ArrayList arrayList = new ArrayList();
            arrayList.add(targetId);
            ArrayList arrayList2 = new ArrayList();
            arrayList2.add(targetName);
            getInstance().chooseSchoolAddLog(eventType.getKey(), eventType.getValue(), System.currentTimeMillis() + "", "", new Gson().toJson(arrayList), new Gson().toJson(arrayList2), "", "2", "", "");
        } catch (Exception e2) {
            android.util.Log.d("AliYunLogUtil", "addLog: " + e2.getMessage());
        }
    }

    public void addLogParamsIsList(AliyunEvent eventType, List<String> targetId, List<String> targetName) {
        try {
            getInstance().chooseSchoolAddLog(eventType.getKey(), eventType.getValue(), System.currentTimeMillis() + "", "", new Gson().toJson(targetId), new Gson().toJson(targetName), "", "2", "", "");
        } catch (Exception e2) {
            android.util.Log.d("AliYunLogUtil", "addLog: " + e2.getMessage());
        }
    }

    public void addLogShare(AliyunEvent eventType, String share_type) {
        try {
            getInstance().chooseSchoolAddLog(eventType.getKey(), eventType.getValue(), System.currentTimeMillis() + "", "", "", "", "", "2", "", share_type);
        } catch (Exception e2) {
            android.util.Log.d("AliYunLogUtil", "addLog: " + e2.getMessage());
        }
    }

    public void addLogTouchName(AliyunEvent eventType, String touchName) {
        try {
            getInstance().chooseSchoolAddLog(eventType.getKey(), eventType.getValue(), System.currentTimeMillis() + "", "", "", "", "", "2", touchName, "");
        } catch (Exception e2) {
            android.util.Log.d("AliYunLogUtil", "addLog: " + e2.getMessage());
        }
    }

    public void getAliYunChooseSchoolCredentials(final Context mContext) {
        this.aliyunChooseSchoolKeyLoading = true;
        this.aliyunKeyLoadChooseSchoolTimes++;
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("user_id", UserConfig.getUserId());
        ajaxParams.put("scene", "choose-school-log");
        YJYHttpUtils.post(mContext, NetworkRequestsURL.aliyunCredentials, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.chooseSchool.util.AliYunLogUtil.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                android.util.Log.e("获取阿里云log数据。getAliyunCredentials", "onFailure");
                AliYunLogUtil.this.aliyunChooseSchoolKeyLoading = false;
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass1) s2);
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    android.util.Log.e("获取阿里云log数据。getAliyunCredentials 成功：", s2);
                    if (jSONObject.optString("code").equals("200")) {
                        String strDecode = DesUtil.decode(CommonParameter.DES_KEY_ALI, jSONObject.optJSONObject("data").optString(SocializeProtocolConstants.PROTOCOL_KEY_EN));
                        android.util.Log.e("获取阿里云log数据。解密：", strDecode);
                        AliyunLogCredentials aliyunLogCredentials = (AliyunLogCredentials) new Gson().fromJson(strDecode, AliyunLogCredentials.class);
                        AliYunLogUtil.this.initChooseSchoolProducer(mContext, aliyunLogCredentials.getEndpoint(), aliyunLogCredentials.getProject(), aliyunLogCredentials.getLogstore());
                        AliYunLogUtil.this.updateAccessKey(aliyunLogCredentials.getAccessKeyId(), aliyunLogCredentials.getAccessKeySecret(), aliyunLogCredentials.getSecurityToken());
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                AliYunLogUtil.this.aliyunChooseSchoolKeyLoading = false;
            }
        });
    }

    public void addLog(AliyunEvent eventType) {
        try {
            getInstance().chooseSchoolAddLog(eventType.getKey(), eventType.getValue(), System.currentTimeMillis() + "", "", "", "", "", "2", "", "");
        } catch (Exception e2) {
            android.util.Log.d("AliYunLogUtil", "addLog: " + e2.getMessage());
        }
    }
}
