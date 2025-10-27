package com.arialyy.aria.core.download;

import android.text.TextUtils;
import com.arialyy.aria.core.common.RequestEnum;
import com.arialyy.aria.core.inf.ICheckEntityUtil;
import com.arialyy.aria.core.inf.IOptionConstant;
import com.arialyy.aria.orm.DbEntity;
import com.arialyy.aria.util.ALog;
import com.arialyy.aria.util.CheckUtil;
import com.arialyy.aria.util.FileUtil;
import com.arialyy.aria.util.RecordUtil;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes2.dex */
public class CheckDGEntityUtil implements ICheckEntityUtil {
    private int action;
    private DownloadGroupEntity mEntity;
    private DGTaskWrapper mWrapper;
    private final String TAG = "CheckDGEntityUtil";
    private boolean needModifyPath = false;

    /* JADX WARN: Multi-variable type inference failed */
    private CheckDGEntityUtil(DGTaskWrapper dGTaskWrapper, int i2) {
        this.action = i2;
        this.mWrapper = dGTaskWrapper;
        this.mEntity = (DownloadGroupEntity) dGTaskWrapper.getEntity();
    }

    private boolean checkDirPath() {
        String dirPathTemp = this.mWrapper.getDirPathTemp();
        if (TextUtils.isEmpty(dirPathTemp)) {
            ALog.e("CheckDGEntityUtil", "文件夹路径不能为null");
            return false;
        }
        if (!FileUtil.canWrite(dirPathTemp)) {
            ALog.e("CheckDGEntityUtil", String.format("路径【%s】不可写", dirPathTemp));
            return false;
        }
        if (!dirPathTemp.startsWith("/")) {
            ALog.e("CheckDGEntityUtil", String.format("文件夹路径【%s】错误", dirPathTemp));
            return false;
        }
        File file = new File(dirPathTemp);
        if (file.isFile()) {
            ALog.e("CheckDGEntityUtil", String.format("路径【%s】是文件，请设置文件夹路径", dirPathTemp));
            return false;
        }
        if (this.mWrapper.isNewTask() && !CheckUtil.checkDGPathConflicts(this.mWrapper.isIgnoreFilePathOccupy(), dirPathTemp)) {
            return false;
        }
        if (TextUtils.isEmpty(this.mEntity.getDirPath()) || !this.mEntity.getDirPath().equals(dirPathTemp)) {
            if (!file.exists()) {
                file.mkdirs();
            }
            this.needModifyPath = true;
            this.mEntity.setDirPath(dirPathTemp);
            ALog.i("CheckDGEntityUtil", String.format("文件夹路径改变，将更新文件夹路径为：%s", dirPathTemp));
        }
        return true;
    }

    private boolean checkGroupHash(boolean z2, String str) {
        DownloadGroupEntity downloadGroupEntity = (DownloadGroupEntity) DbEntity.findFirst(DownloadGroupEntity.class, "groupHash=?", str);
        if (downloadGroupEntity != null && downloadGroupEntity.getGroupHash().equals(this.mEntity.getGroupHash())) {
            this.mEntity.rowID = downloadGroupEntity.rowID;
            return true;
        }
        if (DbEntity.checkDataExist(DownloadGroupEntity.class, "groupHash=?", str)) {
            if (!z2) {
                ALog.e("CheckDGEntityUtil", String.format("下载失败，数据库中已存在相同的url的组任务，groupHash = %s", str));
                return false;
            }
            ALog.w("CheckDGEntityUtil", String.format("数据库中已存在相同的url的组任务，将删除groupHash = %s 的旧任务", str));
            RecordUtil.delGroupTaskRecordByHash(str, true);
        }
        return true;
    }

    private boolean checkSubName() {
        if (this.mWrapper.getSubNameTemp() == null || this.mWrapper.getSubNameTemp().isEmpty() || this.mEntity.getUrls().size() == this.mWrapper.getSubNameTemp().size()) {
            return true;
        }
        ALog.e("CheckDGEntityUtil", "子任务文件名必须和子任务数量一致");
        return false;
    }

    private boolean checkUrls() {
        int i2 = 0;
        if (this.mEntity.getUrls().isEmpty()) {
            ALog.e("CheckDGEntityUtil", "操作失败，子任务下载列表为null");
            return false;
        }
        HashSet hashSet = new HashSet();
        ArrayList arrayList = new ArrayList();
        for (String str : this.mEntity.getUrls()) {
            if (!hashSet.add(str)) {
                arrayList.add(str);
            }
        }
        if (!arrayList.isEmpty()) {
            ALog.e("CheckDGEntityUtil", String.format("组合任务中有url重复，重复的url：%s", Arrays.toString(arrayList.toArray())));
            return false;
        }
        HashSet hashSet2 = new HashSet();
        for (String str2 : this.mEntity.getUrls()) {
            if (TextUtils.isEmpty(str2)) {
                ALog.e("CheckDGEntityUtil", "子任务url为null，即将删除该子任务。");
                hashSet2.add(Integer.valueOf(i2));
            } else if (!str2.startsWith("http")) {
                ALog.e("CheckDGEntityUtil", "子任务url【" + str2 + "】错误，即将删除该子任务。");
                hashSet2.add(Integer.valueOf(i2));
            } else if (str2.indexOf("://") == -1) {
                ALog.e("CheckDGEntityUtil", "子任务url【" + str2 + "】不合法，即将删除该子任务。");
                hashSet2.add(Integer.valueOf(i2));
            } else {
                i2++;
            }
        }
        Iterator it = hashSet2.iterator();
        while (it.hasNext()) {
            int iIntValue = ((Integer) it.next()).intValue();
            this.mEntity.getUrls().remove(iIntValue);
            if (this.mWrapper.getSubNameTemp() != null && !this.mWrapper.getSubNameTemp().isEmpty()) {
                this.mWrapper.getSubNameTemp().remove(iIntValue);
            }
        }
        return true;
    }

    public static CheckDGEntityUtil newInstance(DGTaskWrapper dGTaskWrapper, int i2) {
        return new CheckDGEntityUtil(dGTaskWrapper, i2);
    }

    private void reChangeDirPath(String str) {
        ALog.d("CheckDGEntityUtil", String.format("修改新路径为：%s", str));
        List<DTaskWrapper> subTaskWrapper = this.mWrapper.getSubTaskWrapper();
        if (subTaskWrapper == null || subTaskWrapper.isEmpty()) {
            return;
        }
        Iterator<DTaskWrapper> it = subTaskWrapper.iterator();
        while (it.hasNext()) {
            DownloadEntity entity = it.next().getEntity();
            String filePath = entity.getFilePath();
            String str2 = str + "/" + entity.getFileName();
            File file = new File(filePath);
            if (file.exists()) {
                file.renameTo(new File(str2));
            }
            entity.setFilePath(str2);
        }
    }

    private void saveEntity() {
        this.mEntity.save();
        DbEntity.saveAll(this.mEntity.getSubEntities());
    }

    private void updateSingleSubFileName() {
        int i2 = 0;
        for (DTaskWrapper dTaskWrapper : this.mWrapper.getSubTaskWrapper()) {
            if (i2 < this.mWrapper.getSubNameTemp().size()) {
                String str = this.mWrapper.getSubNameTemp().get(i2);
                DownloadEntity entity = dTaskWrapper.getEntity();
                if (str.equals(entity.getFileName())) {
                    continue;
                } else {
                    String str2 = this.mEntity.getDirPath() + "/" + entity.getFileName();
                    String str3 = this.mEntity.getDirPath() + "/" + str;
                    if (DbEntity.checkDataExist(DownloadEntity.class, "downloadPath=?", str3)) {
                        ALog.w("CheckDGEntityUtil", String.format("更新文件名失败，路径【%s】被其它任务占用", str3));
                        return;
                    } else {
                        RecordUtil.modifyTaskRecord(str2, str3, this.mEntity.getTaskType());
                        entity.setFilePath(str3);
                        entity.setFileName(str);
                    }
                }
            }
            i2++;
        }
    }

    @Override // com.arialyy.aria.core.inf.ICheckEntityUtil
    public boolean checkEntity() {
        if (this.mWrapper.getErrorEvent() != null) {
            ALog.e("CheckDGEntityUtil", String.format("任务操作失败，%s", this.mWrapper.getErrorEvent().errorMsg));
            return false;
        }
        int i2 = this.action;
        if (((i2 == 1 || i2 == 5) && !checkGroupHash(this.mWrapper.isIgnoreTaskOccupy(), this.mEntity.getGroupHash())) || !checkDirPath() || !checkSubName() || !checkUrls()) {
            return false;
        }
        if (this.action != 4 && !this.mWrapper.isUnknownSize() && this.mEntity.getFileSize() == 0) {
            ALog.e("CheckDGEntityUtil", "组合任务必须设置文件文件大小，默认需要强制设置文件大小。如果无法获取到总长度，请调用#unknownSize()来标志该组合任务");
            return false;
        }
        if (this.mWrapper.getOptionParams().getParam(IOptionConstant.requestEnum) == RequestEnum.POST) {
            Iterator<DTaskWrapper> it = this.mWrapper.getSubTaskWrapper().iterator();
            while (it.hasNext()) {
                it.next().getOptionParams().setParams(IOptionConstant.requestEnum, RequestEnum.POST);
            }
        }
        if (this.needModifyPath) {
            reChangeDirPath(this.mWrapper.getDirPathTemp());
        }
        if (!this.mWrapper.getSubNameTemp().isEmpty()) {
            updateSingleSubFileName();
        }
        saveEntity();
        return true;
    }
}
