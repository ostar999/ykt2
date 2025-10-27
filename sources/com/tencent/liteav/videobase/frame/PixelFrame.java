package com.tencent.liteav.videobase.frame;

import com.tencent.liteav.videobase.a.a;
import com.tencent.liteav.videobase.frame.a;
import java.nio.ByteBuffer;

/* loaded from: classes6.dex */
public class PixelFrame extends a.c {
    protected ByteBuffer mBuffer;
    protected byte[] mData;
    protected Object mGLContext;
    protected int mHeight;
    private boolean mIsMirrorHorizontal;
    private boolean mIsMirrorVertical;
    private float[] mMatrix;
    protected a.b mPixelBufferType;
    protected a.c mPixelFormatType;
    private com.tencent.liteav.videobase.utils.d mRotation;
    protected int mTextureId;
    private long mTimestamp;
    protected int mWidth;

    public PixelFrame() {
        super(null);
        this.mTimestamp = 0L;
        this.mWidth = -1;
        this.mHeight = -1;
        this.mRotation = com.tencent.liteav.videobase.utils.d.NORMAL;
        this.mIsMirrorHorizontal = false;
        this.mIsMirrorVertical = false;
        this.mMatrix = null;
        this.mData = null;
        this.mBuffer = null;
        this.mTextureId = -1;
        this.mGLContext = null;
    }

    public ByteBuffer getBuffer() {
        return this.mBuffer;
    }

    public byte[] getData() {
        return this.mData;
    }

    public Object getGLContext() {
        return this.mGLContext;
    }

    public int getHeight() {
        return this.mHeight;
    }

    public float[] getMatrix() {
        return this.mMatrix;
    }

    public a.b getPixelBufferType() {
        return this.mPixelBufferType;
    }

    public a.c getPixelFormatType() {
        return this.mPixelFormatType;
    }

    public com.tencent.liteav.videobase.utils.d getRotation() {
        return this.mRotation;
    }

    public int getTextureId() {
        return this.mTextureId;
    }

    public long getTimestamp() {
        return this.mTimestamp;
    }

    public int getWidth() {
        return this.mWidth;
    }

    public boolean hasTransformParams() {
        return this.mRotation != com.tencent.liteav.videobase.utils.d.NORMAL || this.mIsMirrorHorizontal || this.mIsMirrorVertical || this.mMatrix != null;
    }

    public boolean isMirrorHorizontal() {
        return this.mIsMirrorHorizontal;
    }

    public boolean isMirrorVertical() {
        return this.mIsMirrorVertical;
    }

    public void postRotate(com.tencent.liteav.videobase.utils.d dVar) {
        if (dVar == com.tencent.liteav.videobase.utils.d.ROTATION_90 || dVar == com.tencent.liteav.videobase.utils.d.ROTATION_270) {
            swapWidthHeight();
        }
        setRotation(com.tencent.liteav.videobase.utils.d.a((this.mRotation.a() + dVar.a()) % 360));
    }

    public void setBuffer(ByteBuffer byteBuffer) {
        this.mBuffer = byteBuffer;
    }

    public void setData(byte[] bArr) {
        this.mData = bArr;
    }

    public void setGLContext(Object obj) {
        this.mGLContext = obj;
    }

    public void setHeight(int i2) {
        this.mHeight = i2;
    }

    public void setMatrix(float[] fArr) {
        this.mMatrix = fArr;
    }

    public void setMirrorHorizontal(boolean z2) {
        this.mIsMirrorHorizontal = z2;
    }

    public void setMirrorVertical(boolean z2) {
        this.mIsMirrorVertical = z2;
    }

    public void setPixelBufferType(a.b bVar) {
        this.mPixelBufferType = bVar;
    }

    public void setPixelFormatType(a.c cVar) {
        this.mPixelFormatType = cVar;
    }

    public void setRotation(com.tencent.liteav.videobase.utils.d dVar) {
        this.mRotation = dVar;
    }

    public void setTextureId(int i2) {
        this.mTextureId = i2;
    }

    public void setTimestamp(long j2) {
        this.mTimestamp = j2;
    }

    public void setWidth(int i2) {
        this.mWidth = i2;
    }

    public void swapWidthHeight() {
        int i2 = this.mWidth;
        this.mWidth = this.mHeight;
        this.mHeight = i2;
    }

    public PixelFrame(PixelFrame pixelFrame) {
        super(null);
        this.mTimestamp = 0L;
        this.mWidth = -1;
        this.mHeight = -1;
        this.mRotation = com.tencent.liteav.videobase.utils.d.NORMAL;
        this.mIsMirrorHorizontal = false;
        this.mIsMirrorVertical = false;
        this.mMatrix = null;
        this.mData = null;
        this.mBuffer = null;
        this.mTextureId = -1;
        this.mGLContext = null;
        this.mTimestamp = pixelFrame.mTimestamp;
        this.mWidth = pixelFrame.mWidth;
        this.mHeight = pixelFrame.mHeight;
        this.mPixelBufferType = pixelFrame.mPixelBufferType;
        this.mPixelFormatType = pixelFrame.mPixelFormatType;
        this.mRotation = pixelFrame.mRotation;
        this.mIsMirrorHorizontal = pixelFrame.mIsMirrorHorizontal;
        this.mIsMirrorVertical = pixelFrame.mIsMirrorVertical;
        if (pixelFrame.mMatrix != null) {
            this.mMatrix = new float[16];
            float[] matrix = pixelFrame.getMatrix();
            float[] fArr = this.mMatrix;
            System.arraycopy(matrix, 0, fArr, 0, fArr.length);
        }
        this.mMatrix = pixelFrame.mMatrix;
        this.mData = pixelFrame.mData;
        this.mBuffer = pixelFrame.mBuffer;
        this.mTextureId = pixelFrame.mTextureId;
        this.mGLContext = pixelFrame.mGLContext;
    }

    public PixelFrame(a.InterfaceC0341a<? extends PixelFrame> interfaceC0341a) {
        super(interfaceC0341a);
        this.mTimestamp = 0L;
        this.mWidth = -1;
        this.mHeight = -1;
        this.mRotation = com.tencent.liteav.videobase.utils.d.NORMAL;
        this.mIsMirrorHorizontal = false;
        this.mIsMirrorVertical = false;
        this.mMatrix = null;
        this.mData = null;
        this.mBuffer = null;
        this.mTextureId = -1;
        this.mGLContext = null;
    }
}
