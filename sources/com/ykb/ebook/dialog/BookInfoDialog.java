package com.ykb.ebook.dialog;

import android.content.AppCtxKt;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.ColorResourcesKt;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.ruffian.library.widget.RTextView;
import com.yikaobang.yixue.R2;
import com.ykb.ebook.R;
import com.ykb.ebook.common.ReadConfig;
import com.ykb.ebook.common_interface.AnimAction;
import com.ykb.ebook.dialog.BasicDialog;
import com.ykb.ebook.dialog.BookInfoDialog;
import com.ykb.ebook.util.ScreenUtil;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\u0018\u00002\u00020\u0001:\u0001\u0003B\u0005¢\u0006\u0002\u0010\u0002¨\u0006\u0004"}, d2 = {"Lcom/ykb/ebook/dialog/BookInfoDialog;", "", "()V", "Builder", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes7.dex */
public final class BookInfoDialog {

    @Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B#\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007¢\u0006\u0002\u0010\tR\u001d\u0010\n\u001a\u0004\u0018\u00010\u000b8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u000e\u0010\u000f\u001a\u0004\b\f\u0010\rR\u001d\u0010\u0010\u001a\u0004\u0018\u00010\u00118BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0014\u0010\u000f\u001a\u0004\b\u0012\u0010\u0013R\u001d\u0010\u0015\u001a\u0004\u0018\u00010\u00168BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0019\u0010\u000f\u001a\u0004\b\u0017\u0010\u0018R\u001d\u0010\u001a\u001a\u0004\u0018\u00010\u00168BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u001c\u0010\u000f\u001a\u0004\b\u001b\u0010\u0018¨\u0006\u001d"}, d2 = {"Lcom/ykb/ebook/dialog/BookInfoDialog$Builder;", "Lcom/ykb/ebook/dialog/BasicDialog$Builder;", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "content", "", "onToReadClick", "Lkotlin/Function0;", "", "(Landroid/content/Context;Ljava/lang/String;Lkotlin/jvm/functions/Function0;)V", "btnToRead", "Lcom/ruffian/library/widget/RTextView;", "getBtnToRead", "()Lcom/ruffian/library/widget/RTextView;", "btnToRead$delegate", "Lkotlin/Lazy;", "imgClose", "Landroid/widget/ImageView;", "getImgClose", "()Landroid/widget/ImageView;", "imgClose$delegate", "tvContent", "Landroid/widget/TextView;", "getTvContent", "()Landroid/widget/TextView;", "tvContent$delegate", "tvTitle", "getTvTitle", "tvTitle$delegate", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    @SourceDebugExtension({"SMAP\nBookInfoDialog.kt\nKotlin\n*S Kotlin\n*F\n+ 1 BookInfoDialog.kt\ncom/ykb/ebook/dialog/BookInfoDialog$Builder\n+ 2 ColorResources.kt\nsplitties/resources/ColorResourcesKt\n*L\n1#1,62:1\n42#2:63\n42#2:64\n42#2:65\n42#2:66\n*S KotlinDebug\n*F\n+ 1 BookInfoDialog.kt\ncom/ykb/ebook/dialog/BookInfoDialog$Builder\n*L\n44#1:63\n45#1:64\n46#1:65\n52#1:66\n*E\n"})
    public static final class Builder extends BasicDialog.Builder<Builder> {

        /* renamed from: btnToRead$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy btnToRead;

        /* renamed from: imgClose$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy imgClose;

        /* renamed from: tvContent$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy tvContent;

        /* renamed from: tvTitle$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy tvTitle;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Builder(@NotNull Context context, @NotNull String content, @NotNull final Function0<Unit> onToReadClick) {
            super(context);
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(content, "content");
            Intrinsics.checkNotNullParameter(onToReadClick, "onToReadClick");
            this.imgClose = LazyKt__LazyJVMKt.lazy(new Function0<ImageView>() { // from class: com.ykb.ebook.dialog.BookInfoDialog$Builder$imgClose$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final ImageView invoke() {
                    return (ImageView) this.this$0.findViewById(R.id.img_close);
                }
            });
            this.tvContent = LazyKt__LazyJVMKt.lazy(new Function0<TextView>() { // from class: com.ykb.ebook.dialog.BookInfoDialog$Builder$tvContent$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final TextView invoke() {
                    return (TextView) this.this$0.findViewById(R.id.tv_content);
                }
            });
            this.tvTitle = LazyKt__LazyJVMKt.lazy(new Function0<TextView>() { // from class: com.ykb.ebook.dialog.BookInfoDialog$Builder$tvTitle$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final TextView invoke() {
                    return (TextView) this.this$0.findViewById(R.id.title);
                }
            });
            this.btnToRead = LazyKt__LazyJVMKt.lazy(new Function0<RTextView>() { // from class: com.ykb.ebook.dialog.BookInfoDialog$Builder$btnToRead$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final RTextView invoke() {
                    return (RTextView) this.this$0.findViewById(R.id.btn_to_read);
                }
            });
            setContentView(R.layout.dialog_book_info);
            setHeight((ScreenUtil.getScreenHeight(getActivity()) * R2.attr.bl_unPressed_drawable) / R2.attr.border_width_selected);
            setGravity(80);
            setAnimStyle(AnimAction.INSTANCE.getANIM_BOTTOM());
            setCancelable(true);
            setCanceledOnTouchOutside(true);
            Drawable drawable = AppCtxKt.getAppCtx().getDrawable(R.drawable.shape_white_top_16_bg);
            Intrinsics.checkNotNull(drawable, "null cannot be cast to non-null type android.graphics.drawable.GradientDrawable");
            GradientDrawable gradientDrawable = (GradientDrawable) drawable;
            ImageView imgClose = getImgClose();
            if (imgClose != null) {
                imgClose.setOnClickListener(new View.OnClickListener() { // from class: com.ykb.ebook.dialog.r
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        BookInfoDialog.Builder._init_$lambda$0(this.f26382c, view);
                    }
                });
            }
            ReadConfig readConfig = ReadConfig.INSTANCE;
            if (readConfig.getColorMode() == 2) {
                gradientDrawable.setColor(Color.rgb(18, 22, 34));
                View contentView = getContentView();
                if (contentView != null) {
                    contentView.setBackground(gradientDrawable);
                }
                View viewFindViewById = findViewById(R.id.line);
                if (viewFindViewById != null) {
                    viewFindViewById.setBackground(new ColorDrawable(ColorResourcesKt.color(AppCtxKt.getAppCtx(), R.color.color_171C2D)));
                }
                TextView tvTitle = getTvTitle();
                if (tvTitle != null) {
                    tvTitle.setTextColor(ColorResourcesKt.color(AppCtxKt.getAppCtx(), R.color.color_7380a9));
                }
                TextView tvContent = getTvContent();
                if (tvContent != null) {
                    tvContent.setTextColor(ColorResourcesKt.color(AppCtxKt.getAppCtx(), R.color.color_7380a9));
                }
                ImageView imgClose2 = getImgClose();
                if (imgClose2 != null) {
                    imgClose2.setImageDrawable(AppCtxKt.getAppCtx().getDrawable(R.drawable.icon_close_night_svg));
                }
            } else if (readConfig.getColorMode() == 1) {
                ImageView imgClose3 = getImgClose();
                if (imgClose3 != null) {
                    imgClose3.setImageDrawable(AppCtxKt.getAppCtx().getDrawable(R.drawable.icon_close_yellow_theme_svg));
                }
                gradientDrawable.setColor(Color.rgb(R2.attr.actionModeCloseDrawable, 235, 206));
                View contentView2 = getContentView();
                if (contentView2 != null) {
                    contentView2.setBackground(gradientDrawable);
                }
                RTextView btnToRead = getBtnToRead();
                if (btnToRead != null) {
                    btnToRead.setTextColor(ColorResourcesKt.color(AppCtxKt.getAppCtx(), R.color.color_F5EBCE));
                }
            }
            TextView tvContent2 = getTvContent();
            if (tvContent2 != null) {
                tvContent2.setText(content);
            }
            RTextView btnToRead2 = getBtnToRead();
            if (btnToRead2 != null) {
                btnToRead2.setOnClickListener(new View.OnClickListener() { // from class: com.ykb.ebook.dialog.s
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        BookInfoDialog.Builder._init_$lambda$1(this.f26385c, onToReadClick, view);
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void _init_$lambda$0(Builder this$0, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            this$0.dismiss();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void _init_$lambda$1(Builder this$0, Function0 onToReadClick, View view) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            Intrinsics.checkNotNullParameter(onToReadClick, "$onToReadClick");
            this$0.dismiss();
            onToReadClick.invoke();
        }

        private final RTextView getBtnToRead() {
            return (RTextView) this.btnToRead.getValue();
        }

        private final ImageView getImgClose() {
            return (ImageView) this.imgClose.getValue();
        }

        private final TextView getTvContent() {
            return (TextView) this.tvContent.getValue();
        }

        private final TextView getTvTitle() {
            return (TextView) this.tvTitle.getValue();
        }
    }
}
