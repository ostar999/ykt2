package com.arialyy.aria.core.upload;

import android.text.TextUtils;
import com.arialyy.aria.core.inf.ICheckEntityUtil;
import com.arialyy.aria.util.ALog;
import com.arialyy.aria.util.CheckUtil;
import java.io.File;

/* loaded from: classes2.dex */
public class CheckUEntityUtil implements ICheckEntityUtil {
    private final String TAG = "CheckUEntityUtil";
    private int action;
    private UploadEntity mEntity;
    private UTaskWrapper mWrapper;

    private CheckUEntityUtil(UTaskWrapper uTaskWrapper, int i2) {
        this.action = i2;
        this.mWrapper = uTaskWrapper;
        this.mEntity = uTaskWrapper.getEntity();
    }

    private boolean checkFilePath() {
        String filePath = this.mEntity.getFilePath();
        if (TextUtils.isEmpty(filePath)) {
            ALog.e("CheckUEntityUtil", "上传失败，文件路径为null");
            return false;
        }
        if (!filePath.startsWith("/")) {
            ALog.e("CheckUEntityUtil", "上传失败，文件路径【" + filePath + "】不合法");
            return false;
        }
        if (this.mWrapper.isNewTask() && !CheckUtil.checkUPathConflicts(this.mWrapper.isIgnoreFilePathOccupy(), filePath, this.mWrapper.getRequestType())) {
            return false;
        }
        File file = new File(this.mEntity.getFilePath());
        if (!file.exists()) {
            ALog.e("CheckUEntityUtil", "上传失败，文件【" + filePath + "】不存在");
            return false;
        }
        if (!file.isDirectory()) {
            return true;
        }
        ALog.e("CheckUEntityUtil", "上传失败，文件【" + filePath + "】不能是文件夹");
        return false;
    }

    private boolean checkUrl() {
        String tempUrl = this.mWrapper.getTempUrl();
        if (TextUtils.isEmpty(tempUrl)) {
            ALog.e("CheckUEntityUtil", "上传失败，url为null");
            return false;
        }
        if (!CheckUtil.checkUrl(tempUrl)) {
            ALog.e("CheckUEntityUtil", "上传失败，url【" + tempUrl + "】错误");
            return false;
        }
        if (tempUrl.indexOf("://") != -1) {
            this.mEntity.setUrl(tempUrl);
            return true;
        }
        ALog.e("CheckUEntityUtil", "上传失败，url【" + tempUrl + "】不合法");
        return false;
    }

    public static CheckUEntityUtil newInstance(UTaskWrapper uTaskWrapper, int i2) {
        return new CheckUEntityUtil(uTaskWrapper, i2);
    }

    @Override // com.arialyy.aria.core.inf.ICheckEntityUtil
    public boolean checkEntity() {
        if (this.mWrapper.getErrorEvent() != null) {
            ALog.e("CheckUEntityUtil", String.format("任务操作失败，%s", this.mWrapper.getErrorEvent().errorMsg));
            return false;
        }
        boolean z2 = checkUrl() && checkFilePath();
        if (z2) {
            this.mEntity.save();
        }
        return z2;
    }
}
