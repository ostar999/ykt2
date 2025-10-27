package com.ykb.ebook.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.ruffian.library.widget.RCheckBox;
import com.ruffian.library.widget.RRadioButton;
import com.ruffian.library.widget.RTextView;
import com.ykb.ebook.R;
import com.ykb.ebook.weight.slider.NiftySlider;

/* loaded from: classes6.dex */
public final class ViewReadMenuBinding implements ViewBinding {

    @NonNull
    public final RRadioButton bgBlue;

    @NonNull
    public final RRadioButton bgWhite;

    @NonNull
    public final RRadioButton bgYellow;

    @NonNull
    public final ImageView btnShre;

    @NonNull
    public final RCheckBox cbDarkMode;

    @NonNull
    public final RCheckBox cbNote;

    @NonNull
    public final RRadioButton cbPageFz;

    @NonNull
    public final RRadioButton cbPageSx;

    @NonNull
    public final RRadioButton cbPageZy;

    @NonNull
    public final RCheckBox cbReview;

    @NonNull
    public final ImageView closePageMethod;

    @NonNull
    public final FrameLayout flBookCatalog;

    @NonNull
    public final RelativeLayout flBookComment;

    @NonNull
    public final FrameLayout flBookMark;

    @NonNull
    public final FrameLayout flSetting;

    @NonNull
    public final ImageView imgBack;

    @NonNull
    public final ImageView imgBookCatalog;

    @NonNull
    public final ImageView imgBookComment;

    @NonNull
    public final ImageView imgBookMark;

    @NonNull
    public final ImageView imgBookSetting;

    @NonNull
    public final View lineV;

    @NonNull
    public final View lineView;

    @NonNull
    public final LinearLayout llBottom;

    @NonNull
    public final LinearLayout llBottomMenu;

    @NonNull
    public final LinearLayout llTop;

    @NonNull
    public final LinearLayout lyPageMethod;

    @NonNull
    public final LinearLayout lySetting;

    @NonNull
    private final ConstraintLayout rootView;

    @NonNull
    public final NiftySlider seekFontSize;

    @NonNull
    public final NiftySlider seekPageLine;

    @NonNull
    public final NiftySlider seekPageMargin;

    @NonNull
    public final NiftySlider seekReadPage;

    @NonNull
    public final Toolbar titleBar;

    @NonNull
    public final RTextView tvAddlibrary;

    @NonNull
    public final TextView tvBg;

    @NonNull
    public final RTextView tvCommentNum;

    @NonNull
    public final RTextView tvDownload;

    @NonNull
    public final RTextView tvMoreSet;

    @NonNull
    public final RTextView tvNext;

    @NonNull
    public final RTextView tvPageSet;

    @NonNull
    public final TextView tvPageTitle;

    @NonNull
    public final RTextView tvPreview;

    @NonNull
    public final TextView tvReadRate;

    @NonNull
    public final TextView tvReadTime;

    @NonNull
    public final TextView tvShowProgress;

    @NonNull
    public final View viewPageLine;

    @NonNull
    public final View vwMenuBg;

    private ViewReadMenuBinding(@NonNull ConstraintLayout constraintLayout, @NonNull RRadioButton rRadioButton, @NonNull RRadioButton rRadioButton2, @NonNull RRadioButton rRadioButton3, @NonNull ImageView imageView, @NonNull RCheckBox rCheckBox, @NonNull RCheckBox rCheckBox2, @NonNull RRadioButton rRadioButton4, @NonNull RRadioButton rRadioButton5, @NonNull RRadioButton rRadioButton6, @NonNull RCheckBox rCheckBox3, @NonNull ImageView imageView2, @NonNull FrameLayout frameLayout, @NonNull RelativeLayout relativeLayout, @NonNull FrameLayout frameLayout2, @NonNull FrameLayout frameLayout3, @NonNull ImageView imageView3, @NonNull ImageView imageView4, @NonNull ImageView imageView5, @NonNull ImageView imageView6, @NonNull ImageView imageView7, @NonNull View view, @NonNull View view2, @NonNull LinearLayout linearLayout, @NonNull LinearLayout linearLayout2, @NonNull LinearLayout linearLayout3, @NonNull LinearLayout linearLayout4, @NonNull LinearLayout linearLayout5, @NonNull NiftySlider niftySlider, @NonNull NiftySlider niftySlider2, @NonNull NiftySlider niftySlider3, @NonNull NiftySlider niftySlider4, @NonNull Toolbar toolbar, @NonNull RTextView rTextView, @NonNull TextView textView, @NonNull RTextView rTextView2, @NonNull RTextView rTextView3, @NonNull RTextView rTextView4, @NonNull RTextView rTextView5, @NonNull RTextView rTextView6, @NonNull TextView textView2, @NonNull RTextView rTextView7, @NonNull TextView textView3, @NonNull TextView textView4, @NonNull TextView textView5, @NonNull View view3, @NonNull View view4) {
        this.rootView = constraintLayout;
        this.bgBlue = rRadioButton;
        this.bgWhite = rRadioButton2;
        this.bgYellow = rRadioButton3;
        this.btnShre = imageView;
        this.cbDarkMode = rCheckBox;
        this.cbNote = rCheckBox2;
        this.cbPageFz = rRadioButton4;
        this.cbPageSx = rRadioButton5;
        this.cbPageZy = rRadioButton6;
        this.cbReview = rCheckBox3;
        this.closePageMethod = imageView2;
        this.flBookCatalog = frameLayout;
        this.flBookComment = relativeLayout;
        this.flBookMark = frameLayout2;
        this.flSetting = frameLayout3;
        this.imgBack = imageView3;
        this.imgBookCatalog = imageView4;
        this.imgBookComment = imageView5;
        this.imgBookMark = imageView6;
        this.imgBookSetting = imageView7;
        this.lineV = view;
        this.lineView = view2;
        this.llBottom = linearLayout;
        this.llBottomMenu = linearLayout2;
        this.llTop = linearLayout3;
        this.lyPageMethod = linearLayout4;
        this.lySetting = linearLayout5;
        this.seekFontSize = niftySlider;
        this.seekPageLine = niftySlider2;
        this.seekPageMargin = niftySlider3;
        this.seekReadPage = niftySlider4;
        this.titleBar = toolbar;
        this.tvAddlibrary = rTextView;
        this.tvBg = textView;
        this.tvCommentNum = rTextView2;
        this.tvDownload = rTextView3;
        this.tvMoreSet = rTextView4;
        this.tvNext = rTextView5;
        this.tvPageSet = rTextView6;
        this.tvPageTitle = textView2;
        this.tvPreview = rTextView7;
        this.tvReadRate = textView3;
        this.tvReadTime = textView4;
        this.tvShowProgress = textView5;
        this.viewPageLine = view3;
        this.vwMenuBg = view4;
    }

    @NonNull
    public static ViewReadMenuBinding bind(@NonNull View view) {
        View viewFindChildViewById;
        View viewFindChildViewById2;
        View viewFindChildViewById3;
        View viewFindChildViewById4;
        int i2 = R.id.bg_blue;
        RRadioButton rRadioButton = (RRadioButton) ViewBindings.findChildViewById(view, i2);
        if (rRadioButton != null) {
            i2 = R.id.bg_white;
            RRadioButton rRadioButton2 = (RRadioButton) ViewBindings.findChildViewById(view, i2);
            if (rRadioButton2 != null) {
                i2 = R.id.bg_yellow;
                RRadioButton rRadioButton3 = (RRadioButton) ViewBindings.findChildViewById(view, i2);
                if (rRadioButton3 != null) {
                    i2 = R.id.btn_shre;
                    ImageView imageView = (ImageView) ViewBindings.findChildViewById(view, i2);
                    if (imageView != null) {
                        i2 = R.id.cb_dark_mode;
                        RCheckBox rCheckBox = (RCheckBox) ViewBindings.findChildViewById(view, i2);
                        if (rCheckBox != null) {
                            i2 = R.id.cb_note;
                            RCheckBox rCheckBox2 = (RCheckBox) ViewBindings.findChildViewById(view, i2);
                            if (rCheckBox2 != null) {
                                i2 = R.id.cb_page_fz;
                                RRadioButton rRadioButton4 = (RRadioButton) ViewBindings.findChildViewById(view, i2);
                                if (rRadioButton4 != null) {
                                    i2 = R.id.cb_page_sx;
                                    RRadioButton rRadioButton5 = (RRadioButton) ViewBindings.findChildViewById(view, i2);
                                    if (rRadioButton5 != null) {
                                        i2 = R.id.cb_page_zy;
                                        RRadioButton rRadioButton6 = (RRadioButton) ViewBindings.findChildViewById(view, i2);
                                        if (rRadioButton6 != null) {
                                            i2 = R.id.cb_review;
                                            RCheckBox rCheckBox3 = (RCheckBox) ViewBindings.findChildViewById(view, i2);
                                            if (rCheckBox3 != null) {
                                                i2 = R.id.close_page_method;
                                                ImageView imageView2 = (ImageView) ViewBindings.findChildViewById(view, i2);
                                                if (imageView2 != null) {
                                                    i2 = R.id.fl_book_catalog;
                                                    FrameLayout frameLayout = (FrameLayout) ViewBindings.findChildViewById(view, i2);
                                                    if (frameLayout != null) {
                                                        i2 = R.id.fl_book_comment;
                                                        RelativeLayout relativeLayout = (RelativeLayout) ViewBindings.findChildViewById(view, i2);
                                                        if (relativeLayout != null) {
                                                            i2 = R.id.fl_book_mark;
                                                            FrameLayout frameLayout2 = (FrameLayout) ViewBindings.findChildViewById(view, i2);
                                                            if (frameLayout2 != null) {
                                                                i2 = R.id.fl_setting;
                                                                FrameLayout frameLayout3 = (FrameLayout) ViewBindings.findChildViewById(view, i2);
                                                                if (frameLayout3 != null) {
                                                                    i2 = R.id.img_back;
                                                                    ImageView imageView3 = (ImageView) ViewBindings.findChildViewById(view, i2);
                                                                    if (imageView3 != null) {
                                                                        i2 = R.id.img_book_catalog;
                                                                        ImageView imageView4 = (ImageView) ViewBindings.findChildViewById(view, i2);
                                                                        if (imageView4 != null) {
                                                                            i2 = R.id.img_book_comment;
                                                                            ImageView imageView5 = (ImageView) ViewBindings.findChildViewById(view, i2);
                                                                            if (imageView5 != null) {
                                                                                i2 = R.id.img_book_mark;
                                                                                ImageView imageView6 = (ImageView) ViewBindings.findChildViewById(view, i2);
                                                                                if (imageView6 != null) {
                                                                                    i2 = R.id.img_book_setting;
                                                                                    ImageView imageView7 = (ImageView) ViewBindings.findChildViewById(view, i2);
                                                                                    if (imageView7 != null && (viewFindChildViewById = ViewBindings.findChildViewById(view, (i2 = R.id.line_v))) != null && (viewFindChildViewById2 = ViewBindings.findChildViewById(view, (i2 = R.id.line_view))) != null) {
                                                                                        i2 = R.id.ll_bottom;
                                                                                        LinearLayout linearLayout = (LinearLayout) ViewBindings.findChildViewById(view, i2);
                                                                                        if (linearLayout != null) {
                                                                                            i2 = R.id.ll_bottom_menu;
                                                                                            LinearLayout linearLayout2 = (LinearLayout) ViewBindings.findChildViewById(view, i2);
                                                                                            if (linearLayout2 != null) {
                                                                                                i2 = R.id.ll_top;
                                                                                                LinearLayout linearLayout3 = (LinearLayout) ViewBindings.findChildViewById(view, i2);
                                                                                                if (linearLayout3 != null) {
                                                                                                    i2 = R.id.ly_page_method;
                                                                                                    LinearLayout linearLayout4 = (LinearLayout) ViewBindings.findChildViewById(view, i2);
                                                                                                    if (linearLayout4 != null) {
                                                                                                        i2 = R.id.ly_setting;
                                                                                                        LinearLayout linearLayout5 = (LinearLayout) ViewBindings.findChildViewById(view, i2);
                                                                                                        if (linearLayout5 != null) {
                                                                                                            i2 = R.id.seek_font_size;
                                                                                                            NiftySlider niftySlider = (NiftySlider) ViewBindings.findChildViewById(view, i2);
                                                                                                            if (niftySlider != null) {
                                                                                                                i2 = R.id.seek_page_line;
                                                                                                                NiftySlider niftySlider2 = (NiftySlider) ViewBindings.findChildViewById(view, i2);
                                                                                                                if (niftySlider2 != null) {
                                                                                                                    i2 = R.id.seek_page_margin;
                                                                                                                    NiftySlider niftySlider3 = (NiftySlider) ViewBindings.findChildViewById(view, i2);
                                                                                                                    if (niftySlider3 != null) {
                                                                                                                        i2 = R.id.seek_read_page;
                                                                                                                        NiftySlider niftySlider4 = (NiftySlider) ViewBindings.findChildViewById(view, i2);
                                                                                                                        if (niftySlider4 != null) {
                                                                                                                            i2 = R.id.title_bar;
                                                                                                                            Toolbar toolbar = (Toolbar) ViewBindings.findChildViewById(view, i2);
                                                                                                                            if (toolbar != null) {
                                                                                                                                i2 = R.id.tv_addlibrary;
                                                                                                                                RTextView rTextView = (RTextView) ViewBindings.findChildViewById(view, i2);
                                                                                                                                if (rTextView != null) {
                                                                                                                                    i2 = R.id.tv_bg;
                                                                                                                                    TextView textView = (TextView) ViewBindings.findChildViewById(view, i2);
                                                                                                                                    if (textView != null) {
                                                                                                                                        i2 = R.id.tv_comment_num;
                                                                                                                                        RTextView rTextView2 = (RTextView) ViewBindings.findChildViewById(view, i2);
                                                                                                                                        if (rTextView2 != null) {
                                                                                                                                            i2 = R.id.tv_download;
                                                                                                                                            RTextView rTextView3 = (RTextView) ViewBindings.findChildViewById(view, i2);
                                                                                                                                            if (rTextView3 != null) {
                                                                                                                                                i2 = R.id.tv_more_set;
                                                                                                                                                RTextView rTextView4 = (RTextView) ViewBindings.findChildViewById(view, i2);
                                                                                                                                                if (rTextView4 != null) {
                                                                                                                                                    i2 = R.id.tv_next;
                                                                                                                                                    RTextView rTextView5 = (RTextView) ViewBindings.findChildViewById(view, i2);
                                                                                                                                                    if (rTextView5 != null) {
                                                                                                                                                        i2 = R.id.tv_page_set;
                                                                                                                                                        RTextView rTextView6 = (RTextView) ViewBindings.findChildViewById(view, i2);
                                                                                                                                                        if (rTextView6 != null) {
                                                                                                                                                            i2 = R.id.tv_page_title;
                                                                                                                                                            TextView textView2 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                                                                                                                            if (textView2 != null) {
                                                                                                                                                                i2 = R.id.tv_preview;
                                                                                                                                                                RTextView rTextView7 = (RTextView) ViewBindings.findChildViewById(view, i2);
                                                                                                                                                                if (rTextView7 != null) {
                                                                                                                                                                    i2 = R.id.tv_read_rate;
                                                                                                                                                                    TextView textView3 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                                                                                                                                    if (textView3 != null) {
                                                                                                                                                                        i2 = R.id.tv_read_time;
                                                                                                                                                                        TextView textView4 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                                                                                                                                        if (textView4 != null) {
                                                                                                                                                                            i2 = R.id.tvShowProgress;
                                                                                                                                                                            TextView textView5 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                                                                                                                                            if (textView5 != null && (viewFindChildViewById3 = ViewBindings.findChildViewById(view, (i2 = R.id.viewPageLine))) != null && (viewFindChildViewById4 = ViewBindings.findChildViewById(view, (i2 = R.id.vw_menu_bg))) != null) {
                                                                                                                                                                                return new ViewReadMenuBinding((ConstraintLayout) view, rRadioButton, rRadioButton2, rRadioButton3, imageView, rCheckBox, rCheckBox2, rRadioButton4, rRadioButton5, rRadioButton6, rCheckBox3, imageView2, frameLayout, relativeLayout, frameLayout2, frameLayout3, imageView3, imageView4, imageView5, imageView6, imageView7, viewFindChildViewById, viewFindChildViewById2, linearLayout, linearLayout2, linearLayout3, linearLayout4, linearLayout5, niftySlider, niftySlider2, niftySlider3, niftySlider4, toolbar, rTextView, textView, rTextView2, rTextView3, rTextView4, rTextView5, rTextView6, textView2, rTextView7, textView3, textView4, textView5, viewFindChildViewById3, viewFindChildViewById4);
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
    public static ViewReadMenuBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    @NonNull
    public static ViewReadMenuBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z2) {
        View viewInflate = layoutInflater.inflate(R.layout.view_read_menu, viewGroup, false);
        if (z2) {
            viewGroup.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    @Override // androidx.viewbinding.ViewBinding
    @NonNull
    public ConstraintLayout getRoot() {
        return this.rootView;
    }
}
