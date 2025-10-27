package com.angcyo.tablayout;

import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000d\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010!\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010 \n\u0002\b\u0004\b\u0016\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001e\u0010&\u001a\u00020'2\u0006\u0010(\u001a\u00020\f2\u0006\u0010)\u001a\u00020'2\u0006\u0010*\u001a\u00020'J)\u0010+\u001a\u00020\u00002\u0006\u0010,\u001a\u00020\u00182\u0019\b\u0002\u0010-\u001a\u0013\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020/0.¢\u0006\u0002\b0J\u001e\u00101\u001a\u00020'2\u0006\u0010(\u001a\u00020\f2\u0006\u0010)\u001a\u00020'2\u0006\u0010*\u001a\u00020'J\u001e\u00102\u001a\u00020/2\u0006\u00103\u001a\u00020\f2\u0006\u00104\u001a\u00020'2\u0006\u0010*\u001a\u00020'J6\u00105\u001a\u00020/2\u0006\u0010(\u001a\u00020\f2\b\b\u0002\u0010)\u001a\u00020'2\b\b\u0002\u00106\u001a\u00020'2\b\b\u0002\u0010*\u001a\u00020'2\b\b\u0002\u00107\u001a\u00020'J2\u00105\u001a\u00020/2\f\u00108\u001a\b\u0012\u0004\u0012\u00020\f0\u001e2\b\b\u0002\u0010)\u001a\u00020'2\b\b\u0002\u00106\u001a\u00020'2\b\b\u0002\u0010*\u001a\u00020'J$\u00109\u001a\u00020/2\b\b\u0002\u0010)\u001a\u00020'2\b\b\u0002\u00106\u001a\u00020'2\b\b\u0002\u0010*\u001a\u00020'J\u0006\u0010:\u001a\u00020/J\u0006\u0010;\u001a\u00020/J\f\u0010<\u001a\b\u0012\u0004\u0012\u00020\"0=J\n\u0010>\u001a\u00020'*\u00020\"J\u0012\u0010?\u001a\u00020/*\u00020\"2\u0006\u0010@\u001a\u00020'R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u001a\u0010\u000b\u001a\u00020\fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u001a\u0010\u0011\u001a\u00020\u0012X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R\u001c\u0010\u0017\u001a\u0004\u0018\u00010\u0018X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u001a\"\u0004\b\u001b\u0010\u001cR\u0019\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\f0\u001e8F¢\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010 R\u0019\u0010!\u001a\b\u0012\u0004\u0012\u00020\"0\u001e8F¢\u0006\b\n\u0000\u001a\u0004\b#\u0010 R\u0017\u0010$\u001a\b\u0012\u0004\u0012\u00020\"0\u001e¢\u0006\b\n\u0000\u001a\u0004\b%\u0010 ¨\u0006A"}, d2 = {"Lcom/angcyo/tablayout/DslSelector;", "", "()V", "_onCheckedChangeListener", "Landroid/widget/CompoundButton$OnCheckedChangeListener;", "get_onCheckedChangeListener", "()Landroid/widget/CompoundButton$OnCheckedChangeListener;", "_onChildClickListener", "Landroid/view/View$OnClickListener;", "get_onChildClickListener", "()Landroid/view/View$OnClickListener;", "dslSelectIndex", "", "getDslSelectIndex", "()I", "setDslSelectIndex", "(I)V", "dslSelectorConfig", "Lcom/angcyo/tablayout/DslSelectorConfig;", "getDslSelectorConfig", "()Lcom/angcyo/tablayout/DslSelectorConfig;", "setDslSelectorConfig", "(Lcom/angcyo/tablayout/DslSelectorConfig;)V", "parent", "Landroid/view/ViewGroup;", "getParent", "()Landroid/view/ViewGroup;", "setParent", "(Landroid/view/ViewGroup;)V", "selectorIndexList", "", "getSelectorIndexList", "()Ljava/util/List;", "selectorViewList", "Landroid/view/View;", "getSelectorViewList", "visibleViewList", "getVisibleViewList", "_selector", "", "index", "select", "fromUser", "install", "viewGroup", "config", "Lkotlin/Function1;", "", "Lkotlin/ExtensionFunctionType;", "interceptSelector", "notifySelectChange", "lastSelectorIndex", "reselect", "selector", "notify", "forceNotify", "indexList", "selectorAll", "updateClickListener", "updateStyle", "updateVisibleList", "", "isSe", "setSe", "se", "TabLayout_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes2.dex */
public class DslSelector {

    @Nullable
    private ViewGroup parent;

    @NotNull
    private DslSelectorConfig dslSelectorConfig = new DslSelectorConfig();

    @NotNull
    private final List<View> visibleViewList = new ArrayList();

    @NotNull
    private final List<Integer> selectorIndexList = new ArrayList();

    @NotNull
    private final List<View> selectorViewList = new ArrayList();

    @NotNull
    private final View.OnClickListener _onChildClickListener = new View.OnClickListener() { // from class: com.angcyo.tablayout.b
        @Override // android.view.View.OnClickListener
        public final void onClick(View view) {
            DslSelector._onChildClickListener$lambda$2(this.f3602c, view);
        }
    };

    @NotNull
    private final CompoundButton.OnCheckedChangeListener _onCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() { // from class: com.angcyo.tablayout.c
        @Override // android.widget.CompoundButton.OnCheckedChangeListener
        public final void onCheckedChanged(CompoundButton compoundButton, boolean z2) {
            DslSelector._onCheckedChangeListener$lambda$3(compoundButton, z2);
        }
    };
    private int dslSelectIndex = -1;

    /* JADX INFO: Access modifiers changed from: private */
    public static final void _onCheckedChangeListener$lambda$3(CompoundButton compoundButton, boolean z2) {
        compoundButton.setChecked(compoundButton.isSelected());
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0028  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void _onChildClickListener$lambda$2(com.angcyo.tablayout.DslSelector r10, android.view.View r11) {
        /*
            java.lang.String r0 = "this$0"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r10, r0)
            java.util.List<android.view.View> r0 = r10.visibleViewList
            int r0 = r0.indexOf(r11)
            com.angcyo.tablayout.DslSelectorConfig r1 = r10.dslSelectorConfig
            boolean r1 = r1.getDslMultiMode()
            r2 = 0
            r3 = 1
            if (r1 != 0) goto L1d
            com.angcyo.tablayout.DslSelectorConfig r1 = r10.dslSelectorConfig
            int r1 = r1.getDslMinSelectLimit()
            if (r1 >= r3) goto L28
        L1d:
            java.lang.String r1 = "it"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r11, r1)
            boolean r1 = r10.isSe(r11)
            if (r1 != 0) goto L2a
        L28:
            r6 = r3
            goto L2b
        L2a:
            r6 = r2
        L2b:
            boolean r0 = r10.interceptSelector(r0, r6, r3)
            if (r0 != 0) goto L4c
            java.util.List<android.view.View> r0 = r10.visibleViewList
            int r5 = r0.indexOf(r11)
            r7 = 1
            r8 = 1
            boolean r11 = r11 instanceof android.widget.CompoundButton
            if (r11 == 0) goto L47
            com.angcyo.tablayout.DslSelectorConfig r11 = r10.dslSelectorConfig
            boolean r11 = r11.getDslMultiMode()
            if (r11 == 0) goto L47
            r9 = r3
            goto L48
        L47:
            r9 = r2
        L48:
            r4 = r10
            r4.selector(r5, r6, r7, r8, r9)
        L4c:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.angcyo.tablayout.DslSelector._onChildClickListener$lambda$2(com.angcyo.tablayout.DslSelector, android.view.View):void");
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ DslSelector install$default(DslSelector dslSelector, ViewGroup viewGroup, Function1 function1, int i2, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: install");
        }
        if ((i2 & 2) != 0) {
            function1 = new Function1<DslSelectorConfig, Unit>() { // from class: com.angcyo.tablayout.DslSelector.install.1
                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(DslSelectorConfig dslSelectorConfig) {
                    invoke2(dslSelectorConfig);
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(@NotNull DslSelectorConfig dslSelectorConfig) {
                    Intrinsics.checkNotNullParameter(dslSelectorConfig, "$this$null");
                }
            };
        }
        return dslSelector.install(viewGroup, function1);
    }

    public static /* synthetic */ void selector$default(DslSelector dslSelector, int i2, boolean z2, boolean z3, boolean z4, boolean z5, int i3, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: selector");
        }
        dslSelector.selector(i2, (i3 & 2) != 0 ? true : z2, (i3 & 4) != 0 ? true : z3, (i3 & 8) != 0 ? false : z4, (i3 & 16) != 0 ? false : z5);
    }

    public static /* synthetic */ void selectorAll$default(DslSelector dslSelector, boolean z2, boolean z3, boolean z4, int i2, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: selectorAll");
        }
        if ((i2 & 1) != 0) {
            z2 = true;
        }
        if ((i2 & 2) != 0) {
            z3 = true;
        }
        if ((i2 & 4) != 0) {
            z4 = true;
        }
        dslSelector.selectorAll(z2, z3, z4);
    }

    public final boolean _selector(int index, boolean select, boolean fromUser) {
        List<View> list = this.visibleViewList;
        if (!(index >= 0 && index < list.size())) {
            LibExKt.logi("index out of list.");
            return false;
        }
        List<Integer> selectorIndexList = getSelectorIndexList();
        List<View> selectorViewList = getSelectorViewList();
        if (!selectorIndexList.isEmpty()) {
            if (select) {
                if (!this.dslSelectorConfig.getDslMultiMode()) {
                    Iterator<T> it = selectorIndexList.iterator();
                    while (it.hasNext()) {
                        int iIntValue = ((Number) it.next()).intValue();
                        if (iIntValue != index) {
                            setSe(list.get(iIntValue), false);
                        }
                    }
                    if (selectorIndexList.contains(Integer.valueOf(index))) {
                        return true;
                    }
                } else if (selectorIndexList.contains(Integer.valueOf(index))) {
                    return false;
                }
            } else if (!selectorIndexList.contains(Integer.valueOf(index))) {
                return false;
            }
        }
        if (select) {
            if (selectorViewList.size() + 1 > this.dslSelectorConfig.getDslMaxSelectLimit()) {
                return false;
            }
        } else if (selectorViewList.size() - 1 < this.dslSelectorConfig.getDslMinSelectLimit()) {
            return false;
        }
        View view = list.get(index);
        setSe(view, select);
        if (!this.dslSelectorConfig.getDslMultiMode()) {
            for (View view2 : selectorViewList) {
                int iIndexOf = list.indexOf(view2);
                if (iIndexOf != index) {
                    Function4<View, Integer, Boolean, Boolean, Boolean> onSelectItemView = this.dslSelectorConfig.getOnSelectItemView();
                    Integer numValueOf = Integer.valueOf(iIndexOf);
                    Boolean bool = Boolean.FALSE;
                    if (!onSelectItemView.invoke(view2, numValueOf, bool, Boolean.valueOf(fromUser)).booleanValue()) {
                        setSe(view2, false);
                        this.dslSelectorConfig.getOnStyleItemView().invoke(view2, Integer.valueOf(iIndexOf), bool);
                    }
                }
            }
        }
        this.dslSelectorConfig.getOnStyleItemView().invoke(view, Integer.valueOf(index), Boolean.valueOf(select));
        return true;
    }

    public final int getDslSelectIndex() {
        return this.dslSelectIndex;
    }

    @NotNull
    public final DslSelectorConfig getDslSelectorConfig() {
        return this.dslSelectorConfig;
    }

    @Nullable
    public final ViewGroup getParent() {
        return this.parent;
    }

    @NotNull
    public final List<Integer> getSelectorIndexList() {
        this.selectorIndexList.clear();
        int i2 = 0;
        for (Object obj : this.visibleViewList) {
            int i3 = i2 + 1;
            if (i2 < 0) {
                CollectionsKt__CollectionsKt.throwIndexOverflow();
            }
            if (isSe((View) obj)) {
                this.selectorIndexList.add(Integer.valueOf(i2));
            }
            i2 = i3;
        }
        return this.selectorIndexList;
    }

    @NotNull
    public final List<View> getSelectorViewList() {
        this.selectorViewList.clear();
        int i2 = 0;
        for (Object obj : this.visibleViewList) {
            int i3 = i2 + 1;
            if (i2 < 0) {
                CollectionsKt__CollectionsKt.throwIndexOverflow();
            }
            View view = (View) obj;
            if (isSe(view) || i2 == this.dslSelectIndex) {
                this.selectorViewList.add(view);
            }
            i2 = i3;
        }
        return this.selectorViewList;
    }

    @NotNull
    public final List<View> getVisibleViewList() {
        return this.visibleViewList;
    }

    @NotNull
    public final CompoundButton.OnCheckedChangeListener get_onCheckedChangeListener() {
        return this._onCheckedChangeListener;
    }

    @NotNull
    public final View.OnClickListener get_onChildClickListener() {
        return this._onChildClickListener;
    }

    @NotNull
    public final DslSelector install(@NotNull ViewGroup viewGroup, @NotNull Function1<? super DslSelectorConfig, Unit> config) {
        Intrinsics.checkNotNullParameter(viewGroup, "viewGroup");
        Intrinsics.checkNotNullParameter(config, "config");
        this.dslSelectIndex = -1;
        this.parent = viewGroup;
        updateVisibleList();
        config.invoke(this.dslSelectorConfig);
        updateStyle();
        updateClickListener();
        int size = this.visibleViewList.size();
        int i2 = this.dslSelectIndex;
        boolean z2 = false;
        if (i2 >= 0 && i2 < size) {
            z2 = true;
        }
        if (z2) {
            selector$default(this, i2, false, false, false, false, 30, null);
        }
        return this;
    }

    public final boolean interceptSelector(int index, boolean select, boolean fromUser) {
        List<View> list = this.visibleViewList;
        boolean z2 = false;
        if (index >= 0 && index < list.size()) {
            z2 = true;
        }
        if (z2) {
            return this.dslSelectorConfig.getOnSelectItemView().invoke(list.get(index), Integer.valueOf(index), Boolean.valueOf(select), Boolean.valueOf(fromUser)).booleanValue();
        }
        return true;
    }

    public final boolean isSe(@NotNull View view) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        if (!view.isSelected()) {
            if (!(view instanceof CompoundButton ? ((CompoundButton) view).isChecked() : false)) {
                return false;
            }
        }
        return true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void notifySelectChange(int lastSelectorIndex, boolean reselect, boolean fromUser) {
        List<Integer> selectorIndexList = getSelectorIndexList();
        this.dslSelectorConfig.getOnSelectViewChange().invoke(CollectionsKt___CollectionsKt.getOrNull(this.visibleViewList, lastSelectorIndex), getSelectorViewList(), Boolean.valueOf(reselect), Boolean.valueOf(fromUser));
        this.dslSelectorConfig.getOnSelectIndexChange().invoke(Integer.valueOf(lastSelectorIndex), selectorIndexList, Boolean.valueOf(reselect), Boolean.valueOf(fromUser));
    }

    public final void selector(int index, boolean select, boolean notify, boolean fromUser, boolean forceNotify) {
        List list = CollectionsKt___CollectionsKt.toList(getSelectorIndexList());
        Integer num = (Integer) CollectionsKt___CollectionsKt.lastOrNull(list);
        boolean z2 = true;
        boolean z3 = !this.dslSelectorConfig.getDslMultiMode() && (list.isEmpty() ^ true) && list.contains(Integer.valueOf(index));
        if (!_selector(index, select, fromUser) && !forceNotify) {
            z2 = false;
        }
        if ((LibExKt.isChange(list, getSelectorIndexList()) ? z2 : false) || z3) {
            Integer num2 = (Integer) CollectionsKt___CollectionsKt.lastOrNull((List) getSelectorIndexList());
            this.dslSelectIndex = num2 != null ? num2.intValue() : -1;
            if (notify) {
                notifySelectChange(num != null ? num.intValue() : -1, z3, fromUser);
            }
        }
    }

    public final void selectorAll(boolean select, boolean notify, boolean fromUser) {
        List<View> list = this.visibleViewList;
        ArrayList arrayList = new ArrayList();
        int i2 = 0;
        for (Object obj : list) {
            int i3 = i2 + 1;
            if (i2 < 0) {
                CollectionsKt__CollectionsKt.throwIndexOverflow();
            }
            arrayList.add(Integer.valueOf(i2));
            i2 = i3;
        }
        selector(arrayList, select, notify, fromUser);
    }

    public final void setDslSelectIndex(int i2) {
        this.dslSelectIndex = i2;
    }

    public final void setDslSelectorConfig(@NotNull DslSelectorConfig dslSelectorConfig) {
        Intrinsics.checkNotNullParameter(dslSelectorConfig, "<set-?>");
        this.dslSelectorConfig = dslSelectorConfig;
    }

    public final void setParent(@Nullable ViewGroup viewGroup) {
        this.parent = viewGroup;
    }

    public final void setSe(@NotNull View view, boolean z2) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        view.setSelected(z2);
        if (view instanceof CompoundButton) {
            ((CompoundButton) view).setChecked(z2);
        }
    }

    public final void updateClickListener() {
        ViewGroup viewGroup = this.parent;
        if (viewGroup != null) {
            int childCount = viewGroup.getChildCount();
            for (int i2 = 0; i2 < childCount; i2++) {
                View childAt = viewGroup.getChildAt(i2);
                if (childAt != null) {
                    Intrinsics.checkNotNullExpressionValue(childAt, "getChildAt(i)");
                    childAt.setOnClickListener(this._onChildClickListener);
                    if (childAt instanceof CompoundButton) {
                        ((CompoundButton) childAt).setOnCheckedChangeListener(this._onCheckedChangeListener);
                    }
                }
            }
        }
    }

    public final void updateStyle() {
        int i2 = 0;
        for (Object obj : this.visibleViewList) {
            int i3 = i2 + 1;
            if (i2 < 0) {
                CollectionsKt__CollectionsKt.throwIndexOverflow();
            }
            View view = (View) obj;
            this.dslSelectorConfig.getOnStyleItemView().invoke(view, Integer.valueOf(i2), Boolean.valueOf(this.dslSelectIndex == i2 || isSe(view)));
            i2 = i3;
        }
    }

    @NotNull
    public final List<View> updateVisibleList() {
        this.visibleViewList.clear();
        ViewGroup viewGroup = this.parent;
        boolean z2 = false;
        if (viewGroup != null) {
            int childCount = viewGroup.getChildCount();
            for (int i2 = 0; i2 < childCount; i2++) {
                View childAt = viewGroup.getChildAt(i2);
                if (childAt != null) {
                    Intrinsics.checkNotNullExpressionValue(childAt, "getChildAt(i)");
                    if (childAt.getVisibility() == 0) {
                        this.visibleViewList.add(childAt);
                    }
                }
            }
        }
        int size = this.visibleViewList.size();
        int i3 = this.dslSelectIndex;
        if (i3 >= 0 && i3 < size) {
            z2 = true;
        }
        if (!z2) {
            this.dslSelectIndex = -1;
        } else if (!isSe(this.visibleViewList.get(i3))) {
            setSe(this.visibleViewList.get(this.dslSelectIndex), true);
        }
        return this.visibleViewList;
    }

    public static /* synthetic */ void selector$default(DslSelector dslSelector, List list, boolean z2, boolean z3, boolean z4, int i2, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: selector");
        }
        if ((i2 & 2) != 0) {
            z2 = true;
        }
        if ((i2 & 4) != 0) {
            z3 = true;
        }
        if ((i2 & 8) != 0) {
            z4 = false;
        }
        dslSelector.selector(list, z2, z3, z4);
    }

    public final void selector(@NotNull List<Integer> indexList, boolean select, boolean notify, boolean fromUser) {
        boolean z2;
        Intrinsics.checkNotNullParameter(indexList, "indexList");
        Integer num = (Integer) CollectionsKt___CollectionsKt.lastOrNull((List) getSelectorIndexList());
        Iterator<T> it = indexList.iterator();
        loop0: while (true) {
            z2 = false;
            while (it.hasNext()) {
                if (_selector(((Number) it.next()).intValue(), select, fromUser) || z2) {
                    z2 = true;
                }
            }
        }
        if (z2) {
            Integer num2 = (Integer) CollectionsKt___CollectionsKt.lastOrNull((List) getSelectorIndexList());
            this.dslSelectIndex = num2 != null ? num2.intValue() : -1;
            if (notify) {
                notifySelectChange(num != null ? num.intValue() : -1, false, fromUser);
            }
        }
    }
}
