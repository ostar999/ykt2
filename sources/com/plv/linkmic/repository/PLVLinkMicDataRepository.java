package com.plv.linkmic.repository;

import android.text.TextUtils;
import android.util.Base64;
import android.util.Pair;
import androidx.collection.ArrayMap;
import cn.hutool.core.text.StrPool;
import com.plv.foundationsdk.log.PLVCommonLog;
import com.plv.foundationsdk.net.PLVResponseBean;
import com.plv.foundationsdk.net.PLVResponseExcutor;
import com.plv.foundationsdk.net.PLVrResponseCallback;
import com.plv.foundationsdk.rx.PLVRxBaseTransformer;
import com.plv.foundationsdk.rx.PLVRxEncryptDataFunction;
import com.plv.foundationsdk.sign.PLVSignCreator;
import com.plv.foundationsdk.utils.PLVGsonUtil;
import com.plv.linkmic.PLVLinkMicDataConfig;
import com.plv.linkmic.model.PLVEncryptDataVO;
import com.plv.linkmic.model.PLVHiClassRTCTokenVO;
import com.plv.linkmic.model.PLVLinkMicJoinStatus;
import com.plv.linkmic.model.PLVLinkMicTokenStatisticsInfo;
import com.plv.linkmic.model.PLVQuerySessionIdDataBean;
import com.plv.linkmic.model.PLVRTCMixActionResultFullVO;
import com.plv.linkmic.model.PLVRTCMixActionResultVO;
import com.plv.linkmic.model.PLVRTCMixActionVO;
import com.plv.linkmic.repository.PLVLinkMicHttpRequestException;
import com.plv.linkmic.repository.api.PLVLinkMicApiManager;
import com.plv.thirdpart.blankj.utilcode.util.ConvertUtils;
import com.plv.thirdpart.blankj.utilcode.util.EncryptUtils;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class PLVLinkMicDataRepository {
    private static final String APP_ID = "appId";
    private static final String APP_SIGN = "appSign";
    private static final String APP_TOKEN = "appToken";
    private static final String BIZID = "bizId";
    private static final String CHANNEL_ID = "channelId";
    private static final String CLIENT = "client";
    private static final String CLIENT_TS = "clientTs";
    private static final String CLIENT_VERSION = "clientVersion";
    private static final String CONNECT_APP_ID = "connect_appId";
    private static final String CONNECT_CHANNEL_KEY = "connect_channel_key";
    private static final String DATA = "data";
    private static final String DEVICE_TYPE = "deviceType";
    private static final String GET_STATUS = "getStatus";
    private static final String MODEL = "model";
    private static final String NETWORK_TYPE = "networkType";
    private static final String NICKNAME = "nickname";
    private static final String OS = "os";
    private static final String OS_VERSION = "osVersion";
    private static final String PARAM_BE_NULL = "参数为空";
    private static final String PARAM_CAN_NOT_BE_NULL = "请求参数不可为空";
    private static final String SCENE = "scene";
    private static final String SCREEN_HEIGHT = "screenHeight";
    private static final String SCREEN_WIDTH = "screenWidth";
    private static final String SDK_APP_ID = "sdkAppId";
    private static final String SESSION_ID = "sessionId";
    private static final String STREAM = "stream";
    public static final String TAG = "PLVLinkMicDataRepository";
    private static final String TIMESTAMP = "timestamp";
    private static final String TOKEN = "token";
    private static final String UID = "uid";
    private static final String USER_ID = "userId";
    private static final String USER_SIG = "userSign";
    private static final String USER_TYPE = "userType";
    private static final String VERSION = "version";
    private static final String VIEWER_ID = "viewerId";
    private Disposable getInteractStatusDisposable;
    private Disposable getSessionIdDisposable;
    private Disposable getTokenDisposable;
    private Disposable mixToCdnDisposable;
    private Disposable notifyLiveEndDisposable;
    private Disposable notifyStreamDisposable;
    private String rtcType;

    public static abstract class GetTokenCallbackAdapter extends PLVrResponseCallback<String> {
        private IPLVLinkMicDataRepoListener<PLVLinkMicEngineToken> requestTokenListener;

        public GetTokenCallbackAdapter(IPLVLinkMicDataRepoListener<PLVLinkMicEngineToken> iPLVLinkMicDataRepoListener) {
            this.requestTokenListener = iPLVLinkMicDataRepoListener;
        }

        @Override // com.plv.foundationsdk.net.PLVrResponseCallback
        public void onError(Throwable th) {
            this.requestTokenListener.onFail(new PLVLinkMicHttpRequestException.Builder(5).cause(th).build());
        }

        @Override // com.plv.foundationsdk.net.PLVrResponseCallback
        public void onFailure(PLVResponseBean<String> pLVResponseBean) {
            this.requestTokenListener.onFail(new PLVLinkMicHttpRequestException.Builder(5).msg(pLVResponseBean.toString()).build());
        }

        @Override // com.plv.foundationsdk.net.PLVrResponseCallback
        public void onFinish() {
        }

        public abstract PLVLinkMicEngineToken parseEncryptedData(String str) throws Exception;

        @Override // com.plv.foundationsdk.net.PLVrResponseCallback
        public void onSuccess(String str) {
            try {
                this.requestTokenListener.onSuccess(parseEncryptedData(str));
            } catch (Exception e2) {
                e2.printStackTrace();
                PLVCommonLog.e(PLVLinkMicDataRepository.TAG, "parseEncryptedData:" + e2.getMessage());
                this.requestTokenListener.onFail(new PLVLinkMicHttpRequestException.Builder(6).cause(e2).build());
            }
        }
    }

    public static abstract class IPLVLinkMicDataRepoListener<T> {
        public void onFail(PLVLinkMicHttpRequestException pLVLinkMicHttpRequestException) {
        }

        public void onFinish() {
        }

        public abstract void onSuccess(T t2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkTextEmpty(String str) throws Exception {
        if (TextUtils.isEmpty(str)) {
            throw new Exception("text is empty");
        }
    }

    private void dispose(Disposable disposable) {
        if (disposable != null) {
            disposable.dispose();
        }
    }

    private String generateSessionIdFromLocal() {
        return EncryptUtils.encryptMD5ToString(String.valueOf(System.currentTimeMillis())).toLowerCase().substring(0, 10);
    }

    private Map<String, String> getTokenStaticsInfoMap(PLVLinkMicTokenStatisticsInfo pLVLinkMicTokenStatisticsInfo) {
        return new HashMap<String, String>(pLVLinkMicTokenStatisticsInfo) { // from class: com.plv.linkmic.repository.PLVLinkMicDataRepository.13
            final /* synthetic */ PLVLinkMicTokenStatisticsInfo val$tokenStatisticsInfo;

            {
                this.val$tokenStatisticsInfo = pLVLinkMicTokenStatisticsInfo;
                put("uid", pLVLinkMicTokenStatisticsInfo.getUid());
                put("channelId", pLVLinkMicTokenStatisticsInfo.getChannelId());
                put(PLVLinkMicDataRepository.SCENE, pLVLinkMicTokenStatisticsInfo.getScene());
                put("userType", pLVLinkMicTokenStatisticsInfo.getUserType());
                put("viewerId", pLVLinkMicTokenStatisticsInfo.getViewerId());
                put("nickname", pLVLinkMicTokenStatisticsInfo.getNickname());
                put(PLVLinkMicDataRepository.CLIENT, pLVLinkMicTokenStatisticsInfo.getClient());
                put(PLVLinkMicDataRepository.CLIENT_VERSION, pLVLinkMicTokenStatisticsInfo.getClientVersion());
                put(PLVLinkMicDataRepository.CLIENT_TS, pLVLinkMicTokenStatisticsInfo.getClientTs());
                put("sessionId", pLVLinkMicTokenStatisticsInfo.getSessionId());
                put(PLVLinkMicDataRepository.DEVICE_TYPE, pLVLinkMicTokenStatisticsInfo.getDeviceType());
                put(PLVLinkMicDataRepository.MODEL, pLVLinkMicTokenStatisticsInfo.getModel());
                put("os", pLVLinkMicTokenStatisticsInfo.getOs());
                put(PLVLinkMicDataRepository.OS_VERSION, pLVLinkMicTokenStatisticsInfo.getOsVersion());
                put(PLVLinkMicDataRepository.NETWORK_TYPE, pLVLinkMicTokenStatisticsInfo.getNetworkType());
                put(PLVLinkMicDataRepository.SCREEN_WIDTH, pLVLinkMicTokenStatisticsInfo.getScreenWidth());
                put(PLVLinkMicDataRepository.SCREEN_HEIGHT, pLVLinkMicTokenStatisticsInfo.getScreenHeight());
            }

            @Override // java.util.HashMap, java.util.AbstractMap, java.util.Map
            public String put(String str, String str2) {
                if (str2 == null) {
                    str2 = "";
                }
                return (String) super.put((AnonymousClass13) str, str2);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String parseToken(String str) throws BadPaddingException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException, InvalidAlgorithmParameterException {
        SecretKeySpec secretKeySpec = new SecretKeySpec("polyvliveSDKAuth".getBytes(), "AES");
        IvParameterSpec ivParameterSpec = new IvParameterSpec("1111000011110000".getBytes());
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(2, secretKeySpec, ivParameterSpec);
        String str2 = new String(Base64.decode(cipher.doFinal(ConvertUtils.hexString2Bytes(str)), 0), "UTF-8");
        PLVCommonLog.d(TAG, "parseToken success");
        return str2;
    }

    private void requestARTCToken(String str, String str2, String str3, Map<String, String> map, IPLVLinkMicDataRepoListener<PLVLinkMicEngineToken> iPLVLinkMicDataRepoListener) {
        if (TextUtils.isEmpty(str)) {
            iPLVLinkMicDataRepoListener.onFail(new PLVLinkMicHttpRequestException.Builder(4).msg(PARAM_CAN_NOT_BE_NULL).build());
            return;
        }
        String str4 = System.currentTimeMillis() + "";
        HashMap map2 = new HashMap();
        map2.put("channelId", str);
        map2.put("appId", str2);
        map2.put("timestamp", str4);
        for (Map.Entry<String, String> entry : map.entrySet()) {
            map2.put(entry.getKey(), entry.getValue());
        }
        String[] strArrCreateSignWithSignatureNonceEncrypt = PLVSignCreator.createSignWithSignatureNonceEncrypt(str3, map2);
        dispose(this.getTokenDisposable);
        this.getTokenDisposable = PLVResponseExcutor.excuteUndefinData(PLVLinkMicApiManager.getPolyvLinkMicApi().getMicAuth(str, str2, str4, strArrCreateSignWithSignatureNonceEncrypt[0], PLVSignCreator.getSignatureMethod(), map, strArrCreateSignWithSignatureNonceEncrypt[1], strArrCreateSignWithSignatureNonceEncrypt[2]).map(new PLVRxEncryptDataFunction<PLVEncryptDataVO<String>>(String.class) { // from class: com.plv.linkmic.repository.PLVLinkMicDataRepository.20
            @Override // com.plv.foundationsdk.rx.PLVRxEncryptDataFunction
            public Pair<Object, Boolean> accept(PLVEncryptDataVO<String> pLVEncryptDataVO) {
                return new Pair<>(pLVEncryptDataVO.getDataObj(), Boolean.valueOf(pLVEncryptDataVO.isEncryption()));
            }
        }).map(new Function<PLVEncryptDataVO<String>, String>() { // from class: com.plv.linkmic.repository.PLVLinkMicDataRepository.19
            @Override // io.reactivex.functions.Function
            public String apply(PLVEncryptDataVO<String> pLVEncryptDataVO) throws Exception {
                return pLVEncryptDataVO.getData();
            }
        }), new GetTokenCallbackAdapter(iPLVLinkMicDataRepoListener) { // from class: com.plv.linkmic.repository.PLVLinkMicDataRepository.21
            @Override // com.plv.linkmic.repository.PLVLinkMicDataRepository.GetTokenCallbackAdapter
            public PLVLinkMicEngineToken parseEncryptedData(String str5) throws Exception {
                JSONObject jSONObject = new JSONObject(PLVLinkMicDataRepository.this.parseToken(str5));
                String strOptString = jSONObject.optString(PLVLinkMicDataRepository.CONNECT_CHANNEL_KEY);
                String strOptString2 = jSONObject.optString(PLVLinkMicDataRepository.CONNECT_APP_ID);
                PLVLinkMicDataRepository.this.checkTextEmpty(strOptString);
                PLVLinkMicDataRepository.this.checkTextEmpty(strOptString2);
                return new PLVLinkMicEngineToken(strOptString, strOptString2);
            }
        });
    }

    private void requestTRTCToken(String str, String str2, String str3, String str4, Map<String, String> map, IPLVLinkMicDataRepoListener<PLVLinkMicEngineToken> iPLVLinkMicDataRepoListener) {
        if (TextUtils.isEmpty(str)) {
            iPLVLinkMicDataRepoListener.onFail(new PLVLinkMicHttpRequestException.Builder(4).msg(PARAM_CAN_NOT_BE_NULL).build());
            return;
        }
        String str5 = System.currentTimeMillis() + "";
        HashMap map2 = new HashMap();
        map2.put("channelId", str);
        map2.put("timestamp", str5);
        map2.put("appId", str2);
        for (Map.Entry<String, String> entry : map.entrySet()) {
            map2.put(entry.getKey(), entry.getValue());
        }
        String[] strArrCreateSignWithSignatureNonceEncrypt = PLVSignCreator.createSignWithSignatureNonceEncrypt(str3, map2);
        dispose(this.getTokenDisposable);
        this.getTokenDisposable = PLVResponseExcutor.excuteUndefinData(PLVLinkMicApiManager.getPolyvLinkMicApi().getTRTCMicAuth(str, str5, strArrCreateSignWithSignatureNonceEncrypt[0], PLVSignCreator.getSignatureMethod(), str2, map, strArrCreateSignWithSignatureNonceEncrypt[1], strArrCreateSignWithSignatureNonceEncrypt[2]).map(new PLVRxEncryptDataFunction<PLVEncryptDataVO<String>>(String.class) { // from class: com.plv.linkmic.repository.PLVLinkMicDataRepository.23
            @Override // com.plv.foundationsdk.rx.PLVRxEncryptDataFunction
            public Pair<Object, Boolean> accept(PLVEncryptDataVO<String> pLVEncryptDataVO) {
                return new Pair<>(pLVEncryptDataVO.getDataObj(), Boolean.valueOf(pLVEncryptDataVO.isEncryption()));
            }
        }).map(new Function<PLVEncryptDataVO<String>, String>() { // from class: com.plv.linkmic.repository.PLVLinkMicDataRepository.22
            @Override // io.reactivex.functions.Function
            public String apply(PLVEncryptDataVO<String> pLVEncryptDataVO) throws Exception {
                return pLVEncryptDataVO.getData();
            }
        }), new GetTokenCallbackAdapter(iPLVLinkMicDataRepoListener) { // from class: com.plv.linkmic.repository.PLVLinkMicDataRepository.24
            @Override // com.plv.linkmic.repository.PLVLinkMicDataRepository.GetTokenCallbackAdapter
            public PLVLinkMicEngineToken parseEncryptedData(String str6) throws Exception {
                JSONObject jSONObject = new JSONObject(PLVLinkMicDataRepository.this.parseToken(str6));
                String strOptString = jSONObject.optString(PLVLinkMicDataRepository.SDK_APP_ID);
                String strOptString2 = jSONObject.optString("appId");
                String strOptString3 = jSONObject.optString(PLVLinkMicDataRepository.BIZID);
                PLVLinkMicEngineToken pLVLinkMicEngineToken = new PLVLinkMicEngineToken(jSONObject.optString(PLVLinkMicDataRepository.USER_SIG), strOptString2);
                pLVLinkMicEngineToken.setTBizId(strOptString3);
                pLVLinkMicEngineToken.setTSdkAppId(strOptString);
                return pLVLinkMicEngineToken;
            }
        });
    }

    private void requestURTCToken(String str, String str2, String str3, String str4, String str5, Map<String, String> map, final IPLVLinkMicDataRepoListener<PLVLinkMicEngineToken> iPLVLinkMicDataRepoListener) {
        if (TextUtils.isEmpty(str)) {
            iPLVLinkMicDataRepoListener.onFail(new PLVLinkMicHttpRequestException.Builder(4).msg(PARAM_CAN_NOT_BE_NULL).build());
            return;
        }
        if (!TextUtils.isEmpty(PLVLinkMicDataConfig.token)) {
            String str6 = System.currentTimeMillis() + "";
            dispose(this.getTokenDisposable);
            Consumer<PLVHiClassRTCTokenVO> consumer = new Consumer<PLVHiClassRTCTokenVO>() { // from class: com.plv.linkmic.repository.PLVLinkMicDataRepository.14
                @Override // io.reactivex.functions.Consumer
                public void accept(PLVHiClassRTCTokenVO pLVHiClassRTCTokenVO) throws Exception {
                    if (pLVHiClassRTCTokenVO.isSuccess() == null || !pLVHiClassRTCTokenVO.isSuccess().booleanValue()) {
                        throw new Exception(pLVHiClassRTCTokenVO.getError().getDesc() + "-" + pLVHiClassRTCTokenVO.getError().getCode());
                    }
                    JSONObject jSONObject = new JSONObject(PLVLinkMicDataRepository.this.parseToken(pLVHiClassRTCTokenVO.getData()));
                    String strOptString = jSONObject.optString("token");
                    String strOptString2 = jSONObject.optString("appId");
                    String strOptString3 = jSONObject.optString(PLVLinkMicDataRepository.APP_TOKEN);
                    PLVLinkMicDataRepository.this.checkTextEmpty(strOptString);
                    PLVLinkMicDataRepository.this.checkTextEmpty(strOptString2);
                    PLVLinkMicDataRepository.this.checkTextEmpty(strOptString3);
                    PLVLinkMicEngineToken pLVLinkMicEngineToken = new PLVLinkMicEngineToken(strOptString, strOptString2);
                    pLVLinkMicEngineToken.setUAppToken(strOptString3);
                    iPLVLinkMicDataRepoListener.onSuccess(pLVLinkMicEngineToken);
                }
            };
            Consumer<? super Throwable> consumer2 = new Consumer<Throwable>() { // from class: com.plv.linkmic.repository.PLVLinkMicDataRepository.15
                @Override // io.reactivex.functions.Consumer
                public void accept(Throwable th) throws Exception {
                    iPLVLinkMicDataRepoListener.onFail(new PLVLinkMicHttpRequestException.Builder(5).cause(th).build());
                }
            };
            if (PLVLinkMicDataConfig.isTeacherType) {
                this.getTokenDisposable = PLVLinkMicApiManager.getHiClassApi().getTeachMicToken(PLVLinkMicDataConfig.lessonId, str, str4, str5, str6, PLVLinkMicDataConfig.token, map).compose(new PLVRxBaseTransformer()).subscribe(consumer, consumer2);
                return;
            } else {
                this.getTokenDisposable = PLVLinkMicApiManager.getHiClassApi().getWatchMicToken(PLVLinkMicDataConfig.courseCode, PLVLinkMicDataConfig.lessonId, str, str4, str5, str6, PLVLinkMicDataConfig.token, map).compose(new PLVRxBaseTransformer()).subscribe(consumer, consumer2);
                return;
            }
        }
        String str7 = System.currentTimeMillis() + "";
        HashMap map2 = new HashMap();
        map2.put("channelId", str);
        map2.put("userId", str4);
        map2.put("timestamp", str7);
        map2.put("appId", str2);
        for (Map.Entry<String, String> entry : map.entrySet()) {
            map2.put(entry.getKey(), entry.getValue());
        }
        String[] strArrCreateSignWithSignatureNonceEncrypt = PLVSignCreator.createSignWithSignatureNonceEncrypt(str3, map2);
        dispose(this.getTokenDisposable);
        this.getTokenDisposable = PLVResponseExcutor.excuteUndefinData(PLVLinkMicApiManager.getPolyvLinkMicApi().getUCloudMicAuth(str, str4, str7, strArrCreateSignWithSignatureNonceEncrypt[0], PLVSignCreator.getSignatureMethod(), str2, map, strArrCreateSignWithSignatureNonceEncrypt[1], strArrCreateSignWithSignatureNonceEncrypt[2]).map(new PLVRxEncryptDataFunction<PLVEncryptDataVO<String>>(String.class) { // from class: com.plv.linkmic.repository.PLVLinkMicDataRepository.17
            @Override // com.plv.foundationsdk.rx.PLVRxEncryptDataFunction
            public Pair<Object, Boolean> accept(PLVEncryptDataVO<String> pLVEncryptDataVO) {
                return new Pair<>(pLVEncryptDataVO.getDataObj(), Boolean.valueOf(pLVEncryptDataVO.isEncryption()));
            }
        }).map(new Function<PLVEncryptDataVO<String>, String>() { // from class: com.plv.linkmic.repository.PLVLinkMicDataRepository.16
            @Override // io.reactivex.functions.Function
            public String apply(PLVEncryptDataVO<String> pLVEncryptDataVO) throws Exception {
                return pLVEncryptDataVO.getData();
            }
        }), new GetTokenCallbackAdapter(iPLVLinkMicDataRepoListener) { // from class: com.plv.linkmic.repository.PLVLinkMicDataRepository.18
            @Override // com.plv.linkmic.repository.PLVLinkMicDataRepository.GetTokenCallbackAdapter
            public PLVLinkMicEngineToken parseEncryptedData(String str8) throws Exception {
                JSONObject jSONObject = new JSONObject(PLVLinkMicDataRepository.this.parseToken(str8));
                String strOptString = jSONObject.optString("token");
                String strOptString2 = jSONObject.optString("appId");
                String strOptString3 = jSONObject.optString(PLVLinkMicDataRepository.APP_TOKEN);
                PLVLinkMicDataRepository.this.checkTextEmpty(strOptString);
                PLVLinkMicDataRepository.this.checkTextEmpty(strOptString2);
                PLVLinkMicDataRepository.this.checkTextEmpty(strOptString3);
                PLVLinkMicEngineToken pLVLinkMicEngineToken = new PLVLinkMicEngineToken(strOptString, strOptString2);
                pLVLinkMicEngineToken.setUAppToken(strOptString3);
                return pLVLinkMicEngineToken;
            }
        });
    }

    public void getInteractStatus(String str, String str2, String str3, String str4, boolean z2, final IPLVLinkMicDataRepoListener<PLVLinkMicJoinStatus> iPLVLinkMicDataRepoListener) {
        dispose(this.getInteractStatusDisposable);
        if (TextUtils.isEmpty(str3) || TextUtils.isEmpty(str4)) {
            iPLVLinkMicDataRepoListener.onFail(new PLVLinkMicHttpRequestException.Builder(13).msg(PARAM_BE_NULL).build());
        } else {
            this.getInteractStatusDisposable = PLVResponseExcutor.excuteUndefinData(PLVLinkMicApiManager.getPolyvLinkMicChatApi().getInteractStatus(str3, str4, z2), new PLVrResponseCallback<PLVLinkMicJoinStatus>() { // from class: com.plv.linkmic.repository.PLVLinkMicDataRepository.7
                @Override // com.plv.foundationsdk.net.PLVrResponseCallback
                public void onError(Throwable th) {
                    super.onError(th);
                    iPLVLinkMicDataRepoListener.onFail(new PLVLinkMicHttpRequestException.Builder(14).cause(th).build());
                }

                @Override // com.plv.foundationsdk.net.PLVrResponseCallback
                public void onFailure(PLVResponseBean<PLVLinkMicJoinStatus> pLVResponseBean) {
                    super.onFailure(pLVResponseBean);
                    iPLVLinkMicDataRepoListener.onFail(new PLVLinkMicHttpRequestException.Builder(15).msg(pLVResponseBean.toString()).build());
                }

                @Override // com.plv.foundationsdk.net.PLVrResponseCallback
                public void onFinish() {
                    iPLVLinkMicDataRepoListener.onFinish();
                }

                @Override // com.plv.foundationsdk.net.PLVrResponseCallback
                public void onSuccess(PLVLinkMicJoinStatus pLVLinkMicJoinStatus) {
                    iPLVLinkMicDataRepoListener.onSuccess(pLVLinkMicJoinStatus);
                }
            });
        }
    }

    public void getSessionIdFromServer(String str, String str2, final String str3, String str4, final IPLVLinkMicDataRepoListener<Pair<String, String>> iPLVLinkMicDataRepoListener) {
        dispose(this.getSessionIdDisposable);
        if (TextUtils.isEmpty(str3) || TextUtils.isEmpty(str4)) {
            iPLVLinkMicDataRepoListener.onFail(new PLVLinkMicHttpRequestException.Builder(7).msg(PARAM_CAN_NOT_BE_NULL).build());
            return;
        }
        String str5 = System.currentTimeMillis() + "";
        ArrayMap arrayMap = new ArrayMap();
        arrayMap.put("channelId", str3);
        arrayMap.put(STREAM, str4);
        arrayMap.put("appId", str);
        arrayMap.put("timestamp", str5);
        String[] strArrCreateSignWithSignatureNonceEncrypt = PLVSignCreator.createSignWithSignatureNonceEncrypt(str2, arrayMap);
        this.getSessionIdDisposable = PLVResponseExcutor.excuteUndefinData(PLVLinkMicApiManager.getPolyvLinkMicApi().getLinkMicSessionV3(str3, str4, str, str5, strArrCreateSignWithSignatureNonceEncrypt[0], PLVSignCreator.getSignatureMethod(), strArrCreateSignWithSignatureNonceEncrypt[1], strArrCreateSignWithSignatureNonceEncrypt[2]).map(new PLVRxEncryptDataFunction<PLVEncryptDataVO<PLVQuerySessionIdDataBean>>(PLVQuerySessionIdDataBean.class) { // from class: com.plv.linkmic.repository.PLVLinkMicDataRepository.5
            @Override // com.plv.foundationsdk.rx.PLVRxEncryptDataFunction
            public Pair<Object, Boolean> accept(PLVEncryptDataVO<PLVQuerySessionIdDataBean> pLVEncryptDataVO) {
                return new Pair<>(pLVEncryptDataVO.getDataObj(), Boolean.valueOf(pLVEncryptDataVO.isEncryption()));
            }
        }).map(new Function<PLVEncryptDataVO<PLVQuerySessionIdDataBean>, String>() { // from class: com.plv.linkmic.repository.PLVLinkMicDataRepository.4
            @Override // io.reactivex.functions.Function
            public String apply(PLVEncryptDataVO<PLVQuerySessionIdDataBean> pLVEncryptDataVO) throws Exception {
                return pLVEncryptDataVO.getData().getSessionId();
            }
        }), new PLVrResponseCallback<String>() { // from class: com.plv.linkmic.repository.PLVLinkMicDataRepository.6
            @Override // com.plv.foundationsdk.net.PLVrResponseCallback
            public void onError(Throwable th) {
                super.onError(th);
                PLVCommonLog.e(PLVLinkMicDataRepository.TAG, "getLinkMicSession:" + th.getMessage());
                iPLVLinkMicDataRepoListener.onFail(new PLVLinkMicHttpRequestException.Builder(9).cause(th).build());
            }

            @Override // com.plv.foundationsdk.net.PLVrResponseCallback
            public void onFinish() {
                PLVCommonLog.d(PLVLinkMicDataRepository.TAG, "getLinkMicSession onFinish");
            }

            @Override // com.plv.foundationsdk.net.PLVrResponseCallback
            public void onSuccess(String str6) {
                iPLVLinkMicDataRepoListener.onSuccess(new Pair(str6, str3));
            }
        });
    }

    public void mixToCdn(String str, String str2, PLVRTCMixActionVO pLVRTCMixActionVO, final IPLVLinkMicDataRepoListener<String> iPLVLinkMicDataRepoListener) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2) || pLVRTCMixActionVO == null) {
            iPLVLinkMicDataRepoListener.onFail(new PLVLinkMicHttpRequestException.Builder(19).msg(PARAM_CAN_NOT_BE_NULL).build());
            return;
        }
        String strValueOf = String.valueOf(System.currentTimeMillis());
        String json = PLVGsonUtil.toJson(pLVRTCMixActionVO);
        ArrayMap arrayMap = new ArrayMap();
        arrayMap.put("appId", str);
        arrayMap.put("timestamp", strValueOf);
        arrayMap.put("data", json);
        String[] strArrCreateSignWithSignatureNonceEncrypt = PLVSignCreator.createSignWithSignatureNonceEncrypt(str2, arrayMap);
        dispose(this.mixToCdnDisposable);
        this.mixToCdnDisposable = PLVResponseExcutor.excuteUndefinData(PLVLinkMicApiManager.getPolyvLinkMicApi().mixAction(str, strValueOf, json, strArrCreateSignWithSignatureNonceEncrypt[0], PLVSignCreator.getSignatureMethod(), strArrCreateSignWithSignatureNonceEncrypt[1], strArrCreateSignWithSignatureNonceEncrypt[2]).map(new PLVRxEncryptDataFunction<PLVRTCMixActionResultFullVO>(PLVRTCMixActionResultVO.class) { // from class: com.plv.linkmic.repository.PLVLinkMicDataRepository.11
            @Override // com.plv.foundationsdk.rx.PLVRxEncryptDataFunction
            public Pair<Object, Boolean> accept(PLVRTCMixActionResultFullVO pLVRTCMixActionResultFullVO) {
                return new Pair<>(pLVRTCMixActionResultFullVO.getDataObj(), Boolean.valueOf(pLVRTCMixActionResultFullVO.isEncryption()));
            }
        }), new PLVrResponseCallback<PLVRTCMixActionResultFullVO>() { // from class: com.plv.linkmic.repository.PLVLinkMicDataRepository.12
            @Override // com.plv.foundationsdk.net.PLVrResponseCallback
            public void onError(Throwable th) {
                super.onError(th);
                iPLVLinkMicDataRepoListener.onFail(new PLVLinkMicHttpRequestException.Builder(20).cause(th).build());
            }

            @Override // com.plv.foundationsdk.net.PLVrResponseCallback
            public void onFailure(PLVResponseBean<PLVRTCMixActionResultFullVO> pLVResponseBean) {
                super.onFailure(pLVResponseBean);
                iPLVLinkMicDataRepoListener.onFail(new PLVLinkMicHttpRequestException.Builder(20).msg(pLVResponseBean.getMessage()).build());
            }

            @Override // com.plv.foundationsdk.net.PLVrResponseCallback
            public void onFinish() {
                iPLVLinkMicDataRepoListener.onFinish();
            }

            @Override // com.plv.foundationsdk.net.PLVrResponseCallback
            public void onSuccess(PLVRTCMixActionResultFullVO pLVRTCMixActionResultFullVO) {
                PLVRTCMixActionResultVO data = pLVRTCMixActionResultFullVO.getData();
                if (!data.isRes()) {
                    iPLVLinkMicDataRepoListener.onFail(new PLVLinkMicHttpRequestException.Builder(21).msg("result=" + data).build());
                    return;
                }
                PLVCommonLog.d(PLVLinkMicDataRepository.TAG, "mixToCdn->onSuccess() called with: s = [" + data + StrPool.BRACKET_END);
                iPLVLinkMicDataRepoListener.onSuccess(data.toString());
            }
        });
    }

    public void notifyLiveEnd(String str, String str2, String str3, String str4, final IPLVLinkMicDataRepoListener<String> iPLVLinkMicDataRepoListener) {
        if (TextUtils.isEmpty(str3) || TextUtils.isEmpty(str4)) {
            iPLVLinkMicDataRepoListener.onFail(new PLVLinkMicHttpRequestException.Builder(16).msg(PARAM_CAN_NOT_BE_NULL).build());
            return;
        }
        String strValueOf = String.valueOf(System.currentTimeMillis());
        ArrayMap arrayMap = new ArrayMap();
        arrayMap.put("appId", str);
        arrayMap.put("timestamp", strValueOf);
        arrayMap.put("channelId", str3);
        arrayMap.put(STREAM, str4);
        arrayMap.put("version", PLVLinkMicDataConfig.LINK_MIC_SDK_VERSION);
        String[] strArrCreateSignWithSignatureNonceEncrypt = PLVSignCreator.createSignWithSignatureNonceEncrypt(str2, arrayMap);
        dispose(this.notifyLiveEndDisposable);
        this.notifyLiveEndDisposable = PLVResponseExcutor.excuteUndefinData(PLVLinkMicApiManager.getApiLiveApi().notifyLiveEndV3(str3, str4, strValueOf, strArrCreateSignWithSignatureNonceEncrypt[0], PLVLinkMicDataConfig.LINK_MIC_SDK_VERSION, str, PLVSignCreator.getSignatureMethod(), strArrCreateSignWithSignatureNonceEncrypt[1], strArrCreateSignWithSignatureNonceEncrypt[2]).map(new PLVRxEncryptDataFunction<PLVEncryptDataVO<String>>(String.class) { // from class: com.plv.linkmic.repository.PLVLinkMicDataRepository.9
            @Override // com.plv.foundationsdk.rx.PLVRxEncryptDataFunction
            public Pair<Object, Boolean> accept(PLVEncryptDataVO<String> pLVEncryptDataVO) {
                return new Pair<>(pLVEncryptDataVO.getDataObj(), Boolean.valueOf(pLVEncryptDataVO.isEncryption()));
            }
        }).map(new Function<PLVEncryptDataVO<String>, String>() { // from class: com.plv.linkmic.repository.PLVLinkMicDataRepository.8
            @Override // io.reactivex.functions.Function
            public String apply(PLVEncryptDataVO<String> pLVEncryptDataVO) throws Exception {
                return pLVEncryptDataVO.getData();
            }
        }), new PLVrResponseCallback<String>() { // from class: com.plv.linkmic.repository.PLVLinkMicDataRepository.10
            @Override // com.plv.foundationsdk.net.PLVrResponseCallback
            public void onError(Throwable th) {
                super.onError(th);
                iPLVLinkMicDataRepoListener.onFail(new PLVLinkMicHttpRequestException.Builder(17).cause(th).build());
            }

            @Override // com.plv.foundationsdk.net.PLVrResponseCallback
            public void onFailure(PLVResponseBean<String> pLVResponseBean) {
                super.onFailure(pLVResponseBean);
                iPLVLinkMicDataRepoListener.onFail(new PLVLinkMicHttpRequestException.Builder(17).build());
            }

            @Override // com.plv.foundationsdk.net.PLVrResponseCallback
            public void onFinish() {
                PLVCommonLog.d(PLVLinkMicDataRepository.TAG, "notifyLiveEnd: onFinish");
            }

            @Override // com.plv.foundationsdk.net.PLVrResponseCallback
            public void onSuccess(String str5) {
                if (str5.equals("1")) {
                    iPLVLinkMicDataRepoListener.onSuccess("");
                } else {
                    iPLVLinkMicDataRepoListener.onFail(new PLVLinkMicHttpRequestException.Builder(18).build());
                }
            }
        });
    }

    public void notifyStream(String str, String str2, String str3, String str4, String str5, final IPLVLinkMicDataRepoListener<String> iPLVLinkMicDataRepoListener) {
        if (TextUtils.isEmpty(str3) || TextUtils.isEmpty(str4)) {
            iPLVLinkMicDataRepoListener.onFail(new PLVLinkMicHttpRequestException.Builder(1).msg(PARAM_CAN_NOT_BE_NULL).build());
            return;
        }
        String strValueOf = String.valueOf(System.currentTimeMillis());
        ArrayMap arrayMap = new ArrayMap();
        arrayMap.put("channelId", str3);
        arrayMap.put(STREAM, str4);
        arrayMap.put("timestamp", strValueOf);
        arrayMap.put("appId", str);
        arrayMap.put("goOn", str5);
        arrayMap.put("videoWidth", "1280");
        arrayMap.put("videoHeight", "720");
        arrayMap.put("rtcEnabled", "Y");
        arrayMap.put("pushClient", "AndroidSDK");
        String[] strArrCreateSignWithSignatureNonceEncrypt = PLVSignCreator.createSignWithSignatureNonceEncrypt(str2, arrayMap);
        final String strGenerateSessionIdFromLocal = generateSessionIdFromLocal();
        dispose(this.notifyStreamDisposable);
        this.notifyStreamDisposable = PLVResponseExcutor.excuteUndefinData(PLVLinkMicApiManager.getApiLiveApi().notifyStreamV3(str3, str4, null, strArrCreateSignWithSignatureNonceEncrypt[0], strValueOf, "1280", "720", "Y", "AndroidSDK", str5, str, PLVSignCreator.getSignatureMethod(), strArrCreateSignWithSignatureNonceEncrypt[1], strArrCreateSignWithSignatureNonceEncrypt[2]).map(new PLVRxEncryptDataFunction<PLVEncryptDataVO<String>>(String.class) { // from class: com.plv.linkmic.repository.PLVLinkMicDataRepository.2
            @Override // com.plv.foundationsdk.rx.PLVRxEncryptDataFunction
            public Pair<Object, Boolean> accept(PLVEncryptDataVO<String> pLVEncryptDataVO) {
                return new Pair<>(pLVEncryptDataVO.getDataObj(), Boolean.valueOf(pLVEncryptDataVO.isEncryption()));
            }
        }).map(new Function<PLVEncryptDataVO<String>, String>() { // from class: com.plv.linkmic.repository.PLVLinkMicDataRepository.1
            @Override // io.reactivex.functions.Function
            public String apply(PLVEncryptDataVO<String> pLVEncryptDataVO) throws Exception {
                return pLVEncryptDataVO.getData();
            }
        }), new PLVrResponseCallback<String>() { // from class: com.plv.linkmic.repository.PLVLinkMicDataRepository.3
            @Override // com.plv.foundationsdk.net.PLVrResponseCallback
            public void onFinish() {
                PLVCommonLog.d(PLVLinkMicDataRepository.TAG, "notifyStream: onFinish");
            }

            @Override // com.plv.foundationsdk.net.PLVrResponseCallback
            public void onSuccess(String str6) {
                iPLVLinkMicDataRepoListener.onSuccess(strGenerateSessionIdFromLocal);
            }
        });
    }

    public void release() {
        dispose(this.notifyStreamDisposable);
        dispose(this.getTokenDisposable);
        dispose(this.getSessionIdDisposable);
        dispose(this.getInteractStatusDisposable);
    }

    public void requestToken(String str, String str2, String str3, String str4, PLVLinkMicTokenStatisticsInfo pLVLinkMicTokenStatisticsInfo, IPLVLinkMicDataRepoListener<PLVLinkMicEngineToken> iPLVLinkMicDataRepoListener) {
        requestToken(str, str2, str3, str4, null, pLVLinkMicTokenStatisticsInfo, iPLVLinkMicDataRepoListener);
    }

    public void setRTCType(String str) {
        this.rtcType = str;
    }

    public void requestToken(String str, String str2, String str3, String str4, String str5, PLVLinkMicTokenStatisticsInfo pLVLinkMicTokenStatisticsInfo, IPLVLinkMicDataRepoListener<PLVLinkMicEngineToken> iPLVLinkMicDataRepoListener) {
        Map<String, String> tokenStaticsInfoMap;
        tokenStaticsInfoMap = getTokenStaticsInfoMap(pLVLinkMicTokenStatisticsInfo);
        String str6 = this.rtcType;
        str6.hashCode();
        switch (str6) {
            case "trtc":
                requestTRTCToken(str, str2, str3, str4, tokenStaticsInfoMap, iPLVLinkMicDataRepoListener);
                break;
            case "urtc":
                requestURTCToken(str, str2, str3, str4, str5, tokenStaticsInfoMap, iPLVLinkMicDataRepoListener);
                break;
            case "agora":
                requestARTCToken(str, str2, str3, tokenStaticsInfoMap, iPLVLinkMicDataRepoListener);
                break;
        }
    }
}
