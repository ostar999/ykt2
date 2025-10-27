package android.view;

import android.widget.EditText;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import splitties.experimental.ExperimentalSplittiesApi;

@Metadata(d1 = {"\u0000\u0010\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\";\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0001*\u00020\u00032\n\u0010\u0000\u001a\u0006\u0012\u0002\b\u00030\u00018Ç\u0002@Æ\u0002X\u0087\u000eø\u0001\u0000¢\u0006\u0012\u0012\u0004\b\u0004\u0010\u0005\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\t\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\n"}, d2 = {"newType", "Lsplitties/views/InputType;", "type", "Landroid/widget/EditText;", "getType$annotations", "(Landroid/widget/EditText;)V", "getType", "(Landroid/widget/EditText;)I", "setType-sVLYzLI", "(Landroid/widget/EditText;I)V", "splitties-views_release"}, k = 2, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes9.dex */
public final class EditTextKt {
    @Deprecated(level = DeprecationLevel.HIDDEN, message = NoGetterKt.NO_GETTER)
    public static final /* synthetic */ int getType(EditText editText) {
        Intrinsics.checkNotNullParameter(editText, "<this>");
        throw new UnsupportedOperationException(NoGetterKt.NO_GETTER);
    }

    @ExperimentalSplittiesApi
    public static /* synthetic */ void getType$annotations(EditText editText) {
    }

    /* renamed from: setType-sVLYzLI, reason: not valid java name */
    public static final void m2585setTypesVLYzLI(@NotNull EditText editText, int i2) {
        Intrinsics.checkNotNullParameter(editText, "$this$<set-type>");
        editText.setInputType(i2);
    }
}
