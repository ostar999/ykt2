package com.google.android.exoplayer2.util;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import androidx.annotation.GuardedBy;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.util.HandlerWrapper;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
final class SystemHandlerWrapper implements HandlerWrapper {
    private static final int MAX_POOL_SIZE = 50;

    @GuardedBy("messagePool")
    private static final List<SystemMessage> messagePool = new ArrayList(50);
    private final Handler handler;

    public static final class SystemMessage implements HandlerWrapper.Message {

        @Nullable
        private SystemHandlerWrapper handler;

        @Nullable
        private Message message;

        private SystemMessage() {
        }

        private void recycle() {
            this.message = null;
            this.handler = null;
            SystemHandlerWrapper.recycleMessage(this);
        }

        @Override // com.google.android.exoplayer2.util.HandlerWrapper.Message
        public HandlerWrapper getTarget() {
            return (HandlerWrapper) Assertions.checkNotNull(this.handler);
        }

        public boolean sendAtFrontOfQueue(Handler handler) {
            boolean zSendMessageAtFrontOfQueue = handler.sendMessageAtFrontOfQueue((Message) Assertions.checkNotNull(this.message));
            recycle();
            return zSendMessageAtFrontOfQueue;
        }

        @Override // com.google.android.exoplayer2.util.HandlerWrapper.Message
        public void sendToTarget() {
            ((Message) Assertions.checkNotNull(this.message)).sendToTarget();
            recycle();
        }

        public SystemMessage setMessage(Message message, SystemHandlerWrapper systemHandlerWrapper) {
            this.message = message;
            this.handler = systemHandlerWrapper;
            return this;
        }
    }

    public SystemHandlerWrapper(Handler handler) {
        this.handler = handler;
    }

    private static SystemMessage obtainSystemMessage() {
        SystemMessage systemMessage;
        List<SystemMessage> list = messagePool;
        synchronized (list) {
            systemMessage = list.isEmpty() ? new SystemMessage() : list.remove(list.size() - 1);
        }
        return systemMessage;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void recycleMessage(SystemMessage systemMessage) {
        List<SystemMessage> list = messagePool;
        synchronized (list) {
            if (list.size() < 50) {
                list.add(systemMessage);
            }
        }
    }

    @Override // com.google.android.exoplayer2.util.HandlerWrapper
    public Looper getLooper() {
        return this.handler.getLooper();
    }

    @Override // com.google.android.exoplayer2.util.HandlerWrapper
    public boolean hasMessages(int i2) {
        return this.handler.hasMessages(i2);
    }

    @Override // com.google.android.exoplayer2.util.HandlerWrapper
    public HandlerWrapper.Message obtainMessage(int i2) {
        return obtainSystemMessage().setMessage(this.handler.obtainMessage(i2), this);
    }

    @Override // com.google.android.exoplayer2.util.HandlerWrapper
    public boolean post(Runnable runnable) {
        return this.handler.post(runnable);
    }

    @Override // com.google.android.exoplayer2.util.HandlerWrapper
    public boolean postAtFrontOfQueue(Runnable runnable) {
        return this.handler.postAtFrontOfQueue(runnable);
    }

    @Override // com.google.android.exoplayer2.util.HandlerWrapper
    public boolean postDelayed(Runnable runnable, long j2) {
        return this.handler.postDelayed(runnable, j2);
    }

    @Override // com.google.android.exoplayer2.util.HandlerWrapper
    public void removeCallbacksAndMessages(@Nullable Object obj) {
        this.handler.removeCallbacksAndMessages(obj);
    }

    @Override // com.google.android.exoplayer2.util.HandlerWrapper
    public void removeMessages(int i2) {
        this.handler.removeMessages(i2);
    }

    @Override // com.google.android.exoplayer2.util.HandlerWrapper
    public boolean sendEmptyMessage(int i2) {
        return this.handler.sendEmptyMessage(i2);
    }

    @Override // com.google.android.exoplayer2.util.HandlerWrapper
    public boolean sendEmptyMessageAtTime(int i2, long j2) {
        return this.handler.sendEmptyMessageAtTime(i2, j2);
    }

    @Override // com.google.android.exoplayer2.util.HandlerWrapper
    public boolean sendEmptyMessageDelayed(int i2, int i3) {
        return this.handler.sendEmptyMessageDelayed(i2, i3);
    }

    @Override // com.google.android.exoplayer2.util.HandlerWrapper
    public boolean sendMessageAtFrontOfQueue(HandlerWrapper.Message message) {
        return ((SystemMessage) message).sendAtFrontOfQueue(this.handler);
    }

    @Override // com.google.android.exoplayer2.util.HandlerWrapper
    public HandlerWrapper.Message obtainMessage(int i2, @Nullable Object obj) {
        return obtainSystemMessage().setMessage(this.handler.obtainMessage(i2, obj), this);
    }

    @Override // com.google.android.exoplayer2.util.HandlerWrapper
    public HandlerWrapper.Message obtainMessage(int i2, int i3, int i4) {
        return obtainSystemMessage().setMessage(this.handler.obtainMessage(i2, i3, i4), this);
    }

    @Override // com.google.android.exoplayer2.util.HandlerWrapper
    public HandlerWrapper.Message obtainMessage(int i2, int i3, int i4, @Nullable Object obj) {
        return obtainSystemMessage().setMessage(this.handler.obtainMessage(i2, i3, i4, obj), this);
    }
}
