package com.yddmi.doctor.pages.set;

import android.content.Context;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.aliyun.vod.log.core.AliyunLogCommon;
import com.catchpig.mvvm.ext.ViewExtKt;
import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import com.hjq.toast.Toaster;
import com.huawei.hms.support.hianalytics.HiAnalyticsConstant;
import com.lxj.xpopup.core.CenterPopupView;
import com.yddmi.doctor.R;
import com.yddmi.doctor.config.YddUserManager;
import com.yddmi.doctor.entity.request.BankReqResult;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0007\n\u0002\u0010\u000e\n\u0002\b\u0003\u0018\u00002\u00020\u0001:\u0001%B\u000f\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u001a\u001a\u00020\u001bH\u0002J\b\u0010\u001c\u001a\u00020\u0013H\u0014J\b\u0010\u001d\u001a\u00020\u001bH\u0014J\u000e\u0010\u001e\u001a\u00020\u001b2\u0006\u0010\u001f\u001a\u00020\u0015J&\u0010 \u001a\u00020\u001b2\u0006\u0010!\u001a\u00020\u00132\n\b\u0002\u0010\"\u001a\u0004\u0018\u00010#2\n\b\u0002\u0010$\u001a\u0004\u0018\u00010\u0011R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u000bX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0006X\u0082.¢\u0006\u0002\n\u0000R\u0010\u0010\u0010\u001a\u0004\u0018\u00010\u0011X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0014\u001a\u0004\u0018\u00010\u0015X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0006X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u000eX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u000bX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u000eX\u0082.¢\u0006\u0002\n\u0000¨\u0006&"}, d2 = {"Lcom/yddmi/doctor/pages/set/PopupIdBank;", "Lcom/lxj/xpopup/core/CenterPopupView;", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "(Landroid/content/Context;)V", "bankNumEt", "Landroid/widget/EditText;", "bankOpenEt", "closeImgv", "Landroid/widget/ImageView;", "codeCl", "Landroidx/constraintlayout/widget/ConstraintLayout;", "errorCl", "goBtnTv", "Landroid/widget/TextView;", "idEt", "mBankInfo", "Lcom/yddmi/doctor/entity/request/BankReqResult;", "mCurrentType", "", "mListener", "Lcom/yddmi/doctor/pages/set/PopupIdBank$OnPopupCodeClickListener;", "nameEt", "phoneInputTv", "succesCl", "tipTv", "dealGoClick", "", "getImplLayoutId", "onCreate", "setOnPopupCodeClickListener", ServiceSpecificExtraArgs.CastExtraArgs.LISTENER, "viewStatus", "type", "msg", "", HiAnalyticsConstant.Direction.REQUEST, "OnPopupCodeClickListener", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class PopupIdBank extends CenterPopupView {
    private EditText bankNumEt;
    private EditText bankOpenEt;
    private ImageView closeImgv;
    private ConstraintLayout codeCl;
    private ConstraintLayout errorCl;
    private TextView goBtnTv;
    private EditText idEt;

    @Nullable
    private BankReqResult mBankInfo;
    private int mCurrentType;

    @Nullable
    private OnPopupCodeClickListener mListener;
    private EditText nameEt;
    private TextView phoneInputTv;
    private ConstraintLayout succesCl;
    private TextView tipTv;

    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\bf\u0018\u00002\u00020\u0001J:\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\u00072\u0006\u0010\n\u001a\u00020\u00072\u0006\u0010\u000b\u001a\u00020\u0007H&¨\u0006\f"}, d2 = {"Lcom/yddmi/doctor/pages/set/PopupIdBank$OnPopupCodeClickListener;", "", "onCodeClick", "", "view", "Landroid/view/View;", AliyunLogCommon.TERMINAL_TYPE, "", "name", "id", "bank", "bankOpen", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public interface OnPopupCodeClickListener {
        void onCodeClick(@Nullable View view, @NotNull String phone, @NotNull String name, @NotNull String id, @NotNull String bank, @NotNull String bankOpen);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PopupIdBank(@NonNull @NotNull Context context) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
        this.mCurrentType = 100;
    }

    private final void dealGoClick() {
        TextView textView;
        EditText editText = this.nameEt;
        if (editText == null) {
            Intrinsics.throwUninitializedPropertyAccessException("nameEt");
            editText = null;
        }
        Editable text = editText.getText();
        Intrinsics.checkNotNullExpressionValue(text, "nameEt.text");
        String string = StringsKt__StringsKt.trim(text).toString();
        if (string.length() == 0) {
            Toaster.show(R.string.me_bank_tip0);
            return;
        }
        EditText editText2 = this.idEt;
        if (editText2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("idEt");
            editText2 = null;
        }
        Editable text2 = editText2.getText();
        Intrinsics.checkNotNullExpressionValue(text2, "idEt.text");
        String string2 = StringsKt__StringsKt.trim(text2).toString();
        if ((string2.length() == 0) || string2.length() != 18) {
            Toaster.show(R.string.me_bank_tip1);
            return;
        }
        EditText editText3 = this.bankNumEt;
        if (editText3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("bankNumEt");
            editText3 = null;
        }
        Editable text3 = editText3.getText();
        Intrinsics.checkNotNullExpressionValue(text3, "bankNumEt.text");
        String string3 = StringsKt__StringsKt.trim(text3).toString();
        if (string3.length() == 0) {
            Toaster.show(R.string.me_bank_tip2);
            return;
        }
        EditText editText4 = this.bankOpenEt;
        if (editText4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("bankOpenEt");
            editText4 = null;
        }
        Editable text4 = editText4.getText();
        Intrinsics.checkNotNullExpressionValue(text4, "bankOpenEt.text");
        String string4 = StringsKt__StringsKt.trim(text4).toString();
        if (string4.length() == 0) {
            Toaster.show(R.string.me_bank_tip3);
            return;
        }
        OnPopupCodeClickListener onPopupCodeClickListener = this.mListener;
        if (onPopupCodeClickListener != null) {
            TextView textView2 = this.goBtnTv;
            if (textView2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("goBtnTv");
                textView = null;
            } else {
                textView = textView2;
            }
            onPopupCodeClickListener.onCodeClick(textView, YddUserManager.INSTANCE.getInstance().userPhone(), string, string2, string3, string4);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$0(PopupIdBank this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.dealGoClick();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$1(PopupIdBank this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.dismiss();
    }

    public static /* synthetic */ void viewStatus$default(PopupIdBank popupIdBank, int i2, String str, BankReqResult bankReqResult, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            str = "";
        }
        if ((i3 & 4) != 0) {
            bankReqResult = null;
        }
        popupIdBank.viewStatus(i2, str, bankReqResult);
    }

    @Override // com.lxj.xpopup.core.CenterPopupView, com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return R.layout.set_popup_id_bank;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() {
        TextView textView;
        ImageView imageView;
        super.onCreate();
        View viewFindViewById = findViewById(R.id.phoneInputTv);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(R.id.phoneInputTv)");
        this.phoneInputTv = (TextView) viewFindViewById;
        View viewFindViewById2 = findViewById(R.id.nameEt);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "findViewById(R.id.nameEt)");
        this.nameEt = (EditText) viewFindViewById2;
        View viewFindViewById3 = findViewById(R.id.idEt);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById3, "findViewById(R.id.idEt)");
        this.idEt = (EditText) viewFindViewById3;
        View viewFindViewById4 = findViewById(R.id.bankNumEt);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById4, "findViewById(R.id.bankNumEt)");
        this.bankNumEt = (EditText) viewFindViewById4;
        View viewFindViewById5 = findViewById(R.id.bankOpenEt);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById5, "findViewById(R.id.bankOpenEt)");
        this.bankOpenEt = (EditText) viewFindViewById5;
        View viewFindViewById6 = findViewById(R.id.codeCl);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById6, "findViewById(R.id.codeCl)");
        this.codeCl = (ConstraintLayout) viewFindViewById6;
        View viewFindViewById7 = findViewById(R.id.errorCl);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById7, "findViewById(R.id.errorCl)");
        this.errorCl = (ConstraintLayout) viewFindViewById7;
        View viewFindViewById8 = findViewById(R.id.tipTv);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById8, "findViewById(R.id.tipTv)");
        this.tipTv = (TextView) viewFindViewById8;
        View viewFindViewById9 = findViewById(R.id.succesCl);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById9, "findViewById(R.id.succesCl)");
        this.succesCl = (ConstraintLayout) viewFindViewById9;
        View viewFindViewById10 = findViewById(R.id.closeImgv);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById10, "findViewById(R.id.closeImgv)");
        this.closeImgv = (ImageView) viewFindViewById10;
        View viewFindViewById11 = findViewById(R.id.goBtnTv);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById11, "findViewById(R.id.goBtnTv)");
        TextView textView2 = (TextView) viewFindViewById11;
        this.goBtnTv = textView2;
        if (textView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("goBtnTv");
            textView = null;
        } else {
            textView = textView2;
        }
        ViewExtKt.setDebounceClickListener$default(textView, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.set.c
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                PopupIdBank.onCreate$lambda$0(this.f26009c, view);
            }
        }, 0L, 2, null);
        ImageView imageView2 = this.closeImgv;
        if (imageView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("closeImgv");
            imageView = null;
        } else {
            imageView = imageView2;
        }
        ViewExtKt.setDebounceClickListener$default(imageView, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.set.d
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                PopupIdBank.onCreate$lambda$1(this.f26011c, view);
            }
        }, 0L, 2, null);
        viewStatus$default(this, this.mCurrentType, null, this.mBankInfo, 2, null);
    }

    public final void setOnPopupCodeClickListener(@NotNull OnPopupCodeClickListener listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        this.mListener = listener;
    }

    public final void viewStatus(int type, @Nullable String msg, @Nullable BankReqResult req) {
        this.mCurrentType = type;
        this.mBankInfo = req;
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
                TextView textView2 = this.phoneInputTv;
                if (textView2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("phoneInputTv");
                    textView2 = null;
                }
                textView2.setText(YddUserManager.INSTANCE.getInstance().userPhone());
                if (req == null) {
                    EditText editText = this.nameEt;
                    if (editText == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("nameEt");
                        editText = null;
                    }
                    editText.setText("");
                    EditText editText2 = this.idEt;
                    if (editText2 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("idEt");
                        editText2 = null;
                    }
                    editText2.setText("");
                    EditText editText3 = this.bankNumEt;
                    if (editText3 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("bankNumEt");
                        editText3 = null;
                    }
                    editText3.setText("");
                    EditText editText4 = this.bankOpenEt;
                    if (editText4 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("bankOpenEt");
                    } else {
                        textView = editText4;
                    }
                    textView.setText("");
                    break;
                } else {
                    EditText editText5 = this.nameEt;
                    if (editText5 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("nameEt");
                        editText5 = null;
                    }
                    editText5.setText(req.getName());
                    EditText editText6 = this.idEt;
                    if (editText6 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("idEt");
                        editText6 = null;
                    }
                    editText6.setText(req.getIdCard());
                    EditText editText7 = this.bankNumEt;
                    if (editText7 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("bankNumEt");
                        editText7 = null;
                    }
                    editText7.setText(req.getCardNo());
                    EditText editText8 = this.bankOpenEt;
                    if (editText8 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("bankOpenEt");
                    } else {
                        textView = editText8;
                    }
                    textView.setText(req.getBank());
                    break;
                }
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
                TextView textView3 = this.goBtnTv;
                if (textView3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("goBtnTv");
                    textView3 = null;
                }
                textView3.setVisibility(8);
                if (!(msg == null || msg.length() == 0)) {
                    TextView textView4 = this.tipTv;
                    if (textView4 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("tipTv");
                    } else {
                        textView = textView4;
                    }
                    textView.setText(msg);
                    break;
                } else {
                    TextView textView5 = this.tipTv;
                    if (textView5 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("tipTv");
                    } else {
                        textView = textView5;
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
                TextView textView6 = this.goBtnTv;
                if (textView6 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("goBtnTv");
                } else {
                    textView = textView6;
                }
                textView.setVisibility(8);
                break;
        }
    }
}
