package com.ykb.ebook.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.ykb.ebook.R;

/* loaded from: classes6.dex */
public final class FloorCommentItemBinding implements ViewBinding {

    @NonNull
    public final TextView commentListItemTvPraise;

    @NonNull
    public final FrameLayout fView;

    @NonNull
    public final ProgressBar hidePb;

    @NonNull
    public final RelativeLayout hideSubFloorContent;

    @NonNull
    public final TextView hideText;

    @NonNull
    public final ImageView imageViewPraise;

    @NonNull
    public final ImageView isverauth;

    @NonNull
    public final ImageView isvipimg;

    @NonNull
    public final LinearLayout linZan;

    @NonNull
    public final LinearLayout linetitlschool;

    @NonNull
    public final AdapterItemEbookBinding myexptervew;

    @NonNull
    private final FrameLayout rootView;

    @NonNull
    public final RelativeLayout showSubFloorContent;

    @NonNull
    public final TextView titLou;

    @NonNull
    public final TextView titName;

    @NonNull
    public final TextView titSchool;

    @NonNull
    public final TextView tvOppose;

    @NonNull
    public final TextView tvReply;

    @NonNull
    public final TextView tvSupport;

    private FloorCommentItemBinding(@NonNull FrameLayout frameLayout, @NonNull TextView textView, @NonNull FrameLayout frameLayout2, @NonNull ProgressBar progressBar, @NonNull RelativeLayout relativeLayout, @NonNull TextView textView2, @NonNull ImageView imageView, @NonNull ImageView imageView2, @NonNull ImageView imageView3, @NonNull LinearLayout linearLayout, @NonNull LinearLayout linearLayout2, @NonNull AdapterItemEbookBinding adapterItemEbookBinding, @NonNull RelativeLayout relativeLayout2, @NonNull TextView textView3, @NonNull TextView textView4, @NonNull TextView textView5, @NonNull TextView textView6, @NonNull TextView textView7, @NonNull TextView textView8) {
        this.rootView = frameLayout;
        this.commentListItemTvPraise = textView;
        this.fView = frameLayout2;
        this.hidePb = progressBar;
        this.hideSubFloorContent = relativeLayout;
        this.hideText = textView2;
        this.imageViewPraise = imageView;
        this.isverauth = imageView2;
        this.isvipimg = imageView3;
        this.linZan = linearLayout;
        this.linetitlschool = linearLayout2;
        this.myexptervew = adapterItemEbookBinding;
        this.showSubFloorContent = relativeLayout2;
        this.titLou = textView3;
        this.titName = textView4;
        this.titSchool = textView5;
        this.tvOppose = textView6;
        this.tvReply = textView7;
        this.tvSupport = textView8;
    }

    @NonNull
    public static FloorCommentItemBinding bind(@NonNull View view) {
        View viewFindChildViewById;
        int i2 = R.id.commentList_item_tv_praise;
        TextView textView = (TextView) ViewBindings.findChildViewById(view, i2);
        if (textView != null) {
            FrameLayout frameLayout = (FrameLayout) view;
            i2 = R.id.hide_pb;
            ProgressBar progressBar = (ProgressBar) ViewBindings.findChildViewById(view, i2);
            if (progressBar != null) {
                i2 = R.id.hide_sub_floor_content;
                RelativeLayout relativeLayout = (RelativeLayout) ViewBindings.findChildViewById(view, i2);
                if (relativeLayout != null) {
                    i2 = R.id.hide_text;
                    TextView textView2 = (TextView) ViewBindings.findChildViewById(view, i2);
                    if (textView2 != null) {
                        i2 = R.id.imageView_praise;
                        ImageView imageView = (ImageView) ViewBindings.findChildViewById(view, i2);
                        if (imageView != null) {
                            i2 = R.id.isverauth;
                            ImageView imageView2 = (ImageView) ViewBindings.findChildViewById(view, i2);
                            if (imageView2 != null) {
                                i2 = R.id.isvipimg;
                                ImageView imageView3 = (ImageView) ViewBindings.findChildViewById(view, i2);
                                if (imageView3 != null) {
                                    i2 = R.id.lin_zan;
                                    LinearLayout linearLayout = (LinearLayout) ViewBindings.findChildViewById(view, i2);
                                    if (linearLayout != null) {
                                        i2 = R.id.linetitlschool;
                                        LinearLayout linearLayout2 = (LinearLayout) ViewBindings.findChildViewById(view, i2);
                                        if (linearLayout2 != null && (viewFindChildViewById = ViewBindings.findChildViewById(view, (i2 = R.id.myexptervew))) != null) {
                                            AdapterItemEbookBinding adapterItemEbookBindingBind = AdapterItemEbookBinding.bind(viewFindChildViewById);
                                            i2 = R.id.show_sub_floor_content;
                                            RelativeLayout relativeLayout2 = (RelativeLayout) ViewBindings.findChildViewById(view, i2);
                                            if (relativeLayout2 != null) {
                                                i2 = R.id.tit_lou;
                                                TextView textView3 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                if (textView3 != null) {
                                                    i2 = R.id.tit_name;
                                                    TextView textView4 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                    if (textView4 != null) {
                                                        i2 = R.id.tit_school;
                                                        TextView textView5 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                        if (textView5 != null) {
                                                            i2 = R.id.tv_oppose;
                                                            TextView textView6 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                            if (textView6 != null) {
                                                                i2 = R.id.tv_reply;
                                                                TextView textView7 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                                if (textView7 != null) {
                                                                    i2 = R.id.tv_support;
                                                                    TextView textView8 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                                    if (textView8 != null) {
                                                                        return new FloorCommentItemBinding(frameLayout, textView, frameLayout, progressBar, relativeLayout, textView2, imageView, imageView2, imageView3, linearLayout, linearLayout2, adapterItemEbookBindingBind, relativeLayout2, textView3, textView4, textView5, textView6, textView7, textView8);
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
    public static FloorCommentItemBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    @NonNull
    public static FloorCommentItemBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z2) {
        View viewInflate = layoutInflater.inflate(R.layout.floor_comment_item, viewGroup, false);
        if (z2) {
            viewGroup.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    @Override // androidx.viewbinding.ViewBinding
    @NonNull
    public FrameLayout getRoot() {
        return this.rootView;
    }
}
