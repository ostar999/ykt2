package com.psychiatrygarden.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.lxj.xpopup.core.BottomPopupView;
import com.meizu.cloud.pushsdk.notification.model.NotifyType;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.weblong.SizeUtil;
import com.psychiatrygarden.widget.SelectSpeedPopWindow;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001:\u0001\u0016B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\b\u0010\u0012\u001a\u00020\u0013H\u0014J\b\u0010\u0014\u001a\u00020\u0015H\u0014R\u000e\u0010\t\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082.¢\u0006\u0002\n\u0000R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u000e\u0010\u000e\u001a\u00020\u000fX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082.¢\u0006\u0002\n\u0000¨\u0006\u0017"}, d2 = {"Lcom/psychiatrygarden/widget/SelectSpeedPopWindow;", "Lcom/lxj/xpopup/core/BottomPopupView;", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "defaultSelectString", "", NotifyType.LIGHTS, "Lcom/psychiatrygarden/widget/SelectSpeedPopWindow$OnSpeedSelectListener;", "(Landroid/content/Context;Ljava/lang/String;Lcom/psychiatrygarden/widget/SelectSpeedPopWindow$OnSpeedSelectListener;)V", "defaultSelect", "ivClose", "Landroid/widget/ImageView;", "getL", "()Lcom/psychiatrygarden/widget/SelectSpeedPopWindow$OnSpeedSelectListener;", "rvList", "Landroidx/recyclerview/widget/RecyclerView;", "tvTitle", "Landroid/widget/TextView;", "getImplLayoutId", "", "onCreate", "", "OnSpeedSelectListener", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class SelectSpeedPopWindow extends BottomPopupView {

    @NotNull
    private String defaultSelect;

    @NotNull
    private final String defaultSelectString;
    private ImageView ivClose;

    @NotNull
    private final OnSpeedSelectListener l;
    private RecyclerView rvList;
    private TextView tvTitle;

    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0000\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&¨\u0006\u0006"}, d2 = {"Lcom/psychiatrygarden/widget/SelectSpeedPopWindow$OnSpeedSelectListener;", "", "onSpeedSelect", "", "type", "", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public interface OnSpeedSelectListener {
        void onSpeedSelect(int type);
    }

    @Metadata(d1 = {"\u0000\u001b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003*\u0001\u0000\b\n\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001J\u0018\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\u0002H\u0014¨\u0006\b"}, d2 = {"com/psychiatrygarden/widget/SelectSpeedPopWindow$onCreate$2", "Lcom/chad/library/adapter/base/BaseQuickAdapter;", "", "Lcom/chad/library/adapter/base/viewholder/BaseViewHolder;", "convert", "", "holder", "item", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.psychiatrygarden.widget.SelectSpeedPopWindow$onCreate$2, reason: invalid class name */
    public static final class AnonymousClass2 extends BaseQuickAdapter<String, BaseViewHolder> {
        final /* synthetic */ SelectSpeedPopWindow this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(ArrayList<String> arrayList, final SelectSpeedPopWindow selectSpeedPopWindow) {
            super(R.layout.item_speed, null, 2, 0 == true ? 1 : 0);
            this.this$0 = selectSpeedPopWindow;
            setList(arrayList);
            setOnItemClickListener(new OnItemClickListener() { // from class: com.psychiatrygarden.widget.yg
                @Override // com.chad.library.adapter.base.listener.OnItemClickListener
                public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
                    SelectSpeedPopWindow.AnonymousClass2._init_$lambda$0(selectSpeedPopWindow, this, baseQuickAdapter, view, i2);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void _init_$lambda$0(SelectSpeedPopWindow this$0, AnonymousClass2 this$1, BaseQuickAdapter adapter, View view, int i2) {
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
            View view = holder.itemView;
            Intrinsics.checkNotNull(view, "null cannot be cast to non-null type android.widget.TextView");
            ((TextView) view).setText(item);
            GradientDrawable gradientDrawable = new GradientDrawable();
            gradientDrawable.setCornerRadius(SizeUtil.dp2px(getContext(), 8));
            if (TextUtils.equals(item, this.this$0.defaultSelect)) {
                if (SkinManager.getCurrentSkinType(getContext()) == 1) {
                    gradientDrawable.setColor(Color.parseColor("#422A33"));
                    View view2 = holder.itemView;
                    Intrinsics.checkNotNull(view2, "null cannot be cast to non-null type android.widget.TextView");
                    ((TextView) view2).setTextColor(getContext().getColor(R.color.color_B2575C));
                } else {
                    gradientDrawable.setColor(Color.parseColor("#FFF1F0"));
                    View view3 = holder.itemView;
                    Intrinsics.checkNotNull(view3, "null cannot be cast to non-null type android.widget.TextView");
                    ((TextView) view3).setTextColor(getContext().getColor(R.color.color_F95843));
                }
            } else if (SkinManager.getCurrentSkinType(getContext()) == 1) {
                gradientDrawable.setColor(getContext().getColor(R.color.color_0d111d));
                View view4 = holder.itemView;
                Intrinsics.checkNotNull(view4, "null cannot be cast to non-null type android.widget.TextView");
                ((TextView) view4).setTextColor(getContext().getColor(R.color.color_7380a9));
            } else {
                gradientDrawable.setColor(getContext().getColor(R.color.color_f9fafb));
                View view5 = holder.itemView;
                Intrinsics.checkNotNull(view5, "null cannot be cast to non-null type android.widget.TextView");
                ((TextView) view5).setTextColor(getContext().getColor(R.color.color_141516));
            }
            holder.itemView.setBackground(gradientDrawable);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SelectSpeedPopWindow(@NotNull Context context, @NotNull String defaultSelectString, @NotNull OnSpeedSelectListener l2) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(defaultSelectString, "defaultSelectString");
        Intrinsics.checkNotNullParameter(l2, "l");
        this.defaultSelectString = defaultSelectString;
        this.l = l2;
        this.defaultSelect = defaultSelectString;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$0(SelectSpeedPopWindow this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.dismiss();
    }

    @Override // com.lxj.xpopup.core.BottomPopupView, com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return R.layout.popwindow_video_speed;
    }

    @NotNull
    public final OnSpeedSelectListener getL() {
        return this.l;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() {
        super.onCreate();
        View viewFindViewById = findViewById(R.id.iv_close);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(R.id.iv_close)");
        this.ivClose = (ImageView) viewFindViewById;
        RecyclerView recyclerView = null;
        if (SkinManager.getCurrentSkinType(getContext()) == 1) {
            ImageView imageView = this.ivClose;
            if (imageView == null) {
                Intrinsics.throwUninitializedPropertyAccessException("ivClose");
                imageView = null;
            }
            imageView.setImageResource(R.drawable.close_pop_night);
        }
        ImageView imageView2 = this.ivClose;
        if (imageView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("ivClose");
            imageView2 = null;
        }
        imageView2.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.xg
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SelectSpeedPopWindow.onCreate$lambda$0(this.f17099c, view);
            }
        });
        View viewFindViewById2 = findViewById(R.id.tv_title);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "findViewById(R.id.tv_title)");
        this.tvTitle = (TextView) viewFindViewById2;
        View viewFindViewById3 = findViewById(R.id.rvList);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById3, "findViewById(R.id.rvList)");
        this.rvList = (RecyclerView) viewFindViewById3;
        ArrayList arrayListArrayListOf = CollectionsKt__CollectionsKt.arrayListOf("0.5X", "1X", "1.25X", "1.5X", "2X");
        RecyclerView recyclerView2 = this.rvList;
        if (recyclerView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("rvList");
        } else {
            recyclerView = recyclerView2;
        }
        recyclerView.setAdapter(new AnonymousClass2(arrayListArrayListOf, this));
    }

    public /* synthetic */ SelectSpeedPopWindow(Context context, String str, OnSpeedSelectListener onSpeedSelectListener, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i2 & 2) != 0 ? "1X" : str, onSpeedSelectListener);
    }
}
