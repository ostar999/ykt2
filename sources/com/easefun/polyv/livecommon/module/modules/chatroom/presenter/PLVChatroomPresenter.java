package com.easefun.polyv.livecommon.module.modules.chatroom.presenter;

import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.util.Pair;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import cn.hutool.core.text.StrPool;
import com.alipay.sdk.authjs.a;
import com.easefun.polyv.livecommon.module.config.PLVLiveChannelConfig;
import com.easefun.polyv.livecommon.module.data.IPLVLiveRoomDataManager;
import com.easefun.polyv.livecommon.module.data.PLVLiveRoomDataRequester;
import com.easefun.polyv.livecommon.module.data.PLVStatefulData;
import com.easefun.polyv.livecommon.module.modules.chatroom.PLVCustomGiftBean;
import com.easefun.polyv.livecommon.module.modules.chatroom.PLVCustomGiftEvent;
import com.easefun.polyv.livecommon.module.modules.chatroom.PLVSpecialTypeTag;
import com.easefun.polyv.livecommon.module.modules.chatroom.contract.IPLVChatroomContract;
import com.easefun.polyv.livecommon.module.modules.chatroom.presenter.data.PLVChatroomData;
import com.easefun.polyv.livecommon.module.modules.socket.PLVSocketMessage;
import com.easefun.polyv.livecommon.module.utils.imageloader.PLVImageLoader;
import com.easefun.polyv.livecommon.module.utils.span.PLVFaceManager;
import com.easefun.polyv.livecommon.module.utils.span.PLVTextFaceLoader;
import com.easefun.polyv.livecommon.ui.widget.gif.RelativeImageSpan;
import com.easefun.polyv.livecommon.ui.widget.itemview.PLVBaseViewData;
import com.easefun.polyv.livescenes.chatroom.IPolyvOnlineCountListener;
import com.easefun.polyv.livescenes.chatroom.IPolyvProhibitedWordListener;
import com.easefun.polyv.livescenes.chatroom.PolyvChatroomManager;
import com.easefun.polyv.livescenes.chatroom.PolyvLocalMessage;
import com.easefun.polyv.livescenes.chatroom.PolyvQuestionMessage;
import com.easefun.polyv.livescenes.chatroom.send.custom.PolyvBaseCustomEvent;
import com.easefun.polyv.livescenes.chatroom.send.custom.PolyvCustomEvent;
import com.easefun.polyv.livescenes.chatroom.send.img.PolyvSendLocalImgEvent;
import com.easefun.polyv.livescenes.log.chat.PolyvChatroomELog;
import com.easefun.polyv.livescenes.model.PLVEmotionImageVO;
import com.easefun.polyv.livescenes.model.PolyvChatFunctionSwitchVO;
import com.easefun.polyv.livescenes.model.PolyvLiveClassDetailVO;
import com.easefun.polyv.livescenes.net.PolyvApiManager;
import com.easefun.polyv.livescenes.socket.PolyvSocketWrapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.plv.foundationsdk.log.PLVCommonLog;
import com.plv.foundationsdk.net.PLVResponseApiBean2;
import com.plv.foundationsdk.rx.PLVRxBaseTransformer;
import com.plv.foundationsdk.rx.PLVRxBus;
import com.plv.foundationsdk.utils.PLVGsonUtil;
import com.plv.livescenes.chatroom.IPLVChatroomManager;
import com.plv.livescenes.chatroom.PLVChatApiRequestHelper;
import com.plv.livescenes.linkmic.manager.PLVLinkMicManager;
import com.plv.livescenes.log.chat.PLVChatroomELog;
import com.plv.livescenes.model.PLVChatFunctionSwitchVO;
import com.plv.livescenes.model.PLVEmotionImageVO;
import com.plv.livescenes.model.PLVKickUsersVO;
import com.plv.livescenes.model.interact.PLVCardPushVO;
import com.plv.livescenes.socket.PLVSocketWrapper;
import com.plv.socket.event.PLVBaseEvent;
import com.plv.socket.event.PLVEventHelper;
import com.plv.socket.event.chat.PLVChatEmotionEvent;
import com.plv.socket.event.chat.PLVChatQuoteVO;
import com.plv.socket.event.chat.PLVRewardEvent;
import com.plv.socket.event.chat.PLVSpeakEvent;
import com.plv.socket.event.history.PLVChatImgHistoryEvent;
import com.plv.socket.event.history.PLVFileShareHistoryEvent;
import com.plv.socket.event.history.PLVHistoryConstant;
import com.plv.socket.event.history.PLVSpeakHistoryEvent;
import com.plv.socket.event.ppt.PLVPptShareFileVO;
import com.plv.socket.impl.PLVSocketMessageObserver;
import com.plv.socket.log.PLVELogSender;
import com.plv.socket.socketio.PLVSocketIOClient;
import com.plv.socket.socketio.PLVSocketIOObservable;
import com.plv.socket.status.PLVSocketStatus;
import com.plv.thirdpart.blankj.utilcode.util.ConvertUtils;
import com.plv.thirdpart.blankj.utilcode.util.Utils;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.socket.client.Ack;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import okhttp3.ResponseBody;
import org.eclipse.jetty.util.URIUtil;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes3.dex */
public class PLVChatroomPresenter implements IPLVChatroomContract.IChatroomPresenter {
    private static final int CHAT_MESSAGE_TIMESPAN = 500;
    public static final int GET_CHAT_HISTORY_COUNT = 10;
    private static final String TAG = "PLVChatroomPresenter";
    private Disposable chatEmotionImagesDisposable;
    private Disposable chatHistoryDisposable;
    private Observer<PLVStatefulData<PolyvLiveClassDetailVO>> classDetailVOObserver;
    private Observer<PLVStatefulData<PolyvChatFunctionSwitchVO>> functionSwitchObserver;
    private int getChatHistoryTime;
    private String groupId;
    private boolean hasRequestHistoryEvent;
    private List<IPLVChatroomContract.IChatroomView> iChatroomViews;
    private boolean isFocusMode;
    private boolean isHistoryContainRewardEvent;
    private boolean isNoMoreChatHistory;
    private int kickCount;
    private Disposable kickUsersDisposable;
    private long likesCount;
    private IPLVLiveRoomDataManager liveRoomDataManager;
    private Disposable messageDisposable;
    private int onlineCount;
    private int requestHistoryViewIndex;
    private long viewerCount;
    private int getChatHistoryCount = 10;
    private PLVChatroomData chatroomData = new PLVChatroomData();

    public interface ViewRunnable {
        void run(@NonNull IPLVChatroomContract.IChatroomView view);
    }

    public PLVChatroomPresenter(@NonNull IPLVLiveRoomDataManager liveRoomDataManager) {
        this.liveRoomDataManager = liveRoomDataManager;
        subscribeChatroomMessage();
        observeLiveRoomData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public List<PLVBaseViewData<PLVBaseEvent>> acceptChatHistory(JSONArray jsonArray, int[] speakEmojiSizes) {
        PLVFileShareHistoryEvent pLVFileShareHistoryEvent;
        int i2;
        if (speakEmojiSizes == null) {
            speakEmojiSizes = new int[]{ConvertUtils.dp2px(12.0f)};
        }
        ArrayList arrayList = new ArrayList();
        int i3 = 0;
        while (true) {
            if (i3 >= (jsonArray.length() <= this.getChatHistoryCount ? jsonArray.length() : jsonArray.length() - 1)) {
                return arrayList;
            }
            JSONObject jSONObjectOptJSONObject = jsonArray.optJSONObject(i3);
            if (jSONObjectOptJSONObject != null) {
                String strOptString = jSONObjectOptJSONObject.optString(a.f3175h);
                if (TextUtils.isEmpty(strOptString)) {
                    String strOptString2 = jSONObjectOptJSONObject.optString("msgSource");
                    JSONObject jSONObjectOptJSONObject2 = jSONObjectOptJSONObject.optJSONObject(PLVLinkMicManager.USER);
                    JSONObject jSONObjectOptJSONObject3 = jSONObjectOptJSONObject.optJSONObject("content");
                    int i4 = 2;
                    if (TextUtils.isEmpty(strOptString2)) {
                        if (jSONObjectOptJSONObject2 != null && !"2".equals(jSONObjectOptJSONObject2.optString("uid")) && jSONObjectOptJSONObject3 == null) {
                            PLVSpeakHistoryEvent pLVSpeakHistoryEvent = (PLVSpeakHistoryEvent) PLVGsonUtil.fromJson(PLVSpeakHistoryEvent.class, jSONObjectOptJSONObject.toString());
                            if (PolyvSocketWrapper.getInstance().getLoginVO().getUserId().equals(pLVSpeakHistoryEvent.getUser().getUserId())) {
                                pLVSpeakHistoryEvent.getUser().setNick(PolyvSocketWrapper.getInstance().getLoginVO().getNickName());
                                i4 = 1;
                            }
                            pLVSpeakHistoryEvent.setObjects(PLVTextFaceLoader.messageToSpan(convertSpecialString(pLVSpeakHistoryEvent.getContent()), speakEmojiSizes, Utils.getApp()));
                            PLVChatQuoteVO quote = pLVSpeakHistoryEvent.getQuote();
                            if (quote != null && quote.isSpeakMessage()) {
                                quote.setObjects(PLVTextFaceLoader.messageToSpan(convertSpecialString(quote.getContent()), speakEmojiSizes, Utils.getApp()));
                            }
                            arrayList.add(0, new PLVBaseViewData(pLVSpeakHistoryEvent, i4, PLVEventHelper.isSpecialType(pLVSpeakHistoryEvent.getUser().getUserType()) || PolyvSocketWrapper.getInstance().getLoginVO().getUserId().equals(pLVSpeakHistoryEvent.getUser().getUserId()) ? new PLVSpecialTypeTag(pLVSpeakHistoryEvent.getUser().getUserId()) : null));
                        }
                    } else if ("chatImg".equals(strOptString2)) {
                        PLVChatImgHistoryEvent pLVChatImgHistoryEvent = (PLVChatImgHistoryEvent) PLVGsonUtil.fromJson(PLVChatImgHistoryEvent.class, jSONObjectOptJSONObject.toString());
                        if (PolyvSocketWrapper.getInstance().getLoginVO().getUserId().equals(pLVChatImgHistoryEvent.getUser().getUserId())) {
                            pLVChatImgHistoryEvent.getUser().setNick(PolyvSocketWrapper.getInstance().getLoginVO().getNickName());
                            i2 = 3;
                        } else {
                            i2 = 4;
                        }
                        arrayList.add(0, new PLVBaseViewData(pLVChatImgHistoryEvent, i2, PLVEventHelper.isSpecialType(pLVChatImgHistoryEvent.getUser().getUserType()) || PolyvSocketWrapper.getInstance().getLoginVO().getUserId().equals(pLVChatImgHistoryEvent.getUser().getUserId()) ? new PLVSpecialTypeTag(pLVChatImgHistoryEvent.getUser().getUserId()) : null));
                    } else if (PLVHistoryConstant.MSGSOURCE_REWARD.equals(strOptString2)) {
                        Gson gson = com.plv.livescenes.chatroom.event.PLVEventHelper.gson;
                        PLVRewardEvent.ContentBean contentBean = (PLVRewardEvent.ContentBean) gson.fromJson(jSONObjectOptJSONObject3.toString(), PLVRewardEvent.ContentBean.class);
                        PLVRewardEvent pLVRewardEvent = new PLVRewardEvent();
                        if (contentBean != null) {
                            pLVRewardEvent.setContent(contentBean);
                            pLVRewardEvent.setRoomId(jSONObjectOptJSONObject2.optInt(PLVLinkMicManager.ROOM_ID));
                            PLVBaseViewData pLVBaseViewData = new PLVBaseViewData(PLVCustomGiftEvent.generateCustomGiftEvent(pLVRewardEvent), 100);
                            if (this.isHistoryContainRewardEvent) {
                                arrayList.add(0, pLVBaseViewData);
                            }
                        }
                        PLVRewardEvent pLVRewardEvent2 = (PLVRewardEvent) gson.fromJson(jSONObjectOptJSONObject.toString(), PLVRewardEvent.class);
                        if (pLVRewardEvent2 != null && jSONObjectOptJSONObject2 != null) {
                            pLVRewardEvent2.setRoomId(jSONObjectOptJSONObject2.optInt(PLVLinkMicManager.ROOM_ID));
                            PLVBaseViewData pLVBaseViewData2 = new PLVBaseViewData(PLVCustomGiftEvent.generateCustomGiftEvent(pLVRewardEvent2), 100);
                            if (this.isHistoryContainRewardEvent) {
                                arrayList.add(0, pLVBaseViewData2);
                            } else {
                                Spannable spannableGenerateRewardSpan = generateRewardSpan(pLVRewardEvent2.getContent().getUnick(), pLVRewardEvent2.getContent().getGimg(), pLVRewardEvent2.getContent().getGoodNum());
                                if (spannableGenerateRewardSpan != null) {
                                    pLVRewardEvent2.setObjects(spannableGenerateRewardSpan);
                                    arrayList.add(0, new PLVBaseViewData(pLVRewardEvent2, 7, Boolean.FALSE));
                                }
                            }
                        }
                    } else if ("file".equals(strOptString2) && (pLVFileShareHistoryEvent = (PLVFileShareHistoryEvent) PLVGsonUtil.fromJson(PLVFileShareHistoryEvent.class, jSONObjectOptJSONObject.toString())) != null) {
                        parseFileShareEventFileData(pLVFileShareHistoryEvent);
                        if (PLVSocketWrapper.getInstance().getLoginVO().getUserId().equals(pLVFileShareHistoryEvent.getUser().getUserId())) {
                            pLVFileShareHistoryEvent.getUser().setNick(PLVSocketWrapper.getInstance().getLoginVO().getNickName());
                            i4 = 1;
                        }
                        pLVFileShareHistoryEvent.setObjects(PLVTextFaceLoader.messageToSpan(convertSpecialString(pLVFileShareHistoryEvent.getContent()), speakEmojiSizes, Utils.getApp()));
                        PLVChatQuoteVO quote2 = pLVFileShareHistoryEvent.getQuote();
                        if (quote2 != null && quote2.isSpeakMessage()) {
                            quote2.setObjects(PLVTextFaceLoader.messageToSpan(convertSpecialString(quote2.getContent()), speakEmojiSizes, Utils.getApp()));
                        }
                        arrayList.add(0, new PLVBaseViewData(pLVFileShareHistoryEvent, i4, PLVEventHelper.isSpecialType(pLVFileShareHistoryEvent.getUser().getUserType()) || PLVSocketWrapper.getInstance().getLoginVO().getUserId().equals(pLVFileShareHistoryEvent.getUser().getUserId()) ? new PLVSpecialTypeTag(pLVFileShareHistoryEvent.getUser().getUserId()) : null));
                    }
                } else {
                    "customMessage".equals(strOptString);
                }
            }
            i3++;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:187:0x0497  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void acceptChatroomMessage(java.util.List<com.easefun.polyv.livecommon.module.modules.socket.PLVSocketMessage> r12) {
        /*
            Method dump skipped, instructions count: 1434
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.easefun.polyv.livecommon.module.modules.chatroom.presenter.PLVChatroomPresenter.acceptChatroomMessage(java.util.List):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void acceptEmotionMessage(final PLVChatEmotionEvent emotionEvent, String messageId) {
        emotionEvent.setMessageId(messageId);
        emotionEvent.setTime(Long.valueOf(System.currentTimeMillis()));
        callbackToView(new ViewRunnable() { // from class: com.easefun.polyv.livecommon.module.modules.chatroom.presenter.PLVChatroomPresenter.49
            @Override // com.easefun.polyv.livecommon.module.modules.chatroom.presenter.PLVChatroomPresenter.ViewRunnable
            public void run(@NonNull @NotNull IPLVChatroomContract.IChatroomView view) {
                view.onLoadEmotionMessage(emotionEvent);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void acceptLocalChatMessage(final PolyvLocalMessage textMessage, String messageId) {
        textMessage.setId(messageId);
        textMessage.setObjects(PLVTextFaceLoader.messageToSpan(textMessage.getSpeakMessage(), getSpeakEmojiSizes(), Utils.getApp()));
        textMessage.setTime(Long.valueOf(System.currentTimeMillis()));
        callbackToView(new ViewRunnable() { // from class: com.easefun.polyv.livecommon.module.modules.chatroom.presenter.PLVChatroomPresenter.48
            @Override // com.easefun.polyv.livecommon.module.modules.chatroom.presenter.PLVChatroomPresenter.ViewRunnable
            public void run(@NotNull IPLVChatroomContract.IChatroomView view) {
                view.onLocalSpeakMessage(textMessage);
            }
        });
        this.chatroomData.postSpeakMessageData((CharSequence) textMessage.getObjects()[0], true);
    }

    public static /* synthetic */ int access$808(PLVChatroomPresenter pLVChatroomPresenter) {
        int i2 = pLVChatroomPresenter.getChatHistoryTime;
        pLVChatroomPresenter.getChatHistoryTime = i2 + 1;
        return i2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void callbackToView(ViewRunnable runnable) {
        List<IPLVChatroomContract.IChatroomView> list = this.iChatroomViews;
        if (list != null) {
            for (IPLVChatroomContract.IChatroomView iChatroomView : list) {
                if (iChatroomView != null && runnable != null) {
                    runnable.run(iChatroomView);
                }
            }
        }
    }

    private void clearHistoryInfo() {
        this.getChatHistoryTime = 0;
        this.hasRequestHistoryEvent = false;
        this.isNoMoreChatHistory = false;
        Disposable disposable = this.chatHistoryDisposable;
        if (disposable != null) {
            disposable.dispose();
        }
    }

    public static String convertSpecialString(String input) {
        return input.replace("&lt;", "<").replace("&lt", "<").replace("&gt;", ">").replace("&gt", ">").replace("&yen;", "¥").replace("&yen", "¥");
    }

    @Nullable
    private static PLVPptShareFileVO createFileData(@NonNull String jsonContent) {
        Map map = (Map) PLVGsonUtil.fromJson(new TypeToken<Map<String, String>>() { // from class: com.easefun.polyv.livecommon.module.modules.chatroom.presenter.PLVChatroomPresenter.50
        }, jsonContent);
        if (map == null) {
            return null;
        }
        String str = (String) map.get("url");
        String str2 = (String) map.get("name");
        return new PLVPptShareFileVO().setUrl(str).setName(str2).setSuffix(str2 == null ? "" : str2.substring(str2.lastIndexOf(StrPool.DOT) + 1));
    }

    private Spannable generateRewardSpan(String nickName, String goodImageUrl, int goodNum) {
        if (goodImageUrl.startsWith("//")) {
            goodImageUrl = URIUtil.HTTPS_COLON + goodImageUrl;
        }
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(nickName + " 赠送 p");
        int length = spannableStringBuilder.length() - 1;
        int length2 = spannableStringBuilder.length();
        if (goodNum != 1) {
            spannableStringBuilder.append((CharSequence) (" x" + goodNum));
        }
        Drawable imageAsDrawable = PLVImageLoader.getInstance().getImageAsDrawable(Utils.getApp(), goodImageUrl);
        if (imageAsDrawable == null) {
            return null;
        }
        int iDp2px = ConvertUtils.dp2px(12.0f) * 2;
        imageAsDrawable.setBounds(0, 0, iDp2px, iDp2px);
        spannableStringBuilder.setSpan(new RelativeImageSpan(imageAsDrawable, 3), length, length2, 33);
        return spannableStringBuilder;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public PLVLiveChannelConfig getConfig() {
        return this.liveRoomDataManager.getConfig();
    }

    private int[] getEmojiSizes(int textSizeType) {
        ArrayList arrayList = new ArrayList();
        List<IPLVChatroomContract.IChatroomView> list = this.iChatroomViews;
        if (list != null) {
            for (IPLVChatroomContract.IChatroomView iChatroomView : list) {
                int quizEmojiSize = textSizeType != 1 ? textSizeType != 2 ? 0 : iChatroomView.getQuizEmojiSize() : iChatroomView.getSpeakEmojiSize();
                if (iChatroomView == null || quizEmojiSize <= 0) {
                    arrayList.add(Integer.valueOf(ConvertUtils.dp2px(12.0f)));
                } else {
                    arrayList.add(Integer.valueOf(quizEmojiSize));
                }
            }
        }
        Integer[] numArr = (Integer[]) arrayList.toArray(new Integer[0]);
        int[] iArr = new int[numArr.length];
        for (int i2 = 0; i2 < numArr.length; i2++) {
            iArr[i2] = numArr[i2].intValue();
        }
        return iArr;
    }

    private int[] getQuizEmojiSizes() {
        return getEmojiSizes(2);
    }

    private String getRoomIdCombineDiscuss() {
        if (!TextUtils.isEmpty(this.groupId)) {
            return this.groupId;
        }
        String loginRoomId = PolyvSocketWrapper.getInstance().getLoginRoomId();
        return TextUtils.isEmpty(loginRoomId) ? getConfig().getChannelId() : loginRoomId;
    }

    private void observeLiveRoomData() {
        this.functionSwitchObserver = new Observer<PLVStatefulData<PolyvChatFunctionSwitchVO>>() { // from class: com.easefun.polyv.livecommon.module.modules.chatroom.presenter.PLVChatroomPresenter.51
            @Override // androidx.lifecycle.Observer
            public void onChanged(@Nullable PLVStatefulData<PolyvChatFunctionSwitchVO> chatFunctionSwitchStateData) {
                PolyvChatFunctionSwitchVO data;
                List<PLVChatFunctionSwitchVO.DataBean> data2;
                PLVChatroomPresenter.this.liveRoomDataManager.getFunctionSwitchVO().removeObserver(this);
                if (chatFunctionSwitchStateData == null || !chatFunctionSwitchStateData.isSuccess() || (data = chatFunctionSwitchStateData.getData()) == null || data.getData() == null || (data2 = data.getData()) == null) {
                    return;
                }
                PLVChatroomPresenter.this.chatroomData.postFunctionSwitchData(data2);
            }
        };
        this.liveRoomDataManager.getFunctionSwitchVO().observeForever(this.functionSwitchObserver);
        this.classDetailVOObserver = new Observer<PLVStatefulData<PolyvLiveClassDetailVO>>() { // from class: com.easefun.polyv.livecommon.module.modules.chatroom.presenter.PLVChatroomPresenter.52
            @Override // androidx.lifecycle.Observer
            public void onChanged(@Nullable PLVStatefulData<PolyvLiveClassDetailVO> classDetailVOStateData) {
                PolyvLiveClassDetailVO data;
                PLVChatroomPresenter.this.liveRoomDataManager.getClassDetailVO().removeObserver(this);
                if (classDetailVOStateData == null || !classDetailVOStateData.isSuccess() || (data = classDetailVOStateData.getData()) == null || data.getData() == null) {
                    return;
                }
                PLVChatroomPresenter.this.likesCount = data.getData().getLikes();
                PLVChatroomPresenter.this.viewerCount = data.getData().getPageView();
                PLVChatroomPresenter.this.chatroomData.postLikesCountData(PLVChatroomPresenter.this.likesCount);
                PLVChatroomPresenter.this.chatroomData.postViewerCountData(PLVChatroomPresenter.this.viewerCount);
            }
        };
        this.liveRoomDataManager.getClassDetailVO().observeForever(this.classDetailVOObserver);
    }

    private static void parseFileShareEventFileData(@NonNull PLVFileShareHistoryEvent fileShareEvent) {
        fileShareEvent.setFileData(createFileData(fileShareEvent.getContent()));
    }

    private static void parseSpeakEventFileData(@NonNull PLVSpeakEvent speakEvent) {
        if (speakEvent.isFileShareEvent()) {
            speakEvent.setFileData(createFileData(speakEvent.getValues().get(0)));
        }
    }

    private void subscribeChatroomMessage() {
        this.messageDisposable = PLVRxBus.get().toObservable(PLVSocketMessage.class).buffer(500L, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).map(new Function<List<PLVSocketMessage>, List<PLVSocketMessage>>() { // from class: com.easefun.polyv.livecommon.module.modules.chatroom.presenter.PLVChatroomPresenter.22
            @Override // io.reactivex.functions.Function
            public List<PLVSocketMessage> apply(List<PLVSocketMessage> chatroomMessages) throws Exception {
                if (PolyvSocketWrapper.getInstance().getLoginVO() == null) {
                    return new ArrayList();
                }
                if (PLVChatroomPresenter.this.getConfig().getChannelId().equals(PolyvSocketWrapper.getInstance().getLoginVO().getChannelId())) {
                    return chatroomMessages;
                }
                return null;
            }
        }).observeOn(Schedulers.computation()).subscribe(new Consumer<List<PLVSocketMessage>>() { // from class: com.easefun.polyv.livecommon.module.modules.chatroom.presenter.PLVChatroomPresenter.20
            @Override // io.reactivex.functions.Consumer
            public void accept(List<PLVSocketMessage> chatroomMessages) throws Exception {
                PLVChatroomPresenter.this.acceptChatroomMessage(chatroomMessages);
            }
        }, new Consumer<Throwable>() { // from class: com.easefun.polyv.livecommon.module.modules.chatroom.presenter.PLVChatroomPresenter.21
            @Override // io.reactivex.functions.Consumer
            public void accept(Throwable throwable) throws Exception {
                PLVCommonLog.exception(throwable);
            }
        });
    }

    @Override // com.easefun.polyv.livecommon.module.modules.chatroom.contract.IPLVChatroomContract.IChatroomPresenter
    public void destroy() {
        clearHistoryInfo();
        List<IPLVChatroomContract.IChatroomView> list = this.iChatroomViews;
        if (list != null) {
            list.clear();
        }
        Disposable disposable = this.messageDisposable;
        if (disposable != null) {
            disposable.dispose();
        }
        Disposable disposable2 = this.chatEmotionImagesDisposable;
        if (disposable2 != null) {
            disposable2.dispose();
        }
        Disposable disposable3 = this.kickUsersDisposable;
        if (disposable3 != null) {
            disposable3.dispose();
        }
        this.liveRoomDataManager.getFunctionSwitchVO().removeObserver(this.functionSwitchObserver);
        this.liveRoomDataManager.getClassDetailVO().removeObserver(this.classDetailVOObserver);
        PolyvChatroomManager.getInstance().destroy();
    }

    @Override // com.easefun.polyv.livecommon.module.modules.chatroom.contract.IPLVChatroomContract.IChatroomPresenter
    public Observable<PLVCardPushVO> getCardPushInfo(String cardId) {
        return PLVChatApiRequestHelper.requestCardPushInfo(getConfig().getChannelId(), cardId);
    }

    @Override // com.easefun.polyv.livecommon.module.modules.chatroom.contract.IPLVChatroomContract.IChatroomPresenter
    public void getChatEmotionImages() {
        String channelId = PLVSocketIOClient.getInstance().getChannelId();
        String accountUserId = PLVSocketIOClient.getInstance().getAccountUserId();
        Disposable disposable = this.chatEmotionImagesDisposable;
        if (disposable != null) {
            disposable.dispose();
        }
        this.chatEmotionImagesDisposable = PolyvApiManager.getPolyvApichatApi().getEmotionImages(channelId, accountUserId, 1, 100).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<PLVResponseApiBean2<PLVEmotionImageVO>>() { // from class: com.easefun.polyv.livecommon.module.modules.chatroom.presenter.PLVChatroomPresenter.18
            @Override // io.reactivex.functions.Consumer
            public void accept(PLVResponseApiBean2<PLVEmotionImageVO> polyvEmotionImageVO) throws Exception {
                if (polyvEmotionImageVO == null || polyvEmotionImageVO.getData() == null || polyvEmotionImageVO.getData().getList() == null) {
                    return;
                }
                List<PLVEmotionImageVO.EmotionImage> list = polyvEmotionImageVO.getData().getList();
                PLVChatroomPresenter.this.chatroomData.postEmotionImages(list);
                PLVFaceManager.getInstance().initEmotionList(list);
            }
        }, new Consumer<Throwable>() { // from class: com.easefun.polyv.livecommon.module.modules.chatroom.presenter.PLVChatroomPresenter.19
            @Override // io.reactivex.functions.Consumer
            public void accept(Throwable throwable) throws Exception {
                throwable.printStackTrace();
                PLVCommonLog.exception(throwable);
            }
        });
    }

    @Override // com.easefun.polyv.livecommon.module.modules.chatroom.contract.IPLVChatroomContract.IChatroomPresenter
    public int getChatHistoryTime() {
        return this.getChatHistoryTime;
    }

    @Override // com.easefun.polyv.livecommon.module.modules.chatroom.contract.IPLVChatroomContract.IChatroomPresenter
    @NonNull
    public PLVChatroomData getData() {
        return this.chatroomData;
    }

    @Override // com.easefun.polyv.livecommon.module.modules.chatroom.contract.IPLVChatroomContract.IChatroomPresenter
    public int[] getSpeakEmojiSizes() {
        return getEmojiSizes(1);
    }

    @Override // com.easefun.polyv.livecommon.module.modules.chatroom.contract.IPLVChatroomContract.IChatroomPresenter
    public int getViewIndex(IPLVChatroomContract.IChatroomView v2) {
        List<IPLVChatroomContract.IChatroomView> list = this.iChatroomViews;
        if (list == null) {
            return -1;
        }
        return list.indexOf(v2);
    }

    @Override // com.easefun.polyv.livecommon.module.modules.chatroom.contract.IPLVChatroomContract.IChatroomPresenter
    public void init() {
        PolyvChatroomManager.getInstance().init();
        PolyvChatroomManager.getInstance().setProhibitedWordListener(new IPolyvProhibitedWordListener() { // from class: com.easefun.polyv.livecommon.module.modules.chatroom.presenter.PLVChatroomPresenter.1
            @Override // com.plv.livescenes.chatroom.IPLVProhibitedWordListener
            public void onSendProhibitedWord(@NonNull final String prohibitedMessage, @NonNull final String hintMsg, @NonNull final String status) {
                PLVCommonLog.d(PLVChatroomPresenter.TAG, "chatroom onSendProhibitedWord: 发送的消息涉及违禁词");
                if (PLVChatroomPresenter.this.getConfig().getChannelId().equals(PolyvSocketWrapper.getInstance().getLoginVO().getChannelId())) {
                    PLVChatroomPresenter.this.callbackToView(new ViewRunnable() { // from class: com.easefun.polyv.livecommon.module.modules.chatroom.presenter.PLVChatroomPresenter.1.1
                        @Override // com.easefun.polyv.livecommon.module.modules.chatroom.presenter.PLVChatroomPresenter.ViewRunnable
                        public void run(@NonNull IPLVChatroomContract.IChatroomView view) {
                            view.onSendProhibitedWord(prohibitedMessage, hintMsg, status);
                        }
                    });
                }
            }
        });
        PolyvChatroomManager.getInstance().addOnRoomStatusListener(new IPLVChatroomManager.RoomStatusListener() { // from class: com.easefun.polyv.livecommon.module.modules.chatroom.presenter.PLVChatroomPresenter.2
            @Override // com.plv.livescenes.chatroom.IPLVChatroomManager.RoomStatusListener
            public void onStatus(final boolean isClose) {
                PLVCommonLog.d(PLVChatroomPresenter.TAG, "chatroom onRoomStatus: " + isClose);
                if (PLVChatroomPresenter.this.getConfig().getChannelId().equals(PolyvSocketWrapper.getInstance().getLoginVO().getChannelId())) {
                    PLVChatroomPresenter.this.callbackToView(new ViewRunnable() { // from class: com.easefun.polyv.livecommon.module.modules.chatroom.presenter.PLVChatroomPresenter.2.1
                        @Override // com.easefun.polyv.livecommon.module.modules.chatroom.presenter.PLVChatroomPresenter.ViewRunnable
                        public void run(@NonNull IPLVChatroomContract.IChatroomView view) {
                            view.onCloseRoomStatusChanged(isClose);
                        }
                    });
                }
            }
        });
        PolyvChatroomManager.getInstance().setOnlineCountListener(new IPolyvOnlineCountListener() { // from class: com.easefun.polyv.livecommon.module.modules.chatroom.presenter.PLVChatroomPresenter.3
            @Override // com.plv.livescenes.chatroom.IPLVOnlineCountListener
            public void onCall(int onlineCount) {
                PLVChatroomPresenter.this.onlineCount = onlineCount;
                PLVChatroomPresenter.this.chatroomData.postOnlineCountData(onlineCount);
            }
        });
        PolyvSocketWrapper.getInstance().getSocketObserver().addOnMessageListener(new PLVSocketMessageObserver.OnMessageListener() { // from class: com.easefun.polyv.livecommon.module.modules.chatroom.presenter.PLVChatroomPresenter.4
            @Override // com.plv.socket.impl.PLVSocketMessageObserver.OnMessageListener
            public void onMessage(String listenEvent, String event, String message) {
                PLVCommonLog.d(PLVChatroomPresenter.TAG, "chatroom receiveMessage: " + message + ", event: " + event + ", listenEvent: " + listenEvent);
                if (PLVChatroomPresenter.this.getConfig().getChannelId().equals(PolyvSocketWrapper.getInstance().getLoginVO().getChannelId())) {
                    PLVRxBus.get().post(new PLVSocketMessage(listenEvent, message, event));
                }
            }
        });
        PolyvSocketWrapper.getInstance().getSocketObserver().addOnConnectStatusListener(new PLVSocketIOObservable.OnConnectStatusListener() { // from class: com.easefun.polyv.livecommon.module.modules.chatroom.presenter.PLVChatroomPresenter.5
            @Override // com.plv.socket.socketio.PLVSocketIOObservable.OnConnectStatusListener
            public void onStatus(PLVSocketStatus status) {
                if (status.getStatus() == 2 && PLVChatroomPresenter.this.hasRequestHistoryEvent) {
                    PLVChatroomPresenter pLVChatroomPresenter = PLVChatroomPresenter.this;
                    pLVChatroomPresenter.requestChatHistory(pLVChatroomPresenter.requestHistoryViewIndex);
                }
            }
        });
    }

    @Override // com.easefun.polyv.livecommon.module.modules.chatroom.contract.IPLVChatroomContract.IChatroomPresenter
    public boolean isCloseRoom() {
        return PolyvChatroomManager.getInstance().isCloseRoom();
    }

    @Override // com.easefun.polyv.livecommon.module.modules.chatroom.contract.IPLVChatroomContract.IChatroomPresenter
    public void onJoinDiscuss(String groupId) {
        this.groupId = groupId;
        clearHistoryInfo();
    }

    @Override // com.easefun.polyv.livecommon.module.modules.chatroom.contract.IPLVChatroomContract.IChatroomPresenter
    public void onLeaveDiscuss() {
        this.groupId = null;
        clearHistoryInfo();
    }

    @Override // com.easefun.polyv.livecommon.module.modules.chatroom.contract.IPLVChatroomContract.IChatroomPresenter
    public void registerView(@NonNull IPLVChatroomContract.IChatroomView v2) {
        if (this.iChatroomViews == null) {
            this.iChatroomViews = new ArrayList();
        }
        if (!this.iChatroomViews.contains(v2)) {
            this.iChatroomViews.add(v2);
        }
        v2.setPresenter(this);
    }

    @Override // com.easefun.polyv.livecommon.module.modules.chatroom.contract.IPLVChatroomContract.IChatroomPresenter
    public void requestChatHistory(final int viewIndex) {
        if (PolyvSocketWrapper.getInstance().isAllowChildRoom() && !PolyvSocketWrapper.getInstance().canGetChildRoomIdStatus()) {
            this.hasRequestHistoryEvent = true;
            this.requestHistoryViewIndex = viewIndex;
            return;
        }
        this.hasRequestHistoryEvent = false;
        this.isNoMoreChatHistory = false;
        Disposable disposable = this.chatHistoryDisposable;
        if (disposable != null) {
            disposable.dispose();
        }
        this.chatHistoryDisposable = PolyvApiManager.getPolyvApichatApi().getChatHistory(getRoomIdCombineDiscuss(), this.getChatHistoryTime * this.getChatHistoryCount, ((r0 + 1) * r2) - 1, 1, 1).map(new Function<ResponseBody, JSONArray>() { // from class: com.easefun.polyv.livecommon.module.modules.chatroom.presenter.PLVChatroomPresenter.15
            @Override // io.reactivex.functions.Function
            public JSONArray apply(ResponseBody responseBody) throws Exception {
                return new JSONArray(responseBody.string());
            }
        }).compose(new PLVRxBaseTransformer()).map(new Function<JSONArray, JSONArray>() { // from class: com.easefun.polyv.livecommon.module.modules.chatroom.presenter.PLVChatroomPresenter.14
            @Override // io.reactivex.functions.Function
            public JSONArray apply(JSONArray jsonArray) throws Exception {
                if (jsonArray.length() < PLVChatroomPresenter.this.getChatHistoryCount) {
                    PLVChatroomPresenter.this.isNoMoreChatHistory = true;
                }
                return jsonArray;
            }
        }).observeOn(Schedulers.io()).map(new Function<JSONArray, List<PLVBaseViewData<PLVBaseEvent>>>() { // from class: com.easefun.polyv.livecommon.module.modules.chatroom.presenter.PLVChatroomPresenter.13
            @Override // io.reactivex.functions.Function
            public List<PLVBaseViewData<PLVBaseEvent>> apply(JSONArray jsonArray) throws Exception {
                PLVChatroomPresenter pLVChatroomPresenter = PLVChatroomPresenter.this;
                return pLVChatroomPresenter.acceptChatHistory(jsonArray, pLVChatroomPresenter.getSpeakEmojiSizes());
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<List<PLVBaseViewData<PLVBaseEvent>>>() { // from class: com.easefun.polyv.livecommon.module.modules.chatroom.presenter.PLVChatroomPresenter.11
            @Override // io.reactivex.functions.Consumer
            public void accept(final List<PLVBaseViewData<PLVBaseEvent>> dataList) throws Exception {
                PLVChatroomPresenter.access$808(PLVChatroomPresenter.this);
                PLVChatroomPresenter.this.callbackToView(new ViewRunnable() { // from class: com.easefun.polyv.livecommon.module.modules.chatroom.presenter.PLVChatroomPresenter.11.1
                    @Override // com.easefun.polyv.livecommon.module.modules.chatroom.presenter.PLVChatroomPresenter.ViewRunnable
                    public void run(@NonNull IPLVChatroomContract.IChatroomView view) {
                        view.onHistoryDataList(dataList, PLVChatroomPresenter.this.getChatHistoryTime, PLVChatroomPresenter.this.isNoMoreChatHistory, viewIndex);
                    }
                });
            }
        }, new Consumer<Throwable>() { // from class: com.easefun.polyv.livecommon.module.modules.chatroom.presenter.PLVChatroomPresenter.12
            @Override // io.reactivex.functions.Consumer
            public void accept(final Throwable throwable) throws Exception {
                PLVCommonLog.exception(throwable);
                PLVELogSender.send(PolyvChatroomELog.class, PLVChatroomELog.Event.LOAD_HISTORY_FAIL, throwable);
                PLVChatroomPresenter.this.callbackToView(new ViewRunnable() { // from class: com.easefun.polyv.livecommon.module.modules.chatroom.presenter.PLVChatroomPresenter.12.1
                    @Override // com.easefun.polyv.livecommon.module.modules.chatroom.presenter.PLVChatroomPresenter.ViewRunnable
                    public void run(@NonNull IPLVChatroomContract.IChatroomView view) {
                        view.onHistoryRequestFailed(PLVLiveRoomDataRequester.getErrorMessage(throwable), throwable, viewIndex);
                    }
                });
            }
        });
    }

    @Override // com.easefun.polyv.livecommon.module.modules.chatroom.contract.IPLVChatroomContract.IChatroomPresenter
    public void requestKickUsers() {
        Disposable disposable = this.kickUsersDisposable;
        if (disposable != null) {
            disposable.dispose();
        }
        String loginRoomId = PolyvSocketWrapper.getInstance().getLoginRoomId();
        if (TextUtils.isEmpty(loginRoomId)) {
            loginRoomId = getConfig().getChannelId();
        }
        this.kickUsersDisposable = PLVChatApiRequestHelper.getKickUsers(loginRoomId).subscribe(new Consumer<PLVKickUsersVO>() { // from class: com.easefun.polyv.livecommon.module.modules.chatroom.presenter.PLVChatroomPresenter.16
            @Override // io.reactivex.functions.Consumer
            public void accept(final PLVKickUsersVO plvsKickUsersVO) throws Exception {
                if (plvsKickUsersVO.getCode() == 200) {
                    PLVChatroomPresenter.this.callbackToView(new ViewRunnable() { // from class: com.easefun.polyv.livecommon.module.modules.chatroom.presenter.PLVChatroomPresenter.16.1
                        @Override // com.easefun.polyv.livecommon.module.modules.chatroom.presenter.PLVChatroomPresenter.ViewRunnable
                        public void run(@NonNull IPLVChatroomContract.IChatroomView view) {
                            PLVChatroomPresenter.this.kickCount = plvsKickUsersVO.getData().size();
                            PLVChatroomPresenter.this.chatroomData.postKickCountData(PLVChatroomPresenter.this.kickCount);
                            view.onKickUsersList(plvsKickUsersVO.getData());
                        }
                    });
                } else {
                    PLVCommonLog.exception(new Throwable(plvsKickUsersVO.toString()));
                }
            }
        }, new Consumer<Throwable>() { // from class: com.easefun.polyv.livecommon.module.modules.chatroom.presenter.PLVChatroomPresenter.17
            @Override // io.reactivex.functions.Consumer
            public void accept(Throwable throwable) throws Exception {
                PLVCommonLog.exception(throwable);
                PLVELogSender.send(PolyvChatroomELog.class, PLVChatroomELog.Event.GET_KICKUSERS_FAIL, throwable);
            }
        });
    }

    @Override // com.easefun.polyv.livecommon.module.modules.chatroom.contract.IPLVChatroomContract.IChatroomPresenter
    public Pair<Boolean, Integer> sendChatEmotionImage(final PLVChatEmotionEvent emotionEvent) throws JSONException {
        PLVCommonLog.d(TAG, "chatroom sendChatEmotionImage: " + this.liveRoomDataManager.getSessionId());
        int iSendEmotionImage = PolyvChatroomManager.getInstance().sendEmotionImage(emotionEvent, new Ack() { // from class: com.easefun.polyv.livecommon.module.modules.chatroom.presenter.PLVChatroomPresenter.9
            @Override // io.socket.client.Ack
            public void call(Object... args) {
                Object obj;
                PLVCommonLog.d(PLVChatroomPresenter.TAG, "chatroom sendTextMessage call: " + Arrays.toString(args));
                if (args == null || args.length == 0 || (obj = args[0]) == null) {
                    return;
                }
                PLVChatroomPresenter.this.acceptEmotionMessage(emotionEvent, String.valueOf(obj));
            }
        });
        if (iSendEmotionImage == -6) {
            acceptEmotionMessage(emotionEvent, "");
        }
        PLVCommonLog.d(TAG, "chatroom sendChatEmotionImage: " + emotionEvent.getId() + ", sendValue: " + iSendEmotionImage);
        return new Pair<>(Boolean.valueOf(iSendEmotionImage > 0 || iSendEmotionImage == -6), Integer.valueOf(iSendEmotionImage));
    }

    @Override // com.easefun.polyv.livecommon.module.modules.chatroom.contract.IPLVChatroomContract.IChatroomPresenter
    public void sendChatImage(final PolyvSendLocalImgEvent localImgEvent) {
        PLVCommonLog.d(TAG, "chatroom sendChatImage: " + localImgEvent.getImageFilePath() + ", sessionId: " + this.liveRoomDataManager.getSessionId());
        PolyvChatroomManager.getInstance().sendChatImage(localImgEvent, this.liveRoomDataManager.getSessionId());
        localImgEvent.setTime(Long.valueOf(System.currentTimeMillis()));
        callbackToView(new ViewRunnable() { // from class: com.easefun.polyv.livecommon.module.modules.chatroom.presenter.PLVChatroomPresenter.10
            @Override // com.easefun.polyv.livecommon.module.modules.chatroom.presenter.PLVChatroomPresenter.ViewRunnable
            public void run(@NonNull IPLVChatroomContract.IChatroomView view) {
                view.onLocalImageMessage(localImgEvent);
            }
        });
    }

    @Override // com.easefun.polyv.livecommon.module.modules.chatroom.contract.IPLVChatroomContract.IChatroomPresenter
    public Pair<Boolean, Integer> sendChatMessage(final PolyvLocalMessage textMessage) {
        boolean z2 = true;
        int iSendChatMessage = PolyvChatroomManager.getInstance().sendChatMessage(textMessage, this.liveRoomDataManager.getSessionId(), true, new Ack() { // from class: com.easefun.polyv.livecommon.module.modules.chatroom.presenter.PLVChatroomPresenter.6
            @Override // io.socket.client.Ack
            public void call(Object... args) {
                Object obj;
                PLVCommonLog.d(PLVChatroomPresenter.TAG, "chatroom sendTextMessage call: " + Arrays.toString(args));
                if (args == null || args.length == 0 || (obj = args[0]) == null) {
                    return;
                }
                PLVChatroomPresenter.this.acceptLocalChatMessage(textMessage, String.valueOf(obj));
            }
        });
        if (iSendChatMessage == -6) {
            acceptLocalChatMessage(textMessage, "");
        }
        PLVCommonLog.d(TAG, "chatroom sendTextMessage: " + textMessage.getSpeakMessage() + ", sendValue: " + iSendChatMessage);
        if (iSendChatMessage <= 0 && iSendChatMessage != -6) {
            z2 = false;
        }
        return new Pair<>(Boolean.valueOf(z2), Integer.valueOf(iSendChatMessage));
    }

    @Override // com.easefun.polyv.livecommon.module.modules.chatroom.contract.IPLVChatroomContract.IChatroomPresenter
    public PolyvCustomEvent<PLVCustomGiftBean> sendCustomGiftMessage(PLVCustomGiftBean customGiftBean, String tip) {
        PolyvCustomEvent<PLVCustomGiftBean> polyvCustomEvent = new PolyvCustomEvent<>(PLVCustomGiftBean.EVENT, customGiftBean);
        polyvCustomEvent.setTip(tip);
        polyvCustomEvent.setEmitMode(0);
        polyvCustomEvent.setVersion(1);
        polyvCustomEvent.setJoinHistory(true);
        polyvCustomEvent.setTime(System.currentTimeMillis());
        PLVCommonLog.d(TAG, "chatroom sendCustomGiftMessage: " + polyvCustomEvent);
        PolyvChatroomManager.getInstance().sendCustomMsg(polyvCustomEvent);
        return polyvCustomEvent;
    }

    @Override // com.easefun.polyv.livecommon.module.modules.chatroom.contract.IPLVChatroomContract.IChatroomPresenter
    public <DataBean> void sendCustomMsg(PolyvBaseCustomEvent<DataBean> baseCustomEvent) {
        PLVCommonLog.d(TAG, "chatroom sendCustomMsg: " + baseCustomEvent);
        PolyvChatroomManager.getInstance().sendCustomMsg(baseCustomEvent);
    }

    @Override // com.easefun.polyv.livecommon.module.modules.chatroom.contract.IPLVChatroomContract.IChatroomPresenter
    public void sendLikeMessage() {
        PLVCommonLog.d(TAG, "chatroom sendLikeMessage: " + this.liveRoomDataManager.getSessionId());
        PolyvChatroomManager.getInstance().sendLikes(this.liveRoomDataManager.getSessionId());
        long j2 = this.likesCount + 1;
        this.likesCount = j2;
        this.chatroomData.postLikesCountData(j2);
    }

    @Override // com.easefun.polyv.livecommon.module.modules.chatroom.contract.IPLVChatroomContract.IChatroomPresenter
    public int sendQuestionMessage(final PolyvQuestionMessage questionMessage) throws JSONException {
        int iSendQuestionMessage = PolyvChatroomManager.getInstance().sendQuestionMessage(questionMessage);
        if (iSendQuestionMessage > 0) {
            questionMessage.setObjects(PLVTextFaceLoader.messageToSpan(questionMessage.getQuestionMessage(), getQuizEmojiSizes(), Utils.getApp()));
            callbackToView(new ViewRunnable() { // from class: com.easefun.polyv.livecommon.module.modules.chatroom.presenter.PLVChatroomPresenter.8
                @Override // com.easefun.polyv.livecommon.module.modules.chatroom.presenter.PLVChatroomPresenter.ViewRunnable
                public void run(IPLVChatroomContract.IChatroomView view) {
                    view.onLocalQuestionMessage(questionMessage);
                }
            });
        }
        PLVCommonLog.d(TAG, "chatroom sendQuestionMessage: " + questionMessage.getQuestionMessage() + ", sendValue: " + iSendQuestionMessage);
        return iSendQuestionMessage;
    }

    @Override // com.easefun.polyv.livecommon.module.modules.chatroom.contract.IPLVChatroomContract.IChatroomPresenter
    public Pair<Boolean, Integer> sendQuoteMessage(final PolyvLocalMessage textMessage, String quoteId) throws JSONException {
        int iSendQuoteMessage = PolyvChatroomManager.getInstance().sendQuoteMessage(textMessage, this.liveRoomDataManager.getSessionId(), true, new Ack() { // from class: com.easefun.polyv.livecommon.module.modules.chatroom.presenter.PLVChatroomPresenter.7
            @Override // io.socket.client.Ack
            public void call(Object... args) {
                Object obj;
                PLVCommonLog.d(PLVChatroomPresenter.TAG, "chatroom sendQuoteMessage call: " + Arrays.toString(args));
                if (args == null || args.length == 0 || (obj = args[0]) == null) {
                    return;
                }
                PLVChatroomPresenter.this.acceptLocalChatMessage(textMessage, String.valueOf(obj));
            }
        }, quoteId);
        if (iSendQuoteMessage == -6) {
            acceptLocalChatMessage(textMessage, "");
        }
        PLVCommonLog.d(TAG, "chatroom sendQuoteMessage: " + textMessage.getSpeakMessage() + ", sendValue: " + iSendQuoteMessage);
        return new Pair<>(Boolean.valueOf(iSendQuoteMessage > 0 || iSendQuoteMessage == -6), Integer.valueOf(iSendQuoteMessage));
    }

    @Override // com.easefun.polyv.livecommon.module.modules.chatroom.contract.IPLVChatroomContract.IChatroomPresenter
    public void setGetChatHistoryCount(int getChatHistoryCount) {
        this.getChatHistoryCount = getChatHistoryCount;
    }

    @Override // com.easefun.polyv.livecommon.module.modules.chatroom.contract.IPLVChatroomContract.IChatroomPresenter
    public void setHistoryContainRewardEvent(boolean historyContainRewardEvent) {
        this.isHistoryContainRewardEvent = historyContainRewardEvent;
    }

    @Override // com.easefun.polyv.livecommon.module.modules.chatroom.contract.IPLVChatroomContract.IChatroomPresenter
    public void toggleRoom(boolean isClose, IPLVChatroomManager.RequestApiListener<String> listener) {
        PolyvChatroomManager.getInstance().toggleRoom(isClose, listener);
    }

    @Override // com.easefun.polyv.livecommon.module.modules.chatroom.contract.IPLVChatroomContract.IChatroomPresenter
    public void unregisterView(IPLVChatroomContract.IChatroomView v2) {
        List<IPLVChatroomContract.IChatroomView> list = this.iChatroomViews;
        if (list != null) {
            list.remove(v2);
        }
    }
}
