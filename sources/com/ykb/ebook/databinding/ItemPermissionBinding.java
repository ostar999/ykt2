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
public final class ItemPermissionBinding implements ViewBinding {

    @NonNull
    public final LinearLayout fenxiangLine;

    @NonNull
    public final ImageView leftimg;

    @NonNull
    public final TextView mDofristTxt;

    @NonNull
    public final TextView mfristTxt;

    @NonNull
    public final ImageView rightimg;

    @NonNull
    private final LinearLayout rootView;

    @NonNull
    public final RTextView tvToLock;

    @NonNull
    public final View vBottom;

    private ItemPermissionBinding(@NonNull LinearLayout linearLayout, @NonNull LinearLayout linearLayout2, @NonNull ImageView imageView, @NonNull TextView textView, @NonNull TextView textView2, @NonNull ImageView imageView2, @NonNull RTextView rTextView, @NonNull View view) {
        this.rootView = linearLayout;
        this.fenxiangLine = linearLayout2;
        this.leftimg = imageView;
        this.mDofristTxt = textView;
        this.mfristTxt = textView2;
        this.rightimg = imageView2;
        this.tvToLock = rTextView;
        this.vBottom = view;
    }

    @NonNull
    public static ItemPermissionBinding bind(@NonNull View view) {
        View viewFindChildViewById;
        int i2 = R.id.fenxiang_line;
        LinearLayout linearLayout = (LinearLayout) ViewBindings.findChildViewById(view, i2);
        if (linearLayout != null) {
            i2 = R.id.leftimg;
            ImageView imageView = (ImageView) ViewBindings.findChildViewById(view, i2);
            if (imageView != null) {
                i2 = R.id.mDofristTxt;
                TextView textView = (TextView) ViewBindings.findChildViewById(view, i2);
                if (textView != null) {
                    i2 = R.id.mfristTxt;
                    TextView textView2 = (TextView) ViewBindings.findChildViewById(view, i2);
                    if (textView2 != null) {
                        i2 = R.id.rightimg;
                        ImageView imageView2 = (ImageView) ViewBindings.findChildViewById(view, i2);
                        if (imageView2 != null) {
                            i2 = R.id.tv_to_lock;
                            RTextView rTextView = (RTextView) ViewBindings.findChildViewById(view, i2);
                            if (rTextView != null && (viewFindChildViewById = ViewBindings.findChildViewById(view, (i2 = R.id.v_bottom))) != null) {
                                return new ItemPermissionBinding((LinearLayout) view, linearLayout, imageView, textView, textView2, imageView2, rTextView, viewFindChildViewById);
                            }
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i2)));
    }

    @NonNull
    public static ItemPermissionBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    @NonNull
    public static ItemPermissionBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z2) {
        View viewInflate = layoutInflater.inflate(R.layout.item_permission, viewGroup, false);
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
