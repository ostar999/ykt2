package com.plv.livescenes.download.api;

import android.util.Pair;
import com.easefun.polyv.livescenes.config.PolyvLiveSDKClient;
import com.plv.foundationsdk.rx.PLVRxEncryptDataFunction;
import com.plv.foundationsdk.sign.PLVSignCreator;
import com.plv.foundationsdk.utils.PLVSugarUtil;
import com.plv.livescenes.model.PLVPlaybackVideoVO;
import com.plv.livescenes.model.PLVTempStorePlaybackVideoVO;
import com.plv.livescenes.net.PLVApiManager;
import io.reactivex.Observable;

/* loaded from: classes4.dex */
public class PLVPlaybackDownloadApiManager {
    private static final PLVPlaybackDownloadApiManager INSTANCE = new PLVPlaybackDownloadApiManager();

    private PLVPlaybackDownloadApiManager() {
    }

    public static PLVPlaybackDownloadApiManager getInstance() {
        return INSTANCE;
    }

    public Observable<PLVPlaybackVideoVO> requestPlaybackVideoDetail(String str, String str2) {
        long jCurrentTimeMillis = System.currentTimeMillis();
        String appId = PolyvLiveSDKClient.getInstance().getAppId();
        String[] strArrCreateSignWithSignatureNonceEncrypt = PLVSignCreator.createSignWithSignatureNonceEncrypt(PolyvLiveSDKClient.getInstance().getAppSecret(), PLVSugarUtil.mapOf(PLVSugarUtil.pair("channelId", str), PLVSugarUtil.pair("timestamp", jCurrentTimeMillis + ""), PLVSugarUtil.pair("appId", appId), PLVSugarUtil.pair("vid", str2)));
        return PLVApiManager.getPlvLiveStatusApi().getPlaybackVO(appId, str, str2, jCurrentTimeMillis, strArrCreateSignWithSignatureNonceEncrypt[0], PLVSignCreator.getSignatureMethod(), strArrCreateSignWithSignatureNonceEncrypt[1], strArrCreateSignWithSignatureNonceEncrypt[2]).map(new PLVRxEncryptDataFunction<PLVPlaybackVideoVO>(PLVPlaybackVideoVO.Data.class) { // from class: com.plv.livescenes.download.api.PLVPlaybackDownloadApiManager.2
            @Override // com.plv.foundationsdk.rx.PLVRxEncryptDataFunction
            public Pair<Object, Boolean> accept(PLVPlaybackVideoVO pLVPlaybackVideoVO) {
                return new Pair<>(pLVPlaybackVideoVO.getDataObj(), Boolean.valueOf(pLVPlaybackVideoVO.isEncryption()));
            }
        });
    }

    public Observable<PLVTempStorePlaybackVideoVO> requestTempStorePlaybackVideoDetail(String str, String str2) {
        long jCurrentTimeMillis = System.currentTimeMillis();
        String appId = PolyvLiveSDKClient.getInstance().getAppId();
        String[] strArrCreateSignWithSignatureNonceEncrypt = PLVSignCreator.createSignWithSignatureNonceEncrypt(PolyvLiveSDKClient.getInstance().getAppSecret(), PLVSugarUtil.mapOf(PLVSugarUtil.pair("channelId", str), PLVSugarUtil.pair("timestamp", jCurrentTimeMillis + ""), PLVSugarUtil.pair("appId", appId), PLVSugarUtil.pair("fileId", str2)));
        return PLVApiManager.getPlvLiveStatusApi().getTempStorePlaybackVO(appId, str, str2, jCurrentTimeMillis, strArrCreateSignWithSignatureNonceEncrypt[0], PLVSignCreator.getSignatureMethod(), strArrCreateSignWithSignatureNonceEncrypt[1], strArrCreateSignWithSignatureNonceEncrypt[2]).map(new PLVRxEncryptDataFunction<PLVTempStorePlaybackVideoVO>(PLVTempStorePlaybackVideoVO.Data.class) { // from class: com.plv.livescenes.download.api.PLVPlaybackDownloadApiManager.1
            @Override // com.plv.foundationsdk.rx.PLVRxEncryptDataFunction
            public Pair<Object, Boolean> accept(PLVTempStorePlaybackVideoVO pLVTempStorePlaybackVideoVO) {
                return new Pair<>(pLVTempStorePlaybackVideoVO.getDataObj(), Boolean.valueOf(pLVTempStorePlaybackVideoVO.isEncryption()));
            }
        });
    }
}
