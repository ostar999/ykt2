package com.arialyy.aria.http.download;

import com.arialyy.aria.core.download.DTaskWrapper;
import com.arialyy.aria.core.loader.AbsNormalLoader;
import com.arialyy.aria.core.loader.AbsNormalLoaderUtil;
import com.arialyy.aria.core.loader.LoaderStructure;
import com.arialyy.aria.core.loader.NormalLoader;
import com.arialyy.aria.core.loader.NormalTTBuilder;
import com.arialyy.aria.core.loader.NormalThreadStateManager;
import com.arialyy.aria.http.HttpRecordHandler;
import com.arialyy.aria.http.HttpTaskOption;

/* loaded from: classes2.dex */
public final class HttpDLoaderUtil extends AbsNormalLoaderUtil {
    @Override // com.arialyy.aria.core.loader.AbsNormalLoaderUtil
    public LoaderStructure BuildLoaderStructure() {
        LoaderStructure loaderStructure = new LoaderStructure();
        loaderStructure.addComponent(new HttpRecordHandler(getTaskWrapper())).addComponent(new NormalThreadStateManager(getListener())).addComponent(new HttpDFileInfoTask((DTaskWrapper) getTaskWrapper())).addComponent(new NormalTTBuilder(getTaskWrapper(), new HttpDTTBuilderAdapter()));
        loaderStructure.accept(getLoader());
        return loaderStructure;
    }

    @Override // com.arialyy.aria.core.loader.AbsNormalLoaderUtil
    public AbsNormalLoader getLoader() {
        if (this.mLoader == null) {
            getTaskWrapper().generateTaskOption(HttpTaskOption.class);
            this.mLoader = new NormalLoader(getTaskWrapper(), getListener());
        }
        return this.mLoader;
    }
}
