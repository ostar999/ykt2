package com.arialyy.aria.http.download;

import com.arialyy.aria.core.download.DGTaskWrapper;
import com.arialyy.aria.core.group.AbsGroupLoader;
import com.arialyy.aria.core.group.AbsGroupLoaderUtil;
import com.arialyy.aria.core.listener.DownloadGroupListener;
import com.arialyy.aria.core.loader.LoaderStructure;
import com.arialyy.aria.http.HttpTaskOption;

/* loaded from: classes2.dex */
public final class HttpDGLoaderUtil extends AbsGroupLoaderUtil {
    @Override // com.arialyy.aria.core.group.AbsGroupLoaderUtil
    public LoaderStructure buildLoaderStructure() {
        LoaderStructure loaderStructure = new LoaderStructure();
        loaderStructure.addComponent(new HttpDGInfoTask((DGTaskWrapper) getTaskWrapper(), (DownloadGroupListener) getListener()));
        loaderStructure.accept(getLoader());
        return loaderStructure;
    }

    @Override // com.arialyy.aria.core.group.AbsGroupLoaderUtil
    public AbsGroupLoader getLoader() {
        if (this.mLoader == null) {
            getTaskWrapper().generateTaskOption(HttpTaskOption.class);
            this.mLoader = new HttpDGLoader(getTaskWrapper(), (DownloadGroupListener) getListener());
        }
        return this.mLoader;
    }
}
