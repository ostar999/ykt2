package com.yddmi.doctor.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.kaelli.niceratingbar.NiceRatingBar;
import com.yddmi.doctor.R;
import com.yddmi.doctor.widget.FullVideoView;

/* loaded from: classes6.dex */
public final class ExamActivityRandomBinding implements ViewBinding {

    @NonNull
    public final ConstraintLayout card1Cl;

    @NonNull
    public final FrameLayout card1Fl;

    @NonNull
    public final ConstraintLayout card2Cl;

    @NonNull
    public final FrameLayout card2Fl;

    @NonNull
    public final ConstraintLayout card3Cl;

    @NonNull
    public final FrameLayout card3Fl;

    @NonNull
    public final LinearLayout cardLl;

    @NonNull
    public final ImageView img1V;

    @NonNull
    public final ImageView img2V;

    @NonNull
    public final ImageView img3V;

    @NonNull
    public final TextView maxNumTv;

    @NonNull
    public final TextView name1Tv;

    @NonNull
    public final TextView name2Tv;

    @NonNull
    public final TextView name3Tv;

    @NonNull
    public final TextView resetTv;

    @NonNull
    private final ConstraintLayout rootView;

    @NonNull
    public final NiceRatingBar starBar1;

    @NonNull
    public final NiceRatingBar starBar2;

    @NonNull
    public final NiceRatingBar starBar3;

    @NonNull
    public final TextView startTv;

    @NonNull
    public final TextView tipTv;

    @NonNull
    public final FrameLayout videoFl;

    @NonNull
    public final ImageView videoImgv;

    @NonNull
    public final FullVideoView videoV;

    @NonNull
    public final ImageView xImgv;

    private ExamActivityRandomBinding(@NonNull ConstraintLayout constraintLayout, @NonNull ConstraintLayout constraintLayout2, @NonNull FrameLayout frameLayout, @NonNull ConstraintLayout constraintLayout3, @NonNull FrameLayout frameLayout2, @NonNull ConstraintLayout constraintLayout4, @NonNull FrameLayout frameLayout3, @NonNull LinearLayout linearLayout, @NonNull ImageView imageView, @NonNull ImageView imageView2, @NonNull ImageView imageView3, @NonNull TextView textView, @NonNull TextView textView2, @NonNull TextView textView3, @NonNull TextView textView4, @NonNull TextView textView5, @NonNull NiceRatingBar niceRatingBar, @NonNull NiceRatingBar niceRatingBar2, @NonNull NiceRatingBar niceRatingBar3, @NonNull TextView textView6, @NonNull TextView textView7, @NonNull FrameLayout frameLayout4, @NonNull ImageView imageView4, @NonNull FullVideoView fullVideoView, @NonNull ImageView imageView5) {
        this.rootView = constraintLayout;
        this.card1Cl = constraintLayout2;
        this.card1Fl = frameLayout;
        this.card2Cl = constraintLayout3;
        this.card2Fl = frameLayout2;
        this.card3Cl = constraintLayout4;
        this.card3Fl = frameLayout3;
        this.cardLl = linearLayout;
        this.img1V = imageView;
        this.img2V = imageView2;
        this.img3V = imageView3;
        this.maxNumTv = textView;
        this.name1Tv = textView2;
        this.name2Tv = textView3;
        this.name3Tv = textView4;
        this.resetTv = textView5;
        this.starBar1 = niceRatingBar;
        this.starBar2 = niceRatingBar2;
        this.starBar3 = niceRatingBar3;
        this.startTv = textView6;
        this.tipTv = textView7;
        this.videoFl = frameLayout4;
        this.videoImgv = imageView4;
        this.videoV = fullVideoView;
        this.xImgv = imageView5;
    }

    @NonNull
    public static ExamActivityRandomBinding bind(@NonNull View view) {
        int i2 = R.id.card1Cl;
        ConstraintLayout constraintLayout = (ConstraintLayout) ViewBindings.findChildViewById(view, i2);
        if (constraintLayout != null) {
            i2 = R.id.card1Fl;
            FrameLayout frameLayout = (FrameLayout) ViewBindings.findChildViewById(view, i2);
            if (frameLayout != null) {
                i2 = R.id.card2Cl;
                ConstraintLayout constraintLayout2 = (ConstraintLayout) ViewBindings.findChildViewById(view, i2);
                if (constraintLayout2 != null) {
                    i2 = R.id.card2Fl;
                    FrameLayout frameLayout2 = (FrameLayout) ViewBindings.findChildViewById(view, i2);
                    if (frameLayout2 != null) {
                        i2 = R.id.card3Cl;
                        ConstraintLayout constraintLayout3 = (ConstraintLayout) ViewBindings.findChildViewById(view, i2);
                        if (constraintLayout3 != null) {
                            i2 = R.id.card3Fl;
                            FrameLayout frameLayout3 = (FrameLayout) ViewBindings.findChildViewById(view, i2);
                            if (frameLayout3 != null) {
                                i2 = R.id.cardLl;
                                LinearLayout linearLayout = (LinearLayout) ViewBindings.findChildViewById(view, i2);
                                if (linearLayout != null) {
                                    i2 = R.id.img1V;
                                    ImageView imageView = (ImageView) ViewBindings.findChildViewById(view, i2);
                                    if (imageView != null) {
                                        i2 = R.id.img2V;
                                        ImageView imageView2 = (ImageView) ViewBindings.findChildViewById(view, i2);
                                        if (imageView2 != null) {
                                            i2 = R.id.img3V;
                                            ImageView imageView3 = (ImageView) ViewBindings.findChildViewById(view, i2);
                                            if (imageView3 != null) {
                                                i2 = R.id.maxNumTv;
                                                TextView textView = (TextView) ViewBindings.findChildViewById(view, i2);
                                                if (textView != null) {
                                                    i2 = R.id.name1Tv;
                                                    TextView textView2 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                    if (textView2 != null) {
                                                        i2 = R.id.name2Tv;
                                                        TextView textView3 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                        if (textView3 != null) {
                                                            i2 = R.id.name3Tv;
                                                            TextView textView4 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                            if (textView4 != null) {
                                                                i2 = R.id.resetTv;
                                                                TextView textView5 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                                if (textView5 != null) {
                                                                    i2 = R.id.starBar1;
                                                                    NiceRatingBar niceRatingBar = (NiceRatingBar) ViewBindings.findChildViewById(view, i2);
                                                                    if (niceRatingBar != null) {
                                                                        i2 = R.id.starBar2;
                                                                        NiceRatingBar niceRatingBar2 = (NiceRatingBar) ViewBindings.findChildViewById(view, i2);
                                                                        if (niceRatingBar2 != null) {
                                                                            i2 = R.id.starBar3;
                                                                            NiceRatingBar niceRatingBar3 = (NiceRatingBar) ViewBindings.findChildViewById(view, i2);
                                                                            if (niceRatingBar3 != null) {
                                                                                i2 = R.id.startTv;
                                                                                TextView textView6 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                                                if (textView6 != null) {
                                                                                    i2 = R.id.tipTv;
                                                                                    TextView textView7 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                                                    if (textView7 != null) {
                                                                                        i2 = R.id.videoFl;
                                                                                        FrameLayout frameLayout4 = (FrameLayout) ViewBindings.findChildViewById(view, i2);
                                                                                        if (frameLayout4 != null) {
                                                                                            i2 = R.id.videoImgv;
                                                                                            ImageView imageView4 = (ImageView) ViewBindings.findChildViewById(view, i2);
                                                                                            if (imageView4 != null) {
                                                                                                i2 = R.id.videoV;
                                                                                                FullVideoView fullVideoView = (FullVideoView) ViewBindings.findChildViewById(view, i2);
                                                                                                if (fullVideoView != null) {
                                                                                                    i2 = R.id.xImgv;
                                                                                                    ImageView imageView5 = (ImageView) ViewBindings.findChildViewById(view, i2);
                                                                                                    if (imageView5 != null) {
                                                                                                        return new ExamActivityRandomBinding((ConstraintLayout) view, constraintLayout, frameLayout, constraintLayout2, frameLayout2, constraintLayout3, frameLayout3, linearLayout, imageView, imageView2, imageView3, textView, textView2, textView3, textView4, textView5, niceRatingBar, niceRatingBar2, niceRatingBar3, textView6, textView7, frameLayout4, imageView4, fullVideoView, imageView5);
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
    public static ExamActivityRandomBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    @NonNull
    public static ExamActivityRandomBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z2) {
        View viewInflate = layoutInflater.inflate(R.layout.exam_activity_random, viewGroup, false);
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
