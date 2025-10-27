package com.ykb.ebook.activity;

import android.content.AppCtxKt;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.ColorResourcesKt;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import com.ykb.ebook.R;
import com.ykb.ebook.base.BaseActivity;
import com.ykb.ebook.common.EventBus;
import com.ykb.ebook.common.ReadConfig;
import com.ykb.ebook.databinding.LayoutMoreSettingBinding;
import com.ykb.ebook.dialog.ConfigDialog;
import com.ykb.ebook.extensions.ViewExtensionsKt;
import java.util.Iterator;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0003J\b\u0010\u0013\u001a\u00020\u0014H\u0002J\u0012\u0010\u0015\u001a\u00020\u00142\b\u0010\u0016\u001a\u0004\u0018\u00010\u0017H\u0014J\b\u0010\u0018\u001a\u00020\u0014H\u0014J\b\u0010\u0019\u001a\u00020\u0014H\u0002R\u001b\u0010\u0004\u001a\u00020\u00028TX\u0094\u0084\u0002¢\u0006\f\n\u0004\b\u0007\u0010\b\u001a\u0004\b\u0005\u0010\u0006R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u000e\u001a\u00020\rX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012¨\u0006\u001a"}, d2 = {"Lcom/ykb/ebook/activity/MoreSetAct;", "Lcom/ykb/ebook/base/BaseActivity;", "Lcom/ykb/ebook/databinding/LayoutMoreSettingBinding;", "()V", "binding", "getBinding", "()Lcom/ykb/ebook/databinding/LayoutMoreSettingBinding;", "binding$delegate", "Lkotlin/Lazy;", "fromModuleInner", "", "hideStatusBar", "mBaseTheme", "", "modelStylel", "getModelStylel", "()I", "setModelStylel", "(I)V", "initView", "", "onActivityCreated", "savedInstanceState", "Landroid/os/Bundle;", "onDestroy", "updateStatusBar", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nMoreSetAct.kt\nKotlin\n*S Kotlin\n*F\n+ 1 MoreSetAct.kt\ncom/ykb/ebook/activity/MoreSetAct\n+ 2 ActivityViewBindings.kt\ncom/ykb/ebook/extensions/ActivityViewBindingsKt\n+ 3 ColorResources.kt\nsplitties/resources/ColorResourcesKt\n+ 4 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,263:1\n13#2,10:264\n42#3:274\n42#3:275\n42#3:276\n42#3:277\n42#3:278\n42#3:279\n42#3:280\n42#3:281\n42#3:284\n42#3:285\n42#3:286\n42#3:287\n42#3:288\n42#3:289\n42#3:290\n42#3:291\n42#3:294\n42#3:295\n42#3:296\n42#3:297\n42#3:298\n42#3:299\n42#3:300\n42#3:301\n42#3:304\n1855#4,2:282\n1855#4,2:292\n1855#4,2:302\n*S KotlinDebug\n*F\n+ 1 MoreSetAct.kt\ncom/ykb/ebook/activity/MoreSetAct\n*L\n28#1:264,10\n145#1:274\n146#1:275\n147#1:276\n148#1:277\n149#1:278\n150#1:279\n151#1:280\n152#1:281\n179#1:284\n180#1:285\n181#1:286\n182#1:287\n183#1:288\n184#1:289\n185#1:290\n186#1:291\n215#1:294\n216#1:295\n217#1:296\n218#1:297\n219#1:298\n220#1:299\n221#1:300\n222#1:301\n251#1:304\n171#1:282,2\n207#1:292,2\n242#1:302,2\n*E\n"})
/* loaded from: classes6.dex */
public final class MoreSetAct extends BaseActivity<LayoutMoreSettingBinding> {

    /* renamed from: binding$delegate, reason: from kotlin metadata */
    @NotNull
    private final Lazy binding;
    private boolean fromModuleInner;
    private boolean hideStatusBar = ReadConfig.INSTANCE.getHideStatusBar();
    private int mBaseTheme;
    private int modelStylel;

    public MoreSetAct() {
        final boolean z2 = false;
        this.binding = LazyKt__LazyJVMKt.lazy(LazyThreadSafetyMode.SYNCHRONIZED, (Function0) new Function0<LayoutMoreSettingBinding>() { // from class: com.ykb.ebook.activity.MoreSetAct$special$$inlined$viewBindingActivity$default$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            @NotNull
            public final LayoutMoreSettingBinding invoke() {
                LayoutInflater layoutInflater = this.getLayoutInflater();
                Intrinsics.checkNotNullExpressionValue(layoutInflater, "layoutInflater");
                LayoutMoreSettingBinding layoutMoreSettingBindingInflate = LayoutMoreSettingBinding.inflate(layoutInflater);
                if (z2) {
                    this.setContentView(layoutMoreSettingBindingInflate.getRoot());
                }
                return layoutMoreSettingBindingInflate;
            }
        });
    }

    private final void initView() {
        updateStatusBar();
        int i2 = this.modelStylel;
        if (i2 == 0) {
            LinearLayout root = getBinding().getRoot();
            int i3 = R.color.white;
            root.setBackground(new ColorDrawable(ContextCompat.getColor(this, i3)));
            getBinding().titleBar.setBackground(new ColorDrawable(ContextCompat.getColor(this, i3)));
            getBinding().contentTopLl.setBackground(new ColorDrawable(ContextCompat.getColor(this, i3)));
            getBinding().contentBotLl.setBackground(new ColorDrawable(ContextCompat.getColor(this, i3)));
            getBinding().titleTv.setTextColor(ColorResourcesKt.color(AppCtxKt.getAppCtx(), R.color.color_141516));
            TextView textView = getBinding().voiceClickTv;
            int i4 = R.color.color_303030;
            textView.setTextColor(ColorResourcesKt.color(AppCtxKt.getAppCtx(), i4));
            getBinding().settingBatteryTv.setTextColor(ColorResourcesKt.color(AppCtxKt.getAppCtx(), i4));
            getBinding().showStatusBarTv.setTextColor(ColorResourcesKt.color(AppCtxKt.getAppCtx(), i4));
            getBinding().restTimeTv.setTextColor(ColorResourcesKt.color(AppCtxKt.getAppCtx(), i4));
            getBinding().closeTimeTv.setTextColor(ColorResourcesKt.color(AppCtxKt.getAppCtx(), i4));
            TextView textView2 = getBinding().tvTime;
            int i5 = R.color.color_606060;
            textView2.setTextColor(ColorResourcesKt.color(AppCtxKt.getAppCtx(), i5));
            getBinding().tvRest.setTextColor(ColorResourcesKt.color(AppCtxKt.getAppCtx(), i5));
            getBinding().restMoreIvTime.setColorFilter(ContextCompat.getColor(this, i4), PorterDuff.Mode.SRC_IN);
            getBinding().closeMoreIvTime.setColorFilter(ContextCompat.getColor(this, i4), PorterDuff.Mode.SRC_IN);
            Switch r02 = getBinding().swStatusBar;
            int i6 = R.drawable.ebook_thumb_selector;
            r02.setThumbDrawable(ContextCompat.getDrawable(this, i6));
            getBinding().swShowBattery.setThumbDrawable(ContextCompat.getDrawable(this, i6));
            getBinding().swVoicePage.setThumbDrawable(ContextCompat.getDrawable(this, i6));
            Switch r03 = getBinding().swStatusBar;
            int i7 = R.drawable.ebook_switch_selector_green;
            r03.setTrackDrawable(ContextCompat.getDrawable(this, i7));
            getBinding().swShowBattery.setTrackDrawable(ContextCompat.getDrawable(this, i7));
            getBinding().swVoicePage.setTrackDrawable(ContextCompat.getDrawable(this, i7));
            ColorDrawable colorDrawable = new ColorDrawable(ContextCompat.getColor(this, R.color.color_eeeeee));
            Iterator it = CollectionsKt__CollectionsKt.listOf((Object[]) new View[]{getBinding().settingLine1, getBinding().settingLine2, getBinding().settingLine4, getBinding().settingLine5}).iterator();
            while (it.hasNext()) {
                ((View) it.next()).setBackground(colorDrawable);
            }
            return;
        }
        if (i2 != 1) {
            if (i2 != 2) {
                return;
            }
            LinearLayout root2 = getBinding().getRoot();
            int i8 = R.color.color_121622;
            root2.setBackground(new ColorDrawable(ContextCompat.getColor(this, i8)));
            getBinding().titleBar.setBackground(new ColorDrawable(ContextCompat.getColor(this, i8)));
            getBinding().contentTopLl.setBackground(new ColorDrawable(ContextCompat.getColor(this, i8)));
            getBinding().contentBotLl.setBackground(new ColorDrawable(ContextCompat.getColor(this, i8)));
            TextView textView3 = getBinding().titleTv;
            int i9 = R.color.color_7380a9;
            textView3.setTextColor(ColorResourcesKt.color(AppCtxKt.getAppCtx(), i9));
            getBinding().voiceClickTv.setTextColor(ColorResourcesKt.color(AppCtxKt.getAppCtx(), i9));
            getBinding().settingBatteryTv.setTextColor(ColorResourcesKt.color(AppCtxKt.getAppCtx(), i9));
            getBinding().closeTimeTv.setTextColor(ColorResourcesKt.color(AppCtxKt.getAppCtx(), i9));
            getBinding().restTimeTv.setTextColor(ColorResourcesKt.color(AppCtxKt.getAppCtx(), i9));
            getBinding().showStatusBarTv.setTextColor(ColorResourcesKt.color(AppCtxKt.getAppCtx(), i9));
            TextView textView4 = getBinding().tvTime;
            int i10 = R.color.color_575F79;
            textView4.setTextColor(ColorResourcesKt.color(AppCtxKt.getAppCtx(), i10));
            getBinding().tvRest.setTextColor(ColorResourcesKt.color(AppCtxKt.getAppCtx(), i10));
            getBinding().imgBack.setColorFilter(ContextCompat.getColor(this, i9), PorterDuff.Mode.SRC_IN);
            getBinding().restMoreIvTime.setColorFilter(ContextCompat.getColor(this, i9), PorterDuff.Mode.SRC_IN);
            getBinding().closeMoreIvTime.setColorFilter(ContextCompat.getColor(this, i9), PorterDuff.Mode.SRC_IN);
            Switch r04 = getBinding().swStatusBar;
            int i11 = R.drawable.ebook_thumb_selector_night;
            r04.setThumbDrawable(ContextCompat.getDrawable(this, i11));
            getBinding().swShowBattery.setThumbDrawable(ContextCompat.getDrawable(this, i11));
            getBinding().swVoicePage.setThumbDrawable(ContextCompat.getDrawable(this, i11));
            Switch r05 = getBinding().swStatusBar;
            int i12 = R.drawable.ebook_switch_selector_night;
            r05.setTrackDrawable(ContextCompat.getDrawable(this, i12));
            getBinding().swShowBattery.setTrackDrawable(ContextCompat.getDrawable(this, i12));
            getBinding().swVoicePage.setTrackDrawable(ContextCompat.getDrawable(this, i12));
            ColorDrawable colorDrawable2 = new ColorDrawable(ContextCompat.getColor(this, R.color.color_171C2D));
            Iterator it2 = CollectionsKt__CollectionsKt.listOf((Object[]) new View[]{getBinding().settingLine1, getBinding().settingLine2, getBinding().settingLine4, getBinding().settingLine5}).iterator();
            while (it2.hasNext()) {
                ((View) it2.next()).setBackground(colorDrawable2);
            }
            return;
        }
        LinearLayout root3 = getBinding().getRoot();
        int i13 = R.color.color_FEEEC6;
        root3.setBackground(new ColorDrawable(ContextCompat.getColor(this, i13)));
        getBinding().titleBar.setBackground(new ColorDrawable(ContextCompat.getColor(this, i13)));
        getBinding().contentTopLl.setBackground(new ColorDrawable(ContextCompat.getColor(this, i13)));
        getBinding().contentBotLl.setBackground(new ColorDrawable(ContextCompat.getColor(this, i13)));
        TextView textView5 = getBinding().titleTv;
        int i14 = R.color.black;
        textView5.setTextColor(ColorResourcesKt.color(AppCtxKt.getAppCtx(), i14));
        TextView textView6 = getBinding().voiceClickTv;
        int i15 = R.color.color_303030;
        textView6.setTextColor(ColorResourcesKt.color(AppCtxKt.getAppCtx(), i15));
        getBinding().settingBatteryTv.setTextColor(ColorResourcesKt.color(AppCtxKt.getAppCtx(), i15));
        getBinding().showStatusBarTv.setTextColor(ColorResourcesKt.color(AppCtxKt.getAppCtx(), i15));
        getBinding().closeTimeTv.setTextColor(ColorResourcesKt.color(AppCtxKt.getAppCtx(), i15));
        getBinding().restTimeTv.setTextColor(ColorResourcesKt.color(AppCtxKt.getAppCtx(), i15));
        TextView textView7 = getBinding().tvTime;
        int i16 = R.color.color_909090;
        textView7.setTextColor(ColorResourcesKt.color(AppCtxKt.getAppCtx(), i16));
        getBinding().tvRest.setTextColor(ColorResourcesKt.color(AppCtxKt.getAppCtx(), i16));
        getBinding().imgBack.setColorFilter(ContextCompat.getColor(this, i14), PorterDuff.Mode.SRC_IN);
        getBinding().restMoreIvTime.setColorFilter(ContextCompat.getColor(this, i15), PorterDuff.Mode.SRC_IN);
        getBinding().closeMoreIvTime.setColorFilter(ContextCompat.getColor(this, i15), PorterDuff.Mode.SRC_IN);
        Switch r06 = getBinding().swStatusBar;
        int i17 = R.drawable.ebook_thumb_selector;
        r06.setThumbDrawable(ContextCompat.getDrawable(this, i17));
        getBinding().swShowBattery.setThumbDrawable(ContextCompat.getDrawable(this, i17));
        getBinding().swVoicePage.setThumbDrawable(ContextCompat.getDrawable(this, i17));
        Switch r07 = getBinding().swStatusBar;
        int i18 = R.drawable.ebook_switch_selector_green;
        r07.setTrackDrawable(ContextCompat.getDrawable(this, i18));
        getBinding().swShowBattery.setTrackDrawable(ContextCompat.getDrawable(this, i18));
        getBinding().swVoicePage.setTrackDrawable(ContextCompat.getDrawable(this, i18));
        ColorDrawable colorDrawable3 = new ColorDrawable(ContextCompat.getColor(this, R.color.color_EDE2C3));
        Iterator it3 = CollectionsKt__CollectionsKt.listOf((Object[]) new View[]{getBinding().settingLine1, getBinding().settingLine2, getBinding().settingLine3, getBinding().settingLine4, getBinding().settingLine5}).iterator();
        while (it3.hasNext()) {
            ((View) it3.next()).setBackground(colorDrawable3);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onActivityCreated$lambda$0(MoreSetAct this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onActivityCreated$lambda$1(CompoundButton compoundButton, boolean z2) {
        ReadConfig readConfig = ReadConfig.INSTANCE;
        readConfig.setShowTime(z2);
        readConfig.setShowBattery(z2);
        EventBus.INSTANCE.post(16);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onActivityCreated$lambda$2(MoreSetAct this$0, CompoundButton compoundButton, boolean z2) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.hideStatusBar = !z2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onActivityCreated$lambda$3(CompoundButton compoundButton, boolean z2) {
        ReadConfig.INSTANCE.setShowNote(z2);
        EventBus.INSTANCE.post(21);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onActivityCreated$lambda$4(CompoundButton compoundButton, boolean z2) {
        ReadConfig.INSTANCE.setShowReview(z2);
        EventBus.INSTANCE.post(22);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onActivityCreated$lambda$5(CompoundButton compoundButton, boolean z2) {
        ReadConfig.INSTANCE.setVolumeKeyPage(z2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onActivityCreated$lambda$6(final MoreSetAct this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        new ConfigDialog.Builder(this$0, 2).setOnTimeClick(new Function2<Integer, String, Unit>() { // from class: com.ykb.ebook.activity.MoreSetAct$onActivityCreated$8$1
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Unit invoke(Integer num, String str) {
                invoke(num.intValue(), str);
                return Unit.INSTANCE;
            }

            public final void invoke(int i2, @NotNull String s2) {
                Intrinsics.checkNotNullParameter(s2, "s");
                ReadConfig.INSTANCE.setKeepLight(i2);
                this.this$0.getBinding().tvTime.setText(s2);
                EventBus.INSTANCE.post(18);
            }
        }).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onActivityCreated$lambda$7(final MoreSetAct this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        new ConfigDialog.Builder(this$0, 3).setOnTimeClick(new Function2<Integer, String, Unit>() { // from class: com.ykb.ebook.activity.MoreSetAct$onActivityCreated$9$1
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Unit invoke(Integer num, String str) {
                invoke(num.intValue(), str);
                return Unit.INSTANCE;
            }

            public final void invoke(int i2, @NotNull String s2) {
                Intrinsics.checkNotNullParameter(s2, "s");
                ReadConfig.INSTANCE.setRestRemind(i2);
                this.this$0.getBinding().tvRest.setText(s2);
                EventBus.INSTANCE.post(32);
            }
        }).show();
    }

    private final void updateStatusBar() {
        int iColor;
        getWindow().addFlags(Integer.MIN_VALUE);
        getWindow().clearFlags(67108864);
        Window window = getWindow();
        int i2 = this.modelStylel;
        if (i2 == 1) {
            iColor = AppCtxKt.getAppCtx().getColor(R.color.color_FEEEC6);
        } else {
            iColor = ColorResourcesKt.color(AppCtxKt.getAppCtx(), i2 != 2 ? R.color.white : R.color.color_121622);
        }
        window.setStatusBarColor(iColor);
        getWindow().getDecorView().setSystemUiVisibility(this.modelStylel < 2 ? 8192 : 256);
    }

    public final int getModelStylel() {
        return this.modelStylel;
    }

    @Override // com.ykb.ebook.base.BaseActivity
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        getWindow().setStatusBarColor(getColor(R.color.white));
        boolean z2 = false;
        this.fromModuleInner = getIntent().getBooleanExtra("fromModuleInner", false);
        int intExtra = getIntent().getIntExtra("mBaseTheme", 0);
        this.mBaseTheme = intExtra;
        if (this.fromModuleInner) {
            this.modelStylel = ReadConfig.INSTANCE.getColorMode();
        } else {
            this.modelStylel = intExtra == 0 ? 0 : 2;
        }
        Switch r7 = getBinding().swVoicePage;
        ReadConfig readConfig = ReadConfig.INSTANCE;
        r7.setChecked(readConfig.getVolumeKeyPage());
        boolean showTime = readConfig.getShowTime();
        boolean showBattery = readConfig.getShowBattery();
        Switch r4 = getBinding().swShowBattery;
        if (showTime && showBattery) {
            z2 = true;
        }
        r4.setChecked(z2);
        getBinding().swShowParagraphComment.setChecked(readConfig.isShowReview());
        getBinding().swStatusBar.setChecked(!readConfig.getHideStatusBar());
        int keepLight = readConfig.getKeepLight();
        TextView textView = getBinding().tvPageSet;
        int pageAnim = readConfig.getPageAnim();
        String str = "";
        textView.setText(pageAnim != 1 ? pageAnim != 2 ? pageAnim != 4 ? "" : "左右滑动" : "仿真翻页" : "上下翻页");
        getBinding().tvTime.setText(keepLight != 0 ? keepLight != 300 ? keepLight != 900 ? keepLight != 1800 ? "" : "30分钟" : "15分钟" : "5分钟" : "跟随系统");
        int restRemind = readConfig.getRestRemind();
        TextView textView2 = getBinding().tvRest;
        if (restRemind == 0) {
            str = "关闭";
        } else if (restRemind == 60) {
            str = "1分钟";
        } else if (restRemind == 2700) {
            str = "45分钟";
        } else if (restRemind == 3600) {
            str = "60分钟";
        } else if (restRemind == 7200) {
            str = "120分钟";
        }
        textView2.setText(str);
        getBinding().imgBack.setOnClickListener(new View.OnClickListener() { // from class: com.ykb.ebook.activity.l0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MoreSetAct.onActivityCreated$lambda$0(this.f26165c, view);
            }
        });
        getBinding().swShowBattery.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.ykb.ebook.activity.m0
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z3) {
                MoreSetAct.onActivityCreated$lambda$1(compoundButton, z3);
            }
        });
        getBinding().swStatusBar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.ykb.ebook.activity.n0
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z3) {
                MoreSetAct.onActivityCreated$lambda$2(this.f26173c, compoundButton, z3);
            }
        });
        getBinding().swShowBookNote.setChecked(readConfig.isShowNote());
        getBinding().swShowBookNote.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.ykb.ebook.activity.o0
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z3) {
                MoreSetAct.onActivityCreated$lambda$3(compoundButton, z3);
            }
        });
        getBinding().swShowParagraphComment.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.ykb.ebook.activity.p0
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z3) {
                MoreSetAct.onActivityCreated$lambda$4(compoundButton, z3);
            }
        });
        RelativeLayout relativeLayout = getBinding().rlFlipPage;
        Intrinsics.checkNotNullExpressionValue(relativeLayout, "binding.rlFlipPage");
        ViewExtensionsKt.clickDelay(relativeLayout, new Function0<Unit>() { // from class: com.ykb.ebook.activity.MoreSetAct.onActivityCreated.6
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
                ConfigDialog.Builder builder = new ConfigDialog.Builder(MoreSetAct.this, 1);
                final MoreSetAct moreSetAct = MoreSetAct.this;
                builder.setOnTimeClick(new Function2<Integer, String, Unit>() { // from class: com.ykb.ebook.activity.MoreSetAct.onActivityCreated.6.1
                    {
                        super(2);
                    }

                    @Override // kotlin.jvm.functions.Function2
                    public /* bridge */ /* synthetic */ Unit invoke(Integer num, String str2) {
                        invoke(num.intValue(), str2);
                        return Unit.INSTANCE;
                    }

                    public final void invoke(int i2, @NotNull String s2) {
                        Intrinsics.checkNotNullParameter(s2, "s");
                        moreSetAct.getBinding().tvPageSet.setText(s2);
                        ReadConfig.INSTANCE.setPageAnim(i2);
                        EventBus.INSTANCE.post(291);
                    }
                }).show();
            }
        });
        getBinding().swVoicePage.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.ykb.ebook.activity.q0
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z3) {
                MoreSetAct.onActivityCreated$lambda$5(compoundButton, z3);
            }
        });
        getBinding().lyCloseTime.setOnClickListener(new View.OnClickListener() { // from class: com.ykb.ebook.activity.r0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MoreSetAct.onActivityCreated$lambda$6(this.f26190c, view);
            }
        });
        getBinding().lyResetTime.setOnClickListener(new View.OnClickListener() { // from class: com.ykb.ebook.activity.s0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MoreSetAct.onActivityCreated$lambda$7(this.f26194c, view);
            }
        });
        initView();
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        boolean z2 = this.hideStatusBar;
        ReadConfig readConfig = ReadConfig.INSTANCE;
        if (z2 != readConfig.getHideStatusBar()) {
            readConfig.setHideStatusBar(this.hideStatusBar);
            EventBus.INSTANCE.post(17);
        }
        super.onDestroy();
    }

    public final void setModelStylel(int i2) {
        this.modelStylel = i2;
    }

    @Override // com.ykb.ebook.base.BaseActivity
    @NotNull
    public LayoutMoreSettingBinding getBinding() {
        return (LayoutMoreSettingBinding) this.binding.getValue();
    }
}
