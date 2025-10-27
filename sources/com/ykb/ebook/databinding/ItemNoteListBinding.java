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
import com.ruffian.library.widget.RImageView;
import com.ruffian.library.widget.RTextView;
import com.ykb.ebook.R;

/* loaded from: classes6.dex */
public final class ItemNoteListBinding implements ViewBinding {

    @NonNull
    public final RImageView imgAvatar;

    @NonNull
    public final ImageView imgDelete;

    @NonNull
    public final ImageView imgEdit;

    @NonNull
    public final ImageView personalVipIv;

    @NonNull
    private final LinearLayout rootView;

    @NonNull
    public final TextView tvComment;

    @NonNull
    public final TextView tvCreateTime;

    @NonNull
    public final TextView tvHospital;

    @NonNull
    public final TextView tvNickName;

    @NonNull
    public final RTextView tvPermission;

    @NonNull
    public final TextView tvTime;

    @NonNull
    public final View vLine;

    private ItemNoteListBinding(@NonNull LinearLayout linearLayout, @NonNull RImageView rImageView, @NonNull ImageView imageView, @NonNull ImageView imageView2, @NonNull ImageView imageView3, @NonNull TextView textView, @NonNull TextView textView2, @NonNull TextView textView3, @NonNull TextView textView4, @NonNull RTextView rTextView, @NonNull TextView textView5, @NonNull View view) {
        this.rootView = linearLayout;
        this.imgAvatar = rImageView;
        this.imgDelete = imageView;
        this.imgEdit = imageView2;
        this.personalVipIv = imageView3;
        this.tvComment = textView;
        this.tvCreateTime = textView2;
        this.tvHospital = textView3;
        this.tvNickName = textView4;
        this.tvPermission = rTextView;
        this.tvTime = textView5;
        this.vLine = view;
    }

    @NonNull
    public static ItemNoteListBinding bind(@NonNull View view) {
        View viewFindChildViewById;
        int i2 = R.id.img_avatar;
        RImageView rImageView = (RImageView) ViewBindings.findChildViewById(view, i2);
        if (rImageView != null) {
            i2 = R.id.img_delete;
            ImageView imageView = (ImageView) ViewBindings.findChildViewById(view, i2);
            if (imageView != null) {
                i2 = R.id.img_edit;
                ImageView imageView2 = (ImageView) ViewBindings.findChildViewById(view, i2);
                if (imageView2 != null) {
                    i2 = R.id.personalVipIv;
                    ImageView imageView3 = (ImageView) ViewBindings.findChildViewById(view, i2);
                    if (imageView3 != null) {
                        i2 = R.id.tv_comment;
                        TextView textView = (TextView) ViewBindings.findChildViewById(view, i2);
                        if (textView != null) {
                            i2 = R.id.tv_create_time;
                            TextView textView2 = (TextView) ViewBindings.findChildViewById(view, i2);
                            if (textView2 != null) {
                                i2 = R.id.tv_hospital;
                                TextView textView3 = (TextView) ViewBindings.findChildViewById(view, i2);
                                if (textView3 != null) {
                                    i2 = R.id.tv_nick_name;
                                    TextView textView4 = (TextView) ViewBindings.findChildViewById(view, i2);
                                    if (textView4 != null) {
                                        i2 = R.id.tv_permission;
                                        RTextView rTextView = (RTextView) ViewBindings.findChildViewById(view, i2);
                                        if (rTextView != null) {
                                            i2 = R.id.tv_time;
                                            TextView textView5 = (TextView) ViewBindings.findChildViewById(view, i2);
                                            if (textView5 != null && (viewFindChildViewById = ViewBindings.findChildViewById(view, (i2 = R.id.v_line))) != null) {
                                                return new ItemNoteListBinding((LinearLayout) view, rImageView, imageView, imageView2, imageView3, textView, textView2, textView3, textView4, rTextView, textView5, viewFindChildViewById);
                                            }
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
    public static ItemNoteListBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    @NonNull
    public static ItemNoteListBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z2) {
        View viewInflate = layoutInflater.inflate(R.layout.item_note_list, viewGroup, false);
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
