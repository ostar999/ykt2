package com.plv.livescenes.feature.pointreward;

import android.util.Pair;
import com.easefun.polyv.livescenes.config.PolyvLiveSDKClient;
import com.plv.foundationsdk.log.PLVCommonLog;
import com.plv.foundationsdk.net.PLVResponseBean;
import com.plv.foundationsdk.net.PLVResponseExcutor;
import com.plv.foundationsdk.net.PLVrResponseCallback;
import com.plv.foundationsdk.rx.PLVRxEncryptDataFunction;
import com.plv.foundationsdk.sign.PLVSignCreator;
import com.plv.foundationsdk.utils.PLVFormatUtils;
import com.plv.livescenes.feature.pointreward.IPLVPointRewardDataSource;
import com.plv.livescenes.feature.pointreward.vo.PLVDonateGoodResponseVO;
import com.plv.livescenes.model.pointreward.PLVPointRewardVO;
import com.plv.livescenes.model.pointreward.PLVRewardSettingFullVO;
import com.plv.livescenes.model.pointreward.PLVRewardSettingVO;
import com.plv.livescenes.net.PLVApiManager;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import java.util.HashMap;

/* loaded from: classes4.dex */
public class PLVRewardDataSource implements IPLVPointRewardDataSource {
    private static final String APP_ID = "appId";
    private static final String AVATAR = "avatar";
    private static final String CHANNEL_ID = "channelId";
    private static final String GOOD_ID = "goodId";
    private static final String GOOD_NUM = "goodNum";
    private static final String NICKNAME = "nickname";
    private static final String TAG = "PLVPointRewardDataSourc";
    private static final String TIMESTAMP = "timestamp";
    private static final String VIEWER_ID = "viewerId";
    private Disposable getPointRewardSettingDisposable;
    private Disposable getRemainingRewardPointDisposable;
    private Disposable makeRewardDisposable;

    private void dispose(Disposable disposable) {
        if (disposable != null) {
            disposable.dispose();
        }
    }

    @Override // com.plv.livescenes.feature.pointreward.IPLVPointRewardDataSource
    public void destroy() {
        dispose(this.makeRewardDisposable);
        dispose(this.getRemainingRewardPointDisposable);
        dispose(this.getPointRewardSettingDisposable);
    }

    @Override // com.plv.livescenes.feature.pointreward.IPLVPointRewardDataSource
    public void getPointRewardSetting(String str, final IPLVPointRewardDataSource.IPointRewardListener<PLVRewardSettingVO> iPointRewardListener) {
        dispose(this.getPointRewardSettingDisposable);
        String appId = PolyvLiveSDKClient.getInstance().getAppId();
        String appSecret = PolyvLiveSDKClient.getInstance().getAppSecret();
        long jCurrentTimeMillis = System.currentTimeMillis();
        HashMap map = new HashMap();
        map.put("appId", appId);
        map.put("timestamp", String.valueOf(jCurrentTimeMillis));
        map.put("channelId", str);
        String[] strArrCreateSignWithSignatureNonceEncrypt = PLVSignCreator.createSignWithSignatureNonceEncrypt(appSecret, map);
        this.getPointRewardSettingDisposable = PLVResponseExcutor.excuteUndefinData(PLVApiManager.getPlvLiveStatusApi().getPointRewardSetting(appId, String.valueOf(jCurrentTimeMillis), strArrCreateSignWithSignatureNonceEncrypt[0], str, PLVSignCreator.getSignatureMethod(), strArrCreateSignWithSignatureNonceEncrypt[1], strArrCreateSignWithSignatureNonceEncrypt[2]).map(new PLVRxEncryptDataFunction<PLVRewardSettingFullVO>(PLVRewardSettingVO.class) { // from class: com.plv.livescenes.feature.pointreward.PLVRewardDataSource.2
            @Override // com.plv.foundationsdk.rx.PLVRxEncryptDataFunction
            public Pair<Object, Boolean> accept(PLVRewardSettingFullVO pLVRewardSettingFullVO) {
                return new Pair<>(pLVRewardSettingFullVO.getDataObj(), Boolean.valueOf(pLVRewardSettingFullVO.isEncryption()));
            }
        }).map(new Function<PLVRewardSettingFullVO, PLVRewardSettingVO>() { // from class: com.plv.livescenes.feature.pointreward.PLVRewardDataSource.1
            @Override // io.reactivex.functions.Function
            public PLVRewardSettingVO apply(PLVRewardSettingFullVO pLVRewardSettingFullVO) throws Exception {
                return pLVRewardSettingFullVO.getData();
            }
        }), new PLVrResponseCallback<PLVRewardSettingVO>() { // from class: com.plv.livescenes.feature.pointreward.PLVRewardDataSource.3
            @Override // com.plv.foundationsdk.net.PLVrResponseCallback
            public void onError(Throwable th) {
                super.onError(th);
                iPointRewardListener.onFailed(th);
            }

            @Override // com.plv.foundationsdk.net.PLVrResponseCallback
            public void onFailure(PLVResponseBean<PLVRewardSettingVO> pLVResponseBean) {
                super.onFailure(pLVResponseBean);
                iPointRewardListener.onFailed(new Throwable(pLVResponseBean.getMessage()));
            }

            @Override // com.plv.foundationsdk.net.PLVrResponseCallback
            public void onFinish() {
                PLVCommonLog.d(PLVRewardDataSource.TAG, "getPointRewardSetting onFinish");
            }

            @Override // com.plv.foundationsdk.net.PLVrResponseCallback
            public void onSuccess(PLVRewardSettingVO pLVRewardSettingVO) {
                iPointRewardListener.onSuccess(pLVRewardSettingVO);
            }
        });
    }

    @Override // com.plv.livescenes.feature.pointreward.IPLVPointRewardDataSource
    public void getRemainingRewardPoint(String str, String str2, String str3, final IPLVPointRewardDataSource.IPointRewardListener<String> iPointRewardListener) {
        String appId = PolyvLiveSDKClient.getInstance().getAppId();
        long jCurrentTimeMillis = System.currentTimeMillis();
        String appSecret = PolyvLiveSDKClient.getInstance().getAppSecret();
        HashMap map = new HashMap();
        map.put("channelId", str);
        map.put("appId", appId);
        map.put("timestamp", String.valueOf(jCurrentTimeMillis));
        map.put("viewerId", str2);
        map.put("nickname", str3);
        String[] strArrCreateSignWithSignatureNonceEncrypt = PLVSignCreator.createSignWithSignatureNonceEncrypt(appSecret, map);
        dispose(this.getRemainingRewardPointDisposable);
        this.getRemainingRewardPointDisposable = PLVResponseExcutor.excuteUndefinData(PLVApiManager.getPlvLiveStatusApi().getRewardPoint(PLVFormatUtils.parseInt(str), appId, jCurrentTimeMillis, strArrCreateSignWithSignatureNonceEncrypt[0], PLVSignCreator.getSignatureMethod(), str2, str3, strArrCreateSignWithSignatureNonceEncrypt[1], strArrCreateSignWithSignatureNonceEncrypt[2]).map(new PLVRxEncryptDataFunction<PLVPointRewardVO>(Integer.class) { // from class: com.plv.livescenes.feature.pointreward.PLVRewardDataSource.9
            @Override // com.plv.foundationsdk.rx.PLVRxEncryptDataFunction
            public Pair<Object, Boolean> accept(PLVPointRewardVO pLVPointRewardVO) {
                return new Pair<>(pLVPointRewardVO.getDataObj(), Boolean.valueOf(pLVPointRewardVO.isEncryption()));
            }
        }), new PLVrResponseCallback<PLVPointRewardVO>() { // from class: com.plv.livescenes.feature.pointreward.PLVRewardDataSource.10
            @Override // com.plv.foundationsdk.net.PLVrResponseCallback
            public void onError(Throwable th) {
                super.onError(th);
                iPointRewardListener.onFailed(th);
            }

            @Override // com.plv.foundationsdk.net.PLVrResponseCallback
            public void onFailure(PLVResponseBean<PLVPointRewardVO> pLVResponseBean) {
                super.onFailure(pLVResponseBean);
                iPointRewardListener.onFailed(new Throwable(pLVResponseBean.getMessage()));
            }

            @Override // com.plv.foundationsdk.net.PLVrResponseCallback
            public void onFinish() {
                PLVCommonLog.d(PLVRewardDataSource.TAG, "getRewardPoint onFinish");
            }

            @Override // com.plv.foundationsdk.net.PLVrResponseCallback
            public void onSuccess(PLVPointRewardVO pLVPointRewardVO) {
                iPointRewardListener.onSuccess(String.valueOf(pLVPointRewardVO.getData()));
            }
        });
    }

    @Override // com.plv.livescenes.feature.pointreward.IPLVPointRewardDataSource
    public void makeGiftCashReward(String str, int i2, int i3, String str2, String str3, String str4, String str5, final IPLVPointRewardDataSource.IPointRewardListener<PLVDonateGoodResponseVO> iPointRewardListener) {
        String appId = PolyvLiveSDKClient.getInstance().getAppId();
        String appSecret = PolyvLiveSDKClient.getInstance().getAppSecret();
        long jCurrentTimeMillis = System.currentTimeMillis();
        HashMap map = new HashMap();
        map.put("appId", appId);
        map.put("timestamp", jCurrentTimeMillis + "");
        String[] strArrCreateSignWithSignatureNonceEncrypt = PLVSignCreator.createSignWithSignatureNonceEncrypt(appSecret, map);
        dispose(this.makeRewardDisposable);
        this.makeRewardDisposable = PLVApiManager.getPlvLiveStatusApi().giftCashReward(appId, jCurrentTimeMillis, strArrCreateSignWithSignatureNonceEncrypt[0], PLVFormatUtils.parseInt(str), "none", "GIFT", str2, str3, str4, i2, i3, str5, PLVSignCreator.getSignatureMethod(), strArrCreateSignWithSignatureNonceEncrypt[1], strArrCreateSignWithSignatureNonceEncrypt[2]).map(new PLVRxEncryptDataFunction<PLVDonateGoodResponseVO>(PLVDonateGoodResponseVO.Data.class) { // from class: com.plv.livescenes.feature.pointreward.PLVRewardDataSource.8
            @Override // com.plv.foundationsdk.rx.PLVRxEncryptDataFunction
            public Pair<Object, Boolean> accept(PLVDonateGoodResponseVO pLVDonateGoodResponseVO) {
                return new Pair<>(pLVDonateGoodResponseVO.getDataObj(), Boolean.valueOf(pLVDonateGoodResponseVO.isEncryption()));
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<PLVDonateGoodResponseVO>() { // from class: com.plv.livescenes.feature.pointreward.PLVRewardDataSource.6
            @Override // io.reactivex.functions.Consumer
            public void accept(PLVDonateGoodResponseVO pLVDonateGoodResponseVO) throws Exception {
                iPointRewardListener.onSuccess(pLVDonateGoodResponseVO);
            }
        }, new Consumer<Throwable>() { // from class: com.plv.livescenes.feature.pointreward.PLVRewardDataSource.7
            @Override // io.reactivex.functions.Consumer
            public void accept(Throwable th) throws Exception {
                iPointRewardListener.onFailed(th);
            }
        });
    }

    @Override // com.plv.livescenes.feature.pointreward.IPLVPointRewardDataSource
    public void makePointReward(String str, int i2, int i3, String str2, String str3, String str4, final IPLVPointRewardDataSource.IPointRewardListener<String> iPointRewardListener) {
        String appId = PolyvLiveSDKClient.getInstance().getAppId();
        String appSecret = PolyvLiveSDKClient.getInstance().getAppSecret();
        long jCurrentTimeMillis = System.currentTimeMillis();
        HashMap map = new HashMap();
        map.put("channelId", str);
        map.put("appId", appId);
        map.put("timestamp", String.valueOf(jCurrentTimeMillis));
        map.put("viewerId", str2);
        map.put(GOOD_ID, String.valueOf(i2));
        map.put(GOOD_NUM, String.valueOf(i3));
        map.put("nickname", String.valueOf(str3));
        map.put(AVATAR, str4);
        String[] strArrCreateSignWithSignatureNonceEncrypt = PLVSignCreator.createSignWithSignatureNonceEncrypt(appSecret, map);
        dispose(this.makeRewardDisposable);
        this.makeRewardDisposable = PLVResponseExcutor.excuteUndefinData(PLVApiManager.getPlvLiveStatusApi().pointReward(PLVFormatUtils.parseInt(str), appId, jCurrentTimeMillis, strArrCreateSignWithSignatureNonceEncrypt[0], PLVSignCreator.getSignatureMethod(), str2, i2, i3, str3, str4, strArrCreateSignWithSignatureNonceEncrypt[1], strArrCreateSignWithSignatureNonceEncrypt[2]).map(new PLVRxEncryptDataFunction<PLVPointRewardVO>(Integer.class) { // from class: com.plv.livescenes.feature.pointreward.PLVRewardDataSource.4
            @Override // com.plv.foundationsdk.rx.PLVRxEncryptDataFunction
            public Pair<Object, Boolean> accept(PLVPointRewardVO pLVPointRewardVO) {
                return new Pair<>(pLVPointRewardVO.getDataObj(), Boolean.valueOf(pLVPointRewardVO.isEncryption()));
            }
        }), new PLVrResponseCallback<PLVPointRewardVO>() { // from class: com.plv.livescenes.feature.pointreward.PLVRewardDataSource.5
            @Override // com.plv.foundationsdk.net.PLVrResponseCallback
            public void onError(Throwable th) {
                super.onError(th);
                iPointRewardListener.onFailed(th);
            }

            @Override // com.plv.foundationsdk.net.PLVrResponseCallback
            public void onFailure(PLVResponseBean<PLVPointRewardVO> pLVResponseBean) {
                super.onFailure(pLVResponseBean);
                iPointRewardListener.onFailed(new Throwable(pLVResponseBean.getMessage()));
            }

            @Override // com.plv.foundationsdk.net.PLVrResponseCallback
            public void onFinish() {
                PLVCommonLog.d(PLVRewardDataSource.TAG, "pointReward onFinish");
            }

            @Override // com.plv.foundationsdk.net.PLVrResponseCallback
            public void onSuccess(PLVPointRewardVO pLVPointRewardVO) {
                iPointRewardListener.onSuccess(String.valueOf(pLVPointRewardVO.getData()));
            }
        });
    }
}
