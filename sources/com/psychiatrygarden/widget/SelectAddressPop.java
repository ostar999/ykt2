package com.psychiatrygarden.widget;

import android.content.Context;
import android.view.View;
import com.lxj.xpopup.core.BottomPopupView;
import com.psychiatrygarden.activity.purchase.beans.AreaGetPCData;
import com.psychiatrygarden.bean.AddressModel;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;
import top.defaults.view.Division;
import top.defaults.view.DivisionPickerView;

@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001:\u0001\u000fB\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\b\u0010\u000b\u001a\u00020\fH\u0014J\b\u0010\r\u001a\u00020\u000eH\u0014R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0010"}, d2 = {"Lcom/psychiatrygarden/widget/SelectAddressPop;", "Lcom/lxj/xpopup/core/BottomPopupView;", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "addressData", "Lcom/psychiatrygarden/activity/purchase/beans/AreaGetPCData;", "mListener", "Lcom/psychiatrygarden/widget/SelectAddressPop$OnSelectListener;", "(Landroid/content/Context;Lcom/psychiatrygarden/activity/purchase/beans/AreaGetPCData;Lcom/psychiatrygarden/widget/SelectAddressPop$OnSelectListener;)V", "divisionPickerView", "Ltop/defaults/view/DivisionPickerView;", "getImplLayoutId", "", "onCreate", "", "OnSelectListener", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nSelectAddressPop.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SelectAddressPop.kt\ncom/psychiatrygarden/widget/SelectAddressPop\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,64:1\n1855#2:65\n1855#2,2:66\n1856#2:68\n*S KotlinDebug\n*F\n+ 1 SelectAddressPop.kt\ncom/psychiatrygarden/widget/SelectAddressPop\n*L\n36#1:65\n42#1:66,2\n36#1:68\n*E\n"})
/* loaded from: classes6.dex */
public final class SelectAddressPop extends BottomPopupView {

    @NotNull
    private final AreaGetPCData addressData;
    private DivisionPickerView divisionPickerView;

    @NotNull
    private final OnSelectListener mListener;

    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\bf\u0018\u00002\u00020\u0001J(\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\u0005H&¨\u0006\t"}, d2 = {"Lcom/psychiatrygarden/widget/SelectAddressPop$OnSelectListener;", "", "onSelect", "", "province", "", "city", "cityId", "provinceId", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public interface OnSelectListener {
        void onSelect(@NotNull String province, @NotNull String city, @NotNull String cityId, @NotNull String provinceId);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SelectAddressPop(@NotNull Context context, @NotNull AreaGetPCData addressData, @NotNull OnSelectListener mListener) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(addressData, "addressData");
        Intrinsics.checkNotNullParameter(mListener, "mListener");
        this.addressData = addressData;
        this.mListener = mListener;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$0(SelectAddressPop this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public static final void onCreate$lambda$1(SelectAddressPop this$0, Ref.ObjectRef province, Ref.ObjectRef city, Ref.ObjectRef cityId, Ref.ObjectRef provinceId, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(province, "$province");
        Intrinsics.checkNotNullParameter(city, "$city");
        Intrinsics.checkNotNullParameter(cityId, "$cityId");
        Intrinsics.checkNotNullParameter(provinceId, "$provinceId");
        OnSelectListener onSelectListener = this$0.mListener;
        T province2 = province.element;
        Intrinsics.checkNotNullExpressionValue(province2, "province");
        T city2 = city.element;
        Intrinsics.checkNotNullExpressionValue(city2, "city");
        T cityId2 = cityId.element;
        Intrinsics.checkNotNullExpressionValue(cityId2, "cityId");
        T provinceId2 = provinceId.element;
        Intrinsics.checkNotNullExpressionValue(provinceId2, "provinceId");
        onSelectListener.onSelect((String) province2, (String) city2, (String) cityId2, (String) provinceId2);
        this$0.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Type inference failed for: r0v5, types: [T, java.lang.String] */
    /* JADX WARN: Type inference failed for: r1v1, types: [T, java.lang.String] */
    /* JADX WARN: Type inference failed for: r1v2, types: [T, java.lang.String] */
    /* JADX WARN: Type inference failed for: r1v4, types: [T, java.lang.String] */
    public static final void onCreate$lambda$4(Ref.ObjectRef province, Ref.ObjectRef city, Ref.ObjectRef cityId, Ref.ObjectRef provinceId, Division division) {
        Intrinsics.checkNotNullParameter(province, "$province");
        Intrinsics.checkNotNullParameter(city, "$city");
        Intrinsics.checkNotNullParameter(cityId, "$cityId");
        Intrinsics.checkNotNullParameter(provinceId, "$provinceId");
        if (division == null || !(division instanceof AddressModel)) {
            return;
        }
        AddressModel addressModel = (AddressModel) division;
        province.element = addressModel.getParentName();
        city.element = addressModel.name;
        cityId.element = addressModel.id;
        provinceId.element = String.valueOf(addressModel.parentId);
    }

    @Override // com.lxj.xpopup.core.BottomPopupView, com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return R.layout.pop_select_address;
    }

    /* JADX WARN: Type inference failed for: r0v12, types: [T, java.lang.String] */
    /* JADX WARN: Type inference failed for: r0v20, types: [T, java.lang.String] */
    /* JADX WARN: Type inference failed for: r0v25, types: [T, java.lang.String] */
    /* JADX WARN: Type inference failed for: r0v4, types: [T, java.lang.String] */
    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() {
        DivisionPickerView divisionPickerView;
        super.onCreate();
        final Ref.ObjectRef objectRef = new Ref.ObjectRef();
        objectRef.element = this.addressData.getData().get(0).getTitle();
        final Ref.ObjectRef objectRef2 = new Ref.ObjectRef();
        objectRef2.element = this.addressData.getData().get(0).getSub().get(0).getTitle();
        final Ref.ObjectRef objectRef3 = new Ref.ObjectRef();
        objectRef3.element = this.addressData.getData().get(0).getSub().get(0).getArea_id();
        final Ref.ObjectRef objectRef4 = new Ref.ObjectRef();
        objectRef4.element = this.addressData.getData().get(0).getArea_id();
        findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.pg
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SelectAddressPop.onCreate$lambda$0(this.f16801c, view);
            }
        });
        findViewById(R.id.tv_confirm).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.qg
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SelectAddressPop.onCreate$lambda$1(this.f16831c, objectRef, objectRef2, objectRef3, objectRef4, view);
            }
        });
        View viewFindViewById = findViewById(R.id.picker_view);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(R.id.picker_view)");
        DivisionPickerView divisionPickerView2 = (DivisionPickerView) viewFindViewById;
        this.divisionPickerView = divisionPickerView2;
        if (divisionPickerView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("divisionPickerView");
            divisionPickerView2 = null;
        }
        divisionPickerView2.setType(1);
        List<AreaGetPCData.DataBean> data = this.addressData.getData();
        ArrayList arrayList = new ArrayList();
        Intrinsics.checkNotNullExpressionValue(data, "data");
        for (AreaGetPCData.DataBean dataBean : data) {
            AddressModel addressModel = new AddressModel();
            addressModel.name = dataBean.getTitle();
            addressModel.id = dataBean.getArea_id();
            ArrayList arrayList2 = new ArrayList();
            if (dataBean.getSub() != null) {
                List<AreaGetPCData.DataBean.SubBean> sub = dataBean.getSub();
                Intrinsics.checkNotNullExpressionValue(sub, "it.sub");
                for (AreaGetPCData.DataBean.SubBean subBean : sub) {
                    AddressModel addressModel2 = new AddressModel();
                    addressModel2.name = subBean.getTitle();
                    String pid = subBean.getPid();
                    Intrinsics.checkNotNullExpressionValue(pid, "child.pid");
                    addressModel2.parentId = Integer.parseInt(pid);
                    addressModel2.id = subBean.getArea_id();
                    addressModel2.setParentName(dataBean.getTitle());
                    arrayList2.add(addressModel2);
                }
            }
            addressModel.children = arrayList2;
            arrayList.add(addressModel);
        }
        DivisionPickerView divisionPickerView3 = this.divisionPickerView;
        if (divisionPickerView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("divisionPickerView");
            divisionPickerView3 = null;
        }
        divisionPickerView3.setOnSelectedDateChangedListener(new DivisionPickerView.OnSelectedDivisionChangedListener() { // from class: com.psychiatrygarden.widget.rg
            @Override // top.defaults.view.DivisionPickerView.OnSelectedDivisionChangedListener
            public final void onSelectedDivisionChanged(Division division) {
                SelectAddressPop.onCreate$lambda$4(objectRef, objectRef2, objectRef3, objectRef4, division);
            }
        });
        DivisionPickerView divisionPickerView4 = this.divisionPickerView;
        if (divisionPickerView4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("divisionPickerView");
            divisionPickerView = null;
        } else {
            divisionPickerView = divisionPickerView4;
        }
        divisionPickerView.setDivisions(arrayList);
    }
}
