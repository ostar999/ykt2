package com.psychiatrygarden.activity;

import android.content.Context;
import android.view.View;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lxj.xpopup.XPopup;
import com.psychiatrygarden.bean.CouponItems;
import com.psychiatrygarden.widget.PopCouponRedPacket;
import com.psychiatrygarden.widget.PopMultiCoupon;
import com.psychiatrygarden.widget.PopMultiRedPacket;
import com.psychiatrygarden.widget.PopSingleCoupon;
import com.psychiatrygarden.widget.PopSingleRedPacket;
import com.yikaobang.yixue.R;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.io.CloseableKt;
import kotlin.io.TextStreamsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.Charsets;
import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;

@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007H\u0002J\b\u0010\b\u001a\u00020\tH\u0016J\u0012\u0010\n\u001a\u00020\t2\b\u0010\u000b\u001a\u0004\u0018\u00010\u0007H\u0016J\u0010\u0010\f\u001a\u00020\u00072\u0006\u0010\r\u001a\u00020\u0007H\u0002J\b\u0010\u000e\u001a\u00020\tH\u0016J\b\u0010\u000f\u001a\u00020\tH\u0016¨\u0006\u0010"}, d2 = {"Lcom/psychiatrygarden/activity/TestCouponAct;", "Lcom/psychiatrygarden/activity/BaseActivity;", "()V", "convert2List", "", "Lcom/psychiatrygarden/bean/CouponItems;", "data", "", "init", "", "onEventMainThread", "str", "readAssetsFile", "fileName", "setContentView", "setListenerForWidget", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nTestCouponAct.kt\nKotlin\n*S Kotlin\n*F\n+ 1 TestCouponAct.kt\ncom/psychiatrygarden/activity/TestCouponAct\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,88:1\n1864#2,3:89\n766#2:93\n857#2,2:94\n766#2:96\n857#2,2:97\n1#3:92\n*S KotlinDebug\n*F\n+ 1 TestCouponAct.kt\ncom/psychiatrygarden/activity/TestCouponAct\n*L\n74#1:89,3\n30#1:93\n30#1:94,2\n38#1:96\n38#1:97,2\n*E\n"})
/* loaded from: classes5.dex */
public final class TestCouponAct extends BaseActivity {
    private final List<CouponItems> convert2List(String data) {
        int i2 = 0;
        if (data == null || data.length() == 0) {
            return CollectionsKt__CollectionsKt.emptyList();
        }
        List<CouponItems> items = (List) new Gson().fromJson(data, new TypeToken<List<? extends CouponItems>>() { // from class: com.psychiatrygarden.activity.TestCouponAct$convert2List$items$1
        }.getType());
        long jCurrentTimeMillis = System.currentTimeMillis() / 1000;
        Intrinsics.checkNotNullExpressionValue(items, "items");
        for (Object obj : items) {
            int i3 = i2 + 1;
            if (i2 < 0) {
                CollectionsKt__CollectionsKt.throwIndexOverflow();
            }
            ((CouponItems) obj).setEnd_time(String.valueOf((i3 * 60 * 60) + jCurrentTimeMillis));
            i2 = i3;
        }
        return items;
    }

    private final String readAssetsFile(String fileName) throws IOException {
        InputStream it = getAssets().open(fileName);
        try {
            Intrinsics.checkNotNullExpressionValue(it, "it");
            Reader inputStreamReader = new InputStreamReader(it, Charsets.UTF_8);
            BufferedReader bufferedReader = inputStreamReader instanceof BufferedReader ? (BufferedReader) inputStreamReader : new BufferedReader(inputStreamReader, 8192);
            try {
                String text = TextStreamsKt.readText(bufferedReader);
                CloseableKt.closeFinally(bufferedReader, null);
                CloseableKt.closeFinally(it, null);
                String strOptString = new JSONObject(text).optString("items");
                Intrinsics.checkNotNullExpressionValue(strOptString, "obj.optString(\"items\")");
                return strOptString;
            } finally {
            }
        } finally {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setListenerForWidget$lambda$1(TestCouponAct this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        List<CouponItems> listConvert2List = this$0.convert2List(this$0.readAssetsFile("all.json"));
        if (listConvert2List.isEmpty()) {
            return;
        }
        XPopup.Builder builder = new XPopup.Builder(this$0.mContext);
        Boolean bool = Boolean.FALSE;
        XPopup.Builder builderDismissOnTouchOutside = builder.dismissOnBackPressed(bool).dismissOnTouchOutside(bool);
        Context mContext = this$0.mContext;
        Intrinsics.checkNotNullExpressionValue(mContext, "mContext");
        ArrayList arrayList = new ArrayList();
        for (Object obj : listConvert2List) {
            if (Intrinsics.areEqual(((CouponItems) obj).getIs_receive(), "0")) {
                arrayList.add(obj);
            }
        }
        builderDismissOnTouchOutside.asCustom(new PopCouponRedPacket(mContext, arrayList)).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setListenerForWidget$lambda$3(TestCouponAct this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        List<CouponItems> listConvert2List = this$0.convert2List(this$0.readAssetsFile("all2.json"));
        if (listConvert2List.isEmpty()) {
            return;
        }
        XPopup.Builder builder = new XPopup.Builder(this$0.mContext);
        Boolean bool = Boolean.FALSE;
        XPopup.Builder builderDismissOnTouchOutside = builder.dismissOnBackPressed(bool).dismissOnTouchOutside(bool);
        Context mContext = this$0.mContext;
        Intrinsics.checkNotNullExpressionValue(mContext, "mContext");
        ArrayList arrayList = new ArrayList();
        for (Object obj : listConvert2List) {
            if (Intrinsics.areEqual(((CouponItems) obj).getIs_receive(), "0")) {
                arrayList.add(obj);
            }
        }
        builderDismissOnTouchOutside.asCustom(new PopCouponRedPacket(mContext, arrayList)).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setListenerForWidget$lambda$4(TestCouponAct this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        List<CouponItems> listConvert2List = this$0.convert2List(this$0.readAssetsFile("single_coupon.json"));
        if (listConvert2List.isEmpty()) {
            return;
        }
        XPopup.Builder builder = new XPopup.Builder(this$0);
        Boolean bool = Boolean.FALSE;
        builder.dismissOnBackPressed(bool).dismissOnTouchOutside(bool).asCustom(new PopSingleCoupon(this$0, listConvert2List.get(0))).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setListenerForWidget$lambda$5(TestCouponAct this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        List<CouponItems> listConvert2List = this$0.convert2List(this$0.readAssetsFile("multi_coupon.json"));
        if (listConvert2List.isEmpty()) {
            return;
        }
        XPopup.Builder builder = new XPopup.Builder(this$0);
        Boolean bool = Boolean.FALSE;
        builder.dismissOnBackPressed(bool).dismissOnTouchOutside(bool).asCustom(new PopMultiCoupon(this$0, listConvert2List)).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setListenerForWidget$lambda$6(TestCouponAct this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        List<CouponItems> listConvert2List = this$0.convert2List(this$0.readAssetsFile("single_red_packet.json"));
        if (listConvert2List.isEmpty()) {
            return;
        }
        XPopup.Builder builder = new XPopup.Builder(this$0);
        Boolean bool = Boolean.FALSE;
        builder.dismissOnBackPressed(bool).dismissOnTouchOutside(bool).asCustom(new PopSingleRedPacket(this$0, listConvert2List.get(0))).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setListenerForWidget$lambda$7(TestCouponAct this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        List<CouponItems> listConvert2List = this$0.convert2List(this$0.readAssetsFile("multi_red_packet.json"));
        if (listConvert2List.isEmpty()) {
            return;
        }
        XPopup.Builder builder = new XPopup.Builder(this$0);
        Boolean bool = Boolean.FALSE;
        builder.dismissOnBackPressed(bool).dismissOnTouchOutside(bool).asCustom(new PopMultiRedPacket(this$0, listConvert2List)).show();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(@Nullable String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.act_test_coupon);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        findViewById(R.id.tv_all).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.cp
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                TestCouponAct.setListenerForWidget$lambda$1(this.f12219c, view);
            }
        });
        findViewById(R.id.tv_all2).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.dp
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                TestCouponAct.setListenerForWidget$lambda$3(this.f12258c, view);
            }
        });
        findViewById(R.id.tv_coupon_single).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.ep
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                TestCouponAct.setListenerForWidget$lambda$4(this.f12328c, view);
            }
        });
        findViewById(R.id.tv_coupon_multi).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.fp
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                TestCouponAct.setListenerForWidget$lambda$5(this.f12416c, view);
            }
        });
        findViewById(R.id.tv_single_red_packet).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.gp
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                TestCouponAct.setListenerForWidget$lambda$6(this.f12454c, view);
            }
        });
        findViewById(R.id.tv_multi_red_packet).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.hp
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                TestCouponAct.setListenerForWidget$lambda$7(this.f12486c, view);
            }
        });
    }
}
