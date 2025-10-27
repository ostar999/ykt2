package com.psychiatrygarden.widget;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.lxj.xpopup.core.DrawerPopupView;
import com.meizu.cloud.pushsdk.notification.model.NotifyType;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.widget.SelectSpeedFullScreenPopWindow;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001:\u0001\u0012B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\b\u0010\u000e\u001a\u00020\u000fH\u0014J\b\u0010\u0010\u001a\u00020\u0011H\u0014R\u000e\u0010\t\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u000e\u0010\f\u001a\u00020\rX\u0082.¢\u0006\u0002\n\u0000¨\u0006\u0013"}, d2 = {"Lcom/psychiatrygarden/widget/SelectSpeedFullScreenPopWindow;", "Lcom/lxj/xpopup/core/DrawerPopupView;", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "defaultSelectString", "", NotifyType.LIGHTS, "Lcom/psychiatrygarden/widget/SelectSpeedFullScreenPopWindow$OnSpeedSelectListener;", "(Landroid/content/Context;Ljava/lang/String;Lcom/psychiatrygarden/widget/SelectSpeedFullScreenPopWindow$OnSpeedSelectListener;)V", "defaultSelect", "getL", "()Lcom/psychiatrygarden/widget/SelectSpeedFullScreenPopWindow$OnSpeedSelectListener;", "rvList", "Landroidx/recyclerview/widget/RecyclerView;", "getImplLayoutId", "", "onCreate", "", "OnSpeedSelectListener", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class SelectSpeedFullScreenPopWindow extends DrawerPopupView {

    @NotNull
    private String defaultSelect;

    @NotNull
    private final String defaultSelectString;

    @NotNull
    private final OnSpeedSelectListener l;
    private RecyclerView rvList;

    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0000\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&¨\u0006\u0006"}, d2 = {"Lcom/psychiatrygarden/widget/SelectSpeedFullScreenPopWindow$OnSpeedSelectListener;", "", "onSpeedSelect", "", "type", "", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public interface OnSpeedSelectListener {
        void onSpeedSelect(int type);
    }

    @Metadata(d1 = {"\u0000\u001b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003*\u0001\u0000\b\n\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001J\u0018\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\u0002H\u0014¨\u0006\b"}, d2 = {"com/psychiatrygarden/widget/SelectSpeedFullScreenPopWindow$onCreate$1", "Lcom/chad/library/adapter/base/BaseQuickAdapter;", "", "Lcom/chad/library/adapter/base/viewholder/BaseViewHolder;", "convert", "", "holder", "item", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.psychiatrygarden.widget.SelectSpeedFullScreenPopWindow$onCreate$1, reason: invalid class name */
    public static final class AnonymousClass1 extends BaseQuickAdapter<String, BaseViewHolder> {
        final /* synthetic */ SelectSpeedFullScreenPopWindow this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(ArrayList<String> arrayList, final SelectSpeedFullScreenPopWindow selectSpeedFullScreenPopWindow) {
            super(R.layout.item_speed_definition, null, 2, 0 == true ? 1 : 0);
            this.this$0 = selectSpeedFullScreenPopWindow;
            setList(arrayList);
            setOnItemClickListener(new OnItemClickListener() { // from class: com.psychiatrygarden.widget.wg
                @Override // com.chad.library.adapter.base.listener.OnItemClickListener
                public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
                    SelectSpeedFullScreenPopWindow.AnonymousClass1._init_$lambda$0(selectSpeedFullScreenPopWindow, this, baseQuickAdapter, view, i2);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void _init_$lambda$0(SelectSpeedFullScreenPopWindow this$0, AnonymousClass1 this$1, BaseQuickAdapter adapter, View view, int i2) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            Intrinsics.checkNotNullParameter(this$1, "this$1");
            Intrinsics.checkNotNullParameter(adapter, "adapter");
            Intrinsics.checkNotNullParameter(view, "<anonymous parameter 1>");
            Object item = adapter.getItem(i2);
            Intrinsics.checkNotNull(item, "null cannot be cast to non-null type kotlin.String");
            this$0.defaultSelect = (String) item;
            this$0.getL().onSpeedSelect(i2);
            this$1.notifyDataSetChanged();
            this$0.dismiss();
        }

        @Override // com.chad.library.adapter.base.BaseQuickAdapter
        public void convert(@NotNull BaseViewHolder holder, @NotNull String item) {
            Intrinsics.checkNotNullParameter(holder, "holder");
            Intrinsics.checkNotNullParameter(item, "item");
            holder.setText(R.id.tv_option, item);
            if (TextUtils.equals(item, this.this$0.defaultSelect)) {
                if (SkinManager.getCurrentSkinType(getContext()) == 1) {
                    ((TextView) holder.getView(R.id.tv_option)).setTextColor(getContext().getColor(R.color.color_B2575C));
                } else {
                    ((TextView) holder.getView(R.id.tv_option)).setTextColor(getContext().getColor(R.color.color_F95843));
                }
            }
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SelectSpeedFullScreenPopWindow(@NotNull Context context, @NotNull String defaultSelectString, @NotNull OnSpeedSelectListener l2) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(defaultSelectString, "defaultSelectString");
        Intrinsics.checkNotNullParameter(l2, "l");
        this.defaultSelectString = defaultSelectString;
        this.l = l2;
        this.defaultSelect = defaultSelectString;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return R.layout.layout_landscape_speed_definition;
    }

    @NotNull
    public final OnSpeedSelectListener getL() {
        return this.l;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() {
        super.onCreate();
        View viewFindViewById = findViewById(R.id.rvList);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(R.id.rvList)");
        this.rvList = (RecyclerView) viewFindViewById;
        ((TextView) findViewById(R.id.tv_title)).setText("倍速");
        ArrayList arrayListArrayListOf = CollectionsKt__CollectionsKt.arrayListOf("0.5X", "1X", "1.25X", "1.5X", "2X");
        RecyclerView recyclerView = this.rvList;
        if (recyclerView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("rvList");
            recyclerView = null;
        }
        recyclerView.setAdapter(new AnonymousClass1(arrayListArrayListOf, this));
    }

    public /* synthetic */ SelectSpeedFullScreenPopWindow(Context context, String str, OnSpeedSelectListener onSpeedSelectListener, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i2 & 2) != 0 ? "1X" : str, onSpeedSelectListener);
    }
}
