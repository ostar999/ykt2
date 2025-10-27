package com.easefun.polyv.livescenes.net.api;

import android.util.Pair;
import com.aliyun.player.alivcplayerexpand.util.database.DatabaseManager;
import com.aliyun.player.aliyunplayerbase.net.ServiceCommon;
import com.easefun.polyv.livescenes.config.PolyvLiveSDKClient;
import com.easefun.polyv.livescenes.model.PLVEmotionImageVO;
import com.plv.foundationsdk.net.PLVResponseApiBean;
import com.plv.foundationsdk.net.PLVResponseApiBean2;
import com.plv.foundationsdk.rx.PLVRxEncryptDataFunction;
import com.plv.foundationsdk.sign.PLVSignCreator;
import com.plv.foundationsdk.utils.PLVReflectionUtil;
import com.plv.foundationsdk.utils.PLVUtils;
import com.plv.livescenes.linkmic.manager.PLVLinkMicManager;
import com.plv.livescenes.model.PLVEmotionImageFullVO;
import com.plv.livescenes.model.PLVEmotionImageVO2;
import com.plv.livescenes.net.PLVApiManager;
import com.psychiatrygarden.utils.Constants;
import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import java.util.HashMap;
import okhttp3.ResponseBody;
import org.json.JSONObject;
import retrofit2.http.Field;
import retrofit2.http.Query;

@Deprecated
/* loaded from: classes3.dex */
public enum PolyvApichatApi {
    INSTANCE;

    public Observable<ResponseBody> getChatHistory(@Query(PLVLinkMicManager.ROOM_ID) String str, @Query("start") int i2, @Query("end") int i3, @Query("fullMessage") int i4, @Query("hasCustom") int i5) {
        return PLVApiManager.getPlvApichatApi().getChatHistory(str, i2, i3, i4, i5);
    }

    public Observable<String> getChatHistory2(@Query(PLVLinkMicManager.ROOM_ID) String str, @Query("start") int i2, @Query("end") int i3, @Query("fullMessage") int i4, @Query("hasCustom") int i5) {
        long jCurrentTimeMillis = System.currentTimeMillis();
        String appId = PolyvLiveSDKClient.getInstance().getAppId();
        String appSecret = PolyvLiveSDKClient.getInstance().getAppSecret();
        HashMap map = new HashMap();
        map.put("timestamp", jCurrentTimeMillis + "");
        map.put("appId", appId);
        map.put("channelId", str);
        map.put("start", i2 + "");
        map.put("end", i3 + "");
        map.put("fullMessage", i4 + "");
        map.put("hasCustom", i5 + "");
        String[] strArrCreateSignWithSignatureNonceEncrypt = PLVSignCreator.createSignWithSignatureNonceEncrypt(appSecret, map);
        return PLVApiManager.getPlvLiveStatusApi().getChatHistory2(str, i2, i3, i4, i5, appId, strArrCreateSignWithSignatureNonceEncrypt[0], jCurrentTimeMillis + "", PLVSignCreator.getSignatureMethod(), strArrCreateSignWithSignatureNonceEncrypt[1], strArrCreateSignWithSignatureNonceEncrypt[2]).map(new Function<ResponseBody, String>() { // from class: com.easefun.polyv.livescenes.net.api.PolyvApichatApi.1
            @Override // io.reactivex.functions.Function
            public String apply(ResponseBody responseBody) throws Exception {
                JSONObject jSONObject = new JSONObject(responseBody.string());
                String strOptString = jSONObject.optString("data");
                return jSONObject.optBoolean("encryption") ? PLVUtils.parseEncryptData(strOptString) : strOptString;
            }
        });
    }

    public Observable<PLVResponseApiBean2<PLVEmotionImageVO>> getEmotionImages(@Query(PLVLinkMicManager.ROOM_ID) String str, @Query("accountId") String str2, @Query(Constants.PARAMS_CONSTANTS.PARAMS_PAGE) int i2, @Query(DatabaseManager.SIZE) int i3) {
        return PLVApiManager.getPlvApichatApi().getEmotionImages(str, str2, i2, i3).map(new Function<PLVResponseApiBean2<com.plv.livescenes.model.PLVEmotionImageVO>, PLVResponseApiBean2<PLVEmotionImageVO>>() { // from class: com.easefun.polyv.livescenes.net.api.PolyvApichatApi.2
            /* JADX WARN: Multi-variable type inference failed */
            @Override // io.reactivex.functions.Function
            public PLVResponseApiBean2<PLVEmotionImageVO> apply(@NonNull PLVResponseApiBean2<com.plv.livescenes.model.PLVEmotionImageVO> pLVResponseApiBean2) throws Exception {
                PLVResponseApiBean2<PLVEmotionImageVO> pLVResponseApiBean22 = new PLVResponseApiBean2<>();
                pLVResponseApiBean22.setData(PLVReflectionUtil.copyField(pLVResponseApiBean2.getData(), new PLVEmotionImageVO()));
                pLVResponseApiBean22.setCode(pLVResponseApiBean2.getCode());
                pLVResponseApiBean22.setMessage(pLVResponseApiBean2.getMessage());
                pLVResponseApiBean22.setStatus(pLVResponseApiBean2.getStatus());
                return pLVResponseApiBean22;
            }
        });
    }

    public Observable<PLVEmotionImageVO2> getEmotionImages2(@Query(PLVLinkMicManager.ROOM_ID) String str, @Query("accountId") String str2, @Query(Constants.PARAMS_CONSTANTS.PARAMS_PAGE) int i2, @Query(DatabaseManager.SIZE) int i3) {
        long jCurrentTimeMillis = System.currentTimeMillis();
        String appId = PolyvLiveSDKClient.getInstance().getAppId();
        String appSecret = PolyvLiveSDKClient.getInstance().getAppSecret();
        HashMap map = new HashMap();
        map.put("timestamp", jCurrentTimeMillis + "");
        map.put("appId", appId);
        map.put("channelId", str);
        map.put("pageNumber", i2 + "");
        map.put(ServiceCommon.RequestKey.FORM_KEY_PAGE_SIZE, i3 + "");
        map.put("userId", str2);
        String[] strArrCreateSignWithSignatureNonceEncrypt = PLVSignCreator.createSignWithSignatureNonceEncrypt(appSecret, map);
        return PLVApiManager.getPlvLiveStatusApi().getEmotionImages2(appId, str, str2, jCurrentTimeMillis + "", strArrCreateSignWithSignatureNonceEncrypt[0], i2, i3, PLVSignCreator.getSignatureMethod(), strArrCreateSignWithSignatureNonceEncrypt[1], strArrCreateSignWithSignatureNonceEncrypt[2]).map(new PLVRxEncryptDataFunction<PLVEmotionImageFullVO>(PLVEmotionImageVO2.class) { // from class: com.easefun.polyv.livescenes.net.api.PolyvApichatApi.4
            @Override // com.plv.foundationsdk.rx.PLVRxEncryptDataFunction
            public Pair<Object, Boolean> accept(PLVEmotionImageFullVO pLVEmotionImageFullVO) {
                return new Pair<>(pLVEmotionImageFullVO.getDataObj(), Boolean.valueOf(pLVEmotionImageFullVO.isEncryption()));
            }
        }).map(new Function<PLVEmotionImageFullVO, PLVEmotionImageVO2>() { // from class: com.easefun.polyv.livescenes.net.api.PolyvApichatApi.3
            @Override // io.reactivex.functions.Function
            public PLVEmotionImageVO2 apply(PLVEmotionImageFullVO pLVEmotionImageFullVO) throws Exception {
                return pLVEmotionImageFullVO.getData();
            }
        });
    }

    public Observable<PLVResponseApiBean> postLotteryAbandon(@Field("channelId") String str, @Field("userId") String str2) {
        return PLVApiManager.getPlvApichatApi().postLotteryAbandon(str, str2);
    }

    public Observable<PLVResponseApiBean> postLotteryWinnerInfoNew(@Field("channelId") String str, @Field("lotteryId") String str2, @Field("winnerCode") String str3, @Field(PLVLinkMicManager.VIEWER_ID) String str4, @Field("receiveInfo") String str5, @Field(PLVLinkMicManager.SESSION_ID) String str6) {
        return PLVApiManager.getPlvApichatApi().postLotteryWinnerInfoNew(str, str2, str3, str4, str5, str6);
    }
}
