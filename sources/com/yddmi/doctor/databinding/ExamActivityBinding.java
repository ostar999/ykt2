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
import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.helper.widget.Flow;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.angcyo.tablayout.DslTabLayout;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.sunfusheng.marqueeview.MarqueeView;
import com.yddmi.doctor.R;
import net.center.blurview.ShapeBlurView;

/* loaded from: classes6.dex */
public final class ExamActivityBinding implements ViewBinding {

    @NonNull
    public final ImageView backImgv;

    @NonNull
    public final ImageView bgImgv;

    @NonNull
    public final ShapeBlurView blurView;

    @NonNull
    public final ConstraintLayout btnCl;

    @NonNull
    public final DslTabLayout dslTablayout;

    @NonNull
    public final TextView go24Tv;

    @NonNull
    public final TextView goBodyTv;

    @NonNull
    public final ConstraintLayout historyCl;

    @NonNull
    public final FrameLayout historyFl;

    @NonNull
    public final ImageView historyImgv;

    @NonNull
    public final Flow iconFlow;

    @NonNull
    public final RecyclerView leftRv;

    @NonNull
    public final View lineV;

    @NonNull
    public final ConstraintLayout margueeCl;

    @NonNull
    public final MarqueeView margueeV;

    @NonNull
    public final TextView maxNumTv;

    @NonNull
    public final TextView nameTv;

    @NonNull
    public final ConstraintLayout popCl;

    @NonNull
    public final ImageView random24Imgv;

    @NonNull
    public final ImageView randomBodyImgv;

    @NonNull
    public final RecyclerView rightRv;

    @NonNull
    private final FrameLayout rootView;

    @NonNull
    public final ConstraintLayout ruleCl;

    @NonNull
    public final ImageView ruleImgv;

    @NonNull
    public final RecyclerView rv;

    @NonNull
    public final TextView scoreTv;

    @NonNull
    public final SmartRefreshLayout srl;

    @NonNull
    public final TextView timeTv;

    @NonNull
    public final ConstraintLayout tipCl;

    @NonNull
    public final ImageView tipImgv;

    @NonNull
    public final TextView tipTv;

    @NonNull
    public final ConstraintLayout titleCl;

    @NonNull
    public final ConstraintLayout topCl;

    @NonNull
    public final ImageView topImgv;

    @NonNull
    public final ConstraintLayout topPopCl;

    @NonNull
    public final ImageView topTitleImgv;

    @NonNull
    public final TextView topTv;

    @NonNull
    public final LinearLayout topTypeLl;

    @Nullable
    public final ConstraintLayout twoCl;

    @NonNull
    public final ConstraintLayout userCl;

    @NonNull
    public final ImageView userImgv;

    @NonNull
    public final View view;

    @NonNull
    public final AppCompatImageView x1Imgv;

    @NonNull
    public final AppCompatImageView xImgv;

    private ExamActivityBinding(@NonNull FrameLayout frameLayout, @NonNull ImageView imageView, @NonNull ImageView imageView2, @NonNull ShapeBlurView shapeBlurView, @NonNull ConstraintLayout constraintLayout, @NonNull DslTabLayout dslTabLayout, @NonNull TextView textView, @NonNull TextView textView2, @NonNull ConstraintLayout constraintLayout2, @NonNull FrameLayout frameLayout2, @NonNull ImageView imageView3, @NonNull Flow flow, @NonNull RecyclerView recyclerView, @NonNull View view, @NonNull ConstraintLayout constraintLayout3, @NonNull MarqueeView marqueeView, @NonNull TextView textView3, @NonNull TextView textView4, @NonNull ConstraintLayout constraintLayout4, @NonNull ImageView imageView4, @NonNull ImageView imageView5, @NonNull RecyclerView recyclerView2, @NonNull ConstraintLayout constraintLayout5, @NonNull ImageView imageView6, @NonNull RecyclerView recyclerView3, @NonNull TextView textView5, @NonNull SmartRefreshLayout smartRefreshLayout, @NonNull TextView textView6, @NonNull ConstraintLayout constraintLayout6, @NonNull ImageView imageView7, @NonNull TextView textView7, @NonNull ConstraintLayout constraintLayout7, @NonNull ConstraintLayout constraintLayout8, @NonNull ImageView imageView8, @NonNull ConstraintLayout constraintLayout9, @NonNull ImageView imageView9, @NonNull TextView textView8, @NonNull LinearLayout linearLayout, @Nullable ConstraintLayout constraintLayout10, @NonNull ConstraintLayout constraintLayout11, @NonNull ImageView imageView10, @NonNull View view2, @NonNull AppCompatImageView appCompatImageView, @NonNull AppCompatImageView appCompatImageView2) {
        this.rootView = frameLayout;
        this.backImgv = imageView;
        this.bgImgv = imageView2;
        this.blurView = shapeBlurView;
        this.btnCl = constraintLayout;
        this.dslTablayout = dslTabLayout;
        this.go24Tv = textView;
        this.goBodyTv = textView2;
        this.historyCl = constraintLayout2;
        this.historyFl = frameLayout2;
        this.historyImgv = imageView3;
        this.iconFlow = flow;
        this.leftRv = recyclerView;
        this.lineV = view;
        this.margueeCl = constraintLayout3;
        this.margueeV = marqueeView;
        this.maxNumTv = textView3;
        this.nameTv = textView4;
        this.popCl = constraintLayout4;
        this.random24Imgv = imageView4;
        this.randomBodyImgv = imageView5;
        this.rightRv = recyclerView2;
        this.ruleCl = constraintLayout5;
        this.ruleImgv = imageView6;
        this.rv = recyclerView3;
        this.scoreTv = textView5;
        this.srl = smartRefreshLayout;
        this.timeTv = textView6;
        this.tipCl = constraintLayout6;
        this.tipImgv = imageView7;
        this.tipTv = textView7;
        this.titleCl = constraintLayout7;
        this.topCl = constraintLayout8;
        this.topImgv = imageView8;
        this.topPopCl = constraintLayout9;
        this.topTitleImgv = imageView9;
        this.topTv = textView8;
        this.topTypeLl = linearLayout;
        this.twoCl = constraintLayout10;
        this.userCl = constraintLayout11;
        this.userImgv = imageView10;
        this.view = view2;
        this.x1Imgv = appCompatImageView;
        this.xImgv = appCompatImageView2;
    }

    @NonNull
    public static ExamActivityBinding bind(@NonNull View view) {
        View viewFindChildViewById;
        View viewFindChildViewById2;
        int i2 = R.id.backImgv;
        ImageView imageView = (ImageView) ViewBindings.findChildViewById(view, i2);
        if (imageView != null) {
            i2 = R.id.bgImgv;
            ImageView imageView2 = (ImageView) ViewBindings.findChildViewById(view, i2);
            if (imageView2 != null) {
                i2 = R.id.blurView;
                ShapeBlurView shapeBlurView = (ShapeBlurView) ViewBindings.findChildViewById(view, i2);
                if (shapeBlurView != null) {
                    i2 = R.id.btnCl;
                    ConstraintLayout constraintLayout = (ConstraintLayout) ViewBindings.findChildViewById(view, i2);
                    if (constraintLayout != null) {
                        i2 = R.id.dslTablayout;
                        DslTabLayout dslTabLayout = (DslTabLayout) ViewBindings.findChildViewById(view, i2);
                        if (dslTabLayout != null) {
                            i2 = R.id.go24Tv;
                            TextView textView = (TextView) ViewBindings.findChildViewById(view, i2);
                            if (textView != null) {
                                i2 = R.id.goBodyTv;
                                TextView textView2 = (TextView) ViewBindings.findChildViewById(view, i2);
                                if (textView2 != null) {
                                    i2 = R.id.historyCl;
                                    ConstraintLayout constraintLayout2 = (ConstraintLayout) ViewBindings.findChildViewById(view, i2);
                                    if (constraintLayout2 != null) {
                                        i2 = R.id.historyFl;
                                        FrameLayout frameLayout = (FrameLayout) ViewBindings.findChildViewById(view, i2);
                                        if (frameLayout != null) {
                                            i2 = R.id.historyImgv;
                                            ImageView imageView3 = (ImageView) ViewBindings.findChildViewById(view, i2);
                                            if (imageView3 != null) {
                                                i2 = R.id.iconFlow;
                                                Flow flow = (Flow) ViewBindings.findChildViewById(view, i2);
                                                if (flow != null) {
                                                    i2 = R.id.leftRv;
                                                    RecyclerView recyclerView = (RecyclerView) ViewBindings.findChildViewById(view, i2);
                                                    if (recyclerView != null && (viewFindChildViewById = ViewBindings.findChildViewById(view, (i2 = R.id.lineV))) != null) {
                                                        i2 = R.id.margueeCl;
                                                        ConstraintLayout constraintLayout3 = (ConstraintLayout) ViewBindings.findChildViewById(view, i2);
                                                        if (constraintLayout3 != null) {
                                                            i2 = R.id.margueeV;
                                                            MarqueeView marqueeView = (MarqueeView) ViewBindings.findChildViewById(view, i2);
                                                            if (marqueeView != null) {
                                                                i2 = R.id.maxNumTv;
                                                                TextView textView3 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                                if (textView3 != null) {
                                                                    i2 = R.id.nameTv;
                                                                    TextView textView4 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                                    if (textView4 != null) {
                                                                        i2 = R.id.popCl;
                                                                        ConstraintLayout constraintLayout4 = (ConstraintLayout) ViewBindings.findChildViewById(view, i2);
                                                                        if (constraintLayout4 != null) {
                                                                            i2 = R.id.random24Imgv;
                                                                            ImageView imageView4 = (ImageView) ViewBindings.findChildViewById(view, i2);
                                                                            if (imageView4 != null) {
                                                                                i2 = R.id.randomBodyImgv;
                                                                                ImageView imageView5 = (ImageView) ViewBindings.findChildViewById(view, i2);
                                                                                if (imageView5 != null) {
                                                                                    i2 = R.id.rightRv;
                                                                                    RecyclerView recyclerView2 = (RecyclerView) ViewBindings.findChildViewById(view, i2);
                                                                                    if (recyclerView2 != null) {
                                                                                        i2 = R.id.ruleCl;
                                                                                        ConstraintLayout constraintLayout5 = (ConstraintLayout) ViewBindings.findChildViewById(view, i2);
                                                                                        if (constraintLayout5 != null) {
                                                                                            i2 = R.id.ruleImgv;
                                                                                            ImageView imageView6 = (ImageView) ViewBindings.findChildViewById(view, i2);
                                                                                            if (imageView6 != null) {
                                                                                                i2 = R.id.rv;
                                                                                                RecyclerView recyclerView3 = (RecyclerView) ViewBindings.findChildViewById(view, i2);
                                                                                                if (recyclerView3 != null) {
                                                                                                    i2 = R.id.scoreTv;
                                                                                                    TextView textView5 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                                                                    if (textView5 != null) {
                                                                                                        i2 = R.id.srl;
                                                                                                        SmartRefreshLayout smartRefreshLayout = (SmartRefreshLayout) ViewBindings.findChildViewById(view, i2);
                                                                                                        if (smartRefreshLayout != null) {
                                                                                                            i2 = R.id.timeTv;
                                                                                                            TextView textView6 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                                                                            if (textView6 != null) {
                                                                                                                i2 = R.id.tipCl;
                                                                                                                ConstraintLayout constraintLayout6 = (ConstraintLayout) ViewBindings.findChildViewById(view, i2);
                                                                                                                if (constraintLayout6 != null) {
                                                                                                                    i2 = R.id.tipImgv;
                                                                                                                    ImageView imageView7 = (ImageView) ViewBindings.findChildViewById(view, i2);
                                                                                                                    if (imageView7 != null) {
                                                                                                                        i2 = R.id.tipTv;
                                                                                                                        TextView textView7 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                                                                                        if (textView7 != null) {
                                                                                                                            i2 = R.id.titleCl;
                                                                                                                            ConstraintLayout constraintLayout7 = (ConstraintLayout) ViewBindings.findChildViewById(view, i2);
                                                                                                                            if (constraintLayout7 != null) {
                                                                                                                                i2 = R.id.topCl;
                                                                                                                                ConstraintLayout constraintLayout8 = (ConstraintLayout) ViewBindings.findChildViewById(view, i2);
                                                                                                                                if (constraintLayout8 != null) {
                                                                                                                                    i2 = R.id.topImgv;
                                                                                                                                    ImageView imageView8 = (ImageView) ViewBindings.findChildViewById(view, i2);
                                                                                                                                    if (imageView8 != null) {
                                                                                                                                        i2 = R.id.topPopCl;
                                                                                                                                        ConstraintLayout constraintLayout9 = (ConstraintLayout) ViewBindings.findChildViewById(view, i2);
                                                                                                                                        if (constraintLayout9 != null) {
                                                                                                                                            i2 = R.id.topTitleImgv;
                                                                                                                                            ImageView imageView9 = (ImageView) ViewBindings.findChildViewById(view, i2);
                                                                                                                                            if (imageView9 != null) {
                                                                                                                                                i2 = R.id.topTv;
                                                                                                                                                TextView textView8 = (TextView) ViewBindings.findChildViewById(view, i2);
                                                                                                                                                if (textView8 != null) {
                                                                                                                                                    i2 = R.id.topTypeLl;
                                                                                                                                                    LinearLayout linearLayout = (LinearLayout) ViewBindings.findChildViewById(view, i2);
                                                                                                                                                    if (linearLayout != null) {
                                                                                                                                                        ConstraintLayout constraintLayout10 = (ConstraintLayout) ViewBindings.findChildViewById(view, R.id.twoCl);
                                                                                                                                                        i2 = R.id.userCl;
                                                                                                                                                        ConstraintLayout constraintLayout11 = (ConstraintLayout) ViewBindings.findChildViewById(view, i2);
                                                                                                                                                        if (constraintLayout11 != null) {
                                                                                                                                                            i2 = R.id.userImgv;
                                                                                                                                                            ImageView imageView10 = (ImageView) ViewBindings.findChildViewById(view, i2);
                                                                                                                                                            if (imageView10 != null && (viewFindChildViewById2 = ViewBindings.findChildViewById(view, (i2 = R.id.view))) != null) {
                                                                                                                                                                i2 = R.id.x1Imgv;
                                                                                                                                                                AppCompatImageView appCompatImageView = (AppCompatImageView) ViewBindings.findChildViewById(view, i2);
                                                                                                                                                                if (appCompatImageView != null) {
                                                                                                                                                                    i2 = R.id.xImgv;
                                                                                                                                                                    AppCompatImageView appCompatImageView2 = (AppCompatImageView) ViewBindings.findChildViewById(view, i2);
                                                                                                                                                                    if (appCompatImageView2 != null) {
                                                                                                                                                                        return new ExamActivityBinding((FrameLayout) view, imageView, imageView2, shapeBlurView, constraintLayout, dslTabLayout, textView, textView2, constraintLayout2, frameLayout, imageView3, flow, recyclerView, viewFindChildViewById, constraintLayout3, marqueeView, textView3, textView4, constraintLayout4, imageView4, imageView5, recyclerView2, constraintLayout5, imageView6, recyclerView3, textView5, smartRefreshLayout, textView6, constraintLayout6, imageView7, textView7, constraintLayout7, constraintLayout8, imageView8, constraintLayout9, imageView9, textView8, linearLayout, constraintLayout10, constraintLayout11, imageView10, viewFindChildViewById2, appCompatImageView, appCompatImageView2);
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
    public static ExamActivityBinding inflate(@NonNull LayoutInflater layoutInflater) {
        return inflate(layoutInflater, null, false);
    }

    @NonNull
    public static ExamActivityBinding inflate(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, boolean z2) {
        View viewInflate = layoutInflater.inflate(R.layout.exam_activity, viewGroup, false);
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
