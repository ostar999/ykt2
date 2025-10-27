package com.hjq.toast;

import android.annotation.TargetApi;
import android.app.Application;
import android.os.Handler;
import android.widget.Toast;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

@TargetApi(19)
/* loaded from: classes4.dex */
public class SafeToast extends NotificationToast {
    private boolean mHookTN;

    public SafeToast(Application application) {
        super(application);
    }

    private void hookToastTN() throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
        if (this.mHookTN) {
            return;
        }
        this.mHookTN = true;
        try {
            Field declaredField = Toast.class.getDeclaredField("mTN");
            declaredField.setAccessible(true);
            Object obj = declaredField.get(this);
            Field declaredField2 = declaredField.getType().getDeclaredField("mHandler");
            declaredField2.setAccessible(true);
            Handler handler = (Handler) declaredField2.get(obj);
            if (handler instanceof SafeHandler) {
                return;
            }
            declaredField2.set(obj, new SafeHandler(handler));
        } catch (IllegalAccessException | NoSuchFieldException e2) {
            e2.printStackTrace();
        }
    }

    @Override // com.hjq.toast.NotificationToast, android.widget.Toast, com.hjq.toast.config.IToast
    public void show() throws IllegalAccessException, NoSuchFieldException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        hookToastTN();
        super.show();
    }
}
