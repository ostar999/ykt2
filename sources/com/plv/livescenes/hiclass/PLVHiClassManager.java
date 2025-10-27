package com.plv.livescenes.hiclass;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import com.easefun.polyv.livescenes.socket.PolyvSocketWrapper;
import com.google.android.exoplayer2.C;
import com.heytap.mcssdk.constant.a;
import com.plv.foundationsdk.log.PLVCommonLog;
import com.plv.foundationsdk.rx.PLVRxBaseRetryFunction;
import com.plv.foundationsdk.rx.PLVRxBaseTransformer;
import com.plv.foundationsdk.utils.PLVGsonUtil;
import com.plv.livescenes.hiclass.IPLVHiClassManager;
import com.plv.livescenes.hiclass.api.PLVHCApiManager;
import com.plv.livescenes.hiclass.vo.PLVHCChangeLessonStatusVO;
import com.plv.livescenes.hiclass.vo.PLVHCLessonFinishVO;
import com.plv.livescenes.hiclass.vo.PLVHCStudentLessonListVO;
import com.plv.livescenes.hiclass.vo.PLVHCTeacherLoginVerifyVO;
import com.plv.livescenes.linkmic.manager.PLVLinkMicConfig;
import com.plv.livescenes.net.IPLVDataRequestListener;
import com.plv.livescenes.streamer.linkmic.IPLVLinkMicEventSender;
import com.plv.livescenes.streamer.linkmic.PLVLinkMicEventSender;
import com.plv.livescenes.streamer.transfer.PLVStreamerInnerDataTransfer;
import com.plv.socket.event.PLVEventConstant;
import com.plv.socket.event.PLVEventHelper;
import com.plv.socket.event.ppt.PLVFinishClassEvent;
import com.plv.socket.event.ppt.PLVOnSliceStartEvent;
import com.plv.socket.event.sclass.PLVInLiveAckResult;
import com.plv.socket.event.sclass.PLVInLiveEvent;
import com.plv.socket.impl.PLVSocketMessageObserver;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class PLVHiClassManager implements IPLVHiClassManager {
    private long activeStartLessonTimestamp;
    private Disposable changeLessonStatusDisposable;
    private Disposable checkLessonLateTimeDisposable;
    private Disposable getLessonFinishDisposable;
    private Disposable getWatchNextLessonDisposable;
    private Observer<PLVHiClassDataBean> hiClassDataBeanObserver;
    private LiveData<PLVHiClassDataBean> hiClassDataLiveData;
    private boolean isAutoConnectMicroEnabled;
    private boolean isTeacherType;
    private long lessonEndTime;
    private int limitLinkNumber;
    private IPLVHiClassManager.OnHiClassListener onHiClassListener;
    private PLVSocketMessageObserver.OnMessageListener onMessageListener;
    private Disposable queryLessonStatusDisposable;
    private Disposable verifyTeacherLoginDisposable;
    private int lessonStatus = 0;
    private boolean isFirstStart = true;

    public PLVHiClassManager(@NonNull LiveData<PLVHiClassDataBean> liveData, String str) {
        this.hiClassDataLiveData = liveData;
        this.isTeacherType = "teacher".equals(str);
        observeLiveRoomData();
        observeSocketEvent();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void callOnLessonEnd(long j2, boolean z2, PLVHCStudentLessonListVO.DataVO dataVO) {
        IPLVHiClassManager.OnHiClassListener onHiClassListener = this.onHiClassListener;
        if (onHiClassListener != null) {
            onHiClassListener.onLessonEnd(j2, z2, dataVO);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void changeLessonStatusInner(final IPLVDataRequestListener<String> iPLVDataRequestListener, final int i2) {
        dispose(this.changeLessonStatusDisposable);
        if (PolyvSocketWrapper.getInstance().isOnlineStatus()) {
            this.changeLessonStatusDisposable = PLVHCApiManager.getInstance().changeLessonStatus(i2).subscribe(new Consumer<PLVHCChangeLessonStatusVO>() { // from class: com.plv.livescenes.hiclass.PLVHiClassManager.8
                @Override // io.reactivex.functions.Consumer
                public void accept(PLVHCChangeLessonStatusVO pLVHCChangeLessonStatusVO) throws Exception {
                    if (pLVHCChangeLessonStatusVO.isSuccess() == null || !pLVHCChangeLessonStatusVO.isSuccess().booleanValue()) {
                        throw new Exception(pLVHCChangeLessonStatusVO.getError().getDesc() + "-" + pLVHCChangeLessonStatusVO.getError().getCode());
                    }
                    int i3 = i2;
                    if (1 == i3) {
                        PLVHiClassManager.this.activeStartLessonTimestamp = System.currentTimeMillis();
                        PLVLinkMicEventSender.getInstance().emitStartClassEvent(PLVLinkMicConfig.getInstance().getSessionId(), PLVHiClassManager.this.lessonEndTime - PLVHiClassManager.this.activeStartLessonTimestamp);
                        PLVHiClassManager.this.onLessonStarted();
                    } else if (2 == i3) {
                        PLVLinkMicEventSender.getInstance().emitFinishClassEvent(true);
                        PLVHiClassManager.this.onLessonEnd(false);
                    }
                    IPLVDataRequestListener iPLVDataRequestListener2 = iPLVDataRequestListener;
                    if (iPLVDataRequestListener2 != null) {
                        iPLVDataRequestListener2.onSuccess("");
                    }
                }
            }, new Consumer<Throwable>() { // from class: com.plv.livescenes.hiclass.PLVHiClassManager.9
                @Override // io.reactivex.functions.Consumer
                public void accept(Throwable th) throws Exception {
                    IPLVDataRequestListener iPLVDataRequestListener2 = iPLVDataRequestListener;
                    if (iPLVDataRequestListener2 != null) {
                        iPLVDataRequestListener2.onFailed(th.getMessage(), th);
                    }
                }
            });
            return;
        }
        Throwable th = new Throwable("当前网络不可用，请检查网络设置");
        if (iPLVDataRequestListener != null) {
            iPLVDataRequestListener.onFailed(th.getMessage(), th);
        }
    }

    private void checkLessonLateTime() {
        if (this.isTeacherType) {
            dispose(this.checkLessonLateTimeDisposable);
            this.checkLessonLateTimeDisposable = Observable.just(1).delay((this.lessonEndTime - System.currentTimeMillis()) + 13800000, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).flatMap(new Function<Integer, ObservableSource<Integer>>() { // from class: com.plv.livescenes.hiclass.PLVHiClassManager.3
                @Override // io.reactivex.functions.Function
                public ObservableSource<Integer> apply(Integer num) throws Exception {
                    if (PLVHiClassManager.this.onHiClassListener != null) {
                        PLVHiClassManager.this.onHiClassListener.onLessonLateTooLong(600000L);
                    }
                    return Observable.just(1).delay(600000L, TimeUnit.MILLISECONDS);
                }
            }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Integer>() { // from class: com.plv.livescenes.hiclass.PLVHiClassManager.1
                @Override // io.reactivex.functions.Consumer
                public void accept(Integer num) throws Exception {
                    PLVHiClassManager.this.changeLessonStatusInner(null, 2);
                }
            }, new Consumer<Throwable>() { // from class: com.plv.livescenes.hiclass.PLVHiClassManager.2
                @Override // io.reactivex.functions.Consumer
                public void accept(Throwable th) throws Exception {
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dispose(Disposable disposable) {
        if (disposable != null) {
            disposable.dispose();
        }
    }

    private void getLessonFinishInfo(final boolean z2) {
        dispose(this.getLessonFinishDisposable);
        dispose(this.getWatchNextLessonDisposable);
        this.getLessonFinishDisposable = PLVHCApiManager.getInstance().getLessonFinishInfo().retryWhen(new PLVRxBaseRetryFunction(3, C.DEFAULT_MAX_SEEK_TO_PREVIOUS_POSITION_MS)).compose(new PLVRxBaseTransformer()).subscribe(new Consumer<PLVHCLessonFinishVO>() { // from class: com.plv.livescenes.hiclass.PLVHiClassManager.4
            @Override // io.reactivex.functions.Consumer
            public void accept(PLVHCLessonFinishVO pLVHCLessonFinishVO) throws Exception {
                if (pLVHCLessonFinishVO.isSuccess() == null || !pLVHCLessonFinishVO.isSuccess().booleanValue()) {
                    throw new Exception(pLVHCLessonFinishVO.getError().getDesc() + "-" + pLVHCLessonFinishVO.getError().getCode());
                }
                long endTime = pLVHCLessonFinishVO.getData().getEndTime() - pLVHCLessonFinishVO.getData().getStartTime();
                if (PLVHiClassManager.this.isTeacherType) {
                    PLVHiClassManager.this.callOnLessonEnd(endTime, z2, null);
                } else {
                    PLVHiClassManager.this.getWatchNextLesson(endTime, z2);
                }
            }
        }, new Consumer<Throwable>() { // from class: com.plv.livescenes.hiclass.PLVHiClassManager.5
            @Override // io.reactivex.functions.Consumer
            public void accept(Throwable th) throws Exception {
                PLVCommonLog.exception(th);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getWatchNextLesson(final long j2, final boolean z2) {
        dispose(this.getWatchNextLessonDisposable);
        this.getWatchNextLessonDisposable = PLVHCApiManager.getInstance().getWatchNextLesson().subscribe(new Consumer<PLVHCStudentLessonListVO.DataVO>() { // from class: com.plv.livescenes.hiclass.PLVHiClassManager.6
            @Override // io.reactivex.functions.Consumer
            public void accept(PLVHCStudentLessonListVO.DataVO dataVO) throws Exception {
                PLVHiClassManager.this.callOnLessonEnd(j2, z2, dataVO);
            }
        }, new Consumer<Throwable>() { // from class: com.plv.livescenes.hiclass.PLVHiClassManager.7
            @Override // io.reactivex.functions.Consumer
            public void accept(Throwable th) throws Exception {
                PLVHiClassManager.this.callOnLessonEnd(j2, z2, null);
                PLVCommonLog.exception(th);
            }
        });
    }

    private void observeLiveRoomData() {
        Observer<PLVHiClassDataBean> observer = new Observer<PLVHiClassDataBean>() { // from class: com.plv.livescenes.hiclass.PLVHiClassManager.16
            @Override // androidx.lifecycle.Observer
            public void onChanged(@Nullable PLVHiClassDataBean pLVHiClassDataBean) {
                if (pLVHiClassDataBean == null) {
                    return;
                }
                PLVHiClassManager.this.updateMaxResolution(pLVHiClassDataBean);
                boolean z2 = false;
                PLVHiClassManager.this.limitLinkNumber = pLVHiClassDataBean.getLinkNumber() == null ? 0 : pLVHiClassDataBean.getLinkNumber().intValue();
                if (PLVHiClassManager.this.onHiClassListener != null) {
                    PLVHiClassManager.this.onHiClassListener.onLimitLinkNumber(PLVHiClassManager.this.limitLinkNumber);
                }
                PLVHiClassManager pLVHiClassManager = PLVHiClassManager.this;
                if (pLVHiClassManager.limitLinkNumber > 0 && pLVHiClassDataBean.isAutoConnectMicroEnabled()) {
                    z2 = true;
                }
                pLVHiClassManager.isAutoConnectMicroEnabled = z2;
                PLVHiClassManager.this.lessonEndTime = pLVHiClassDataBean.getLessonEndTime() == null ? 0L : pLVHiClassDataBean.getLessonEndTime().longValue();
                int iIntValue = pLVHiClassDataBean.getStatus().intValue();
                if (1 == iIntValue) {
                    PLVHiClassManager.this.onLessonStarted();
                } else if (iIntValue == 0) {
                    PLVHiClassManager.this.onLessonPreparing(pLVHiClassDataBean.getServerTime().longValue(), pLVHiClassDataBean.getLessonStartTime().longValue());
                } else if (2 == iIntValue) {
                    PLVHiClassManager.this.onLessonEnd(true);
                }
            }
        };
        this.hiClassDataBeanObserver = observer;
        this.hiClassDataLiveData.observeForever(observer);
    }

    private void observeSocketEvent() {
        this.onMessageListener = new PLVSocketMessageObserver.OnMessageListener() { // from class: com.plv.livescenes.hiclass.PLVHiClassManager.15
            @Override // com.plv.socket.impl.PLVSocketMessageObserver.OnMessageListener
            public void onMessage(String str, String str2, String str3) {
                str2.hashCode();
                if (str2.equals("onSliceStart")) {
                    if (((PLVOnSliceStartEvent) PLVEventHelper.toEventModel(str, str2, str3, PLVOnSliceStartEvent.class)) != null) {
                        PLVHiClassManager.this.onLessonStarted();
                    }
                } else if (str2.equals(PLVEventConstant.Class.FINISH_CLASS) && ((PLVFinishClassEvent) PLVEventHelper.toEventModel(str, str2, str3, PLVFinishClassEvent.class)) != null) {
                    PLVHiClassManager.this.onLessonEnd(false);
                }
            }
        };
        PolyvSocketWrapper.getInstance().getSocketObserver().addOnMessageListener(this.onMessageListener, PLVEventConstant.LinkMic.JOIN_REQUEST_EVENT, PLVEventConstant.LinkMic.JOIN_RESPONSE_EVENT, "joinSuccess", PLVEventConstant.LinkMic.JOIN_LEAVE_EVENT, PLVEventConstant.LinkMic.JOIN_ANSWER_EVENT, PLVEventConstant.Class.SE_SWITCH_MESSAGE, "message");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onLessonEnd(boolean z2) {
        if (this.lessonStatus == 3) {
            return;
        }
        this.lessonStatus = 3;
        getLessonFinishInfo(z2);
        verifyTeacherLogin();
        dispose(this.checkLessonLateTimeDisposable);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onLessonPreparing(long j2, long j3) {
        if (this.lessonStatus == 1) {
            return;
        }
        this.lessonStatus = 1;
        IPLVHiClassManager.OnHiClassListener onHiClassListener = this.onHiClassListener;
        if (onHiClassListener != null) {
            onHiClassListener.onLessonPreparing(j2, j3);
        }
        verifyTeacherLogin();
        dispose(this.checkLessonLateTimeDisposable);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onLessonStarted() {
        if (this.lessonStatus == 2) {
            return;
        }
        this.lessonStatus = 2;
        dispose(this.getLessonFinishDisposable);
        dispose(this.getWatchNextLessonDisposable);
        dispose(this.verifyTeacherLoginDisposable);
        if (this.isFirstStart) {
            queryLessonStatus(a.f7153q);
        }
        IPLVHiClassManager.OnHiClassListener onHiClassListener = this.onHiClassListener;
        if (onHiClassListener != null) {
            onHiClassListener.onLessonStarted(this.isFirstStart);
            this.isFirstStart = false;
        }
        checkLessonLateTime();
    }

    private void queryLessonStatus(long j2) {
        dispose(this.queryLessonStatusDisposable);
        this.queryLessonStatusDisposable = Observable.interval(j2, a.f7153q, TimeUnit.MILLISECONDS).subscribe(new Consumer<Long>() { // from class: com.plv.livescenes.hiclass.PLVHiClassManager.13
            @Override // io.reactivex.functions.Consumer
            public void accept(Long l2) throws Exception {
                PLVInLiveEvent pLVInLiveEvent = new PLVInLiveEvent();
                pLVInLiveEvent.setSessionId(PLVLinkMicConfig.getInstance().getSessionId());
                PolyvSocketWrapper.getInstance().emit(PLVEventConstant.Class.IN_LIVE_EVENT, pLVInLiveEvent, new IPLVLinkMicEventSender.PLVSMainCallAck() { // from class: com.plv.livescenes.hiclass.PLVHiClassManager.13.1
                    @Override // com.plv.livescenes.streamer.linkmic.IPLVLinkMicEventSender.PLVSMainCallAck
                    public void onCall(Object... objArr) {
                        Object obj;
                        PLVInLiveAckResult pLVInLiveAckResult;
                        if (objArr == null || objArr.length == 0 || (obj = objArr[0]) == null || (pLVInLiveAckResult = (PLVInLiveAckResult) PLVGsonUtil.fromJson(PLVInLiveAckResult.class, obj.toString())) == null || !pLVInLiveAckResult.isSuccess()) {
                            return;
                        }
                        if (pLVInLiveAckResult.getData().isLive()) {
                            PLVHiClassManager.this.onLessonStarted();
                        } else {
                            PLVHiClassManager.this.onLessonEnd(false);
                        }
                    }
                });
            }
        }, new Consumer<Throwable>() { // from class: com.plv.livescenes.hiclass.PLVHiClassManager.14
            @Override // io.reactivex.functions.Consumer
            public void accept(Throwable th) throws Exception {
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateMaxResolution(PLVHiClassDataBean pLVHiClassDataBean) {
        if (pLVHiClassDataBean == null || pLVHiClassDataBean.getRtcMaxResolution() == null) {
            return;
        }
        PLVStreamerInnerDataTransfer.getInstance().setSupportedMaxBitrate(pLVHiClassDataBean.getRtcMaxResolution().intValue());
    }

    private void verifyTeacherLogin() {
        if (this.isTeacherType) {
            dispose(this.verifyTeacherLoginDisposable);
            this.verifyTeacherLoginDisposable = Observable.interval(0L, 10L, TimeUnit.SECONDS).flatMap(new Function<Long, ObservableSource<PLVHCTeacherLoginVerifyVO>>() { // from class: com.plv.livescenes.hiclass.PLVHiClassManager.12
                @Override // io.reactivex.functions.Function
                public ObservableSource<PLVHCTeacherLoginVerifyVO> apply(Long l2) throws Exception {
                    return PLVHCApiManager.getInstance().verifyTeacherLogin();
                }
            }).subscribe(new Consumer<PLVHCTeacherLoginVerifyVO>() { // from class: com.plv.livescenes.hiclass.PLVHiClassManager.10
                @Override // io.reactivex.functions.Consumer
                public void accept(PLVHCTeacherLoginVerifyVO pLVHCTeacherLoginVerifyVO) throws Exception {
                    if (pLVHCTeacherLoginVerifyVO.getError() == null || !pLVHCTeacherLoginVerifyVO.getError().isRepeatLogin()) {
                        return;
                    }
                    if (PLVHiClassManager.this.onHiClassListener != null) {
                        PLVHiClassManager.this.onHiClassListener.onRepeatLogin(pLVHCTeacherLoginVerifyVO.getError().getDesc());
                    }
                    PLVHiClassManager pLVHiClassManager = PLVHiClassManager.this;
                    pLVHiClassManager.dispose(pLVHiClassManager.verifyTeacherLoginDisposable);
                }
            }, new Consumer<Throwable>() { // from class: com.plv.livescenes.hiclass.PLVHiClassManager.11
                @Override // io.reactivex.functions.Consumer
                public void accept(Throwable th) throws Exception {
                    PLVCommonLog.exception(th);
                }
            });
        }
    }

    @Override // com.plv.livescenes.hiclass.IPLVHiClassManager
    public void changeLessonStatus(IPLVDataRequestListener<String> iPLVDataRequestListener, int i2) {
        changeLessonStatusInner(iPLVDataRequestListener, i2);
    }

    @Override // com.plv.livescenes.hiclass.IPLVHiClassManager
    public void destroy() {
        this.activeStartLessonTimestamp = 0L;
        this.limitLinkNumber = 0;
        this.isAutoConnectMicroEnabled = false;
        this.lessonStatus = 0;
        this.isFirstStart = true;
        dispose(this.getLessonFinishDisposable);
        dispose(this.getWatchNextLessonDisposable);
        dispose(this.changeLessonStatusDisposable);
        dispose(this.verifyTeacherLoginDisposable);
        dispose(this.queryLessonStatusDisposable);
        dispose(this.checkLessonLateTimeDisposable);
        this.hiClassDataLiveData.removeObserver(this.hiClassDataBeanObserver);
        PolyvSocketWrapper.getInstance().getSocketObserver().removeOnMessageListener(this.onMessageListener);
    }

    @Override // com.plv.livescenes.hiclass.IPLVHiClassManager
    public int getLessonStatus() {
        return this.lessonStatus;
    }

    @Override // com.plv.livescenes.hiclass.IPLVHiClassManager
    public int getLimitLinkNumber() {
        return this.limitLinkNumber;
    }

    @Override // com.plv.livescenes.hiclass.IPLVHiClassManager
    public boolean isAutoConnectEnabledWithTimeRange() {
        return System.currentTimeMillis() - this.activeStartLessonTimestamp <= 30000 && this.isAutoConnectMicroEnabled;
    }

    @Override // com.plv.livescenes.hiclass.IPLVHiClassManager
    public void setOnHiClassListener(IPLVHiClassManager.OnHiClassListener onHiClassListener) {
        this.onHiClassListener = onHiClassListener;
    }
}
