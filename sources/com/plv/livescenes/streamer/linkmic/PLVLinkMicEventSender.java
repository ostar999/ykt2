package com.plv.livescenes.streamer.linkmic;

import androidx.annotation.Nullable;
import com.easefun.polyv.livescenes.socket.PolyvSocketWrapper;
import com.easefun.polyv.livescenes.streamer.transfer.PLVSStreamerInnerDataTransfer;
import com.google.android.exoplayer2.source.rtsp.RtspMediaSource;
import com.plv.business.model.ppt.PLVChangePPTVideoPositionVO;
import com.plv.business.model.ppt.PLVPPTAuthentic;
import com.plv.foundationsdk.log.PLVCommonLog;
import com.plv.foundationsdk.rx.PLVRxRetryWithDelay;
import com.plv.foundationsdk.utils.PLVGsonUtil;
import com.plv.livescenes.linkmic.manager.PLVLinkMicManager;
import com.plv.livescenes.socket.PLVSocketWrapper;
import com.plv.livescenes.streamer.linkmic.IPLVLinkMicEventSender;
import com.plv.socket.event.PLVEventConstant;
import com.plv.socket.event.linkmic.PLVJoinAnswerSEvent;
import com.plv.socket.event.linkmic.PLVJoinResponseSEvent;
import com.plv.socket.event.linkmic.PLVMuteUserMediaEvent;
import com.plv.socket.event.linkmic.PLVOpenMicrophoneEvent;
import com.plv.socket.event.linkmic.PLVTeacherSetPermissionEvent;
import com.plv.socket.event.ppt.PLVFinishClassEvent;
import com.plv.socket.event.ppt.PLVOnSliceIDEvent;
import com.plv.socket.event.ppt.PLVOnSliceStartEvent;
import com.plv.socket.event.seminar.PLVCancelHelpEvent;
import com.plv.socket.event.seminar.PLVGroupRequestHelpEvent;
import com.plv.socket.impl.PLVSocketMessageObserver;
import com.plv.socket.net.model.PLVSocketLoginVO;
import com.plv.socket.user.PLVSocketUserBean;
import com.plv.thirdpart.blankj.utilcode.util.EncryptUtils;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.socket.client.Ack;
import java.util.concurrent.TimeUnit;
import org.jetbrains.annotations.NotNull;

/* loaded from: classes5.dex */
public class PLVLinkMicEventSender implements IPLVLinkMicEventSender {
    private static final String TAG = "PLVLinkMicEventSender";
    private static volatile PLVLinkMicEventSender singleton;
    private Disposable guestAutoLinkMicDisposable;
    private boolean isVideoLinkMicType = true;
    private PLVSocketMessageObserver.OnMessageListener onMessageListener = null;

    /* renamed from: com.plv.livescenes.streamer.linkmic.PLVLinkMicEventSender$3, reason: invalid class name */
    public class AnonymousClass3 implements Function<String, ObservableSource<String>> {
        private PLVSocketMessageObserver.OnMessageListener onMessageListener;
        final /* synthetic */ IPLVLinkMicEventSender.IPLVGuestAutoLinkMicListener val$li;
        final /* synthetic */ PLVSocketUserBean val$socketUserBean;
        final /* synthetic */ TeacherSetPermissionWatcher val$teacherSetPermissionWatcher;

        public AnonymousClass3(TeacherSetPermissionWatcher teacherSetPermissionWatcher, IPLVLinkMicEventSender.IPLVGuestAutoLinkMicListener iPLVGuestAutoLinkMicListener, PLVSocketUserBean pLVSocketUserBean) {
            this.val$teacherSetPermissionWatcher = teacherSetPermissionWatcher;
            this.val$li = iPLVGuestAutoLinkMicListener;
            this.val$socketUserBean = pLVSocketUserBean;
        }

        @Override // io.reactivex.functions.Function
        public ObservableSource<String> apply(@NotNull String str) throws Exception {
            return Observable.create(new ObservableOnSubscribe<String>() { // from class: com.plv.livescenes.streamer.linkmic.PLVLinkMicEventSender.3.3
                @Override // io.reactivex.ObservableOnSubscribe
                public void subscribe(@NotNull final ObservableEmitter<String> observableEmitter) throws Exception {
                    AnonymousClass3.this.val$teacherSetPermissionWatcher.init(new TeacherSetPermissionWatcher.TeacherSetPermissionWatcherListener() { // from class: com.plv.livescenes.streamer.linkmic.PLVLinkMicEventSender.3.3.1
                        @Override // com.plv.livescenes.streamer.linkmic.PLVLinkMicEventSender.TeacherSetPermissionWatcher.TeacherSetPermissionWatcherListener
                        public void off() {
                        }

                        @Override // com.plv.livescenes.streamer.linkmic.PLVLinkMicEventSender.TeacherSetPermissionWatcher.TeacherSetPermissionWatcherListener
                        public void on() {
                            observableEmitter.onNext("");
                            observableEmitter.onComplete();
                            AnonymousClass3.this.val$li.onAutoLinkMic();
                        }
                    });
                    PLVJoinResponseSEvent pLVJoinResponseSEvent = new PLVJoinResponseSEvent();
                    pLVJoinResponseSEvent.setRoomId(PLVSocketWrapper.getInstance().getLoginRoomId());
                    pLVJoinResponseSEvent.setValue("1");
                    pLVJoinResponseSEvent.setToEmitAll(1);
                    PLVJoinResponseSEvent.UserBean userBean = new PLVJoinResponseSEvent.UserBean();
                    userBean.setBanned(AnonymousClass3.this.val$socketUserBean.isBanned());
                    userBean.setChannelId(AnonymousClass3.this.val$socketUserBean.getChannelId());
                    userBean.setClientIp(AnonymousClass3.this.val$socketUserBean.getClientIp());
                    userBean.setNick(AnonymousClass3.this.val$socketUserBean.getNick());
                    userBean.setPic(AnonymousClass3.this.val$socketUserBean.getPic());
                    userBean.setUid(AnonymousClass3.this.val$socketUserBean.getUid());
                    userBean.setUserId(AnonymousClass3.this.val$socketUserBean.getUserId());
                    userBean.setUserType(AnonymousClass3.this.val$socketUserBean.getUserType());
                    pLVJoinResponseSEvent.setUser(userBean);
                    PLVSocketWrapper.getInstance().emit(PLVEventConstant.LinkMic.JOIN_RESPONSE_EVENT, pLVJoinResponseSEvent);
                }
            }).timeout(RtspMediaSource.DEFAULT_TIMEOUT_MS, TimeUnit.MILLISECONDS, new ObservableSource<String>() { // from class: com.plv.livescenes.streamer.linkmic.PLVLinkMicEventSender.3.2
                @Override // io.reactivex.ObservableSource
                public void subscribe(@NotNull Observer<? super String> observer) {
                    AnonymousClass3.this.val$li.onTimeout();
                    observer.onComplete();
                }
            }).doFinally(new Action() { // from class: com.plv.livescenes.streamer.linkmic.PLVLinkMicEventSender.3.1
                @Override // io.reactivex.functions.Action
                public void run() throws Exception {
                    PLVCommonLog.d(PLVLinkMicEventSender.TAG, "doFinally");
                    AnonymousClass3.this.val$teacherSetPermissionWatcher.release();
                    AnonymousClass3.this.val$teacherSetPermissionWatcher.init(new TeacherSetPermissionWatcher.TeacherSetPermissionWatcherListener() { // from class: com.plv.livescenes.streamer.linkmic.PLVLinkMicEventSender.3.1.1
                        @Override // com.plv.livescenes.streamer.linkmic.PLVLinkMicEventSender.TeacherSetPermissionWatcher.TeacherSetPermissionWatcherListener
                        public void off() {
                            AnonymousClass3.this.val$li.onHangupByTeacher();
                        }

                        @Override // com.plv.livescenes.streamer.linkmic.PLVLinkMicEventSender.TeacherSetPermissionWatcher.TeacherSetPermissionWatcherListener
                        public void on() {
                            AnonymousClass3.this.val$li.onInviteByTeacher();
                        }
                    });
                }
            });
        }
    }

    public static class TeacherSetPermissionWatcher {
        private TeacherSetPermissionWatcherListener listener;
        private PLVSocketMessageObserver.OnMessageListener onMessageListener;
        private String userId;

        public interface TeacherSetPermissionWatcherListener {
            void off();

            void on();
        }

        public TeacherSetPermissionWatcher(String str) {
            this.userId = str;
        }

        public void init(TeacherSetPermissionWatcherListener teacherSetPermissionWatcherListener) {
            this.listener = teacherSetPermissionWatcherListener;
            this.onMessageListener = new PLVSocketMessageObserver.OnMessageListener() { // from class: com.plv.livescenes.streamer.linkmic.PLVLinkMicEventSender.TeacherSetPermissionWatcher.1
                @Override // com.plv.socket.impl.PLVSocketMessageObserver.OnMessageListener
                public void onMessage(String str, String str2, String str3) {
                    PLVPPTAuthentic pLVPPTAuthentic;
                    if (PLVEventConstant.LinkMic.TEACHER_SET_PERMISSION.equals(str2) && (pLVPPTAuthentic = (PLVPPTAuthentic) PLVGsonUtil.fromJson(PLVPPTAuthentic.class, str3)) != null && pLVPPTAuthentic.getUserId().equals(TeacherSetPermissionWatcher.this.userId) && "voice".equals(pLVPPTAuthentic.getType())) {
                        if (pLVPPTAuthentic.getStatus().equals("1")) {
                            if (TeacherSetPermissionWatcher.this.listener != null) {
                                TeacherSetPermissionWatcher.this.listener.on();
                            }
                        } else if (TeacherSetPermissionWatcher.this.listener != null) {
                            TeacherSetPermissionWatcher.this.listener.off();
                        }
                    }
                }
            };
            PLVSocketWrapper.getInstance().getSocketObserver().addOnMessageListener(this.onMessageListener);
        }

        public void release() {
            this.listener = null;
            PLVSocketWrapper.getInstance().getSocketObserver().removeOnMessageListener(this.onMessageListener);
        }
    }

    private PLVLinkMicEventSender() {
    }

    private void dispose(Disposable disposable) {
        if (disposable != null) {
            disposable.dispose();
        }
    }

    public static PLVLinkMicEventSender getInstance() {
        if (singleton == null) {
            synchronized (PLVLinkMicEventSender.class) {
                if (singleton == null) {
                    singleton = new PLVLinkMicEventSender();
                }
            }
        }
        return singleton;
    }

    @Nullable
    private PLVSocketLoginVO getLoginVO() {
        return PolyvSocketWrapper.getInstance().getLoginVO();
    }

    @Override // com.plv.livescenes.streamer.linkmic.IPLVLinkMicEventSender
    public void closeAllUserLinkMic(String str, Ack ack) {
        PLVTeacherSetPermissionEvent pLVTeacherSetPermissionEvent = new PLVTeacherSetPermissionEvent();
        pLVTeacherSetPermissionEvent.setRoomId(PolyvSocketWrapper.getInstance().getLoginRoomId());
        pLVTeacherSetPermissionEvent.setSessionId(str);
        pLVTeacherSetPermissionEvent.setUserId("all");
        pLVTeacherSetPermissionEvent.setEmitMode(0);
        pLVTeacherSetPermissionEvent.setStatus("0");
        pLVTeacherSetPermissionEvent.setType("voice");
        pLVTeacherSetPermissionEvent.setToAll(true);
        pLVTeacherSetPermissionEvent.setSign(EncryptUtils.encryptMD5ToString("polyvChatSignEVENT" + pLVTeacherSetPermissionEvent.getEVENT() + PLVLinkMicManager.EMIT_MODE + pLVTeacherSetPermissionEvent.getEVENT() + PLVLinkMicManager.ROOM_ID + pLVTeacherSetPermissionEvent.getRoomId() + PLVLinkMicManager.SESSION_ID + str + "status" + pLVTeacherSetPermissionEvent.getStatus() + PLVLinkMicManager.TO_ALL + pLVTeacherSetPermissionEvent.isToAll() + "type" + pLVTeacherSetPermissionEvent.getType() + "userId" + pLVTeacherSetPermissionEvent.getUserId() + PLVLinkMicManager.PLV_CHAT_SIGN).toUpperCase());
        PolyvSocketWrapper.getInstance().emit("message", pLVTeacherSetPermissionEvent, ack);
    }

    @Override // com.plv.livescenes.streamer.linkmic.IPLVLinkMicEventSender
    public void closeLinkMic() {
        openLinkMic(this.isVideoLinkMicType, false, null);
    }

    @Override // com.plv.livescenes.streamer.linkmic.IPLVLinkMicEventSender
    public void closeUserLinkMic(String str, Ack ack) {
        PLVOpenMicrophoneEvent pLVOpenMicrophoneEvent = new PLVOpenMicrophoneEvent();
        pLVOpenMicrophoneEvent.setEVENT("OPEN_MICROPHONE");
        pLVOpenMicrophoneEvent.setRoomId(PolyvSocketWrapper.getInstance().getLoginRoomId());
        pLVOpenMicrophoneEvent.setStatus("close");
        pLVOpenMicrophoneEvent.setUserId(str);
        pLVOpenMicrophoneEvent.setType(this.isVideoLinkMicType ? "video" : "audio");
        PolyvSocketWrapper.getInstance().emit("message", pLVOpenMicrophoneEvent, ack);
    }

    @Override // com.plv.livescenes.streamer.linkmic.IPLVLinkMicEventSender
    public void emitFinishClassEvent(String str) {
        if (getLoginVO() == null) {
            return;
        }
        PolyvSocketWrapper.getInstance().emit("message", new PLVFinishClassEvent(PolyvSocketWrapper.getInstance().getLoginRoomId(), getLoginVO().getChannelId(), str));
    }

    @Override // com.plv.livescenes.streamer.linkmic.IPLVLinkMicEventSender
    public void emitStartClassEvent(String str, long j2) {
        if (getLoginVO() == null) {
            return;
        }
        PolyvSocketWrapper.getInstance().emit("message", new PLVOnSliceStartEvent(1, PolyvSocketWrapper.getInstance().getLoginRoomId(), str, 0L, getLoginVO().getUserId(), 0, PLVOnSliceStartEvent.COURSE_TYPE_HI_CLASS, true, j2));
    }

    @Override // com.plv.livescenes.streamer.linkmic.IPLVLinkMicEventSender
    public void groupCancelHelp(Ack ack) {
        PolyvSocketWrapper.getInstance().emit(PLVEventConstant.Seminar.SEMINAR_EVENT, new PLVCancelHelpEvent(), ack);
    }

    @Override // com.plv.livescenes.streamer.linkmic.IPLVLinkMicEventSender
    public void groupRequestHelp(Ack ack) {
        PolyvSocketWrapper.getInstance().emit(PLVEventConstant.Seminar.SEMINAR_EVENT, new PLVGroupRequestHelpEvent(), ack);
    }

    @Override // com.plv.livescenes.streamer.linkmic.IPLVLinkMicEventSender
    public void guestAutoLinkMic(int i2, final IPLVLinkMicEventSender.IPLVGuestAutoLinkMicListener iPLVGuestAutoLinkMicListener) {
        PLVSocketUserBean pLVSocketUserBeanCreateSocketUserBean = PLVSocketWrapper.getInstance().getLoginVO().createSocketUserBean();
        TeacherSetPermissionWatcher teacherSetPermissionWatcher = new TeacherSetPermissionWatcher(pLVSocketUserBeanCreateSocketUserBean.getUserId());
        dispose(this.guestAutoLinkMicDisposable);
        this.guestAutoLinkMicDisposable = Observable.create(new ObservableOnSubscribe<String>() { // from class: com.plv.livescenes.streamer.linkmic.PLVLinkMicEventSender.5
            @Override // io.reactivex.ObservableOnSubscribe
            public void subscribe(@NotNull ObservableEmitter<String> observableEmitter) throws Exception {
                if (PLVSocketWrapper.getInstance().isOnlineStatus()) {
                    observableEmitter.onNext("");
                } else {
                    if (observableEmitter.isDisposed()) {
                        return;
                    }
                    observableEmitter.onError(new Exception("socket is not online"));
                }
            }
        }).retryWhen(new PLVRxRetryWithDelay(i2, 1000, new PLVRxRetryWithDelay.PLVRxRetryWithDelayListener() { // from class: com.plv.livescenes.streamer.linkmic.PLVLinkMicEventSender.4
            @Override // com.plv.foundationsdk.rx.PLVRxRetryWithDelay.PLVRxRetryWithDelayListener
            public void onRetry(Throwable th) {
                PLVCommonLog.i(PLVLinkMicEventSender.TAG, "guestAutoLinkMic->retry check is socket online");
            }

            @Override // com.plv.foundationsdk.rx.PLVRxRetryWithDelay.PLVRxRetryWithDelayListener
            public void onRetryEnd(Throwable th) {
                PLVCommonLog.e(PLVLinkMicEventSender.TAG, "guestAutoLinkMic->retry end" + th);
            }
        })).flatMap(new AnonymousClass3(teacherSetPermissionWatcher, iPLVGuestAutoLinkMicListener, pLVSocketUserBeanCreateSocketUserBean)).subscribe(new Consumer<String>() { // from class: com.plv.livescenes.streamer.linkmic.PLVLinkMicEventSender.1
            @Override // io.reactivex.functions.Consumer
            public void accept(String str) throws Exception {
                PLVCommonLog.d(PLVLinkMicEventSender.TAG, "guestAutoLinkMic, onNext->" + str);
            }
        }, new Consumer<Throwable>() { // from class: com.plv.livescenes.streamer.linkmic.PLVLinkMicEventSender.2
            @Override // io.reactivex.functions.Consumer
            public void accept(Throwable th) throws Exception {
                iPLVGuestAutoLinkMicListener.onTimeout();
                th.printStackTrace();
            }
        });
    }

    @Override // com.plv.livescenes.streamer.linkmic.IPLVLinkMicEventSender
    public boolean isVideoLinkMicType() {
        return this.isVideoLinkMicType;
    }

    @Override // com.plv.livescenes.streamer.linkmic.IPLVLinkMicEventSender
    public void listenerClassDuration(final IPLVLinkMicEventSender.IPLVGuestClassDuration iPLVGuestClassDuration) {
        this.onMessageListener = new PLVSocketMessageObserver.OnMessageListener() { // from class: com.plv.livescenes.streamer.linkmic.PLVLinkMicEventSender.6
            @Override // com.plv.socket.impl.PLVSocketMessageObserver.OnMessageListener
            public void onMessage(String str, String str2, String str3) {
                PLVOnSliceStartEvent pLVOnSliceStartEvent;
                if (str2 == null) {
                    return;
                }
                if (str2.equals("onSliceID")) {
                    PLVOnSliceIDEvent pLVOnSliceIDEvent = (PLVOnSliceIDEvent) PLVGsonUtil.fromJson(PLVOnSliceIDEvent.class, str3);
                    if (pLVOnSliceIDEvent != null) {
                        iPLVGuestClassDuration.onClassDuration(pLVOnSliceIDEvent.getData().getPushtime(), pLVOnSliceIDEvent.getData().getDuration());
                        return;
                    }
                    return;
                }
                if (str2.equals("onSliceStart") && (pLVOnSliceStartEvent = (PLVOnSliceStartEvent) PLVGsonUtil.fromJson(PLVOnSliceStartEvent.class, str3)) != null) {
                    iPLVGuestClassDuration.onClassDuration(pLVOnSliceStartEvent.getPushtime(), 0L);
                }
            }
        };
        PLVSocketWrapper.getInstance().getSocketObserver().addOnMessageListener(this.onMessageListener);
    }

    @Override // com.plv.livescenes.streamer.linkmic.IPLVLinkMicEventSender
    public void muteUserMedia(PLVSocketUserBean pLVSocketUserBean, String str, boolean z2, boolean z3, Ack ack) {
        if (getLoginVO() == null) {
            return;
        }
        PLVMuteUserMediaEvent pLVMuteUserMediaEvent = new PLVMuteUserMediaEvent();
        pLVMuteUserMediaEvent.setEVENT(PLVEventConstant.LinkMic.EVENT_MUTE_USER_MEDIA);
        pLVMuteUserMediaEvent.setMute(z3);
        pLVMuteUserMediaEvent.setSessionId(str);
        pLVMuteUserMediaEvent.setTeacherId(getLoginVO().getUserId());
        pLVMuteUserMediaEvent.setUserId(pLVSocketUserBean.getUserId());
        pLVMuteUserMediaEvent.setUid(pLVSocketUserBean.getUid());
        pLVMuteUserMediaEvent.setType(z2 ? "video" : "audio");
        PolyvSocketWrapper.getInstance().emit("message", pLVMuteUserMediaEvent, ack);
    }

    @Override // com.plv.livescenes.streamer.linkmic.IPLVLinkMicEventSender
    public boolean openLinkMic(boolean z2, boolean z3, Ack ack) {
        if (PLVSStreamerInnerDataTransfer.getInstance().getInteractNumLimit() == 0 || getLoginVO() == null) {
            return false;
        }
        this.isVideoLinkMicType = z2;
        PLVOpenMicrophoneEvent pLVOpenMicrophoneEvent = new PLVOpenMicrophoneEvent();
        pLVOpenMicrophoneEvent.setEVENT("OPEN_MICROPHONE");
        pLVOpenMicrophoneEvent.setRoomId(PolyvSocketWrapper.getInstance().getLoginRoomId());
        pLVOpenMicrophoneEvent.setStatus(z3 ? "open" : "close");
        pLVOpenMicrophoneEvent.setTeacherId(getLoginVO().getUserId());
        pLVOpenMicrophoneEvent.setType(z2 ? "video" : "audio");
        PolyvSocketWrapper.getInstance().emit("message", pLVOpenMicrophoneEvent, ack);
        return true;
    }

    @Override // com.plv.livescenes.streamer.linkmic.IPLVLinkMicEventSender
    public void release() {
        dispose(this.guestAutoLinkMicDisposable);
        if (this.onMessageListener != null) {
            PLVSocketWrapper.getInstance().getSocketObserver().removeOnMessageListener(this.onMessageListener);
            this.onMessageListener = null;
        }
    }

    @Override // com.plv.livescenes.streamer.linkmic.IPLVLinkMicEventSender
    public void responseUserLinkMic(PLVSocketUserBean pLVSocketUserBean, Ack ack) {
        responseUserLinkMic(pLVSocketUserBean, false, ack);
    }

    @Override // com.plv.livescenes.streamer.linkmic.IPLVLinkMicEventSender
    public void sendCupEvent(PLVSocketUserBean pLVSocketUserBean, String str, Ack ack) {
        PLVTeacherSetPermissionEvent pLVTeacherSetPermissionEvent = new PLVTeacherSetPermissionEvent();
        pLVTeacherSetPermissionEvent.setRoomId(PolyvSocketWrapper.getInstance().getLoginRoomId());
        pLVTeacherSetPermissionEvent.setSessionId(str);
        pLVTeacherSetPermissionEvent.setUserId(pLVSocketUserBean.getUserId());
        pLVTeacherSetPermissionEvent.setEmitMode(0);
        pLVTeacherSetPermissionEvent.setStatus("1");
        pLVTeacherSetPermissionEvent.setType(PLVTeacherSetPermissionEvent.TYPE_CUP);
        pLVTeacherSetPermissionEvent.setSign(EncryptUtils.encryptMD5ToString("polyvChatSignEVENT" + pLVTeacherSetPermissionEvent.getEVENT() + PLVLinkMicManager.EMIT_MODE + pLVTeacherSetPermissionEvent.getEVENT() + PLVLinkMicManager.ROOM_ID + pLVTeacherSetPermissionEvent.getRoomId() + PLVLinkMicManager.SESSION_ID + str + "status" + pLVTeacherSetPermissionEvent.getStatus() + "type" + pLVTeacherSetPermissionEvent.getType() + "userId" + pLVTeacherSetPermissionEvent.getUserId() + PLVLinkMicManager.PLV_CHAT_SIGN).toUpperCase());
        PolyvSocketWrapper.getInstance().emit("message", pLVTeacherSetPermissionEvent, ack);
    }

    @Override // com.plv.livescenes.streamer.linkmic.IPLVLinkMicEventSender
    public void sendJoinAnswerEvent() {
        PLVJoinAnswerSEvent pLVJoinAnswerSEvent = new PLVJoinAnswerSEvent();
        pLVJoinAnswerSEvent.setStatus(1);
        PolyvSocketWrapper.getInstance().emit(PLVEventConstant.LinkMic.JOIN_ANSWER_EVENT, pLVJoinAnswerSEvent);
    }

    @Override // com.plv.livescenes.streamer.linkmic.IPLVLinkMicEventSender
    public void sendRaiseHandEvent(int i2, String str, Ack ack) {
        if (getLoginVO() == null) {
            return;
        }
        PLVTeacherSetPermissionEvent pLVTeacherSetPermissionEvent = new PLVTeacherSetPermissionEvent();
        pLVTeacherSetPermissionEvent.setRoomId(PolyvSocketWrapper.getInstance().getLoginRoomId());
        pLVTeacherSetPermissionEvent.setSessionId(str);
        pLVTeacherSetPermissionEvent.setUserId(getLoginVO().getUserId());
        pLVTeacherSetPermissionEvent.setEmitMode(0);
        pLVTeacherSetPermissionEvent.setStatus("1");
        pLVTeacherSetPermissionEvent.setType(PLVTeacherSetPermissionEvent.TYPE_RAISE_HAND);
        pLVTeacherSetPermissionEvent.setRaiseHandTime(i2);
        pLVTeacherSetPermissionEvent.setSign(EncryptUtils.encryptMD5ToString("polyvChatSignEVENT" + pLVTeacherSetPermissionEvent.getEVENT() + PLVLinkMicManager.EMIT_MODE + pLVTeacherSetPermissionEvent.getEVENT() + PLVLinkMicManager.RAISE_HAND_TIME + pLVTeacherSetPermissionEvent.getRaiseHandTime() + PLVLinkMicManager.ROOM_ID + pLVTeacherSetPermissionEvent.getRoomId() + PLVLinkMicManager.SESSION_ID + str + "status" + pLVTeacherSetPermissionEvent.getStatus() + "type" + pLVTeacherSetPermissionEvent.getType() + "userId" + pLVTeacherSetPermissionEvent.getUserId() + PLVLinkMicManager.PLV_CHAT_SIGN).toUpperCase());
        PolyvSocketWrapper.getInstance().emit("message", pLVTeacherSetPermissionEvent, ack);
    }

    @Override // com.plv.livescenes.streamer.linkmic.IPLVLinkMicEventSender
    public void sendScreenShareEvent(PLVSocketUserBean pLVSocketUserBean, String str, boolean z2, Ack ack) {
        PLVPPTAuthentic pLVPPTAuthentic = new PLVPPTAuthentic();
        String str2 = z2 ? "1" : "0";
        pLVPPTAuthentic.setEVENT(PLVEventConstant.LinkMic.TEACHER_SET_PERMISSION);
        pLVPPTAuthentic.setEmitMode(0);
        pLVPPTAuthentic.setRoomId(PLVSocketWrapper.getInstance().getLoginVO().getChannelId());
        pLVPPTAuthentic.setSessionId(str);
        pLVPPTAuthentic.setStatus(str2);
        pLVPPTAuthentic.setType(PLVPPTAuthentic.PermissionType.SCREEN_SHARE);
        pLVPPTAuthentic.setUserId(pLVSocketUserBean.getUserId());
        pLVPPTAuthentic.setSign(EncryptUtils.encryptMD5ToString("polyvChatSignEVENTTEACHER_SET_PERMISSIONemitMode0roomId" + PLVSocketWrapper.getInstance().getLoginVO().getChannelId() + PLVLinkMicManager.SESSION_ID + str + "status" + str2 + "typespeakeruserId" + pLVSocketUserBean.getUserId() + PLVLinkMicManager.PLV_CHAT_SIGN).toUpperCase());
        PolyvSocketWrapper.getInstance().emit("message", PLVGsonUtil.toJson(pLVPPTAuthentic), ack);
    }

    @Override // com.plv.livescenes.streamer.linkmic.IPLVLinkMicEventSender
    public void setDocumentStreamerViewPosition(boolean z2, String str) {
        PLVChangePPTVideoPositionVO pLVChangePPTVideoPositionVO = new PLVChangePPTVideoPositionVO();
        pLVChangePPTVideoPositionVO.setEVENT(PLVEventConstant.Class.SE_SWITCH_PPT_MESSAGE);
        pLVChangePPTVideoPositionVO.setRoomId(PLVSocketWrapper.getInstance().getLoginVO().getChannelId());
        pLVChangePPTVideoPositionVO.setStatus(Integer.valueOf(!z2 ? 1 : 0));
        pLVChangePPTVideoPositionVO.setSessionId(str);
        PLVSocketWrapper.getInstance().emit("message", PLVGsonUtil.toJson(pLVChangePPTVideoPositionVO));
    }

    @Override // com.plv.livescenes.streamer.linkmic.IPLVLinkMicEventSender
    public void setMediaPermission(PLVSocketUserBean pLVSocketUserBean, String str, boolean z2, boolean z3, Ack ack) {
        PLVTeacherSetPermissionEvent pLVTeacherSetPermissionEvent = new PLVTeacherSetPermissionEvent();
        pLVTeacherSetPermissionEvent.setRoomId(PolyvSocketWrapper.getInstance().getLoginRoomId());
        pLVTeacherSetPermissionEvent.setSessionId(str);
        pLVTeacherSetPermissionEvent.setUserId(pLVSocketUserBean.getUserId());
        pLVTeacherSetPermissionEvent.setEmitMode(0);
        pLVTeacherSetPermissionEvent.setStatus(!z3 ? "1" : "0");
        pLVTeacherSetPermissionEvent.setType(z2 ? "video" : "audio");
        pLVTeacherSetPermissionEvent.setSign(EncryptUtils.encryptMD5ToString("polyvChatSignEVENT" + pLVTeacherSetPermissionEvent.getEVENT() + PLVLinkMicManager.EMIT_MODE + pLVTeacherSetPermissionEvent.getEVENT() + PLVLinkMicManager.ROOM_ID + pLVTeacherSetPermissionEvent.getRoomId() + PLVLinkMicManager.SESSION_ID + str + "status" + pLVTeacherSetPermissionEvent.getStatus() + "type" + pLVTeacherSetPermissionEvent.getType() + "userId" + pLVTeacherSetPermissionEvent.getUserId() + PLVLinkMicManager.PLV_CHAT_SIGN).toUpperCase());
        PolyvSocketWrapper.getInstance().emit("message", pLVTeacherSetPermissionEvent, ack);
    }

    @Override // com.plv.livescenes.streamer.linkmic.IPLVLinkMicEventSender
    public void setPaintPermission(PLVSocketUserBean pLVSocketUserBean, String str, boolean z2, Ack ack) {
        PLVTeacherSetPermissionEvent pLVTeacherSetPermissionEvent = new PLVTeacherSetPermissionEvent();
        pLVTeacherSetPermissionEvent.setRoomId(PolyvSocketWrapper.getInstance().getLoginRoomId());
        pLVTeacherSetPermissionEvent.setSessionId(str);
        pLVTeacherSetPermissionEvent.setUserId(pLVSocketUserBean.getUserId());
        pLVTeacherSetPermissionEvent.setEmitMode(0);
        pLVTeacherSetPermissionEvent.setStatus(z2 ? "1" : "0");
        pLVTeacherSetPermissionEvent.setType("paint");
        pLVTeacherSetPermissionEvent.setSign(EncryptUtils.encryptMD5ToString("polyvChatSignEVENT" + pLVTeacherSetPermissionEvent.getEVENT() + PLVLinkMicManager.EMIT_MODE + pLVTeacherSetPermissionEvent.getEVENT() + PLVLinkMicManager.ROOM_ID + pLVTeacherSetPermissionEvent.getRoomId() + PLVLinkMicManager.SESSION_ID + str + "status" + pLVTeacherSetPermissionEvent.getStatus() + "type" + pLVTeacherSetPermissionEvent.getType() + "userId" + pLVTeacherSetPermissionEvent.getUserId() + PLVLinkMicManager.PLV_CHAT_SIGN).toUpperCase());
        PolyvSocketWrapper.getInstance().emit("message", pLVTeacherSetPermissionEvent, ack);
    }

    @Override // com.plv.livescenes.streamer.linkmic.IPLVLinkMicEventSender
    public void setSpeakerPermission(PLVSocketUserBean pLVSocketUserBean, String str, boolean z2, Ack ack) {
        if (pLVSocketUserBean == null) {
            return;
        }
        String str2 = z2 ? "1" : "0";
        PLVPPTAuthentic pLVPPTAuthentic = new PLVPPTAuthentic();
        pLVPPTAuthentic.setEVENT(PLVEventConstant.LinkMic.TEACHER_SET_PERMISSION);
        pLVPPTAuthentic.setEmitMode(0);
        pLVPPTAuthentic.setRoomId(PLVSocketWrapper.getInstance().getLoginVO().getChannelId());
        pLVPPTAuthentic.setSessionId(str);
        pLVPPTAuthentic.setStatus(str2);
        pLVPPTAuthentic.setType("speaker");
        pLVPPTAuthentic.setUserId(pLVSocketUserBean.getUserId());
        pLVPPTAuthentic.setSign(EncryptUtils.encryptMD5ToString("polyvChatSignEVENTTEACHER_SET_PERMISSIONemitMode0roomId" + PLVSocketWrapper.getInstance().getLoginVO().getChannelId() + PLVLinkMicManager.SESSION_ID + str + "status" + str2 + "typespeakeruserId" + pLVSocketUserBean.getUserId() + PLVLinkMicManager.PLV_CHAT_SIGN).toUpperCase());
        PolyvSocketWrapper.getInstance().emit("message", PLVGsonUtil.toJson(pLVPPTAuthentic), ack);
    }

    @Override // com.plv.livescenes.streamer.linkmic.IPLVLinkMicEventSender
    public void setSwitchFirstView(PLVSocketUserBean pLVSocketUserBean, Ack ack) {
        if (pLVSocketUserBean == null) {
            return;
        }
        PLVPPTAuthentic pLVPPTAuthentic = new PLVPPTAuthentic();
        pLVPPTAuthentic.setEmitMode(0);
        pLVPPTAuthentic.setRoomId(PLVSocketWrapper.getInstance().getLoginVO().getChannelId());
        pLVPPTAuthentic.setUserId(pLVSocketUserBean.getUserId());
        PolyvSocketWrapper.getInstance().emit(PLVEventConstant.Class.SE_SWITCH_MESSAGE, PLVGsonUtil.toJson(pLVPPTAuthentic), ack);
    }

    @Override // com.plv.livescenes.streamer.linkmic.IPLVLinkMicEventSender
    public void responseUserLinkMic(PLVSocketUserBean pLVSocketUserBean, boolean z2, Ack ack) {
        PLVJoinResponseSEvent pLVJoinResponseSEvent = new PLVJoinResponseSEvent();
        pLVJoinResponseSEvent.setRoomId(PolyvSocketWrapper.getInstance().getLoginRoomId());
        pLVJoinResponseSEvent.setValue("1");
        pLVJoinResponseSEvent.setToEmitAll(1);
        if ("guest".equals(pLVSocketUserBean.getUserType())) {
            pLVJoinResponseSEvent.setNeedAnswer(!PLVSStreamerInnerDataTransfer.getInstance().isAutoLinkToGuest() ? 1 : 0);
        }
        if (z2) {
            pLVJoinResponseSEvent.setNeedAnswer(1);
        }
        PLVJoinResponseSEvent.UserBean userBean = new PLVJoinResponseSEvent.UserBean();
        userBean.setBanned(pLVSocketUserBean.isBanned());
        userBean.setChannelId(pLVSocketUserBean.getChannelId());
        userBean.setClientIp(pLVSocketUserBean.getClientIp());
        userBean.setNick(pLVSocketUserBean.getNick());
        userBean.setPic(pLVSocketUserBean.getPic());
        userBean.setUid(pLVSocketUserBean.getUid());
        userBean.setUserId(pLVSocketUserBean.getUserId());
        userBean.setUserType(pLVSocketUserBean.getUserType());
        pLVJoinResponseSEvent.setUser(userBean);
        PolyvSocketWrapper.getInstance().emit(PLVEventConstant.LinkMic.JOIN_RESPONSE_EVENT, pLVJoinResponseSEvent, ack);
    }

    @Override // com.plv.livescenes.streamer.linkmic.IPLVLinkMicEventSender
    public void emitFinishClassEvent(boolean z2) {
        PolyvSocketWrapper.getInstance().emit("message", new PLVFinishClassEvent(z2 ? 1 : 0));
    }
}
