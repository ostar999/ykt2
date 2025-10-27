package com.psychiatrygarden.widget;

import android.widget.TextView;
import com.aliyun.vod.common.utils.UriUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.plv.livescenes.model.PLVLiveClassDetailVO;
import com.psychiatrygarden.bean.ChartFilterBean;
import com.psychiatrygarden.widget.LabelsView;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt__MutableCollectionsJVMKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000\u001b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003*\u0001\u0000\b\n\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001J\u0018\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\u0002H\u0014¨\u0006\b"}, d2 = {"com/psychiatrygarden/widget/PopCustomPlanFilter$mAdapter$1", "Lcom/chad/library/adapter/base/BaseQuickAdapter;", "Lcom/psychiatrygarden/bean/ChartFilterBean;", "Lcom/chad/library/adapter/base/viewholder/BaseViewHolder;", "convert", "", "holder", "item", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nPopCustomPlanFilter.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PopCustomPlanFilter.kt\ncom/psychiatrygarden/widget/PopCustomPlanFilter$mAdapter$1\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,323:1\n1864#2,3:324\n1855#2:327\n1855#2,2:328\n1855#2,2:330\n766#2:332\n857#2,2:333\n1549#2:335\n1620#2,3:336\n1856#2:339\n1855#2:340\n1855#2:341\n1855#2,2:342\n1856#2:344\n1856#2:345\n1864#2,2:346\n1855#2,2:348\n1866#2:350\n1855#2:351\n1855#2:352\n1855#2,2:353\n1856#2:355\n1856#2:356\n1864#2,2:357\n1855#2:359\n1864#2,3:360\n1856#2:363\n1866#2:364\n766#2:366\n857#2,2:367\n2634#2:369\n1855#2,2:371\n2634#2:373\n1#3:365\n1#3:370\n1#3:374\n*S KotlinDebug\n*F\n+ 1 PopCustomPlanFilter.kt\ncom/psychiatrygarden/widget/PopCustomPlanFilter$mAdapter$1\n*L\n47#1:324,3\n72#1:327\n74#1:328,2\n92#1:330,2\n95#1:332\n95#1:333,2\n95#1:335\n95#1:336,3\n72#1:339\n104#1:340\n105#1:341\n107#1:342,2\n105#1:344\n104#1:345\n117#1:346,2\n119#1:348,2\n117#1:350\n139#1:351\n141#1:352\n142#1:353,2\n141#1:355\n139#1:356\n152#1:357,2\n154#1:359\n155#1:360,3\n154#1:363\n152#1:364\n177#1:366\n177#1:367,2\n179#1:369\n183#1:371,2\n221#1:373\n179#1:370\n221#1:374\n*E\n"})
/* loaded from: classes6.dex */
public final class PopCustomPlanFilter$mAdapter$1 extends BaseQuickAdapter<ChartFilterBean, BaseViewHolder> {
    final /* synthetic */ PopCustomPlanFilter this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PopCustomPlanFilter$mAdapter$1(PopCustomPlanFilter popCustomPlanFilter) {
        super(R.layout.item_question_chart_filter, null, 2, null);
        this.this$0 = popCustomPlanFilter;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final CharSequence convert$lambda$0(TextView textView, int i2, ChartFilterBean.ChartFilterValue chartFilterValue) {
        StringBuilder sb = new StringBuilder();
        sb.append(chartFilterValue.getName());
        String title = chartFilterValue.getTitle();
        sb.append(title == null || title.length() == 0 ? "" : chartFilterValue.getTitle());
        String value = chartFilterValue.getValue();
        sb.append(value == null || value.length() == 0 ? "" : chartFilterValue.getValue());
        return sb.toString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void convert$lambda$25(ChartFilterBean item, PopCustomPlanFilter this$0, PopCustomPlanFilter$mAdapter$1 this$1, TextView textView, Object obj, int i2) {
        Object next;
        List<ChartFilterBean.ChartFilterValue> value;
        Intrinsics.checkNotNullParameter(item, "$item");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(this$1, "this$1");
        ChartFilterBean.ChartFilterValue chartFilterValue = item.getValue().get(i2);
        chartFilterValue.setSelect(textView.isSelected());
        ArrayList arrayList = (ArrayList) this$0.paramsMap.get(chartFilterValue.getType());
        if (arrayList == null || arrayList.isEmpty()) {
            arrayList = new ArrayList();
            LinkedHashMap linkedHashMap = this$0.paramsMap;
            String type = chartFilterValue.getType();
            Intrinsics.checkNotNullExpressionValue(type, "labelItem.type");
            linkedHashMap.put(type, arrayList);
        }
        if (chartFilterValue.isSelect()) {
            if (!arrayList.contains(chartFilterValue.getKey())) {
                arrayList.add(chartFilterValue.getKey());
            }
        } else if (arrayList.contains(chartFilterValue.getKey())) {
            arrayList.remove(chartFilterValue.getKey());
        }
        if (Intrinsics.areEqual(UriUtil.QUERY_CATEGORY, chartFilterValue.getType())) {
            for (ChartFilterBean chartFilterBean : this$0.list) {
                if (Intrinsics.areEqual(UriUtil.QUERY_CATEGORY, chartFilterBean.getType())) {
                    List<ChartFilterBean.ChartFilterValue> value2 = chartFilterBean.getValue();
                    Intrinsics.checkNotNullExpressionValue(value2, "it.value");
                    for (ChartFilterBean.ChartFilterValue chartFilterValue2 : value2) {
                        chartFilterValue2.setSelect(textView.isSelected() && Intrinsics.areEqual(chartFilterValue.getKey(), chartFilterValue2.getKey()));
                        if (!chartFilterValue2.isSelect()) {
                            ArrayList arrayList2 = (ArrayList) this$0.paramsMap.get(UriUtil.QUERY_CATEGORY);
                            if (arrayList2 == null || arrayList2.isEmpty()) {
                                this$0.paramsMap.remove(chartFilterBean.getType());
                            } else if (arrayList2.contains(chartFilterValue2.getKey())) {
                                arrayList2.remove(chartFilterValue2.getKey());
                                if (arrayList2.isEmpty()) {
                                    this$0.paramsMap.remove(chartFilterBean.getType());
                                }
                            }
                        }
                    }
                } else if (Intrinsics.areEqual(chartFilterBean.getType(), "frequency")) {
                    List<ChartFilterBean.ChartFilterValue> value3 = chartFilterBean.getValue();
                    Intrinsics.checkNotNullExpressionValue(value3, "it.value");
                    for (ChartFilterBean.ChartFilterValue chartFilterValue3 : value3) {
                        chartFilterValue3.setSelect(textView.isSelected() && Intrinsics.areEqual(chartFilterValue.getKey(), chartFilterValue3.getKey()));
                    }
                    List<ChartFilterBean.ChartFilterValue> value4 = chartFilterBean.getValue();
                    Intrinsics.checkNotNullExpressionValue(value4, "it.value");
                    ArrayList arrayList3 = new ArrayList();
                    for (Object obj2 : value4) {
                        if (((ChartFilterBean.ChartFilterValue) obj2).isSelect()) {
                            arrayList3.add(obj2);
                        }
                    }
                    ArrayList arrayList4 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList3, 10));
                    Iterator it = arrayList3.iterator();
                    while (it.hasNext()) {
                        arrayList4.add(((ChartFilterBean.ChartFilterValue) it.next()).getKey());
                    }
                    if (!arrayList4.isEmpty()) {
                        LinkedHashMap linkedHashMap2 = this$0.paramsMap;
                        Object obj3 = arrayList4.get(0);
                        Intrinsics.checkNotNullExpressionValue(obj3, "keys[0]");
                        linkedHashMap2.put("frequency", CollectionsKt__CollectionsKt.arrayListOf((String) obj3));
                    }
                }
            }
            ArrayList<String> arrayList5 = (ArrayList) this$0.paramsMap.get("frequency");
            if (arrayList5 == null) {
                arrayList5 = new ArrayList();
            }
            if (this$0.paramsMap.get("frequency") == null || !(!arrayList5.isEmpty())) {
                this$0.paramsMap.put("frequency", arrayList5);
            } else {
                for (String str : arrayList5) {
                    for (ChartFilterBean chartFilterBean2 : this$0.list) {
                        if (Intrinsics.areEqual(chartFilterBean2.getType(), "frequency") && (value = chartFilterBean2.getValue()) != null) {
                            Intrinsics.checkNotNullExpressionValue(value, "value");
                            Iterator<T> it2 = value.iterator();
                            while (it2.hasNext()) {
                                ((ChartFilterBean.ChartFilterValue) it2.next()).setSelect(false);
                            }
                            Unit unit = Unit.INSTANCE;
                        }
                    }
                }
                arrayList5.clear();
            }
            this$1.notifyDataSetChanged();
            String frequency = chartFilterValue.getFrequency();
            if (!(frequency == null || frequency.length() == 0)) {
                int i3 = 0;
                for (Object obj4 : this$0.list) {
                    int i4 = i3 + 1;
                    if (i3 < 0) {
                        CollectionsKt__CollectionsKt.throwIndexOverflow();
                    }
                    ChartFilterBean chartFilterBean3 = (ChartFilterBean) obj4;
                    if (Intrinsics.areEqual(chartFilterBean3.getType(), "frequency")) {
                        List<ChartFilterBean.ChartFilterValue> value5 = chartFilterBean3.getValue();
                        if (value5 != null) {
                            Intrinsics.checkNotNullExpressionValue(value5, "value");
                            for (ChartFilterBean.ChartFilterValue chartFilterValue4 : value5) {
                                if (Intrinsics.areEqual(chartFilterValue4.getKey(), chartFilterValue.getFrequency())) {
                                    chartFilterValue4.setSelect(chartFilterValue.isSelect());
                                    if (chartFilterValue4.isSelect() && !arrayList5.contains(chartFilterValue4.getKey())) {
                                        arrayList5.add(chartFilterValue4.getKey());
                                    }
                                } else {
                                    chartFilterValue4.setSelect(arrayList5.contains(chartFilterValue4.getKey()));
                                }
                            }
                            Unit unit2 = Unit.INSTANCE;
                        }
                        this$1.notifyItemChanged(i3);
                    }
                    i3 = i4;
                }
            }
            ArrayList arrayList6 = (ArrayList) this$0.paramsMap.get(PLVLiveClassDetailVO.LiveStatus.LIVE_START);
            if (arrayList6 == null) {
                arrayList6 = new ArrayList();
            }
            if (this$0.paramsMap.get(PLVLiveClassDetailVO.LiveStatus.LIVE_START) == null) {
                this$0.paramsMap.put(PLVLiveClassDetailVO.LiveStatus.LIVE_START, arrayList6);
            }
            if (arrayList6.size() > 0) {
                for (ChartFilterBean chartFilterBean4 : this$0.list) {
                    if (Intrinsics.areEqual(chartFilterBean4.getType(), PLVLiveClassDetailVO.LiveStatus.LIVE_START)) {
                        List<ChartFilterBean.ChartFilterValue> value6 = chartFilterBean4.getValue();
                        Intrinsics.checkNotNullExpressionValue(value6, "it.value");
                        for (ChartFilterBean.ChartFilterValue chartFilterValue5 : value6) {
                            Iterator it3 = arrayList6.iterator();
                            while (it3.hasNext()) {
                                if (Intrinsics.areEqual(chartFilterValue5.getKey(), (String) it3.next())) {
                                    chartFilterValue5.setSelect(false);
                                }
                            }
                        }
                    }
                }
                arrayList6.clear();
            }
            int i5 = 0;
            for (Object obj5 : this$0.list) {
                int i6 = i5 + 1;
                if (i5 < 0) {
                    CollectionsKt__CollectionsKt.throwIndexOverflow();
                }
                ChartFilterBean chartFilterBean5 = (ChartFilterBean) obj5;
                if (Intrinsics.areEqual(chartFilterBean5.getType(), PLVLiveClassDetailVO.LiveStatus.LIVE_START)) {
                    List<String> star = chartFilterValue.getStar();
                    if (star != null) {
                        Intrinsics.checkNotNullExpressionValue(star, "star");
                        for (String str2 : star) {
                            List<ChartFilterBean.ChartFilterValue> value7 = chartFilterBean5.getValue();
                            Intrinsics.checkNotNullExpressionValue(value7, "b.value");
                            int i7 = 0;
                            for (Object obj6 : value7) {
                                int i8 = i7 + 1;
                                if (i7 < 0) {
                                    CollectionsKt__CollectionsKt.throwIndexOverflow();
                                }
                                ChartFilterBean.ChartFilterValue chartFilterValue6 = (ChartFilterBean.ChartFilterValue) obj6;
                                if (Intrinsics.areEqual(chartFilterValue6.getKey(), str2)) {
                                    chartFilterValue6.setSelect(chartFilterValue.isSelect());
                                    if (chartFilterValue6.isSelect() && !arrayList6.contains(str2)) {
                                        arrayList6.add(str2);
                                    }
                                } else {
                                    chartFilterValue6.setSelect(arrayList6.contains(chartFilterValue6.getKey()));
                                }
                                i7 = i8;
                            }
                        }
                        Unit unit3 = Unit.INSTANCE;
                    }
                    this$1.notifyItemChanged(i5);
                }
                i5 = i6;
            }
            if (!arrayList6.isEmpty()) {
                CollectionsKt__MutableCollectionsJVMKt.sort(arrayList6);
            }
        } else {
            Object obj7 = null;
            if (Intrinsics.areEqual("frequency", chartFilterValue.getType())) {
                Iterator<T> it4 = this$1.getData().iterator();
                while (true) {
                    if (it4.hasNext()) {
                        next = it4.next();
                        if (Intrinsics.areEqual(((ChartFilterBean) next).getType(), UriUtil.QUERY_CATEGORY)) {
                            break;
                        }
                    } else {
                        next = null;
                        break;
                    }
                }
                ChartFilterBean chartFilterBean6 = (ChartFilterBean) next;
                if (chartFilterBean6 == null) {
                    return;
                }
                Iterator<T> it5 = this$1.getData().iterator();
                while (true) {
                    if (!it5.hasNext()) {
                        break;
                    }
                    Object next2 = it5.next();
                    if (Intrinsics.areEqual(((ChartFilterBean) next2).getType(), "frequency")) {
                        obj7 = next2;
                        break;
                    }
                }
                ChartFilterBean chartFilterBean7 = (ChartFilterBean) obj7;
                if (chartFilterBean7 == null) {
                    return;
                }
                List<ChartFilterBean.ChartFilterValue> value8 = chartFilterBean7.getValue();
                Intrinsics.checkNotNullExpressionValue(value8, "frequencyItem.value");
                ArrayList arrayList7 = new ArrayList();
                for (Object obj8 : value8) {
                    if (((ChartFilterBean.ChartFilterValue) obj8).isSelect()) {
                        arrayList7.add(obj8);
                    }
                }
                if (arrayList7.size() > 1 || arrayList7.isEmpty()) {
                    Iterator<T> it6 = chartFilterBean6.getValue().iterator();
                    while (it6.hasNext()) {
                        ((ChartFilterBean.ChartFilterValue) it6.next()).setSelect(false);
                    }
                    this$0.paramsMap.remove(UriUtil.QUERY_CATEGORY);
                } else {
                    List<ChartFilterBean.ChartFilterValue> value9 = chartFilterBean6.getValue();
                    Intrinsics.checkNotNullExpressionValue(value9, "categoryItem.value");
                    for (ChartFilterBean.ChartFilterValue chartFilterValue7 : value9) {
                        if (chartFilterValue7.isSelect() && !Intrinsics.areEqual(chartFilterValue7.getFrequency(), chartFilterValue.getFrequency())) {
                            chartFilterValue7.setSelect(false);
                            ArrayList arrayList8 = (ArrayList) this$0.paramsMap.get("frequency");
                            if (arrayList8 != null && arrayList8.size() > 0) {
                                arrayList8.remove(chartFilterValue7.getKey());
                            }
                        }
                    }
                }
            } else if (Intrinsics.areEqual(PLVLiveClassDetailVO.LiveStatus.LIVE_START, chartFilterValue.getType())) {
                Iterator<T> it7 = this$1.getData().iterator();
                while (true) {
                    if (!it7.hasNext()) {
                        break;
                    }
                    Object next3 = it7.next();
                    if (Intrinsics.areEqual(((ChartFilterBean) next3).getType(), UriUtil.QUERY_CATEGORY)) {
                        obj7 = next3;
                        break;
                    }
                }
                ChartFilterBean chartFilterBean8 = (ChartFilterBean) obj7;
                if (chartFilterBean8 == null) {
                    return;
                }
                Iterator<T> it8 = chartFilterBean8.getValue().iterator();
                while (it8.hasNext()) {
                    ((ChartFilterBean.ChartFilterValue) it8.next()).setSelect(false);
                }
                this$0.paramsMap.remove(UriUtil.QUERY_CATEGORY);
            }
        }
        CollectionsKt__MutableCollectionsJVMKt.sort(arrayList);
        this$1.notifyDataSetChanged();
    }

    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(@NotNull BaseViewHolder holder, @NotNull final ChartFilterBean item) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        Intrinsics.checkNotNullParameter(item, "item");
        holder.setText(R.id.title, item.getName());
        LabelsView labelsView = (LabelsView) holder.getView(R.id.labelView);
        labelsView.setLabels(item.getValue(), new LabelsView.LabelTextProvider() { // from class: com.psychiatrygarden.widget.dc
            @Override // com.psychiatrygarden.widget.LabelsView.LabelTextProvider
            public final CharSequence getLabelText(TextView textView, int i2, Object obj) {
                return PopCustomPlanFilter$mAdapter$1.convert$lambda$0(textView, i2, (ChartFilterBean.ChartFilterValue) obj);
            }
        });
        ArrayList arrayList = new ArrayList();
        List<ChartFilterBean.ChartFilterValue> value = item.getValue();
        Intrinsics.checkNotNullExpressionValue(value, "item.value");
        int i2 = 0;
        for (Object obj : value) {
            int i3 = i2 + 1;
            if (i2 < 0) {
                CollectionsKt__CollectionsKt.throwIndexOverflow();
            }
            if (((ChartFilterBean.ChartFilterValue) obj).isSelect()) {
                arrayList.add(Integer.valueOf(i2));
            }
            i2 = i3;
        }
        if (!arrayList.isEmpty()) {
            labelsView.setSelects(arrayList);
        }
        final PopCustomPlanFilter popCustomPlanFilter = this.this$0;
        labelsView.setOnLabelClickListener(new LabelsView.OnLabelClickListener() { // from class: com.psychiatrygarden.widget.ec
            @Override // com.psychiatrygarden.widget.LabelsView.OnLabelClickListener
            public final void onLabelClick(TextView textView, Object obj2, int i4) {
                PopCustomPlanFilter$mAdapter$1.convert$lambda$25(item, popCustomPlanFilter, this, textView, obj2, i4);
            }
        });
    }
}
