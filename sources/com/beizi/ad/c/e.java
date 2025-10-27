package com.beizi.ad.c;

/* loaded from: classes2.dex */
public final class e {

    public enum a {
        ADP_UNKNOWN(0),
        ADP_IVIDEO(1),
        ADP_LOADING(2),
        ADP_TABLE(3),
        ADP_BANNER(4),
        ADP_CUSTOMER(5),
        ADP_NATIVE(6);


        /* renamed from: h, reason: collision with root package name */
        private final int f3932h;

        a(int i2) {
            this.f3932h = i2;
        }

        public static a a(int i2) {
            switch (i2) {
                case 0:
                    return ADP_UNKNOWN;
                case 1:
                    return ADP_IVIDEO;
                case 2:
                    return ADP_LOADING;
                case 3:
                    return ADP_TABLE;
                case 4:
                    return ADP_BANNER;
                case 5:
                    return ADP_CUSTOMER;
                case 6:
                    return ADP_NATIVE;
                default:
                    return null;
            }
        }
    }

    public enum b {
        DEVICE_UNKNOWN(0),
        DEVICE_PHONE(1),
        DEVICE_FLAT(2),
        DEVICE_WEAR(3),
        DEVICE_PC(4),
        DEVICE_OTHER(5);


        /* renamed from: g, reason: collision with root package name */
        private final int f3940g;

        b(int i2) {
            this.f3940g = i2;
        }
    }

    public enum c {
        ISP_UNKNOWN(0),
        ISP_CN_MOBILE(1),
        ISP_CN_UNICOM(2),
        ISP_CN_TEL(3),
        ISP_OTHER(4);


        /* renamed from: f, reason: collision with root package name */
        private final int f3947f;

        c(int i2) {
            this.f3947f = i2;
        }

        public final int a() {
            return this.f3947f;
        }
    }

    public enum d {
        NET_UNKNOWN(0),
        NET_3G(1),
        NET_4G(2),
        NET_5G(3),
        NET_WIFI(4),
        NET_OTHER(5),
        NET_2G(6);


        /* renamed from: h, reason: collision with root package name */
        private final int f3956h;

        d(int i2) {
            this.f3956h = i2;
        }

        public final int a() {
            return this.f3956h;
        }
    }

    /* renamed from: com.beizi.ad.c.e$e, reason: collision with other inner class name */
    public enum EnumC0049e {
        PLATFORM_UNKNOWN(0),
        PLATFORM_IOS(1),
        PLATFORM_ANDROID(2),
        PLATFORM_OTHER(3);


        /* renamed from: e, reason: collision with root package name */
        private final int f3962e;

        EnumC0049e(int i2) {
            this.f3962e = i2;
        }
    }

    public enum f {
        RENDER_UNKNOWN(0),
        RENDER_VIDEO(1),
        RENDER_PIC(2),
        RENDER_H5(3),
        RENDER_JSON(4),
        RENDER_VAST_VIDEO(5);


        /* renamed from: g, reason: collision with root package name */
        private final int f3970g;

        f(int i2) {
            this.f3970g = i2;
        }

        public static f a(int i2) {
            if (i2 == 0) {
                return RENDER_UNKNOWN;
            }
            if (i2 == 1) {
                return RENDER_VIDEO;
            }
            if (i2 == 2) {
                return RENDER_PIC;
            }
            if (i2 == 3) {
                return RENDER_H5;
            }
            if (i2 == 4) {
                return RENDER_JSON;
            }
            if (i2 != 5) {
                return null;
            }
            return RENDER_VAST_VIDEO;
        }
    }

    public enum g {
        REQ_UNKNOWN(0),
        REQ_AD(1),
        REQ_WIFI_PRELOAD(2);


        /* renamed from: d, reason: collision with root package name */
        private final int f3975d;

        g(int i2) {
            this.f3975d = i2;
        }
    }

    public enum h {
        SCREEN_DIRECTION_UNKOWN(0),
        PORTRAIT(1),
        LANDSCAPE(2);


        /* renamed from: d, reason: collision with root package name */
        private final int f3980d;

        h(int i2) {
            this.f3980d = i2;
        }

        public static h a(int i2) {
            if (i2 == 0) {
                return SCREEN_DIRECTION_UNKOWN;
            }
            if (i2 == 1) {
                return PORTRAIT;
            }
            if (i2 != 2) {
                return null;
            }
            return LANDSCAPE;
        }
    }

    public enum i {
        SRC_UNKNOWN(0),
        SRC_APP(1),
        SRC_WAP(2),
        SRC_PC(3),
        SRC_TEST(4);


        /* renamed from: f, reason: collision with root package name */
        private final int f3987f;

        i(int i2) {
            this.f3987f = i2;
        }
    }
}
