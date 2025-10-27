package com.plv.livescenes.chatroom;

import android.text.TextUtils;
import android.util.Pair;
import android.util.Patterns;
import androidx.collection.ArrayMap;
import com.easefun.polyv.livescenes.config.PolyvLiveSDKClient;
import com.plv.foundationsdk.net.dns.PLVAliHttpDnsStorage;
import com.plv.foundationsdk.rx.PLVRxBaseTransformer;
import com.plv.foundationsdk.rx.PLVRxEncryptDataFunction;
import com.plv.foundationsdk.sign.PLVSignCreator;
import com.plv.foundationsdk.utils.PLVCheckUtils;
import com.plv.foundationsdk.utils.PLVSugarUtil;
import com.plv.foundationsdk.utils.PLVTimeUtils;
import com.plv.livescenes.model.PLVChatFunctionSwitchVO;
import com.plv.livescenes.model.PLVKickUsersVO;
import com.plv.livescenes.model.PLVListUsersVO;
import com.plv.livescenes.model.PLVLiveClassDetailVO;
import com.plv.livescenes.model.PLVPlaybackListVO;
import com.plv.livescenes.model.interact.PLVCardPushVO;
import com.plv.livescenes.net.PLVApiManager;
import com.plv.livescenes.playback.video.PLVPlaybackListType;
import com.plv.livescenes.previous.model.PLVChapterDataVO;
import com.plv.socket.user.PLVAuthorizationBean;
import com.plv.thirdpart.blankj.utilcode.util.EncryptUtils;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import java.util.HashMap;
import java.util.List;
import okhttp3.ResponseBody;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class PLVChatApiRequestHelper {
    private static final String ACTOR = "actor";
    private static final String APP_ID = "appId";
    private static final String AUTHORIZATION = "authorization";
    private static final String BG_COLOR = "bgColor";
    private static final String CHANNEL_ID = "channelId";
    private static final String CHAT = "chat";
    public static final String CONSTANT_N = "N";
    public static final String CONSTANT_ROOMS_SIGN = "polyv_rooms_sign";
    public static final String CONSTANT_ROOM_SIGN = "polyv_room_sign";
    public static final String CONSTANT_Y = "Y";
    private static final String F_COLOR = "fColor";
    public static final int GET_STATUS_OFF = 0;
    public static final int GET_STATUS_ON = 1;
    private static final String ID = "id";
    private static final String IMAGE_URL = "imageUrl";
    private static final String LIMIT = "limit";
    private static final String LIST_TYPE = "listType";
    private static final String MSG = "msg";
    private static final String MSG_TYPE = "msgType";
    private static final String NICK = "nick";
    private static final String ORIGIN = "origin";
    private static final String PAGE = "page";
    private static final String PAGE_SIZE = "pageSize";
    private static final String PIC = "pic";
    private static final String PLAYBACK = "playback";
    private static final String PLV_SWITCH_API_INNOR = "polyv_switch_api_innor";
    private static final String ROOM_ID = "roomId";
    private static final String SESSION_ID = "sessionId";
    private static final String TIME = "time";
    private static final String TIMESTAMP = "timestamp";
    private static final String USER = "user";
    private static final String USER_ID = "userId";
    private static final String USER_TYPE = "userType";
    private static final String VID = "vid";
    private static final String VIEWER_ID = "viewerId";
    private static final String VIEWER_NAME = "viewerName";
    private static final String VOD = "vod";
    private static volatile PLVChatApiRequestHelper singleton;

    private PLVChatApiRequestHelper() {
    }

    public static Observable<ResponseBody> closeRoom(String str, boolean z2) {
        long jCurrentTimeMillis = System.currentTimeMillis();
        return PLVApiManager.getPlvApichatApi().closeRoom(str, "", z2 ? "Y" : "N", jCurrentTimeMillis, EncryptUtils.encryptMD5ToString(CONSTANT_ROOMS_SIGN + str + jCurrentTimeMillis).toLowerCase()).compose(new PLVRxBaseTransformer());
    }

    public static PLVChatApiRequestHelper getInstance() {
        if (singleton == null) {
            synchronized (PLVChatApiRequestHelper.class) {
                if (singleton == null) {
                    singleton = new PLVChatApiRequestHelper();
                }
            }
        }
        return singleton;
    }

    public static Observable<PLVKickUsersVO> getKickUsers(String str) {
        return PLVApiManager.getPlvApichatApi().getKickUsers(str).compose(new PLVRxBaseTransformer());
    }

    public static Observable<PLVListUsersVO> getListUsers(String str, int i2, int i3) {
        return getListUsers(str, i2, i3, null);
    }

    public static Observable<PLVCardPushVO> requestCardPushInfo(String str, String str2) {
        return PLVApiManager.getPlvLiveImagesApi().getCardPushInfo(str, str2, System.currentTimeMillis() + "").compose(new PLVRxBaseTransformer());
    }

    public String generateUser(String str, String str2, String str3, String str4, PLVAuthorizationBean pLVAuthorizationBean, String str5) throws JSONException {
        String strCheckParams = PLVCheckUtils.checkParams(str, "viewerId", str2, "channelId", str3, VIEWER_NAME, str4, "imageUrl", str5, "userType");
        if (strCheckParams != null) {
            throw new IllegalArgumentException(strCheckParams + "is empty");
        }
        if (!Patterns.WEB_URL.matcher(str4).matches()) {
            throw new IllegalArgumentException("imageUrl is not webUrl");
        }
        if (pLVAuthorizationBean != null && TextUtils.isEmpty(pLVAuthorizationBean.getActor())) {
            throw new IllegalArgumentException("authorization is empty");
        }
        JSONObject jSONObject = new JSONObject();
        if (pLVAuthorizationBean != null) {
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put(ACTOR, pLVAuthorizationBean.getActor());
            jSONObject2.put(BG_COLOR, pLVAuthorizationBean.getBgColor());
            jSONObject2.put(F_COLOR, pLVAuthorizationBean.getfColor());
            jSONObject.put(AUTHORIZATION, jSONObject2);
        }
        jSONObject.put("channelId", str2);
        jSONObject.put("nick", str3);
        jSONObject.put("pic", str4);
        jSONObject.put("roomId", str2);
        jSONObject.put("userId", str);
        jSONObject.put("userType", str5);
        return jSONObject.toString();
    }

    public Observable<List<PLVChapterDataVO>> getChapterList(String str, String str2) {
        return PLVApiManager.getPlvChaptersApis().getChaptersList(str, str2).compose(new PLVRxBaseTransformer());
    }

    public Observable<ResponseBody> getChatPlaybackMessage(String str, int i2, int i3, int i4, String str2, boolean z2) {
        HashMap map = new HashMap();
        map.put("vid", str);
        map.put(LIMIT, Integer.valueOf(Math.max(0, i2)));
        map.put("time", PLVTimeUtils.generateTime(i3 * 1000, true));
        if (z2) {
            map.put("id", Integer.valueOf(i4));
        }
        map.put("origin", str2);
        return PLVApiManager.getPlvLiveStatusApi().getDanmaku(map).compose(new PLVRxBaseTransformer());
    }

    public Observable<PLVPlaybackListVO> getPlaybackList(String str, int i2, int i3, PLVPlaybackListType pLVPlaybackListType) {
        long jCurrentTimeMillis = System.currentTimeMillis();
        String appId = PolyvLiveSDKClient.getInstance().getAppId();
        String appSecret = PolyvLiveSDKClient.getInstance().getAppSecret();
        String str2 = pLVPlaybackListType == PLVPlaybackListType.VOD ? VOD : PLAYBACK;
        ArrayMap arrayMap = new ArrayMap();
        arrayMap.put("appId", appId);
        arrayMap.put("timestamp", String.valueOf(jCurrentTimeMillis));
        arrayMap.put("page", String.valueOf(i2));
        arrayMap.put("pageSize", String.valueOf(i3));
        arrayMap.put("listType", str2);
        String[] strArrCreateSignWithSignatureNonceEncrypt = PLVSignCreator.createSignWithSignatureNonceEncrypt(appSecret, arrayMap);
        return PLVApiManager.getPlvLiveStatusApi().getPlaybackList(str, appId, jCurrentTimeMillis, i2, i3, str2, strArrCreateSignWithSignatureNonceEncrypt[0], PLVSignCreator.getSignatureMethod(), strArrCreateSignWithSignatureNonceEncrypt[1], strArrCreateSignWithSignatureNonceEncrypt[2]).map(new PLVRxEncryptDataFunction<PLVPlaybackListVO>(PLVPlaybackListVO.DataBean.class) { // from class: com.plv.livescenes.chatroom.PLVChatApiRequestHelper.4
            @Override // com.plv.foundationsdk.rx.PLVRxEncryptDataFunction
            public Pair<Object, Boolean> accept(PLVPlaybackListVO pLVPlaybackListVO) {
                return new Pair<>(pLVPlaybackListVO.getDataObj(), Boolean.valueOf(pLVPlaybackListVO.isEncryption()));
            }
        }).compose(new PLVRxBaseTransformer());
    }

    public Observable<PLVChatFunctionSwitchVO> requestFunctionSwitch(String str) {
        long jCurrentTimeMillis = System.currentTimeMillis();
        HashMap map = new HashMap();
        map.put("channelId", str);
        map.put("timestamp", jCurrentTimeMillis + "");
        String[] strArrCreateSignWithSignatureNonceEncrypt = PLVSignCreator.createSignWithSignatureNonceEncrypt(PLV_SWITCH_API_INNOR, map);
        return PLVApiManager.getPlvLiveStatusApi().getChatFunctionSwitch(jCurrentTimeMillis, strArrCreateSignWithSignatureNonceEncrypt[0], str, PLVSignCreator.getSignatureMethod(), strArrCreateSignWithSignatureNonceEncrypt[1], strArrCreateSignWithSignatureNonceEncrypt[2]).map(new PLVRxEncryptDataFunction<PLVChatFunctionSwitchVO>(PLVChatFunctionSwitchVO.DataBean.class, true) { // from class: com.plv.livescenes.chatroom.PLVChatApiRequestHelper.1
            @Override // com.plv.foundationsdk.rx.PLVRxEncryptDataFunction
            public Pair<Object, Boolean> accept(PLVChatFunctionSwitchVO pLVChatFunctionSwitchVO) {
                return new Pair<>(pLVChatFunctionSwitchVO.getDataObj(), Boolean.valueOf(pLVChatFunctionSwitchVO.isEncryption()));
            }
        }).compose(new PLVRxBaseTransformer());
    }

    public Observable<PLVLiveClassDetailVO> requestLiveClassDetailApi(String str, String str2, String str3) {
        String str4 = System.currentTimeMillis() + "";
        HashMap map = new HashMap();
        map.put("channelId", str);
        map.put("timestamp", str4);
        map.put("appId", str2);
        String[] strArrCreateSignWithSignatureNonceEncrypt = PLVSignCreator.createSignWithSignatureNonceEncrypt(str3, map);
        return PLVApiManager.getPlvLiveStatusApi().getLiveClassDetail(str, str4, strArrCreateSignWithSignatureNonceEncrypt[0], PLVSignCreator.getSignatureMethod(), str2, strArrCreateSignWithSignatureNonceEncrypt[1], strArrCreateSignWithSignatureNonceEncrypt[2]).map(new PLVRxEncryptDataFunction<PLVLiveClassDetailVO>(PLVLiveClassDetailVO.DataBean.class) { // from class: com.plv.livescenes.chatroom.PLVChatApiRequestHelper.3
            @Override // com.plv.foundationsdk.rx.PLVRxEncryptDataFunction
            public Pair<Object, Boolean> accept(PLVLiveClassDetailVO pLVLiveClassDetailVO) {
                return new Pair<>(pLVLiveClassDetailVO.getDataObj(), Boolean.valueOf(pLVLiveClassDetailVO.isEncryption()));
            }
        }).doOnNext(new Consumer<PLVLiveClassDetailVO>() { // from class: com.plv.livescenes.chatroom.PLVChatApiRequestHelper.2
            @Override // io.reactivex.functions.Consumer
            public void accept(final PLVLiveClassDetailVO pLVLiveClassDetailVO) throws Exception {
                String str5 = (String) PLVSugarUtil.nullable(new PLVSugarUtil.Supplier<String>() { // from class: com.plv.livescenes.chatroom.PLVChatApiRequestHelper.2.1
                    @Override // com.plv.foundationsdk.utils.PLVSugarUtil.Supplier
                    public String get() {
                        return pLVLiveClassDetailVO.getData().getHttpDnsKey();
                    }
                });
                if (str5 != null) {
                    PLVAliHttpDnsStorage.INSTANCE.setKey(str5);
                }
            }
        }).compose(new PLVRxBaseTransformer());
    }

    public Observable<ResponseBody> sendChatPlaybackMessage(String str, String str2, int i2, String str3, String str4, String str5, String str6) {
        HashMap map = new HashMap();
        map.put("vid", str);
        map.put("msg", str2);
        map.put("time", PLVTimeUtils.generateTime(i2 * 1000, true));
        map.put("origin", str3);
        map.put("msgType", str4);
        map.put("user", str5);
        if (!TextUtils.isEmpty(str6)) {
            map.put("sessionId", str6);
        }
        return PLVApiManager.getPlvLiveStatusApi().sendDanmaku(map).compose(new PLVRxBaseTransformer());
    }

    public static Observable<PLVListUsersVO> getListUsers(String str, int i2, int i3, String str2) {
        return getListUsers(str, i2, i3, 1, true, str2);
    }

    public static Observable<PLVListUsersVO> getListUsers(String str, int i2, int i3, int i4, boolean z2, String str2) {
        return PLVApiManager.getPlvApichatApi().getListUsers(str, i2, i3, i4, z2, str2).compose(new PLVRxBaseTransformer());
    }
}
