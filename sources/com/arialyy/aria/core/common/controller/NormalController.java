package com.arialyy.aria.core.common.controller;

import com.arialyy.aria.core.command.CancelCmd;
import com.arialyy.aria.core.command.CmdHelper;
import com.arialyy.aria.core.command.StartCmd;
import com.arialyy.aria.core.event.EventMsgUtil;
import com.arialyy.aria.core.wrapper.AbsTaskWrapper;
import com.arialyy.aria.util.ALog;

/* loaded from: classes2.dex */
public final class NormalController extends FeatureController implements INormalFeature {
    private String TAG;

    public NormalController(AbsTaskWrapper absTaskWrapper) {
        super(absTaskWrapper);
        this.TAG = "NormalController";
    }

    @Override // com.arialyy.aria.core.common.controller.INormalFeature
    public void cancel() {
        cancel(false);
    }

    @Override // com.arialyy.aria.core.common.controller.INormalFeature
    public long reStart() {
        setAction(8);
        if (!checkConfig()) {
            return -1L;
        }
        EventMsgUtil.getDefault().post(CmdHelper.createNormalCmd(getTaskWrapper(), 186, checkTaskType()));
        return getEntity().getId();
    }

    @Override // com.arialyy.aria.core.common.controller.INormalFeature
    public void reTry() {
        setAction(7);
        if (checkConfig()) {
            int iCheckTaskType = checkTaskType();
            EventMsgUtil.getDefault().post(CmdHelper.createNormalCmd(getTaskWrapper(), 181, iCheckTaskType));
            EventMsgUtil.getDefault().post(CmdHelper.createNormalCmd(getTaskWrapper(), 178, iCheckTaskType));
        }
    }

    @Override // com.arialyy.aria.core.common.controller.INormalFeature
    public void resume() {
        resume(false);
    }

    @Override // com.arialyy.aria.core.common.controller.INormalFeature
    public void save() {
        setAction(9);
        if (checkConfig()) {
            ALog.i(this.TAG, "保存成功");
        } else {
            ALog.e(this.TAG, "保存修改失败");
        }
    }

    @Override // com.arialyy.aria.core.common.controller.INormalFeature
    public void stop() {
        setAction(3);
        if (checkConfig()) {
            EventMsgUtil.getDefault().post(CmdHelper.createNormalCmd(getTaskWrapper(), 181, checkTaskType()));
        }
    }

    @Override // com.arialyy.aria.core.common.controller.INormalFeature
    public void cancel(boolean z2) {
        setAction(4);
        if (checkConfig()) {
            CancelCmd cancelCmd = (CancelCmd) CmdHelper.createNormalCmd(getTaskWrapper(), 180, checkTaskType());
            cancelCmd.removeFile = z2;
            EventMsgUtil.getDefault().post(cancelCmd);
        }
    }

    @Override // com.arialyy.aria.core.common.controller.INormalFeature
    public void resume(boolean z2) {
        setAction(2);
        if (checkConfig()) {
            StartCmd startCmd = (StartCmd) CmdHelper.createNormalCmd(getTaskWrapper(), 178, checkTaskType());
            startCmd.setNowStart(z2);
            EventMsgUtil.getDefault().post(startCmd);
        }
    }
}
