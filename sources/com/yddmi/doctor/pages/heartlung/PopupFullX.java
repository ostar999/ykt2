package com.yddmi.doctor.pages.heartlung;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import com.catchpig.mvvm.ext.ViewExtKt;
import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import com.lxj.xpopup.impl.FullScreenPopupView;
import com.noober.background.view.BLTextView;
import com.yddmi.doctor.R;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001:\u0001\u0011B\u000f\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u000b\u001a\u00020\fH\u0014J\b\u0010\r\u001a\u00020\u000eH\u0014J\u000e\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\u0010\u001a\u00020\bR\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.¢\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082.¢\u0006\u0002\n\u0000¨\u0006\u0012"}, d2 = {"Lcom/yddmi/doctor/pages/heartlung/PopupFullX;", "Lcom/lxj/xpopup/impl/FullScreenPopupView;", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "(Landroid/content/Context;)V", "callBtv", "Lcom/noober/background/view/BLTextView;", "mListener", "Lcom/yddmi/doctor/pages/heartlung/PopupFullX$OnPopupFullXClickListener;", "xImgv", "Landroid/widget/ImageView;", "getImplLayoutId", "", "onCreate", "", "setOnPopupFullXClickListener", ServiceSpecificExtraArgs.CastExtraArgs.LISTENER, "OnPopupFullXClickListener", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class PopupFullX extends FullScreenPopupView {
    private BLTextView callBtv;

    @Nullable
    private OnPopupFullXClickListener mListener;
    private ImageView xImgv;

    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&¨\u0006\u0006"}, d2 = {"Lcom/yddmi/doctor/pages/heartlung/PopupFullX$OnPopupFullXClickListener;", "", "onClick", "", "v", "Landroid/view/View;", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public interface OnPopupFullXClickListener {
        void onClick(@NotNull View v2);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PopupFullX(@NonNull @NotNull Context context) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$0(PopupFullX this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        OnPopupFullXClickListener onPopupFullXClickListener = this$0.mListener;
        if (onPopupFullXClickListener != null) {
            BLTextView bLTextView = this$0.callBtv;
            if (bLTextView == null) {
                Intrinsics.throwUninitializedPropertyAccessException("callBtv");
                bLTextView = null;
            }
            onPopupFullXClickListener.onClick(bLTextView);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$1(PopupFullX this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        OnPopupFullXClickListener onPopupFullXClickListener = this$0.mListener;
        if (onPopupFullXClickListener != null) {
            ImageView imageView = this$0.xImgv;
            if (imageView == null) {
                Intrinsics.throwUninitializedPropertyAccessException("xImgv");
                imageView = null;
            }
            onPopupFullXClickListener.onClick(imageView);
        }
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return R.layout.garbled_heartlung_popup_fullx;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() {
        BLTextView bLTextView;
        ImageView imageView;
        super.onCreate();
        View viewFindViewById = findViewById(R.id.callBtv);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(R.id.callBtv)");
        this.callBtv = (BLTextView) viewFindViewById;
        View viewFindViewById2 = findViewById(R.id.xImgv);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "findViewById(R.id.xImgv)");
        this.xImgv = (ImageView) viewFindViewById2;
        BLTextView bLTextView2 = this.callBtv;
        if (bLTextView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("callBtv");
            bLTextView = null;
        } else {
            bLTextView = bLTextView2;
        }
        ViewExtKt.setDebounceClickListener$default(bLTextView, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.heartlung.h0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                PopupFullX.onCreate$lambda$0(this.f25793c, view);
            }
        }, 0L, 2, null);
        ImageView imageView2 = this.xImgv;
        if (imageView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("xImgv");
            imageView = null;
        } else {
            imageView = imageView2;
        }
        ViewExtKt.setDebounceClickListener$default(imageView, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.heartlung.i0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                PopupFullX.onCreate$lambda$1(this.f25795c, view);
            }
        }, 0L, 2, null);
    }

    public final void setOnPopupFullXClickListener(@NotNull OnPopupFullXClickListener listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        this.mListener = listener;
    }
}
