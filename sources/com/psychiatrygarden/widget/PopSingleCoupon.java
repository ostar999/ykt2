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
import cn.hutool.core.lang.RegexPool;
import com.lxj.xpopup.core.CenterPopupView;
import com.plv.foundationsdk.rx.PLVRxEncryptDataFunction;
import com.plv.socket.event.linkmic.PLVUpdateMicSiteEvent;
import com.psychiatrygarden.bean.CouponItems;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CouponUtils;
import com.psychiatrygarden.utils.NewToast;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.weblong.SizeUtil;
import com.yikaobang.yixue.R;
import com.ykb.ebook.extensions.ViewExtensionsKt;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Regex;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;

@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\b\u0010\u000f\u001a\u00020\u0010H\u0016J\b\u0010\u0011\u001a\u00020\u0010H\u0002J\b\u0010\u0012\u001a\u00020\u0013H\u0014J\b\u0010\u0014\u001a\u00020\u0010H\u0014J\u0012\u0010\u0015\u001a\u00020\u00102\b\b\u0002\u0010\u0016\u001a\u00020\nH\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\fX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\fX\u0082.¢\u0006\u0002\n\u0000¨\u0006\u0017"}, d2 = {"Lcom/psychiatrygarden/widget/PopSingleCoupon;", "Lcom/lxj/xpopup/core/CenterPopupView;", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "item", "Lcom/psychiatrygarden/bean/CouponItems;", "(Landroid/content/Context;Lcom/psychiatrygarden/bean/CouponItems;)V", "mHandler", "Landroid/os/Handler;", "outOfDate", "", "tvCountDown", "Landroid/widget/TextView;", "tvDesc", "tvPrice", "dismiss", "", "getCoupon", "getImplLayoutId", "", "onCreate", PLVRxEncryptDataFunction.SET_DATA_METHOD, PLVUpdateMicSiteEvent.EVENT_NAME, "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class PopSingleCoupon extends CenterPopupView {

    @NotNull
    private final CouponItems item;

    @NotNull
    private final Handler mHandler;
    private boolean outOfDate;
    private TextView tvCountDown;
    private TextView tvDesc;
    private TextView tvPrice;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PopSingleCoupon(@NotNull Context context, @NotNull CouponItems item) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(item, "item");
        this.item = item;
        this.mHandler = new Handler(Looper.getMainLooper(), new Handler.Callback() { // from class: com.psychiatrygarden.widget.wc
            @Override // android.os.Handler.Callback
            public final boolean handleMessage(Message message) {
                return PopSingleCoupon.mHandler$lambda$0(this.f17048c, message);
            }
        });
    }

    private final void getCoupon() {
        Context context = getContext();
        String str = NetworkRequestsURL.getCoupon;
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("coupon", this.item.getId());
        Unit unit = Unit.INSTANCE;
        YJYHttpUtils.post(context, str, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.widget.PopSingleCoupon.getCoupon.2
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(@Nullable Throwable t2, int errorNo, @Nullable String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                if (strMsg != null) {
                    NewToast.showShort(PopSingleCoupon.this.getContext(), strMsg);
                }
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(@NotNull String t2) {
                Intrinsics.checkNotNullParameter(t2, "t");
                super.onSuccess((AnonymousClass2) t2);
                try {
                    JSONObject jSONObject = new JSONObject(t2);
                    if (Intrinsics.areEqual(jSONObject.optString("code"), "200")) {
                        NewToast.showShort(PopSingleCoupon.this.getContext(), "领取成功");
                        PopSingleCoupon.this.dismiss();
                    } else {
                        String strOptString = jSONObject.optString("message");
                        if (!TextUtils.isEmpty(strOptString)) {
                            NewToast.showShort(PopSingleCoupon.this.getContext(), strOptString);
                        }
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean mHandler$lambda$0(PopSingleCoupon this$0, Message it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(it, "it");
        this$0.setData(true);
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$1(PopSingleCoupon this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$2(PopSingleCoupon this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.getCoupon();
    }

    private final void setData(boolean update) {
        StringBuilder sb;
        String endTime = this.item.getEnd_time();
        TextView textView = null;
        if (!TextUtils.isEmpty(endTime)) {
            Intrinsics.checkNotNullExpressionValue(endTime, "endTime");
            if (new Regex(RegexPool.NUMBERS).matches(endTime)) {
                long j2 = Long.parseLong(endTime) - (System.currentTimeMillis() / 1000);
                if (j2 <= 172800) {
                    TextView textView2 = this.tvCountDown;
                    if (textView2 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("tvCountDown");
                        textView2 = null;
                    }
                    ViewExtensionsKt.visible(textView2);
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
                    TextView textView3 = this.tvCountDown;
                    if (textView3 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("tvCountDown");
                        textView3 = null;
                    }
                    textView3.setText("倒计时 " + strValueOf + ':' + strValueOf2 + ':' + strValueOf3);
                    this.mHandler.sendEmptyMessageDelayed(0, 1000L);
                } else {
                    TextView textView4 = this.tvCountDown;
                    if (textView4 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("tvCountDown");
                        textView4 = null;
                    }
                    ViewExtensionsKt.gone(textView4);
                }
            }
        }
        if (update) {
            return;
        }
        if (Intrinsics.areEqual(this.item.getType(), "1")) {
            TextView textView5 = this.tvPrice;
            if (textView5 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("tvPrice");
                textView5 = null;
            }
            textView5.setTextSize(2, 18.0f);
            TextView textView6 = this.tvPrice;
            if (textView6 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("tvPrice");
                textView6 = null;
            }
            textView6.setTypeface(Typeface.DEFAULT_BOLD);
            TextView textView7 = this.tvPrice;
            if (textView7 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("tvPrice");
                textView7 = null;
            }
            StringBuilder sb2 = new StringBuilder();
            CouponUtils couponUtils = CouponUtils.INSTANCE;
            String discount = this.item.getDiscount();
            Intrinsics.checkNotNullExpressionValue(discount, "item.discount");
            sb2.append(couponUtils.convertDiscount(discount));
            sb2.append((char) 25240);
            textView7.setText(sb2.toString());
        } else {
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder("¥");
            spannableStringBuilder.setSpan(new AbsoluteSizeSpan(SizeUtil.sp2px(getContext(), 10)), 0, spannableStringBuilder.length(), 18);
            spannableStringBuilder.append((CharSequence) CouponUtils.INSTANCE.formatPrice(this.item.getPrice()));
            spannableStringBuilder.setSpan(new AbsoluteSizeSpan(SizeUtil.sp2px(getContext(), 18)), 1, spannableStringBuilder.length(), 18);
            spannableStringBuilder.setSpan(new StyleSpan(1), 0, spannableStringBuilder.length(), 18);
            TextView textView8 = this.tvPrice;
            if (textView8 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("tvPrice");
                textView8 = null;
            }
            textView8.setText(spannableStringBuilder);
        }
        String price = CouponUtils.INSTANCE.formatPrice(this.item.getThreshold_price());
        TextView textView9 = this.tvDesc;
        if (textView9 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("tvDesc");
        } else {
            textView = textView9;
        }
        if (Intrinsics.areEqual("0", price)) {
            sb = new StringBuilder();
            sb.append("立减");
        } else {
            sb = new StringBuilder();
            sb.append((char) 28385);
            sb.append(price);
            price = "可用";
        }
        sb.append(price);
        textView.setText(sb.toString());
    }

    public static /* synthetic */ void setData$default(PopSingleCoupon popSingleCoupon, boolean z2, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            z2 = false;
        }
        popSingleCoupon.setData(z2);
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void dismiss() {
        super.dismiss();
        this.mHandler.removeCallbacksAndMessages(null);
    }

    @Override // com.lxj.xpopup.core.CenterPopupView, com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return R.layout.layout_single_coupon;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() {
        super.onCreate();
        findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.xc
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                PopSingleCoupon.onCreate$lambda$1(this.f17094c, view);
            }
        });
        findViewById(R.id.tv_get).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.yc
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                PopSingleCoupon.onCreate$lambda$2(this.f17126c, view);
            }
        });
        View viewFindViewById = findViewById(R.id.tv_desc);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(R.id.tv_desc)");
        this.tvDesc = (TextView) viewFindViewById;
        View viewFindViewById2 = findViewById(R.id.tv_count_down);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "findViewById(R.id.tv_count_down)");
        this.tvCountDown = (TextView) viewFindViewById2;
        View viewFindViewById3 = findViewById(R.id.tv_price);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById3, "findViewById(R.id.tv_price)");
        this.tvPrice = (TextView) viewFindViewById3;
        if (SkinManager.getCurrentSkinType(getContext()) == 1) {
            View viewFindViewById4 = findViewById(R.id.tv_get);
            Intrinsics.checkNotNullExpressionValue(viewFindViewById4, "findViewById(R.id.tv_get)");
            TextView textView = (TextView) viewFindViewById4;
            textView.setTextColor(getContext().getColor(R.color.color_121622));
            TextView textView2 = this.tvCountDown;
            if (textView2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("tvCountDown");
                textView2 = null;
            }
            textView2.setTextColor(getContext().getColor(R.color.first_txt_color_night));
            TextView textView3 = this.tvPrice;
            if (textView3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("tvPrice");
                textView3 = null;
            }
            textView3.setTextColor(getContext().getColor(R.color.main_theme_color_night));
            TextView textView4 = this.tvDesc;
            if (textView4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("tvDesc");
                textView4 = null;
            }
            textView4.setTextColor(getContext().getColor(R.color.main_theme_color_night));
            textView.setBackground(getContext().getDrawable(R.drawable.shape_btn_red_radius_12_deep));
        }
        setData$default(this, false, 1, null);
    }
}
