package com.arialyy.aria.core.task;

import android.os.Bundle;
import com.arialyy.aria.exception.AriaException;

/* loaded from: classes2.dex */
public interface IThreadTaskObserver {
    long getThreadProgress();

    void updateCompleteState();

    void updateFailState(AriaException ariaException, boolean z2);

    void updateProgress(long j2);

    void updateState(int i2, Bundle bundle);
}
