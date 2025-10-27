package com.ykb.ebook.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Size;
import com.caverock.androidsvg.SVG;
import com.caverock.androidsvg.SVGParseException;
import com.tencent.smtt.sdk.TbsReaderView;
import java.io.FileInputStream;
import java.io.InputStream;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J-\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\b2\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\bH\u0002¢\u0006\u0002\u0010\nJ)\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0007\u001a\u00020\b2\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\b¢\u0006\u0002\u0010\rJ)\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0007\u001a\u00020\b2\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\b¢\u0006\u0002\u0010\u0010J\u0010\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0005\u001a\u00020\u0006H\u0002J\u0010\u0010\u0011\u001a\u0004\u0018\u00010\u00122\u0006\u0010\u000b\u001a\u00020\fJ\u0010\u0010\u0011\u001a\u0004\u0018\u00010\u00122\u0006\u0010\u000e\u001a\u00020\u000f¨\u0006\u0013"}, d2 = {"Lcom/ykb/ebook/util/SvgUtils;", "", "()V", "createBitmap", "Landroid/graphics/Bitmap;", "svg", "Lcom/caverock/androidsvg/SVG;", "width", "", "height", "(Lcom/caverock/androidsvg/SVG;Ljava/lang/Integer;Ljava/lang/Integer;)Landroid/graphics/Bitmap;", "inputStream", "Ljava/io/InputStream;", "(Ljava/io/InputStream;ILjava/lang/Integer;)Landroid/graphics/Bitmap;", TbsReaderView.KEY_FILE_PATH, "", "(Ljava/lang/String;ILjava/lang/Integer;)Landroid/graphics/Bitmap;", "getSize", "Landroid/util/Size;", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nSvgUtils.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SvgUtils.kt\ncom/ykb/ebook/util/SvgUtils\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,86:1\n1#2:87\n*E\n"})
/* loaded from: classes7.dex */
public final class SvgUtils {

    @NotNull
    public static final SvgUtils INSTANCE = new SvgUtils();

    private SvgUtils() {
    }

    public static /* synthetic */ Bitmap createBitmap$default(SvgUtils svgUtils, String str, int i2, Integer num, int i3, Object obj) {
        if ((i3 & 4) != 0) {
            num = null;
        }
        return svgUtils.createBitmap(str, i2, num);
    }

    @Nullable
    public final Bitmap createBitmap(@NotNull String filePath, int width, @Nullable Integer height) {
        Object objM783constructorimpl;
        Intrinsics.checkNotNullParameter(filePath, "filePath");
        try {
            Result.Companion companion = Result.INSTANCE;
            objM783constructorimpl = Result.m783constructorimpl(INSTANCE.createBitmap(new FileInputStream(filePath), width, height));
        } catch (Throwable th) {
            Result.Companion companion2 = Result.INSTANCE;
            objM783constructorimpl = Result.m783constructorimpl(ResultKt.createFailure(th));
        }
        if (Result.m789isFailureimpl(objM783constructorimpl)) {
            objM783constructorimpl = null;
        }
        return (Bitmap) objM783constructorimpl;
    }

    @Nullable
    public final Size getSize(@NotNull String filePath) {
        Object objM783constructorimpl;
        Intrinsics.checkNotNullParameter(filePath, "filePath");
        try {
            Result.Companion companion = Result.INSTANCE;
            objM783constructorimpl = Result.m783constructorimpl(INSTANCE.getSize(new FileInputStream(filePath)));
        } catch (Throwable th) {
            Result.Companion companion2 = Result.INSTANCE;
            objM783constructorimpl = Result.m783constructorimpl(ResultKt.createFailure(th));
        }
        if (Result.m789isFailureimpl(objM783constructorimpl)) {
            objM783constructorimpl = null;
        }
        return (Size) objM783constructorimpl;
    }

    public static /* synthetic */ Bitmap createBitmap$default(SvgUtils svgUtils, InputStream inputStream, int i2, Integer num, int i3, Object obj) {
        if ((i3 & 4) != 0) {
            num = null;
        }
        return svgUtils.createBitmap(inputStream, i2, num);
    }

    public static /* synthetic */ Bitmap createBitmap$default(SvgUtils svgUtils, SVG svg, Integer num, Integer num2, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            num = null;
        }
        if ((i2 & 4) != 0) {
            num2 = null;
        }
        return svgUtils.createBitmap(svg, num, num2);
    }

    @Nullable
    public final Bitmap createBitmap(@NotNull InputStream inputStream, int width, @Nullable Integer height) {
        Object objM783constructorimpl;
        Intrinsics.checkNotNullParameter(inputStream, "inputStream");
        try {
            Result.Companion companion = Result.INSTANCE;
            SVG svg = SVG.getFromInputStream(inputStream);
            SvgUtils svgUtils = INSTANCE;
            Intrinsics.checkNotNullExpressionValue(svg, "svg");
            objM783constructorimpl = Result.m783constructorimpl(svgUtils.createBitmap(svg, Integer.valueOf(width), height));
        } catch (Throwable th) {
            Result.Companion companion2 = Result.INSTANCE;
            objM783constructorimpl = Result.m783constructorimpl(ResultKt.createFailure(th));
        }
        if (Result.m789isFailureimpl(objM783constructorimpl)) {
            objM783constructorimpl = null;
        }
        return (Bitmap) objM783constructorimpl;
    }

    @Nullable
    public final Size getSize(@NotNull InputStream inputStream) {
        Object objM783constructorimpl;
        Intrinsics.checkNotNullParameter(inputStream, "inputStream");
        try {
            Result.Companion companion = Result.INSTANCE;
            SVG svg = SVG.getFromInputStream(inputStream);
            SvgUtils svgUtils = INSTANCE;
            Intrinsics.checkNotNullExpressionValue(svg, "svg");
            objM783constructorimpl = Result.m783constructorimpl(svgUtils.getSize(svg));
        } catch (Throwable th) {
            Result.Companion companion2 = Result.INSTANCE;
            objM783constructorimpl = Result.m783constructorimpl(ResultKt.createFailure(th));
        }
        if (Result.m789isFailureimpl(objM783constructorimpl)) {
            objM783constructorimpl = null;
        }
        return (Size) objM783constructorimpl;
    }

    private final Bitmap createBitmap(SVG svg, Integer width, Integer height) throws SVGParseException {
        Size size = getSize(svg);
        int width2 = width != null ? size.getWidth() / width.intValue() : -1;
        int height2 = height != null ? size.getHeight() / height.intValue() : -1;
        if (width2 > 1 && height2 > 1) {
            width2 = Math.max(width2, height2);
        } else if (width2 <= 1) {
            width2 = height2 > 1 ? height2 : 1;
        }
        if (svg.getDocumentViewBox() == null && size.getWidth() > 0 && size.getHeight() > 0) {
            svg.setDocumentViewBox(0.0f, 0.0f, svg.getDocumentWidth(), svg.getDocumentHeight());
        }
        svg.setDocumentWidth("100%");
        svg.setDocumentHeight("100%");
        Bitmap bitmap = Bitmap.createBitmap(size.getWidth() / width2, size.getHeight() / width2, Bitmap.Config.ARGB_8888);
        svg.renderToCanvas(new Canvas(bitmap));
        Intrinsics.checkNotNullExpressionValue(bitmap, "bitmap");
        return bitmap;
    }

    private final Size getSize(SVG svg) {
        Integer numValueOf = Integer.valueOf((int) svg.getDocumentWidth());
        if (!(numValueOf.intValue() > 0)) {
            numValueOf = null;
        }
        int iIntValue = numValueOf != null ? numValueOf.intValue() : (int) (svg.getDocumentViewBox().right - svg.getDocumentViewBox().left);
        Integer numValueOf2 = Integer.valueOf((int) svg.getDocumentHeight());
        Integer num = numValueOf2.intValue() > 0 ? numValueOf2 : null;
        return new Size(iIntValue, num != null ? num.intValue() : (int) (svg.getDocumentViewBox().bottom - svg.getDocumentViewBox().top));
    }
}
