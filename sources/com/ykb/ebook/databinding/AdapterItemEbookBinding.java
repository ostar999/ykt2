package com.ykb.ebook.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.ykb.ebook.R;
import com.ykb.ebook.weight.MyExpendViews;

/* loaded from: classes6.dex */
public final class AdapterItemEbookBinding implements ViewBinding {

    @NonNull
    public final TextView idExpandTextview;

    @NonNull
    public final TextView idSourceTextview;

    @NonNull
    public final ImageView img;

    @NonNull
    public final LinearLayout lineAskArrowChild;

    @NonNull
    public final RelativeLayout relImg;

    @NonNull
    private final MyExpendViews rootView;

    @NonNull
    public final TextView txtStr;

    private AdapterItemEbookBinding(@NonNull MyExpendViews myExpendViews, @NonNull TextView textView, @NonNull TextView textView2, @NonNull ImageView imageView, @NonNull LinearLayout linearLayout, @NonNull RelativeLayout relativeLayout, @NonNull TextView textView3) {
        this.rootView = myExpendViews;
        this.idExpandTextview = textView;
        this.idSourceTextview = textView2;
        this.img = imageView;
        this.lineAskArrowChild = linearLayout;
        this.relImg = relativeLayout;
        this.txtStr = textView3;
    }

    @NonNull
    public static AdapterItemEbookBinding bind(@NonNull View view) {
        int i2 = R.id.id_expand_textview;
        TextView textView = (TextView) ViewBindings.findChildViewById(view, i2);
        if (textView != null) {
            i2 = R.id.id_source_textview;
            TextView textView2 = (TextView) ViewBindings.findChildViewById(view, i2);
            if (textView2 != null) {
                i2 = R.id.img;
                ImageView imageView = (ImageView) ViewBindings.findChildViewById(view, i2);
                if (imageView != null) {
                    i2 = R.id.line_ask_arrow_child;
                    LinearLayout linearLayout = (LinearLayout) ViewBindings.findChildViewById(view, i2);
                    if (linearLayout != null) {
                        i2 = R.id.rel_img;
                        RelativeLayout relativeLayout = (RelativeLayout) ViewBindings.findChildViewById(view, i2);
                        if (relativeLayout != null) {
                            i2 = R.id.txt_str;
                            TextView textView3 = (TextView) ViewBindings.findChildViewById(view, i2);
                            if (textView3 != null) {
                                return new AdapterItemEbookBinding((MyExpendViews) view, textView, textView2, imageView, linearLayout, relativeLayout, textView3);
                            }
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i2)));
    }

    @NonNull
    public static AdapterItemEbookBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    @NonNull
    public static AdapterItemEbookBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z2) {
        View viewInflate = layoutInflater.inflate(R.layout.adapter_item_ebook, viewGroup, false);
        if (z2) {
            viewGroup.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    @Override // androidx.viewbinding.ViewBinding
    @NonNull
    public MyExpendViews getRoot() {
        return this.rootView;
    }
}
