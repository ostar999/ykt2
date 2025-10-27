package com.yddmi.doctor.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.noober.background.view.BLConstraintLayout;
import com.noober.background.view.BLImageView;
import com.noober.background.view.BLTextView;
import com.yddmi.doctor.R;
import com.zhpan.bannerview.BannerViewPager;
import com.zhpan.indicator.IndicatorView;

/* loaded from: classes6.dex */
public final class ProductActivityBinding implements ViewBinding {

    @NonNull
    public final BLConstraintLayout aliCl;

    @NonNull
    public final ImageView aliImgv;

    @NonNull
    public final BLImageView backImgv;

    @NonNull
    public final BannerViewPager banner;

    @NonNull
    public final BLConstraintLayout bottomCl;

    @NonNull
    public final TextView detailsTv;

    @NonNull
    public final ConstraintLayout goodsCl;

    @NonNull
    public final ImageView goodsImgv;

    @NonNull
    public final IndicatorView indicatorV;

    @NonNull
    public final TextView maxMoneyTv;

    @NonNull
    public final ConstraintLayout moneyCl;

    @NonNull
    public final TextView moneyTv;

    @NonNull
    public final ImageView payTypeImgv;

    @NonNull
    public final ImageView rightImgv;

    @NonNull
    private final FrameLayout rootView;

    @NonNull
    public final RecyclerView rv;

    @NonNull
    public final FrameLayout rvFl;

    @NonNull
    public final TextView saleTip1Tv;

    @NonNull
    public final TextView saleTipTv;

    @NonNull
    public final TextView salesNumTv;

    @NonNull
    public final ImageView selectAliImgv;

    @NonNull
    public final ImageView selectWxImgv;

    @NonNull
    public final ImageView stateImgv;

    @NonNull
    public final ConstraintLayout ticketCl;

    @NonNull
    public final ImageView ticketImgv;

    @NonNull
    public final BLTextView ticketNameTv;

    @NonNull
    public final TextView ticketTv;

    @NonNull
    public final TextView tip1Tv;

    @NonNull
    public final ImageView tipImgv;

    @NonNull
    public final TextView tipTv;

    /* renamed from: tv, reason: collision with root package name */
    @NonNull
    public final TextView f25744tv;

    @NonNull
    public final ImageView typeImgv;

    @NonNull
    public final BLTextView unlockTv;

    @NonNull
    public final BLConstraintLayout wxCl;

    @NonNull
    public final ImageView wxImgv;

    private ProductActivityBinding(@NonNull FrameLayout frameLayout, @NonNull BLConstraintLayout bLConstraintLayout, @NonNull ImageView imageView, @NonNull BLImageView bLImageView, @NonNull BannerViewPager bannerViewPager, @NonNull BLConstraintLayout bLConstraintLayout2, @NonNull TextView textView, @NonNull ConstraintLayout constraintLayout, @NonNull ImageView imageView2, @NonNull IndicatorView indicatorView, @NonNull TextView textView2, @NonNull ConstraintLayout constraintLayout2, @NonNull TextView textView3, @NonNull ImageView imageView3, @NonNull ImageView imageView4, @NonNull RecyclerView recyclerView, @NonNull FrameLayout frameLayout2, @NonNull TextView textView4, @NonNull TextView textView5, @NonNull TextView textView6, @NonNull ImageView imageView5, @NonNull ImageView imageView6, @NonNull ImageView imageView7, @NonNull ConstraintLayout constraintLayout3, @NonNull ImageView imageView8, @NonNull BLTextView bLTextView, @NonNull TextView textView7, @NonNull TextView textView8, @NonNull ImageView imageView9, @NonNull TextView textView9, @NonNull TextView textView10, @NonNull ImageView imageView10, @NonNull BLTextView bLTextView2, @NonNull BLConstraintLayout bLConstraintLayout3, @NonNull ImageView imageView11) {
        this.rootView = frameLayout;
        this.aliCl = bLConstraintLayout;
        this.aliImgv = imageView;
        this.backImgv = bLImageView;
        this.banner = bannerViewPager;
        this.bottomCl = bLConstraintLayout2;
        this.detailsTv = textView;
        this.goodsCl = constraintLayout;
        this.goodsImgv = imageView2;
        this.indicatorV = indicatorView;
        this.maxMoneyTv = textView2;
        this.moneyCl = constraintLayout2;
        this.moneyTv = textView3;
        this.payTypeImgv = imageView3;
        this.rightImgv = imageView4;
        this.rv = recyclerView;
        this.rvFl = frameLayout2;
        this.saleTip1Tv = textView4;
        this.saleTipTv = textView5;
        this.salesNumTv = textView6;
        this.selectAliImgv = imageView5;
        this.selectWxImgv = imageView6;
        this.stateImgv = imageView7;
        this.ticketCl = constraintLayout3;
        this.ticketImgv = imageView8;
        this.ticketNameTv = bLTextView;
        this.ticketTv = textView7;
        this.tip1Tv = textView8;
        this.tipImgv = imageView9;
        this.tipTv = textView9;
        this.f25744tv = textView10;
        this.typeImgv = imageView10;
        this.unlockTv = bLTextView2;
        this.wxCl = bLConstraintLayout3;
        this.wxImgv = imageView11;
    }

    @NonNull
    public static ProductActivityBinding bind(@NonNull View view) {
        int i2 = R.id.aliCl;
        BLConstraintLayout bLConstraintLayout = (BLConstraintLayout) ViewBindings.findChildViewById(view, i2);
        if (bLConstraintLayout != null) {
            i2 = R.id.aliImgv;
            ImageView imageView = (ImageView) ViewBindings.findChildViewById(view, i2);
            if (imageView != null) {
                i2 = R.id.backImgv;
                BLImageView bLImageView = (BLImageView) ViewBindings.findChildViewById(view, i2);
                if (bLImageView != null) {
                    i2 = R.id.banner;
                    BannerViewPager bannerViewPager = (BannerViewPager) ViewBindings.findChildViewById(view, i2);
                    if (bannerViewPager != null) {
                        i2 = R.id.bottomCl;
                        BLConstraintLayout bLConstraintLayout2 = (BLConstraintLayout) ViewBindings.findChildViewById(view, i2);
                        if (bLConstraintLayout2 != null) {
                            i2 = R.id.detailsTv;
                            TextView textView = (TextView) ViewBindings.findChildViewById(view, i2);
                            if (textView != null) {
                                i2 = R.id.goodsCl;
                                ConstraintLayout constraintLayout = (ConstraintLayout) ViewBindings.findChildViewById(view, i2);
                                if (constraintLayout != null) {
                                    i2 = R.id.goodsImgv;
                                    ImageView imageView2 = (ImageView) ViewBindings.findChildViewById(view, i2);
                                    if (imageView2 != null) {
                                        i2 = R.id.indicatorV;
                                        IndicatorView indicatorView = (IndicatorView) ViewBindings.findChildViewById(view, i2);
                                        if (indicatorView != null) {
                                            i2 = R.id.maxMoneyTv;
                                            TextView textView2 = (TextView) ViewBindings.findChildViewById(view, i2);
                                            if (textView2 != null) {
                                                i2 = R.id.moneyCl;
                                                ConstraintLayout constraintLayout2 = (ConstraintLayout) ViewBindings.findChildViewById(view, i2);
                                                if (constraintLayout2 != null) {
                                                    i2 = R.id.moneyTv;
                                                    TextView textView3 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                    if (textView3 != null) {
                                                        i2 = R.id.payTypeImgv;
                                                        ImageView imageView3 = (ImageView) ViewBindings.findChildViewById(view, i2);
                                                        if (imageView3 != null) {
                                                            i2 = R.id.rightImgv;
                                                            ImageView imageView4 = (ImageView) ViewBindings.findChildViewById(view, i2);
                                                            if (imageView4 != null) {
                                                                i2 = R.id.rv;
                                                                RecyclerView recyclerView = (RecyclerView) ViewBindings.findChildViewById(view, i2);
                                                                if (recyclerView != null) {
                                                                    i2 = R.id.rvFl;
                                                                    FrameLayout frameLayout = (FrameLayout) ViewBindings.findChildViewById(view, i2);
                                                                    if (frameLayout != null) {
                                                                        i2 = R.id.saleTip1Tv;
                                                                        TextView textView4 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                                        if (textView4 != null) {
                                                                            i2 = R.id.saleTipTv;
                                                                            TextView textView5 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                                            if (textView5 != null) {
                                                                                i2 = R.id.salesNumTv;
                                                                                TextView textView6 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                                                if (textView6 != null) {
                                                                                    i2 = R.id.selectAliImgv;
                                                                                    ImageView imageView5 = (ImageView) ViewBindings.findChildViewById(view, i2);
                                                                                    if (imageView5 != null) {
                                                                                        i2 = R.id.selectWxImgv;
                                                                                        ImageView imageView6 = (ImageView) ViewBindings.findChildViewById(view, i2);
                                                                                        if (imageView6 != null) {
                                                                                            i2 = R.id.stateImgv;
                                                                                            ImageView imageView7 = (ImageView) ViewBindings.findChildViewById(view, i2);
                                                                                            if (imageView7 != null) {
                                                                                                i2 = R.id.ticketCl;
                                                                                                ConstraintLayout constraintLayout3 = (ConstraintLayout) ViewBindings.findChildViewById(view, i2);
                                                                                                if (constraintLayout3 != null) {
                                                                                                    i2 = R.id.ticketImgv;
                                                                                                    ImageView imageView8 = (ImageView) ViewBindings.findChildViewById(view, i2);
                                                                                                    if (imageView8 != null) {
                                                                                                        i2 = R.id.ticketNameTv;
                                                                                                        BLTextView bLTextView = (BLTextView) ViewBindings.findChildViewById(view, i2);
                                                                                                        if (bLTextView != null) {
                                                                                                            i2 = R.id.ticketTv;
                                                                                                            TextView textView7 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                                                                            if (textView7 != null) {
                                                                                                                i2 = R.id.tip1Tv;
                                                                                                                TextView textView8 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                                                                                if (textView8 != null) {
                                                                                                                    i2 = R.id.tipImgv;
                                                                                                                    ImageView imageView9 = (ImageView) ViewBindings.findChildViewById(view, i2);
                                                                                                                    if (imageView9 != null) {
                                                                                                                        i2 = R.id.tipTv;
                                                                                                                        TextView textView9 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                                                                                        if (textView9 != null) {
                                                                                                                            i2 = R.id.f25733tv;
                                                                                                                            TextView textView10 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                                                                                            if (textView10 != null) {
                                                                                                                                i2 = R.id.typeImgv;
                                                                                                                                ImageView imageView10 = (ImageView) ViewBindings.findChildViewById(view, i2);
                                                                                                                                if (imageView10 != null) {
                                                                                                                                    i2 = R.id.unlockTv;
                                                                                                                                    BLTextView bLTextView2 = (BLTextView) ViewBindings.findChildViewById(view, i2);
                                                                                                                                    if (bLTextView2 != null) {
                                                                                                                                        i2 = R.id.wxCl;
                                                                                                                                        BLConstraintLayout bLConstraintLayout3 = (BLConstraintLayout) ViewBindings.findChildViewById(view, i2);
                                                                                                                                        if (bLConstraintLayout3 != null) {
                                                                                                                                            i2 = R.id.wxImgv;
                                                                                                                                            ImageView imageView11 = (ImageView) ViewBindings.findChildViewById(view, i2);
                                                                                                                                            if (imageView11 != null) {
                                                                                                                                                return new ProductActivityBinding((FrameLayout) view, bLConstraintLayout, imageView, bLImageView, bannerViewPager, bLConstraintLayout2, textView, constraintLayout, imageView2, indicatorView, textView2, constraintLayout2, textView3, imageView3, imageView4, recyclerView, frameLayout, textView4, textView5, textView6, imageView5, imageView6, imageView7, constraintLayout3, imageView8, bLTextView, textView7, textView8, imageView9, textView9, textView10, imageView10, bLTextView2, bLConstraintLayout3, imageView11);
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
    public static ProductActivityBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    @NonNull
    public static ProductActivityBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z2) {
        View viewInflate = layoutInflater.inflate(R.layout.product_activity, viewGroup, false);
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
