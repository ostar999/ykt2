package com.easefun.polyv.livecommon.module.modules.chatroom.presenter;

import android.text.TextUtils;
import android.util.Pair;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.easefun.polyv.livecommon.R;
import com.easefun.polyv.livecommon.module.modules.chatroom.contract.IPLVChatroomContract;
import com.easefun.polyv.livecommon.module.modules.chatroom.model.repo.PLVManagerChatRepo;
import com.easefun.polyv.livecommon.module.modules.chatroom.model.vo.PLVManagerChatHistoryLoadStatus;
import com.easefun.polyv.livecommon.module.modules.chatroom.presenter.usecase.PLVCalculateUnreadMessageCountUseCase;
import com.easefun.polyv.livecommon.module.modules.chatroom.presenter.usecase.PLVCombineSameUserChatUseCase;
import com.easefun.polyv.livecommon.module.modules.chatroom.presenter.usecase.PLVMergeChatEventUseCase;
import com.easefun.polyv.livecommon.module.modules.chatroom.presenter.usecase.PLVWrapChatEventUseCase;
import com.easefun.polyv.livecommon.module.modules.chatroom.presenter.vo.PLVChatEventWrapVO;
import com.easefun.polyv.livecommon.module.modules.chatroom.presenter.vo.PLVManagerChatUiState;
import com.easefun.polyv.livecommon.module.modules.chatroom.presenter.vo.PLVManagerChatVO;
import com.easefun.polyv.livecommon.ui.widget.itemview.PLVBaseViewData;
import com.easefun.polyv.livescenes.chatroom.PolyvLocalMessage;
import com.easefun.polyv.livescenes.chatroom.send.img.PolyvSendLocalImgEvent;
import com.plv.foundationsdk.component.livedata.Event;
import com.plv.foundationsdk.log.PLVCommonLog;
import com.plv.foundationsdk.utils.PLVAppUtils;
import com.plv.foundationsdk.utils.PLVSugarUtil;
import com.plv.livescenes.chatroom.PLVLocalMessage;
import com.plv.socket.event.PLVBaseEvent;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class PLVManagerChatViewModel extends ViewModel {
    private long lastReadTime;
    private final PLVManagerChatRepo chatRepo = new PLVManagerChatRepo();
    private final PLVWrapChatEventUseCase wrapChatEventUseCase = new PLVWrapChatEventUseCase();
    private final PLVMergeChatEventUseCase mergeChatEventUseCase = new PLVMergeChatEventUseCase();
    private final PLVCombineSameUserChatUseCase combineSameUserChatUseCase = new PLVCombineSameUserChatUseCase();
    private final PLVCalculateUnreadMessageCountUseCase calculateUnreadMessageCountUseCase = new PLVCalculateUnreadMessageCountUseCase();
    private final MutableLiveData<PLVManagerChatVO> managerChatLiveData = new MutableLiveData<>();
    private final MutableLiveData<PLVManagerChatUiState> uiStateLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<Event<String>>> notifyMsgLiveData = new MutableLiveData<>();
    private final List<PLVChatEventWrapVO> originChatEvents = new ArrayList();
    private PLVManagerChatUiState lastUiState = new PLVManagerChatUiState();
    private final CompositeDisposable disposes = new CompositeDisposable();

    public PLVManagerChatViewModel() {
        initUiState();
        observeManagerChatData();
        observeHistoryLoadStatus();
    }

    private void addNotifyMsg(String notifyMsg) {
        List<Event<String>> value = this.notifyMsgLiveData.getValue();
        if (value == null) {
            value = new ArrayList<>();
        }
        value.add(new Event<>(notifyMsg));
        this.notifyMsgLiveData.postValue(value);
    }

    private void initUiState() {
        this.managerChatLiveData.postValue(new PLVManagerChatVO());
        this.uiStateLiveData.postValue(this.lastUiState);
    }

    private void observeHistoryLoadStatus() {
        this.disposes.add(this.chatRepo.getHistoryLoadStatusObservable().retry().observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<PLVManagerChatHistoryLoadStatus>() { // from class: com.easefun.polyv.livecommon.module.modules.chatroom.presenter.PLVManagerChatViewModel.7
            @Override // io.reactivex.functions.Consumer
            public void accept(PLVManagerChatHistoryLoadStatus loadStatus) {
                PLVManagerChatViewModel pLVManagerChatViewModel = PLVManagerChatViewModel.this;
                pLVManagerChatViewModel.postUiStateUpdate(pLVManagerChatViewModel.lastUiState.copy().setCanLoadMoreHistoryMessage(loadStatus.isCanLoadMore()).setHistoryMessageLoading(loadStatus.isLoading()));
            }
        }, new Consumer<Throwable>() { // from class: com.easefun.polyv.livecommon.module.modules.chatroom.presenter.PLVManagerChatViewModel.8
            @Override // io.reactivex.functions.Consumer
            public void accept(Throwable throwable) {
                PLVCommonLog.exception(throwable);
            }
        }));
    }

    private void observeManagerChatData() {
        this.disposes.add(this.chatRepo.getManagerChatObservable().observeOn(Schedulers.computation()).map(new Function<PLVBaseViewData<PLVBaseEvent>, PLVChatEventWrapVO>() { // from class: com.easefun.polyv.livecommon.module.modules.chatroom.presenter.PLVManagerChatViewModel.6
            @Override // io.reactivex.functions.Function
            public PLVChatEventWrapVO apply(@NonNull PLVBaseViewData<PLVBaseEvent> plvBaseEventPLVBaseViewData) {
                return PLVManagerChatViewModel.this.wrapChatEventUseCase.wrap(plvBaseEventPLVBaseViewData.getData());
            }
        }).filter(new Predicate<PLVChatEventWrapVO>() { // from class: com.easefun.polyv.livecommon.module.modules.chatroom.presenter.PLVManagerChatViewModel.5
            @Override // io.reactivex.functions.Predicate
            public boolean test(@NonNull PLVChatEventWrapVO plvChatEventWrapVO) {
                return plvChatEventWrapVO.isValid();
            }
        }).doOnNext(new Consumer<PLVChatEventWrapVO>() { // from class: com.easefun.polyv.livecommon.module.modules.chatroom.presenter.PLVManagerChatViewModel.4
            @Override // io.reactivex.functions.Consumer
            public void accept(PLVChatEventWrapVO plvChatEventWrapVO) {
                PLVManagerChatViewModel.this.mergeChatEventUseCase.merge(PLVManagerChatViewModel.this.originChatEvents, plvChatEventWrapVO);
            }
        }).map(new Function<PLVChatEventWrapVO, List<PLVChatEventWrapVO>>() { // from class: com.easefun.polyv.livecommon.module.modules.chatroom.presenter.PLVManagerChatViewModel.3
            @Override // io.reactivex.functions.Function
            public List<PLVChatEventWrapVO> apply(@NonNull PLVChatEventWrapVO plvChatEventWrapVO) {
                return PLVManagerChatViewModel.this.combineSameUserChatUseCase.combine(PLVManagerChatViewModel.this.originChatEvents);
            }
        }).retry().observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<List<PLVChatEventWrapVO>>() { // from class: com.easefun.polyv.livecommon.module.modules.chatroom.presenter.PLVManagerChatViewModel.1
            /* JADX WARN: Multi-variable type inference failed */
            @Override // io.reactivex.functions.Consumer
            public void accept(List<PLVChatEventWrapVO> chatEventWrapVOList) {
                PLVManagerChatVO pLVManagerChatVO = (PLVManagerChatVO) PLVManagerChatViewModel.this.managerChatLiveData.getValue();
                if (pLVManagerChatVO == null) {
                    pLVManagerChatVO = new PLVManagerChatVO();
                }
                pLVManagerChatVO.setChatEventWrapVOList(chatEventWrapVOList);
                PLVManagerChatViewModel.this.managerChatLiveData.postValue(pLVManagerChatVO);
                PLVManagerChatViewModel.this.updateUnreadMessageCount();
            }
        }, new Consumer<Throwable>() { // from class: com.easefun.polyv.livecommon.module.modules.chatroom.presenter.PLVManagerChatViewModel.2
            @Override // io.reactivex.functions.Consumer
            public void accept(Throwable throwable) {
                PLVCommonLog.exception(throwable);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void postUiStateUpdate(PLVManagerChatUiState uiState) {
        this.lastUiState = uiState;
        this.uiStateLiveData.postValue(uiState);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateUnreadMessageCount() {
        postUiStateUpdate(this.lastUiState.copy().setUnreadMessageCount(this.calculateUnreadMessageCountUseCase.calculate(this.originChatEvents, this.lastReadTime)));
    }

    public LiveData<PLVManagerChatVO> getManagerChatLiveData() {
        return this.managerChatLiveData;
    }

    public LiveData<List<Event<String>>> getNotifyMsgLiveData() {
        return this.notifyMsgLiveData;
    }

    public LiveData<PLVManagerChatUiState> getUiStateLiveData() {
        return this.uiStateLiveData;
    }

    public void init(IPLVChatroomContract.IChatroomPresenter chatroomPresenter) {
        this.chatRepo.init(chatroomPresenter);
    }

    @Override // androidx.lifecycle.ViewModel
    public void onCleared() {
        this.disposes.dispose();
    }

    public void removeNotifyMsg(Event<String> notifyMsg) {
        List<Event<String>> value = this.notifyMsgLiveData.getValue();
        if (value == null) {
            this.notifyMsgLiveData.postValue(new ArrayList());
        } else {
            value.remove(notifyMsg);
            this.notifyMsgLiveData.postValue(value);
        }
    }

    public void requestChatHistory(final String roomId, final int start, final int end) {
        this.chatRepo.requestChatHistory(roomId, start, end);
    }

    public void sendImageMessage(PolyvSendLocalImgEvent message) {
        message.setIsManagerChatMsg(true);
        if (message.getId() == null) {
            message.setId(String.valueOf(System.currentTimeMillis()));
        }
        this.chatRepo.sendImageMessage(message);
    }

    public boolean sendTextMessage(String message) {
        if (TextUtils.isEmpty(message) && PLVAppUtils.getApp() != null) {
            addNotifyMsg(PLVAppUtils.getApp().getString(R.string.plv_chat_toast_send_text_empty));
            return false;
        }
        PolyvLocalMessage polyvLocalMessage = new PolyvLocalMessage(message);
        polyvLocalMessage.setIsManagerChatMsg(true);
        if (polyvLocalMessage.getId() == null) {
            polyvLocalMessage.setId(String.valueOf(System.currentTimeMillis()));
        }
        Pair<Boolean, Integer> pairSendTextMessage = this.chatRepo.sendTextMessage(polyvLocalMessage);
        boolean zBooleanValue = ((Boolean) pairSendTextMessage.first).booleanValue();
        if (zBooleanValue || PLVAppUtils.getApp() == null) {
            return zBooleanValue;
        }
        addNotifyMsg(PLVSugarUtil.format(PLVAppUtils.getApp().getString(R.string.plv_chat_toast_send_msg_failed_param), PLVLocalMessage.sendValueToDescribe(((Integer) pairSendTextMessage.second).intValue())));
        return false;
    }

    public void setMessageAlreadyRead(PLVChatEventWrapVO chatEventWrapVO) {
        if (chatEventWrapVO != null && this.lastReadTime <= chatEventWrapVO.getLastEventTime().longValue()) {
            this.lastReadTime = chatEventWrapVO.getLastEventTime().longValue();
            updateUnreadMessageCount();
        }
    }
}
