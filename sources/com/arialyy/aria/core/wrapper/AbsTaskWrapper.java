package com.arialyy.aria.core.wrapper;

import com.arialyy.aria.core.TaskOptionParams;
import com.arialyy.aria.core.common.AbsEntity;
import com.arialyy.aria.core.config.BaseTaskConfig;
import com.arialyy.aria.core.event.ErrorEvent;
import com.arialyy.aria.core.inf.ITaskOption;
import com.arialyy.aria.util.ComponentUtil;

/* loaded from: classes2.dex */
public abstract class AbsTaskWrapper<ENTITY extends AbsEntity> implements ITaskWrapper {
    private int code;
    private ENTITY entity;
    private ErrorEvent errorEvent;
    private ITaskOption taskOption;
    private boolean refreshInfo = false;
    private boolean isNewTask = false;
    private int requestType = 1;
    private boolean removeFile = false;
    private boolean isSupportBP = true;
    private TaskOptionParams optionParams = new TaskOptionParams();
    private boolean ignoreFilePathOccupy = false;

    public AbsTaskWrapper(ENTITY entity) {
        this.entity = entity;
    }

    public void generateTaskOption(Class<? extends ITaskOption> cls) {
        this.taskOption = ComponentUtil.getInstance().buildTaskOption(cls, this.optionParams);
    }

    public int getCode() {
        return this.code;
    }

    public abstract BaseTaskConfig getConfig();

    public ErrorEvent getErrorEvent() {
        return this.errorEvent;
    }

    public TaskOptionParams getOptionParams() {
        if (this.optionParams == null) {
            this.optionParams = new TaskOptionParams();
        }
        return this.optionParams;
    }

    @Override // com.arialyy.aria.core.wrapper.ITaskWrapper
    public int getRequestType() {
        return this.requestType;
    }

    public int getState() {
        return getEntity().getState();
    }

    public ITaskOption getTaskOption() {
        return this.taskOption;
    }

    public boolean isIgnoreFilePathOccupy() {
        return this.ignoreFilePathOccupy;
    }

    public boolean isNewTask() {
        return this.isNewTask;
    }

    public boolean isRefreshInfo() {
        return this.refreshInfo;
    }

    public boolean isRemoveFile() {
        return this.removeFile;
    }

    public boolean isSupportBP() {
        return this.isSupportBP;
    }

    public void setCode(int i2) {
        this.code = i2;
    }

    public void setErrorEvent(ErrorEvent errorEvent) {
        this.errorEvent = errorEvent;
    }

    public void setIgnoreFilePathOccupy(boolean z2) {
        this.ignoreFilePathOccupy = z2;
    }

    public void setNewTask(boolean z2) {
        this.isNewTask = z2;
    }

    public void setRefreshInfo(boolean z2) {
        this.refreshInfo = z2;
    }

    public void setRemoveFile(boolean z2) {
        this.removeFile = z2;
    }

    public void setRequestType(int i2) {
        this.requestType = i2;
    }

    public void setState(int i2) {
        this.entity.setState(i2);
    }

    public void setSupportBP(boolean z2) {
        this.isSupportBP = z2;
    }

    public void setTaskOption(ITaskOption iTaskOption) {
        this.taskOption = iTaskOption;
    }

    @Override // com.arialyy.aria.core.wrapper.ITaskWrapper
    public ENTITY getEntity() {
        return this.entity;
    }
}
