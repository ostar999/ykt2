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
import com.ykb.ebook.weight.FloorViews;

/* loaded from: classes6.dex */
public final class ItemReviewCommentBinding implements ViewBinding {

    @NonNull
    public final FloorViews floor;

    @NonNull
    public final RImageView imgAvatar;

    @NonNull
    public final RImageView imgPicture;

    @NonNull
    public final LinearLayout llContent;

    @NonNull
    public final LinearLayout llItemContent;

    @NonNull
    public final LinearLayout llTypeContent;

    @NonNull
    public final ImageView personalVipIv;

    @NonNull
    private final LinearLayout rootView;

    @NonNull
    public final TextView tvComment;

    @NonNull
    public final TextView tvCommentReply;

    @NonNull
    public final TextView tvHospital;

    @NonNull
    public final TextView tvNickName;

    @NonNull
    public final RTextView tvOpposition;

    @NonNull
    public final RTextView tvSupport;

    @NonNull
    public final TextView tvTime;

    @NonNull
    public final TextView tvTypeText;

    @NonNull
    public final View typeLineCustomer;

    @NonNull
    public final View vLine;

    private ItemReviewCommentBinding(@NonNull LinearLayout linearLayout, @NonNull FloorViews floorViews, @NonNull RImageView rImageView, @NonNull RImageView rImageView2, @NonNull LinearLayout linearLayout2, @NonNull LinearLayout linearLayout3, @NonNull LinearLayout linearLayout4, @NonNull ImageView imageView, @NonNull TextView textView, @NonNull TextView textView2, @NonNull TextView textView3, @NonNull TextView textView4, @NonNull RTextView rTextView, @NonNull RTextView rTextView2, @NonNull TextView textView5, @NonNull TextView textView6, @NonNull View view, @NonNull View view2) {
        this.rootView = linearLayout;
        this.floor = floorViews;
        this.imgAvatar = rImageView;
        this.imgPicture = rImageView2;
        this.llContent = linearLayout2;
        this.llItemContent = linearLayout3;
        this.llTypeContent = linearLayout4;
        this.personalVipIv = imageView;
        this.tvComment = textView;
        this.tvCommentReply = textView2;
        this.tvHospital = textView3;
        this.tvNickName = textView4;
        this.tvOpposition = rTextView;
        this.tvSupport = rTextView2;
        this.tvTime = textView5;
        this.tvTypeText = textView6;
        this.typeLineCustomer = view;
        this.vLine = view2;
    }

    @NonNull
    public static ItemReviewCommentBinding bind(@NonNull View view) {
        View viewFindChildViewById;
        View viewFindChildViewById2;
        int i2 = R.id.floor;
        FloorViews floorViews = (FloorViews) ViewBindings.findChildViewById(view, i2);
        if (floorViews != null) {
            i2 = R.id.img_avatar;
            RImageView rImageView = (RImageView) ViewBindings.findChildViewById(view, i2);
            if (rImageView != null) {
                i2 = R.id.img_picture;
                RImageView rImageView2 = (RImageView) ViewBindings.findChildViewById(view, i2);
                if (rImageView2 != null) {
                    i2 = R.id.ll_content;
                    LinearLayout linearLayout = (LinearLayout) ViewBindings.findChildViewById(view, i2);
                    if (linearLayout != null) {
                        LinearLayout linearLayout2 = (LinearLayout) view;
                        i2 = R.id.ll_type_content;
                        LinearLayout linearLayout3 = (LinearLayout) ViewBindings.findChildViewById(view, i2);
                        if (linearLayout3 != null) {
                            i2 = R.id.personalVipIv;
                            ImageView imageView = (ImageView) ViewBindings.findChildViewById(view, i2);
                            if (imageView != null) {
                                i2 = R.id.tv_comment;
                                TextView textView = (TextView) ViewBindings.findChildViewById(view, i2);
                                if (textView != null) {
                                    i2 = R.id.tv_comment_reply;
                                    TextView textView2 = (TextView) ViewBindings.findChildViewById(view, i2);
                                    if (textView2 != null) {
                                        i2 = R.id.tv_hospital;
                                        TextView textView3 = (TextView) ViewBindings.findChildViewById(view, i2);
                                        if (textView3 != null) {
                                            i2 = R.id.tv_nick_name;
                                            TextView textView4 = (TextView) ViewBindings.findChildViewById(view, i2);
                                            if (textView4 != null) {
                                                i2 = R.id.tv_opposition;
                                                RTextView rTextView = (RTextView) ViewBindings.findChildViewById(view, i2);
                                                if (rTextView != null) {
                                                    i2 = R.id.tv_support;
                                                    RTextView rTextView2 = (RTextView) ViewBindings.findChildViewById(view, i2);
                                                    if (rTextView2 != null) {
                                                        i2 = R.id.tv_time;
                                                        TextView textView5 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                        if (textView5 != null) {
                                                            i2 = R.id.tv_type_text;
                                                            TextView textView6 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                            if (textView6 != null && (viewFindChildViewById = ViewBindings.findChildViewById(view, (i2 = R.id.type_line_customer))) != null && (viewFindChildViewById2 = ViewBindings.findChildViewById(view, (i2 = R.id.v_line))) != null) {
                                                                return new ItemReviewCommentBinding(linearLayout2, floorViews, rImageView, rImageView2, linearLayout, linearLayout2, linearLayout3, imageView, textView, textView2, textView3, textView4, rTextView, rTextView2, textView5, textView6, viewFindChildViewById, viewFindChildViewById2);
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
    public static ItemReviewCommentBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    @NonNull
    public static ItemReviewCommentBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z2) {
        View viewInflate = layoutInflater.inflate(R.layout.item_review_comment, viewGroup, false);
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
