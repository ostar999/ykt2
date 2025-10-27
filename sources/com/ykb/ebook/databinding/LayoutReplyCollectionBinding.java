package com.ykb.ebook.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.ruffian.library.widget.RImageView;
import com.ruffian.library.widget.RTextView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.ykb.ebook.R;

/* loaded from: classes6.dex */
public final class LayoutReplyCollectionBinding implements ViewBinding {

    @NonNull
    public final RImageView imgAvatar;

    @NonNull
    public final ImageView imgBack;

    @NonNull
    public final RImageView imgLoginAvatar;

    @NonNull
    public final RImageView imgPicture;

    @NonNull
    public final LinearLayout layoutItemRoot;

    @NonNull
    public final LinearLayout layoutRoot;

    @NonNull
    public final View line;

    @NonNull
    public final LinearLayout llWriteReply;

    @NonNull
    public final ClassicsHeader refreshHeader;

    @NonNull
    public final SmartRefreshLayout refreshLayout;

    @NonNull
    private final LinearLayout rootView;

    @NonNull
    public final RecyclerView rvNewest;

    @NonNull
    public final Toolbar toolBar;

    @NonNull
    public final TextView toolbarTitle;

    @NonNull
    public final RTextView tvAddSp;

    @NonNull
    public final TextView tvComment;

    @NonNull
    public final RTextView tvHighComment;

    @NonNull
    public final TextView tvHospital;

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
    public final View viewLine2;

    private LayoutReplyCollectionBinding(@NonNull LinearLayout linearLayout, @NonNull RImageView rImageView, @NonNull ImageView imageView, @NonNull RImageView rImageView2, @NonNull RImageView rImageView3, @NonNull LinearLayout linearLayout2, @NonNull LinearLayout linearLayout3, @NonNull View view, @NonNull LinearLayout linearLayout4, @NonNull ClassicsHeader classicsHeader, @NonNull SmartRefreshLayout smartRefreshLayout, @NonNull RecyclerView recyclerView, @NonNull Toolbar toolbar, @NonNull TextView textView, @NonNull RTextView rTextView, @NonNull TextView textView2, @NonNull RTextView rTextView2, @NonNull TextView textView3, @NonNull TextView textView4, @NonNull RTextView rTextView3, @NonNull RTextView rTextView4, @NonNull RTextView rTextView5, @NonNull TextView textView5, @NonNull View view2) {
        this.rootView = linearLayout;
        this.imgAvatar = rImageView;
        this.imgBack = imageView;
        this.imgLoginAvatar = rImageView2;
        this.imgPicture = rImageView3;
        this.layoutItemRoot = linearLayout2;
        this.layoutRoot = linearLayout3;
        this.line = view;
        this.llWriteReply = linearLayout4;
        this.refreshHeader = classicsHeader;
        this.refreshLayout = smartRefreshLayout;
        this.rvNewest = recyclerView;
        this.toolBar = toolbar;
        this.toolbarTitle = textView;
        this.tvAddSp = rTextView;
        this.tvComment = textView2;
        this.tvHighComment = rTextView2;
        this.tvHospital = textView3;
        this.tvNickName = textView4;
        this.tvOpposition = rTextView3;
        this.tvReply = rTextView4;
        this.tvSupport = rTextView5;
        this.tvTime = textView5;
        this.viewLine2 = view2;
    }

    @NonNull
    public static LayoutReplyCollectionBinding bind(@NonNull View view) {
        View viewFindChildViewById;
        int i2 = R.id.img_avatar;
        RImageView rImageView = (RImageView) ViewBindings.findChildViewById(view, i2);
        if (rImageView != null) {
            i2 = R.id.img_back;
            ImageView imageView = (ImageView) ViewBindings.findChildViewById(view, i2);
            if (imageView != null) {
                i2 = R.id.img_login_avatar;
                RImageView rImageView2 = (RImageView) ViewBindings.findChildViewById(view, i2);
                if (rImageView2 != null) {
                    i2 = R.id.img_picture;
                    RImageView rImageView3 = (RImageView) ViewBindings.findChildViewById(view, i2);
                    if (rImageView3 != null) {
                        i2 = R.id.layoutItemRoot;
                        LinearLayout linearLayout = (LinearLayout) ViewBindings.findChildViewById(view, i2);
                        if (linearLayout != null) {
                            LinearLayout linearLayout2 = (LinearLayout) view;
                            i2 = R.id.line;
                            View viewFindChildViewById2 = ViewBindings.findChildViewById(view, i2);
                            if (viewFindChildViewById2 != null) {
                                i2 = R.id.ll_write_reply;
                                LinearLayout linearLayout3 = (LinearLayout) ViewBindings.findChildViewById(view, i2);
                                if (linearLayout3 != null) {
                                    i2 = R.id.refresh_header;
                                    ClassicsHeader classicsHeader = (ClassicsHeader) ViewBindings.findChildViewById(view, i2);
                                    if (classicsHeader != null) {
                                        i2 = R.id.refresh_layout;
                                        SmartRefreshLayout smartRefreshLayout = (SmartRefreshLayout) ViewBindings.findChildViewById(view, i2);
                                        if (smartRefreshLayout != null) {
                                            i2 = R.id.rv_newest;
                                            RecyclerView recyclerView = (RecyclerView) ViewBindings.findChildViewById(view, i2);
                                            if (recyclerView != null) {
                                                i2 = R.id.tool_bar;
                                                Toolbar toolbar = (Toolbar) ViewBindings.findChildViewById(view, i2);
                                                if (toolbar != null) {
                                                    i2 = R.id.toolbar_title;
                                                    TextView textView = (TextView) ViewBindings.findChildViewById(view, i2);
                                                    if (textView != null) {
                                                        i2 = R.id.tv_add_sp;
                                                        RTextView rTextView = (RTextView) ViewBindings.findChildViewById(view, i2);
                                                        if (rTextView != null) {
                                                            i2 = R.id.tv_comment;
                                                            TextView textView2 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                            if (textView2 != null) {
                                                                i2 = R.id.tv_high_comment;
                                                                RTextView rTextView2 = (RTextView) ViewBindings.findChildViewById(view, i2);
                                                                if (rTextView2 != null) {
                                                                    i2 = R.id.tv_hospital;
                                                                    TextView textView3 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                                    if (textView3 != null) {
                                                                        i2 = R.id.tv_nick_name;
                                                                        TextView textView4 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                                        if (textView4 != null) {
                                                                            i2 = R.id.tv_opposition;
                                                                            RTextView rTextView3 = (RTextView) ViewBindings.findChildViewById(view, i2);
                                                                            if (rTextView3 != null) {
                                                                                i2 = R.id.tv_reply;
                                                                                RTextView rTextView4 = (RTextView) ViewBindings.findChildViewById(view, i2);
                                                                                if (rTextView4 != null) {
                                                                                    i2 = R.id.tv_support;
                                                                                    RTextView rTextView5 = (RTextView) ViewBindings.findChildViewById(view, i2);
                                                                                    if (rTextView5 != null) {
                                                                                        i2 = R.id.tv_time;
                                                                                        TextView textView5 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                                                        if (textView5 != null && (viewFindChildViewById = ViewBindings.findChildViewById(view, (i2 = R.id.viewLine2))) != null) {
                                                                                            return new LayoutReplyCollectionBinding(linearLayout2, rImageView, imageView, rImageView2, rImageView3, linearLayout, linearLayout2, viewFindChildViewById2, linearLayout3, classicsHeader, smartRefreshLayout, recyclerView, toolbar, textView, rTextView, textView2, rTextView2, textView3, textView4, rTextView3, rTextView4, rTextView5, textView5, viewFindChildViewById);
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
    public static LayoutReplyCollectionBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    @NonNull
    public static LayoutReplyCollectionBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z2) {
        View viewInflate = layoutInflater.inflate(R.layout.layout_reply_collection, viewGroup, false);
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
