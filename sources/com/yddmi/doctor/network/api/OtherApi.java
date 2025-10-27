package com.yddmi.doctor.network.api;

import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000¨\u0006\u0007"}, d2 = {"Lcom/yddmi/doctor/network/api/OtherApi;", "", "()V", "wxBaseUrl", "", "wxGetAccessToken", "wxGetUserInfo", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class OtherApi {

    @NotNull
    public static final OtherApi INSTANCE = new OtherApi();

    @NotNull
    public static final String wxBaseUrl = "https://api.weixin.qq.com/sns/";

    @NotNull
    public static final String wxGetAccessToken = "oauth2/access_token?appid=wx35f164d814467bdc&secret=765778627e1a063fcc0ba715d0f7f345&grant_type=authorization_code";

    @NotNull
    public static final String wxGetUserInfo = "userinfo";

    private OtherApi() {
    }
}
