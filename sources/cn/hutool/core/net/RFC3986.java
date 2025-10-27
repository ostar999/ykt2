package cn.hutool.core.net;

import cn.hutool.core.codec.PercentCodec;

/* loaded from: classes.dex */
public class RFC3986 {
    public static final PercentCodec FRAGMENT;
    public static final PercentCodec GEN_DELIMS;
    public static final PercentCodec PATH;
    public static final PercentCodec PCHAR;
    public static final PercentCodec QUERY;
    public static final PercentCodec QUERY_PARAM_NAME;
    public static final PercentCodec QUERY_PARAM_NAME_STRICT;
    public static final PercentCodec QUERY_PARAM_VALUE;
    public static final PercentCodec QUERY_PARAM_VALUE_STRICT;
    public static final PercentCodec RESERVED;
    public static final PercentCodec SEGMENT;
    public static final PercentCodec SEGMENT_NZ_NC;
    public static final PercentCodec SUB_DELIMS;
    public static final PercentCodec UNRESERVED;

    static {
        PercentCodec percentCodecOf = PercentCodec.of(":/?#[]@");
        GEN_DELIMS = percentCodecOf;
        PercentCodec percentCodecOf2 = PercentCodec.of("!$&'()*+,;=");
        SUB_DELIMS = percentCodecOf2;
        RESERVED = percentCodecOf.orNew(percentCodecOf2);
        PercentCodec percentCodecOf3 = PercentCodec.of(unreservedChars());
        UNRESERVED = percentCodecOf3;
        PercentCodec percentCodecOr = percentCodecOf3.orNew(percentCodecOf2).or(PercentCodec.of(":@"));
        PCHAR = percentCodecOr;
        SEGMENT = percentCodecOr;
        SEGMENT_NZ_NC = PercentCodec.of(percentCodecOr).removeSafe(':');
        PATH = percentCodecOr.orNew(PercentCodec.of("/"));
        PercentCodec percentCodecOrNew = percentCodecOr.orNew(PercentCodec.of("/?"));
        QUERY = percentCodecOrNew;
        FRAGMENT = percentCodecOrNew;
        PercentCodec percentCodecRemoveSafe = PercentCodec.of(percentCodecOrNew).removeSafe('&');
        QUERY_PARAM_VALUE = percentCodecRemoveSafe;
        QUERY_PARAM_VALUE_STRICT = percentCodecOf3;
        QUERY_PARAM_NAME = PercentCodec.of(percentCodecRemoveSafe).removeSafe('=');
        QUERY_PARAM_NAME_STRICT = percentCodecOf3;
    }

    private static StringBuilder unreservedChars() {
        StringBuilder sb = new StringBuilder();
        for (char c3 = 'A'; c3 <= 'Z'; c3 = (char) (c3 + 1)) {
            sb.append(c3);
        }
        for (char c4 = 'a'; c4 <= 'z'; c4 = (char) (c4 + 1)) {
            sb.append(c4);
        }
        for (char c5 = '0'; c5 <= '9'; c5 = (char) (c5 + 1)) {
            sb.append(c5);
        }
        sb.append("_.-~");
        return sb;
    }
}
