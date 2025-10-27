package com.yddmi.doctor.pages.main;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.blankj.utilcode.util.PhoneUtils;
import com.catchpig.mvvm.ext.ImageViewExtKt;
import com.catchpig.mvvm.ext.SpanExtKt;
import com.catchpig.mvvm.ext.ViewExtKt;
import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import com.lxj.xpopup.core.CenterPopupView;
import com.plv.foundationsdk.rx.PLVRxEncryptDataFunction;
import com.yddmi.doctor.R;
import com.yddmi.doctor.config.YddConfig;
import com.yddmi.doctor.config.YddHostConfig;
import com.yddmi.doctor.config.YddPointManager;
import com.yddmi.doctor.entity.result.SkillCall;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsJVMKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001:\u0001\u0018B\u000f\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u000f\u001a\u00020\u0010H\u0014J\b\u0010\u0011\u001a\u00020\u0012H\u0014J\u000e\u0010\u0013\u001a\u00020\u00002\u0006\u0010\u0014\u001a\u00020\rJ\u000e\u0010\u0015\u001a\u00020\u00122\u0006\u0010\u0016\u001a\u00020\u000bJ\b\u0010\u0017\u001a\u00020\u0012H\u0002R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082.¢\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\f\u001a\u0004\u0018\u00010\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0006X\u0082.¢\u0006\u0002\n\u0000¨\u0006\u0019"}, d2 = {"Lcom/yddmi/doctor/pages/main/PopupCallMe;", "Lcom/lxj/xpopup/core/CenterPopupView;", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "(Landroid/content/Context;)V", "callTv", "Landroid/widget/TextView;", "flowTv", "logoImgv", "Landroid/widget/ImageView;", "mListener", "Lcom/yddmi/doctor/pages/main/PopupCallMe$OnPopupCallMeClickListener;", "skillCall", "Lcom/yddmi/doctor/entity/result/SkillCall;", "sloganTv", "getImplLayoutId", "", "onCreate", "", PLVRxEncryptDataFunction.SET_DATA_METHOD, "data", "setOnPopupCallMeClickListener", ServiceSpecificExtraArgs.CastExtraArgs.LISTENER, "viewDetailsShow", "OnPopupCallMeClickListener", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class PopupCallMe extends CenterPopupView {
    private TextView callTv;
    private TextView flowTv;
    private ImageView logoImgv;

    @Nullable
    private OnPopupCallMeClickListener mListener;

    @Nullable
    private SkillCall skillCall;
    private TextView sloganTv;

    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0012\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005H&¨\u0006\u0006"}, d2 = {"Lcom/yddmi/doctor/pages/main/PopupCallMe$OnPopupCallMeClickListener;", "", "onClick", "", "view", "Landroid/view/View;", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public interface OnPopupCallMeClickListener {
        void onClick(@Nullable View view);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PopupCallMe(@NonNull @NotNull Context context) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    private final void viewDetailsShow() {
        ImageView imageView;
        String strReplace$default;
        TextView textView;
        TextView textView2;
        View viewFindViewById = findViewById(R.id.xImgv);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById<ImageView>(R.id.xImgv)");
        ViewExtKt.setDebounceClickListener$default(viewFindViewById, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.main.y
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                PopupCallMe.viewDetailsShow$lambda$0(this.f25953c, view);
            }
        }, 0L, 2, null);
        TextView textView3 = this.flowTv;
        if (textView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("flowTv");
            textView3 = null;
        }
        textView3.setVisibility(YddConfig.INSTANCE.getConfig(YddConfig.ConfigCustomer) ? 0 : 4);
        ImageView imageView2 = this.logoImgv;
        if (imageView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("logoImgv");
            imageView = null;
        } else {
            imageView = imageView2;
        }
        YddHostConfig companion = YddHostConfig.INSTANCE.getInstance();
        SkillCall skillCall = this.skillCall;
        ImageViewExtKt.load(imageView, companion.getFileFullUrl(skillCall != null ? skillCall.getUrl() : null), (261628 & 2) != 0 ? 0 : 0, (261628 & 4) != 0 ? 0 : 0, (261628 & 8) != 0 ? false : false, (261628 & 16) != 0 ? false : false, (261628 & 32) != 0 ? 0 : 0, (261628 & 64) != 0 ? 0 : 0, (261628 & 128) != 0 ? 0.0f : 0.0f, (261628 & 256) != 0 ? 20.0f : 0.0f, (261628 & 512) != 0 ? 0 : 0, (261628 & 1024) != 0 ? null : null, (261628 & 2048) != 0 ? false : false, (261628 & 4096) != 0 ? false : false, (261628 & 8192) != 0 ? 0 : 0, (261628 & 16384) != 0 ? 0 : 0, (261628 & 32768) != 0 ? false : false, (261628 & 65536) != 0 ? null : null, (261628 & 131072) == 0 ? null : null);
        SkillCall skillCall2 = this.skillCall;
        String companySlogan = skillCall2 != null ? skillCall2.getCompanySlogan() : null;
        if (companySlogan == null || companySlogan.length() == 0) {
            strReplace$default = "";
        } else {
            SkillCall skillCall3 = this.skillCall;
            Intrinsics.checkNotNull(skillCall3);
            strReplace$default = StringsKt__StringsJVMKt.replace$default(skillCall3.getCompanySlogan(), "\\n", "\n", false, 4, (Object) null);
        }
        TextView textView4 = this.sloganTv;
        if (textView4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sloganTv");
            textView4 = null;
        }
        textView4.setText(strReplace$default);
        TextView textView5 = this.callTv;
        if (textView5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("callTv");
            textView5 = null;
        }
        textView5.append(getContext().getString(R.string.home_callme_phone_type));
        TextView textView6 = this.callTv;
        if (textView6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("callTv");
            textView6 = null;
        }
        SkillCall skillCall4 = this.skillCall;
        SpanExtKt.appendUnderlineSpan(textView6, String.valueOf(skillCall4 != null ? skillCall4.getPhoneNumber() : null));
        TextView textView7 = this.callTv;
        if (textView7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("callTv");
            textView7 = null;
        }
        SkillCall skillCall5 = this.skillCall;
        textView7.append(" " + (skillCall5 != null ? skillCall5.getAddress() : null));
        TextView textView8 = this.callTv;
        if (textView8 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("callTv");
            textView = null;
        } else {
            textView = textView8;
        }
        ViewExtKt.setDebounceClickListener$default(textView, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.main.z
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                PopupCallMe.viewDetailsShow$lambda$1(this.f25954c, view);
            }
        }, 0L, 2, null);
        TextView textView9 = this.flowTv;
        if (textView9 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("flowTv");
            textView2 = null;
        } else {
            textView2 = textView9;
        }
        ViewExtKt.setDebounceClickListener$default(textView2, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.main.a0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                PopupCallMe.viewDetailsShow$lambda$2(this.f25912c, view);
            }
        }, 0L, 2, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void viewDetailsShow$lambda$0(PopupCallMe this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void viewDetailsShow$lambda$1(PopupCallMe this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        String string = this$0.getContext().getString(R.string.home_phone);
        Intrinsics.checkNotNullExpressionValue(string, "context.getString(R.string.home_phone)");
        SkillCall skillCall = this$0.skillCall;
        String phoneNumber = skillCall != null ? skillCall.getPhoneNumber() : null;
        if (!(phoneNumber == null || phoneNumber.length() == 0)) {
            SkillCall skillCall2 = this$0.skillCall;
            Intrinsics.checkNotNull(skillCall2);
            string = skillCall2.getPhoneNumber();
        }
        PhoneUtils.dial(string);
        YddPointManager.addPoint$default(YddPointManager.INSTANCE.getInstance(), "SJ-A-HOME-CONTACTUS", "联系我们-手机号", null, 4, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void viewDetailsShow$lambda$2(PopupCallMe this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        OnPopupCallMeClickListener onPopupCallMeClickListener = this$0.mListener;
        if (onPopupCallMeClickListener != null) {
            onPopupCallMeClickListener.onClick(view);
        }
    }

    @Override // com.lxj.xpopup.core.CenterPopupView, com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return R.layout.popup_home_callme;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() {
        super.onCreate();
        View viewFindViewById = findViewById(R.id.logoImgv);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(R.id.logoImgv)");
        this.logoImgv = (ImageView) viewFindViewById;
        View viewFindViewById2 = findViewById(R.id.sloganTv);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "findViewById(R.id.sloganTv)");
        this.sloganTv = (TextView) viewFindViewById2;
        View viewFindViewById3 = findViewById(R.id.callTv);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById3, "findViewById(R.id.callTv)");
        this.callTv = (TextView) viewFindViewById3;
        View viewFindViewById4 = findViewById(R.id.flowTv);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById4, "findViewById(R.id.flowTv)");
        this.flowTv = (TextView) viewFindViewById4;
        viewDetailsShow();
    }

    @NotNull
    public final PopupCallMe setData(@NotNull SkillCall data) {
        Intrinsics.checkNotNullParameter(data, "data");
        this.skillCall = data;
        return this;
    }

    public final void setOnPopupCallMeClickListener(@NotNull OnPopupCallMeClickListener listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        this.mListener = listener;
    }
}
