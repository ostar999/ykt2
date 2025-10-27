package com.ykb.ebook.dialog;

import android.content.Context;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.ruffian.library.widget.RRadioButton;
import com.ykb.ebook.R;
import com.ykb.ebook.common.ReadConfig;
import com.ykb.ebook.common_interface.AnimAction;
import com.ykb.ebook.dialog.BasicDialog;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\u0018\u00002\u00020\u0001:\u0001\u0003B\u0005¢\u0006\u0002\u0010\u0002¨\u0006\u0004"}, d2 = {"Lcom/ykb/ebook/dialog/FlipPageSetDialog;", "", "()V", "Builder", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes7.dex */
public final class FlipPageSetDialog {

    @Metadata(d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0010\u000e\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u00012\u00020\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\b\u0010!\u001a\u00020\u001bH\u0002J\u001a\u0010\"\u001a\u00020\u001b2\b\u0010#\u001a\u0004\u0018\u00010$2\u0006\u0010%\u001a\u00020&H\u0016J\u0010\u0010'\u001a\u00020\u001b2\u0006\u0010(\u001a\u00020)H\u0016J \u0010*\u001a\u00020\u00002\u0018\u0010+\u001a\u0014\u0012\u0004\u0012\u00020\u0019\u0012\u0004\u0012\u00020\u001a\u0012\u0004\u0012\u00020\u001b0\u0018R\u001d\u0010\u0006\u001a\u0004\u0018\u00010\u00078BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\n\u0010\u000b\u001a\u0004\b\b\u0010\tR\u001d\u0010\f\u001a\u0004\u0018\u00010\u00078BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u000e\u0010\u000b\u001a\u0004\b\r\u0010\tR\u001d\u0010\u000f\u001a\u0004\u0018\u00010\u00078BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0011\u0010\u000b\u001a\u0004\b\u0010\u0010\tR\u001d\u0010\u0012\u001a\u0004\u0018\u00010\u00138BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0016\u0010\u000b\u001a\u0004\b\u0014\u0010\u0015R\"\u0010\u0017\u001a\u0016\u0012\u0004\u0012\u00020\u0019\u0012\u0004\u0012\u00020\u001a\u0012\u0004\u0012\u00020\u001b\u0018\u00010\u0018X\u0082\u000e¢\u0006\u0002\n\u0000R\u001d\u0010\u001c\u001a\u0004\u0018\u00010\u001d8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b \u0010\u000b\u001a\u0004\b\u001e\u0010\u001f¨\u0006,"}, d2 = {"Lcom/ykb/ebook/dialog/FlipPageSetDialog$Builder;", "Lcom/ykb/ebook/dialog/BasicDialog$Builder;", "Landroid/widget/CompoundButton$OnCheckedChangeListener;", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "(Landroid/content/Context;)V", "cbFz", "Lcom/ruffian/library/widget/RRadioButton;", "getCbFz", "()Lcom/ruffian/library/widget/RRadioButton;", "cbFz$delegate", "Lkotlin/Lazy;", "cbSx", "getCbSx", "cbSx$delegate", "cbZy", "getCbZy", "cbZy$delegate", "imgClose", "Landroid/widget/ImageView;", "getImgClose", "()Landroid/widget/ImageView;", "imgClose$delegate", "setClick", "Lkotlin/Function2;", "", "", "", "tvCancel", "Landroid/widget/TextView;", "getTvCancel", "()Landroid/widget/TextView;", "tvCancel$delegate", "initView", "onCheckedChanged", "button", "Landroid/widget/CompoundButton;", "isChecked", "", "onClick", "view", "Landroid/view/View;", "setOnClick", "click", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Builder extends BasicDialog.Builder<Builder> implements CompoundButton.OnCheckedChangeListener {

        /* renamed from: cbFz$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy cbFz;

        /* renamed from: cbSx$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy cbSx;

        /* renamed from: cbZy$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy cbZy;

        /* renamed from: imgClose$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy imgClose;

        @Nullable
        private Function2<? super Integer, ? super String, Unit> setClick;

        /* renamed from: tvCancel$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy tvCancel;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Builder(@NotNull Context context) {
            super(context);
            Intrinsics.checkNotNullParameter(context, "context");
            this.tvCancel = LazyKt__LazyJVMKt.lazy(new Function0<TextView>() { // from class: com.ykb.ebook.dialog.FlipPageSetDialog$Builder$tvCancel$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final TextView invoke() {
                    return (TextView) this.this$0.findViewById(R.id.btn_cancel);
                }
            });
            this.imgClose = LazyKt__LazyJVMKt.lazy(new Function0<ImageView>() { // from class: com.ykb.ebook.dialog.FlipPageSetDialog$Builder$imgClose$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final ImageView invoke() {
                    return (ImageView) this.this$0.findViewById(R.id.img_close);
                }
            });
            this.cbFz = LazyKt__LazyJVMKt.lazy(new Function0<RRadioButton>() { // from class: com.ykb.ebook.dialog.FlipPageSetDialog$Builder$cbFz$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final RRadioButton invoke() {
                    return (RRadioButton) this.this$0.findViewById(R.id.cb_fz);
                }
            });
            this.cbZy = LazyKt__LazyJVMKt.lazy(new Function0<RRadioButton>() { // from class: com.ykb.ebook.dialog.FlipPageSetDialog$Builder$cbZy$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final RRadioButton invoke() {
                    return (RRadioButton) this.this$0.findViewById(R.id.cb_zy);
                }
            });
            this.cbSx = LazyKt__LazyJVMKt.lazy(new Function0<RRadioButton>() { // from class: com.ykb.ebook.dialog.FlipPageSetDialog$Builder$cbSx$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final RRadioButton invoke() {
                    return (RRadioButton) this.this$0.findViewById(R.id.cb_sx);
                }
            });
            setContentView(R.layout.dialog_flip_page_set);
            setGravity(80);
            setAnimStyle(AnimAction.INSTANCE.getANIM_BOTTOM());
            setCancelable(true);
            setCanceledOnTouchOutside(true);
            setOnClickListener(getTvCancel(), getImgClose());
            initView();
        }

        private final RRadioButton getCbFz() {
            return (RRadioButton) this.cbFz.getValue();
        }

        private final RRadioButton getCbSx() {
            return (RRadioButton) this.cbSx.getValue();
        }

        private final RRadioButton getCbZy() {
            return (RRadioButton) this.cbZy.getValue();
        }

        private final ImageView getImgClose() {
            return (ImageView) this.imgClose.getValue();
        }

        private final TextView getTvCancel() {
            return (TextView) this.tvCancel.getValue();
        }

        private final void initView() {
            RRadioButton cbZy;
            RRadioButton cbFz = getCbFz();
            if (cbFz != null) {
                cbFz.setOnCheckedChangeListener(this);
            }
            RRadioButton cbZy2 = getCbZy();
            if (cbZy2 != null) {
                cbZy2.setOnCheckedChangeListener(this);
            }
            RRadioButton cbSx = getCbSx();
            if (cbSx != null) {
                cbSx.setOnCheckedChangeListener(this);
            }
            int pageAnim = ReadConfig.INSTANCE.getPageAnim();
            if (pageAnim == 2) {
                RRadioButton cbFz2 = getCbFz();
                if (cbFz2 == null) {
                    return;
                }
                cbFz2.setChecked(true);
                return;
            }
            if (pageAnim != 3) {
                if (pageAnim == 4 && (cbZy = getCbZy()) != null) {
                    cbZy.setChecked(true);
                    return;
                }
                return;
            }
            RRadioButton cbSx2 = getCbSx();
            if (cbSx2 == null) {
                return;
            }
            cbSx2.setChecked(true);
        }

        @Override // android.widget.CompoundButton.OnCheckedChangeListener
        public void onCheckedChanged(@Nullable CompoundButton button, boolean isChecked) {
            Function2<? super Integer, ? super String, Unit> function2;
            if (isChecked) {
                if (Intrinsics.areEqual(button, getCbFz())) {
                    Function2<? super Integer, ? super String, Unit> function22 = this.setClick;
                    if (function22 != null) {
                        function22.invoke(2, "仿真翻页");
                    }
                } else if (Intrinsics.areEqual(button, getCbZy())) {
                    Function2<? super Integer, ? super String, Unit> function23 = this.setClick;
                    if (function23 != null) {
                        function23.invoke(4, "左右滚动");
                    }
                } else if (Intrinsics.areEqual(button, getCbSx()) && (function2 = this.setClick) != null) {
                    function2.invoke(3, "上下翻页");
                }
                dismiss();
            }
        }

        @Override // com.ykb.ebook.dialog.BasicDialog.Builder, com.ykb.ebook.common_interface.ClickAction, android.view.View.OnClickListener
        public void onClick(@NotNull View view) {
            Intrinsics.checkNotNullParameter(view, "view");
            dismiss();
        }

        @NotNull
        public final Builder setOnClick(@NotNull Function2<? super Integer, ? super String, Unit> click) {
            Intrinsics.checkNotNullParameter(click, "click");
            this.setClick = click;
            return this;
        }
    }
}
