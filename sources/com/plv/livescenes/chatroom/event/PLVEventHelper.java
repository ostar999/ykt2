package com.plv.livescenes.chatroom.event;

import android.text.TextUtils;
import androidx.annotation.Nullable;
import com.google.gson.Gson;
import com.plv.foundationsdk.log.PLVCommonLog;
import com.plv.socket.user.PLVSocketUserConstant;
import org.eclipse.jetty.util.URIUtil;

/* loaded from: classes4.dex */
public class PLVEventHelper {
    private static final String TAG = "PLVEventHelper";
    public static final Gson gson = new Gson();

    public static String fitActor(String str, String str2) {
        return TextUtils.isEmpty(str) ? "teacher".equals(str2) ? PLVSocketUserConstant.ACTOR_TEACHER : PLVSocketUserConstant.USERTYPE_MANAGER.equals(str2) ? PLVSocketUserConstant.ACTOR_MANAGER : "assistant".equals(str2) ? PLVSocketUserConstant.ACTOR_ASSISTANT : str : str;
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

    @Nullable
    public static <T> T getEventObject(Class<T> cls, String str, String str2) {
        try {
            return (T) gson.fromJson(str, (Class) cls);
        } catch (Exception e2) {
            PLVCommonLog.e(TAG, e2.getMessage());
            return null;
        }
    }
}
