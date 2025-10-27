package com.arialyy.aria.core.command;

import com.arialyy.aria.core.AriaConfig;
import com.arialyy.aria.core.wrapper.AbsTaskWrapper;
import com.arialyy.aria.util.ALog;
import com.arialyy.aria.util.NetUtils;

/* loaded from: classes2.dex */
final class ResumeAllCmd<T extends AbsTaskWrapper> extends AbsNormalCmd<T> {
    public ResumeAllCmd(T t2, int i2) {
        super(t2, i2);
    }

    @Override // com.arialyy.aria.core.command.ICmd
    public void executeCmd() {
        if (NetUtils.isConnected(AriaConfig.getInstance().getAPP())) {
            new Thread(new ResumeThread(this.isDownloadCmd, String.format("state!=%s", 1))).start();
        } else {
            ALog.w(this.TAG, "恢复任务失败，网络未连接");
        }
    }
}
