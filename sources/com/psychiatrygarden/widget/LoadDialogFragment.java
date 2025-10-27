package com.psychiatrygarden.widget;

import android.app.Dialog;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.psychiatrygarden.utils.SkinManager;
import com.yikaobang.yixue.R;
import java.lang.reflect.Field;

/* loaded from: classes6.dex */
public class LoadDialogFragment extends DialogFragment {
    public static volatile LoadDialogFragment instence;
    private String defaultTxt;
    private TextView dialog_text;
    private boolean is_cancel;
    private ImageView loadingImage;
    private View mLoadRootView;

    public LoadDialogFragment(boolean is_cancel) {
        this.is_cancel = is_cancel;
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(1, dp, getResources().getDisplayMetrics());
    }

    public static LoadDialogFragment getInstence() {
        if (instence == null) {
            synchronized (LoadDialogFragment.class) {
                if (instence == null) {
                    instence = new LoadDialogFragment();
                }
            }
        }
        return instence;
    }

    public void fiedvalue() throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
        try {
            Field declaredField = DialogFragment.class.getDeclaredField("mDismissed");
            declaredField.setAccessible(true);
            declaredField.set(this, Boolean.FALSE);
            Field declaredField2 = DialogFragment.class.getDeclaredField("mShownByMe");
            declaredField2.setAccessible(true);
            declaredField2.set(this, Boolean.TRUE);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override // androidx.fragment.app.DialogFragment
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialogOnCreateDialog = super.onCreateDialog(savedInstanceState);
        dialogOnCreateDialog.getWindow().setSoftInputMode(4);
        return dialogOnCreateDialog;
    }

    @Override // androidx.fragment.app.Fragment
    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().setCancelable(this.is_cancel);
        getDialog().setCanceledOnTouchOutside(this.is_cancel);
        View viewOnCreateView = super.onCreateView(inflater, container, savedInstanceState);
        this.mLoadRootView = viewOnCreateView;
        if (viewOnCreateView == null) {
            this.mLoadRootView = inflater.inflate(R.layout.dialog_download_progress, container, false);
        }
        this.dialog_text = (TextView) this.mLoadRootView.findViewById(R.id.dialog_text);
        ImageView imageView = (ImageView) this.mLoadRootView.findViewById(R.id.progress_wheel_download);
        this.loadingImage = imageView;
        imageView.setBackgroundResource(SkinManager.getCurrentSkinType(getContext()) == 1 ? R.drawable.loading_night : R.drawable.loading);
        if (!TextUtils.isEmpty(this.defaultTxt)) {
            this.dialog_text.setText(this.defaultTxt);
        }
        AnimationDrawable animationDrawable = (AnimationDrawable) this.loadingImage.getBackground();
        if (animationDrawable != null) {
            animationDrawable.start();
        }
        return this.mLoadRootView;
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        window.setBackgroundDrawable(new ColorDrawable(0));
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.dimAmount = 0.0f;
        window.setLayout(-2, -2);
        window.setLayout(-1, -1);
        attributes.gravity = 17;
        window.setAttributes(attributes);
    }

    public void setDialog_text(String text) {
        TextView textView = this.dialog_text;
        if (textView != null) {
            textView.setText(text);
        }
    }

    @Override // androidx.fragment.app.DialogFragment
    public void show(FragmentManager manager, String tag) {
        try {
            manager.beginTransaction().remove(this).commitAllowingStateLoss();
            fiedvalue();
            FragmentTransaction fragmentTransactionBeginTransaction = manager.beginTransaction();
            fragmentTransactionBeginTransaction.add(this, tag);
            fragmentTransactionBeginTransaction.commitAllowingStateLoss();
            manager.executePendingTransactions();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public LoadDialogFragment() {
        this.is_cancel = true;
    }

    public LoadDialogFragment(String txt) {
        this.is_cancel = true;
        this.defaultTxt = txt;
    }
}
