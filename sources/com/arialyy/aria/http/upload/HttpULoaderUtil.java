package com.arialyy.aria.http.upload;

import com.arialyy.aria.core.TaskRecord;
import com.arialyy.aria.core.common.SubThreadConfig;
import com.arialyy.aria.core.loader.AbsNormalLoader;
import com.arialyy.aria.core.loader.AbsNormalLoaderUtil;
import com.arialyy.aria.core.loader.AbsNormalTTBuilderAdapter;
import com.arialyy.aria.core.loader.LoaderStructure;
import com.arialyy.aria.core.loader.NormalTTBuilder;
import com.arialyy.aria.core.loader.NormalThreadStateManager;
import com.arialyy.aria.core.task.IThreadTaskAdapter;
import com.arialyy.aria.core.upload.UTaskWrapper;
import com.arialyy.aria.http.HttpRecordHandler;
import com.arialyy.aria.http.HttpTaskOption;

/* loaded from: classes2.dex */
public final class HttpULoaderUtil extends AbsNormalLoaderUtil {
    @Override // com.arialyy.aria.core.loader.AbsNormalLoaderUtil
    public LoaderStructure BuildLoaderStructure() {
        LoaderStructure loaderStructure = new LoaderStructure();
        loaderStructure.addComponent(new HttpRecordHandler(getTaskWrapper())).addComponent(new NormalThreadStateManager(getListener())).addComponent(new NormalTTBuilder(getTaskWrapper(), new AbsNormalTTBuilderAdapter() { // from class: com.arialyy.aria.http.upload.HttpULoaderUtil.1
            @Override // com.arialyy.aria.core.loader.AbsNormalTTBuilderAdapter
            public IThreadTaskAdapter getAdapter(SubThreadConfig subThreadConfig) {
                return new HttpUThreadTaskAdapter(subThreadConfig);
            }

            @Override // com.arialyy.aria.core.loader.AbsNormalTTBuilderAdapter
            public boolean handleNewTask(TaskRecord taskRecord, int i2) {
                return true;
            }
        }));
        loaderStructure.accept(getLoader());
        return loaderStructure;
    }

    @Override // com.arialyy.aria.core.loader.AbsNormalLoaderUtil
    public AbsNormalLoader getLoader() {
        if (this.mLoader == null) {
            getTaskWrapper().generateTaskOption(HttpTaskOption.class);
            this.mLoader = new HttpULoader((UTaskWrapper) getTaskWrapper(), getListener());
        }
        return this.mLoader;
    }
}
