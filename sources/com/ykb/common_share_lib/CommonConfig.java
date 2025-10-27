package com.ykb.common_share_lib;

import android.content.SharedPreferences;
import com.aliyun.vod.log.struct.AliyunLogKey;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\b\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R(\u0010\u0005\u001a\u0004\u0018\u00010\u00042\b\u0010\u0003\u001a\u0004\u0018\u00010\u00048F@FX\u0086\u000e¢\u0006\f\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR(\u0010\n\u001a\u0004\u0018\u00010\u00042\b\u0010\u0003\u001a\u0004\u0018\u00010\u00048F@FX\u0086\u000e¢\u0006\f\u001a\u0004\b\u000b\u0010\u0007\"\u0004\b\f\u0010\tR\u000e\u0010\r\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R(\u0010\u000e\u001a\u0004\u0018\u00010\u00042\b\u0010\u0003\u001a\u0004\u0018\u00010\u00048F@FX\u0086\u000e¢\u0006\f\u001a\u0004\b\u000f\u0010\u0007\"\u0004\b\u0010\u0010\tR$\u0010\u0012\u001a\u00020\u00112\u0006\u0010\u0003\u001a\u00020\u00118F@FX\u0086\u000e¢\u0006\f\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R$\u0010\u0018\u001a\u00020\u00172\u0006\u0010\u0003\u001a\u00020\u00178F@FX\u0086\u000e¢\u0006\f\u001a\u0004\b\u0019\u0010\u001a\"\u0004\b\u001b\u0010\u001cR(\u0010\u001d\u001a\u0004\u0018\u00010\u00042\b\u0010\u0003\u001a\u0004\u0018\u00010\u00048F@FX\u0086\u000e¢\u0006\f\u001a\u0004\b\u001e\u0010\u0007\"\u0004\b\u001f\u0010\tR\u001b\u0010 \u001a\u00020!8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b$\u0010%\u001a\u0004\b\"\u0010#R(\u0010&\u001a\u0004\u0018\u00010\u00042\b\u0010\u0003\u001a\u0004\u0018\u00010\u00048F@FX\u0086\u000e¢\u0006\f\u001a\u0004\b'\u0010\u0007\"\u0004\b(\u0010\t¨\u0006)"}, d2 = {"Lcom/ykb/common_share_lib/CommonConfig;", "", "()V", "value", "", "ApplicationId", "getApplicationId", "()Ljava/lang/String;", "setApplicationId", "(Ljava/lang/String;)V", "FLAVOR", "getFLAVOR", "setFLAVOR", "PREF_NAME", "User_Agent", "getUser_Agent", "setUser_Agent", "", "YI_KAO_BANG", "getYI_KAO_BANG", "()Z", "setYI_KAO_BANG", "(Z)V", "", "YI_KAO_BANG_NETWORK", "getYI_KAO_BANG_NETWORK", "()I", "setYI_KAO_BANG_NETWORK", "(I)V", "app_version", "getApp_version", "setApp_version", "prefs", "Landroid/content/SharedPreferences;", "getPrefs", "()Landroid/content/SharedPreferences;", "prefs$delegate", "Lkotlin/Lazy;", AliyunLogKey.KEY_UUID, "getUuid", "setUuid", "common_share_lib_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes4.dex */
public final class CommonConfig {

    @NotNull
    private static final String PREF_NAME = "common_config";

    @NotNull
    public static final CommonConfig INSTANCE = new CommonConfig();

    /* renamed from: prefs$delegate, reason: from kotlin metadata */
    @NotNull
    private static final Lazy prefs = LazyKt__LazyJVMKt.lazy(new Function0<SharedPreferences>() { // from class: com.ykb.common_share_lib.CommonConfig$prefs$2
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        @NotNull
        public final SharedPreferences invoke() {
            return GlobalApplication.INSTANCE.getInstance().getSharedPreferences("common_config", 0);
        }
    });

    private CommonConfig() {
    }

    private final SharedPreferences getPrefs() {
        return (SharedPreferences) prefs.getValue();
    }

    @Nullable
    public final String getApp_version() {
        return getPrefs().getString("CommonConfig_app_version", "");
    }

    @Nullable
    public final String getApplicationId() {
        return getPrefs().getString("APPLICATION_ID", "");
    }

    @Nullable
    public final String getFLAVOR() {
        return getPrefs().getString("FLAVOR", "");
    }

    @Nullable
    public final String getUser_Agent() {
        return getPrefs().getString("CommonConfig_User_Agent", "");
    }

    @Nullable
    public final String getUuid() {
        return getPrefs().getString("CommonConfig_uuid", "");
    }

    public final boolean getYI_KAO_BANG() {
        return getPrefs().getBoolean("YI_KAO_BANG", false);
    }

    public final int getYI_KAO_BANG_NETWORK() {
        return getPrefs().getInt("YI_KAO_BANG_NETWORK", 2);
    }

    public final void setApp_version(@Nullable String str) {
        getPrefs().edit().putString("CommonConfig_app_version", str).apply();
    }

    public final void setApplicationId(@Nullable String str) {
        getPrefs().edit().putString("APPLICATION_ID", str).apply();
    }

    public final void setFLAVOR(@Nullable String str) {
        getPrefs().edit().putString("FLAVOR", str).apply();
    }

    public final void setUser_Agent(@Nullable String str) {
        getPrefs().edit().putString("CommonConfig_User_Agent", str).apply();
    }

    public final void setUuid(@Nullable String str) {
        getPrefs().edit().putString("CommonConfig_uuid", str).apply();
    }

    public final void setYI_KAO_BANG(boolean z2) {
        getPrefs().edit().putBoolean("YI_KAO_BANG", z2).apply();
    }

    public final void setYI_KAO_BANG_NETWORK(int i2) {
        getPrefs().edit().putInt("YI_KAO_BANG_NETWORK", i2).apply();
    }
}
