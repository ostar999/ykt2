package com.arialyy.aria.core.command;

import com.arialyy.aria.core.wrapper.AbsTaskWrapper;

/* loaded from: classes2.dex */
final class StopAllCmd<T extends AbsTaskWrapper> extends AbsNormalCmd<T> {
    public StopAllCmd(T t2, int i2) {
        super(t2, i2);
    }

    @Override // com.arialyy.aria.core.command.ICmd
    public void executeCmd() {
        stopAll();
    }
}
