package com.arialyy.aria.core.inf;

import android.app.Dialog;
import android.util.Log;
import android.widget.PopupWindow;
import com.arialyy.aria.core.WidgetLiftManager;
import com.arialyy.aria.util.CommonUtil;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes2.dex */
public abstract class AbsReceiver implements IReceiver {
    public Object obj;
    private String targetName;
    protected String TAG = getClass().getSimpleName();
    private boolean needRmReceiver = false;
    private boolean isFragment = false;
    public boolean isLocalOrAnonymousClass = false;

    public AbsReceiver(Object obj) {
        this.obj = obj;
        initParams();
    }

    private Dialog getDialog(Object obj) {
        try {
            return (Dialog) obj.getClass().getMethod("getDialog", new Class[0]).invoke(obj, new Object[0]);
        } catch (IllegalAccessException e2) {
            e2.printStackTrace();
            return null;
        } catch (NoSuchMethodException e3) {
            e3.printStackTrace();
            return null;
        } catch (InvocationTargetException e4) {
            e4.printStackTrace();
            return null;
        }
    }

    public static String getKey(IReceiver iReceiver) {
        return String.format("%s_%s_%s", iReceiver.getTargetName(), iReceiver.getType(), Integer.valueOf(iReceiver.hashCode()));
    }

    private void handleFragmentOrDialogParam(Class cls, boolean z2) {
        WidgetLiftManager widgetLiftManager = new WidgetLiftManager();
        Object obj = this.obj;
        if (obj instanceof Dialog) {
            this.needRmReceiver = widgetLiftManager.handleDialogLift((Dialog) obj);
            return;
        }
        if (obj instanceof PopupWindow) {
            this.needRmReceiver = widgetLiftManager.handlePopupWindowLift((PopupWindow) obj);
            return;
        }
        if (CommonUtil.isFragment(cls)) {
            this.isFragment = true;
        }
        if (CommonUtil.isDialogFragment(cls)) {
            this.isFragment = true;
            if (z2) {
                Log.e(this.TAG, String.format("%s 是匿名内部类，无法获取到dialog对象，为了防止内存泄漏，请在dismiss方法中调用Aria.download(this).unRegister();来注销事件", this.obj.getClass().getName()));
            } else {
                this.needRmReceiver = widgetLiftManager.handleDialogFragmentLift(getDialog(this.obj));
            }
        }
    }

    private void initParams() {
        try {
            this.targetName = CommonUtil.getTargetName(this.obj);
            Class<?> cls = this.obj.getClass();
            if (!CommonUtil.isLocalOrAnonymousClass(cls)) {
                handleFragmentOrDialogParam(cls, false);
            } else {
                this.isLocalOrAnonymousClass = true;
                handleFragmentOrDialogParam(Class.forName(CommonUtil.getTargetName(this.obj)), true);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    private void removeObj() {
        this.obj = null;
    }

    @Override // com.arialyy.aria.core.inf.IReceiver
    public void destroy() {
        unRegisterListener();
        removeObj();
    }

    @Override // com.arialyy.aria.core.inf.IReceiver
    public String getTargetName() {
        return this.targetName;
    }

    @Override // com.arialyy.aria.core.inf.IReceiver
    public boolean isFragment() {
        return this.isFragment;
    }

    public boolean isNeedRmListener() {
        return this.needRmReceiver;
    }

    public abstract void unRegisterListener();

    @Override // com.arialyy.aria.core.inf.IReceiver
    public String getKey() {
        return getKey(this);
    }
}
