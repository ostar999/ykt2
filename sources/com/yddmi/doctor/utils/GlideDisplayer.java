package com.yddmi.doctor.utils;

import android.widget.ImageView;
import com.catchpig.mvvm.ext.ImageViewExtKt;
import com.lwkandroid.widget.ngv.INgvImageLoader;
import com.tencent.open.SocialConstants;
import com.yddmi.doctor.R;
import com.yddmi.doctor.entity.result.LocalMedia;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0000\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0003J(\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\nH\u0016¨\u0006\f"}, d2 = {"Lcom/yddmi/doctor/utils/GlideDisplayer;", "Lcom/lwkandroid/widget/ngv/INgvImageLoader;", "Lcom/yddmi/doctor/entity/result/LocalMedia;", "()V", "load", "", SocialConstants.PARAM_SOURCE, "imageView", "Landroid/widget/ImageView;", "width", "", "height", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class GlideDisplayer implements INgvImageLoader<LocalMedia> {
    @Override // com.lwkandroid.widget.ngv.INgvImageLoader
    public void load(@NotNull LocalMedia source, @NotNull ImageView imageView, int width, int height) {
        Intrinsics.checkNotNullParameter(source, "source");
        Intrinsics.checkNotNullParameter(imageView, "imageView");
        ImageViewExtKt.load(imageView, source.getPath(), (261628 & 2) != 0 ? 0 : R.drawable.common_no_data, (261628 & 4) != 0 ? 0 : 0, (261628 & 8) != 0 ? false : false, (261628 & 16) != 0 ? false : false, (261628 & 32) != 0 ? 0 : 0, (261628 & 64) != 0 ? 0 : 0, (261628 & 128) != 0 ? 0.0f : 0.0f, (261628 & 256) != 0 ? 20.0f : 0.0f, (261628 & 512) != 0 ? 0 : 6, (261628 & 1024) != 0 ? null : null, (261628 & 2048) != 0 ? false : false, (261628 & 4096) != 0 ? false : false, (261628 & 8192) != 0 ? 0 : 0, (261628 & 16384) != 0 ? 0 : 0, (261628 & 32768) != 0 ? false : false, (261628 & 65536) != 0 ? null : null, (261628 & 131072) == 0 ? null : null);
    }
}
