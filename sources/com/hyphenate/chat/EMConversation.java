package com.hyphenate.chat;

import android.text.TextUtils;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.adapter.EMAConversation;
import com.hyphenate.chat.adapter.message.EMAMessage;
import com.hyphenate.util.EMLog;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/* loaded from: classes4.dex */
public class EMConversation extends EMBase<EMAConversation> {
    private static final int LIST_SIZE = 512;
    private static final String TAG = "conversation";

    /* renamed from: com.hyphenate.chat.EMConversation$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$hyphenate$chat$EMMessage$ChatType;

        static {
            int[] iArr = new int[EMMessage.ChatType.values().length];
            $SwitchMap$com$hyphenate$chat$EMMessage$ChatType = iArr;
            try {
                iArr[EMMessage.ChatType.GroupChat.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$hyphenate$chat$EMMessage$ChatType[EMMessage.ChatType.ChatRoom.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    public enum EMConversationType {
        Chat,
        GroupChat,
        ChatRoom,
        DiscussionGroup,
        HelpDesk
    }

    public enum EMSearchDirection {
        UP,
        DOWN
    }

    public static class MessageCache {
        TreeMap<Long, Object> sortedMessages = new TreeMap<>(new MessageComparator());
        Map<String, EMMessage> messages = new HashMap();
        Map<String, Long> idTimeMap = new HashMap();
        boolean hasDuplicateTime = false;
        final boolean sortByServerTime = EMClient.getInstance().getChatConfigPrivate().b().isSortMessageByServerTime();

        public class MessageComparator implements Comparator<Long> {
            public MessageComparator() {
            }

            @Override // java.util.Comparator
            public int compare(Long l2, Long l3) {
                long jLongValue = l2.longValue() - l3.longValue();
                if (jLongValue > 0) {
                    return 1;
                }
                return jLongValue == 0 ? 0 : -1;
            }
        }

        public synchronized void addMessage(EMMessage eMMessage) {
            if (eMMessage != null) {
                if (eMMessage.emaObject != 0 && eMMessage.getMsgTime() != 0 && eMMessage.getMsgTime() != -1 && eMMessage.getMsgId() != null && !eMMessage.getMsgId().isEmpty() && eMMessage.getType() != EMMessage.Type.CMD) {
                    String msgId = eMMessage.getMsgId();
                    Iterator<EMMessage> it = this.messages.values().iterator();
                    while (it.hasNext()) {
                        if (TextUtils.equals(msgId, it.next().getMsgId())) {
                            Long l2 = this.idTimeMap.get(msgId);
                            if (l2 != null) {
                                this.sortedMessages.remove(l2);
                                this.idTimeMap.remove(msgId);
                            }
                            it.remove();
                        }
                    }
                    long msgTime = this.sortByServerTime ? eMMessage.getMsgTime() : eMMessage.localTime();
                    if (this.sortedMessages.containsKey(Long.valueOf(msgTime))) {
                        this.hasDuplicateTime = true;
                        Object obj = this.sortedMessages.get(Long.valueOf(msgTime));
                        if (obj != null) {
                            if (obj instanceof EMMessage) {
                                LinkedList linkedList = new LinkedList();
                                linkedList.add((EMMessage) obj);
                                linkedList.add(eMMessage);
                                this.sortedMessages.put(Long.valueOf(msgTime), linkedList);
                            } else if (obj instanceof List) {
                                ((List) obj).add(eMMessage);
                            }
                        }
                    } else {
                        this.sortedMessages.put(Long.valueOf(msgTime), eMMessage);
                    }
                    this.messages.put(msgId, eMMessage);
                    this.idTimeMap.put(msgId, Long.valueOf(msgTime));
                }
            }
        }

        public synchronized void addMessages(List<EMMessage> list) {
            Iterator<EMMessage> it = list.iterator();
            while (it.hasNext()) {
                addMessage(it.next());
            }
        }

        public synchronized void clear() {
            this.sortedMessages.clear();
            this.messages.clear();
            this.idTimeMap.clear();
        }

        public synchronized List<EMMessage> getAllMessages() {
            ArrayList arrayList;
            arrayList = new ArrayList();
            if (this.hasDuplicateTime) {
                for (Object obj : this.sortedMessages.values()) {
                    if (obj != null) {
                        if (obj instanceof List) {
                            arrayList.addAll((List) obj);
                        } else {
                            arrayList.add((EMMessage) obj);
                        }
                    }
                }
            } else {
                Iterator<Object> it = this.sortedMessages.values().iterator();
                while (it.hasNext()) {
                    arrayList.add((EMMessage) it.next());
                }
            }
            return arrayList;
        }

        public synchronized EMMessage getLastMessage() {
            if (this.sortedMessages.isEmpty()) {
                return null;
            }
            Object value = this.sortedMessages.lastEntry().getValue();
            if (value == null) {
                return null;
            }
            if (value instanceof EMMessage) {
                return (EMMessage) value;
            }
            if (!(value instanceof List)) {
                return null;
            }
            List list = (List) value;
            if (list.size() <= 0) {
                return null;
            }
            return (EMMessage) list.get(list.size() - 1);
        }

        public synchronized EMMessage getMessage(String str) {
            if (str != null) {
                if (!str.isEmpty()) {
                    return this.messages.get(str);
                }
            }
            return null;
        }

        public synchronized boolean isEmpty() {
            return this.sortedMessages.isEmpty();
        }

        public synchronized void removeMessage(String str) {
            Object obj;
            if (str != null) {
                if (!str.isEmpty()) {
                    if (this.messages.get(str) != null) {
                        Long l2 = this.idTimeMap.get(str);
                        if (l2 != null) {
                            if (this.hasDuplicateTime && this.sortedMessages.containsKey(l2) && (obj = this.sortedMessages.get(l2)) != null && (obj instanceof List)) {
                                List list = (List) obj;
                                Iterator it = list.iterator();
                                while (true) {
                                    if (!it.hasNext()) {
                                        break;
                                    }
                                    EMMessage eMMessage = (EMMessage) it.next();
                                    if (eMMessage != null && eMMessage.getMsgId() != null && eMMessage.getMsgId().equals(str)) {
                                        list.remove(eMMessage);
                                        break;
                                    }
                                }
                                this.idTimeMap.remove(str);
                            } else {
                                TreeMap<Long, Object> treeMap = this.sortedMessages;
                                treeMap.remove(l2);
                                this.idTimeMap.remove(str);
                            }
                        }
                        this.messages.remove(str);
                    }
                }
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public EMConversation(EMAConversation eMAConversation) {
        this.emaObject = eMAConversation;
    }

    public static EMConversationType msgType2ConversationType(String str, EMMessage.ChatType chatType) {
        int i2 = AnonymousClass1.$SwitchMap$com$hyphenate$chat$EMMessage$ChatType[chatType.ordinal()];
        return i2 != 1 ? i2 != 2 ? EMConversationType.Chat : EMConversationType.ChatRoom : EMConversationType.GroupChat;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public boolean appendMessage(EMMessage eMMessage) {
        boolean zAppendMessage = ((EMAConversation) this.emaObject).appendMessage((EMAMessage) eMMessage.emaObject);
        if (zAppendMessage) {
            getCache().addMessage(eMMessage);
        }
        return zAppendMessage;
    }

    public void clear() {
        getCache().clear();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void clearAllMessages() {
        ((EMAConversation) this.emaObject).clearAllMessages();
        getCache().clear();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public String conversationId() {
        return ((EMAConversation) this.emaObject).conversationId();
    }

    @Override // com.hyphenate.chat.EMBase
    public /* bridge */ /* synthetic */ boolean equals(Object obj) {
        return super.equals(obj);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public List<EMMessage> getAllMessages() {
        if (getCache().isEmpty()) {
            EMAMessage eMAMessageLatestMessage = ((EMAConversation) this.emaObject).latestMessage();
            ArrayList arrayList = new ArrayList();
            if (eMAMessageLatestMessage != null) {
                arrayList.add(new EMMessage(eMAMessageLatestMessage));
            }
            getCache().addMessages(arrayList);
        }
        return getCache().getAllMessages();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int getAllMsgCount() {
        return ((EMAConversation) this.emaObject).messagesCount();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public MessageCache getCache() {
        MessageCache messageCache;
        synchronized (EMClient.getInstance().chatManager().caches) {
            messageCache = EMClient.getInstance().chatManager().caches.get(((EMAConversation) this.emaObject).conversationId());
            if (messageCache == null) {
                messageCache = new MessageCache();
            }
            EMClient.getInstance().chatManager().caches.put(((EMAConversation) this.emaObject).conversationId(), messageCache);
        }
        return messageCache;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public String getExtField() {
        return ((EMAConversation) this.emaObject).extField();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public EMMessage getLastMessage() {
        if (!getCache().isEmpty()) {
            return getCache().getLastMessage();
        }
        EMAMessage eMAMessageLatestMessage = ((EMAConversation) this.emaObject).latestMessage();
        EMMessage eMMessage = eMAMessageLatestMessage == null ? null : new EMMessage(eMAMessageLatestMessage);
        getCache().addMessage(eMMessage);
        return eMMessage;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public EMMessage getLatestMessageFromOthers() {
        EMAMessage eMAMessageLatestMessageFromOthers = ((EMAConversation) this.emaObject).latestMessageFromOthers();
        EMMessage eMMessage = eMAMessageLatestMessageFromOthers == null ? null : new EMMessage(eMAMessageLatestMessageFromOthers);
        getCache().addMessage(eMMessage);
        return eMMessage;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public EMMessage getMessage(String str, boolean z2) {
        EMMessage message = getCache().getMessage(str);
        if (message == null) {
            EMAMessage eMAMessageLoadMessage = ((EMAConversation) this.emaObject).loadMessage(str);
            if (eMAMessageLoadMessage == null) {
                return null;
            }
            message = new EMMessage(eMAMessageLoadMessage);
        }
        ((EMAConversation) this.emaObject).markMessageAsRead(str, z2);
        return message;
    }

    public String getMessageAttachmentPath() {
        return EMClient.getInstance().getChatConfigPrivate().E() + "/" + EMClient.getInstance().getCurrentUser() + "/" + conversationId();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public EMConversationType getType() {
        EMAConversation.EMAConversationType eMAConversationType_getType = ((EMAConversation) this.emaObject)._getType();
        return eMAConversationType_getType == EMAConversation.EMAConversationType.CHAT ? EMConversationType.Chat : eMAConversationType_getType == EMAConversation.EMAConversationType.GROUPCHAT ? EMConversationType.GroupChat : eMAConversationType_getType == EMAConversation.EMAConversationType.CHATROOM ? EMConversationType.ChatRoom : eMAConversationType_getType == EMAConversation.EMAConversationType.DISCUSSIONGROUP ? EMConversationType.DiscussionGroup : eMAConversationType_getType == EMAConversation.EMAConversationType.HELPDESK ? EMConversationType.HelpDesk : EMConversationType.Chat;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int getUnreadMsgCount() {
        return ((EMAConversation) this.emaObject).unreadMessagesCount();
    }

    @Override // com.hyphenate.chat.EMBase
    public /* bridge */ /* synthetic */ int hashCode() {
        return super.hashCode();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public boolean insertMessage(EMMessage eMMessage) {
        boolean zInsertMessage = ((EMAConversation) this.emaObject).insertMessage((EMAMessage) eMMessage.emaObject);
        if (zInsertMessage) {
            getCache().addMessage(eMMessage);
        }
        return zInsertMessage;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public boolean isChatThread() {
        return ((EMAConversation) this.emaObject).isChatThread();
    }

    public boolean isGroup() {
        EMConversationType type = getType();
        return !isChatThread() && (type == EMConversationType.GroupChat || type == EMConversationType.ChatRoom);
    }

    public List<EMMessage> loadMoreMsgFromDB(String str, int i2) {
        return loadMoreMsgFromDB(str, i2, EMSearchDirection.UP);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public List<EMMessage> loadMoreMsgFromDB(String str, int i2, EMSearchDirection eMSearchDirection) {
        List<EMAMessage> listLoadMoreMessages = ((EMAConversation) this.emaObject).loadMoreMessages(str, i2, eMSearchDirection == EMSearchDirection.UP ? EMAConversation.EMASearchDirection.UP : EMAConversation.EMASearchDirection.DOWN);
        ArrayList arrayList = new ArrayList();
        for (EMAMessage eMAMessage : listLoadMoreMessages) {
            if (eMAMessage != null) {
                arrayList.add(new EMMessage(eMAMessage));
            }
        }
        getCache().addMessages(arrayList);
        return arrayList;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void markAllMessagesAsRead() {
        ((EMAConversation) this.emaObject).markAllMessagesAsRead(true);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void markMessageAsRead(String str) {
        ((EMAConversation) this.emaObject).markMessageAsRead(str, true);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void removeMessage(String str) {
        EMLog.d(TAG, "remove msg from conversation: " + str);
        ((EMAConversation) this.emaObject)._removeMessage(str);
        getCache().removeMessage(str);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public List<EMMessage> searchCustomMsgFromDB(String str, long j2, int i2, String str2, EMSearchDirection eMSearchDirection) {
        List<EMAMessage> listSearchCustomMessages = ((EMAConversation) this.emaObject).searchCustomMessages(str, j2, i2, str2, eMSearchDirection == EMSearchDirection.UP ? EMAConversation.EMASearchDirection.UP : EMAConversation.EMASearchDirection.DOWN);
        List<EMMessage> linkedList = listSearchCustomMessages.size() > 512 ? new LinkedList<>() : new ArrayList<>();
        for (EMAMessage eMAMessage : listSearchCustomMessages) {
            if (eMAMessage != null) {
                linkedList.add(new EMMessage(eMAMessage));
            }
        }
        return linkedList;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public List<EMMessage> searchMsgFromDB(long j2, int i2, EMSearchDirection eMSearchDirection) {
        List<EMAMessage> listSearchMessages = ((EMAConversation) this.emaObject).searchMessages(j2, i2, eMSearchDirection == EMSearchDirection.UP ? EMAConversation.EMASearchDirection.UP : EMAConversation.EMASearchDirection.DOWN);
        List<EMMessage> linkedList = listSearchMessages.size() > 512 ? new LinkedList<>() : new ArrayList<>();
        for (EMAMessage eMAMessage : listSearchMessages) {
            if (eMAMessage != null) {
                linkedList.add(new EMMessage(eMAMessage));
            }
        }
        return linkedList;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public List<EMMessage> searchMsgFromDB(long j2, long j3, int i2) {
        List<EMAMessage> listSearchMessages = ((EMAConversation) this.emaObject).searchMessages(j2, j3, i2);
        List<EMMessage> linkedList = listSearchMessages.size() > 512 ? new LinkedList<>() : new ArrayList<>();
        for (EMAMessage eMAMessage : listSearchMessages) {
            if (eMAMessage != null) {
                linkedList.add(new EMMessage(eMAMessage));
            }
        }
        return linkedList;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public List<EMMessage> searchMsgFromDB(EMMessage.Type type, long j2, int i2, String str, EMSearchDirection eMSearchDirection) {
        List<EMAMessage> listSearchMessages = ((EMAConversation) this.emaObject).searchMessages(type.ordinal(), j2, i2, str, eMSearchDirection == EMSearchDirection.UP ? EMAConversation.EMASearchDirection.UP : EMAConversation.EMASearchDirection.DOWN);
        List<EMMessage> linkedList = listSearchMessages.size() > 512 ? new LinkedList<>() : new ArrayList<>();
        for (EMAMessage eMAMessage : listSearchMessages) {
            if (eMAMessage != null) {
                linkedList.add(new EMMessage(eMAMessage));
            }
        }
        return linkedList;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public List<EMMessage> searchMsgFromDB(String str, long j2, int i2, String str2, EMSearchDirection eMSearchDirection) {
        List<EMAMessage> listSearchMessages = ((EMAConversation) this.emaObject).searchMessages(str, j2, i2, str2, eMSearchDirection == EMSearchDirection.UP ? EMAConversation.EMASearchDirection.UP : EMAConversation.EMASearchDirection.DOWN);
        List<EMMessage> linkedList = listSearchMessages.size() > 512 ? new LinkedList<>() : new ArrayList<>();
        for (EMAMessage eMAMessage : listSearchMessages) {
            if (eMAMessage != null) {
                linkedList.add(new EMMessage(eMAMessage));
            }
        }
        return linkedList;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void setExtField(String str) {
        if (isChatThread()) {
            return;
        }
        ((EMAConversation) this.emaObject)._setExtField(str);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public boolean updateMessage(EMMessage eMMessage) {
        boolean zUpdateMessage = ((EMAConversation) this.emaObject).updateMessage((EMAMessage) eMMessage.emaObject);
        if (zUpdateMessage) {
            getCache().addMessage(eMMessage);
        }
        return zUpdateMessage;
    }
}
