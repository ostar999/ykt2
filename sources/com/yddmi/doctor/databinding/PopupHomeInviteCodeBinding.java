package com.yddmi.doctor.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.noober.background.view.BLEditText;
import com.noober.background.view.BLTextView;
import com.yddmi.doctor.R;

/* loaded from: classes6.dex */
public final class PopupHomeInviteCodeBinding implements ViewBinding {

    @NonNull
    public final ImageView closeImgv;

    @NonNull
    public final ConstraintLayout codeCl;

    @NonNull
    public final BLEditText codeEt;

    @NonNull
    public final TextView codeTv;

    @NonNull
    public final ConstraintLayout errorCl;

    @NonNull
    public final BLTextView getTv;

    @NonNull
    public final TextView goBtnTv;

    @NonNull
    public final TextView goTv;

    @NonNull
    public final BLEditText inviteCodeEt;

    @NonNull
    public final TextView inviteCodeTv;

    @NonNull
    public final BLTextView numTv;

    @NonNull
    public final BLEditText phoneEt;

    @NonNull
    public final TextView phoneTv;

    @NonNull
    public final ConstraintLayout popCl;

    @NonNull
    private final ConstraintLayout rootView;

    @NonNull
    public final ConstraintLayout succesCl;

    @NonNull
    public final TextView tipSuccessTv;

    @NonNull
    public final TextView tipTv;

    private PopupHomeInviteCodeBinding(@NonNull ConstraintLayout constraintLayout, @NonNull ImageView imageView, @NonNull ConstraintLayout constraintLayout2, @NonNull BLEditText bLEditText, @NonNull TextView textView, @NonNull ConstraintLayout constraintLayout3, @NonNull BLTextView bLTextView, @NonNull TextView textView2, @NonNull TextView textView3, @NonNull BLEditText bLEditText2, @NonNull TextView textView4, @NonNull BLTextView bLTextView2, @NonNull BLEditText bLEditText3, @NonNull TextView textView5, @NonNull ConstraintLayout constraintLayout4, @NonNull ConstraintLayout constraintLayout5, @NonNull TextView textView6, @NonNull TextView textView7) {
        this.rootView = constraintLayout;
        this.closeImgv = imageView;
        this.codeCl = constraintLayout2;
        this.codeEt = bLEditText;
        this.codeTv = textView;
        this.errorCl = constraintLayout3;
        this.getTv = bLTextView;
        this.goBtnTv = textView2;
        this.goTv = textView3;
        this.inviteCodeEt = bLEditText2;
        this.inviteCodeTv = textView4;
        this.numTv = bLTextView2;
        this.phoneEt = bLEditText3;
        this.phoneTv = textView5;
        this.popCl = constraintLayout4;
        this.succesCl = constraintLayout5;
        this.tipSuccessTv = textView6;
        this.tipTv = textView7;
    }

    @NonNull
    public static PopupHomeInviteCodeBinding bind(@NonNull View view) {
        int i2 = R.id.closeImgv;
        ImageView imageView = (ImageView) ViewBindings.findChildViewById(view, i2);
        if (imageView != null) {
            i2 = R.id.codeCl;
            ConstraintLayout constraintLayout = (ConstraintLayout) ViewBindings.findChildViewById(view, i2);
            if (constraintLayout != null) {
                i2 = R.id.codeEt;
                BLEditText bLEditText = (BLEditText) ViewBindings.findChildViewById(view, i2);
                if (bLEditText != null) {
                    i2 = R.id.codeTv;
                    TextView textView = (TextView) ViewBindings.findChildViewById(view, i2);
                    if (textView != null) {
                        i2 = R.id.errorCl;
                        ConstraintLayout constraintLayout2 = (ConstraintLayout) ViewBindings.findChildViewById(view, i2);
                        if (constraintLayout2 != null) {
                            i2 = R.id.getTv;
                            BLTextView bLTextView = (BLTextView) ViewBindings.findChildViewById(view, i2);
                            if (bLTextView != null) {
                                i2 = R.id.goBtnTv;
                                TextView textView2 = (TextView) ViewBindings.findChildViewById(view, i2);
                                if (textView2 != null) {
                                    i2 = R.id.goTv;
                                    TextView textView3 = (TextView) ViewBindings.findChildViewById(view, i2);
                                    if (textView3 != null) {
                                        i2 = R.id.inviteCodeEt;
                                        BLEditText bLEditText2 = (BLEditText) ViewBindings.findChildViewById(view, i2);
                                        if (bLEditText2 != null) {
                                            i2 = R.id.inviteCodeTv;
                                            TextView textView4 = (TextView) ViewBindings.findChildViewById(view, i2);
                                            if (textView4 != null) {
                                                i2 = R.id.numTv;
                                                BLTextView bLTextView2 = (BLTextView) ViewBindings.findChildViewById(view, i2);
                                                if (bLTextView2 != null) {
                                                    i2 = R.id.phoneEt;
                                                    BLEditText bLEditText3 = (BLEditText) ViewBindings.findChildViewById(view, i2);
                                                    if (bLEditText3 != null) {
                                                        i2 = R.id.phoneTv;
                                                        TextView textView5 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                        if (textView5 != null) {
                                                            i2 = R.id.popCl;
                                                            ConstraintLayout constraintLayout3 = (ConstraintLayout) ViewBindings.findChildViewById(view, i2);
                                                            if (constraintLayout3 != null) {
                                                                i2 = R.id.succesCl;
                                                                ConstraintLayout constraintLayout4 = (ConstraintLayout) ViewBindings.findChildViewById(view, i2);
                                                                if (constraintLayout4 != null) {
                                                                    i2 = R.id.tipSuccessTv;
                                                                    TextView textView6 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                                    if (textView6 != null) {
                                                                        i2 = R.id.tipTv;
                                                                        TextView textView7 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                                        if (textView7 != null) {
                                                                            return new PopupHomeInviteCodeBinding((ConstraintLayout) view, imageView, constraintLayout, bLEditText, textView, constraintLayout2, bLTextView, textView2, textView3, bLEditText2, textView4, bLTextView2, bLEditText3, textView5, constraintLayout3, constraintLayout4, textView6, textView7);
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
    public static PopupHomeInviteCodeBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    @NonNull
    public static PopupHomeInviteCodeBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z2) {
        View viewInflate = layoutInflater.inflate(R.layout.popup_home_invite_code, viewGroup, false);
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
