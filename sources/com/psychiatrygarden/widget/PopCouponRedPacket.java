package com.psychiatrygarden.widget;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.lxj.xpopup.core.CenterPopupView;
import com.psychiatrygarden.bean.CouponItems;
import com.psychiatrygarden.exam.RvCountDownHelper;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.NewToast;
import com.psychiatrygarden.utils.SkinManager;
import com.yikaobang.yixue.R;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;

@Metadata(d1 = {"\u00007\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003*\u0001\u000b\u0018\u00002\u00020\u0001B\u001b\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\u0002\u0010\u0007J\b\u0010\r\u001a\u00020\u000eH\u0002J\b\u0010\u000f\u001a\u00020\u0010H\u0014J\b\u0010\u0011\u001a\u00020\u000eH\u0014J\b\u0010\u0012\u001a\u00020\u000eH\u0014R\u0010\u0010\b\u001a\u0004\u0018\u00010\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0004\n\u0002\u0010\f¨\u0006\u0013"}, d2 = {"Lcom/psychiatrygarden/widget/PopCouponRedPacket;", "Lcom/lxj/xpopup/core/CenterPopupView;", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "items", "", "Lcom/psychiatrygarden/bean/CouponItems;", "(Landroid/content/Context;Ljava/util/List;)V", "helper", "Lcom/psychiatrygarden/exam/RvCountDownHelper;", "mAdapter", "com/psychiatrygarden/widget/PopCouponRedPacket$mAdapter$1", "Lcom/psychiatrygarden/widget/PopCouponRedPacket$mAdapter$1;", "getCoupon", "", "getImplLayoutId", "", "onCreate", "onDismiss", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nPopCouponRedPacket.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PopCouponRedPacket.kt\ncom/psychiatrygarden/widget/PopCouponRedPacket\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,187:1\n766#2:188\n857#2,2:189\n766#2:191\n857#2,2:192\n*S KotlinDebug\n*F\n+ 1 PopCouponRedPacket.kt\ncom/psychiatrygarden/widget/PopCouponRedPacket\n*L\n152#1:188\n152#1:189,2\n153#1:191\n153#1:192,2\n*E\n"})
/* loaded from: classes6.dex */
public final class PopCouponRedPacket extends CenterPopupView {

    @Nullable
    private RvCountDownHelper helper;

    @NotNull
    private final List<CouponItems> items;

    @NotNull
    private final PopCouponRedPacket$mAdapter$1 mAdapter;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v1, types: [com.psychiatrygarden.widget.PopCouponRedPacket$mAdapter$1] */
    public PopCouponRedPacket(@NotNull final Context context, @NotNull List<? extends CouponItems> items) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(items, "items");
        this.items = items;
        this.mAdapter = new BaseQuickAdapter<CouponItems, BaseViewHolder>() { // from class: com.psychiatrygarden.widget.PopCouponRedPacket$mAdapter$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(R.layout.item_red_packet_coupon, null, 2, null);
            }

            /* JADX WARN: Removed duplicated region for block: B:31:0x0126  */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public void convert(@org.jetbrains.annotations.NotNull com.chad.library.adapter.base.viewholder.BaseViewHolder r13, @org.jetbrains.annotations.NotNull com.psychiatrygarden.bean.CouponItems r14) {
                /*
                    Method dump skipped, instructions count: 566
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException("Method not decompiled: com.psychiatrygarden.widget.PopCouponRedPacket$mAdapter$1.convert(com.chad.library.adapter.base.viewholder.BaseViewHolder, com.psychiatrygarden.bean.CouponItems):void");
            }
        };
    }

    private final void getCoupon() throws IOException {
        List<CouponItems> list = this.items;
        ArrayList arrayList = new ArrayList();
        for (Object obj : list) {
            if (!Intrinsics.areEqual(((CouponItems) obj).getCoupon_type(), "2")) {
                arrayList.add(obj);
            }
        }
        String strJoinToString$default = CollectionsKt___CollectionsKt.joinToString$default(arrayList, ",", null, null, 0, null, new Function1<CouponItems, CharSequence>() { // from class: com.psychiatrygarden.widget.PopCouponRedPacket$getCoupon$coupons$2
            @Override // kotlin.jvm.functions.Function1
            @NotNull
            public final CharSequence invoke(@NotNull CouponItems it) {
                Intrinsics.checkNotNullParameter(it, "it");
                String id = it.getId();
                Intrinsics.checkNotNullExpressionValue(id, "it.id");
                return id;
            }
        }, 30, null);
        List<CouponItems> list2 = this.items;
        ArrayList arrayList2 = new ArrayList();
        for (Object obj2 : list2) {
            if (Intrinsics.areEqual(((CouponItems) obj2).getCoupon_type(), "2")) {
                arrayList2.add(obj2);
            }
        }
        String strJoinToString$default2 = CollectionsKt___CollectionsKt.joinToString$default(arrayList2, ",", null, null, 0, null, new Function1<CouponItems, CharSequence>() { // from class: com.psychiatrygarden.widget.PopCouponRedPacket$getCoupon$redEnvelope$2
            @Override // kotlin.jvm.functions.Function1
            @NotNull
            public final CharSequence invoke(@NotNull CouponItems it) {
                Intrinsics.checkNotNullParameter(it, "it");
                String id = it.getId();
                Intrinsics.checkNotNullExpressionValue(id, "it.id");
                return id;
            }
        }, 30, null);
        Context context = getContext();
        String str = NetworkRequestsURL.getCoupon;
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("coupon", strJoinToString$default);
        ajaxParams.put("red_envelope", strJoinToString$default2);
        Unit unit = Unit.INSTANCE;
        YJYHttpUtils.post(context, str, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.widget.PopCouponRedPacket.getCoupon.2
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(@Nullable Throwable t2, int errorNo, @Nullable String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                if (strMsg != null) {
                    NewToast.showShort(PopCouponRedPacket.this.getContext(), strMsg);
                }
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(@NotNull String t2) {
                Intrinsics.checkNotNullParameter(t2, "t");
                super.onSuccess((AnonymousClass2) t2);
                try {
                    JSONObject jSONObject = new JSONObject(t2);
                    if (Intrinsics.areEqual(jSONObject.optString("code"), "200")) {
                        NewToast.showShort(PopCouponRedPacket.this.getContext(), "领取成功");
                        PopCouponRedPacket.this.dismiss();
                    } else {
                        String strOptString = jSONObject.optString("message");
                        if (!TextUtils.isEmpty(strOptString)) {
                            NewToast.showShort(PopCouponRedPacket.this.getContext(), strOptString);
                        }
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$0(PopCouponRedPacket this$0, View view) throws IOException {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.getCoupon();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$1(PopCouponRedPacket this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.dismiss();
    }

    @Override // com.lxj.xpopup.core.CenterPopupView, com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return this.items.size() > 2 ? R.layout.layout_red_packet_coupon : R.layout.layout_red_packet_coupon_2_items;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() {
        super.onCreate();
        View viewFindViewById = findViewById(R.id.rvList);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(R.id.rvList)");
        RecyclerView recyclerView = (RecyclerView) viewFindViewById;
        findViewById(R.id.tv_get).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.zb
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) throws IOException {
                PopCouponRedPacket.onCreate$lambda$0(this.f17152c, view);
            }
        });
        if (SkinManager.getCurrentSkinType(getContext()) == 1) {
            View viewFindViewById2 = findViewById(R.id.tv_get);
            Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "findViewById(R.id.tv_get)");
            ((TextView) viewFindViewById2).setTextColor(getContext().getColor(R.color.main_theme_color_night));
        }
        findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.ac
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                PopCouponRedPacket.onCreate$lambda$1(this.f16316c, view);
            }
        });
        this.helper = new RvCountDownHelper(this.mAdapter, recyclerView);
        recyclerView.setItemAnimator(null);
        recyclerView.setAdapter(this.mAdapter);
        setList(this.items);
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onDismiss() {
        RvCountDownHelper rvCountDownHelper = this.helper;
        if (rvCountDownHelper != null) {
            rvCountDownHelper.destroy();
        }
        super.onDismiss();
    }
}
