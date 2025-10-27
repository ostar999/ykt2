package com.easefun.polyv.livecommon.module.data;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.easefun.polyv.livecommon.module.config.PLVLiveChannelConfig;
import com.easefun.polyv.livecommon.module.config.PLVLiveChannelConfigFiller;
import com.easefun.polyv.livecommon.module.data.PLVLiveRoomDataRequester;
import com.easefun.polyv.livescenes.model.PolyvChatFunctionSwitchVO;
import com.easefun.polyv.livescenes.model.PolyvLiveClassDetailVO;
import com.plv.livescenes.hiclass.PLVHiClassDataBean;
import com.plv.livescenes.model.PLVPlaybackChannelDetailVO;
import com.plv.livescenes.model.commodity.saas.PLVCommodityVO2;
import com.plv.livescenes.model.interact.PLVWebviewUpdateAppStatusVO;
import com.plv.livescenes.streamer.transfer.PLVStreamerInnerDataTransfer;
import com.plv.socket.event.chat.PLVRewardEvent;
import com.plv.socket.event.sclass.PLVInLiveAckResult;

/* loaded from: classes3.dex */
public class PLVLiveRoomDataManager implements IPLVLiveRoomDataManager {
    private boolean isRequestedPageViewer;
    private boolean isSupportRTC;
    private PLVLiveChannelConfig liveChannelConfig;
    private PLVLiveRoomDataRequester liveRoomDataRequester;
    private String sessionId;
    private MutableLiveData<PLVStatefulData<Integer>> pageViewerData = new MutableLiveData<>();
    private MutableLiveData<PLVStatefulData<PolyvLiveClassDetailVO>> classDetailVO = new MutableLiveData<>();
    private MutableLiveData<PLVStatefulData<PolyvChatFunctionSwitchVO>> functionSwitchVO = new MutableLiveData<>();
    private MutableLiveData<PLVStatefulData<PLVCommodityVO2>> commodityVO = new MutableLiveData<>();
    private MutableLiveData<PLVStatefulData<LiveStatus>> liveStatusData = new MutableLiveData<>();
    private MutableLiveData<PLVStatefulData<Boolean>> pointRewardEnableData = new MutableLiveData<>();
    private MutableLiveData<PLVRewardEvent> pointRewardEventData = new MutableLiveData<>();
    private MutableLiveData<PLVStatefulData<String>> channelNameData = new MutableLiveData<>();
    private MutableLiveData<PLVWebviewUpdateAppStatusVO> interactStatusData = new MutableLiveData<>();
    private MutableLiveData<PLVStatefulData<PLVHiClassDataBean>> fulClassDataBean = new MutableLiveData<>();
    private MutableLiveData<PLVHiClassDataBean> classDataBean = new MutableLiveData<>();
    private MutableLiveData<Boolean> isOnlyAudio = new MutableLiveData<>();
    private MutableLiveData<PLVStatefulData<PLVPlaybackChannelDetailVO>> playbackChannelDetailVO = new MutableLiveData<>();
    private MutableLiveData<String> sessionIdLiveData = new MutableLiveData<>();

    public enum LiveStatus {
        LIVE(PLVInLiveAckResult.STATUS_LIVE),
        STOP("stop"),
        END("end");

        private String value;

        LiveStatus(String value) {
            this.value = value;
        }

        public String getValue() {
            return this.value;
        }
    }

    public PLVLiveRoomDataManager(@NonNull PLVLiveChannelConfig config) {
        this.liveChannelConfig = config;
        this.liveRoomDataRequester = new PLVLiveRoomDataRequester(config);
        this.isOnlyAudio.setValue(Boolean.valueOf(PLVStreamerInnerDataTransfer.getInstance().isOnlyAudio()));
    }

    @Override // com.easefun.polyv.livecommon.module.data.IPLVLiveRoomDataManager
    public void destroy() {
        this.liveRoomDataRequester.destroy();
    }

    @Override // com.easefun.polyv.livecommon.module.data.IPLVLiveRoomDataManager
    public int getCommodityRank() {
        return this.liveRoomDataRequester.getCommodityRank();
    }

    @Override // com.easefun.polyv.livecommon.module.data.IPLVLiveRoomDataManager
    @NonNull
    public PLVLiveChannelConfig getConfig() {
        return this.liveChannelConfig;
    }

    @Override // com.easefun.polyv.livecommon.module.data.IPLVLiveRoomDataManager
    public LiveData<PLVHiClassDataBean> getHiClassDataBean() {
        return this.classDataBean;
    }

    @Override // com.easefun.polyv.livecommon.module.data.IPLVLiveRoomDataManager
    public MutableLiveData<PLVWebviewUpdateAppStatusVO> getInteractStatusData() {
        return this.interactStatusData;
    }

    @Override // com.easefun.polyv.livecommon.module.data.IPLVLiveRoomDataManager
    public LiveData<Boolean> getIsOnlyAudioEnabled() {
        return this.isOnlyAudio;
    }

    @Override // com.easefun.polyv.livecommon.module.data.IPLVLiveRoomDataManager
    public LiveData<PLVStatefulData<PLVPlaybackChannelDetailVO>> getPlaybackChannelData() {
        return this.playbackChannelDetailVO;
    }

    @Override // com.easefun.polyv.livecommon.module.data.IPLVLiveRoomDataManager
    public MutableLiveData<PLVStatefulData<Boolean>> getPointRewardEnableData() {
        return this.pointRewardEnableData;
    }

    @Override // com.easefun.polyv.livecommon.module.data.IPLVLiveRoomDataManager
    public MutableLiveData<PLVRewardEvent> getRewardEventData() {
        return this.pointRewardEventData;
    }

    @Override // com.easefun.polyv.livecommon.module.data.IPLVLiveRoomDataManager
    public String getSessionId() {
        return this.sessionId;
    }

    @Override // com.easefun.polyv.livecommon.module.data.IPLVLiveRoomDataManager
    public boolean isNeedStreamRecover() {
        return this.liveChannelConfig.isLiveStreamingWhenLogin();
    }

    @Override // com.easefun.polyv.livecommon.module.data.IPLVLiveRoomDataManager
    public boolean isOnlyAudio() {
        if (this.isOnlyAudio.getValue() == null) {
            return false;
        }
        return this.isOnlyAudio.getValue().booleanValue();
    }

    @Override // com.easefun.polyv.livecommon.module.data.IPLVLiveRoomDataManager
    public boolean isSupportRTC() {
        if (getConfig().isPPTChannelType()) {
            return true;
        }
        return this.isSupportRTC;
    }

    @Override // com.easefun.polyv.livecommon.module.data.IPLVLiveRoomDataManager
    public void requestChannelDetail() {
        this.liveRoomDataRequester.requestChannelDetail(new PLVLiveRoomDataRequester.IPLVNetRequestListener<PolyvLiveClassDetailVO>() { // from class: com.easefun.polyv.livecommon.module.data.PLVLiveRoomDataManager.3
            @Override // com.easefun.polyv.livecommon.module.data.PLVLiveRoomDataRequester.IPLVNetRequestListener
            public void onFailed(String msg, Throwable throwable) {
                PLVLiveRoomDataManager.this.classDetailVO.postValue(PLVStatefulData.error(msg, throwable));
            }

            @Override // com.easefun.polyv.livecommon.module.data.PLVLiveRoomDataRequester.IPLVNetRequestListener
            public void onSuccess(PolyvLiveClassDetailVO liveClassDetailVO) {
                if (!PLVLiveRoomDataManager.this.isRequestedPageViewer && liveClassDetailVO.getData() != null) {
                    liveClassDetailVO.getData().setPageView(liveClassDetailVO.getData().getPageView() + 1);
                }
                PLVLiveRoomDataManager.this.classDetailVO.postValue(PLVStatefulData.success(liveClassDetailVO));
            }
        });
    }

    @Override // com.easefun.polyv.livecommon.module.data.IPLVLiveRoomDataManager
    public void requestChannelSwitch() {
        this.liveRoomDataRequester.requestChannelSwitch(new PLVLiveRoomDataRequester.IPLVNetRequestListener<PolyvChatFunctionSwitchVO>() { // from class: com.easefun.polyv.livecommon.module.data.PLVLiveRoomDataManager.1
            @Override // com.easefun.polyv.livecommon.module.data.PLVLiveRoomDataRequester.IPLVNetRequestListener
            public void onFailed(String msg, Throwable throwable) {
                PLVLiveRoomDataManager.this.functionSwitchVO.postValue(PLVStatefulData.error(msg, throwable));
            }

            @Override // com.easefun.polyv.livecommon.module.data.PLVLiveRoomDataRequester.IPLVNetRequestListener
            public void onSuccess(PolyvChatFunctionSwitchVO polyvChatFunctionSwitchVO) {
                PLVLiveRoomDataManager.this.functionSwitchVO.postValue(PLVStatefulData.success(polyvChatFunctionSwitchVO));
            }
        });
    }

    @Override // com.easefun.polyv.livecommon.module.data.IPLVLiveRoomDataManager
    public void requestLessonDetail() {
        this.liveRoomDataRequester.requestLessonDetail(new PLVLiveRoomDataRequester.IPLVNetRequestListener<PLVHiClassDataBean>() { // from class: com.easefun.polyv.livecommon.module.data.PLVLiveRoomDataManager.7
            @Override // com.easefun.polyv.livecommon.module.data.PLVLiveRoomDataRequester.IPLVNetRequestListener
            public void onFailed(String msg, Throwable throwable) {
                PLVLiveRoomDataManager.this.fulClassDataBean.postValue(PLVStatefulData.error(msg, throwable));
                PLVLiveRoomDataManager.this.classDataBean.postValue(null);
            }

            @Override // com.easefun.polyv.livecommon.module.data.PLVLiveRoomDataRequester.IPLVNetRequestListener
            public void onSuccess(PLVHiClassDataBean hiClassDataBean) {
                PLVLiveRoomDataManager.this.fulClassDataBean.postValue(PLVStatefulData.success(hiClassDataBean));
                PLVLiveRoomDataManager.this.classDataBean.postValue(hiClassDataBean);
            }
        });
    }

    @Override // com.easefun.polyv.livecommon.module.data.IPLVLiveRoomDataManager
    public void requestLiveStatus() {
        this.liveRoomDataRequester.requestLiveStatus(new PLVLiveRoomDataRequester.IPLVNetRequestListener<LiveStatus>() { // from class: com.easefun.polyv.livecommon.module.data.PLVLiveRoomDataManager.5
            @Override // com.easefun.polyv.livecommon.module.data.PLVLiveRoomDataRequester.IPLVNetRequestListener
            public void onFailed(String msg, Throwable throwable) {
                PLVLiveRoomDataManager.this.liveStatusData.postValue(PLVStatefulData.error(msg, throwable));
            }

            @Override // com.easefun.polyv.livecommon.module.data.PLVLiveRoomDataRequester.IPLVNetRequestListener
            public void onSuccess(LiveStatus liveStatus) {
                PLVLiveRoomDataManager.this.liveStatusData.postValue(PLVStatefulData.success(liveStatus));
            }
        });
    }

    @Override // com.easefun.polyv.livecommon.module.data.IPLVLiveRoomDataManager
    public void requestPageViewer() {
        this.liveRoomDataRequester.requestPageViewer(new PLVLiveRoomDataRequester.IPLVNetRequestListener<Integer>() { // from class: com.easefun.polyv.livecommon.module.data.PLVLiveRoomDataManager.2
            @Override // com.easefun.polyv.livecommon.module.data.PLVLiveRoomDataRequester.IPLVNetRequestListener
            public void onFailed(String msg, Throwable throwable) {
                PLVLiveRoomDataManager.this.pageViewerData.postValue(PLVStatefulData.error(msg, throwable));
            }

            @Override // com.easefun.polyv.livecommon.module.data.PLVLiveRoomDataRequester.IPLVNetRequestListener
            public void onSuccess(Integer integer) {
                PLVLiveRoomDataManager.this.isRequestedPageViewer = true;
                PLVLiveRoomDataManager.this.pageViewerData.postValue(PLVStatefulData.success(integer));
            }
        });
    }

    @Override // com.easefun.polyv.livecommon.module.data.IPLVLiveRoomDataManager
    public void requestPlaybackChannelStatus() {
        this.liveRoomDataRequester.requestPlaybackChannelDetail(new PLVLiveRoomDataRequester.IPLVNetRequestListener<PLVPlaybackChannelDetailVO>() { // from class: com.easefun.polyv.livecommon.module.data.PLVLiveRoomDataManager.8
            @Override // com.easefun.polyv.livecommon.module.data.PLVLiveRoomDataRequester.IPLVNetRequestListener
            public void onFailed(String msg, Throwable throwable) {
                PLVLiveRoomDataManager.this.playbackChannelDetailVO.postValue(PLVStatefulData.error(msg, throwable));
            }

            @Override // com.easefun.polyv.livecommon.module.data.PLVLiveRoomDataRequester.IPLVNetRequestListener
            public void onSuccess(PLVPlaybackChannelDetailVO detailVO) {
                PLVLiveRoomDataManager.this.playbackChannelDetailVO.postValue(PLVStatefulData.success(detailVO));
            }
        });
    }

    @Override // com.easefun.polyv.livecommon.module.data.IPLVLiveRoomDataManager
    public void requestProductList() {
        requestProductList(-1);
    }

    @Override // com.easefun.polyv.livecommon.module.data.IPLVLiveRoomDataManager
    public void requestUpdateChannelName() {
        this.liveRoomDataRequester.requestUpdateChannelName(new PLVLiveRoomDataRequester.IPLVNetRequestListener<String>() { // from class: com.easefun.polyv.livecommon.module.data.PLVLiveRoomDataManager.6
            @Override // com.easefun.polyv.livecommon.module.data.PLVLiveRoomDataRequester.IPLVNetRequestListener
            public void onFailed(String msg, Throwable throwable) {
                PLVLiveRoomDataManager.this.channelNameData.postValue(PLVStatefulData.error(msg, throwable));
            }

            @Override // com.easefun.polyv.livecommon.module.data.PLVLiveRoomDataRequester.IPLVNetRequestListener
            public void onSuccess(String s2) {
                PLVLiveRoomDataManager.this.channelNameData.postValue(PLVStatefulData.success(s2));
            }
        });
    }

    @Override // com.easefun.polyv.livecommon.module.data.IPLVLiveRoomDataManager
    public void setConfigVid(String vid) {
        this.liveChannelConfig.setupVid(vid);
    }

    @Override // com.easefun.polyv.livecommon.module.data.IPLVLiveRoomDataManager
    public void setNeedStreamRecover(boolean isNeed) {
        this.liveChannelConfig.setLiveStreamingWhenLogin(isNeed);
        PLVLiveChannelConfigFiller.setLiveStreamingWhenLogin(isNeed);
    }

    @Override // com.easefun.polyv.livecommon.module.data.IPLVLiveRoomDataManager
    public void setOnlyAudio(boolean onlyAudio) {
        this.isOnlyAudio.postValue(Boolean.valueOf(onlyAudio));
    }

    @Override // com.easefun.polyv.livecommon.module.data.IPLVLiveRoomDataManager
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
        this.sessionIdLiveData.postValue(sessionId);
    }

    @Override // com.easefun.polyv.livecommon.module.data.IPLVLiveRoomDataManager
    public void setSupportRTC(boolean supportRTC) {
        this.isSupportRTC = supportRTC;
    }

    @Override // com.easefun.polyv.livecommon.module.data.IPLVLiveRoomDataManager
    public MutableLiveData<PLVStatefulData<PolyvLiveClassDetailVO>> getClassDetailVO() {
        return this.classDetailVO;
    }

    @Override // com.easefun.polyv.livecommon.module.data.IPLVLiveRoomDataManager
    public MutableLiveData<PLVStatefulData<PLVCommodityVO2>> getCommodityVO() {
        return this.commodityVO;
    }

    @Override // com.easefun.polyv.livecommon.module.data.IPLVLiveRoomDataManager
    public MutableLiveData<PLVStatefulData<PLVHiClassDataBean>> getFulHiClassDataBean() {
        return this.fulClassDataBean;
    }

    @Override // com.easefun.polyv.livecommon.module.data.IPLVLiveRoomDataManager
    public MutableLiveData<PLVStatefulData<PolyvChatFunctionSwitchVO>> getFunctionSwitchVO() {
        return this.functionSwitchVO;
    }

    @Override // com.easefun.polyv.livecommon.module.data.IPLVLiveRoomDataManager
    public MutableLiveData<PLVStatefulData<LiveStatus>> getLiveStatusData() {
        return this.liveStatusData;
    }

    @Override // com.easefun.polyv.livecommon.module.data.IPLVLiveRoomDataManager
    public MutableLiveData<PLVStatefulData<Integer>> getPageViewerData() {
        return this.pageViewerData;
    }

    @Override // com.easefun.polyv.livecommon.module.data.IPLVLiveRoomDataManager
    public MutableLiveData<String> getSessionIdLiveData() {
        return this.sessionIdLiveData;
    }

    @Override // com.easefun.polyv.livecommon.module.data.IPLVLiveRoomDataManager
    public void requestProductList(int rank) {
        this.liveRoomDataRequester.requestProductList(rank, new PLVLiveRoomDataRequester.IPLVNetRequestListener<PLVCommodityVO2>() { // from class: com.easefun.polyv.livecommon.module.data.PLVLiveRoomDataManager.4
            @Override // com.easefun.polyv.livecommon.module.data.PLVLiveRoomDataRequester.IPLVNetRequestListener
            public void onFailed(String msg, Throwable throwable) {
                PLVLiveRoomDataManager.this.commodityVO.postValue(PLVStatefulData.error(msg, throwable));
            }

            @Override // com.easefun.polyv.livecommon.module.data.PLVLiveRoomDataRequester.IPLVNetRequestListener
            public void onSuccess(PLVCommodityVO2 polyvCommodityVO) {
                PLVLiveRoomDataManager.this.commodityVO.postValue(PLVStatefulData.success(polyvCommodityVO));
            }
        });
    }
}
