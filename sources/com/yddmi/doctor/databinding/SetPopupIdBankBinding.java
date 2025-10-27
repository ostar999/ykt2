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
public final class SetPopupIdBankBinding implements ViewBinding {

    @NonNull
    public final BLEditText bankNumEt;

    @NonNull
    public final BLEditText bankOpenEt;

    @NonNull
    public final TextView bankOpenTv;

    @NonNull
    public final TextView bankTv;

    @NonNull
    public final ImageView closeImgv;

    @NonNull
    public final ConstraintLayout codeCl;

    @NonNull
    public final ConstraintLayout errorCl;

    @NonNull
    public final TextView goBtnTv;

    @NonNull
    public final TextView goTv;

    @NonNull
    public final BLEditText idEt;

    @NonNull
    public final TextView idTv;

    @NonNull
    public final BLEditText nameEt;

    @NonNull
    public final TextView nameTv;

    @NonNull
    public final BLTextView phoneInputTv;

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

    private SetPopupIdBankBinding(@NonNull ConstraintLayout constraintLayout, @NonNull BLEditText bLEditText, @NonNull BLEditText bLEditText2, @NonNull TextView textView, @NonNull TextView textView2, @NonNull ImageView imageView, @NonNull ConstraintLayout constraintLayout2, @NonNull ConstraintLayout constraintLayout3, @NonNull TextView textView3, @NonNull TextView textView4, @NonNull BLEditText bLEditText3, @NonNull TextView textView5, @NonNull BLEditText bLEditText4, @NonNull TextView textView6, @NonNull BLTextView bLTextView, @NonNull TextView textView7, @NonNull ConstraintLayout constraintLayout4, @NonNull ConstraintLayout constraintLayout5, @NonNull TextView textView8, @NonNull TextView textView9) {
        this.rootView = constraintLayout;
        this.bankNumEt = bLEditText;
        this.bankOpenEt = bLEditText2;
        this.bankOpenTv = textView;
        this.bankTv = textView2;
        this.closeImgv = imageView;
        this.codeCl = constraintLayout2;
        this.errorCl = constraintLayout3;
        this.goBtnTv = textView3;
        this.goTv = textView4;
        this.idEt = bLEditText3;
        this.idTv = textView5;
        this.nameEt = bLEditText4;
        this.nameTv = textView6;
        this.phoneInputTv = bLTextView;
        this.phoneTv = textView7;
        this.popCl = constraintLayout4;
        this.succesCl = constraintLayout5;
        this.tipSuccessTv = textView8;
        this.tipTv = textView9;
    }

    @NonNull
    public static SetPopupIdBankBinding bind(@NonNull View view) {
        int i2 = R.id.bankNumEt;
        BLEditText bLEditText = (BLEditText) ViewBindings.findChildViewById(view, i2);
        if (bLEditText != null) {
            i2 = R.id.bankOpenEt;
            BLEditText bLEditText2 = (BLEditText) ViewBindings.findChildViewById(view, i2);
            if (bLEditText2 != null) {
                i2 = R.id.bankOpenTv;
                TextView textView = (TextView) ViewBindings.findChildViewById(view, i2);
                if (textView != null) {
                    i2 = R.id.bankTv;
                    TextView textView2 = (TextView) ViewBindings.findChildViewById(view, i2);
                    if (textView2 != null) {
                        i2 = R.id.closeImgv;
                        ImageView imageView = (ImageView) ViewBindings.findChildViewById(view, i2);
                        if (imageView != null) {
                            i2 = R.id.codeCl;
                            ConstraintLayout constraintLayout = (ConstraintLayout) ViewBindings.findChildViewById(view, i2);
                            if (constraintLayout != null) {
                                i2 = R.id.errorCl;
                                ConstraintLayout constraintLayout2 = (ConstraintLayout) ViewBindings.findChildViewById(view, i2);
                                if (constraintLayout2 != null) {
                                    i2 = R.id.goBtnTv;
                                    TextView textView3 = (TextView) ViewBindings.findChildViewById(view, i2);
                                    if (textView3 != null) {
                                        i2 = R.id.goTv;
                                        TextView textView4 = (TextView) ViewBindings.findChildViewById(view, i2);
                                        if (textView4 != null) {
                                            i2 = R.id.idEt;
                                            BLEditText bLEditText3 = (BLEditText) ViewBindings.findChildViewById(view, i2);
                                            if (bLEditText3 != null) {
                                                i2 = R.id.idTv;
                                                TextView textView5 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                if (textView5 != null) {
                                                    i2 = R.id.nameEt;
                                                    BLEditText bLEditText4 = (BLEditText) ViewBindings.findChildViewById(view, i2);
                                                    if (bLEditText4 != null) {
                                                        i2 = R.id.nameTv;
                                                        TextView textView6 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                        if (textView6 != null) {
                                                            i2 = R.id.phoneInputTv;
                                                            BLTextView bLTextView = (BLTextView) ViewBindings.findChildViewById(view, i2);
                                                            if (bLTextView != null) {
                                                                i2 = R.id.phoneTv;
                                                                TextView textView7 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                                if (textView7 != null) {
                                                                    i2 = R.id.popCl;
                                                                    ConstraintLayout constraintLayout3 = (ConstraintLayout) ViewBindings.findChildViewById(view, i2);
                                                                    if (constraintLayout3 != null) {
                                                                        i2 = R.id.succesCl;
                                                                        ConstraintLayout constraintLayout4 = (ConstraintLayout) ViewBindings.findChildViewById(view, i2);
                                                                        if (constraintLayout4 != null) {
                                                                            i2 = R.id.tipSuccessTv;
                                                                            TextView textView8 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                                            if (textView8 != null) {
                                                                                i2 = R.id.tipTv;
                                                                                TextView textView9 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                                                if (textView9 != null) {
                                                                                    return new SetPopupIdBankBinding((ConstraintLayout) view, bLEditText, bLEditText2, textView, textView2, imageView, constraintLayout, constraintLayout2, textView3, textView4, bLEditText3, textView5, bLEditText4, textView6, bLTextView, textView7, constraintLayout3, constraintLayout4, textView8, textView9);
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
    public static SetPopupIdBankBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    @NonNull
    public static SetPopupIdBankBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z2) {
        View viewInflate = layoutInflater.inflate(R.layout.set_popup_id_bank, viewGroup, false);
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
