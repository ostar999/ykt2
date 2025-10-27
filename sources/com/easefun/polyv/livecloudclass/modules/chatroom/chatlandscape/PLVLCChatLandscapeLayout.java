package com.easefun.polyv.livecloudclass.modules.chatroom.chatlandscape;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.easefun.polyv.livecloudclass.R;
import com.easefun.polyv.livecloudclass.modules.chatroom.adapter.PLVLCChatCommonMessageList;
import com.easefun.polyv.livecommon.module.modules.chatroom.PLVSpecialTypeTag;
import com.easefun.polyv.livecommon.module.modules.chatroom.contract.IPLVChatroomContract;
import com.easefun.polyv.livecommon.module.modules.chatroom.view.PLVAbsChatroomView;
import com.easefun.polyv.livecommon.ui.widget.PLVMessageRecyclerView;
import com.easefun.polyv.livecommon.ui.widget.itemview.PLVBaseViewData;
import com.easefun.polyv.livescenes.chatroom.PolyvLocalMessage;
import com.easefun.polyv.livescenes.chatroom.send.img.PolyvSendLocalImgEvent;
import com.plv.foundationsdk.utils.PLVAppUtils;
import com.plv.livescenes.playback.chat.IPLVChatPlaybackCallDataListener;
import com.plv.livescenes.playback.chat.IPLVChatPlaybackManager;
import com.plv.livescenes.playback.chat.PLVChatPlaybackCallDataExListener;
import com.plv.livescenes.playback.chat.PLVChatPlaybackData;
import com.plv.livescenes.socket.PLVSocketWrapper;
import com.plv.socket.event.PLVBaseEvent;
import com.plv.socket.event.PLVEventHelper;
import com.plv.socket.event.chat.PLVChatEmotionEvent;
import com.plv.socket.event.chat.PLVCloseRoomEvent;
import com.plv.socket.event.chat.PLVFocusModeEvent;
import com.plv.thirdpart.blankj.utilcode.util.ConvertUtils;
import com.plv.thirdpart.blankj.utilcode.util.ScreenUtils;
import com.plv.thirdpart.blankj.utilcode.util.ToastUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes3.dex */
public class PLVLCChatLandscapeLayout extends FrameLayout {
    public static final float RATIO_H = 0.49f;
    public static final float RATIO_W = 0.27f;
    private PLVLCChatCommonMessageList chatCommonMessageList;
    private IPLVChatPlaybackCallDataListener chatPlaybackDataListener;
    private IPLVChatPlaybackManager chatPlaybackManager;
    private IPLVChatroomContract.IChatroomPresenter chatroomPresenter;
    private IPLVChatroomContract.IChatroomView chatroomView;
    private Handler handler;
    private boolean isChatPlaybackLayout;
    private boolean isCloseRoomStatus;
    private boolean isFocusModeStatus;
    private OnRoomStatusListener onRoomStatusListener;
    private SwipeRefreshLayout swipeLoadView;
    private boolean toShow;
    private TextView unreadMsgTv;

    public interface OnRoomStatusListener {
        void onStatusChanged(boolean z2, boolean z3);
    }

    public PLVLCChatLandscapeLayout(@NonNull Context context) {
        this(context, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void acceptCloseRoomEvent(PLVCloseRoomEvent pLVCloseRoomEvent) {
        this.isCloseRoomStatus = pLVCloseRoomEvent.getValue().isClosed();
        PLVAppUtils.postToMainThread(new Runnable() { // from class: com.easefun.polyv.livecloudclass.modules.chatroom.chatlandscape.PLVLCChatLandscapeLayout.6
            @Override // java.lang.Runnable
            public void run() {
                if (PLVLCChatLandscapeLayout.this.onRoomStatusListener != null) {
                    PLVLCChatLandscapeLayout.this.onRoomStatusListener.onStatusChanged(PLVLCChatLandscapeLayout.this.isCloseRoomStatus, PLVLCChatLandscapeLayout.this.isFocusModeStatus);
                }
                if (PLVLCChatLandscapeLayout.this.chatCommonMessageList == null || !PLVLCChatLandscapeLayout.this.chatCommonMessageList.isLandscapeLayout()) {
                    return;
                }
                ToastUtils.showLong(PLVLCChatLandscapeLayout.this.isCloseRoomStatus ? R.string.plv_chat_toast_chatroom_close : R.string.plv_chat_toast_chatroom_open);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void acceptFocusModeEvent(PLVFocusModeEvent pLVFocusModeEvent) {
        this.isFocusModeStatus = pLVFocusModeEvent.isOpen();
        PLVAppUtils.postToMainThread(new Runnable() { // from class: com.easefun.polyv.livecloudclass.modules.chatroom.chatlandscape.PLVLCChatLandscapeLayout.5
            @Override // java.lang.Runnable
            public void run() {
                if (PLVLCChatLandscapeLayout.this.onRoomStatusListener != null) {
                    PLVLCChatLandscapeLayout.this.onRoomStatusListener.onStatusChanged(PLVLCChatLandscapeLayout.this.isCloseRoomStatus, PLVLCChatLandscapeLayout.this.isFocusModeStatus);
                }
                if (PLVLCChatLandscapeLayout.this.chatCommonMessageList == null || PLVLCChatLandscapeLayout.this.chatCommonMessageList.isLandscapeLayout()) {
                    ToastUtils.showLong(PLVLCChatLandscapeLayout.this.isFocusModeStatus ? R.string.plv_chat_toast_focus_mode_open : R.string.plv_chat_toast_focus_mode_close);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addChatHistoryToList(List<PLVBaseViewData<PLVBaseEvent>> list, boolean z2) {
        PLVLCChatCommonMessageList pLVLCChatCommonMessageList = this.chatCommonMessageList;
        if (pLVLCChatCommonMessageList != null) {
            pLVLCChatCommonMessageList.addChatHistoryToList(list, z2, true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addChatMessageToList(final List<PLVBaseViewData> list, final boolean z2) {
        Runnable runnable = new Runnable() { // from class: com.easefun.polyv.livecloudclass.modules.chatroom.chatlandscape.PLVLCChatLandscapeLayout.8
            @Override // java.lang.Runnable
            public void run() {
                if (PLVLCChatLandscapeLayout.this.chatCommonMessageList != null) {
                    PLVLCChatLandscapeLayout.this.chatCommonMessageList.addChatMessageToList(list, z2, true);
                }
            }
        };
        if (Looper.myLooper() == Looper.getMainLooper()) {
            runnable.run();
        } else {
            this.handler.post(runnable);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addChatMessageToListHead(List<PLVBaseViewData> list) {
        PLVLCChatCommonMessageList pLVLCChatCommonMessageList = this.chatCommonMessageList;
        if (pLVLCChatCommonMessageList != null) {
            pLVLCChatCommonMessageList.addChatMessageToListHead(list, false, true);
        }
    }

    private void initLayoutWH() {
        post(new Runnable() { // from class: com.easefun.polyv.livecloudclass.modules.chatroom.chatlandscape.PLVLCChatLandscapeLayout.3
            @Override // java.lang.Runnable
            public void run() {
                ViewGroup.LayoutParams layoutParams = PLVLCChatLandscapeLayout.this.getLayoutParams();
                int iMax = Math.max(ScreenUtils.getScreenWidth(), ScreenUtils.getScreenHeight());
                int iMin = Math.min(ScreenUtils.getScreenWidth(), ScreenUtils.getScreenHeight());
                layoutParams.width = (int) (iMax * 0.27f);
                layoutParams.height = (int) (iMin * 0.49f);
                PLVLCChatLandscapeLayout.this.setLayoutParams(layoutParams);
            }
        });
    }

    private void initView() {
        if (ScreenUtils.isPortrait()) {
            setVisibility(8);
        } else {
            setVisibility(0);
        }
        LayoutInflater.from(getContext()).inflate(R.layout.plvlc_chatroom_chat_landscape_layout, (ViewGroup) this, true);
        this.unreadMsgTv = (TextView) findViewById(R.id.unread_msg_tv);
        SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_load_view);
        this.swipeLoadView = swipeRefreshLayout;
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        this.swipeLoadView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() { // from class: com.easefun.polyv.livecloudclass.modules.chatroom.chatlandscape.PLVLCChatLandscapeLayout.2
            @Override // androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
            public void onRefresh() {
                if (PLVLCChatLandscapeLayout.this.isChatPlaybackLayout) {
                    if (PLVLCChatLandscapeLayout.this.chatPlaybackManager != null) {
                        PLVLCChatLandscapeLayout.this.chatPlaybackManager.loadPrevious();
                    }
                } else if (PLVLCChatLandscapeLayout.this.chatroomPresenter != null) {
                    PLVLCChatLandscapeLayout.this.chatroomPresenter.requestChatHistory(PLVLCChatLandscapeLayout.this.chatroomPresenter.getViewIndex(PLVLCChatLandscapeLayout.this.chatroomView));
                }
            }
        });
        initLayoutWH();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeChatMessageToList(int i2, int i3) {
        PLVLCChatCommonMessageList pLVLCChatCommonMessageList = this.chatCommonMessageList;
        if (pLVLCChatCommonMessageList != null) {
            pLVLCChatCommonMessageList.removeChatMessage(i2, i3, true);
            if (this.chatCommonMessageList.canScrollVertically(1)) {
                return;
            }
            PLVLCChatCommonMessageList pLVLCChatCommonMessageList2 = this.chatCommonMessageList;
            pLVLCChatCommonMessageList2.scrollToPosition(pLVLCChatCommonMessageList2.getItemCount() - 1);
        }
    }

    public IPLVChatPlaybackCallDataListener getChatPlaybackDataListener() {
        return this.chatPlaybackDataListener;
    }

    public IPLVChatroomContract.IChatroomView getChatroomView() {
        return this.chatroomView;
    }

    public void init(PLVLCChatCommonMessageList pLVLCChatCommonMessageList) {
        this.chatCommonMessageList = pLVLCChatCommonMessageList;
        if (pLVLCChatCommonMessageList == null) {
            return;
        }
        pLVLCChatCommonMessageList.addUnreadView(this.unreadMsgTv);
        pLVLCChatCommonMessageList.addOnUnreadCountChangeListener(new PLVMessageRecyclerView.OnUnreadCountChangeListener() { // from class: com.easefun.polyv.livecloudclass.modules.chatroom.chatlandscape.PLVLCChatLandscapeLayout.1
            @Override // com.easefun.polyv.livecommon.ui.widget.PLVMessageRecyclerView.OnUnreadCountChangeListener
            public void onChange(int i2) {
                PLVLCChatLandscapeLayout.this.unreadMsgTv.setText(i2 + "条新消息");
            }
        });
        pLVLCChatCommonMessageList.attachToParent(this.swipeLoadView, true);
    }

    @Override // android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        IPLVChatroomContract.IChatroomPresenter iChatroomPresenter;
        super.onConfigurationChanged(configuration);
        if (configuration.orientation != 2) {
            setVisibility(8);
            return;
        }
        if (this.toShow) {
            setVisibility(0);
        }
        PLVLCChatCommonMessageList pLVLCChatCommonMessageList = this.chatCommonMessageList;
        if (pLVLCChatCommonMessageList == null || !pLVLCChatCommonMessageList.attachToParent(this.swipeLoadView, true) || (iChatroomPresenter = this.chatroomPresenter) == null) {
            return;
        }
        this.chatCommonMessageList.setMsgIndex(iChatroomPresenter.getViewIndex(this.chatroomView));
        if (this.isChatPlaybackLayout || this.chatroomPresenter.getChatHistoryTime() != 0) {
            return;
        }
        IPLVChatroomContract.IChatroomPresenter iChatroomPresenter2 = this.chatroomPresenter;
        iChatroomPresenter2.requestChatHistory(iChatroomPresenter2.getViewIndex(this.chatroomView));
    }

    public void setIsChatPlaybackLayout(boolean z2) {
        this.isChatPlaybackLayout = z2;
    }

    public void setOnRoomStatusListener(OnRoomStatusListener onRoomStatusListener) {
        this.onRoomStatusListener = onRoomStatusListener;
    }

    public void toggle(boolean z2) {
        this.toShow = z2;
        setVisibility((z2 && ScreenUtils.isLandscape()) ? 0 : 8);
    }

    public PLVLCChatLandscapeLayout(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public PLVLCChatLandscapeLayout(@NonNull Context context, @Nullable AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.toShow = true;
        this.handler = new Handler(Looper.getMainLooper());
        this.chatPlaybackDataListener = new PLVChatPlaybackCallDataExListener() { // from class: com.easefun.polyv.livecloudclass.modules.chatroom.chatlandscape.PLVLCChatLandscapeLayout.4
            @Override // com.plv.livescenes.playback.chat.IPLVChatPlaybackCallDataListener
            public void onData(List<PLVChatPlaybackData> list) {
            }

            @Override // com.plv.livescenes.playback.chat.IPLVChatPlaybackCallDataListener
            public void onDataCleared() {
                PLVLCChatLandscapeLayout.this.removeChatMessageToList((String) null, true);
            }

            @Override // com.plv.livescenes.playback.chat.IPLVChatPlaybackCallDataListener
            public void onDataInserted(int i3, int i4, List<PLVChatPlaybackData> list, boolean z2, int i5) {
                boolean z3;
                ArrayList arrayList = new ArrayList();
                Iterator<PLVChatPlaybackData> it = list.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    PLVChatPlaybackData next = it.next();
                    arrayList.add(new PLVBaseViewData(next, next.isImgMsg() ? 4 : 2, PLVEventHelper.isSpecialType(next.getUserType()) || PLVSocketWrapper.getInstance().getLoginVO().getUserId().equals(next.getUserId()) ? new PLVSpecialTypeTag(next.getUserId()) : null));
                }
                if (z2) {
                    PLVLCChatLandscapeLayout.this.addChatMessageToListHead(arrayList);
                    return;
                }
                if (PLVLCChatLandscapeLayout.this.chatCommonMessageList != null && PLVLCChatLandscapeLayout.this.chatCommonMessageList.getItemCount() == 0) {
                    z3 = true;
                }
                PLVLCChatLandscapeLayout.this.addChatMessageToList(arrayList, z3);
            }

            @Override // com.plv.livescenes.playback.chat.IPLVChatPlaybackCallDataListener
            public void onDataRemoved(int i3, int i4, List<PLVChatPlaybackData> list, boolean z2) {
                PLVLCChatLandscapeLayout.this.removeChatMessageToList(i3, i4);
            }

            @Override // com.plv.livescenes.playback.chat.IPLVChatPlaybackCallDataListener
            public void onHasNotAddedData() {
                if (PLVLCChatLandscapeLayout.this.unreadMsgTv == null || PLVLCChatLandscapeLayout.this.unreadMsgTv.getVisibility() == 0) {
                    return;
                }
                PLVLCChatLandscapeLayout.this.unreadMsgTv.setText("有新消息，点击查看");
                PLVLCChatLandscapeLayout.this.unreadMsgTv.setVisibility(0);
            }

            @Override // com.plv.livescenes.playback.chat.PLVChatPlaybackCallDataExListener
            public void onLoadPreviousEnabled(boolean z2, boolean z3) {
                if (PLVLCChatLandscapeLayout.this.swipeLoadView != null) {
                    PLVLCChatLandscapeLayout.this.swipeLoadView.setEnabled(z2);
                }
                if (z2 || z3) {
                    return;
                }
                ToastUtils.showShort(R.string.plv_chat_toast_history_all_loaded);
            }

            @Override // com.plv.livescenes.playback.chat.IPLVChatPlaybackCallDataListener
            public void onLoadPreviousFinish() {
                if (PLVLCChatLandscapeLayout.this.swipeLoadView != null) {
                    PLVLCChatLandscapeLayout.this.swipeLoadView.setRefreshing(false);
                }
            }

            @Override // com.plv.livescenes.playback.chat.IPLVChatPlaybackCallDataListener
            public void onManager(IPLVChatPlaybackManager iPLVChatPlaybackManager) {
                PLVLCChatLandscapeLayout.this.chatPlaybackManager = iPLVChatPlaybackManager;
            }
        };
        this.chatroomView = new PLVAbsChatroomView() { // from class: com.easefun.polyv.livecloudclass.modules.chatroom.chatlandscape.PLVLCChatLandscapeLayout.7
            @Override // com.easefun.polyv.livecommon.module.modules.chatroom.view.PLVAbsChatroomView, com.easefun.polyv.livecommon.module.modules.chatroom.contract.IPLVChatroomContract.IChatroomView
            public int getSpeakEmojiSize() {
                return ConvertUtils.dp2px(14.0f);
            }

            @Override // com.easefun.polyv.livecommon.module.modules.chatroom.view.PLVAbsChatroomView, com.easefun.polyv.livecommon.module.modules.chatroom.contract.IPLVChatroomContract.IChatroomView
            public void onCloseRoomEvent(@NonNull PLVCloseRoomEvent pLVCloseRoomEvent) {
                super.onCloseRoomEvent(pLVCloseRoomEvent);
                if (PLVLCChatLandscapeLayout.this.isChatPlaybackLayout) {
                    return;
                }
                PLVLCChatLandscapeLayout.this.acceptCloseRoomEvent(pLVCloseRoomEvent);
            }

            @Override // com.easefun.polyv.livecommon.module.modules.chatroom.view.PLVAbsChatroomView, com.easefun.polyv.livecommon.module.modules.chatroom.contract.IPLVChatroomContract.IChatroomView
            public void onFocusModeEvent(@NonNull PLVFocusModeEvent pLVFocusModeEvent) {
                super.onFocusModeEvent(pLVFocusModeEvent);
                if (PLVLCChatLandscapeLayout.this.isChatPlaybackLayout) {
                    return;
                }
                PLVLCChatLandscapeLayout.this.acceptFocusModeEvent(pLVFocusModeEvent);
            }

            @Override // com.easefun.polyv.livecommon.module.modules.chatroom.view.PLVAbsChatroomView, com.easefun.polyv.livecommon.module.modules.chatroom.contract.IPLVChatroomContract.IChatroomView
            public void onHistoryDataList(List<PLVBaseViewData<PLVBaseEvent>> list, int i3, boolean z2, int i4) {
                super.onHistoryDataList(list, i3, z2, i4);
                if (PLVLCChatLandscapeLayout.this.isChatPlaybackLayout) {
                    return;
                }
                PLVLCChatLandscapeLayout.this.swipeLoadView.setRefreshing(false);
                PLVLCChatLandscapeLayout.this.swipeLoadView.setEnabled(true);
                if (!list.isEmpty()) {
                    PLVLCChatLandscapeLayout.this.addChatHistoryToList(list, i3 == 1);
                }
                if (z2) {
                    ToastUtils.showShort(R.string.plv_chat_toast_history_all_loaded);
                    PLVLCChatLandscapeLayout.this.swipeLoadView.setEnabled(false);
                }
            }

            @Override // com.easefun.polyv.livecommon.module.modules.chatroom.view.PLVAbsChatroomView, com.easefun.polyv.livecommon.module.modules.chatroom.contract.IPLVChatroomContract.IChatroomView
            public void onHistoryRequestFailed(String str, Throwable th, int i3) {
                super.onHistoryRequestFailed(str, th, i3);
                if (PLVLCChatLandscapeLayout.this.isChatPlaybackLayout) {
                    return;
                }
                PLVLCChatLandscapeLayout.this.swipeLoadView.setRefreshing(false);
                PLVLCChatLandscapeLayout.this.swipeLoadView.setEnabled(true);
                if (i3 == PLVLCChatLandscapeLayout.this.chatroomPresenter.getViewIndex(PLVLCChatLandscapeLayout.this.chatroomView)) {
                    ToastUtils.showShort(PLVLCChatLandscapeLayout.this.getContext().getString(R.string.plv_chat_toast_history_load_failed) + ": " + str);
                }
            }

            @Override // com.easefun.polyv.livecommon.module.modules.chatroom.view.PLVAbsChatroomView, com.easefun.polyv.livecommon.module.modules.chatroom.contract.IPLVChatroomContract.IChatroomView
            public void onLoadEmotionMessage(@Nullable @org.jetbrains.annotations.Nullable PLVChatEmotionEvent pLVChatEmotionEvent) {
                super.onLoadEmotionMessage(pLVChatEmotionEvent);
                if (PLVLCChatLandscapeLayout.this.isChatPlaybackLayout || pLVChatEmotionEvent == null) {
                    return;
                }
                ArrayList arrayList = new ArrayList();
                arrayList.add(new PLVBaseViewData(pLVChatEmotionEvent, 8, pLVChatEmotionEvent.isSpecialTypeOrMe() ? new PLVSpecialTypeTag(pLVChatEmotionEvent.getUserId()) : null));
                PLVLCChatLandscapeLayout.this.addChatMessageToList(arrayList, pLVChatEmotionEvent.isLocal());
            }

            @Override // com.easefun.polyv.livecommon.module.modules.chatroom.view.PLVAbsChatroomView, com.easefun.polyv.livecommon.module.modules.chatroom.contract.IPLVChatroomContract.IChatroomView
            public void onLocalImageMessage(@Nullable PolyvSendLocalImgEvent polyvSendLocalImgEvent) {
                super.onLocalImageMessage(polyvSendLocalImgEvent);
                if (PLVLCChatLandscapeLayout.this.isChatPlaybackLayout) {
                    return;
                }
                ArrayList arrayList = new ArrayList();
                arrayList.add(new PLVBaseViewData(polyvSendLocalImgEvent, 3, new PLVSpecialTypeTag(polyvSendLocalImgEvent.getUserId())));
                PLVLCChatLandscapeLayout.this.addChatMessageToList(arrayList, true);
            }

            @Override // com.easefun.polyv.livecommon.module.modules.chatroom.view.PLVAbsChatroomView, com.easefun.polyv.livecommon.module.modules.chatroom.contract.IPLVChatroomContract.IChatroomView
            public void onLocalSpeakMessage(@Nullable PolyvLocalMessage polyvLocalMessage) {
                super.onLocalSpeakMessage(polyvLocalMessage);
                if (PLVLCChatLandscapeLayout.this.isChatPlaybackLayout || polyvLocalMessage == null) {
                    return;
                }
                ArrayList arrayList = new ArrayList();
                arrayList.add(new PLVBaseViewData(polyvLocalMessage, 1, new PLVSpecialTypeTag(polyvLocalMessage.getUserId())));
                PLVLCChatLandscapeLayout.this.addChatMessageToList(arrayList, true);
            }

            @Override // com.easefun.polyv.livecommon.module.modules.chatroom.view.PLVAbsChatroomView, com.easefun.polyv.livecommon.module.modules.chatroom.contract.IPLVChatroomContract.IChatroomView
            public void onRemoveMessageEvent(@Nullable String str, boolean z2) {
                super.onRemoveMessageEvent(str, z2);
                if (PLVLCChatLandscapeLayout.this.isChatPlaybackLayout) {
                    return;
                }
                PLVLCChatLandscapeLayout.this.removeChatMessageToList(str, z2);
            }

            @Override // com.easefun.polyv.livecommon.module.modules.chatroom.view.PLVAbsChatroomView, com.easefun.polyv.livecommon.module.modules.chatroom.contract.IPLVChatroomContract.IChatroomView
            public void onSpeakImgDataList(List<PLVBaseViewData> list) {
                super.onSpeakImgDataList(list);
                if (PLVLCChatLandscapeLayout.this.isChatPlaybackLayout) {
                    return;
                }
                PLVLCChatLandscapeLayout.this.addChatMessageToList(list, false);
            }

            @Override // com.easefun.polyv.livecommon.module.modules.chatroom.view.PLVAbsChatroomView, com.easefun.polyv.livecommon.module.modules.chatroom.contract.IPLVChatroomContract.IChatroomView
            public void setPresenter(@NonNull IPLVChatroomContract.IChatroomPresenter iChatroomPresenter) {
                super.setPresenter(iChatroomPresenter);
                PLVLCChatLandscapeLayout.this.chatroomPresenter = iChatroomPresenter;
                if (!PLVLCChatLandscapeLayout.this.isChatPlaybackLayout && PLVLCChatLandscapeLayout.this.chatroomPresenter.getChatHistoryTime() == 0 && PLVLCChatLandscapeLayout.this.chatCommonMessageList != null && PLVLCChatLandscapeLayout.this.chatCommonMessageList.isLandscapeLayout()) {
                    PLVLCChatLandscapeLayout.this.chatroomPresenter.requestChatHistory(PLVLCChatLandscapeLayout.this.chatroomPresenter.getViewIndex(PLVLCChatLandscapeLayout.this.chatroomView));
                }
            }
        };
        initView();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeChatMessageToList(final String str, final boolean z2) {
        Runnable runnable = new Runnable() { // from class: com.easefun.polyv.livecloudclass.modules.chatroom.chatlandscape.PLVLCChatLandscapeLayout.9
            @Override // java.lang.Runnable
            public void run() {
                if (PLVLCChatLandscapeLayout.this.chatCommonMessageList == null) {
                    return;
                }
                if (z2) {
                    PLVLCChatLandscapeLayout.this.chatCommonMessageList.removeAllChatMessage(true);
                } else {
                    PLVLCChatLandscapeLayout.this.chatCommonMessageList.removeChatMessage(str, true);
                }
            }
        };
        if (Looper.myLooper() == Looper.getMainLooper()) {
            runnable.run();
        } else {
            this.handler.post(runnable);
        }
    }
}
