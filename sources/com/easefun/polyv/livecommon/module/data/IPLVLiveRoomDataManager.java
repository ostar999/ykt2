package com.easefun.polyv.livecommon.module.data;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.easefun.polyv.livecommon.module.config.PLVLiveChannelConfig;
import com.easefun.polyv.livecommon.module.data.PLVLiveRoomDataManager;
import com.easefun.polyv.livescenes.model.PolyvChatFunctionSwitchVO;
import com.easefun.polyv.livescenes.model.PolyvLiveClassDetailVO;
import com.plv.livescenes.hiclass.PLVHiClassDataBean;
import com.plv.livescenes.model.PLVPlaybackChannelDetailVO;
import com.plv.livescenes.model.commodity.saas.PLVCommodityVO2;
import com.plv.livescenes.model.interact.PLVWebviewUpdateAppStatusVO;
import com.plv.socket.event.chat.PLVRewardEvent;

/* loaded from: classes3.dex */
public interface IPLVLiveRoomDataManager {
    void destroy();

    LiveData<PLVStatefulData<PolyvLiveClassDetailVO>> getClassDetailVO();

    int getCommodityRank();

    LiveData<PLVStatefulData<PLVCommodityVO2>> getCommodityVO();

    @NonNull
    PLVLiveChannelConfig getConfig();

    LiveData<PLVStatefulData<PLVHiClassDataBean>> getFulHiClassDataBean();

    LiveData<PLVStatefulData<PolyvChatFunctionSwitchVO>> getFunctionSwitchVO();

    LiveData<PLVHiClassDataBean> getHiClassDataBean();

    MutableLiveData<PLVWebviewUpdateAppStatusVO> getInteractStatusData();

    LiveData<Boolean> getIsOnlyAudioEnabled();

    LiveData<PLVStatefulData<PLVLiveRoomDataManager.LiveStatus>> getLiveStatusData();

    LiveData<PLVStatefulData<Integer>> getPageViewerData();

    LiveData<PLVStatefulData<PLVPlaybackChannelDetailVO>> getPlaybackChannelData();

    MutableLiveData<PLVStatefulData<Boolean>> getPointRewardEnableData();

    MutableLiveData<PLVRewardEvent> getRewardEventData();

    String getSessionId();

    LiveData<String> getSessionIdLiveData();

    boolean isNeedStreamRecover();

    boolean isOnlyAudio();

    boolean isSupportRTC();

    void requestChannelDetail();

    void requestChannelSwitch();

    void requestLessonDetail();

    void requestLiveStatus();

    void requestPageViewer();

    void requestPlaybackChannelStatus();

    void requestProductList();

    void requestProductList(int rank);

    void requestUpdateChannelName();

    void setConfigVid(String vid);

    void setNeedStreamRecover(boolean isNeed);

    void setOnlyAudio(boolean onlyAudio);

    void setSessionId(String sessionId);

    void setSupportRTC(boolean isSupportRTC);
}
