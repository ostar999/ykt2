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
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
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

@Metadata(d1 = {"\u0000Q\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0003*\u0001\u000b\u0018\u00002\u00020\u0001B\u001b\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\u0002\u0010\u0007J\b\u0010\u000f\u001a\u00020\u0010H\u0002J\b\u0010\u0011\u001a\u00020\u0012H\u0014J0\u0010\u0013\u001a\u00020\u00102\u0006\u0010\u0014\u001a\u00020\u00062\u001e\u0010\u0015\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0018\u0012\u0004\u0012\u00020\u00180\u0017\u0012\u0004\u0012\u00020\u00190\u0016H\u0002J\b\u0010\u001a\u001a\u00020\u0010H\u0014J\b\u0010\u001b\u001a\u00020\u0010H\u0014R\u0010\u0010\b\u001a\u0004\u0018\u00010\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0004\n\u0002\u0010\fR\u000e\u0010\r\u001a\u00020\u000eX\u0082.¢\u0006\u0002\n\u0000¨\u0006\u001c"}, d2 = {"Lcom/psychiatrygarden/widget/PopMultiRedPacket;", "Lcom/lxj/xpopup/core/CenterPopupView;", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "items", "", "Lcom/psychiatrygarden/bean/CouponItems;", "(Landroid/content/Context;Ljava/util/List;)V", "helper", "Lcom/psychiatrygarden/exam/RvCountDownHelper;", "mAdapter", "com/psychiatrygarden/widget/PopMultiRedPacket$mAdapter$1", "Lcom/psychiatrygarden/widget/PopMultiRedPacket$mAdapter$1;", "rvRedPacket", "Landroidx/recyclerview/widget/RecyclerView;", "getCoupon", "", "getImplLayoutId", "", "initCountDownData", "item", "mCountDownAdapter", "Lcom/chad/library/adapter/base/BaseQuickAdapter;", "Lkotlin/Pair;", "", "Lcom/chad/library/adapter/base/viewholder/BaseViewHolder;", "onCreate", "onDismiss", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nPopMultiRedPacket.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PopMultiRedPacket.kt\ncom/psychiatrygarden/widget/PopMultiRedPacket\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,196:1\n766#2:197\n857#2,2:198\n*S KotlinDebug\n*F\n+ 1 PopMultiRedPacket.kt\ncom/psychiatrygarden/widget/PopMultiRedPacket\n*L\n159#1:197\n159#1:198,2\n*E\n"})
/* loaded from: classes6.dex */
public final class PopMultiRedPacket extends CenterPopupView {

    @Nullable
    private RvCountDownHelper helper;

    @NotNull
    private final List<CouponItems> items;

    @NotNull
    private final PopMultiRedPacket$mAdapter$1 mAdapter;
    private RecyclerView rvRedPacket;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v1, types: [com.psychiatrygarden.widget.PopMultiRedPacket$mAdapter$1] */
    public PopMultiRedPacket(@NotNull final Context context, @NotNull List<? extends CouponItems> items) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(items, "items");
        this.items = items;
        this.mAdapter = new BaseQuickAdapter<CouponItems, BaseViewHolder>() { // from class: com.psychiatrygarden.widget.PopMultiRedPacket$mAdapter$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(R.layout.item_multi_red_packet, null, 2, null);
            }

            /* JADX WARN: Removed duplicated region for block: B:14:0x0074  */
            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public void convert(@org.jetbrains.annotations.NotNull com.chad.library.adapter.base.viewholder.BaseViewHolder r11, @org.jetbrains.annotations.NotNull com.psychiatrygarden.bean.CouponItems r12) {
                /*
                    Method dump skipped, instructions count: 386
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException("Method not decompiled: com.psychiatrygarden.widget.PopMultiRedPacket$mAdapter$1.convert(com.chad.library.adapter.base.viewholder.BaseViewHolder, com.psychiatrygarden.bean.CouponItems):void");
            }
        };
    }

    private final void getCoupon() {
        List<CouponItems> list = this.items;
        ArrayList arrayList = new ArrayList();
        for (Object obj : list) {
            if (Intrinsics.areEqual(((CouponItems) obj).getCoupon_type(), "2")) {
                arrayList.add(obj);
            }
        }
        Context context = getContext();
        String str = NetworkRequestsURL.getCoupon;
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("red_envelope", CollectionsKt___CollectionsKt.joinToString$default(this.items, ",", null, null, 0, null, new Function1<CouponItems, CharSequence>() { // from class: com.psychiatrygarden.widget.PopMultiRedPacket$getCoupon$1$1
            @Override // kotlin.jvm.functions.Function1
            @NotNull
            public final CharSequence invoke(@NotNull CouponItems it) {
                Intrinsics.checkNotNullParameter(it, "it");
                String id = it.getId();
                Intrinsics.checkNotNullExpressionValue(id, "it.id");
                return id;
            }
        }, 30, null));
        if (!arrayList.isEmpty()) {
            ajaxParams.put("red_envelope", CollectionsKt___CollectionsKt.joinToString$default(arrayList, ",", null, null, 0, null, new Function1<CouponItems, CharSequence>() { // from class: com.psychiatrygarden.widget.PopMultiRedPacket$getCoupon$1$2
                @Override // kotlin.jvm.functions.Function1
                @NotNull
                public final CharSequence invoke(@NotNull CouponItems it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    String id = it.getId();
                    Intrinsics.checkNotNullExpressionValue(id, "it.id");
                    return id;
                }
            }, 30, null));
        }
        Unit unit = Unit.INSTANCE;
        YJYHttpUtils.post(context, str, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.widget.PopMultiRedPacket.getCoupon.2
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(@Nullable Throwable t2, int errorNo, @Nullable String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                if (strMsg != null) {
                    NewToast.showShort(PopMultiRedPacket.this.getContext(), strMsg);
                }
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(@NotNull String t2) {
                Intrinsics.checkNotNullParameter(t2, "t");
                super.onSuccess((AnonymousClass2) t2);
                try {
                    JSONObject jSONObject = new JSONObject(t2);
                    if (Intrinsics.areEqual(jSONObject.optString("code"), "200")) {
                        NewToast.showShort(PopMultiRedPacket.this.getContext(), "领取成功");
                        PopMultiRedPacket.this.dismiss();
                    } else {
                        String strOptString = jSONObject.optString("message");
                        if (!TextUtils.isEmpty(strOptString)) {
                            NewToast.showShort(PopMultiRedPacket.this.getContext(), strOptString);
                        }
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void initCountDownData(CouponItems item, BaseQuickAdapter<Pair<String, String>, BaseViewHolder> mCountDownAdapter) {
        String endTime = item.getEnd_time();
        long jCurrentTimeMillis = System.currentTimeMillis() / 1000;
        Intrinsics.checkNotNullExpressionValue(endTime, "endTime");
        long j2 = Long.parseLong(endTime) - jCurrentTimeMillis;
        if (j2 <= 0) {
            if (mCountDownAdapter.getData().size() == 3) {
                mCountDownAdapter.getData().set(0, new Pair<>("0", "0"));
                mCountDownAdapter.getData().set(1, new Pair<>("0", "0"));
                mCountDownAdapter.getData().set(2, new Pair<>("0", "0"));
                mCountDownAdapter.notifyDataSetChanged();
                return;
            }
            return;
        }
        long j3 = 3600;
        String strValueOf = String.valueOf(j2 / j3);
        long j4 = j2 % j3;
        long j5 = 60;
        String strValueOf2 = String.valueOf(j4 / j5);
        String strValueOf3 = String.valueOf(j4 % j5);
        if (strValueOf.length() == 1) {
            strValueOf = '0' + strValueOf;
        }
        if (strValueOf2.length() == 1) {
            strValueOf2 = '0' + strValueOf2;
        }
        if (strValueOf3.length() == 1) {
            strValueOf3 = '0' + strValueOf3;
        }
        if (mCountDownAdapter.getData().isEmpty()) {
            ArrayList arrayList = new ArrayList();
            arrayList.add(new Pair(String.valueOf(strValueOf.charAt(0)), String.valueOf(strValueOf.charAt(1))));
            arrayList.add(new Pair(String.valueOf(strValueOf2.charAt(0)), String.valueOf(strValueOf2.charAt(1))));
            arrayList.add(new Pair(String.valueOf(strValueOf3.charAt(0)), String.valueOf(strValueOf3.charAt(1))));
            mCountDownAdapter.setList(arrayList);
            return;
        }
        List<Pair<String, String>> data = mCountDownAdapter.getData();
        data.set(0, new Pair<>(String.valueOf(strValueOf.charAt(0)), String.valueOf(strValueOf.charAt(1))));
        data.set(1, new Pair<>(String.valueOf(strValueOf2.charAt(0)), String.valueOf(strValueOf2.charAt(1))));
        data.set(2, new Pair<>(String.valueOf(strValueOf3.charAt(0)), String.valueOf(strValueOf3.charAt(1))));
        mCountDownAdapter.notifyDataSetChanged();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$0(PopMultiRedPacket this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$1(PopMultiRedPacket this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.getCoupon();
    }

    @Override // com.lxj.xpopup.core.CenterPopupView, com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return R.layout.layout_multi_red_packet;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() {
        super.onCreate();
        findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.nc
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                PopMultiRedPacket.onCreate$lambda$0(this.f16735c, view);
            }
        });
        findViewById(R.id.tv_get).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.oc
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                PopMultiRedPacket.onCreate$lambda$1(this.f16762c, view);
            }
        });
        View viewFindViewById = findViewById(R.id.rvRedPacket);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(R.id.rvRedPacket)");
        RecyclerView recyclerView = (RecyclerView) viewFindViewById;
        this.rvRedPacket = recyclerView;
        RecyclerView recyclerView2 = null;
        if (recyclerView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("rvRedPacket");
            recyclerView = null;
        }
        recyclerView.setItemAnimator(null);
        PopMultiRedPacket$mAdapter$1 popMultiRedPacket$mAdapter$1 = this.mAdapter;
        RecyclerView recyclerView3 = this.rvRedPacket;
        if (recyclerView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("rvRedPacket");
            recyclerView3 = null;
        }
        this.helper = new RvCountDownHelper(popMultiRedPacket$mAdapter$1, recyclerView3);
        RecyclerView recyclerView4 = this.rvRedPacket;
        if (recyclerView4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("rvRedPacket");
        } else {
            recyclerView2 = recyclerView4;
        }
        recyclerView2.setAdapter(this.mAdapter);
        setList(this.items);
        if (SkinManager.getCurrentSkinType(getContext()) == 1) {
            View viewFindViewById2 = findViewById(R.id.tv_get);
            Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "findViewById(R.id.tv_get)");
            ((TextView) viewFindViewById2).setTextColor(getContext().getColor(R.color.main_theme_color_night));
        }
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onDismiss() {
        super.onDismiss();
        RvCountDownHelper rvCountDownHelper = this.helper;
        if (rvCountDownHelper != null) {
            rvCountDownHelper.destroy();
        }
    }
}
