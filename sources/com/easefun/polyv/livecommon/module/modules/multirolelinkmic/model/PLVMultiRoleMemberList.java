package com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Pair;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.easefun.polyv.livecommon.module.data.IPLVLiveRoomDataManager;
import com.easefun.polyv.livecommon.module.modules.linkmic.model.PLVLinkMicDataMapper;
import com.easefun.polyv.livecommon.module.modules.linkmic.model.PLVLinkMicItemDataBean;
import com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleLinkMicList;
import com.easefun.polyv.livecommon.module.modules.streamer.model.PLVMemberItemDataBean;
import com.easefun.polyv.livecommon.module.modules.streamer.presenter.PLVStreamerPresenter;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.audio.SilenceSkippingAudioProcessor;
import com.plv.foundationsdk.log.PLVCommonLog;
import com.plv.foundationsdk.rx.PLVRxBaseRetryFunction;
import com.plv.foundationsdk.rx.PLVRxBaseTransformer;
import com.plv.foundationsdk.utils.PLVGsonUtil;
import com.plv.linkmic.PLVLinkMicEventHandler;
import com.plv.linkmic.model.PLVJoinInfoEvent;
import com.plv.linkmic.model.PLVJoinRequestSEvent;
import com.plv.linkmic.model.PLVLinkMicJoinStatus;
import com.plv.livescenes.chatroom.PLVChatApiRequestHelper;
import com.plv.livescenes.chatroom.PLVChatroomManager;
import com.plv.livescenes.linkmic.IPLVLinkMicManager;
import com.plv.livescenes.linkmic.listener.PLVLinkMicEventListener;
import com.plv.livescenes.log.chat.PLVChatroomELog;
import com.plv.livescenes.model.PLVListUsersVO;
import com.plv.livescenes.socket.PLVSocketWrapper;
import com.plv.socket.event.PLVEventConstant;
import com.plv.socket.event.PLVEventHelper;
import com.plv.socket.event.chat.PLVBanIpEvent;
import com.plv.socket.event.chat.PLVSetNickEvent;
import com.plv.socket.event.chat.PLVUnshieldEvent;
import com.plv.socket.event.linkmic.PLVTeacherSetPermissionEvent;
import com.plv.socket.event.login.PLVKickEvent;
import com.plv.socket.event.login.PLVLoginEvent;
import com.plv.socket.event.login.PLVLogoutEvent;
import com.plv.socket.event.ppt.PLVOnSliceIDEvent;
import com.plv.socket.event.seminar.PLVJoinDiscussEvent;
import com.plv.socket.event.seminar.PLVSetLeaderEvent;
import com.plv.socket.impl.PLVSocketMessageObserver;
import com.plv.socket.log.PLVELogSender;
import com.plv.socket.socketio.PLVSocketIOObservable;
import com.plv.socket.status.PLVSocketStatus;
import com.plv.socket.user.PLVClassStatusBean;
import com.plv.socket.user.PLVSocketUserBean;
import com.plv.socket.user.PLVSocketUserConstant;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public class PLVMultiRoleMemberList {
    public static final int MEMBER_LENGTH_LESS = 30;
    public static final int MEMBER_LENGTH_MORE = 500;
    private static final String TAG = "PLVMultiRoleMemberList";
    private String groupId;
    private String groupLeaderId;
    private boolean isAddMyMemberItem;
    private boolean isNoGroupIdCalled;
    private boolean isRequestedData;
    private boolean isTeacherType;
    private PLVLinkMicEventListener linkMicEventListener;

    @Nullable
    private PLVMultiRoleLinkMicList linkMicList;

    @Nullable
    private IPLVLinkMicManager linkMicManager;
    private Disposable listUserTimerDisposable;
    private Disposable listUsersDisposable;
    private IPLVLiveRoomDataManager liveRoomDataManager;
    private int memberLength;
    private String myLinkMicId;
    private PLVSocketIOObservable.OnConnectStatusListener onConnectStatusListener;
    private OnMemberListListener onMemberListListener;
    private PLVSocketMessageObserver.OnMessageListener onMessageListener;
    private int raiseHandCount;
    private List<PLVMemberItemDataBean> memberList = new LinkedList();
    private final int memberPage = 1;
    private Map<String, Runnable> raiseHandMap = new HashMap();
    private Map<String, PLVLinkMicItemDataBean> raiseHandRecoverMap = new HashMap();
    private Handler handler = new Handler(Looper.getMainLooper());

    public interface OnMemberListListener {
        void onLeaveDiscuss();

        void onLocalUserVolumeChanged(int volume);

        void onMemberItemChanged(int pos);

        void onMemberItemInsert(int pos);

        void onMemberItemRemove(int pos);

        void onMemberListChanged(List<PLVMemberItemDataBean> dataBeans);

        void onRemoteUserVolumeChanged();

        void onUserGetCup(String userNick, boolean isByEvent, int linkMicListPos, int memberListPos);

        void onUserHasGroupLeader(boolean isHasGroupLeader, String nick, @Nullable String leaderId);

        void onUserHasPaint(boolean isMyself, boolean isHasPaint, int linkMicListPos, int memberListPos);

        void onUserMuteAudio(final String uid, final boolean mute, int linkMicListPos, int memberListPos);

        void onUserMuteVideo(final String uid, final boolean mute, int linkMicListPos, int memberListPos);

        void onUserRaiseHand(int raiseHandCount, boolean isRaiseHand, int linkMicListPos, int memberListPos);
    }

    public PLVMultiRoleMemberList(IPLVLiveRoomDataManager liveRoomDataManager) {
        this.liveRoomDataManager = liveRoomDataManager;
        boolean zEquals = "teacher".equals(liveRoomDataManager.getConfig().getUser().getViewerType());
        this.isTeacherType = zEquals;
        this.isAddMyMemberItem = !zEquals;
        if (zEquals) {
            this.memberLength = 500;
        } else {
            this.memberLength = 30;
        }
        observeSocketEvent();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void acceptBanIpEvent(PLVBanIpEvent banIpEvent) {
        List<PLVSocketUserBean> userIds;
        if (banIpEvent == null || (userIds = banIpEvent.getUserIds()) == null) {
            return;
        }
        Iterator<PLVSocketUserBean> it = userIds.iterator();
        while (it.hasNext()) {
            Pair<Integer, PLVMemberItemDataBean> memberItemWithUserId = getMemberItemWithUserId(it.next().getUserId());
            if (memberItemWithUserId != null) {
                ((PLVMemberItemDataBean) memberItemWithUserId.second).getSocketUserBean().setBanned(true);
                callOnMemberItemChanged(((Integer) memberItemWithUserId.first).intValue());
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void acceptJoinDiscussEvent(final PLVJoinDiscussEvent joinDiscussEvent) {
        String str;
        if (joinDiscussEvent == null || (str = this.groupId) == null || str.equals(joinDiscussEvent.getGroupId())) {
            return;
        }
        this.groupId = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean acceptJoinRequestSEvent(PLVJoinRequestSEvent joinRequestSEvent) {
        if (joinRequestSEvent == null || joinRequestSEvent.getUser() == null) {
            return false;
        }
        boolean zUpdateMemberItemInfo = updateMemberItemInfo(PLVLinkMicDataMapper.map2SocketUserBean(joinRequestSEvent.getUser()), PLVLinkMicDataMapper.map2LinkMicItemData(joinRequestSEvent.getUser()), false, true, false);
        if (zUpdateMemberItemInfo) {
            callOnMemberListChangedWithSort();
        }
        return zUpdateMemberItemInfo;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void acceptKickEvent(PLVKickEvent kickEvent) {
        Pair<Integer, PLVMemberItemDataBean> memberItemWithUserId;
        if (kickEvent == null || kickEvent.getUser() == null || (memberItemWithUserId = getMemberItemWithUserId(kickEvent.getUser().getUserId())) == null) {
            return;
        }
        this.memberList.remove(memberItemWithUserId.second);
        callOnMemberItemRemove(((Integer) memberItemWithUserId.first).intValue());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void acceptLocalAudioVolumeIndication(PLVLinkMicEventHandler.PLVAudioVolumeInfo speaker) {
        PLVMultiRoleLinkMicList pLVMultiRoleLinkMicList = this.linkMicList;
        Pair<Integer, PLVLinkMicItemDataBean> linkMicItemWithLinkMicId = pLVMultiRoleLinkMicList == null ? null : pLVMultiRoleLinkMicList.getLinkMicItemWithLinkMicId(speaker.getUid());
        if (linkMicItemWithLinkMicId != null) {
            ((PLVLinkMicItemDataBean) linkMicItemWithLinkMicId.second).setCurVolume(speaker.getVolume());
        }
        callOnLocalUserVolumeChanged(speaker.getVolume());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void acceptLoginEvent(PLVLoginEvent loginEvent) {
        if (loginEvent == null || loginEvent.getUser() == null) {
            return;
        }
        PLVSocketUserBean user = loginEvent.getUser();
        user.setClassStatus(loginEvent.getClassStatus());
        if (PLVSocketUserConstant.USERSOURCE_CHATROOM.equals(user.getUserSource()) || getMemberItemWithUserId(user.getUserId()) != null || isMySocketUserId(user.getUserId()) || "teacher".equals(user.getUserType())) {
            return;
        }
        PLVMemberItemDataBean pLVMemberItemDataBean = new PLVMemberItemDataBean();
        pLVMemberItemDataBean.setSocketUserBean(user);
        pLVMemberItemDataBean.addBaseLinkMicBean(user);
        checkSetRaiseHand(pLVMemberItemDataBean.getLinkMicItemDataBean(), user.getClassStatus(), user.getUserId());
        this.memberList.add(pLVMemberItemDataBean);
        PLVStreamerPresenter.SortMemberListUtils.sort(this.memberList);
        Pair<Integer, PLVMemberItemDataBean> memberItemWithUserId = getMemberItemWithUserId(user.getUserId());
        if (memberItemWithUserId != null) {
            callOnMemberItemInsert(((Integer) memberItemWithUserId.first).intValue());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void acceptLogoutEvent(PLVLogoutEvent logoutEvent) {
        Pair<Integer, PLVMemberItemDataBean> memberItemWithUserId;
        if (logoutEvent == null || (memberItemWithUserId = getMemberItemWithUserId(logoutEvent.getUserId())) == null) {
            return;
        }
        this.memberList.remove(memberItemWithUserId.second);
        callOnMemberItemRemove(((Integer) memberItemWithUserId.first).intValue());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void acceptOnSliceIDEvent(PLVOnSliceIDEvent onSliceIDEvent) {
        if (onSliceIDEvent == null || TextUtils.isEmpty(this.groupId)) {
            return;
        }
        if (!onSliceIDEvent.isInDiscuss() || (onSliceIDEvent.isInDiscuss() && TextUtils.isEmpty(onSliceIDEvent.getGroupId()))) {
            callOnLeaveDiscuss();
        } else if (this.groupId.equals(onSliceIDEvent.getGroupId())) {
            checkCallLeaderChanged(onSliceIDEvent.getLeader(), "");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void acceptRemoteAudioVolumeIndication(PLVLinkMicEventHandler.PLVAudioVolumeInfo[] speakers) {
        boolean z2;
        Iterator<PLVMemberItemDataBean> it = this.memberList.iterator();
        while (it.hasNext()) {
            PLVLinkMicItemDataBean linkMicItemDataBean = it.next().getLinkMicItemDataBean();
            if (linkMicItemDataBean != null) {
                String linkMicId = linkMicItemDataBean.getLinkMicId();
                if (!isMyLinkMicId(linkMicId)) {
                    int length = speakers.length;
                    int i2 = 0;
                    while (true) {
                        if (i2 >= length) {
                            z2 = false;
                            break;
                        }
                        PLVLinkMicEventHandler.PLVAudioVolumeInfo pLVAudioVolumeInfo = speakers[i2];
                        if (linkMicId.equals(pLVAudioVolumeInfo.getUid())) {
                            linkMicItemDataBean.setCurVolume(pLVAudioVolumeInfo.getVolume());
                            z2 = true;
                            break;
                        }
                        i2++;
                    }
                    if (!z2) {
                        linkMicItemDataBean.setCurVolume(0);
                    }
                }
            }
        }
        callOnRemoteUserVolumeChanged();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void acceptSetLeaderEvent(PLVSetLeaderEvent setLeaderEvent) {
        if (setLeaderEvent != null) {
            checkCallLeaderChanged(setLeaderEvent.getUserId(), setLeaderEvent.getNick());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void acceptSetNickEvent(PLVSetNickEvent setNickEvent) {
        Pair<Integer, PLVMemberItemDataBean> memberItemWithUserId;
        if (setNickEvent == null || !"success".equals(setNickEvent.getStatus()) || (memberItemWithUserId = getMemberItemWithUserId(setNickEvent.getUserId())) == null) {
            return;
        }
        ((PLVMemberItemDataBean) memberItemWithUserId.second).getSocketUserBean().setNick(setNickEvent.getNick());
        callOnMemberItemChanged(((Integer) memberItemWithUserId.first).intValue());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void acceptTeacherSetPermissionEvent(PLVTeacherSetPermissionEvent pLVTeacherSetPermissionEvent) {
        PLVLinkMicItemDataBean linkMicItemDataBean;
        if (pLVTeacherSetPermissionEvent != null) {
            String type = pLVTeacherSetPermissionEvent.getType();
            String status = pLVTeacherSetPermissionEvent.getStatus();
            String userId = pLVTeacherSetPermissionEvent.getUserId();
            Pair<Integer, PLVMemberItemDataBean> memberItemWithUserId = getMemberItemWithUserId(userId);
            if (memberItemWithUserId == null || (linkMicItemDataBean = ((PLVMemberItemDataBean) memberItemWithUserId.second).getLinkMicItemDataBean()) == null) {
                return;
            }
            PLVMultiRoleLinkMicList pLVMultiRoleLinkMicList = this.linkMicList;
            Pair<Integer, PLVLinkMicItemDataBean> linkMicItemWithLinkMicId = pLVMultiRoleLinkMicList == null ? null : pLVMultiRoleLinkMicList.getLinkMicItemWithLinkMicId(linkMicItemDataBean.getLinkMicId());
            int iIntValue = linkMicItemWithLinkMicId == null ? -1 : ((Integer) linkMicItemWithLinkMicId.first).intValue();
            PLVClassStatusBean classStatus = ((PLVMemberItemDataBean) memberItemWithUserId.second).getSocketUserBean().getClassStatus();
            if (PLVTeacherSetPermissionEvent.TYPE_CUP.equals(type)) {
                linkMicItemDataBean.setCupNum(linkMicItemDataBean.getCupNum() + 1);
                if (classStatus != null) {
                    classStatus.setCup(linkMicItemDataBean.getCupNum());
                }
                callOnUserGetCup(linkMicItemDataBean.getNick(), true, iIntValue, ((Integer) memberItemWithUserId.first).intValue());
                return;
            }
            if (!PLVTeacherSetPermissionEvent.TYPE_RAISE_HAND.equals(type)) {
                if ("paint".equals(type)) {
                    if ("1".equals(status)) {
                        linkMicItemDataBean.setHasPaint(true);
                    } else if ("0".equals(status)) {
                        linkMicItemDataBean.setHasPaint(false);
                    }
                    if (classStatus != null) {
                        classStatus.setPaint(linkMicItemDataBean.isHasPaint() ? 1 : 0);
                    }
                    callOnUserHasPaint(isMyLinkMicId(linkMicItemDataBean.getLinkMicId()), linkMicItemDataBean.isHasPaint(), iIntValue, ((Integer) memberItemWithUserId.first).intValue());
                    return;
                }
                return;
            }
            if ("0".equals(status)) {
                return;
            }
            this.raiseHandCount = Math.max(0, this.raiseHandCount);
            if (!this.raiseHandMap.containsKey(userId)) {
                this.raiseHandCount++;
            }
            if ("1".equals(status)) {
                linkMicItemDataBean.setRaiseHand(true);
            }
            if (classStatus != null) {
                classStatus.setRaiseHand(linkMicItemDataBean.isRaiseHand() ? 1 : 0);
            }
            callOnUserRaiseHand(this.raiseHandCount, linkMicItemDataBean.isRaiseHand(), iIntValue, ((Integer) memberItemWithUserId.first).intValue());
            if (linkMicItemDataBean.isRaiseHand()) {
                startRaiseHandTimeoutCount(linkMicItemDataBean, classStatus, userId, pLVTeacherSetPermissionEvent.getRaiseHandTime());
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void acceptUnshieldEvent(PLVUnshieldEvent unshieldEvent) {
        List<PLVSocketUserBean> userIds;
        if (unshieldEvent == null || (userIds = unshieldEvent.getUserIds()) == null) {
            return;
        }
        Iterator<PLVSocketUserBean> it = userIds.iterator();
        while (it.hasNext()) {
            Pair<Integer, PLVMemberItemDataBean> memberItemWithUserId = getMemberItemWithUserId(it.next().getUserId());
            if (memberItemWithUserId != null) {
                ((PLVMemberItemDataBean) memberItemWithUserId.second).getSocketUserBean().setBanned(false);
                callOnMemberItemChanged(((Integer) memberItemWithUserId.first).intValue());
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void acceptUserMuteAudio(final String linkMicId, final boolean isMute, int streamType) {
        PLVMultiRoleLinkMicList pLVMultiRoleLinkMicList = this.linkMicList;
        Pair<Integer, PLVLinkMicItemDataBean> linkMicItemWithLinkMicId = pLVMultiRoleLinkMicList == null ? null : pLVMultiRoleLinkMicList.getLinkMicItemWithLinkMicId(linkMicId);
        if (linkMicItemWithLinkMicId == null || !((PLVLinkMicItemDataBean) linkMicItemWithLinkMicId.second).includeStreamType(streamType)) {
            return;
        }
        ((PLVLinkMicItemDataBean) linkMicItemWithLinkMicId.second).setMuteAudio(isMute);
        int iIntValue = ((Integer) linkMicItemWithLinkMicId.first).intValue();
        Pair<Integer, PLVMemberItemDataBean> memberItemWithLinkMicId = getMemberItemWithLinkMicId(linkMicId);
        callOnUserMuteAudio(linkMicId, isMute, iIntValue, memberItemWithLinkMicId == null ? -1 : ((Integer) memberItemWithLinkMicId.first).intValue());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void acceptUserMuteVideo(final String linkMicId, final boolean isMute, int streamType) {
        PLVMultiRoleLinkMicList pLVMultiRoleLinkMicList = this.linkMicList;
        Pair<Integer, PLVLinkMicItemDataBean> linkMicItemWithLinkMicId = pLVMultiRoleLinkMicList == null ? null : pLVMultiRoleLinkMicList.getLinkMicItemWithLinkMicId(linkMicId);
        if (linkMicItemWithLinkMicId == null || !((PLVLinkMicItemDataBean) linkMicItemWithLinkMicId.second).includeStreamType(streamType)) {
            return;
        }
        ((PLVLinkMicItemDataBean) linkMicItemWithLinkMicId.second).setMuteVideo(isMute);
        int iIntValue = ((Integer) linkMicItemWithLinkMicId.first).intValue();
        Pair<Integer, PLVMemberItemDataBean> memberItemWithLinkMicId = getMemberItemWithLinkMicId(linkMicId);
        callOnUserMuteVideo(linkMicId, isMute, iIntValue, memberItemWithLinkMicId == null ? -1 : ((Integer) memberItemWithLinkMicId.first).intValue());
    }

    public static /* synthetic */ int access$1106(PLVMultiRoleMemberList pLVMultiRoleMemberList) {
        int i2 = pLVMultiRoleMemberList.raiseHandCount - 1;
        pLVMultiRoleMemberList.raiseHandCount = i2;
        return i2;
    }

    private void addMyMemberItem(@Nullable PLVClassStatusBean classStatusBean) {
        if (this.isAddMyMemberItem) {
            PLVMemberItemDataBean pLVMemberItemDataBean = new PLVMemberItemDataBean();
            PLVSocketUserBean pLVSocketUserBeanCreateSocketUserBean = PLVSocketWrapper.getInstance().getLoginVO().createSocketUserBean();
            if (classStatusBean != null) {
                pLVSocketUserBeanCreateSocketUserBean.setClassStatus(classStatusBean);
            }
            pLVMemberItemDataBean.setSocketUserBean(pLVSocketUserBeanCreateSocketUserBean);
            PLVMultiRoleLinkMicList pLVMultiRoleLinkMicList = this.linkMicList;
            PLVLinkMicItemDataBean myLinkMicItemBean = pLVMultiRoleLinkMicList == null ? null : pLVMultiRoleLinkMicList.getMyLinkMicItemBean();
            if (myLinkMicItemBean != null) {
                boolean zIsHasPaint = myLinkMicItemBean.isHasPaint();
                int cupNum = myLinkMicItemBean.getCupNum();
                pLVMemberItemDataBean.setLinkMicItemDataBean(myLinkMicItemBean);
                checkUpdateHasPermission(classStatusBean, myLinkMicItemBean, zIsHasPaint, cupNum);
            } else {
                pLVMemberItemDataBean.addBaseLinkMicBean(pLVMemberItemDataBean.getSocketUserBean());
            }
            if (classStatusBean != null && classStatusBean.isGroupLeader()) {
                checkCallLeaderChanged(pLVSocketUserBeanCreateSocketUserBean.getUserId(), pLVSocketUserBeanCreateSocketUserBean.getNick());
            }
            this.memberList.add(0, pLVMemberItemDataBean);
        }
    }

    private void callOnLeaveDiscuss() {
        OnMemberListListener onMemberListListener = this.onMemberListListener;
        if (onMemberListListener != null) {
            onMemberListListener.onLeaveDiscuss();
        }
    }

    private void callOnLocalUserVolumeChanged(int volume) {
        OnMemberListListener onMemberListListener = this.onMemberListListener;
        if (onMemberListListener != null) {
            onMemberListListener.onLocalUserVolumeChanged(volume);
        }
    }

    private void callOnMemberItemChanged(int pos) {
        OnMemberListListener onMemberListListener = this.onMemberListListener;
        if (onMemberListListener != null) {
            onMemberListListener.onMemberItemChanged(pos);
        }
    }

    private void callOnMemberItemInsert(int pos) {
        OnMemberListListener onMemberListListener = this.onMemberListListener;
        if (onMemberListListener != null) {
            onMemberListListener.onMemberItemInsert(pos);
        }
    }

    private void callOnMemberItemRemove(int pos) {
        OnMemberListListener onMemberListListener = this.onMemberListListener;
        if (onMemberListListener != null) {
            onMemberListListener.onMemberItemRemove(pos);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void callOnMemberListChangedWithSort() {
        OnMemberListListener onMemberListListener = this.onMemberListListener;
        if (onMemberListListener != null) {
            onMemberListListener.onMemberListChanged(PLVStreamerPresenter.SortMemberListUtils.sort(this.memberList));
        }
    }

    private void callOnRemoteUserVolumeChanged() {
        OnMemberListListener onMemberListListener = this.onMemberListListener;
        if (onMemberListListener != null) {
            onMemberListListener.onRemoteUserVolumeChanged();
        }
    }

    private void callOnUserGetCup(String userNick, boolean isByEvent, int linkMicListPos, int memberListPos) {
        OnMemberListListener onMemberListListener = this.onMemberListListener;
        if (onMemberListListener != null) {
            onMemberListListener.onUserGetCup(userNick, isByEvent, linkMicListPos, memberListPos);
        }
    }

    private void callOnUserHasGroupLeader(boolean isHasGroupLeader, String nick, String leaderId) {
        OnMemberListListener onMemberListListener = this.onMemberListListener;
        if (onMemberListListener != null) {
            onMemberListListener.onUserHasGroupLeader(isHasGroupLeader, nick, leaderId);
        }
    }

    private void callOnUserHasPaint(boolean isMyself, boolean isHasPaint, int linkMicListPos, int memberListPos) {
        OnMemberListListener onMemberListListener = this.onMemberListListener;
        if (onMemberListListener != null) {
            onMemberListListener.onUserHasPaint(isMyself, isHasPaint, linkMicListPos, memberListPos);
        }
    }

    private void callOnUserMuteAudio(final String uid, final boolean mute, int linkMicListPos, int memberListPos) {
        OnMemberListListener onMemberListListener = this.onMemberListListener;
        if (onMemberListListener != null) {
            onMemberListListener.onUserMuteAudio(uid, mute, linkMicListPos, memberListPos);
        }
    }

    private void callOnUserMuteVideo(final String uid, final boolean mute, int linkMicListPos, int memberListPos) {
        OnMemberListListener onMemberListListener = this.onMemberListListener;
        if (onMemberListListener != null) {
            onMemberListListener.onUserMuteVideo(uid, mute, linkMicListPos, memberListPos);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void callOnUserRaiseHand(int raiseHandCount, boolean isRaiseHand, int linkMicListPos, int memberListPos) {
        OnMemberListListener onMemberListListener = this.onMemberListListener;
        if (onMemberListListener != null) {
            onMemberListListener.onUserRaiseHand(raiseHandCount, isRaiseHand, linkMicListPos, memberListPos);
        }
    }

    private void checkCallLeaderChanged(String leaderUserId, @NonNull String nick) {
        if (leaderUserId == null || leaderUserId.equals(this.groupLeaderId) || TextUtils.isEmpty(this.groupId)) {
            return;
        }
        boolean zIsMySocketUserId = isMySocketUserId(leaderUserId);
        this.groupLeaderId = leaderUserId;
        if (!this.isTeacherType) {
            this.memberLength = zIsMySocketUserId ? 500 : 30;
        }
        if (TextUtils.isEmpty(nick) && isMySocketUserId(this.groupLeaderId)) {
            nick = this.liveRoomDataManager.getConfig().getUser().getViewerName();
        }
        callOnUserHasGroupLeader(zIsMySocketUserId, nick, this.groupLeaderId);
    }

    private void checkSetRaiseHand(@NonNull final PLVLinkMicItemDataBean linkMicItemDataBean, final PLVClassStatusBean classStatusBean, final String userId) {
        if (classStatusBean == null || !classStatusBean.isRaiseHand()) {
            return;
        }
        for (String str : this.raiseHandMap.keySet()) {
            if (userId != null && userId.equals(str)) {
                this.raiseHandRecoverMap.put(userId, linkMicItemDataBean);
                linkMicItemDataBean.setRaiseHand(true);
                return;
            }
        }
    }

    private void checkUpdateHasPermission(PLVClassStatusBean classStatusBean, PLVLinkMicItemDataBean linkMicItemDataBean, boolean oldLinkMicItemHasPaint, int oldLinkMicItemCupNum) {
        if (classStatusBean != null) {
            PLVMultiRoleLinkMicList pLVMultiRoleLinkMicList = this.linkMicList;
            Pair<Integer, PLVLinkMicItemDataBean> linkMicItemWithLinkMicId = pLVMultiRoleLinkMicList == null ? null : pLVMultiRoleLinkMicList.getLinkMicItemWithLinkMicId(linkMicItemDataBean.getLinkMicId());
            if (linkMicItemWithLinkMicId == null) {
                return;
            }
            int iIntValue = ((Integer) linkMicItemWithLinkMicId.first).intValue();
            if (classStatusBean.hasPaint() != oldLinkMicItemHasPaint) {
                callOnUserHasPaint(isMyLinkMicId(((PLVLinkMicItemDataBean) linkMicItemWithLinkMicId.second).getLinkMicId()), linkMicItemDataBean.isHasPaint(), iIntValue, -1);
            }
            if (classStatusBean.getCup() != oldLinkMicItemCupNum) {
                callOnUserGetCup(((PLVLinkMicItemDataBean) linkMicItemWithLinkMicId.second).getNick(), false, iIntValue, -1);
            }
        }
    }

    private void dispose(Disposable disposable) {
        if (disposable != null) {
            disposable.dispose();
        }
    }

    private Pair<Integer, PLVMemberItemDataBean> getMemberItemWithLinkMicIdInner(String linkMicId) {
        for (int i2 = 0; i2 < this.memberList.size(); i2++) {
            PLVMemberItemDataBean pLVMemberItemDataBean = this.memberList.get(i2);
            PLVLinkMicItemDataBean linkMicItemDataBean = pLVMemberItemDataBean.getLinkMicItemDataBean();
            if (linkMicItemDataBean != null) {
                String linkMicId2 = linkMicItemDataBean.getLinkMicId();
                if (linkMicId != null && linkMicId.equals(linkMicId2)) {
                    return new Pair<>(Integer.valueOf(i2), pLVMemberItemDataBean);
                }
            } else {
                PLVSocketUserBean socketUserBean = pLVMemberItemDataBean.getSocketUserBean();
                if (socketUserBean != null && linkMicId != null && linkMicId.equals(socketUserBean.getUserId())) {
                    return new Pair<>(Integer.valueOf(i2), pLVMemberItemDataBean);
                }
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Pair<Integer, PLVMemberItemDataBean> getMemberItemWithUserId(String userId) {
        for (int i2 = 0; i2 < this.memberList.size(); i2++) {
            PLVMemberItemDataBean pLVMemberItemDataBean = this.memberList.get(i2);
            PLVSocketUserBean socketUserBean = pLVMemberItemDataBean.getSocketUserBean();
            if (socketUserBean != null) {
                String userId2 = socketUserBean.getUserId();
                if (userId != null && userId.equals(userId2)) {
                    return new Pair<>(Integer.valueOf(i2), pLVMemberItemDataBean);
                }
            }
        }
        return null;
    }

    private String getRoomIdCombineDiscuss() {
        if (!TextUtils.isEmpty(this.groupId)) {
            return this.groupId;
        }
        String loginRoomId = PLVSocketWrapper.getInstance().getLoginRoomId();
        return TextUtils.isEmpty(loginRoomId) ? this.liveRoomDataManager.getConfig().getChannelId() : loginRoomId;
    }

    private boolean isMyLinkMicId(String linkMicId) {
        return linkMicId != null && linkMicId.equals(this.myLinkMicId);
    }

    private boolean isMySocketUserId(String userId) {
        return userId != null && userId.equals(this.liveRoomDataManager.getConfig().getUser().getViewerId());
    }

    private void observeRTCEventInner() {
        PLVLinkMicEventListener pLVLinkMicEventListener = new PLVLinkMicEventListener() { // from class: com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleMemberList.15
            @Override // com.plv.linkmic.PLVLinkMicEventHandler
            public void onLocalAudioVolumeIndication(final PLVLinkMicEventHandler.PLVAudioVolumeInfo speaker) {
                super.onLocalAudioVolumeIndication(speaker);
                PLVMultiRoleMemberList.this.acceptLocalAudioVolumeIndication(speaker);
            }

            @Override // com.plv.linkmic.PLVLinkMicEventHandler
            public void onRemoteAudioVolumeIndication(PLVLinkMicEventHandler.PLVAudioVolumeInfo[] speakers) {
                super.onRemoteAudioVolumeIndication(speakers);
                PLVMultiRoleMemberList.this.acceptRemoteAudioVolumeIndication(speakers);
            }

            @Override // com.plv.linkmic.PLVLinkMicEventHandler
            public void onUserMuteAudio(final String uid, final boolean mute, int streamType) {
                super.onUserMuteAudio(uid, mute);
                PLVCommonLog.d(PLVMultiRoleMemberList.TAG, "onUserMuteAudio: " + uid + "*" + mute + "*" + streamType);
                PLVMultiRoleMemberList.this.acceptUserMuteAudio(uid, mute, streamType);
            }

            @Override // com.plv.linkmic.PLVLinkMicEventHandler
            public void onUserMuteVideo(String uid, boolean mute, int streamType) {
                super.onUserMuteVideo(uid, mute);
                PLVCommonLog.d(PLVMultiRoleMemberList.TAG, "onUserMuteVideo: " + uid + "*" + mute + "*" + streamType);
                PLVMultiRoleMemberList.this.acceptUserMuteVideo(uid, mute, streamType);
            }
        };
        this.linkMicEventListener = pLVLinkMicEventListener;
        IPLVLinkMicManager iPLVLinkMicManager = this.linkMicManager;
        if (iPLVLinkMicManager != null) {
            iPLVLinkMicManager.addEventHandler(pLVLinkMicEventListener);
        }
    }

    private void observeSocketEvent() {
        this.onConnectStatusListener = new PLVSocketIOObservable.OnConnectStatusListener() { // from class: com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleMemberList.13
            @Override // com.plv.socket.socketio.PLVSocketIOObservable.OnConnectStatusListener
            public void onStatus(PLVSocketStatus status) {
                if (4 == status.getStatus() && PLVMultiRoleMemberList.this.isRequestedData) {
                    PLVMultiRoleMemberList.this.requestMemberListApi();
                }
            }
        };
        this.onMessageListener = new PLVSocketMessageObserver.OnMessageListener() { // from class: com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleMemberList.14
            @Override // com.plv.socket.impl.PLVSocketMessageObserver.OnMessageListener
            public void onMessage(String listenEvent, String event, String message) {
                event.hashCode();
                switch (event) {
                    case "SET_NICK":
                        PLVMultiRoleMemberList.this.acceptSetNickEvent((PLVSetNickEvent) PLVEventHelper.toEventModel(listenEvent, event, message, PLVSetNickEvent.class));
                        break;
                    case "LOGOUT":
                        PLVMultiRoleMemberList.this.acceptLogoutEvent((PLVLogoutEvent) PLVEventHelper.toEventModel(listenEvent, event, message, PLVLogoutEvent.class));
                        break;
                    case "joinRequest":
                        PLVMultiRoleMemberList.this.acceptJoinRequestSEvent((PLVJoinRequestSEvent) PLVGsonUtil.fromJson(PLVJoinRequestSEvent.class, message));
                        break;
                    case "joinDiscuss":
                        PLVMultiRoleMemberList.this.acceptJoinDiscussEvent((PLVJoinDiscussEvent) PLVGsonUtil.fromJson(PLVJoinDiscussEvent.class, message));
                        break;
                    case "onSliceID":
                        PLVMultiRoleMemberList.this.acceptOnSliceIDEvent((PLVOnSliceIDEvent) PLVEventHelper.toEventModel(listenEvent, event, message, PLVOnSliceIDEvent.class));
                        break;
                    case "KICK":
                        PLVMultiRoleMemberList.this.acceptKickEvent((PLVKickEvent) PLVEventHelper.toEventModel(listenEvent, event, message, PLVKickEvent.class));
                        break;
                    case "BANIP":
                        PLVMultiRoleMemberList.this.acceptBanIpEvent((PLVBanIpEvent) PLVEventHelper.toEventModel(listenEvent, event, message, PLVBanIpEvent.class));
                        break;
                    case "LOGIN":
                        PLVMultiRoleMemberList.this.acceptLoginEvent((PLVLoginEvent) PLVEventHelper.toEventModel(listenEvent, event, message, PLVLoginEvent.class));
                        break;
                    case "TEACHER_SET_PERMISSION":
                        PLVMultiRoleMemberList.this.acceptTeacherSetPermissionEvent((PLVTeacherSetPermissionEvent) PLVEventHelper.toEventModel(listenEvent, event, message, PLVTeacherSetPermissionEvent.class));
                        break;
                    case "setLeader":
                        PLVMultiRoleMemberList.this.acceptSetLeaderEvent((PLVSetLeaderEvent) PLVGsonUtil.fromJson(PLVSetLeaderEvent.class, message));
                        break;
                    case "UNSHIELD":
                        PLVMultiRoleMemberList.this.acceptUnshieldEvent((PLVUnshieldEvent) PLVEventHelper.toEventModel(listenEvent, event, message, PLVUnshieldEvent.class));
                        break;
                }
            }
        };
        PLVSocketWrapper.getInstance().getSocketObserver().addOnConnectStatusListener(this.onConnectStatusListener);
        PLVSocketWrapper.getInstance().getSocketObserver().addOnMessageListener(this.onMessageListener, PLVEventConstant.LinkMic.JOIN_REQUEST_EVENT, PLVEventConstant.LinkMic.JOIN_RESPONSE_EVENT, "joinSuccess", PLVEventConstant.LinkMic.JOIN_LEAVE_EVENT, PLVEventConstant.LinkMic.JOIN_ANSWER_EVENT, PLVEventConstant.Class.SE_SWITCH_MESSAGE, PLVEventConstant.Seminar.SEMINAR_EVENT, "message");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void requestMemberListApi() {
        dispose(this.listUsersDisposable);
        dispose(this.listUserTimerDisposable);
        PLVMultiRoleLinkMicList pLVMultiRoleLinkMicList = this.linkMicList;
        if (pLVMultiRoleLinkMicList != null) {
            pLVMultiRoleLinkMicList.disposeRequestData();
        }
        final String roomIdCombineDiscuss = getRoomIdCombineDiscuss();
        this.listUsersDisposable = PLVChatApiRequestHelper.getListUsers(roomIdCombineDiscuss, 1, this.memberLength, this.liveRoomDataManager.getSessionId()).retryWhen(new PLVRxBaseRetryFunction(Integer.MAX_VALUE, C.DEFAULT_MAX_SEEK_TO_PREVIOUS_POSITION_MS)).compose(new PLVRxBaseTransformer()).subscribe(new Consumer<PLVListUsersVO>() { // from class: com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleMemberList.2
            @Override // io.reactivex.functions.Consumer
            public void accept(PLVListUsersVO PLVListUsersVO) throws Exception {
                PLVChatroomManager.getInstance().setOnlineCount(PLVListUsersVO.getCount());
                PLVMultiRoleMemberList.this.updateMemberListWithListUsers(PLVListUsersVO.getUserlist());
                if (PLVMultiRoleMemberList.this.linkMicList != null) {
                    PLVMultiRoleMemberList.this.linkMicList.requestData();
                }
                PLVMultiRoleMemberList.this.requestMemberListApiTimer(roomIdCombineDiscuss);
            }
        }, new Consumer<Throwable>() { // from class: com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleMemberList.3
            @Override // io.reactivex.functions.Consumer
            public void accept(Throwable throwable) throws Exception {
                PLVCommonLog.exception(throwable);
                PLVELogSender.send(PLVChatroomELog.class, PLVChatroomELog.Event.GET_LISTUSERS_FAIL, throwable);
            }
        });
    }

    private Disposable requestMemberListApiLessInner(final Consumer<List<PLVSocketUserBean>> onNext) {
        return PLVChatApiRequestHelper.getListUsers(getRoomIdCombineDiscuss(), 1, 30, this.liveRoomDataManager.getSessionId()).map(new Function<PLVListUsersVO, List<PLVSocketUserBean>>() { // from class: com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleMemberList.9
            @Override // io.reactivex.functions.Function
            public List<PLVSocketUserBean> apply(PLVListUsersVO PLVListUsersVO) throws Exception {
                return PLVListUsersVO.getUserlist();
            }
        }).compose(new PLVRxBaseTransformer()).subscribe(new Consumer<List<PLVSocketUserBean>>() { // from class: com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleMemberList.7
            @Override // io.reactivex.functions.Consumer
            public void accept(List<PLVSocketUserBean> userBeans) throws Exception {
                Consumer consumer = onNext;
                if (consumer != null) {
                    consumer.accept(userBeans);
                }
            }
        }, new Consumer<Throwable>() { // from class: com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleMemberList.8
            @Override // io.reactivex.functions.Consumer
            public void accept(Throwable throwable) throws Exception {
                PLVCommonLog.exception(throwable);
                PLVELogSender.send(PLVChatroomELog.class, PLVChatroomELog.Event.GET_LISTUSERS_FAIL, throwable);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void requestMemberListApiTimer(final String roomId) {
        dispose(this.listUserTimerDisposable);
        this.listUserTimerDisposable = Observable.interval(SilenceSkippingAudioProcessor.DEFAULT_PADDING_SILENCE_US, SilenceSkippingAudioProcessor.DEFAULT_PADDING_SILENCE_US, TimeUnit.MILLISECONDS, Schedulers.io()).flatMap(new Function<Long, Observable<PLVListUsersVO>>() { // from class: com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleMemberList.6
            @Override // io.reactivex.functions.Function
            public Observable<PLVListUsersVO> apply(Long aLong) throws Exception {
                return PLVChatApiRequestHelper.getListUsers(roomId, 1, PLVMultiRoleMemberList.this.memberLength, PLVMultiRoleMemberList.this.liveRoomDataManager.getSessionId()).retry(1L);
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<PLVListUsersVO>() { // from class: com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleMemberList.4
            @Override // io.reactivex.functions.Consumer
            public void accept(PLVListUsersVO PLVListUsersVO) throws Exception {
                PLVChatroomManager.getInstance().setOnlineCount(PLVListUsersVO.getCount());
                PLVMultiRoleMemberList.this.updateMemberListWithListUsers(PLVListUsersVO.getUserlist());
            }
        }, new Consumer<Throwable>() { // from class: com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleMemberList.5
            @Override // io.reactivex.functions.Consumer
            public void accept(Throwable throwable) throws Exception {
                PLVCommonLog.exception(throwable);
                PLVELogSender.send(PLVChatroomELog.class, PLVChatroomELog.Event.GET_LISTUSERS_FAIL, throwable);
            }
        });
    }

    private void startJoiningTimeoutCount(final PLVLinkMicItemDataBean linkMicItemDataBean) {
        final Runnable runnable = new Runnable() { // from class: com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleMemberList.10
            @Override // java.lang.Runnable
            public void run() {
                linkMicItemDataBean.setStatusMethodCallListener(null);
                linkMicItemDataBean.setStatus("wait");
                PLVMultiRoleMemberList.this.callOnMemberListChangedWithSort();
            }
        };
        this.handler.postDelayed(runnable, SilenceSkippingAudioProcessor.DEFAULT_PADDING_SILENCE_US);
        linkMicItemDataBean.setStatusMethodCallListener(new Runnable() { // from class: com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleMemberList.11
            @Override // java.lang.Runnable
            public void run() {
                if (linkMicItemDataBean.isJoiningStatus()) {
                    return;
                }
                PLVMultiRoleMemberList.this.handler.removeCallbacks(runnable);
            }
        });
    }

    private void startRaiseHandTimeoutCount(@NonNull final PLVLinkMicItemDataBean linkMicItemDataBean, final PLVClassStatusBean classStatusBean, final String userId, long timeMillis) {
        Runnable runnable = new Runnable() { // from class: com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleMemberList.12
            @Override // java.lang.Runnable
            public void run() {
                PLVMultiRoleMemberList.access$1106(PLVMultiRoleMemberList.this);
                PLVMultiRoleMemberList.this.raiseHandMap.remove(userId);
                linkMicItemDataBean.setRaiseHand(false);
                PLVLinkMicItemDataBean pLVLinkMicItemDataBean = (PLVLinkMicItemDataBean) PLVMultiRoleMemberList.this.raiseHandRecoverMap.remove(userId);
                if (pLVLinkMicItemDataBean != null) {
                    pLVLinkMicItemDataBean.setRaiseHand(false);
                }
                PLVClassStatusBean pLVClassStatusBean = classStatusBean;
                if (pLVClassStatusBean != null) {
                    pLVClassStatusBean.setRaiseHand(linkMicItemDataBean.isRaiseHand() ? 1 : 0);
                }
                Pair memberItemWithUserId = PLVMultiRoleMemberList.this.getMemberItemWithUserId(userId);
                int iIntValue = memberItemWithUserId == null ? -1 : ((Integer) memberItemWithUserId.first).intValue();
                Pair<Integer, PLVLinkMicItemDataBean> linkMicItemWithLinkMicId = PLVMultiRoleMemberList.this.linkMicList == null ? null : PLVMultiRoleMemberList.this.linkMicList.getLinkMicItemWithLinkMicId(linkMicItemDataBean.getLinkMicId());
                int iIntValue2 = linkMicItemWithLinkMicId != null ? ((Integer) linkMicItemWithLinkMicId.first).intValue() : -1;
                PLVMultiRoleMemberList pLVMultiRoleMemberList = PLVMultiRoleMemberList.this;
                pLVMultiRoleMemberList.callOnUserRaiseHand(Math.max(0, pLVMultiRoleMemberList.raiseHandCount), linkMicItemDataBean.isRaiseHand(), iIntValue2, iIntValue);
            }
        };
        if (this.raiseHandMap.containsKey(userId)) {
            this.handler.removeCallbacks(this.raiseHandMap.get(userId));
        }
        this.raiseHandMap.put(userId, runnable);
        this.handler.postDelayed(runnable, timeMillis);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateMemberItemIdleStatus(String linkMicId) {
        Pair<Integer, PLVMemberItemDataBean> memberItemWithLinkMicId = getMemberItemWithLinkMicId(linkMicId);
        if (memberItemWithLinkMicId == null || ((PLVMemberItemDataBean) memberItemWithLinkMicId.second).getLinkMicItemDataBean().isIdleStatus()) {
            return;
        }
        ((PLVMemberItemDataBean) memberItemWithLinkMicId.second).getLinkMicItemDataBean().setStatus(PLVLinkMicItemDataBean.STATUS_IDLE);
        callOnMemberListChangedWithSort();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean updateMemberItemInfo(@NonNull PLVSocketUserBean socketUserBean, @NonNull PLVLinkMicItemDataBean linkMicItemDataBean, boolean isJoinList, boolean isGroupLeader) {
        return updateMemberItemInfo(socketUserBean, linkMicItemDataBean, isJoinList, false, isGroupLeader);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public List<String> updateMemberItemStatus(List<PLVJoinInfoEvent> joinList, List<PLVLinkMicJoinStatus.WaitListBean> waitList) {
        boolean z2;
        boolean z3;
        ArrayList arrayList = new ArrayList();
        Iterator<PLVMemberItemDataBean> it = this.memberList.iterator();
        while (it.hasNext()) {
            PLVLinkMicItemDataBean linkMicItemDataBean = it.next().getLinkMicItemDataBean();
            if (linkMicItemDataBean != null && !linkMicItemDataBean.isIdleStatus() && !isMyLinkMicId(linkMicItemDataBean.getLinkMicId()) && !TextUtils.isEmpty(linkMicItemDataBean.getLinkMicId())) {
                String linkMicId = linkMicItemDataBean.getLinkMicId();
                Iterator<PLVJoinInfoEvent> it2 = joinList.iterator();
                while (true) {
                    z2 = true;
                    if (!it2.hasNext()) {
                        z3 = false;
                        break;
                    }
                    PLVJoinInfoEvent next = it2.next();
                    if (linkMicId != null && linkMicId.equals(next.getUserId())) {
                        z3 = true;
                        break;
                    }
                }
                if (z3) {
                    z2 = z3;
                    if (z2) {
                    }
                } else {
                    for (PLVLinkMicJoinStatus.WaitListBean waitListBean : waitList) {
                        if (linkMicId != null && linkMicId.equals(waitListBean.getUserId())) {
                            break;
                        }
                    }
                    z2 = z3;
                    if (z2 && !linkMicItemDataBean.isJoiningStatus()) {
                        linkMicItemDataBean.setStatus(PLVLinkMicItemDataBean.STATUS_IDLE);
                        arrayList.add(linkMicItemDataBean.getLinkMicId());
                    }
                }
            }
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateMemberListWithListUsers(List<PLVSocketUserBean> socketUserBeanList) {
        PLVLinkMicItemDataBean linkMicItemDataBean;
        LinkedList linkedList = new LinkedList();
        PLVClassStatusBean classStatus = null;
        int i2 = 0;
        while (i2 < socketUserBeanList.size()) {
            PLVSocketUserBean pLVSocketUserBean = socketUserBeanList.get(i2);
            String userId = pLVSocketUserBean.getUserId();
            if (isMySocketUserId(userId)) {
                classStatus = pLVSocketUserBean.getClassStatus();
                socketUserBeanList.remove(pLVSocketUserBean);
                i2--;
            } else if (!"teacher".equals(pLVSocketUserBean.getUserType())) {
                PLVMemberItemDataBean pLVMemberItemDataBean = new PLVMemberItemDataBean();
                pLVMemberItemDataBean.setSocketUserBean(pLVSocketUserBean);
                Pair<Integer, PLVMemberItemDataBean> memberItemWithUserId = getMemberItemWithUserId(userId);
                if (memberItemWithUserId == null || (linkMicItemDataBean = ((PLVMemberItemDataBean) memberItemWithUserId.second).getLinkMicItemDataBean()) == null) {
                    pLVMemberItemDataBean.addBaseLinkMicBean(pLVSocketUserBean);
                } else {
                    boolean zIsHasPaint = linkMicItemDataBean.isHasPaint();
                    int cupNum = linkMicItemDataBean.getCupNum();
                    pLVMemberItemDataBean.setLinkMicItemDataBean(linkMicItemDataBean);
                    checkUpdateHasPermission(pLVSocketUserBean.getClassStatus(), linkMicItemDataBean, zIsHasPaint, cupNum);
                }
                PLVClassStatusBean classStatus2 = pLVSocketUserBean.getClassStatus();
                if (classStatus2 != null && classStatus2.isGroupLeader()) {
                    checkCallLeaderChanged(pLVSocketUserBean.getUserId(), pLVSocketUserBean.getNick());
                }
                linkedList.add(pLVMemberItemDataBean);
            }
            i2++;
        }
        this.memberList = linkedList;
        addMyMemberItem(classStatus);
        callOnMemberListChangedWithSort();
        if (TextUtils.isEmpty(this.groupId) || this.groupLeaderId != null || this.isNoGroupIdCalled) {
            return;
        }
        this.isNoGroupIdCalled = true;
        callOnUserHasGroupLeader(false, "", null);
    }

    public void destroy() {
        this.isRequestedData = false;
        this.memberList.clear();
        this.raiseHandMap.clear();
        this.raiseHandRecoverMap.clear();
        dispose(this.listUsersDisposable);
        dispose(this.listUserTimerDisposable);
        this.handler.removeCallbacksAndMessages(null);
        PLVSocketWrapper.getInstance().getSocketObserver().removeOnConnectStatusListener(this.onConnectStatusListener);
        PLVSocketWrapper.getInstance().getSocketObserver().removeOnMessageListener(this.onMessageListener);
        IPLVLinkMicManager iPLVLinkMicManager = this.linkMicManager;
        if (iPLVLinkMicManager != null) {
            iPLVLinkMicManager.removeEventHandler(this.linkMicEventListener);
        }
    }

    public List<PLVMemberItemDataBean> getData() {
        return this.memberList;
    }

    public PLVMemberItemDataBean getItem(int memberListPos) {
        if (memberListPos < 0 || memberListPos >= this.memberList.size()) {
            return null;
        }
        return this.memberList.get(memberListPos);
    }

    public Pair<Integer, PLVMemberItemDataBean> getItemPairWithLinkMicListPos(int linkMicListPos) {
        PLVMultiRoleLinkMicList pLVMultiRoleLinkMicList = this.linkMicList;
        if (pLVMultiRoleLinkMicList == null || pLVMultiRoleLinkMicList.getItem(linkMicListPos) == null) {
            return null;
        }
        return getMemberItemWithLinkMicId(this.linkMicList.getItem(linkMicListPos).getLinkMicId());
    }

    public int getItemPos(int linkMicListPos) {
        Pair<Integer, PLVMemberItemDataBean> itemPairWithLinkMicListPos = getItemPairWithLinkMicListPos(linkMicListPos);
        if (itemPairWithLinkMicListPos != null) {
            return ((Integer) itemPairWithLinkMicListPos.first).intValue();
        }
        return -1;
    }

    public PLVMemberItemDataBean getItemWithLinkMicListPos(int linkMicListPos) {
        Pair<Integer, PLVMemberItemDataBean> itemPairWithLinkMicListPos = getItemPairWithLinkMicListPos(linkMicListPos);
        if (itemPairWithLinkMicListPos != null) {
            return (PLVMemberItemDataBean) itemPairWithLinkMicListPos.second;
        }
        return null;
    }

    public Pair<Integer, PLVMemberItemDataBean> getMemberItemWithLinkMicId(String linkMicId) {
        return getMemberItemWithLinkMicIdInner(linkMicId);
    }

    public boolean isLeader() {
        return isMySocketUserId(this.groupLeaderId);
    }

    public void observeRTCEvent(IPLVLinkMicManager linkMicManager) {
        this.linkMicManager = linkMicManager;
        observeRTCEventInner();
    }

    public void requestData() {
        this.isRequestedData = true;
        requestMemberListApi();
    }

    public Disposable requestMemberListLess(Consumer<List<PLVSocketUserBean>> onNext) {
        return requestMemberListApiLessInner(onNext);
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public void setLeaderId(String leaderId) {
        if (leaderId != null) {
            checkCallLeaderChanged(leaderId, "");
        }
    }

    public void setLinkMicList(@Nullable PLVMultiRoleLinkMicList linkMicList) {
        this.linkMicList = linkMicList;
        if (linkMicList == null) {
            return;
        }
        linkMicList.addOnLinkMicListListener(new PLVMultiRoleLinkMicList.AbsOnLinkMicListListener() { // from class: com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleMemberList.1
            @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleLinkMicList.AbsOnLinkMicListListener, com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleLinkMicList.OnLinkMicListListener
            public PLVLinkMicItemDataBean onGetSavedLinkMicItem(String linkMicId) {
                Pair<Integer, PLVMemberItemDataBean> memberItemWithLinkMicId = PLVMultiRoleMemberList.this.getMemberItemWithLinkMicId(linkMicId);
                if (memberItemWithLinkMicId == null) {
                    return null;
                }
                return ((PLVMemberItemDataBean) memberItemWithLinkMicId.second).getLinkMicItemDataBean();
            }

            @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleLinkMicList.AbsOnLinkMicListListener, com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleLinkMicList.OnLinkMicListListener
            public void onLinkMicItemIdleChanged(String linkMicId) {
                PLVMultiRoleMemberList.this.updateMemberItemIdleStatus(linkMicId);
            }

            @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleLinkMicList.AbsOnLinkMicListListener, com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleLinkMicList.OnLinkMicListListener
            public void onLinkMicItemInfoChanged() {
                PLVMultiRoleMemberList.this.callOnMemberListChangedWithSort();
            }

            @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleLinkMicList.AbsOnLinkMicListListener, com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleLinkMicList.OnLinkMicListListener
            public void onLinkMicItemRemove(PLVLinkMicItemDataBean linkMicItemDataBean, int position) {
                PLVMultiRoleMemberList.this.updateMemberItemIdleStatus(linkMicItemDataBean.getLinkMicId());
            }

            @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleLinkMicList.AbsOnLinkMicListListener, com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleLinkMicList.OnLinkMicListListener
            public boolean onUpdateLinkMicItemInfo(@NonNull PLVSocketUserBean socketUserBean, @NonNull PLVLinkMicItemDataBean linkMicItemDataBean, boolean isJoinList, boolean isGroupLeader) {
                return PLVMultiRoleMemberList.this.updateMemberItemInfo(socketUserBean, linkMicItemDataBean, isJoinList, isGroupLeader);
            }

            @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleLinkMicList.AbsOnLinkMicListListener, com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleLinkMicList.OnLinkMicListListener
            public List<String> onUpdateLinkMicItemStatus(List<PLVJoinInfoEvent> joinList, List<PLVLinkMicJoinStatus.WaitListBean> waitList) {
                return PLVMultiRoleMemberList.this.updateMemberItemStatus(joinList, waitList);
            }

            @Override // com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleLinkMicList.AbsOnLinkMicListListener, com.easefun.polyv.livecommon.module.modules.multirolelinkmic.model.PLVMultiRoleLinkMicList.OnLinkMicListListener
            public void syncLinkMicItem(PLVLinkMicItemDataBean linkMicItemDataBean, String userId) {
                Pair memberItemWithUserId = PLVMultiRoleMemberList.this.getMemberItemWithUserId(userId);
                if (memberItemWithUserId != null) {
                    PLVClassStatusBean classStatus = ((PLVMemberItemDataBean) memberItemWithUserId.second).getSocketUserBean().getClassStatus();
                    if (classStatus != null) {
                        linkMicItemDataBean.setHasPaint(classStatus.hasPaint());
                        linkMicItemDataBean.setCupNum(classStatus.getCup());
                    }
                    ((PLVMemberItemDataBean) memberItemWithUserId.second).setLinkMicItemDataBean(linkMicItemDataBean);
                }
            }
        });
    }

    public void setMyLinkMicId(String myLinkMicId) {
        this.myLinkMicId = myLinkMicId;
    }

    public void setOnMemberListListener(OnMemberListListener listener) {
        this.onMemberListListener = listener;
    }

    public void updateUserJoining(PLVLinkMicItemDataBean linkMicItemDataBean) {
        if (linkMicItemDataBean != null) {
            linkMicItemDataBean.setStatus(PLVLinkMicItemDataBean.STATUS_JOINING);
            startJoiningTimeoutCount(linkMicItemDataBean);
            callOnMemberListChangedWithSort();
        }
    }

    public void updateUserMuteAudio(final String linkMicId, final boolean isMute, int streamType) {
        acceptUserMuteAudio(linkMicId, isMute, streamType);
    }

    public void updateUserMuteVideo(final String linkMicId, final boolean isMute, int streamType) {
        acceptUserMuteVideo(linkMicId, isMute, streamType);
    }

    private boolean updateMemberItemInfo(@NonNull PLVSocketUserBean socketUserBean, @NonNull PLVLinkMicItemDataBean linkMicItemDataBean, boolean isJoinList, boolean isUpdateJoiningStatus, boolean isGroupLeader) {
        PLVMemberItemDataBean pLVMemberItemDataBean;
        if (isGroupLeader) {
            checkCallLeaderChanged(linkMicItemDataBean.getLinkMicId(), linkMicItemDataBean.getNick());
        }
        boolean z2 = false;
        if (isMyLinkMicId(linkMicItemDataBean.getLinkMicId()) || isMySocketUserId(linkMicItemDataBean.getLinkMicId())) {
            return false;
        }
        Pair<Integer, PLVMemberItemDataBean> memberItemWithUserId = getMemberItemWithUserId(socketUserBean.getUserId());
        if (memberItemWithUserId == null || ((PLVMemberItemDataBean) memberItemWithUserId.second).getLinkMicItemDataBean() == null) {
            if (memberItemWithUserId == null) {
                pLVMemberItemDataBean = new PLVMemberItemDataBean();
                pLVMemberItemDataBean.setSocketUserBean(socketUserBean);
                if (!"teacher".equals(socketUserBean.getUserType())) {
                    this.memberList.add(pLVMemberItemDataBean);
                }
            } else {
                pLVMemberItemDataBean = (PLVMemberItemDataBean) memberItemWithUserId.second;
            }
            pLVMemberItemDataBean.setLinkMicItemDataBean(linkMicItemDataBean);
            PLVMultiRoleLinkMicList pLVMultiRoleLinkMicList = this.linkMicList;
            if (pLVMultiRoleLinkMicList != null) {
                pLVMultiRoleLinkMicList.updateLinkMicItemInfoWithRtcJoinList(linkMicItemDataBean, linkMicItemDataBean.getLinkMicId());
            }
        } else {
            ((PLVMemberItemDataBean) memberItemWithUserId.second).updateBaseLinkMicBean(linkMicItemDataBean);
            PLVLinkMicItemDataBean linkMicItemDataBean2 = ((PLVMemberItemDataBean) memberItemWithUserId.second).getLinkMicItemDataBean();
            boolean zIsJoiningStatus = linkMicItemDataBean2.isJoiningStatus();
            boolean zIsJoinStatus = linkMicItemDataBean2.isJoinStatus();
            boolean zIsWaitStatus = linkMicItemDataBean2.isWaitStatus();
            if (isJoinList) {
                PLVMultiRoleLinkMicList pLVMultiRoleLinkMicList2 = this.linkMicList;
                if (pLVMultiRoleLinkMicList2 != null && pLVMultiRoleLinkMicList2.updateLinkMicItemInfoWithRtcJoinList(linkMicItemDataBean2, linkMicItemDataBean2.getLinkMicId())) {
                    z2 = true;
                }
                if (z2) {
                    return true;
                }
                if (linkMicItemDataBean2.isRtcJoinStatus() || zIsJoinStatus || zIsJoiningStatus) {
                    return z2;
                }
                linkMicItemDataBean2.setStatus(PLVLinkMicItemDataBean.STATUS_JOIN);
            } else {
                if ((zIsJoiningStatus && !isUpdateJoiningStatus) || zIsWaitStatus) {
                    return false;
                }
                linkMicItemDataBean2.setStatus("wait");
            }
        }
        return true;
    }
}
