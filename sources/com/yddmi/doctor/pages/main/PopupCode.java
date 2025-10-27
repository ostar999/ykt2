package com.yddmi.doctor.pages.main;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.catchpig.mvvm.ext.ViewExtKt;
import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import com.hjq.toast.Toaster;
import com.lxj.xpopup.core.CenterPopupView;
import com.yddmi.doctor.R;
import com.yddmi.doctor.config.YddPointManager;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u00002\u00020\u0001:\u0001\u001dB\u000f\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u0014\u001a\u00020\u000fH\u0014J\b\u0010\u0015\u001a\u00020\u0016H\u0014J\u000e\u0010\u0017\u001a\u00020\u00162\u0006\u0010\u0018\u001a\u00020\u0011J\u001a\u0010\u0019\u001a\u00020\u00162\u0006\u0010\u001a\u001a\u00020\u000f2\n\b\u0002\u0010\u001b\u001a\u0004\u0018\u00010\u001cR\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\bX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0010\u001a\u0004\u0018\u00010\u0011X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\bX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\rX\u0082.¢\u0006\u0002\n\u0000¨\u0006\u001e"}, d2 = {"Lcom/yddmi/doctor/pages/main/PopupCode;", "Lcom/lxj/xpopup/core/CenterPopupView;", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "(Landroid/content/Context;)V", "closeImgv", "Landroid/widget/ImageView;", "codeCl", "Landroidx/constraintlayout/widget/ConstraintLayout;", "errorCl", "et", "Landroid/widget/EditText;", "goBtnTv", "Landroid/widget/TextView;", "mCurrentType", "", "mListener", "Lcom/yddmi/doctor/pages/main/PopupCode$OnPopupCodeClickListener;", "succesCl", "tipTv", "getImplLayoutId", "onCreate", "", "setOnPopupCodeClickListener", ServiceSpecificExtraArgs.CastExtraArgs.LISTENER, "viewStatus", "type", "msg", "", "OnPopupCodeClickListener", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class PopupCode extends CenterPopupView {
    private ImageView closeImgv;
    private ConstraintLayout codeCl;
    private ConstraintLayout errorCl;
    private EditText et;
    private TextView goBtnTv;
    private int mCurrentType;

    @Nullable
    private OnPopupCodeClickListener mListener;
    private ConstraintLayout succesCl;
    private TextView tipTv;

    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\u001c\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u00052\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007H&J\b\u0010\b\u001a\u00020\u0003H&¨\u0006\t"}, d2 = {"Lcom/yddmi/doctor/pages/main/PopupCode$OnPopupCodeClickListener;", "", "onCodeClick", "", "view", "Landroid/view/View;", "code", "", "onCodeSuccess", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public interface OnPopupCodeClickListener {
        void onCodeClick(@Nullable View view, @Nullable String code);

        void onCodeSuccess();
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PopupCode(@NonNull @NotNull Context context) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
        this.mCurrentType = 100;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$0(PopupCode this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (this$0.mCurrentType == 100) {
            EditText editText = this$0.et;
            if (editText == null) {
                Intrinsics.throwUninitializedPropertyAccessException("et");
                editText = null;
            }
            String string = editText.getText().toString();
            if (string.length() == 0) {
                Toaster.show(R.string.home_code_tip);
                return;
            }
            YddPointManager.addPoint$default(YddPointManager.INSTANCE.getInstance(), "SJ-A-HOME-EXCHANGECODE-TRUE", "兑换码-确认", null, 4, null);
            OnPopupCodeClickListener onPopupCodeClickListener = this$0.mListener;
            if (onPopupCodeClickListener != null) {
                onPopupCodeClickListener.onCodeClick(view, string);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$1(PopupCode this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        OnPopupCodeClickListener onPopupCodeClickListener = this$0.mListener;
        if (onPopupCodeClickListener != null) {
            onPopupCodeClickListener.onCodeSuccess();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$2(PopupCode this$0, View view) {
        OnPopupCodeClickListener onPopupCodeClickListener;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (102 == this$0.mCurrentType && (onPopupCodeClickListener = this$0.mListener) != null) {
            onPopupCodeClickListener.onCodeSuccess();
        }
        this$0.dismiss();
        YddPointManager.addPoint$default(YddPointManager.INSTANCE.getInstance(), "SJ-A-HOME-EXCHANGECODE-FALSE", "兑换码-取消", null, 4, null);
    }

    public static /* synthetic */ void viewStatus$default(PopupCode popupCode, int i2, String str, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            str = "";
        }
        popupCode.viewStatus(i2, str);
    }

    @Override // com.lxj.xpopup.core.CenterPopupView, com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return R.layout.popup_home_code;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() {
        TextView textView;
        ImageView imageView;
        super.onCreate();
        View viewFindViewById = findViewById(R.id.codeEt);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(R.id.codeEt)");
        this.et = (EditText) viewFindViewById;
        View viewFindViewById2 = findViewById(R.id.codeCl);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "findViewById(R.id.codeCl)");
        this.codeCl = (ConstraintLayout) viewFindViewById2;
        View viewFindViewById3 = findViewById(R.id.errorCl);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById3, "findViewById(R.id.errorCl)");
        this.errorCl = (ConstraintLayout) viewFindViewById3;
        View viewFindViewById4 = findViewById(R.id.tipTv);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById4, "findViewById(R.id.tipTv)");
        this.tipTv = (TextView) viewFindViewById4;
        View viewFindViewById5 = findViewById(R.id.succesCl);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById5, "findViewById(R.id.succesCl)");
        this.succesCl = (ConstraintLayout) viewFindViewById5;
        View viewFindViewById6 = findViewById(R.id.closeImgv);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById6, "findViewById(R.id.closeImgv)");
        this.closeImgv = (ImageView) viewFindViewById6;
        View viewFindViewById7 = findViewById(R.id.goBtnTv);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById7, "findViewById(R.id.goBtnTv)");
        TextView textView2 = (TextView) viewFindViewById7;
        this.goBtnTv = textView2;
        if (textView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("goBtnTv");
            textView = null;
        } else {
            textView = textView2;
        }
        ViewExtKt.setDebounceClickListener$default(textView, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.main.b0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                PopupCode.onCreate$lambda$0(this.f25916c, view);
            }
        }, 0L, 2, null);
        View viewFindViewById8 = findViewById(R.id.goTv);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById8, "findViewById<TextView>(R.id.goTv)");
        ViewExtKt.setDebounceClickListener$default(viewFindViewById8, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.main.c0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                PopupCode.onCreate$lambda$1(this.f25918c, view);
            }
        }, 0L, 2, null);
        ImageView imageView2 = this.closeImgv;
        if (imageView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("closeImgv");
            imageView = null;
        } else {
            imageView = imageView2;
        }
        ViewExtKt.setDebounceClickListener$default(imageView, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.main.d0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                PopupCode.onCreate$lambda$2(this.f25920c, view);
            }
        }, 0L, 2, null);
        viewStatus$default(this, this.mCurrentType, null, 2, null);
    }

    public final void setOnPopupCodeClickListener(@NotNull OnPopupCodeClickListener listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        this.mListener = listener;
    }

    public final void viewStatus(int type, @Nullable String msg) {
        this.mCurrentType = type;
        if (this.closeImgv == null) {
        }
        TextView textView = null;
        switch (type) {
            case 100:
                ConstraintLayout constraintLayout = this.codeCl;
                if (constraintLayout == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("codeCl");
                    constraintLayout = null;
                }
                constraintLayout.setVisibility(0);
                ConstraintLayout constraintLayout2 = this.errorCl;
                if (constraintLayout2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("errorCl");
                    constraintLayout2 = null;
                }
                constraintLayout2.setVisibility(8);
                ConstraintLayout constraintLayout3 = this.succesCl;
                if (constraintLayout3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("succesCl");
                    constraintLayout3 = null;
                }
                constraintLayout3.setVisibility(8);
                ImageView imageView = this.closeImgv;
                if (imageView == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("closeImgv");
                    imageView = null;
                }
                imageView.setVisibility(0);
                EditText editText = this.et;
                if (editText == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("et");
                } else {
                    textView = editText;
                }
                textView.setText("");
                break;
            case 101:
                ConstraintLayout constraintLayout4 = this.codeCl;
                if (constraintLayout4 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("codeCl");
                    constraintLayout4 = null;
                }
                constraintLayout4.setVisibility(8);
                ConstraintLayout constraintLayout5 = this.errorCl;
                if (constraintLayout5 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("errorCl");
                    constraintLayout5 = null;
                }
                constraintLayout5.setVisibility(0);
                ConstraintLayout constraintLayout6 = this.succesCl;
                if (constraintLayout6 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("succesCl");
                    constraintLayout6 = null;
                }
                constraintLayout6.setVisibility(8);
                ImageView imageView2 = this.closeImgv;
                if (imageView2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("closeImgv");
                    imageView2 = null;
                }
                imageView2.setVisibility(0);
                TextView textView2 = this.goBtnTv;
                if (textView2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("goBtnTv");
                    textView2 = null;
                }
                textView2.setVisibility(8);
                if (!(msg == null || msg.length() == 0)) {
                    TextView textView3 = this.tipTv;
                    if (textView3 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("tipTv");
                    } else {
                        textView = textView3;
                    }
                    textView.setText(msg);
                    break;
                } else {
                    TextView textView4 = this.tipTv;
                    if (textView4 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("tipTv");
                    } else {
                        textView = textView4;
                    }
                    textView.setText(getContext().getString(R.string.home_code_tip1));
                    break;
                }
                break;
            case 102:
                ConstraintLayout constraintLayout7 = this.codeCl;
                if (constraintLayout7 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("codeCl");
                    constraintLayout7 = null;
                }
                constraintLayout7.setVisibility(8);
                ConstraintLayout constraintLayout8 = this.errorCl;
                if (constraintLayout8 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("errorCl");
                    constraintLayout8 = null;
                }
                constraintLayout8.setVisibility(8);
                ConstraintLayout constraintLayout9 = this.succesCl;
                if (constraintLayout9 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("succesCl");
                    constraintLayout9 = null;
                }
                constraintLayout9.setVisibility(0);
                ImageView imageView3 = this.closeImgv;
                if (imageView3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("closeImgv");
                    imageView3 = null;
                }
                imageView3.setVisibility(0);
                TextView textView5 = this.goBtnTv;
                if (textView5 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("goBtnTv");
                } else {
                    textView = textView5;
                }
                textView.setVisibility(8);
                break;
        }
    }
}
