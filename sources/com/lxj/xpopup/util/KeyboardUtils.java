package com.lxj.xpopup.util;

import android.R;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import com.lxj.xpopup.core.BasePopupView;

/* loaded from: classes4.dex */
public final class KeyboardUtils {
    private static final SparseArray<ViewTreeObserver.OnGlobalLayoutListener> listenerArray = new SparseArray<>();
    private static int sDecorViewDelta = 0;
    public static int sDecorViewInvisibleHeightPre;

    public interface OnSoftInputChangedListener {
        void onSoftInputChanged(int i2);
    }

    public static class SoftInputReceiver extends ResultReceiver {
        private Context context;

        public SoftInputReceiver(Context context) {
            super(new Handler());
            this.context = context;
        }

        @Override // android.os.ResultReceiver
        public void onReceiveResult(int i2, Bundle bundle) {
            super.onReceiveResult(i2, bundle);
            if (i2 == 1 || i2 == 3) {
                KeyboardUtils.toggleSoftInput(this.context);
            }
            this.context = null;
        }
    }

    private KeyboardUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int getDecorViewInvisibleHeight(Window window) {
        View decorView = window.getDecorView();
        Rect rect = new Rect();
        decorView.getWindowVisibleDisplayFrame(rect);
        Log.d("KeyboardUtils", "getDecorViewInvisibleHeight: " + (decorView.getBottom() - rect.bottom));
        int iAbs = Math.abs(decorView.getBottom() - rect.bottom);
        if (iAbs > XPopupUtils.getNavBarHeight() + XPopupUtils.getStatusBarHeight()) {
            return iAbs - sDecorViewDelta;
        }
        sDecorViewDelta = iAbs;
        return 0;
    }

    public static void hideSoftInput(View view) {
        ((InputMethodManager) view.getContext().getSystemService("input_method")).hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void registerSoftInputChangedListener(final Window window, BasePopupView basePopupView, final OnSoftInputChangedListener onSoftInputChangedListener) {
        if ((window.getAttributes().flags & 512) != 0) {
            window.clearFlags(512);
        }
        FrameLayout frameLayout = (FrameLayout) window.findViewById(R.id.content);
        final int[] iArr = {getDecorViewInvisibleHeight(window)};
        ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.lxj.xpopup.util.KeyboardUtils.1
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public void onGlobalLayout() {
                int decorViewInvisibleHeight = KeyboardUtils.getDecorViewInvisibleHeight(window);
                if (iArr[0] != decorViewInvisibleHeight) {
                    onSoftInputChangedListener.onSoftInputChanged(decorViewInvisibleHeight);
                    iArr[0] = decorViewInvisibleHeight;
                }
            }
        };
        frameLayout.getViewTreeObserver().addOnGlobalLayoutListener(onGlobalLayoutListener);
        listenerArray.append(basePopupView.getId(), onGlobalLayoutListener);
    }

    public static void removeLayoutChangeListener(Window window, BasePopupView basePopupView) {
        SparseArray<ViewTreeObserver.OnGlobalLayoutListener> sparseArray;
        ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener;
        View viewFindViewById = window.findViewById(R.id.content);
        if (viewFindViewById == null || (onGlobalLayoutListener = (sparseArray = listenerArray).get(basePopupView.getId())) == null) {
            return;
        }
        viewFindViewById.getViewTreeObserver().removeOnGlobalLayoutListener(onGlobalLayoutListener);
        sparseArray.remove(basePopupView.getId());
    }

    public static void showSoftInput(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) view.getContext().getSystemService("input_method");
        if (inputMethodManager == null) {
            return;
        }
        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        inputMethodManager.showSoftInput(view, 0, new SoftInputReceiver(view.getContext()));
        inputMethodManager.toggleSoftInput(2, 1);
    }

    public static void toggleSoftInput(Context context) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService("input_method");
        if (inputMethodManager == null) {
            return;
        }
        inputMethodManager.toggleSoftInput(0, 0);
    }

    public static void hideSoftInput(@NonNull Window window) {
        View currentFocus = window.getCurrentFocus();
        if (currentFocus == null) {
            View decorView = window.getDecorView();
            View viewFindViewWithTag = decorView.findViewWithTag("keyboardTagView");
            if (viewFindViewWithTag == null) {
                viewFindViewWithTag = new EditText(window.getContext());
                viewFindViewWithTag.setTag("keyboardTagView");
                ((ViewGroup) decorView).addView(viewFindViewWithTag, 0, 0);
            }
            currentFocus = viewFindViewWithTag;
            currentFocus.requestFocus();
        }
        hideSoftInput(currentFocus);
    }
}
