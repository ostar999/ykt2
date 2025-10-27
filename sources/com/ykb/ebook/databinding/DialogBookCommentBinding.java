package com.ykb.ebook.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.ruffian.library.widget.REditText;
import com.ruffian.library.widget.RImageView;
import com.ruffian.library.widget.RTextView;
import com.ykb.ebook.R;

/* loaded from: classes6.dex */
public final class DialogBookCommentBinding implements ViewBinding {

    @NonNull
    public final REditText etInput;

    @NonNull
    public final FrameLayout flImage;

    @NonNull
    public final RImageView imgAlbum;

    @NonNull
    public final ImageView imgChoose;

    @NonNull
    public final ImageView imgDelete;

    @NonNull
    public final ImageView imgFullScreen;

    @NonNull
    public final LinearLayout lyContent;

    @NonNull
    public final LinearLayout lyContentView;

    @NonNull
    private final LinearLayout rootView;

    @NonNull
    public final TextView tvCancel;

    @NonNull
    public final RTextView tvHigh;

    @NonNull
    public final RTextView tvLow;

    @NonNull
    public final RTextView tvMiddle;

    @NonNull
    public final TextView tvPub;

    @NonNull
    public final RTextView tvPublish;

    @NonNull
    public final TextView tvTitle;

    @NonNull
    public final View viewLine1;

    private DialogBookCommentBinding(@NonNull LinearLayout linearLayout, @NonNull REditText rEditText, @NonNull FrameLayout frameLayout, @NonNull RImageView rImageView, @NonNull ImageView imageView, @NonNull ImageView imageView2, @NonNull ImageView imageView3, @NonNull LinearLayout linearLayout2, @NonNull LinearLayout linearLayout3, @NonNull TextView textView, @NonNull RTextView rTextView, @NonNull RTextView rTextView2, @NonNull RTextView rTextView3, @NonNull TextView textView2, @NonNull RTextView rTextView4, @NonNull TextView textView3, @NonNull View view) {
        this.rootView = linearLayout;
        this.etInput = rEditText;
        this.flImage = frameLayout;
        this.imgAlbum = rImageView;
        this.imgChoose = imageView;
        this.imgDelete = imageView2;
        this.imgFullScreen = imageView3;
        this.lyContent = linearLayout2;
        this.lyContentView = linearLayout3;
        this.tvCancel = textView;
        this.tvHigh = rTextView;
        this.tvLow = rTextView2;
        this.tvMiddle = rTextView3;
        this.tvPub = textView2;
        this.tvPublish = rTextView4;
        this.tvTitle = textView3;
        this.viewLine1 = view;
    }

    @NonNull
    public static DialogBookCommentBinding bind(@NonNull View view) {
        View viewFindChildViewById;
        int i2 = R.id.et_input;
        REditText rEditText = (REditText) ViewBindings.findChildViewById(view, i2);
        if (rEditText != null) {
            i2 = R.id.fl_image;
            FrameLayout frameLayout = (FrameLayout) ViewBindings.findChildViewById(view, i2);
            if (frameLayout != null) {
                i2 = R.id.img_album;
                RImageView rImageView = (RImageView) ViewBindings.findChildViewById(view, i2);
                if (rImageView != null) {
                    i2 = R.id.img_choose;
                    ImageView imageView = (ImageView) ViewBindings.findChildViewById(view, i2);
                    if (imageView != null) {
                        i2 = R.id.img_delete;
                        ImageView imageView2 = (ImageView) ViewBindings.findChildViewById(view, i2);
                        if (imageView2 != null) {
                            i2 = R.id.img_full_screen;
                            ImageView imageView3 = (ImageView) ViewBindings.findChildViewById(view, i2);
                            if (imageView3 != null) {
                                i2 = R.id.ly_content;
                                LinearLayout linearLayout = (LinearLayout) ViewBindings.findChildViewById(view, i2);
                                if (linearLayout != null) {
                                    LinearLayout linearLayout2 = (LinearLayout) view;
                                    i2 = R.id.tv_cancel;
                                    TextView textView = (TextView) ViewBindings.findChildViewById(view, i2);
                                    if (textView != null) {
                                        i2 = R.id.tv_high;
                                        RTextView rTextView = (RTextView) ViewBindings.findChildViewById(view, i2);
                                        if (rTextView != null) {
                                            i2 = R.id.tv_low;
                                            RTextView rTextView2 = (RTextView) ViewBindings.findChildViewById(view, i2);
                                            if (rTextView2 != null) {
                                                i2 = R.id.tv_middle;
                                                RTextView rTextView3 = (RTextView) ViewBindings.findChildViewById(view, i2);
                                                if (rTextView3 != null) {
                                                    i2 = R.id.tv_pub;
                                                    TextView textView2 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                    if (textView2 != null) {
                                                        i2 = R.id.tv_publish;
                                                        RTextView rTextView4 = (RTextView) ViewBindings.findChildViewById(view, i2);
                                                        if (rTextView4 != null) {
                                                            i2 = R.id.tv_title;
                                                            TextView textView3 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                            if (textView3 != null && (viewFindChildViewById = ViewBindings.findChildViewById(view, (i2 = R.id.viewLine1))) != null) {
                                                                return new DialogBookCommentBinding(linearLayout2, rEditText, frameLayout, rImageView, imageView, imageView2, imageView3, linearLayout, linearLayout2, textView, rTextView, rTextView2, rTextView3, textView2, rTextView4, textView3, viewFindChildViewById);
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
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i2)));
    }

    @NonNull
    public static DialogBookCommentBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    @NonNull
    public static DialogBookCommentBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z2) {
        View viewInflate = layoutInflater.inflate(R.layout.dialog_book_comment, viewGroup, false);
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
