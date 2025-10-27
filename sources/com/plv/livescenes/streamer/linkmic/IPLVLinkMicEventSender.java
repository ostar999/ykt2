package com.plv.livescenes.streamer.linkmic;

import android.os.Handler;
import android.os.Looper;
import com.plv.socket.user.PLVSocketUserBean;
import io.socket.client.Ack;

/* loaded from: classes5.dex */
public interface IPLVLinkMicEventSender {

    public interface IPLVGuestAutoLinkMicListener {
        void onAutoLinkMic();

        void onHangupByTeacher();

        void onInviteByTeacher();

        void onTimeout();
    }

    public interface IPLVGuestClassDuration {
        void onClassDuration(long j2, long j3);
    }

    public static abstract class PLVSMainCallAck implements Ack {
        protected Handler handler = new Handler(Looper.getMainLooper());

        @Override // io.socket.client.Ack
        public void call(final Object... objArr) {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                onCall(objArr);
            } else {
                this.handler.post(new Runnable() { // from class: com.plv.livescenes.streamer.linkmic.IPLVLinkMicEventSender.PLVSMainCallAck.1
                    @Override // java.lang.Runnable
                    public void run() {
                        PLVSMainCallAck.this.onCall(objArr);
                    }
                });
            }
        }

        public abstract void onCall(Object... objArr);
    }

    void closeAllUserLinkMic(String str, Ack ack);

    void closeLinkMic();

    void closeUserLinkMic(String str, Ack ack);

    void emitFinishClassEvent(String str);

    void emitFinishClassEvent(boolean z2);

    void emitStartClassEvent(String str, long j2);

    void groupCancelHelp(Ack ack);

    void groupRequestHelp(Ack ack);

    void guestAutoLinkMic(int i2, IPLVGuestAutoLinkMicListener iPLVGuestAutoLinkMicListener);

    boolean isVideoLinkMicType();

    void listenerClassDuration(IPLVGuestClassDuration iPLVGuestClassDuration);

    void muteUserMedia(PLVSocketUserBean pLVSocketUserBean, String str, boolean z2, boolean z3, Ack ack);

    boolean openLinkMic(boolean z2, boolean z3, Ack ack);

    void release();

    void responseUserLinkMic(PLVSocketUserBean pLVSocketUserBean, Ack ack);

    void responseUserLinkMic(PLVSocketUserBean pLVSocketUserBean, boolean z2, Ack ack);

    void sendCupEvent(PLVSocketUserBean pLVSocketUserBean, String str, Ack ack);

    void sendJoinAnswerEvent();

    void sendRaiseHandEvent(int i2, String str, Ack ack);

    void sendScreenShareEvent(PLVSocketUserBean pLVSocketUserBean, String str, boolean z2, Ack ack);

    void setDocumentStreamerViewPosition(boolean z2, String str);

    void setMediaPermission(PLVSocketUserBean pLVSocketUserBean, String str, boolean z2, boolean z3, Ack ack);

    void setPaintPermission(PLVSocketUserBean pLVSocketUserBean, String str, boolean z2, Ack ack);

    void setSpeakerPermission(PLVSocketUserBean pLVSocketUserBean, String str, boolean z2, Ack ack);

    void setSwitchFirstView(PLVSocketUserBean pLVSocketUserBean, Ack ack);
}
