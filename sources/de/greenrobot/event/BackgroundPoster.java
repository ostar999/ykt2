package de.greenrobot.event;

import android.util.Log;

/* loaded from: classes8.dex */
final class BackgroundPoster implements Runnable {
    private final EventBus eventBus;
    private volatile boolean executorRunning;
    private final PendingPostQueue queue = new PendingPostQueue();

    public BackgroundPoster(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    public void enqueue(Subscription subscription, Object obj) {
        PendingPost pendingPostObtainPendingPost = PendingPost.obtainPendingPost(subscription, obj);
        synchronized (this) {
            this.queue.enqueue(pendingPostObtainPendingPost);
            if (!this.executorRunning) {
                this.executorRunning = true;
                this.eventBus.getExecutorService().execute(this);
            }
        }
    }

    @Override // java.lang.Runnable
    public void run() {
        while (true) {
            try {
                PendingPost pendingPostPoll = this.queue.poll(1000);
                if (pendingPostPoll == null) {
                    synchronized (this) {
                        pendingPostPoll = this.queue.poll();
                        if (pendingPostPoll == null) {
                            return;
                        }
                    }
                }
                this.eventBus.invokeSubscriber(pendingPostPoll);
            } catch (InterruptedException e2) {
                Log.w("Event", Thread.currentThread().getName() + " was interruppted", e2);
                return;
            } finally {
                this.executorRunning = false;
            }
        }
    }
}
