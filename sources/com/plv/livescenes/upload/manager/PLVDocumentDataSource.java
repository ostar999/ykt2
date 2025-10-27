package com.plv.livescenes.upload.manager;

import android.text.TextUtils;
import android.util.Pair;
import androidx.annotation.WorkerThread;
import androidx.collection.ArrayMap;
import com.easefun.polyv.livescenes.config.PolyvLiveSDKClient;
import com.easefun.polyv.livescenes.upload.model.PLVSPPTConvertStatusVO;
import com.easefun.polyv.livescenes.upload.model.PLVSPPTUploadTokenVO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.plv.foundationsdk.net.PLVResponseApiBean;
import com.plv.foundationsdk.net.PLVResponseBean;
import com.plv.foundationsdk.net.PLVResponseExcutor;
import com.plv.foundationsdk.net.PLVrResponseCallback;
import com.plv.foundationsdk.rx.PLVRxEncryptDataFunction;
import com.plv.foundationsdk.sign.PLVSignCreator;
import com.plv.foundationsdk.utils.PLVFormatUtils;
import com.plv.foundationsdk.utils.PLVSugarUtil;
import com.plv.livescenes.hiclass.PLVHiClassGlobalConfig;
import com.plv.livescenes.hiclass.api.PLVHCApiManager;
import com.plv.livescenes.hiclass.vo.PLVHCLiveApiChannelTokenVO;
import com.plv.livescenes.net.PLVApiManager;
import com.plv.livescenes.upload.model.PLVPPTConvertStatusFullVO;
import com.plv.livescenes.upload.model.PLVPPTConvertStatusVO;
import com.plv.livescenes.upload.model.PLVPPTUploadTokenVO;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import java.io.IOException;
import java.util.List;
import retrofit2.Response;

/* loaded from: classes5.dex */
class PLVDocumentDataSource {
    private Disposable getDocumentConvertStatusDisposable;

    private void dispose(Disposable disposable) {
        if (disposable != null) {
            disposable.dispose();
        }
    }

    private Observable<List<PLVPPTConvertStatusVO>> getDocumentUploadConvertStatusForHiClass(final List<String> list) {
        return PLVHCApiManager.getInstance().getLiveApiChannelToken(PLVHiClassGlobalConfig.getToken(), PLVHiClassGlobalConfig.getLessonId()).subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io()).filter(new Predicate<PLVHCLiveApiChannelTokenVO>() { // from class: com.plv.livescenes.upload.manager.PLVDocumentDataSource.5
            @Override // io.reactivex.functions.Predicate
            public boolean test(@NonNull PLVHCLiveApiChannelTokenVO pLVHCLiveApiChannelTokenVO) throws Exception {
                return (pLVHCLiveApiChannelTokenVO.getSuccess() == null || !pLVHCLiveApiChannelTokenVO.getSuccess().booleanValue() || pLVHCLiveApiChannelTokenVO.getData() == null) ? false : true;
            }
        }).flatMap(new Function<PLVHCLiveApiChannelTokenVO, ObservableSource<PLVResponseApiBean>>() { // from class: com.plv.livescenes.upload.manager.PLVDocumentDataSource.4
            @Override // io.reactivex.functions.Function
            public ObservableSource<PLVResponseApiBean> apply(@NonNull PLVHCLiveApiChannelTokenVO pLVHCLiveApiChannelTokenVO) throws Exception {
                String appId = pLVHCLiveApiChannelTokenVO.getData().getAppId();
                String token = pLVHCLiveApiChannelTokenVO.getData().getToken();
                int iIntValue = ((Integer) PLVSugarUtil.getOrDefault(pLVHCLiveApiChannelTokenVO.getData().getChannelId(), 0)).intValue();
                return PLVApiManager.getPlvLiveStatusApi().getDocumentConvertStatusWithChannelToken(appId, String.valueOf(System.currentTimeMillis()), iIntValue, PLVSugarUtil.stringJoin(list, ","), token);
            }
        }).filter(new Predicate<PLVResponseApiBean>() { // from class: com.plv.livescenes.upload.manager.PLVDocumentDataSource.3
            @Override // io.reactivex.functions.Predicate
            public boolean test(@NonNull PLVResponseApiBean pLVResponseApiBean) throws Exception {
                return pLVResponseApiBean.getData().isJsonArray();
            }
        }).map(new Function<PLVResponseApiBean, List<PLVPPTConvertStatusVO>>() { // from class: com.plv.livescenes.upload.manager.PLVDocumentDataSource.2
            @Override // io.reactivex.functions.Function
            public List<PLVPPTConvertStatusVO> apply(@NonNull PLVResponseApiBean pLVResponseApiBean) throws Exception {
                return (List) new Gson().fromJson(pLVResponseApiBean.getData().getAsJsonArray(), new TypeToken<List<PLVSPPTConvertStatusVO>>() { // from class: com.plv.livescenes.upload.manager.PLVDocumentDataSource.2.1
                }.getType());
            }
        });
    }

    private Observable<List<PLVPPTConvertStatusVO>> getDocumentUploadConvertStatusForLive(final List<String> list) {
        dispose(this.getDocumentConvertStatusDisposable);
        return Observable.create(new ObservableOnSubscribe<List<PLVPPTConvertStatusVO>>() { // from class: com.plv.livescenes.upload.manager.PLVDocumentDataSource.1
            @Override // io.reactivex.ObservableOnSubscribe
            public void subscribe(final ObservableEmitter<List<PLVPPTConvertStatusVO>> observableEmitter) throws Exception {
                String appId = PolyvLiveSDKClient.getInstance().getAppId();
                String str = System.currentTimeMillis() + "";
                String channelId = PolyvLiveSDKClient.getInstance().getChannelId();
                StringBuilder sb = new StringBuilder();
                for (int i2 = 0; i2 < list.size(); i2++) {
                    if (i2 == 0) {
                        sb = new StringBuilder((String) list.get(i2));
                    } else {
                        sb.append(",");
                        sb.append((String) list.get(i2));
                    }
                }
                ArrayMap arrayMap = new ArrayMap();
                arrayMap.put("appId", appId);
                arrayMap.put("timestamp", str);
                arrayMap.put("channelId", channelId);
                arrayMap.put("fileId", sb.toString());
                String[] strArrCreateSignWithSignatureNonceEncrypt = PLVSignCreator.createSignWithSignatureNonceEncrypt(PolyvLiveSDKClient.getInstance().getAppSecret(), arrayMap);
                PLVDocumentDataSource.this.getDocumentConvertStatusDisposable = PLVResponseExcutor.excuteUndefinData(PLVApiManager.getPlvLiveStatusApi().getDocumentConvertStatus(appId, str, strArrCreateSignWithSignatureNonceEncrypt[0], PLVSignCreator.getSignatureMethod(), PLVFormatUtils.parseInt(channelId), sb.toString(), strArrCreateSignWithSignatureNonceEncrypt[1], strArrCreateSignWithSignatureNonceEncrypt[2]).map(new PLVRxEncryptDataFunction<PLVPPTConvertStatusFullVO>(PLVPPTConvertStatusVO.class, true) { // from class: com.plv.livescenes.upload.manager.PLVDocumentDataSource.1.1
                    @Override // com.plv.foundationsdk.rx.PLVRxEncryptDataFunction
                    public Pair<Object, Boolean> accept(PLVPPTConvertStatusFullVO pLVPPTConvertStatusFullVO) {
                        return new Pair<>(pLVPPTConvertStatusFullVO.getDataObj(), Boolean.valueOf(pLVPPTConvertStatusFullVO.isEncryption()));
                    }
                }), new PLVrResponseCallback<PLVPPTConvertStatusFullVO>() { // from class: com.plv.livescenes.upload.manager.PLVDocumentDataSource.1.2
                    @Override // com.plv.foundationsdk.net.PLVrResponseCallback
                    public void onError(Throwable th) {
                        super.onError(th);
                        observableEmitter.onError(th);
                    }

                    @Override // com.plv.foundationsdk.net.PLVrResponseCallback
                    public void onFailure(PLVResponseBean<PLVPPTConvertStatusFullVO> pLVResponseBean) {
                        super.onFailure(pLVResponseBean);
                        if (observableEmitter.isDisposed()) {
                            observableEmitter.onError(new Throwable(pLVResponseBean.toString()));
                        }
                    }

                    @Override // com.plv.foundationsdk.net.PLVrResponseCallback
                    public void onFinish() {
                        observableEmitter.onComplete();
                    }

                    @Override // com.plv.foundationsdk.net.PLVrResponseCallback
                    public void onSuccess(PLVPPTConvertStatusFullVO pLVPPTConvertStatusFullVO) {
                        observableEmitter.onNext(pLVPPTConvertStatusFullVO.getData());
                    }
                });
            }
        });
    }

    @WorkerThread
    private PLVPPTUploadTokenVO getUploadTokenForHiClass(String str, String str2, String str3) throws IOException {
        PLVHCLiveApiChannelTokenVO liveApiChannelTokenSync = PLVHCApiManager.getInstance().getLiveApiChannelTokenSync(PLVHiClassGlobalConfig.getToken(), PLVHiClassGlobalConfig.getLessonId());
        if (liveApiChannelTokenSync == null) {
            throw new IOException("request channel token fail.");
        }
        if (liveApiChannelTokenSync.getSuccess() == null || !liveApiChannelTokenSync.getSuccess().booleanValue() || liveApiChannelTokenSync.getData() == null) {
            if (liveApiChannelTokenSync.getError() == null) {
                throw new IOException("request channel token fail.");
            }
            throw new IOException(liveApiChannelTokenSync.getError().getCode() + "-" + liveApiChannelTokenSync.getError().getDesc());
        }
        Response<PLVPPTUploadTokenVO> responseExecute = PLVApiManager.getPlvLiveStatusApi().getPPTUploadTokenWithChannelToken(liveApiChannelTokenSync.getData().getAppId(), System.currentTimeMillis() + "", ((Integer) PLVSugarUtil.getOrDefault(liveApiChannelTokenSync.getData().getChannelId(), 0)).intValue(), str3, str, str2, liveApiChannelTokenSync.getData().getToken()).execute();
        if (responseExecute.isSuccessful()) {
            return responseExecute.body();
        }
        String str4 = responseExecute.code() + "\n";
        if (responseExecute.errorBody() != null) {
            str4 = str4 + responseExecute.errorBody().string();
        }
        throw new IOException(str4);
    }

    @WorkerThread
    private PLVPPTUploadTokenVO getUploadTokenForLive(String str, String str2, String str3) throws IOException {
        String appId = PolyvLiveSDKClient.getInstance().getAppId();
        String str4 = System.currentTimeMillis() + "";
        String channelId = PolyvLiveSDKClient.getInstance().getChannelId();
        ArrayMap arrayMap = new ArrayMap();
        arrayMap.put("appId", appId);
        arrayMap.put("timestamp", str4);
        arrayMap.put("channelId", channelId);
        arrayMap.put("fileId", str3);
        arrayMap.put("fileName", str);
        arrayMap.put("type", str2);
        String[] strArrCreateSignWithSignatureNonceEncrypt = PLVSignCreator.createSignWithSignatureNonceEncrypt(PolyvLiveSDKClient.getInstance().getAppSecret(), arrayMap);
        Response<PLVPPTUploadTokenVO> responseExecute = PLVApiManager.getPlvLiveStatusApi().getPPTUploadToken(appId, str4, strArrCreateSignWithSignatureNonceEncrypt[0], PLVSignCreator.getSignatureMethod(), PLVFormatUtils.parseInt(channelId), str3, str, str2, strArrCreateSignWithSignatureNonceEncrypt[1], strArrCreateSignWithSignatureNonceEncrypt[2]).execute();
        if (responseExecute.isSuccessful()) {
            PLVPPTUploadTokenVO pLVPPTUploadTokenVOBody = responseExecute.body();
            if (pLVPPTUploadTokenVOBody == null) {
                return null;
            }
            return (PLVPPTUploadTokenVO) PLVRxEncryptDataFunction.transformData(pLVPPTUploadTokenVOBody, pLVPPTUploadTokenVOBody.getDataObj(), PLVPPTUploadTokenVO.DataBean.class, pLVPPTUploadTokenVOBody.isEncryption());
        }
        String str5 = responseExecute.code() + "\n";
        if (responseExecute.errorBody() != null) {
            str5 = str5 + responseExecute.errorBody().string();
        }
        throw new IOException(str5);
    }

    public Observable<List<PLVPPTConvertStatusVO>> getDocumentUploadConvertStatus(List<String> list) {
        return (TextUtils.isEmpty(PolyvLiveSDKClient.getInstance().getAppId()) || TextUtils.isEmpty(PolyvLiveSDKClient.getInstance().getAppSecret())) ? getDocumentUploadConvertStatusForHiClass(list) : getDocumentUploadConvertStatusForLive(list);
    }

    @WorkerThread
    public PLVPPTUploadTokenVO getUploadToken(String str, String str2, String str3) throws IOException {
        return (TextUtils.isEmpty(PolyvLiveSDKClient.getInstance().getAppId()) || TextUtils.isEmpty(PolyvLiveSDKClient.getInstance().getAppSecret())) ? !TextUtils.isEmpty(PLVHiClassGlobalConfig.getToken()) ? getUploadTokenForHiClass(str, str2, str3) : new PLVSPPTUploadTokenVO() : getUploadTokenForLive(str, str2, str3);
    }
}
