package com.alibaba.sdk.android.oss.model;

import com.meizu.cloud.pushsdk.constants.PushConstants;
import org.eclipse.jetty.servlet.ServletHandler;

/* loaded from: classes2.dex */
public enum ObjectPermission {
    Private(PushConstants.MZ_PUSH_MESSAGE_METHOD_ACTION_PRIVATE),
    PublicRead("public-read"),
    PublicReadWrite("public-read-write"),
    Default(ServletHandler.__DEFAULT_SERVLET),
    Unknown("");

    private String permissionString;

    ObjectPermission(String str) {
        this.permissionString = str;
    }

    public static ObjectPermission parsePermission(String str) {
        ObjectPermission[] objectPermissionArr = {Private, PublicRead, PublicReadWrite, Default};
        for (int i2 = 0; i2 < 4; i2++) {
            ObjectPermission objectPermission = objectPermissionArr[i2];
            if (objectPermission.permissionString.equals(str)) {
                return objectPermission;
            }
        }
        return Unknown;
    }

    @Override // java.lang.Enum
    public String toString() {
        return this.permissionString;
    }
}
