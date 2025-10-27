package com.arialyy.aria.http;

import com.arialyy.aria.core.common.AbsNormalEntity;
import com.arialyy.aria.core.common.SubThreadConfig;
import com.arialyy.aria.core.task.AbsThreadTaskAdapter;

/* loaded from: classes2.dex */
public abstract class BaseHttpThreadTaskAdapter extends AbsThreadTaskAdapter {
    protected HttpTaskOption mTaskOption;

    public BaseHttpThreadTaskAdapter(SubThreadConfig subThreadConfig) {
        super(subThreadConfig);
        this.mTaskOption = (HttpTaskOption) getTaskWrapper().getTaskOption();
    }

    public AbsNormalEntity getEntity() {
        return (AbsNormalEntity) getTaskWrapper().getEntity();
    }

    public String getFileName() {
        return getEntity().getFileName();
    }
}
