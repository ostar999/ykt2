package com.arialyy.aria.core.download.target;

import com.arialyy.aria.core.download.DTaskWrapper;
import com.arialyy.aria.core.download.DownloadEntity;
import com.arialyy.aria.core.inf.AbsTarget;
import com.arialyy.aria.orm.DbEntity;
import com.arialyy.aria.util.ALog;
import com.arialyy.aria.util.CommonUtil;
import com.arialyy.aria.util.DbDataHelper;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
class HttpGroupConfigHandler<TARGET extends AbsTarget> extends AbsGroupConfigHandler<TARGET> {
    private List<String> mSubNameTemp;
    private List<String> mUrls;

    public HttpGroupConfigHandler(TARGET target, long j2) {
        super(target, j2);
        this.mUrls = new ArrayList();
        this.mSubNameTemp = new ArrayList();
        this.mUrls.addAll(getEntity().getUrls());
    }

    public void setGroupUrl(List<String> list) throws NoSuchAlgorithmException {
        if (list == null || list.isEmpty()) {
            throw new NullPointerException("组合任务子任务下载地址列表为空");
        }
        this.mUrls.clear();
        this.mUrls.addAll(list);
        String md5Code = CommonUtil.getMd5Code(list);
        getEntity().setGroupHash(md5Code);
        List<DownloadEntity> listCreateHttpSubTask = DbDataHelper.createHttpSubTask(md5Code, this.mUrls);
        ArrayList arrayList = new ArrayList();
        for (DownloadEntity downloadEntity : listCreateHttpSubTask) {
            downloadEntity.setTaskType(1);
            arrayList.add(new DTaskWrapper(downloadEntity));
        }
        getEntity().setUrls(list);
        getEntity().setSubEntities(listCreateHttpSubTask);
        getTaskWrapper().setSubTaskWrapper(arrayList);
    }

    public TARGET setSubFileName(List<String> list) {
        if (list == null || list.isEmpty()) {
            ALog.w(this.TAG, "修改子任务的文件名失败：列表为null");
            return getTarget();
        }
        if (list.size() != getTaskWrapper().getSubTaskWrapper().size()) {
            ALog.w(this.TAG, "修改子任务的文件名失败：子任务文件名列表数量和子任务的数量不匹配");
            return getTarget();
        }
        this.mSubNameTemp.clear();
        this.mSubNameTemp.addAll(list);
        getTaskWrapper().setSubNameTemp(this.mSubNameTemp);
        return getTarget();
    }

    public TARGET updateUrls(List<String> list) throws NoSuchAlgorithmException {
        if (list == null || list.isEmpty()) {
            throw new NullPointerException("下载地址列表为空");
        }
        if (list.size() != this.mUrls.size()) {
            throw new IllegalArgumentException("新下载地址数量和旧下载地址数量不一致");
        }
        this.mUrls.clear();
        this.mUrls.addAll(list);
        String md5Code = CommonUtil.getMd5Code(list);
        getEntity().setUrls(this.mUrls);
        getEntity().setGroupHash(md5Code);
        getEntity().update();
        if (getEntity().getSubEntities() != null && !getEntity().getSubEntities().isEmpty()) {
            int i2 = 0;
            for (DownloadEntity downloadEntity : getEntity().getSubEntities()) {
                downloadEntity.setUrl(this.mUrls.get(i2));
                downloadEntity.setGroupHash(md5Code);
                i2++;
            }
            DbEntity.updateManyData(getEntity().getSubEntities());
        }
        return getTarget();
    }
}
