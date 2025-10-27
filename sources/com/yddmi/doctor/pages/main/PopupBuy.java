package com.yddmi.doctor.pages.main;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.catchpig.mvvm.ext.ContextExtKt;
import com.catchpig.mvvm.ext.SpanExtKt;
import com.catchpig.mvvm.ext.ViewExtKt;
import com.catchpig.utils.manager.ContextManager;
import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import com.lxj.xpopup.core.CenterPopupView;
import com.yddmi.doctor.R;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u0002\n\u0002\b\b\u0018\u00002\u00020\u0001:\u0001&B\u000f\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u001e\u0010\u0019\u001a\u00020\u00002\u0006\u0010\u001a\u001a\u00020\u00122\u0006\u0010\u001b\u001a\u00020\u000f2\u0006\u0010\u001c\u001a\u00020\u000fJ\b\u0010\u001d\u001a\u00020\u000fH\u0014J\b\u0010\u001e\u001a\u00020\u001fH\u0014J\u000e\u0010 \u001a\u00020\u001f2\u0006\u0010!\u001a\u00020\u0014J\b\u0010\"\u001a\u00020\u001fH\u0002J\u001a\u0010#\u001a\u00020\u001f2\u0006\u0010$\u001a\u00020\u000f2\n\b\u0002\u0010%\u001a\u0004\u0018\u00010\u0012R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\bX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\bX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000bX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0013\u001a\u0004\u0018\u00010\u0014X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u000bX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\bX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u000bX\u0082.¢\u0006\u0002\n\u0000¨\u0006'"}, d2 = {"Lcom/yddmi/doctor/pages/main/PopupBuy;", "Lcom/lxj/xpopup/core/CenterPopupView;", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "(Landroid/content/Context;)V", "closeImgv", "Landroid/widget/ImageView;", "codeBtnCl", "Landroidx/constraintlayout/widget/ConstraintLayout;", "codeCl", "detailTv", "Landroid/widget/TextView;", "errorCl", "integralTv", "mBuyIntegral", "", "mCurrentType", "mGoodsName", "", "mListener", "Lcom/yddmi/doctor/pages/main/PopupBuy$OnPopupBuyClickListener;", "mUserIntegral", "shareBtnTv", "succesCl", "tipSuccessTv", "buyGoodsNameSet", "name", "buyIntegral", "userIntegral", "getImplLayoutId", "onCreate", "", "setOnPopupBuyClickListener", ServiceSpecificExtraArgs.CastExtraArgs.LISTENER, "viewShow", "viewStatus", "type", "msg", "OnPopupBuyClickListener", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class PopupBuy extends CenterPopupView {
    private ImageView closeImgv;
    private ConstraintLayout codeBtnCl;
    private ConstraintLayout codeCl;
    private TextView detailTv;
    private ConstraintLayout errorCl;
    private TextView integralTv;
    private int mBuyIntegral;
    private int mCurrentType;

    @NotNull
    private String mGoodsName;

    @Nullable
    private OnPopupBuyClickListener mListener;
    private int mUserIntegral;
    private TextView shareBtnTv;
    private ConstraintLayout succesCl;
    private TextView tipSuccessTv;

    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0012\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005H&¨\u0006\u0006"}, d2 = {"Lcom/yddmi/doctor/pages/main/PopupBuy$OnPopupBuyClickListener;", "", "onClick", "", "view", "Landroid/view/View;", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public interface OnPopupBuyClickListener {
        void onClick(@Nullable View view);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PopupBuy(@NonNull @NotNull Context context) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
        this.mCurrentType = 100;
        this.mGoodsName = "";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$0(PopupBuy this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.dismiss();
        OnPopupBuyClickListener onPopupBuyClickListener = this$0.mListener;
        if (onPopupBuyClickListener != null) {
            ImageView imageView = this$0.closeImgv;
            if (imageView == null) {
                Intrinsics.throwUninitializedPropertyAccessException("closeImgv");
                imageView = null;
            }
            onPopupBuyClickListener.onClick(imageView);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$1(PopupBuy this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        OnPopupBuyClickListener onPopupBuyClickListener = this$0.mListener;
        if (onPopupBuyClickListener != null) {
            onPopupBuyClickListener.onClick(view);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$2(PopupBuy this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        OnPopupBuyClickListener onPopupBuyClickListener = this$0.mListener;
        if (onPopupBuyClickListener != null) {
            onPopupBuyClickListener.onClick(view);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$3(PopupBuy this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        OnPopupBuyClickListener onPopupBuyClickListener = this$0.mListener;
        if (onPopupBuyClickListener != null) {
            onPopupBuyClickListener.onClick(view);
        }
    }

    private final void viewShow() {
        TextView textView = this.detailTv;
        TextView textView2 = null;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("detailTv");
            textView = null;
        }
        ContextManager.Companion companion = ContextManager.INSTANCE;
        textView.setText(companion.getInstance().getContext().getString(R.string.home_buy_title) + "\n" + this.mGoodsName + "?");
        TextView textView3 = this.detailTv;
        if (textView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("detailTv");
            textView3 = null;
        }
        SpanExtKt.appendSizeSpan(textView3, "(", 0.7f);
        TextView textView4 = this.detailTv;
        if (textView4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("detailTv");
            textView4 = null;
        }
        int i2 = this.mBuyIntegral;
        TextView textView5 = this.detailTv;
        if (textView5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("detailTv");
            textView5 = null;
        }
        String str = i2 + textView5.getContext().getString(R.string.me_integral1);
        Context context = companion.getInstance().getContext();
        int i3 = R.color.c_f13a36;
        SpanExtKt.appendSizeColorSpan(textView4, str, 0.7f, ContextExtKt.getColorM(context, i3));
        TextView textView6 = this.detailTv;
        if (textView6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("detailTv");
            textView6 = null;
        }
        SpanExtKt.appendSizeSpan(textView6, ")", 0.7f);
        TextView textView7 = this.integralTv;
        if (textView7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("integralTv");
            textView7 = null;
        }
        textView7.append(companion.getInstance().getContext().getString(R.string.home_integral));
        TextView textView8 = this.integralTv;
        if (textView8 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("integralTv");
            textView8 = null;
        }
        SpanExtKt.appendColorSpan(textView8, String.valueOf(this.mUserIntegral), ContextExtKt.getColorM(companion.getInstance().getContext(), i3));
        TextView textView9 = this.integralTv;
        if (textView9 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("integralTv");
            textView9 = null;
        }
        textView9.append(companion.getInstance().getContext().getString(R.string.home_integral1));
        if (this.mUserIntegral < this.mBuyIntegral) {
            TextView textView10 = this.shareBtnTv;
            if (textView10 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("shareBtnTv");
                textView10 = null;
            }
            textView10.setText(companion.getInstance().getContext().getString(R.string.home_buy4));
        } else {
            TextView textView11 = this.shareBtnTv;
            if (textView11 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("shareBtnTv");
                textView11 = null;
            }
            textView11.setText(companion.getInstance().getContext().getString(R.string.home_buy3));
        }
        TextView textView12 = this.tipSuccessTv;
        if (textView12 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tipSuccessTv");
        } else {
            textView2 = textView12;
        }
        textView2.setText(companion.getInstance().getContext().getString(R.string.home_code_tip3, this.mGoodsName));
    }

    public static /* synthetic */ void viewStatus$default(PopupBuy popupBuy, int i2, String str, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            str = "";
        }
        popupBuy.viewStatus(i2, str);
    }

    @NotNull
    public final PopupBuy buyGoodsNameSet(@NotNull String name, int buyIntegral, int userIntegral) {
        Intrinsics.checkNotNullParameter(name, "name");
        this.mGoodsName = name;
        this.mBuyIntegral = buyIntegral;
        this.mUserIntegral = userIntegral;
        if (this.closeImgv != null) {
            viewShow();
        }
        return this;
    }

    @Override // com.lxj.xpopup.core.CenterPopupView, com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return R.layout.main_popup_buy;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() {
        ImageView imageView;
        TextView textView;
        super.onCreate();
        View viewFindViewById = findViewById(R.id.closeImgv);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(R.id.closeImgv)");
        this.closeImgv = (ImageView) viewFindViewById;
        View viewFindViewById2 = findViewById(R.id.codeCl);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "findViewById(R.id.codeCl)");
        this.codeCl = (ConstraintLayout) viewFindViewById2;
        View viewFindViewById3 = findViewById(R.id.errorCl);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById3, "findViewById(R.id.errorCl)");
        this.errorCl = (ConstraintLayout) viewFindViewById3;
        View viewFindViewById4 = findViewById(R.id.succesCl);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById4, "findViewById(R.id.succesCl)");
        this.succesCl = (ConstraintLayout) viewFindViewById4;
        View viewFindViewById5 = findViewById(R.id.codeBtnCl);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById5, "findViewById(R.id.codeBtnCl)");
        this.codeBtnCl = (ConstraintLayout) viewFindViewById5;
        View viewFindViewById6 = findViewById(R.id.shareBtnTv);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById6, "findViewById(R.id.shareBtnTv)");
        this.shareBtnTv = (TextView) viewFindViewById6;
        View viewFindViewById7 = findViewById(R.id.detailTv);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById7, "findViewById(R.id.detailTv)");
        this.detailTv = (TextView) viewFindViewById7;
        View viewFindViewById8 = findViewById(R.id.integralTv);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById8, "findViewById(R.id.integralTv)");
        this.integralTv = (TextView) viewFindViewById8;
        View viewFindViewById9 = findViewById(R.id.tipSuccessTv);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById9, "findViewById(R.id.tipSuccessTv)");
        this.tipSuccessTv = (TextView) viewFindViewById9;
        ImageView imageView2 = this.closeImgv;
        if (imageView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("closeImgv");
            imageView = null;
        } else {
            imageView = imageView2;
        }
        ViewExtKt.setDebounceClickListener$default(imageView, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.main.u
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                PopupBuy.onCreate$lambda$0(this.f25949c, view);
            }
        }, 0L, 2, null);
        View viewFindViewById10 = findViewById(R.id.buyBtnTv);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById10, "findViewById<TextView>(R.id.buyBtnTv)");
        ViewExtKt.setDebounceClickListener$default(viewFindViewById10, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.main.v
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                PopupBuy.onCreate$lambda$1(this.f25950c, view);
            }
        }, 0L, 2, null);
        TextView textView2 = this.shareBtnTv;
        if (textView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("shareBtnTv");
            textView = null;
        } else {
            textView = textView2;
        }
        ViewExtKt.setDebounceClickListener$default(textView, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.main.w
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                PopupBuy.onCreate$lambda$2(this.f25951c, view);
            }
        }, 0L, 2, null);
        View viewFindViewById11 = findViewById(R.id.goTv);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById11, "findViewById<TextView>(R.id.goTv)");
        ViewExtKt.setDebounceClickListener$default(viewFindViewById11, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.main.x
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                PopupBuy.onCreate$lambda$3(this.f25952c, view);
            }
        }, 0L, 2, null);
        viewShow();
    }

    public final void setOnPopupBuyClickListener(@NotNull OnPopupBuyClickListener listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        this.mListener = listener;
    }

    public final void viewStatus(int type, @Nullable String msg) {
        this.mCurrentType = type;
        if (this.closeImgv == null) {
        }
        ConstraintLayout constraintLayout = null;
        switch (type) {
            case 100:
                ConstraintLayout constraintLayout2 = this.codeCl;
                if (constraintLayout2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("codeCl");
                    constraintLayout2 = null;
                }
                constraintLayout2.setVisibility(0);
                ConstraintLayout constraintLayout3 = this.errorCl;
                if (constraintLayout3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("errorCl");
                    constraintLayout3 = null;
                }
                constraintLayout3.setVisibility(8);
                ConstraintLayout constraintLayout4 = this.succesCl;
                if (constraintLayout4 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("succesCl");
                    constraintLayout4 = null;
                }
                constraintLayout4.setVisibility(8);
                ImageView imageView = this.closeImgv;
                if (imageView == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("closeImgv");
                    imageView = null;
                }
                imageView.setVisibility(0);
                ConstraintLayout constraintLayout5 = this.codeBtnCl;
                if (constraintLayout5 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("codeBtnCl");
                } else {
                    constraintLayout = constraintLayout5;
                }
                constraintLayout.setVisibility(0);
                break;
            case 101:
                ConstraintLayout constraintLayout6 = this.codeCl;
                if (constraintLayout6 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("codeCl");
                    constraintLayout6 = null;
                }
                constraintLayout6.setVisibility(8);
                ConstraintLayout constraintLayout7 = this.errorCl;
                if (constraintLayout7 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("errorCl");
                    constraintLayout7 = null;
                }
                constraintLayout7.setVisibility(0);
                ConstraintLayout constraintLayout8 = this.succesCl;
                if (constraintLayout8 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("succesCl");
                    constraintLayout8 = null;
                }
                constraintLayout8.setVisibility(8);
                ImageView imageView2 = this.closeImgv;
                if (imageView2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("closeImgv");
                    imageView2 = null;
                }
                imageView2.setVisibility(0);
                ConstraintLayout constraintLayout9 = this.codeBtnCl;
                if (constraintLayout9 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("codeBtnCl");
                } else {
                    constraintLayout = constraintLayout9;
                }
                constraintLayout.setVisibility(8);
                break;
            case 102:
                ConstraintLayout constraintLayout10 = this.codeCl;
                if (constraintLayout10 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("codeCl");
                    constraintLayout10 = null;
                }
                constraintLayout10.setVisibility(8);
                ConstraintLayout constraintLayout11 = this.errorCl;
                if (constraintLayout11 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("errorCl");
                    constraintLayout11 = null;
                }
                constraintLayout11.setVisibility(8);
                ConstraintLayout constraintLayout12 = this.succesCl;
                if (constraintLayout12 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("succesCl");
                    constraintLayout12 = null;
                }
                constraintLayout12.setVisibility(0);
                ImageView imageView3 = this.closeImgv;
                if (imageView3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("closeImgv");
                    imageView3 = null;
                }
                imageView3.setVisibility(0);
                ConstraintLayout constraintLayout13 = this.codeBtnCl;
                if (constraintLayout13 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("codeBtnCl");
                } else {
                    constraintLayout = constraintLayout13;
                }
                constraintLayout.setVisibility(8);
                break;
        }
    }
}
