package com.yddmi.doctor.utils;

import android.content.Context;
import com.yddmi.doctor.R;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0004\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0010\u0010\u0007\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0004\u0018\u00010\bJ \u0010\t\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0010\u0010\n\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0004\u0018\u00010\bJ*\u0010\u000b\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00040\b2\b\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u0010\u0010\u0007\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0004\u0018\u00010\b¨\u0006\f"}, d2 = {"Lcom/yddmi/doctor/utils/PermissionNameConvert;", "", "()V", "getPermissionString", "", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "permissions", "", "listToString", "hints", "permissionsToNames", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class PermissionNameConvert {

    @NotNull
    public static final PermissionNameConvert INSTANCE = new PermissionNameConvert();

    private PermissionNameConvert() {
    }

    @NotNull
    public final String getPermissionString(@NotNull Context context, @Nullable List<String> permissions) {
        Intrinsics.checkNotNullParameter(context, "context");
        return listToString(context, permissionsToNames(context, permissions));
    }

    @NotNull
    public final String listToString(@NotNull Context context, @Nullable List<String> hints) {
        Intrinsics.checkNotNullParameter(context, "context");
        if (hints == null || hints.isEmpty()) {
            String string = context.getString(R.string.common_permission_unknown);
            Intrinsics.checkNotNullExpressionValue(string, "context.getString(R.stri…ommon_permission_unknown)");
            return string;
        }
        StringBuilder sb = new StringBuilder();
        for (String str : hints) {
            if (sb.length() == 0) {
                sb.append(str);
            } else {
                sb.append("、");
                sb.append(str);
            }
        }
        String string2 = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string2, "builder.toString()");
        return string2;
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:199:0x034a  */
    /* JADX WARN: Removed duplicated region for block: B:249:0x043b  */
    /* JADX WARN: Removed duplicated region for block: B:254:0x0451  */
    /* JADX WARN: Removed duplicated region for block: B:263:0x0476  */
    /* JADX WARN: Removed duplicated region for block: B:264:0x0479  */
    /* JADX WARN: Removed duplicated region for block: B:349:0x03e6 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:354:0x02da A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:357:0x0463 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:359:0x01bb A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:367:0x0285 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:373:0x048a A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:376:0x01ce A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:383:0x04ce A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:387:0x035c A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:392:0x04ee A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:440:0x000f A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:447:0x000f A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:451:0x000f A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:454:0x000f A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:466:0x000f A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:474:0x000f A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:478:0x000f A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:487:0x000f A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:493:0x000f A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:499:0x000f A[SYNTHETIC] */
    @org.jetbrains.annotations.NotNull
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.util.List<java.lang.String> permissionsToNames(@org.jetbrains.annotations.Nullable android.content.Context r13, @org.jetbrains.annotations.Nullable java.util.List<java.lang.String> r14) {
        /*
            Method dump skipped, instructions count: 1506
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.yddmi.doctor.utils.PermissionNameConvert.permissionsToNames(android.content.Context, java.util.List):java.util.List");
    }
}
