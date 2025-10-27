package com.ykb.ebook.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.ykb.ebook.R;

/* loaded from: classes6.dex */
public final class DialogRestTimeConfigBinding implements ViewBinding {

    @NonNull
    public final TextView btnCancel;

    @NonNull
    public final View lineView;

    @NonNull
    public final LinearLayout restTimeRll;

    @NonNull
    private final LinearLayout rootView;

    @NonNull
    public final RecyclerView rvList;

    @NonNull
    public final TextView tvTitle;

    private DialogRestTimeConfigBinding(@NonNull LinearLayout linearLayout, @NonNull TextView textView, @NonNull View view, @NonNull LinearLayout linearLayout2, @NonNull RecyclerView recyclerView, @NonNull TextView textView2) {
        this.rootView = linearLayout;
        this.btnCancel = textView;
        this.lineView = view;
        this.restTimeRll = linearLayout2;
        this.rvList = recyclerView;
        this.tvTitle = textView2;
    }

    @NonNull
    public static DialogRestTimeConfigBinding bind(@NonNull View view) {
        View viewFindChildViewById;
        int i2 = R.id.btn_cancel;
        TextView textView = (TextView) ViewBindings.findChildViewById(view, i2);
        if (textView != null && (viewFindChildViewById = ViewBindings.findChildViewById(view, (i2 = R.id.lineView))) != null) {
            LinearLayout linearLayout = (LinearLayout) view;
            i2 = R.id.rvList;
            RecyclerView recyclerView = (RecyclerView) ViewBindings.findChildViewById(view, i2);
            if (recyclerView != null) {
                i2 = R.id.tv_title;
                TextView textView2 = (TextView) ViewBindings.findChildViewById(view, i2);
                if (textView2 != null) {
                    return new DialogRestTimeConfigBinding(linearLayout, textView, viewFindChildViewById, linearLayout, recyclerView, textView2);
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i2)));
    }

    @NonNull
    public static DialogRestTimeConfigBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    @NonNull
    public static DialogRestTimeConfigBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z2) {
        View viewInflate = layoutInflater.inflate(R.layout.dialog_rest_time_config, viewGroup, false);
        if (z2) {
            viewGroup.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    @Override // androidx.viewbinding.ViewBinding
    @NonNull
    public LinearLayout getRoot() {
        return this.rootView;
    }
}
