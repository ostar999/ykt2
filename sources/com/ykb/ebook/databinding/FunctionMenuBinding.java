package com.ykb.ebook.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.ruffian.library.widget.RCheckBox;
import com.ruffian.library.widget.RFrameLayout;
import com.ruffian.library.widget.RLinearLayout;
import com.ruffian.library.widget.RRadioButton;
import com.ruffian.library.widget.RTextView;
import com.ykb.ebook.R;

/* loaded from: classes6.dex */
public final class FunctionMenuBinding implements ViewBinding {

    @NonNull
    public final RRadioButton cbColorFive;

    @NonNull
    public final RRadioButton cbColorFour;

    @NonNull
    public final RRadioButton cbColorOne;

    @NonNull
    public final RRadioButton cbColorThree;

    @NonNull
    public final RRadioButton cbColorTwo;

    @NonNull
    public final RCheckBox cbDrawLine;

    @NonNull
    public final RFrameLayout flDrawLine;

    @NonNull
    public final FrameLayout flDrawLineMenu;

    @NonNull
    public final RFrameLayout flTextBg;

    @NonNull
    public final ImageView ivDown;

    @NonNull
    public final ImageView ivUp;

    @NonNull
    public final RLinearLayout llMenu;

    @NonNull
    private final RelativeLayout rootView;

    @NonNull
    public final RTextView tvComment;

    @NonNull
    public final RTextView tvCopy;

    @NonNull
    public final RTextView tvError;

    @NonNull
    public final RTextView tvNotes;

    @NonNull
    public final RTextView tvShare;

    private FunctionMenuBinding(@NonNull RelativeLayout relativeLayout, @NonNull RRadioButton rRadioButton, @NonNull RRadioButton rRadioButton2, @NonNull RRadioButton rRadioButton3, @NonNull RRadioButton rRadioButton4, @NonNull RRadioButton rRadioButton5, @NonNull RCheckBox rCheckBox, @NonNull RFrameLayout rFrameLayout, @NonNull FrameLayout frameLayout, @NonNull RFrameLayout rFrameLayout2, @NonNull ImageView imageView, @NonNull ImageView imageView2, @NonNull RLinearLayout rLinearLayout, @NonNull RTextView rTextView, @NonNull RTextView rTextView2, @NonNull RTextView rTextView3, @NonNull RTextView rTextView4, @NonNull RTextView rTextView5) {
        this.rootView = relativeLayout;
        this.cbColorFive = rRadioButton;
        this.cbColorFour = rRadioButton2;
        this.cbColorOne = rRadioButton3;
        this.cbColorThree = rRadioButton4;
        this.cbColorTwo = rRadioButton5;
        this.cbDrawLine = rCheckBox;
        this.flDrawLine = rFrameLayout;
        this.flDrawLineMenu = frameLayout;
        this.flTextBg = rFrameLayout2;
        this.ivDown = imageView;
        this.ivUp = imageView2;
        this.llMenu = rLinearLayout;
        this.tvComment = rTextView;
        this.tvCopy = rTextView2;
        this.tvError = rTextView3;
        this.tvNotes = rTextView4;
        this.tvShare = rTextView5;
    }

    @NonNull
    public static FunctionMenuBinding bind(@NonNull View view) {
        int i2 = R.id.cb_color_five;
        RRadioButton rRadioButton = (RRadioButton) ViewBindings.findChildViewById(view, i2);
        if (rRadioButton != null) {
            i2 = R.id.cb_color_four;
            RRadioButton rRadioButton2 = (RRadioButton) ViewBindings.findChildViewById(view, i2);
            if (rRadioButton2 != null) {
                i2 = R.id.cb_color_one;
                RRadioButton rRadioButton3 = (RRadioButton) ViewBindings.findChildViewById(view, i2);
                if (rRadioButton3 != null) {
                    i2 = R.id.cb_color_three;
                    RRadioButton rRadioButton4 = (RRadioButton) ViewBindings.findChildViewById(view, i2);
                    if (rRadioButton4 != null) {
                        i2 = R.id.cb_color_two;
                        RRadioButton rRadioButton5 = (RRadioButton) ViewBindings.findChildViewById(view, i2);
                        if (rRadioButton5 != null) {
                            i2 = R.id.cb_draw_line;
                            RCheckBox rCheckBox = (RCheckBox) ViewBindings.findChildViewById(view, i2);
                            if (rCheckBox != null) {
                                i2 = R.id.fl_draw_line;
                                RFrameLayout rFrameLayout = (RFrameLayout) ViewBindings.findChildViewById(view, i2);
                                if (rFrameLayout != null) {
                                    i2 = R.id.fl_draw_line_menu;
                                    FrameLayout frameLayout = (FrameLayout) ViewBindings.findChildViewById(view, i2);
                                    if (frameLayout != null) {
                                        i2 = R.id.fl_text_bg;
                                        RFrameLayout rFrameLayout2 = (RFrameLayout) ViewBindings.findChildViewById(view, i2);
                                        if (rFrameLayout2 != null) {
                                            i2 = R.id.iv_down;
                                            ImageView imageView = (ImageView) ViewBindings.findChildViewById(view, i2);
                                            if (imageView != null) {
                                                i2 = R.id.iv_up;
                                                ImageView imageView2 = (ImageView) ViewBindings.findChildViewById(view, i2);
                                                if (imageView2 != null) {
                                                    i2 = R.id.ll_menu;
                                                    RLinearLayout rLinearLayout = (RLinearLayout) ViewBindings.findChildViewById(view, i2);
                                                    if (rLinearLayout != null) {
                                                        i2 = R.id.tv_comment;
                                                        RTextView rTextView = (RTextView) ViewBindings.findChildViewById(view, i2);
                                                        if (rTextView != null) {
                                                            i2 = R.id.tv_copy;
                                                            RTextView rTextView2 = (RTextView) ViewBindings.findChildViewById(view, i2);
                                                            if (rTextView2 != null) {
                                                                i2 = R.id.tv_error;
                                                                RTextView rTextView3 = (RTextView) ViewBindings.findChildViewById(view, i2);
                                                                if (rTextView3 != null) {
                                                                    i2 = R.id.tv_notes;
                                                                    RTextView rTextView4 = (RTextView) ViewBindings.findChildViewById(view, i2);
                                                                    if (rTextView4 != null) {
                                                                        i2 = R.id.tv_share;
                                                                        RTextView rTextView5 = (RTextView) ViewBindings.findChildViewById(view, i2);
                                                                        if (rTextView5 != null) {
                                                                            return new FunctionMenuBinding((RelativeLayout) view, rRadioButton, rRadioButton2, rRadioButton3, rRadioButton4, rRadioButton5, rCheckBox, rFrameLayout, frameLayout, rFrameLayout2, imageView, imageView2, rLinearLayout, rTextView, rTextView2, rTextView3, rTextView4, rTextView5);
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
    public static FunctionMenuBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    @NonNull
    public static FunctionMenuBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z2) {
        View viewInflate = layoutInflater.inflate(R.layout.function_menu, viewGroup, false);
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
