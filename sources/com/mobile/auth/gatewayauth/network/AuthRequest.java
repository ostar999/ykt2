package com.mobile.auth.gatewayauth.network;

import com.aliyun.auth.core.AliyunVodKey;
import com.mobile.auth.v.c;
import com.nirvana.tools.jsoner.JsonerTag;

/* loaded from: classes4.dex */
public class AuthRequest extends c {

    @JsonerTag(keyName = "Version")
    private String Version = "2017-05-25";

    @JsonerTag(keyName = AliyunVodKey.KEY_VOD_COMMON_FORMAT)
    private String Format = "JSON";
}
