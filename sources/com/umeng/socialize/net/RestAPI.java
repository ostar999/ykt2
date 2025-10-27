package com.umeng.socialize.net;

import com.umeng.socialize.net.analytics.AnalyticsReqeust;
import com.umeng.socialize.net.analytics.AnalyticsResponse;
import com.umeng.socialize.net.base.SocializeClient;

/* loaded from: classes6.dex */
public class RestAPI {

    /* renamed from: a, reason: collision with root package name */
    private static SocializeClient f23742a = new SocializeClient();

    public static LinkCardResponse convertLinkCard(LinkcardRequest linkcardRequest) {
        return (LinkCardResponse) f23742a.execute(linkcardRequest);
    }

    public static AnalyticsResponse doShareByRequest(AnalyticsReqeust analyticsReqeust) {
        return (AnalyticsResponse) f23742a.execute(analyticsReqeust);
    }

    public static ActionBarResponse queryShareId(ActionBarRequest actionBarRequest) {
        return (ActionBarResponse) f23742a.execute(actionBarRequest);
    }
}
