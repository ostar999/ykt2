package com.xiaomi.push;

import com.yikaobang.yixue.R2;

/* loaded from: classes6.dex */
public enum fr {
    TCP_CONN_FAIL(1),
    TCP_CONN_TIME(2),
    PING_RTT(3),
    CHANNEL_CON_FAIL(4),
    CHANNEL_CON_OK(5),
    ICMP_PING_FAIL(6),
    ICMP_PING_OK(7),
    CHANNEL_ONLINE_RATE(8),
    GSLB_REQUEST_SUCCESS(10000),
    GSLB_TCP_NOACCESS(10101),
    GSLB_TCP_NETUNREACH(10102),
    GSLB_TCP_CONNREFUSED(10103),
    GSLB_TCP_NOROUTETOHOST(10104),
    GSLB_TCP_TIMEOUT(10105),
    GSLB_TCP_INVALARG(10106),
    GSLB_TCP_UKNOWNHOST(10107),
    GSLB_TCP_ERR_OTHER(R2.drawable.ic_empty_data_comment_night),
    GSLB_ERR(R2.drawable.jiantou1),
    CONN_SUCCESS(20000),
    CONN_TCP_NOACCESS(R2.id.tv_shi),
    CONN_TCP_NETUNREACH(R2.id.tv_shield_sub_title),
    CONN_TCP_CONNREFUSED(R2.id.tv_shop_name),
    CONN_TCP_NOROUTETOHOST(R2.id.tv_shouhuoren),
    CONN_TCP_TIMEOUT(R2.id.tv_shouhuos),
    CONN_TCP_INVALARG(R2.id.tv_show_content),
    CONN_TCP_UKNOWNHOST(R2.id.tv_show_msg),
    CONN_TCP_ERR_OTHER(R2.id.tv_three),
    CONN_XMPP_ERR(R2.id.umeng_socialize_bind_cancel),
    CONN_BOSH_UNKNOWNHOST(R2.id.umeng_socialize_first_area_title),
    CONN_BOSH_ERR(R2.id.verimg),
    BIND_SUCCESS(30000),
    BIND_TCP_READ_TIMEOUT_DEPRECTED(R2.styleable.SwitchPreferenceCompat_summaryOff),
    BIND_TCP_CONNRESET_DEPRECTED(R2.styleable.SwitchPreferenceCompat_summaryOn),
    BIND_TCP_BROKEN_PIPE_DEPRECTED(R2.styleable.SwitchPreferenceCompat_switchTextOff),
    BIND_TCP_READ_TIMEOUT(R2.styleable.TabItem_android_layout),
    BIND_TCP_CONNRESET(R2.styleable.TabItem_android_text),
    BIND_TCP_BROKEN_PIPE(R2.styleable.TabLayout_tabBackground),
    BIND_TCP_ERR(R2.styleable.TextInputLayout_boxCornerRadiusBottomStart),
    BIND_XMPP_ERR(R2.styleable.WheelView_npv_TextColorSelected),
    BIND_BOSH_ITEM_NOT_FOUND(R2.styleable.WheelView_npv_TextSizeNormal),
    BIND_BOSH_ERR(R2.styleable.background_bl_pressed_gradient_startColor),
    BIND_TIMEOUT(R2.styleable.background_bl_pressed_gradient_useLevel),
    BIND_INVALID_SIG(R2.styleable.background_bl_pressed_solid_color),
    CHANNEL_TCP_READTIMEOUT_DEPRECTED(40101),
    CHANNEL_TCP_CONNRESET_DEPRECTED(40102),
    CHANNEL_TCP_BROKEN_PIPE_DEPRECTED(40103),
    CHANNEL_TCP_READTIMEOUT(40108),
    CHANNEL_TCP_CONNRESET(40109),
    CHANNEL_TCP_BROKEN_PIPE(40110),
    CHANNEL_TCP_ERR(40199),
    CHANNEL_XMPPEXCEPTION(40399),
    CHANNEL_BOSH_ITEMNOTFIND(40401),
    CHANNEL_BOSH_EXCEPTION(40499),
    CHANNEL_TIMER_DELAYED(50001),
    CHANNEL_STATS_COUNTER(8000);


    /* renamed from: a, reason: collision with other field name */
    private final int f431a;

    fr(int i2) {
        this.f431a = i2;
    }

    public static fr a(int i2) {
        if (i2 == 30501) {
            return BIND_TIMEOUT;
        }
        if (i2 == 30502) {
            return BIND_INVALID_SIG;
        }
        switch (i2) {
            case 1:
                return TCP_CONN_FAIL;
            case 2:
                return TCP_CONN_TIME;
            case 3:
                return PING_RTT;
            case 4:
                return CHANNEL_CON_FAIL;
            case 5:
                return CHANNEL_CON_OK;
            case 6:
                return ICMP_PING_FAIL;
            case 7:
                return ICMP_PING_OK;
            case 8:
                return CHANNEL_ONLINE_RATE;
            default:
                switch (i2) {
                    case 8000:
                        return CHANNEL_STATS_COUNTER;
                    case 10000:
                        return GSLB_REQUEST_SUCCESS;
                    case R2.drawable.ic_empty_data_comment_night /* 10199 */:
                        return GSLB_TCP_ERR_OTHER;
                    case R2.drawable.jiantou1 /* 10999 */:
                        return GSLB_ERR;
                    case 20000:
                        return CONN_SUCCESS;
                    case R2.id.tv_three /* 20199 */:
                        return CONN_TCP_ERR_OTHER;
                    case R2.id.umeng_socialize_bind_cancel /* 20399 */:
                        return CONN_XMPP_ERR;
                    case R2.id.umeng_socialize_first_area_title /* 20407 */:
                        return CONN_BOSH_UNKNOWNHOST;
                    case R2.id.verimg /* 20499 */:
                        return CONN_BOSH_ERR;
                    case 30000:
                        return BIND_SUCCESS;
                    case R2.styleable.TextInputLayout_boxCornerRadiusBottomStart /* 30199 */:
                        return BIND_TCP_ERR;
                    case R2.styleable.WheelView_npv_TextColorSelected /* 30399 */:
                        return BIND_XMPP_ERR;
                    case R2.styleable.WheelView_npv_TextSizeNormal /* 30401 */:
                        return BIND_BOSH_ITEM_NOT_FOUND;
                    case R2.styleable.background_bl_pressed_gradient_startColor /* 30499 */:
                        return BIND_BOSH_ERR;
                    case 40199:
                        return CHANNEL_TCP_ERR;
                    case 40399:
                        return CHANNEL_XMPPEXCEPTION;
                    case 40401:
                        return CHANNEL_BOSH_ITEMNOTFIND;
                    case 40499:
                        return CHANNEL_BOSH_EXCEPTION;
                    case 50001:
                        return CHANNEL_TIMER_DELAYED;
                    default:
                        switch (i2) {
                            case 10101:
                                return GSLB_TCP_NOACCESS;
                            case 10102:
                                return GSLB_TCP_NETUNREACH;
                            case 10103:
                                return GSLB_TCP_CONNREFUSED;
                            case 10104:
                                return GSLB_TCP_NOROUTETOHOST;
                            case 10105:
                                return GSLB_TCP_TIMEOUT;
                            case 10106:
                                return GSLB_TCP_INVALARG;
                            case 10107:
                                return GSLB_TCP_UKNOWNHOST;
                            default:
                                switch (i2) {
                                    case R2.id.tv_shi /* 20101 */:
                                        return CONN_TCP_NOACCESS;
                                    case R2.id.tv_shield_sub_title /* 20102 */:
                                        return CONN_TCP_NETUNREACH;
                                    case R2.id.tv_shop_name /* 20103 */:
                                        return CONN_TCP_CONNREFUSED;
                                    case R2.id.tv_shouhuoren /* 20104 */:
                                        return CONN_TCP_NOROUTETOHOST;
                                    case R2.id.tv_shouhuos /* 20105 */:
                                        return CONN_TCP_TIMEOUT;
                                    case R2.id.tv_show_content /* 20106 */:
                                        return CONN_TCP_INVALARG;
                                    case R2.id.tv_show_msg /* 20107 */:
                                        return CONN_TCP_UKNOWNHOST;
                                    default:
                                        switch (i2) {
                                            case R2.styleable.SwitchPreferenceCompat_summaryOff /* 30101 */:
                                                return BIND_TCP_READ_TIMEOUT_DEPRECTED;
                                            case R2.styleable.SwitchPreferenceCompat_summaryOn /* 30102 */:
                                                return BIND_TCP_CONNRESET_DEPRECTED;
                                            case R2.styleable.SwitchPreferenceCompat_switchTextOff /* 30103 */:
                                                return BIND_TCP_BROKEN_PIPE_DEPRECTED;
                                            default:
                                                switch (i2) {
                                                    case R2.styleable.TabItem_android_layout /* 30108 */:
                                                        return BIND_TCP_READ_TIMEOUT;
                                                    case R2.styleable.TabItem_android_text /* 30109 */:
                                                        return BIND_TCP_CONNRESET;
                                                    case R2.styleable.TabLayout_tabBackground /* 30110 */:
                                                        return BIND_TCP_BROKEN_PIPE;
                                                    default:
                                                        switch (i2) {
                                                            case 40101:
                                                                return CHANNEL_TCP_READTIMEOUT_DEPRECTED;
                                                            case 40102:
                                                                return CHANNEL_TCP_CONNRESET_DEPRECTED;
                                                            case 40103:
                                                                return CHANNEL_TCP_BROKEN_PIPE_DEPRECTED;
                                                            default:
                                                                switch (i2) {
                                                                    case 40108:
                                                                        return CHANNEL_TCP_READTIMEOUT;
                                                                    case 40109:
                                                                        return CHANNEL_TCP_CONNRESET;
                                                                    case 40110:
                                                                        return CHANNEL_TCP_BROKEN_PIPE;
                                                                    default:
                                                                        return null;
                                                                }
                                                        }
                                                }
                                        }
                                }
                        }
                }
        }
    }

    public int a() {
        return this.f431a;
    }
}
