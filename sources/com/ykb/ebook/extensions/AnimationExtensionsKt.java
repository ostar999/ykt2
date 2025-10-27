package com.ykb.ebook.extensions;

import android.content.Context;
import android.content.res.Resources;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import androidx.annotation.AnimRes;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000\u0014\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\u001a\u0018\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\b\b\u0001\u0010\u0004\u001a\u00020\u0005¨\u0006\u0006"}, d2 = {"loadAnimation", "Landroid/view/animation/Animation;", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "id", "", "ebook_release"}, k = 2, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes7.dex */
public final class AnimationExtensionsKt {
    @NotNull
    public static final Animation loadAnimation(@NotNull Context context, @AnimRes int i2) throws Resources.NotFoundException {
        Intrinsics.checkNotNullParameter(context, "context");
        Animation loadAnimation = AnimationUtils.loadAnimation(context, i2);
        Intrinsics.checkNotNullExpressionValue(loadAnimation, "loadAnimation");
        return loadAnimation;
    }
}
