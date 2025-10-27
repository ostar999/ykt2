package cn.hutool.core.net;

import cn.hutool.core.codec.PercentCodec;

/* loaded from: classes.dex */
public class FormUrlencoded {
    public static final PercentCodec ALL = PercentCodec.of(RFC3986.UNRESERVED).removeSafe('~').addSafe('*').setEncodeSpaceAsPlus(true);
}
