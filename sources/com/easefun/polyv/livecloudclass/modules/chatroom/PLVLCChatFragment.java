package com.easefun.polyv.livecloudclass.modules.chatroom;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.easefun.polyv.livecloudclass.R;
import com.easefun.polyv.livecloudclass.modules.chatroom.adapter.PLVLCChatCommonMessageList;
import com.easefun.polyv.livecloudclass.modules.chatroom.adapter.PLVLCEmotionPersonalListAdapter;
import com.easefun.polyv.livecloudclass.modules.chatroom.chatmore.PLVLCChatFunctionListener;
import com.easefun.polyv.livecloudclass.modules.chatroom.chatmore.PLVLCChatMoreLayout;
import com.easefun.polyv.livecloudclass.modules.chatroom.utils.PLVChatroomUtils;
import com.easefun.polyv.livecloudclass.modules.chatroom.widget.PLVLCBulletinTextView;
import com.easefun.polyv.livecloudclass.modules.chatroom.widget.PLVLCGreetingTextView;
import com.easefun.polyv.livecloudclass.modules.chatroom.widget.PLVLCLikeIconView;
import com.easefun.polyv.livecommon.module.modules.chatroom.PLVSpecialTypeTag;
import com.easefun.polyv.livecommon.module.modules.chatroom.contract.IPLVChatroomContract;
import com.easefun.polyv.livecommon.module.modules.chatroom.view.PLVAbsChatroomView;
import com.easefun.polyv.livecommon.module.modules.interact.cardpush.PLVCardPushManager;
import com.easefun.polyv.livecommon.module.modules.reward.view.effect.IPLVPointRewardEventProducer;
import com.easefun.polyv.livecommon.module.modules.reward.view.effect.PLVPointRewardEffectQueue;
import com.easefun.polyv.livecommon.module.modules.reward.view.effect.PLVPointRewardEffectWidget;
import com.easefun.polyv.livecommon.module.modules.reward.view.effect.PLVRewardSVGAHelper;
import com.easefun.polyv.livecommon.module.utils.PLVToast;
import com.easefun.polyv.livecommon.module.utils.PLVUriPathHelper;
import com.easefun.polyv.livecommon.ui.widget.PLVImagePreviewPopupWindow;
import com.easefun.polyv.livecommon.ui.widget.PLVMessageRecyclerView;
import com.easefun.polyv.livecommon.ui.widget.PLVTriangleIndicateTextView;
import com.easefun.polyv.livecommon.ui.widget.itemview.PLVBaseViewData;
import com.easefun.polyv.livecommon.ui.window.PLVInputFragment;
import com.easefun.polyv.livescenes.chatroom.PolyvLocalMessage;
import com.easefun.polyv.livescenes.chatroom.send.img.PolyvSendLocalImgEvent;
import com.hjq.permissions.Permission;
import com.opensource.svgaplayer.SVGAImageView;
import com.opensource.svgaplayer.SVGAParser;
import com.plv.foundationsdk.permission.PLVFastPermission;
import com.plv.foundationsdk.permission.PLVOnPermissionCallback;
import com.plv.foundationsdk.utils.PLVAppUtils;
import com.plv.foundationsdk.utils.PLVSDCardUtils;
import com.plv.foundationsdk.utils.PLVSugarUtil;
import com.plv.livescenes.chatroom.send.img.PLVSendChatImageHelper;
import com.plv.livescenes.model.PLVChatFunctionSwitchVO;
import com.plv.livescenes.model.PLVEmotionImageVO;
import com.plv.livescenes.model.interact.PLVChatFunctionVO;
import com.plv.livescenes.model.interact.PLVWebviewUpdateAppStatusVO;
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
import com.plv.socket.event.chat.PLVLikesEvent;
import com.plv.socket.event.chat.PLVRewardEvent;
import com.plv.socket.event.chat.PLVSpeakEvent;
import com.plv.socket.event.interact.PLVNewsPushStartEvent;
import com.plv.socket.event.login.PLVLoginEvent;
import com.plv.socket.user.PLVSocketUserConstant;
import com.plv.thirdpart.blankj.utilcode.util.ActivityUtils;
import com.plv.thirdpart.blankj.utilcode.util.ConvertUtils;
import com.plv.thirdpart.blankj.utilcode.util.ScreenUtils;
import com.plv.thirdpart.blankj.utilcode.util.StringUtils;
import com.plv.thirdpart.blankj.utilcode.util.ToastUtils;
import com.psychiatrygarden.utils.MimeTypes;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes3.dex */
public class PLVLCChatFragment extends PLVInputFragment implements View.OnClickListener {
    private static final int REQUEST_OPEN_CAMERA = 2;
    private static final int REQUEST_SELECT_IMG = 1;
    private PLVLCBulletinTextView bulletinTv;
    private TextView cardEnterCdTv;
    private PLVTriangleIndicateTextView cardEnterTipsView;
    private ImageView cardEnterView;
    private PLVCardPushManager cardPushManager;
    private PLVLCChatCommonMessageList chatCommonMessageList;
    private PLVLCChatMoreLayout chatMoreLayout;
    private IPLVChatPlaybackManager chatPlaybackManager;
    private TextView chatPlaybackTipsTv;
    private IPLVChatroomContract.IChatroomPresenter chatroomPresenter;
    private ImageView deleteMsgIv;
    private ViewGroup emojiLy;
    private RecyclerView emojiPersonalRv;
    private RecyclerView emojiRv;
    private List<PLVEmotionImageVO.EmotionImage> emotionImages;
    private PLVImagePreviewPopupWindow emotionPreviewWindow;
    private List<PLVChatFunctionSwitchVO.DataBean> functionSwitchData;
    private PLVLCGreetingTextView greetingTv;
    private EditText inputEt;
    private boolean isChatPlaybackLayout;
    private boolean isCloseRoomStatus;
    private boolean isFocusModeStatus;
    private boolean isLiveType;
    private boolean isSelectOnlyTeacher;
    private boolean isShowGreeting;
    private long likesCount;
    private TextView likesCountTv;
    private ViewGroup likesLy;
    private PLVLCLikeIconView likesView;
    private OnViewActionListener onViewActionListener;
    private Runnable playbackTipsRunnable;
    private IPLVPointRewardEventProducer pointRewardEventProducer;
    private PLVPointRewardEffectWidget polyvPointRewardEffectWidget;
    private Editable recordInputMessage;

    @Nullable
    private ImageView rewardIv;
    private SVGAImageView rewardSvgImage;
    private TextView sendMsgTv;
    private PLVRewardSVGAHelper svgaHelper;
    private SVGAParser svgaParser;
    private SwipeRefreshLayout swipeLoadView;
    private ImageView tabEmojiIv;
    private ImageView tabPersonalIv;
    private File takePictureFilePath;
    private Uri takePictureUri;
    private ImageView toggleEmojiIv;
    private ImageView toggleMoreIv;
    private TextView unreadMsgTv;
    private String TAG = getClass().getSimpleName();
    private boolean isSelectCloseEffect = false;
    private boolean isOpenPointReward = false;
    private IPLVChatPlaybackCallDataListener chatPlaybackDataListener = new PLVChatPlaybackCallDataExListener() { // from class: com.easefun.polyv.livecloudclass.modules.chatroom.PLVLCChatFragment.5
        @Override // com.plv.livescenes.playback.chat.IPLVChatPlaybackCallDataListener
        public void onData(List<PLVChatPlaybackData> list) {
        }

        @Override // com.plv.livescenes.playback.chat.IPLVChatPlaybackCallDataListener
        public void onDataCleared() {
            PLVLCChatFragment.this.removeChatMessageToList((String) null, true);
        }

        @Override // com.plv.livescenes.playback.chat.IPLVChatPlaybackCallDataListener
        public void onDataInserted(int i2, int i3, List<PLVChatPlaybackData> list, boolean z2, int i4) {
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
                PLVLCChatFragment.this.addChatMessageToListHead(arrayList);
                return;
            }
            if (PLVLCChatFragment.this.chatCommonMessageList != null && PLVLCChatFragment.this.chatCommonMessageList.getItemCount() == 0) {
                z3 = true;
            }
            PLVLCChatFragment.this.addChatMessageToList(arrayList, z3);
        }

        @Override // com.plv.livescenes.playback.chat.IPLVChatPlaybackCallDataListener
        public void onDataRemoved(int i2, int i3, List<PLVChatPlaybackData> list, boolean z2) {
            PLVLCChatFragment.this.removeChatMessageToList(i2, i3);
        }

        @Override // com.plv.livescenes.playback.chat.IPLVChatPlaybackCallDataListener
        public void onHasNotAddedData() {
            if (PLVLCChatFragment.this.unreadMsgTv == null || PLVLCChatFragment.this.unreadMsgTv.getVisibility() == 0) {
                return;
            }
            PLVLCChatFragment.this.unreadMsgTv.setText("有新消息，点击查看");
            PLVLCChatFragment.this.unreadMsgTv.setVisibility(0);
        }

        @Override // com.plv.livescenes.playback.chat.PLVChatPlaybackCallDataExListener
        public void onLoadPreviousEnabled(boolean z2, boolean z3) {
            if (PLVLCChatFragment.this.swipeLoadView != null) {
                PLVLCChatFragment.this.swipeLoadView.setEnabled(z2);
            }
            if (z2 || z3) {
                return;
            }
            ToastUtils.showShort(R.string.plv_chat_toast_history_all_loaded);
        }

        @Override // com.plv.livescenes.playback.chat.IPLVChatPlaybackCallDataListener
        public void onLoadPreviousFinish() {
            if (PLVLCChatFragment.this.swipeLoadView != null) {
                PLVLCChatFragment.this.swipeLoadView.setRefreshing(false);
            }
        }

        @Override // com.plv.livescenes.playback.chat.IPLVChatPlaybackCallDataListener
        public void onManager(IPLVChatPlaybackManager iPLVChatPlaybackManager) {
            PLVLCChatFragment.this.chatPlaybackManager = iPLVChatPlaybackManager;
        }
    };
    private IPLVChatroomContract.IChatroomView chatroomView = new PLVAbsChatroomView() { // from class: com.easefun.polyv.livecloudclass.modules.chatroom.PLVLCChatFragment.8
        @Override // com.easefun.polyv.livecommon.module.modules.chatroom.view.PLVAbsChatroomView, com.easefun.polyv.livecommon.module.modules.chatroom.contract.IPLVChatroomContract.IChatroomView
        public int getSpeakEmojiSize() {
            return ConvertUtils.dp2px(16.0f);
        }

        @Override // com.easefun.polyv.livecommon.module.modules.chatroom.view.PLVAbsChatroomView, com.easefun.polyv.livecommon.module.modules.chatroom.contract.IPLVChatroomContract.IChatroomView
        public void onCloseRoomEvent(@NonNull PLVCloseRoomEvent pLVCloseRoomEvent) {
            super.onCloseRoomEvent(pLVCloseRoomEvent);
            if (PLVLCChatFragment.this.isChatPlaybackLayout) {
                return;
            }
            PLVLCChatFragment.this.acceptCloseRoomEvent(pLVCloseRoomEvent);
        }

        @Override // com.easefun.polyv.livecommon.module.modules.chatroom.view.PLVAbsChatroomView, com.easefun.polyv.livecommon.module.modules.chatroom.contract.IPLVChatroomContract.IChatroomView
        public void onFocusModeEvent(@NonNull PLVFocusModeEvent pLVFocusModeEvent) {
            super.onFocusModeEvent(pLVFocusModeEvent);
            if (PLVLCChatFragment.this.isChatPlaybackLayout) {
                return;
            }
            PLVLCChatFragment.this.acceptFocusModeEvent(pLVFocusModeEvent);
        }

        @Override // com.easefun.polyv.livecommon.module.modules.chatroom.view.PLVAbsChatroomView, com.easefun.polyv.livecommon.module.modules.chatroom.contract.IPLVChatroomContract.IChatroomView
        public void onHistoryDataList(List<PLVBaseViewData<PLVBaseEvent>> list, int i2, boolean z2, int i3) {
            super.onHistoryDataList(list, i2, z2, i3);
            if (PLVLCChatFragment.this.isChatPlaybackLayout) {
                return;
            }
            if (PLVLCChatFragment.this.swipeLoadView != null) {
                PLVLCChatFragment.this.swipeLoadView.setRefreshing(false);
                PLVLCChatFragment.this.swipeLoadView.setEnabled(true);
            }
            if (!list.isEmpty()) {
                PLVLCChatFragment.this.addChatHistoryToList(list, i2 == 1);
            }
            if (z2) {
                ToastUtils.showShort(R.string.plv_chat_toast_history_all_loaded);
                if (PLVLCChatFragment.this.swipeLoadView != null) {
                    PLVLCChatFragment.this.swipeLoadView.setEnabled(false);
                }
            }
        }

        @Override // com.easefun.polyv.livecommon.module.modules.chatroom.view.PLVAbsChatroomView, com.easefun.polyv.livecommon.module.modules.chatroom.contract.IPLVChatroomContract.IChatroomView
        public void onHistoryRequestFailed(String str, Throwable th, int i2) {
            super.onHistoryRequestFailed(str, th, i2);
            if (PLVLCChatFragment.this.isChatPlaybackLayout) {
                return;
            }
            if (PLVLCChatFragment.this.swipeLoadView != null) {
                PLVLCChatFragment.this.swipeLoadView.setRefreshing(false);
                PLVLCChatFragment.this.swipeLoadView.setEnabled(true);
            }
            if (PLVLCChatFragment.this.chatroomPresenter == null || i2 != PLVLCChatFragment.this.chatroomPresenter.getViewIndex(PLVLCChatFragment.this.chatroomView)) {
                return;
            }
            ToastUtils.showShort(PLVLCChatFragment.this.getString(R.string.plv_chat_toast_history_load_failed) + ": " + str);
        }

        @Override // com.easefun.polyv.livecommon.module.modules.chatroom.view.PLVAbsChatroomView, com.easefun.polyv.livecommon.module.modules.chatroom.contract.IPLVChatroomContract.IChatroomView
        public void onLikesEvent(@NonNull PLVLikesEvent pLVLikesEvent) {
            super.onLikesEvent(pLVLikesEvent);
            PLVLCChatFragment.this.acceptLikesMessage(pLVLikesEvent.getCount());
        }

        @Override // com.easefun.polyv.livecommon.module.modules.chatroom.view.PLVAbsChatroomView, com.easefun.polyv.livecommon.module.modules.chatroom.contract.IPLVChatroomContract.IChatroomView
        public void onLoadEmotionMessage(@Nullable PLVChatEmotionEvent pLVChatEmotionEvent) {
            super.onLoadEmotionMessage(pLVChatEmotionEvent);
            if (PLVLCChatFragment.this.isChatPlaybackLayout || pLVChatEmotionEvent == null) {
                return;
            }
            ArrayList arrayList = new ArrayList();
            arrayList.add(new PLVBaseViewData(pLVChatEmotionEvent, 8, pLVChatEmotionEvent.isSpecialTypeOrMe() ? new PLVSpecialTypeTag(pLVChatEmotionEvent.getUserId()) : null));
            PLVLCChatFragment.this.addChatMessageToList(arrayList, pLVChatEmotionEvent.isLocal());
        }

        @Override // com.easefun.polyv.livecommon.module.modules.chatroom.view.PLVAbsChatroomView, com.easefun.polyv.livecommon.module.modules.chatroom.contract.IPLVChatroomContract.IChatroomView
        public void onLocalImageMessage(@Nullable PolyvSendLocalImgEvent polyvSendLocalImgEvent) {
            super.onLocalImageMessage(polyvSendLocalImgEvent);
            if (PLVLCChatFragment.this.isChatPlaybackLayout) {
                return;
            }
            ArrayList arrayList = new ArrayList();
            arrayList.add(new PLVBaseViewData(polyvSendLocalImgEvent, 3, new PLVSpecialTypeTag(polyvSendLocalImgEvent.getUserId())));
            PLVLCChatFragment.this.addChatMessageToList(arrayList, true);
        }

        @Override // com.easefun.polyv.livecommon.module.modules.chatroom.view.PLVAbsChatroomView, com.easefun.polyv.livecommon.module.modules.chatroom.contract.IPLVChatroomContract.IChatroomView
        public void onLocalSpeakMessage(@Nullable PolyvLocalMessage polyvLocalMessage) {
            super.onLocalSpeakMessage(polyvLocalMessage);
            if (PLVLCChatFragment.this.isChatPlaybackLayout || polyvLocalMessage == null) {
                return;
            }
            final ArrayList arrayList = new ArrayList();
            arrayList.add(new PLVBaseViewData(polyvLocalMessage, 1, new PLVSpecialTypeTag(polyvLocalMessage.getUserId())));
            if (PLVLCChatFragment.this.isShowKeyBoard(new PLVInputFragment.OnceHideKeyBoardListener() { // from class: com.easefun.polyv.livecloudclass.modules.chatroom.PLVLCChatFragment.8.2
                @Override // com.easefun.polyv.livecommon.ui.window.PLVInputFragment.OnceHideKeyBoardListener
                public void call() {
                    PLVLCChatFragment.this.addChatMessageToList(arrayList, true);
                }
            })) {
                return;
            }
            PLVLCChatFragment.this.addChatMessageToList(arrayList, true);
        }

        @Override // com.easefun.polyv.livecommon.module.modules.chatroom.view.PLVAbsChatroomView, com.easefun.polyv.livecommon.module.modules.chatroom.contract.IPLVChatroomContract.IChatroomView
        public void onLoginError(@Nullable PLVLoginEvent pLVLoginEvent, final String str, final int i2) {
            super.onLoginError(pLVLoginEvent, str, i2);
            PLVAppUtils.postToMainThread(new Runnable() { // from class: com.easefun.polyv.livecloudclass.modules.chatroom.PLVLCChatFragment.8.1
                @Override // java.lang.Runnable
                public void run() {
                    PLVToast.Builder.create().setText(PLVSugarUtil.format("{}({})", str, Integer.valueOf(i2))).show();
                    Activity activity = (Activity) PLVSugarUtil.firstNotNull(PLVLCChatFragment.this.getActivity(), ActivityUtils.getTopActivity());
                    if (activity != null) {
                        activity.finish();
                    }
                }
            });
        }

        @Override // com.easefun.polyv.livecommon.module.modules.chatroom.view.PLVAbsChatroomView, com.easefun.polyv.livecommon.module.modules.chatroom.contract.IPLVChatroomContract.IChatroomView
        public void onLoginEvent(@NonNull PLVLoginEvent pLVLoginEvent) {
            super.onLoginEvent(pLVLoginEvent);
            PLVLCChatFragment.this.acceptLoginEvent(pLVLoginEvent);
        }

        @Override // com.easefun.polyv.livecommon.module.modules.chatroom.view.PLVAbsChatroomView, com.easefun.polyv.livecommon.module.modules.chatroom.contract.IPLVChatroomContract.IChatroomView
        public void onNewsPushCancelMessage() {
            super.onNewsPushCancelMessage();
            PLVLCChatFragment.this.acceptNewsPushCancelMessage();
        }

        @Override // com.easefun.polyv.livecommon.module.modules.chatroom.view.PLVAbsChatroomView, com.easefun.polyv.livecommon.module.modules.chatroom.contract.IPLVChatroomContract.IChatroomView
        public void onNewsPushStartMessage(@NonNull PLVNewsPushStartEvent pLVNewsPushStartEvent) {
            super.onNewsPushStartMessage(pLVNewsPushStartEvent);
            PLVLCChatFragment.this.acceptNewsPushStartMessage(pLVNewsPushStartEvent);
        }

        @Override // com.easefun.polyv.livecommon.module.modules.chatroom.view.PLVAbsChatroomView, com.easefun.polyv.livecommon.module.modules.chatroom.contract.IPLVChatroomContract.IChatroomView
        public void onRemoveMessageEvent(@Nullable String str, boolean z2) {
            super.onRemoveMessageEvent(str, z2);
            if (PLVLCChatFragment.this.isChatPlaybackLayout) {
                return;
            }
            PLVLCChatFragment.this.removeChatMessageToList(str, z2);
        }

        @Override // com.easefun.polyv.livecommon.module.modules.chatroom.view.PLVAbsChatroomView, com.easefun.polyv.livecommon.module.modules.chatroom.contract.IPLVChatroomContract.IChatroomView
        public void onRewardEvent(@NonNull PLVRewardEvent pLVRewardEvent) {
            super.onRewardEvent(pLVRewardEvent);
            PLVLCChatFragment.this.acceptPointRewardMessage(pLVRewardEvent);
        }

        @Override // com.easefun.polyv.livecommon.module.modules.chatroom.view.PLVAbsChatroomView, com.easefun.polyv.livecommon.module.modules.chatroom.contract.IPLVChatroomContract.IChatroomView
        public void onSpeakEvent(@NonNull PLVSpeakEvent pLVSpeakEvent) {
            super.onSpeakEvent(pLVSpeakEvent);
            if (PLVLCChatFragment.this.isChatPlaybackLayout) {
                return;
            }
            PLVLCChatFragment.this.acceptSpeakEvent(pLVSpeakEvent);
        }

        @Override // com.easefun.polyv.livecommon.module.modules.chatroom.view.PLVAbsChatroomView, com.easefun.polyv.livecommon.module.modules.chatroom.contract.IPLVChatroomContract.IChatroomView
        public void onSpeakImgDataList(List<PLVBaseViewData> list) {
            super.onSpeakImgDataList(list);
            if (PLVLCChatFragment.this.isChatPlaybackLayout) {
                return;
            }
            PLVLCChatFragment.this.addChatMessageToList(list, false);
        }

        @Override // com.easefun.polyv.livecommon.module.modules.chatroom.view.PLVAbsChatroomView, com.easefun.polyv.livecommon.module.modules.chatroom.contract.IPLVChatroomContract.IChatroomView
        public void setPresenter(@NonNull IPLVChatroomContract.IChatroomPresenter iChatroomPresenter) {
            super.setPresenter(iChatroomPresenter);
            PLVLCChatFragment.this.chatroomPresenter = iChatroomPresenter;
        }
    };
    private TextWatcher inputTextWatcher = new TextWatcher() { // from class: com.easefun.polyv.livecloudclass.modules.chatroom.PLVLCChatFragment.17
        @Override // android.text.TextWatcher
        public void afterTextChanged(Editable editable) {
        }

        @Override // android.text.TextWatcher
        public void beforeTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
        }

        @Override // android.text.TextWatcher
        public void onTextChanged(CharSequence charSequence, int i2, int i3, int i4) {
            if (charSequence == null || charSequence.length() <= 0) {
                PLVLCChatFragment.this.sendMsgTv.setSelected(false);
                PLVLCChatFragment.this.sendMsgTv.setEnabled(false);
            } else {
                PLVLCChatFragment.this.sendMsgTv.setEnabled(true);
                PLVLCChatFragment.this.sendMsgTv.setSelected(true);
            }
        }
    };

    public interface OnViewActionListener {
        void onClickDynamicFunction(String str);

        void onShowBulletinAction();

        void onShowEffectAction(boolean z2);

        void onShowRewardAction();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void acceptCloseRoomEvent(PLVCloseRoomEvent pLVCloseRoomEvent) {
        this.isCloseRoomStatus = pLVCloseRoomEvent.getValue().isClosed();
        PLVAppUtils.postToMainThread(new Runnable() { // from class: com.easefun.polyv.livecloudclass.modules.chatroom.PLVLCChatFragment.7
            @Override // java.lang.Runnable
            public void run() {
                PLVLCChatFragment pLVLCChatFragment = PLVLCChatFragment.this;
                pLVLCChatFragment.updateViewByRoomStatusChanged(pLVLCChatFragment.isCloseRoomStatus);
                if (PLVLCChatFragment.this.chatCommonMessageList == null || !PLVLCChatFragment.this.chatCommonMessageList.isLandscapeLayout()) {
                    ToastUtils.showLong(PLVLCChatFragment.this.isCloseRoomStatus ? R.string.plv_chat_toast_chatroom_close : R.string.plv_chat_toast_chatroom_open);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void acceptFocusModeEvent(PLVFocusModeEvent pLVFocusModeEvent) {
        this.isFocusModeStatus = pLVFocusModeEvent.isOpen();
        PLVAppUtils.postToMainThread(new Runnable() { // from class: com.easefun.polyv.livecloudclass.modules.chatroom.PLVLCChatFragment.6
            @Override // java.lang.Runnable
            public void run() {
                PLVLCChatFragment pLVLCChatFragment = PLVLCChatFragment.this;
                pLVLCChatFragment.updateViewByRoomStatusChanged(pLVLCChatFragment.isFocusModeStatus);
                if (PLVLCChatFragment.this.chatCommonMessageList != null) {
                    if (PLVLCChatFragment.this.isFocusModeStatus) {
                        PLVLCChatFragment.this.chatCommonMessageList.changeDisplayType(3);
                    } else {
                        PLVLCChatFragment.this.chatCommonMessageList.changeDisplayType(PLVLCChatFragment.this.isSelectOnlyTeacher ? 2 : 1);
                    }
                }
                if (PLVLCChatFragment.this.chatCommonMessageList == null || !PLVLCChatFragment.this.chatCommonMessageList.isLandscapeLayout()) {
                    ToastUtils.showLong(PLVLCChatFragment.this.isFocusModeStatus ? R.string.plv_chat_toast_focus_mode_open : R.string.plv_chat_toast_focus_mode_close);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void acceptLikesMessage(final int i2) {
        this.handler.post(new Runnable() { // from class: com.easefun.polyv.livecloudclass.modules.chatroom.PLVLCChatFragment.18
            @Override // java.lang.Runnable
            @SuppressLint({"SetTextI18n"})
            public void run() {
                PLVLCChatFragment.this.startAddLoveIconTask(200L, Math.min(5, i2));
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void acceptLoginEvent(final PLVLoginEvent pLVLoginEvent) {
        this.handler.post(new Runnable() { // from class: com.easefun.polyv.livecloudclass.modules.chatroom.PLVLCChatFragment.9
            @Override // java.lang.Runnable
            public void run() {
                if (PLVLCChatFragment.this.greetingTv == null || !PLVLCChatFragment.this.isShowGreeting) {
                    return;
                }
                PLVLCChatFragment.this.greetingTv.acceptLoginEvent(pLVLoginEvent);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void acceptNewsPushCancelMessage() {
        PLVCardPushManager pLVCardPushManager = this.cardPushManager;
        if (pLVCardPushManager != null) {
            pLVCardPushManager.acceptNewsPushCancelMessage();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void acceptNewsPushStartMessage(PLVNewsPushStartEvent pLVNewsPushStartEvent) {
        PLVCardPushManager pLVCardPushManager = this.cardPushManager;
        if (pLVCardPushManager != null) {
            pLVCardPushManager.acceptNewsPushStartMessage(this.chatroomPresenter, pLVNewsPushStartEvent);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void acceptPointRewardMessage(PLVRewardEvent pLVRewardEvent) {
        if (this.pointRewardEventProducer != null) {
            ScreenUtils.isPortrait();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void acceptSpeakEvent(PLVSpeakEvent pLVSpeakEvent) {
        PLVLCBulletinTextView pLVLCBulletinTextView;
        if (!PLVSocketUserConstant.USERTYPE_MANAGER.equals(pLVSpeakEvent.getUser().getUserType()) || (pLVLCBulletinTextView = this.bulletinTv) == null) {
            return;
        }
        pLVLCBulletinTextView.startMarquee((CharSequence) pLVSpeakEvent.getObjects()[0]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addChatHistoryToList(List<PLVBaseViewData<PLVBaseEvent>> list, boolean z2) {
        PLVLCChatCommonMessageList pLVLCChatCommonMessageList = this.chatCommonMessageList;
        if (pLVLCChatCommonMessageList != null) {
            pLVLCChatCommonMessageList.addChatHistoryToList(list, z2, false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addChatMessageToList(final List<PLVBaseViewData> list, final boolean z2) {
        Runnable runnable = new Runnable() { // from class: com.easefun.polyv.livecloudclass.modules.chatroom.PLVLCChatFragment.10
            @Override // java.lang.Runnable
            public void run() {
                if (PLVLCChatFragment.this.chatCommonMessageList != null) {
                    PLVLCChatFragment.this.chatCommonMessageList.addChatMessageToList(list, z2, false);
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
            pLVLCChatCommonMessageList.addChatMessageToListHead(list, false, false);
        }
    }

    private void changeEmojiTab(boolean z2) {
        this.tabEmojiIv.setSelected(z2);
        this.tabPersonalIv.setSelected(z2);
        int color = Color.parseColor("#FF2B2C35");
        int color2 = Color.parseColor("#FF202127");
        this.tabEmojiIv.setBackgroundColor(z2 ? color : color2);
        ImageView imageView = this.tabPersonalIv;
        if (z2) {
            color = color2;
        }
        imageView.setBackgroundColor(color);
        if (z2) {
            this.emojiRv.setVisibility(0);
            this.sendMsgTv.setVisibility(0);
            this.deleteMsgIv.setVisibility(0);
            this.emojiPersonalRv.setVisibility(4);
            return;
        }
        this.emojiRv.setVisibility(4);
        this.sendMsgTv.setVisibility(4);
        this.deleteMsgIv.setVisibility(4);
        this.emojiPersonalRv.setVisibility(0);
    }

    private boolean checkCanSendImg() {
        if (this.isCloseRoomStatus) {
            ToastUtils.showShort("聊天室已关闭，无法发送图片");
            return false;
        }
        if (!this.isFocusModeStatus) {
            return true;
        }
        ToastUtils.showShort("专注模式下无法使用");
        return false;
    }

    private void destroyPointRewardEffectQueue() {
        IPLVPointRewardEventProducer iPLVPointRewardEventProducer = this.pointRewardEventProducer;
        if (iPLVPointRewardEventProducer != null) {
            iPLVPointRewardEventProducer.destroy();
        }
    }

    private void initChatMoreLayout() {
        PLVLCChatMoreLayout pLVLCChatMoreLayout = (PLVLCChatMoreLayout) findViewById(R.id.plvlc_chat_more_layout);
        this.chatMoreLayout = pLVLCChatMoreLayout;
        if (!this.isLiveType) {
            pLVLCChatMoreLayout.updateFunctionShow(PLVLCChatMoreLayout.CHAT_FUNCTION_TYPE_BULLETIN, false);
        }
        this.chatMoreLayout.setFunctionListener(new PLVLCChatFunctionListener() { // from class: com.easefun.polyv.livecloudclass.modules.chatroom.PLVLCChatFragment.4
            @Override // com.easefun.polyv.livecloudclass.modules.chatroom.chatmore.PLVLCChatFunctionListener
            public void onFunctionCallback(String str, String str2) {
                PLVLCChatFragment pLVLCChatFragment;
                int i2;
                PLVLCChatFragment pLVLCChatFragment2;
                int i3;
                str.hashCode();
                switch (str) {
                    case "CHAT_FUNCTION_TYPE_OPEN_CAMERA":
                        PLVLCChatFragment.this.requestOpenCamera();
                        break;
                    case "CHAT_FUNCTION_TYPE_SEND_IMAGE":
                        PLVLCChatFragment.this.requestSelectImg();
                        break;
                    case "CHAT_FUNCTION_TYPE_EFFECT":
                        PLVLCChatFragment.this.hideSoftInputAndPopupLayout();
                        PLVLCChatFragment.this.isSelectCloseEffect = !r9.isSelectCloseEffect;
                        PLVChatFunctionVO functionByType = PLVLCChatFragment.this.chatMoreLayout.getFunctionByType(PLVLCChatMoreLayout.CHAT_FUNCTION_TYPE_EFFECT);
                        if (functionByType != null) {
                            functionByType.setSelected(PLVLCChatFragment.this.isSelectCloseEffect);
                            if (PLVLCChatFragment.this.isSelectCloseEffect) {
                                pLVLCChatFragment = PLVLCChatFragment.this;
                                i2 = R.string.plv_chat_view_show_effect;
                            } else {
                                pLVLCChatFragment = PLVLCChatFragment.this;
                                i2 = R.string.plv_chat_view_close_effect;
                            }
                            functionByType.setName(pLVLCChatFragment.getString(i2));
                            PLVLCChatFragment.this.chatMoreLayout.updateFunctionStatus(functionByType);
                        }
                        if (PLVLCChatFragment.this.isSelectCloseEffect) {
                            PLVLCChatFragment.this.polyvPointRewardEffectWidget.hideAndReleaseEffect();
                            PLVLCChatFragment.this.svgaHelper.clear();
                            PLVLCChatFragment.this.rewardSvgImage.setVisibility(4);
                        } else {
                            PLVLCChatFragment.this.polyvPointRewardEffectWidget.showAndPrepareEffect();
                            PLVLCChatFragment.this.rewardSvgImage.setVisibility(0);
                        }
                        if (PLVLCChatFragment.this.onViewActionListener != null) {
                            PLVLCChatFragment.this.onViewActionListener.onShowEffectAction(!PLVLCChatFragment.this.isSelectCloseEffect);
                            break;
                        }
                        break;
                    case "CHAT_FUNCTION_TYPE_BULLETIN":
                        PLVLCChatFragment.this.hideSoftInputAndPopupLayout();
                        if (PLVLCChatFragment.this.onViewActionListener != null) {
                            PLVLCChatFragment.this.onViewActionListener.onShowBulletinAction();
                            break;
                        }
                        break;
                    case "CHAT_FUNCTION_TYPE_ONLY_TEACHER":
                        if (!PLVLCChatFragment.this.isFocusModeStatus) {
                            PLVLCChatFragment.this.isSelectOnlyTeacher = !r9.isSelectOnlyTeacher;
                            PLVChatFunctionVO functionByType2 = PLVLCChatFragment.this.chatMoreLayout.getFunctionByType(PLVLCChatMoreLayout.CHAT_FUNCTION_TYPE_ONLY_TEACHER);
                            if (functionByType2 != null) {
                                functionByType2.setSelected(PLVLCChatFragment.this.isSelectOnlyTeacher);
                                if (PLVLCChatFragment.this.isSelectOnlyTeacher) {
                                    pLVLCChatFragment2 = PLVLCChatFragment.this;
                                    i3 = R.string.plv_chat_view_all_message;
                                } else {
                                    pLVLCChatFragment2 = PLVLCChatFragment.this;
                                    i3 = R.string.plv_chat_view_special_message;
                                }
                                functionByType2.setName(pLVLCChatFragment2.getString(i3));
                                PLVLCChatFragment.this.chatMoreLayout.updateFunctionStatus(functionByType2);
                            }
                            if (PLVLCChatFragment.this.chatCommonMessageList != null) {
                                PLVLCChatFragment.this.chatCommonMessageList.changeDisplayType(PLVLCChatFragment.this.isSelectOnlyTeacher ? 2 : 1);
                                break;
                            }
                        } else {
                            ToastUtils.showShort("专注模式下无法使用");
                            break;
                        }
                        break;
                    default:
                        PLVLCChatFragment.this.hideSoftInputAndPopupLayout();
                        if (PLVLCChatFragment.this.onViewActionListener != null) {
                            PLVLCChatFragment.this.onViewActionListener.onClickDynamicFunction(str2);
                            break;
                        }
                        break;
                }
            }
        });
    }

    private void initPointRewardEffectQueue() {
        if (this.pointRewardEventProducer == null) {
            PLVPointRewardEffectQueue pLVPointRewardEffectQueue = new PLVPointRewardEffectQueue();
            this.pointRewardEventProducer = pLVPointRewardEffectQueue;
            PLVPointRewardEffectWidget pLVPointRewardEffectWidget = this.polyvPointRewardEffectWidget;
            if (pLVPointRewardEffectWidget != null) {
                pLVPointRewardEffectWidget.setEventProducer(pLVPointRewardEffectQueue);
            }
        }
    }

    private void initView() {
        IPLVChatroomContract.IChatroomPresenter iChatroomPresenter;
        TextView textView = (TextView) findViewById(R.id.unread_msg_tv);
        this.unreadMsgTv = textView;
        PLVLCChatCommonMessageList pLVLCChatCommonMessageList = this.chatCommonMessageList;
        if (pLVLCChatCommonMessageList != null) {
            pLVLCChatCommonMessageList.addUnreadView(textView);
            this.chatCommonMessageList.addOnUnreadCountChangeListener(new PLVMessageRecyclerView.OnUnreadCountChangeListener() { // from class: com.easefun.polyv.livecloudclass.modules.chatroom.PLVLCChatFragment.1
                @Override // com.easefun.polyv.livecommon.ui.widget.PLVMessageRecyclerView.OnUnreadCountChangeListener
                public void onChange(int i2) {
                    PLVLCChatFragment.this.unreadMsgTv.setText("有" + i2 + "条新消息，点击查看");
                }
            });
        }
        EditText editText = (EditText) findViewById(R.id.input_et);
        this.inputEt = editText;
        editText.addTextChangedListener(this.inputTextWatcher);
        if (this.isChatPlaybackLayout) {
            this.inputEt.setHint("聊天室暂时关闭");
            this.inputEt.setEnabled(false);
        }
        ImageView imageView = (ImageView) findViewById(R.id.toggle_emoji_iv);
        this.toggleEmojiIv = imageView;
        imageView.setOnClickListener(this);
        ImageView imageView2 = (ImageView) findViewById(R.id.toggle_more_iv);
        this.toggleMoreIv = imageView2;
        if (this.isChatPlaybackLayout) {
            imageView2.setVisibility(8);
            this.toggleEmojiIv.setEnabled(false);
            this.toggleEmojiIv.setAlpha(0.5f);
        } else {
            imageView2.setVisibility(0);
            this.toggleEmojiIv.setEnabled(true);
            this.toggleEmojiIv.setAlpha(1.0f);
        }
        this.toggleMoreIv.setOnClickListener(this);
        SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_load_view);
        this.swipeLoadView = swipeRefreshLayout;
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        this.swipeLoadView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() { // from class: com.easefun.polyv.livecloudclass.modules.chatroom.PLVLCChatFragment.2
            @Override // androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
            public void onRefresh() {
                if (PLVLCChatFragment.this.isChatPlaybackLayout) {
                    if (PLVLCChatFragment.this.chatPlaybackManager != null) {
                        PLVLCChatFragment.this.chatPlaybackManager.loadPrevious();
                    }
                } else if (PLVLCChatFragment.this.chatroomPresenter != null) {
                    PLVLCChatFragment.this.chatroomPresenter.requestChatHistory(PLVLCChatFragment.this.chatroomPresenter.getViewIndex(PLVLCChatFragment.this.chatroomView));
                }
            }
        });
        PLVLCChatCommonMessageList pLVLCChatCommonMessageList2 = this.chatCommonMessageList;
        if (pLVLCChatCommonMessageList2 != null && pLVLCChatCommonMessageList2.attachToParent(this.swipeLoadView, false) && (iChatroomPresenter = this.chatroomPresenter) != null) {
            this.chatCommonMessageList.setMsgIndex(iChatroomPresenter.getViewIndex(this.chatroomView));
            if (!this.isChatPlaybackLayout && this.chatroomPresenter.getChatHistoryTime() == 0) {
                IPLVChatroomContract.IChatroomPresenter iChatroomPresenter2 = this.chatroomPresenter;
                iChatroomPresenter2.requestChatHistory(iChatroomPresenter2.getViewIndex(this.chatroomView));
            }
        }
        initChatMoreLayout();
        this.emojiLy = (ViewGroup) findViewById(R.id.emoji_ly);
        TextView textView2 = (TextView) findViewById(R.id.send_msg_tv);
        this.sendMsgTv = textView2;
        textView2.setOnClickListener(this);
        ImageView imageView3 = (ImageView) findViewById(R.id.delete_msg_iv);
        this.deleteMsgIv = imageView3;
        imageView3.setOnClickListener(this);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.emoji_rv);
        this.emojiRv = recyclerView;
        PLVChatroomUtils.initEmojiList(recyclerView, this.inputEt);
        this.emojiPersonalRv = (RecyclerView) findViewById(R.id.emoji_personal_rv);
        ImageView imageView4 = (ImageView) findViewById(R.id.plvlc_emoji_tab_emoji_iv);
        this.tabEmojiIv = imageView4;
        imageView4.setSelected(true);
        this.tabEmojiIv.setOnClickListener(this);
        ImageView imageView5 = (ImageView) findViewById(R.id.plvlc_emoji_tab_personal_iv);
        this.tabPersonalIv = imageView5;
        imageView5.setVisibility(0);
        this.tabPersonalIv.setOnClickListener(this);
        this.emotionPreviewWindow = new PLVImagePreviewPopupWindow(getContext());
        this.likesLy = (ViewGroup) findViewById(R.id.likes_ly);
        PLVLCLikeIconView pLVLCLikeIconView = (PLVLCLikeIconView) findViewById(R.id.likes_view);
        this.likesView = pLVLCLikeIconView;
        pLVLCLikeIconView.setOnButtonClickListener(this);
        this.likesCountTv = (TextView) findViewById(R.id.likes_count_tv);
        long j2 = this.likesCount;
        if (j2 != 0) {
            this.likesCountTv.setText(StringUtils.toWString(j2));
        }
        ImageView imageView6 = (ImageView) findViewById(R.id.plvlc_iv_show_point_reward);
        this.rewardIv = imageView6;
        imageView6.setOnClickListener(this);
        this.rewardIv.setVisibility(this.isOpenPointReward ? 0 : 8);
        this.rewardSvgImage = (SVGAImageView) findViewById(R.id.plvlc_reward_svg);
        this.svgaParser = new SVGAParser(getContext());
        PLVRewardSVGAHelper pLVRewardSVGAHelper = new PLVRewardSVGAHelper();
        this.svgaHelper = pLVRewardSVGAHelper;
        pLVRewardSVGAHelper.init(this.rewardSvgImage, this.svgaParser);
        PLVPointRewardEffectWidget pLVPointRewardEffectWidget = (PLVPointRewardEffectWidget) findViewById(R.id.plvlc_point_reward_effect);
        this.polyvPointRewardEffectWidget = pLVPointRewardEffectWidget;
        pLVPointRewardEffectWidget.setEventProducer(this.pointRewardEventProducer);
        this.greetingTv = (PLVLCGreetingTextView) findViewById(R.id.greeting_tv);
        this.bulletinTv = (PLVLCBulletinTextView) findViewById(R.id.bulletin_tv);
        TextView textView3 = (TextView) findViewById(R.id.plvlc_chat_playback_tips_tv);
        this.chatPlaybackTipsTv = textView3;
        if (this.isChatPlaybackLayout) {
            textView3.setVisibility(0);
            TextView textView4 = this.chatPlaybackTipsTv;
            Runnable runnable = new Runnable() { // from class: com.easefun.polyv.livecloudclass.modules.chatroom.PLVLCChatFragment.3
                @Override // java.lang.Runnable
                public void run() {
                    PLVLCChatFragment.this.chatPlaybackTipsTv.setVisibility(8);
                }
            };
            this.playbackTipsRunnable = runnable;
            textView4.postDelayed(runnable, 5000L);
        }
        this.cardEnterView = (ImageView) findViewById(R.id.card_enter_view);
        this.cardEnterCdTv = (TextView) findViewById(R.id.card_enter_cd_tv);
        PLVTriangleIndicateTextView pLVTriangleIndicateTextView = (PLVTriangleIndicateTextView) findViewById(R.id.card_enter_tips_view);
        this.cardEnterTipsView = pLVTriangleIndicateTextView;
        PLVCardPushManager pLVCardPushManager = this.cardPushManager;
        if (pLVCardPushManager != null) {
            pLVCardPushManager.registerView(this.cardEnterView, this.cardEnterCdTv, pLVTriangleIndicateTextView);
        }
        addPopupButton(this.toggleEmojiIv);
        addPopupLayout(this.emojiLy);
        addPopupButton(this.toggleMoreIv);
        addPopupLayout(this.chatMoreLayout);
        acceptFunctionSwitchData(this.functionSwitchData);
        acceptEmotionImageData(this.emotionImages);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void openCamera() {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        String str = System.currentTimeMillis() + ".jpg";
        if (Build.VERSION.SDK_INT >= 29) {
            Environment.getExternalStorageState();
            ContentValues contentValues = new ContentValues();
            contentValues.put("_display_name", str);
            this.takePictureUri = getContext().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
        } else {
            if (getContext() == null || getContext().getApplicationContext() == null) {
                return;
            }
            this.takePictureFilePath = new File(PLVSDCardUtils.createPath(getContext(), "PLVChatImg"), str);
            this.takePictureUri = FileProvider.getUriForFile(getContext(), getContext().getApplicationContext().getPackageName() + ".plvfileprovider", this.takePictureFilePath);
        }
        intent.putExtra("output", this.takePictureUri);
        intent.addFlags(2);
        startActivityForResult(intent, 2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeChatMessageToList(int i2, int i3) {
        PLVLCChatCommonMessageList pLVLCChatCommonMessageList = this.chatCommonMessageList;
        if (pLVLCChatCommonMessageList != null) {
            pLVLCChatCommonMessageList.removeChatMessage(i2, i3, false);
            if (this.chatCommonMessageList.canScrollVertically(1)) {
                return;
            }
            PLVLCChatCommonMessageList pLVLCChatCommonMessageList2 = this.chatCommonMessageList;
            pLVLCChatCommonMessageList2.scrollToPosition(pLVLCChatCommonMessageList2.getItemCount() - 1);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void requestOpenCamera() {
        if (checkCanSendImg()) {
            ArrayList arrayList = new ArrayList(2);
            arrayList.add(Permission.WRITE_EXTERNAL_STORAGE);
            arrayList.add(Permission.CAMERA);
            PLVFastPermission.getInstance().start((Activity) getContext(), arrayList, new PLVOnPermissionCallback() { // from class: com.easefun.polyv.livecloudclass.modules.chatroom.PLVLCChatFragment.13
                @Override // com.plv.foundationsdk.permission.PLVOnPermissionCallback
                public void onAllGranted() {
                    PLVLCChatFragment.this.openCamera();
                }

                @Override // com.plv.foundationsdk.permission.PLVOnPermissionCallback
                public void onPartialGranted(ArrayList<String> arrayList2, ArrayList<String> arrayList3, ArrayList<String> arrayList4) {
                    if (arrayList4.isEmpty()) {
                        ToastUtils.showShort("请允许存储和相机权限后再拍摄");
                    } else {
                        PLVLCChatFragment.this.showRequestPermissionDialog("拍摄所需的存储或相机权限被拒绝，请到应用设置的权限管理中恢复");
                    }
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void requestSelectImg() {
        if (checkCanSendImg()) {
            ArrayList arrayList = new ArrayList(1);
            arrayList.add(Permission.WRITE_EXTERNAL_STORAGE);
            PLVFastPermission.getInstance().start((Activity) getContext(), arrayList, new PLVOnPermissionCallback() { // from class: com.easefun.polyv.livecloudclass.modules.chatroom.PLVLCChatFragment.12
                @Override // com.plv.foundationsdk.permission.PLVOnPermissionCallback
                public void onAllGranted() {
                    PLVLCChatFragment.this.selectImg();
                }

                @Override // com.plv.foundationsdk.permission.PLVOnPermissionCallback
                public void onPartialGranted(ArrayList<String> arrayList2, ArrayList<String> arrayList3, ArrayList<String> arrayList4) {
                    if (arrayList4.isEmpty()) {
                        ToastUtils.showShort("请允许存储权限后再发送图片");
                    } else {
                        PLVLCChatFragment.this.showRequestPermissionDialog("发送图片所需的存储权限被拒绝，请到应用设置的权限管理中恢复");
                    }
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void selectImg() {
        Intent intent = new Intent();
        intent.setType(MimeTypes.IMAGE_ALL);
        intent.setAction("android.intent.action.GET_CONTENT");
        intent.addCategory("android.intent.category.OPENABLE");
        startActivityForResult(Intent.createChooser(intent, "选择图片"), 1);
    }

    private boolean sendChatMessage(String str) {
        IPLVChatroomContract.IChatroomPresenter iChatroomPresenter;
        if (str.trim().length() == 0) {
            ToastUtils.showLong(R.string.plv_chat_toast_send_text_empty);
            return false;
        }
        PolyvLocalMessage polyvLocalMessage = new PolyvLocalMessage(str);
        if (this.isChatPlaybackLayout || (iChatroomPresenter = this.chatroomPresenter) == null) {
            return false;
        }
        Pair<Boolean, Integer> pairSendChatMessage = iChatroomPresenter.sendChatMessage(polyvLocalMessage);
        if (((Boolean) pairSendChatMessage.first).booleanValue()) {
            this.inputEt.setText("");
            hideSoftInputAndPopupLayout();
            return true;
        }
        ToastUtils.showShort(getString(R.string.plv_chat_toast_send_msg_failed) + ": " + pairSendChatMessage.second);
        return false;
    }

    private void sendImg(String str) {
        PolyvSendLocalImgEvent polyvSendLocalImgEvent = new PolyvSendLocalImgEvent();
        polyvSendLocalImgEvent.setImageFilePath(str);
        int[] pictureWh = PLVSendChatImageHelper.getPictureWh(str);
        polyvSendLocalImgEvent.setWidth(pictureWh[0]);
        polyvSendLocalImgEvent.setHeight(pictureWh[1]);
        IPLVChatroomContract.IChatroomPresenter iChatroomPresenter = this.chatroomPresenter;
        if (iChatroomPresenter != null) {
            iChatroomPresenter.sendChatImage(polyvSendLocalImgEvent);
        }
        hideSoftInputAndPopupLayout();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showRequestPermissionDialog(String str) {
        new AlertDialog.Builder(getContext()).setTitle("提示").setMessage(str).setPositiveButton("确定", new DialogInterface.OnClickListener() { // from class: com.easefun.polyv.livecloudclass.modules.chatroom.PLVLCChatFragment.15
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i2) {
                PLVFastPermission.getInstance().jump2Settings(PLVLCChatFragment.this.getContext());
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() { // from class: com.easefun.polyv.livecloudclass.modules.chatroom.PLVLCChatFragment.14
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i2) {
            }
        }).setCancelable(false).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startAddLoveIconTask(final long j2, final int i2) {
        if (i2 >= 1) {
            this.handler.postDelayed(new Runnable() { // from class: com.easefun.polyv.livecloudclass.modules.chatroom.PLVLCChatFragment.19
                @Override // java.lang.Runnable
                public void run() {
                    if (PLVLCChatFragment.this.likesView != null) {
                        PLVLCChatFragment.this.likesView.addLoveIcon(1);
                    }
                    PLVLCChatFragment.this.startAddLoveIconTask(j2, i2 - 1);
                }
            }, j2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateViewByRoomStatusChanged(boolean z2) {
        boolean z3 = (this.isCloseRoomStatus || this.isFocusModeStatus) ? false : true;
        if (z2) {
            hideSoftInputAndPopupLayout();
            if (this.recordInputMessage == null) {
                this.recordInputMessage = this.inputEt.getText();
            }
            this.inputEt.setText("");
        } else {
            Editable editable = this.recordInputMessage;
            if (editable != null && z3) {
                this.inputEt.setText(editable);
                this.recordInputMessage = null;
            }
        }
        this.inputEt.setHint(this.isCloseRoomStatus ? "聊天室已关闭" : this.isFocusModeStatus ? "当前为专注模式，无法发言" : "我也来聊几句");
        this.inputEt.setEnabled(z3);
        this.toggleEmojiIv.setEnabled(z3);
        this.toggleEmojiIv.setAlpha(z3 ? 1.0f : 0.5f);
    }

    public void acceptEmotionImageData(List<PLVEmotionImageVO.EmotionImage> list) {
        this.emotionImages = list;
        if (this.view == null || list == null || list.isEmpty()) {
            return;
        }
        PLVChatroomUtils.initEmojiPersonalList(this.emojiPersonalRv, 5, list, new PLVLCEmotionPersonalListAdapter.OnViewActionListener() { // from class: com.easefun.polyv.livecloudclass.modules.chatroom.PLVLCChatFragment.16
            @Override // com.easefun.polyv.livecloudclass.modules.chatroom.adapter.PLVLCEmotionPersonalListAdapter.OnViewActionListener
            public void onEmotionViewClick(PLVEmotionImageVO.EmotionImage emotionImage) {
                if (PLVLCChatFragment.this.isChatPlaybackLayout || PLVLCChatFragment.this.chatroomPresenter == null) {
                    return;
                }
                Pair<Boolean, Integer> pairSendChatEmotionImage = PLVLCChatFragment.this.chatroomPresenter.sendChatEmotionImage(new PLVChatEmotionEvent(emotionImage.getId()));
                if (((Boolean) pairSendChatEmotionImage.first).booleanValue()) {
                    PLVLCChatFragment.this.hideSoftInputAndPopupLayout();
                    return;
                }
                ToastUtils.showShort(PLVLCChatFragment.this.getString(R.string.plv_chat_toast_send_msg_failed) + ": " + pairSendChatEmotionImage.second);
            }

            @Override // com.easefun.polyv.livecloudclass.modules.chatroom.adapter.PLVLCEmotionPersonalListAdapter.OnViewActionListener
            public void onEmotionViewLongClick(PLVEmotionImageVO.EmotionImage emotionImage, View view) {
                PLVLCChatFragment.this.emotionPreviewWindow.showInTopCenter(emotionImage.getUrl(), view);
            }
        });
    }

    public void acceptFunctionSwitchData(List<PLVChatFunctionSwitchVO.DataBean> list) {
        boolean zIsEnabled;
        this.functionSwitchData = list;
        if (this.view == null || list == null) {
            return;
        }
        for (PLVChatFunctionSwitchVO.DataBean dataBean : list) {
            zIsEnabled = dataBean.isEnabled();
            String type = dataBean.getType();
            type.hashCode();
            switch (type) {
                case "viewerSendImgEnabled":
                    this.chatMoreLayout.updateFunctionShow(PLVLCChatMoreLayout.CHAT_FUNCTION_TYPE_SEND_IMAGE, zIsEnabled);
                    this.chatMoreLayout.updateFunctionShow(PLVLCChatMoreLayout.CHAT_FUNCTION_TYPE_OPEN_CAMERA, zIsEnabled);
                    break;
                case "sendFlowersEnabled":
                    this.likesLy.setVisibility(zIsEnabled ? 0 : 8);
                    break;
                case "welcome":
                    this.isShowGreeting = zIsEnabled;
                    break;
            }
        }
    }

    @Override // com.easefun.polyv.livecommon.ui.window.PLVInputFragment
    public int attachContainerViewId() {
        return R.id.plvlc_chatroom_input_layout_container;
    }

    public IPLVChatPlaybackCallDataListener getChatPlaybackDataListener() {
        return this.chatPlaybackDataListener;
    }

    public IPLVChatroomContract.IChatroomView getChatroomView() {
        return this.chatroomView;
    }

    public void init(PLVLCChatCommonMessageList pLVLCChatCommonMessageList) {
        this.chatCommonMessageList = pLVLCChatCommonMessageList;
    }

    @Override // com.easefun.polyv.livecommon.ui.window.PLVInputFragment
    public int inputLayoutId() {
        return R.id.bottom_input_ly;
    }

    @Override // com.easefun.polyv.livecommon.ui.window.PLVInputFragment
    public int inputViewId() {
        return R.id.input_et;
    }

    public boolean isDisplaySpecialType() {
        return this.isSelectOnlyTeacher;
    }

    @Override // androidx.fragment.app.Fragment
    public void onActivityResult(int i2, int i3, Intent intent) {
        super.onActivityResult(i2, i3, intent);
        if (i2 == 1 && i3 == -1) {
            Uri data = intent.getData();
            if (data != null) {
                sendImg(PLVUriPathHelper.getPrivatePath(getContext(), data));
                return;
            } else {
                ToastUtils.showShort("cannot retrieve selected image");
                return;
            }
        }
        if (i2 == 2 && i3 == -1) {
            if (Build.VERSION.SDK_INT >= 29) {
                sendImg(PLVUriPathHelper.getPrivatePath(getContext(), this.takePictureUri));
            } else {
                sendImg(this.takePictureFilePath.getAbsolutePath());
            }
        }
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        OnViewActionListener onViewActionListener;
        int id = view.getId();
        if (id == R.id.toggle_emoji_iv) {
            togglePopupLayout(this.toggleEmojiIv, this.emojiLy);
            return;
        }
        if (id == R.id.toggle_more_iv) {
            togglePopupLayout(this.toggleMoreIv, this.chatMoreLayout);
            return;
        }
        if (id == R.id.delete_msg_iv) {
            PLVChatroomUtils.deleteEmoText(this.inputEt);
            return;
        }
        if (id == R.id.send_msg_tv) {
            sendChatMessage(this.inputEt.getText().toString());
            return;
        }
        if (id == R.id.likes_view) {
            IPLVChatroomContract.IChatroomPresenter iChatroomPresenter = this.chatroomPresenter;
            if (iChatroomPresenter != null) {
                iChatroomPresenter.sendLikeMessage();
            }
            acceptLikesMessage(1);
            return;
        }
        if (id == R.id.plvlc_emoji_tab_emoji_iv) {
            changeEmojiTab(true);
            return;
        }
        if (id == R.id.plvlc_emoji_tab_personal_iv) {
            changeEmojiTab(false);
        } else {
            if (id != R.id.plvlc_iv_show_point_reward || (onViewActionListener = this.onViewActionListener) == null) {
                return;
            }
            onViewActionListener.onShowRewardAction();
        }
    }

    @Override // com.easefun.polyv.livecommon.ui.window.PLVInputFragment, androidx.fragment.app.Fragment, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        PLVLCChatCommonMessageList pLVLCChatCommonMessageList;
        IPLVChatroomContract.IChatroomPresenter iChatroomPresenter;
        super.onConfigurationChanged(configuration);
        if (configuration.orientation != 1 || (pLVLCChatCommonMessageList = this.chatCommonMessageList) == null || !pLVLCChatCommonMessageList.attachToParent(this.swipeLoadView, false) || (iChatroomPresenter = this.chatroomPresenter) == null) {
            return;
        }
        this.chatCommonMessageList.setMsgIndex(iChatroomPresenter.getViewIndex(this.chatroomView));
        if (this.isChatPlaybackLayout || this.chatroomPresenter.getChatHistoryTime() != 0) {
            return;
        }
        IPLVChatroomContract.IChatroomPresenter iChatroomPresenter2 = this.chatroomPresenter;
        iChatroomPresenter2.requestChatHistory(iChatroomPresenter2.getViewIndex(this.chatroomView));
    }

    @Override // androidx.fragment.app.Fragment
    @Nullable
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        this.view = layoutInflater.inflate(R.layout.plvlc_chatroom_chat_portrait_fragment, (ViewGroup) null);
        initView();
        return this.view;
    }

    @Override // com.easefun.polyv.livecommon.ui.window.PLVBaseFragment, androidx.fragment.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        if (this.isOpenPointReward) {
            destroyPointRewardEffectQueue();
        }
        TextView textView = this.chatPlaybackTipsTv;
        if (textView != null) {
            textView.removeCallbacks(this.playbackTipsRunnable);
        }
    }

    @Override // com.easefun.polyv.livecommon.ui.window.PLVInputFragment
    public boolean onSendMsg(String str) {
        return sendChatMessage(str);
    }

    public void setCardPushManager(PLVCardPushManager pLVCardPushManager) {
        this.cardPushManager = pLVCardPushManager;
    }

    public void setIsChatPlaybackLayout(boolean z2) {
        this.isChatPlaybackLayout = z2;
    }

    public void setIsLiveType(boolean z2) {
        this.isLiveType = z2;
    }

    public void setLikesCount(long j2) {
        this.likesCount = j2;
        String wString = StringUtils.toWString(j2);
        TextView textView = this.likesCountTv;
        if (textView != null) {
            textView.setText(wString);
        }
    }

    public void setOnViewActionListener(OnViewActionListener onViewActionListener) {
        this.onViewActionListener = onViewActionListener;
    }

    public void setOpenPointReward(boolean z2) {
        this.isOpenPointReward = z2;
        ImageView imageView = this.rewardIv;
        if (imageView != null) {
            imageView.setVisibility(z2 ? 0 : 8);
        }
        updateRewardEffectBtnVisibility(this.isOpenPointReward);
        initPointRewardEffectQueue();
    }

    public void updateChatMoreFunction(PLVWebviewUpdateAppStatusVO pLVWebviewUpdateAppStatusVO) {
        PLVLCChatMoreLayout pLVLCChatMoreLayout = this.chatMoreLayout;
        if (pLVLCChatMoreLayout != null) {
            pLVLCChatMoreLayout.updateFunctionView(pLVWebviewUpdateAppStatusVO);
        }
    }

    public void updateInteractStatus(boolean z2, boolean z3) {
        this.chatMoreLayout.updateFunctionNew(PLVLCChatMoreLayout.CHAT_FUNCTION_TYPE_MESSAGE, z2, z3);
    }

    public void updateRewardEffectBtnVisibility(boolean z2) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeChatMessageToList(final String str, final boolean z2) {
        Runnable runnable = new Runnable() { // from class: com.easefun.polyv.livecloudclass.modules.chatroom.PLVLCChatFragment.11
            @Override // java.lang.Runnable
            public void run() {
                if (PLVLCChatFragment.this.chatCommonMessageList == null) {
                    return;
                }
                if (z2) {
                    PLVLCChatFragment.this.chatCommonMessageList.removeAllChatMessage(false);
                } else {
                    PLVLCChatFragment.this.chatCommonMessageList.removeChatMessage(str, false);
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
