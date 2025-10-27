package com.yddmi.doctor.pages.main;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.aliyun.vod.log.core.AliyunLogCommon;
import com.catchpig.mvvm.ext.ViewExtKt;
import com.catchpig.utils.ext.StringExtKt;
import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import com.hjq.toast.Toaster;
import com.lxj.xpopup.core.CenterPopupView;
import com.yddmi.doctor.R;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000S\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0002*\u0001\u0013\u0018\u00002\u00020\u0001:\u0001%B\u000f\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u001c\u001a\u00020\u0011H\u0014J\b\u0010\u001d\u001a\u00020\u001eH\u0014J\u000e\u0010\u001f\u001a\u00020\u001e2\u0006\u0010 \u001a\u00020\u0016J\u001a\u0010!\u001a\u00020\u001e2\u0006\u0010\"\u001a\u00020\u00112\n\b\u0002\u0010#\u001a\u0004\u0018\u00010$R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\bX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\rX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\nX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0012\u001a\u00020\u0013X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0014R\u0010\u0010\u0015\u001a\u0004\u0018\u00010\u0016X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0011X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\rX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\nX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\bX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\rX\u0082.¢\u0006\u0002\n\u0000¨\u0006&"}, d2 = {"Lcom/yddmi/doctor/pages/main/PopupInviteCode;", "Lcom/lxj/xpopup/core/CenterPopupView;", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "(Landroid/content/Context;)V", "closeImgv", "Landroid/widget/ImageView;", "codeCl", "Landroidx/constraintlayout/widget/ConstraintLayout;", "codeEt", "Landroid/widget/EditText;", "errorCl", "getTv", "Landroid/widget/TextView;", "goBtnTv", "inviteCodeEt", "mCurrentType", "", "mHandler", "com/yddmi/doctor/pages/main/PopupInviteCode$mHandler$1", "Lcom/yddmi/doctor/pages/main/PopupInviteCode$mHandler$1;", "mListener", "Lcom/yddmi/doctor/pages/main/PopupInviteCode$OnPopupCodeClickListener;", "numIndex", "numTv", "phoneEt", "succesCl", "tipTv", "getImplLayoutId", "onCreate", "", "setOnPopupCodeClickListener", ServiceSpecificExtraArgs.CastExtraArgs.LISTENER, "viewStatus", "type", "msg", "", "OnPopupCodeClickListener", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class PopupInviteCode extends CenterPopupView {
    private ImageView closeImgv;
    private ConstraintLayout codeCl;
    private EditText codeEt;
    private ConstraintLayout errorCl;
    private TextView getTv;
    private TextView goBtnTv;
    private EditText inviteCodeEt;
    private int mCurrentType;

    @NotNull
    private final PopupInviteCode$mHandler$1 mHandler;

    @Nullable
    private OnPopupCodeClickListener mListener;
    private int numIndex;
    private TextView numTv;
    private EditText phoneEt;
    private ConstraintLayout succesCl;
    private TextView tipTv;

    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\bf\u0018\u00002\u00020\u0001J*\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\u0007H&J\u0010\u0010\n\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u0007H&¨\u0006\u000b"}, d2 = {"Lcom/yddmi/doctor/pages/main/PopupInviteCode$OnPopupCodeClickListener;", "", "onCodeClick", "", "view", "Landroid/view/View;", AliyunLogCommon.TERMINAL_TYPE, "", "code", "inviteCode", "onCodeGet", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public interface OnPopupCodeClickListener {
        void onCodeClick(@Nullable View view, @NotNull String phone, @NotNull String code, @NotNull String inviteCode);

        void onCodeGet(@NotNull String phone);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Type inference failed for: r0v1, types: [com.yddmi.doctor.pages.main.PopupInviteCode$mHandler$1] */
    public PopupInviteCode(@NonNull @NotNull Context context) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
        this.mCurrentType = 100;
        this.numIndex = 60;
        final Looper mainLooper = Looper.getMainLooper();
        this.mHandler = new Handler(mainLooper) { // from class: com.yddmi.doctor.pages.main.PopupInviteCode$mHandler$1
            @Override // android.os.Handler
            public void handleMessage(@NotNull Message msg) {
                Intrinsics.checkNotNullParameter(msg, "msg");
                super.handleMessage(msg);
                if (msg.what == 100) {
                    removeMessages(100);
                    this.this$0.numIndex--;
                    TextView textView = this.this$0.numTv;
                    TextView textView2 = null;
                    if (textView == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("numTv");
                        textView = null;
                    }
                    textView.setText(this.this$0.numIndex + "s");
                    if (this.this$0.numIndex >= 1) {
                        sendEmptyMessageDelayed(100, 1000L);
                        return;
                    }
                    TextView textView3 = this.this$0.numTv;
                    if (textView3 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("numTv");
                        textView3 = null;
                    }
                    textView3.setVisibility(4);
                    TextView textView4 = this.this$0.getTv;
                    if (textView4 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("getTv");
                    } else {
                        textView2 = textView4;
                    }
                    textView2.setVisibility(0);
                    this.this$0.numIndex = 60;
                }
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$0(PopupInviteCode this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        EditText editText = this$0.phoneEt;
        TextView textView = null;
        if (editText == null) {
            Intrinsics.throwUninitializedPropertyAccessException("phoneEt");
            editText = null;
        }
        Editable text = editText.getText();
        Intrinsics.checkNotNullExpressionValue(text, "phoneEt.text");
        String string = StringsKt__StringsKt.trim(text).toString();
        if (!StringExtKt.isPhoneNumber(string)) {
            Toaster.show(R.string.login_phone);
            return;
        }
        TextView textView2 = this$0.getTv;
        if (textView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("getTv");
            textView2 = null;
        }
        textView2.setVisibility(4);
        TextView textView3 = this$0.numTv;
        if (textView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("numTv");
        } else {
            textView = textView3;
        }
        textView.setVisibility(0);
        this$0.mHandler.removeMessages(100);
        this$0.mHandler.sendEmptyMessageDelayed(100, 1000L);
        OnPopupCodeClickListener onPopupCodeClickListener = this$0.mListener;
        if (onPopupCodeClickListener != null) {
            onPopupCodeClickListener.onCodeGet(string);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$1(PopupInviteCode this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        EditText editText = this$0.phoneEt;
        TextView textView = null;
        if (editText == null) {
            Intrinsics.throwUninitializedPropertyAccessException("phoneEt");
            editText = null;
        }
        Editable text = editText.getText();
        Intrinsics.checkNotNullExpressionValue(text, "phoneEt.text");
        String string = StringsKt__StringsKt.trim(text).toString();
        if (!StringExtKt.isPhoneNumber(string)) {
            Toaster.show(R.string.login_phone);
            return;
        }
        EditText editText2 = this$0.codeEt;
        if (editText2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("codeEt");
            editText2 = null;
        }
        Editable text2 = editText2.getText();
        Intrinsics.checkNotNullExpressionValue(text2, "codeEt.text");
        String string2 = StringsKt__StringsKt.trim(text2).toString();
        if (string2.length() == 0) {
            Toaster.show(R.string.login_input_num);
            return;
        }
        EditText editText3 = this$0.inviteCodeEt;
        if (editText3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("inviteCodeEt");
            editText3 = null;
        }
        Editable text3 = editText3.getText();
        Intrinsics.checkNotNullExpressionValue(text3, "inviteCodeEt.text");
        String string3 = StringsKt__StringsKt.trim(text3).toString();
        if (string2.length() == 0) {
            Toaster.show(R.string.home_code_invite_tip);
            return;
        }
        OnPopupCodeClickListener onPopupCodeClickListener = this$0.mListener;
        if (onPopupCodeClickListener != null) {
            TextView textView2 = this$0.goBtnTv;
            if (textView2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("goBtnTv");
            } else {
                textView = textView2;
            }
            onPopupCodeClickListener.onCodeClick(textView, string, string2, string3);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$2(PopupInviteCode this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.dismiss();
    }

    public static /* synthetic */ void viewStatus$default(PopupInviteCode popupInviteCode, int i2, String str, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            str = "";
        }
        popupInviteCode.viewStatus(i2, str);
    }

    @Override // com.lxj.xpopup.core.CenterPopupView, com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return R.layout.popup_home_invite_code;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() {
        TextView textView;
        TextView textView2;
        ImageView imageView;
        super.onCreate();
        View viewFindViewById = findViewById(R.id.phoneEt);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(R.id.phoneEt)");
        this.phoneEt = (EditText) viewFindViewById;
        View viewFindViewById2 = findViewById(R.id.codeEt);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "findViewById(R.id.codeEt)");
        this.codeEt = (EditText) viewFindViewById2;
        View viewFindViewById3 = findViewById(R.id.inviteCodeEt);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById3, "findViewById(R.id.inviteCodeEt)");
        this.inviteCodeEt = (EditText) viewFindViewById3;
        View viewFindViewById4 = findViewById(R.id.getTv);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById4, "findViewById(R.id.getTv)");
        this.getTv = (TextView) viewFindViewById4;
        View viewFindViewById5 = findViewById(R.id.numTv);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById5, "findViewById(R.id.numTv)");
        this.numTv = (TextView) viewFindViewById5;
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
        this.goBtnTv = (TextView) viewFindViewById11;
        TextView textView3 = this.getTv;
        if (textView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("getTv");
            textView = null;
        } else {
            textView = textView3;
        }
        ViewExtKt.setDebounceClickListener$default(textView, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.main.e0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                PopupInviteCode.onCreate$lambda$0(this.f25922c, view);
            }
        }, 0L, 2, null);
        TextView textView4 = this.goBtnTv;
        if (textView4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("goBtnTv");
            textView2 = null;
        } else {
            textView2 = textView4;
        }
        ViewExtKt.setDebounceClickListener$default(textView2, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.main.f0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                PopupInviteCode.onCreate$lambda$1(this.f25924c, view);
            }
        }, 0L, 2, null);
        ImageView imageView2 = this.closeImgv;
        if (imageView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("closeImgv");
            imageView = null;
        } else {
            imageView = imageView2;
        }
        ViewExtKt.setDebounceClickListener$default(imageView, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.main.g0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                PopupInviteCode.onCreate$lambda$2(this.f25926c, view);
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
                EditText editText = this.phoneEt;
                if (editText == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("phoneEt");
                    editText = null;
                }
                editText.setText("");
                EditText editText2 = this.codeEt;
                if (editText2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("codeEt");
                    editText2 = null;
                }
                editText2.setText("");
                EditText editText3 = this.inviteCodeEt;
                if (editText3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("inviteCodeEt");
                } else {
                    textView = editText3;
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
