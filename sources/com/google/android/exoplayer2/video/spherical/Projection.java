package com.google.android.exoplayer2.video.spherical;

import com.google.android.exoplayer2.util.Assertions;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes3.dex */
final class Projection {
    public static final int DRAW_MODE_TRIANGLES = 0;
    public static final int DRAW_MODE_TRIANGLES_FAN = 2;
    public static final int DRAW_MODE_TRIANGLES_STRIP = 1;
    public static final int POSITION_COORDS_PER_VERTEX = 3;
    public static final int TEXTURE_COORDS_PER_VERTEX = 2;
    public final Mesh leftMesh;
    public final Mesh rightMesh;
    public final boolean singleMesh;
    public final int stereoMode;

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface DrawMode {
    }

    public static final class Mesh {
        private final SubMesh[] subMeshes;

        public Mesh(SubMesh... subMeshArr) {
            this.subMeshes = subMeshArr;
        }

        public SubMesh getSubMesh(int i2) {
            return this.subMeshes[i2];
        }

        public int getSubMeshCount() {
            return this.subMeshes.length;
        }
    }

    public static final class SubMesh {
        public static final int VIDEO_TEXTURE_ID = 0;
        public final int mode;
        public final float[] textureCoords;
        public final int textureId;
        public final float[] vertices;

        public SubMesh(int i2, float[] fArr, float[] fArr2, int i3) {
            this.textureId = i2;
            Assertions.checkArgument(((long) fArr.length) * 2 == ((long) fArr2.length) * 3);
            this.vertices = fArr;
            this.textureCoords = fArr2;
            this.mode = i3;
        }

        public int getVertexCount() {
            return this.vertices.length / 3;
        }
    }

    public Projection(Mesh mesh, int i2) {
        this(mesh, mesh, i2);
    }

    public static Projection createEquirectangular(int i2) {
        return createEquirectangular(50.0f, 36, 72, 180.0f, 360.0f, i2);
    }

    public Projection(Mesh mesh, Mesh mesh2, int i2) {
        this.leftMesh = mesh;
        this.rightMesh = mesh2;
        this.stereoMode = i2;
        this.singleMesh = mesh == mesh2;
    }

    public static Projection createEquirectangular(float f2, int i2, int i3, float f3, float f4, int i4) {
        int i5;
        float f5;
        int i6;
        int i7;
        int i8;
        float[] fArr;
        int i9;
        int i10 = i2;
        int i11 = i3;
        Assertions.checkArgument(f2 > 0.0f);
        Assertions.checkArgument(i10 >= 1);
        Assertions.checkArgument(i11 >= 1);
        Assertions.checkArgument(f3 > 0.0f && f3 <= 180.0f);
        Assertions.checkArgument(f4 > 0.0f && f4 <= 360.0f);
        float radians = (float) Math.toRadians(f3);
        float radians2 = (float) Math.toRadians(f4);
        float f6 = radians / i10;
        float f7 = radians2 / i11;
        int i12 = i11 + 1;
        int i13 = ((i12 * 2) + 2) * i10;
        float[] fArr2 = new float[i13 * 3];
        float[] fArr3 = new float[i13 * 2];
        int i14 = 0;
        int i15 = 0;
        int i16 = 0;
        while (i14 < i10) {
            float f8 = radians / 2.0f;
            float f9 = (i14 * f6) - f8;
            int i17 = i14 + 1;
            float f10 = (i17 * f6) - f8;
            int i18 = 0;
            while (i18 < i12) {
                float f11 = f9;
                int i19 = i17;
                int i20 = 0;
                int i21 = 2;
                while (i20 < i21) {
                    if (i20 == 0) {
                        f5 = f11;
                        i5 = i12;
                    } else {
                        i5 = i12;
                        f5 = f10;
                    }
                    float f12 = i18 * f7;
                    float f13 = f7;
                    int i22 = i15 + 1;
                    int i23 = i18;
                    double d3 = f2;
                    float f14 = f6;
                    double d4 = (f12 + 3.1415927f) - (radians2 / 2.0f);
                    int i24 = i20;
                    double d5 = f5;
                    float[] fArr4 = fArr3;
                    float f15 = f10;
                    fArr2[i15] = -((float) (Math.sin(d4) * d3 * Math.cos(d5)));
                    int i25 = i22 + 1;
                    int i26 = i14;
                    fArr2[i22] = (float) (d3 * Math.sin(d5));
                    int i27 = i25 + 1;
                    fArr2[i25] = (float) (d3 * Math.cos(d4) * Math.cos(d5));
                    int i28 = i16 + 1;
                    fArr4[i16] = f12 / radians2;
                    int i29 = i28 + 1;
                    fArr4[i28] = ((i26 + i24) * f14) / radians;
                    if (i23 == 0 && i24 == 0) {
                        i6 = i3;
                        i7 = i23;
                        i8 = i24;
                    } else {
                        i6 = i3;
                        i7 = i23;
                        i8 = i24;
                        if (i7 != i6 || i8 != 1) {
                            fArr = fArr4;
                            i9 = 2;
                        }
                        i16 = i29;
                        i15 = i27;
                        i20 = i8 + 1;
                        i11 = i6;
                        i18 = i7;
                        fArr3 = fArr;
                        i21 = i9;
                        i14 = i26;
                        i12 = i5;
                        f7 = f13;
                        f6 = f14;
                        f10 = f15;
                    }
                    System.arraycopy(fArr2, i27 - 3, fArr2, i27, 3);
                    i27 += 3;
                    fArr = fArr4;
                    i9 = 2;
                    System.arraycopy(fArr, i29 - 2, fArr, i29, 2);
                    i29 += 2;
                    i16 = i29;
                    i15 = i27;
                    i20 = i8 + 1;
                    i11 = i6;
                    i18 = i7;
                    fArr3 = fArr;
                    i21 = i9;
                    i14 = i26;
                    i12 = i5;
                    f7 = f13;
                    f6 = f14;
                    f10 = f15;
                }
                float f16 = f6;
                int i30 = i18;
                int i31 = i11;
                int i32 = i30 + 1;
                f9 = f11;
                i17 = i19;
                i12 = i12;
                f6 = f16;
                f10 = f10;
                i11 = i31;
                i18 = i32;
            }
            i10 = i2;
            i14 = i17;
        }
        return new Projection(new Mesh(new SubMesh(0, fArr2, fArr3, 1)), i4);
    }
}
