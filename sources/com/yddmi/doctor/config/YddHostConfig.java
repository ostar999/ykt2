package com.yddmi.doctor.config;

import com.alipay.sdk.cons.c;
import com.catchpig.mvvm.utils.DataStoreUtils;
import com.catchpig.utils.ext.LogExtKt;
import com.yddmi.doctor.BuildConfig;
import com.yddmi.doctor.network.api.YddApi;
import com.yddmi.doctor.repository.YddClinicRepository;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsJVMKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0003\u0018\u0000 !2\u00020\u0001:\u0002!\"B\u0005¢\u0006\u0002\u0010\u0002J\u000e\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\nJ\u0006\u0010\u000f\u001a\u00020\u0004J\u0006\u0010\u0010\u001a\u00020\u0004J\u0006\u0010\u0011\u001a\u00020\nJ\u0006\u0010\u0012\u001a\u00020\u0013J\u0010\u0010\u0014\u001a\u00020\u00042\b\u0010\u0015\u001a\u0004\u0018\u00010\u0004J\u0010\u0010\u0016\u001a\u00020\u00042\b\u0010\u0015\u001a\u0004\u0018\u00010\u0004J\u0006\u0010\u0017\u001a\u00020\u0004J\u0006\u0010\u0018\u001a\u00020\u0004J\b\u0010\u0019\u001a\u00020\rH\u0002J\u000e\u0010\u001a\u001a\u00020\r2\u0006\u0010\u001b\u001a\u00020\u0004J\u0006\u0010\u001c\u001a\u00020\u0004J\u0006\u0010\u001d\u001a\u00020\u0004J\u0006\u0010\u001e\u001a\u00020\u0004J\b\u0010\u001f\u001a\u00020 H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006#"}, d2 = {"Lcom/yddmi/doctor/config/YddHostConfig;", "", "()V", "aiuiAppId", "", "aiuiAppKey", "hostBaseUrl", "hostFile", "hostPrivateFile", "hostType", "Lcom/yddmi/doctor/config/YddHostConfig$YddHost;", "hostWebBaseUrl", "dealChangeHost", "", c.f3231f, "getAiuiAppId", "getAiuiAppKey", "getCurrentHost", "getCurrentHostNumber", "", "getFileFullPrivateUrl", "url", "getFileFullUrl", "getWebBaseUrl", "serviceBaseGet", "serviceFilePrivate", "servicePrivate", "serviceUrl", "servicePrivateApiGet", "servicePrivateGet", "servicePrivateGetSp", "serviceYunan", "", "Companion", "YddHost", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class YddHostConfig {

    /* renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion INSTANCE = new Companion(null);

    @NotNull
    private static final Lazy<YddHostConfig> instance$delegate = LazyKt__LazyJVMKt.lazy(LazyThreadSafetyMode.SYNCHRONIZED, (Function0) new Function0<YddHostConfig>() { // from class: com.yddmi.doctor.config.YddHostConfig$Companion$instance$2
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        @NotNull
        public final YddHostConfig invoke() {
            return new YddHostConfig();
        }
    });

    @NotNull
    private YddHost hostType;

    @NotNull
    private volatile String hostBaseUrl = YddApi.baseUrlFormal;

    @NotNull
    private volatile String hostWebBaseUrl = YddApi.baseUrlFormal;

    @NotNull
    private volatile String hostFile = YddApi.hostFileFormal;

    @NotNull
    private volatile String hostPrivateFile = YddApi.hostFileFormal;

    @NotNull
    private String aiuiAppId = YddConfig.aiuiAppId;

    @NotNull
    private String aiuiAppKey = YddConfig.aiuiAppKey;

    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u001b\u0010\u0003\u001a\u00020\u00048FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0007\u0010\b\u001a\u0004\b\u0005\u0010\u0006¨\u0006\t"}, d2 = {"Lcom/yddmi/doctor/config/YddHostConfig$Companion;", "", "()V", "instance", "Lcom/yddmi/doctor/config/YddHostConfig;", "getInstance", "()Lcom/yddmi/doctor/config/YddHostConfig;", "instance$delegate", "Lkotlin/Lazy;", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @NotNull
        public final YddHostConfig getInstance() {
            return (YddHostConfig) YddHostConfig.instance$delegate.getValue();
        }
    }

    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[YddHost.values().length];
            try {
                iArr[YddHost.DEV.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[YddHost.TEST.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[YddHost.TEST192.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[YddHost.TEST126.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[YddHost.UAT.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr[YddHost.BASE.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr[YddHost.FORMAL.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\t\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\t¨\u0006\n"}, d2 = {"Lcom/yddmi/doctor/config/YddHostConfig$YddHost;", "", "(Ljava/lang/String;I)V", "DEV", "TEST", "TEST192", "TEST126", "UAT", BuildConfig.yddHost, "BASE", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public enum YddHost {
        DEV,
        TEST,
        TEST192,
        TEST126,
        UAT,
        FORMAL,
        BASE
    }

    public YddHostConfig() {
        this.hostType = YddHost.FORMAL;
        YddHost currentHost = getCurrentHost();
        this.hostType = currentHost;
        dealChangeHost(currentHost);
    }

    private final void serviceFilePrivate() {
        String str = (String) DataStoreUtils.INSTANCE.getSyncData(YddConfig.SP_PRIVATE, "");
        if (str.length() == 0) {
            int i2 = WhenMappings.$EnumSwitchMapping$0[getCurrentHost().ordinal()];
            String str2 = YddApi.baseUrlTest192File;
            switch (i2) {
                case 1:
                    str2 = YddApi.baseUrlDevFile;
                    break;
                case 2:
                case 3:
                    break;
                case 4:
                    str2 = YddApi.baseUrlTest126File;
                    break;
                case 5:
                    str2 = YddApi.baseUrlUat192File;
                    break;
                case 6:
                    str2 = servicePrivateGet() + "file/";
                    break;
                default:
                    str2 = YddApi.hostFileFormal;
                    break;
            }
            this.hostPrivateFile = str2;
        } else {
            this.hostPrivateFile = str + "file/";
        }
        LogExtKt.logi("私有服务地址：" + str + "  私有文件地址： " + this.hostPrivateFile, YddConfig.TAG);
    }

    private final boolean serviceYunan() {
        GlobalAction globalAction = GlobalAction.INSTANCE;
        globalAction.setMYunNan(!Intrinsics.areEqual(this.hostBaseUrl, servicePrivateGet()));
        serviceFilePrivate();
        return globalAction.getMYunNan();
    }

    public final void dealChangeHost(@NotNull YddHost host) {
        Intrinsics.checkNotNullParameter(host, "host");
        LogExtKt.logi("Host设置： " + host.name(), YddConfig.TAG);
        this.hostType = host;
        DataStoreUtils.INSTANCE.putSyncData(YddConfig.SP_HOST_TYPE, host.name());
        switch (WhenMappings.$EnumSwitchMapping$0[host.ordinal()]) {
            case 1:
                this.hostFile = YddApi.baseUrlDevFile;
                this.hostPrivateFile = YddApi.baseUrlDevFile;
                this.hostBaseUrl = YddApi.baseUrlDev;
                this.hostWebBaseUrl = YddApi.baseUrlDev;
                this.aiuiAppId = YddConfig.aiuiAppIdTest;
                this.aiuiAppKey = YddConfig.aiuiAppKeyTest;
                break;
            case 2:
                this.hostFile = YddApi.baseUrlTest;
                this.hostPrivateFile = YddApi.baseUrlTest;
                this.hostBaseUrl = YddApi.baseUrlTest;
                this.hostWebBaseUrl = YddApi.baseUrlTest;
                this.aiuiAppId = YddConfig.aiuiAppIdTest;
                this.aiuiAppKey = YddConfig.aiuiAppKeyTest;
                break;
            case 3:
                this.hostFile = YddApi.baseUrlTest192File;
                this.hostPrivateFile = YddApi.baseUrlTest192File;
                this.hostBaseUrl = YddApi.baseUrlTest192;
                this.hostWebBaseUrl = YddApi.baseUrlTest192;
                this.aiuiAppId = YddConfig.aiuiAppIdTest;
                this.aiuiAppKey = YddConfig.aiuiAppKeyTest;
                break;
            case 4:
                this.hostFile = YddApi.baseUrlTest126File;
                this.hostPrivateFile = YddApi.baseUrlTest126File;
                this.hostBaseUrl = YddApi.baseUrlTest126;
                this.hostWebBaseUrl = YddApi.baseUrlTest126;
                this.aiuiAppId = YddConfig.aiuiAppIdTest;
                this.aiuiAppKey = YddConfig.aiuiAppKeyTest;
                break;
            case 5:
                this.hostFile = YddApi.baseUrlUat192File;
                this.hostPrivateFile = YddApi.baseUrlUat;
                this.hostBaseUrl = YddApi.baseUrlUat;
                this.hostWebBaseUrl = YddApi.baseUrlUat;
                this.aiuiAppId = YddConfig.aiuiAppIdTest;
                this.aiuiAppKey = YddConfig.aiuiAppKeyTest;
                break;
            case 6:
                YddApi yddApi = YddApi.INSTANCE;
                yddApi.setBaseUrl(servicePrivateApiGet());
                yddApi.setBaseUrlFile(servicePrivateGet() + "file/");
                this.hostFile = yddApi.getBaseUrlFile();
                this.hostPrivateFile = yddApi.getBaseUrlFile();
                this.hostBaseUrl = yddApi.getBaseUrl();
                this.hostWebBaseUrl = servicePrivateGet();
                this.aiuiAppId = YddConfig.aiuiAppId;
                this.aiuiAppKey = YddConfig.aiuiAppKey;
                break;
            default:
                this.hostFile = YddApi.hostFileFormal;
                this.hostPrivateFile = YddApi.hostFileFormal;
                this.hostBaseUrl = YddApi.baseUrlFormal;
                this.hostWebBaseUrl = YddApi.baseUrlFormal;
                this.aiuiAppId = YddConfig.aiuiAppId;
                this.aiuiAppKey = YddConfig.aiuiAppKey;
                break;
        }
        serviceYunan();
        YddClinicRepository.INSTANCE.dealChangeBaseUrl(host);
    }

    @NotNull
    public final String getAiuiAppId() {
        return this.aiuiAppId;
    }

    @NotNull
    public final String getAiuiAppKey() {
        return this.aiuiAppKey;
    }

    @NotNull
    public final YddHost getCurrentHost() {
        String str = (String) DataStoreUtils.INSTANCE.getSyncData(YddConfig.SP_HOST_TYPE, "");
        if (str != null) {
            str.length();
        }
        return YddHost.FORMAL;
    }

    public final int getCurrentHostNumber() {
        int i2 = WhenMappings.$EnumSwitchMapping$0[getCurrentHost().ordinal()];
        if (i2 == 1 || i2 == 2) {
            return 100;
        }
        if (i2 == 3) {
            return 101;
        }
        if (i2 != 4) {
            return i2 != 5 ? 200 : 103;
        }
        return 102;
    }

    @NotNull
    public final String getFileFullPrivateUrl(@Nullable String url) {
        if (url == null || url.length() == 0) {
            return "";
        }
        if (StringsKt__StringsJVMKt.startsWith$default(url, "http", false, 2, null)) {
            return url;
        }
        return this.hostPrivateFile + url;
    }

    @NotNull
    public final String getFileFullUrl(@Nullable String url) {
        if (url == null || url.length() == 0) {
            return "";
        }
        if (StringsKt__StringsJVMKt.startsWith$default(url, "http", false, 2, null)) {
            return url;
        }
        return this.hostFile + url;
    }

    @NotNull
    /* renamed from: getWebBaseUrl, reason: from getter */
    public final String getHostWebBaseUrl() {
        return this.hostWebBaseUrl;
    }

    @NotNull
    /* renamed from: serviceBaseGet, reason: from getter */
    public final String getHostBaseUrl() {
        return this.hostBaseUrl;
    }

    public final void servicePrivate(@NotNull String serviceUrl) {
        Intrinsics.checkNotNullParameter(serviceUrl, "serviceUrl");
        DataStoreUtils.INSTANCE.putSyncData(YddConfig.SP_PRIVATE, serviceUrl);
        serviceFilePrivate();
        serviceYunan();
        YddClinicRepository.INSTANCE.dealOtherService();
    }

    @NotNull
    public final String servicePrivateApiGet() {
        StringBuilder sb;
        String str = (String) DataStoreUtils.INSTANCE.getSyncData(YddConfig.SP_PRIVATE, "");
        if (str.length() == 0) {
            str = this.hostBaseUrl;
            sb = new StringBuilder();
        } else {
            sb = new StringBuilder();
        }
        sb.append(str);
        sb.append(YddApi.gateWay);
        return sb.toString();
    }

    @NotNull
    public final String servicePrivateGet() {
        String str = (String) DataStoreUtils.INSTANCE.getSyncData(YddConfig.SP_PRIVATE, "");
        return str.length() == 0 ? this.hostBaseUrl : str;
    }

    @NotNull
    public final String servicePrivateGetSp() {
        return (String) DataStoreUtils.INSTANCE.getSyncData(YddConfig.SP_PRIVATE, "");
    }
}
