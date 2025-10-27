package com.catchpig.mvvm.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.azhon.appupdate.view.NumberProgressBar;
import com.catchpig.mvvm.R;

/* loaded from: classes2.dex */
public final class AppUpdateDialogUpdateBinding implements ViewBinding {

    @NonNull
    public final Button btnUpdate;

    @NonNull
    public final ImageButton ibClose;

    @NonNull
    public final ImageView ivBg;

    @NonNull
    public final LinearLayout ll;

    @NonNull
    public final NumberProgressBar npBar;

    @NonNull
    private final ConstraintLayout rootView;

    @NonNull
    public final TextView titleTv;

    @NonNull
    public final TextView tvDescription;

    @NonNull
    public final TextView tvSize;

    @NonNull
    public final TextView versionTv;

    private AppUpdateDialogUpdateBinding(@NonNull ConstraintLayout constraintLayout, @NonNull Button button, @NonNull ImageButton imageButton, @NonNull ImageView imageView, @NonNull LinearLayout linearLayout, @NonNull NumberProgressBar numberProgressBar, @NonNull TextView textView, @NonNull TextView textView2, @NonNull TextView textView3, @NonNull TextView textView4) {
        this.rootView = constraintLayout;
        this.btnUpdate = button;
        this.ibClose = imageButton;
        this.ivBg = imageView;
        this.ll = linearLayout;
        this.npBar = numberProgressBar;
        this.titleTv = textView;
        this.tvDescription = textView2;
        this.tvSize = textView3;
        this.versionTv = textView4;
    }

    @NonNull
    public static AppUpdateDialogUpdateBinding bind(@NonNull View view) {
        int i2 = R.id.btn_update;
        Button button = (Button) ViewBindings.findChildViewById(view, i2);
        if (button != null) {
            i2 = R.id.ib_close;
            ImageButton imageButton = (ImageButton) ViewBindings.findChildViewById(view, i2);
            if (imageButton != null) {
                i2 = R.id.iv_bg;
                ImageView imageView = (ImageView) ViewBindings.findChildViewById(view, i2);
                if (imageView != null) {
                    i2 = R.id.ll;
                    LinearLayout linearLayout = (LinearLayout) ViewBindings.findChildViewById(view, i2);
                    if (linearLayout != null) {
                        i2 = R.id.np_bar;
                        NumberProgressBar numberProgressBar = (NumberProgressBar) ViewBindings.findChildViewById(view, i2);
                        if (numberProgressBar != null) {
                            i2 = R.id.titleTv;
                            TextView textView = (TextView) ViewBindings.findChildViewById(view, i2);
                            if (textView != null) {
                                i2 = R.id.tv_description;
                                TextView textView2 = (TextView) ViewBindings.findChildViewById(view, i2);
                                if (textView2 != null) {
                                    i2 = R.id.tv_size;
                                    TextView textView3 = (TextView) ViewBindings.findChildViewById(view, i2);
                                    if (textView3 != null) {
                                        i2 = R.id.versionTv;
                                        TextView textView4 = (TextView) ViewBindings.findChildViewById(view, i2);
                                        if (textView4 != null) {
                                            return new AppUpdateDialogUpdateBinding((ConstraintLayout) view, button, imageButton, imageView, linearLayout, numberProgressBar, textView, textView2, textView3, textView4);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i2)));
    }

    @NonNull
    public static AppUpdateDialogUpdateBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    @NonNull
    public static AppUpdateDialogUpdateBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z2) {
        View viewInflate = layoutInflater.inflate(R.layout.app_update_dialog_update, viewGroup, false);
        if (z2) {
            viewGroup.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    @Override // androidx.viewbinding.ViewBinding
    @NonNull
    public ConstraintLayout getRoot() {
        return this.rootView;
    }
}
