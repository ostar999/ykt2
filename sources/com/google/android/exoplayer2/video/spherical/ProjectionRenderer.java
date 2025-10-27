package com.google.android.exoplayer2.video.spherical;

import android.opengl.GLES20;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.GlUtil;
import com.google.android.exoplayer2.video.spherical.Projection;
import com.yikaobang.yixue.R2;
import java.nio.Buffer;
import java.nio.FloatBuffer;

/* loaded from: classes3.dex */
final class ProjectionRenderer {

    @Nullable
    private MeshData leftMeshData;
    private int mvpMatrixHandle;
    private int positionHandle;
    private GlUtil.Program program;

    @Nullable
    private MeshData rightMeshData;
    private int stereoMode;
    private int texCoordsHandle;
    private int textureHandle;
    private int uTexMatrixHandle;
    private static final String[] VERTEX_SHADER_CODE = {"uniform mat4 uMvpMatrix;", "uniform mat3 uTexMatrix;", "attribute vec4 aPosition;", "attribute vec2 aTexCoords;", "varying vec2 vTexCoords;", "void main() {", "  gl_Position = uMvpMatrix * aPosition;", "  vTexCoords = (uTexMatrix * vec3(aTexCoords, 1)).xy;", "}"};
    private static final String[] FRAGMENT_SHADER_CODE = {"#extension GL_OES_EGL_image_external : require", "precision mediump float;", "uniform samplerExternalOES uTexture;", "varying vec2 vTexCoords;", "void main() {", "  gl_FragColor = texture2D(uTexture, vTexCoords);", "}"};
    private static final float[] TEX_MATRIX_WHOLE = {1.0f, 0.0f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f, 1.0f, 1.0f};
    private static final float[] TEX_MATRIX_TOP = {1.0f, 0.0f, 0.0f, 0.0f, -0.5f, 0.0f, 0.0f, 0.5f, 1.0f};
    private static final float[] TEX_MATRIX_BOTTOM = {1.0f, 0.0f, 0.0f, 0.0f, -0.5f, 0.0f, 0.0f, 1.0f, 1.0f};
    private static final float[] TEX_MATRIX_LEFT = {0.5f, 0.0f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f, 1.0f, 1.0f};
    private static final float[] TEX_MATRIX_RIGHT = {0.5f, 0.0f, 0.0f, 0.0f, -1.0f, 0.0f, 0.5f, 1.0f, 1.0f};

    public static class MeshData {
        private final int drawMode;
        private final FloatBuffer textureBuffer;
        private final FloatBuffer vertexBuffer;
        private final int vertexCount;

        public MeshData(Projection.SubMesh subMesh) {
            this.vertexCount = subMesh.getVertexCount();
            this.vertexBuffer = GlUtil.createBuffer(subMesh.vertices);
            this.textureBuffer = GlUtil.createBuffer(subMesh.textureCoords);
            int i2 = subMesh.mode;
            if (i2 == 1) {
                this.drawMode = 5;
            } else if (i2 != 2) {
                this.drawMode = 4;
            } else {
                this.drawMode = 6;
            }
        }
    }

    public static boolean isSupported(Projection projection) {
        Projection.Mesh mesh = projection.leftMesh;
        Projection.Mesh mesh2 = projection.rightMesh;
        return mesh.getSubMeshCount() == 1 && mesh.getSubMesh(0).textureId == 0 && mesh2.getSubMeshCount() == 1 && mesh2.getSubMesh(0).textureId == 0;
    }

    public void draw(int i2, float[] fArr, boolean z2) {
        MeshData meshData = z2 ? this.rightMeshData : this.leftMeshData;
        if (meshData == null) {
            return;
        }
        ((GlUtil.Program) Assertions.checkNotNull(this.program)).use();
        GlUtil.checkGlError();
        GLES20.glEnableVertexAttribArray(this.positionHandle);
        GLES20.glEnableVertexAttribArray(this.texCoordsHandle);
        GlUtil.checkGlError();
        int i3 = this.stereoMode;
        GLES20.glUniformMatrix3fv(this.uTexMatrixHandle, 1, false, i3 == 1 ? z2 ? TEX_MATRIX_BOTTOM : TEX_MATRIX_TOP : i3 == 2 ? z2 ? TEX_MATRIX_RIGHT : TEX_MATRIX_LEFT : TEX_MATRIX_WHOLE, 0);
        GLES20.glUniformMatrix4fv(this.mvpMatrixHandle, 1, false, fArr, 0);
        GLES20.glActiveTexture(33984);
        GLES20.glBindTexture(36197, i2);
        GLES20.glUniform1i(this.textureHandle, 0);
        GlUtil.checkGlError();
        GLES20.glVertexAttribPointer(this.positionHandle, 3, R2.color.m3_ref_palette_dynamic_tertiary60, false, 12, (Buffer) meshData.vertexBuffer);
        GlUtil.checkGlError();
        GLES20.glVertexAttribPointer(this.texCoordsHandle, 2, R2.color.m3_ref_palette_dynamic_tertiary60, false, 8, (Buffer) meshData.textureBuffer);
        GlUtil.checkGlError();
        GLES20.glDrawArrays(meshData.drawMode, 0, meshData.vertexCount);
        GlUtil.checkGlError();
        GLES20.glDisableVertexAttribArray(this.positionHandle);
        GLES20.glDisableVertexAttribArray(this.texCoordsHandle);
    }

    public void init() {
        GlUtil.Program program = new GlUtil.Program(VERTEX_SHADER_CODE, FRAGMENT_SHADER_CODE);
        this.program = program;
        this.mvpMatrixHandle = program.getUniformLocation("uMvpMatrix");
        this.uTexMatrixHandle = this.program.getUniformLocation("uTexMatrix");
        this.positionHandle = this.program.getAttribLocation("aPosition");
        this.texCoordsHandle = this.program.getAttribLocation("aTexCoords");
        this.textureHandle = this.program.getUniformLocation("uTexture");
    }

    public void setProjection(Projection projection) {
        if (isSupported(projection)) {
            this.stereoMode = projection.stereoMode;
            MeshData meshData = new MeshData(projection.leftMesh.getSubMesh(0));
            this.leftMeshData = meshData;
            if (!projection.singleMesh) {
                meshData = new MeshData(projection.rightMesh.getSubMesh(0));
            }
            this.rightMeshData = meshData;
        }
    }

    public void shutdown() {
        GlUtil.Program program = this.program;
        if (program != null) {
            program.delete();
        }
    }
}
