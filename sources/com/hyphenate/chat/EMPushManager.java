package com.hyphenate.chat;

import android.text.TextUtils;
import android.util.Pair;
import com.huawei.hms.push.constant.RemoteMessageConst;
import com.hyphenate.EMCallBack;
import com.hyphenate.EMValueCallBack;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.adapter.EMAError;
import com.hyphenate.chat.adapter.EMAPushConfigs;
import com.hyphenate.chat.adapter.EMAPushManager;
import com.hyphenate.chat.adapter.EMASilentModeItem;
import com.hyphenate.chat.adapter.EMASilentModeParam;
import com.hyphenate.cloud.EMHttpClient;
import com.hyphenate.exceptions.HyphenateException;
import com.hyphenate.util.DeviceUuidFactory;
import com.hyphenate.util.EMLog;
import com.plv.livescenes.linkmic.manager.PLVLinkMicManager;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class EMPushManager {
    private static final String TAG = "EMPushManager";
    EMAPushManager emaObject;
    EMClient mClient;

    public enum DisplayStyle {
        SimpleBanner,
        MessageSummary
    }

    public enum EMPushAction {
        ARRIVE("arrive"),
        CLICK("click");

        private String name;

        EMPushAction(String str) {
            this.name = str;
        }
    }

    public enum EMPushRemindType {
        ALL,
        MENTION_ONLY,
        NONE
    }

    public EMPushManager(EMClient eMClient, EMAPushManager eMAPushManager) {
        this.emaObject = eMAPushManager;
        this.mClient = eMClient;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleError(EMAError eMAError) throws HyphenateException {
        if (eMAError.errCode() != 0) {
            throw new HyphenateException(eMAError);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$bindDeviceToken$0(String str, String str2, EMCallBack eMCallBack) {
        try {
            bindDeviceToken(str, str2);
            if (eMCallBack != null) {
                eMCallBack.onSuccess();
            }
        } catch (HyphenateException e2) {
            if (eMCallBack != null) {
                eMCallBack.onError(e2.getErrorCode(), e2.getDescription());
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void reportPushAction(String str) throws HyphenateException, IllegalArgumentException {
        EMAError eMAError = new EMAError();
        this.emaObject.reportPushAction(str, eMAError);
        handleError(eMAError);
    }

    public void asyncUpdatePushDisplayStyle(final DisplayStyle displayStyle, final EMCallBack eMCallBack) {
        EMClient.getInstance().execute(new Runnable() { // from class: com.hyphenate.chat.EMPushManager.2
            @Override // java.lang.Runnable
            public void run() {
                EMCallBack eMCallBack2;
                int errorCode;
                String message;
                try {
                    EMPushManager.this.updatePushDisplayStyle(displayStyle);
                    eMCallBack.onSuccess();
                } catch (HyphenateException e2) {
                    eMCallBack2 = eMCallBack;
                    errorCode = e2.getErrorCode();
                    message = e2.getDescription();
                    eMCallBack2.onError(errorCode, message);
                } catch (IllegalArgumentException e3) {
                    eMCallBack2 = eMCallBack;
                    errorCode = 205;
                    message = e3.getMessage();
                    eMCallBack2.onError(errorCode, message);
                }
            }
        });
    }

    public void asyncUpdatePushNickname(final String str, final EMCallBack eMCallBack) {
        EMClient.getInstance().execute(new Runnable() { // from class: com.hyphenate.chat.EMPushManager.1
            @Override // java.lang.Runnable
            public void run() {
                EMCallBack eMCallBack2;
                int errorCode;
                String message;
                try {
                    EMPushManager.this.updatePushNickname(str);
                    eMCallBack.onSuccess();
                } catch (HyphenateException e2) {
                    eMCallBack2 = eMCallBack;
                    errorCode = e2.getErrorCode();
                    message = e2.getDescription();
                    eMCallBack2.onError(errorCode, message);
                } catch (IllegalArgumentException e3) {
                    eMCallBack2 = eMCallBack;
                    errorCode = 205;
                    message = e3.getMessage();
                    eMCallBack2.onError(errorCode, message);
                }
            }
        });
    }

    public synchronized void bindDeviceToken(String str, String str2) throws HyphenateException {
        HyphenateException e2;
        String str3;
        String str4;
        String str5;
        EMClient eMClient = this.mClient;
        if (eMClient == null || !eMClient.isSdkInited()) {
            throw new HyphenateException(1, "SDK should init first!");
        }
        if (!this.mClient.isLoggedInBefore()) {
            throw new HyphenateException(1, "You need to log in first!");
        }
        if (TextUtils.isEmpty(str)) {
            throw new HyphenateException(110, "Notifier name should not be empty!");
        }
        String strQ = com.hyphenate.chat.core.a.a().q();
        if (!TextUtils.isEmpty(strQ) && TextUtils.equals(strQ, str2)) {
            if (!EMClient.getInstance().getChatConfigPrivate().I()) {
                String str6 = TAG;
                EMLog.e(str6, str6 + " not first login, ignore token upload action.");
                return;
            }
            EMLog.d(TAG, "push token not change, but last login is not on this device, upload to server");
        }
        String str7 = EMClient.getInstance().getChatConfigPrivate().a(true, false) + "/users/" + EMClient.getInstance().getCurrentUser();
        DeviceUuidFactory deviceUuidFactory = new DeviceUuidFactory(EMClient.getInstance().getContext());
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(RemoteMessageConst.DEVICE_TOKEN, TextUtils.isEmpty(str) ? "" : str2);
            jSONObject.put("notifier_name", str);
            jSONObject.put("device_id", deviceUuidFactory.getDeviceUuid().toString());
            String str8 = "";
            int i2 = 2;
            int iIntValue = 1;
            do {
                try {
                    str5 = TAG;
                    EMLog.e(str5, "uploadTokenInternal, token=" + str2 + ", url=" + str7 + ", notifier name=" + str);
                    Pair<Integer, String> pairSendRequestWithToken = EMHttpClient.getInstance().sendRequestWithToken(str7, jSONObject.toString(), EMHttpClient.PUT);
                    iIntValue = ((Integer) pairSendRequestWithToken.first).intValue();
                    str3 = (String) pairSendRequestWithToken.second;
                } catch (HyphenateException e3) {
                    String str9 = str8;
                    e2 = e3;
                    str3 = str9;
                }
                if (iIntValue == 200) {
                    com.hyphenate.chat.core.a.a().h(str2);
                    com.hyphenate.chat.core.a.a().i(str);
                    EMLog.e(str5, "uploadTokenInternal success.");
                    return;
                }
                try {
                    EMLog.e(str5, "uploadTokenInternal failed: " + str3);
                    str4 = EMClient.getInstance().getChatConfigPrivate().a(true, true) + "/users/" + EMClient.getInstance().getCurrentUser();
                } catch (HyphenateException e4) {
                    e2 = e4;
                }
                String str10 = str4;
                str8 = str3;
                str7 = str10;
                i2--;
                e2 = e4;
                EMLog.e(TAG, "uploadTokenInternal failed: " + e2.getDescription());
                str4 = EMClient.getInstance().getChatConfigPrivate().a(true, true) + "/users/" + EMClient.getInstance().getCurrentUser();
                String str102 = str4;
                str8 = str3;
                str7 = str102;
                i2--;
            } while (i2 > 0);
            throw new HyphenateException(iIntValue, str8);
        } catch (Exception e5) {
            EMLog.e(TAG, "uploadTokenInternal put json exception: " + e5.toString());
            throw new HyphenateException(1, "uploadTokenInternal put json exception: " + e5.getMessage());
        }
    }

    public synchronized void bindDeviceToken(final String str, final String str2, final EMCallBack eMCallBack) {
        EMClient eMClient = this.mClient;
        if (eMClient != null && eMClient.isSdkInited()) {
            this.mClient.execute(new Runnable() { // from class: com.hyphenate.chat.h
                @Override // java.lang.Runnable
                public final void run() {
                    this.f8590c.lambda$bindDeviceToken$0(str, str2, eMCallBack);
                }
            });
            return;
        }
        if (eMCallBack != null) {
            eMCallBack.onError(1, "SDK should init first!");
        }
    }

    public void clearRemindTypeForConversation(final String str, final EMConversation.EMConversationType eMConversationType, final EMCallBack eMCallBack) {
        this.mClient.execute(new Runnable() { // from class: com.hyphenate.chat.EMPushManager.4
            @Override // java.lang.Runnable
            public void run() {
                try {
                    EMAError eMAError = new EMAError();
                    EMPushManager.this.emaObject.clearRemindTypeForConversation(str, eMConversationType.ordinal(), eMAError);
                    EMPushManager.this.handleError(eMAError);
                    eMCallBack.onSuccess();
                } catch (HyphenateException e2) {
                    eMCallBack.onError(e2.getErrorCode(), e2.getDescription());
                }
            }
        });
    }

    @Deprecated
    public void disableOfflinePush(int i2, int i3) throws HyphenateException {
        EMAError eMAError = new EMAError();
        this.emaObject.disableOfflineNotification(i2, i3, eMAError);
        handleError(eMAError);
    }

    @Deprecated
    public void enableOfflinePush() throws HyphenateException {
        EMAError eMAError = new EMAError();
        this.emaObject.enableOfflineNotification(eMAError);
        handleError(eMAError);
    }

    @Deprecated
    public List<String> getNoPushGroups() {
        return this.emaObject.getNoPushGroups();
    }

    @Deprecated
    public List<String> getNoPushUsers() {
        return this.emaObject.getNoPushUsers();
    }

    public void getPreferredNotificationLanguage(final EMValueCallBack<String> eMValueCallBack) {
        this.mClient.execute(new Runnable() { // from class: com.hyphenate.chat.EMPushManager.10
            @Override // java.lang.Runnable
            public void run() {
                try {
                    EMAError eMAError = new EMAError();
                    String pushPerformLanguage = EMPushManager.this.emaObject.getPushPerformLanguage(eMAError);
                    EMPushManager.this.handleError(eMAError);
                    eMValueCallBack.onSuccess(pushPerformLanguage);
                } catch (HyphenateException e2) {
                    eMValueCallBack.onError(e2.getErrorCode(), e2.getDescription());
                }
            }
        });
    }

    public EMPushConfigs getPushConfigs() {
        EMAPushConfigs pushConfigs = this.emaObject.getPushConfigs();
        if (pushConfigs == null) {
            return null;
        }
        return new EMPushConfigs(pushConfigs);
    }

    public EMPushConfigs getPushConfigsFromServer() throws HyphenateException {
        EMAError eMAError = new EMAError();
        EMAPushConfigs pushConfigsFromServer = this.emaObject.getPushConfigsFromServer(eMAError);
        handleError(eMAError);
        return new EMPushConfigs(pushConfigsFromServer);
    }

    public void getPushTemplate(final EMValueCallBack<String> eMValueCallBack) {
        this.mClient.execute(new Runnable() { // from class: com.hyphenate.chat.EMPushManager.12
            @Override // java.lang.Runnable
            public void run() {
                try {
                    EMAError eMAError = new EMAError();
                    String pushTemplate = EMPushManager.this.emaObject.getPushTemplate(eMAError);
                    EMPushManager.this.handleError(eMAError);
                    eMValueCallBack.onSuccess(pushTemplate);
                } catch (HyphenateException e2) {
                    eMValueCallBack.onError(e2.getErrorCode(), e2.getDescription());
                }
            }
        });
    }

    public void getSilentModeForAll(final EMValueCallBack<EMSilentModeResult> eMValueCallBack) {
        this.mClient.execute(new Runnable() { // from class: com.hyphenate.chat.EMPushManager.7
            @Override // java.lang.Runnable
            public void run() {
                try {
                    EMAError eMAError = new EMAError();
                    EMASilentModeItem silentModeForAll = EMPushManager.this.emaObject.getSilentModeForAll(eMAError);
                    EMPushManager.this.handleError(eMAError);
                    eMValueCallBack.onSuccess(new EMSilentModeResult(silentModeForAll));
                } catch (HyphenateException e2) {
                    eMValueCallBack.onError(e2.getErrorCode(), e2.getDescription());
                }
            }
        });
    }

    public void getSilentModeForConversation(final String str, final EMConversation.EMConversationType eMConversationType, final EMValueCallBack<EMSilentModeResult> eMValueCallBack) {
        this.mClient.execute(new Runnable() { // from class: com.hyphenate.chat.EMPushManager.5
            @Override // java.lang.Runnable
            public void run() {
                try {
                    EMAError eMAError = new EMAError();
                    EMASilentModeItem silentModeForConversation = EMPushManager.this.emaObject.getSilentModeForConversation(str, eMConversationType.ordinal(), eMAError);
                    EMPushManager.this.handleError(eMAError);
                    eMValueCallBack.onSuccess(new EMSilentModeResult(silentModeForConversation));
                } catch (HyphenateException e2) {
                    eMValueCallBack.onError(e2.getErrorCode(), e2.getDescription());
                }
            }
        });
    }

    public void getSilentModeForConversations(final List<EMConversation> list, final EMValueCallBack<Map<String, EMSilentModeResult>> eMValueCallBack) {
        this.mClient.execute(new Runnable() { // from class: com.hyphenate.chat.EMPushManager.8
            @Override // java.lang.Runnable
            public void run() {
                try {
                    StringBuilder sb = new StringBuilder();
                    StringBuilder sb2 = new StringBuilder();
                    for (EMConversation eMConversation : list) {
                        if (eMConversation.getType() == EMConversation.EMConversationType.Chat) {
                            if (!sb.toString().isEmpty()) {
                                sb.append(",");
                            }
                            sb.append(eMConversation.conversationId());
                        } else {
                            if (!sb2.toString().isEmpty()) {
                                sb2.append(",");
                            }
                            sb2.append(eMConversation.conversationId());
                        }
                    }
                    HashMap map = new HashMap();
                    map.put(PLVLinkMicManager.USER, sb.toString());
                    map.put("group", sb2.toString());
                    EMAError eMAError = new EMAError();
                    List<EMASilentModeItem> silentModeForConversations = EMPushManager.this.emaObject.getSilentModeForConversations(map, eMAError);
                    EMPushManager.this.handleError(eMAError);
                    HashMap map2 = new HashMap();
                    for (EMASilentModeItem eMASilentModeItem : silentModeForConversations) {
                        map2.put(eMASilentModeItem.getConversationId(), new EMSilentModeResult(eMASilentModeItem));
                    }
                    eMValueCallBack.onSuccess(map2);
                } catch (HyphenateException e2) {
                    eMValueCallBack.onError(e2.getErrorCode(), e2.getDescription());
                }
            }
        });
    }

    public void reportPushAction(final JSONObject jSONObject, final EMPushAction eMPushAction, final EMCallBack eMCallBack) {
        EMClient.getInstance().execute(new Runnable() { // from class: com.hyphenate.chat.EMPushManager.13
            @Override // java.lang.Runnable
            public void run() throws JSONException {
                EMCallBack eMCallBack2;
                int errorCode;
                String message;
                try {
                    JSONObject jSONObject2 = jSONObject;
                    if (jSONObject2 != null) {
                        jSONObject2.put("action", eMPushAction.name);
                        EMPushManager.this.reportPushAction(jSONObject.toString());
                    }
                    eMCallBack.onSuccess();
                } catch (HyphenateException e2) {
                    eMCallBack2 = eMCallBack;
                    errorCode = e2.getErrorCode();
                    message = e2.getDescription();
                    eMCallBack2.onError(errorCode, message);
                } catch (IllegalArgumentException e3) {
                    eMCallBack2 = eMCallBack;
                    errorCode = 205;
                    message = e3.getMessage();
                    eMCallBack2.onError(errorCode, message);
                } catch (JSONException e4) {
                    e4.printStackTrace();
                }
            }
        });
    }

    public void setPreferredNotificationLanguage(final String str, final EMCallBack eMCallBack) {
        this.mClient.execute(new Runnable() { // from class: com.hyphenate.chat.EMPushManager.9
            @Override // java.lang.Runnable
            public void run() {
                try {
                    EMAError eMAError = new EMAError();
                    EMPushManager.this.emaObject.setPushPerformLanguage(str, eMAError);
                    EMPushManager.this.handleError(eMAError);
                    eMCallBack.onSuccess();
                } catch (HyphenateException e2) {
                    eMCallBack.onError(e2.getErrorCode(), e2.getDescription());
                }
            }
        });
    }

    public void setPushTemplate(final String str, final EMCallBack eMCallBack) {
        this.mClient.execute(new Runnable() { // from class: com.hyphenate.chat.EMPushManager.11
            @Override // java.lang.Runnable
            public void run() {
                try {
                    EMAError eMAError = new EMAError();
                    EMPushManager.this.emaObject.setPushTemplate(str, eMAError);
                    EMPushManager.this.handleError(eMAError);
                    eMCallBack.onSuccess();
                } catch (HyphenateException e2) {
                    eMCallBack.onError(e2.getErrorCode(), e2.getDescription());
                }
            }
        });
    }

    public void setSilentModeForAll(final EMSilentModeParam eMSilentModeParam, final EMValueCallBack<EMSilentModeResult> eMValueCallBack) {
        this.mClient.execute(new Runnable() { // from class: com.hyphenate.chat.EMPushManager.6
            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.lang.Runnable
            public void run() {
                try {
                    EMAError eMAError = new EMAError();
                    EMASilentModeItem silentModeForAll = EMPushManager.this.emaObject.setSilentModeForAll((EMASilentModeParam) eMSilentModeParam.emaObject, eMAError);
                    EMPushManager.this.handleError(eMAError);
                    eMValueCallBack.onSuccess(new EMSilentModeResult(silentModeForAll));
                } catch (HyphenateException e2) {
                    eMValueCallBack.onError(e2.getErrorCode(), e2.getDescription());
                }
            }
        });
    }

    public void setSilentModeForConversation(final String str, final EMConversation.EMConversationType eMConversationType, final EMSilentModeParam eMSilentModeParam, final EMValueCallBack<EMSilentModeResult> eMValueCallBack) {
        this.mClient.execute(new Runnable() { // from class: com.hyphenate.chat.EMPushManager.3
            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.lang.Runnable
            public void run() {
                try {
                    EMAError eMAError = new EMAError();
                    EMASilentModeItem silentModeForConversation = EMPushManager.this.emaObject.setSilentModeForConversation(str, eMConversationType.ordinal(), (EMASilentModeParam) eMSilentModeParam.emaObject, eMAError);
                    EMPushManager.this.handleError(eMAError);
                    eMValueCallBack.onSuccess(new EMSilentModeResult(silentModeForConversation));
                } catch (HyphenateException e2) {
                    eMValueCallBack.onError(e2.getErrorCode(), e2.getDescription());
                }
            }
        });
    }

    public void unBindDeviceToken() throws HyphenateException {
        bindDeviceToken(com.hyphenate.chat.core.a.a().r(), "");
    }

    public void updatePushDisplayStyle(DisplayStyle displayStyle) throws HyphenateException, IllegalArgumentException {
        if (TextUtils.isEmpty(EMClient.getInstance().getCurrentUser())) {
            throw new IllegalArgumentException("currentUser is null or empty");
        }
        if (TextUtils.isEmpty(EMClient.getInstance().getAccessToken())) {
            throw new IllegalArgumentException("token is null or empty");
        }
        EMAError eMAError = new EMAError();
        this.emaObject.updatePushDisplayStyle(displayStyle.ordinal(), eMAError);
        handleError(eMAError);
    }

    public boolean updatePushNickname(String str) throws HyphenateException, IllegalArgumentException {
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("nick name is null or empty");
        }
        if (TextUtils.isEmpty(EMClient.getInstance().getCurrentUser())) {
            throw new IllegalArgumentException("currentUser is null or empty");
        }
        if (TextUtils.isEmpty(EMClient.getInstance().getAccessToken())) {
            throw new IllegalArgumentException("token is null or empty");
        }
        EMAError eMAError = new EMAError();
        this.emaObject.updatePushNickname(str, eMAError);
        handleError(eMAError);
        return true;
    }

    @Deprecated
    public void updatePushServiceForGroup(List<String> list, boolean z2) throws HyphenateException {
        EMAError eMAError = new EMAError();
        this.emaObject.updatePushServiceForGroup(list, z2, eMAError);
        handleError(eMAError);
    }

    @Deprecated
    public void updatePushServiceForUsers(List<String> list, boolean z2) throws HyphenateException {
        EMAError eMAError = new EMAError();
        this.emaObject.updatePushServiceForUsers(list, z2, eMAError);
        handleError(eMAError);
    }
}
