package org.greenrobot.eventbus;

/* loaded from: classes9.dex */
public interface MainThreadSupport {
    Poster createPoster(EventBus eventBus);

    boolean isMainThread();
}
