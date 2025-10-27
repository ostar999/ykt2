package com.aliyun.player.alivcplayerexpand.util;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;
import java.lang.reflect.Field;

/* loaded from: classes2.dex */
public class FixedToastUtils {
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

    public static Toast show(Context context, String str) throws IllegalAccessException, IllegalArgumentException {
        Toast toast = mToast;
        if (toast == null) {
            mToast = Toast.makeText(context.getApplicationContext(), str, 0);
            if (Build.VERSION.SDK_INT == 25) {
                hook(mToast);
            }
        } else {
            toast.setDuration(0);
            mToast.setText(str);
        }
        mToast.show();
        return mToast;
    }

    public static Toast show(Context context, int i2) {
        return show(context, context.getResources().getString(i2));
    }
}
