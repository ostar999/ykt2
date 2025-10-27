package com.yddmi.doctor.pages.heartlung;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.catchpig.mvvm.ext.ContextExtKt;
import com.catchpig.mvvm.ext.ViewExtKt;
import com.catchpig.utils.ext.CommonExtKt;
import com.catchpig.utils.ext.LogExtKt;
import com.catchpig.utils.ext.TextViewExtKt;
import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import com.lxj.xpopup.core.BottomPopupView;
import com.noober.background.drawable.DrawableCreator;
import com.plv.foundationsdk.rx.PLVRxEncryptDataFunction;
import com.yddmi.doctor.R;
import com.yddmi.doctor.config.YddConfig;
import com.yddmi.doctor.entity.result.HeartDetail;
import java.util.concurrent.ConcurrentHashMap;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\u0002\u0018\u00002\u00020\u0001:\u0001\"B\u000f\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u0016\u001a\u00020\fH\u0014J\b\u0010\u0017\u001a\u00020\u0018H\u0014J\b\u0010\u0019\u001a\u00020\u0018H\u0016J\b\u0010\u001a\u001a\u00020\u0018H\u0014J\u0010\u0010\u001b\u001a\u00020\u00002\b\u0010\u001c\u001a\u0004\u0018\u00010\u000eJ\u000e\u0010\u001d\u001a\u00020\u00182\u0006\u0010\u001e\u001a\u00020\u0014J\u0010\u0010\u001f\u001a\u00020\u00182\u0006\u0010 \u001a\u00020!H\u0002R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u001c\u0010\r\u001a\u0004\u0018\u00010\u000eX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012R\u0010\u0010\u0013\u001a\u0004\u0018\u00010\u0014X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\bX\u0082.¢\u0006\u0002\n\u0000¨\u0006#"}, d2 = {"Lcom/yddmi/doctor/pages/heartlung/PopupBottomCorrection;", "Lcom/lxj/xpopup/core/BottomPopupView;", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "(Landroid/content/Context;)V", "TAG", "", "answerTv", "Landroid/widget/TextView;", "et", "Landroid/widget/EditText;", "feedbackType", "", "mData", "Lcom/yddmi/doctor/entity/result/HeartDetail;", "getMData", "()Lcom/yddmi/doctor/entity/result/HeartDetail;", "setMData", "(Lcom/yddmi/doctor/entity/result/HeartDetail;)V", "mListener", "Lcom/yddmi/doctor/pages/heartlung/PopupBottomCorrection$OnPopupCorrectionClickListener;", "questionTv", "getImplLayoutId", "onCreate", "", "onDestroy", "onDismiss", PLVRxEncryptDataFunction.SET_DATA_METHOD, "data", "setOnPopupCorrectionClickListener", ServiceSpecificExtraArgs.CastExtraArgs.LISTENER, "viewSwitchType", "question", "", "OnPopupCorrectionClickListener", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class PopupBottomCorrection extends BottomPopupView {

    @NotNull
    private final String TAG;
    private TextView answerTv;
    private EditText et;
    private int feedbackType;

    @Nullable
    private HeartDetail mData;

    @Nullable
    private OnPopupCorrectionClickListener mListener;
    private TextView questionTv;

    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\bf\u0018\u00002\u00020\u0001J\"\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\b\u0010\u0006\u001a\u0004\u0018\u00010\u00072\u0006\u0010\b\u001a\u00020\tH&¨\u0006\n"}, d2 = {"Lcom/yddmi/doctor/pages/heartlung/PopupBottomCorrection$OnPopupCorrectionClickListener;", "", "onClick", "", "view", "Landroid/view/View;", "m", "Lcom/yddmi/doctor/entity/result/HeartDetail;", "feedbackType", "", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public interface OnPopupCorrectionClickListener {
        void onClick(@NotNull View view, @Nullable HeartDetail m2, int feedbackType);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PopupBottomCorrection(@NonNull @NotNull Context context) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
        this.TAG = "PopupBottomCorrection";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$0(PopupBottomCorrection this$0, View view) throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.viewSwitchType(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$1(PopupBottomCorrection this$0, View view) throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.viewSwitchType(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$2(View view) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$3(PopupBottomCorrection this$0, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        EditText editText = this$0.et;
        EditText editText2 = null;
        if (editText == null) {
            Intrinsics.throwUninitializedPropertyAccessException("et");
            editText = null;
        }
        String string = editText.getText().toString();
        HeartDetail heartDetail = this$0.mData;
        if (heartDetail != null) {
            heartDetail.setMDescription(string);
        }
        OnPopupCorrectionClickListener onPopupCorrectionClickListener = this$0.mListener;
        if (onPopupCorrectionClickListener != null) {
            Intrinsics.checkNotNullExpressionValue(it, "it");
            onPopupCorrectionClickListener.onClick(it, this$0.mData, this$0.feedbackType);
        }
        EditText editText3 = this$0.et;
        if (editText3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("et");
        } else {
            editText2 = editText3;
        }
        editText2.setText("");
    }

    private final void viewSwitchType(boolean question) throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
        DrawableCreator.Builder cornersRadius = new DrawableCreator.Builder().setCornersRadius(CommonExtKt.dp2px(this, 6));
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "context");
        int i2 = R.color.c_2f2f46;
        Drawable select = cornersRadius.setStrokeColor(ContextExtKt.getColorM(context, i2)).setStrokeWidth(CommonExtKt.dp2px(this, 1)).build();
        DrawableCreator.Builder cornersRadius2 = new DrawableCreator.Builder().setCornersRadius(CommonExtKt.dp2px(this, 6));
        Context context2 = getContext();
        Intrinsics.checkNotNullExpressionValue(context2, "context");
        Drawable noSelect = cornersRadius2.setStrokeColor(ContextExtKt.getColorM(context2, R.color.c_d4d5da)).setStrokeWidth(CommonExtKt.dp2px(this, 1)).build();
        TextView textView = null;
        if (question) {
            this.feedbackType = 0;
            TextView textView2 = this.questionTv;
            if (textView2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("questionTv");
                textView2 = null;
            }
            Intrinsics.checkNotNullExpressionValue(select, "select");
            ViewExtKt.setBackgroundJellyBean16(textView2, select);
            TextView textView3 = this.answerTv;
            if (textView3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("answerTv");
                textView3 = null;
            }
            Intrinsics.checkNotNullExpressionValue(noSelect, "noSelect");
            ViewExtKt.setBackgroundJellyBean16(textView3, noSelect);
            TextView textView4 = this.questionTv;
            if (textView4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("questionTv");
                textView4 = null;
            }
            TextViewExtKt.setTextColorRes(textView4, i2);
            TextView textView5 = this.answerTv;
            if (textView5 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("answerTv");
            } else {
                textView = textView5;
            }
            TextViewExtKt.setTextColorRes(textView, R.color.c_8f91a1);
            return;
        }
        this.feedbackType = 1;
        TextView textView6 = this.questionTv;
        if (textView6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("questionTv");
            textView6 = null;
        }
        Intrinsics.checkNotNullExpressionValue(noSelect, "noSelect");
        ViewExtKt.setBackgroundJellyBean16(textView6, noSelect);
        TextView textView7 = this.answerTv;
        if (textView7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("answerTv");
            textView7 = null;
        }
        Intrinsics.checkNotNullExpressionValue(select, "select");
        ViewExtKt.setBackgroundJellyBean16(textView7, select);
        TextView textView8 = this.questionTv;
        if (textView8 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("questionTv");
            textView8 = null;
        }
        TextViewExtKt.setTextColorRes(textView8, R.color.c_8f91a1);
        TextView textView9 = this.answerTv;
        if (textView9 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("answerTv");
        } else {
            textView = textView9;
        }
        TextViewExtKt.setTextColorRes(textView, i2);
    }

    @Override // com.lxj.xpopup.core.BottomPopupView, com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return R.layout.garbled_heartlung_popup_correction;
    }

    @Nullable
    public final HeartDetail getMData() {
        return this.mData;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() {
        TextView textView;
        TextView textView2;
        super.onCreate();
        View viewFindViewById = findViewById(R.id.et);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(R.id.et)");
        this.et = (EditText) viewFindViewById;
        View viewFindViewById2 = findViewById(R.id.questionTv);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "findViewById(R.id.questionTv)");
        this.questionTv = (TextView) viewFindViewById2;
        View viewFindViewById3 = findViewById(R.id.answerTv);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById3, "findViewById(R.id.answerTv)");
        this.answerTv = (TextView) viewFindViewById3;
        TextView textView3 = this.questionTv;
        EditText editText = null;
        if (textView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("questionTv");
            textView = null;
        } else {
            textView = textView3;
        }
        ViewExtKt.setDebounceClickListener$default(textView, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.heartlung.d0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
                PopupBottomCorrection.onCreate$lambda$0(this.f25785c, view);
            }
        }, 0L, 2, null);
        TextView textView4 = this.answerTv;
        if (textView4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("answerTv");
            textView2 = null;
        } else {
            textView2 = textView4;
        }
        ViewExtKt.setDebounceClickListener$default(textView2, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.heartlung.e0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
                PopupBottomCorrection.onCreate$lambda$1(this.f25788c, view);
            }
        }, 0L, 2, null);
        View viewFindViewById4 = findViewById(R.id.historyTv);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById4, "findViewById<TextView>(R.id.historyTv)");
        ViewExtKt.setDebounceClickListener$default(viewFindViewById4, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.heartlung.f0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                PopupBottomCorrection.onCreate$lambda$2(view);
            }
        }, 0L, 2, null);
        View viewFindViewById5 = findViewById(R.id.submitTv);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById5, "findViewById<TextView>(R.id.submitTv)");
        ViewExtKt.setDebounceClickListener$default(viewFindViewById5, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.heartlung.g0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                PopupBottomCorrection.onCreate$lambda$3(this.f25791c, view);
            }
        }, 0L, 2, null);
        Object obj = YddConfig.INSTANCE.getKvData().get(YddConfig.KV_HEARTLUNG_CORRECTION);
        String str = obj instanceof String ? (String) obj : null;
        if (str == null || str.length() == 0) {
            return;
        }
        EditText editText2 = this.et;
        if (editText2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("et");
            editText2 = null;
        }
        editText2.setText(str);
        EditText editText3 = this.et;
        if (editText3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("et");
        } else {
            editText = editText3;
        }
        ViewExtKt.selectionEndGo(editText);
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onDestroy() {
        super.onDestroy();
        LogExtKt.logd("onDestroy ", this.TAG);
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onDismiss() {
        super.onDismiss();
        LogExtKt.logd("onDismiss ", this.TAG);
        ConcurrentHashMap<String, Object> kvData = YddConfig.INSTANCE.getKvData();
        EditText editText = this.et;
        if (editText == null) {
            Intrinsics.throwUninitializedPropertyAccessException("et");
            editText = null;
        }
        kvData.put(YddConfig.KV_HEARTLUNG_CORRECTION, editText.getText().toString());
    }

    @NotNull
    public final PopupBottomCorrection setData(@Nullable HeartDetail data) {
        this.mData = data;
        return this;
    }

    public final void setMData(@Nullable HeartDetail heartDetail) {
        this.mData = heartDetail;
    }

    public final void setOnPopupCorrectionClickListener(@NotNull OnPopupCorrectionClickListener listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        this.mListener = listener;
    }
}
