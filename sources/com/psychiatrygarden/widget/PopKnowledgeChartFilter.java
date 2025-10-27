package com.psychiatrygarden.widget;

import android.content.Context;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.view.View;
import com.aliyun.vod.common.utils.UriUtil;
import com.lxj.xpopup.core.BottomPopupView;
import com.meizu.cloud.pushsdk.notification.model.NotifyType;
import com.plv.livescenes.model.PLVLiveClassDetailVO;
import com.psychiatrygarden.activity.knowledge.KnowledgeRuleActivity;
import com.psychiatrygarden.bean.ChartFilterBean;
import com.psychiatrygarden.utils.weblong.SizeUtil;
import com.yikaobang.yixue.R;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.MapsKt__MapsJVMKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000g\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\b\u0003*\u0001\u0013\u0018\u00002\u00020\u0001:\u0001#BI\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\b\u0012\u0018\u0010\t\u001a\u0014\u0012\u0004\u0012\u00020\b\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00050\n\u0012\u0006\u0010\u000b\u001a\u00020\f¢\u0006\u0002\u0010\rJ\b\u0010\u001a\u001a\u00020\u001bH\u0014J\b\u0010\u001c\u001a\u00020\u001bH\u0014J\b\u0010\u001d\u001a\u00020\u001eH\u0014J\b\u0010\u001f\u001a\u00020\u001eH\u0014J(\u0010 \u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\b0!2\u0012\u0010\"\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\b0!H\u0002R \u0010\t\u001a\u0014\u0012\u0004\u0012\u00020\b\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00050\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u000b\u001a\u00020\f¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0012\u001a\u00020\u0013X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0014RJ\u0010\u0015\u001a>\u0012\u0004\u0012\u00020\b\u0012\u0014\u0012\u0012\u0012\u0004\u0012\u00020\b0\u0017j\b\u0012\u0004\u0012\u00020\b`\u00180\u0016j\u001e\u0012\u0004\u0012\u00020\b\u0012\u0014\u0012\u0012\u0012\u0004\u0012\u00020\b0\u0017j\b\u0012\u0004\u0012\u00020\b`\u0018`\u0019X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006$"}, d2 = {"Lcom/psychiatrygarden/widget/PopKnowledgeChartFilter;", "Lcom/lxj/xpopup/core/BottomPopupView;", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "list", "", "Lcom/psychiatrygarden/bean/ChartFilterBean;", "h", "", "defaultSelectMap", "Landroid/util/ArrayMap;", NotifyType.LIGHTS, "Lcom/psychiatrygarden/widget/PopKnowledgeChartFilter$ConfirmListener;", "(Landroid/content/Context;Ljava/util/List;Ljava/lang/String;Landroid/util/ArrayMap;Lcom/psychiatrygarden/widget/PopKnowledgeChartFilter$ConfirmListener;)V", "filterClick", "", "getL", "()Lcom/psychiatrygarden/widget/PopKnowledgeChartFilter$ConfirmListener;", "mAdapter", "com/psychiatrygarden/widget/PopKnowledgeChartFilter$mAdapter$1", "Lcom/psychiatrygarden/widget/PopKnowledgeChartFilter$mAdapter$1;", "paramsMap", "Ljava/util/LinkedHashMap;", "Ljava/util/ArrayList;", "Lkotlin/collections/ArrayList;", "Lkotlin/collections/LinkedHashMap;", "getImplLayoutId", "", "getMaxHeight", "onCreate", "", "onShow", "processMapInOrder", "", "map", "ConfirmListener", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nPopKnowledgeChartFilter.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PopKnowledgeChartFilter.kt\ncom/psychiatrygarden/widget/PopKnowledgeChartFilter\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 4 _Maps.kt\nkotlin/collections/MapsKt___MapsKt\n*L\n1#1,367:1\n1#2:368\n1655#3,8:369\n1855#3:377\n1855#3,2:378\n1856#3:380\n1855#3,2:381\n1855#3,2:383\n125#4:385\n152#4,3:386\n*S KotlinDebug\n*F\n+ 1 PopKnowledgeChartFilter.kt\ncom/psychiatrygarden/widget/PopKnowledgeChartFilter\n*L\n332#1:369,8\n333#1:377\n335#1:378,2\n333#1:380\n342#1:381,2\n351#1:383,2\n316#1:385\n316#1:386,3\n*E\n"})
/* loaded from: classes6.dex */
public final class PopKnowledgeChartFilter extends BottomPopupView {

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
    private final PopKnowledgeChartFilter$mAdapter$1 mAdapter;

    @NotNull
    private final LinkedHashMap<String, ArrayList<String>> paramsMap;

    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0000\bf\u0018\u00002\u00020\u0001J\u001c\u0010\u0002\u001a\u00020\u00032\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00060\u0005H&¨\u0006\u0007"}, d2 = {"Lcom/psychiatrygarden/widget/PopKnowledgeChartFilter$ConfirmListener;", "", "onConfirm", "", "params", "", "", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public interface ConfirmListener {
        void onConfirm(@NotNull Map<String, String> params);
    }

    public /* synthetic */ PopKnowledgeChartFilter(Context context, List list, String str, ArrayMap arrayMap, ConfirmListener confirmListener, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, list, (i2 & 4) != 0 ? null : str, arrayMap, confirmListener);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$0(PopKnowledgeChartFilter this$0, String detailImg, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        KnowledgeRuleActivity.Companion companion = KnowledgeRuleActivity.INSTANCE;
        Context context = this$0.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "context");
        Intrinsics.checkNotNullExpressionValue(detailImg, "detailImg");
        companion.navigationToKnowledgeRuleActivity(context, detailImg, "说明");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$1(PopKnowledgeChartFilter this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$3(PopKnowledgeChartFilter this$0, View view) throws IOException {
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
        return R.layout.pop_knowledge_chart_filter;
    }

    @NotNull
    public final ConfirmListener getL() {
        return this.l;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public int getMaxHeight() {
        return getContext().getResources().getDisplayMetrics().heightPixels - SizeUtil.dp2px(getContext(), 96);
    }

    /* JADX WARN: Removed duplicated region for block: B:31:0x00a6  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x0145  */
    @Override // com.lxj.xpopup.core.BasePopupView
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void onCreate() {
        /*
            Method dump skipped, instructions count: 599
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.psychiatrygarden.widget.PopKnowledgeChartFilter.onCreate():void");
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onShow() {
        super.onShow();
        this.filterClick = false;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public PopKnowledgeChartFilter(@NotNull Context context, @NotNull List<? extends ChartFilterBean> list, @Nullable String str, @NotNull ArrayMap<String, List<String>> defaultSelectMap, @NotNull ConfirmListener l2) {
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
        this.mAdapter = new PopKnowledgeChartFilter$mAdapter$1(this);
    }
}
