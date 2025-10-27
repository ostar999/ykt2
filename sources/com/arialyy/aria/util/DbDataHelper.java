package com.arialyy.aria.util;

import cn.hutool.core.text.StrPool;
import com.arialyy.aria.core.TaskRecord;
import com.arialyy.aria.core.ThreadRecord;
import com.arialyy.aria.core.download.DGEntityWrapper;
import com.arialyy.aria.core.download.DTaskWrapper;
import com.arialyy.aria.core.download.DownloadEntity;
import com.arialyy.aria.core.download.DownloadGroupEntity;
import com.arialyy.aria.orm.DbEntity;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes2.dex */
public class DbDataHelper {
    public static List<DTaskWrapper> createDGSubTaskWrapper(DownloadGroupEntity downloadGroupEntity) {
        ArrayList arrayList = new ArrayList();
        Iterator<DownloadEntity> it = downloadGroupEntity.getSubEntities().iterator();
        while (it.hasNext()) {
            DTaskWrapper dTaskWrapper = new DTaskWrapper(it.next());
            dTaskWrapper.setGroupHash(downloadGroupEntity.getKey());
            dTaskWrapper.setGroupTask(true);
            arrayList.add(dTaskWrapper);
        }
        return arrayList;
    }

    public static List<DownloadEntity> createHttpSubTask(String str, List<String> list) {
        ArrayList arrayList = new ArrayList();
        int size = list.size();
        for (int i2 = 0; i2 < size; i2++) {
            String str2 = list.get(i2);
            DownloadEntity downloadEntity = new DownloadEntity();
            downloadEntity.setUrl(str2);
            downloadEntity.setFilePath(str + StrPool.UNDERLINE + i2);
            int iLastIndexOf = str2.lastIndexOf(File.separator);
            int iLastIndexOf2 = str2.lastIndexOf("?");
            if (iLastIndexOf2 < 0 || iLastIndexOf2 < iLastIndexOf) {
                iLastIndexOf2 = str2.length();
            }
            downloadEntity.setFileName(str2.substring(iLastIndexOf + 1, iLastIndexOf2));
            downloadEntity.setGroupHash(str);
            downloadEntity.setGroupChild(true);
            arrayList.add(downloadEntity);
        }
        return arrayList;
    }

    public static DownloadGroupEntity getDGEntity(long j2) {
        List listFindRelationData = DbEntity.findRelationData(DGEntityWrapper.class, "DownloadGroupEntity.rowid=?", String.valueOf(j2));
        if (listFindRelationData == null || listFindRelationData.size() == 0) {
            return null;
        }
        return ((DGEntityWrapper) listFindRelationData.get(0)).groupEntity;
    }

    public static DownloadGroupEntity getDGEntityByHash(String str) {
        List listFindRelationData = DbEntity.findRelationData(DGEntityWrapper.class, "DownloadGroupEntity.groupHash=?", str);
        if (listFindRelationData == null || listFindRelationData.size() == 0) {
            return null;
        }
        return ((DGEntityWrapper) listFindRelationData.get(0)).groupEntity;
    }

    public static DownloadGroupEntity getDGEntityByPath(String str) {
        List listFindRelationData = DbEntity.findRelationData(DGEntityWrapper.class, "DownloadGroupEntity.dirPath=?", str);
        if (listFindRelationData == null || listFindRelationData.size() == 0) {
            return null;
        }
        return ((DGEntityWrapper) listFindRelationData.get(0)).groupEntity;
    }

    public static DownloadGroupEntity getOrCreateFtpDGEntity(String str) {
        DownloadGroupEntity downloadGroupEntity;
        List listFindRelationData = DbEntity.findRelationData(DGEntityWrapper.class, "DownloadGroupEntity.groupHash=?", str);
        if (listFindRelationData == null || listFindRelationData.isEmpty() || (downloadGroupEntity = ((DGEntityWrapper) listFindRelationData.get(0)).groupEntity) == null) {
            downloadGroupEntity = new DownloadGroupEntity();
        }
        downloadGroupEntity.setGroupHash(str);
        return downloadGroupEntity;
    }

    public static TaskRecord getTaskRecord(String str, int i2) {
        TaskRecord taskRecord = (TaskRecord) DbEntity.findFirst(TaskRecord.class, "filePath=? AND taskType=?", str, String.valueOf(i2));
        if (taskRecord != null) {
            taskRecord.threadRecords = DbEntity.findDatas(ThreadRecord.class, "taskKey=? AND threadType=?", str, String.valueOf(i2));
        }
        return taskRecord;
    }
}
