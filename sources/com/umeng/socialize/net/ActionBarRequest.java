package com.umeng.socialize.net;

import android.content.Context;
import android.text.TextUtils;
import android.util.Base64;
import com.umeng.socialize.Config;
import com.umeng.socialize.net.base.SocializeRequest;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import com.umeng.socialize.net.utils.URequest;
import com.umeng.socialize.utils.SocializeUtils;

/* loaded from: classes6.dex */
public class ActionBarRequest extends SocializeRequest {

    /* renamed from: a, reason: collision with root package name */
    private static final String f23736a = "/bar/get/";

    /* renamed from: b, reason: collision with root package name */
    private static final int f23737b = 1;

    /* renamed from: c, reason: collision with root package name */
    private int f23738c;

    /* JADX WARN: Illegal instructions before constructor call */
    public ActionBarRequest(Context context, boolean z2) {
        URequest.RequestMethod requestMethod = URequest.RequestMethod.GET;
        super(context, "", ActionBarResponse.class, 1, requestMethod);
        this.mContext = context;
        this.f23738c = z2 ? 1 : 0;
        this.mMethod = requestMethod;
    }

    @Override // com.umeng.socialize.net.utils.URequest
    public String getEcryptString(String str) {
        return Base64.encodeToString(str.getBytes(), 2);
    }

    @Override // com.umeng.socialize.net.base.SocializeRequest
    public String getPath() {
        return f23736a + SocializeUtils.getAppkey(this.mContext) + "/android";
    }

    @Override // com.umeng.socialize.net.base.SocializeRequest, com.umeng.socialize.net.utils.URequest
    public void onPrepareRequest() {
        addStringParams(SocializeProtocolConstants.PROTOCOL_KEY_DESCRIPTOR, Config.Descriptor);
        addStringParams(SocializeProtocolConstants.PROTOCOL_KEY_NEW_INSTALL, String.valueOf(this.f23738c));
        if (TextUtils.isEmpty(Config.EntityName)) {
            return;
        }
        addStringParams(SocializeProtocolConstants.PROTOCOL_KEY_ENTITY_NAME, Config.EntityName);
    }
}
