package com.arialyy.aria.core.command;

import com.arialyy.aria.core.queue.AbsTaskQueue;
import com.arialyy.aria.core.wrapper.AbsTaskWrapper;

/* loaded from: classes2.dex */
public abstract class AbsCmd<T extends AbsTaskWrapper> implements ICmd {
    protected String TAG;
    protected boolean isDownloadCmd = true;
    protected AbsTaskQueue mQueue;
    protected T mTaskWrapper;
}
