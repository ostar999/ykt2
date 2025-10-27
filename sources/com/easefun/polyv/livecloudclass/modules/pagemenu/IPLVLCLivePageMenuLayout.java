package com.easefun.polyv.livecloudclass.modules.pagemenu;

import com.easefun.polyv.livecloudclass.modules.chatroom.adapter.PLVLCChatCommonMessageList;
import com.easefun.polyv.livecommon.module.data.IPLVLiveRoomDataManager;
import com.easefun.polyv.livecommon.module.modules.chatroom.contract.IPLVChatroomContract;
import com.easefun.polyv.livecommon.module.modules.interact.cardpush.PLVCardPushManager;
import com.easefun.polyv.livecommon.module.modules.player.live.enums.PLVLiveStateEnum;
import com.easefun.polyv.livecommon.module.modules.previous.contract.IPLVPreviousPlaybackContract;
import com.easefun.polyv.livecommon.module.utils.listener.IPLVOnDataChangedListener;
import com.plv.livescenes.playback.chat.IPLVChatPlaybackManager;

/* loaded from: classes3.dex */
public interface IPLVLCLivePageMenuLayout {

    public interface OnViewActionListener {
        int getVideoCurrentPosition();

        void onAddedChatTab(boolean z2);

        void onChangeVideoVidAction(String str);

        void onClickChatMoreDynamicFunction(String str);

        void onSeekToAction(int i2);

        void onSendDanmuAction(CharSequence charSequence);

        void onShowBulletinAction();

        void onShowEffectAction(boolean z2);

        void onShowRewardAction();
    }

    void addOnViewerCountListener(IPLVOnDataChangedListener<Long> iPLVOnDataChangedListener);

    void destroy();

    PLVCardPushManager getCardPushManager();

    PLVLCChatCommonMessageList getChatCommonMessageList();

    IPLVChatPlaybackManager getChatPlaybackManager();

    IPLVChatroomContract.IChatroomPresenter getChatroomPresenter();

    IPLVPreviousPlaybackContract.IPreviousPlaybackPresenter getPreviousPresenter();

    void init(IPLVLiveRoomDataManager iPLVLiveRoomDataManager);

    boolean isChatPlaybackEnabled();

    boolean onBackPressed();

    void onPlaybackVideoPrepared(String str, String str2);

    void onPlaybackVideoSeekComplete(int i2);

    void setOnViewActionListener(OnViewActionListener onViewActionListener);

    void updateLiveStatus(PLVLiveStateEnum pLVLiveStateEnum);
}
