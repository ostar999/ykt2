package com.catchpig.mvvm.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u0019\u0010\t\u001a\n \u000b*\u0004\u0018\u00010\n0\n¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r¨\u0006\u000e"}, d2 = {"Lcom/catchpig/mvvm/config/Config;", "", "()V", "DATA_STORE_NAME", "", "DATE_FORMAT", "NO_ASSIGNMENT", "", "X5_FILE_LICENSE_KEY", "gson", "Lcom/google/gson/Gson;", "kotlin.jvm.PlatformType", "getGson", "()Lcom/google/gson/Gson;", "mvvm_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes2.dex */
public final class Config {

    @NotNull
    public static final String DATA_STORE_NAME = "data_preferences";

    @NotNull
    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final int NO_ASSIGNMENT = -1;

    @NotNull
    public static final String X5_FILE_LICENSE_KEY = "RDuYdaq3B8Qk3+IE6H/4w9vr5eny1boQibcd4wGxY5WsQUV3nYGhl3OlnnCz0dw2";

    @NotNull
    public static final Config INSTANCE = new Config();
    private static final Gson gson = new GsonBuilder().create();

    private Config() {
    }

    public final Gson getGson() {
        return gson;
    }
}
