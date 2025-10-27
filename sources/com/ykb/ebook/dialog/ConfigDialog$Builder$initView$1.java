package com.ykb.ebook.dialog;

import android.content.AppCtxKt;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.util.ColorResourcesKt;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import com.ykb.ebook.R;
import com.ykb.ebook.adapter.base.QuickViewHolder;
import com.ykb.ebook.common.ReadConfig;
import com.ykb.ebook.dialog.ConfigDialog;
import com.ykb.ebook.model.ConfigItem;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\u001a\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007H\n¢\u0006\u0002\b\b"}, d2 = {"<anonymous>", "", "holder", "Lcom/ykb/ebook/adapter/base/QuickViewHolder;", "<anonymous parameter 1>", "", "item", "Lcom/ykb/ebook/model/ConfigItem;", "invoke"}, k = 3, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nConfigDialog.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ConfigDialog.kt\ncom/ykb/ebook/dialog/ConfigDialog$Builder$initView$1\n+ 2 ColorResources.kt\nsplitties/resources/ColorResourcesKt\n*L\n1#1,173:1\n42#2:174\n42#2:175\n42#2:176\n*S KotlinDebug\n*F\n+ 1 ConfigDialog.kt\ncom/ykb/ebook/dialog/ConfigDialog$Builder$initView$1\n*L\n72#1:174\n78#1:175\n85#1:176\n*E\n"})
/* loaded from: classes7.dex */
public final class ConfigDialog$Builder$initView$1 extends Lambda implements Function3<QuickViewHolder, Integer, ConfigItem, Unit> {
    final /* synthetic */ ConfigDialog.Builder this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ConfigDialog$Builder$initView$1(ConfigDialog.Builder builder) {
        super(3);
        this.this$0 = builder;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void invoke$lambda$0(ConfigDialog.Builder this$0, ConfigItem configItem, View view) {
        String title;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Function2 function2 = this$0.onTimeClick;
        if (function2 != null) {
            Integer numValueOf = Integer.valueOf(configItem != null ? configItem.getValue() : 0);
            if (configItem == null || (title = configItem.getTitle()) == null) {
                title = "";
            }
            function2.invoke(numValueOf, title);
        }
        this$0.dismiss();
    }

    @Override // kotlin.jvm.functions.Function3
    public /* bridge */ /* synthetic */ Unit invoke(QuickViewHolder quickViewHolder, Integer num, ConfigItem configItem) {
        invoke(quickViewHolder, num.intValue(), configItem);
        return Unit.INSTANCE;
    }

    public final void invoke(@NotNull QuickViewHolder holder, int i2, @Nullable final ConfigItem configItem) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        TextView textView = (TextView) holder.getView(R.id.cb_item);
        View view = holder.getView(R.id.lineV);
        textView.setText(configItem != null ? configItem.getTitle() : null);
        ImageView imageView = (ImageView) holder.getView(R.id.iv);
        imageView.setVisibility(configItem != null && configItem.getChecked() ? 0 : 8);
        View view2 = holder.itemView;
        final ConfigDialog.Builder builder = this.this$0;
        view2.setOnClickListener(new View.OnClickListener() { // from class: com.ykb.ebook.dialog.e0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view3) {
                ConfigDialog$Builder$initView$1.invoke$lambda$0(builder, configItem, view3);
            }
        });
        int colorMode = ReadConfig.INSTANCE.getColorMode();
        if (colorMode == 0) {
            Intrinsics.checkNotNull(textView);
            textView.setTextColor(ColorResourcesKt.color(AppCtxKt.getAppCtx(), configItem != null && configItem.getChecked() ? R.color.color_dd594a : R.color.color_303030));
            Intrinsics.checkNotNull(view);
            view.setBackground(new ColorDrawable(ContextCompat.getColor(this.this$0.getContext(), R.color.color_eeeeee)));
            imageView.setImageDrawable(this.this$0.getDrawable(R.mipmap.ic_config_checked));
            return;
        }
        if (colorMode == 1) {
            Intrinsics.checkNotNull(textView);
            textView.setTextColor(ColorResourcesKt.color(AppCtxKt.getAppCtx(), configItem != null && configItem.getChecked() ? R.color.color_dd594a : R.color.color_303030));
            imageView.setColorFilter(ContextCompat.getColor(this.this$0.getContext(), R.color.color_dd594a), PorterDuff.Mode.SRC_IN);
            Intrinsics.checkNotNull(view);
            view.setBackground(new ColorDrawable(ContextCompat.getColor(this.this$0.getContext(), R.color.color_EDE2C3)));
            imageView.setImageDrawable(this.this$0.getDrawable(R.mipmap.ic_config_checked));
            return;
        }
        if (colorMode != 2) {
            return;
        }
        Intrinsics.checkNotNull(textView);
        textView.setTextColor(ColorResourcesKt.color(AppCtxKt.getAppCtx(), configItem != null && configItem.getChecked() ? R.color.color_B2575C : R.color.color_7380a9));
        imageView.setColorFilter(ContextCompat.getColor(this.this$0.getContext(), R.color.color_B2575C), PorterDuff.Mode.SRC_IN);
        Intrinsics.checkNotNull(view);
        view.setBackground(new ColorDrawable(ContextCompat.getColor(this.this$0.getContext(), R.color.color_171C2D)));
        imageView.setImageDrawable(this.this$0.getDrawable(R.mipmap.ic_config_checked_night));
    }
}
