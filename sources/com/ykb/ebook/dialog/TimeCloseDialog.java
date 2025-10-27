package com.ykb.ebook.dialog;

import android.content.Context;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import com.ruffian.library.widget.RRadioButton;
import com.yikaobang.yixue.R2;
import com.ykb.ebook.R;
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

@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\u0018\u00002\u00020\u0001:\u0001\u0003B\u0005¢\u0006\u0002\u0010\u0002¨\u0006\u0004"}, d2 = {"Lcom/ykb/ebook/dialog/TimeCloseDialog;", "", "()V", "Builder", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes7.dex */
public final class TimeCloseDialog {

    @Metadata(d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0010\u000e\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u00012\u00020\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\b\u0010$\u001a\u00020\u001eH\u0002J\u001a\u0010%\u001a\u00020\u001e2\b\u0010&\u001a\u0004\u0018\u00010'2\u0006\u0010(\u001a\u00020)H\u0016J\u0010\u0010*\u001a\u00020\u001e2\u0006\u0010+\u001a\u00020,H\u0016J\u000e\u0010-\u001a\u00020\u00002\u0006\u0010.\u001a\u00020\u001cJ \u0010/\u001a\u00020\u00002\u0018\u00100\u001a\u0014\u0012\u0004\u0012\u00020\u001c\u0012\u0004\u0012\u00020\u001d\u0012\u0004\u0012\u00020\u001e0\u001bR\u001d\u0010\u0006\u001a\u0004\u0018\u00010\u00078BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\n\u0010\u000b\u001a\u0004\b\b\u0010\tR\u001d\u0010\f\u001a\u0004\u0018\u00010\u00078BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u000e\u0010\u000b\u001a\u0004\b\r\u0010\tR\u001d\u0010\u000f\u001a\u0004\u0018\u00010\u00078BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0011\u0010\u000b\u001a\u0004\b\u0010\u0010\tR\u001d\u0010\u0012\u001a\u0004\u0018\u00010\u00078BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0014\u0010\u000b\u001a\u0004\b\u0013\u0010\tR\u001d\u0010\u0015\u001a\u0004\u0018\u00010\u00168BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0019\u0010\u000b\u001a\u0004\b\u0017\u0010\u0018R\"\u0010\u001a\u001a\u0016\u0012\u0004\u0012\u00020\u001c\u0012\u0004\u0012\u00020\u001d\u0012\u0004\u0012\u00020\u001e\u0018\u00010\u001bX\u0082\u000e¢\u0006\u0002\n\u0000R\u001d\u0010\u001f\u001a\u0004\u0018\u00010 8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b#\u0010\u000b\u001a\u0004\b!\u0010\"¨\u00061"}, d2 = {"Lcom/ykb/ebook/dialog/TimeCloseDialog$Builder;", "Lcom/ykb/ebook/dialog/BasicDialog$Builder;", "Landroid/widget/CompoundButton$OnCheckedChangeListener;", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "(Landroid/content/Context;)V", "cbFifteen", "Lcom/ruffian/library/widget/RRadioButton;", "getCbFifteen", "()Lcom/ruffian/library/widget/RRadioButton;", "cbFifteen$delegate", "Lkotlin/Lazy;", "cbFive", "getCbFive", "cbFive$delegate", "cbSystem", "getCbSystem", "cbSystem$delegate", "cbThirty", "getCbThirty", "cbThirty$delegate", "imgClose", "Landroid/widget/ImageView;", "getImgClose", "()Landroid/widget/ImageView;", "imgClose$delegate", "onTimeClick", "Lkotlin/Function2;", "", "", "", "tvCancel", "Landroid/widget/TextView;", "getTvCancel", "()Landroid/widget/TextView;", "tvCancel$delegate", "initView", "onCheckedChanged", "button", "Landroid/widget/CompoundButton;", "isChecked", "", "onClick", "view", "Landroid/view/View;", "setChecked", CrashHianalyticsData.TIME, "setOnTimeClick", "click", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Builder extends BasicDialog.Builder<Builder> implements CompoundButton.OnCheckedChangeListener {

        /* renamed from: cbFifteen$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy cbFifteen;

        /* renamed from: cbFive$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy cbFive;

        /* renamed from: cbSystem$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy cbSystem;

        /* renamed from: cbThirty$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy cbThirty;

        /* renamed from: imgClose$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy imgClose;

        @Nullable
        private Function2<? super Integer, ? super String, Unit> onTimeClick;

        /* renamed from: tvCancel$delegate, reason: from kotlin metadata */
        @NotNull
        private final Lazy tvCancel;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Builder(@NotNull Context context) {
            super(context);
            Intrinsics.checkNotNullParameter(context, "context");
            this.tvCancel = LazyKt__LazyJVMKt.lazy(new Function0<TextView>() { // from class: com.ykb.ebook.dialog.TimeCloseDialog$Builder$tvCancel$2
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
            this.imgClose = LazyKt__LazyJVMKt.lazy(new Function0<ImageView>() { // from class: com.ykb.ebook.dialog.TimeCloseDialog$Builder$imgClose$2
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
            this.cbSystem = LazyKt__LazyJVMKt.lazy(new Function0<RRadioButton>() { // from class: com.ykb.ebook.dialog.TimeCloseDialog$Builder$cbSystem$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final RRadioButton invoke() {
                    return (RRadioButton) this.this$0.findViewById(R.id.cb_system);
                }
            });
            this.cbFive = LazyKt__LazyJVMKt.lazy(new Function0<RRadioButton>() { // from class: com.ykb.ebook.dialog.TimeCloseDialog$Builder$cbFive$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final RRadioButton invoke() {
                    return (RRadioButton) this.this$0.findViewById(R.id.cb_five_minutes);
                }
            });
            this.cbFifteen = LazyKt__LazyJVMKt.lazy(new Function0<RRadioButton>() { // from class: com.ykb.ebook.dialog.TimeCloseDialog$Builder$cbFifteen$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final RRadioButton invoke() {
                    return (RRadioButton) this.this$0.findViewById(R.id.cb_fifteen_minutes);
                }
            });
            this.cbThirty = LazyKt__LazyJVMKt.lazy(new Function0<RRadioButton>() { // from class: com.ykb.ebook.dialog.TimeCloseDialog$Builder$cbThirty$2
                {
                    super(0);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // kotlin.jvm.functions.Function0
                @Nullable
                public final RRadioButton invoke() {
                    return (RRadioButton) this.this$0.findViewById(R.id.cb_thirty_minutes);
                }
            });
            setContentView(R.layout.dialog_time_close_choose);
            setGravity(80);
            setAnimStyle(AnimAction.INSTANCE.getANIM_BOTTOM());
            setCancelable(true);
            setCanceledOnTouchOutside(true);
            setOnClickListener(getTvCancel(), getImgClose());
            initView();
        }

        private final RRadioButton getCbFifteen() {
            return (RRadioButton) this.cbFifteen.getValue();
        }

        private final RRadioButton getCbFive() {
            return (RRadioButton) this.cbFive.getValue();
        }

        private final RRadioButton getCbSystem() {
            return (RRadioButton) this.cbSystem.getValue();
        }

        private final RRadioButton getCbThirty() {
            return (RRadioButton) this.cbThirty.getValue();
        }

        private final ImageView getImgClose() {
            return (ImageView) this.imgClose.getValue();
        }

        private final TextView getTvCancel() {
            return (TextView) this.tvCancel.getValue();
        }

        private final void initView() {
            RRadioButton cbSystem = getCbSystem();
            if (cbSystem != null) {
                cbSystem.setOnCheckedChangeListener(this);
            }
            RRadioButton cbFive = getCbFive();
            if (cbFive != null) {
                cbFive.setOnCheckedChangeListener(this);
            }
            RRadioButton cbFifteen = getCbFifteen();
            if (cbFifteen != null) {
                cbFifteen.setOnCheckedChangeListener(this);
            }
            RRadioButton cbThirty = getCbThirty();
            if (cbThirty != null) {
                cbThirty.setOnCheckedChangeListener(this);
            }
        }

        @Override // android.widget.CompoundButton.OnCheckedChangeListener
        public void onCheckedChanged(@Nullable CompoundButton button, boolean isChecked) {
            Function2<? super Integer, ? super String, Unit> function2;
            if (isChecked) {
                if (Intrinsics.areEqual(button, getCbSystem())) {
                    Function2<? super Integer, ? super String, Unit> function22 = this.onTimeClick;
                    if (function22 != null) {
                        function22.invoke(0, "跟随系统");
                    }
                } else if (Intrinsics.areEqual(button, getCbFive())) {
                    Function2<? super Integer, ? super String, Unit> function23 = this.onTimeClick;
                    if (function23 != null) {
                        function23.invoke(300, "5分钟");
                    }
                } else if (Intrinsics.areEqual(button, getCbFifteen())) {
                    Function2<? super Integer, ? super String, Unit> function24 = this.onTimeClick;
                    if (function24 != null) {
                        function24.invoke(900, "15分钟");
                    }
                } else if (Intrinsics.areEqual(button, getCbThirty()) && (function2 = this.onTimeClick) != null) {
                    function2.invoke(Integer.valueOf(R2.attr.ic_knowledge_chart_data), "30分钟");
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
        public final Builder setChecked(int time) {
            RRadioButton cbThirty;
            if (time == 0) {
                RRadioButton cbSystem = getCbSystem();
                if (cbSystem != null) {
                    cbSystem.setChecked(true);
                }
            } else if (time == 300) {
                RRadioButton cbFive = getCbFive();
                if (cbFive != null) {
                    cbFive.setChecked(true);
                }
            } else if (time == 900) {
                RRadioButton cbFifteen = getCbFifteen();
                if (cbFifteen != null) {
                    cbFifteen.setChecked(true);
                }
            } else if (time == 1800 && (cbThirty = getCbThirty()) != null) {
                cbThirty.setChecked(true);
            }
            return this;
        }

        @NotNull
        public final Builder setOnTimeClick(@NotNull Function2<? super Integer, ? super String, Unit> click) {
            Intrinsics.checkNotNullParameter(click, "click");
            this.onTimeClick = click;
            return this;
        }
    }
}
