package de.greenrobot.event;

import java.lang.reflect.InvocationTargetException;

/* loaded from: classes8.dex */
class AsyncPoster implements Runnable {
    private final EventBus eventBus;
    private final PendingPostQueue queue = new PendingPostQueue();

    public AsyncPoster(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    public void enqueue(Subscription subscription, Object obj) {
        this.queue.enqueue(PendingPost.obtainPendingPost(subscription, obj));
        this.eventBus.getExecutorService().execute(this);
    }

    @Override // java.lang.Runnable
    public void run() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        PendingPost pendingPostPoll = this.queue.poll();
        if (pendingPostPoll == null) {
            throw new IllegalStateException("No pending post available");
        }
        this.eventBus.invokeSubscriber(pendingPostPoll);
    }
}
