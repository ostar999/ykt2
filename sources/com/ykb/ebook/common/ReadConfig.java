package com.ykb.ebook.common;

import android.content.AppCtxKt;
import com.ykb.ebook.extensions.ContextExtensionsKt;
import com.ykb.ebook.util.Log;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\bh\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001c\u0010\u008e\u0001\u001a\u00020(2\u0013\u0010\u008f\u0001\u001a\u000e\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020(0'R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0006X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0006X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0006X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0006X\u0086T¢\u0006\u0002\n\u0000R\u0011\u0010\f\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR$\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u000f\u001a\u00020\u00108F@FX\u0086\u000e¢\u0006\f\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R$\u0010\u0016\u001a\u00020\u00042\u0006\u0010\u000f\u001a\u00020\u00048F@FX\u0086\u000e¢\u0006\f\u001a\u0004\b\u0017\u0010\u000e\"\u0004\b\u0018\u0010\u0019R$\u0010\u001a\u001a\u00020\u00042\u0006\u0010\u000f\u001a\u00020\u00048F@FX\u0086\u000e¢\u0006\f\u001a\u0004\b\u001b\u0010\u000e\"\u0004\b\u001c\u0010\u0019R$\u0010\u001d\u001a\u00020\u00042\u0006\u0010\u000f\u001a\u00020\u00048F@FX\u0086\u000e¢\u0006\f\u001a\u0004\b\u001e\u0010\u000e\"\u0004\b\u001f\u0010\u0019R$\u0010 \u001a\u00020\u00102\u0006\u0010\u000f\u001a\u00020\u00108F@FX\u0086\u000e¢\u0006\f\u001a\u0004\b!\u0010\u0013\"\u0004\b\"\u0010\u0015R$\u0010#\u001a\u00020\u00102\u0006\u0010\u000f\u001a\u00020\u00108F@FX\u0086\u000e¢\u0006\f\u001a\u0004\b$\u0010\u0013\"\u0004\b%\u0010\u0015R\u001a\u0010&\u001a\u000e\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020(0'X\u0082.¢\u0006\u0002\n\u0000R$\u0010)\u001a\u00020\u00102\u0006\u0010\u000f\u001a\u00020\u00108F@FX\u0086\u000e¢\u0006\f\u001a\u0004\b*\u0010\u0013\"\u0004\b+\u0010\u0015R$\u0010,\u001a\u00020\u00102\u0006\u0010\u000f\u001a\u00020\u00108F@FX\u0086\u000e¢\u0006\f\u001a\u0004\b,\u0010\u0013\"\u0004\b-\u0010\u0015R$\u0010.\u001a\u00020\u00102\u0006\u0010\u000f\u001a\u00020\u00108F@FX\u0086\u000e¢\u0006\f\u001a\u0004\b.\u0010\u0013\"\u0004\b/\u0010\u0015R$\u00100\u001a\u00020\u00042\u0006\u0010\u000f\u001a\u00020\u00048F@FX\u0086\u000e¢\u0006\f\u001a\u0004\b1\u0010\u000e\"\u0004\b2\u0010\u0019R\u0011\u00103\u001a\u00020\u00108F¢\u0006\u0006\u001a\u0004\b4\u0010\u0013R$\u00105\u001a\u00020\u00102\u0006\u0010\u000f\u001a\u00020\u00108F@FX\u0086\u000e¢\u0006\f\u001a\u0004\b6\u0010\u0013\"\u0004\b7\u0010\u0015R$\u00108\u001a\u00020\u00042\u0006\u0010\u000f\u001a\u00020\u00048F@FX\u0086\u000e¢\u0006\f\u001a\u0004\b9\u0010\u000e\"\u0004\b:\u0010\u0019R$\u0010;\u001a\u00020\u00042\u0006\u0010\u000f\u001a\u00020\u00048F@FX\u0086\u000e¢\u0006\f\u001a\u0004\b<\u0010\u000e\"\u0004\b=\u0010\u0019R\u0011\u0010>\u001a\u00020\u00108F¢\u0006\u0006\u001a\u0004\b?\u0010\u0013R$\u0010@\u001a\u00020\u00042\u0006\u0010\u000f\u001a\u00020\u00048F@FX\u0086\u000e¢\u0006\f\u001a\u0004\bA\u0010\u000e\"\u0004\bB\u0010\u0019R$\u0010C\u001a\u00020\u00042\u0006\u0010\u000f\u001a\u00020\u00048F@FX\u0086\u000e¢\u0006\f\u001a\u0004\bD\u0010\u000e\"\u0004\bE\u0010\u0019R$\u0010F\u001a\u00020\u00042\u0006\u0010\u000f\u001a\u00020\u00048F@FX\u0086\u000e¢\u0006\f\u001a\u0004\bG\u0010\u000e\"\u0004\bH\u0010\u0019R$\u0010I\u001a\u00020\u00042\u0006\u0010\u000f\u001a\u00020\u00048F@FX\u0086\u000e¢\u0006\f\u001a\u0004\bJ\u0010\u000e\"\u0004\bK\u0010\u0019R$\u0010L\u001a\u00020\u00042\u0006\u0010\u000f\u001a\u00020\u00048F@FX\u0086\u000e¢\u0006\f\u001a\u0004\bM\u0010\u000e\"\u0004\bN\u0010\u0019R\u001a\u0010O\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bP\u0010Q\"\u0004\bR\u0010SR$\u0010T\u001a\u00020\u00042\u0006\u0010\u000f\u001a\u00020\u00048F@FX\u0086\u000e¢\u0006\f\u001a\u0004\bU\u0010\u000e\"\u0004\bV\u0010\u0019R$\u0010W\u001a\u00020\u00102\u0006\u0010\u000f\u001a\u00020\u00108F@FX\u0086\u000e¢\u0006\f\u001a\u0004\bX\u0010\u0013\"\u0004\bY\u0010\u0015R$\u0010Z\u001a\u00020\u00042\u0006\u0010\u000f\u001a\u00020\u00048F@FX\u0086\u000e¢\u0006\f\u001a\u0004\b[\u0010\u000e\"\u0004\b\\\u0010\u0019R\u001a\u0010]\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b^\u0010\u000e\"\u0004\b_\u0010\u0019R$\u0010`\u001a\u00020\u00102\u0006\u0010\u000f\u001a\u00020\u00108F@FX\u0086\u000e¢\u0006\f\u001a\u0004\ba\u0010\u0013\"\u0004\bb\u0010\u0015R$\u0010c\u001a\u00020\u00102\u0006\u0010\u000f\u001a\u00020\u00108F@FX\u0086\u000e¢\u0006\f\u001a\u0004\bd\u0010\u0013\"\u0004\be\u0010\u0015R$\u0010f\u001a\u00020\u00102\u0006\u0010\u000f\u001a\u00020\u00108F@FX\u0086\u000e¢\u0006\f\u001a\u0004\bg\u0010\u0013\"\u0004\bh\u0010\u0015R$\u0010i\u001a\u00020\u00102\u0006\u0010\u000f\u001a\u00020\u00108F@FX\u0086\u000e¢\u0006\f\u001a\u0004\bj\u0010\u0013\"\u0004\bk\u0010\u0015R$\u0010l\u001a\u00020\u00042\u0006\u0010\u000f\u001a\u00020\u00048F@FX\u0086\u000e¢\u0006\f\u001a\u0004\bm\u0010\u000e\"\u0004\bn\u0010\u0019R$\u0010o\u001a\u00020\u00042\u0006\u0010\u000f\u001a\u00020\u00048F@FX\u0086\u000e¢\u0006\f\u001a\u0004\bp\u0010\u000e\"\u0004\bq\u0010\u0019R$\u0010r\u001a\u00020\u00062\u0006\u0010\u000f\u001a\u00020\u00068F@FX\u0086\u000e¢\u0006\f\u001a\u0004\bs\u0010Q\"\u0004\bt\u0010SR\u0011\u0010u\u001a\u00020\u00108F¢\u0006\u0006\u001a\u0004\bv\u0010\u0013R\u0011\u0010w\u001a\u00020\u00108F¢\u0006\u0006\u001a\u0004\bx\u0010\u0013R$\u0010y\u001a\u00020\u00042\u0006\u0010\u000f\u001a\u00020\u00048F@FX\u0086\u000e¢\u0006\f\u001a\u0004\bz\u0010\u000e\"\u0004\b{\u0010\u0019R$\u0010|\u001a\u00020\u00042\u0006\u0010\u000f\u001a\u00020\u00048F@FX\u0086\u000e¢\u0006\f\u001a\u0004\b}\u0010\u000e\"\u0004\b~\u0010\u0019R&\u0010\u007f\u001a\u00020\u00042\u0006\u0010\u000f\u001a\u00020\u00048F@FX\u0086\u000e¢\u0006\u000e\u001a\u0005\b\u0080\u0001\u0010\u000e\"\u0005\b\u0081\u0001\u0010\u0019R'\u0010\u0082\u0001\u001a\u00020\u00042\u0006\u0010\u000f\u001a\u00020\u00048F@FX\u0086\u000e¢\u0006\u000e\u001a\u0005\b\u0083\u0001\u0010\u000e\"\u0005\b\u0084\u0001\u0010\u0019R\u001d\u0010\u0085\u0001\u001a\u00020\u0010X\u0086\u000e¢\u0006\u0010\n\u0000\u001a\u0005\b\u0086\u0001\u0010\u0013\"\u0005\b\u0087\u0001\u0010\u0015R'\u0010\u0088\u0001\u001a\u00020\u00102\u0006\u0010\u000f\u001a\u00020\u00108F@FX\u0086\u000e¢\u0006\u000e\u001a\u0005\b\u0089\u0001\u0010\u0013\"\u0005\b\u008a\u0001\u0010\u0015R'\u0010\u008b\u0001\u001a\u00020\u00102\u0006\u0010\u000f\u001a\u00020\u00108F@FX\u0086\u000e¢\u0006\u000e\u001a\u0005\b\u008c\u0001\u0010\u0013\"\u0005\b\u008d\u0001\u0010\u0015¨\u0006\u0090\u0001"}, d2 = {"Lcom/ykb/ebook/common/ReadConfig;", "", "()V", "FIRST_CHAPTER_PAGE_INDEX", "", "INDENT_CHAR", "", "LAST_CHAPTER_PAGE_INDEX", "QUESTION_MULTI_CHAR", "QUESTION_SINGLE_CHAR", "REVIEW_CHAR", "SRC_REPLACE_CHAR", "bgAlpha", "getBgAlpha", "()I", "value", "", "bookIsUnlock", "getBookIsUnlock", "()Z", "setBookIsUnlock", "(Z)V", "colorMode", "getColorMode", "setColorMode", "(I)V", "colorModePre", "getColorModePre", "setColorModePre", "drawLineColor", "getDrawLineColor", "setDrawLineColor", "enableReview", "getEnableReview", "setEnableReview", "firstPage", "getFirstPage", "setFirstPage", "firstPageListener", "Lkotlin/Function1;", "", "hideStatusBar", "getHideStatusBar", "setHideStatusBar", "isShowNote", "setShowNote", "isShowReview", "setShowReview", "keepLight", "getKeepLight", "setKeepLight", "keyPageOnLongPress", "getKeyPageOnLongPress", "lastPage", "getLastPage", "setLastPage", "letterSpacing", "getLetterSpacing", "setLetterSpacing", "lineSpacingExtra", "getLineSpacingExtra", "setLineSpacingExtra", "noAnimScrollPage", "getNoAnimScrollPage", "paddingBottom", "getPaddingBottom", "setPaddingBottom", "paddingLeft", "getPaddingLeft", "setPaddingLeft", "paddingRight", "getPaddingRight", "setPaddingRight", "paddingTop", "getPaddingTop", "setPaddingTop", "pageAnim", "getPageAnim", "setPageAnim", "paragraphIndent", "getParagraphIndent", "()Ljava/lang/String;", "setParagraphIndent", "(Ljava/lang/String;)V", "paragraphSpacing", "getParagraphSpacing", "setParagraphSpacing", "previewImageByClick", "getPreviewImageByClick", "setPreviewImageByClick", "restRemind", "getRestRemind", "setRestRemind", "selectTextPosition", "getSelectTextPosition", "setSelectTextPosition", "showBattery", "getShowBattery", "setShowBattery", "showFeiYe", "getShowFeiYe", "setShowFeiYe", "showFirstPage", "getShowFirstPage", "setShowFirstPage", "showTime", "getShowTime", "setShowTime", "systemTypefaces", "getSystemTypefaces", "setSystemTypefaces", "textBold", "getTextBold", "setTextBold", "textFont", "getTextFont", "setTextFont", "textFullJustify", "getTextFullJustify", "textSelectAble", "getTextSelectAble", "textSize", "getTextSize", "setTextSize", "titleBottomSpacing", "getTitleBottomSpacing", "setTitleBottomSpacing", "titleSize", "getTitleSize", "setTitleSize", "titleTopSpacing", "getTitleTopSpacing", "setTitleTopSpacing", "useAntiAlias", "getUseAntiAlias", "setUseAntiAlias", "useZhLayout", "getUseZhLayout", "setUseZhLayout", "volumeKeyPage", "getVolumeKeyPage", "setVolumeKeyPage", "setFirstPageListenerCallBack", "action", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class ReadConfig {
    public static final int FIRST_CHAPTER_PAGE_INDEX = -9999;

    @NotNull
    public static final String INDENT_CHAR = "\u3000";
    public static final int LAST_CHAPTER_PAGE_INDEX = -8888;

    @NotNull
    public static final String QUESTION_MULTI_CHAR = "▦";

    @NotNull
    public static final String QUESTION_SINGLE_CHAR = "▧";

    @NotNull
    public static final String REVIEW_CHAR = "▨";

    @NotNull
    public static final String SRC_REPLACE_CHAR = "@";
    private static Function1<? super Boolean, Unit> firstPageListener;

    @NotNull
    public static final ReadConfig INSTANCE = new ReadConfig();
    private static int selectTextPosition = -1;

    @NotNull
    private static String paragraphIndent = "";
    private static boolean useAntiAlias = ContextExtensionsKt.getPrefBoolean$default(AppCtxKt.getAppCtx(), PreferKeyKt.ANTI_ALIAS, false, 2, null);
    private static final int bgAlpha = ContextExtensionsKt.getPrefInt(AppCtxKt.getAppCtx(), PreferKeyKt.BG_ALPHA, 100);

    private ReadConfig() {
    }

    public final int getBgAlpha() {
        return bgAlpha;
    }

    public final boolean getBookIsUnlock() {
        return ContextExtensionsKt.getPrefBoolean(AppCtxKt.getAppCtx(), PreferKeyKt.BOOK_IS_UNLOCK, false);
    }

    public final int getColorMode() {
        return ContextExtensionsKt.getPrefInt(AppCtxKt.getAppCtx(), PreferKeyKt.COLOR_MODE, 0);
    }

    public final int getColorModePre() {
        return ContextExtensionsKt.getPrefInt(AppCtxKt.getAppCtx(), PreferKeyKt.COLOR_MODE_PRE, 0);
    }

    public final int getDrawLineColor() {
        return ContextExtensionsKt.getPrefInt(AppCtxKt.getAppCtx(), PreferKeyKt.DRAW_LINE_COLOR, 0);
    }

    public final boolean getEnableReview() {
        return ContextExtensionsKt.getPrefBoolean(AppCtxKt.getAppCtx(), PreferKeyKt.ENABLE_REVIEW, true);
    }

    public final boolean getFirstPage() {
        return ContextExtensionsKt.getPrefBoolean(AppCtxKt.getAppCtx(), PreferKeyKt.CURRENT_FIRST_PAGE, false);
    }

    public final boolean getHideStatusBar() {
        return ContextExtensionsKt.getPrefBoolean(AppCtxKt.getAppCtx(), PreferKeyKt.HIDE_STATUS_BAR, false);
    }

    public final int getKeepLight() {
        return ContextExtensionsKt.getPrefInt(AppCtxKt.getAppCtx(), PreferKeyKt.KEEP_LIGHT, 0);
    }

    public final boolean getKeyPageOnLongPress() {
        return ContextExtensionsKt.getPrefBoolean(AppCtxKt.getAppCtx(), PreferKeyKt.KEY_PAGE_ON_LONG_PRESS, false);
    }

    public final boolean getLastPage() {
        return ContextExtensionsKt.getPrefBoolean(AppCtxKt.getAppCtx(), PreferKeyKt.CURRENT_LAST_PAGE, false);
    }

    public final int getLetterSpacing() {
        return ContextExtensionsKt.getPrefInt(AppCtxKt.getAppCtx(), PreferKeyKt.LETTER_SPACING, 0);
    }

    public final int getLineSpacingExtra() {
        return ContextExtensionsKt.getPrefInt(AppCtxKt.getAppCtx(), PreferKeyKt.LINE_SPACING_EXTRA, 20);
    }

    public final boolean getNoAnimScrollPage() {
        return ContextExtensionsKt.getPrefBoolean(AppCtxKt.getAppCtx(), PreferKeyKt.NO_ANIM_SCROLL, false);
    }

    public final int getPaddingBottom() {
        return ContextExtensionsKt.getPrefInt(AppCtxKt.getAppCtx(), PreferKeyKt.CONTENT_PADDING_BOTTOM, 16);
    }

    public final int getPaddingLeft() {
        return ContextExtensionsKt.getPrefInt(AppCtxKt.getAppCtx(), PreferKeyKt.CONTENT_PADDING_LEFT, 16);
    }

    public final int getPaddingRight() {
        return ContextExtensionsKt.getPrefInt(AppCtxKt.getAppCtx(), PreferKeyKt.CONTENT_PADDING_RIGHT, 16);
    }

    public final int getPaddingTop() {
        return ContextExtensionsKt.getPrefInt(AppCtxKt.getAppCtx(), PreferKeyKt.CONTENT_PADDING_TOP, 24);
    }

    public final int getPageAnim() {
        return ContextExtensionsKt.getPrefInt(AppCtxKt.getAppCtx(), PreferKeyKt.PAGE_ANIM, 2);
    }

    @NotNull
    public final String getParagraphIndent() {
        return paragraphIndent;
    }

    public final int getParagraphSpacing() {
        return ContextExtensionsKt.getPrefInt(AppCtxKt.getAppCtx(), PreferKeyKt.PARAGRAPH_SPACING, 2);
    }

    public final boolean getPreviewImageByClick() {
        return ContextExtensionsKt.getPrefBoolean(AppCtxKt.getAppCtx(), PreferKeyKt.PREVIEW_IMAGE_BY_CLICK, true);
    }

    public final int getRestRemind() {
        return ContextExtensionsKt.getPrefInt(AppCtxKt.getAppCtx(), PreferKeyKt.REST_REMIND, 0);
    }

    public final int getSelectTextPosition() {
        return selectTextPosition;
    }

    public final boolean getShowBattery() {
        return ContextExtensionsKt.getPrefBoolean(AppCtxKt.getAppCtx(), PreferKeyKt.SHOW_BATTERY, true);
    }

    public final boolean getShowFeiYe() {
        return ContextExtensionsKt.getPrefBoolean(AppCtxKt.getAppCtx(), PreferKeyKt.FEIYE_TAG, false);
    }

    public final boolean getShowFirstPage() {
        return ContextExtensionsKt.getPrefBoolean(AppCtxKt.getAppCtx(), PreferKeyKt.SHOW_FIRST_PAGE, true);
    }

    public final boolean getShowTime() {
        return ContextExtensionsKt.getPrefBoolean(AppCtxKt.getAppCtx(), "show_time", true);
    }

    public final int getSystemTypefaces() {
        return ContextExtensionsKt.getPrefInt$default(AppCtxKt.getAppCtx(), PreferKeyKt.SYSTEM_TYPEFACES, 0, 2, null);
    }

    public final int getTextBold() {
        return ContextExtensionsKt.getPrefInt(AppCtxKt.getAppCtx(), PreferKeyKt.TEXT_BOLD, 0);
    }

    @NotNull
    public final String getTextFont() {
        String prefString = ContextExtensionsKt.getPrefString(AppCtxKt.getAppCtx(), PreferKeyKt.TEXT_FONT, "");
        return prefString == null ? "" : prefString;
    }

    public final boolean getTextFullJustify() {
        return ContextExtensionsKt.getPrefBoolean(AppCtxKt.getAppCtx(), PreferKeyKt.TEXT_FULL_JUSTIFY, true);
    }

    public final boolean getTextSelectAble() {
        return ContextExtensionsKt.getPrefBoolean(AppCtxKt.getAppCtx(), PreferKeyKt.TEXT_SELECTABLE, true);
    }

    public final int getTextSize() {
        return ContextExtensionsKt.getPrefInt(AppCtxKt.getAppCtx(), PreferKeyKt.TEXT_SIZE, 22);
    }

    public final int getTitleBottomSpacing() {
        return ContextExtensionsKt.getPrefInt(AppCtxKt.getAppCtx(), PreferKeyKt.TITLE_BOTTOM_SPACING, 0);
    }

    public final int getTitleSize() {
        return ContextExtensionsKt.getPrefInt(AppCtxKt.getAppCtx(), PreferKeyKt.TITLE_SIZE, 4);
    }

    public final int getTitleTopSpacing() {
        return ContextExtensionsKt.getPrefInt(AppCtxKt.getAppCtx(), PreferKeyKt.TITLE_TOP_SPACING, 0);
    }

    public final boolean getUseAntiAlias() {
        return useAntiAlias;
    }

    public final boolean getUseZhLayout() {
        return ContextExtensionsKt.getPrefBoolean(AppCtxKt.getAppCtx(), PreferKeyKt.USE_ZH_LAYOUT, true);
    }

    public final boolean getVolumeKeyPage() {
        return ContextExtensionsKt.getPrefBoolean(AppCtxKt.getAppCtx(), PreferKeyKt.VOLUME_KEY_PAGE, true);
    }

    public final boolean isShowNote() {
        return ContextExtensionsKt.getPrefBoolean(AppCtxKt.getAppCtx(), PreferKeyKt.IS_SHOW_NOTE, true);
    }

    public final boolean isShowReview() {
        return ContextExtensionsKt.getPrefBoolean(AppCtxKt.getAppCtx(), PreferKeyKt.IS_SHOW_REVIEW, true);
    }

    public final void setBookIsUnlock(boolean z2) {
        ContextExtensionsKt.putPrefBoolean(AppCtxKt.getAppCtx(), PreferKeyKt.BOOK_IS_UNLOCK, z2);
    }

    public final void setColorMode(int i2) {
        ContextExtensionsKt.putPrefInt(AppCtxKt.getAppCtx(), PreferKeyKt.COLOR_MODE, i2);
    }

    public final void setColorModePre(int i2) {
        ContextExtensionsKt.putPrefInt(AppCtxKt.getAppCtx(), PreferKeyKt.COLOR_MODE_PRE, i2);
    }

    public final void setDrawLineColor(int i2) {
        ContextExtensionsKt.putPrefInt(AppCtxKt.getAppCtx(), PreferKeyKt.DRAW_LINE_COLOR, i2);
    }

    public final void setEnableReview(boolean z2) {
        ContextExtensionsKt.putPrefBoolean(AppCtxKt.getAppCtx(), PreferKeyKt.ENABLE_REVIEW, z2);
    }

    public final void setFirstPage(boolean z2) {
        ContextExtensionsKt.putPrefBoolean(AppCtxKt.getAppCtx(), PreferKeyKt.CURRENT_FIRST_PAGE, z2);
        Log.INSTANCE.logD("firstChapterFirstPage", String.valueOf(z2));
    }

    public final void setFirstPageListenerCallBack(@NotNull Function1<? super Boolean, Unit> action) {
        Intrinsics.checkNotNullParameter(action, "action");
        firstPageListener = action;
    }

    public final void setHideStatusBar(boolean z2) {
        ContextExtensionsKt.putPrefBoolean(AppCtxKt.getAppCtx(), PreferKeyKt.HIDE_STATUS_BAR, z2);
    }

    public final void setKeepLight(int i2) {
        ContextExtensionsKt.putPrefInt(AppCtxKt.getAppCtx(), PreferKeyKt.KEEP_LIGHT, i2);
    }

    public final void setLastPage(boolean z2) {
        ContextExtensionsKt.putPrefBoolean(AppCtxKt.getAppCtx(), PreferKeyKt.CURRENT_LAST_PAGE, z2);
    }

    public final void setLetterSpacing(int i2) {
        ContextExtensionsKt.putPrefInt(AppCtxKt.getAppCtx(), PreferKeyKt.LETTER_SPACING, i2);
    }

    public final void setLineSpacingExtra(int i2) {
        ContextExtensionsKt.putPrefInt(AppCtxKt.getAppCtx(), PreferKeyKt.LINE_SPACING_EXTRA, i2);
    }

    public final void setPaddingBottom(int i2) {
        ContextExtensionsKt.putPrefInt(AppCtxKt.getAppCtx(), PreferKeyKt.CONTENT_PADDING_BOTTOM, i2);
    }

    public final void setPaddingLeft(int i2) {
        ContextExtensionsKt.putPrefInt(AppCtxKt.getAppCtx(), PreferKeyKt.CONTENT_PADDING_LEFT, i2);
    }

    public final void setPaddingRight(int i2) {
        ContextExtensionsKt.putPrefInt(AppCtxKt.getAppCtx(), PreferKeyKt.CONTENT_PADDING_RIGHT, i2);
    }

    public final void setPaddingTop(int i2) {
        ContextExtensionsKt.putPrefInt(AppCtxKt.getAppCtx(), PreferKeyKt.CONTENT_PADDING_TOP, i2);
    }

    public final void setPageAnim(int i2) {
        ContextExtensionsKt.putPrefInt(AppCtxKt.getAppCtx(), PreferKeyKt.PAGE_ANIM, i2);
    }

    public final void setParagraphIndent(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        paragraphIndent = str;
    }

    public final void setParagraphSpacing(int i2) {
        ContextExtensionsKt.putPrefInt(AppCtxKt.getAppCtx(), PreferKeyKt.PARAGRAPH_SPACING, i2);
    }

    public final void setPreviewImageByClick(boolean z2) {
        ContextExtensionsKt.putPrefBoolean(AppCtxKt.getAppCtx(), PreferKeyKt.PREVIEW_IMAGE_BY_CLICK, z2);
    }

    public final void setRestRemind(int i2) {
        ContextExtensionsKt.putPrefInt(AppCtxKt.getAppCtx(), PreferKeyKt.REST_REMIND, i2);
    }

    public final void setSelectTextPosition(int i2) {
        selectTextPosition = i2;
    }

    public final void setShowBattery(boolean z2) {
        ContextExtensionsKt.putPrefBoolean(AppCtxKt.getAppCtx(), PreferKeyKt.SHOW_BATTERY, z2);
    }

    public final void setShowFeiYe(boolean z2) {
        ContextExtensionsKt.putPrefBoolean(AppCtxKt.getAppCtx(), PreferKeyKt.FEIYE_TAG, z2);
    }

    public final void setShowFirstPage(boolean z2) {
        ContextExtensionsKt.putPrefBoolean(AppCtxKt.getAppCtx(), PreferKeyKt.SHOW_FIRST_PAGE, z2);
    }

    public final void setShowNote(boolean z2) {
        ContextExtensionsKt.putPrefBoolean(AppCtxKt.getAppCtx(), PreferKeyKt.IS_SHOW_NOTE, z2);
    }

    public final void setShowReview(boolean z2) {
        ContextExtensionsKt.putPrefBoolean(AppCtxKt.getAppCtx(), PreferKeyKt.IS_SHOW_REVIEW, z2);
    }

    public final void setShowTime(boolean z2) {
        ContextExtensionsKt.putPrefBoolean(AppCtxKt.getAppCtx(), "show_time", z2);
    }

    public final void setSystemTypefaces(int i2) {
        ContextExtensionsKt.putPrefInt(AppCtxKt.getAppCtx(), PreferKeyKt.SYSTEM_TYPEFACES, i2);
    }

    public final void setTextBold(int i2) {
        ContextExtensionsKt.putPrefInt(AppCtxKt.getAppCtx(), PreferKeyKt.TEXT_BOLD, i2);
    }

    public final void setTextFont(@NotNull String value) {
        Intrinsics.checkNotNullParameter(value, "value");
        ContextExtensionsKt.putPrefString(AppCtxKt.getAppCtx(), PreferKeyKt.TEXT_FONT, value);
    }

    public final void setTextSize(int i2) {
        ContextExtensionsKt.putPrefInt(AppCtxKt.getAppCtx(), PreferKeyKt.TEXT_SIZE, i2);
    }

    public final void setTitleBottomSpacing(int i2) {
        ContextExtensionsKt.putPrefInt(AppCtxKt.getAppCtx(), PreferKeyKt.TITLE_BOTTOM_SPACING, i2);
    }

    public final void setTitleSize(int i2) {
        ContextExtensionsKt.putPrefInt(AppCtxKt.getAppCtx(), PreferKeyKt.TITLE_SIZE, i2);
    }

    public final void setTitleTopSpacing(int i2) {
        ContextExtensionsKt.putPrefInt(AppCtxKt.getAppCtx(), PreferKeyKt.TITLE_TOP_SPACING, i2);
    }

    public final void setUseAntiAlias(boolean z2) {
        useAntiAlias = z2;
    }

    public final void setUseZhLayout(boolean z2) {
        ContextExtensionsKt.putPrefBoolean(AppCtxKt.getAppCtx(), PreferKeyKt.USE_ZH_LAYOUT, z2);
    }

    public final void setVolumeKeyPage(boolean z2) {
        ContextExtensionsKt.putPrefBoolean(AppCtxKt.getAppCtx(), PreferKeyKt.VOLUME_KEY_PAGE, z2);
    }
}
