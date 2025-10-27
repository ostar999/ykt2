package com.hyphenate.easeui.manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.LruCache;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.huawei.hms.support.hianalytics.HiAnalyticsConstant;
import com.hyphenate.EMValueCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMCursorResult;
import com.hyphenate.chat.EMGroupReadAck;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.util.EMLog;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes4.dex */
public class EaseDingMessageHelper {
    static final int CACHE_SIZE_CONVERSATION = 5;
    static final int CACHE_SIZE_MESSAGE = 10;
    static final String KEY_CONVERSATION_ID = "EMConversationID";
    static final String KEY_DING = "EMDingMessage";
    static final String KEY_DING_ACK = "EMDingMessageAck";
    private static String NAME_PREFS = "group-ack-data-prefs";
    private static final String TAG = "EaseDingMessageHelper";
    private static EaseDingMessageHelper instance;
    private SharedPreferences dataPrefs;
    private SharedPreferences.Editor prefsEditor;
    private LruCache<String, LruCache<String, List<String>>> dataCache = new LruCache<String, LruCache<String, List<String>>>(5) { // from class: com.hyphenate.easeui.manager.EaseDingMessageHelper.2
        @Override // android.util.LruCache
        public int sizeOf(String str, LruCache<String, List<String>> lruCache) {
            return 1;
        }
    };
    private Map<String, WeakReference<IAckUserUpdateListener>> listenerMap = new HashMap();

    public interface IAckUserUpdateListener {
        void onUpdate(List<String> list);
    }

    public EaseDingMessageHelper(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(NAME_PREFS, 0);
        this.dataPrefs = sharedPreferences;
        this.prefsEditor = sharedPreferences.edit();
    }

    private LruCache<String, List<String>> createCache() {
        return new LruCache<String, List<String>>(10) { // from class: com.hyphenate.easeui.manager.EaseDingMessageHelper.3
            @Override // android.util.LruCache
            public int sizeOf(String str, List<String> list) {
                return 1;
            }
        };
    }

    public static EaseDingMessageHelper get() {
        if (instance == null) {
            synchronized (EaseDingMessageHelper.class) {
                if (instance == null) {
                    instance = new EaseDingMessageHelper(EMClient.getInstance().getContext());
                }
            }
        }
        return instance;
    }

    private boolean validateMessage(EMMessage eMMessage) {
        return eMMessage != null && eMMessage.getChatType() == EMMessage.ChatType.GroupChat && isDingMessage(eMMessage);
    }

    public EMMessage createDingMessage(String str, String str2) {
        EMMessage eMMessageCreateTxtSendMessage = EMMessage.createTxtSendMessage(str2, str);
        eMMessageCreateTxtSendMessage.setIsNeedGroupAck(true);
        return eMMessageCreateTxtSendMessage;
    }

    public void delete(EMMessage eMMessage) {
        if (validateMessage(eMMessage)) {
            String to = eMMessage.getTo();
            String msgId = eMMessage.getMsgId();
            LruCache<String, List<String>> lruCache = this.dataCache.get(to);
            if (lruCache != null) {
                lruCache.remove(msgId);
            }
            String strGenerateKey = generateKey(to, msgId);
            if (this.dataPrefs.contains(strGenerateKey)) {
                this.prefsEditor.remove(strGenerateKey).commit();
            }
        }
    }

    public void fetchGroupReadAck(EMMessage eMMessage) {
        EMClient.getInstance().chatManager().asyncFetchGroupReadAcks(eMMessage.getMsgId(), 20, "", new EMValueCallBack<EMCursorResult<EMGroupReadAck>>() { // from class: com.hyphenate.easeui.manager.EaseDingMessageHelper.1
            @Override // com.hyphenate.EMValueCallBack
            public void onError(int i2, String str) {
                EMLog.d(EaseDingMessageHelper.TAG, "asyncFetchGroupReadAcks fail: " + i2);
            }

            @Override // com.hyphenate.EMValueCallBack
            public void onSuccess(EMCursorResult<EMGroupReadAck> eMCursorResult) {
                EMLog.d(EaseDingMessageHelper.TAG, "asyncFetchGroupReadAcks success");
                if (eMCursorResult.getData() == null || ((List) eMCursorResult.getData()).size() <= 0) {
                    EMLog.d(EaseDingMessageHelper.TAG, "no data");
                    return;
                }
                Iterator it = ((List) eMCursorResult.getData()).iterator();
                while (it.hasNext()) {
                    EaseDingMessageHelper.this.handleGroupReadAck((EMGroupReadAck) it.next());
                }
            }
        });
    }

    public String generateKey(@NonNull String str, @NonNull String str2) {
        return str + HiAnalyticsConstant.REPORT_VAL_SEPARATOR + str2;
    }

    public LruCache<String, LruCache<String, List<String>>> getDataCache() {
        return this.dataCache;
    }

    public SharedPreferences getDataPrefs() {
        return this.dataPrefs;
    }

    public Map<String, WeakReference<IAckUserUpdateListener>> getListenerMap() {
        return this.listenerMap;
    }

    public void handleGroupReadAck(EMGroupReadAck eMGroupReadAck) {
        if (eMGroupReadAck == null) {
            return;
        }
        EMLog.d(TAG, "handle group read ack: " + eMGroupReadAck.getMsgId());
        String from = eMGroupReadAck.getFrom();
        String msgId = eMGroupReadAck.getMsgId();
        String strConversationId = EMClient.getInstance().chatManager().getMessage(msgId).conversationId();
        LruCache<String, List<String>> lruCacheCreateCache = this.dataCache.get(strConversationId);
        if (lruCacheCreateCache == null) {
            lruCacheCreateCache = createCache();
            this.dataCache.put(strConversationId, lruCacheCreateCache);
        }
        List<String> arrayList = lruCacheCreateCache.get(msgId);
        if (arrayList == null) {
            arrayList = new ArrayList<>();
            lruCacheCreateCache.put(msgId, arrayList);
        }
        if (!arrayList.contains(from)) {
            arrayList.add(from);
        }
        WeakReference<IAckUserUpdateListener> weakReference = this.listenerMap.get(msgId);
        if (weakReference != null) {
            weakReference.get().onUpdate(arrayList);
        }
        String strGenerateKey = generateKey(strConversationId, msgId);
        HashSet hashSet = new HashSet();
        hashSet.addAll(arrayList);
        this.prefsEditor.putStringSet(strGenerateKey, hashSet).commit();
    }

    public boolean isDingMessage(EMMessage eMMessage) {
        return eMMessage.isNeedGroupAck();
    }

    public void sendAckMessage(EMMessage eMMessage) {
        if (!validateMessage(eMMessage) || eMMessage.isAcked() || EMClient.getInstance().getCurrentUser().equalsIgnoreCase(eMMessage.getFrom())) {
            return;
        }
        try {
            if (!eMMessage.isNeedGroupAck() || eMMessage.isUnread()) {
                return;
            }
            EMClient.getInstance().chatManager().ackGroupMessageRead(eMMessage.conversationId(), eMMessage.getMsgId(), ((EMTextMessageBody) eMMessage.getBody()).getMessage());
            eMMessage.setUnread(false);
            EMLog.i(TAG, "Send the group ack cmd-type message.");
        } catch (Exception e2) {
            EMLog.d(TAG, e2.getMessage());
        }
    }

    public void setUserUpdateListener(EMMessage eMMessage, @Nullable IAckUserUpdateListener iAckUserUpdateListener) {
        if (validateMessage(eMMessage)) {
            String msgId = eMMessage.getMsgId();
            if (iAckUserUpdateListener == null) {
                this.listenerMap.remove(msgId);
            } else {
                this.listenerMap.put(msgId, new WeakReference<>(iAckUserUpdateListener));
            }
        }
    }

    public void delete(EMConversation eMConversation) {
        if (eMConversation.isGroup()) {
            String strConversationId = eMConversation.conversationId();
            this.dataCache.remove(strConversationId);
            String strGenerateKey = generateKey(strConversationId, "");
            for (String str : this.dataPrefs.getAll().keySet()) {
                if (str.startsWith(strGenerateKey)) {
                    this.prefsEditor.remove(str);
                }
            }
            this.prefsEditor.commit();
        }
    }
}
