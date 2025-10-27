package com.psychiatrygarden.bean;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\u001a\u000e\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003¨\u0006\u0004"}, d2 = {"tabTypeGetType", "Lcom/psychiatrygarden/bean/TabType;", TypedValues.AttributesType.S_TARGET, "", "xizongapp_ykbRelease"}, k = 2, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final class CourseDirectoryBeanKt {
    @NotNull
    public static final TabType tabTypeGetType(@NotNull String target) {
        Intrinsics.checkNotNullParameter(target, "target");
        for (TabType tabType : TabType.values()) {
            if (Intrinsics.areEqual(tabType.getValue(), target)) {
                return tabType;
            }
        }
        return TabType.CATALOGUE;
    }
}
