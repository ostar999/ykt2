package com.yddmi.doctor.pages.product;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.catchpig.mvvm.ext.ViewExtKt;
import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import com.lxj.xpopup.core.BottomPopupView;
import com.plv.foundationsdk.rx.PLVRxEncryptDataFunction;
import com.yddmi.doctor.R;
import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Deprecated(message = "已废")
@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001:\u0001\u001eB\u000f\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u0013\u001a\u00020\u000fH\u0014J\b\u0010\u0014\u001a\u00020\u0015H\u0014J\u0016\u0010\u0016\u001a\u00020\u00152\u0006\u0010\u0017\u001a\u00020\n2\u0006\u0010\u0018\u001a\u00020\nJ\u000e\u0010\u0019\u001a\u00020\u00152\u0006\u0010\u001a\u001a\u00020\bJ\u0010\u0010\u001b\u001a\u00020\u00152\u0006\u0010\u001c\u001a\u00020\u001dH\u0002R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.¢\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\rX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0006X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\rX\u0082.¢\u0006\u0002\n\u0000¨\u0006\u001f"}, d2 = {"Lcom/yddmi/doctor/pages/product/PopupBottomPaytype;", "Lcom/lxj/xpopup/core/BottomPopupView;", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "(Landroid/content/Context;)V", "aliSelectImgv", "Landroid/widget/ImageView;", "mListener", "Lcom/yddmi/doctor/pages/product/PopupBottomPaytype$OnPopupPayClickListener;", "mMoney", "", "mName", "moneyTv", "Landroid/widget/TextView;", "payType", "", "tipTv", "wxSelectImgv", "yTv", "getImplLayoutId", "onCreate", "", PLVRxEncryptDataFunction.SET_DATA_METHOD, "name", "money", "setOnPopupPayClickListener", ServiceSpecificExtraArgs.CastExtraArgs.LISTENER, "viewChangeSelect", "ali", "", "OnPopupPayClickListener", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class PopupBottomPaytype extends BottomPopupView {
    private ImageView aliSelectImgv;

    @Nullable
    private OnPopupPayClickListener mListener;

    @NotNull
    private String mMoney;

    @NotNull
    private String mName;
    private TextView moneyTv;
    private int payType;
    private TextView tipTv;
    private ImageView wxSelectImgv;
    private TextView yTv;

    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0000\bf\u0018\u00002\u00020\u0001J\u0012\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005H&¨\u0006\u0006"}, d2 = {"Lcom/yddmi/doctor/pages/product/PopupBottomPaytype$OnPopupPayClickListener;", "", "onGoPay", "", "type", "", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public interface OnPopupPayClickListener {

        @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
        public static final class DefaultImpls {
            public static /* synthetic */ void onGoPay$default(OnPopupPayClickListener onPopupPayClickListener, int i2, int i3, Object obj) {
                if (obj != null) {
                    throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: onGoPay");
                }
                if ((i3 & 1) != 0) {
                    i2 = 100;
                }
                onPopupPayClickListener.onGoPay(i2);
            }
        }

        void onGoPay(int type);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PopupBottomPaytype(@NonNull @NotNull Context context) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
        this.payType = 100;
        this.mName = "";
        this.mMoney = "";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$0(PopupBottomPaytype this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$1(PopupBottomPaytype this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.viewChangeSelect(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$2(PopupBottomPaytype this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.viewChangeSelect(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$3(PopupBottomPaytype this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        OnPopupPayClickListener onPopupPayClickListener = this$0.mListener;
        if (onPopupPayClickListener != null) {
            onPopupPayClickListener.onGoPay(this$0.payType);
        }
        this$0.dismiss();
    }

    private final void viewChangeSelect(boolean ali) {
        ImageView imageView = null;
        if (ali) {
            this.payType = 100;
            ImageView imageView2 = this.aliSelectImgv;
            if (imageView2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("aliSelectImgv");
                imageView2 = null;
            }
            imageView2.setImageResource(R.drawable.common_select1);
            ImageView imageView3 = this.wxSelectImgv;
            if (imageView3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("wxSelectImgv");
            } else {
                imageView = imageView3;
            }
            imageView.setImageResource(R.drawable.common_unselect);
            return;
        }
        this.payType = 101;
        ImageView imageView4 = this.aliSelectImgv;
        if (imageView4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("aliSelectImgv");
            imageView4 = null;
        }
        imageView4.setImageResource(R.drawable.common_unselect);
        ImageView imageView5 = this.wxSelectImgv;
        if (imageView5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("wxSelectImgv");
        } else {
            imageView = imageView5;
        }
        imageView.setImageResource(R.drawable.common_select1);
    }

    @Override // com.lxj.xpopup.core.BottomPopupView, com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return R.layout.product_pop_pay;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() {
        super.onCreate();
        this.payType = 100;
        View viewFindViewById = findViewById(R.id.aliSelectImgv);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(R.id.aliSelectImgv)");
        this.aliSelectImgv = (ImageView) viewFindViewById;
        View viewFindViewById2 = findViewById(R.id.wxSelectImgv);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "findViewById(R.id.wxSelectImgv)");
        this.wxSelectImgv = (ImageView) viewFindViewById2;
        View viewFindViewById3 = findViewById(R.id.yTv);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById3, "findViewById(R.id.yTv)");
        this.yTv = (TextView) viewFindViewById3;
        View viewFindViewById4 = findViewById(R.id.moneyTv);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById4, "findViewById(R.id.moneyTv)");
        this.moneyTv = (TextView) viewFindViewById4;
        View viewFindViewById5 = findViewById(R.id.tipTv);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById5, "findViewById(R.id.tipTv)");
        this.tipTv = (TextView) viewFindViewById5;
        View viewFindViewById6 = findViewById(R.id.xImgv);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById6, "findViewById<ImageView>(R.id.xImgv)");
        ViewExtKt.setDebounceClickListener$default(viewFindViewById6, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.product.a
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                PopupBottomPaytype.onCreate$lambda$0(this.f25984c, view);
            }
        }, 0L, 2, null);
        View viewFindViewById7 = findViewById(R.id.aliCl);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById7, "findViewById<ConstraintLayout>(R.id.aliCl)");
        ViewExtKt.setDebounceClickListener$default(viewFindViewById7, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.product.b
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                PopupBottomPaytype.onCreate$lambda$1(this.f25985c, view);
            }
        }, 0L, 2, null);
        View viewFindViewById8 = findViewById(R.id.wxCl);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById8, "findViewById<ConstraintLayout>(R.id.wxCl)");
        ViewExtKt.setDebounceClickListener$default(viewFindViewById8, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.product.c
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                PopupBottomPaytype.onCreate$lambda$2(this.f25986c, view);
            }
        }, 0L, 2, null);
        View viewFindViewById9 = findViewById(R.id.goTv);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById9, "findViewById<BLTextView>(R.id.goTv)");
        ViewExtKt.setDebounceClickListener$default(viewFindViewById9, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.product.d
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                PopupBottomPaytype.onCreate$lambda$3(this.f25987c, view);
            }
        }, 0L, 2, null);
        TextView textView = null;
        if (this.mMoney.length() == 0) {
            TextView textView2 = this.yTv;
            if (textView2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("yTv");
                textView2 = null;
            }
            textView2.setVisibility(4);
        } else {
            TextView textView3 = this.yTv;
            if (textView3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("yTv");
                textView3 = null;
            }
            textView3.setVisibility(0);
            TextView textView4 = this.moneyTv;
            if (textView4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("moneyTv");
                textView4 = null;
            }
            textView4.setText(this.mMoney);
        }
        TextView textView5 = this.tipTv;
        if (textView5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tipTv");
            textView5 = null;
        }
        TextView textView6 = this.tipTv;
        if (textView6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tipTv");
        } else {
            textView = textView6;
        }
        textView5.setText(textView.getContext().getString(R.string.product_buy_name) + this.mName);
    }

    public final void setData(@NotNull String name, @NotNull String money) {
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(money, "money");
        this.mName = name;
        this.mMoney = money;
    }

    public final void setOnPopupPayClickListener(@NotNull OnPopupPayClickListener listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        this.mListener = listener;
    }
}
