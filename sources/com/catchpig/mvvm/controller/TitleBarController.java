package com.catchpig.mvvm.controller;

import android.app.Activity;
import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.catchpig.mvvm.R;
import com.catchpig.mvvm.controller.TitleBarController;
import com.catchpig.mvvm.databinding.LayoutTitleBarBinding;
import com.catchpig.mvvm.entity.TitleMenuParam;
import com.catchpig.mvvm.entity.TitleParam;
import com.catchpig.mvvm.interfaces.IGlobalConfig;
import com.catchpig.mvvm.ksp.KotlinMvvmCompiler;
import com.catchpig.utils.ext.ContextExtKt;
import com.easefun.polyv.livecommon.module.modules.document.model.enums.PLVDocumentMarkToolType;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0002J\u0010\u0010\r\u001a\u00020\n2\u0006\u0010\u000e\u001a\u00020\u000fH\u0002J\u0010\u0010\u0010\u001a\u00020\n2\u0006\u0010\u0011\u001a\u00020\u0012H\u0002J\u0010\u0010\u0013\u001a\u00020\n2\u0006\u0010\u0014\u001a\u00020\u0015H\u0002J\u0018\u0010\u0016\u001a\u00020\n2\u0006\u0010\u000e\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0019H\u0002J\u0010\u0010\u001a\u001a\u00020\n2\u0006\u0010\u0014\u001a\u00020\u0015H\u0002J\u000e\u0010\u001b\u001a\u00020\n2\u0006\u0010\u001c\u001a\u00020\u0012J\u0012\u0010\u001d\u001a\u00020\n2\b\u0010\u001c\u001a\u0004\u0018\u00010\u0012H\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001e"}, d2 = {"Lcom/catchpig/mvvm/controller/TitleBarController;", "Landroid/view/View$OnClickListener;", PushConstants.INTENT_ACTIVITY_NAME, "Landroid/app/Activity;", "title", "Lcom/catchpig/mvvm/entity/TitleParam;", "(Landroid/app/Activity;Lcom/catchpig/mvvm/entity/TitleParam;)V", "globalConfig", "Lcom/catchpig/mvvm/interfaces/IGlobalConfig;", "drawBackIcon", "", "backIcon", "Landroid/widget/ImageView;", "drawBackground", "titleBar", "Landroid/widget/RelativeLayout;", "drawLine", PLVDocumentMarkToolType.BRUSH, "Landroid/view/View;", "drawTextColor", "titleBarBinding", "Lcom/catchpig/mvvm/databinding/LayoutTitleBarBinding;", "drawTitleMenu", "Landroid/widget/FrameLayout;", "titleMenu", "Lcom/catchpig/mvvm/entity/TitleMenuParam;", "initListener", "initTitleBar", "view", "onClick", "mvvm_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes2.dex */
public final class TitleBarController implements View.OnClickListener {

    @NotNull
    private final Activity activity;

    @NotNull
    private final IGlobalConfig globalConfig;

    @NotNull
    private final TitleParam title;

    public TitleBarController(@NotNull Activity activity, @NotNull TitleParam title) {
        Intrinsics.checkNotNullParameter(activity, "activity");
        Intrinsics.checkNotNullParameter(title, "title");
        this.activity = activity;
        this.title = title;
        this.globalConfig = KotlinMvvmCompiler.INSTANCE.globalConfig();
    }

    private final void drawBackIcon(ImageView backIcon) {
        if (this.title.getBackIcon() == -1) {
            backIcon.setImageResource(this.globalConfig.getTitleBackIcon());
        } else {
            backIcon.setImageResource(this.title.getBackIcon());
        }
    }

    private final void drawBackground(RelativeLayout titleBar) {
        titleBar.setBackgroundResource(this.title.getBackgroundColor() == -1 ? this.globalConfig.getTitleBackground() : this.title.getBackgroundColor());
    }

    private final void drawLine(View line) {
        if (!this.globalConfig.isShowTitleLine()) {
            line.setVisibility(8);
        } else {
            line.setVisibility(0);
            this.globalConfig.getTitleLineColor();
        }
    }

    private final void drawTextColor(LayoutTitleBarBinding titleBarBinding) {
        int iColorResToInt = this.title.getTextColor() == -1 ? ContextExtKt.colorResToInt(this.activity, this.globalConfig.getTitleTextColor()) : ContextExtKt.colorResToInt(this.activity, this.title.getTextColor());
        titleBarBinding.titleText.setTextColor(iColorResToInt);
        titleBarBinding.rightFirstText.setTextColor(this.title.getRightFirstTextColor() == -1 ? ContextExtKt.colorResToInt(this.activity, this.globalConfig.getTitleRightFirstTextColor()) : ContextExtKt.colorResToInt(this.activity, this.title.getRightFirstTextColor()));
        titleBarBinding.rightSecondText.setTextColor(iColorResToInt);
    }

    private final void drawTitleMenu(FrameLayout titleBar, TitleMenuParam titleMenu) {
        TextView textView = (TextView) titleBar.findViewById(R.id.rightFirstText);
        if (titleMenu.getRightFirstText() != -1) {
            textView.setVisibility(0);
            textView.setText(titleMenu.getRightFirstText());
        } else {
            textView.setVisibility(8);
        }
        TextView textView2 = (TextView) titleBar.findViewById(R.id.rightSecondText);
        if (titleMenu.getRightSecondText() != -1) {
            textView2.setVisibility(0);
            textView2.setText(titleMenu.getRightSecondText());
        } else {
            textView2.setVisibility(8);
        }
        ImageView imageView = (ImageView) titleBar.findViewById(R.id.rightFirstDrawable);
        if (titleMenu.getRightFirstDrawable() != -1) {
            imageView.setVisibility(0);
            imageView.setImageResource(titleMenu.getRightFirstDrawable());
        } else {
            imageView.setVisibility(8);
        }
        ImageView imageView2 = (ImageView) titleBar.findViewById(R.id.rightSecondDrawable);
        if (titleMenu.getRightSecondDrawable() == -1) {
            imageView2.setVisibility(8);
        } else {
            imageView2.setVisibility(0);
            imageView2.setImageResource(titleMenu.getRightSecondDrawable());
        }
    }

    private final void initListener(LayoutTitleBarBinding titleBarBinding) {
        titleBarBinding.backIcon.setOnClickListener(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void initTitleBar$lambda$1$lambda$0(RelativeLayout it, int i2) {
        Intrinsics.checkNotNullParameter(it, "$it");
        ViewGroup.LayoutParams layoutParams = it.getLayoutParams();
        layoutParams.height = i2;
        it.setLayoutParams(layoutParams);
    }

    public final void initTitleBar(@NotNull View view) throws Resources.NotFoundException {
        TextView textView;
        Intrinsics.checkNotNullParameter(view, "view");
        LayoutTitleBarBinding layoutTitleBarBindingBind = LayoutTitleBarBinding.bind(view);
        Intrinsics.checkNotNullExpressionValue(layoutTitleBarBindingBind, "bind(view)");
        final RelativeLayout it = layoutTitleBarBindingBind.titleBar;
        final int dimensionPixelOffset = this.activity.getResources().getDimensionPixelOffset(this.globalConfig.getTitleHeight());
        it.post(new Runnable() { // from class: s0.b
            @Override // java.lang.Runnable
            public final void run() {
                TitleBarController.initTitleBar$lambda$1$lambda$0(it, dimensionPixelOffset);
            }
        });
        it.setVisibility(0);
        initListener(layoutTitleBarBindingBind);
        Intrinsics.checkNotNullExpressionValue(it, "it");
        drawBackground(it);
        drawTextColor(layoutTitleBarBindingBind);
        ImageView imageView = layoutTitleBarBindingBind.backIcon;
        Intrinsics.checkNotNullExpressionValue(imageView, "titleBarBinding.backIcon");
        drawBackIcon(imageView);
        if (this.title.getValue() != -1 && (textView = layoutTitleBarBindingBind.titleText) != null) {
            textView.setText(this.title.getValue());
        }
        View view2 = layoutTitleBarBindingBind.line;
        Intrinsics.checkNotNullExpressionValue(view2, "titleBarBinding.line");
        drawLine(view2);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(@Nullable View view) {
        Intrinsics.checkNotNull(view);
        if (view.getId() == R.id.back_icon) {
            this.activity.finish();
        }
    }
}
