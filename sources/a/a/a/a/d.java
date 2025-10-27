package a.a.a.a;

import a.a.a.a.f.g;
import android.opengl.GLES20;
import com.yikaobang.yixue.R2;
import java.nio.Buffer;

/* loaded from: classes.dex */
public class d extends g {

    /* renamed from: h, reason: collision with root package name */
    private static final String f1130h = "uniform mat4 uMVPMatrix;\nuniform mat4 uTexMatrix;\nattribute vec4 aPosition;\nattribute vec4 aTextureCoord;\nvarying vec2 vTextureCoord;\nvoid main() {\n    gl_Position = uMVPMatrix * aPosition;\n    vTextureCoord = (uTexMatrix * aTextureCoord).xy;\n}\n";

    /* renamed from: i, reason: collision with root package name */
    private static final String f1131i = "precision mediump float;\nvarying vec2 vTextureCoord;\nuniform sampler2D sTexture;\nvoid main() {\n    gl_FragColor = vec4(texture2D(sTexture, vTextureCoord).rgb, 1.0);\n}\n";

    /* renamed from: d, reason: collision with root package name */
    private int f1132d;

    /* renamed from: e, reason: collision with root package name */
    private int f1133e;

    /* renamed from: f, reason: collision with root package name */
    private int f1134f;

    /* renamed from: g, reason: collision with root package name */
    private int f1135g;

    public d() {
        super(f1130h, f1131i);
    }

    @Override // a.a.a.a.f.g
    public a.a.a.a.f.a a() {
        return new a();
    }

    @Override // a.a.a.a.f.g
    public void b() {
        int iGlGetAttribLocation = GLES20.glGetAttribLocation(this.f1166a, "aPosition");
        this.f1134f = iGlGetAttribLocation;
        a.a.a.a.f.e.a(iGlGetAttribLocation, "aPosition");
        int iGlGetAttribLocation2 = GLES20.glGetAttribLocation(this.f1166a, "aTextureCoord");
        this.f1135g = iGlGetAttribLocation2;
        a.a.a.a.f.e.a(iGlGetAttribLocation2, "aTextureCoord");
        int iGlGetUniformLocation = GLES20.glGetUniformLocation(this.f1166a, "uMVPMatrix");
        this.f1132d = iGlGetUniformLocation;
        a.a.a.a.f.e.a(iGlGetUniformLocation, "uMVPMatrix");
        int iGlGetUniformLocation2 = GLES20.glGetUniformLocation(this.f1166a, "uTexMatrix");
        this.f1133e = iGlGetUniformLocation2;
        a.a.a.a.f.e.a(iGlGetUniformLocation2, "uTexMatrix");
    }

    @Override // a.a.a.a.f.g
    public void a(int i2, float[] fArr, float[] fArr2) {
        a.a.a.a.f.e.a("draw start");
        GLES20.glUseProgram(this.f1166a);
        a.a.a.a.f.e.a("glUseProgram");
        GLES20.glActiveTexture(33984);
        GLES20.glBindTexture(R2.attr.tab_indicator_height, i2);
        GLES20.glUniformMatrix4fv(this.f1132d, 1, false, fArr2, 0);
        a.a.a.a.f.e.a("glUniformMatrix4fv");
        GLES20.glUniformMatrix4fv(this.f1133e, 1, false, fArr, 0);
        a.a.a.a.f.e.a("glUniformMatrix4fv");
        GLES20.glEnableVertexAttribArray(this.f1134f);
        a.a.a.a.f.e.a("glEnableVertexAttribArray");
        GLES20.glVertexAttribPointer(this.f1134f, 2, R2.color.m3_ref_palette_dynamic_tertiary60, false, 8, (Buffer) this.f1167b.b());
        a.a.a.a.f.e.a("glVertexAttribPointer");
        GLES20.glEnableVertexAttribArray(this.f1135g);
        a.a.a.a.f.e.a("glEnableVertexAttribArray");
        GLES20.glVertexAttribPointer(this.f1135g, 2, R2.color.m3_ref_palette_dynamic_tertiary60, false, 8, (Buffer) this.f1167b.a());
        a.a.a.a.f.e.a("glVertexAttribPointer");
        GLES20.glDrawArrays(5, 0, this.f1167b.c());
        a.a.a.a.f.e.a("glDrawArrays");
        GLES20.glDisableVertexAttribArray(this.f1134f);
        GLES20.glDisableVertexAttribArray(this.f1135g);
        GLES20.glBindTexture(R2.attr.tab_indicator_height, 0);
        GLES20.glUseProgram(0);
    }
}
