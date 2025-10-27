package com.umeng.socialize.b.b;

import cn.hutool.core.img.ImgUtil;
import com.umeng.socialize.utils.SLog;
import com.umeng.socialize.utils.UmengText;
import java.io.ByteArrayInputStream;
import java.io.IOException;

/* loaded from: classes6.dex */
public class d {

    /* renamed from: a, reason: collision with root package name */
    public static final int f23649a = 0;

    /* renamed from: b, reason: collision with root package name */
    public static final int f23650b = 1;

    /* renamed from: c, reason: collision with root package name */
    public static final int f23651c = 2;

    /* renamed from: d, reason: collision with root package name */
    public static final int f23652d = 3;

    /* renamed from: e, reason: collision with root package name */
    public static final int f23653e = 4;

    /* renamed from: f, reason: collision with root package name */
    public static final int f23654f = 5;

    /* renamed from: g, reason: collision with root package name */
    public static final int f23655g = 6;

    /* renamed from: h, reason: collision with root package name */
    public static final int f23656h = 7;

    /* renamed from: i, reason: collision with root package name */
    public static final int f23657i = 8;

    /* renamed from: j, reason: collision with root package name */
    public static final int f23658j = 9;

    /* renamed from: k, reason: collision with root package name */
    public static final int f23659k = 10;

    /* renamed from: l, reason: collision with root package name */
    public static final int f23660l = 11;

    /* renamed from: m, reason: collision with root package name */
    public static final String[] f23661m = {"jpeg", ImgUtil.IMAGE_TYPE_GIF, "png", ImgUtil.IMAGE_TYPE_BMP, "pcx", "iff", "ras", "pbm", "pgm", "ppm", ImgUtil.IMAGE_TYPE_PSD, "swf"};

    public static String a(byte[] bArr) throws Throwable {
        ByteArrayInputStream byteArrayInputStream = null;
        try {
            try {
                ByteArrayInputStream byteArrayInputStream2 = new ByteArrayInputStream(bArr);
                try {
                    int i2 = byteArrayInputStream2.read();
                    int i3 = byteArrayInputStream2.read();
                    if (i2 == 71 && i3 == 73) {
                        String str = f23661m[1];
                        try {
                            byteArrayInputStream2.close();
                        } catch (IOException e2) {
                            SLog.error(UmengText.IMAGE.CLOSE, e2);
                        }
                        return str;
                    }
                    if (i2 == 137 && i3 == 80) {
                        String str2 = f23661m[2];
                        try {
                            byteArrayInputStream2.close();
                        } catch (IOException e3) {
                            SLog.error(UmengText.IMAGE.CLOSE, e3);
                        }
                        return str2;
                    }
                    if (i2 == 255 && i3 == 216) {
                        String str3 = f23661m[0];
                        try {
                            byteArrayInputStream2.close();
                        } catch (IOException e4) {
                            SLog.error(UmengText.IMAGE.CLOSE, e4);
                        }
                        return str3;
                    }
                    if (i2 == 66 && i3 == 77) {
                        String str4 = f23661m[3];
                        try {
                            byteArrayInputStream2.close();
                        } catch (IOException e5) {
                            SLog.error(UmengText.IMAGE.CLOSE, e5);
                        }
                        return str4;
                    }
                    if (i2 == 10 && i3 < 6) {
                        String str5 = f23661m[4];
                        try {
                            byteArrayInputStream2.close();
                        } catch (IOException e6) {
                            SLog.error(UmengText.IMAGE.CLOSE, e6);
                        }
                        return str5;
                    }
                    if (i2 == 70 && i3 == 79) {
                        String str6 = f23661m[5];
                        try {
                            byteArrayInputStream2.close();
                        } catch (IOException e7) {
                            SLog.error(UmengText.IMAGE.CLOSE, e7);
                        }
                        return str6;
                    }
                    if (i2 == 89 && i3 == 166) {
                        String str7 = f23661m[6];
                        try {
                            byteArrayInputStream2.close();
                        } catch (IOException e8) {
                            SLog.error(UmengText.IMAGE.CLOSE, e8);
                        }
                        return str7;
                    }
                    if (i2 == 80 && i3 >= 49 && i3 <= 54) {
                        int i4 = i3 - 48;
                        if (i4 < 1 || i4 > 6) {
                            try {
                                byteArrayInputStream2.close();
                            } catch (IOException e9) {
                                SLog.error(UmengText.IMAGE.CLOSE, e9);
                            }
                            return "";
                        }
                        String str8 = f23661m[new int[]{7, 8, 9}[(i4 - 1) % 3]];
                        try {
                            byteArrayInputStream2.close();
                        } catch (IOException e10) {
                            SLog.error(UmengText.IMAGE.CLOSE, e10);
                        }
                        return str8;
                    }
                    if (i2 == 56 && i3 == 66) {
                        String str9 = f23661m[10];
                        try {
                            byteArrayInputStream2.close();
                        } catch (IOException e11) {
                            SLog.error(UmengText.IMAGE.CLOSE, e11);
                        }
                        return str9;
                    }
                    if (i2 != 70 || i3 != 87) {
                        try {
                            byteArrayInputStream2.close();
                        } catch (IOException e12) {
                            SLog.error(UmengText.IMAGE.CLOSE, e12);
                        }
                        return "";
                    }
                    String str10 = f23661m[11];
                    try {
                        byteArrayInputStream2.close();
                    } catch (IOException e13) {
                        SLog.error(UmengText.IMAGE.CLOSE, e13);
                    }
                    return str10;
                } catch (Exception e14) {
                    e = e14;
                    byteArrayInputStream = byteArrayInputStream2;
                    SLog.error(UmengText.IMAGE.CHECK_FORMAT_ERROR, e);
                    if (byteArrayInputStream != null) {
                        try {
                            byteArrayInputStream.close();
                        } catch (IOException e15) {
                            SLog.error(UmengText.IMAGE.CLOSE, e15);
                        }
                    }
                    return "";
                } catch (Throwable th) {
                    th = th;
                    byteArrayInputStream = byteArrayInputStream2;
                    if (byteArrayInputStream != null) {
                        try {
                            byteArrayInputStream.close();
                        } catch (IOException e16) {
                            SLog.error(UmengText.IMAGE.CLOSE, e16);
                        }
                    }
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (Exception e17) {
            e = e17;
        }
    }
}
