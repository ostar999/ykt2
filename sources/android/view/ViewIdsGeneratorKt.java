package android.view;

import android.view.View;
import java.util.concurrent.atomic.AtomicInteger;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import splitties.mainthread.MainThreadKt;

@Metadata(d1 = {"\u0000\u0018\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\u001a\u0006\u0010\t\u001a\u00020\u0001\u001a\b\u0010\n\u001a\u00020\u0001H\u0002\u001a\n\u0010\u000b\u001a\u00020\u0001*\u00020\u0006\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0002\u001a\u00020\u0001X\u0082\u000e¢\u0006\u0002\n\u0000\"\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000\"\u0015\u0010\u0005\u001a\u00020\u0001*\u00020\u00068F¢\u0006\u0006\u001a\u0004\b\u0007\u0010\b¨\u0006\f"}, d2 = {"aaptIdsStart", "", "mainThreadLastGeneratedId", "nextGeneratedId", "Ljava/util/concurrent/atomic/AtomicInteger;", "existingOrNewId", "Landroid/view/View;", "getExistingOrNewId", "(Landroid/view/View;)I", "generateViewId", "generatedViewIdCompat", "assignAndGetGeneratedId", "splitties-views_release"}, k = 2, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes9.dex */
public final class ViewIdsGeneratorKt {
    private static final int aaptIdsStart = 16777215;
    private static int mainThreadLastGeneratedId = 16777214;

    @NotNull
    private static final AtomicInteger nextGeneratedId = new AtomicInteger(1);

    public static final int assignAndGetGeneratedId(@NotNull View view) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        int iGenerateViewId = generateViewId();
        view.setId(iGenerateViewId);
        view.setSaveEnabled(false);
        return iGenerateViewId;
    }

    public static final int generateViewId() {
        if (!(MainThreadKt.mainThread == Thread.currentThread())) {
            return View.generateViewId();
        }
        int i2 = mainThreadLastGeneratedId;
        mainThreadLastGeneratedId = (i2 == 1 ? 16777215 : i2) - 1;
        return i2;
    }

    private static final int generatedViewIdCompat() {
        AtomicInteger atomicInteger;
        int i2;
        int i3;
        do {
            atomicInteger = nextGeneratedId;
            i2 = atomicInteger.get();
            i3 = i2 + 1;
            if (i3 > 16777215) {
                i3 = 1;
            }
        } while (!atomicInteger.compareAndSet(i2, i3));
        return i2;
    }

    public static final int getExistingOrNewId(@NotNull View view) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        int id = view.getId();
        return id == -1 ? assignAndGetGeneratedId(view) : id;
    }
}
