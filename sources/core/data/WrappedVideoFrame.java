package core.data;

import android.graphics.Matrix;
import java.nio.ByteBuffer;
import org.wrtca.api.VideoFrame;
import org.wrtca.video.NV21Buffer;

/* loaded from: classes8.dex */
public class WrappedVideoFrame {
    private int mBufferType;
    private ByteBuffer mByteBufferU;
    private ByteBuffer mByteBufferV;
    private ByteBuffer mByteBufferY;
    private byte[] mData;
    private int mFormat;
    private int mHeight;
    private int mRotation;
    private int mTextureId;
    private long mTimeStamp;
    private Matrix mTransFormMatrix;
    public VideoFrame mVideoFrame;
    private int mWidth;

    public WrappedVideoFrame() {
        this.mTextureId = -1;
    }

    public WrappedVideoFrame createEmptyDstFrameFromSource() {
        WrappedVideoFrame wrappedVideoFrame = new WrappedVideoFrame();
        wrappedVideoFrame.mWidth = this.mWidth;
        wrappedVideoFrame.mHeight = this.mHeight;
        wrappedVideoFrame.mTimeStamp = this.mTimeStamp;
        wrappedVideoFrame.mRotation = this.mRotation;
        if (this.mTransFormMatrix != null) {
            wrappedVideoFrame.mTransFormMatrix = new Matrix(this.mTransFormMatrix);
        }
        return wrappedVideoFrame;
    }

    public int getBufferType() {
        return this.mBufferType;
    }

    public ByteBuffer getByteBufferU() {
        return this.mByteBufferU;
    }

    public ByteBuffer getByteBufferV() {
        return this.mByteBufferV;
    }

    public ByteBuffer getByteBufferY() {
        return this.mByteBufferY;
    }

    public byte[] getData() {
        return this.mData;
    }

    public int getFormat() {
        return this.mFormat;
    }

    public int getHeight() {
        return this.mHeight;
    }

    public int getRotation() {
        return this.mRotation;
    }

    public int getTextureId() {
        return this.mTextureId;
    }

    public long getTimeStamp() {
        return this.mTimeStamp;
    }

    public Matrix getTransFormMatrix() {
        return this.mTransFormMatrix;
    }

    public VideoFrame getVideoFrame() {
        return this.mVideoFrame;
    }

    public int getWidth() {
        return this.mWidth;
    }

    public void release() {
        VideoFrame videoFrame = this.mVideoFrame;
        if (videoFrame != null) {
            videoFrame.release();
        }
        this.mVideoFrame = null;
        this.mData = null;
        this.mByteBufferY = null;
        this.mByteBufferU = null;
        this.mByteBufferV = null;
    }

    public void setBufferType(int i2) {
        this.mBufferType = i2;
    }

    public void setByteBufferU(ByteBuffer byteBuffer) {
        this.mByteBufferU = byteBuffer;
    }

    public void setByteBufferV(ByteBuffer byteBuffer) {
        this.mByteBufferV = byteBuffer;
    }

    public void setByteBufferY(ByteBuffer byteBuffer) {
        this.mByteBufferY = byteBuffer;
    }

    public void setData(byte[] bArr) {
        this.mData = bArr;
    }

    public void setFormat(int i2) {
        this.mFormat = i2;
    }

    public void setHeight(int i2) {
        this.mHeight = i2;
    }

    public void setRotation(int i2) {
        this.mRotation = i2;
    }

    public void setTextureId(int i2) {
        this.mTextureId = i2;
    }

    public void setTimeStamp(long j2) {
        this.mTimeStamp = j2;
    }

    public void setTransFormMatrix(Matrix matrix) {
        this.mTransFormMatrix = matrix;
    }

    public void setWidth(int i2) {
        this.mWidth = i2;
    }

    public WrappedVideoFrame(VideoFrame videoFrame) {
        this.mTextureId = -1;
        VideoFrame.Buffer buffer = videoFrame.getBuffer();
        if (buffer != null) {
            this.mVideoFrame = videoFrame;
            videoFrame.retain();
            this.mWidth = buffer.getWidth();
            this.mHeight = buffer.getHeight();
            this.mTimeStamp = videoFrame.getTimestampNs();
            this.mRotation = videoFrame.getRotation();
            if (buffer instanceof VideoFrame.TextureBuffer) {
                this.mBufferType = 1;
                VideoFrame.TextureBuffer textureBuffer = (VideoFrame.TextureBuffer) buffer;
                this.mTextureId = textureBuffer.getTextureId();
                this.mTransFormMatrix = textureBuffer.getTransformMatrix();
                if (textureBuffer.getType() == VideoFrame.TextureBuffer.Type.OES) {
                    this.mFormat = 2;
                    return;
                } else {
                    if (textureBuffer.getType() == VideoFrame.TextureBuffer.Type.RGB) {
                        this.mFormat = 1;
                        return;
                    }
                    return;
                }
            }
            if (buffer instanceof NV21Buffer) {
                this.mBufferType = 3;
                this.mFormat = 3;
                this.mData = ((NV21Buffer) buffer).getData();
            } else if (buffer instanceof VideoFrame.I420Buffer) {
                this.mBufferType = 2;
                this.mFormat = 4;
                VideoFrame.I420Buffer i420Buffer = (VideoFrame.I420Buffer) buffer;
                this.mByteBufferY = i420Buffer.getDataY();
                this.mByteBufferU = i420Buffer.getDataU();
                this.mByteBufferV = i420Buffer.getDataV();
            }
        }
    }
}
