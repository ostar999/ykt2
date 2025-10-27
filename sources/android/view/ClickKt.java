package android.view;

import android.view.View;
import com.meizu.cloud.pushsdk.notification.model.AdvanceSetting;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000\u001e\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\u001a\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004H\u0086\b\u001a*\u0010\u0005\u001a\u00020\u0001*\u00020\u00022\b\b\u0002\u0010\u0006\u001a\u00020\u00072\u000e\b\u0004\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00010\bH\u0086\bø\u0001\u0000\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\t"}, d2 = {"onClick", "", "Landroid/view/View;", "block", "Landroid/view/View$OnClickListener;", "onLongClick", "consume", "", "Lkotlin/Function0;", "splitties-views_release"}, k = 2, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes9.dex */
public final class ClickKt {

    @Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\u0010\u0004\u001a\u00020\u00032\u000e\u0010\u0002\u001a\n \u0001*\u0004\u0018\u00010\u00000\u0000H\n"}, d2 = {"Landroid/view/View;", "kotlin.jvm.PlatformType", AdvanceSetting.NETWORK_TYPE, "", "<anonymous>"}, k = 3, mv = {1, 5, 1})
    /* renamed from: splitties.views.ClickKt$onLongClick$1, reason: from Kotlin metadata */
    public static final class View implements View.OnLongClickListener {
        final /* synthetic */ Function0<Unit> $block;
        final /* synthetic */ boolean $consume;

        public View(Function0<Unit> function0, boolean z2) {
            this.$block = function0;
            this.$consume = z2;
        }

        @Override // android.view.View.OnLongClickListener
        public final boolean onLongClick(android.view.View view) {
            this.$block.invoke();
            return this.$consume;
        }
    }

    public static final void onClick(@NotNull android.view.View view, @NotNull View.OnClickListener block) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        Intrinsics.checkNotNullParameter(block, "block");
        view.setOnClickListener(block);
    }

    public static final void onLongClick(@NotNull android.view.View view, boolean z2, @NotNull Function0<Unit> block) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        Intrinsics.checkNotNullParameter(block, "block");
        view.setOnLongClickListener(new View(block, z2));
    }

    public static /* synthetic */ void onLongClick$default(android.view.View view, boolean z2, Function0 block, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            z2 = true;
        }
        Intrinsics.checkNotNullParameter(view, "<this>");
        Intrinsics.checkNotNullParameter(block, "block");
        view.setOnLongClickListener(new View(block, z2));
    }
}
