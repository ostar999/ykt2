package com.tencent.liteav.beauty.b.a;

import com.tencent.liteav.basic.opengl.j;
import com.tencent.liteav.beauty.NativeLoad;

/* loaded from: classes6.dex */
public class c extends j {
    @Override // com.tencent.liteav.basic.opengl.j
    public boolean a() {
        int iNativeLoadGLProgram = NativeLoad.nativeLoadGLProgram(6);
        this.f18592a = iNativeLoadGLProgram;
        if (iNativeLoadGLProgram == 0 || !b()) {
            this.f18598g = false;
        } else {
            this.f18598g = true;
        }
        c();
        return this.f18598g;
    }
}
