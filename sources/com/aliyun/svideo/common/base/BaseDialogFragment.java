package com.aliyun.svideo.common.base;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

/* loaded from: classes2.dex */
public abstract class BaseDialogFragment extends DialogFragment {
    private static final float DEFAULT_DIMAMOUNT = 0.2f;
    protected String tag = getClass().getSimpleName();

    public static final int getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    public static final int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    public abstract void bindView(View view);

    public int getDialogAnimationRes() {
        return 0;
    }

    public int getDialogHeight() {
        return -2;
    }

    public int getDialogWidth() {
        return -1;
    }

    public float getDimAmount() {
        return 0.2f;
    }

    public String getFragmentTag() {
        return this.tag;
    }

    public int getGravity() {
        return 80;
    }

    public abstract int getLayoutRes();

    public DialogInterface.OnKeyListener getOnKeyListener() {
        return null;
    }

    public boolean isCancelableOutside() {
        return true;
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        getDialog().requestWindowFeature(1);
        View viewInflate = getLayoutRes() > 0 ? layoutInflater.inflate(getLayoutRes(), viewGroup, false) : null;
        bindView(viewInflate);
        return viewInflate;
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        if (window != null) {
            window.setBackgroundDrawable(new ColorDrawable(0));
            WindowManager.LayoutParams attributes = window.getAttributes();
            if (getDialogWidth() > 0) {
                attributes.width = getDialogWidth();
            } else {
                attributes.width = -1;
            }
            if (getDialogHeight() > 0) {
                attributes.height = getDialogHeight();
            } else {
                attributes.height = -2;
            }
            attributes.dimAmount = getDimAmount();
            attributes.gravity = getGravity();
            window.setAttributes(attributes);
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onViewCreated(View view, @Nullable Bundle bundle) {
        super.onViewCreated(view, bundle);
        Dialog dialog = getDialog();
        dialog.setCanceledOnTouchOutside(isCancelableOutside());
        if (dialog.getWindow() != null && getDialogAnimationRes() > 0) {
            dialog.getWindow().setWindowAnimations(getDialogAnimationRes());
        }
        if (getOnKeyListener() != null) {
            dialog.setOnKeyListener(getOnKeyListener());
        }
    }

    public void show(FragmentManager fragmentManager) {
        show(fragmentManager, getFragmentTag());
    }
}
