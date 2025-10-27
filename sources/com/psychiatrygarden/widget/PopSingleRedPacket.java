package com.psychiatrygarden.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.lxj.xpopup.core.CenterPopupView;
import com.plv.foundationsdk.rx.PLVRxEncryptDataFunction;
import com.psychiatrygarden.bean.CouponItems;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CouponUtils;
import com.psychiatrygarden.utils.NewToast;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.weblong.SizeUtil;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;

@Metadata(d1 = {"\u0000O\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004*\u0001\n\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\b\u0010\u0015\u001a\u00020\u0016H\u0016J\b\u0010\u0017\u001a\u00020\u0016H\u0002J\b\u0010\u0018\u001a\u00020\u0019H\u0014J\b\u0010\u001a\u001a\u00020\u0016H\u0002J\b\u0010\u001b\u001a\u00020\u0016H\u0014J\b\u0010\u001c\u001a\u00020\u0016H\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082.¢\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u000bR\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0013X\u0082.¢\u0006\u0002\n\u0000¨\u0006\u001d"}, d2 = {"Lcom/psychiatrygarden/widget/PopSingleRedPacket;", "Lcom/lxj/xpopup/core/CenterPopupView;", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "item", "Lcom/psychiatrygarden/bean/CouponItems;", "(Landroid/content/Context;Lcom/psychiatrygarden/bean/CouponItems;)V", "llCountDown", "Landroid/view/View;", "mCountDownAdapter", "com/psychiatrygarden/widget/PopSingleRedPacket$mCountDownAdapter$1", "Lcom/psychiatrygarden/widget/PopSingleRedPacket$mCountDownAdapter$1;", "mHandler", "Landroid/os/Handler;", "outOfDate", "", "rvCountDown", "Landroidx/recyclerview/widget/RecyclerView;", "tvDesc", "Landroid/widget/TextView;", "tvPrice", "dismiss", "", "getCoupon", "getImplLayoutId", "", "initCountDownData", "onCreate", PLVRxEncryptDataFunction.SET_DATA_METHOD, "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class PopSingleRedPacket extends CenterPopupView {

    @NotNull
    private final CouponItems item;
    private View llCountDown;

    @NotNull
    private final PopSingleRedPacket$mCountDownAdapter$1 mCountDownAdapter;

    @NotNull
    private final Handler mHandler;
    private boolean outOfDate;
    private RecyclerView rvCountDown;
    private TextView tvDesc;
    private TextView tvPrice;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Type inference failed for: r4v2, types: [com.psychiatrygarden.widget.PopSingleRedPacket$mCountDownAdapter$1] */
    public PopSingleRedPacket(@NotNull final Context context, @NotNull CouponItems item) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(item, "item");
        this.item = item;
        this.mHandler = new Handler(Looper.getMainLooper(), new Handler.Callback() { // from class: com.psychiatrygarden.widget.zc
            @Override // android.os.Handler.Callback
            public final boolean handleMessage(Message message) {
                return PopSingleRedPacket.mHandler$lambda$0(this.f17153c, message);
            }
        });
        this.mCountDownAdapter = new BaseQuickAdapter<Pair<? extends String, ? extends String>, BaseViewHolder>() { // from class: com.psychiatrygarden.widget.PopSingleRedPacket$mCountDownAdapter$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(R.layout.item_red_packet_count_down, null, 2, null);
            }

            @Override // com.chad.library.adapter.base.BaseQuickAdapter
            public /* bridge */ /* synthetic */ void convert(BaseViewHolder baseViewHolder, Pair<? extends String, ? extends String> pair) {
                convert2(baseViewHolder, (Pair<String, String>) pair);
            }

            /* renamed from: convert, reason: avoid collision after fix types in other method */
            public void convert2(@NotNull BaseViewHolder holder, @NotNull Pair<String, String> item2) {
                Intrinsics.checkNotNullParameter(holder, "holder");
                Intrinsics.checkNotNullParameter(item2, "item");
                holder.setText(R.id.tv_time_1, item2.getFirst()).setText(R.id.tv_time_2, item2.getSecond()).setGone(R.id.tv_quote, holder.getLayoutPosition() == getData().size() - 1);
                if (SkinManager.getCurrentSkinType(context) == 1) {
                    holder.setTextColor(R.id.tv_quote, context.getColor(R.color.person_orange_color_night));
                }
                this.mHandler.sendEmptyMessageDelayed(0, 1000L);
            }
        };
    }

    private final void getCoupon() {
        if (this.outOfDate) {
            NewToast.showShort(getContext(), "已过有效期，无法领取");
            return;
        }
        Context context = getContext();
        String str = NetworkRequestsURL.getCoupon;
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("red_envelope", this.item.getId());
        Unit unit = Unit.INSTANCE;
        YJYHttpUtils.post(context, str, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.widget.PopSingleRedPacket.getCoupon.2
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(@Nullable Throwable t2, int errorNo, @Nullable String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                if (strMsg != null) {
                    NewToast.showShort(PopSingleRedPacket.this.getContext(), strMsg);
                }
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(@NotNull String t2) {
                Intrinsics.checkNotNullParameter(t2, "t");
                super.onSuccess((AnonymousClass2) t2);
                try {
                    JSONObject jSONObject = new JSONObject(t2);
                    if (Intrinsics.areEqual(jSONObject.optString("code"), "200")) {
                        NewToast.showShort(PopSingleRedPacket.this.getContext(), "领取成功");
                        PopSingleRedPacket.this.dismiss();
                    } else {
                        String strOptString = jSONObject.optString("message");
                        if (!TextUtils.isEmpty(strOptString)) {
                            NewToast.showShort(PopSingleRedPacket.this.getContext(), strOptString);
                        }
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    private final void initCountDownData() {
        String endTime = this.item.getEnd_time();
        long jCurrentTimeMillis = System.currentTimeMillis() / 1000;
        Intrinsics.checkNotNullExpressionValue(endTime, "endTime");
        long j2 = Long.parseLong(endTime) - jCurrentTimeMillis;
        if (j2 <= 0) {
            if (getData().size() == 3) {
                getData().set(0, new Pair<>("0", "0"));
                getData().set(1, new Pair<>("0", "0"));
                getData().set(2, new Pair<>("0", "0"));
                notifyDataSetChanged();
            }
            this.outOfDate = true;
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
        if (getData().isEmpty()) {
            ArrayList arrayList = new ArrayList();
            arrayList.add(new Pair(String.valueOf(strValueOf.charAt(0)), String.valueOf(strValueOf.charAt(1))));
            arrayList.add(new Pair(String.valueOf(strValueOf2.charAt(0)), String.valueOf(strValueOf2.charAt(1))));
            arrayList.add(new Pair(String.valueOf(strValueOf3.charAt(0)), String.valueOf(strValueOf3.charAt(1))));
            setList(arrayList);
            return;
        }
        List<Pair<? extends String, ? extends String>> data = getData();
        data.set(0, new Pair<>(String.valueOf(strValueOf.charAt(0)), String.valueOf(strValueOf.charAt(1))));
        data.set(1, new Pair<>(String.valueOf(strValueOf2.charAt(0)), String.valueOf(strValueOf2.charAt(1))));
        data.set(2, new Pair<>(String.valueOf(strValueOf3.charAt(0)), String.valueOf(strValueOf3.charAt(1))));
        notifyDataSetChanged();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean mHandler$lambda$0(PopSingleRedPacket this$0, Message it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(it, "it");
        this$0.initCountDownData();
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$1(PopSingleRedPacket this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$2(PopSingleRedPacket this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.getCoupon();
    }

    private final void setData() {
        String str;
        TextView textView = null;
        if (Intrinsics.areEqual(this.item.getType(), "1")) {
            TextView textView2 = this.tvPrice;
            if (textView2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("tvPrice");
                textView2 = null;
            }
            textView2.setTextSize(2, 18.0f);
            TextView textView3 = this.tvPrice;
            if (textView3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("tvPrice");
                textView3 = null;
            }
            textView3.setTypeface(Typeface.DEFAULT_BOLD);
            TextView textView4 = this.tvPrice;
            if (textView4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("tvPrice");
                textView4 = null;
            }
            StringBuilder sb = new StringBuilder();
            CouponUtils couponUtils = CouponUtils.INSTANCE;
            String discount = this.item.getDiscount();
            Intrinsics.checkNotNullExpressionValue(discount, "item.discount");
            sb.append(couponUtils.convertDiscount(discount));
            sb.append((char) 25240);
            textView4.setText(sb.toString());
        } else {
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder("¥");
            spannableStringBuilder.setSpan(new AbsoluteSizeSpan(SizeUtil.sp2px(getContext(), 20)), 0, spannableStringBuilder.length(), 18);
            spannableStringBuilder.append((CharSequence) CouponUtils.INSTANCE.formatPrice(this.item.getPrice()));
            spannableStringBuilder.setSpan(new AbsoluteSizeSpan(SizeUtil.sp2px(getContext(), 40)), 1, spannableStringBuilder.length(), 18);
            spannableStringBuilder.setSpan(new StyleSpan(1), 0, spannableStringBuilder.length(), 18);
            TextView textView5 = this.tvPrice;
            if (textView5 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("tvPrice");
                textView5 = null;
            }
            textView5.setText(spannableStringBuilder);
        }
        String price = CouponUtils.INSTANCE.formatPrice(this.item.getThreshold_price());
        TextView textView6 = this.tvDesc;
        if (textView6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvDesc");
        } else {
            textView = textView6;
        }
        if (Intrinsics.areEqual("2", this.item.getCoupon_type()) || Intrinsics.areEqual(price, "0")) {
            str = "立减";
        } else {
            str = (char) 28385 + price + "可用";
        }
        textView.setText(str);
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void dismiss() {
        super.dismiss();
        this.mHandler.removeCallbacksAndMessages(null);
    }

    @Override // com.lxj.xpopup.core.CenterPopupView, com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return R.layout.layout_single_red_packet;
    }

    /* JADX WARN: Removed duplicated region for block: B:29:0x0160  */
    @Override // com.lxj.xpopup.core.BasePopupView
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void onCreate() {
        /*
            Method dump skipped, instructions count: 386
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.psychiatrygarden.widget.PopSingleRedPacket.onCreate():void");
    }
}
