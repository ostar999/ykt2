package com.plv.livescenes.feature.liveinfo;

import android.util.Pair;
import androidx.collection.ArrayMap;
import com.easefun.polyv.livescenes.config.PolyvLiveSDKClient;
import com.plv.foundationsdk.log.PLVCommonLog;
import com.plv.foundationsdk.net.PLVResponseExcutor;
import com.plv.foundationsdk.net.PLVrResponseCallback;
import com.plv.foundationsdk.rx.PLVRxEncryptDataFunction;
import com.plv.foundationsdk.rx.PLVRxTimer;
import com.plv.foundationsdk.sign.PLVSignCreator;
import com.plv.foundationsdk.utils.PLVGsonUtil;
import com.plv.livescenes.model.PLVIncreasePageViewerVO;
import com.plv.livescenes.net.PLVApiManager;
import com.plv.livescenes.socket.PLVSocketWrapper;
import com.plv.socket.event.login.PLVLoginEvent;
import com.plv.socket.impl.PLVSocketMessageObserver;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import java.util.List;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class PLVLiveInfoDataSource {
    private static final String TAG = "PLVLiveInfoDataSource";
    private int channelId;
    private Disposable increaseViewerApiDisposable;
    private Disposable increaseViewerTimer;
    private Disposable observeLoginEventDisposable;
    private String viewerId;
    private int pageViewerRequestInterval = 5000;
    private int viewerTimes2Send = 0;

    public PLVLiveInfoDataSource(int i2, String str) {
        this.channelId = i2;
        this.viewerId = str;
    }

    public static /* synthetic */ int access$308(PLVLiveInfoDataSource pLVLiveInfoDataSource) {
        int i2 = pLVLiveInfoDataSource.viewerTimes2Send;
        pLVLiveInfoDataSource.viewerTimes2Send = i2 + 1;
        return i2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dispose(Disposable disposable) {
        if (disposable != null) {
            disposable.dispose();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void increasePageViewer() {
        String appId = PolyvLiveSDKClient.getInstance().getAppId();
        String appSecret = PolyvLiveSDKClient.getInstance().getAppSecret();
        long jCurrentTimeMillis = System.currentTimeMillis();
        ArrayMap arrayMap = new ArrayMap();
        arrayMap.put("channelId", String.valueOf(this.channelId));
        arrayMap.put("appId", appId);
        arrayMap.put("timestamp", String.valueOf(jCurrentTimeMillis));
        arrayMap.put("times", String.valueOf(this.viewerTimes2Send));
        String[] strArrCreateSignWithSignatureNonceEncrypt = PLVSignCreator.createSignWithSignatureNonceEncrypt(appSecret, arrayMap);
        dispose(this.increaseViewerApiDisposable);
        this.increaseViewerApiDisposable = PLVResponseExcutor.excuteUndefinData(PLVApiManager.getPlvLiveStatusApi().increasePageViewer2(this.channelId, appId, jCurrentTimeMillis, strArrCreateSignWithSignatureNonceEncrypt[0], PLVSignCreator.getSignatureMethod(), this.viewerTimes2Send, strArrCreateSignWithSignatureNonceEncrypt[1], strArrCreateSignWithSignatureNonceEncrypt[2]).map(new PLVRxEncryptDataFunction<PLVIncreasePageViewerVO>(Integer.class) { // from class: com.plv.livescenes.feature.liveinfo.PLVLiveInfoDataSource.7
            @Override // com.plv.foundationsdk.rx.PLVRxEncryptDataFunction
            public Pair<Object, Boolean> accept(PLVIncreasePageViewerVO pLVIncreasePageViewerVO) {
                return new Pair<>(pLVIncreasePageViewerVO.getDataObj(), Boolean.valueOf(pLVIncreasePageViewerVO.isEncryption()));
            }
        }), new PLVrResponseCallback<PLVIncreasePageViewerVO>() { // from class: com.plv.livescenes.feature.liveinfo.PLVLiveInfoDataSource.8
            @Override // com.plv.foundationsdk.net.PLVrResponseCallback
            public void onFinish() {
                PLVCommonLog.d(PLVLiveInfoDataSource.TAG, "increasePageViewer onFinish");
            }

            @Override // com.plv.foundationsdk.net.PLVrResponseCallback
            public void onSuccess(PLVIncreasePageViewerVO pLVIncreasePageViewerVO) {
                PLVLiveInfoDataSource.this.viewerTimes2Send = 0;
            }
        });
    }

    public void destroy() {
        dispose(this.observeLoginEventDisposable);
        dispose(this.increaseViewerTimer);
        dispose(this.increaseViewerApiDisposable);
    }

    public void observePageViewer(final Action action) {
        Observable observableCreate = Observable.create(new ObservableOnSubscribe<String>() { // from class: com.plv.livescenes.feature.liveinfo.PLVLiveInfoDataSource.1
            @Override // io.reactivex.ObservableOnSubscribe
            public void subscribe(final ObservableEmitter<String> observableEmitter) throws Exception {
                PLVSocketWrapper.getInstance().getSocketObserver().addOnMessageListener(new PLVSocketMessageObserver.OnMessageListener() { // from class: com.plv.livescenes.feature.liveinfo.PLVLiveInfoDataSource.1.1
                    @Override // com.plv.socket.impl.PLVSocketMessageObserver.OnMessageListener
                    public void onMessage(String str, String str2, String str3) {
                        if ("message".equals(str) && "LOGIN".equals(str2)) {
                            observableEmitter.onNext(str3);
                        }
                    }
                });
            }
        });
        dispose(this.observeLoginEventDisposable);
        this.observeLoginEventDisposable = observableCreate.buffer(500L, TimeUnit.MILLISECONDS).flatMap(new Function<List<String>, ObservableSource<String>>() { // from class: com.plv.livescenes.feature.liveinfo.PLVLiveInfoDataSource.6
            @Override // io.reactivex.functions.Function
            public ObservableSource<String> apply(List<String> list) throws Exception {
                return Observable.fromIterable(list);
            }
        }).map(new Function<String, PLVLoginEvent>() { // from class: com.plv.livescenes.feature.liveinfo.PLVLiveInfoDataSource.5
            @Override // io.reactivex.functions.Function
            public PLVLoginEvent apply(String str) throws Exception {
                return (PLVLoginEvent) PLVGsonUtil.fromJson(PLVLoginEvent.class, str);
            }
        }).observeOn(AndroidSchedulers.mainThread()).doOnNext(new Consumer<PLVLoginEvent>() { // from class: com.plv.livescenes.feature.liveinfo.PLVLiveInfoDataSource.4
            @Override // io.reactivex.functions.Consumer
            public void accept(PLVLoginEvent pLVLoginEvent) throws Exception {
                Action action2 = action;
                if (action2 != null) {
                    action2.run();
                }
                if (pLVLoginEvent == null || !PLVLiveInfoDataSource.this.viewerId.equals(pLVLoginEvent.getUser().getUserId())) {
                    return;
                }
                PLVLiveInfoDataSource pLVLiveInfoDataSource = PLVLiveInfoDataSource.this;
                pLVLiveInfoDataSource.dispose(pLVLiveInfoDataSource.increaseViewerTimer);
                PLVLiveInfoDataSource.access$308(PLVLiveInfoDataSource.this);
                PLVLiveInfoDataSource.this.increaseViewerTimer = PLVRxTimer.delay(5000L, new Consumer<Object>() { // from class: com.plv.livescenes.feature.liveinfo.PLVLiveInfoDataSource.4.1
                    @Override // io.reactivex.functions.Consumer
                    public void accept(Object obj) throws Exception {
                        PLVLiveInfoDataSource.this.increasePageViewer();
                    }
                });
            }
        }).subscribe(new Consumer<PLVLoginEvent>() { // from class: com.plv.livescenes.feature.liveinfo.PLVLiveInfoDataSource.2
            @Override // io.reactivex.functions.Consumer
            public void accept(PLVLoginEvent pLVLoginEvent) throws Exception {
                PLVCommonLog.d(PLVLiveInfoDataSource.TAG, "loginEvent:" + pLVLoginEvent.toString());
            }
        }, new Consumer<Throwable>() { // from class: com.plv.livescenes.feature.liveinfo.PLVLiveInfoDataSource.3
            @Override // io.reactivex.functions.Consumer
            public void accept(Throwable th) throws Exception {
                PLVCommonLog.e(PLVLiveInfoDataSource.TAG, "observePageViewer:" + th.getMessage());
            }
        });
    }

    public void setPageViewerRequestInterval(int i2) {
        this.pageViewerRequestInterval = i2;
    }
}
