package com.plv.livescenes.document;

import android.text.TextUtils;
import android.util.Pair;
import com.easefun.polyv.livescenes.config.PolyvLiveSDKClient;
import com.plv.foundationsdk.config.PLVVideoViewConstant;
import com.plv.foundationsdk.log.elog.PLVELogsService;
import com.plv.foundationsdk.log.elog.logcode.ppt.PLVErrorCodePPTRequest;
import com.plv.foundationsdk.net.PLVResponseBean;
import com.plv.foundationsdk.net.PLVResponseExcutor;
import com.plv.foundationsdk.net.PLVrResponseCallback;
import com.plv.foundationsdk.rx.PLVRxEncryptDataFunction;
import com.plv.foundationsdk.sign.PLVSignCreator;
import com.plv.foundationsdk.utils.PLVGsonUtil;
import com.plv.livescenes.document.model.PLVPPTInfo;
import com.plv.livescenes.hiclass.PLVHiClassGlobalConfig;
import com.plv.livescenes.hiclass.api.PLVHCApiManager;
import com.plv.livescenes.hiclass.vo.PLVHCLiveApiChannelTokenVO;
import com.plv.livescenes.log.PLVElogEntityCreator;
import com.plv.livescenes.net.PLVApiManager;
import com.plv.thirdpart.blankj.utilcode.util.EncryptUtils;
import com.psychiatrygarden.utils.Constants;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import java.util.HashMap;
import okhttp3.ResponseBody;

/* loaded from: classes4.dex */
public class PLVDocumentDataManager {
    public static void delDocument(PLVPPTInfo.DataBean.ContentsBean contentsBean, final PLVrResponseCallback<ResponseBody> pLVrResponseCallback) {
        if (contentsBean == null || TextUtils.isEmpty(contentsBean.getChannelId()) || TextUtils.isEmpty(contentsBean.getFileId())) {
            PLVELogsService.getInstance().addStaticsLog(PLVElogEntityCreator.createLiveEntity(PLVErrorCodePPTRequest.class, 4, "channel id is null"));
        }
        PLVResponseExcutor.excuteUndefinData(PLVApiManager.getDocumentApi().delPPT(contentsBean.getChannelId(), contentsBean.getFileId(), EncryptUtils.encryptMD5ToString(contentsBean.getChannelId() + contentsBean.getFileId() + PLVVideoViewConstant.LIVE_PREFIX).toUpperCase()), new PLVrResponseCallback<ResponseBody>() { // from class: com.plv.livescenes.document.PLVDocumentDataManager.1
            @Override // com.plv.foundationsdk.net.PLVrResponseCallback
            public void onError(Throwable th) {
                super.onError(th);
                PLVrResponseCallback pLVrResponseCallback2 = pLVrResponseCallback;
                if (pLVrResponseCallback2 != null) {
                    pLVrResponseCallback2.onError(th);
                }
                PLVELogsService.getInstance().addStaticsLog(PLVElogEntityCreator.createLiveEntity(PLVErrorCodePPTRequest.class, 5, th.getMessage()));
            }

            @Override // com.plv.foundationsdk.net.PLVrResponseCallback
            public void onFinish() {
            }

            @Override // com.plv.foundationsdk.net.PLVrResponseCallback
            public void onSuccess(ResponseBody responseBody) {
                if (responseBody == null) {
                    PLVELogsService.getInstance().addStaticsLog(PLVElogEntityCreator.createLiveEntity(PLVErrorCodePPTRequest.class, 6, PLVGsonUtil.toJson(this.responseBean)));
                    return;
                }
                PLVrResponseCallback pLVrResponseCallback2 = pLVrResponseCallback;
                if (pLVrResponseCallback2 != null) {
                    pLVrResponseCallback2.onSuccess(responseBody);
                }
            }
        });
    }

    public static void getDocumentList(PLVrResponseCallback<PLVPPTInfo> pLVrResponseCallback) {
        String appId = PolyvLiveSDKClient.getInstance().getAppId();
        String appSecret = PolyvLiveSDKClient.getInstance().getAppSecret();
        if (!TextUtils.isEmpty(appId) && !TextUtils.isEmpty(appSecret)) {
            getDocumentListForLive(pLVrResponseCallback);
        } else if (TextUtils.isEmpty(PLVHiClassGlobalConfig.getToken())) {
            pLVrResponseCallback.onError(new IllegalAccessException("Not init config"));
        } else {
            getDocumentListForHiClass(pLVrResponseCallback);
        }
    }

    private static void getDocumentListForHiClass(final PLVrResponseCallback<PLVPPTInfo> pLVrResponseCallback) {
        String token = PLVHiClassGlobalConfig.getToken();
        final long lessonId = PLVHiClassGlobalConfig.getLessonId();
        PLVHCApiManager.getInstance().getLiveApiChannelToken(token, lessonId).filter(new Predicate<PLVHCLiveApiChannelTokenVO>() { // from class: com.plv.livescenes.document.PLVDocumentDataManager.7
            @Override // io.reactivex.functions.Predicate
            public boolean test(@NonNull PLVHCLiveApiChannelTokenVO pLVHCLiveApiChannelTokenVO) throws Exception {
                if (pLVHCLiveApiChannelTokenVO.getSuccess() != null && pLVHCLiveApiChannelTokenVO.getSuccess().booleanValue() && pLVHCLiveApiChannelTokenVO.getData() != null) {
                    return true;
                }
                if (pLVrResponseCallback == null || pLVHCLiveApiChannelTokenVO.getError() == null) {
                    return false;
                }
                pLVrResponseCallback.onError(new Exception(pLVHCLiveApiChannelTokenVO.getError().getCode() + "-" + pLVHCLiveApiChannelTokenVO.getError().getDesc()));
                return false;
            }
        }).flatMap(new Function<PLVHCLiveApiChannelTokenVO, ObservableSource<PLVPPTInfo>>() { // from class: com.plv.livescenes.document.PLVDocumentDataManager.6
            @Override // io.reactivex.functions.Function
            public ObservableSource<PLVPPTInfo> apply(@NonNull PLVHCLiveApiChannelTokenVO pLVHCLiveApiChannelTokenVO) throws Exception {
                String token2 = pLVHCLiveApiChannelTokenVO.getData().getToken();
                return PLVApiManager.getPlvLiveStatusApi().getPPTListWithChannelToken(String.valueOf(pLVHCLiveApiChannelTokenVO.getData().getChannelId()), System.currentTimeMillis(), "N", pLVHCLiveApiChannelTokenVO.getData().getAppId(), token2);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<PLVPPTInfo>() { // from class: com.plv.livescenes.document.PLVDocumentDataManager.4
            @Override // io.reactivex.functions.Consumer
            public void accept(PLVPPTInfo pLVPPTInfo) throws Exception {
                if (pLVPPTInfo != null) {
                    PLVrResponseCallback pLVrResponseCallback2 = pLVrResponseCallback;
                    if (pLVrResponseCallback2 != null) {
                        pLVrResponseCallback2.onSuccess(pLVPPTInfo);
                        return;
                    }
                    return;
                }
                PLVELogsService.getInstance().addStaticsLog(PLVElogEntityCreator.createLiveEntity(PLVErrorCodePPTRequest.class, 3, "HiClass lessonId = " + lessonId));
            }
        }, new Consumer<Throwable>() { // from class: com.plv.livescenes.document.PLVDocumentDataManager.5
            @Override // io.reactivex.functions.Consumer
            public void accept(Throwable th) throws Exception {
                PLVrResponseCallback pLVrResponseCallback2 = pLVrResponseCallback;
                if (pLVrResponseCallback2 != null) {
                    pLVrResponseCallback2.onError(th);
                }
                PLVELogsService.getInstance().addStaticsLog(PLVElogEntityCreator.createLiveEntity(PLVErrorCodePPTRequest.class, 2, th.getMessage()));
            }
        });
    }

    private static void getDocumentListForLive(final PLVrResponseCallback<PLVPPTInfo> pLVrResponseCallback) {
        long jCurrentTimeMillis = System.currentTimeMillis();
        String channelId = PolyvLiveSDKClient.getInstance().getChannelId();
        String appId = PolyvLiveSDKClient.getInstance().getAppId();
        HashMap map = new HashMap();
        map.put("channelId", channelId);
        map.put("timestamp", jCurrentTimeMillis + "");
        map.put("isShowUrl", "N");
        map.put(Constants.PARAMS_CONSTANTS.PARAMS_PAGE, "1");
        map.put("limit", "100");
        map.put("appId", appId);
        String[] strArrCreateSignWithSignatureNonceEncrypt = PLVSignCreator.createSignWithSignatureNonceEncrypt(PolyvLiveSDKClient.getInstance().getAppSecret(), map);
        PLVResponseExcutor.excuteUndefinData(PLVApiManager.getPlvLiveStatusApi().getPPTList(channelId, strArrCreateSignWithSignatureNonceEncrypt[0], PLVSignCreator.getSignatureMethod(), jCurrentTimeMillis, "N", 1, 100, appId, strArrCreateSignWithSignatureNonceEncrypt[1], strArrCreateSignWithSignatureNonceEncrypt[2]).map(new PLVRxEncryptDataFunction<PLVPPTInfo>(PLVPPTInfo.DataBean.class) { // from class: com.plv.livescenes.document.PLVDocumentDataManager.2
            @Override // com.plv.foundationsdk.rx.PLVRxEncryptDataFunction
            public Pair<Object, Boolean> accept(PLVPPTInfo pLVPPTInfo) {
                return new Pair<>(pLVPPTInfo.getDataObj(), Boolean.valueOf(pLVPPTInfo.isEncryption()));
            }
        }), new PLVrResponseCallback<PLVPPTInfo>() { // from class: com.plv.livescenes.document.PLVDocumentDataManager.3
            @Override // com.plv.foundationsdk.net.PLVrResponseCallback
            public void onError(Throwable th) {
                super.onError(th);
                PLVrResponseCallback pLVrResponseCallback2 = pLVrResponseCallback;
                if (pLVrResponseCallback2 != null) {
                    pLVrResponseCallback2.onError(th);
                }
                PLVELogsService.getInstance().addStaticsLog(PLVElogEntityCreator.createLiveEntity(PLVErrorCodePPTRequest.class, 2, th.getMessage()));
            }

            @Override // com.plv.foundationsdk.net.PLVrResponseCallback
            public void onFailure(PLVResponseBean<PLVPPTInfo> pLVResponseBean) {
                super.onFailure(pLVResponseBean);
                PLVrResponseCallback pLVrResponseCallback2 = pLVrResponseCallback;
                if (pLVrResponseCallback2 != null) {
                    pLVrResponseCallback2.onFailure(pLVResponseBean);
                }
                PLVELogsService.getInstance().addStaticsLog(PLVElogEntityCreator.createLiveEntity(PLVErrorCodePPTRequest.class, 2, PLVGsonUtil.toJson(pLVResponseBean)));
            }

            @Override // com.plv.foundationsdk.net.PLVrResponseCallback
            public void onFinish() {
                PLVrResponseCallback pLVrResponseCallback2 = pLVrResponseCallback;
                if (pLVrResponseCallback2 != null) {
                    pLVrResponseCallback2.onFinish();
                }
            }

            @Override // com.plv.foundationsdk.net.PLVrResponseCallback
            public void onSuccess(PLVPPTInfo pLVPPTInfo) {
                if (pLVPPTInfo == null) {
                    PLVELogsService.getInstance().addStaticsLog(PLVElogEntityCreator.createLiveEntity(PLVErrorCodePPTRequest.class, 3, PLVGsonUtil.toJson(this.responseBean)));
                    return;
                }
                PLVrResponseCallback pLVrResponseCallback2 = pLVrResponseCallback;
                if (pLVrResponseCallback2 != null) {
                    pLVrResponseCallback2.onSuccess(pLVPPTInfo);
                }
            }
        });
    }
}
