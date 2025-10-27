package com.plv.livescenes.streamer.transfer;

import androidx.annotation.NonNull;
import com.google.android.exoplayer2.C;
import com.plv.foundationsdk.rx.PLVRxTimer;
import com.plv.livescenes.socket.PLVSocketWrapper;
import com.plv.livescenes.streamer.IPLVStreamerManager;
import com.plv.livescenes.streamer.listener.PLVStreamerEventListener;
import com.plv.socket.event.PLVEventHelper;
import com.plv.socket.event.ppt.PLVOnSliceIDEvent;
import com.plv.socket.impl.PLVSocketMessageObserver;
import com.plv.socket.socketio.PLVSocketIOObservable;
import com.plv.thirdpart.blankj.utilcode.util.LogUtils;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.LinkedList;
import java.util.Queue;
import javax.xml.transform.TransformerException;
import org.json.JSONException;

/* loaded from: classes5.dex */
class PLVStreamerClassOffJudge {
    private PLVSocketMessageObserver.OnMessageListener messageEventListener;
    private OnJudgeAsClassOffListener onJudgeAsClassOffListener;
    private PLVSocketIOObservable.OnSocketEventListener socketReconnectEventListener;
    private Disposable timerToReloadQueue;
    private PLVSocketMessageObserver socketMsgObservable = PLVSocketWrapper.getInstance().getSocketObserver();
    private Queue<String> socketReconnectEventQueue = new LinkedList();
    private boolean rejoinChannelSuccess = false;
    private boolean onSliceIdNotInClass = false;

    public interface OnJudgeAsClassOffListener {
        void onJudgeAsClassOff();
    }

    public PLVStreamerClassOffJudge(IPLVStreamerManager iPLVStreamerManager, @NonNull OnJudgeAsClassOffListener onJudgeAsClassOffListener) {
        this.onJudgeAsClassOffListener = onJudgeAsClassOffListener;
        iPLVStreamerManager.addEventHandler(new PLVStreamerEventListener() { // from class: com.plv.livescenes.streamer.transfer.PLVStreamerClassOffJudge.1
            @Override // com.plv.linkmic.PLVLinkMicEventHandler
            public void onRejoinChannelSuccess(String str, String str2) throws JSONException, TransformerException, IllegalArgumentException {
                LogUtils.d("断网重连后：onRejoinChannelSuccess");
                PLVStreamerClassOffJudge.this.rejoinChannelSuccess = true;
                PLVStreamerClassOffJudge.this.tryEmitFinishClass();
            }
        });
        reloadReconnectEvenQueue();
        this.messageEventListener = new PLVSocketMessageObserver.OnMessageListener() { // from class: com.plv.livescenes.streamer.transfer.PLVStreamerClassOffJudge.2
            @Override // com.plv.socket.impl.PLVSocketMessageObserver.OnMessageListener
            public void onMessage(String str, String str2, String str3) throws JSONException, TransformerException, IllegalArgumentException {
                if (str2.equals("onSliceID")) {
                    PLVStreamerClassOffJudge pLVStreamerClassOffJudge = PLVStreamerClassOffJudge.this;
                    pLVStreamerClassOffJudge.dispose(pLVStreamerClassOffJudge.timerToReloadQueue);
                    PLVOnSliceIDEvent pLVOnSliceIDEvent = (PLVOnSliceIDEvent) PLVEventHelper.toEventModel(str, str2, str3, PLVOnSliceIDEvent.class);
                    if (str2.equals((String) PLVStreamerClassOffJudge.this.socketReconnectEventQueue.peek())) {
                        LogUtils.d("断网重连后收到了：" + str2);
                        PLVStreamerClassOffJudge.this.socketReconnectEventQueue.poll();
                        if (pLVOnSliceIDEvent == null || pLVOnSliceIDEvent.isInClass()) {
                            return;
                        }
                        PLVStreamerClassOffJudge.this.onSliceIdNotInClass = true;
                        PLVStreamerClassOffJudge.this.reloadReconnectEvenQueue();
                        PLVStreamerClassOffJudge.this.tryEmitFinishClass();
                    }
                }
            }
        };
        this.socketReconnectEventListener = new PLVSocketIOObservable.OnSocketEventListener() { // from class: com.plv.livescenes.streamer.transfer.PLVStreamerClassOffJudge.3
            @Override // com.plv.socket.socketio.PLVSocketIOObservable.OnSocketEventListener
            public void onMessage(String str, Object... objArr) {
                if (str.equals(PLVStreamerClassOffJudge.this.socketReconnectEventQueue.peek())) {
                    PLVStreamerClassOffJudge.this.socketReconnectEventQueue.poll();
                    PLVStreamerClassOffJudge pLVStreamerClassOffJudge = PLVStreamerClassOffJudge.this;
                    pLVStreamerClassOffJudge.dispose(pLVStreamerClassOffJudge.timerToReloadQueue);
                    PLVStreamerClassOffJudge.this.timerToReloadQueue = PLVRxTimer.delay(C.DEFAULT_MAX_SEEK_TO_PREVIOUS_POSITION_MS, new Consumer<Object>() { // from class: com.plv.livescenes.streamer.transfer.PLVStreamerClassOffJudge.3.1
                        @Override // io.reactivex.functions.Consumer
                        public void accept(Object obj) throws Exception {
                            PLVStreamerClassOffJudge.this.reloadReconnectEvenQueue();
                        }
                    });
                }
            }
        };
        this.socketMsgObservable.addOnMessageListener(this.messageEventListener, "message");
        this.socketMsgObservable.addOnSocketEventListener(this.socketReconnectEventListener, "reconnect");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dispose(Disposable disposable) {
        if (disposable != null) {
            disposable.dispose();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void reloadReconnectEvenQueue() {
        this.socketReconnectEventQueue.clear();
        this.socketReconnectEventQueue.add("reconnect");
        this.socketReconnectEventQueue.add("onSliceID");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void tryEmitFinishClass() {
        if (this.onSliceIdNotInClass && this.rejoinChannelSuccess) {
            this.onJudgeAsClassOffListener.onJudgeAsClassOff();
            this.onSliceIdNotInClass = false;
            this.rejoinChannelSuccess = false;
        }
    }

    public void destroy() {
        this.socketMsgObservable.removeOnMessageListener(this.messageEventListener);
        this.socketMsgObservable.removeOnSocketEventListener(this.socketReconnectEventListener);
    }
}
