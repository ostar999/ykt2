package com.arialyy.aria.core.common.controller;

import android.os.Handler;
import android.os.Looper;
import com.arialyy.aria.core.AriaConfig;
import com.arialyy.aria.core.common.AbsEntity;
import com.arialyy.aria.core.download.DGTaskWrapper;
import com.arialyy.aria.core.download.DTaskWrapper;
import com.arialyy.aria.core.scheduler.TaskSchedulers;
import com.arialyy.aria.core.upload.UTaskWrapper;
import com.arialyy.aria.core.wrapper.AbsTaskWrapper;
import com.arialyy.aria.util.ALog;
import com.arialyy.aria.util.CommonUtil;
import com.hjq.permissions.Permission;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes2.dex */
public abstract class FeatureController {
    public static final int ACTION_ADD = 5;
    public static final int ACTION_CANCEL = 4;
    public static final int ACTION_CREATE = 1;
    private static final int ACTION_DEF = 0;
    public static final int ACTION_PRIORITY = 6;
    public static final int ACTION_RESTART = 8;
    public static final int ACTION_RESUME = 2;
    public static final int ACTION_RETRY = 7;
    public static final int ACTION_SAVE = 9;
    public static final int ACTION_STOP = 3;
    private AbsTaskWrapper mTaskWrapper;
    private boolean ignoreCheckPermissions = false;
    private int action = 0;
    private final String TAG = CommonUtil.getClassName((Class) getClass());

    public FeatureController(AbsTaskWrapper absTaskWrapper) {
        this.mTaskWrapper = absTaskWrapper;
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x0046  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private boolean checkEntity() {
        /*
            r2 = this;
            com.arialyy.aria.core.wrapper.AbsTaskWrapper r0 = r2.mTaskWrapper
            boolean r1 = r0 instanceof com.arialyy.aria.core.download.DTaskWrapper
            if (r1 == 0) goto Lf
            com.arialyy.aria.core.download.DTaskWrapper r0 = (com.arialyy.aria.core.download.DTaskWrapper) r0
            int r1 = r2.action
            com.arialyy.aria.core.download.CheckDEntityUtil r0 = com.arialyy.aria.core.download.CheckDEntityUtil.newInstance(r0, r1)
            goto L47
        Lf:
            boolean r1 = r0 instanceof com.arialyy.aria.core.download.DGTaskWrapper
            if (r1 == 0) goto L39
            int r0 = r0.getRequestType()
            r1 = 4
            if (r0 != r1) goto L25
            com.arialyy.aria.core.wrapper.AbsTaskWrapper r0 = r2.mTaskWrapper
            com.arialyy.aria.core.download.DGTaskWrapper r0 = (com.arialyy.aria.core.download.DGTaskWrapper) r0
            int r1 = r2.action
            com.arialyy.aria.core.download.CheckFtpDirEntityUtil r0 = com.arialyy.aria.core.download.CheckFtpDirEntityUtil.newInstance(r0, r1)
            goto L47
        L25:
            com.arialyy.aria.core.wrapper.AbsTaskWrapper r0 = r2.mTaskWrapper
            int r0 = r0.getRequestType()
            r1 = 2
            if (r0 != r1) goto L46
            com.arialyy.aria.core.wrapper.AbsTaskWrapper r0 = r2.mTaskWrapper
            com.arialyy.aria.core.download.DGTaskWrapper r0 = (com.arialyy.aria.core.download.DGTaskWrapper) r0
            int r1 = r2.action
            com.arialyy.aria.core.download.CheckDGEntityUtil r0 = com.arialyy.aria.core.download.CheckDGEntityUtil.newInstance(r0, r1)
            goto L47
        L39:
            boolean r1 = r0 instanceof com.arialyy.aria.core.upload.UTaskWrapper
            if (r1 == 0) goto L46
            com.arialyy.aria.core.upload.UTaskWrapper r0 = (com.arialyy.aria.core.upload.UTaskWrapper) r0
            int r1 = r2.action
            com.arialyy.aria.core.upload.CheckUEntityUtil r0 = com.arialyy.aria.core.upload.CheckUEntityUtil.newInstance(r0, r1)
            goto L47
        L46:
            r0 = 0
        L47:
            if (r0 == 0) goto L51
            boolean r0 = r0.checkEntity()
            if (r0 == 0) goto L51
            r0 = 1
            goto L52
        L51:
            r0 = 0
        L52:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.arialyy.aria.core.common.controller.FeatureController.checkEntity():boolean");
    }

    private boolean checkPermission() {
        if (AriaConfig.getInstance().getAPP().checkCallingOrSelfPermission(Permission.WRITE_EXTERNAL_STORAGE) != 0) {
            ALog.e(this.TAG, "启动失败，缺少权限：Manifest.permission.WRITE_EXTERNAL_STORAGE");
            return false;
        }
        if (AriaConfig.getInstance().getAPP().checkCallingOrSelfPermission("android.permission.INTERNET") != 0) {
            ALog.e(this.TAG, "启动失败，缺少权限：Manifest.permission.INTERNET");
            return false;
        }
        if (AriaConfig.getInstance().getAPP().checkCallingOrSelfPermission(Permission.READ_EXTERNAL_STORAGE) == 0) {
            return true;
        }
        ALog.e(this.TAG, "启动失败，缺少权限：Manifest.permission.READ_EXTERNAL_STORAGE");
        return false;
    }

    public static <T extends FeatureController> T newInstance(Class<T> cls, AbsTaskWrapper absTaskWrapper) {
        if (absTaskWrapper.getEntity().getId() == -1 && cls != ControllerType.CREATE_CONTROLLER) {
            throw new IllegalArgumentException("对于不存在的任务（第一次下载），只能使用\"ControllerType.CREATE_CONTROLLER\"");
        }
        if (absTaskWrapper.getEntity().getId() != -1 && cls != ControllerType.TASK_CONTROLLER) {
            throw new IllegalArgumentException("对于已存在的任务，只能使用\" ControllerType.TASK_CONTROLLER\"，请检查是否重复调用#create()方法");
        }
        try {
            return cls.getConstructor(AbsTaskWrapper.class).newInstance(absTaskWrapper);
        } catch (IllegalAccessException e2) {
            e2.printStackTrace();
            return null;
        } catch (InstantiationException e3) {
            e3.printStackTrace();
            return null;
        } catch (NoSuchMethodException e4) {
            e4.printStackTrace();
            return null;
        } catch (InvocationTargetException e5) {
            e5.printStackTrace();
            return null;
        }
    }

    public boolean checkConfig() {
        if (!this.ignoreCheckPermissions && !checkPermission()) {
            return false;
        }
        boolean zCheckEntity = checkEntity();
        TaskSchedulers taskSchedulers = TaskSchedulers.getInstance();
        if (!zCheckEntity && taskSchedulers != null) {
            new Handler(Looper.getMainLooper(), taskSchedulers).obtainMessage(11, checkTaskType(), -1, null).sendToTarget();
        }
        return zCheckEntity;
    }

    public int checkTaskType() {
        AbsTaskWrapper absTaskWrapper = this.mTaskWrapper;
        if (absTaskWrapper instanceof DTaskWrapper) {
            return 1;
        }
        if (absTaskWrapper instanceof DGTaskWrapper) {
            return 3;
        }
        return absTaskWrapper instanceof UTaskWrapper ? 2 : 0;
    }

    public AbsEntity getEntity() {
        return this.mTaskWrapper.getEntity();
    }

    public AbsTaskWrapper getTaskWrapper() {
        return this.mTaskWrapper;
    }

    public void ignoreCheckPermissions() {
        this.ignoreCheckPermissions = true;
    }

    public void ignoreFilePathOccupy() {
        this.mTaskWrapper.setIgnoreFilePathOccupy(true);
    }

    public void setAction(int i2) {
        this.action = i2;
    }
}
