package com.easefun.polyv.livecommon.module.data;

import com.easefun.polyv.livecommon.module.config.PLVLiveChannelConfig;
import com.easefun.polyv.livecommon.module.data.PLVLiveRoomDataManager;
import com.easefun.polyv.livescenes.chatroom.PolyvChatApiRequestHelper;
import com.easefun.polyv.livescenes.config.PolyvLiveSDKClient;
import com.easefun.polyv.livescenes.model.PolyvChatFunctionSwitchVO;
import com.easefun.polyv.livescenes.model.PolyvLiveClassDetailVO;
import com.easefun.polyv.livescenes.net.PolyvApiManager;
import com.google.android.exoplayer2.C;
import com.plv.foundationsdk.log.PLVCommonLog;
import com.plv.foundationsdk.net.PLVResponseBean;
import com.plv.foundationsdk.net.PLVResponseExcutor;
import com.plv.foundationsdk.net.PLVrResponseCallback;
import com.plv.foundationsdk.rx.PLVRxBaseRetryFunction;
import com.plv.foundationsdk.rx.PLVRxBaseTransformer;
import com.plv.foundationsdk.utils.PLVFormatUtils;
import com.plv.livescenes.hiclass.PLVHiClassDataBean;
import com.plv.livescenes.hiclass.api.PLVHCApiManager;
import com.plv.livescenes.hiclass.vo.PLVHCLessonDetailVO;
import com.plv.livescenes.model.PLVIncreasePageViewerVO;
import com.plv.livescenes.model.PLVLiveStatusVO2;
import com.plv.livescenes.model.PLVPlaybackChannelDetailVO;
import com.plv.livescenes.model.commodity.saas.PLVCommodityVO2;
import com.plv.livescenes.net.PLVApiManager;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import okhttp3.ResponseBody;
import org.json.JSONObject;
import retrofit2.HttpException;

/* loaded from: classes3.dex */
public class PLVLiveRoomDataRequester {
    public static final int GET_COMMODITY_COUNT = 20;
    private static final String TAG = "PLVLiveRoomDataRequeste";
    private Disposable channelDetailDisposable;
    private Disposable channelSwitchDisposable;
    private int commodityRank = -1;
    private Disposable getLiveStatusDisposable;
    private Disposable lessonDetailDisposable;
    private PLVLiveChannelConfig liveChannelConfig;
    private Disposable pageViewerDisposable;
    private Disposable playbackChannelDetail;
    private Disposable productListDisposable;
    private Disposable updateChannelNameDisposable;

    public interface IPLVNetRequestListener<T> {
        void onFailed(String msg, Throwable throwable);

        void onSuccess(T t2);
    }

    public PLVLiveRoomDataRequester(PLVLiveChannelConfig config) {
        this.liveChannelConfig = config;
    }

    private PLVLiveChannelConfig getConfig() {
        return this.liveChannelConfig;
    }

    public static String getErrorMessage(Throwable t2) {
        String message = t2.getMessage();
        if (!(t2 instanceof HttpException)) {
            return message;
        }
        try {
            return ((HttpException) t2).response().errorBody().string();
        } catch (Exception e2) {
            PLVCommonLog.d(TAG, "getErrorMessage：" + e2.getMessage());
            return message;
        }
    }

    public void destroy() {
        disposablePageViewer();
        disposeChannelDetail();
        disposeProductList();
        disposeChannelSwitch();
        disposeGetLiveStatus();
        disposeUpdateChannelName();
        disposeLessonDetail();
        disposePlayBackChannelDetail();
    }

    public void disposablePageViewer() {
        Disposable disposable = this.pageViewerDisposable;
        if (disposable != null) {
            disposable.dispose();
        }
    }

    public void disposeChannelDetail() {
        Disposable disposable = this.channelDetailDisposable;
        if (disposable != null) {
            disposable.dispose();
        }
    }

    public void disposeChannelSwitch() {
        Disposable disposable = this.channelSwitchDisposable;
        if (disposable != null) {
            disposable.dispose();
        }
    }

    public void disposeGetLiveStatus() {
        Disposable disposable = this.getLiveStatusDisposable;
        if (disposable != null) {
            disposable.dispose();
        }
    }

    public void disposeLessonDetail() {
        Disposable disposable = this.lessonDetailDisposable;
        if (disposable != null) {
            disposable.dispose();
        }
    }

    public void disposePlayBackChannelDetail() {
        Disposable disposable = this.playbackChannelDetail;
        if (disposable != null) {
            disposable.dispose();
        }
    }

    public void disposeProductList() {
        Disposable disposable = this.productListDisposable;
        if (disposable != null) {
            disposable.dispose();
        }
    }

    public void disposeUpdateChannelName() {
        Disposable disposable = this.updateChannelNameDisposable;
        if (disposable != null) {
            disposable.dispose();
        }
    }

    public int getCommodityRank() {
        return this.commodityRank;
    }

    public void requestChannelDetail(final IPLVNetRequestListener<PolyvLiveClassDetailVO> listener) {
        disposeChannelDetail();
        String channelId = getConfig().getChannelId();
        String appSecret = PolyvLiveSDKClient.getInstance().getAppSecret();
        this.channelDetailDisposable = PolyvChatApiRequestHelper.getInstance().requestLiveClassDetailApi(channelId, PolyvLiveSDKClient.getInstance().getAppId(), appSecret).retryWhen(new PLVRxBaseRetryFunction(3, C.DEFAULT_MAX_SEEK_TO_PREVIOUS_POSITION_MS)).compose(new PLVRxBaseTransformer()).subscribe(new Consumer<PolyvLiveClassDetailVO>() { // from class: com.easefun.polyv.livecommon.module.data.PLVLiveRoomDataRequester.2
            @Override // io.reactivex.functions.Consumer
            public void accept(PolyvLiveClassDetailVO liveClassDetailVO) throws Exception {
                IPLVNetRequestListener iPLVNetRequestListener = listener;
                if (iPLVNetRequestListener != null) {
                    iPLVNetRequestListener.onSuccess(liveClassDetailVO);
                }
            }
        }, new Consumer<Throwable>() { // from class: com.easefun.polyv.livecommon.module.data.PLVLiveRoomDataRequester.3
            @Override // io.reactivex.functions.Consumer
            public void accept(Throwable throwable) throws Exception {
                IPLVNetRequestListener iPLVNetRequestListener = listener;
                if (iPLVNetRequestListener != null) {
                    iPLVNetRequestListener.onFailed(PLVLiveRoomDataRequester.getErrorMessage(throwable), throwable);
                }
            }
        });
    }

    public void requestChannelSwitch(final IPLVNetRequestListener<PolyvChatFunctionSwitchVO> listener) {
        disposeChannelSwitch();
        this.channelSwitchDisposable = PolyvChatApiRequestHelper.getInstance().requestFunctionSwitch(getConfig().getChannelId()).retryWhen(new PLVRxBaseRetryFunction(3, C.DEFAULT_MAX_SEEK_TO_PREVIOUS_POSITION_MS)).compose(new PLVRxBaseTransformer()).subscribe(new Consumer<PolyvChatFunctionSwitchVO>() { // from class: com.easefun.polyv.livecommon.module.data.PLVLiveRoomDataRequester.4
            @Override // io.reactivex.functions.Consumer
            public void accept(PolyvChatFunctionSwitchVO polyvChatFunctionSwitchVO) throws Exception {
                IPLVNetRequestListener iPLVNetRequestListener = listener;
                if (iPLVNetRequestListener != null) {
                    iPLVNetRequestListener.onSuccess(polyvChatFunctionSwitchVO);
                }
            }
        }, new Consumer<Throwable>() { // from class: com.easefun.polyv.livecommon.module.data.PLVLiveRoomDataRequester.5
            @Override // io.reactivex.functions.Consumer
            public void accept(Throwable throwable) throws Exception {
                IPLVNetRequestListener iPLVNetRequestListener = listener;
                if (iPLVNetRequestListener != null) {
                    iPLVNetRequestListener.onFailed(PLVLiveRoomDataRequester.getErrorMessage(throwable), throwable);
                }
            }
        });
    }

    public void requestLessonDetail(final IPLVNetRequestListener<PLVHiClassDataBean> listener) {
        disposeLessonDetail();
        this.lessonDetailDisposable = PLVHCApiManager.getInstance().getLessonDetail("teacher".equals(getConfig().getUser().getViewerType()), getConfig().getHiClassConfig().getCourseCode(), getConfig().getHiClassConfig().getLessonId(), getConfig().getHiClassConfig().getToken()).retryWhen(new PLVRxBaseRetryFunction(3, C.DEFAULT_MAX_SEEK_TO_PREVIOUS_POSITION_MS)).compose(new PLVRxBaseTransformer()).subscribe(new Consumer<PLVHCLessonDetailVO>() { // from class: com.easefun.polyv.livecommon.module.data.PLVLiveRoomDataRequester.11
            @Override // io.reactivex.functions.Consumer
            public void accept(PLVHCLessonDetailVO plvhcLessonDetailVO) throws Exception {
                if (plvhcLessonDetailVO.isSuccess() == null || !plvhcLessonDetailVO.isSuccess().booleanValue() || plvhcLessonDetailVO.getData() == null) {
                    throw new Exception(plvhcLessonDetailVO.getError().getDesc() + "-" + plvhcLessonDetailVO.getError().getCode());
                }
                IPLVNetRequestListener iPLVNetRequestListener = listener;
                if (iPLVNetRequestListener != null) {
                    iPLVNetRequestListener.onSuccess(plvhcLessonDetailVO.getData());
                }
            }
        }, new Consumer<Throwable>() { // from class: com.easefun.polyv.livecommon.module.data.PLVLiveRoomDataRequester.12
            @Override // io.reactivex.functions.Consumer
            public void accept(Throwable throwable) throws Exception {
                IPLVNetRequestListener iPLVNetRequestListener = listener;
                if (iPLVNetRequestListener != null) {
                    iPLVNetRequestListener.onFailed(PLVLiveRoomDataRequester.getErrorMessage(throwable), throwable);
                }
            }
        });
    }

    public void requestLiveStatus(final IPLVNetRequestListener<PLVLiveRoomDataManager.LiveStatus> listener) {
        disposeGetLiveStatus();
        String channelId = getConfig().getChannelId();
        String appId = getConfig().getAccount().getAppId();
        String appSecret = getConfig().getAccount().getAppSecret();
        long jCurrentTimeMillis = System.currentTimeMillis();
        this.getLiveStatusDisposable = PLVResponseExcutor.excuteUndefinData(PolyvApiManager.getPolyvLiveStatusApi().getLiveStatusJson3(channelId, jCurrentTimeMillis + "", appId, appSecret), new PLVrResponseCallback<PLVLiveStatusVO2>() { // from class: com.easefun.polyv.livecommon.module.data.PLVLiveRoomDataRequester.8
            @Override // com.plv.foundationsdk.net.PLVrResponseCallback
            public void onError(Throwable throwable) {
                super.onError(throwable);
                IPLVNetRequestListener iPLVNetRequestListener = listener;
                if (iPLVNetRequestListener != null) {
                    iPLVNetRequestListener.onFailed(PLVLiveRoomDataRequester.getErrorMessage(throwable), throwable);
                }
            }

            @Override // com.plv.foundationsdk.net.PLVrResponseCallback
            public void onFailure(PLVResponseBean<PLVLiveStatusVO2> polyvResponseBean) {
                super.onFailure(polyvResponseBean);
                if (listener != null) {
                    String string = this.responseBean.toString();
                    listener.onFailed(string, new Throwable(string));
                }
            }

            @Override // com.plv.foundationsdk.net.PLVrResponseCallback
            public void onFinish() {
                PLVCommonLog.d(PLVLiveRoomDataRequester.TAG, "getLiveStatusJson2 onFinish");
            }

            @Override // com.plv.foundationsdk.net.PLVrResponseCallback
            public void onSuccess(PLVLiveStatusVO2 statusVO) {
                if (statusVO == null || statusVO.getCode() != 200) {
                    return;
                }
                String str = statusVO.getData().split(",")[0];
                PLVLiveRoomDataManager.LiveStatus liveStatus = PLVLiveRoomDataManager.LiveStatus.LIVE;
                if (!liveStatus.getValue().equals(str)) {
                    liveStatus = PLVLiveRoomDataManager.LiveStatus.STOP;
                    if (!liveStatus.getValue().equals(str)) {
                        liveStatus = PLVLiveRoomDataManager.LiveStatus.END;
                        if (!liveStatus.getValue().equals(str)) {
                            liveStatus = null;
                        }
                    }
                }
                IPLVNetRequestListener iPLVNetRequestListener = listener;
                if (iPLVNetRequestListener != null) {
                    iPLVNetRequestListener.onSuccess(liveStatus);
                }
            }
        });
    }

    public void requestPageViewer(final IPLVNetRequestListener<Integer> listener) {
        disposablePageViewer();
        String appId = getConfig().getAccount().getAppId();
        String appSecret = getConfig().getAccount().getAppSecret();
        String channelId = getConfig().getChannelId();
        this.pageViewerDisposable = PLVResponseExcutor.excuteUndefinData(PolyvApiManager.getPolyvLiveStatusApi().increasePageViewer2(PLVFormatUtils.parseInt(channelId), appId, System.currentTimeMillis(), appSecret, 1), new PLVrResponseCallback<PLVIncreasePageViewerVO>() { // from class: com.easefun.polyv.livecommon.module.data.PLVLiveRoomDataRequester.1
            @Override // com.plv.foundationsdk.net.PLVrResponseCallback
            public void onError(Throwable throwable) {
                super.onError(throwable);
                IPLVNetRequestListener iPLVNetRequestListener = listener;
                if (iPLVNetRequestListener != null) {
                    iPLVNetRequestListener.onFailed(PLVLiveRoomDataRequester.getErrorMessage(throwable), throwable);
                }
            }

            @Override // com.plv.foundationsdk.net.PLVrResponseCallback
            public void onFailure(PLVResponseBean<PLVIncreasePageViewerVO> PLVResponseBean) {
                super.onFailure(PLVResponseBean);
                if (listener != null) {
                    String string = this.responseBean.toString();
                    listener.onFailed(string, new Throwable(string));
                }
            }

            @Override // com.plv.foundationsdk.net.PLVrResponseCallback
            public void onFinish() {
                PLVCommonLog.d(PLVLiveRoomDataRequester.TAG, "increasePageViewer onFinish");
            }

            @Override // com.plv.foundationsdk.net.PLVrResponseCallback
            public void onSuccess(PLVIncreasePageViewerVO vo) {
                IPLVNetRequestListener iPLVNetRequestListener = listener;
                if (iPLVNetRequestListener != null) {
                    iPLVNetRequestListener.onSuccess(vo.getData());
                }
            }
        });
    }

    public void requestPlaybackChannelDetail(final IPLVNetRequestListener<PLVPlaybackChannelDetailVO> listener) {
        disposePlayBackChannelDetail();
        this.playbackChannelDetail = PLVApiManager.getPlvChannelStatusApi().getPlaybackChannelDetail(getConfig().getChannelId(), String.valueOf(System.currentTimeMillis())).compose(new PLVRxBaseTransformer()).subscribe(new Consumer<PLVPlaybackChannelDetailVO>() { // from class: com.easefun.polyv.livecommon.module.data.PLVLiveRoomDataRequester.13
            @Override // io.reactivex.functions.Consumer
            public void accept(PLVPlaybackChannelDetailVO detailVO) throws Exception {
                if (listener != null) {
                    if (detailVO.getData() == null) {
                        String message = detailVO.getMessage();
                        listener.onFailed(message, new Throwable(message));
                    }
                    listener.onSuccess(detailVO);
                }
            }
        }, new Consumer<Throwable>() { // from class: com.easefun.polyv.livecommon.module.data.PLVLiveRoomDataRequester.14
            @Override // io.reactivex.functions.Consumer
            public void accept(Throwable throwable) throws Exception {
                IPLVNetRequestListener iPLVNetRequestListener = listener;
                if (iPLVNetRequestListener != null) {
                    iPLVNetRequestListener.onFailed(PLVLiveRoomDataRequester.getErrorMessage(throwable), throwable);
                }
            }
        });
    }

    public void requestProductList(final IPLVNetRequestListener<PLVCommodityVO2> listener) {
        requestProductList(-1, listener);
    }

    public void requestUpdateChannelName(final IPLVNetRequestListener<String> listener) {
        disposeUpdateChannelName();
        String channelId = getConfig().getChannelId();
        final String channelName = getConfig().getChannelName();
        this.updateChannelNameDisposable = PolyvApiManager.getPolyvLiveStatusApi().updateChannelSetting(channelId, System.currentTimeMillis(), PolyvLiveSDKClient.getInstance().getAppId(), channelName, PolyvLiveSDKClient.getInstance().getAppSecret()).compose(new PLVRxBaseTransformer()).subscribe(new Consumer<ResponseBody>() { // from class: com.easefun.polyv.livecommon.module.data.PLVLiveRoomDataRequester.9
            @Override // io.reactivex.functions.Consumer
            public void accept(ResponseBody responseBody) throws Exception {
                JSONObject jSONObject = new JSONObject(responseBody.string());
                if ("success".equals(jSONObject.optString("status"))) {
                    IPLVNetRequestListener iPLVNetRequestListener = listener;
                    if (iPLVNetRequestListener != null) {
                        iPLVNetRequestListener.onSuccess(channelName);
                        return;
                    }
                    return;
                }
                if (listener != null) {
                    String strOptString = jSONObject.optString("message");
                    listener.onFailed(strOptString, new Throwable(strOptString));
                }
            }
        }, new Consumer<Throwable>() { // from class: com.easefun.polyv.livecommon.module.data.PLVLiveRoomDataRequester.10
            @Override // io.reactivex.functions.Consumer
            public void accept(Throwable throwable) throws Exception {
                IPLVNetRequestListener iPLVNetRequestListener = listener;
                if (iPLVNetRequestListener != null) {
                    iPLVNetRequestListener.onFailed(PLVLiveRoomDataRequester.getErrorMessage(throwable), throwable);
                }
            }
        });
    }

    public void requestProductList(int rank, final IPLVNetRequestListener<PLVCommodityVO2> listener) {
        this.commodityRank = rank;
        disposeProductList();
        String channelId = getConfig().getChannelId();
        String appId = getConfig().getAccount().getAppId();
        String appSecret = getConfig().getAccount().getAppSecret();
        long jCurrentTimeMillis = System.currentTimeMillis();
        this.productListDisposable = (rank > -1 ? PolyvApiManager.getPolyvLiveStatusApi().getProductList2(channelId, appId, jCurrentTimeMillis, 20, rank, appSecret) : PolyvApiManager.getPolyvLiveStatusApi().getProductList2(channelId, appId, jCurrentTimeMillis, 20, appSecret)).retryWhen(new PLVRxBaseRetryFunction(3, C.DEFAULT_MAX_SEEK_TO_PREVIOUS_POSITION_MS)).compose(new PLVRxBaseTransformer()).subscribe(new Consumer<PLVCommodityVO2>() { // from class: com.easefun.polyv.livecommon.module.data.PLVLiveRoomDataRequester.6
            @Override // io.reactivex.functions.Consumer
            public void accept(PLVCommodityVO2 polyvCommodityVO) throws Exception {
                IPLVNetRequestListener iPLVNetRequestListener = listener;
                if (iPLVNetRequestListener != null) {
                    iPLVNetRequestListener.onSuccess(polyvCommodityVO);
                }
            }
        }, new Consumer<Throwable>() { // from class: com.easefun.polyv.livecommon.module.data.PLVLiveRoomDataRequester.7
            @Override // io.reactivex.functions.Consumer
            public void accept(Throwable throwable) throws Exception {
                IPLVNetRequestListener iPLVNetRequestListener = listener;
                if (iPLVNetRequestListener != null) {
                    iPLVNetRequestListener.onFailed(PLVLiveRoomDataRequester.getErrorMessage(throwable), throwable);
                }
            }
        });
    }
}
