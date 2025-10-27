package com.hyphenate.easeui.manager;

import android.os.Process;
import java.util.concurrent.ThreadFactory;

/* loaded from: classes4.dex */
public class BackgroundThreadFactory implements ThreadFactory {
    private final int mThreadPriority;

    public BackgroundThreadFactory(int i2) {
        this.mThreadPriority = i2;
    }

    @Override // java.util.concurrent.ThreadFactory
    public Thread newThread(final Runnable runnable) {
        return new Thread(new Runnable() { // from class: com.hyphenate.easeui.manager.BackgroundThreadFactory.1
            @Override // java.lang.Runnable
            public void run() {
                try {
                    Process.setThreadPriority(BackgroundThreadFactory.this.mThreadPriority);
                } catch (Throwable th) {
                    th.printStackTrace();
                }
                runnable.run();
            }
        });
    }
}
