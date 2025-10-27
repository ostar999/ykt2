package com.ykb.ebook.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
public final class ItemBookReviewBinding implements ViewBinding {

    @NonNull
    public final FloorViews floor;

    @NonNull
    public final RImageView imgAvatar;

    @NonNull
    public final RImageView imgPicture;

    @NonNull
    public final LinearLayout lyContentView;

    @NonNull
    public final LinearLayout lyLookMore;

    @NonNull
    public final LinearLayout lyTitleView;

    @NonNull
    private final LinearLayout rootView;

    @NonNull
    public final TextView tvComment;

    @NonNull
    public final TextView tvHospital;

    @NonNull
    public final TextView tvMoreValue;

    @NonNull
    public final TextView tvNewestReview;

    @NonNull
    public final TextView tvNickName;

    @NonNull
    public final RTextView tvOpposition;

    @NonNull
    public final RTextView tvReply;

    @NonNull
    public final RTextView tvSupport;

    @NonNull
    public final TextView tvTime;

    @NonNull
    public final View vLine;

    @NonNull
    public final View viewLineOne;

    private ItemBookReviewBinding(@NonNull LinearLayout linearLayout, @NonNull FloorViews floorViews, @NonNull RImageView rImageView, @NonNull RImageView rImageView2, @NonNull LinearLayout linearLayout2, @NonNull LinearLayout linearLayout3, @NonNull LinearLayout linearLayout4, @NonNull TextView textView, @NonNull TextView textView2, @NonNull TextView textView3, @NonNull TextView textView4, @NonNull TextView textView5, @NonNull RTextView rTextView, @NonNull RTextView rTextView2, @NonNull RTextView rTextView3, @NonNull TextView textView6, @NonNull View view, @NonNull View view2) {
        this.rootView = linearLayout;
        this.floor = floorViews;
        this.imgAvatar = rImageView;
        this.imgPicture = rImageView2;
        this.lyContentView = linearLayout2;
        this.lyLookMore = linearLayout3;
        this.lyTitleView = linearLayout4;
        this.tvComment = textView;
        this.tvHospital = textView2;
        this.tvMoreValue = textView3;
        this.tvNewestReview = textView4;
        this.tvNickName = textView5;
        this.tvOpposition = rTextView;
        this.tvReply = rTextView2;
        this.tvSupport = rTextView3;
        this.tvTime = textView6;
        this.vLine = view;
        this.viewLineOne = view2;
    }

    @NonNull
    public static ItemBookReviewBinding bind(@NonNull View view) {
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
                    i2 = R.id.ly_content_view;
                    LinearLayout linearLayout = (LinearLayout) ViewBindings.findChildViewById(view, i2);
                    if (linearLayout != null) {
                        i2 = R.id.ly_look_more;
                        LinearLayout linearLayout2 = (LinearLayout) ViewBindings.findChildViewById(view, i2);
                        if (linearLayout2 != null) {
                            i2 = R.id.ly_title_view;
                            LinearLayout linearLayout3 = (LinearLayout) ViewBindings.findChildViewById(view, i2);
                            if (linearLayout3 != null) {
                                i2 = R.id.tv_comment;
                                TextView textView = (TextView) ViewBindings.findChildViewById(view, i2);
                                if (textView != null) {
                                    i2 = R.id.tv_hospital;
                                    TextView textView2 = (TextView) ViewBindings.findChildViewById(view, i2);
                                    if (textView2 != null) {
                                        i2 = R.id.tv_more_value;
                                        TextView textView3 = (TextView) ViewBindings.findChildViewById(view, i2);
                                        if (textView3 != null) {
                                            i2 = R.id.tv_newest_review;
                                            TextView textView4 = (TextView) ViewBindings.findChildViewById(view, i2);
                                            if (textView4 != null) {
                                                i2 = R.id.tv_nick_name;
                                                TextView textView5 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                if (textView5 != null) {
                                                    i2 = R.id.tv_opposition;
                                                    RTextView rTextView = (RTextView) ViewBindings.findChildViewById(view, i2);
                                                    if (rTextView != null) {
                                                        i2 = R.id.tv_reply;
                                                        RTextView rTextView2 = (RTextView) ViewBindings.findChildViewById(view, i2);
                                                        if (rTextView2 != null) {
                                                            i2 = R.id.tv_support;
                                                            RTextView rTextView3 = (RTextView) ViewBindings.findChildViewById(view, i2);
                                                            if (rTextView3 != null) {
                                                                i2 = R.id.tv_time;
                                                                TextView textView6 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                                if (textView6 != null && (viewFindChildViewById = ViewBindings.findChildViewById(view, (i2 = R.id.v_line))) != null && (viewFindChildViewById2 = ViewBindings.findChildViewById(view, (i2 = R.id.viewLineOne))) != null) {
                                                                    return new ItemBookReviewBinding((LinearLayout) view, floorViews, rImageView, rImageView2, linearLayout, linearLayout2, linearLayout3, textView, textView2, textView3, textView4, textView5, rTextView, rTextView2, rTextView3, textView6, viewFindChildViewById, viewFindChildViewById2);
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
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i2)));
    }

    @NonNull
    public static ItemBookReviewBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    @NonNull
    public static ItemBookReviewBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z2) {
        View viewInflate = layoutInflater.inflate(R.layout.item_book_review, viewGroup, false);
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
