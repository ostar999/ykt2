package com.plv.socket.event;

import android.text.TextUtils;
import com.plv.foundationsdk.log.PLVCommonLog;
import com.plv.foundationsdk.utils.PLVGsonUtil;
import com.plv.socket.event.chat.IPLVManagerChatEvent;
import com.plv.socket.user.PLVSocketUserConstant;
import org.eclipse.jetty.util.URIUtil;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class PLVEventHelper {
    public static String fixActor(String str, String str2) {
        return TextUtils.isEmpty(str) ? "teacher".equals(str2) ? PLVSocketUserConstant.ACTOR_TEACHER : PLVSocketUserConstant.USERTYPE_MANAGER.equals(str2) ? PLVSocketUserConstant.ACTOR_MANAGER : "assistant".equals(str2) ? PLVSocketUserConstant.ACTOR_ASSISTANT : "guest".equals(str2) ? PLVSocketUserConstant.ACTOR_GUEST : str : str;
    }

    public static String fixChatPic(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        if (str.startsWith("//")) {
            return URIUtil.HTTP_COLON + str;
        }
        if (!str.startsWith("/")) {
            return str;
        }
        return "http://livestatic.videocc.net" + str;
    }

    public static String getEvent(String str) throws JSONException {
        return new JSONObject(str).optString("EVENT");
    }

    public static boolean isChatroomTeacher(String str, String str2) {
        return "teacher".equals(str) && !TextUtils.isEmpty(str2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static boolean isManagerChatMsg(PLVBaseEvent pLVBaseEvent) {
        if (pLVBaseEvent instanceof IPLVManagerChatEvent) {
            return ((IPLVManagerChatEvent) pLVBaseEvent).isManagerChatMsg();
        }
        return false;
    }

    public static boolean isSpecialType(String str) {
        return PLVSocketUserConstant.USERTYPE_MANAGER.equals(str) || "teacher".equals(str) || "guest".equals(str) || "assistant".equals(str);
    }

    public static <T extends PLVBaseEvent> T toEventModel(String str, String str2, String str3, Class<T> cls) {
        try {
            T t2 = (T) PLVGsonUtil.fromJson(cls, str3);
            if (t2 == null || !t2.getListenEvent().equals(str)) {
                return null;
            }
            if (t2.getEVENT().equals(str2)) {
                return t2;
            }
            return null;
        } catch (Exception e2) {
            PLVCommonLog.exception(e2);
            return null;
        }
    }

    public static <T extends PLVBaseEvent> T toMessageEventModel(String str, Class<T> cls) {
        try {
            return (T) toEventModel("message", getEvent(str), str, cls);
        } catch (JSONException e2) {
            PLVCommonLog.exception(e2);
            return null;
        }
    }
}
