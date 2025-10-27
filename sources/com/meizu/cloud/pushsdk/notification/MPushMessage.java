package com.meizu.cloud.pushsdk.notification;

import cn.hutool.core.text.CharPool;
import com.meizu.cloud.pushinternal.DebugLogger;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class MPushMessage implements Serializable {
    private static final String TAG = "MPushMessage";
    private String clickType;
    private String content;
    private String isDiscard;
    private String notifyType;
    private String packageName;
    private String pushType;
    private String taskId;
    private String title;
    private Map<String, String> extra = new HashMap();
    private Map<String, String> params = new HashMap();

    private static Map<String, String> getParamsMap(JSONObject jSONObject) {
        HashMap map = new HashMap();
        try {
            Iterator<String> itKeys = jSONObject.keys();
            while (itKeys.hasNext()) {
                String next = itKeys.next();
                map.put(next, jSONObject.getString(next));
            }
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        return map;
    }

    public static MPushMessage parsePushMessage(String str, String str2, String str3, String str4) throws JSONException {
        JSONObject jSONObject;
        MPushMessage mPushMessage = new MPushMessage();
        try {
            mPushMessage.setTaskId(str4);
            mPushMessage.setPushType(str);
            mPushMessage.setPackageName(str3);
            JSONObject jSONObject2 = new JSONObject(str2).getJSONObject("data");
            if (!jSONObject2.isNull("content")) {
                mPushMessage.setContent(jSONObject2.getString("content"));
            }
            if (!jSONObject2.isNull(PushConstants.IS_DISCARD)) {
                mPushMessage.setIsDiscard(jSONObject2.getString(PushConstants.IS_DISCARD));
            }
            if (!jSONObject2.isNull("title")) {
                mPushMessage.setTitle(jSONObject2.getString("title"));
            }
            if (!jSONObject2.isNull(PushConstants.CLICK_TYPE)) {
                mPushMessage.setClickType(jSONObject2.getString(PushConstants.CLICK_TYPE));
            }
            if (!jSONObject2.isNull(PushConstants.EXTRA) && (jSONObject = jSONObject2.getJSONObject(PushConstants.EXTRA)) != null) {
                try {
                    if (!jSONObject.isNull(PushConstants.PARAMS)) {
                        try {
                            JSONObject jSONObject3 = jSONObject.getJSONObject(PushConstants.PARAMS);
                            if (jSONObject3 != null) {
                                mPushMessage.setParams(getParamsMap(jSONObject3));
                            }
                        } catch (JSONException e2) {
                            DebugLogger.i(TAG, "parameter parse error message " + e2.getMessage());
                        }
                    }
                    mPushMessage.setExtra(getParamsMap(jSONObject));
                } finally {
                    jSONObject.remove(PushConstants.PARAMS);
                }
            }
        } catch (JSONException e3) {
            DebugLogger.i(TAG, "parse push message error " + e3.getMessage());
        }
        DebugLogger.i(TAG, " parsePushMessage " + mPushMessage);
        return mPushMessage;
    }

    public String getClickType() {
        return this.clickType;
    }

    public String getContent() {
        return this.content;
    }

    public Map<String, String> getExtra() {
        return this.extra;
    }

    public String getIsDiscard() {
        return this.isDiscard;
    }

    public String getNotifyType() {
        return this.notifyType;
    }

    public String getPackageName() {
        return this.packageName;
    }

    public Map<String, String> getParams() {
        return this.params;
    }

    public String getPushType() {
        return this.pushType;
    }

    public String getTaskId() {
        return this.taskId;
    }

    public String getTitle() {
        return this.title;
    }

    public void setClickType(String str) {
        this.clickType = str;
    }

    public void setContent(String str) {
        this.content = str;
    }

    public void setExtra(Map<String, String> map) {
        this.extra = map;
    }

    public void setIsDiscard(String str) {
        this.isDiscard = str;
    }

    public void setNotifyType(String str) {
        this.notifyType = str;
    }

    public void setPackageName(String str) {
        this.packageName = str;
    }

    public void setParams(Map<String, String> map) {
        this.params = map;
    }

    public void setPushType(String str) {
        this.pushType = str;
    }

    public void setTaskId(String str) {
        this.taskId = str;
    }

    public void setTitle(String str) {
        this.title = str;
    }

    public String toString() {
        return "MPushMessage{taskId='" + this.taskId + CharPool.SINGLE_QUOTE + ", pushType='" + this.pushType + CharPool.SINGLE_QUOTE + ", packageName='" + this.packageName + CharPool.SINGLE_QUOTE + ", title='" + this.title + CharPool.SINGLE_QUOTE + ", content='" + this.content + CharPool.SINGLE_QUOTE + ", notifyType='" + this.notifyType + CharPool.SINGLE_QUOTE + ", clickType='" + this.clickType + CharPool.SINGLE_QUOTE + ", isDiscard='" + this.isDiscard + CharPool.SINGLE_QUOTE + ", extra=" + this.extra + ", params=" + this.params + '}';
    }
}
