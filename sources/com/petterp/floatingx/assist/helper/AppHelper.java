package com.petterp.floatingx.assist.helper;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.mobile.auth.gatewayauth.Constant;
import com.petterp.floatingx.FloatingX;
import com.petterp.floatingx.assist.helper.BasisHelper;
import com.petterp.floatingx.listener.IFxProxyTagActivityLifecycle;
import com.petterp.floatingx.util.FxLog;
import com.petterp.floatingx.util.FxScopeEnum;
import com.petterp.floatingx.util.FxScreenExtKt;
import com.tencent.open.SocialConstants;
import com.umeng.analytics.pro.am;
import com.umeng.analytics.pro.d;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt__MutableCollectionsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0007\u0018\u0000 $2\u00020\u0001:\u0002#$BC\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0010\u0010\u0004\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00060\u0005\u0012\u0010\u0010\u0007\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00060\u0005\u0012\u0006\u0010\b\u001a\u00020\t\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u000b¢\u0006\u0002\u0010\fJ\u0015\u0010\u0018\u001a\u00020\t2\u0006\u0010\u0019\u001a\u00020\u001aH\u0000¢\u0006\u0002\b\u001bJ\u0019\u0010\u0018\u001a\u00020\t2\n\u0010\u001c\u001a\u0006\u0012\u0002\b\u00030\u0006H\u0000¢\u0006\u0002\b\u001bJ\u0017\u0010\u001d\u001a\u00020\u001e2\b\u0010\u001f\u001a\u0004\u0018\u00010\u001aH\u0000¢\u0006\u0002\b J\u0017\u0010!\u001a\u00020\u001e2\b\u0010\u001f\u001a\u0004\u0018\u00010\u001aH\u0000¢\u0006\u0002\b\"R\u001e\u0010\u0004\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00060\u0005X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0016\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0014\u0010\b\u001a\u00020\tX\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u001a\u0010\u0002\u001a\u00020\u0003X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R\u001e\u0010\u0007\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00060\u0005X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u000e¨\u0006%"}, d2 = {"Lcom/petterp/floatingx/assist/helper/AppHelper;", "Lcom/petterp/floatingx/assist/helper/BasisHelper;", "tag", "", "blackFilterList", "", "Ljava/lang/Class;", "whiteInsertList", "isAllInstall", "", "fxLifecycleExpand", "Lcom/petterp/floatingx/listener/IFxProxyTagActivityLifecycle;", "(Ljava/lang/String;Ljava/util/List;Ljava/util/List;ZLcom/petterp/floatingx/listener/IFxProxyTagActivityLifecycle;)V", "getBlackFilterList$floatingx_release", "()Ljava/util/List;", "getFxLifecycleExpand$floatingx_release", "()Lcom/petterp/floatingx/listener/IFxProxyTagActivityLifecycle;", "isAllInstall$floatingx_release", "()Z", "getTag$floatingx_release", "()Ljava/lang/String;", "setTag$floatingx_release", "(Ljava/lang/String;)V", "getWhiteInsertList$floatingx_release", "isCanInstall", SocialConstants.PARAM_ACT, "Landroid/app/Activity;", "isCanInstall$floatingx_release", "cls", "updateNavigationBar", "", PushConstants.INTENT_ACTIVITY_NAME, "updateNavigationBar$floatingx_release", "updateStatsBar", "updateStatsBar$floatingx_release", "Builder", "Companion", "floatingx_release"}, k = 1, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes4.dex */
public final class AppHelper extends BasisHelper {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);
    private final /* synthetic */ List<Class<?>> blackFilterList;
    private final /* synthetic */ IFxProxyTagActivityLifecycle fxLifecycleExpand;
    private final /* synthetic */ boolean isAllInstall;
    private /* synthetic */ String tag;
    private final /* synthetic */ List<Class<?>> whiteInsertList;

    @Metadata(d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0000\u0012\u0004\u0012\u00020\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0003J/\u0010\u000f\u001a\u00020\u00002\"\u0010\u0010\u001a\u0012\u0012\u000e\b\u0001\u0012\n\u0012\u0006\b\u0001\u0012\u00020\u00120\u00060\u0011\"\n\u0012\u0006\b\u0001\u0012\u00020\u00120\u0006¢\u0006\u0002\u0010\u0013J\u001c\u0010\u000f\u001a\u00020\u00002\u0014\u0010\u0014\u001a\u0010\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00020\u00120\u00060\u0015J/\u0010\u0016\u001a\u00020\u00002\"\u0010\u0010\u001a\u0012\u0012\u000e\b\u0001\u0012\n\u0012\u0006\b\u0001\u0012\u00020\u00120\u00060\u0011\"\n\u0012\u0006\b\u0001\u0012\u00020\u00120\u0006¢\u0006\u0002\u0010\u0013J\u001c\u0010\u0016\u001a\u00020\u00002\u0014\u0010\u0014\u001a\u0010\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00020\u00120\u00060\u0015J\b\u0010\u0017\u001a\u00020\u0002H\u0016J\b\u0010\u0018\u001a\u00020\u0002H\u0014J\u0006\u0010\u0007\u001a\u00020\u0000J\u000e\u0010\u0019\u001a\u00020\u00002\u0006\u0010\u001a\u001a\u00020\u001bJ\u000e\u0010\u001c\u001a\u00020\u00002\u0006\u0010\u001d\u001a\u00020\bJ\u000e\u0010\u001e\u001a\u00020\u00002\u0006\u0010\f\u001a\u00020\rJ\u000e\u0010\u001f\u001a\u00020\u00002\u0006\u0010 \u001a\u00020\nR\u0018\u0010\u0004\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00060\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u0004\u0018\u00010\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u000e¢\u0006\u0002\n\u0000R\u0018\u0010\u000e\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00060\u0005X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006!"}, d2 = {"Lcom/petterp/floatingx/assist/helper/AppHelper$Builder;", "Lcom/petterp/floatingx/assist/helper/BasisHelper$Builder;", "Lcom/petterp/floatingx/assist/helper/AppHelper;", "()V", "blackFilterList", "", "Ljava/lang/Class;", "enableFx", "", "fxLifecycleExpand", "Lcom/petterp/floatingx/listener/IFxProxyTagActivityLifecycle;", "isEnableAllInstall", "tag", "", "whiteInsertList", "addInstallBlackClass", am.aF, "", "Landroid/app/Activity;", "([Ljava/lang/Class;)Lcom/petterp/floatingx/assist/helper/AppHelper$Builder;", "cls", "", "addInstallWhiteClass", "build", "buildHelper", "setContext", d.R, "Landroid/content/Context;", "setEnableAllInstall", Constant.API_PARAMS_KEY_ENABLE, "setTag", "setTagActivityLifecycle", "tagActivityLifecycle", "floatingx_release"}, k = 1, mv = {1, 5, 1}, xi = 48)
    public static final class Builder extends BasisHelper.Builder<Builder, AppHelper> {
        private boolean enableFx;

        @Nullable
        private IFxProxyTagActivityLifecycle fxLifecycleExpand;

        @NotNull
        private List<Class<?>> whiteInsertList = new ArrayList();

        @NotNull
        private List<Class<?>> blackFilterList = new ArrayList();
        private boolean isEnableAllInstall = true;

        @NotNull
        private String tag = FloatingX.FX_DEFAULT_TAG;

        @NotNull
        public final Builder addInstallBlackClass(@NotNull Class<? extends Activity>... c3) {
            Intrinsics.checkNotNullParameter(c3, "c");
            CollectionsKt__MutableCollectionsKt.addAll(this.blackFilterList, c3);
            return this;
        }

        @NotNull
        public final Builder addInstallWhiteClass(@NotNull Class<? extends Activity>... c3) {
            Intrinsics.checkNotNullParameter(c3, "c");
            CollectionsKt__MutableCollectionsKt.addAll(this.whiteInsertList, c3);
            return this;
        }

        @NotNull
        public final Builder enableFx() {
            this.enableFx = true;
            return this;
        }

        @NotNull
        public final Builder setContext(@NotNull Context context) {
            Intrinsics.checkNotNullParameter(context, "context");
            if (context instanceof Application) {
                FloatingX.INSTANCE.setContext$floatingx_release((Application) context);
            } else {
                FloatingX floatingX = FloatingX.INSTANCE;
                Context applicationContext = context.getApplicationContext();
                if (applicationContext == null) {
                    throw new NullPointerException("null cannot be cast to non-null type android.app.Application");
                }
                floatingX.setContext$floatingx_release((Application) applicationContext);
            }
            return this;
        }

        @NotNull
        public final Builder setEnableAllInstall(boolean isEnable) {
            this.isEnableAllInstall = isEnable;
            return this;
        }

        @NotNull
        public final Builder setTag(@NotNull String tag) throws IllegalArgumentException {
            Intrinsics.checkNotNullParameter(tag, "tag");
            if (tag.length() == 0) {
                throw new IllegalArgumentException("浮窗 tag 不能为 [\"\"],请设置一个合法的tag");
            }
            this.tag = tag;
            return this;
        }

        @NotNull
        public final Builder setTagActivityLifecycle(@NotNull IFxProxyTagActivityLifecycle tagActivityLifecycle) {
            Intrinsics.checkNotNullParameter(tagActivityLifecycle, "tagActivityLifecycle");
            this.fxLifecycleExpand = tagActivityLifecycle;
            return this;
        }

        @NotNull
        public final Builder addInstallBlackClass(@NotNull List<? extends Class<? extends Activity>> cls) {
            Intrinsics.checkNotNullParameter(cls, "cls");
            this.blackFilterList.addAll(cls);
            return this;
        }

        @NotNull
        public final Builder addInstallWhiteClass(@NotNull List<? extends Class<? extends Activity>> cls) {
            Intrinsics.checkNotNullParameter(cls, "cls");
            this.whiteInsertList.addAll(cls);
            return this;
        }

        @Override // com.petterp.floatingx.assist.helper.BasisHelper.Builder
        @NotNull
        public AppHelper build() {
            AppHelper appHelper = (AppHelper) super.build();
            appHelper.enableFx = this.enableFx;
            if (appHelper.enableDebugLog) {
                if (appHelper.fxLogTag.length() == 0) {
                    appHelper.fxLogTag = appHelper.getTag();
                }
            }
            appHelper.initLog$floatingx_release(FxScopeEnum.APP_SCOPE.getTag());
            return appHelper;
        }

        @Override // com.petterp.floatingx.assist.helper.BasisHelper.Builder
        @NotNull
        public AppHelper buildHelper() {
            return new AppHelper(this.tag, this.blackFilterList, this.whiteInsertList, this.isEnableAllInstall, this.fxLifecycleExpand);
        }
    }

    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0007¨\u0006\u0005"}, d2 = {"Lcom/petterp/floatingx/assist/helper/AppHelper$Companion;", "", "()V", "builder", "Lcom/petterp/floatingx/assist/helper/AppHelper$Builder;", "floatingx_release"}, k = 1, mv = {1, 5, 1}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @JvmStatic
        @NotNull
        public final Builder builder() {
            return new Builder();
        }
    }

    public AppHelper(@NotNull String tag, @NotNull List<Class<?>> blackFilterList, @NotNull List<Class<?>> whiteInsertList, boolean z2, @Nullable IFxProxyTagActivityLifecycle iFxProxyTagActivityLifecycle) {
        Intrinsics.checkNotNullParameter(tag, "tag");
        Intrinsics.checkNotNullParameter(blackFilterList, "blackFilterList");
        Intrinsics.checkNotNullParameter(whiteInsertList, "whiteInsertList");
        this.tag = tag;
        this.blackFilterList = blackFilterList;
        this.whiteInsertList = whiteInsertList;
        this.isAllInstall = z2;
        this.fxLifecycleExpand = iFxProxyTagActivityLifecycle;
    }

    @JvmStatic
    @NotNull
    public static final Builder builder() {
        return INSTANCE.builder();
    }

    @NotNull
    public final List<Class<?>> getBlackFilterList$floatingx_release() {
        return this.blackFilterList;
    }

    @Nullable
    /* renamed from: getFxLifecycleExpand$floatingx_release, reason: from getter */
    public final IFxProxyTagActivityLifecycle getFxLifecycleExpand() {
        return this.fxLifecycleExpand;
    }

    @NotNull
    /* renamed from: getTag$floatingx_release, reason: from getter */
    public final String getTag() {
        return this.tag;
    }

    @NotNull
    public final List<Class<?>> getWhiteInsertList$floatingx_release() {
        return this.whiteInsertList;
    }

    /* renamed from: isAllInstall$floatingx_release, reason: from getter */
    public final boolean getIsAllInstall() {
        return this.isAllInstall;
    }

    public final /* synthetic */ boolean isCanInstall$floatingx_release(Activity act) {
        Intrinsics.checkNotNullParameter(act, "act");
        return isCanInstall$floatingx_release(act.getClass());
    }

    public final void setTag$floatingx_release(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.tag = str;
    }

    public final /* synthetic */ void updateNavigationBar$floatingx_release(Activity activity) {
        Integer numValueOf = activity == null ? null : Integer.valueOf(FxScreenExtKt.getNavigationBarHeight(activity));
        int iIntValue = numValueOf == null ? this.navigationBarHeight : numValueOf.intValue();
        this.navigationBarHeight = iIntValue;
        FxLog fxLog = this.fxLog;
        if (fxLog == null) {
            return;
        }
        fxLog.v(Intrinsics.stringPlus("system-> navigationBar-", Integer.valueOf(iIntValue)));
    }

    public final /* synthetic */ void updateStatsBar$floatingx_release(Activity activity) {
        Integer numValueOf = activity == null ? null : Integer.valueOf(FxScreenExtKt.getStatusBarHeight(activity));
        int iIntValue = numValueOf == null ? this.statsBarHeight : numValueOf.intValue();
        this.statsBarHeight = iIntValue;
        FxLog fxLog = this.fxLog;
        if (fxLog == null) {
            return;
        }
        fxLog.v(Intrinsics.stringPlus("system-> statusBarHeight-", Integer.valueOf(iIntValue)));
    }

    public final /* synthetic */ boolean isCanInstall$floatingx_release(Class cls) {
        Intrinsics.checkNotNullParameter(cls, "cls");
        return (this.isAllInstall && !this.blackFilterList.contains(cls)) || (!this.isAllInstall && this.whiteInsertList.contains(cls));
    }
}
