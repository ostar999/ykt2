package com.arialyy.aria.core.manager;

import com.arialyy.aria.core.download.DGEntityWrapper;
import com.arialyy.aria.core.download.DGTaskWrapper;
import com.arialyy.aria.core.download.DownloadGroupEntity;
import com.arialyy.aria.orm.DbEntity;
import com.arialyy.aria.util.CommonUtil;
import com.arialyy.aria.util.DbDataHelper;
import java.util.List;

/* loaded from: classes2.dex */
class DGTaskWrapperFactory implements IGroupWrapperFactory<DownloadGroupEntity, DGTaskWrapper> {
    private static volatile DGTaskWrapperFactory INSTANCE;
    private final String TAG = CommonUtil.getClassName(this);

    private DGTaskWrapperFactory() {
    }

    public static DGTaskWrapperFactory getInstance() {
        if (INSTANCE == null) {
            synchronized (DGTaskWrapperFactory.class) {
                INSTANCE = new DGTaskWrapperFactory();
            }
        }
        return INSTANCE;
    }

    private DownloadGroupEntity getOrCreateHttpDGEntity(long j2) {
        List listFindRelationData = DbEntity.findRelationData(DGEntityWrapper.class, "DownloadGroupEntity.rowid=?", String.valueOf(j2));
        if (listFindRelationData == null || listFindRelationData.isEmpty()) {
            return new DownloadGroupEntity();
        }
        DownloadGroupEntity downloadGroupEntity = ((DGEntityWrapper) listFindRelationData.get(0)).groupEntity;
        return downloadGroupEntity == null ? new DownloadGroupEntity() : downloadGroupEntity;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.arialyy.aria.core.manager.IGroupWrapperFactory
    public DGTaskWrapper getGroupWrapper(long j2) {
        DGTaskWrapper dGTaskWrapper;
        if (j2 == -1) {
            dGTaskWrapper = new DGTaskWrapper(new DownloadGroupEntity());
        } else {
            DownloadGroupEntity orCreateHttpDGEntity = getOrCreateHttpDGEntity(j2);
            DGTaskWrapper dGTaskWrapper2 = new DGTaskWrapper(orCreateHttpDGEntity);
            if (orCreateHttpDGEntity.getSubEntities() != null && !orCreateHttpDGEntity.getSubEntities().isEmpty()) {
                dGTaskWrapper2.setSubTaskWrapper(DbDataHelper.createDGSubTaskWrapper(orCreateHttpDGEntity));
            }
            dGTaskWrapper = dGTaskWrapper2;
        }
        dGTaskWrapper.setRequestType(((DownloadGroupEntity) dGTaskWrapper.getEntity()).getTaskType());
        return dGTaskWrapper;
    }
}
