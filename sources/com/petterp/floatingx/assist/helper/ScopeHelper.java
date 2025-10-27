package com.petterp.floatingx.assist.helper;

import android.app.Activity;
import android.view.View;
import android.widget.FrameLayout;
import androidx.fragment.app.Fragment;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.petterp.floatingx.assist.helper.BasisHelper;
import com.petterp.floatingx.impl.control.FxScopeControl;
import com.petterp.floatingx.listener.control.IFxScopeControl;
import com.petterp.floatingx.util.FxLog;
import com.petterp.floatingx.util.FxScopeEnum;
import com.petterp.floatingx.util.FxUiExtKt;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u0000 \f2\u00020\u0001:\u0002\u000b\fB\u0005¢\u0006\u0002\u0010\u0002J\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0006\u001a\u00020\u0005J\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00070\u00042\u0006\u0010\b\u001a\u00020\u0007J\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\t0\u00042\u0006\u0010\n\u001a\u00020\t¨\u0006\r"}, d2 = {"Lcom/petterp/floatingx/assist/helper/ScopeHelper;", "Lcom/petterp/floatingx/assist/helper/BasisHelper;", "()V", "toControl", "Lcom/petterp/floatingx/listener/control/IFxScopeControl;", "Landroid/app/Activity;", PushConstants.INTENT_ACTIVITY_NAME, "Landroid/widget/FrameLayout;", "group", "Landroidx/fragment/app/Fragment;", "fragment", "Builder", "Companion", "floatingx_release"}, k = 1, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes4.dex */
public final class ScopeHelper extends BasisHelper {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0000\u0012\u0004\u0012\u00020\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0003J\b\u0010\u0004\u001a\u00020\u0002H\u0014¨\u0006\u0005"}, d2 = {"Lcom/petterp/floatingx/assist/helper/ScopeHelper$Builder;", "Lcom/petterp/floatingx/assist/helper/BasisHelper$Builder;", "Lcom/petterp/floatingx/assist/helper/ScopeHelper;", "()V", "buildHelper", "floatingx_release"}, k = 1, mv = {1, 5, 1}, xi = 48)
    public static final class Builder extends BasisHelper.Builder<Builder, ScopeHelper> {
        @Override // com.petterp.floatingx.assist.helper.BasisHelper.Builder
        @NotNull
        public ScopeHelper buildHelper() {
            return new ScopeHelper();
        }
    }

    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J%\u0010\u0003\u001a\u00020\u00042\u0017\u0010\u0005\u001a\u0013\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0006¢\u0006\u0002\b\tH\u0086\bø\u0001\u0000J\b\u0010\n\u001a\u00020\u0007H\u0007\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\u000b"}, d2 = {"Lcom/petterp/floatingx/assist/helper/ScopeHelper$Companion;", "", "()V", "build", "Lcom/petterp/floatingx/assist/helper/ScopeHelper;", "obj", "Lkotlin/Function1;", "Lcom/petterp/floatingx/assist/helper/ScopeHelper$Builder;", "", "Lkotlin/ExtensionFunctionType;", "builder", "floatingx_release"}, k = 1, mv = {1, 5, 1}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final /* synthetic */ ScopeHelper build(Function1<? super Builder, Unit> obj) {
            Intrinsics.checkNotNullParameter(obj, "obj");
            Builder builder = builder();
            obj.invoke(builder);
            return builder.build();
        }

        @JvmStatic
        @NotNull
        public final Builder builder() {
            return new Builder();
        }
    }

    @JvmStatic
    @NotNull
    public static final Builder builder() {
        return INSTANCE.builder();
    }

    @NotNull
    public final IFxScopeControl<Activity> toControl(@NotNull Activity activity) {
        Unit unit;
        FxLog fxLog;
        Intrinsics.checkNotNullParameter(activity, "activity");
        initLog$floatingx_release(FxScopeEnum.ACTIVITY_SCOPE.getTag());
        FxScopeControl fxScopeControl = new FxScopeControl(this);
        FrameLayout contentView = FxUiExtKt.getContentView(activity);
        if (contentView == null) {
            unit = null;
        } else {
            fxScopeControl.setContainerGroup$floatingx_release(contentView);
            unit = Unit.INSTANCE;
        }
        if (unit == null && (fxLog = this.fxLog) != null) {
            fxLog.e("install to Activity the Error,current contentView(R.id.content) = null!");
        }
        return fxScopeControl;
    }

    @NotNull
    public final IFxScopeControl<Fragment> toControl(@NotNull Fragment fragment) {
        Intrinsics.checkNotNullParameter(fragment, "fragment");
        initLog$floatingx_release(FxScopeEnum.FRAGMENT_SCOPE.getTag());
        View view = fragment.getView();
        FrameLayout frameLayout = view instanceof FrameLayout ? (FrameLayout) view : null;
        if (frameLayout != null) {
            FxScopeControl fxScopeControl = new FxScopeControl(this);
            fxScopeControl.setContainerGroup$floatingx_release(frameLayout);
            return fxScopeControl;
        }
        throw new IllegalStateException("Check if your root layout is FrameLayout, or if the init call timing is after onCreateView()!".toString());
    }

    @NotNull
    public final IFxScopeControl<FrameLayout> toControl(@NotNull FrameLayout group) {
        Intrinsics.checkNotNullParameter(group, "group");
        initLog$floatingx_release(FxScopeEnum.VIEW_GROUP_SCOPE.getTag());
        FxScopeControl fxScopeControl = new FxScopeControl(this);
        fxScopeControl.setContainerGroup$floatingx_release(group);
        return fxScopeControl;
    }
}
