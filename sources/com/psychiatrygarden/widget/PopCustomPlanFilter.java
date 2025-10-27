package com.psychiatrygarden.widget;

import android.content.Context;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import com.aliyun.vod.common.utils.UriUtil;
import com.lxj.xpopup.core.BottomPopupView;
import com.meizu.cloud.pushsdk.notification.model.NotifyType;
import com.plv.livescenes.model.PLVLiveClassDetailVO;
import com.psychiatrygarden.bean.ChartFilterBean;
import com.psychiatrygarden.utils.weblong.SizeUtil;
import com.yikaobang.yixue.R;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.MapsKt__MapsJVMKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000g\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\b\u0003*\u0001\u0013\u0018\u00002\u00020\u0001:\u0001#BI\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\b\u0012\u0018\u0010\t\u001a\u0014\u0012\u0004\u0012\u00020\b\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00050\n\u0012\u0006\u0010\u000b\u001a\u00020\f¢\u0006\u0002\u0010\rJ\b\u0010\u001a\u001a\u00020\u001bH\u0014J\b\u0010\u001c\u001a\u00020\u001bH\u0014J\b\u0010\u001d\u001a\u00020\u001eH\u0014J\b\u0010\u001f\u001a\u00020\u001eH\u0014J(\u0010 \u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\b0!2\u0012\u0010\"\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\b0!H\u0002R \u0010\t\u001a\u0014\u0012\u0004\u0012\u00020\b\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00050\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u000b\u001a\u00020\f¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0012\u001a\u00020\u0013X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0014RJ\u0010\u0015\u001a>\u0012\u0004\u0012\u00020\b\u0012\u0014\u0012\u0012\u0012\u0004\u0012\u00020\b0\u0017j\b\u0012\u0004\u0012\u00020\b`\u00180\u0016j\u001e\u0012\u0004\u0012\u00020\b\u0012\u0014\u0012\u0012\u0012\u0004\u0012\u00020\b0\u0017j\b\u0012\u0004\u0012\u00020\b`\u0018`\u0019X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006$"}, d2 = {"Lcom/psychiatrygarden/widget/PopCustomPlanFilter;", "Lcom/lxj/xpopup/core/BottomPopupView;", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "list", "", "Lcom/psychiatrygarden/bean/ChartFilterBean;", "h", "", "defaultSelectMap", "Landroid/util/ArrayMap;", NotifyType.LIGHTS, "Lcom/psychiatrygarden/widget/PopCustomPlanFilter$ConfirmListener;", "(Landroid/content/Context;Ljava/util/List;Ljava/lang/String;Landroid/util/ArrayMap;Lcom/psychiatrygarden/widget/PopCustomPlanFilter$ConfirmListener;)V", "filterClick", "", "getL", "()Lcom/psychiatrygarden/widget/PopCustomPlanFilter$ConfirmListener;", "mAdapter", "com/psychiatrygarden/widget/PopCustomPlanFilter$mAdapter$1", "Lcom/psychiatrygarden/widget/PopCustomPlanFilter$mAdapter$1;", "paramsMap", "Ljava/util/LinkedHashMap;", "Ljava/util/ArrayList;", "Lkotlin/collections/ArrayList;", "Lkotlin/collections/LinkedHashMap;", "getImplLayoutId", "", "getMaxHeight", "onCreate", "", "onShow", "processMapInOrder", "", "map", "ConfirmListener", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nPopCustomPlanFilter.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PopCustomPlanFilter.kt\ncom/psychiatrygarden/widget/PopCustomPlanFilter\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 4 _Maps.kt\nkotlin/collections/MapsKt___MapsKt\n*L\n1#1,323:1\n1#2:324\n1655#3,8:325\n1855#3:333\n1855#3,2:334\n1856#3:336\n1855#3,2:337\n1855#3,2:339\n125#4:341\n152#4,3:342\n*S KotlinDebug\n*F\n+ 1 PopCustomPlanFilter.kt\ncom/psychiatrygarden/widget/PopCustomPlanFilter\n*L\n288#1:325,8\n289#1:333\n291#1:334,2\n289#1:336\n298#1:337,2\n307#1:339,2\n272#1:341\n272#1:342,3\n*E\n"})
/* loaded from: classes6.dex */
public final class PopCustomPlanFilter extends BottomPopupView {

    @NotNull
    private final ArrayMap<String, List<String>> defaultSelectMap;
    private boolean filterClick;

    @Nullable
    private final String h;

    @NotNull
    private final ConfirmListener l;

    @NotNull
    private final List<ChartFilterBean> list;

    @NotNull
    private final PopCustomPlanFilter$mAdapter$1 mAdapter;

    @NotNull
    private final LinkedHashMap<String, ArrayList<String>> paramsMap;

    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0000\bf\u0018\u00002\u00020\u0001J\u001c\u0010\u0002\u001a\u00020\u00032\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00060\u0005H&¨\u0006\u0007"}, d2 = {"Lcom/psychiatrygarden/widget/PopCustomPlanFilter$ConfirmListener;", "", "onConfirm", "", "params", "", "", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public interface ConfirmListener {
        void onConfirm(@NotNull Map<String, String> params);
    }

    public /* synthetic */ PopCustomPlanFilter(Context context, List list, String str, ArrayMap arrayMap, ConfirmListener confirmListener, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, list, (i2 & 4) != 0 ? null : str, arrayMap, confirmListener);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$0(PopCustomPlanFilter this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$2(PopCustomPlanFilter this$0, View view) throws IOException {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        ArrayMap arrayMap = new ArrayMap();
        LinkedHashMap<String, ArrayList<String>> linkedHashMap = this$0.paramsMap;
        ArrayList arrayList = new ArrayList(linkedHashMap.size());
        Iterator<Map.Entry<String, ArrayList<String>>> it = linkedHashMap.entrySet().iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().getValue());
        }
        if (!arrayList.isEmpty()) {
            for (Map.Entry<String, ArrayList<String>> entry : this$0.paramsMap.entrySet()) {
                String key = entry.getKey();
                String strJoinToString$default = CollectionsKt___CollectionsKt.joinToString$default(entry.getValue(), ",", null, null, 0, null, null, 62, null);
                if (!TextUtils.isEmpty(strJoinToString$default)) {
                    arrayMap.put(key, strJoinToString$default);
                }
            }
        }
        this$0.filterClick = true;
        this$0.l.onConfirm(this$0.processMapInOrder(arrayMap));
        this$0.dismiss();
    }

    private final Map<String, String> processMapInOrder(Map<String, String> map) {
        Map mapCreateMapBuilder = MapsKt__MapsJVMKt.createMapBuilder();
        for (String str : CollectionsKt__CollectionsKt.listOf((Object[]) new String[]{UriUtil.QUERY_CATEGORY, PLVLiveClassDetailVO.LiveStatus.LIVE_START, "frequency"})) {
            String str2 = map.get(str);
            if (str2 != null) {
                mapCreateMapBuilder.put(str, str2);
            }
        }
        return MapsKt__MapsJVMKt.build(mapCreateMapBuilder);
    }

    @Override // com.lxj.xpopup.core.BottomPopupView, com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return R.layout.pop_custom_plan_filter;
    }

    @NotNull
    public final ConfirmListener getL() {
        return this.l;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public int getMaxHeight() {
        return getContext().getResources().getDisplayMetrics().heightPixels - SizeUtil.dp2px(getContext(), 96);
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() {
        super.onCreate();
        findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.bc
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                PopCustomPlanFilter.onCreate$lambda$0(this.f16343c, view);
            }
        });
        findViewById(R.id.tv_ok).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.cc
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) throws IOException {
                PopCustomPlanFilter.onCreate$lambda$2(this.f16372c, view);
            }
        });
        View viewFindViewById = findViewById(R.id.rvOptions);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(R.id.rvOptions)");
        RecyclerView recyclerView = (RecyclerView) viewFindViewById;
        List<ChartFilterBean> list = this.list;
        recyclerView.setVisibility(list == null || list.isEmpty() ? 8 : 0);
        PopCustomPlanFilter$mAdapter$1 popCustomPlanFilter$mAdapter$1 = this.mAdapter;
        List<ChartFilterBean> list2 = this.list;
        HashSet hashSet = new HashSet();
        ArrayList arrayList = new ArrayList();
        for (Object obj : list2) {
            if (hashSet.add(((ChartFilterBean) obj).getType())) {
                arrayList.add(obj);
            }
        }
        popCustomPlanFilter$mAdapter$1.setList(arrayList);
        recyclerView.setAdapter(popCustomPlanFilter$mAdapter$1);
        for (ChartFilterBean chartFilterBean : this.list) {
            List<String> arrayList2 = this.defaultSelectMap.get(chartFilterBean.getType());
            if (arrayList2 == null) {
                arrayList2 = new ArrayList<>();
            }
            List<ChartFilterBean.ChartFilterValue> value = chartFilterBean.getValue();
            Intrinsics.checkNotNullExpressionValue(value, "it.value");
            for (ChartFilterBean.ChartFilterValue chartFilterValue : value) {
                chartFilterValue.setSelect(arrayList2.contains(chartFilterValue.getKey()));
            }
        }
        if (!this.defaultSelectMap.isEmpty()) {
            this.paramsMap.clear();
            Set<Map.Entry<String, List<String>>> setEntrySet = this.defaultSelectMap.entrySet();
            Intrinsics.checkNotNullExpressionValue(setEntrySet, "defaultSelectMap.entries");
            Iterator<T> it = setEntrySet.iterator();
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                AbstractMap abstractMap = this.paramsMap;
                Object key = entry.getKey();
                Intrinsics.checkNotNullExpressionValue(key, "it.key");
                abstractMap.put(key, new ArrayList((Collection) entry.getValue()));
            }
            this.mAdapter.notifyDataSetChanged();
        }
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onShow() {
        super.onShow();
        this.filterClick = false;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public PopCustomPlanFilter(@NotNull Context context, @NotNull List<? extends ChartFilterBean> list, @Nullable String str, @NotNull ArrayMap<String, List<String>> defaultSelectMap, @NotNull ConfirmListener l2) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(list, "list");
        Intrinsics.checkNotNullParameter(defaultSelectMap, "defaultSelectMap");
        Intrinsics.checkNotNullParameter(l2, "l");
        this.list = list;
        this.h = str;
        this.defaultSelectMap = defaultSelectMap;
        this.l = l2;
        this.paramsMap = new LinkedHashMap<>();
        this.mAdapter = new PopCustomPlanFilter$mAdapter$1(this);
    }
}
