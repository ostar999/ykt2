package com.ykb.ebook.dialog;

import android.content.AppCtxKt;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.util.ColorResourcesKt;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.yikaobang.yixue.R2;
import com.ykb.ebook.R;
import com.ykb.ebook.adapter.CommonAdapter;
import com.ykb.ebook.common.ReadConfig;
import com.ykb.ebook.common_interface.AnimAction;
import com.ykb.ebook.dialog.BasicDialog;
import com.ykb.ebook.model.ConfigItem;
import java.util.ArrayList;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\u0018\u0000 \u00042\u00020\u0001:\u0002\u0003\u0004B\u0005¢\u0006\u0002\u0010\u0002¨\u0006\u0005"}, d2 = {"Lcom/ykb/ebook/dialog/ConfigDialog;", "", "()V", "Builder", "Companion", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes7.dex */
public final class ConfigDialog {
    public static final int TYPE_OFF_SCREEN = 2;
    public static final int TYPE_PAGE = 1;
    public static final int TYPE_REST_TIME = 3;

    @Metadata(d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u000e\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\b\u0010(\u001a\u00020\u0013H\u0002J\u0010\u0010)\u001a\u00020\u00132\u0006\u0010*\u001a\u00020\bH\u0016J \u0010+\u001a\u00020\u00002\u0018\u0010,\u001a\u0014\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\u00130\u0011R\u001d\u0010\u0007\u001a\u0004\u0018\u00010\b8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u000b\u0010\f\u001a\u0004\b\t\u0010\nR\u0014\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\"\u0010\u0010\u001a\u0016\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\u0013\u0018\u00010\u0011X\u0082\u000e¢\u0006\u0002\n\u0000R\u001d\u0010\u0014\u001a\u0004\u0018\u00010\u00158BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0018\u0010\f\u001a\u0004\b\u0016\u0010\u0017R\u001d\u0010\u0019\u001a\u0004\u0018\u00010\u001a8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u001d\u0010\f\u001a\u0004\b\u001b\u0010\u001cR\u001d\u0010\u001e\u001a\u0004\u0018\u00010\u001f8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\"\u0010\f\u001a\u0004\b \u0010!R\u001d\u0010#\u001a\u0004\u0018\u00010\u001f8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b%\u0010\f\u001a\u0004\b$\u0010!R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b&\u0010'¨\u0006-"}, d2 = {"Lcom/ykb/ebook/dialog/ConfigDialog$Builder;", "Lcom/ykb/ebook/dialog/BasicDialog$Builder;", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "type", "", "(Landroid/content/Context;I)V", "lineView", "Landroid/view/View;", "getLineView", "()Landroid/view/View;", "lineView$delegate", "Lkotlin/Lazy;", "mAdapter", "Lcom/ykb/ebook/adapter/CommonAdapter;", "Lcom/ykb/ebook/model/ConfigItem;", "onTimeClick", "Lkotlin/Function2;", "", "", "restTimeRll", "Landroid/widget/LinearLayout;", "getRestTimeRll", "()Landroid/widget/LinearLayout;", "restTimeRll$delegate", "rvList", "Landroidx/recyclerview/widget/RecyclerView;", "getRvList", "()Landroidx/recyclerview/widget/RecyclerView;", "rvList$delegate", "tvCancel", "Landroid/widget/TextView;", "getTvCancel", "()Landroid/widget/TextView;", "tvCancel$delegate", "tvTitle", "getTvTitle", "tvTitle$delegate", "getType", "()I", "initView", "onClick", "view", "setOnTimeClick", "click", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    @SourceDebugExtension({"SMAP\nConfigDialog.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ConfigDialog.kt\ncom/ykb/ebook/dialog/ConfigDialog$Builder\n+ 2 TextResources.kt\nsplitties/resources/TextResourcesKt\n+ 3 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n+ 4 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 5 ColorResources.kt\nsplitties/resources/ColorResourcesKt\n*L\n1#1,173:1\n175#2:174\n13644#3,3:175\n1855#4,2:178\n42#5:180\n42#5:181\n42#5:182\n42#5:183\n42#5:184\n42#5:185\n*S KotlinDebug\n*F\n+ 1 ConfigDialog.kt\ncom/ykb/ebook/dialog/ConfigDialog$Builder\n*L\n93#1:174\n121#1:175,3\n124#1:178,2\n138#1:180\n140#1:181\n147#1:182\n149#1:183\n156#1:184\n158#1:185\n*E\n"})
    public static final class Builder extends BasicDialog.Builder<Builder> {

        /* renamed from: lineView$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy lineView;

        @NotNull
        private CommonAdapter<ConfigItem> mAdapter;

        @Nullable
        private Function2<? super Integer, ? super String, Unit> onTimeClick;

        /* renamed from: restTimeRll$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy restTimeRll;

        /* renamed from: rvList$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy rvList;

        /* renamed from: tvCancel$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy tvCancel;

        /* renamed from: tvTitle$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy tvTitle;
        private final int type;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Builder(@NotNull Context context, int i2) throws Resources.NotFoundException {
            super(context);
            Intrinsics.checkNotNullParameter(context, "context");
            this.type = i2;
            this.tvCancel = LazyKt__LazyJVMKt.lazy(new Function0<TextView>() { // from class: com.ykb.ebook.dialog.ConfigDialog$Builder$tvCancel$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final TextView invoke() {
                    return (TextView) this.this$0.findViewById(R.id.btn_cancel);
                }
            });
            this.tvTitle = LazyKt__LazyJVMKt.lazy(new Function0<TextView>() { // from class: com.ykb.ebook.dialog.ConfigDialog$Builder$tvTitle$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final TextView invoke() {
                    return (TextView) this.this$0.findViewById(R.id.tv_title);
                }
            });
            this.rvList = LazyKt__LazyJVMKt.lazy(new Function0<RecyclerView>() { // from class: com.ykb.ebook.dialog.ConfigDialog$Builder$rvList$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final RecyclerView invoke() {
                    return (RecyclerView) this.this$0.findViewById(R.id.rvList);
                }
            });
            this.lineView = LazyKt__LazyJVMKt.lazy(new Function0<View>() { // from class: com.ykb.ebook.dialog.ConfigDialog$Builder$lineView$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final View invoke() {
                    return this.this$0.findViewById(R.id.lineView);
                }
            });
            this.restTimeRll = LazyKt__LazyJVMKt.lazy(new Function0<LinearLayout>() { // from class: com.ykb.ebook.dialog.ConfigDialog$Builder$restTimeRll$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final LinearLayout invoke() {
                    return (LinearLayout) this.this$0.findViewById(R.id.restTimeRll);
                }
            });
            this.mAdapter = new CommonAdapter<>(R.layout.item_rest_time, null, 2, 0 == true ? 1 : 0);
            setContentView(R.layout.dialog_rest_time_config);
            setGravity(80);
            setAnimStyle(AnimAction.INSTANCE.getANIM_BOTTOM());
            setCancelable(true);
            setCanceledOnTouchOutside(true);
            setOnClickListener(getTvCancel());
            initView();
        }

        private final View getLineView() {
            return (View) this.lineView.getValue();
        }

        private final LinearLayout getRestTimeRll() {
            return (LinearLayout) this.restTimeRll.getValue();
        }

        private final RecyclerView getRvList() {
            return (RecyclerView) this.rvList.getValue();
        }

        private final TextView getTvCancel() {
            return (TextView) this.tvCancel.getValue();
        }

        private final TextView getTvTitle() {
            return (TextView) this.tvTitle.getValue();
        }

        private final void initView() throws Resources.NotFoundException {
            TextView tvTitle = getTvTitle();
            if (tvTitle != null) {
                int i2 = this.type;
                tvTitle.setText(i2 != 1 ? i2 != 2 ? "休息时间提醒" : "屏幕关闭时间" : "翻页设置");
            }
            this.mAdapter.setConvert(new ConfigDialog$Builder$initView$1(this));
            RecyclerView rvList = getRvList();
            if (rvList != null) {
                rvList.setAdapter(this.mAdapter);
            }
            Context context = getContext();
            int i3 = this.type;
            String[] stringArray = context.getResources().getStringArray(i3 == 3 ? R.array.rest_time : i3 == 2 ? R.array.off_screen : R.array.page_type);
            Intrinsics.checkNotNullExpressionValue(stringArray, "resources.getStringArray(stringResId)");
            ArrayList arrayList = new ArrayList();
            int i4 = this.type;
            if (i4 == 1) {
                arrayList.add(2);
                arrayList.add(4);
                arrayList.add(1);
            } else if (i4 == 2) {
                arrayList.add(0);
                arrayList.add(300);
                arrayList.add(900);
                arrayList.add(Integer.valueOf(R2.attr.ic_knowledge_chart_data));
            } else if (i4 == 3) {
                arrayList.add(0);
                arrayList.add(60);
                arrayList.add(Integer.valueOf(R2.attr.onCross));
                arrayList.add(3600);
                arrayList.add(Integer.valueOf(R2.dimen.dp_584));
            }
            ArrayList<ConfigItem> arrayList2 = new ArrayList();
            int length = stringArray.length;
            int i5 = 0;
            int i6 = 0;
            while (i5 < length) {
                String str = stringArray[i5];
                int i7 = i6 + 1;
                Object obj = arrayList.get(i6);
                Intrinsics.checkNotNullExpressionValue(obj, "values[index]");
                arrayList2.add(new ConfigItem(((Number) obj).intValue(), str, false));
                i5++;
                i6 = i7;
            }
            for (ConfigItem configItem : arrayList2) {
                if (this.type == 3 && configItem.getValue() == ReadConfig.INSTANCE.getRestRemind()) {
                    configItem.setChecked(true);
                } else if (this.type == 2 && configItem.getValue() == ReadConfig.INSTANCE.getKeepLight()) {
                    configItem.setChecked(true);
                } else if (this.type == 1 && configItem.getValue() == ReadConfig.INSTANCE.getPageAnim()) {
                    configItem.setChecked(true);
                }
            }
            this.mAdapter.submitList(arrayList2);
            int colorMode = ReadConfig.INSTANCE.getColorMode();
            if (colorMode == 0) {
                LinearLayout restTimeRll = getRestTimeRll();
                if (restTimeRll != null) {
                    restTimeRll.setBackground(getDrawable(R.drawable.shape_white_top_16_bg));
                }
                TextView tvTitle2 = getTvTitle();
                Intrinsics.checkNotNull(tvTitle2);
                tvTitle2.setBackground(getDrawable(R.drawable.shape_white_top_16_bg));
                TextView tvTitle3 = getTvTitle();
                Intrinsics.checkNotNull(tvTitle3);
                int i8 = R.color.color_303030;
                tvTitle3.setTextColor(ColorResourcesKt.color(AppCtxKt.getAppCtx(), i8));
                TextView tvCancel = getTvCancel();
                Intrinsics.checkNotNull(tvCancel);
                tvCancel.setBackground(new ColorDrawable(ContextCompat.getColor(getContext(), R.color.white)));
                TextView tvCancel2 = getTvCancel();
                Intrinsics.checkNotNull(tvCancel2);
                tvCancel2.setTextColor(ColorResourcesKt.color(AppCtxKt.getAppCtx(), i8));
                View lineView = getLineView();
                Intrinsics.checkNotNull(lineView);
                lineView.setBackground(new ColorDrawable(ContextCompat.getColor(getContext(), R.color.color_eeeeee)));
                return;
            }
            if (colorMode == 1) {
                LinearLayout restTimeRll2 = getRestTimeRll();
                if (restTimeRll2 != null) {
                    restTimeRll2.setBackground(getDrawable(R.drawable.shape_yellow_top_16_bg));
                }
                TextView tvTitle4 = getTvTitle();
                Intrinsics.checkNotNull(tvTitle4);
                tvTitle4.setBackground(getDrawable(R.drawable.shape_yellow_top_16_bg));
                TextView tvTitle5 = getTvTitle();
                Intrinsics.checkNotNull(tvTitle5);
                int i9 = R.color.color_303030;
                tvTitle5.setTextColor(ColorResourcesKt.color(AppCtxKt.getAppCtx(), i9));
                TextView tvCancel3 = getTvCancel();
                Intrinsics.checkNotNull(tvCancel3);
                tvCancel3.setBackground(new ColorDrawable(ContextCompat.getColor(getContext(), R.color.color_F5EBCE)));
                TextView tvCancel4 = getTvCancel();
                Intrinsics.checkNotNull(tvCancel4);
                tvCancel4.setTextColor(ColorResourcesKt.color(AppCtxKt.getAppCtx(), i9));
                View lineView2 = getLineView();
                Intrinsics.checkNotNull(lineView2);
                lineView2.setBackground(new ColorDrawable(ContextCompat.getColor(getContext(), R.color.color_EDE2C3)));
                return;
            }
            if (colorMode != 2) {
                return;
            }
            LinearLayout restTimeRll3 = getRestTimeRll();
            if (restTimeRll3 != null) {
                restTimeRll3.setBackground(getDrawable(R.drawable.shape_blue_top_16_bg));
            }
            TextView tvTitle6 = getTvTitle();
            Intrinsics.checkNotNull(tvTitle6);
            tvTitle6.setBackground(getDrawable(R.drawable.shape_blue_top_16_bg));
            TextView tvTitle7 = getTvTitle();
            Intrinsics.checkNotNull(tvTitle7);
            int i10 = R.color.color_7380a9;
            tvTitle7.setTextColor(ColorResourcesKt.color(AppCtxKt.getAppCtx(), i10));
            TextView tvCancel5 = getTvCancel();
            Intrinsics.checkNotNull(tvCancel5);
            tvCancel5.setBackground(new ColorDrawable(ContextCompat.getColor(getContext(), R.color.color_121622)));
            TextView tvCancel6 = getTvCancel();
            Intrinsics.checkNotNull(tvCancel6);
            tvCancel6.setTextColor(ColorResourcesKt.color(AppCtxKt.getAppCtx(), i10));
            View lineView3 = getLineView();
            Intrinsics.checkNotNull(lineView3);
            lineView3.setBackground(new ColorDrawable(ContextCompat.getColor(getContext(), R.color.color_171C2D)));
        }

        public final int getType() {
            return this.type;
        }

        @Override // com.ykb.ebook.dialog.BasicDialog.Builder, com.ykb.ebook.common_interface.ClickAction, android.view.View.OnClickListener
        public void onClick(@NotNull View view) {
            Intrinsics.checkNotNullParameter(view, "view");
            dismiss();
        }

        @NotNull
        public final Builder setOnTimeClick(@NotNull Function2<? super Integer, ? super String, Unit> click) {
            Intrinsics.checkNotNullParameter(click, "click");
            this.onTimeClick = click;
            return this;
        }

        public /* synthetic */ Builder(Context context, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
            this(context, (i3 & 2) != 0 ? 1 : i2);
        }
    }
}
