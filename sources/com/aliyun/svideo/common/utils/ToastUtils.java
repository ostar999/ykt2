package com.aliyun.svideo.common.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;
import java.lang.reflect.Field;

/* loaded from: classes2.dex */
public class ToastUtils {
    private static Field mFieldTN;
    private static Field mFieldTNHandler;
    private static Toast mToast;

    public static class FiexHandler extends Handler {
        private Handler impl;

        public FiexHandler(Handler handler) {
            this.impl = handler;
        }

        @Override // android.os.Handler
        public void dispatchMessage(Message message) {
            try {
                super.dispatchMessage(message);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            this.impl.handleMessage(message);
        }
    }

    static {
        if (Build.VERSION.SDK_INT == 25) {
            try {
                Field declaredField = Toast.class.getDeclaredField("mTN");
                mFieldTN = declaredField;
                declaredField.setAccessible(true);
                Field declaredField2 = mFieldTN.getType().getDeclaredField("mHandler");
                mFieldTNHandler = declaredField2;
                declaredField2.setAccessible(true);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    private static void hook(Toast toast) throws IllegalAccessException, IllegalArgumentException {
        try {
            Object obj = mFieldTN.get(toast);
            mFieldTNHandler.set(obj, new FiexHandler((Handler) mFieldTNHandler.get(obj)));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    @SuppressLint({"ShowToast"})
    private static Toast initToast(Context context) throws IllegalAccessException, IllegalArgumentException {
        if (mToast == null) {
            mToast = Toast.makeText(context.getApplicationContext(), "", 0);
            if (Build.VERSION.SDK_INT == 25) {
                hook(mToast);
            }
        }
        return mToast;
    }

    public static void show(Context context, String str) throws IllegalAccessException, IllegalArgumentException {
        show(context, str, 0);
    }

    public static void showInCenter(Context context, String str) throws IllegalAccessException, IllegalArgumentException {
        Toast toastInitToast = initToast(context);
        toastInitToast.setText(str);
        toastInitToast.setDuration(0);
        toastInitToast.setGravity(17, 0, 0);
        toastInitToast.show();
    }

    public static void show(Context context, int i2) throws IllegalAccessException, IllegalArgumentException {
        show(context, i2, 0);
    }

    public static void show(Context context, String str, int i2) throws IllegalAccessException, IllegalArgumentException {
        Toast toastInitToast = initToast(context);
        toastInitToast.setDuration(i2);
        toastInitToast.setText(str);
        mToast.show();
    }

    public static void show(Context context, int i2, int i3) throws IllegalAccessException, IllegalArgumentException {
        Toast toastInitToast = initToast(context);
        toastInitToast.setDuration(i3);
        toastInitToast.setText(i2);
        mToast.show();
    }

    public static void show(Context context, String str, int i2, int i3) throws IllegalAccessException, IllegalArgumentException {
        Toast toastInitToast = initToast(context);
        toastInitToast.setDuration(i3);
        toastInitToast.setText(str);
        toastInitToast.setGravity(i2, 0, 0);
        mToast.show();
    }
}
