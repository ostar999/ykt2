package com.sina.weibo.sdk.share;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/* loaded from: classes6.dex */
public class BaseActivity extends Activity {
    private boolean j() throws NoSuchMethodException, SecurityException {
        boolean zBooleanValue;
        Exception e2;
        try {
            TypedArray typedArrayObtainStyledAttributes = obtainStyledAttributes((int[]) Class.forName("com.android.internal.R$styleable").getField("Window").get(null));
            Method method = ActivityInfo.class.getMethod("isTranslucentOrFloating", TypedArray.class);
            method.setAccessible(true);
            zBooleanValue = ((Boolean) method.invoke(null, typedArrayObtainStyledAttributes)).booleanValue();
            try {
                method.setAccessible(false);
            } catch (Exception e3) {
                e2 = e3;
                e2.printStackTrace();
                return zBooleanValue;
            }
        } catch (Exception e4) {
            zBooleanValue = false;
            e2 = e4;
        }
        return zBooleanValue;
    }

    private boolean k() throws NoSuchFieldException, SecurityException {
        try {
            Field declaredField = Activity.class.getDeclaredField("mActivityInfo");
            declaredField.setAccessible(true);
            ((ActivityInfo) declaredField.get(this)).screenOrientation = -1;
            declaredField.setAccessible(false);
            return true;
        } catch (Exception e2) {
            e2.printStackTrace();
            return false;
        }
    }

    @Override // android.app.Activity
    public void onCreate(Bundle bundle) throws NoSuchFieldException, SecurityException {
        if (Build.VERSION.SDK_INT == 26 && j()) {
            k();
        }
        super.onCreate(bundle);
    }

    @Override // android.app.Activity
    public void setRequestedOrientation(int i2) {
        if (Build.VERSION.SDK_INT == 26 && j()) {
            return;
        }
        super.setRequestedOrientation(i2);
    }
}
