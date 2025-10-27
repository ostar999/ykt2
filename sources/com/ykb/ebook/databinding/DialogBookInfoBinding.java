package com.ykb.ebook.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.ruffian.library.widget.RTextView;
import com.ykb.ebook.R;

/* loaded from: classes6.dex */
public final class DialogBookInfoBinding implements ViewBinding {

    @NonNull
    public final RTextView btnToRead;

    @NonNull
    public final ImageView imgClose;

    @NonNull
    public final View line;

    @NonNull
    private final LinearLayout rootView;

    @NonNull
    public final TextView title;

    @NonNull
    public final TextView tvContent;

    private DialogBookInfoBinding(@NonNull LinearLayout linearLayout, @NonNull RTextView rTextView, @NonNull ImageView imageView, @NonNull View view, @NonNull TextView textView, @NonNull TextView textView2) {
        this.rootView = linearLayout;
        this.btnToRead = rTextView;
        this.imgClose = imageView;
        this.line = view;
        this.title = textView;
        this.tvContent = textView2;
    }

    @NonNull
    public static DialogBookInfoBinding bind(@NonNull View view) {
        View viewFindChildViewById;
        int i2 = R.id.btn_to_read;
        RTextView rTextView = (RTextView) ViewBindings.findChildViewById(view, i2);
        if (rTextView != null) {
            i2 = R.id.img_close;
            ImageView imageView = (ImageView) ViewBindings.findChildViewById(view, i2);
            if (imageView != null && (viewFindChildViewById = ViewBindings.findChildViewById(view, (i2 = R.id.line))) != null) {
                i2 = R.id.title;
                TextView textView = (TextView) ViewBindings.findChildViewById(view, i2);
                if (textView != null) {
                    i2 = R.id.tv_content;
                    TextView textView2 = (TextView) ViewBindings.findChildViewById(view, i2);
                    if (textView2 != null) {
                        return new DialogBookInfoBinding((LinearLayout) view, rTextView, imageView, viewFindChildViewById, textView, textView2);
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i2)));
    }

    @NonNull
    public static DialogBookInfoBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    @NonNull
    public static DialogBookInfoBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z2) {
        View viewInflate = layoutInflater.inflate(R.layout.dialog_book_info, viewGroup, false);
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
