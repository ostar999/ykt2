package com.plv.socket.net;

import android.util.Pair;
import com.google.android.exoplayer2.C;
import com.plv.foundationsdk.log.PLVCommonLog;
import com.plv.foundationsdk.rx.PLVRxBaseRetryFunction;
import com.plv.foundationsdk.rx.PLVRxBaseTransformer;
import com.plv.foundationsdk.rx.PLVRxEncryptDataFunction;
import com.plv.foundationsdk.sign.PLVSignCreator;
import com.plv.foundationsdk.utils.PLVCheckUtils;
import com.plv.socket.net.api.PLVSocketApiManager;
import com.plv.socket.net.model.PLVHiClassChatTokenVO;
import com.plv.socket.net.model.PLVNewChatTokenVO;
import com.plv.socket.socketio.PLVSocketIOClient;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import okhttp3.ResponseBody;

/* loaded from: classes5.dex */
public class PLVSocketApiHelper {
    private static final String ANDROID_SDK = "android-SDK";
    private static final String APP_ID = "appId";
    private static final String CHANNEL_ID = "channelId";
    private static final String ORIGIN = "origin";
    private static final String ROLE = "role";
    private static final String TAG = "PLVSocketApiHelper";
    private static final String TIMESTAMP = "timestamp";
    private static final String USER_ID = "userId";
    private Disposable getHiClassChatTokenDisposable;
    private Disposable getTokenDisposable;
    private Disposable heartBeatDisposable;

    /* JADX INFO: Access modifiers changed from: private */
    public String parseTokenData(PLVNewChatTokenVO pLVNewChatTokenVO) throws Exception {
        return pLVNewChatTokenVO.getData().getToken();
    }

    public void dispose() {
        disposeChatTokenApi();
        disposeHeartBeatApi();
        disposeHiClassChatTokenApi();
    }

    public void disposeChatTokenApi() {
        Disposable disposable = this.getTokenDisposable;
        if (disposable != null) {
            disposable.dispose();
        }
    }

    public void disposeHeartBeatApi() {
        Disposable disposable = this.heartBeatDisposable;
        if (disposable != null) {
            disposable.dispose();
        }
    }

    public void disposeHiClassChatTokenApi() {
        Disposable disposable = this.getHiClassChatTokenDisposable;
        if (disposable != null) {
            disposable.dispose();
        }
    }

    public void getChatToken(String str, String str2, String str3, Consumer<PLVNewChatTokenVO> consumer, Consumer<Throwable> consumer2) {
        String accountAppId = PLVSocketIOClient.getInstance().getAccountAppId();
        String accountAppSecret = PLVSocketIOClient.getInstance().getAccountAppSecret();
        long jCurrentTimeMillis = System.currentTimeMillis();
        HashMap map = new HashMap();
        map.put("appId", accountAppId);
        map.put("origin", ANDROID_SDK);
        map.put(ROLE, str3);
        map.put("timestamp", jCurrentTimeMillis + "");
        map.put("userId", str);
        map.put("channelId", str2);
        String[] strArrCreateSignWithSignatureNonceEncrypt = PLVSignCreator.createSignWithSignatureNonceEncrypt(accountAppSecret, map);
        disposeChatTokenApi();
        this.getTokenDisposable = PLVSocketApiManager.getSocketApi().getChatToken(str2, accountAppId, jCurrentTimeMillis + "", strArrCreateSignWithSignatureNonceEncrypt[0], PLVSignCreator.getSignatureMethod(), str3, str, ANDROID_SDK, strArrCreateSignWithSignatureNonceEncrypt[1], strArrCreateSignWithSignatureNonceEncrypt[2]).map(new PLVRxEncryptDataFunction<PLVNewChatTokenVO>(PLVNewChatTokenVO.DataBean.class) { // from class: com.plv.socket.net.PLVSocketApiHelper.3
            @Override // com.plv.foundationsdk.rx.PLVRxEncryptDataFunction
            public Pair<Object, Boolean> accept(PLVNewChatTokenVO pLVNewChatTokenVO) {
                return new Pair<>(pLVNewChatTokenVO.getDataObj(), Boolean.valueOf(pLVNewChatTokenVO.isEncryption()));
            }
        }).retryWhen(new PLVRxBaseRetryFunction(Integer.MAX_VALUE, C.DEFAULT_MAX_SEEK_TO_PREVIOUS_POSITION_MS)).flatMap(new Function<PLVNewChatTokenVO, ObservableSource<PLVNewChatTokenVO>>() { // from class: com.plv.socket.net.PLVSocketApiHelper.2
            @Override // io.reactivex.functions.Function
            public ObservableSource<PLVNewChatTokenVO> apply(PLVNewChatTokenVO pLVNewChatTokenVO) throws Exception {
                PLVCheckUtils.checkCodeThrow(pLVNewChatTokenVO.getCode(), pLVNewChatTokenVO.getMessage());
                return Observable.just(pLVNewChatTokenVO);
            }
        }).doOnNext(new Consumer<PLVNewChatTokenVO>() { // from class: com.plv.socket.net.PLVSocketApiHelper.1
            @Override // io.reactivex.functions.Consumer
            public void accept(PLVNewChatTokenVO pLVNewChatTokenVO) throws Exception {
                PLVSocketApiHelper.this.parseTokenData(pLVNewChatTokenVO);
            }
        }).compose(new PLVRxBaseTransformer()).subscribe(consumer, consumer2);
    }

    public void getHiClassChatToken(final Consumer<PLVHiClassChatTokenVO> consumer, Consumer<Throwable> consumer2) {
        disposeHiClassChatTokenApi();
        if (PLVSocketIOClient.getInstance().isTeacherType()) {
            this.getHiClassChatTokenDisposable = PLVSocketApiManager.getHiClassApi().getTeachChatToken(PLVSocketIOClient.getInstance().getLessonId(), PLVSocketIOClient.getInstance().getToken()).retryWhen(new PLVRxBaseRetryFunction(Integer.MAX_VALUE, C.DEFAULT_MAX_SEEK_TO_PREVIOUS_POSITION_MS)).compose(new PLVRxBaseTransformer()).subscribe(new Consumer<PLVHiClassChatTokenVO>() { // from class: com.plv.socket.net.PLVSocketApiHelper.4
                @Override // io.reactivex.functions.Consumer
                public void accept(PLVHiClassChatTokenVO pLVHiClassChatTokenVO) throws Exception {
                    if (pLVHiClassChatTokenVO.isSuccess().booleanValue()) {
                        consumer.accept(pLVHiClassChatTokenVO);
                        return;
                    }
                    throw new Exception(pLVHiClassChatTokenVO.getError().getDesc() + "-" + pLVHiClassChatTokenVO.getError().getCode());
                }
            }, consumer2);
        } else {
            this.getHiClassChatTokenDisposable = PLVSocketApiManager.getHiClassApi().getWatchChatToken(PLVSocketIOClient.getInstance().getCourseCode(), PLVSocketIOClient.getInstance().getLessonId(), PLVSocketIOClient.getInstance().getToken()).retryWhen(new PLVRxBaseRetryFunction(Integer.MAX_VALUE, C.DEFAULT_MAX_SEEK_TO_PREVIOUS_POSITION_MS)).compose(new PLVRxBaseTransformer()).subscribe(consumer, consumer2);
        }
    }

    public void requestHeartBeat(final String str) {
        disposeHeartBeatApi();
        this.heartBeatDisposable = Observable.interval(0L, 60L, TimeUnit.SECONDS, Schedulers.io()).flatMap(new Function<Long, ObservableSource<ResponseBody>>() { // from class: com.plv.socket.net.PLVSocketApiHelper.7
            @Override // io.reactivex.functions.Function
            public ObservableSource<ResponseBody> apply(Long l2) throws Exception {
                return PLVSocketApiManager.getApiChatApi().requestHeartbeat(str).retry(1L);
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<ResponseBody>() { // from class: com.plv.socket.net.PLVSocketApiHelper.5
            @Override // io.reactivex.functions.Consumer
            public void accept(ResponseBody responseBody) throws Exception {
                PLVCommonLog.d(PLVSocketApiHelper.TAG, "accept: heartBeatDisposable:" + responseBody.toString());
            }
        }, new Consumer<Throwable>() { // from class: com.plv.socket.net.PLVSocketApiHelper.6
            @Override // io.reactivex.functions.Consumer
            public void accept(Throwable th) throws Exception {
                PLVCommonLog.d(PLVSocketApiHelper.TAG, "accept: heartBeatDisposable throwable:" + th.getMessage());
            }
        });
    }
}
