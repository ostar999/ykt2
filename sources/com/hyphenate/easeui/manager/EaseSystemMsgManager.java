package com.hyphenate.easeui.manager;

import android.text.TextUtils;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.easeui.constants.EaseConstant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class EaseSystemMsgManager {
    private static EaseSystemMsgManager instance;

    private EaseSystemMsgManager() {
    }

    public static EaseSystemMsgManager getInstance() {
        if (instance == null) {
            synchronized (EaseSystemMsgManager.class) {
                if (instance == null) {
                    instance = new EaseSystemMsgManager();
                }
            }
        }
        return instance;
    }

    private void putObject(EMMessage eMMessage, String str, Object obj) throws JSONException {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        if (obj instanceof String) {
            eMMessage.setAttribute(str, (String) obj);
            return;
        }
        if (obj instanceof Byte) {
            eMMessage.setAttribute(str, ((Integer) obj).intValue());
            return;
        }
        if (obj instanceof Character) {
            eMMessage.setAttribute(str, ((Integer) obj).intValue());
            return;
        }
        if (obj instanceof Short) {
            eMMessage.setAttribute(str, ((Integer) obj).intValue());
            return;
        }
        if (obj instanceof Integer) {
            eMMessage.setAttribute(str, ((Integer) obj).intValue());
            return;
        }
        if (obj instanceof Boolean) {
            eMMessage.setAttribute(str, ((Boolean) obj).booleanValue());
            return;
        }
        if (obj instanceof Long) {
            eMMessage.setAttribute(str, ((Long) obj).longValue());
            return;
        }
        if (obj instanceof Float) {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put(str, obj);
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
            eMMessage.setAttribute(str, jSONObject);
            return;
        }
        if (obj instanceof Double) {
            JSONObject jSONObject2 = new JSONObject();
            try {
                jSONObject2.put(str, obj);
            } catch (JSONException e3) {
                e3.printStackTrace();
            }
            eMMessage.setAttribute(str, jSONObject2);
            return;
        }
        if (obj instanceof JSONObject) {
            eMMessage.setAttribute(str, (JSONObject) obj);
            return;
        }
        if (obj instanceof JSONArray) {
            eMMessage.setAttribute(str, (JSONArray) obj);
            return;
        }
        JSONObject jSONObject3 = new JSONObject();
        try {
            jSONObject3.put(str, obj);
        } catch (JSONException e4) {
            e4.printStackTrace();
        }
        eMMessage.setAttribute(str, jSONObject3);
    }

    public EMMessage createMessage(String str, Map<String, Object> map) throws JSONException {
        EMMessage eMMessageCreateReceiveMessage = EMMessage.createReceiveMessage(EMMessage.Type.TXT);
        eMMessageCreateReceiveMessage.setFrom(EaseConstant.DEFAULT_SYSTEM_MESSAGE_ID);
        eMMessageCreateReceiveMessage.setMsgId(UUID.randomUUID().toString());
        eMMessageCreateReceiveMessage.setStatus(EMMessage.Status.SUCCESS);
        eMMessageCreateReceiveMessage.addBody(new EMTextMessageBody(str));
        if (map != null && !map.isEmpty()) {
            for (String str2 : map.keySet()) {
                putObject(eMMessageCreateReceiveMessage, str2, map.get(str2));
            }
        }
        eMMessageCreateReceiveMessage.setUnread(true);
        EMClient.getInstance().chatManager().saveMessage(eMMessageCreateReceiveMessage);
        return eMMessageCreateReceiveMessage;
    }

    public Map<String, Object> createMsgExt() {
        return new HashMap();
    }

    public List<EMMessage> getAllMessages() {
        return getConversation().getAllMessages();
    }

    public EMConversation getConversation() {
        return getConversation(true);
    }

    public EMMessage getLastMessageByConversation(EMConversation eMConversation) {
        if (eMConversation == null) {
            return null;
        }
        return eMConversation.getLastMessage();
    }

    public String getMessageContent(EMMessage eMMessage) {
        return ((EMTextMessageBody) eMMessage.getBody()).getMessage();
    }

    public boolean isSystemConversation(EMConversation eMConversation) {
        return eMConversation.getType() == EMConversation.EMConversationType.Chat && TextUtils.equals(eMConversation.conversationId(), EaseConstant.DEFAULT_SYSTEM_MESSAGE_ID);
    }

    public boolean isSystemMessage(EMMessage eMMessage) {
        return eMMessage.getType() == EMMessage.Type.TXT && TextUtils.equals(eMMessage.getFrom(), EaseConstant.DEFAULT_SYSTEM_MESSAGE_ID);
    }

    public void markAllMessagesAsRead() {
        EMClient.getInstance().chatManager().getConversation(EaseConstant.DEFAULT_SYSTEM_MESSAGE_ID).markAllMessagesAsRead();
    }

    public boolean removeMessage(EMMessage eMMessage) {
        if (eMMessage == null || !isSystemMessage(eMMessage)) {
            return false;
        }
        EMClient.getInstance().chatManager().getConversation(EaseConstant.DEFAULT_SYSTEM_MESSAGE_ID).removeMessage(eMMessage.getMsgId());
        return true;
    }

    public boolean updateMessage(EMMessage eMMessage) {
        if (eMMessage == null || !isSystemMessage(eMMessage)) {
            return false;
        }
        EMClient.getInstance().chatManager().updateMessage(eMMessage);
        return true;
    }

    public EMConversation getConversation(boolean z2) {
        return EMClient.getInstance().chatManager().getConversation(EaseConstant.DEFAULT_SYSTEM_MESSAGE_ID, EMConversation.EMConversationType.Chat, z2);
    }
}
