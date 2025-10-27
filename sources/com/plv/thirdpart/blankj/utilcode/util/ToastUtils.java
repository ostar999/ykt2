package com.plv.thirdpart.blankj.utilcode.util;

import android.R;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.core.view.ViewCompat;
import java.lang.ref.WeakReference;

/* loaded from: classes5.dex */
public final class ToastUtils {
    private static Toast sToast;
    private static WeakReference<View> sViewWeakReference;
    private static final Handler HANDLER = new Handler(Looper.getMainLooper());
    private static int sLayoutId = -1;
    private static int gravity = 81;
    private static int xOffset = 0;
    private static int yOffset = (int) ((Utils.getApp().getResources().getDisplayMetrics().density * 64.0f) + 0.5d);
    private static final int COLOR_DEFAULT = -16777217;
    private static int bgColor = COLOR_DEFAULT;
    private static int bgResource = -1;
    private static int msgColor = COLOR_DEFAULT;

    private ToastUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static void cancel() {
        Toast toast = sToast;
        if (toast != null) {
            toast.cancel();
            sToast = null;
        }
    }

    private static View getView(@LayoutRes int i2) {
        WeakReference<View> weakReference;
        View view;
        if (sLayoutId == i2 && (weakReference = sViewWeakReference) != null && (view = weakReference.get()) != null) {
            return view;
        }
        View viewInflate = ((LayoutInflater) Utils.getApp().getSystemService("layout_inflater")).inflate(i2, (ViewGroup) null);
        sViewWeakReference = new WeakReference<>(viewInflate);
        sLayoutId = i2;
        return viewInflate;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void setBgAndGravity() {
        View view = sToast.getView();
        if (view != null) {
            int i2 = bgResource;
            if (i2 != -1) {
                view.setBackgroundResource(i2);
            } else if (bgColor != COLOR_DEFAULT) {
                Drawable background = view.getBackground();
                if (background != null) {
                    background.setColorFilter(new PorterDuffColorFilter(bgColor, PorterDuff.Mode.SRC_IN));
                } else {
                    ViewCompat.setBackground(view, new ColorDrawable(bgColor));
                }
            }
        }
        sToast.setGravity(gravity, xOffset, yOffset);
    }

    public static void setBgColor(@ColorInt int i2) {
        bgColor = i2;
    }

    public static void setBgResource(@DrawableRes int i2) {
        bgResource = i2;
    }

    public static void setGravity(int i2, int i3, int i4) {
        gravity = i2;
        xOffset = i3;
        yOffset = i4;
    }

    public static void setMsgColor(@ColorInt int i2) {
        msgColor = i2;
    }

    private static void show(@StringRes int i2, int i3) {
        show(Utils.getApp().getResources().getText(i2).toString(), i3);
    }

    public static View showCustomLong(@LayoutRes int i2) {
        View view = getView(i2);
        show(view, 1);
        return view;
    }

    public static View showCustomShort(@LayoutRes int i2) {
        View view = getView(i2);
        show(view, 0);
        return view;
    }

    public static void showLong(@NonNull CharSequence charSequence) {
        show(charSequence, 1);
    }

    public static void showShort(@NonNull CharSequence charSequence) {
        show(charSequence, 0);
    }

    private static void show(@StringRes int i2, int i3, Object... objArr) {
        show(String.format(Utils.getApp().getResources().getString(i2), objArr), i3);
    }

    public static void showLong(@StringRes int i2) {
        show(i2, 1);
    }

    public static void showShort(@StringRes int i2) {
        show(i2, 0);
    }

    private static void show(String str, int i2, Object... objArr) {
        show(String.format(str, objArr), i2);
    }

    public static void showLong(@StringRes int i2, Object... objArr) {
        show(i2, 1, objArr);
    }

    public static void showShort(@StringRes int i2, Object... objArr) {
        show(i2, 0, objArr);
    }

    private static void show(final CharSequence charSequence, final int i2) {
        HANDLER.post(new Runnable() { // from class: com.plv.thirdpart.blankj.utilcode.util.ToastUtils.1
            @Override // java.lang.Runnable
            public void run() {
                ToastUtils.cancel();
                Toast unused = ToastUtils.sToast = Toast.makeText(Utils.getApp(), charSequence, i2);
                View view = ToastUtils.sToast.getView();
                if (view != null) {
                    TextView textView = (TextView) view.findViewById(R.id.message);
                    if (ToastUtils.msgColor != ToastUtils.COLOR_DEFAULT) {
                        textView.setTextColor(ToastUtils.msgColor);
                    }
                }
                ToastUtils.setBgAndGravity();
                ToastUtils.sToast.show();
            }
        });
    }

    public static void showLong(String str, Object... objArr) {
        show(str, 1, objArr);
    }

    public static void showShort(String str, Object... objArr) {
        show(str, 0, objArr);
    }

    private static void show(final View view, final int i2) {
        HANDLER.post(new Runnable() { // from class: com.plv.thirdpart.blankj.utilcode.util.ToastUtils.2
            @Override // java.lang.Runnable
            public void run() {
                ToastUtils.cancel();
                Toast unused = ToastUtils.sToast = new Toast(Utils.getApp());
                ToastUtils.sToast.setView(view);
                ToastUtils.sToast.setDuration(i2);
                ToastUtils.setBgAndGravity();
                ToastUtils.sToast.show();
            }
        });
    }
}
