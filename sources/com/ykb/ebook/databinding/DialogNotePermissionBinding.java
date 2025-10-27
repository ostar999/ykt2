package com.ykb.ebook.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.ruffian.library.widget.RCheckBox;
import com.ruffian.library.widget.RLinearLayout;
import com.ykb.ebook.R;

/* loaded from: classes6.dex */
public final class DialogNotePermissionBinding implements ViewBinding {

    @NonNull
    public final RCheckBox cbAttentionLeft;

    @NonNull
    public final RCheckBox cbAttentionRight;

    @NonNull
    public final RCheckBox cbAttentionTitle;

    @NonNull
    public final RCheckBox cbOpenLeft;

    @NonNull
    public final RCheckBox cbOpenRight;

    @NonNull
    public final RCheckBox cbOpenTitle;

    @NonNull
    public final RCheckBox cbPrivateLeft;

    @NonNull
    public final RCheckBox cbPrivateRight;

    @NonNull
    public final RCheckBox cbPrivateTitle;

    @NonNull
    public final RCheckBox cbShieldLeft;

    @NonNull
    public final RCheckBox cbShieldRight;

    @NonNull
    public final RCheckBox cbShieldTitle;

    @NonNull
    public final ImageView imgClose;

    @NonNull
    public final LinearLayout llAttention;

    @NonNull
    public final LinearLayout llOpen;

    @NonNull
    public final LinearLayout llPrivate;

    @NonNull
    public final LinearLayout llShield;

    @NonNull
    private final RLinearLayout rootView;

    @NonNull
    public final RCheckBox tvAttentionSubTitle;

    @NonNull
    public final RCheckBox tvOpenSubTitle;

    @NonNull
    public final RCheckBox tvPrivateSubTitle;

    @NonNull
    public final RCheckBox tvShieldSubTitle;

    private DialogNotePermissionBinding(@NonNull RLinearLayout rLinearLayout, @NonNull RCheckBox rCheckBox, @NonNull RCheckBox rCheckBox2, @NonNull RCheckBox rCheckBox3, @NonNull RCheckBox rCheckBox4, @NonNull RCheckBox rCheckBox5, @NonNull RCheckBox rCheckBox6, @NonNull RCheckBox rCheckBox7, @NonNull RCheckBox rCheckBox8, @NonNull RCheckBox rCheckBox9, @NonNull RCheckBox rCheckBox10, @NonNull RCheckBox rCheckBox11, @NonNull RCheckBox rCheckBox12, @NonNull ImageView imageView, @NonNull LinearLayout linearLayout, @NonNull LinearLayout linearLayout2, @NonNull LinearLayout linearLayout3, @NonNull LinearLayout linearLayout4, @NonNull RCheckBox rCheckBox13, @NonNull RCheckBox rCheckBox14, @NonNull RCheckBox rCheckBox15, @NonNull RCheckBox rCheckBox16) {
        this.rootView = rLinearLayout;
        this.cbAttentionLeft = rCheckBox;
        this.cbAttentionRight = rCheckBox2;
        this.cbAttentionTitle = rCheckBox3;
        this.cbOpenLeft = rCheckBox4;
        this.cbOpenRight = rCheckBox5;
        this.cbOpenTitle = rCheckBox6;
        this.cbPrivateLeft = rCheckBox7;
        this.cbPrivateRight = rCheckBox8;
        this.cbPrivateTitle = rCheckBox9;
        this.cbShieldLeft = rCheckBox10;
        this.cbShieldRight = rCheckBox11;
        this.cbShieldTitle = rCheckBox12;
        this.imgClose = imageView;
        this.llAttention = linearLayout;
        this.llOpen = linearLayout2;
        this.llPrivate = linearLayout3;
        this.llShield = linearLayout4;
        this.tvAttentionSubTitle = rCheckBox13;
        this.tvOpenSubTitle = rCheckBox14;
        this.tvPrivateSubTitle = rCheckBox15;
        this.tvShieldSubTitle = rCheckBox16;
    }

    @NonNull
    public static DialogNotePermissionBinding bind(@NonNull View view) {
        int i2 = R.id.cb_attention_left;
        RCheckBox rCheckBox = (RCheckBox) ViewBindings.findChildViewById(view, i2);
        if (rCheckBox != null) {
            i2 = R.id.cb_attention_right;
            RCheckBox rCheckBox2 = (RCheckBox) ViewBindings.findChildViewById(view, i2);
            if (rCheckBox2 != null) {
                i2 = R.id.cb_attention_title;
                RCheckBox rCheckBox3 = (RCheckBox) ViewBindings.findChildViewById(view, i2);
                if (rCheckBox3 != null) {
                    i2 = R.id.cb_open_left;
                    RCheckBox rCheckBox4 = (RCheckBox) ViewBindings.findChildViewById(view, i2);
                    if (rCheckBox4 != null) {
                        i2 = R.id.cb_open_right;
                        RCheckBox rCheckBox5 = (RCheckBox) ViewBindings.findChildViewById(view, i2);
                        if (rCheckBox5 != null) {
                            i2 = R.id.cb_open_title;
                            RCheckBox rCheckBox6 = (RCheckBox) ViewBindings.findChildViewById(view, i2);
                            if (rCheckBox6 != null) {
                                i2 = R.id.cb_private_left;
                                RCheckBox rCheckBox7 = (RCheckBox) ViewBindings.findChildViewById(view, i2);
                                if (rCheckBox7 != null) {
                                    i2 = R.id.cb_private_right;
                                    RCheckBox rCheckBox8 = (RCheckBox) ViewBindings.findChildViewById(view, i2);
                                    if (rCheckBox8 != null) {
                                        i2 = R.id.cb_private_title;
                                        RCheckBox rCheckBox9 = (RCheckBox) ViewBindings.findChildViewById(view, i2);
                                        if (rCheckBox9 != null) {
                                            i2 = R.id.cb_shield_left;
                                            RCheckBox rCheckBox10 = (RCheckBox) ViewBindings.findChildViewById(view, i2);
                                            if (rCheckBox10 != null) {
                                                i2 = R.id.cb_shield_right;
                                                RCheckBox rCheckBox11 = (RCheckBox) ViewBindings.findChildViewById(view, i2);
                                                if (rCheckBox11 != null) {
                                                    i2 = R.id.cb_shield_title;
                                                    RCheckBox rCheckBox12 = (RCheckBox) ViewBindings.findChildViewById(view, i2);
                                                    if (rCheckBox12 != null) {
                                                        i2 = R.id.img_close;
                                                        ImageView imageView = (ImageView) ViewBindings.findChildViewById(view, i2);
                                                        if (imageView != null) {
                                                            i2 = R.id.ll_attention;
                                                            LinearLayout linearLayout = (LinearLayout) ViewBindings.findChildViewById(view, i2);
                                                            if (linearLayout != null) {
                                                                i2 = R.id.ll_open;
                                                                LinearLayout linearLayout2 = (LinearLayout) ViewBindings.findChildViewById(view, i2);
                                                                if (linearLayout2 != null) {
                                                                    i2 = R.id.ll_private;
                                                                    LinearLayout linearLayout3 = (LinearLayout) ViewBindings.findChildViewById(view, i2);
                                                                    if (linearLayout3 != null) {
                                                                        i2 = R.id.ll_shield;
                                                                        LinearLayout linearLayout4 = (LinearLayout) ViewBindings.findChildViewById(view, i2);
                                                                        if (linearLayout4 != null) {
                                                                            i2 = R.id.tv_attention_sub_title;
                                                                            RCheckBox rCheckBox13 = (RCheckBox) ViewBindings.findChildViewById(view, i2);
                                                                            if (rCheckBox13 != null) {
                                                                                i2 = R.id.tv_open_sub_title;
                                                                                RCheckBox rCheckBox14 = (RCheckBox) ViewBindings.findChildViewById(view, i2);
                                                                                if (rCheckBox14 != null) {
                                                                                    i2 = R.id.tv_private_sub_title;
                                                                                    RCheckBox rCheckBox15 = (RCheckBox) ViewBindings.findChildViewById(view, i2);
                                                                                    if (rCheckBox15 != null) {
                                                                                        i2 = R.id.tv_shield_sub_title;
                                                                                        RCheckBox rCheckBox16 = (RCheckBox) ViewBindings.findChildViewById(view, i2);
                                                                                        if (rCheckBox16 != null) {
                                                                                            return new DialogNotePermissionBinding((RLinearLayout) view, rCheckBox, rCheckBox2, rCheckBox3, rCheckBox4, rCheckBox5, rCheckBox6, rCheckBox7, rCheckBox8, rCheckBox9, rCheckBox10, rCheckBox11, rCheckBox12, imageView, linearLayout, linearLayout2, linearLayout3, linearLayout4, rCheckBox13, rCheckBox14, rCheckBox15, rCheckBox16);
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
    public static DialogNotePermissionBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    @NonNull
    public static DialogNotePermissionBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z2) {
        View viewInflate = layoutInflater.inflate(R.layout.dialog_note_permission, viewGroup, false);
        if (z2) {
            viewGroup.addView(viewInflate);
        }
        return bind(viewInflate);
    }

    @Override // androidx.viewbinding.ViewBinding
    @NonNull
    public RLinearLayout getRoot() {
        return this.rootView;
    }
}
