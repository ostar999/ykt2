package com.ykb.ebook.weight;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import com.aliyun.vod.log.core.AliyunLogCommon;
import com.google.android.exoplayer2.text.ttml.TtmlNode;
import com.ruffian.library.widget.RFrameLayout;
import com.ruffian.library.widget.RRadioButton;
import com.ruffian.library.widget.RTextView;
import com.ykb.ebook.common.ReadConfig;
import com.ykb.ebook.databinding.PopupActionMenuBinding;
import com.ykb.ebook.extensions.ViewExtensionsKt;
import com.ykb.ebook.util.Log;
import com.ykb.ebook.util.ScreenUtil;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u00002\u00020\u00012\u00020\u0002:\u0001)B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u0006\u0010\u0013\u001a\u00020\u0014J\b\u0010\u0015\u001a\u00020\u0014H\u0016J\u0006\u0010\u0016\u001a\u00020\u0014J\u0012\u0010\u0017\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u000b0\u0018J2\u0010\u0019\u001a\u00020\u00142\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u001a\u001a\u00020\u000b2\u0006\u0010\u001b\u001a\u00020\u000b2\b\b\u0002\u0010\u0010\u001a\u00020\r2\b\b\u0002\u0010\u000f\u001a\u00020\rJ\u0018\u0010\u001c\u001a\u00020\u00142\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020\rH\u0016J>\u0010 \u001a\u00020\u00142\u0006\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020\u000b2\u0006\u0010$\u001a\u00020\u000b2\u0006\u0010%\u001a\u00020\u000b2\u0006\u0010&\u001a\u00020\u000b2\u0006\u0010'\u001a\u00020\u000b2\u0006\u0010(\u001a\u00020\u000bR\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u000bX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006*"}, d2 = {"Lcom/ykb/ebook/weight/TextActionMenu;", "Landroid/widget/PopupWindow;", "Landroid/widget/CompoundButton$OnCheckedChangeListener;", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", com.alipay.sdk.authjs.a.f3170c, "Lcom/ykb/ebook/weight/TextActionMenu$Callback;", "(Landroid/content/Context;Lcom/ykb/ebook/weight/TextActionMenu$Callback;)V", "binding", "Lcom/ykb/ebook/databinding/PopupActionMenuBinding;", "drawLineColor", "", "drawOrCancel", "", "drawType", AliyunLogCommon.SubModule.EDIT, "hasNote", "xOffset", "yOffset", "addDrawSuccess", "", "dismiss", "editLineSuccess", "getOffset", "Lkotlin/Pair;", "initCheck", "color", TtmlNode.TAG_STYLE, "onCheckedChanged", "button", "Landroid/widget/CompoundButton;", "isCheck", "show", "view", "Landroid/view/View;", "windowHeight", "startX", "startTopY", "startBottomY", "endX", "endBottomY", "Callback", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes8.dex */
public final class TextActionMenu extends PopupWindow implements CompoundButton.OnCheckedChangeListener {

    @NotNull
    private final PopupActionMenuBinding binding;

    @NotNull
    private final Callback callback;

    @NotNull
    private final Context context;
    private int drawLineColor;
    private boolean drawOrCancel;
    private int drawType;
    private boolean edit;
    private boolean hasNote;
    private int xOffset;
    private int yOffset;

    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0007\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0003H&J(\u0010\t\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u00032\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\u000bH&J\u0010\u0010\u000e\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0003H&J\u0010\u0010\u000f\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0003H&J\u0010\u0010\u0010\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0003H&J\u0010\u0010\u0011\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0003H&R\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005¨\u0006\u0012"}, d2 = {"Lcom/ykb/ebook/weight/TextActionMenu$Callback;", "", "selectedText", "", "getSelectedText", "()Ljava/lang/String;", "onCommentClick", "", "text", "onDrawLineBack", "color", "", "drawType", "operator", "onErrorClick", "onNoteClick", "onShareClick", "sendToClip", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public interface Callback {
        @NotNull
        String getSelectedText();

        void onCommentClick(@NotNull String text);

        void onDrawLineBack(@NotNull String text, int color, int drawType, int operator);

        void onErrorClick(@NotNull String text);

        void onNoteClick(@NotNull String text);

        void onShareClick(@NotNull String text);

        void sendToClip(@NotNull String text);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TextActionMenu(@NotNull Context context, @NotNull Callback callback) {
        super(-2, -2);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(callback, "callback");
        this.context = context;
        this.callback = callback;
        PopupActionMenuBinding popupActionMenuBindingInflate = PopupActionMenuBinding.inflate(LayoutInflater.from(context));
        Intrinsics.checkNotNullExpressionValue(popupActionMenuBindingInflate, "inflate(LayoutInflater.from(context))");
        this.binding = popupActionMenuBindingInflate;
        setContentView(popupActionMenuBindingInflate.getRoot());
        setBackgroundDrawable(new ColorDrawable(0));
        setTouchable(true);
        setOutsideTouchable(false);
        setFocusable(false);
        RFrameLayout rFrameLayout = popupActionMenuBindingInflate.flTextBg;
        Intrinsics.checkNotNullExpressionValue(rFrameLayout, "binding.flTextBg");
        ViewExtensionsKt.clickDelay(rFrameLayout, new Function0<Unit>() { // from class: com.ykb.ebook.weight.TextActionMenu.1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public /* bridge */ /* synthetic */ Unit invoke() {
                invoke2();
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2() {
                TextActionMenu.this.drawType = 1;
                TextActionMenu.this.callback.onDrawLineBack(TextActionMenu.this.callback.getSelectedText(), TextActionMenu.this.drawLineColor, TextActionMenu.this.drawType, TextActionMenu.this.drawOrCancel ? 0 : 2);
            }
        });
        RFrameLayout rFrameLayout2 = popupActionMenuBindingInflate.flDrawLine;
        Intrinsics.checkNotNullExpressionValue(rFrameLayout2, "binding.flDrawLine");
        ViewExtensionsKt.clickDelay(rFrameLayout2, new Function0<Unit>() { // from class: com.ykb.ebook.weight.TextActionMenu.2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public /* bridge */ /* synthetic */ Unit invoke() {
                invoke2();
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2() {
                TextActionMenu.this.drawType = 0;
                TextActionMenu.this.callback.onDrawLineBack(TextActionMenu.this.callback.getSelectedText(), TextActionMenu.this.drawLineColor, TextActionMenu.this.drawType, TextActionMenu.this.drawOrCancel ? 0 : 2);
            }
        });
        RTextView rTextView = popupActionMenuBindingInflate.tvNotes;
        Intrinsics.checkNotNullExpressionValue(rTextView, "binding.tvNotes");
        ViewExtensionsKt.clickDelay(rTextView, new Function0<Unit>() { // from class: com.ykb.ebook.weight.TextActionMenu.3
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public /* bridge */ /* synthetic */ Unit invoke() {
                invoke2();
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2() {
                TextActionMenu.this.callback.onNoteClick(TextActionMenu.this.callback.getSelectedText());
            }
        });
        RTextView rTextView2 = popupActionMenuBindingInflate.tvComment;
        Intrinsics.checkNotNullExpressionValue(rTextView2, "binding.tvComment");
        ViewExtensionsKt.clickDelay(rTextView2, new Function0<Unit>() { // from class: com.ykb.ebook.weight.TextActionMenu.4
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public /* bridge */ /* synthetic */ Unit invoke() {
                invoke2();
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2() {
                TextActionMenu.this.callback.onCommentClick(TextActionMenu.this.callback.getSelectedText());
            }
        });
        RTextView rTextView3 = popupActionMenuBindingInflate.tvCopy;
        Intrinsics.checkNotNullExpressionValue(rTextView3, "binding.tvCopy");
        ViewExtensionsKt.clickDelay(rTextView3, new Function0<Unit>() { // from class: com.ykb.ebook.weight.TextActionMenu.5
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public /* bridge */ /* synthetic */ Unit invoke() {
                invoke2();
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2() {
                TextActionMenu.this.callback.sendToClip(TextActionMenu.this.callback.getSelectedText());
            }
        });
        RTextView rTextView4 = popupActionMenuBindingInflate.tvShare;
        Intrinsics.checkNotNullExpressionValue(rTextView4, "binding.tvShare");
        ViewExtensionsKt.clickDelay(rTextView4, new Function0<Unit>() { // from class: com.ykb.ebook.weight.TextActionMenu.6
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public /* bridge */ /* synthetic */ Unit invoke() {
                invoke2();
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2() {
                TextActionMenu.this.callback.onShareClick(TextActionMenu.this.callback.getSelectedText());
            }
        });
        RTextView rTextView5 = popupActionMenuBindingInflate.tvError;
        Intrinsics.checkNotNullExpressionValue(rTextView5, "binding.tvError");
        ViewExtensionsKt.clickDelay(rTextView5, new Function0<Unit>() { // from class: com.ykb.ebook.weight.TextActionMenu.7
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public /* bridge */ /* synthetic */ Unit invoke() {
                invoke2();
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2() {
                TextActionMenu.this.callback.onErrorClick(TextActionMenu.this.callback.getSelectedText());
            }
        });
    }

    public final void addDrawSuccess() {
        this.drawOrCancel = false;
        this.binding.cbDrawLine.setText("取消");
        this.binding.cbDrawLine.setOnCheckedChangeListener(null);
        this.binding.cbDrawLine.setChecked(true);
        this.binding.cbDrawLine.setOnCheckedChangeListener(this);
    }

    @Override // android.widget.PopupWindow
    public void dismiss() {
        super.dismiss();
        RelativeLayout root = this.binding.getRoot();
        Intrinsics.checkNotNullExpressionValue(root, "binding.root");
        ViewExtensionsKt.gone(root);
    }

    public final void editLineSuccess() {
        this.drawOrCancel = false;
        this.binding.cbDrawLine.setText("取消");
        this.binding.cbDrawLine.setOnCheckedChangeListener(null);
        this.binding.cbDrawLine.setChecked(true);
        this.binding.cbDrawLine.setOnCheckedChangeListener(this);
    }

    @NotNull
    public final Pair<Integer, Integer> getOffset() {
        return new Pair<>(Integer.valueOf(this.xOffset), Integer.valueOf(this.yOffset));
    }

    public final void initCheck(boolean drawOrCancel, int color, int style, boolean hasNote, boolean edit) {
        this.drawOrCancel = drawOrCancel;
        Log.INSTANCE.logD("initCheck", "drawOrCancel = " + drawOrCancel + ", hasNote = " + hasNote + ", edit = " + edit);
        this.hasNote = hasNote;
        this.edit = edit;
        if (!drawOrCancel && !hasNote) {
            FrameLayout frameLayout = this.binding.flDrawLineMenu;
            Intrinsics.checkNotNullExpressionValue(frameLayout, "binding.flDrawLineMenu");
            ViewExtensionsKt.visible(frameLayout);
            this.binding.cbDrawLine.setText("取消");
            this.binding.cbDrawLine.setChecked(true);
        } else if (drawOrCancel && !hasNote) {
            FrameLayout frameLayout2 = this.binding.flDrawLineMenu;
            Intrinsics.checkNotNullExpressionValue(frameLayout2, "binding.flDrawLineMenu");
            ViewExtensionsKt.gone(frameLayout2);
            this.binding.cbDrawLine.setText("划线");
            this.binding.cbDrawLine.setChecked(false);
        } else if (!drawOrCancel && hasNote) {
            FrameLayout frameLayout3 = this.binding.flDrawLineMenu;
            Intrinsics.checkNotNullExpressionValue(frameLayout3, "binding.flDrawLineMenu");
            ViewExtensionsKt.gone(frameLayout3);
            this.binding.cbDrawLine.setText("划线");
            this.binding.cbDrawLine.setChecked(false);
        }
        this.binding.cbDrawLine.setOnCheckedChangeListener(this);
        if (style < 0) {
            style = 0;
        }
        this.drawType = style;
        if (color < 0) {
            color = 0;
        }
        this.drawLineColor = color;
        if (color == 0) {
            this.binding.cbColorOne.setChecked(true);
        } else if (color == 1) {
            this.binding.cbColorTwo.setChecked(true);
        } else if (color == 2) {
            this.binding.cbColorThree.setChecked(true);
        } else if (color == 3) {
            this.binding.cbColorFour.setChecked(true);
        } else if (color == 4) {
            this.binding.cbColorFive.setChecked(true);
        }
        this.binding.cbColorOne.setOnCheckedChangeListener(this);
        this.binding.cbColorTwo.setOnCheckedChangeListener(this);
        this.binding.cbColorThree.setOnCheckedChangeListener(this);
        this.binding.cbColorFour.setOnCheckedChangeListener(this);
        this.binding.cbColorFive.setOnCheckedChangeListener(this);
    }

    @Override // android.widget.CompoundButton.OnCheckedChangeListener
    public void onCheckedChanged(@NotNull CompoundButton button, boolean isCheck) {
        Intrinsics.checkNotNullParameter(button, "button");
        if (Intrinsics.areEqual(button, this.binding.cbDrawLine)) {
            Log.INSTANCE.logD("isCheck", String.valueOf(isCheck));
            if (isCheck) {
                FrameLayout frameLayout = this.binding.flDrawLineMenu;
                Intrinsics.checkNotNullExpressionValue(frameLayout, "binding.flDrawLineMenu");
                ViewExtensionsKt.visible(frameLayout);
                button.setText("取消");
                Callback callback = this.callback;
                callback.onDrawLineBack(callback.getSelectedText(), this.drawLineColor, this.drawType, 0);
                return;
            }
            FrameLayout frameLayout2 = this.binding.flDrawLineMenu;
            Intrinsics.checkNotNullExpressionValue(frameLayout2, "binding.flDrawLineMenu");
            ViewExtensionsKt.gone(frameLayout2);
            button.setText("划线");
            Callback callback2 = this.callback;
            callback2.onDrawLineBack(callback2.getSelectedText(), this.drawLineColor, this.drawType, 1);
            return;
        }
        if (isCheck) {
            if (Intrinsics.areEqual(button, this.binding.cbColorOne)) {
                this.drawLineColor = 0;
            } else if (Intrinsics.areEqual(button, this.binding.cbColorTwo)) {
                this.drawLineColor = 1;
            } else if (Intrinsics.areEqual(button, this.binding.cbColorThree)) {
                this.drawLineColor = 2;
            } else if (Intrinsics.areEqual(button, this.binding.cbColorFour)) {
                this.drawLineColor = 3;
            } else if (Intrinsics.areEqual(button, this.binding.cbColorFive)) {
                this.drawLineColor = 4;
            }
            if (button instanceof RRadioButton) {
                ReadConfig.INSTANCE.setDrawLineColor(this.drawLineColor);
            }
            Callback callback3 = this.callback;
            callback3.onDrawLineBack(callback3.getSelectedText(), this.drawLineColor, this.drawType, this.drawOrCancel ? 0 : 2);
        }
    }

    public final void show(@NotNull View view, int windowHeight, int startX, int startTopY, int startBottomY, int endX, int endBottomY) {
        Intrinsics.checkNotNullParameter(view, "view");
        ViewGroup.LayoutParams layoutParams = this.binding.llMenu.getLayoutParams();
        Intrinsics.checkNotNull(layoutParams, "null cannot be cast to non-null type android.widget.RelativeLayout.LayoutParams");
        RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) layoutParams;
        layoutParams2.topMargin = 0;
        if (startTopY > 500) {
            ImageView imageView = this.binding.ivDown;
            Intrinsics.checkNotNullExpressionValue(imageView, "binding.ivDown");
            ViewExtensionsKt.visible(imageView);
            ImageView imageView2 = this.binding.ivUp;
            Intrinsics.checkNotNullExpressionValue(imageView2, "binding.ivUp");
            ViewExtensionsKt.gone(imageView2);
            int i2 = windowHeight - startTopY;
            showAtLocation(view, 81, 0, i2);
            this.yOffset = i2;
        } else if (endBottomY - startBottomY > 500) {
            ImageView imageView3 = this.binding.ivDown;
            Intrinsics.checkNotNullExpressionValue(imageView3, "binding.ivDown");
            ViewExtensionsKt.visible(imageView3);
            ImageView imageView4 = this.binding.ivUp;
            Intrinsics.checkNotNullExpressionValue(imageView4, "binding.ivUp");
            ViewExtensionsKt.gone(imageView4);
            showAtLocation(view, 49, 0, startBottomY);
            this.yOffset = startBottomY;
        } else {
            ImageView imageView5 = this.binding.ivDown;
            Intrinsics.checkNotNullExpressionValue(imageView5, "binding.ivDown");
            ViewExtensionsKt.gone(imageView5);
            ImageView imageView6 = this.binding.ivUp;
            Intrinsics.checkNotNullExpressionValue(imageView6, "binding.ivUp");
            ViewExtensionsKt.visible(imageView6);
            layoutParams2.topMargin = ScreenUtil.getPxByDp(this.context, 6);
            showAtLocation(view, 49, 0, endBottomY);
            this.yOffset = endBottomY;
        }
        this.binding.llMenu.setLayoutParams(layoutParams2);
    }
}
