package com.ykb.ebook.weight;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import com.google.android.exoplayer2.text.ttml.TtmlNode;
import com.ruffian.library.widget.RFrameLayout;
import com.ruffian.library.widget.RRadioButton;
import com.ruffian.library.widget.RTextView;
import com.ykb.ebook.common.ReadConfig;
import com.ykb.ebook.databinding.FunctionMenuBinding;
import com.ykb.ebook.extensions.ContextExtensionsKt;
import com.ykb.ebook.extensions.ViewExtensionsKt;
import com.ykb.ebook.weight.TextActionMenu;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u00012\u00020\u0002B#\b\u0007\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ(\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\b2\u0006\u0010\u0015\u001a\u00020\b2\b\b\u0002\u0010\u0016\u001a\u00020\u0013J\u001a\u0010\u0017\u001a\u00020\u00112\b\u0010\u0018\u001a\u0004\u0018\u00010\u00192\u0006\u0010\u001a\u001a\u00020\u0013H\u0016J\u000e\u0010\u001b\u001a\u00020\u00112\u0006\u0010\u001c\u001a\u00020\rR\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\f\u001a\u0004\u0018\u00010\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u001d"}, d2 = {"Lcom/ykb/ebook/weight/FunctionMenu;", "Landroid/widget/FrameLayout;", "Landroid/widget/CompoundButton$OnCheckedChangeListener;", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "attributeSet", "Landroid/util/AttributeSet;", "def", "", "(Landroid/content/Context;Landroid/util/AttributeSet;I)V", "binding", "Lcom/ykb/ebook/databinding/FunctionMenuBinding;", com.alipay.sdk.authjs.a.f3170c, "Lcom/ykb/ebook/weight/TextActionMenu$Callback;", "drawLineColor", "drawType", "initCheck", "", "drawOrCancel", "", "color", TtmlNode.TAG_STYLE, "hasNote", "onCheckedChanged", "button", "Landroid/widget/CompoundButton;", "isCheck", "setCallBack", "cb", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes8.dex */
public final class FunctionMenu extends FrameLayout implements CompoundButton.OnCheckedChangeListener {

    @NotNull
    private final FunctionMenuBinding binding;

    @Nullable
    private TextActionMenu.Callback callback;
    private int drawLineColor;
    private int drawType;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    @JvmOverloads
    public FunctionMenu(@NotNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0, 4, null);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    @JvmOverloads
    public FunctionMenu(@NotNull final Context context, @Nullable AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        Intrinsics.checkNotNullParameter(context, "context");
        FunctionMenuBinding functionMenuBindingInflate = FunctionMenuBinding.inflate(LayoutInflater.from(context));
        Intrinsics.checkNotNullExpressionValue(functionMenuBindingInflate, "inflate(LayoutInflater.from(context))");
        this.binding = functionMenuBindingInflate;
        addView(functionMenuBindingInflate.getRoot());
        RFrameLayout rFrameLayout = functionMenuBindingInflate.flTextBg;
        Intrinsics.checkNotNullExpressionValue(rFrameLayout, "binding.flTextBg");
        ViewExtensionsKt.clickDelay(rFrameLayout, new Function0<Unit>() { // from class: com.ykb.ebook.weight.FunctionMenu.1
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
                String selectedText;
                FunctionMenu.this.drawType = 1;
                TextActionMenu.Callback callback = FunctionMenu.this.callback;
                if (callback != null) {
                    TextActionMenu.Callback callback2 = FunctionMenu.this.callback;
                    if (callback2 == null || (selectedText = callback2.getSelectedText()) == null) {
                        selectedText = "";
                    }
                    callback.onDrawLineBack(selectedText, FunctionMenu.this.drawLineColor, FunctionMenu.this.drawType, 2);
                }
            }
        });
        RFrameLayout rFrameLayout2 = functionMenuBindingInflate.flDrawLine;
        Intrinsics.checkNotNullExpressionValue(rFrameLayout2, "binding.flDrawLine");
        ViewExtensionsKt.clickDelay(rFrameLayout2, new Function0<Unit>() { // from class: com.ykb.ebook.weight.FunctionMenu.2
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
                String selectedText;
                FunctionMenu.this.drawType = 0;
                TextActionMenu.Callback callback = FunctionMenu.this.callback;
                if (callback != null) {
                    TextActionMenu.Callback callback2 = FunctionMenu.this.callback;
                    if (callback2 == null || (selectedText = callback2.getSelectedText()) == null) {
                        selectedText = "";
                    }
                    callback.onDrawLineBack(selectedText, FunctionMenu.this.drawLineColor, FunctionMenu.this.drawType, 2);
                }
            }
        });
        RTextView rTextView = functionMenuBindingInflate.tvNotes;
        Intrinsics.checkNotNullExpressionValue(rTextView, "binding.tvNotes");
        ViewExtensionsKt.clickDelay(rTextView, new Function0<Unit>() { // from class: com.ykb.ebook.weight.FunctionMenu.3
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
                String selectedText;
                TextActionMenu.Callback callback = FunctionMenu.this.callback;
                if (callback != null) {
                    TextActionMenu.Callback callback2 = FunctionMenu.this.callback;
                    if (callback2 == null || (selectedText = callback2.getSelectedText()) == null) {
                        selectedText = "";
                    }
                    callback.onNoteClick(selectedText);
                }
            }
        });
        RTextView rTextView2 = functionMenuBindingInflate.tvComment;
        Intrinsics.checkNotNullExpressionValue(rTextView2, "binding.tvComment");
        ViewExtensionsKt.clickDelay(rTextView2, new Function0<Unit>() { // from class: com.ykb.ebook.weight.FunctionMenu.4
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
                String selectedText;
                TextActionMenu.Callback callback = FunctionMenu.this.callback;
                if (callback != null) {
                    TextActionMenu.Callback callback2 = FunctionMenu.this.callback;
                    if (callback2 == null || (selectedText = callback2.getSelectedText()) == null) {
                        selectedText = "";
                    }
                    callback.onCommentClick(selectedText);
                }
            }
        });
        RTextView rTextView3 = functionMenuBindingInflate.tvCopy;
        Intrinsics.checkNotNullExpressionValue(rTextView3, "binding.tvCopy");
        ViewExtensionsKt.clickDelay(rTextView3, new Function0<Unit>() { // from class: com.ykb.ebook.weight.FunctionMenu.5
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
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
                String selectedText;
                Context context2 = context;
                TextActionMenu.Callback callback = this.callback;
                if (callback == null || (selectedText = callback.getSelectedText()) == null) {
                    selectedText = "";
                }
                ContextExtensionsKt.sendToClip(context2, selectedText);
            }
        });
        RTextView rTextView4 = functionMenuBindingInflate.tvShare;
        Intrinsics.checkNotNullExpressionValue(rTextView4, "binding.tvShare");
        ViewExtensionsKt.clickDelay(rTextView4, new Function0<Unit>() { // from class: com.ykb.ebook.weight.FunctionMenu.6
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
                String selectedText;
                TextActionMenu.Callback callback = FunctionMenu.this.callback;
                if (callback != null) {
                    TextActionMenu.Callback callback2 = FunctionMenu.this.callback;
                    if (callback2 == null || (selectedText = callback2.getSelectedText()) == null) {
                        selectedText = "";
                    }
                    callback.onShareClick(selectedText);
                }
            }
        });
        RTextView rTextView5 = functionMenuBindingInflate.tvError;
        Intrinsics.checkNotNullExpressionValue(rTextView5, "binding.tvError");
        ViewExtensionsKt.clickDelay(rTextView5, new Function0<Unit>() { // from class: com.ykb.ebook.weight.FunctionMenu.7
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
                String selectedText;
                TextActionMenu.Callback callback = FunctionMenu.this.callback;
                if (callback != null) {
                    TextActionMenu.Callback callback2 = FunctionMenu.this.callback;
                    if (callback2 == null || (selectedText = callback2.getSelectedText()) == null) {
                        selectedText = "";
                    }
                    callback.onErrorClick(selectedText);
                }
            }
        });
    }

    public static /* synthetic */ void initCheck$default(FunctionMenu functionMenu, boolean z2, int i2, int i3, boolean z3, int i4, Object obj) {
        if ((i4 & 8) != 0) {
            z3 = false;
        }
        functionMenu.initCheck(z2, i2, i3, z3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initCheck$lambda$0(FunctionMenu this$0, CompoundButton compoundButton, boolean z2) {
        String selectedText;
        String selectedText2;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        String str = "";
        if (z2) {
            FrameLayout frameLayout = this$0.binding.flDrawLineMenu;
            Intrinsics.checkNotNullExpressionValue(frameLayout, "binding.flDrawLineMenu");
            ViewExtensionsKt.visible(frameLayout);
            compoundButton.setText("取消");
            TextActionMenu.Callback callback = this$0.callback;
            if (callback != null) {
                if (callback != null && (selectedText2 = callback.getSelectedText()) != null) {
                    str = selectedText2;
                }
                callback.onDrawLineBack(str, this$0.drawLineColor, this$0.drawType, 0);
                return;
            }
            return;
        }
        FrameLayout frameLayout2 = this$0.binding.flDrawLineMenu;
        Intrinsics.checkNotNullExpressionValue(frameLayout2, "binding.flDrawLineMenu");
        ViewExtensionsKt.gone(frameLayout2);
        compoundButton.setText("划线");
        TextActionMenu.Callback callback2 = this$0.callback;
        if (callback2 != null) {
            if (callback2 != null && (selectedText = callback2.getSelectedText()) != null) {
                str = selectedText;
            }
            callback2.onDrawLineBack(str, this$0.drawLineColor, this$0.drawType, 1);
        }
    }

    public final void initCheck(boolean drawOrCancel, int color, int style, boolean hasNote) {
        this.binding.cbDrawLine.setChecked(!drawOrCancel);
        this.binding.cbDrawLine.setText((!drawOrCancel || hasNote) ? "取消" : "划线");
        if (hasNote) {
            this.binding.tvNotes.setEnabled(false);
            this.binding.tvNotes.setClickable(false);
            FrameLayout frameLayout = this.binding.flDrawLineMenu;
            Intrinsics.checkNotNullExpressionValue(frameLayout, "binding.flDrawLineMenu");
            ViewExtensionsKt.visible(frameLayout);
            this.binding.cbDrawLine.setText("取消");
            this.binding.cbDrawLine.setChecked(false);
        } else {
            this.binding.tvNotes.setEnabled(true);
            this.binding.tvNotes.setClickable(true);
            if (drawOrCancel) {
                FrameLayout frameLayout2 = this.binding.flDrawLineMenu;
                Intrinsics.checkNotNullExpressionValue(frameLayout2, "binding.flDrawLineMenu");
                ViewExtensionsKt.gone(frameLayout2);
            } else {
                FrameLayout frameLayout3 = this.binding.flDrawLineMenu;
                Intrinsics.checkNotNullExpressionValue(frameLayout3, "binding.flDrawLineMenu");
                ViewExtensionsKt.visible(frameLayout3);
            }
        }
        this.binding.cbDrawLine.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.ykb.ebook.weight.g
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z2) {
                FunctionMenu.initCheck$lambda$0(this.f26483c, compoundButton, z2);
            }
        });
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
    public void onCheckedChanged(@Nullable CompoundButton button, boolean isCheck) {
        String selectedText;
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
            TextActionMenu.Callback callback = this.callback;
            if (callback != null) {
                if (callback == null || (selectedText = callback.getSelectedText()) == null) {
                    selectedText = "";
                }
                callback.onDrawLineBack(selectedText, this.drawLineColor, this.drawType, 2);
            }
        }
    }

    public final void setCallBack(@NotNull TextActionMenu.Callback cb) {
        Intrinsics.checkNotNullParameter(cb, "cb");
        this.callback = cb;
    }

    public /* synthetic */ FunctionMenu(Context context, AttributeSet attributeSet, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, attributeSet, (i3 & 4) != 0 ? 0 : i2);
    }
}
