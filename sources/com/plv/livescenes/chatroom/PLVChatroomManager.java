package com.plv.livescenes.chatroom;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Pair;
import com.easefun.polyv.livescenes.config.PolyvLiveSDKClient;
import com.google.android.exoplayer2.C;
import com.google.gson.Gson;
import com.plv.foundationsdk.log.PLVCommonLog;
import com.plv.foundationsdk.log.elog.logcode.chat.PLVErrorCodeChatroomApi;
import com.plv.foundationsdk.net.PLVRetrofitHelper;
import com.plv.foundationsdk.rx.PLVRxBaseRetryFunction;
import com.plv.foundationsdk.rx.PLVRxBaseTransformer;
import com.plv.foundationsdk.rx.PLVRxBus;
import com.plv.foundationsdk.rx.PLVRxEncryptDataFunction;
import com.plv.foundationsdk.sign.PLVSignCreator;
import com.plv.foundationsdk.utils.PLVCheckUtils;
import com.plv.foundationsdk.utils.PLVGsonUtil;
import com.plv.livescenes.chatroom.IPLVChatroomManager;
import com.plv.livescenes.chatroom.model.PLVChatroomKickVO;
import com.plv.livescenes.chatroom.model.PLVChatroomShieldVO;
import com.plv.livescenes.chatroom.model.PLVChatroomUnKickVO;
import com.plv.livescenes.chatroom.send.custom.PLVBaseCustomEvent;
import com.plv.livescenes.chatroom.send.custom.PLVCustomEvent;
import com.plv.livescenes.chatroom.send.custom.PLVSendCustomMsgListener;
import com.plv.livescenes.chatroom.send.img.PLVSendChatImageHelper;
import com.plv.livescenes.chatroom.send.img.PLVSendChatImageListener;
import com.plv.livescenes.chatroom.send.img.PLVSendChatImgEvent;
import com.plv.livescenes.chatroom.send.img.PLVSendLocalImgEvent;
import com.plv.livescenes.linkmic.manager.PLVLinkMicManager;
import com.plv.livescenes.log.PLVELogSender;
import com.plv.livescenes.log.chat.PLVChatroomELog;
import com.plv.livescenes.model.PLVChatFunctionSwitchVO;
import com.plv.livescenes.model.PLVInteractiveCallbackVO;
import com.plv.livescenes.model.PLVLiveClassDetailVO;
import com.plv.livescenes.model.PLVTeacherStatusInfo;
import com.plv.livescenes.net.PLVApiManager;
import com.plv.livescenes.socket.PLVSocketWrapper;
import com.plv.socket.event.PLVEventConstant;
import com.plv.socket.event.PLVEventHelper;
import com.plv.socket.event.chat.PLVChatEmotionEvent;
import com.plv.socket.impl.PLVSocketMessageObserver;
import com.plv.socket.net.model.PLVSocketLoginVO;
import com.plv.socket.socketio.PLVSocketIOObservable;
import com.plv.socket.status.PLVSocketStatus;
import com.plv.socket.user.PLVSocketUserConstant;
import com.plv.thirdpart.blankj.utilcode.util.EncryptUtils;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.socket.client.Ack;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import okhttp3.ResponseBody;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class PLVChatroomManager implements IPLVChatroomManager {
    private static final String ACCOUNT_ID = "accountId";
    private static final String ACTOR = "actor";
    private static final String APP_ID = "appId";
    private static final String BUNDLE_KEY_INTERACTIVE = "interactive_event";
    private static final String CHANNEL_ID = "channelId";
    private static final String CHAT_IMG = "chatImg";
    private static final String CONTENT = "content";
    private static final String COUNT = "count";
    private static final String ID = "id";
    private static final String NEED_ID_CALLBACK = "needIdCallback";
    private static final String NICK = "nick";
    private static final String PIC = "pic";
    private static final String PLV_SWITCH_API_INNOR = "polyv_switch_api_innor";
    private static final String QUOTE_ID = "quoteId";
    private static final String ROOM_ID = "roomId";
    private static final String SESSION_ID = "sessionId";
    private static final int SOCKET_CALLBACK_TIMEOUT = 5000;
    private static final String SOURCE = "source";
    static final String STATUS_ERROR = "error";
    private static final String TAG = "PLVChatroomManager";
    private static final String TIMES = "times";
    private static final String TIMESTAMP = "timestamp";
    public static final int TOKEN_VALIDATE = 7200000;
    private static final String UP_LOADING_SUCCESS = "upLoadingSuccess";
    private static final String USER = "user";
    private static final String USER_ID = "userId";
    private static final String USER_TYPE = "userType";
    private static final String VALUES = "values";
    private static final String VIEWER_ID = "viewerId";
    private static final int WHAT_CALLBACK_RESPONSE = 16;
    private static final int WHAT_CALLBACK_TIMEOUT = 15;
    private static final int WHAT_LIKES_API = 13;
    private static final int WHAT_LIKES_SOCKET = 12;
    private static volatile PLVChatroomManager chatMessageSender;
    private PLVChatFunctionSwitchVO chatFunctionSwitchVO;
    CompositeDisposable compositeDisposable;
    private Disposable getChatFunctionSwitchDisposable;
    boolean isBanIp;
    boolean isCloseRoom;
    boolean isReceiveCloseRoomEvent;
    private PLVSocketIOObservable.OnConnectStatusListener onConnectStatusListener;
    private PLVSocketMessageObserver.OnMessageListener onMessageListener;
    private int onlineCount;
    private IPLVOnlineCountListener onlineCountListener;
    IPLVProhibitedWordListener prohibitedWordListener;
    private List<IPLVChatroomManager.RoomStatusListener> roomStatusListeners;
    List<PLVSendChatImageListener> sendChatImageListeners;
    private PLVSendCustomMsgListener sendCustomMsgListener;
    private Disposable sendLikesDisposable;
    private String sendLikesSessionId;
    private PLVSocketCallbackListener socketCallback;
    private Disposable toggleRoomDisposable;
    private int willSendApiLikesCount;
    private int willSendSocketLikesCount;
    Map<String, PLVSendLocalImgEvent> sendImgIdMap = new HashMap();

    @SuppressLint({"HandlerLeak"})
    private Handler handler = new Handler() { // from class: com.plv.livescenes.chatroom.PLVChatroomManager.1
        @Override // android.os.Handler
        public void handleMessage(Message message) throws JSONException {
            int i2 = message.what;
            if (12 == i2) {
                PLVChatroomManager pLVChatroomManager = PLVChatroomManager.this;
                pLVChatroomManager.sendLikesWithSocket(pLVChatroomManager.willSendSocketLikesCount, PLVChatroomManager.this.sendLikesSessionId);
                PLVChatroomManager.this.willSendSocketLikesCount = 0;
            } else if (13 == i2) {
                PLVChatroomManager pLVChatroomManager2 = PLVChatroomManager.this;
                pLVChatroomManager2.sendLikesWithApi(pLVChatroomManager2.willSendApiLikesCount);
                PLVChatroomManager.this.willSendApiLikesCount = 0;
            } else if (15 == i2) {
                PLVChatroomManager.this.sendInteractiveSocketMessage("message", message.obj, message.arg1, message.getData().getString(PLVChatroomManager.BUNDLE_KEY_INTERACTIVE));
            } else {
                if (16 != i2 || PLVChatroomManager.this.socketCallback == null) {
                    return;
                }
                PLVChatroomManager.this.socketCallback.socketCallback((PLVInteractiveCallbackVO) message.obj);
            }
        }
    };

    public interface ChatImageRunnable {
        void run(PLVSendChatImageListener pLVSendChatImageListener);
    }

    private PLVChatroomManager() {
    }

    private PLVChatroomShieldVO generateShieldVO(String str, boolean z2) {
        PLVChatroomShieldVO pLVChatroomShieldVO = new PLVChatroomShieldVO(z2);
        pLVChatroomShieldVO.setRoomId(PLVSocketWrapper.getInstance().getLoginRoomId());
        pLVChatroomShieldVO.setChannelId(getLoginVO().getChannelId());
        pLVChatroomShieldVO.setType("userId");
        pLVChatroomShieldVO.setValue(str);
        pLVChatroomShieldVO.setSign(EncryptUtils.encryptMD5ToString(PLVChatApiRequestHelper.CONSTANT_ROOM_SIGN + PLVSocketWrapper.getInstance().getLoginRoomId()).toLowerCase());
        return pLVChatroomShieldVO;
    }

    public static Observable<ResponseBody> getExtendChatHistory(String str, int i2, int i3) {
        long jCurrentTimeMillis = System.currentTimeMillis();
        LinkedHashMap<String, Object> linkedHashMap = new LinkedHashMap<String, Object>(i3, str, i2, jCurrentTimeMillis) { // from class: com.plv.livescenes.chatroom.PLVChatroomManager.12
            final /* synthetic */ int val$end;
            final /* synthetic */ String val$roomId;
            final /* synthetic */ int val$start;
            final /* synthetic */ long val$timestamp;

            {
                this.val$end = i3;
                this.val$roomId = str;
                this.val$start = i2;
                this.val$timestamp = jCurrentTimeMillis;
                put("end", Integer.valueOf(i3));
                put("fullMessage", 1);
                put("hide", 0);
                put("roomId", str);
                put("source", "extend");
                put("start", Integer.valueOf(i2));
                put("timestamp", Long.valueOf(jCurrentTimeMillis));
            }
        };
        StringBuilder sb = new StringBuilder();
        sb.append(PLVLinkMicManager.PLV_CHAT_SIGN);
        for (Map.Entry<String, Object> entry : linkedHashMap.entrySet()) {
            sb.append(entry.getKey());
            sb.append(entry.getValue());
        }
        sb.append(PLVLinkMicManager.PLV_CHAT_SIGN);
        return PLVApiManager.getPlvApichatApi().getChatHistory(str, i2, i3, 1, "extend", 0, jCurrentTimeMillis, EncryptUtils.encryptMD5ToString(sb.toString()).toUpperCase());
    }

    public static PLVChatroomManager getInstance() {
        if (chatMessageSender == null) {
            synchronized (PLVChatroomManager.class) {
                if (chatMessageSender == null) {
                    chatMessageSender = new PLVChatroomManager();
                }
            }
        }
        return chatMessageSender;
    }

    private void observeConnectStatus() {
        this.onConnectStatusListener = new PLVSocketIOObservable.OnConnectStatusListener() { // from class: com.plv.livescenes.chatroom.PLVChatroomManager.3
            @Override // com.plv.socket.socketio.PLVSocketIOObservable.OnConnectStatusListener
            public void onStatus(PLVSocketStatus pLVSocketStatus) {
                int status = pLVSocketStatus.getStatus();
                if (2 == status || 4 == status) {
                    PLVChatroomManager pLVChatroomManager = PLVChatroomManager.this;
                    if (!pLVChatroomManager.isReceiveCloseRoomEvent && pLVChatroomManager.isCloseRoom) {
                        pLVChatroomManager.isCloseRoom = false;
                        pLVChatroomManager.callRoomStatusListenerStatus(false);
                    }
                } else {
                    PLVChatroomManager.this.isReceiveCloseRoomEvent = false;
                }
                PLVTeacherStatusInfo pLVTeacherStatusInfo = new PLVTeacherStatusInfo();
                pLVTeacherStatusInfo.setWatchStatus(PLVLiveClassDetailVO.LiveStatus.LOGIN_CHAT_ROOM);
                PLVRxBus.get().post(pLVTeacherStatusInfo);
            }
        };
        PLVSocketWrapper.getInstance().getSocketObserver().addOnConnectStatusListener(this.onConnectStatusListener);
    }

    private <DataBean> boolean preJudgeWeatherSendCustomMsg(PLVBaseCustomEvent<DataBean> pLVBaseCustomEvent) {
        PLVSendCustomMsgListener pLVSendCustomMsgListener;
        int i2 = (pLVBaseCustomEvent == null || pLVBaseCustomEvent.getData() == null || TextUtils.isEmpty(pLVBaseCustomEvent.getEVENT())) ? -1 : (pLVBaseCustomEvent.getEmitMode() < 0 || pLVBaseCustomEvent.getEmitMode() > 2 || pLVBaseCustomEvent.getEVENT().length() > 50) ? -7 : (!this.isCloseRoom || PLVEventHelper.isSpecialType(getLoginVO().getUserType())) ? this.isBanIp ? -6 : 10000 : -4;
        if (i2 == 10000 || (pLVSendCustomMsgListener = this.sendCustomMsgListener) == null) {
            return true;
        }
        pLVSendCustomMsgListener.onSendFail(pLVBaseCustomEvent, i2);
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendChatImageEvent(PLVSendLocalImgEvent pLVSendLocalImgEvent, String str, String str2, String str3) {
        PLVSendChatImgEvent pLVSendChatImgEvent = new PLVSendChatImgEvent();
        pLVSendChatImgEvent.setEVENT("CHAT_IMG");
        pLVSendChatImgEvent.setRoomId(PLVSocketWrapper.getInstance().getLoginRoomId());
        pLVSendChatImgEvent.setSessionId(str3);
        pLVSendChatImgEvent.setAccountId(PolyvLiveSDKClient.getInstance().getUserId());
        pLVSendChatImgEvent.setSource(pLVSendLocalImgEvent.getSource());
        if (!TextUtils.isEmpty(str3)) {
            pLVSendChatImgEvent.setSessionId(str3);
        }
        ArrayList arrayList = new ArrayList();
        PLVSendChatImgEvent.ValueBean valueBean = new PLVSendChatImgEvent.ValueBean();
        valueBean.setUploadImgUrl(str);
        valueBean.setType("chatImg");
        valueBean.setStatus(UP_LOADING_SUCCESS);
        valueBean.setId(str2);
        PLVSendChatImgEvent.ValueBean.SizeBean sizeBean = new PLVSendChatImgEvent.ValueBean.SizeBean();
        sizeBean.setWidth(pLVSendLocalImgEvent.getWidth());
        sizeBean.setHeight(pLVSendLocalImgEvent.getHeight());
        valueBean.setSize(sizeBean);
        arrayList.add(valueBean);
        pLVSendChatImgEvent.setValues(arrayList);
        PLVSocketWrapper.getInstance().emit("message", new Gson().toJson(pLVSendChatImgEvent));
        this.sendImgIdMap.put(str2, pLVSendLocalImgEvent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendLikesWithApi(int i2) {
        long jCurrentTimeMillis = System.currentTimeMillis();
        HashMap map = new HashMap();
        map.put("timestamp", jCurrentTimeMillis + "");
        map.put("appId", PolyvLiveSDKClient.getInstance().getAppId());
        map.put(TIMES, i2 + "");
        map.put("viewerId", getLoginVO().getUserId());
        String[] strArrCreateSignWithSignatureNonceEncrypt = PLVSignCreator.createSignWithSignatureNonceEncrypt(PolyvLiveSDKClient.getInstance().getAppSecret(), map);
        Disposable disposable = this.sendLikesDisposable;
        if (disposable != null) {
            disposable.dispose();
        }
        this.sendLikesDisposable = PLVApiManager.getPlvLiveStatusApi().sendLikes(getLoginVO().getChannelId(), PolyvLiveSDKClient.getInstance().getAppId(), jCurrentTimeMillis + "", strArrCreateSignWithSignatureNonceEncrypt[0], PLVSignCreator.getSignatureMethod(), getLoginVO().getUserId(), i2, strArrCreateSignWithSignatureNonceEncrypt[1], strArrCreateSignWithSignatureNonceEncrypt[2]).compose(new PLVRxBaseTransformer()).subscribe(new Consumer<ResponseBody>() { // from class: com.plv.livescenes.chatroom.PLVChatroomManager.4
            @Override // io.reactivex.functions.Consumer
            public void accept(ResponseBody responseBody) throws Exception {
                PLVCommonLog.d(PLVChatroomManager.TAG, "sendLikes :" + responseBody.toString());
            }
        }, new Consumer<Throwable>() { // from class: com.plv.livescenes.chatroom.PLVChatroomManager.5
            @Override // io.reactivex.functions.Consumer
            public void accept(Throwable th) throws Exception {
                PLVCommonLog.exception(th);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendLikesWithSocket(int i2, String str) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("EVENT", "LIKES");
            jSONObject.put("nick", getLoginVO().getNickName());
            jSONObject.put(COUNT, i2);
            jSONObject.put("roomId", PLVSocketWrapper.getInstance().getLoginRoomId());
            jSONObject.put("userId", getLoginVO().getUserId());
            if (!TextUtils.isEmpty(str)) {
                jSONObject.put("sessionId", str);
            }
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        PLVSocketWrapper.getInstance().emit("message", jSONObject.toString());
    }

    private void startEventCountdown(int i2, long j2) {
        stopEventCountdown(i2);
        this.handler.sendEmptyMessageDelayed(i2, j2);
    }

    private void stopEventCountdown(int i2) {
        this.handler.removeMessages(i2);
    }

    @Override // com.plv.livescenes.chatroom.IPLVChatroomManager
    public void addOnRoomStatusListener(IPLVChatroomManager.RoomStatusListener roomStatusListener) {
        if (this.roomStatusListeners == null) {
            this.roomStatusListeners = new ArrayList();
        }
        if (this.roomStatusListeners.contains(roomStatusListener)) {
            return;
        }
        this.roomStatusListeners.add(roomStatusListener);
    }

    @Override // com.plv.livescenes.chatroom.IPLVChatroomManager
    public void addSendChatImageListener(PLVSendChatImageListener pLVSendChatImageListener) {
        if (this.sendChatImageListeners == null) {
            this.sendChatImageListeners = new ArrayList();
        }
        if (this.sendChatImageListeners.contains(pLVSendChatImageListener)) {
            return;
        }
        this.sendChatImageListeners.add(pLVSendChatImageListener);
    }

    public void callRoomStatusListenerStatus(boolean z2) {
        List<IPLVChatroomManager.RoomStatusListener> list = this.roomStatusListeners;
        if (list != null) {
            Iterator<IPLVChatroomManager.RoomStatusListener> it = list.iterator();
            while (it.hasNext()) {
                it.next().onStatus(z2);
            }
        }
    }

    public void callbackChatImage(ChatImageRunnable chatImageRunnable) {
        List<PLVSendChatImageListener> list = this.sendChatImageListeners;
        if (list != null) {
            for (PLVSendChatImageListener pLVSendChatImageListener : list) {
                if (pLVSendChatImageListener != null && chatImageRunnable != null) {
                    chatImageRunnable.run(pLVSendChatImageListener);
                }
            }
        }
    }

    @Override // com.plv.livescenes.chatroom.IPLVChatroomManager
    public void destroy() {
        this.isCloseRoom = false;
        this.isReceiveCloseRoomEvent = false;
        this.isBanIp = false;
        this.sendLikesSessionId = null;
        this.willSendSocketLikesCount = 0;
        this.willSendApiLikesCount = 0;
        this.onlineCount = 0;
        this.handler.removeCallbacksAndMessages(null);
        List<PLVSendChatImageListener> list = this.sendChatImageListeners;
        if (list != null) {
            list.clear();
            this.sendChatImageListeners = null;
        }
        this.chatFunctionSwitchVO = null;
        this.sendCustomMsgListener = null;
        this.prohibitedWordListener = null;
        this.socketCallback = null;
        this.onlineCountListener = null;
        PLVSocketWrapper.getInstance().getSocketObserver().removeOnMessageListener(this.onMessageListener);
        PLVSocketWrapper.getInstance().getSocketObserver().removeOnConnectStatusListener(this.onConnectStatusListener);
        Disposable disposable = this.sendLikesDisposable;
        if (disposable != null) {
            disposable.dispose();
            this.sendLikesDisposable = null;
        }
        Disposable disposable2 = this.getChatFunctionSwitchDisposable;
        if (disposable2 != null) {
            disposable2.dispose();
            this.getChatFunctionSwitchDisposable = null;
        }
        CompositeDisposable compositeDisposable = this.compositeDisposable;
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
            this.compositeDisposable = null;
        }
        Disposable disposable3 = this.toggleRoomDisposable;
        if (disposable3 != null) {
            disposable3.dispose();
            this.toggleRoomDisposable = null;
        }
        List<IPLVChatroomManager.RoomStatusListener> list2 = this.roomStatusListeners;
        if (list2 != null) {
            list2.clear();
            this.roomStatusListeners = null;
        }
        PLVRetrofitHelper.clearProgressListener();
    }

    @Override // com.plv.livescenes.chatroom.IPLVChatroomManager
    public PLVChatFunctionSwitchVO getChatFunctionSwitchVO() {
        return this.chatFunctionSwitchVO;
    }

    public PLVSocketLoginVO getLoginVO() {
        return PLVSocketWrapper.getInstance().getLoginVO();
    }

    @Override // com.plv.livescenes.chatroom.IPLVChatroomManager
    public int getOnlineCount() {
        return this.onlineCount;
    }

    @Override // com.plv.livescenes.chatroom.IPLVChatroomManager
    public void init() {
        this.compositeDisposable = new CompositeDisposable();
        observeConnectStatus();
        this.onMessageListener = new PLVSocketMessageObserver.OnMessageListener() { // from class: com.plv.livescenes.chatroom.PLVChatroomManager.2
            @Override // com.plv.socket.impl.PLVSocketMessageObserver.OnMessageListener
            public void onMessage(String str, String str2, String str3) {
                PLVChatRoomBaseOperation operation;
                if (!"message".equals(str) || (operation = PLVChatRoomOperationFactory.getOperation(PLVChatroomManager.getInstance(), str2)) == null) {
                    return;
                }
                operation.operate(str, str3);
            }
        };
        PLVSocketWrapper.getInstance().getSocketObserver().addOnMessageListener(this.onMessageListener);
    }

    @Override // com.plv.livescenes.chatroom.IPLVChatroomManager
    public boolean isCloseRoom() {
        return this.isCloseRoom;
    }

    @Override // com.plv.livescenes.chatroom.IPLVChatroomManager
    public int kick(String str) {
        if (TextUtils.isEmpty(str)) {
            return -7;
        }
        if (!PLVSocketWrapper.getInstance().isOnlineStatus()) {
            return -3;
        }
        PLVChatroomKickVO pLVChatroomKickVO = new PLVChatroomKickVO();
        pLVChatroomKickVO.setRoomId(PLVSocketWrapper.getInstance().getLoginRoomId());
        pLVChatroomKickVO.setChannelId(getLoginVO().getChannelId());
        pLVChatroomKickVO.setType("userId");
        pLVChatroomKickVO.setUserId(str);
        pLVChatroomKickVO.setSign(EncryptUtils.encryptMD5ToString(PLVChatApiRequestHelper.CONSTANT_ROOM_SIGN + PLVSocketWrapper.getInstance().getLoginRoomId()).toLowerCase());
        PLVSocketWrapper.getInstance().emit("message", pLVChatroomKickVO);
        return 1;
    }

    @Override // com.plv.livescenes.chatroom.IPLVChatroomManager
    public void removeOnRoomStatusListener(IPLVChatroomManager.RoomStatusListener roomStatusListener) {
        List<IPLVChatroomManager.RoomStatusListener> list = this.roomStatusListeners;
        if (list != null) {
            list.remove(roomStatusListener);
        }
    }

    @Override // com.plv.livescenes.chatroom.IPLVChatroomManager
    public void removeSendChatImageListener(PLVSendChatImageListener pLVSendChatImageListener) {
        List<PLVSendChatImageListener> list = this.sendChatImageListeners;
        if (list != null) {
            list.remove(pLVSendChatImageListener);
        }
    }

    @Override // com.plv.livescenes.chatroom.IPLVChatroomManager
    public int removeShield(String str) {
        if (TextUtils.isEmpty(str)) {
            return -7;
        }
        if (!PLVSocketWrapper.getInstance().isOnlineStatus()) {
            return -3;
        }
        PLVSocketWrapper.getInstance().emit("message", generateShieldVO(str, false));
        return 1;
    }

    @Override // com.plv.livescenes.chatroom.IPLVChatroomManager
    public void requestFunctionSwitch(final Consumer<PLVChatFunctionSwitchVO> consumer) {
        long jCurrentTimeMillis = System.currentTimeMillis();
        HashMap map = new HashMap();
        map.put("channelId", getLoginVO().getChannelId());
        map.put("timestamp", jCurrentTimeMillis + "");
        String[] strArrCreateSignWithSignatureNonceEncrypt = PLVSignCreator.createSignWithSignatureNonceEncrypt(PLV_SWITCH_API_INNOR, map);
        Disposable disposable = this.getChatFunctionSwitchDisposable;
        if (disposable != null) {
            disposable.dispose();
        }
        this.getChatFunctionSwitchDisposable = PLVApiManager.getPlvLiveStatusApi().getChatFunctionSwitch(jCurrentTimeMillis, strArrCreateSignWithSignatureNonceEncrypt[0], getLoginVO().getChannelId(), PLVSignCreator.getSignatureMethod(), strArrCreateSignWithSignatureNonceEncrypt[1], strArrCreateSignWithSignatureNonceEncrypt[2]).map(new PLVRxEncryptDataFunction<PLVChatFunctionSwitchVO>(PLVChatFunctionSwitchVO.DataBean.class, true) { // from class: com.plv.livescenes.chatroom.PLVChatroomManager.15
            @Override // com.plv.foundationsdk.rx.PLVRxEncryptDataFunction
            public Pair<Object, Boolean> accept(PLVChatFunctionSwitchVO pLVChatFunctionSwitchVO) {
                return new Pair<>(pLVChatFunctionSwitchVO.getDataObj(), Boolean.valueOf(pLVChatFunctionSwitchVO.isEncryption()));
            }
        }).retryWhen(new PLVRxBaseRetryFunction(Integer.MAX_VALUE, C.DEFAULT_MAX_SEEK_TO_PREVIOUS_POSITION_MS)).compose(new PLVRxBaseTransformer()).subscribe(new Consumer<PLVChatFunctionSwitchVO>() { // from class: com.plv.livescenes.chatroom.PLVChatroomManager.13
            @Override // io.reactivex.functions.Consumer
            public void accept(PLVChatFunctionSwitchVO pLVChatFunctionSwitchVO) throws Exception {
                PLVChatroomManager.this.chatFunctionSwitchVO = pLVChatFunctionSwitchVO;
                Consumer consumer2 = consumer;
                if (consumer2 != null) {
                    consumer2.accept(PLVChatroomManager.this.chatFunctionSwitchVO);
                }
            }
        }, new Consumer<Throwable>() { // from class: com.plv.livescenes.chatroom.PLVChatroomManager.14
            @Override // io.reactivex.functions.Consumer
            public void accept(Throwable th) throws Exception {
                PLVCommonLog.exception(th);
            }
        });
    }

    @Override // com.plv.livescenes.chatroom.IPLVChatroomManager
    public void sendChatImage(final PLVSendLocalImgEvent pLVSendLocalImgEvent, final String str) {
        if (pLVSendLocalImgEvent == null || TextUtils.isEmpty(pLVSendLocalImgEvent.getImageFilePath())) {
            callbackChatImage(new ChatImageRunnable() { // from class: com.plv.livescenes.chatroom.PLVChatroomManager.6
                @Override // com.plv.livescenes.chatroom.PLVChatroomManager.ChatImageRunnable
                public void run(PLVSendChatImageListener pLVSendChatImageListener) {
                    pLVSendChatImageListener.onSendFail(pLVSendLocalImgEvent, -1);
                }
            });
            return;
        }
        if ((this.isCloseRoom && !PLVEventHelper.isSpecialType(getLoginVO().getUserType())) || this.isBanIp) {
            callbackChatImage(new ChatImageRunnable() { // from class: com.plv.livescenes.chatroom.PLVChatroomManager.7
                @Override // com.plv.livescenes.chatroom.PLVChatroomManager.ChatImageRunnable
                public void run(PLVSendChatImageListener pLVSendChatImageListener) {
                    pLVSendChatImageListener.onSendFail(pLVSendLocalImgEvent, PLVChatroomManager.this.isBanIp ? -6 : -4);
                }
            });
            return;
        }
        if (!PLVSocketWrapper.getInstance().isOnlineStatus()) {
            callbackChatImage(new ChatImageRunnable() { // from class: com.plv.livescenes.chatroom.PLVChatroomManager.8
                @Override // com.plv.livescenes.chatroom.PLVChatroomManager.ChatImageRunnable
                public void run(PLVSendChatImageListener pLVSendChatImageListener) {
                    pLVSendChatImageListener.onSendFail(pLVSendLocalImgEvent, -3);
                }
            });
            return;
        }
        try {
            PLVSendChatImageHelper.sendChatImage(getLoginVO().getChannelId(), pLVSendLocalImgEvent, new PLVSendChatImageListener() { // from class: com.plv.livescenes.chatroom.PLVChatroomManager.9
                @Override // com.plv.livescenes.chatroom.send.img.PLVSendChatImageListener
                public void onCheckFail(final PLVSendLocalImgEvent pLVSendLocalImgEvent2, final Throwable th) {
                    PLVChatroomManager.this.callbackChatImage(new ChatImageRunnable() { // from class: com.plv.livescenes.chatroom.PLVChatroomManager.9.4
                        @Override // com.plv.livescenes.chatroom.PLVChatroomManager.ChatImageRunnable
                        public void run(PLVSendChatImageListener pLVSendChatImageListener) {
                            pLVSendChatImageListener.onCheckFail(pLVSendLocalImgEvent2, th);
                        }
                    });
                }

                @Override // com.plv.livescenes.chatroom.send.img.PLVSendChatImageListener
                public void onProgress(final PLVSendLocalImgEvent pLVSendLocalImgEvent2, final float f2) {
                    CompositeDisposable compositeDisposable;
                    List<PLVSendChatImageListener> list = PLVChatroomManager.this.sendChatImageListeners;
                    if (list == null || list.isEmpty() || f2 == 1.0f || (compositeDisposable = PLVChatroomManager.this.compositeDisposable) == null) {
                        return;
                    }
                    compositeDisposable.add(AndroidSchedulers.mainThread().createWorker().schedule(new Runnable() { // from class: com.plv.livescenes.chatroom.PLVChatroomManager.9.3
                        @Override // java.lang.Runnable
                        public void run() {
                            PLVChatroomManager.this.callbackChatImage(new ChatImageRunnable() { // from class: com.plv.livescenes.chatroom.PLVChatroomManager.9.3.1
                                @Override // com.plv.livescenes.chatroom.PLVChatroomManager.ChatImageRunnable
                                public void run(PLVSendChatImageListener pLVSendChatImageListener) {
                                    AnonymousClass3 anonymousClass3 = AnonymousClass3.this;
                                    pLVSendChatImageListener.onProgress(pLVSendLocalImgEvent2, f2);
                                }
                            });
                        }
                    }));
                }

                @Override // com.plv.livescenes.chatroom.send.img.PLVSendChatImageListener
                public void onSendFail(final PLVSendLocalImgEvent pLVSendLocalImgEvent2, final int i2) {
                    PLVChatroomManager.this.callbackChatImage(new ChatImageRunnable() { // from class: com.plv.livescenes.chatroom.PLVChatroomManager.9.2
                        @Override // com.plv.livescenes.chatroom.PLVChatroomManager.ChatImageRunnable
                        public void run(PLVSendChatImageListener pLVSendChatImageListener) {
                            pLVSendChatImageListener.onSendFail(pLVSendLocalImgEvent2, i2);
                        }
                    });
                }

                @Override // com.plv.livescenes.chatroom.send.img.PLVSendChatImageListener
                public void onSuccess(PLVSendLocalImgEvent pLVSendLocalImgEvent2, String str2, String str3) {
                    PLVChatroomManager.this.sendChatImageEvent(pLVSendLocalImgEvent2, str2, str3, str);
                }

                @Override // com.plv.livescenes.chatroom.send.img.PLVSendChatImageListener
                public void onUploadFail(final PLVSendLocalImgEvent pLVSendLocalImgEvent2, final Throwable th) {
                    PLVELogSender.send(PLVErrorCodeChatroomApi.class, 17, th);
                    PLVChatroomManager.this.callbackChatImage(new ChatImageRunnable() { // from class: com.plv.livescenes.chatroom.PLVChatroomManager.9.1
                        @Override // com.plv.livescenes.chatroom.PLVChatroomManager.ChatImageRunnable
                        public void run(PLVSendChatImageListener pLVSendChatImageListener) {
                            pLVSendChatImageListener.onUploadFail(pLVSendLocalImgEvent2, th);
                        }
                    });
                }
            }, this.compositeDisposable);
        } catch (Exception e2) {
            callbackChatImage(new ChatImageRunnable() { // from class: com.plv.livescenes.chatroom.PLVChatroomManager.10
                @Override // com.plv.livescenes.chatroom.PLVChatroomManager.ChatImageRunnable
                public void run(PLVSendChatImageListener pLVSendChatImageListener) {
                    pLVSendChatImageListener.onUploadFail(pLVSendLocalImgEvent, e2);
                }
            });
        }
    }

    @Override // com.plv.livescenes.chatroom.IPLVChatroomManager
    public int sendChatMessage(PLVLocalMessage pLVLocalMessage, String str) {
        return sendChatMessage(pLVLocalMessage, str, false, null);
    }

    @Override // com.plv.livescenes.chatroom.IPLVChatroomManager
    public <DataBean> void sendCustomMsg(PLVBaseCustomEvent<DataBean> pLVBaseCustomEvent) {
        if (preJudgeWeatherSendCustomMsg(pLVBaseCustomEvent)) {
            PLVCustomEvent pLVCustomEvent = new PLVCustomEvent(pLVBaseCustomEvent.getEVENT(), pLVBaseCustomEvent.getEmitMode(), pLVBaseCustomEvent.getData());
            try {
                pLVCustomEvent.setRoomId(Integer.parseInt(PLVSocketWrapper.getInstance().getLoginRoomId()));
                pLVCustomEvent.setUser(null);
                pLVCustomEvent.setId(null);
                pLVCustomEvent.setTime(0L);
                pLVCustomEvent.setMsgSource(null);
                pLVCustomEvent.setMsgType(null);
                if (pLVBaseCustomEvent.getJoinHistory()) {
                    pLVCustomEvent.setJoinHistory(true);
                } else {
                    pLVCustomEvent.setJoinHistory(false);
                }
                if (TextUtils.isEmpty(pLVBaseCustomEvent.getTip())) {
                    pLVCustomEvent.setTip(PLVBaseCustomEvent.TIP_DEFAULT);
                } else {
                    pLVCustomEvent.setTip(pLVBaseCustomEvent.getTip());
                }
                pLVCustomEvent.setVersion(1);
                PLVSocketWrapper.getInstance().emit("customMessage", new Gson().toJson(pLVCustomEvent));
                PLVSendCustomMsgListener pLVSendCustomMsgListener = this.sendCustomMsgListener;
                if (pLVSendCustomMsgListener != null) {
                    pLVSendCustomMsgListener.onSuccess(pLVBaseCustomEvent);
                }
            } catch (NumberFormatException e2) {
                PLVSendCustomMsgListener pLVSendCustomMsgListener2 = this.sendCustomMsgListener;
                if (pLVSendCustomMsgListener2 != null) {
                    pLVSendCustomMsgListener2.onSendFail(pLVBaseCustomEvent, e2);
                }
            }
        }
    }

    @Override // com.plv.livescenes.chatroom.IPLVChatroomManager
    public int sendEmotionImage(PLVChatEmotionEvent pLVChatEmotionEvent, Ack ack) throws JSONException {
        if (this.isCloseRoom && !PLVEventHelper.isSpecialType(getLoginVO().getUserType())) {
            return -4;
        }
        if (this.isBanIp) {
            return -6;
        }
        if (!PLVSocketWrapper.getInstance().isOnlineStatus()) {
            return -3;
        }
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("id", pLVChatEmotionEvent.getId());
            PLVSocketWrapper.getInstance().emit("emotion", jSONObject.toString(), ack);
            return 1;
        } catch (JSONException e2) {
            e2.printStackTrace();
            PLVCommonLog.exception(e2);
            return -2;
        }
    }

    @Override // com.plv.livescenes.chatroom.IPLVChatroomManager
    public void sendInteractiveSocketMessage(String str, Object obj, int i2, final String str2) {
        if (i2 < 0) {
            PLVInteractiveCallbackVO pLVInteractiveCallbackVO = new PLVInteractiveCallbackVO();
            pLVInteractiveCallbackVO.setCode(400);
            pLVInteractiveCallbackVO.setEVENT(str2);
            Message messageObtain = Message.obtain();
            messageObtain.obj = pLVInteractiveCallbackVO;
            messageObtain.what = 16;
            this.handler.sendMessage(messageObtain);
            return;
        }
        String json = obj instanceof String ? (String) obj : PLVGsonUtil.toJson(obj);
        Message messageObtain2 = Message.obtain();
        messageObtain2.what = 15;
        messageObtain2.arg1 = i2 - 1;
        messageObtain2.obj = json;
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_KEY_INTERACTIVE, str2);
        messageObtain2.setData(bundle);
        this.handler.sendMessageDelayed(messageObtain2, 5000L);
        PLVSocketWrapper.getInstance().emit(str, json, new Ack() { // from class: com.plv.livescenes.chatroom.PLVChatroomManager.11
            @Override // io.socket.client.Ack
            public void call(Object... objArr) {
                PLVCommonLog.d(PLVChatroomManager.TAG, "callback:" + objArr[0].toString());
                PLVInteractiveCallbackVO pLVInteractiveCallbackVO2 = (PLVInteractiveCallbackVO) PLVGsonUtil.fromJson(PLVInteractiveCallbackVO.class, objArr[0].toString());
                if (pLVInteractiveCallbackVO2 == null) {
                    pLVInteractiveCallbackVO2 = new PLVInteractiveCallbackVO();
                }
                pLVInteractiveCallbackVO2.setEVENT(str2);
                PLVChatroomManager.this.handler.removeMessages(15);
                Message messageObtain3 = Message.obtain();
                messageObtain3.obj = pLVInteractiveCallbackVO2;
                messageObtain3.what = 16;
                PLVChatroomManager.this.handler.sendMessage(messageObtain3);
            }
        });
    }

    @Override // com.plv.livescenes.chatroom.IPLVChatroomManager
    public void sendLikes(String str) {
        sendLikes(1, str);
    }

    @Override // com.plv.livescenes.chatroom.IPLVChatroomManager
    public int sendLookAtMeMessage() throws JSONException {
        if (!PLVSocketWrapper.getInstance().isOnlineStatus()) {
            return -3;
        }
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("EVENT", PLVEventConstant.Class.EVENT_LOOK_AT_ME);
            PLVSocketWrapper.getInstance().emit("message", jSONObject.toString());
            return 1;
        } catch (JSONException e2) {
            PLVCommonLog.exception(e2);
            return -2;
        }
    }

    @Override // com.plv.livescenes.chatroom.IPLVChatroomManager
    public int sendQuestionMessage(PLVQuestionMessage pLVQuestionMessage) throws JSONException {
        if (pLVQuestionMessage == null || PLVCheckUtils.checkParams(pLVQuestionMessage.getQuestionMessage(), "") != null) {
            return -1;
        }
        if (!PLVSocketWrapper.getInstance().isOnlineStatus()) {
            return -3;
        }
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("EVENT", PLVEventConstant.Chatroom.EVENT_S_QUESTION);
            jSONObject.put("content", pLVQuestionMessage.getQuestionMessage());
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put(ACTOR, PLVSocketUserConstant.ACTOR_STUDENT);
            jSONObject2.put("nick", getLoginVO().getNickName());
            jSONObject2.put("pic", getLoginVO().getAvatarUrl());
            jSONObject2.put("userId", getLoginVO().getUserId());
            jSONObject2.put("userType", getLoginVO().getUserType());
            jSONObject.put("user", jSONObject2);
            jSONObject.put("roomId", PLVSocketWrapper.getInstance().getLoginRoomId());
            PLVSocketWrapper.getInstance().emit("message", jSONObject.toString());
            return 1;
        } catch (JSONException unused) {
            return -2;
        }
    }

    @Override // com.plv.livescenes.chatroom.IPLVChatroomManager
    public int sendQuoteMessage(PLVLocalMessage pLVLocalMessage, String str, String str2) {
        return sendQuoteMessage(pLVLocalMessage, str, false, null, str2);
    }

    @Override // com.plv.livescenes.chatroom.IPLVChatroomManager
    public void setOnlineCount(int i2) {
        this.onlineCount = Math.max(0, i2);
        IPLVOnlineCountListener iPLVOnlineCountListener = this.onlineCountListener;
        if (iPLVOnlineCountListener != null) {
            iPLVOnlineCountListener.onCall(i2);
        }
        this.handler.post(new Runnable() { // from class: com.plv.livescenes.chatroom.PLVChatroomManager.16
            @Override // java.lang.Runnable
            public void run() {
                PLVTeacherStatusInfo pLVTeacherStatusInfo = new PLVTeacherStatusInfo();
                pLVTeacherStatusInfo.setWatchStatus(PLVLiveClassDetailVO.LiveStatus.LOGIN_CHAT_ROOM);
                PLVRxBus.get().post(pLVTeacherStatusInfo);
            }
        });
    }

    @Override // com.plv.livescenes.chatroom.IPLVChatroomManager
    public void setOnlineCountListener(IPLVOnlineCountListener iPLVOnlineCountListener) {
        this.onlineCountListener = iPLVOnlineCountListener;
    }

    @Override // com.plv.livescenes.chatroom.IPLVChatroomManager
    public void setProhibitedWordListener(IPLVProhibitedWordListener iPLVProhibitedWordListener) {
        this.prohibitedWordListener = iPLVProhibitedWordListener;
    }

    @Override // com.plv.livescenes.chatroom.IPLVChatroomManager
    public void setSendCustomMsgListener(PLVSendCustomMsgListener pLVSendCustomMsgListener) {
        this.sendCustomMsgListener = pLVSendCustomMsgListener;
    }

    @Override // com.plv.livescenes.chatroom.IPLVChatroomManager
    public void setSocketCallbackListener(PLVSocketCallbackListener pLVSocketCallbackListener) {
        this.socketCallback = pLVSocketCallbackListener;
    }

    @Override // com.plv.livescenes.chatroom.IPLVChatroomManager
    public int shield(String str) {
        if (TextUtils.isEmpty(str)) {
            return -7;
        }
        if (!PLVSocketWrapper.getInstance().isOnlineStatus()) {
            return -3;
        }
        PLVSocketWrapper.getInstance().emit("message", generateShieldVO(str, true));
        return 1;
    }

    @Override // com.plv.livescenes.chatroom.IPLVChatroomManager
    public void toggleRoom(final boolean z2, final IPLVChatroomManager.RequestApiListener<String> requestApiListener) {
        Disposable disposable = this.toggleRoomDisposable;
        if (disposable != null) {
            disposable.dispose();
        }
        this.toggleRoomDisposable = PLVChatApiRequestHelper.closeRoom(PLVSocketWrapper.getInstance().getLoginRoomId(), z2).subscribe(new Consumer<ResponseBody>() { // from class: com.plv.livescenes.chatroom.PLVChatroomManager.17
            @Override // io.reactivex.functions.Consumer
            public void accept(ResponseBody responseBody) throws Exception {
                String strString = responseBody.string();
                JSONObject jSONObject = new JSONObject(strString);
                PLVCheckUtils.checkCodeThrow(jSONObject.optInt("code"), jSONObject.optString("message"));
                PLVChatroomManager pLVChatroomManager = PLVChatroomManager.this;
                boolean z3 = z2;
                pLVChatroomManager.isCloseRoom = z3;
                pLVChatroomManager.callRoomStatusListenerStatus(z3);
                IPLVChatroomManager.RequestApiListener requestApiListener2 = requestApiListener;
                if (requestApiListener2 != null) {
                    requestApiListener2.onSuccess(strString);
                }
            }
        }, new Consumer<Throwable>() { // from class: com.plv.livescenes.chatroom.PLVChatroomManager.18
            @Override // io.reactivex.functions.Consumer
            public void accept(Throwable th) throws Exception {
                PLVCommonLog.exception(th);
                com.plv.socket.log.PLVELogSender.send(PLVChatroomELog.class, PLVChatroomELog.Event.CLOSE_CHATROOM_FAIL, th);
                IPLVChatroomManager.RequestApiListener requestApiListener2 = requestApiListener;
                if (requestApiListener2 != null) {
                    requestApiListener2.onFailed(th);
                }
            }
        });
    }

    @Override // com.plv.livescenes.chatroom.IPLVChatroomManager
    public int unKick(String str) {
        if (TextUtils.isEmpty(str)) {
            return -7;
        }
        if (!PLVSocketWrapper.getInstance().isOnlineStatus()) {
            return -3;
        }
        PLVChatroomUnKickVO pLVChatroomUnKickVO = new PLVChatroomUnKickVO();
        pLVChatroomUnKickVO.setRoomId(PLVSocketWrapper.getInstance().getLoginRoomId());
        pLVChatroomUnKickVO.setChannelId(getLoginVO().getChannelId());
        pLVChatroomUnKickVO.setType("userId");
        pLVChatroomUnKickVO.setValue(str);
        pLVChatroomUnKickVO.setSign(EncryptUtils.encryptMD5ToString(PLVChatApiRequestHelper.CONSTANT_ROOM_SIGN + PLVSocketWrapper.getInstance().getLoginRoomId()).toLowerCase());
        PLVSocketWrapper.getInstance().emit("message", pLVChatroomUnKickVO);
        return 1;
    }

    @Override // com.plv.livescenes.chatroom.IPLVChatroomManager
    public int sendChatMessage(PLVLocalMessage pLVLocalMessage, String str, boolean z2, Ack ack) {
        return sendQuoteMessage(pLVLocalMessage, str, z2, ack, null);
    }

    @Override // com.plv.livescenes.chatroom.IPLVChatroomManager
    public void sendLikes(int i2, String str) {
        int i3 = this.willSendSocketLikesCount;
        if (i3 == 200 || this.willSendApiLikesCount == 200 || i2 <= 0) {
            return;
        }
        this.sendLikesSessionId = str;
        if (i3 == 0) {
            this.willSendSocketLikesCount = Math.min(200, i3 + i2);
            startEventCountdown(12, 5000L);
        } else {
            this.willSendSocketLikesCount = Math.min(200, i3 + i2);
        }
        int i4 = this.willSendApiLikesCount;
        if (i4 != 0) {
            this.willSendApiLikesCount = Math.min(200, i4 + i2);
        } else {
            this.willSendApiLikesCount = Math.min(200, i4 + i2);
            startEventCountdown(13, 5000L);
        }
    }

    @Override // com.plv.livescenes.chatroom.IPLVChatroomManager
    public int sendQuoteMessage(PLVLocalMessage pLVLocalMessage, String str, boolean z2, Ack ack, String str2) throws JSONException {
        if (pLVLocalMessage == null || PLVCheckUtils.checkParams(pLVLocalMessage.getSpeakMessage(), "") != null) {
            return -1;
        }
        if (this.isCloseRoom && !PLVEventHelper.isSpecialType(getLoginVO().getUserType())) {
            return -4;
        }
        if (this.isBanIp) {
            return -6;
        }
        if (!PLVSocketWrapper.getInstance().isOnlineStatus()) {
            return -3;
        }
        JSONObject jSONObject = new JSONObject();
        try {
            JSONArray jSONArray = new JSONArray();
            jSONObject.put("EVENT", "SPEAK");
            jSONArray.put(0, pLVLocalMessage.getSpeakMessage());
            jSONObject.put(VALUES, jSONArray);
            jSONObject.put(ACCOUNT_ID, PolyvLiveSDKClient.getInstance().getUserId());
            jSONObject.put("sessionId", str);
            jSONObject.put("roomId", PLVSocketWrapper.getInstance().getLoginRoomId());
            jSONObject.put(NEED_ID_CALLBACK, z2);
            if (!TextUtils.isEmpty(str2)) {
                jSONObject.put(QUOTE_ID, str2);
            }
            if (pLVLocalMessage.isManagerChatMsg()) {
                jSONObject.put("source", pLVLocalMessage.getSource());
            }
            PLVSocketWrapper.getInstance().emit("message", jSONObject.toString(), ack);
            return 1;
        } catch (JSONException e2) {
            PLVCommonLog.exception(e2);
            return -2;
        }
    }
}
