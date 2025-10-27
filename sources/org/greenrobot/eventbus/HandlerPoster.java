package org.greenrobot.eventbus;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;

/* loaded from: classes9.dex */
public class HandlerPoster extends Handler implements Poster {
    private final EventBus eventBus;
    private boolean handlerActive;
    private final int maxMillisInsideHandleMessage;
    private final PendingPostQueue queue;

    public HandlerPoster(EventBus eventBus, Looper looper, int i2) {
        super(looper);
        this.eventBus = eventBus;
        this.maxMillisInsideHandleMessage = i2;
        this.queue = new PendingPostQueue();
    }

    @Override // org.greenrobot.eventbus.Poster
    public void enqueue(Subscription subscription, Object obj) {
        PendingPost pendingPostObtainPendingPost = PendingPost.obtainPendingPost(subscription, obj);
        synchronized (this) {
            this.queue.enqueue(pendingPostObtainPendingPost);
            if (!this.handlerActive) {
                this.handlerActive = true;
                if (!sendMessage(obtainMessage())) {
                    throw new EventBusException("Could not send handler message");
                }
            }
        }
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) {
        try {
            long jUptimeMillis = SystemClock.uptimeMillis();
            do {
                PendingPost pendingPostPoll = this.queue.poll();
                if (pendingPostPoll == null) {
                    synchronized (this) {
                        pendingPostPoll = this.queue.poll();
                        if (pendingPostPoll == null) {
                            return;
                        }
                    }
                }
                this.eventBus.invokeSubscriber(pendingPostPoll);
            } while (SystemClock.uptimeMillis() - jUptimeMillis < this.maxMillisInsideHandleMessage);
            if (!sendMessage(obtainMessage())) {
                throw new EventBusException("Could not send handler message");
            }
            this.handlerActive = true;
        } finally {
            this.handlerActive = false;
        }
    }
}
