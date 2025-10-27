package com.ykb.ebook.weight;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import com.google.android.exoplayer2.text.ttml.TtmlNode;
import com.lxj.xpopup.core.AttachPopupView;
import com.ruffian.library.widget.RCheckBox;
import com.ruffian.library.widget.RRadioButton;
import com.ykb.ebook.R;
import com.ykb.ebook.common.ReadConfig;
import com.ykb.ebook.extensions.ViewExtensionsKt;
import com.ykb.ebook.weight.TextActionMenu;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0007\u0018\u00002\u00020\u00012\u00020\u0002:\u0001$B7\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u0006\u0010\u000b\u001a\u00020\n\u0012\b\b\u0002\u0010\f\u001a\u00020\b¢\u0006\u0002\u0010\rJ\b\u0010\u001d\u001a\u00020\nH\u0014J\u001a\u0010\u001e\u001a\u00020\u001f2\b\u0010 \u001a\u0004\u0018\u00010!2\u0006\u0010\"\u001a\u00020\bH\u0016J\b\u0010#\u001a\u00020\u001fH\u0014R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u000fX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u000fX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u000fX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u000fX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082.¢\u0006\u0002\n\u0000R\u0011\u0010\t\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u000e\u0010\u0018\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u001bX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u000b\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0017¨\u0006%"}, d2 = {"Lcom/ykb/ebook/weight/FunctionMenuPop;", "Lcom/lxj/xpopup/core/AttachPopupView;", "Landroid/widget/CompoundButton$OnCheckedChangeListener;", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", com.alipay.sdk.authjs.a.f3170c, "Lcom/ykb/ebook/weight/TextActionMenu$Callback;", "drawOrCancel", "", "color", "", TtmlNode.TAG_STYLE, "hasNote", "(Landroid/content/Context;Lcom/ykb/ebook/weight/TextActionMenu$Callback;ZIIZ)V", "cbColorFive", "Lcom/ruffian/library/widget/RRadioButton;", "cbColorFour", "cbColorOne", "cbColorThree", "cbColorTwo", "cbDrawLine", "Lcom/ruffian/library/widget/RCheckBox;", "getColor", "()I", "drawLineColor", "drawType", "flDrawLineMenu", "Landroid/widget/FrameLayout;", "getStyle", "getImplLayoutId", "onCheckedChanged", "", "button", "Landroid/widget/CompoundButton;", "isChecked", "onCreate", "Callback", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SuppressLint({"ViewConstructor"})
/* loaded from: classes8.dex */
public final class FunctionMenuPop extends AttachPopupView implements CompoundButton.OnCheckedChangeListener {

    @NotNull
    private final TextActionMenu.Callback callback;
    private RRadioButton cbColorFive;
    private RRadioButton cbColorFour;
    private RRadioButton cbColorOne;
    private RRadioButton cbColorThree;
    private RRadioButton cbColorTwo;
    private RCheckBox cbDrawLine;
    private final int color;
    private int drawLineColor;
    private final boolean drawOrCancel;
    private int drawType;
    private FrameLayout flDrawLineMenu;
    private final boolean hasNote;
    private final int style;

    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0006\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0003H&J(\u0010\t\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u00032\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\u000bH&J\u0010\u0010\u000e\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0003H&J\u0010\u0010\u000f\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0003H&J\u0010\u0010\u0010\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0003H&R\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005¨\u0006\u0011"}, d2 = {"Lcom/ykb/ebook/weight/FunctionMenuPop$Callback;", "", "selectedText", "", "getSelectedText", "()Ljava/lang/String;", "onCommentClick", "", "text", "onDrawLineBack", "color", "", "drawType", "operator", "onErrorClick", "onNoteClick", "onShareClick", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public interface Callback {
        @NotNull
        String getSelectedText();

        void onCommentClick(@NotNull String text);

        void onDrawLineBack(@NotNull String text, int color, int drawType, int operator);

        void onErrorClick(@NotNull String text);

        void onNoteClick(@NotNull String text);

        void onShareClick(@NotNull String text);
    }

    public /* synthetic */ FunctionMenuPop(Context context, TextActionMenu.Callback callback, boolean z2, int i2, int i3, boolean z3, int i4, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, callback, z2, i2, i3, (i4 & 32) != 0 ? false : z3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$0(FunctionMenuPop this$0, CompoundButton compoundButton, boolean z2) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        FrameLayout frameLayout = null;
        if (z2) {
            FrameLayout frameLayout2 = this$0.flDrawLineMenu;
            if (frameLayout2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("flDrawLineMenu");
            } else {
                frameLayout = frameLayout2;
            }
            ViewExtensionsKt.visible(frameLayout);
            compoundButton.setText("取消");
            TextActionMenu.Callback callback = this$0.callback;
            callback.onDrawLineBack(callback.getSelectedText(), this$0.drawLineColor, this$0.drawType, 0);
            return;
        }
        FrameLayout frameLayout3 = this$0.flDrawLineMenu;
        if (frameLayout3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("flDrawLineMenu");
        } else {
            frameLayout = frameLayout3;
        }
        ViewExtensionsKt.gone(frameLayout);
        compoundButton.setText("划线");
        TextActionMenu.Callback callback2 = this$0.callback;
        callback2.onDrawLineBack(callback2.getSelectedText(), this$0.drawLineColor, this$0.drawType, 1);
    }

    public final int getColor() {
        return this.color;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return R.layout.popup_action_menu;
    }

    public final int getStyle() {
        return this.style;
    }

    @Override // android.widget.CompoundButton.OnCheckedChangeListener
    public void onCheckedChanged(@Nullable CompoundButton button, boolean isChecked) {
        if (isChecked) {
            RRadioButton rRadioButton = this.cbColorOne;
            RRadioButton rRadioButton2 = null;
            if (rRadioButton == null) {
                Intrinsics.throwUninitializedPropertyAccessException("cbColorOne");
                rRadioButton = null;
            }
            if (Intrinsics.areEqual(button, rRadioButton)) {
                this.drawLineColor = 0;
            } else {
                RRadioButton rRadioButton3 = this.cbColorTwo;
                if (rRadioButton3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("cbColorTwo");
                    rRadioButton3 = null;
                }
                if (Intrinsics.areEqual(button, rRadioButton3)) {
                    this.drawLineColor = 1;
                } else {
                    RRadioButton rRadioButton4 = this.cbColorThree;
                    if (rRadioButton4 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("cbColorThree");
                        rRadioButton4 = null;
                    }
                    if (Intrinsics.areEqual(button, rRadioButton4)) {
                        this.drawLineColor = 2;
                    } else {
                        RRadioButton rRadioButton5 = this.cbColorFour;
                        if (rRadioButton5 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("cbColorFour");
                            rRadioButton5 = null;
                        }
                        if (Intrinsics.areEqual(button, rRadioButton5)) {
                            this.drawLineColor = 3;
                        } else {
                            RRadioButton rRadioButton6 = this.cbColorFive;
                            if (rRadioButton6 == null) {
                                Intrinsics.throwUninitializedPropertyAccessException("cbColorFive");
                            } else {
                                rRadioButton2 = rRadioButton6;
                            }
                            if (Intrinsics.areEqual(button, rRadioButton2)) {
                                this.drawLineColor = 4;
                            }
                        }
                    }
                }
            }
            if (button instanceof RRadioButton) {
                ReadConfig.INSTANCE.setDrawLineColor(this.drawLineColor);
            }
            TextActionMenu.Callback callback = this.callback;
            callback.onDrawLineBack(callback.getSelectedText(), this.drawLineColor, this.drawType, 2);
        }
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() {
        super.onCreate();
        View viewFindViewById = findViewById(R.id.cb_color_one);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "findViewById(R.id.cb_color_one)");
        this.cbColorOne = (RRadioButton) viewFindViewById;
        View viewFindViewById2 = findViewById(R.id.cb_color_two);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "findViewById(R.id.cb_color_two)");
        this.cbColorTwo = (RRadioButton) viewFindViewById2;
        View viewFindViewById3 = findViewById(R.id.cb_color_three);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById3, "findViewById(R.id.cb_color_three)");
        this.cbColorThree = (RRadioButton) viewFindViewById3;
        View viewFindViewById4 = findViewById(R.id.cb_color_four);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById4, "findViewById(R.id.cb_color_four)");
        this.cbColorFour = (RRadioButton) viewFindViewById4;
        View viewFindViewById5 = findViewById(R.id.cb_color_five);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById5, "findViewById(R.id.cb_color_five)");
        this.cbColorFive = (RRadioButton) viewFindViewById5;
        View viewFindViewById6 = findViewById(R.id.cb_draw_line);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById6, "findViewById(R.id.cb_draw_line)");
        this.cbDrawLine = (RCheckBox) viewFindViewById6;
        View viewFindViewById7 = findViewById(R.id.fl_draw_line_menu);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById7, "findViewById(R.id.fl_draw_line_menu)");
        this.flDrawLineMenu = (FrameLayout) viewFindViewById7;
        View viewFindViewById8 = findViewById(R.id.fl_draw_line);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById8, "findViewById<View>(R.id.fl_draw_line)");
        ViewExtensionsKt.clickDelay(viewFindViewById8, new Function0<Unit>() { // from class: com.ykb.ebook.weight.FunctionMenuPop.onCreate.1
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
                FunctionMenuPop.this.drawType = 0;
                FunctionMenuPop.this.callback.onDrawLineBack(FunctionMenuPop.this.callback.getSelectedText(), FunctionMenuPop.this.drawLineColor, FunctionMenuPop.this.drawType, 2);
            }
        });
        View viewFindViewById9 = findViewById(R.id.tv_notes);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById9, "findViewById<View>(R.id.tv_notes)");
        ViewExtensionsKt.clickDelay(viewFindViewById9, new Function0<Unit>() { // from class: com.ykb.ebook.weight.FunctionMenuPop.onCreate.2
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
                FunctionMenuPop.this.callback.onNoteClick(FunctionMenuPop.this.callback.getSelectedText());
            }
        });
        View viewFindViewById10 = findViewById(R.id.tv_comment);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById10, "findViewById<View>(R.id.tv_comment)");
        ViewExtensionsKt.clickDelay(viewFindViewById10, new Function0<Unit>() { // from class: com.ykb.ebook.weight.FunctionMenuPop.onCreate.3
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
                FunctionMenuPop.this.callback.onCommentClick(FunctionMenuPop.this.callback.getSelectedText());
            }
        });
        View viewFindViewById11 = findViewById(R.id.tv_copy);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById11, "findViewById<View>(R.id.tv_copy)");
        ViewExtensionsKt.clickDelay(viewFindViewById11, new Function0<Unit>() { // from class: com.ykb.ebook.weight.FunctionMenuPop.onCreate.4
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
                FunctionMenuPop.this.callback.sendToClip(FunctionMenuPop.this.callback.getSelectedText());
            }
        });
        View viewFindViewById12 = findViewById(R.id.tv_share);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById12, "findViewById<View>(R.id.tv_share)");
        ViewExtensionsKt.clickDelay(viewFindViewById12, new Function0<Unit>() { // from class: com.ykb.ebook.weight.FunctionMenuPop.onCreate.5
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
                FunctionMenuPop.this.callback.onShareClick(FunctionMenuPop.this.callback.getSelectedText());
            }
        });
        View viewFindViewById13 = findViewById(R.id.tv_error);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById13, "findViewById<View>(R.id.tv_error)");
        ViewExtensionsKt.clickDelay(viewFindViewById13, new Function0<Unit>() { // from class: com.ykb.ebook.weight.FunctionMenuPop.onCreate.6
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
                FunctionMenuPop.this.callback.onErrorClick(FunctionMenuPop.this.callback.getSelectedText());
            }
        });
        RCheckBox rCheckBox = this.cbDrawLine;
        RRadioButton rRadioButton = null;
        if (rCheckBox == null) {
            Intrinsics.throwUninitializedPropertyAccessException("cbDrawLine");
            rCheckBox = null;
        }
        rCheckBox.setChecked(!this.drawOrCancel);
        RCheckBox rCheckBox2 = this.cbDrawLine;
        if (rCheckBox2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("cbDrawLine");
            rCheckBox2 = null;
        }
        rCheckBox2.setText((!this.drawOrCancel || this.hasNote) ? "取消" : "划线");
        if (this.hasNote) {
            FrameLayout frameLayout = this.flDrawLineMenu;
            if (frameLayout == null) {
                Intrinsics.throwUninitializedPropertyAccessException("flDrawLineMenu");
                frameLayout = null;
            }
            ViewExtensionsKt.visible(frameLayout);
            RCheckBox rCheckBox3 = this.cbDrawLine;
            if (rCheckBox3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("cbDrawLine");
                rCheckBox3 = null;
            }
            rCheckBox3.setText("取消");
            RCheckBox rCheckBox4 = this.cbDrawLine;
            if (rCheckBox4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("cbDrawLine");
                rCheckBox4 = null;
            }
            rCheckBox4.setChecked(false);
        } else if (this.drawOrCancel) {
            FrameLayout frameLayout2 = this.flDrawLineMenu;
            if (frameLayout2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("flDrawLineMenu");
                frameLayout2 = null;
            }
            ViewExtensionsKt.gone(frameLayout2);
        } else {
            FrameLayout frameLayout3 = this.flDrawLineMenu;
            if (frameLayout3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("flDrawLineMenu");
                frameLayout3 = null;
            }
            ViewExtensionsKt.visible(frameLayout3);
        }
        RCheckBox rCheckBox5 = this.cbDrawLine;
        if (rCheckBox5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("cbDrawLine");
            rCheckBox5 = null;
        }
        rCheckBox5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.ykb.ebook.weight.h
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z2) {
                FunctionMenuPop.onCreate$lambda$0(this.f26486c, compoundButton, z2);
            }
        });
        int i2 = this.style;
        if (i2 < 0) {
            i2 = 0;
        }
        this.drawType = i2;
        int i3 = this.color;
        int i4 = i3 >= 0 ? i3 : 0;
        this.drawLineColor = i4;
        if (i4 == 0) {
            RRadioButton rRadioButton2 = this.cbColorOne;
            if (rRadioButton2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("cbColorOne");
                rRadioButton2 = null;
            }
            rRadioButton2.setChecked(true);
        } else if (i4 == 1) {
            RRadioButton rRadioButton3 = this.cbColorTwo;
            if (rRadioButton3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("cbColorTwo");
                rRadioButton3 = null;
            }
            rRadioButton3.setChecked(true);
        } else if (i4 == 2) {
            RRadioButton rRadioButton4 = this.cbColorThree;
            if (rRadioButton4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("cbColorThree");
                rRadioButton4 = null;
            }
            rRadioButton4.setChecked(true);
        } else if (i4 == 3) {
            RRadioButton rRadioButton5 = this.cbColorFour;
            if (rRadioButton5 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("cbColorFour");
                rRadioButton5 = null;
            }
            rRadioButton5.setChecked(true);
        } else if (i4 == 4) {
            RRadioButton rRadioButton6 = this.cbColorFive;
            if (rRadioButton6 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("cbColorFive");
                rRadioButton6 = null;
            }
            rRadioButton6.setChecked(true);
        }
        RRadioButton rRadioButton7 = this.cbColorOne;
        if (rRadioButton7 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("cbColorOne");
            rRadioButton7 = null;
        }
        rRadioButton7.setOnCheckedChangeListener(this);
        RRadioButton rRadioButton8 = this.cbColorTwo;
        if (rRadioButton8 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("cbColorTwo");
            rRadioButton8 = null;
        }
        rRadioButton8.setOnCheckedChangeListener(this);
        RRadioButton rRadioButton9 = this.cbColorThree;
        if (rRadioButton9 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("cbColorThree");
            rRadioButton9 = null;
        }
        rRadioButton9.setOnCheckedChangeListener(this);
        RRadioButton rRadioButton10 = this.cbColorFour;
        if (rRadioButton10 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("cbColorFour");
            rRadioButton10 = null;
        }
        rRadioButton10.setOnCheckedChangeListener(this);
        RRadioButton rRadioButton11 = this.cbColorFive;
        if (rRadioButton11 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("cbColorFive");
        } else {
            rRadioButton = rRadioButton11;
        }
        rRadioButton.setOnCheckedChangeListener(this);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FunctionMenuPop(@NotNull Context context, @NotNull TextActionMenu.Callback callback, boolean z2, int i2, int i3, boolean z3) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(callback, "callback");
        this.callback = callback;
        this.drawOrCancel = z2;
        this.color = i2;
        this.style = i3;
        this.hasNote = z3;
    }
}
