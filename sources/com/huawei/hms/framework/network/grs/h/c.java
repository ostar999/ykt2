package com.huawei.hms.framework.network.grs.h;

import android.content.Context;
import android.text.TextUtils;
import com.huawei.hms.framework.common.IoUtils;
import com.huawei.hms.framework.common.Logger;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/* loaded from: classes4.dex */
public class c {

    /* renamed from: a, reason: collision with root package name */
    private static final String f7696a = "c";

    public static String a(String str, Context context) throws IOException {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        InputStream inputStreamOpen = null;
        try {
            try {
                if (!new File(str).isDirectory()) {
                    inputStreamOpen = context.getAssets().open(str);
                    byte[] bArr = new byte[8192];
                    while (true) {
                        int i2 = inputStreamOpen.read(bArr);
                        if (i2 == -1) {
                            byteArrayOutputStream.flush();
                            return new String(byteArrayOutputStream.toByteArray(), "UTF-8");
                        }
                        byteArrayOutputStream.write(bArr, 0, i2);
                    }
                }
            } catch (IOException unused) {
                Logger.w(f7696a, "local config file is not exist.filename is {%s}", str);
            }
            return "";
        } finally {
            IoUtils.closeSecure((OutputStream) byteArrayOutputStream);
            IoUtils.closeSecure(inputStreamOpen);
        }
    }
}
