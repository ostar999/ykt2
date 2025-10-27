package com.blankj.utilcode.util;

import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.IntRange;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.snackbar.Snackbar;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;

/* loaded from: classes2.dex */
public final class SnackbarUtils {
    private static final int COLOR_DEFAULT = -16777217;
    private static final int COLOR_ERROR = -65536;
    private static final int COLOR_MESSAGE = -1;
    private static final int COLOR_SUCCESS = -13912576;
    private static final int COLOR_WARNING = -16128;
    public static final int LENGTH_INDEFINITE = -2;
    public static final int LENGTH_LONG = 0;
    public static final int LENGTH_SHORT = -1;
    private static WeakReference<Snackbar> sWeakSnackbar;
    private View.OnClickListener actionListener;
    private CharSequence actionText;
    private int actionTextColor;
    private int bgColor;
    private int bgResource;
    private int bottomMargin;
    private int duration;
    private CharSequence message;
    private int messageColor;
    private View view;

    @Retention(RetentionPolicy.SOURCE)
    public @interface Duration {
    }

    private SnackbarUtils(View view) {
        setDefault();
        this.view = view;
    }

    public static void addView(@LayoutRes int i2, @NonNull ViewGroup.LayoutParams layoutParams) {
        View view = getView();
        if (view != null) {
            view.setPadding(0, 0, 0, 0);
            ((Snackbar.SnackbarLayout) view).addView(LayoutInflater.from(view.getContext()).inflate(i2, (ViewGroup) null), -1, layoutParams);
        }
    }

    public static void dismiss() {
        WeakReference<Snackbar> weakReference = sWeakSnackbar;
        if (weakReference == null || weakReference.get() == null) {
            return;
        }
        sWeakSnackbar.get().dismiss();
        sWeakSnackbar = null;
    }

    private static ViewGroup findSuitableParentCopyFromSnackbar(View view) {
        ViewGroup viewGroup = null;
        while (!(view instanceof CoordinatorLayout)) {
            if (view instanceof FrameLayout) {
                if (view.getId() == 16908290) {
                    return (ViewGroup) view;
                }
                viewGroup = (ViewGroup) view;
            }
            if (view != null) {
                Object parent = view.getParent();
                view = parent instanceof View ? (View) parent : null;
            }
            if (view == null) {
                return viewGroup;
            }
        }
        return (ViewGroup) view;
    }

    public static View getView() {
        Snackbar snackbar = sWeakSnackbar.get();
        if (snackbar == null) {
            return null;
        }
        return snackbar.getView();
    }

    private void setDefault() {
        this.message = "";
        this.messageColor = COLOR_DEFAULT;
        this.bgColor = COLOR_DEFAULT;
        this.bgResource = -1;
        this.duration = -1;
        this.actionText = "";
        this.actionTextColor = COLOR_DEFAULT;
        this.bottomMargin = 0;
    }

    public static SnackbarUtils with(@NonNull View view) {
        return new SnackbarUtils(view);
    }

    public SnackbarUtils setAction(@NonNull CharSequence charSequence, @NonNull View.OnClickListener onClickListener) {
        return setAction(charSequence, COLOR_DEFAULT, onClickListener);
    }

    public SnackbarUtils setBgColor(@ColorInt int i2) {
        this.bgColor = i2;
        return this;
    }

    public SnackbarUtils setBgResource(@DrawableRes int i2) {
        this.bgResource = i2;
        return this;
    }

    public SnackbarUtils setBottomMargin(@IntRange(from = 1) int i2) {
        this.bottomMargin = i2;
        return this;
    }

    public SnackbarUtils setDuration(int i2) {
        this.duration = i2;
        return this;
    }

    public SnackbarUtils setMessage(@NonNull CharSequence charSequence) {
        this.message = charSequence;
        return this;
    }

    public SnackbarUtils setMessageColor(@ColorInt int i2) {
        this.messageColor = i2;
        return this;
    }

    public Snackbar show() {
        return show(false);
    }

    public void showError() {
        showError(false);
    }

    public void showSuccess() {
        showSuccess(false);
    }

    public void showWarning() {
        showWarning(false);
    }

    public SnackbarUtils setAction(@NonNull CharSequence charSequence, @ColorInt int i2, @NonNull View.OnClickListener onClickListener) {
        this.actionText = charSequence;
        this.actionTextColor = i2;
        this.actionListener = onClickListener;
        return this;
    }

    public Snackbar show(boolean z2) {
        View view = this.view;
        if (view == null) {
            return null;
        }
        if (z2) {
            ViewGroup viewGroupFindSuitableParentCopyFromSnackbar = findSuitableParentCopyFromSnackbar(view);
            View viewFindViewWithTag = viewGroupFindSuitableParentCopyFromSnackbar.findViewWithTag("topSnackBarCoordinatorLayout");
            if (viewFindViewWithTag == null) {
                viewFindViewWithTag = new CoordinatorLayout(view.getContext());
                viewFindViewWithTag.setTag("topSnackBarCoordinatorLayout");
                viewFindViewWithTag.setRotation(180.0f);
                viewFindViewWithTag.setElevation(100.0f);
                viewGroupFindSuitableParentCopyFromSnackbar.addView(viewFindViewWithTag, -1, -1);
            }
            view = viewFindViewWithTag;
        }
        if (this.messageColor != COLOR_DEFAULT) {
            SpannableString spannableString = new SpannableString(this.message);
            spannableString.setSpan(new ForegroundColorSpan(this.messageColor), 0, spannableString.length(), 33);
            sWeakSnackbar = new WeakReference<>(Snackbar.make(view, spannableString, this.duration));
        } else {
            sWeakSnackbar = new WeakReference<>(Snackbar.make(view, this.message, this.duration));
        }
        Snackbar snackbar = sWeakSnackbar.get();
        Snackbar.SnackbarLayout snackbarLayout = (Snackbar.SnackbarLayout) snackbar.getView();
        if (z2) {
            for (int i2 = 0; i2 < snackbarLayout.getChildCount(); i2++) {
                snackbarLayout.getChildAt(i2).setRotation(180.0f);
            }
        }
        int i3 = this.bgResource;
        if (i3 != -1) {
            snackbarLayout.setBackgroundResource(i3);
        } else {
            int i4 = this.bgColor;
            if (i4 != COLOR_DEFAULT) {
                snackbarLayout.setBackgroundColor(i4);
            }
        }
        if (this.bottomMargin != 0) {
            ((ViewGroup.MarginLayoutParams) snackbarLayout.getLayoutParams()).bottomMargin = this.bottomMargin;
        }
        if (this.actionText.length() > 0 && this.actionListener != null) {
            int i5 = this.actionTextColor;
            if (i5 != COLOR_DEFAULT) {
                snackbar.setActionTextColor(i5);
            }
            snackbar.setAction(this.actionText, this.actionListener);
        }
        snackbar.show();
        return snackbar;
    }

    public void showError(boolean z2) {
        this.bgColor = -65536;
        this.messageColor = -1;
        this.actionTextColor = -1;
        show(z2);
    }

    public void showSuccess(boolean z2) {
        this.bgColor = COLOR_SUCCESS;
        this.messageColor = -1;
        this.actionTextColor = -1;
        show(z2);
    }

    public void showWarning(boolean z2) {
        this.bgColor = COLOR_WARNING;
        this.messageColor = -1;
        this.actionTextColor = -1;
        show(z2);
    }

    public static void addView(@NonNull View view, @NonNull ViewGroup.LayoutParams layoutParams) {
        View view2 = getView();
        if (view2 != null) {
            view2.setPadding(0, 0, 0, 0);
            ((Snackbar.SnackbarLayout) view2).addView(view, layoutParams);
        }
    }
}
