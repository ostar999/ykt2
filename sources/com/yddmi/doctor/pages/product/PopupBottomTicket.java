package com.yddmi.doctor.pages.product;

import android.content.Context;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.catchpig.mvvm.ext.ViewExtKt;
import com.catchpig.utils.ext.CommonExtKt;
import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import com.lxj.xpopup.core.BottomPopupView;
import com.yddmi.doctor.R;
import com.yddmi.doctor.entity.result.SkillTicket;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\b\u0018\u00002\u00020\u0001:\u0001\u0019B\u000f\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u000f\u001a\u00020\u0010H\u0014J\b\u0010\u0011\u001a\u00020\u0012H\u0014J\u000e\u0010\u0013\u001a\u00020\u00122\u0006\u0010\u0014\u001a\u00020\nJ\u001e\u0010\u0015\u001a\u00020\u00002\b\u0010\u0016\u001a\u0004\u0018\u00010\b2\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\b0\fJ\b\u0010\u0018\u001a\u00020\u0012H\u0002R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.¢\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u0004\u0018\u00010\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u0016\u0010\u000b\u001a\n\u0012\u0004\u0012\u00020\b\u0018\u00010\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082.¢\u0006\u0002\n\u0000¨\u0006\u001a"}, d2 = {"Lcom/yddmi/doctor/pages/product/PopupBottomTicket;", "Lcom/lxj/xpopup/core/BottomPopupView;", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "(Landroid/content/Context;)V", "mAdapter", "Lcom/yddmi/doctor/pages/product/AdapterTicket;", "mCurrent", "Lcom/yddmi/doctor/entity/result/SkillTicket;", "mListener", "Lcom/yddmi/doctor/pages/product/PopupBottomTicket$OnPopupTicketClickListener;", "mTicketList", "", "rv", "Landroidx/recyclerview/widget/RecyclerView;", "getImplLayoutId", "", "onCreate", "", "setOnPopupTicketClickListener", ServiceSpecificExtraArgs.CastExtraArgs.LISTENER, "setPopupData", "current", "all", "viewShow", "OnPopupTicketClickListener", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class PopupBottomTicket extends BottomPopupView {
    private AdapterTicket mAdapter;

    @Nullable
    private SkillTicket mCurrent;

    @Nullable
    private OnPopupTicketClickListener mListener;

    @Nullable
    private List<SkillTicket> mTicketList;
    private RecyclerView rv;

    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0014\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005H&¨\u0006\u0006"}, d2 = {"Lcom/yddmi/doctor/pages/product/PopupBottomTicket$OnPopupTicketClickListener;", "", "onGoTicketSelect", "", "m", "Lcom/yddmi/doctor/entity/result/SkillTicket;", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public interface OnPopupTicketClickListener {

        @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
        public static final class DefaultImpls {
            public static /* synthetic */ void onGoTicketSelect$default(OnPopupTicketClickListener onPopupTicketClickListener, SkillTicket skillTicket, int i2, Object obj) {
                if (obj != null) {
                    throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: onGoTicketSelect");
                }
                if ((i2 & 1) != 0) {
                    skillTicket = null;
                }
                onPopupTicketClickListener.onGoTicketSelect(skillTicket);
            }
        }

        void onGoTicketSelect(@Nullable SkillTicket m2);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PopupBottomTicket(@NonNull @NotNull Context context) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$0(PopupBottomTicket this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$1(PopupBottomTicket this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        OnPopupTicketClickListener onPopupTicketClickListener = this$0.mListener;
        if (onPopupTicketClickListener != null) {
            onPopupTicketClickListener.onGoTicketSelect(this$0.mCurrent);
        }
    }

    private final void viewShow() {
        AdapterTicket adapterTicket = this.mAdapter;
        AdapterTicket adapterTicket2 = null;
        if (adapterTicket == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mAdapter");
            adapterTicket = null;
        }
        adapterTicket.setCurrentData(this.mCurrent);
        AdapterTicket adapterTicket3 = this.mAdapter;
        if (adapterTicket3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mAdapter");
        } else {
            adapterTicket2 = adapterTicket3;
        }
        adapterTicket2.set(this.mTicketList);
    }

    @Override // com.lxj.xpopup.core.BottomPopupView, com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return R.layout.product_popup_ticket;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() {
        super.onCreate();
        View viewFindViewById = findViewById(R.id.xImgv);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById<ImageView>(R.id.xImgv)");
        ViewExtKt.setDebounceClickListener$default(viewFindViewById, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.product.e
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                PopupBottomTicket.onCreate$lambda$0(this.f25988c, view);
            }
        }, 0L, 2, null);
        View viewFindViewById2 = findViewById(R.id.sureBtv);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "findViewById<TextView>(R.id.sureBtv)");
        ViewExtKt.setDebounceClickListener$default(viewFindViewById2, new View.OnClickListener() { // from class: com.yddmi.doctor.pages.product.f
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                PopupBottomTicket.onCreate$lambda$1(this.f25989c, view);
            }
        }, 0L, 2, null);
        AdapterTicket adapterTicket = new AdapterTicket();
        this.mAdapter = adapterTicket;
        adapterTicket.setEmptyMShow(true);
        AdapterTicket adapterTicket2 = this.mAdapter;
        AdapterTicket adapterTicket3 = null;
        if (adapterTicket2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mAdapter");
            adapterTicket2 = null;
        }
        adapterTicket2.setEmptyDawableWidthH(CommonExtKt.dp2px(this, 110), CommonExtKt.dp2px(this, 106));
        AdapterTicket adapterTicket4 = this.mAdapter;
        if (adapterTicket4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mAdapter");
            adapterTicket4 = null;
        }
        adapterTicket4.setEmptyDawableId(R.drawable.product_no_ticket);
        AdapterTicket adapterTicket5 = this.mAdapter;
        if (adapterTicket5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mAdapter");
            adapterTicket5 = null;
        }
        String string = getContext().getString(R.string.product_ticket3);
        Intrinsics.checkNotNullExpressionValue(string, "context.getString(R.string.product_ticket3)");
        adapterTicket5.setEmptyTip(string);
        AdapterTicket adapterTicket6 = this.mAdapter;
        if (adapterTicket6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mAdapter");
            adapterTicket6 = null;
        }
        adapterTicket6.setEmptyTipColor(R.color.c_7f7f7f);
        AdapterTicket adapterTicket7 = this.mAdapter;
        if (adapterTicket7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mAdapter");
            adapterTicket7 = null;
        }
        adapterTicket7.setOnItemClickListener(new Function2<SkillTicket, Integer, Unit>() { // from class: com.yddmi.doctor.pages.product.PopupBottomTicket.onCreate.3
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Unit invoke(SkillTicket skillTicket, Integer num) {
                invoke(skillTicket, num.intValue());
                return Unit.INSTANCE;
            }

            /* JADX WARN: Removed duplicated region for block: B:14:0x0048  */
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public final void invoke(@org.jetbrains.annotations.NotNull com.yddmi.doctor.entity.result.SkillTicket r4, int r5) {
                /*
                    r3 = this;
                    java.lang.String r5 = "m"
                    kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r4, r5)
                    int r5 = r4.getCanUse()
                    if (r5 == 0) goto L11
                    int r4 = com.yddmi.doctor.R.string.product_ticket5
                    com.hjq.toast.Toaster.show(r4)
                    return
                L11:
                    com.yddmi.doctor.pages.product.PopupBottomTicket r5 = com.yddmi.doctor.pages.product.PopupBottomTicket.this
                    com.yddmi.doctor.entity.result.SkillTicket r5 = com.yddmi.doctor.pages.product.PopupBottomTicket.access$getMCurrent$p(r5)
                    java.lang.String r0 = "mAdapter"
                    r1 = 0
                    if (r5 == 0) goto L48
                    com.yddmi.doctor.pages.product.PopupBottomTicket r5 = com.yddmi.doctor.pages.product.PopupBottomTicket.this
                    com.yddmi.doctor.entity.result.SkillTicket r5 = com.yddmi.doctor.pages.product.PopupBottomTicket.access$getMCurrent$p(r5)
                    kotlin.jvm.internal.Intrinsics.checkNotNull(r5)
                    java.lang.String r5 = r5.getCouponId()
                    java.lang.String r2 = r4.getCouponId()
                    boolean r5 = kotlin.jvm.internal.Intrinsics.areEqual(r5, r2)
                    if (r5 == 0) goto L48
                    com.yddmi.doctor.pages.product.PopupBottomTicket r4 = com.yddmi.doctor.pages.product.PopupBottomTicket.this
                    com.yddmi.doctor.pages.product.PopupBottomTicket.access$setMCurrent$p(r4, r1)
                    com.yddmi.doctor.pages.product.PopupBottomTicket r4 = com.yddmi.doctor.pages.product.PopupBottomTicket.this
                    com.yddmi.doctor.pages.product.AdapterTicket r4 = com.yddmi.doctor.pages.product.PopupBottomTicket.access$getMAdapter$p(r4)
                    if (r4 != 0) goto L44
                    kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException(r0)
                    r4 = r1
                L44:
                    r4.setCurrentData(r1)
                    goto L62
                L48:
                    com.yddmi.doctor.pages.product.PopupBottomTicket r5 = com.yddmi.doctor.pages.product.PopupBottomTicket.this
                    com.yddmi.doctor.pages.product.PopupBottomTicket.access$setMCurrent$p(r5, r4)
                    com.yddmi.doctor.pages.product.PopupBottomTicket r4 = com.yddmi.doctor.pages.product.PopupBottomTicket.this
                    com.yddmi.doctor.pages.product.AdapterTicket r4 = com.yddmi.doctor.pages.product.PopupBottomTicket.access$getMAdapter$p(r4)
                    if (r4 != 0) goto L59
                    kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException(r0)
                    r4 = r1
                L59:
                    com.yddmi.doctor.pages.product.PopupBottomTicket r5 = com.yddmi.doctor.pages.product.PopupBottomTicket.this
                    com.yddmi.doctor.entity.result.SkillTicket r5 = com.yddmi.doctor.pages.product.PopupBottomTicket.access$getMCurrent$p(r5)
                    r4.setCurrentData(r5)
                L62:
                    com.yddmi.doctor.pages.product.PopupBottomTicket r4 = com.yddmi.doctor.pages.product.PopupBottomTicket.this
                    com.yddmi.doctor.pages.product.AdapterTicket r4 = com.yddmi.doctor.pages.product.PopupBottomTicket.access$getMAdapter$p(r4)
                    if (r4 != 0) goto L6e
                    kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException(r0)
                    goto L6f
                L6e:
                    r1 = r4
                L6f:
                    r1.notifyDataSetChanged()
                    return
                */
                throw new UnsupportedOperationException("Method not decompiled: com.yddmi.doctor.pages.product.PopupBottomTicket.AnonymousClass3.invoke(com.yddmi.doctor.entity.result.SkillTicket, int):void");
            }
        });
        View viewFindViewById3 = findViewById(R.id.rv);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById3, "findViewById(R.id.rv)");
        RecyclerView recyclerView = (RecyclerView) viewFindViewById3;
        this.rv = recyclerView;
        if (recyclerView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("rv");
            recyclerView = null;
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(1);
        recyclerView.setLayoutManager(linearLayoutManager);
        AdapterTicket adapterTicket8 = this.mAdapter;
        if (adapterTicket8 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mAdapter");
        } else {
            adapterTicket3 = adapterTicket8;
        }
        recyclerView.setAdapter(adapterTicket3);
        viewShow();
    }

    public final void setOnPopupTicketClickListener(@NotNull OnPopupTicketClickListener listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        this.mListener = listener;
    }

    @NotNull
    public final PopupBottomTicket setPopupData(@Nullable SkillTicket current, @NotNull List<SkillTicket> all) {
        Intrinsics.checkNotNullParameter(all, "all");
        this.mCurrent = current;
        this.mTicketList = all;
        if (this.rv != null) {
            viewShow();
        }
        return this;
    }
}
