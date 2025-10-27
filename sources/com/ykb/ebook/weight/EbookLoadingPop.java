package com.ykb.ebook.weight;

import android.annotation.SuppressLint;
import android.content.AppCtxKt;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.ColorResourcesKt;
import android.widget.ImageView;
import android.widget.TextView;
import com.lxj.xpopup.impl.FullScreenPopupView;
import com.ykb.ebook.R;
import com.ykb.ebook.common.ReadConfig;
import com.ykb.ebook.extensions.ViewExtensionsKt;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\b\u0010\u0016\u001a\u00020\u0017H\u0014J\b\u0010\u0018\u001a\u00020\u0019H\u0014J\b\u0010\u001a\u001a\u00020\u0019H\u0014J\b\u0010\u001b\u001a\u00020\u0019H\u0014R\u001b\u0010\u0007\u001a\u00020\b8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u000b\u0010\f\u001a\u0004\b\t\u0010\nR\u001b\u0010\r\u001a\u00020\u000e8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0011\u0010\f\u001a\u0004\b\u000f\u0010\u0010R\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015¨\u0006\u001c"}, d2 = {"Lcom/ykb/ebook/weight/EbookLoadingPop;", "Lcom/lxj/xpopup/impl/FullScreenPopupView;", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "title", "", "(Landroid/content/Context;Ljava/lang/String;)V", "ivLoading", "Landroid/widget/ImageView;", "getIvLoading", "()Landroid/widget/ImageView;", "ivLoading$delegate", "Lkotlin/Lazy;", "loadingTitle", "Landroid/widget/TextView;", "getLoadingTitle", "()Landroid/widget/TextView;", "loadingTitle$delegate", "getTitle", "()Ljava/lang/String;", "setTitle", "(Ljava/lang/String;)V", "getImplLayoutId", "", "onCreate", "", "onDismiss", "onShow", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SuppressLint({"ViewConstructor"})
@SourceDebugExtension({"SMAP\nEbookLoadingPop.kt\nKotlin\n*S Kotlin\n*F\n+ 1 EbookLoadingPop.kt\ncom/ykb/ebook/weight/EbookLoadingPop\n+ 2 ColorResources.kt\nsplitties/resources/ColorResourcesKt\n*L\n1#1,65:1\n42#2:66\n42#2:67\n*S KotlinDebug\n*F\n+ 1 EbookLoadingPop.kt\ncom/ykb/ebook/weight/EbookLoadingPop\n*L\n54#1:66\n55#1:67\n*E\n"})
/* loaded from: classes8.dex */
public final class EbookLoadingPop extends FullScreenPopupView {

    /* renamed from: ivLoading$delegate, reason: from kotlin metadata */
    @NotNull
    private final Lazy ivLoading;

    /* renamed from: loadingTitle$delegate, reason: from kotlin metadata */
    @NotNull
    private final Lazy loadingTitle;

    @NotNull
    private String title;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public EbookLoadingPop(@NotNull Context context, @NotNull String title) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(title, "title");
        this.title = title;
        this.ivLoading = LazyKt__LazyJVMKt.lazy(new Function0<ImageView>() { // from class: com.ykb.ebook.weight.EbookLoadingPop$ivLoading$2
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final ImageView invoke() {
                return (ImageView) this.this$0.findViewById(R.id.iv_loading);
            }
        });
        this.loadingTitle = LazyKt__LazyJVMKt.lazy(new Function0<TextView>() { // from class: com.ykb.ebook.weight.EbookLoadingPop$loadingTitle$2
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final TextView invoke() {
                return (TextView) this.this$0.findViewById(R.id.tv_loading_title);
            }
        });
    }

    private final ImageView getIvLoading() {
        Object value = this.ivLoading.getValue();
        Intrinsics.checkNotNullExpressionValue(value, "<get-ivLoading>(...)");
        return (ImageView) value;
    }

    private final TextView getLoadingTitle() {
        Object value = this.loadingTitle.getValue();
        Intrinsics.checkNotNullExpressionValue(value, "<get-loadingTitle>(...)");
        return (TextView) value;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return R.layout.ebook_loading_pop;
    }

    @NotNull
    public final String getTitle() {
        return this.title;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() {
        int iColor;
        super.onCreate();
        TextView loadingTitle = getLoadingTitle();
        int colorMode = ReadConfig.INSTANCE.getColorMode();
        if (colorMode == 0 || colorMode == 1) {
            iColor = ColorResourcesKt.color(AppCtxKt.getAppCtx(), R.color.color_C2C6CB);
        } else {
            iColor = ColorResourcesKt.color(AppCtxKt.getAppCtx(), R.color.color_575F79);
        }
        loadingTitle.setTextColor(iColor);
        getLoadingTitle().setText(this.title);
        if (TextUtils.isEmpty(this.title)) {
            ViewExtensionsKt.gone(getLoadingTitle());
        }
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onDismiss() {
        Drawable background = getIvLoading().getBackground();
        Intrinsics.checkNotNull(background, "null cannot be cast to non-null type android.graphics.drawable.AnimationDrawable");
        ((AnimationDrawable) background).stop();
        super.onDismiss();
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onShow() {
        Drawable background = getIvLoading().getBackground();
        Intrinsics.checkNotNull(background, "null cannot be cast to non-null type android.graphics.drawable.AnimationDrawable");
        AnimationDrawable animationDrawable = (AnimationDrawable) background;
        animationDrawable.setOneShot(false);
        animationDrawable.start();
        super.onShow();
    }

    public final void setTitle(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.title = str;
    }

    public /* synthetic */ EbookLoadingPop(Context context, String str, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i2 & 2) != 0 ? "努力加载中" : str);
    }
}
