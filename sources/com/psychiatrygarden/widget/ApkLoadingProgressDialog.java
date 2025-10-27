package com.psychiatrygarden.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import com.psychiatrygarden.interfaceclass.DialogLoadingBtnInterface;
import com.psychiatrygarden.utils.CommonUtil;
import com.yikaobang.yixue.R;

/* loaded from: classes6.dex */
public class ApkLoadingProgressDialog extends DialogFragment {
    private Context mContext;
    DialogLoadingBtnInterface mLoadingInterface;
    private ProgressBar mProgressbar;
    private TextView mTxtAllSize;
    private TextView mTxtAlreadyLoading;
    private TextView mTxtPercentage;

    public ApkLoadingProgressDialog(Context context, DialogLoadingBtnInterface mLoadingInterface) {
        this.mContext = context;
        this.mLoadingInterface = mLoadingInterface;
    }

    private int getContextRect(Activity activity) {
        Rect rect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        return rect.height();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreateView$0(View view) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        this.mLoadingInterface.CancelBtnInterface();
    }

    public static ApkLoadingProgressDialog newInstance(Context context, DialogLoadingBtnInterface mLoadingInterface) {
        Bundle bundle = new Bundle();
        ApkLoadingProgressDialog apkLoadingProgressDialog = new ApkLoadingProgressDialog(context, mLoadingInterface);
        apkLoadingProgressDialog.setArguments(bundle);
        return apkLoadingProgressDialog;
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(1, R.style.DialogFullScreen);
    }

    @Override // androidx.fragment.app.Fragment
    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().setCancelable(false);
        getDialog().setCanceledOnTouchOutside(false);
        View viewInflate = inflater.inflate(R.layout.dialog_loading_progress, container, false);
        this.mProgressbar = (ProgressBar) viewInflate.findViewById(R.id.progressbar);
        this.mTxtPercentage = (TextView) viewInflate.findViewById(R.id.txt_percentage);
        this.mTxtAlreadyLoading = (TextView) viewInflate.findViewById(R.id.txt_already_loading);
        this.mTxtAllSize = (TextView) viewInflate.findViewById(R.id.txt_all_size);
        Button button = (Button) viewInflate.findViewById(R.id.btn_cancel_loading);
        this.mProgressbar.setIndeterminate(false);
        this.mProgressbar.setProgress(0);
        this.mProgressbar.setMax(100);
        button.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.i
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16568c.lambda$onCreateView$0(view);
            }
        });
        return viewInflate;
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.dimAmount = 0.5f;
        window.setLayout(-1, -1);
        attributes.gravity = 17;
        window.setAttributes(attributes);
    }

    public void setAllSize(String msg) {
        if (msg == null || msg.equals("")) {
            return;
        }
        this.mTxtAllSize.setText(msg);
    }

    public void setAlreadyLoading(String msg) {
        if (msg == null || msg.equals("")) {
            return;
        }
        this.mTxtAlreadyLoading.setText(msg);
    }

    public void setPercentage(String msg) {
        if (msg == null || msg.equals("")) {
            return;
        }
        this.mTxtPercentage.setText(msg);
    }

    public void setProgressbar(int progress) {
        this.mProgressbar.setProgress(progress);
    }

    @Override // androidx.fragment.app.DialogFragment
    public void show(FragmentManager manager, String tag) {
        try {
            manager.beginTransaction().remove(this).commit();
            super.show(manager, tag);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }
}
