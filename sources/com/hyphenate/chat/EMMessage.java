package com.hyphenate.chat;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import cn.hutool.core.text.StrPool;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.adapter.EMACallback;
import com.hyphenate.chat.adapter.EMAMessageReaction;
import com.hyphenate.chat.adapter.EMAThreadInfo;
import com.hyphenate.chat.adapter.message.EMACmdMessageBody;
import com.hyphenate.chat.adapter.message.EMACustomMessageBody;
import com.hyphenate.chat.adapter.message.EMAFileMessageBody;
import com.hyphenate.chat.adapter.message.EMAImageMessageBody;
import com.hyphenate.chat.adapter.message.EMALocationMessageBody;
import com.hyphenate.chat.adapter.message.EMAMessage;
import com.hyphenate.chat.adapter.message.EMAMessageBody;
import com.hyphenate.chat.adapter.message.EMATextMessageBody;
import com.hyphenate.chat.adapter.message.EMAVideoMessageBody;
import com.hyphenate.chat.adapter.message.EMAVoiceMessageBody;
import com.hyphenate.exceptions.HyphenateException;
import com.hyphenate.util.EMFileHelper;
import com.hyphenate.util.EMLog;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import okhttp3.HttpUrl;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class EMMessage extends EMBase<EMAMessage> implements Parcelable, Cloneable {
    static final String ATTR_ENCRYPTED = "isencrypted";
    public static final Parcelable.Creator<EMMessage> CREATOR = new Parcelable.Creator<EMMessage>() { // from class: com.hyphenate.chat.EMMessage.2
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public EMMessage createFromParcel(Parcel parcel) {
            try {
                return new EMMessage(parcel);
            } catch (HyphenateException e2) {
                e2.printStackTrace();
                return null;
            }
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public EMMessage[] newArray(int i2) {
            return new EMMessage[i2];
        }
    };
    private static final String TAG = "msg";
    EMMessageBody body;
    EMCallbackHolder messageStatusCallBack;

    /* renamed from: com.hyphenate.chat.EMMessage$3, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass3 {
        static final /* synthetic */ int[] $SwitchMap$com$hyphenate$chat$EMMessage$Status;
        static final /* synthetic */ int[] $SwitchMap$com$hyphenate$chat$adapter$message$EMAMessage$EMAMessageStatus;

        static {
            int[] iArr = new int[Status.values().length];
            $SwitchMap$com$hyphenate$chat$EMMessage$Status = iArr;
            try {
                iArr[Status.CREATE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$hyphenate$chat$EMMessage$Status[Status.SUCCESS.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$hyphenate$chat$EMMessage$Status[Status.FAIL.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$hyphenate$chat$EMMessage$Status[Status.INPROGRESS.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            int[] iArr2 = new int[EMAMessage.EMAMessageStatus.values().length];
            $SwitchMap$com$hyphenate$chat$adapter$message$EMAMessage$EMAMessageStatus = iArr2;
            try {
                iArr2[EMAMessage.EMAMessageStatus.NEW.ordinal()] = 1;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$hyphenate$chat$adapter$message$EMAMessage$EMAMessageStatus[EMAMessage.EMAMessageStatus.SUCCESS.ordinal()] = 2;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$hyphenate$chat$adapter$message$EMAMessage$EMAMessageStatus[EMAMessage.EMAMessageStatus.FAIL.ordinal()] = 3;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$hyphenate$chat$adapter$message$EMAMessage$EMAMessageStatus[EMAMessage.EMAMessageStatus.DELIVERING.ordinal()] = 4;
            } catch (NoSuchFieldError unused8) {
            }
        }
    }

    public enum ChatType {
        Chat,
        GroupChat,
        ChatRoom
    }

    public enum Direct {
        SEND,
        RECEIVE
    }

    public static class EMCallbackHolder implements EMCallBack {
        EMCallBack innerCallback = null;
        private EMCallBack strong;
        private WeakReference<EMCallBack> weak;

        public EMCallbackHolder(EMCallBack eMCallBack) {
            this.weak = new WeakReference<>(eMCallBack);
        }

        public synchronized EMCallBack getRef() {
            EMCallBack eMCallBack = this.strong;
            if (eMCallBack != null) {
                return eMCallBack;
            }
            WeakReference<EMCallBack> weakReference = this.weak;
            if (weakReference == null) {
                return null;
            }
            EMCallBack eMCallBack2 = weakReference.get();
            if (eMCallBack2 == null) {
                EMLog.d("msg", "getRef weak:" + eMCallBack2);
            }
            return eMCallBack2;
        }

        public synchronized void makeItStrong() {
            if (this.strong != null) {
                return;
            }
            WeakReference<EMCallBack> weakReference = this.weak;
            if (weakReference != null && weakReference.get() != null) {
                this.strong = this.weak.get();
            }
        }

        @Override // com.hyphenate.EMCallBack
        public void onError(int i2, String str) {
            EMCallBack eMCallBack = this.innerCallback;
            if (eMCallBack != null) {
                eMCallBack.onError(i2, str);
            }
            EMCallBack ref = getRef();
            if (ref == null) {
                EMLog.d("msg", "CallbackHolder getRef: null");
            } else {
                ref.onError(i2, str);
                release();
            }
        }

        @Override // com.hyphenate.EMCallBack
        public void onProgress(int i2, String str) {
            EMCallBack eMCallBack = this.innerCallback;
            if (eMCallBack != null) {
                eMCallBack.onProgress(i2, str);
            }
            EMCallBack ref = getRef();
            if (ref != null) {
                ref.onProgress(i2, str);
            }
        }

        @Override // com.hyphenate.EMCallBack
        public void onSuccess() {
            EMCallBack eMCallBack = this.innerCallback;
            if (eMCallBack != null) {
                eMCallBack.onSuccess();
            }
            EMCallBack ref = getRef();
            if (ref == null) {
                EMLog.d("msg", "CallbackHolder getRef: null");
            } else {
                ref.onSuccess();
                release();
            }
        }

        public synchronized void release() {
            if (this.strong == null) {
                return;
            }
            this.weak = new WeakReference<>(this.strong);
            this.strong = null;
        }

        public synchronized void update(EMCallBack eMCallBack) {
            if (this.strong != null) {
                this.strong = eMCallBack;
            } else {
                this.weak = new WeakReference<>(eMCallBack);
            }
        }
    }

    public enum Status {
        SUCCESS,
        FAIL,
        INPROGRESS,
        CREATE
    }

    public enum Type {
        TXT,
        IMAGE,
        VIDEO,
        LOCATION,
        VOICE,
        FILE,
        CMD,
        CUSTOM
    }

    private EMMessage(Parcel parcel) throws HyphenateException {
        EMMessage message = EMClient.getInstance().chatManager().getMessage(parcel.readString());
        if (message == null) {
            throw new HyphenateException("EMMessage constructed from parcel failed");
        }
        this.emaObject = message.emaObject;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public EMMessage(EMAMessage eMAMessage) {
        this.emaObject = eMAMessage;
    }

    public static EMMessage createFileSendMessage(Uri uri, String str) {
        if (!EMFileHelper.getInstance().isFileExist(uri)) {
            EMLog.e("msg", "file does not exist");
            return null;
        }
        EMMessage eMMessageCreateSendMessage = createSendMessage(Type.FILE);
        eMMessageCreateSendMessage.setTo(str);
        eMMessageCreateSendMessage.addBody(new EMNormalFileMessageBody(uri));
        return eMMessageCreateSendMessage;
    }

    public static EMMessage createFileSendMessage(String str, String str2) {
        return createFileSendMessage(EMFileHelper.getInstance().formatInUri(str), str2);
    }

    public static EMMessage createImageSendMessage(Uri uri, boolean z2, String str) {
        if (!EMFileHelper.getInstance().isFileExist(uri)) {
            EMLog.e("msg", "image file does not exsit");
            return null;
        }
        EMMessage eMMessageCreateSendMessage = createSendMessage(Type.IMAGE);
        eMMessageCreateSendMessage.setTo(str);
        EMImageMessageBody eMImageMessageBody = new EMImageMessageBody(uri);
        eMImageMessageBody.setSendOriginalImage(z2);
        eMMessageCreateSendMessage.addBody(eMImageMessageBody);
        return eMMessageCreateSendMessage;
    }

    public static EMMessage createImageSendMessage(String str, boolean z2, String str2) {
        return createImageSendMessage(EMFileHelper.getInstance().formatInUri(str), z2, str2);
    }

    public static EMMessage createLocationSendMessage(double d3, double d4, String str, String str2) {
        EMMessage eMMessageCreateSendMessage = createSendMessage(Type.LOCATION);
        eMMessageCreateSendMessage.addBody(new EMLocationMessageBody(str, d3, d4));
        eMMessageCreateSendMessage.setTo(str2);
        return eMMessageCreateSendMessage;
    }

    public static EMMessage createLocationSendMessage(double d3, double d4, String str, String str2, String str3) {
        EMMessage eMMessageCreateSendMessage = createSendMessage(Type.LOCATION);
        eMMessageCreateSendMessage.addBody(new EMLocationMessageBody(str, d3, d4, str2));
        eMMessageCreateSendMessage.setTo(str3);
        return eMMessageCreateSendMessage;
    }

    public static EMMessage createReceiveMessage(Type type) {
        EMMessage eMMessage = new EMMessage(EMAMessage.createReceiveMessage("", self(), null, ChatType.Chat.ordinal()));
        eMMessage.setTo(EMSessionManager.getInstance().currentUser.getUsername());
        return eMMessage;
    }

    public static EMMessage createSendMessage(Type type) {
        return new EMMessage(EMAMessage.createSendMessage(self(), "", null, ChatType.Chat.ordinal()));
    }

    public static EMMessage createTextSendMessage(String str, String str2) {
        if (str.length() <= 0) {
            EMLog.e("msg", "text content size must be greater than 0");
            return null;
        }
        EMMessage eMMessageCreateSendMessage = createSendMessage(Type.TXT);
        eMMessageCreateSendMessage.addBody(new EMTextMessageBody(str));
        eMMessageCreateSendMessage.setTo(str2);
        return eMMessageCreateSendMessage;
    }

    @Deprecated
    public static EMMessage createTxtSendMessage(String str, String str2) {
        if (str.length() <= 0) {
            EMLog.e("msg", "text content size must be greater than 0");
            return null;
        }
        EMMessage eMMessageCreateSendMessage = createSendMessage(Type.TXT);
        eMMessageCreateSendMessage.addBody(new EMTextMessageBody(str));
        eMMessageCreateSendMessage.setTo(str2);
        return eMMessageCreateSendMessage;
    }

    public static EMMessage createVideoSendMessage(Uri uri, Uri uri2, int i2, String str) {
        if (!EMFileHelper.getInstance().isFileExist(uri)) {
            EMLog.e("msg", "video file does not exist");
            return null;
        }
        EMMessage eMMessageCreateSendMessage = createSendMessage(Type.VIDEO);
        eMMessageCreateSendMessage.setTo(str);
        eMMessageCreateSendMessage.addBody(new EMVideoMessageBody(uri, uri2, i2, EMFileHelper.getInstance().getFileLength(uri)));
        return eMMessageCreateSendMessage;
    }

    public static EMMessage createVideoSendMessage(Uri uri, String str, int i2, String str2) {
        return createVideoSendMessage(uri, EMFileHelper.getInstance().formatInUri(str), i2, str2);
    }

    public static EMMessage createVideoSendMessage(String str, String str2, int i2, String str3) {
        return createVideoSendMessage(EMFileHelper.getInstance().formatInUri(str), EMFileHelper.getInstance().formatInUri(str2), i2, str3);
    }

    public static EMMessage createVoiceSendMessage(Uri uri, int i2, String str) {
        if (!EMFileHelper.getInstance().isFileExist(uri)) {
            EMLog.e("msg", "voice file does not exsit");
            return null;
        }
        EMMessage eMMessageCreateSendMessage = createSendMessage(Type.VOICE);
        eMMessageCreateSendMessage.addBody(new EMVoiceMessageBody(uri, i2));
        eMMessageCreateSendMessage.setTo(str);
        return eMMessageCreateSendMessage;
    }

    public static EMMessage createVoiceSendMessage(String str, int i2, String str2) {
        return createVoiceSendMessage(EMFileHelper.getInstance().formatInUri(str), i2, str2);
    }

    public static String self() {
        String currentUser = EMClient.getInstance().getCurrentUser();
        return currentUser == null ? EMSessionManager.getInstance().getLastLoginUser() : currentUser;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void addBody(EMMessageBody eMMessageBody) {
        this.body = eMMessageBody;
        ((EMAMessage) this.emaObject).addBody((EMAMessageBody) eMMessageBody.emaObject);
    }

    public Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public String conversationId() {
        return ((EMAMessage) this.emaObject).conversationId();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public Direct direct() {
        return ((EMAMessage) this.emaObject).direction() == EMAMessage.EMADirection.SEND ? Direct.SEND : Direct.RECEIVE;
    }

    @Override // com.hyphenate.chat.EMBase
    public /* bridge */ /* synthetic */ boolean equals(Object obj) {
        return super.equals(obj);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public Map<String, Object> ext() {
        return ((EMAMessage) this.emaObject).ext();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public EMMessageBody getBody() {
        EMMessageBody eMCustomMessageBody;
        EMMessageBody eMMessageBody = this.body;
        if (eMMessageBody != null) {
            return eMMessageBody;
        }
        List<EMAMessageBody> listBodies = ((EMAMessage) this.emaObject).bodies();
        if (listBodies.size() <= 0) {
            return null;
        }
        EMAMessageBody eMAMessageBody = listBodies.get(0);
        if (eMAMessageBody instanceof EMATextMessageBody) {
            eMCustomMessageBody = new EMTextMessageBody((EMATextMessageBody) eMAMessageBody);
        } else if (eMAMessageBody instanceof EMACmdMessageBody) {
            eMCustomMessageBody = new EMCmdMessageBody((EMACmdMessageBody) eMAMessageBody);
        } else if (eMAMessageBody instanceof EMAVideoMessageBody) {
            eMCustomMessageBody = new EMVideoMessageBody((EMAVideoMessageBody) eMAMessageBody);
        } else if (eMAMessageBody instanceof EMAVoiceMessageBody) {
            eMCustomMessageBody = new EMVoiceMessageBody((EMAVoiceMessageBody) eMAMessageBody);
        } else if (eMAMessageBody instanceof EMAImageMessageBody) {
            eMCustomMessageBody = new EMImageMessageBody((EMAImageMessageBody) eMAMessageBody);
        } else if (eMAMessageBody instanceof EMALocationMessageBody) {
            eMCustomMessageBody = new EMLocationMessageBody((EMALocationMessageBody) eMAMessageBody);
        } else {
            if (!(eMAMessageBody instanceof EMAFileMessageBody)) {
                if (eMAMessageBody instanceof EMACustomMessageBody) {
                    eMCustomMessageBody = new EMCustomMessageBody((EMACustomMessageBody) eMAMessageBody);
                }
                return this.body;
            }
            eMCustomMessageBody = new EMNormalFileMessageBody((EMAFileMessageBody) eMAMessageBody);
        }
        this.body = eMCustomMessageBody;
        return this.body;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public boolean getBooleanAttribute(String str) throws HyphenateException {
        if (str == null || str.equals("")) {
            throw new HyphenateException("attribute " + str + " can not be null or empty");
        }
        AtomicBoolean atomicBoolean = new AtomicBoolean();
        if (((EMAMessage) this.emaObject).getBooleanAttribute(str, false, atomicBoolean)) {
            return atomicBoolean.get();
        }
        throw new HyphenateException("attribute " + str + " not found");
    }

    /* JADX WARN: Multi-variable type inference failed */
    public boolean getBooleanAttribute(String str, boolean z2) {
        if (str == null || str.equals("")) {
            return z2;
        }
        AtomicBoolean atomicBoolean = new AtomicBoolean();
        return !((EMAMessage) this.emaObject).getBooleanAttribute(str, false, atomicBoolean) ? z2 : atomicBoolean.get();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public EMChatThread getChatThread() {
        EMAThreadInfo chatThread = ((EMAMessage) this.emaObject).getChatThread();
        if (chatThread == null) {
            return null;
        }
        return new EMChatThread(chatThread);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public ChatType getChatType() {
        EMAMessage.EMAChatType eMAChatTypeChatType = ((EMAMessage) this.emaObject).chatType();
        return eMAChatTypeChatType == EMAMessage.EMAChatType.SINGLE ? ChatType.Chat : eMAChatTypeChatType == EMAMessage.EMAChatType.GROUP ? ChatType.GroupChat : ChatType.ChatRoom;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public double getDoubleAttribute(String str) throws HyphenateException {
        if (str == null || str.equals("")) {
            throw new HyphenateException("attribute " + str + " can not be null or empty");
        }
        AtomicReference atomicReference = new AtomicReference();
        if (((EMAMessage) this.emaObject).getDoubleAttribute(str, -1.0d, atomicReference)) {
            return ((Double) atomicReference.get()).doubleValue();
        }
        throw new HyphenateException("attribute " + str + " not found");
    }

    /* JADX WARN: Multi-variable type inference failed */
    public double getDoubleAttribute(String str, double d3) {
        if (str == null || str.equals("")) {
            return d3;
        }
        AtomicReference atomicReference = new AtomicReference();
        return !((EMAMessage) this.emaObject).getDoubleAttribute(str, d3, atomicReference) ? d3 : ((Double) atomicReference.get()).doubleValue();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public float getFloatAttribute(String str) throws HyphenateException {
        if (str == null || str.equals("")) {
            throw new HyphenateException("attribute " + str + " can not be null or empty");
        }
        AtomicReference atomicReference = new AtomicReference();
        if (((EMAMessage) this.emaObject).getFloatAttribute(str, -1.0f, atomicReference)) {
            return ((Float) atomicReference.get()).floatValue();
        }
        throw new HyphenateException("attribute " + str + " not found");
    }

    /* JADX WARN: Multi-variable type inference failed */
    public float getFloatAttribute(String str, float f2) {
        if (str == null || str.equals("")) {
            return f2;
        }
        AtomicReference atomicReference = new AtomicReference();
        return !((EMAMessage) this.emaObject).getFloatAttribute(str, f2, atomicReference) ? f2 : ((Float) atomicReference.get()).floatValue();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public String getFrom() {
        return ((EMAMessage) this.emaObject).from();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int getIntAttribute(String str) throws HyphenateException {
        if (str == null || str.equals("")) {
            throw new HyphenateException("attribute " + str + " can not be null or empty");
        }
        AtomicInteger atomicInteger = new AtomicInteger();
        if (((EMAMessage) this.emaObject).getIntAttribute(str, -1, atomicInteger)) {
            return atomicInteger.intValue();
        }
        throw new HyphenateException("attribute " + str + " not found");
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int getIntAttribute(String str, int i2) {
        if (str == null || str.equals("")) {
            return i2;
        }
        AtomicInteger atomicInteger = new AtomicInteger();
        return !((EMAMessage) this.emaObject).getIntAttribute(str, -1, atomicInteger) ? i2 : atomicInteger.intValue();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public JSONArray getJSONArrayAttribute(String str) throws HyphenateException {
        if (str == null || str.equals("")) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        if (((EMAMessage) this.emaObject).getJsonAttribute(str, HttpUrl.PATH_SEGMENT_ENCODE_SET_URI, sb)) {
            try {
                return new JSONArray(sb.toString());
            } catch (JSONException unused) {
            }
        }
        throw new HyphenateException("attribute " + str + " not found");
    }

    /* JADX WARN: Multi-variable type inference failed */
    public JSONObject getJSONObjectAttribute(String str) throws HyphenateException {
        if (str == null || str.equals("")) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        if (((EMAMessage) this.emaObject).getJsonAttribute(str, StrPool.EMPTY_JSON, sb)) {
            try {
                return new JSONObject(sb.toString());
            } catch (JSONException unused) {
            }
        }
        throw new HyphenateException("attribute " + str + " not found");
    }

    /* JADX WARN: Multi-variable type inference failed */
    public long getLongAttribute(String str) throws HyphenateException {
        if (str == null || str.equals("")) {
            throw new HyphenateException("attribute " + str + " can not be null or empty");
        }
        AtomicLong atomicLong = new AtomicLong();
        if (((EMAMessage) this.emaObject).getLongAttribute(str, -1L, atomicLong)) {
            return atomicLong.longValue();
        }
        throw new HyphenateException("attribute " + str + " not found");
    }

    /* JADX WARN: Multi-variable type inference failed */
    public long getLongAttribute(String str, long j2) {
        if (str == null || str.equals("")) {
            return j2;
        }
        AtomicLong atomicLong = new AtomicLong();
        return !((EMAMessage) this.emaObject).getLongAttribute(str, -1L, atomicLong) ? j2 : atomicLong.longValue();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public List<EMMessageReaction> getMessageReaction() {
        List<EMAMessageReaction> listReactionList = ((EMAMessage) this.emaObject).reactionList();
        if (listReactionList.size() <= 0) {
            return null;
        }
        ArrayList arrayList = new ArrayList(listReactionList.size());
        Iterator<EMAMessageReaction> it = listReactionList.iterator();
        while (it.hasNext()) {
            arrayList.add(new EMMessageReaction(it.next()));
        }
        return arrayList;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public String getMsgId() {
        return ((EMAMessage) this.emaObject).msgId();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public long getMsgTime() {
        return ((EMAMessage) this.emaObject).timeStamp();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public String getRecaller() {
        return ((EMAMessage) this.emaObject).getRecaller();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public String getStringAttribute(String str) throws HyphenateException {
        if (str == null || str.equals("")) {
            throw new HyphenateException("attribute " + str + " can not be null or empty");
        }
        StringBuilder sb = new StringBuilder();
        if (((EMAMessage) this.emaObject).getStringAttribute(str, "", sb)) {
            return sb.toString();
        }
        throw new HyphenateException("attribute " + str + " not found");
    }

    /* JADX WARN: Multi-variable type inference failed */
    public String getStringAttribute(String str, String str2) {
        if (str == null || str.equals("")) {
            return str2;
        }
        StringBuilder sb = new StringBuilder();
        return !((EMAMessage) this.emaObject).getStringAttribute(str, "", sb) ? str2 : sb.toString();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public String getTo() {
        return ((EMAMessage) this.emaObject).to();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public Type getType() {
        List<EMAMessageBody> listBodies = ((EMAMessage) this.emaObject).bodies();
        if (listBodies.size() > 0) {
            int iType = listBodies.get(0).type();
            Type type = Type.TXT;
            if (iType == type.ordinal()) {
                return type;
            }
            Type type2 = Type.IMAGE;
            if (iType == type2.ordinal()) {
                return type2;
            }
            Type type3 = Type.CMD;
            if (iType == type3.ordinal()) {
                return type3;
            }
            Type type4 = Type.FILE;
            if (iType == type4.ordinal()) {
                return type4;
            }
            Type type5 = Type.VIDEO;
            if (iType == type5.ordinal()) {
                return type5;
            }
            Type type6 = Type.VOICE;
            if (iType == type6.ordinal()) {
                return type6;
            }
            Type type7 = Type.LOCATION;
            if (iType == type7.ordinal()) {
                return type7;
            }
            Type type8 = Type.CUSTOM;
            if (iType == type8.ordinal()) {
                return type8;
            }
        }
        return Type.TXT;
    }

    public String getUserName() {
        return (getFrom() == null || !getFrom().equals(EMClient.getInstance().getCurrentUser())) ? getFrom() : getTo();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int groupAckCount() {
        return ((EMAMessage) this.emaObject).groupAckCount();
    }

    @Override // com.hyphenate.chat.EMBase
    public /* bridge */ /* synthetic */ int hashCode() {
        return super.hashCode();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public boolean isAcked() {
        return ((EMAMessage) this.emaObject).isAcked();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public boolean isChatThreadMessage() {
        return ((EMAMessage) this.emaObject).isChatThreadMessage();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public boolean isDelivered() {
        return ((EMAMessage) this.emaObject).isDeliverAcked();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public boolean isListened() {
        return ((EMAMessage) this.emaObject).isListened();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public boolean isNeedGroupAck() {
        return ((EMAMessage) this.emaObject).isNeedGroupAck();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public boolean isOnlineState() {
        return ((EMAMessage) this.emaObject).isOnlineState();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public boolean isUnread() {
        return !((EMAMessage) this.emaObject).isRead();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public long localTime() {
        return ((EMAMessage) this.emaObject).getLocalTime();
    }

    public void makeCallbackStrong() {
        EMCallbackHolder eMCallbackHolder = this.messageStatusCallBack;
        if (eMCallbackHolder != null) {
            eMCallbackHolder.makeItStrong();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int progress() {
        return ((EMAMessage) this.emaObject).progress();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void setAcked(boolean z2) {
        ((EMAMessage) this.emaObject).setIsAcked(z2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void setAttribute(String str, double d3) {
        if (str == null || str.equals("")) {
            return;
        }
        ((EMAMessage) this.emaObject).setAttribute(str, d3);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void setAttribute(String str, float f2) {
        if (str == null || str.equals("")) {
            return;
        }
        ((EMAMessage) this.emaObject).setAttribute(str, f2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void setAttribute(String str, int i2) {
        if (str == null || str.equals("")) {
            return;
        }
        ((EMAMessage) this.emaObject).setAttribute(str, i2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void setAttribute(String str, long j2) {
        if (str == null || str.equals("")) {
            return;
        }
        ((EMAMessage) this.emaObject).setAttribute(str, j2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void setAttribute(String str, String str2) {
        if (str == null || str.equals("")) {
            return;
        }
        ((EMAMessage) this.emaObject).setAttribute(str, str2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void setAttribute(String str, JSONArray jSONArray) {
        if (str == null || str.equals("")) {
            return;
        }
        ((EMAMessage) this.emaObject).setJsonAttribute(str, jSONArray.toString());
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void setAttribute(String str, JSONObject jSONObject) {
        if (str == null || str.equals("")) {
            return;
        }
        ((EMAMessage) this.emaObject).setJsonAttribute(str, jSONObject.toString());
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void setAttribute(String str, boolean z2) {
        if (str == null || str.equals("")) {
            return;
        }
        ((EMAMessage) this.emaObject).setAttribute(str, z2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void setBody(EMMessageBody eMMessageBody) {
        if (this.body != eMMessageBody) {
            this.body = eMMessageBody;
            ((EMAMessage) this.emaObject).clearBodies();
            ((EMAMessage) this.emaObject).addBody((EMAMessageBody) eMMessageBody.emaObject);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void setCallback(final EMCallbackHolder eMCallbackHolder) {
        ((EMAMessage) this.emaObject).setCallback(new EMACallback(new EMCallBack() { // from class: com.hyphenate.chat.EMMessage.1
            @Override // com.hyphenate.EMCallBack
            public void onError(int i2, String str) {
                EMCallbackHolder eMCallbackHolder2 = eMCallbackHolder;
                if (eMCallbackHolder2 != null) {
                    eMCallbackHolder2.onError(i2, str);
                }
            }

            @Override // com.hyphenate.EMCallBack
            public void onProgress(int i2, String str) {
                EMCallbackHolder eMCallbackHolder2 = eMCallbackHolder;
                if (eMCallbackHolder2 != null) {
                    eMCallbackHolder2.onProgress(i2, str);
                }
            }

            @Override // com.hyphenate.EMCallBack
            public void onSuccess() {
                EMCallbackHolder eMCallbackHolder2 = eMCallbackHolder;
                if (eMCallbackHolder2 != null) {
                    eMCallbackHolder2.onSuccess();
                }
            }
        }));
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void setChatType(ChatType chatType) {
        EMAMessage.EMAChatType eMAChatType = EMAMessage.EMAChatType.SINGLE;
        if (chatType != ChatType.Chat) {
            eMAChatType = chatType == ChatType.GroupChat ? EMAMessage.EMAChatType.GROUP : EMAMessage.EMAChatType.CHATROOM;
        }
        ((EMAMessage) this.emaObject).setChatType(eMAChatType);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void setDeliverAcked(boolean z2) {
        ((EMAMessage) this.emaObject).setIsDeliverAcked(z2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void setDelivered(boolean z2) {
        ((EMAMessage) this.emaObject).setIsDeliverAcked(z2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void setDirection(Direct direct) {
        ((EMAMessage) this.emaObject).setDirection(direct.ordinal());
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void setFrom(String str) {
        ((EMAMessage) this.emaObject).setFrom(str);
        if (self().equals(str) || getTo() == null || getTo() == "" || !getTo().equals(self())) {
            return;
        }
        ((EMAMessage) this.emaObject).setConversationId(str);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void setGroupAckCount(int i2) {
        ((EMAMessage) this.emaObject).setGroupAckCount(i2);
    }

    public synchronized void setInnerCallback(EMCallBack eMCallBack) {
        EMCallbackHolder eMCallbackHolder = this.messageStatusCallBack;
        if (eMCallbackHolder == null) {
            eMCallbackHolder = new EMCallbackHolder(null);
            this.messageStatusCallBack = eMCallbackHolder;
        }
        eMCallbackHolder.innerCallback = eMCallBack;
        setCallback(this.messageStatusCallBack);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void setIsChatThreadMessage(boolean z2) {
        ((EMAMessage) this.emaObject).setIsChatThreadMessage(z2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void setIsNeedGroupAck(boolean z2) {
        ((EMAMessage) this.emaObject).setIsNeedGroupAck(z2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void setListened(boolean z2) {
        ((EMAMessage) this.emaObject).setListened(z2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void setLocalTime(long j2) {
        ((EMAMessage) this.emaObject).setLocalTime(j2);
    }

    public synchronized void setMessageStatusCallback(EMCallBack eMCallBack) {
        EMCallbackHolder eMCallbackHolder = this.messageStatusCallBack;
        if (eMCallbackHolder != null) {
            eMCallbackHolder.update(eMCallBack);
        } else {
            this.messageStatusCallBack = new EMCallbackHolder(eMCallBack);
        }
        setCallback(this.messageStatusCallBack);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void setMsgId(String str) {
        ((EMAMessage) this.emaObject).setMsgId(str);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void setMsgTime(long j2) {
        ((EMAMessage) this.emaObject).setTimeStamp(j2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void setProgress(int i2) {
        ((EMAMessage) this.emaObject).setProgress(i2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void setStatus(Status status) {
        EMAMessage.EMAMessageStatus eMAMessageStatus = EMAMessage.EMAMessageStatus.SUCCESS;
        int i2 = AnonymousClass3.$SwitchMap$com$hyphenate$chat$EMMessage$Status[status.ordinal()];
        if (i2 == 1) {
            eMAMessageStatus = EMAMessage.EMAMessageStatus.NEW;
        } else if (i2 != 2) {
            if (i2 == 3) {
                eMAMessageStatus = EMAMessage.EMAMessageStatus.FAIL;
            } else if (i2 == 4) {
                eMAMessageStatus = EMAMessage.EMAMessageStatus.DELIVERING;
            }
        }
        ((EMAMessage) this.emaObject).setStatus(eMAMessageStatus.ordinal());
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void setTo(String str) {
        ((EMAMessage) this.emaObject).setTo(str);
        ((EMAMessage) this.emaObject).setConversationId(str);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void setUnread(boolean z2) {
        ((EMAMessage) this.emaObject).setIsRead(!z2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public Status status() {
        EMAMessage.EMAMessageStatus eMAMessageStatus_status = ((EMAMessage) this.emaObject)._status();
        Status status = Status.CREATE;
        int i2 = AnonymousClass3.$SwitchMap$com$hyphenate$chat$adapter$message$EMAMessage$EMAMessageStatus[eMAMessageStatus_status.ordinal()];
        return i2 != 1 ? i2 != 2 ? i2 != 3 ? i2 != 4 ? status : Status.INPROGRESS : Status.FAIL : Status.SUCCESS : status;
    }

    public String toString() {
        return "msg{from:" + getFrom() + ", to:" + getTo() + " body:" + getBody();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeString(getMsgId());
    }
}
