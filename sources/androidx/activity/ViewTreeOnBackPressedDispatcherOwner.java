package androidx.activity;

import android.view.View;
import kotlin.Metadata;
import kotlin.jvm.JvmName;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt__SequencesKt;
import kotlin.sequences.SequencesKt___SequencesKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\u0016\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\u001a\u0013\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u0002H\u0007¢\u0006\u0002\b\u0003\u001a\u0019\u0010\u0004\u001a\u00020\u0005*\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u0001H\u0007¢\u0006\u0002\b\u0007¨\u0006\b"}, d2 = {"findViewTreeOnBackPressedDispatcherOwner", "Landroidx/activity/OnBackPressedDispatcherOwner;", "Landroid/view/View;", "get", "setViewTreeOnBackPressedDispatcherOwner", "", "onBackPressedDispatcherOwner", "set", "activity_release"}, k = 2, mv = {1, 7, 1}, xi = 48)
@JvmName(name = "ViewTreeOnBackPressedDispatcherOwner")
/* loaded from: classes.dex */
public final class ViewTreeOnBackPressedDispatcherOwner {
    @JvmName(name = "get")
    @Nullable
    public static final OnBackPressedDispatcherOwner get(@NotNull View view) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        return (OnBackPressedDispatcherOwner) SequencesKt___SequencesKt.firstOrNull(SequencesKt___SequencesKt.mapNotNull(SequencesKt__SequencesKt.generateSequence(view, (Function1<? super View, ? extends View>) ((Function1<? super Object, ? extends Object>) new Function1<View, View>() { // from class: androidx.activity.ViewTreeOnBackPressedDispatcherOwner$findViewTreeOnBackPressedDispatcherOwner$1
            @Override // kotlin.jvm.functions.Function1
            @Nullable
            public final View invoke(@NotNull View it) {
                Intrinsics.checkNotNullParameter(it, "it");
                Object parent = it.getParent();
                if (parent instanceof View) {
                    return (View) parent;
                }
                return null;
            }
        })), new Function1<View, OnBackPressedDispatcherOwner>() { // from class: androidx.activity.ViewTreeOnBackPressedDispatcherOwner$findViewTreeOnBackPressedDispatcherOwner$2
            @Override // kotlin.jvm.functions.Function1
            @Nullable
            public final OnBackPressedDispatcherOwner invoke(@NotNull View it) {
                Intrinsics.checkNotNullParameter(it, "it");
                Object tag = it.getTag(R.id.view_tree_on_back_pressed_dispatcher_owner);
                if (tag instanceof OnBackPressedDispatcherOwner) {
                    return (OnBackPressedDispatcherOwner) tag;
                }
                return null;
            }
        }));
    }

    @JvmName(name = "set")
    public static final void set(@NotNull View view, @NotNull OnBackPressedDispatcherOwner onBackPressedDispatcherOwner) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        Intrinsics.checkNotNullParameter(onBackPressedDispatcherOwner, "onBackPressedDispatcherOwner");
        view.setTag(R.id.view_tree_on_back_pressed_dispatcher_owner, onBackPressedDispatcherOwner);
    }
}
