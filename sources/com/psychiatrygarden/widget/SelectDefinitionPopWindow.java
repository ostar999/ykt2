package com.psychiatrygarden.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.aliyun.player.alivcplayerexpand.listener.QualityValue;
import com.aliyun.player.nativeclass.TrackInfo;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.lxj.xpopup.core.BottomPopupView;
import com.meizu.cloud.pushsdk.notification.model.NotifyType;
import com.psychiatrygarden.aliPlayer.utils.VideoDefinition;
import com.psychiatrygarden.utils.Constants;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.weblong.SizeUtil;
import com.psychiatrygarden.widget.SelectDefinitionPopWindow;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001:\u0002\u0019\u001aB/\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\b\u0012\u0006\u0010\t\u001a\u00020\n¢\u0006\u0002\u0010\u000bJ\b\u0010\u0015\u001a\u00020\u0016H\u0014J\b\u0010\u0017\u001a\u00020\u0018H\u0014R\u0010\u0010\f\u001a\u0004\u0018\u00010\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082.¢\u0006\u0002\n\u0000R\u0011\u0010\t\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082.¢\u0006\u0002\n\u0000¨\u0006\u001b"}, d2 = {"Lcom/psychiatrygarden/widget/SelectDefinitionPopWindow;", "Lcom/lxj/xpopup/core/BottomPopupView;", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "list", "", "Lcom/aliyun/player/nativeclass/TrackInfo;", "defaultSelectString", "", NotifyType.LIGHTS, "Lcom/psychiatrygarden/widget/SelectDefinitionPopWindow$OnDefinitionSelectListener;", "(Landroid/content/Context;Ljava/util/List;Ljava/lang/String;Lcom/psychiatrygarden/widget/SelectDefinitionPopWindow$OnDefinitionSelectListener;)V", "defaultSelect", "ivClose", "Landroid/widget/ImageView;", "getL", "()Lcom/psychiatrygarden/widget/SelectDefinitionPopWindow$OnDefinitionSelectListener;", "rvList", "Landroidx/recyclerview/widget/RecyclerView;", "tvTitle", "Landroid/widget/TextView;", "getImplLayoutId", "", "onCreate", "", "DefinitionValue", "OnDefinitionSelectListener", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nSelectDefinitionPopWindow.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SelectDefinitionPopWindow.kt\ncom/psychiatrygarden/widget/SelectDefinitionPopWindow\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,120:1\n1855#2,2:121\n*S KotlinDebug\n*F\n+ 1 SelectDefinitionPopWindow.kt\ncom/psychiatrygarden/widget/SelectDefinitionPopWindow\n*L\n43#1:121,2\n*E\n"})
/* loaded from: classes6.dex */
public final class SelectDefinitionPopWindow extends BottomPopupView {

    @Nullable
    private String defaultSelect;

    @Nullable
    private final String defaultSelectString;
    private ImageView ivClose;

    @NotNull
    private final OnDefinitionSelectListener l;

    @NotNull
    private final List<TrackInfo> list;
    private RecyclerView rvList;
    private TextView tvTitle;

    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005J\t\u0010\t\u001a\u00020\u0003HÆ\u0003J\t\u0010\n\u001a\u00020\u0003HÆ\u0003J\u001d\u0010\u000b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u000f\u001a\u00020\u0010HÖ\u0001J\t\u0010\u0011\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007¨\u0006\u0012"}, d2 = {"Lcom/psychiatrygarden/widget/SelectDefinitionPopWindow$DefinitionValue;", "", Constants.PARAMS_CONSTANTS.PARAMS_TOPIC_LABEL, "", "value", "(Ljava/lang/String;Ljava/lang/String;)V", "getLabel", "()Ljava/lang/String;", "getValue", "component1", "component2", "copy", "equals", "", "other", "hashCode", "", "toString", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final /* data */ class DefinitionValue {

        @NotNull
        private final String label;

        @NotNull
        private final String value;

        public DefinitionValue(@NotNull String label, @NotNull String value) {
            Intrinsics.checkNotNullParameter(label, "label");
            Intrinsics.checkNotNullParameter(value, "value");
            this.label = label;
            this.value = value;
        }

        public static /* synthetic */ DefinitionValue copy$default(DefinitionValue definitionValue, String str, String str2, int i2, Object obj) {
            if ((i2 & 1) != 0) {
                str = definitionValue.label;
            }
            if ((i2 & 2) != 0) {
                str2 = definitionValue.value;
            }
            return definitionValue.copy(str, str2);
        }

        @NotNull
        /* renamed from: component1, reason: from getter */
        public final String getLabel() {
            return this.label;
        }

        @NotNull
        /* renamed from: component2, reason: from getter */
        public final String getValue() {
            return this.value;
        }

        @NotNull
        public final DefinitionValue copy(@NotNull String label, @NotNull String value) {
            Intrinsics.checkNotNullParameter(label, "label");
            Intrinsics.checkNotNullParameter(value, "value");
            return new DefinitionValue(label, value);
        }

        public boolean equals(@Nullable Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof DefinitionValue)) {
                return false;
            }
            DefinitionValue definitionValue = (DefinitionValue) other;
            return Intrinsics.areEqual(this.label, definitionValue.label) && Intrinsics.areEqual(this.value, definitionValue.value);
        }

        @NotNull
        public final String getLabel() {
            return this.label;
        }

        @NotNull
        public final String getValue() {
            return this.value;
        }

        public int hashCode() {
            return (this.label.hashCode() * 31) + this.value.hashCode();
        }

        @NotNull
        public String toString() {
            return "DefinitionValue(label=" + this.label + ", value=" + this.value + ')';
        }
    }

    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&¨\u0006\u0006"}, d2 = {"Lcom/psychiatrygarden/widget/SelectDefinitionPopWindow$OnDefinitionSelectListener;", "", "onDefinitionSelect", "", "definition", "", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public interface OnDefinitionSelectListener {
        void onDefinitionSelect(@NotNull String definition);
    }

    @Metadata(d1 = {"\u0000\u001b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003*\u0001\u0000\b\n\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001J\u0018\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\u0002H\u0014¨\u0006\b"}, d2 = {"com/psychiatrygarden/widget/SelectDefinitionPopWindow$onCreate$3", "Lcom/chad/library/adapter/base/BaseQuickAdapter;", "Lcom/psychiatrygarden/widget/SelectDefinitionPopWindow$DefinitionValue;", "Lcom/chad/library/adapter/base/viewholder/BaseViewHolder;", "convert", "", "holder", "item", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
    /* renamed from: com.psychiatrygarden.widget.SelectDefinitionPopWindow$onCreate$3, reason: invalid class name */
    public static final class AnonymousClass3 extends BaseQuickAdapter<DefinitionValue, BaseViewHolder> {
        final /* synthetic */ SelectDefinitionPopWindow this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass3(ArrayList<DefinitionValue> arrayList, final SelectDefinitionPopWindow selectDefinitionPopWindow) {
            super(R.layout.item_speed, null, 2, 0 == true ? 1 : 0);
            this.this$0 = selectDefinitionPopWindow;
            setList(arrayList);
            setOnItemClickListener(new OnItemClickListener() { // from class: com.psychiatrygarden.widget.ug
                @Override // com.chad.library.adapter.base.listener.OnItemClickListener
                public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i2) {
                    SelectDefinitionPopWindow.AnonymousClass3._init_$lambda$0(selectDefinitionPopWindow, this, baseQuickAdapter, view, i2);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void _init_$lambda$0(SelectDefinitionPopWindow this$0, AnonymousClass3 this$1, BaseQuickAdapter adapter, View view, int i2) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            Intrinsics.checkNotNullParameter(this$1, "this$1");
            Intrinsics.checkNotNullParameter(adapter, "adapter");
            Intrinsics.checkNotNullParameter(view, "<anonymous parameter 1>");
            Object item = adapter.getItem(i2);
            Intrinsics.checkNotNull(item, "null cannot be cast to non-null type com.psychiatrygarden.widget.SelectDefinitionPopWindow.DefinitionValue");
            this$0.defaultSelect = ((DefinitionValue) item).getValue();
            OnDefinitionSelectListener l2 = this$0.getL();
            String str = this$0.defaultSelect;
            Intrinsics.checkNotNull(str);
            l2.onDefinitionSelect(str);
            this$1.notifyDataSetChanged();
            this$0.dismiss();
        }

        @Override // com.chad.library.adapter.base.BaseQuickAdapter
        public void convert(@NotNull BaseViewHolder holder, @NotNull DefinitionValue item) {
            Intrinsics.checkNotNullParameter(holder, "holder");
            Intrinsics.checkNotNullParameter(item, "item");
            View view = holder.itemView;
            Intrinsics.checkNotNull(view, "null cannot be cast to non-null type android.widget.TextView");
            ((TextView) view).setText(item.getLabel());
            GradientDrawable gradientDrawable = new GradientDrawable();
            gradientDrawable.setCornerRadius(SizeUtil.dp2px(getContext(), 8));
            if (TextUtils.equals(item.getValue(), this.this$0.defaultSelect)) {
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

    public /* synthetic */ SelectDefinitionPopWindow(Context context, List list, String str, OnDefinitionSelectListener onDefinitionSelectListener, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, list, (i2 & 4) != 0 ? QualityValue.QUALITY_FLUENT : str, onDefinitionSelectListener);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$0(SelectDefinitionPopWindow this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.dismiss();
    }

    @Override // com.lxj.xpopup.core.BottomPopupView, com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return R.layout.popwindow_video_definition;
    }

    @NotNull
    public final OnDefinitionSelectListener getL() {
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
        imageView2.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.tg
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SelectDefinitionPopWindow.onCreate$lambda$0(this.f16936c, view);
            }
        });
        View viewFindViewById2 = findViewById(R.id.tv_title);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "findViewById(R.id.tv_title)");
        this.tvTitle = (TextView) viewFindViewById2;
        View viewFindViewById3 = findViewById(R.id.rvList);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById3, "findViewById(R.id.rvList)");
        this.rvList = (RecyclerView) viewFindViewById3;
        ArrayList arrayList = new ArrayList();
        for (TrackInfo trackInfo : this.list) {
            String str = trackInfo.vodDefinition;
            VideoDefinition videoDefinition = VideoDefinition.FD;
            if (Intrinsics.areEqual(str, videoDefinition.getDefinition())) {
                String label = videoDefinition.getLabel();
                Intrinsics.checkNotNullExpressionValue(label, "FD.label");
                String str2 = trackInfo.vodDefinition;
                Intrinsics.checkNotNullExpressionValue(str2, "it.vodDefinition");
                arrayList.add(new DefinitionValue(label, str2));
            } else {
                VideoDefinition videoDefinition2 = VideoDefinition.LD;
                if (Intrinsics.areEqual(str, videoDefinition2.getDefinition())) {
                    String label2 = videoDefinition2.getLabel();
                    Intrinsics.checkNotNullExpressionValue(label2, "LD.label");
                    String str3 = trackInfo.vodDefinition;
                    Intrinsics.checkNotNullExpressionValue(str3, "it.vodDefinition");
                    arrayList.add(new DefinitionValue(label2, str3));
                } else {
                    VideoDefinition videoDefinition3 = VideoDefinition.SD;
                    if (Intrinsics.areEqual(str, videoDefinition3.getDefinition())) {
                        String label3 = videoDefinition3.getLabel();
                        Intrinsics.checkNotNullExpressionValue(label3, "SD.label");
                        String str4 = trackInfo.vodDefinition;
                        Intrinsics.checkNotNullExpressionValue(str4, "it.vodDefinition");
                        arrayList.add(new DefinitionValue(label3, str4));
                    } else {
                        VideoDefinition videoDefinition4 = VideoDefinition.HD;
                        if (Intrinsics.areEqual(str, videoDefinition4.getDefinition())) {
                            String label4 = videoDefinition4.getLabel();
                            Intrinsics.checkNotNullExpressionValue(label4, "HD.label");
                            String str5 = trackInfo.vodDefinition;
                            Intrinsics.checkNotNullExpressionValue(str5, "it.vodDefinition");
                            arrayList.add(new DefinitionValue(label4, str5));
                        } else {
                            VideoDefinition videoDefinition5 = VideoDefinition.TK;
                            if (Intrinsics.areEqual(str, videoDefinition5.getDefinition())) {
                                String label5 = videoDefinition5.getLabel();
                                Intrinsics.checkNotNullExpressionValue(label5, "TK.label");
                                String str6 = trackInfo.vodDefinition;
                                Intrinsics.checkNotNullExpressionValue(str6, "it.vodDefinition");
                                arrayList.add(new DefinitionValue(label5, str6));
                            } else {
                                VideoDefinition videoDefinition6 = VideoDefinition.FK;
                                if (Intrinsics.areEqual(str, videoDefinition6.getDefinition())) {
                                    String label6 = videoDefinition6.getLabel();
                                    Intrinsics.checkNotNullExpressionValue(label6, "FK.label");
                                    String str7 = trackInfo.vodDefinition;
                                    Intrinsics.checkNotNullExpressionValue(str7, "it.vodDefinition");
                                    arrayList.add(new DefinitionValue(label6, str7));
                                } else {
                                    VideoDefinition videoDefinition7 = VideoDefinition.OD;
                                    if (Intrinsics.areEqual(str, videoDefinition7.getDefinition())) {
                                        String label7 = videoDefinition7.getLabel();
                                        Intrinsics.checkNotNullExpressionValue(label7, "OD.label");
                                        String str8 = trackInfo.vodDefinition;
                                        Intrinsics.checkNotNullExpressionValue(str8, "it.vodDefinition");
                                        arrayList.add(new DefinitionValue(label7, str8));
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        RecyclerView recyclerView2 = this.rvList;
        if (recyclerView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("rvList");
        } else {
            recyclerView = recyclerView2;
        }
        recyclerView.setAdapter(new AnonymousClass3(arrayList, this));
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public SelectDefinitionPopWindow(@NotNull Context context, @NotNull List<? extends TrackInfo> list, @Nullable String str, @NotNull OnDefinitionSelectListener l2) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(list, "list");
        Intrinsics.checkNotNullParameter(l2, "l");
        this.list = list;
        this.defaultSelectString = str;
        this.l = l2;
        this.defaultSelect = str;
    }
}
