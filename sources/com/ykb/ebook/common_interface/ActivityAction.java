package com.ykb.ebook.common_interface;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import com.huawei.hms.support.api.entity.core.CommonCode;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\n\u0010\u0002\u001a\u0004\u0018\u00010\u0003H\u0016J\b\u0010\u0004\u001a\u00020\u0005H&J\u0010\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0016J\u0018\u0010\u0006\u001a\u00020\u00072\u000e\u0010\n\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00030\u000bH\u0016¨\u0006\f"}, d2 = {"Lcom/ykb/ebook/common_interface/ActivityAction;", "", "getActivity", "Landroid/app/Activity;", "getContext", "Landroid/content/Context;", "startActivity", "", CommonCode.Resolution.HAS_RESOLUTION_FROM_APK, "Landroid/content/Intent;", "clazz", "Ljava/lang/Class;", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public interface ActivityAction {

    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    public static final class DefaultImpls {
        @Nullable
        public static Activity getActivity(@NotNull ActivityAction activityAction) {
            Context context = activityAction.getContext();
            while (!(context instanceof Activity)) {
                if (!(context instanceof ContextWrapper) || (context = ((ContextWrapper) context).getBaseContext()) == null) {
                    return null;
                }
            }
            return (Activity) context;
        }

        public static void startActivity(@NotNull ActivityAction activityAction, @NotNull Class<? extends Activity> clazz) {
            Intrinsics.checkNotNullParameter(clazz, "clazz");
            activityAction.startActivity(new Intent(activityAction.getContext(), clazz));
        }

        public static void startActivity(@NotNull ActivityAction activityAction, @NotNull Intent intent) {
            Intrinsics.checkNotNullParameter(intent, "intent");
            if (!(activityAction.getContext() instanceof Activity)) {
                intent.addFlags(268435456);
            }
            activityAction.getContext().startActivity(intent);
        }
    }

    @Nullable
    Activity getActivity();

    @NotNull
    Context getContext();

    void startActivity(@NotNull Intent intent);

    void startActivity(@NotNull Class<? extends Activity> clazz);
}
