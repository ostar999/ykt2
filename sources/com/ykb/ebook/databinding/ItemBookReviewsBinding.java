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
import com.ruffian.library.widget.RImageView;
import com.ruffian.library.widget.RView;
import com.ykb.ebook.R;
import com.ykb.ebook.weight.FloorViews;

/* loaded from: classes6.dex */
public final class ItemBookReviewsBinding implements ViewBinding {

    @NonNull
    public final FloorViews floor;

    /* renamed from: i, reason: collision with root package name */
    @NonNull
    public final RView f26290i;

    @NonNull
    public final RImageView imgAvatar;

    @NonNull
    public final RImageView imgPicture;

    @NonNull
    public final ImageView ivReviewFace;

    @NonNull
    public final View line;

    @NonNull
    public final LinearLayout linearLayout;

    @NonNull
    public final LinearLayout lyContentView;

    @NonNull
    public final LinearLayout lyLookMore;

    @NonNull
    public final RelativeLayout lyTitleView;

    @NonNull
    public final ImageView personalVipIv;

    @NonNull
    private final RelativeLayout rootView;

    @NonNull
    public final TextView tvComment;

    @NonNull
    public final TextView tvHigh;

    @NonNull
    public final TextView tvHospital;

    @NonNull
    public final TextView tvMoreValue;

    @NonNull
    public final TextView tvNewestReview;

    @NonNull
    public final TextView tvNickName;

    @NonNull
    public final TextView tvOpposition;

    @NonNull
    public final TextView tvReply;

    @NonNull
    public final TextView tvSupport;

    @NonNull
    public final TextView tvTime;

    @NonNull
    public final TextView tvViewAll;

    @NonNull
    public final View vLine;

    private ItemBookReviewsBinding(@NonNull RelativeLayout relativeLayout, @NonNull FloorViews floorViews, @NonNull RView rView, @NonNull RImageView rImageView, @NonNull RImageView rImageView2, @NonNull ImageView imageView, @NonNull View view, @NonNull LinearLayout linearLayout, @NonNull LinearLayout linearLayout2, @NonNull LinearLayout linearLayout3, @NonNull RelativeLayout relativeLayout2, @NonNull ImageView imageView2, @NonNull TextView textView, @NonNull TextView textView2, @NonNull TextView textView3, @NonNull TextView textView4, @NonNull TextView textView5, @NonNull TextView textView6, @NonNull TextView textView7, @NonNull TextView textView8, @NonNull TextView textView9, @NonNull TextView textView10, @NonNull TextView textView11, @NonNull View view2) {
        this.rootView = relativeLayout;
        this.floor = floorViews;
        this.f26290i = rView;
        this.imgAvatar = rImageView;
        this.imgPicture = rImageView2;
        this.ivReviewFace = imageView;
        this.line = view;
        this.linearLayout = linearLayout;
        this.lyContentView = linearLayout2;
        this.lyLookMore = linearLayout3;
        this.lyTitleView = relativeLayout2;
        this.personalVipIv = imageView2;
        this.tvComment = textView;
        this.tvHigh = textView2;
        this.tvHospital = textView3;
        this.tvMoreValue = textView4;
        this.tvNewestReview = textView5;
        this.tvNickName = textView6;
        this.tvOpposition = textView7;
        this.tvReply = textView8;
        this.tvSupport = textView9;
        this.tvTime = textView10;
        this.tvViewAll = textView11;
        this.vLine = view2;
    }

    @NonNull
    public static ItemBookReviewsBinding bind(@NonNull View view) {
        View viewFindChildViewById;
        View viewFindChildViewById2;
        int i2 = R.id.floor;
        FloorViews floorViews = (FloorViews) ViewBindings.findChildViewById(view, i2);
        if (floorViews != null) {
            i2 = R.id.f26092i;
            RView rView = (RView) ViewBindings.findChildViewById(view, i2);
            if (rView != null) {
                i2 = R.id.img_avatar;
                RImageView rImageView = (RImageView) ViewBindings.findChildViewById(view, i2);
                if (rImageView != null) {
                    i2 = R.id.img_picture;
                    RImageView rImageView2 = (RImageView) ViewBindings.findChildViewById(view, i2);
                    if (rImageView2 != null) {
                        i2 = R.id.iv_review_face;
                        ImageView imageView = (ImageView) ViewBindings.findChildViewById(view, i2);
                        if (imageView != null && (viewFindChildViewById = ViewBindings.findChildViewById(view, (i2 = R.id.line))) != null) {
                            i2 = R.id.linearLayout;
                            LinearLayout linearLayout = (LinearLayout) ViewBindings.findChildViewById(view, i2);
                            if (linearLayout != null) {
                                i2 = R.id.ly_content_view;
                                LinearLayout linearLayout2 = (LinearLayout) ViewBindings.findChildViewById(view, i2);
                                if (linearLayout2 != null) {
                                    i2 = R.id.ly_look_more;
                                    LinearLayout linearLayout3 = (LinearLayout) ViewBindings.findChildViewById(view, i2);
                                    if (linearLayout3 != null) {
                                        i2 = R.id.ly_title_view;
                                        RelativeLayout relativeLayout = (RelativeLayout) ViewBindings.findChildViewById(view, i2);
                                        if (relativeLayout != null) {
                                            i2 = R.id.personalVipIv;
                                            ImageView imageView2 = (ImageView) ViewBindings.findChildViewById(view, i2);
                                            if (imageView2 != null) {
                                                i2 = R.id.tv_comment;
                                                TextView textView = (TextView) ViewBindings.findChildViewById(view, i2);
                                                if (textView != null) {
                                                    i2 = R.id.tv_high;
                                                    TextView textView2 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                    if (textView2 != null) {
                                                        i2 = R.id.tv_hospital;
                                                        TextView textView3 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                        if (textView3 != null) {
                                                            i2 = R.id.tv_more_value;
                                                            TextView textView4 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                            if (textView4 != null) {
                                                                i2 = R.id.tv_newest_review;
                                                                TextView textView5 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                                if (textView5 != null) {
                                                                    i2 = R.id.tv_nick_name;
                                                                    TextView textView6 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                                    if (textView6 != null) {
                                                                        i2 = R.id.tv_opposition;
                                                                        TextView textView7 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                                        if (textView7 != null) {
                                                                            i2 = R.id.tv_reply;
                                                                            TextView textView8 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                                            if (textView8 != null) {
                                                                                i2 = R.id.tv_support;
                                                                                TextView textView9 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                                                if (textView9 != null) {
                                                                                    i2 = R.id.tv_time;
                                                                                    TextView textView10 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                                                    if (textView10 != null) {
                                                                                        i2 = R.id.tv_view_all;
                                                                                        TextView textView11 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                                                        if (textView11 != null && (viewFindChildViewById2 = ViewBindings.findChildViewById(view, (i2 = R.id.v_line))) != null) {
                                                                                            return new ItemBookReviewsBinding((RelativeLayout) view, floorViews, rView, rImageView, rImageView2, imageView, viewFindChildViewById, linearLayout, linearLayout2, linearLayout3, relativeLayout, imageView2, textView, textView2, textView3, textView4, textView5, textView6, textView7, textView8, textView9, textView10, textView11, viewFindChildViewById2);
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
                            }
                        }
                    }
                }
            }
        }
        throw new NullPointerException("Missing required view with ID: ".concat(view.getResources().getResourceName(i2)));
    }

    @NonNull
    public static ItemBookReviewsBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    @NonNull
    public static ItemBookReviewsBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z2) {
        View viewInflate = layoutInflater.inflate(R.layout.item_book_reviews, viewGroup, false);
        if (z2) {
            viewGroup.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    @Override // androidx.viewbinding.ViewBinding
    @NonNull
    public RelativeLayout getRoot() {
        return this.rootView;
    }
}
