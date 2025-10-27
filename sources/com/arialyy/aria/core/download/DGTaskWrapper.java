package com.arialyy.aria.core.download;

import com.arialyy.aria.core.config.Configuration;
import com.arialyy.aria.core.config.DGroupConfig;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public class DGTaskWrapper extends AbsGroupTaskWrapper<DownloadGroupEntity, DTaskWrapper> {
    private String dirPathTemp;
    private List<String> subNameTemp;
    private List<DTaskWrapper> subWrappers;
    private boolean unknownSize;

    public DGTaskWrapper(DownloadGroupEntity downloadGroupEntity) {
        super(downloadGroupEntity);
        this.unknownSize = false;
        this.subNameTemp = new ArrayList();
    }

    public String getDirPathTemp() {
        return this.dirPathTemp;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.arialyy.aria.core.wrapper.ITaskWrapper
    public String getKey() {
        return ((DownloadGroupEntity) getEntity()).getKey();
    }

    public List<String> getSubNameTemp() {
        return this.subNameTemp;
    }

    @Override // com.arialyy.aria.core.download.AbsGroupTaskWrapper
    public List<DTaskWrapper> getSubTaskWrapper() {
        if (this.subWrappers == null) {
            this.subWrappers = new ArrayList();
        }
        return this.subWrappers;
    }

    public boolean isUnknownSize() {
        return this.unknownSize;
    }

    public void setDirPathTemp(String str) {
        this.dirPathTemp = str;
    }

    public void setSubNameTemp(List<String> list) {
        this.subNameTemp = list;
    }

    @Override // com.arialyy.aria.core.download.AbsGroupTaskWrapper
    public void setSubTaskWrapper(List<DTaskWrapper> list) {
        this.subWrappers = list;
    }

    public void setUnknownSize(boolean z2) {
        this.unknownSize = z2;
    }

    @Override // com.arialyy.aria.core.wrapper.AbsTaskWrapper
    public DGroupConfig getConfig() {
        return Configuration.getInstance().dGroupCfg;
    }
}
