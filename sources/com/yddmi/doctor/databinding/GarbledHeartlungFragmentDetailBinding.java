package com.yddmi.doctor.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.lwkandroid.widget.ngv.NineGridView;
import com.noober.background.view.BLConstraintLayout;
import com.noober.background.view.BLTextView;
import com.yddmi.doctor.R;
import net.lucode.hackware.magicindicator.MagicIndicator;

/* loaded from: classes6.dex */
public final class GarbledHeartlungFragmentDetailBinding implements ViewBinding {

    @NonNull
    public final ImageView audioImgv;

    @NonNull
    public final ConstraintLayout bottomCl;

    @NonNull
    public final ConstraintLayout collectCl;

    @NonNull
    public final ImageView collectImgv;

    @NonNull
    public final TextView collectTv;

    @NonNull
    public final TextView currentTimeTv;

    @NonNull
    public final BLTextView detailTv;

    @NonNull
    public final ConstraintLayout errorCl;

    @NonNull
    public final ImageView errorImgv;

    @NonNull
    public final TextView errorTv;

    @NonNull
    public final BLTextView goBtv;

    @NonNull
    public final LinearLayout ll;

    @NonNull
    public final MagicIndicator magicIndicator;

    @NonNull
    public final ConstraintLayout mediaCl;

    @NonNull
    public final TextView nameTv;

    @NonNull
    public final NineGridView nine1GridView;

    @NonNull
    public final NineGridView nineGridView;

    @NonNull
    public final BLTextView numTv;

    @NonNull
    public final LinearLayout oneLl;

    @NonNull
    public final ImageView pauseImgv;

    @NonNull
    public final ImageView playImgv;

    @NonNull
    public final ConstraintLayout pointCl;

    @NonNull
    public final ImageView pointImgv;

    @NonNull
    private final ConstraintLayout rootView;

    @NonNull
    public final RecyclerView rv;

    @NonNull
    public final ScrollView scrollV;

    @NonNull
    public final SeekBar seekBar;

    @NonNull
    public final BLConstraintLayout topCl;

    @NonNull
    public final TextView totalTimeTv;

    private GarbledHeartlungFragmentDetailBinding(@NonNull ConstraintLayout constraintLayout, @NonNull ImageView imageView, @NonNull ConstraintLayout constraintLayout2, @NonNull ConstraintLayout constraintLayout3, @NonNull ImageView imageView2, @NonNull TextView textView, @NonNull TextView textView2, @NonNull BLTextView bLTextView, @NonNull ConstraintLayout constraintLayout4, @NonNull ImageView imageView3, @NonNull TextView textView3, @NonNull BLTextView bLTextView2, @NonNull LinearLayout linearLayout, @NonNull MagicIndicator magicIndicator, @NonNull ConstraintLayout constraintLayout5, @NonNull TextView textView4, @NonNull NineGridView nineGridView, @NonNull NineGridView nineGridView2, @NonNull BLTextView bLTextView3, @NonNull LinearLayout linearLayout2, @NonNull ImageView imageView4, @NonNull ImageView imageView5, @NonNull ConstraintLayout constraintLayout6, @NonNull ImageView imageView6, @NonNull RecyclerView recyclerView, @NonNull ScrollView scrollView, @NonNull SeekBar seekBar, @NonNull BLConstraintLayout bLConstraintLayout, @NonNull TextView textView5) {
        this.rootView = constraintLayout;
        this.audioImgv = imageView;
        this.bottomCl = constraintLayout2;
        this.collectCl = constraintLayout3;
        this.collectImgv = imageView2;
        this.collectTv = textView;
        this.currentTimeTv = textView2;
        this.detailTv = bLTextView;
        this.errorCl = constraintLayout4;
        this.errorImgv = imageView3;
        this.errorTv = textView3;
        this.goBtv = bLTextView2;
        this.ll = linearLayout;
        this.magicIndicator = magicIndicator;
        this.mediaCl = constraintLayout5;
        this.nameTv = textView4;
        this.nine1GridView = nineGridView;
        this.nineGridView = nineGridView2;
        this.numTv = bLTextView3;
        this.oneLl = linearLayout2;
        this.pauseImgv = imageView4;
        this.playImgv = imageView5;
        this.pointCl = constraintLayout6;
        this.pointImgv = imageView6;
        this.rv = recyclerView;
        this.scrollV = scrollView;
        this.seekBar = seekBar;
        this.topCl = bLConstraintLayout;
        this.totalTimeTv = textView5;
    }

    @NonNull
    public static GarbledHeartlungFragmentDetailBinding bind(@NonNull View view) {
        int i2 = R.id.audioImgv;
        ImageView imageView = (ImageView) ViewBindings.findChildViewById(view, i2);
        if (imageView != null) {
            i2 = R.id.bottomCl;
            ConstraintLayout constraintLayout = (ConstraintLayout) ViewBindings.findChildViewById(view, i2);
            if (constraintLayout != null) {
                i2 = R.id.collectCl;
                ConstraintLayout constraintLayout2 = (ConstraintLayout) ViewBindings.findChildViewById(view, i2);
                if (constraintLayout2 != null) {
                    i2 = R.id.collectImgv;
                    ImageView imageView2 = (ImageView) ViewBindings.findChildViewById(view, i2);
                    if (imageView2 != null) {
                        i2 = R.id.collectTv;
                        TextView textView = (TextView) ViewBindings.findChildViewById(view, i2);
                        if (textView != null) {
                            i2 = R.id.currentTimeTv;
                            TextView textView2 = (TextView) ViewBindings.findChildViewById(view, i2);
                            if (textView2 != null) {
                                i2 = R.id.detailTv;
                                BLTextView bLTextView = (BLTextView) ViewBindings.findChildViewById(view, i2);
                                if (bLTextView != null) {
                                    i2 = R.id.errorCl;
                                    ConstraintLayout constraintLayout3 = (ConstraintLayout) ViewBindings.findChildViewById(view, i2);
                                    if (constraintLayout3 != null) {
                                        i2 = R.id.errorImgv;
                                        ImageView imageView3 = (ImageView) ViewBindings.findChildViewById(view, i2);
                                        if (imageView3 != null) {
                                            i2 = R.id.errorTv;
                                            TextView textView3 = (TextView) ViewBindings.findChildViewById(view, i2);
                                            if (textView3 != null) {
                                                i2 = R.id.goBtv;
                                                BLTextView bLTextView2 = (BLTextView) ViewBindings.findChildViewById(view, i2);
                                                if (bLTextView2 != null) {
                                                    i2 = R.id.ll;
                                                    LinearLayout linearLayout = (LinearLayout) ViewBindings.findChildViewById(view, i2);
                                                    if (linearLayout != null) {
                                                        i2 = R.id.magicIndicator;
                                                        MagicIndicator magicIndicator = (MagicIndicator) ViewBindings.findChildViewById(view, i2);
                                                        if (magicIndicator != null) {
                                                            i2 = R.id.mediaCl;
                                                            ConstraintLayout constraintLayout4 = (ConstraintLayout) ViewBindings.findChildViewById(view, i2);
                                                            if (constraintLayout4 != null) {
                                                                i2 = R.id.nameTv;
                                                                TextView textView4 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                                if (textView4 != null) {
                                                                    i2 = R.id.nine1GridView;
                                                                    NineGridView nineGridView = (NineGridView) ViewBindings.findChildViewById(view, i2);
                                                                    if (nineGridView != null) {
                                                                        i2 = R.id.nineGridView;
                                                                        NineGridView nineGridView2 = (NineGridView) ViewBindings.findChildViewById(view, i2);
                                                                        if (nineGridView2 != null) {
                                                                            i2 = R.id.numTv;
                                                                            BLTextView bLTextView3 = (BLTextView) ViewBindings.findChildViewById(view, i2);
                                                                            if (bLTextView3 != null) {
                                                                                i2 = R.id.oneLl;
                                                                                LinearLayout linearLayout2 = (LinearLayout) ViewBindings.findChildViewById(view, i2);
                                                                                if (linearLayout2 != null) {
                                                                                    i2 = R.id.pauseImgv;
                                                                                    ImageView imageView4 = (ImageView) ViewBindings.findChildViewById(view, i2);
                                                                                    if (imageView4 != null) {
                                                                                        i2 = R.id.playImgv;
                                                                                        ImageView imageView5 = (ImageView) ViewBindings.findChildViewById(view, i2);
                                                                                        if (imageView5 != null) {
                                                                                            i2 = R.id.pointCl;
                                                                                            ConstraintLayout constraintLayout5 = (ConstraintLayout) ViewBindings.findChildViewById(view, i2);
                                                                                            if (constraintLayout5 != null) {
                                                                                                i2 = R.id.pointImgv;
                                                                                                ImageView imageView6 = (ImageView) ViewBindings.findChildViewById(view, i2);
                                                                                                if (imageView6 != null) {
                                                                                                    i2 = R.id.rv;
                                                                                                    RecyclerView recyclerView = (RecyclerView) ViewBindings.findChildViewById(view, i2);
                                                                                                    if (recyclerView != null) {
                                                                                                        i2 = R.id.scrollV;
                                                                                                        ScrollView scrollView = (ScrollView) ViewBindings.findChildViewById(view, i2);
                                                                                                        if (scrollView != null) {
                                                                                                            i2 = R.id.seekBar;
                                                                                                            SeekBar seekBar = (SeekBar) ViewBindings.findChildViewById(view, i2);
                                                                                                            if (seekBar != null) {
                                                                                                                i2 = R.id.topCl;
                                                                                                                BLConstraintLayout bLConstraintLayout = (BLConstraintLayout) ViewBindings.findChildViewById(view, i2);
                                                                                                                if (bLConstraintLayout != null) {
                                                                                                                    i2 = R.id.totalTimeTv;
                                                                                                                    TextView textView5 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                                                                                    if (textView5 != null) {
                                                                                                                        return new GarbledHeartlungFragmentDetailBinding((ConstraintLayout) view, imageView, constraintLayout, constraintLayout2, imageView2, textView, textView2, bLTextView, constraintLayout3, imageView3, textView3, bLTextView2, linearLayout, magicIndicator, constraintLayout4, textView4, nineGridView, nineGridView2, bLTextView3, linearLayout2, imageView4, imageView5, constraintLayout5, imageView6, recyclerView, scrollView, seekBar, bLConstraintLayout, textView5);
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
    public static GarbledHeartlungFragmentDetailBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    @NonNull
    public static GarbledHeartlungFragmentDetailBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z2) {
        View viewInflate = layoutInflater.inflate(R.layout.garbled_heartlung_fragment_detail, viewGroup, false);
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
