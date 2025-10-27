package com.ykb.ebook.weight;

import android.view.View;
import android.view.animation.Animation;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.ykb.ebook.databinding.ViewReadMenuBinding;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000\u0019\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0016J\u0010\u0010\u0006\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0016J\u0010\u0010\u0007\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0016¨\u0006\b"}, d2 = {"com/ykb/ebook/weight/ReadMenu$menuInListener$1", "Landroid/view/animation/Animation$AnimationListener;", "onAnimationEnd", "", "animation", "Landroid/view/animation/Animation;", "onAnimationRepeat", "onAnimationStart", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nReadMenu.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ReadMenu.kt\ncom/ykb/ebook/weight/ReadMenu$menuInListener$1\n+ 2 Padding.kt\nsplitties/views/PaddingKt\n*L\n1#1,1070:1\n15#2:1071\n*S KotlinDebug\n*F\n+ 1 ReadMenu.kt\ncom/ykb/ebook/weight/ReadMenu$menuInListener$1\n*L\n129#1:1071\n*E\n"})
/* loaded from: classes8.dex */
public final class ReadMenu$menuInListener$1 implements Animation.AnimationListener {
    final /* synthetic */ ReadMenu this$0;

    public ReadMenu$menuInListener$1(ReadMenu readMenu) {
        this.this$0 = readMenu;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onAnimationEnd$lambda$1$lambda$0(ReadMenu this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        ReadMenu.runMenuOut$default(this$0, null, 1, null);
    }

    @Override // android.view.animation.Animation.AnimationListener
    public void onAnimationEnd(@NotNull Animation animation) {
        Intrinsics.checkNotNullParameter(animation, "animation");
        ViewReadMenuBinding viewReadMenuBinding = this.this$0.binding;
        final ReadMenu readMenu = this.this$0;
        viewReadMenuBinding.vwMenuBg.setOnClickListener(new View.OnClickListener() { // from class: com.ykb.ebook.weight.q0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ReadMenu$menuInListener$1.onAnimationEnd$lambda$1$lambda$0(readMenu, view);
            }
        });
        ConstraintLayout root = viewReadMenuBinding.getRoot();
        Intrinsics.checkNotNullExpressionValue(root, "root");
        root.setPadding(0, 0, 0, 0);
        this.this$0.getCallback().upSystemUiVisibility();
    }

    @Override // android.view.animation.Animation.AnimationListener
    public void onAnimationRepeat(@NotNull Animation animation) {
        Intrinsics.checkNotNullParameter(animation, "animation");
    }

    @Override // android.view.animation.Animation.AnimationListener
    public void onAnimationStart(@NotNull Animation animation) {
        Intrinsics.checkNotNullParameter(animation, "animation");
        this.this$0.getCallback().upSystemUiVisibility();
    }
}
