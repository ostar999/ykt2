package com.yddmi.doctor.pages.main;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.catchpig.mvvm.ext.ViewExtKt;
import com.catchpig.utils.manager.ContextManager;
import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import com.lxj.xpopup.core.CenterPopupView;
import com.plv.foundationsdk.rx.PLVRxEncryptDataFunction;
import com.yddmi.doctor.R;
import com.yddmi.doctor.entity.result.HomeMsg;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001:\u0001\u0012B\u000f\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\t\u001a\u00020\nH\u0014J\b\u0010\u000b\u001a\u00020\fH\u0014J\u000e\u0010\r\u001a\u00020\u00002\u0006\u0010\u000e\u001a\u00020\u0006J\u000e\u0010\u000f\u001a\u00020\f2\u0006\u0010\u0010\u001a\u00020\bJ\b\u0010\u0011\u001a\u00020\fH\u0002R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0013"}, d2 = {"Lcom/yddmi/doctor/pages/main/PopupMsg;", "Lcom/lxj/xpopup/core/CenterPopupView;", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "(Landroid/content/Context;)V", "mData", "Lcom/yddmi/doctor/entity/result/HomeMsg;", "mListener", "Lcom/yddmi/doctor/pages/main/PopupMsg$OnPopupMsgClickListener;", "getImplLayoutId", "", "onCreate", "", PLVRxEncryptDataFunction.SET_DATA_METHOD, "msg", "setOnPopupMsgClickListener", ServiceSpecificExtraArgs.CastExtraArgs.LISTENER, "viewDataShow", "OnPopupMsgClickListener", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class PopupMsg extends CenterPopupView {

    @Nullable
    private HomeMsg mData;

    @Nullable
    private OnPopupMsgClickListener mListener;

    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u001a\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0006\u001a\u00020\u0007H&¨\u0006\b"}, d2 = {"Lcom/yddmi/doctor/pages/main/PopupMsg$OnPopupMsgClickListener;", "", "onMsgClick", "", "view", "Landroid/view/View;", "msg", "Lcom/yddmi/doctor/entity/result/HomeMsg;", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public interface OnPopupMsgClickListener {
        void onMsgClick(@Nullable View view, @NotNull HomeMsg msg);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PopupMsg(@NonNull @NotNull Context context) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$0(PopupMsg this$0, View view) {
        OnPopupMsgClickListener onPopupMsgClickListener;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        HomeMsg homeMsg = this$0.mData;
        if (homeMsg == null || (onPopupMsgClickListener = this$0.mListener) == null) {
            return;
        }
        Intrinsics.checkNotNull(homeMsg);
        onPopupMsgClickListener.onMsgClick(view, homeMsg);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$1(PopupMsg this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.dismiss();
    }

    private final void viewDataShow() {
        String title;
        String content;
        if (this.mData != null) {
            ((TextView) findViewById(R.id.titleTv)).setText(ContextManager.INSTANCE.getInstance().getContext().getString(R.string.notification_title));
            TextView textView = (TextView) findViewById(R.id.subTitleTv);
            HomeMsg homeMsg = this.mData;
            String str = "";
            if (homeMsg == null || (title = homeMsg.getTitle()) == null) {
                title = "";
            }
            textView.setText(title);
            TextView textView2 = (TextView) findViewById(R.id.desTv);
            HomeMsg homeMsg2 = this.mData;
            if (homeMsg2 != null && (content = homeMsg2.getContent()) != null) {
                str = content;
            }
            textView2.setText(str);
        }
    }

    @Override // com.lxj.xpopup.core.CenterPopupView, com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return R.layout.popup_home_msg;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() {
        super.onCreate();
        View viewFindViewById = findViewById(R.id.goTv);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById<TextView>(R.id.goTv)");
        ViewExtKt.setDebounceClickListener$default(viewFindViewById, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.main.h0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                PopupMsg.onCreate$lambda$0(this.f25928c, view);
            }
        }, 0L, 2, null);
        View viewFindViewById2 = findViewById(R.id.closeImgv);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "findViewById<ImageView>(R.id.closeImgv)");
        ViewExtKt.setDebounceClickListener$default(viewFindViewById2, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.main.i0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                PopupMsg.onCreate$lambda$1(this.f25930c, view);
            }
        }, 0L, 2, null);
        viewDataShow();
    }

    @NotNull
    public final PopupMsg setData(@NotNull HomeMsg msg) {
        Intrinsics.checkNotNullParameter(msg, "msg");
        this.mData = msg;
        return this;
    }

    public final void setOnPopupMsgClickListener(@NotNull OnPopupMsgClickListener listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        this.mListener = listener;
    }
}
