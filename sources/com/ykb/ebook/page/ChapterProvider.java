package com.ykb.ebook.page;

import android.content.AppCtxKt;
import android.content.Context;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.ParcelFileDescriptor;
import android.text.Layout;
import android.text.TextPaint;
import com.tencent.connect.common.Constants;
import com.ykb.ebook.R;
import com.ykb.ebook.common.ReadConfig;
import com.ykb.ebook.extensions.ConvertExtensionsKt;
import com.ykb.ebook.extensions.PaintExtensionsKt;
import com.ykb.ebook.extensions.StringExtensionsKt;
import com.ykb.ebook.model.ChapterInfo;
import com.ykb.ebook.model.TextChapter;
import com.ykb.ebook.util.RealPathUtil;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.io.CloseableKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlinx.coroutines.CoroutineScope;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000j\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0007\n\u0002\b\t\n\u0002\u0010\b\n\u0002\b0\n\u0002\u0018\u0002\n\u0002\b\u0011\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0005\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001e\u0010k\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00040l2\b\u0010N\u001a\u0004\u0018\u00010MH\u0002J.\u0010m\u001a\u00020n2\u0006\u0010o\u001a\u00020p2\u0006\u0010q\u001a\u00020r2\u0006\u0010s\u001a\u00020t2\u0006\u0010u\u001a\u00020v2\u0006\u0010w\u001a\u00020\u001cJ\u0012\u0010P\u001a\u0004\u0018\u00010M2\u0006\u0010x\u001a\u00020tH\u0002J\b\u0010y\u001a\u00020zH\u0002J\u0006\u0010{\u001a\u00020zJ\u0016\u0010|\u001a\u00020z2\u0006\u0010}\u001a\u00020\u001c2\u0006\u0010~\u001a\u00020\u001cR$\u0010\u0003\u001a\u00020\u00048\u0006@\u0006X\u0087\u000e¢\u0006\u0014\n\u0000\u0012\u0004\b\u0005\u0010\u0002\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR$\u0010\n\u001a\u00020\u000b8\u0006@\u0006X\u0087\u000e¢\u0006\u0014\n\u0000\u0012\u0004\b\f\u0010\u0002\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u001e\u0010\u0013\u001a\u00020\u00122\u0006\u0010\u0011\u001a\u00020\u0012@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R&\u0010\u0016\u001a\u00020\u00122\u0006\u0010\u0011\u001a\u00020\u00128\u0006@BX\u0087\u000e¢\u0006\u000e\n\u0000\u0012\u0004\b\u0017\u0010\u0002\u001a\u0004\b\u0018\u0010\u0015R&\u0010\u0019\u001a\u00020\u00122\u0006\u0010\u0011\u001a\u00020\u00128\u0006@BX\u0087\u000e¢\u0006\u000e\n\u0000\u0012\u0004\b\u001a\u0010\u0002\u001a\u0004\b\u001b\u0010\u0015R&\u0010\u001d\u001a\u00020\u001c2\u0006\u0010\u0011\u001a\u00020\u001c8\u0006@BX\u0087\u000e¢\u0006\u000e\n\u0000\u0012\u0004\b\u001e\u0010\u0002\u001a\u0004\b\u001f\u0010 R&\u0010!\u001a\u00020\u001c2\u0006\u0010\u0011\u001a\u00020\u001c8\u0006@BX\u0087\u000e¢\u0006\u000e\n\u0000\u0012\u0004\b\"\u0010\u0002\u001a\u0004\b#\u0010 R&\u0010$\u001a\u00020\u001c2\u0006\u0010\u0011\u001a\u00020\u001c8\u0006@BX\u0087\u000e¢\u0006\u000e\n\u0000\u0012\u0004\b%\u0010\u0002\u001a\u0004\b&\u0010 R&\u0010'\u001a\u00020\u001c2\u0006\u0010\u0011\u001a\u00020\u001c8\u0006@BX\u0087\u000e¢\u0006\u000e\n\u0000\u0012\u0004\b(\u0010\u0002\u001a\u0004\b)\u0010 R&\u0010*\u001a\u00020\u001c2\u0006\u0010\u0011\u001a\u00020\u001c8\u0006@BX\u0087\u000e¢\u0006\u000e\n\u0000\u0012\u0004\b+\u0010\u0002\u001a\u0004\b,\u0010 R$\u0010-\u001a\u00020\u00048\u0006@\u0006X\u0087\u000e¢\u0006\u0014\n\u0000\u0012\u0004\b.\u0010\u0002\u001a\u0004\b/\u0010\u0007\"\u0004\b0\u0010\tR&\u00101\u001a\u00020\u001c2\u0006\u0010\u0011\u001a\u00020\u001c8\u0006@BX\u0087\u000e¢\u0006\u000e\n\u0000\u0012\u0004\b2\u0010\u0002\u001a\u0004\b3\u0010 R$\u00104\u001a\u00020\u00048\u0006@\u0006X\u0087\u000e¢\u0006\u0014\n\u0000\u0012\u0004\b5\u0010\u0002\u001a\u0004\b6\u0010\u0007\"\u0004\b7\u0010\tR$\u00108\u001a\u00020\u000b8\u0006@\u0006X\u0087\u000e¢\u0006\u0014\n\u0000\u0012\u0004\b9\u0010\u0002\u001a\u0004\b:\u0010\u000e\"\u0004\b;\u0010\u0010R&\u0010<\u001a\u00020\u00122\u0006\u0010\u0011\u001a\u00020\u00128\u0006@BX\u0087\u000e¢\u0006\u000e\n\u0000\u0012\u0004\b=\u0010\u0002\u001a\u0004\b>\u0010\u0015R$\u0010?\u001a\u00020\u00048\u0006@\u0006X\u0087\u000e¢\u0006\u0014\n\u0000\u0012\u0004\b@\u0010\u0002\u001a\u0004\bA\u0010\u0007\"\u0004\bB\u0010\tR$\u0010C\u001a\u00020\u000b8\u0006@\u0006X\u0087\u000e¢\u0006\u0014\n\u0000\u0012\u0004\bD\u0010\u0002\u001a\u0004\bE\u0010\u000e\"\u0004\bF\u0010\u0010R&\u0010G\u001a\u00020\u00122\u0006\u0010\u0011\u001a\u00020\u00128\u0006@BX\u0087\u000e¢\u0006\u000e\n\u0000\u0012\u0004\bH\u0010\u0002\u001a\u0004\bI\u0010\u0015R&\u0010J\u001a\u00020\u001c2\u0006\u0010\u0011\u001a\u00020\u001c8\u0006@BX\u0087\u000e¢\u0006\u000e\n\u0000\u0012\u0004\bK\u0010\u0002\u001a\u0004\bL\u0010 R*\u0010N\u001a\u0004\u0018\u00010M2\b\u0010\u0011\u001a\u0004\u0018\u00010M8\u0006@BX\u0087\u000e¢\u0006\u000e\n\u0000\u0012\u0004\bO\u0010\u0002\u001a\u0004\bP\u0010QR&\u0010R\u001a\u00020\u001c2\u0006\u0010\u0011\u001a\u00020\u001c8\u0006@BX\u0087\u000e¢\u0006\u000e\n\u0000\u0012\u0004\bS\u0010\u0002\u001a\u0004\bT\u0010 R&\u0010U\u001a\u00020\u001c2\u0006\u0010\u0011\u001a\u00020\u001c8\u0006@BX\u0087\u000e¢\u0006\u000e\n\u0000\u0012\u0004\bV\u0010\u0002\u001a\u0004\bW\u0010 R&\u0010X\u001a\u00020\u001c2\u0006\u0010\u0011\u001a\u00020\u001c8\u0006@BX\u0087\u000e¢\u0006\u000e\n\u0000\u0012\u0004\bY\u0010\u0002\u001a\u0004\bZ\u0010 R&\u0010[\u001a\u00020\u001c2\u0006\u0010\u0011\u001a\u00020\u001c8\u0006@BX\u0087\u000e¢\u0006\u000e\n\u0000\u0012\u0004\b\\\u0010\u0002\u001a\u0004\b]\u0010 R$\u0010^\u001a\u00020_8\u0006@\u0006X\u0087\u000e¢\u0006\u0014\n\u0000\u0012\u0004\b`\u0010\u0002\u001a\u0004\ba\u0010b\"\u0004\bc\u0010dR&\u0010e\u001a\u00020\u001c2\u0006\u0010\u0011\u001a\u00020\u001c8\u0006@BX\u0087\u000e¢\u0006\u000e\n\u0000\u0012\u0004\bf\u0010\u0002\u001a\u0004\bg\u0010 R&\u0010h\u001a\u00020\u001c2\u0006\u0010\u0011\u001a\u00020\u001c8\u0006@BX\u0087\u000e¢\u0006\u000e\n\u0000\u0012\u0004\bi\u0010\u0002\u001a\u0004\bj\u0010 ¨\u0006\u007f"}, d2 = {"Lcom/ykb/ebook/page/ChapterProvider;", "", "()V", "contentPaint", "Landroid/text/TextPaint;", "getContentPaint$annotations", "getContentPaint", "()Landroid/text/TextPaint;", "setContentPaint", "(Landroid/text/TextPaint;)V", "contentPaintFontMetrics", "Landroid/graphics/Paint$FontMetrics;", "getContentPaintFontMetrics$annotations", "getContentPaintFontMetrics", "()Landroid/graphics/Paint$FontMetrics;", "setContentPaintFontMetrics", "(Landroid/graphics/Paint$FontMetrics;)V", "<set-?>", "", "contentPaintTextHeight", "getContentPaintTextHeight", "()F", "indentCharWidth", "getIndentCharWidth$annotations", "getIndentCharWidth", "lineSpacingExtra", "getLineSpacingExtra$annotations", "getLineSpacingExtra", "", "paddingBottom", "getPaddingBottom$annotations", "getPaddingBottom", "()I", "paddingLeft", "getPaddingLeft$annotations", "getPaddingLeft", "paddingRight", "getPaddingRight$annotations", "getPaddingRight", "paddingTop", "getPaddingTop$annotations", "getPaddingTop", "paragraphSpacing", "getParagraphSpacing$annotations", "getParagraphSpacing", "reviewPaint", "getReviewPaint$annotations", "getReviewPaint", "setReviewPaint", "titleBottomSpacing", "getTitleBottomSpacing$annotations", "getTitleBottomSpacing", "titleNumPaint", "getTitleNumPaint$annotations", "getTitleNumPaint", "setTitleNumPaint", "titleNumPaintFontMetrics", "getTitleNumPaintFontMetrics$annotations", "getTitleNumPaintFontMetrics", "setTitleNumPaintFontMetrics", "titleNumPaintTextHeight", "getTitleNumPaintTextHeight$annotations", "getTitleNumPaintTextHeight", "titlePaint", "getTitlePaint$annotations", "getTitlePaint", "setTitlePaint", "titlePaintFontMetrics", "getTitlePaintFontMetrics$annotations", "getTitlePaintFontMetrics", "setTitlePaintFontMetrics", "titlePaintTextHeight", "getTitlePaintTextHeight$annotations", "getTitlePaintTextHeight", "titleTopSpacing", "getTitleTopSpacing$annotations", "getTitleTopSpacing", "Landroid/graphics/Typeface;", "typeface", "getTypeface$annotations", "getTypeface", "()Landroid/graphics/Typeface;", "viewHeight", "getViewHeight$annotations", "getViewHeight", "viewWidth", "getViewWidth$annotations", "getViewWidth", "visibleBottom", "getVisibleBottom$annotations", "getVisibleBottom", "visibleHeight", "getVisibleHeight$annotations", "getVisibleHeight", "visibleRect", "Landroid/graphics/RectF;", "getVisibleRect$annotations", "getVisibleRect", "()Landroid/graphics/RectF;", "setVisibleRect", "(Landroid/graphics/RectF;)V", "visibleRight", "getVisibleRight$annotations", "getVisibleRight", "visibleWidth", "getVisibleWidth$annotations", "getVisibleWidth", "getPaints", "Lkotlin/Pair;", "getTextChapterAsync", "Lcom/ykb/ebook/model/TextChapter;", Constants.PARAM_SCOPE, "Lkotlinx/coroutines/CoroutineScope;", "bookChapter", "Lcom/ykb/ebook/model/ChapterInfo;", "displayTitle", "", "bookContent", "Lcom/ykb/ebook/model/BookContent;", "chapterSize", "fontPath", "upLayout", "", "upStyle", "upViewSize", "width", "height", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nChapterProvider.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ChapterProvider.kt\ncom/ykb/ebook/page/ChapterProvider\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,323:1\n1#2:324\n*E\n"})
/* loaded from: classes7.dex */
public final class ChapterProvider {

    @NotNull
    public static final ChapterProvider INSTANCE;

    @NotNull
    private static TextPaint contentPaint;

    @NotNull
    private static Paint.FontMetrics contentPaintFontMetrics;
    private static float contentPaintTextHeight;
    private static float indentCharWidth;
    private static float lineSpacingExtra;
    private static int paddingBottom;
    private static int paddingLeft;
    private static int paddingRight;
    private static int paddingTop;
    private static int paragraphSpacing;

    @NotNull
    private static TextPaint reviewPaint;
    private static int titleBottomSpacing;

    @NotNull
    private static TextPaint titleNumPaint;

    @NotNull
    private static Paint.FontMetrics titleNumPaintFontMetrics;
    private static float titleNumPaintTextHeight;

    @NotNull
    private static TextPaint titlePaint;

    @NotNull
    private static Paint.FontMetrics titlePaintFontMetrics;
    private static float titlePaintTextHeight;
    private static int titleTopSpacing;

    @Nullable
    private static Typeface typeface;
    private static int viewHeight;
    private static int viewWidth;
    private static int visibleBottom;
    private static int visibleHeight;

    @NotNull
    private static RectF visibleRect;
    private static int visibleRight;
    private static int visibleWidth;

    static {
        ChapterProvider chapterProvider = new ChapterProvider();
        INSTANCE = chapterProvider;
        titlePaintFontMetrics = new Paint.FontMetrics();
        contentPaintFontMetrics = new Paint.FontMetrics();
        titleNumPaintFontMetrics = new Paint.FontMetrics();
        titlePaint = new TextPaint();
        titleNumPaint = new TextPaint();
        contentPaint = new TextPaint();
        reviewPaint = new TextPaint();
        visibleRect = new RectF();
        typeface = Typeface.DEFAULT;
        chapterProvider.upStyle();
    }

    private ChapterProvider() {
    }

    @NotNull
    public static final TextPaint getContentPaint() {
        return contentPaint;
    }

    @JvmStatic
    public static /* synthetic */ void getContentPaint$annotations() {
    }

    @NotNull
    public static final Paint.FontMetrics getContentPaintFontMetrics() {
        return contentPaintFontMetrics;
    }

    @JvmStatic
    public static /* synthetic */ void getContentPaintFontMetrics$annotations() {
    }

    public static final float getIndentCharWidth() {
        return indentCharWidth;
    }

    @JvmStatic
    public static /* synthetic */ void getIndentCharWidth$annotations() {
    }

    public static final float getLineSpacingExtra() {
        return lineSpacingExtra;
    }

    @JvmStatic
    public static /* synthetic */ void getLineSpacingExtra$annotations() {
    }

    public static final int getPaddingBottom() {
        return paddingBottom;
    }

    @JvmStatic
    public static /* synthetic */ void getPaddingBottom$annotations() {
    }

    public static final int getPaddingLeft() {
        return paddingLeft;
    }

    @JvmStatic
    public static /* synthetic */ void getPaddingLeft$annotations() {
    }

    public static final int getPaddingRight() {
        return paddingRight;
    }

    @JvmStatic
    public static /* synthetic */ void getPaddingRight$annotations() {
    }

    public static final int getPaddingTop() {
        return paddingTop;
    }

    @JvmStatic
    public static /* synthetic */ void getPaddingTop$annotations() {
    }

    private final Pair<TextPaint, TextPaint> getPaints(Typeface typeface2) {
        Typeface typefaceCreate = Typeface.create(typeface2, 1);
        Typeface typefaceCreate2 = Typeface.create(typeface2, 0);
        ReadConfig readConfig = ReadConfig.INSTANCE;
        int textBold = readConfig.getTextBold();
        Pair pair = textBold != 1 ? textBold != 2 ? new Pair(typefaceCreate, typefaceCreate2) : Build.VERSION.SDK_INT >= 28 ? new Pair(typefaceCreate2, Typeface.create(typeface2, 300, false)) : new Pair(typefaceCreate2, typefaceCreate2) : Build.VERSION.SDK_INT >= 28 ? new Pair(Typeface.create(typeface2, 900, false), typefaceCreate) : new Pair(typefaceCreate, typefaceCreate);
        Typeface typeface3 = (Typeface) pair.component1();
        Typeface typeface4 = (Typeface) pair.component2();
        TextPaint textPaint = new TextPaint();
        textPaint.setColor(AppCtxKt.getAppCtx().getColor(readConfig.getColorMode() != 2 ? R.color.color_303030 : R.color.color_7380a9));
        textPaint.setLetterSpacing(readConfig.getLetterSpacing());
        textPaint.setTypeface(typeface3);
        textPaint.setTextSize(ConvertExtensionsKt.spToPx(readConfig.getTextSize() + readConfig.getTitleSize()));
        textPaint.setAntiAlias(true);
        textPaint.setLinearText(true);
        TextPaint textPaint2 = new TextPaint();
        textPaint2.setColor(AppCtxKt.getAppCtx().getColor(readConfig.getColorMode() != 2 ? R.color.color_303030 : R.color.color_7380a9));
        textPaint2.setLetterSpacing(readConfig.getLetterSpacing());
        textPaint2.setTypeface(typeface4);
        textPaint2.setTextSize(ConvertExtensionsKt.spToPx(readConfig.getTextSize()));
        textPaint2.setAntiAlias(true);
        textPaint2.setLinearText(true);
        return new Pair<>(textPaint, textPaint2);
    }

    public static final int getParagraphSpacing() {
        return paragraphSpacing;
    }

    @JvmStatic
    public static /* synthetic */ void getParagraphSpacing$annotations() {
    }

    @NotNull
    public static final TextPaint getReviewPaint() {
        return reviewPaint;
    }

    @JvmStatic
    public static /* synthetic */ void getReviewPaint$annotations() {
    }

    public static final int getTitleBottomSpacing() {
        return titleBottomSpacing;
    }

    @JvmStatic
    public static /* synthetic */ void getTitleBottomSpacing$annotations() {
    }

    @NotNull
    public static final TextPaint getTitleNumPaint() {
        return titleNumPaint;
    }

    @JvmStatic
    public static /* synthetic */ void getTitleNumPaint$annotations() {
    }

    @NotNull
    public static final Paint.FontMetrics getTitleNumPaintFontMetrics() {
        return titleNumPaintFontMetrics;
    }

    @JvmStatic
    public static /* synthetic */ void getTitleNumPaintFontMetrics$annotations() {
    }

    public static final float getTitleNumPaintTextHeight() {
        return titleNumPaintTextHeight;
    }

    @JvmStatic
    public static /* synthetic */ void getTitleNumPaintTextHeight$annotations() {
    }

    @NotNull
    public static final TextPaint getTitlePaint() {
        return titlePaint;
    }

    @JvmStatic
    public static /* synthetic */ void getTitlePaint$annotations() {
    }

    @NotNull
    public static final Paint.FontMetrics getTitlePaintFontMetrics() {
        return titlePaintFontMetrics;
    }

    @JvmStatic
    public static /* synthetic */ void getTitlePaintFontMetrics$annotations() {
    }

    public static final float getTitlePaintTextHeight() {
        return titlePaintTextHeight;
    }

    @JvmStatic
    public static /* synthetic */ void getTitlePaintTextHeight$annotations() {
    }

    public static final int getTitleTopSpacing() {
        return titleTopSpacing;
    }

    @JvmStatic
    public static /* synthetic */ void getTitleTopSpacing$annotations() {
    }

    @Nullable
    public static final Typeface getTypeface() {
        return typeface;
    }

    @JvmStatic
    public static /* synthetic */ void getTypeface$annotations() {
    }

    public static final int getViewHeight() {
        return viewHeight;
    }

    @JvmStatic
    public static /* synthetic */ void getViewHeight$annotations() {
    }

    public static final int getViewWidth() {
        return viewWidth;
    }

    @JvmStatic
    public static /* synthetic */ void getViewWidth$annotations() {
    }

    public static final int getVisibleBottom() {
        return visibleBottom;
    }

    @JvmStatic
    public static /* synthetic */ void getVisibleBottom$annotations() {
    }

    public static final int getVisibleHeight() {
        return visibleHeight;
    }

    @JvmStatic
    public static /* synthetic */ void getVisibleHeight$annotations() {
    }

    @NotNull
    public static final RectF getVisibleRect() {
        return visibleRect;
    }

    @JvmStatic
    public static /* synthetic */ void getVisibleRect$annotations() {
    }

    public static final int getVisibleRight() {
        return visibleRight;
    }

    @JvmStatic
    public static /* synthetic */ void getVisibleRight$annotations() {
    }

    public static final int getVisibleWidth() {
        return visibleWidth;
    }

    @JvmStatic
    public static /* synthetic */ void getVisibleWidth$annotations() {
    }

    public static final void setContentPaint(@NotNull TextPaint textPaint) {
        Intrinsics.checkNotNullParameter(textPaint, "<set-?>");
        contentPaint = textPaint;
    }

    public static final void setContentPaintFontMetrics(@NotNull Paint.FontMetrics fontMetrics) {
        Intrinsics.checkNotNullParameter(fontMetrics, "<set-?>");
        contentPaintFontMetrics = fontMetrics;
    }

    public static final void setReviewPaint(@NotNull TextPaint textPaint) {
        Intrinsics.checkNotNullParameter(textPaint, "<set-?>");
        reviewPaint = textPaint;
    }

    public static final void setTitleNumPaint(@NotNull TextPaint textPaint) {
        Intrinsics.checkNotNullParameter(textPaint, "<set-?>");
        titleNumPaint = textPaint;
    }

    public static final void setTitleNumPaintFontMetrics(@NotNull Paint.FontMetrics fontMetrics) {
        Intrinsics.checkNotNullParameter(fontMetrics, "<set-?>");
        titleNumPaintFontMetrics = fontMetrics;
    }

    public static final void setTitlePaint(@NotNull TextPaint textPaint) {
        Intrinsics.checkNotNullParameter(textPaint, "<set-?>");
        titlePaint = textPaint;
    }

    public static final void setTitlePaintFontMetrics(@NotNull Paint.FontMetrics fontMetrics) {
        Intrinsics.checkNotNullParameter(fontMetrics, "<set-?>");
        titlePaintFontMetrics = fontMetrics;
    }

    public static final void setVisibleRect(@NotNull RectF rectF) {
        Intrinsics.checkNotNullParameter(rectF, "<set-?>");
        visibleRect = rectF;
    }

    private final void upLayout() {
        if (viewWidth > 0 && viewHeight > 0) {
            ReadConfig readConfig = ReadConfig.INSTANCE;
            paddingLeft = ConvertExtensionsKt.dpToPx(readConfig.getPaddingLeft());
            paddingTop = ConvertExtensionsKt.dpToPx(readConfig.getPaddingTop());
            paddingRight = ConvertExtensionsKt.dpToPx(readConfig.getPaddingRight());
            int iDpToPx = ConvertExtensionsKt.dpToPx(readConfig.getPaddingBottom());
            paddingBottom = iDpToPx;
            int i2 = viewWidth;
            int i3 = i2 - paddingLeft;
            int i4 = paddingRight;
            visibleWidth = i3 - i4;
            int i5 = viewHeight;
            int i6 = paddingTop;
            int i7 = (i5 - i6) - iDpToPx;
            visibleHeight = i7;
            visibleRight = i2 - i4;
            visibleBottom = i6 + i7;
        }
        visibleRect.set(paddingLeft, paddingTop, visibleRight, visibleBottom);
    }

    public final float getContentPaintTextHeight() {
        return contentPaintTextHeight;
    }

    @NotNull
    public final TextChapter getTextChapterAsync(@NotNull CoroutineScope scope, @NotNull ChapterInfo bookChapter, @NotNull String displayTitle, @NotNull com.ykb.ebook.model.BookContent bookContent, int chapterSize) {
        Intrinsics.checkNotNullParameter(scope, "scope");
        Intrinsics.checkNotNullParameter(bookChapter, "bookChapter");
        Intrinsics.checkNotNullParameter(displayTitle, "displayTitle");
        Intrinsics.checkNotNullParameter(bookContent, "bookContent");
        TextChapter textChapter = new TextChapter(bookChapter, bookChapter.getSort() - 1, displayTitle, chapterSize, bookContent.getSameTitleRemoved());
        textChapter.createLayout(scope, bookContent);
        return textChapter;
    }

    public final void upStyle() {
        ReadConfig readConfig = ReadConfig.INSTANCE;
        Typeface typeface2 = getTypeface(readConfig.getTextFont());
        typeface = typeface2;
        Pair<TextPaint, TextPaint> paints = getPaints(typeface2);
        titlePaint = paints.getFirst();
        contentPaint = paints.getSecond();
        titleNumPaint.setColor(titlePaint.getColor());
        titleNumPaint.setLetterSpacing(readConfig.getLetterSpacing());
        titleNumPaint.setTypeface(titlePaint.getTypeface());
        titleNumPaint.setTextSize(ConvertExtensionsKt.spToPx(Integer.valueOf((readConfig.getTextSize() + 27) - 16).intValue()));
        titlePaint.setTextSize(ConvertExtensionsKt.spToPx(Integer.valueOf((readConfig.getTextSize() + 33) - 16).intValue()));
        titleNumPaint.setAntiAlias(true);
        titleNumPaint.setLinearText(true);
        lineSpacingExtra = readConfig.getLineSpacingExtra() / 10.0f;
        paragraphSpacing = readConfig.getParagraphSpacing();
        titleTopSpacing = ConvertExtensionsKt.dpToPx(readConfig.getTitleTopSpacing());
        titleBottomSpacing = ConvertExtensionsKt.dpToPx(readConfig.getTitleBottomSpacing());
        indentCharWidth = Layout.getDesiredWidth(readConfig.getParagraphIndent(), contentPaint) / r0.length();
        titlePaintTextHeight = PaintExtensionsKt.getTextHeight(titlePaint);
        titleNumPaintTextHeight = PaintExtensionsKt.getTextHeight(titleNumPaint);
        contentPaintTextHeight = PaintExtensionsKt.getTextHeight(contentPaint);
        Paint.FontMetrics fontMetrics = titlePaint.getFontMetrics();
        Intrinsics.checkNotNullExpressionValue(fontMetrics, "titlePaint.fontMetrics");
        titlePaintFontMetrics = fontMetrics;
        Paint.FontMetrics fontMetrics2 = contentPaint.getFontMetrics();
        Intrinsics.checkNotNullExpressionValue(fontMetrics2, "contentPaint.fontMetrics");
        contentPaintFontMetrics = fontMetrics2;
        Paint.FontMetrics fontMetrics3 = titleNumPaint.getFontMetrics();
        Intrinsics.checkNotNullExpressionValue(fontMetrics3, "titleNumPaint.fontMetrics");
        titleNumPaintFontMetrics = fontMetrics3;
        upLayout();
    }

    public final void upViewSize(int width, int height) {
        if (width <= 0 || height <= 0) {
            return;
        }
        if (width == viewWidth && height == viewHeight) {
            return;
        }
        viewWidth = width;
        viewHeight = height;
        upLayout();
    }

    private final Typeface getTypeface(String fontPath) {
        Object objM783constructorimpl;
        Typeface typefaceCreateFromFile;
        try {
            Result.Companion companion = Result.INSTANCE;
            if (StringExtensionsKt.isContentScheme(fontPath) && Build.VERSION.SDK_INT >= 26) {
                ParcelFileDescriptor parcelFileDescriptorOpenFileDescriptor = AppCtxKt.getAppCtx().getContentResolver().openFileDescriptor(Uri.parse(fontPath), "r");
                Intrinsics.checkNotNull(parcelFileDescriptorOpenFileDescriptor);
                try {
                    typefaceCreateFromFile = new Typeface.Builder(parcelFileDescriptorOpenFileDescriptor.getFileDescriptor()).build();
                    CloseableKt.closeFinally(parcelFileDescriptorOpenFileDescriptor, null);
                } finally {
                }
            } else if (StringExtensionsKt.isContentScheme(fontPath)) {
                RealPathUtil realPathUtil = RealPathUtil.INSTANCE;
                Context appCtx = AppCtxKt.getAppCtx();
                Uri uri = Uri.parse(fontPath);
                Intrinsics.checkNotNullExpressionValue(uri, "parse(fontPath)");
                typefaceCreateFromFile = Typeface.createFromFile(realPathUtil.getPath(appCtx, uri));
            } else {
                if (fontPath.length() > 0) {
                    typefaceCreateFromFile = Typeface.createFromFile(fontPath);
                } else {
                    int systemTypefaces = ReadConfig.INSTANCE.getSystemTypefaces();
                    typefaceCreateFromFile = systemTypefaces != 1 ? systemTypefaces != 2 ? Typeface.SANS_SERIF : Typeface.MONOSPACE : Typeface.SERIF;
                }
            }
            objM783constructorimpl = Result.m783constructorimpl(typefaceCreateFromFile);
        } catch (Throwable th) {
            Result.Companion companion2 = Result.INSTANCE;
            objM783constructorimpl = Result.m783constructorimpl(ResultKt.createFailure(th));
        }
        if (Result.m786exceptionOrNullimpl(objM783constructorimpl) != null) {
            ReadConfig.INSTANCE.setTextFont("");
            objM783constructorimpl = Typeface.SANS_SERIF;
        }
        Typeface typeface2 = (Typeface) objM783constructorimpl;
        return typeface2 == null ? Typeface.DEFAULT : typeface2;
    }
}
