package com.opensource.svgaplayer;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import com.umeng.analytics.pro.d;
import java.util.HashMap;
import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Deprecated(message = "This class has been deprecated from 2.4.0. We don't recommend you to use it.")
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004B\u0017\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007B\u001f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\n¨\u0006\u000b"}, d2 = {"Lcom/opensource/svgaplayer/SVGAPlayer;", "Lcom/opensource/svgaplayer/SVGAImageView;", d.R, "Landroid/content/Context;", "(Landroid/content/Context;)V", "attrs", "Landroid/util/AttributeSet;", "(Landroid/content/Context;Landroid/util/AttributeSet;)V", "defStyleAttr", "", "(Landroid/content/Context;Landroid/util/AttributeSet;I)V", BuildConfig.LIBRARY_PACKAGE_NAME}, k = 1, mv = {1, 1, 15})
/* loaded from: classes4.dex */
public final class SVGAPlayer extends SVGAImageView {
    private HashMap _$_findViewCache;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SVGAPlayer(@NotNull Context context) {
        super(context, null, 0, 6, null);
        Intrinsics.checkParameterIsNotNull(context, "context");
    }

    @Override // com.opensource.svgaplayer.SVGAImageView
    public void _$_clearFindViewByIdCache() {
        HashMap map = this._$_findViewCache;
        if (map != null) {
            map.clear();
        }
    }

    @Override // com.opensource.svgaplayer.SVGAImageView
    public View _$_findCachedViewById(int i2) {
        if (this._$_findViewCache == null) {
            this._$_findViewCache = new HashMap();
        }
        View view = (View) this._$_findViewCache.get(Integer.valueOf(i2));
        if (view != null) {
            return view;
        }
        View viewFindViewById = findViewById(i2);
        this._$_findViewCache.put(Integer.valueOf(i2), viewFindViewById);
        return viewFindViewById;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SVGAPlayer(@NotNull Context context, @NotNull AttributeSet attrs) {
        super(context, attrs, 0, 4, null);
        Intrinsics.checkParameterIsNotNull(context, "context");
        Intrinsics.checkParameterIsNotNull(attrs, "attrs");
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SVGAPlayer(@NotNull Context context, @NotNull AttributeSet attrs, int i2) {
        super(context, attrs, i2);
        Intrinsics.checkParameterIsNotNull(context, "context");
        Intrinsics.checkParameterIsNotNull(attrs, "attrs");
    }
}
