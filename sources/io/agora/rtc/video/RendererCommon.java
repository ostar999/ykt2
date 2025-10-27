package io.agora.rtc.video;

/* loaded from: classes8.dex */
public class RendererCommon {

    public interface GlDrawer {
        void drawOes(int oesTextureId, float[] texMatrix, int x2, int y2, int width, int height);

        void drawRgb(int textureId, float[] texMatrix, int x2, int y2, int width, int height);

        void drawYuv(int[] yuvTextures, float[] texMatrix, int x2, int y2, int width, int height);

        void release();
    }
}
