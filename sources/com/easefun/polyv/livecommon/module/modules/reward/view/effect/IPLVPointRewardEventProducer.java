package com.easefun.polyv.livecommon.module.modules.reward.view.effect;

import com.plv.socket.event.chat.PLVRewardEvent;

/* loaded from: classes3.dex */
public interface IPLVPointRewardEventProducer {

    public interface IPLVOnFetchRewardEventListener {
        void onFetchSucceed(PLVRewardEvent event);
    }

    public interface OnPreparedListener {
        void onPrepared();
    }

    void addEvent(PLVRewardEvent rewardEvent);

    void destroy();

    void fetchEvent(IPLVOnFetchRewardEventListener onFetchRewardEventListener);

    void prepare(OnPreparedListener onPreparedListener);
}
