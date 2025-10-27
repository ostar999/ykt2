package com.hyphenate.easeui.ui.base;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.hyphenate.easeui.manager.EaseThreadManager;

/* loaded from: classes4.dex */
public class EaseBaseFragment extends Fragment {
    public Activity mContext;
    public boolean onClickBackPress;

    public <T extends View> T findViewById(@IdRes int i2) {
        return (T) requireView().findViewById(i2);
    }

    public void hideKeyboard() {
        InputMethodManager inputMethodManager;
        if (getActivity().getWindow().getAttributes().softInputMode == 2 || getActivity().getCurrentFocus() == null || (inputMethodManager = (InputMethodManager) getActivity().getSystemService("input_method")) == null) {
            return;
        }
        inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 2);
    }

    public boolean isActivityDisable() {
        Activity activity = this.mContext;
        return activity == null || activity.isFinishing();
    }

    @Override // androidx.fragment.app.Fragment
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.mContext = (Activity) context;
    }

    public void onBackPress() {
        this.onClickBackPress = true;
        this.mContext.onBackPressed();
    }

    public void runOnUiThread(Runnable runnable) {
        EaseThreadManager.getInstance().runOnMainThread(runnable);
    }
}
